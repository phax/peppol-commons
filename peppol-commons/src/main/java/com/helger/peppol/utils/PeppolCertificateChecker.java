/*
 * Copyright (C) 2015-2023 Philip Helger
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

import java.io.IOException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.concurrent.ThreadSafe;
import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.functional.IToBooleanFunction;
import com.helger.commons.state.ETriState;
import com.helger.peppol.utils.CertificateRevocationChecker.AbstractRevocationCheckBuilder;

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
    private final IToBooleanFunction <X509Certificate> m_aValueProvider;

    public PeppolRevocationCache (@Nonnull final IToBooleanFunction <X509Certificate> aValueProvider)
    {
      ValueEnforcer.notNull (aValueProvider, "ValueProvider");
      m_aCache = ExpiringMap.builder ()
                            .expirationPolicy (ExpirationPolicy.CREATED)
                            .expiration (6, TimeUnit.HOURS)
                            .build ();
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
      return m_aCache.computeIfAbsent (sKey, k -> Boolean.valueOf (m_aValueProvider.applyAsBoolean (aCert)))
                     .booleanValue ();
    }

    public void clearCache ()
    {
      m_aCache.clear ();
    }
  }

  public static final boolean DEFAULT_CACHE_OSCP_RESULTS = true;

  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolCertificateChecker.class);

  // Revocation checking stuff
  private static final AtomicBoolean CACHE_OCSP_RESULTS = new AtomicBoolean (DEFAULT_CACHE_OSCP_RESULTS);

  private static final PeppolRevocationCache REVOCATION_CACHE_AP = new PeppolRevocationCache (aCert -> peppolRevocationCheck ().certificate (aCert)
                                                                                                                               .validCAsPeppolAP ()
                                                                                                                               .build ()
                                                                                                                               .isRevoked ());
  private static final PeppolRevocationCache REVOCATION_CACHE_SMP = new PeppolRevocationCache (aCert -> peppolRevocationCheck ().certificate (aCert)
                                                                                                                                .validCAsPeppolSMP ()
                                                                                                                                .build ()
                                                                                                                                .isRevoked ());

  /** Peppol Access Point (AP) stuff */
  private static final ICommonsList <X509Certificate> PEPPOL_AP_CA_CERTS = new CommonsArrayList <> ();
  private static final ICommonsList <X500Principal> PEPPOL_AP_CA_ISSUERS = new CommonsArrayList <> ();

  /** Peppol Service Metadata Publisher (SMP) stuff */
  private static final ICommonsList <X509Certificate> PEPPOL_SMP_CA_CERTS = new CommonsArrayList <> ();
  private static final ICommonsList <X500Principal> PEPPOL_SMP_CA_ISSUERS = new CommonsArrayList <> ();

  static
  {
    // PKI v3 (recursive)
    addTrustedPeppolAPCACertificate (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP);
    addTrustedPeppolAPCACertificate (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_AP);

    addTrustedPeppolSMPCACertificate (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_SMP);
    addTrustedPeppolSMPCACertificate (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_SMP);
  }

  private PeppolCertificateChecker ()
  {}

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

  private static boolean _isCA (@Nonnull final X509Certificate aCert)
  {
    final byte [] aBCBytes = aCert.getExtensionValue (Extension.basicConstraints.getId ());
    if (aBCBytes != null)
    {
      try
      {
        final ASN1Encodable aBCDecoded = JcaX509ExtensionUtils.parseExtensionValue (aBCBytes);
        if (aBCDecoded instanceof ASN1Sequence)
        {
          final ASN1Sequence aBCSequence = (ASN1Sequence) aBCDecoded;
          final BasicConstraints aBasicConstraints = BasicConstraints.getInstance (aBCSequence);
          return aBasicConstraints.isCA ();
        }
      }
      catch (final IOException e)
      {
        // Fall through
      }
    }
    return false;
  }

  /**
   * Register a trusted Peppol AP CA Certificate
   *
   * @param aCert
   *        The CA certificate to be added. May not be <code>null</code>.
   * @throws IllegalArgumentException
   *         If the provided certificate is already trusted
   * @since 9.0.4
   */
  public static void addTrustedPeppolAPCACertificate (@Nonnull final X509Certificate aCert)
  {
    ValueEnforcer.notNull (aCert, "Certificate");

    if (!_isCA (aCert))
      throw new IllegalArgumentException ("The provided AP certificate does not seem to be a CA: " + aCert);

    if (PEPPOL_AP_CA_CERTS.contains (aCert))
      throw new IllegalArgumentException ("AP certificate is already trusted as Peppol AP CA: " + aCert);

    PEPPOL_AP_CA_CERTS.add (aCert);
    PEPPOL_AP_CA_ISSUERS.add (aCert.getSubjectX500Principal ());
  }

  /**
   * Explicitly remove all known trusted Peppol AP CA certificates so that
   * different ones can be added. Handle this with care!
   *
   * @since 9.0.4
   */
  public static void clearTrustedPeppolAPCACertificates ()
  {
    LOGGER.warn ("Explicitly removing all " +
                 PEPPOL_AP_CA_CERTS.size () +
                 " entries from the list of trusted Peppol AP CA certificates");
    PEPPOL_AP_CA_CERTS.clear ();
    PEPPOL_AP_CA_ISSUERS.clear ();
  }

  /**
   * Register a trusted Peppol SMP CA Certificate
   *
   * @param aCert
   *        The CA certificate to be added. May not be <code>null</code>.
   * @throws IllegalArgumentException
   *         If the provided certificate is already trusted
   * @since 9.0.4
   */
  public static void addTrustedPeppolSMPCACertificate (@Nonnull final X509Certificate aCert)
  {
    ValueEnforcer.notNull (aCert, "Certificate");

    if (!_isCA (aCert))
      throw new IllegalArgumentException ("The provided SMP certificate does not seem to be a CA: " + aCert);

    if (PEPPOL_SMP_CA_CERTS.contains (aCert))
      throw new IllegalArgumentException ("SMP certificate is already trusted as Peppol AP CA: " + aCert);

    PEPPOL_SMP_CA_CERTS.add (aCert);
    PEPPOL_SMP_CA_ISSUERS.add (aCert.getSubjectX500Principal ());
  }

  /**
   * Explicitly remove all known trusted Peppol SMP CA certificates so that
   * different ones can be added. Handle this with care!
   *
   * @since 9.0.4
   */
  public static void clearTrustedPeppolSMPCACertificates ()
  {
    LOGGER.warn ("Explicitly removing all " +
                 PEPPOL_SMP_CA_CERTS.size () +
                 " entries from the list of trusted Peppol SMP CA certificates");
    PEPPOL_SMP_CA_CERTS.clear ();
    PEPPOL_SMP_CA_ISSUERS.clear ();
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
   * @return A new {@link PeppolRevocationCheckBuilder} instance.
   * @since 8.5.2
   */
  public static PeppolRevocationCheckBuilder peppolRevocationCheck ()
  {
    return new PeppolRevocationCheckBuilder ();
  }

  /**
   * A special revocation check builder that has specific support for Peppol
   * requirements.
   *
   * @author Philip Helger
   */
  @NotThreadSafe
  public static class PeppolRevocationCheckBuilder extends AbstractRevocationCheckBuilder <PeppolRevocationCheckBuilder>
  {
    /**
     * Use only the Peppol AccessPoint certificates as valid ones.
     *
     * @return this for chaining
     */
    @Nonnull
    public PeppolRevocationCheckBuilder validCAsPeppolAP ()
    {
      return validCAs (PEPPOL_AP_CA_CERTS);
    }

    /**
     * Use only the Peppol SMP certificates as valid ones.
     *
     * @return this for chaining
     */
    @Nonnull
    public PeppolRevocationCheckBuilder validCAsPeppolSMP ()
    {
      return validCAs (PEPPOL_SMP_CA_CERTS);
    }
  }

  /**
   * Check if the provided certificate is a valid certificate.
   *
   * @param aIssuers
   *        The list of valid certificate issuers to check against. May be
   *        <code>null</code> to not perform this check.
   * @param aCache
   *        The cache. May be <code>null</code> to disable caching.
   * @param aRevocationChecker
   *        The revocation checker builder with all necessary parameters already
   *        set. May not be <code>null</code>.
   * @return {@link EPeppolCertificateCheckResult} and never <code>null</code>.
   * @since 8.5.2
   */
  @Nonnull
  public static EPeppolCertificateCheckResult checkCertificate (@Nullable final ICommonsList <X500Principal> aIssuers,
                                                                @Nullable final PeppolRevocationCache aCache,
                                                                @Nonnull final AbstractRevocationCheckBuilder <?> aRevocationChecker)
  {
    ValueEnforcer.notNull (aRevocationChecker, "RevocationChecker");

    final X509Certificate aCert = aRevocationChecker.certificate ();
    if (aCert == null)
      return EPeppolCertificateCheckResult.NO_CERTIFICATE_PROVIDED;

    try
    {
      // null means now
      final Date aCheckDate = aRevocationChecker.checkDate ();
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
      if (aRevocationChecker.build ().isRevoked ())
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
   *        {@link CertificateRevocationChecker#getRevocationCheckMode()}.
   * @return {@link EPeppolCertificateCheckResult} and never <code>null</code>.
   */
  @Nonnull
  public static EPeppolCertificateCheckResult checkPeppolAPCertificate (@Nullable final X509Certificate aCert,
                                                                        @Nullable final OffsetDateTime aCheckDT,
                                                                        @Nonnull final ETriState eCacheOSCResult,
                                                                        @Nullable final ERevocationCheckMode eCheckMode)
  {
    final boolean bCache = eCacheOSCResult.isUndefined () ? isCacheOCSPResults () : eCacheOSCResult.isTrue ();
    return checkCertificate (PEPPOL_AP_CA_ISSUERS,
                             bCache ? REVOCATION_CACHE_AP : null,
                             peppolRevocationCheck ().certificate (aCert)
                                                     .checkDate (aCheckDT)
                                                     .validCAsPeppolAP ()
                                                     .checkMode (eCheckMode));
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
   *        {@link CertificateRevocationChecker#getRevocationCheckMode()}.
   * @return {@link EPeppolCertificateCheckResult} and never <code>null</code>.
   */
  @Nonnull
  public static EPeppolCertificateCheckResult checkPeppolSMPCertificate (@Nullable final X509Certificate aCert,
                                                                         @Nullable final OffsetDateTime aCheckDT,
                                                                         @Nonnull final ETriState eCacheOSCResult,
                                                                         @Nullable final ERevocationCheckMode eCheckMode)
  {
    final boolean bCache = eCacheOSCResult.isUndefined () ? isCacheOCSPResults () : eCacheOSCResult.isTrue ();
    return checkCertificate (PEPPOL_SMP_CA_ISSUERS,
                             bCache ? REVOCATION_CACHE_SMP : null,
                             peppolRevocationCheck ().certificate (aCert)
                                                     .checkDate (aCheckDT)
                                                     .validCAsPeppolSMP ()
                                                     .checkMode (eCheckMode));
  }
}
