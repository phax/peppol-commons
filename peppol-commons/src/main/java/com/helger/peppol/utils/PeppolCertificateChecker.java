/*
 * Copyright (C) 2015-2024 Philip Helger
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

import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.concurrent.ThreadSafe;
import javax.security.auth.x500.X500Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.annotation.ReturnsMutableObject;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.state.ETriState;

/**
 * The Peppol certificate checker
 *
 * @author Philip Helger
 * @since 7.0.4
 */
@ThreadSafe
public final class PeppolCertificateChecker
{
  public static final boolean DEFAULT_CACHE_OSCP_RESULTS = true;

  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolCertificateChecker.class);

  // Revocation checking stuff
  private static final AtomicBoolean CACHE_OCSP_RESULTS = new AtomicBoolean (DEFAULT_CACHE_OSCP_RESULTS);

  /** Peppol Access Point (AP) stuff */
  private static final TrustedCACertificates PEPPOL_AP_TRUSTED = new TrustedCACertificates ();

  /** Peppol Service Metadata Publisher (SMP) stuff */
  private static final TrustedCACertificates PEPPOL_SMP_TRUSTED = new TrustedCACertificates ();

  static
  {
    // PKI v3 (recursive)
    PEPPOL_AP_TRUSTED.addTrustedCACertificate (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP);
    PEPPOL_AP_TRUSTED.addTrustedCACertificate (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_AP);

    PEPPOL_SMP_TRUSTED.addTrustedCACertificate (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_SMP);
    PEPPOL_SMP_TRUSTED.addTrustedCACertificate (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_SMP);
  }

  // Caches don't consider the checking date
  private static final PeppolRevocationCache REVOCATION_CACHE_AP = new PeppolRevocationCache (aCert -> peppolRevocationCheck ().certificate (aCert)
                                                                                                                               .validCAsPeppolAP ()
                                                                                                                               .build (),
                                                                                              PeppolRevocationCache.DEFAULT_CACHING_DURATION);
  private static final PeppolRevocationCache REVOCATION_CACHE_SMP = new PeppolRevocationCache (aCert -> peppolRevocationCheck ().certificate (aCert)
                                                                                                                                .validCAsPeppolSMP ()
                                                                                                                                .build (),
                                                                                               PeppolRevocationCache.DEFAULT_CACHING_DURATION);

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
  @ReturnsMutableObject
  public static PeppolRevocationCache getRevocationCacheAP ()
  {
    return REVOCATION_CACHE_AP;
  }

  @Nonnull
  @ReturnsMutableObject
  public static PeppolRevocationCache getRevocationCacheSMP ()
  {
    return REVOCATION_CACHE_SMP;
  }

  /**
   * @return The list of trusted Peppol AP certificates. Never
   *         <code>null</code>.
   * @since 9.4.0
   */
  @Nonnull
  @ReturnsMutableObject
  public static TrustedCACertificates getTrustedCertificatesAP ()
  {
    return PEPPOL_AP_TRUSTED;
  }

  /**
   * @return The list of trusted Peppol SMP certificates. Never
   *         <code>null</code>.
   * @since 9.4.0
   */
  @Nonnull
  @ReturnsMutableObject
  public static TrustedCACertificates getTrustedCertificatesSMP ()
  {
    return PEPPOL_SMP_TRUSTED;
  }

  /**
   * Register a trusted Peppol AP CA Certificate
   *
   * @param aCert
   *        The CA certificate to be added. May not be <code>null</code>.
   * @throws IllegalArgumentException
   *         If the provided certificate is already trusted
   * @since 9.0.4
   * @deprecated Use
   *             <code>getTrustedCertificatesAP ().addTrustedCACertificate</code>
   *             instead
   */
  @Deprecated (forRemoval = true, since = "9.4.0")
  public static void addTrustedPeppolAPCACertificate (@Nonnull final X509Certificate aCert)
  {
    getTrustedCertificatesAP ().addTrustedCACertificate (aCert);
  }

  /**
   * Explicitly remove all known trusted Peppol AP CA certificates so that
   * different ones can be added. Handle this with care!
   *
   * @since 9.0.4
   * @deprecated Use
   *             <code>getTrustedCertificatesAP ().clearTrustedCACertificates</code>
   *             instead
   */
  @Deprecated (forRemoval = true, since = "9.4.0")
  public static void clearTrustedPeppolAPCACertificates ()
  {
    getTrustedCertificatesAP ().clearTrustedCACertificates ();
  }

  /**
   * @return All the Peppol AP CA certificates currently valid. Neither
   *         <code>null</code> nor empty.
   * @since 8.2.7
   * @deprecated Use
   *             <code>getTrustedCertificatesAP ().getAllTrustedCACertificates</code>
   *             instead
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  @Deprecated (forRemoval = true, since = "9.4.0")
  public static ICommonsList <X509Certificate> getAllPeppolAPCACertificates ()
  {
    return getTrustedCertificatesAP ().getAllTrustedCACertificates ();
  }

  /**
   * @return All the Peppol AP CA issuers currently valid. Neither
   *         <code>null</code> nor empty.
   * @since 8.2.7
   * @deprecated Use
   *             <code>getTrustedCertificatesAP ().getAllTrustedCAIssuers</code>
   *             instead
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  @Deprecated (forRemoval = true, since = "9.4.0")
  public static ICommonsList <X500Principal> getAllPeppolAPCAIssuers ()
  {
    return getTrustedCertificatesAP ().getAllTrustedCAIssuers ();
  }

  /**
   * Register a trusted Peppol SMP CA Certificate
   *
   * @param aCert
   *        The CA certificate to be added. May not be <code>null</code>.
   * @throws IllegalArgumentException
   *         If the provided certificate is already trusted
   * @since 9.0.4
   * @deprecated Use
   *             <code>getTrustedCertificatesSMP ().addTrustedCACertificate</code>
   *             instead
   */
  @Deprecated (forRemoval = true, since = "9.4.0")
  public static void addTrustedPeppolSMPCACertificate (@Nonnull final X509Certificate aCert)
  {
    getTrustedCertificatesSMP ().addTrustedCACertificate (aCert);
  }

  /**
   * Explicitly remove all known trusted Peppol SMP CA certificates so that
   * different ones can be added. Handle this with care!
   *
   * @since 9.0.4
   * @deprecated Use
   *             <code>getTrustedCertificatesSMP ().clearTrustedCACertificates</code>
   *             instead
   */
  @Deprecated (forRemoval = true, since = "9.4.0")
  public static void clearTrustedPeppolSMPCACertificates ()
  {
    getTrustedCertificatesSMP ().clearTrustedCACertificates ();
  }

  /**
   * @return All the Peppol SMP CA certificates currently valid. Neither
   *         <code>null</code> nor empty.
   * @since 8.2.7
   * @deprecated Use
   *             <code>getTrustedCertificatesSMP ().getAllTrustedCACertificates</code>
   *             instead
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  @Deprecated (forRemoval = true, since = "9.4.0")
  public static ICommonsList <X509Certificate> getAllPeppolSMPCACertificates ()
  {
    return getTrustedCertificatesSMP ().getAllTrustedCACertificates ();
  }

  /**
   * @return All the Peppol SMP CA issuers currently valid. Neither
   *         <code>null</code> nor empty.
   * @since 8.2.7
   * @deprecated Use
   *             <code>getTrustedCertificatesSMP ().getAllTrustedCAIssuers</code>
   *             instead
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  @Deprecated (forRemoval = true, since = "9.4.0")
  public static ICommonsList <X500Principal> getAllPeppolSMPCAIssuers ()
  {
    return getTrustedCertificatesSMP ().getAllTrustedCAIssuers ();
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
     * Use the Peppol AP CA certificates as valid ones.
     *
     * @return this for chaining
     */
    @Nonnull
    public PeppolRevocationCheckBuilder validCAsPeppolAP ()
    {
      return validCAs (getTrustedCertificatesAP ().getAllTrustedCACertificates ());
    }

    /**
     * Use only the Peppol SMP CA certificates as valid ones.
     *
     * @return this for chaining
     */
    @Nonnull
    public PeppolRevocationCheckBuilder validCAsPeppolSMP ()
    {
      return validCAs (getTrustedCertificatesSMP ().getAllTrustedCACertificates ());
    }
  }

  /**
   * Check if the provided certificate (from the revocation checker) is a valid
   * certificate. It checks:
   * <ol>
   * <li>Validity at the provided date time (aRevocationChecker.checkDate) or
   * per now if none was provided</li>
   * <li>If the certificate issuer is part of the provided list of issuers</li>
   * <li>If the certificate is revoked</li>
   * </ol>
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

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Running Peppol Certificate Check" +
                    (aIssuers != null ? " against a list of " + aIssuers.size () + " certificate issuers" : "") +
                    (aCache != null ? "; a cache is provided" : "; not using a cache"));

    // Get the certificate to be validated
    final X509Certificate aCert = aRevocationChecker.certificate ();
    if (aCert == null)
    {
      LOGGER.warn ("No Peppol Certificate was provided to the certificate check");
      return EPeppolCertificateCheckResult.NO_CERTIFICATE_PROVIDED;
    }

    // Check validity date
    final Date aCheckDate = aRevocationChecker.checkDate ();
    try
    {
      // null means now
      if (aCheckDate == null)
      {
        if (LOGGER.isDebugEnabled ())
          LOGGER.debug ("Checking the Peppol Certificate validity against the current date time");
        aCert.checkValidity ();
      }
      else
      {
        if (LOGGER.isDebugEnabled ())
          LOGGER.debug ("Checking the Peppol Certificate validity against the provided date time " + aCheckDate);
        aCert.checkValidity (aCheckDate);
      }
    }
    catch (final CertificateNotYetValidException ex)
    {
      LOGGER.warn ("The provided Peppol Certificate is not yet valid per " +
                   (aCheckDate == null ? "now" : aCheckDate.toString ()));
      return EPeppolCertificateCheckResult.NOT_YET_VALID;
    }
    catch (final CertificateExpiredException ex)
    {
      LOGGER.warn ("The provided Peppol Certificate is expired per " +
                   (aCheckDate == null ? "now" : aCheckDate.toString ()));
      return EPeppolCertificateCheckResult.EXPIRED;
    }

    if (aIssuers != null)
    {
      // Check if issuer is known
      final X500Principal aIssuer = aCert.getIssuerX500Principal ();
      if (!aIssuers.contains (aIssuer))
      {
        // Not a valid Peppol certificate
        LOGGER.warn ("The provided Peppol Certificate issuer '" +
                     aIssuer +
                     "' is not in the list of trusted issuers " +
                     aIssuers);
        return EPeppolCertificateCheckResult.UNSUPPORTED_ISSUER;
      }
    }
    else
    {
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Not testing against known Peppol Certificate issuers");
    }

    // Check revocation OCSP/CLR
    if (aCache != null)
    {
      // Caching is enabled
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Testing if the Peppol Certificate is revoked, using a cache");

      final boolean bRevoked = aCache.isRevoked (aCert);
      if (bRevoked)
      {
        LOGGER.warn ("The Peppol Certificate is revoked [caching used]");
        return EPeppolCertificateCheckResult.REVOKED;
      }
    }
    else
    {
      // No caching desired
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Testing if the Peppol Certificate is revoked, without a cache");

      if (aRevocationChecker.build ().isRevoked ())
      {
        LOGGER.warn ("The Peppol Certificate is revoked [no caching]");
        return EPeppolCertificateCheckResult.REVOKED;
      }
    }

    // Done
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("The Peppol Certificate seems to be valid");

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
    final boolean bRevocationCache = eCacheOSCResult.isUndefined () ? isCacheOCSPResults () : eCacheOSCResult.isTrue ();
    return checkCertificate (getTrustedCertificatesAP ().getAllTrustedCAIssuers (),
                             bRevocationCache ? getRevocationCacheAP () : null,
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
    final boolean bRevocationCache = eCacheOSCResult.isUndefined () ? isCacheOCSPResults () : eCacheOSCResult.isTrue ();
    return checkCertificate (getTrustedCertificatesSMP ().getAllTrustedCAIssuers (),
                             bRevocationCache ? getRevocationCacheSMP () : null,
                             peppolRevocationCheck ().certificate (aCert)
                                                     .checkDate (aCheckDT)
                                                     .validCAsPeppolSMP ()
                                                     .checkMode (eCheckMode));
  }
}
