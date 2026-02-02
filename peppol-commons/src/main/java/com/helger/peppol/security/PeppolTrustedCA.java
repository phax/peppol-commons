/*
 * Copyright (C) 2015-2026 Philip Helger
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
package com.helger.peppol.security;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.concurrent.ThreadSafe;
import com.helger.security.certificate.TrustedCAChecker;

/**
 * This class contains all the trusted CAs in Peppol.
 *
 * @author Philip Helger
 * @since 10.2.0
 */
@ThreadSafe
@SuppressWarnings ("deprecation")
public final class PeppolTrustedCA
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolTrustedCA.class);

  private static final TrustedCAChecker TEST_AP = new TrustedCAChecker (PeppolTrustStores.Config2018.CERTIFICATE_PILOT_AP,
                                                                        PeppolTrustStores.Config2025.CERTIFICATE_TEST_AP);
  private static final TrustedCAChecker PROD_AP = new TrustedCAChecker (PeppolTrustStores.Config2018.CERTIFICATE_PRODUCTION_AP,
                                                                        PeppolTrustStores.Config2025.CERTIFICATE_PRODUCTION_AP);
  private static final TrustedCAChecker ALL_AP = new TrustedCAChecker (PeppolTrustStores.Config2018.CERTIFICATE_PILOT_AP,
                                                                       PeppolTrustStores.Config2025.CERTIFICATE_TEST_AP,
                                                                       PeppolTrustStores.Config2018.CERTIFICATE_PRODUCTION_AP,
                                                                       PeppolTrustStores.Config2025.CERTIFICATE_PRODUCTION_AP);

  private static final TrustedCAChecker TEST_SMP = new TrustedCAChecker (PeppolTrustStores.Config2018.CERTIFICATE_PILOT_SMP,
                                                                         PeppolTrustStores.Config2025.CERTIFICATE_TEST_SMP);
  private static final TrustedCAChecker PROD_SMP = new TrustedCAChecker (PeppolTrustStores.Config2018.CERTIFICATE_PRODUCTION_SMP,
                                                                         PeppolTrustStores.Config2025.CERTIFICATE_PRODUCTION_SMP);
  private static final TrustedCAChecker ALL_SMP = new TrustedCAChecker (PeppolTrustStores.Config2018.CERTIFICATE_PILOT_SMP,
                                                                        PeppolTrustStores.Config2025.CERTIFICATE_TEST_SMP,
                                                                        PeppolTrustStores.Config2018.CERTIFICATE_PRODUCTION_SMP,
                                                                        PeppolTrustStores.Config2025.CERTIFICATE_PRODUCTION_SMP);

  private PeppolTrustedCA ()
  {}

  /**
   * @return The Peppol CA checker for Pilot AP certificates.
   */
  @NonNull
  public static TrustedCAChecker peppolTestAP ()
  {
    return TEST_AP;
  }

  /**
   * @return The Peppol CA checker for Production AP certificates.
   */
  @NonNull
  public static TrustedCAChecker peppolProductionAP ()
  {
    return PROD_AP;
  }

  /**
   * @return The Peppol CA checker for Pilot and Production AP certificates.
   */
  @NonNull
  public static TrustedCAChecker peppolAllAP ()
  {
    return ALL_AP;
  }

  /**
   * @return The Peppol CA checker for Pilot SMP certificates.
   */
  @NonNull
  public static TrustedCAChecker peppolTestSMP ()
  {
    return TEST_SMP;
  }

  /**
   * @return The Peppol CA checker for Production SMP certificates.
   */
  @NonNull
  public static TrustedCAChecker peppolProductionSMP ()
  {
    return PROD_SMP;
  }

  /**
   * @return The Peppol CA checker for Pilot and Production SMP certificates.
   */
  @NonNull
  public static TrustedCAChecker peppolAllSMP ()
  {
    return ALL_SMP;
  }

  /**
   * Remove all entries from the revocation cache.
   */
  public static void clearRevocationCheckCache ()
  {
    TEST_AP.getRevocationCache ().clearCache ();
    PROD_AP.getRevocationCache ().clearCache ();
    ALL_AP.getRevocationCache ().clearCache ();

    TEST_SMP.getRevocationCache ().clearCache ();
    PROD_SMP.getRevocationCache ().clearCache ();
    ALL_SMP.getRevocationCache ().clearCache ();

    LOGGER.info ("The PeppolTrustedCA revocation cache was cleared");
  }
}
