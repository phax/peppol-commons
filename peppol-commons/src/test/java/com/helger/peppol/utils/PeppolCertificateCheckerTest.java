/**
 * Copyright (C) 2015-2020 Philip Helger
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
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.time.Month;

import org.junit.Test;

import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.state.ETriState;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.KeyStoreHelper;

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

    // It's the same certificate, but we need one issued by the pilot AP
    e = PeppolCertificateChecker.checkPeppolAPCertificate (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP,
                                                           null,
                                                           ETriState.UNDEFINED,
                                                           ETriState.UNDEFINED);
    assertEquals (EPeppolCertificateCheckResult.UNSUPPORTED_ISSUER, e);
  }

  @Test
  public void testRealAPCert () throws Exception
  {
    final File fAP = new File ("src/test/resources/test-ap.p12");
    if (fAP.exists ())
    {
      final KeyStore aKS = KeyStoreHelper.loadKeyStore (EKeyStoreType.PKCS12, fAP.getAbsolutePath (), "peppol").getKeyStore ();
      assertNotNull (aKS);

      final X509Certificate aCert = (X509Certificate) aKS.getCertificate (aKS.aliases ().nextElement ());
      final EPeppolCertificateCheckResult e = PeppolCertificateChecker.checkPeppolAPCertificate (aCert,
                                                                                                 null,
                                                                                                 ETriState.FALSE,
                                                                                                 ETriState.TRUE);
      assertEquals (EPeppolCertificateCheckResult.VALID, e);
    }
  }

  @Test
  public void testRealSMPCert () throws Exception
  {
    final File fSMP = new File ("src/test/resources/test-smp.p12");
    if (fSMP.exists ())
    {
      final KeyStore aKS = KeyStoreHelper.loadKeyStore (EKeyStoreType.PKCS12, fSMP.getAbsolutePath (), "peppol").getKeyStore ();
      assertNotNull (aKS);

      final X509Certificate aCert = (X509Certificate) aKS.getCertificate (aKS.aliases ().nextElement ());
      final EPeppolCertificateCheckResult e = PeppolCertificateChecker.checkPeppolSMPCertificate (aCert,
                                                                                                  null,
                                                                                                  ETriState.FALSE,
                                                                                                  ETriState.TRUE);
      assertEquals (EPeppolCertificateCheckResult.VALID, e);
    }
  }
}
