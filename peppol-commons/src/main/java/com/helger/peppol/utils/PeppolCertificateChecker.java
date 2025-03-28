/*
 * Copyright (C) 2015-2025 Philip Helger
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
import java.util.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.security.auth.x500.X500Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.security.revocation.AbstractRevocationCheckBuilder;
import com.helger.security.revocation.RevocationCheckResultCache;

/**
 * The Peppol certificate checker
 *
 * @author Philip Helger
 * @since 7.0.4
 */
@ThreadSafe
public final class PeppolCertificateChecker
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolCertificateChecker.class);

  private static final PeppolCAChecker TEST_AP = new PeppolCAChecker (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP);
  private static final PeppolCAChecker PROD_AP = new PeppolCAChecker (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_AP);
  private static final PeppolCAChecker ALL_AP = new PeppolCAChecker (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP,
                                                                     PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_AP);

  private static final PeppolCAChecker TEST_SMP = new PeppolCAChecker (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_SMP);
  private static final PeppolCAChecker PROD_SMP = new PeppolCAChecker (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_SMP);
  private static final PeppolCAChecker ALL_SMP = new PeppolCAChecker (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_SMP,
                                                                      PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_SMP);

  private static final PeppolCAChecker TEST_EB2B_AP = new PeppolCAChecker (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_EB2B_AP);
  private static final PeppolCAChecker PROD_EB2B_AP = new PeppolCAChecker (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_EB2B_AP);

  private PeppolCertificateChecker ()
  {}

  /**
   * @return The Peppol CA checker for Pilot AP certificates.
   * @since 9.6.0
   */
  @Nonnull
  public static PeppolCAChecker peppolTestAP ()
  {
    return TEST_AP;
  }

  /**
   * @return The Peppol CA checker for Production AP certificates.
   * @since 9.6.0
   */
  @Nonnull
  public static PeppolCAChecker peppolProductionAP ()
  {
    return PROD_AP;
  }

  /**
   * @return The Peppol CA checker for Pilot and Production AP certificates.
   * @since 9.6.0
   */
  @Nonnull
  public static PeppolCAChecker peppolAllAP ()
  {
    return ALL_AP;
  }

  /**
   * @return The Peppol CA checker for Pilot SMP certificates.
   * @since 9.6.0
   */
  @Nonnull
  public static PeppolCAChecker peppolTestSMP ()
  {
    return TEST_SMP;
  }

  /**
   * @return The Peppol CA checker for Production SMP certificates.
   * @since 9.6.0
   */
  @Nonnull
  public static PeppolCAChecker peppolProductionSMP ()
  {
    return PROD_SMP;
  }

  /**
   * @return The Peppol CA checker for Pilot and Production SMP certificates.
   * @since 9.6.0
   */
  @Nonnull
  public static PeppolCAChecker peppolAllSMP ()
  {
    return ALL_SMP;
  }

  /**
   * @return The Peppol CA checker for Pilot eB2B AP certificates.
   * @since 9.6.0
   */
  @Nonnull
  public static PeppolCAChecker peppolTestEb2bAP ()
  {
    return TEST_EB2B_AP;
  }

  /**
   * @return The Peppol CA checker for production eB2B AP certificates.
   * @since 10.0.1
   */
  @Nonnull
  public static PeppolCAChecker peppolProductionEb2bAP ()
  {
    return PROD_EB2B_AP;
  }

  /**
   * Remove all entries from the OSCP cache.
   */
  public static void clearRevocationCheckCache ()
  {
    TEST_AP.clearRevocationCache ();
    PROD_AP.clearRevocationCache ();
    ALL_AP.clearRevocationCache ();

    TEST_SMP.clearRevocationCache ();
    PROD_SMP.clearRevocationCache ();
    ALL_SMP.clearRevocationCache ();

    TEST_EB2B_AP.clearRevocationCache ();
    PROD_EB2B_AP.clearRevocationCache ();

    LOGGER.info ("The PeppolCertificateChecker revocation cache was cleared");
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
   * @param aRevocationCache
   *        The cache. May be <code>null</code> to disable caching.
   * @param aRevocationChecker
   *        The revocation checker builder with all necessary parameters already
   *        set. May not be <code>null</code>.
   * @return {@link EPeppolCertificateCheckResult} and never <code>null</code>.
   * @since 8.5.2
   */
  @Nonnull
  public static EPeppolCertificateCheckResult checkCertificate (@Nullable final ICommonsSet <X500Principal> aIssuers,
                                                                @Nullable final RevocationCheckResultCache aRevocationCache,
                                                                @Nonnull final AbstractRevocationCheckBuilder <?> aRevocationChecker)
  {
    ValueEnforcer.notNull (aRevocationChecker, "RevocationChecker");

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Running Peppol Certificate Check" +
                    (aIssuers != null ? " against a list of " + aIssuers.size () + " certificate issuers" : "") +
                    (aRevocationCache != null ? "; a cache is provided" : "; not using a cache"));

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
    if (aRevocationCache != null)
    {
      // Caching is enabled
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Testing if the Peppol Certificate is revoked, using a cache");

      final boolean bRevoked = aRevocationCache.isRevoked (aCert);
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
}
