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
package com.helger.dbnalliance.commons.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.Month;

import org.junit.Test;

import com.helger.commons.datetime.PDTFactory;
import com.helger.peppol.utils.EPeppolCertificateCheckResult;
import com.helger.peppol.utils.PeppolCAChecker;
import com.helger.peppol.utils.PeppolCertificateChecker;

/**
 * Test class for class {@link DBNAllianceTrustStores}.
 *
 * @author Philip Helger
 */
public final class DBNAllianceTrustStoresTest
{
  @Test
  public void testConstants ()
  {
    assertNotNull (DBNAllianceTrustStores.Config2023.CERTIFICATE_PILOT_ROOT);
    assertNotNull (DBNAllianceTrustStores.Config2023.CERTIFICATE_PILOT_INTERMEDIATE);
  }

  @Test
  public void testBasic ()
  {
    final PeppolCAChecker aDemoCA = new PeppolCAChecker (DBNAllianceTrustStores.Config2023.CERTIFICATE_PILOT_INTERMEDIATE);

    EPeppolCertificateCheckResult e = PeppolCertificateChecker.peppolTestAP ().checkCertificate (null);
    assertEquals (EPeppolCertificateCheckResult.NO_CERTIFICATE_PROVIDED, e);

    e = aDemoCA.checkCertificate (DBNAllianceTrustStores.Config2023.CERTIFICATE_PILOT_INTERMEDIATE,
                                  PDTFactory.createOffsetDateTime (2000, Month.JANUARY, 1));
    assertEquals (EPeppolCertificateCheckResult.NOT_YET_VALID, e);

    e = aDemoCA.checkCertificate (DBNAllianceTrustStores.Config2023.CERTIFICATE_PILOT_INTERMEDIATE,
                                  PDTFactory.createOffsetDateTime (2099, Month.JANUARY, 1));
    assertEquals (EPeppolCertificateCheckResult.EXPIRED, e);
  }
}
