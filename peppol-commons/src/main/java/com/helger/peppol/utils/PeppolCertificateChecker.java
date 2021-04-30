/**
 * Copyright (C) 2015-2021 Philip Helger
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
import java.security.cert.CertPathValidatorException;
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
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import javax.security.auth.x500.X500Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.CommonsHashSet;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.commons.concurrent.SimpleReadWriteLock;
import com.helger.commons.datetime.PDTFactory;
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
  @Deprecated
  public static final boolean DEFAULT_OSCP_CHECK_ENABLED = true;
  public static final boolean DEFAULT_CACHE_OSCP_RESULTS = true;
  public static final ERevocationCheckMode DEFAULT_REVOCATION_CHECK_MODE = ERevocationCheckMode.OCSP;
  public static final boolean DEFAULT_ALLOW_SOFT_FAIL = false;

  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolCertificateChecker.class);

  /** Peppol Access Point (AP) stuff */
  private static final ICommonsList <X509Certificate> PEPPOL_AP_CA_CERTS = new CommonsArrayList <> ();
  private static final ICommonsList <X500Principal> PEPPOL_AP_CA_ISSUERS = new CommonsArrayList <> ();

  /** Peppol Service Metadata Publisher (SMP) stuff */
  private static final ICommonsList <X509Certificate> PEPPOL_SMP_CA_CERTS = new CommonsArrayList <> ();
  private static final ICommonsList <X500Principal> PEPPOL_SMP_CA_ISSUERS = new CommonsArrayList <> ();

  static
  {
    // PKI v3 (recursive)
    PEPPOL_AP_CA_CERTS.add (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP);
    PEPPOL_AP_CA_CERTS.add (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_AP);

    PEPPOL_SMP_CA_CERTS.add (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_SMP);
    PEPPOL_SMP_CA_CERTS.add (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_SMP);

    // all issuers (1 level only)
    PEPPOL_AP_CA_ISSUERS.add (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP.getSubjectX500Principal ());
    PEPPOL_AP_CA_ISSUERS.add (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_AP.getSubjectX500Principal ());

    PEPPOL_SMP_CA_ISSUERS.add (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_SMP.getSubjectX500Principal ());
    PEPPOL_SMP_CA_ISSUERS.add (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_SMP.getSubjectX500Principal ());
  }

  private static final AtomicBoolean CACHE_OCSP_RESULTS = new AtomicBoolean (DEFAULT_CACHE_OSCP_RESULTS);
  private static final SimpleReadWriteLock RW_LOCK = new SimpleReadWriteLock ();
  private static ERevocationCheckMode s_eRevocationCheckMode = DEFAULT_REVOCATION_CHECK_MODE;
  private static final AtomicBoolean ALLOW_SOFT_FAIL = new AtomicBoolean (DEFAULT_ALLOW_SOFT_FAIL);
  @GuardedBy ("RW_LOCK")
  private static Consumer <? super GeneralSecurityException> s_aExceptionHdl = ex -> LOGGER.warn ("Certificate is revoked", ex);
  @GuardedBy ("RW_LOCK")
  private static Consumer <? super List <CertPathValidatorException>> s_aSoftFailExceptionHdl = exs -> LOGGER.warn ("Certificate revocation check succeeded but has messages: " +
                                                                                                                    exs);

  /**
   * An revocation cache that checks the revocation status of each certificate
   * and keeps the status for up to 6 hours.
   *
   * @author Philip Helger
   */
  @ThreadSafe
  public static final class PeppolRevocationCache
  {
    private final ExpiringMap <String, Boolean> m_aCache;
    private final Function <X509Certificate, Boolean> m_aValueProvider;

    public PeppolRevocationCache (@Nonnull final Function <X509Certificate, Boolean> aValueProvider)
    {
      ValueEnforcer.notNull (aValueProvider, "ValueProvider");
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
                                                                                                                                                      (ERevocationCheckMode) null,
                                                                                                                                                      getExceptionHdl ())));
  private static final PeppolRevocationCache REVOCATION_CACHE_SMP = new PeppolRevocationCache (aCert -> Boolean.valueOf (isPeppolSMPCertificateRevoked (aCert,
                                                                                                                                                        null,
                                                                                                                                                        (ERevocationCheckMode) null,
                                                                                                                                                        getExceptionHdl ())));

  private PeppolCertificateChecker ()
  {}

  /**
   * @return <code>true</code> if OSCP checks are enabled, <code>false</code> if
   *         not. The default is {@value #DEFAULT_OSCP_CHECK_ENABLED}.
   * @deprecated Since 8.3.0. Use {@link #getRevocationCheckMode()} instead
   */
  @Deprecated
  public static boolean isOCSPEnabled ()
  {
    return getRevocationCheckMode ().isOCSP ();
  }

  /**
   * Enable or disable OSCP checks.
   *
   * @param bCheck
   *        <code>true</code> to enable them, <code>false</code> to disable
   *        them.
   * @deprecated Since 8.3.0. Use
   *             {@link #setRevocationCheckMode(ERevocationCheckMode)} instead
   */
  @Deprecated
  public static void setOCSPEnabled (final boolean bCheck)
  {
    // Legacy behaviour
    setRevocationCheckMode (bCheck ? ERevocationCheckMode.OCSP_BEFORE_CRL : ERevocationCheckMode.CRL);
  }

  /**
   * @return The global revocation check mode. Never <code>null</code>. The
   *         default is {@link ERevocationCheckMode#OCSP}.
   * @since 8.3.0
   */
  @Nonnull
  public static ERevocationCheckMode getRevocationCheckMode ()
  {
    return RW_LOCK.readLockedGet ( () -> s_eRevocationCheckMode);
  }

  /**
   * Set the global revocation check mode to use, if no specific mode was
   * provided.
   *
   * @param eRevocationCheckMode
   *        The global revocation check mode to use. May not be
   *        <code>null</code>.
   * @since 8.3.0
   */
  public static void setRevocationCheckMode (@Nonnull final ERevocationCheckMode eRevocationCheckMode)
  {
    ValueEnforcer.notNull (eRevocationCheckMode, "RevocationCheckMode");
    RW_LOCK.writeLockedGet ( () -> s_eRevocationCheckMode = eRevocationCheckMode);
    LOGGER.info ("Global PeppolCertificateChecker revocation mode was set to: " + eRevocationCheckMode);
  }

  /**
   * Allow revocation check to succeed if the revocation status cannot be
   * determined for one of the following reasons:
   * <ul>
   * <li>The CRL or OCSP response cannot be obtained because of a network
   * error.</li>
   * <li>The OCSP responder returns one of the following errors specified in
   * section 2.3 of RFC 2560: internalError or tryLater.</li>
   * </ul>
   * Note that these conditions apply to both OCSP and CRLs, and unless the
   * NO_FALLBACK option is set, the revocation check is allowed to succeed only
   * if both mechanisms fail under one of the conditions as stated
   * above.Exceptions that cause the network errors are ignored but can be later
   * retrieved by calling the getSoftFailExceptions method.
   *
   * @return <code>true</code> if soft fail is enabled, <code>false</code> if
   *         not. Default is defined by {@link #DEFAULT_ALLOW_SOFT_FAIL}.
   * @since 8.5.2
   */
  public static boolean isAllowSoftFail ()
  {
    return ALLOW_SOFT_FAIL.get ();
  }

  /**
   * Set enable "soft fail" mode.
   *
   * @param bAllow
   *        <code>true</code> to allow it, <code>false</code> to disallow it.
   * @since 8.5.2
   */
  public static void setAllowSoftFail (final boolean bAllow)
  {
    ALLOW_SOFT_FAIL.set (bAllow);
    LOGGER.info ("Global PeppolCertificateChecker allows for soft fail: " + bAllow);
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
    LOGGER.info ("Global PeppolCertificateChecker OCSP cache enabled: " + bCache);
  }

  /**
   * Remove all entries from the OSCP cache.
   */
  public static void clearOCSPCache ()
  {
    REVOCATION_CACHE_AP.clearCache ();
    REVOCATION_CACHE_SMP.clearCache ();
    LOGGER.info ("The PeppolCertificateChecker OCSP cache was cleared");
  }

  @Nonnull
  public static PeppolRevocationCache getRevocationCacheAP ()
  {
    return REVOCATION_CACHE_AP;
  }

  @Nonnull
  public static PeppolRevocationCache getRevocationCacheSMP ()
  {
    return REVOCATION_CACHE_SMP;
  }

  /**
   * @return All the Peppol AP CA certificates currently valid. Neither
   *         <code>null</code> nor empty.
   * @since 8.2.7
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public static ICommonsList <X509Certificate> getAllPeppolAPCACertificates ()
  {
    return PEPPOL_AP_CA_CERTS.getClone ();
  }

  /**
   * @return All the Peppol AP CA issuers currently valid. Neither
   *         <code>null</code> nor empty.
   * @since 8.2.7
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public static ICommonsList <X500Principal> getAllPeppolAPCAIssuers ()
  {
    return PEPPOL_AP_CA_ISSUERS.getClone ();
  }

  /**
   * @return All the Peppol SMP CA certificates currently valid. Neither
   *         <code>null</code> nor empty.
   * @since 8.2.7
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public static ICommonsList <X509Certificate> getAllPeppolSMPCACertificates ()
  {
    return PEPPOL_SMP_CA_CERTS.getClone ();
  }

  /**
   * @return All the Peppol SMP CA issuers currently valid. Neither
   *         <code>null</code> nor empty.
   * @since 8.2.7
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public static ICommonsList <X500Principal> getAllPeppolSMPCAIssuers ()
  {
    return PEPPOL_SMP_CA_ISSUERS.getClone ();
  }

  /**
   * @return The exception handler to be invoked in case of an exception in
   *         certificate checking.
   */
  @Nonnull
  public static Consumer <? super GeneralSecurityException> getExceptionHdl ()
  {
    return RW_LOCK.readLockedGet ( () -> s_aExceptionHdl);
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
    RW_LOCK.writeLockedGet ( () -> s_aExceptionHdl = aExceptionHdl);
  }

  /**
   * @return The handler to be invoked in case of failures in certificate
   *         checking.
   * @see #isAllowSoftFail()
   * @since 8.5.2
   */
  @Nonnull
  public static Consumer <? super List <CertPathValidatorException>> getSoftFailExceptionHdl ()
  {
    return RW_LOCK.readLockedGet ( () -> s_aSoftFailExceptionHdl);
  }

  /**
   * Set the handler to be invoked, if certificate checking has soft failures.
   *
   * @param aSoftFailExceptionHdl
   *        The handler to be used. May not be <code>null</code>.
   * @see #isAllowSoftFail()
   * @since 8.5.2
   */
  public static void setSoftFailExceptionHdl (@Nonnull final Consumer <? super List <CertPathValidatorException>> aSoftFailExceptionHdl)
  {
    ValueEnforcer.notNull (aSoftFailExceptionHdl, "SoftFailExceptionHdl");
    RW_LOCK.writeLockedGet ( () -> s_aSoftFailExceptionHdl = aSoftFailExceptionHdl);
  }

  /**
   * Check if a certificate is revoked based on the available CA certificates.
   *
   * @param aCert
   *        The certificate to be check. May not be <code>null</code>.
   * @param aValidCAs
   *        The list of allowed CA certificates to be used. May neither be
   *        <code>null</code> nor empty.
   * @param aCheckDate
   *        The check date time. May be <code>null</code>.
   * @param eCheckMode
   *        Possibility to define the revocation checking mode. May be
   *        <code>null</code> to indicate to use the global one from
   *        {@link #getRevocationCheckMode()}.
   * @param aExceptionHdl
   *        The exception handler to be used. May not be <code>null</code>.
   * @return <code>true</code> if it is revoked, <code>false</code> if not.
   * @deprecated Since 8.5.2 Use
   *             {@link #isCertificateRevoked(X509Certificate, ICommonsList, Date, ERevocationCheckMode, Consumer, Consumer)}
   *             instead
   */
  @Deprecated
  public static boolean isCertificateRevoked (@Nonnull final X509Certificate aCert,
                                              @Nonnull final ICommonsList <X509Certificate> aValidCAs,
                                              @Nullable final Date aCheckDate,
                                              @Nullable final ERevocationCheckMode eCheckMode,
                                              @Nonnull final Consumer <? super GeneralSecurityException> aExceptionHdl)
  {
    return isCertificateRevoked (aCert, aValidCAs, aCheckDate, eCheckMode, aExceptionHdl, getSoftFailExceptionHdl ());
  }

  /**
   * Check if a certificate is revoked based on the available CA certificates.
   *
   * @param aCert
   *        The certificate to be check. May not be <code>null</code>.
   * @param aValidCAs
   *        The list of allowed CA certificates to be used. May neither be
   *        <code>null</code> nor empty.
   * @param aCheckDate
   *        The check date time. May be <code>null</code>.
   * @param eCheckMode
   *        Possibility to define the revocation checking mode. May be
   *        <code>null</code> to indicate to use the global one from
   *        {@link #getRevocationCheckMode()}.
   * @param aExceptionHdl
   *        The exception handler to be used for a critical exception. May not
   *        be <code>null</code>.
   * @param aSoftFailExceptionHdl
   *        The handler for "soft fail" exceptions. That means the certificate
   *        is considered valid, but something went wrong. Only filled if
   *        {@link #isAllowSoftFail()} is enabled.
   * @return <code>true</code> if it is revoked, <code>false</code> if not.
   */
  public static boolean isCertificateRevoked (@Nonnull final X509Certificate aCert,
                                              @Nonnull final ICommonsList <X509Certificate> aValidCAs,
                                              @Nullable final Date aCheckDate,
                                              @Nullable final ERevocationCheckMode eCheckMode,
                                              @Nonnull final Consumer <? super GeneralSecurityException> aExceptionHdl,
                                              @Nonnull final Consumer <? super List <CertPathValidatorException>> aSoftFailExceptionHdl)
  {
    ValueEnforcer.notNull (aCert, "Cert");
    ValueEnforcer.notEmpty (aValidCAs, "ValidCAs");
    ValueEnforcer.notNull (aExceptionHdl, "ExceptionHdl");
    ValueEnforcer.notNull (aSoftFailExceptionHdl, "aSoftFailExceptionHdl");

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Performing certificate revocation check on certificate '" +
                    aCert.getSubjectX500Principal ().getName () +
                    "' " +
                    (aCheckDate != null ? "for datetime " + aCheckDate : "without a datetime"));

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
      final ERevocationCheckMode eRealCheckMode = eCheckMode == null ? getRevocationCheckMode () : eCheckMode;
      if (eRealCheckMode.isNone ())
      {
        // No revocation check
      }
      else
      {
        if (LOGGER.isDebugEnabled ())
          LOGGER.debug ("Certificate check is performed using revocation mode " + eRealCheckMode);

        try
        {
          Security.setProperty ("ocsp.enable", Boolean.toString (eRealCheckMode.isOCSP ()));
        }
        catch (final SecurityException ex)
        {
          LOGGER.warn ("Failed to set Security property 'ocsp.enable' to '" + eRealCheckMode.isOCSP () + "'");
        }

        if (aCheckDate != null)
        {
          // Check at specific date
          aPKIXParams.setDate (aCheckDate);
        }

        // Specify a list of intermediate certificates ("Collection" is a key in
        // the "SUN" security provider)
        final CertStore aIntermediateCertStore = CertStore.getInstance ("Collection", new CollectionCertStoreParameters (aValidCAs));
        aPKIXParams.addCertStore (aIntermediateCertStore);

        if (LOGGER.isDebugEnabled ())
          LOGGER.debug ("Checking certificate\n" + aCert + "\n\nagainst " + aValidCAs.size () + " valid CAs:\n" + aValidCAs);

        // Throws an exception in case of an error
        final CertPathBuilder aCPB = CertPathBuilder.getInstance ("PKIX");
        final PKIXRevocationChecker rc = (PKIXRevocationChecker) aCPB.getRevocationChecker ();

        // Call once, to avoid it is changed during the execution of the method
        final boolean bAllowSoftFail = isAllowSoftFail ();

        // Build checking options
        final EnumSet <PKIXRevocationChecker.Option> aOptions = EnumSet.of (PKIXRevocationChecker.Option.ONLY_END_ENTITY);
        if (bAllowSoftFail)
          aOptions.add (PKIXRevocationChecker.Option.SOFT_FAIL);
        eRealCheckMode.addAllOptionsTo (aOptions);
        if (LOGGER.isDebugEnabled ())
          LOGGER.debug ("OCSP/CLR effective options = " + aOptions);
        rc.setOptions (aOptions);

        final PKIXCertPathBuilderResult aBuilderResult = (PKIXCertPathBuilderResult) aCPB.build (aPKIXParams);
        if (LOGGER.isDebugEnabled ())
          LOGGER.debug ("OCSP/CLR builder result = " + aBuilderResult);

        final CertPathValidator aCPV = CertPathValidator.getInstance ("PKIX");
        final PKIXCertPathValidatorResult aValidateResult = (PKIXCertPathValidatorResult) aCPV.validate (aBuilderResult.getCertPath (),
                                                                                                         aPKIXParams);
        if (LOGGER.isDebugEnabled ())
          LOGGER.debug ("OCSP/CLR validation result = " + aValidateResult);

        if (bAllowSoftFail)
          aSoftFailExceptionHdl.accept (rc.getSoftFailExceptions ());
      }

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
   * @param eCheckMode
   *        Possibility to override the OSCP checking flag on a per query basis.
   *        May be <code>null</code> to use the global flag from
   *        {@link #getRevocationCheckMode()}.
   * @param aExceptionHdl
   *        The exception handler to be used. May not be <code>null</code>.
   * @return <code>true</code> if it is revoked, <code>false</code> if not.
   */
  public static boolean isPeppolAPCertificateRevoked (@Nonnull final X509Certificate aCert,
                                                      @Nullable final LocalDateTime aCheckDT,
                                                      @Nullable final ERevocationCheckMode eCheckMode,
                                                      @Nonnull final Consumer <? super GeneralSecurityException> aExceptionHdl)
  {
    return isCertificateRevoked (aCert,
                                 PEPPOL_AP_CA_CERTS,
                                 aCheckDT == null ? null : PDTFactory.createDate (aCheckDT),
                                 eCheckMode,
                                 aExceptionHdl,
                                 getSoftFailExceptionHdl ());
  }

  /**
   * Check if a Peppol SMP certificate is revoked.
   *
   * @param aCert
   *        The certificate to be check. May not be <code>null</code>.
   * @param aCheckDT
   *        The check date time. May be <code>null</code>.
   * @param eCheckMode
   *        Possibility to override the OSCP checking flag on a per query basis.
   *        May be <code>null</code> to use the global flag from
   *        {@link #getRevocationCheckMode()}.
   * @param aExceptionHdl
   *        The exception handler to be used. May not be <code>null</code>.
   * @return <code>true</code> if it is revoked, <code>false</code> if not.
   */
  public static boolean isPeppolSMPCertificateRevoked (@Nonnull final X509Certificate aCert,
                                                       @Nullable final LocalDateTime aCheckDT,
                                                       @Nullable final ERevocationCheckMode eCheckMode,
                                                       @Nonnull final Consumer <? super GeneralSecurityException> aExceptionHdl)
  {
    return isCertificateRevoked (aCert,
                                 PEPPOL_SMP_CA_CERTS,
                                 aCheckDT == null ? null : PDTFactory.createDate (aCheckDT),
                                 eCheckMode,
                                 aExceptionHdl,
                                 getSoftFailExceptionHdl ());
  }

  /**
   * Check if the provided certificate is a valid certificate.
   *
   * @param aCert
   *        The certificate to be checked. May be <code>null</code>.
   * @param aCheckDate
   *        The check date and time to use. May be <code>null</code> which means
   *        "now".
   * @param aIssuers
   *        The list of valid certificate issuers to check against. May be
   *        <code>null</code> to not perform this check.
   * @param aValidCAs
   *        List of valid CAs to check against. May not be <code>null</code>.
   * @param aCache
   *        The cache. May be <code>null</code> to disable caching.
   * @param eCheckMode
   *        Possibility to override the OSCP checking flag on a per query basis.
   *        May be <code>null</code> to use the global flag from
   *        {@link #getRevocationCheckMode()}.
   * @return {@link EPeppolCertificateCheckResult} and never <code>null</code>.
   * @since 8.2.7
   */
  @Nonnull
  public static EPeppolCertificateCheckResult checkCertificate (@Nullable final X509Certificate aCert,
                                                                @Nullable final Date aCheckDate,
                                                                @Nullable final ICommonsList <X500Principal> aIssuers,
                                                                @Nonnull final ICommonsList <X509Certificate> aValidCAs,
                                                                @Nullable final PeppolRevocationCache aCache,
                                                                @Nullable final ERevocationCheckMode eCheckMode)
  {
    return checkCertificate (aCert, aCheckDate, aIssuers, aValidCAs, aCache, eCheckMode, getExceptionHdl (), getSoftFailExceptionHdl ());
  }

  /**
   * Check if the provided certificate is a valid certificate.
   *
   * @param aCert
   *        The certificate to be checked. May be <code>null</code>.
   * @param aCheckDate
   *        The check date and time to use. May be <code>null</code> which means
   *        "now".
   * @param aIssuers
   *        The list of valid certificate issuers to check against. May be
   *        <code>null</code> to not perform this check.
   * @param aValidCAs
   *        List of valid CAs to check against. May not be <code>null</code>.
   * @param aCache
   *        The cache. May be <code>null</code> to disable caching.
   * @param eCheckMode
   *        Possibility to override the OSCP checking flag on a per query basis.
   *        May be <code>null</code> to use the global flag from
   *        {@link #getRevocationCheckMode()}.
   * @param aExceptionHdl
   *        The exception handler to be used for a critical exception. May not
   *        be <code>null</code>.
   * @param aSoftFailExceptionHdl
   *        The handler for "soft fail" exceptions. That means the certificate
   *        is considered valid, but something went wrong. Only filled if
   *        {@link #isAllowSoftFail()} is enabled.
   * @return {@link EPeppolCertificateCheckResult} and never <code>null</code>.
   * @since 8.5.2
   */
  @Nonnull
  public static EPeppolCertificateCheckResult checkCertificate (@Nullable final X509Certificate aCert,
                                                                @Nullable final Date aCheckDate,
                                                                @Nullable final ICommonsList <X500Principal> aIssuers,
                                                                @Nonnull final ICommonsList <X509Certificate> aValidCAs,
                                                                @Nullable final PeppolRevocationCache aCache,
                                                                @Nullable final ERevocationCheckMode eCheckMode,
                                                                @Nonnull final Consumer <? super GeneralSecurityException> aExceptionHdl,
                                                                @Nonnull final Consumer <? super List <CertPathValidatorException>> aSoftFailExceptionHdl)
  {
    ValueEnforcer.notNull (aValidCAs, "ValidCAs");
    ValueEnforcer.notNull (aExceptionHdl, "ExceptionHdl");
    ValueEnforcer.notNull (aSoftFailExceptionHdl, "SoftFailExceptionHdl");

    if (aCert == null)
      return EPeppolCertificateCheckResult.NO_CERTIFICATE_PROVIDED;

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

    if (aIssuers != null)
    {
      // Check if issuer is known
      final X500Principal aIssuer = aCert.getIssuerX500Principal ();
      if (!aIssuers.contains (aIssuer))
      {
        // Not a PEPPOL AP certificate
        return EPeppolCertificateCheckResult.UNSUPPORTED_ISSUER;
      }
    }
    else
    {
      LOGGER.debug ("Not testing against known certificate issuers");
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
      if (isCertificateRevoked (aCert, aValidCAs, aCheckDate, eCheckMode, aExceptionHdl, aSoftFailExceptionHdl))
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
   * @param eCheckMode
   *        Possibility to override the OSCP checking flag on a per query basis.
   *        May be <code>null</code> to use the global flag from
   *        {@link #getRevocationCheckMode()}.
   * @return {@link EPeppolCertificateCheckResult} and never <code>null</code>.
   */
  @Nonnull
  public static EPeppolCertificateCheckResult checkPeppolAPCertificate (@Nullable final X509Certificate aCert,
                                                                        @Nullable final OffsetDateTime aCheckDT,
                                                                        @Nonnull final ETriState eCacheOSCResult,
                                                                        @Nullable final ERevocationCheckMode eCheckMode)
  {
    final boolean bCache = eCacheOSCResult.isUndefined () ? isCacheOCSPResults () : eCacheOSCResult.isTrue ();
    return checkCertificate (aCert,
                             aCheckDT == null ? null : PDTFactory.createDate (aCheckDT),
                             PEPPOL_AP_CA_ISSUERS,
                             PEPPOL_AP_CA_CERTS,
                             bCache ? REVOCATION_CACHE_AP : null,
                             eCheckMode);
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
   * @param eCheckMode
   *        Possibility to override the OSCP checking flag on a per query basis.
   *        May be <code>null</code> to use the global flag from
   *        {@link #getRevocationCheckMode()}.
   * @return {@link EPeppolCertificateCheckResult} and never <code>null</code>.
   */
  @Nonnull
  public static EPeppolCertificateCheckResult checkPeppolSMPCertificate (@Nullable final X509Certificate aCert,
                                                                         @Nullable final OffsetDateTime aCheckDT,
                                                                         @Nonnull final ETriState eCacheOSCResult,
                                                                         @Nullable final ERevocationCheckMode eCheckMode)
  {
    final boolean bCache = eCacheOSCResult.isUndefined () ? isCacheOCSPResults () : eCacheOSCResult.isTrue ();
    return checkCertificate (aCert,
                             aCheckDT == null ? null : PDTFactory.createDate (aCheckDT),
                             PEPPOL_SMP_CA_ISSUERS,
                             PEPPOL_SMP_CA_CERTS,
                             bCache ? REVOCATION_CACHE_SMP : null,
                             eCheckMode);
  }
}
