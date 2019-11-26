/**
 * Copyright (C) 2015-2019 Philip Helger
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

import static org.junit.Assert.assertEquals;

import java.time.Month;

import org.junit.Test;

import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.state.ETriState;

/**
 * Test class for class {@link PeppolCertificateChecker}
 *
 * @author Philip Helger
 */
public class PeppolCertificateCheckerTest
{
  @Test
  public void testBasic ()
  {
    EPeppolCertificateCheckResult e = PeppolCertificateChecker.checkPeppolAPCertificate (null,
                                                                                         null,
                                                                                         ETriState.UNDEFINED,
                                                                                         ETriState.UNDEFINED);
    assertEquals (EPeppolCertificateCheckResult.NO_CERTIFICATE_PROVIDED, e);

    e = PeppolCertificateChecker.checkPeppolAPCertificate (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP,
                                                           PDTFactory.createLocalDateTime (2000, Month.JANUARY, 1),
                                                           ETriState.UNDEFINED,
                                                           ETriState.UNDEFINED);
    assertEquals (EPeppolCertificateCheckResult.NOT_YET_VALID, e);

    e = PeppolCertificateChecker.checkPeppolAPCertificate (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP,
                                                           PDTFactory.createLocalDateTime (2099, Month.JANUARY, 1),
                                                           ETriState.UNDEFINED,
                                                           ETriState.UNDEFINED);
    assertEquals (EPeppolCertificateCheckResult.EXPIRED, e);

    e = PeppolCertificateChecker.checkPeppolAPCertificate (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP,
                                                           null,
                                                           ETriState.UNDEFINED,
                                                           ETriState.UNDEFINED);
    assertEquals (EPeppolCertificateCheckResult.UNSUPPORTED_ISSUER, e);
  }
}
