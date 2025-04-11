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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.security.auth.x500.X500Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.security.certificate.CertificateHelper;
import com.helger.security.certificate.ECertificateCheckResult;
import com.helger.security.certificate.TrustedCAChecker;
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

  private static final TrustedCAChecker TEST_AP = new TrustedCAChecker (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP);
  private static final TrustedCAChecker PROD_AP = new TrustedCAChecker (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_AP);
  private static final TrustedCAChecker ALL_AP = new TrustedCAChecker (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP,
                                                                       PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_AP);

  private static final TrustedCAChecker TEST_SMP = new TrustedCAChecker (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_SMP);
  private static final TrustedCAChecker PROD_SMP = new TrustedCAChecker (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_SMP);
  private static final TrustedCAChecker ALL_SMP = new TrustedCAChecker (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_SMP,
                                                                        PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_SMP);

  private static final TrustedCAChecker TEST_EB2B_AP = new TrustedCAChecker (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_EB2B_AP);
  private static final TrustedCAChecker PROD_EB2B_AP = new TrustedCAChecker (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_EB2B_AP);

  private PeppolCertificateChecker ()
  {}

  /**
   * @return The Peppol CA checker for Pilot AP certificates.
   * @since 9.6.0
   */
  @Nonnull
  public static TrustedCAChecker peppolTestAP ()
  {
    return TEST_AP;
  }

  /**
   * @return The Peppol CA checker for Production AP certificates.
   * @since 9.6.0
   */
  @Nonnull
  public static TrustedCAChecker peppolProductionAP ()
  {
    return PROD_AP;
  }

  /**
   * @return The Peppol CA checker for Pilot and Production AP certificates.
   * @since 9.6.0
   */
  @Nonnull
  public static TrustedCAChecker peppolAllAP ()
  {
    return ALL_AP;
  }

  /**
   * @return The Peppol CA checker for Pilot SMP certificates.
   * @since 9.6.0
   */
  @Nonnull
  public static TrustedCAChecker peppolTestSMP ()
  {
    return TEST_SMP;
  }

  /**
   * @return The Peppol CA checker for Production SMP certificates.
   * @since 9.6.0
   */
  @Nonnull
  public static TrustedCAChecker peppolProductionSMP ()
  {
    return PROD_SMP;
  }

  /**
   * @return The Peppol CA checker for Pilot and Production SMP certificates.
   * @since 9.6.0
   */
  @Nonnull
  public static TrustedCAChecker peppolAllSMP ()
  {
    return ALL_SMP;
  }

  /**
   * @return The Peppol CA checker for Pilot eB2B AP certificates.
   * @since 9.6.0
   */
  @Nonnull
  public static TrustedCAChecker peppolTestEb2bAP ()
  {
    return TEST_EB2B_AP;
  }

  /**
   * @return The Peppol CA checker for production eB2B AP certificates.
   * @since 10.0.1
   */
  @Nonnull
  public static TrustedCAChecker peppolProductionEb2bAP ()
  {
    return PROD_EB2B_AP;
  }

  /**
   * Remove all entries from the OSCP cache.
   */
  public static void clearRevocationCheckCache ()
  {
    TEST_AP.getRevocationCache ().clearCache ();
    PROD_AP.getRevocationCache ().clearCache ();
    ALL_AP.getRevocationCache ().clearCache ();

    TEST_SMP.getRevocationCache ().clearCache ();
    PROD_SMP.getRevocationCache ().clearCache ();
    ALL_SMP.getRevocationCache ().clearCache ();

    TEST_EB2B_AP.getRevocationCache ().clearCache ();
    PROD_EB2B_AP.getRevocationCache ().clearCache ();

    LOGGER.info ("The PeppolCertificateChecker revocation cache was cleared");
  }

  /**
   * Check if the provided certificate (from the revocation checker) is a valid certificate. It
   * checks:
   * <ol>
   * <li>Validity at the provided date time (aRevocationChecker.checkDate) or per now if none was
   * provided</li>
   * <li>If the certificate issuer is part of the provided list of issuers</li>
   * <li>If the certificate is revoked</li>
   * </ol>
   *
   * @param aIssuers
   *        The list of valid certificate issuers to check against. May be <code>null</code> to not
   *        perform this check.
   * @param aRevocationCache
   *        The cache. May be <code>null</code> to disable caching.
   * @param aRevocationChecker
   *        The revocation checker builder with all necessary parameters already set. May not be
   *        <code>null</code>.
   * @return {@link ECertificateCheckResult} and never <code>null</code>.
   * @since 8.5.2
   * @deprecated Use
   *             {@link CertificateHelper#checkCertificate(ICommonsSet, RevocationCheckResultCache, AbstractRevocationCheckBuilder)}
   *             instead
   */
  @Nonnull
  @Deprecated (forRemoval = true, since = "10.2.0")
  public static ECertificateCheckResult checkCertificate (@Nullable final ICommonsSet <X500Principal> aIssuers,
                                                          @Nullable final RevocationCheckResultCache aRevocationCache,
                                                          @Nonnull final AbstractRevocationCheckBuilder <?> aRevocationChecker)
  {
    return CertificateHelper.checkCertificate (aIssuers, aRevocationCache, aRevocationChecker);
  }
}
