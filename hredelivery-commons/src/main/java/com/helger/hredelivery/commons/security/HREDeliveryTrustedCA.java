/*
 * Copyright (C) 2025-2026 Philip Helger
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
package com.helger.hredelivery.commons.security;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.concurrent.ThreadSafe;
import com.helger.security.certificate.TrustedCAChecker;

/**
 * This class contains all the trusted CAs in HR eDelivery.
 *
 * @author Philip Helger
 * @since 12.1.1
 */
@ThreadSafe
public final class HREDeliveryTrustedCA
{
  private static final Logger LOGGER = LoggerFactory.getLogger (HREDeliveryTrustedCA.class);

  private static final TrustedCAChecker FINA_DEMO = new TrustedCAChecker (HREDeliveryTrustStores.Fina2015.CERTIFICATE_DEMO_CA_2020);
  private static final TrustedCAChecker FINA_PROD = new TrustedCAChecker (HREDeliveryTrustStores.Fina2015.CERTIFICATE_PRODUCTION_RDC_2020,
                                                                          HREDeliveryTrustStores.Fina2015.CERTIFICATE_PRODUCTION_RDC_2025);
  private static final TrustedCAChecker FINA_ALL = new TrustedCAChecker (HREDeliveryTrustStores.Fina2015.CERTIFICATE_DEMO_CA_2020,
                                                                         HREDeliveryTrustStores.Fina2015.CERTIFICATE_PRODUCTION_RDC_2020,
                                                                         HREDeliveryTrustStores.Fina2015.CERTIFICATE_PRODUCTION_RDC_2025);

  private HREDeliveryTrustedCA ()
  {}

  /**
   * @return The CA checker for HR eDelivery Fina Demo.
   */
  @NonNull
  public static TrustedCAChecker hrEdeliveryFinaDemo ()
  {
    return FINA_DEMO;
  }

  /**
   * @return The CA checker for HR eDelivery Fina Production.
   */
  @NonNull
  public static TrustedCAChecker hrEdeliveryFinaProduction ()
  {
    return FINA_PROD;
  }

  /**
   * @return The CA checker for HR eDelivery Fina Demo+Production.
   */
  @NonNull
  public static TrustedCAChecker hrEdeliveryFinaAll ()
  {
    return FINA_ALL;
  }

  /**
   * Remove all entries from the revocation cache.
   */
  public static void clearRevocationCheckCache ()
  {
    FINA_DEMO.getRevocationCache ().clearCache ();
    FINA_PROD.getRevocationCache ().clearCache ();
    FINA_ALL.getRevocationCache ().clearCache ();

    LOGGER.info ("The HREDeliveryTrustedCA revocation cache was cleared");
  }
}
