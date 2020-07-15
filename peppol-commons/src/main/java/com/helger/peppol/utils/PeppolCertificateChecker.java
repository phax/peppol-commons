/**
 * Copyright (C) 2015-2020 Philip Helger
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.peppol.utils;

import java.security.GeneralSecurityException;
import java.security.Security;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathValidator;
import java.security.cert.CertStore;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXRevocationChecker;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import javax.security.auth.x500.X500Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.CommonsHashSet;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.commons.concurrent.SimpleReadWriteLock;
import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.functional.IFunction;
import com.helger.commons.state.ETriState;
import com.helger.commons.timing.StopWatch;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

/**
 * The Peppol certificate checker
 *
 * @author Philip Helger
 * @since 7.0.4
 */
@ThreadSafe
public final class PeppolCertificateChecker
{
  public static final boolean DEFAULT_OSCP_CHECK_ENABLED = true;
  public static final boolean DEFAULT_CACHE_OSCP_RESULTS = true;

  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolCertificateChecker.class);

  /** Peppol Access Point (AP) stuff */
  private static final ICommonsList <X509Certificate> PEPPOL_AP_CA_CERTS = new CommonsArrayList <> ();
  private static final ICommonsList <X500Principal> PEPPOL_AP_CA_ISSUERS;

  /** Peppol Service Metadata Publisher (SMP) stuff */
  private static final ICommonsList <X509Certificate> PEPPOL_SMP_CA_CERTS = new CommonsArrayList <> ();
  private static final ICommonsList <X500Principal> PEPPOL_SMP_CA_ISSUERS;

  static
  {
    // PKI v3
    PEPPOL_AP_CA_CERTS.add (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP);
    PEPPOL_AP_CA_CERTS.add (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_AP);
    PEPPOL_SMP_CA_CERTS.add (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_SMP);
    PEPPOL_SMP_CA_CERTS.add (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_SMP);

    // all issuers
    PEPPOL_AP_CA_ISSUERS = new CommonsArrayList <> (PEPPOL_AP_CA_CERTS, X509Certificate::getSubjectX500Principal);
    PEPPOL_SMP_CA_ISSUERS = new CommonsArrayList <> (PEPPOL_SMP_CA_CERTS, X509Certificate::getSubjectX500Principal);
  }

  private static final AtomicBoolean OCSP_ENABLED = new AtomicBoolean (DEFAULT_OSCP_CHECK_ENABLED);
  private static final AtomicBoolean CACHE_OCSP_RESULTS = new AtomicBoolean (DEFAULT_CACHE_OSCP_RESULTS);
  private static final SimpleReadWriteLock s_aRWLock = new SimpleReadWriteLock ();
  @GuardedBy ("s_aRWLock")
  private static Consumer <? super GeneralSecurityException> s_aExceptionHdl = ex -> LOGGER.warn ("Certificate is revoked", ex);

  /**
   * An revocation cache that checks the revocation status of each certificate
   * and keeps the status for up to 6 hours.
   *
   * @author Philip Helger
   */
  @ThreadSafe
  private static final class PeppolRevocationCache
  {
    private final ExpiringMap <String, Boolean> m_aCache;
    private final IFunction <X509Certificate, Boolean> m_aValueProvider;

    public PeppolRevocationCache (@Nonnull final IFunction <X509Certificate, Boolean> aValueProvider)
    {
      m_aCache = ExpiringMap.builder ().expirationPolicy (ExpirationPolicy.CREATED).expiration (6, TimeUnit.HOURS).build ();
      m_aValueProvider = aValueProvider;
    }

    @Nonnull
    private static String _getKey (@Nonnull final X509Certificate aCert)
    {
      return aCert.getSubjectX500Principal ().getName () + "-" + aCert.getSerialNumber ().toString ();
    }

    public boolean isRevoked (@Nonnull final X509Certificate aCert)
    {
      final String sKey = _getKey (aCert);
      return m_aCache.computeIfAbsent (sKey, k -> m_aValueProvider.apply (aCert)).booleanValue ();
    }

    public void clearCache ()
    {
      m_aCache.clear ();
    }
  }

  private static final PeppolRevocationCache REVOCATION_CACHE_AP = new PeppolRevocationCache (aCert -> Boolean.valueOf (isPeppolAPCertificateRevoked (aCert,
                                                                                                                                                      null,
                                                                                                                                                      ETriState.UNDEFINED,
                                                                                                                                                      getExceptionHdl ())));
  private static final PeppolRevocationCache REVOCATION_CACHE_SMP = new PeppolRevocationCache (aCert -> Boolean.valueOf (isPeppolSMPCertificateRevoked (aCert,
                                                                                                                                                        null,
                                                                                                                                                        ETriState.UNDEFINED,
                                                                                                                                                        getExceptionHdl ())));

  private PeppolCertificateChecker ()
  {}

  /**
   * @return <code>true</code> if OSCP checks are enabled, <code>false</code> if
   *         not. The default is {@value #DEFAULT_OSCP_CHECK_ENABLED}.
   */
  public static boolean isOCSPEnabled ()
  {
    return OCSP_ENABLED.get ();
  }

  /**
   * Enable or disable OSCP checks.
   *
   * @param bCheck
   *        <code>true</code> to enable them, <code>false</code> to disable
   *        them.
   */
  public static void setOCSPEnabled (final boolean bCheck)
  {
    OCSP_ENABLED.set (bCheck);
  }

  /**
   * @return <code>true</code> if OSCP results may be cached, <code>false</code>
   *         if not. The default is {@value #DEFAULT_CACHE_OSCP_RESULTS}.
   */
  public static boolean isCacheOCSPResults ()
  {
    return CACHE_OCSP_RESULTS.get ();
  }

  /**
   * Enable or disable caching of OSCP results.
   *
   * @param bCache
   *        <code>true</code> to enable caching, <code>false</code> to disable
   *        it.
   */
  public static void setCacheOCSPResults (final boolean bCache)
  {
    CACHE_OCSP_RESULTS.set (bCache);
  }

  /**
   * Remove all entries from the OSCP cache.
   */
  public static void clearOCSPCache ()
  {
    REVOCATION_CACHE_AP.clearCache ();
    REVOCATION_CACHE_SMP.clearCache ();
  }

  /**
   * @return The exception handler to be invoked in case of an exception in
   *         certificate checking.
   */
  @Nonnull
  public static Consumer <? super GeneralSecurityException> getExceptionHdl ()
  {
    return s_aRWLock.readLockedGet ( () -> s_aExceptionHdl);
  }

  /**
   * Set the exception handler to be invoked, if certificate checking throws an
   * exception.
   *
   * @param aExceptionHdl
   *        The exception handler to be used. May not be <code>null</code>.
   */
  public static void setExceptionHdl (@Nonnull final Consumer <? super GeneralSecurityException> aExceptionHdl)
  {
    ValueEnforcer.notNull (aExceptionHdl, "ExceptionHdl");
    s_aRWLock.writeLockedGet ( () -> s_aExceptionHdl = aExceptionHdl);
  }

  /**
   * Check if a certificate is revoked based on the available CA certificates.
   *
   * @param aCert
   *        The certificate to be check. May not be <code>null</code>.
   * @param aValidCAs
   *        The list of allowed CA certificates to be used. May neither be
   *        <code>null</code> nor empty.
   * @param aCheckDT
   *        The check date time. May be <code>null</code>.
   * @param eCheckOSCP
   *        Possibility to override the OSCP checking flag on a per query basis.
   *        Use {@link ETriState#UNDEFINED} to only use the global flag.
   * @param aExceptionHdl
   *        The exception handler to be used. May not be <code>null</code>.
   * @return <code>true</code> if it is revoked, <code>false</code> if not.
   */
  public static boolean isCertificateRevoked (@Nonnull final X509Certificate aCert,
                                              @Nonnull final ICommonsList <X509Certificate> aValidCAs,
                                              @Nullable final LocalDateTime aCheckDT,
                                              @Nonnull final ETriState eCheckOSCP,
                                              @Nonnull final Consumer <? super GeneralSecurityException> aExceptionHdl)
  {
    ValueEnforcer.notNull (aCert, "Cert");
    ValueEnforcer.notEmpty (aValidCAs, "ValidCAs");
    ValueEnforcer.notNull (eCheckOSCP, "CheckOSCP");
    ValueEnforcer.notNull (aExceptionHdl, "ExceptionHdl");

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Performing certificate revocation check on certificate '" +
                    aCert.getSubjectX500Principal ().getName () +
                    "'" +
                    (aCheckDT != null ? " for datetime " + aCheckDT : ""));

    // check OCSP and CLR
    final StopWatch aSW = StopWatch.createdStarted ();
    try
    {
      final X509CertSelector aSelector = new X509CertSelector ();
      aSelector.setCertificate (aCert);

      // Certificate -> trust anchors; name constraints MUST be null
      final ICommonsSet <TrustAnchor> aTrustAnchors = new CommonsHashSet <> (aValidCAs, x -> new TrustAnchor (x, null));
      final PKIXBuilderParameters aPKIXParams = new PKIXBuilderParameters (aTrustAnchors, aSelector);
      aPKIXParams.setRevocationEnabled (true);

      // Enable On-Line Certificate Status Protocol (OCSP) support
      final boolean bEnableOCSP = eCheckOSCP.isUndefined () ? isOCSPEnabled () : eCheckOSCP.isTrue ();
      try
      {
        Security.setProperty ("ocsp.enable", Boolean.toString (bEnableOCSP));
      }
      catch (final SecurityException ex)
      {
        LOGGER.warn ("Failed to set Security property 'ocsp.enable' to '" + bEnableOCSP + "'");
      }

      if (aCheckDT != null)
      {
        // Check at what date?
        final Date aCheckDate = PDTFactory.createDate (aCheckDT);
        aPKIXParams.setDate (aCheckDate);
      }

      // Specify a list of intermediate certificates ("Collection" is a key in
      // the "SUN" security provider)
      final CertStore aIntermediateCertStore = CertStore.getInstance ("Collection", new CollectionCertStoreParameters (aValidCAs));
      aPKIXParams.addCertStore (aIntermediateCertStore);

      // Throws an exception in case of an error
      final CertPathBuilder aCPB = CertPathBuilder.getInstance ("PKIX");
      final PKIXRevocationChecker rc = (PKIXRevocationChecker) aCPB.getRevocationChecker ();
      // OCSP over CLR is the default
      // Allow fallback to CLR
      rc.setOptions (EnumSet.of (PKIXRevocationChecker.Option.ONLY_END_ENTITY));

      final PKIXCertPathBuilderResult aBuilderResult = (PKIXCertPathBuilderResult) aCPB.build (aPKIXParams);
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("OCSP/CLR builder result = " + aBuilderResult);

      final CertPathValidator aCPV = CertPathValidator.getInstance ("PKIX");
      final PKIXCertPathValidatorResult aValidateResult = (PKIXCertPathValidatorResult) aCPV.validate (aBuilderResult.getCertPath (),
                                                                                                       aPKIXParams);
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("OCSP/CLR validation result = " + aValidateResult);

      return false;
    }
    catch (final GeneralSecurityException ex)
    {
      aExceptionHdl.accept (ex);
      return true;
    }
    finally
    {
      final long nMillis = aSW.stopAndGetMillis ();
      if (nMillis > 500)
        LOGGER.warn ("OCSP/CLR revocation check took " + nMillis + " milliseconds which is too long");
      else
        if (LOGGER.isDebugEnabled ())
          LOGGER.debug ("OCSP/CLR revocation check took " + nMillis + " milliseconds");
    }
  }

  /**
   * Check if a Peppol AP certificate is revoked.
   *
   * @param aCert
   *        The certificate to be check. May not be <code>null</code>.
   * @param aCheckDT
   *        The check date time. May be <code>null</code>.
   * @param eCheckOSCP
   *        Possibility to override the OSCP checking flag on a per query basis.
   *        Use {@link ETriState#UNDEFINED} to only use the global flag.
   * @param aExceptionHdl
   *        The exception handler to be used. May not be <code>null</code>.
   * @return <code>true</code> if it is revoked, <code>false</code> if not.
   */
  public static boolean isPeppolAPCertificateRevoked (@Nonnull final X509Certificate aCert,
                                                      @Nullable final LocalDateTime aCheckDT,
                                                      @Nonnull final ETriState eCheckOSCP,
                                                      @Nonnull final Consumer <? super GeneralSecurityException> aExceptionHdl)
  {
    return isCertificateRevoked (aCert, PEPPOL_AP_CA_CERTS, aCheckDT, eCheckOSCP, aExceptionHdl);
  }

  /**
   * Check if a Peppol SMP certificate is revoked.
   *
   * @param aCert
   *        The certificate to be check. May not be <code>null</code>.
   * @param aCheckDT
   *        The check date time. May be <code>null</code>.
   * @param eCheckOSCP
   *        Possibility to override the OSCP checking flag on a per query basis.
   *        Use {@link ETriState#UNDEFINED} to only use the global flag.
   * @param aExceptionHdl
   *        The exception handler to be used. May not be <code>null</code>.
   * @return <code>true</code> if it is revoked, <code>false</code> if not.
   */
  public static boolean isPeppolSMPCertificateRevoked (@Nonnull final X509Certificate aCert,
                                                       @Nullable final LocalDateTime aCheckDT,
                                                       @Nonnull final ETriState eCheckOSCP,
                                                       @Nonnull final Consumer <? super GeneralSecurityException> aExceptionHdl)
  {
    return isCertificateRevoked (aCert, PEPPOL_SMP_CA_CERTS, aCheckDT, eCheckOSCP, aExceptionHdl);
  }

  @Nonnull
  private static EPeppolCertificateCheckResult _checkCertificate (@Nullable final X509Certificate aCert,
                                                                  @Nullable final LocalDateTime aCheckDT,
                                                                  @Nonnull final ICommonsList <X500Principal> aIssuers,
                                                                  @Nonnull final ICommonsList <X509Certificate> aValidCAs,
                                                                  @Nullable final PeppolRevocationCache aCache,
                                                                  @Nonnull final ETriState eCheckOSCP)
  {
    if (aCert == null)
      return EPeppolCertificateCheckResult.NO_CERTIFICATE_PROVIDED;

    // Check date valid
    final Date aCheckDate = PDTFactory.createDate (aCheckDT);
    try
    {
      // null means now
      if (aCheckDate == null)
        aCert.checkValidity ();
      else
        aCert.checkValidity (aCheckDate);
    }
    catch (final CertificateNotYetValidException ex)
    {
      return EPeppolCertificateCheckResult.NOT_YET_VALID;
    }
    catch (final CertificateExpiredException ex)
    {
      return EPeppolCertificateCheckResult.EXPIRED;
    }

    // Check if issuer is known
    final X500Principal aIssuer = aCert.getIssuerX500Principal ();
    if (!aIssuers.contains (aIssuer))
    {
      // Not a PEPPOL AP certificate
      return EPeppolCertificateCheckResult.UNSUPPORTED_ISSUER;
    }

    // Check OCSP/CLR
    if (aCache != null)
    {
      final boolean bRevoked = aCache.isRevoked (aCert);
      if (bRevoked)
        return EPeppolCertificateCheckResult.REVOKED;
    }
    else
    {
      // No caching desired
      if (isCertificateRevoked (aCert, aValidCAs, aCheckDT, eCheckOSCP, getExceptionHdl ()))
        return EPeppolCertificateCheckResult.REVOKED;
    }

    return EPeppolCertificateCheckResult.VALID;
  }

  /**
   * Check if the provided certificate is a valid Peppol AP certificate.
   *
   * @param aCert
   *        The certificate to be checked. May be <code>null</code>.
   * @param aCheckDT
   *        The check date and time to use. May be <code>null</code> which means
   *        "now".
   * @param eCacheOSCResult
   *        Possibility to override the usage of OSCP caching flag on a per
   *        query basis. Use {@link ETriState#UNDEFINED} to solely use the
   *        global flag.
   * @param eCheckOSCP
   *        Possibility to override the OSCP checking flag on a per query basis.
   *        Use {@link ETriState#UNDEFINED} to solely use the global flag.
   * @return {@link EPeppolCertificateCheckResult} and never <code>null</code>.
   */
  @Nonnull
  public static EPeppolCertificateCheckResult checkPeppolAPCertificate (@Nullable final X509Certificate aCert,
                                                                        @Nullable final LocalDateTime aCheckDT,
                                                                        @Nonnull final ETriState eCacheOSCResult,
                                                                        @Nonnull final ETriState eCheckOSCP)
  {
    final boolean bCache = eCacheOSCResult.isUndefined () ? isCacheOCSPResults () : eCacheOSCResult.isTrue ();
    return _checkCertificate (aCert, aCheckDT, PEPPOL_AP_CA_ISSUERS, PEPPOL_AP_CA_CERTS, bCache ? REVOCATION_CACHE_AP : null, eCheckOSCP);
  }

  /**
   * Check if the provided certificate is a valid Peppol SMP certificate.
   *
   * @param aCert
   *        The certificate to be checked. May be <code>null</code>.
   * @param aCheckDT
   *        The check date and time to use. May be <code>null</code> which means
   *        "now".
   * @param eCacheOSCResult
   *        Possibility to override the usage of OSCP caching flag on a per
   *        query basis. Use {@link ETriState#UNDEFINED} to solely use the
   *        global flag.
   * @param eCheckOSCP
   *        Possibility to override the OSCP checking flag on a per query basis.
   *        Use {@link ETriState#UNDEFINED} to solely use the global flag.
   * @return {@link EPeppolCertificateCheckResult} and never <code>null</code>.
   */
  @Nonnull
  public static EPeppolCertificateCheckResult checkPeppolSMPCertificate (@Nullable final X509Certificate aCert,
                                                                         @Nullable final LocalDateTime aCheckDT,
                                                                         @Nonnull final ETriState eCacheOSCResult,
                                                                         @Nonnull final ETriState eCheckOSCP)
  {
    final boolean bCache = eCacheOSCResult.isUndefined () ? isCacheOCSPResults () : eCacheOSCResult.isTrue ();
    return _checkCertificate (aCert,
                              aCheckDT,
                              PEPPOL_SMP_CA_ISSUERS,
                              PEPPOL_SMP_CA_CERTS,
                              bCache ? REVOCATION_CACHE_SMP : null,
                              eCheckOSCP);
  }
}
