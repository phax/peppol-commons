/*
 * Copyright (C) 2025 Philip Helger
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.Month;

import org.junit.Test;

import com.helger.datetime.helper.PDTFactory;
import com.helger.hredelivery.commons.security.HREDeliveryTrustStores.Fina2015;
import com.helger.security.certificate.ECertificateCheckResult;
import com.helger.security.certificate.TrustedCAChecker;

/**
 * Test class for class {@link HREDeliveryTrustStores}.
 *
 * @author Philip Helger
 */
public final class HREDeliveryTrustStoresTest
{
  @Test
  public void testConstants ()
  {
    assertNotNull (Fina2015.TRUSTSTORE_PRODUCTION);
    assertNotNull (Fina2015.CERTIFICATE_PRODUCTION_ROOT);
    assertNotNull (Fina2015.CERTIFICATE_PRODUCTION_RDC_2020);
    assertNotNull (Fina2015.CERTIFICATE_PRODUCTION_RDC_2025);

    assertNotNull (Fina2015.TRUSTSTORE_DEMO);
    assertNotNull (Fina2015.CERTIFICATE_DEMO_ROOT);
    assertNotNull (Fina2015.CERTIFICATE_DEMO_CA_2020);
  }

  @Test
  public void testBasic ()
  {
    final TrustedCAChecker aDemoCA = new TrustedCAChecker (Fina2015.CERTIFICATE_DEMO_ROOT);

    ECertificateCheckResult e = aDemoCA.checkCertificate (null);
    assertEquals (ECertificateCheckResult.NO_CERTIFICATE_PROVIDED, e);

    e = aDemoCA.checkCertificate (Fina2015.CERTIFICATE_DEMO_CA_2020,
                                  PDTFactory.createOffsetDateTime (2000, Month.JANUARY, 1));
    assertEquals (ECertificateCheckResult.NOT_YET_VALID, e);

    e = aDemoCA.checkCertificate (Fina2015.CERTIFICATE_DEMO_CA_2020,
                                  PDTFactory.createOffsetDateTime (2099, Month.JANUARY, 1));
    assertEquals (ECertificateCheckResult.EXPIRED, e);
  }
}
