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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.time.Month;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.state.ETriState;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.KeyStoreHelper;
import com.helger.security.revocation.ERevocationCheckMode;

/**
 * Test class for class {@link PeppolCertificateChecker}
 *
 * @author Philip Helger
 */
public class PeppolCertificateCheckerTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolCertificateCheckerTest.class);

  @Test
  public void testBasic ()
  {
    EPeppolCertificateCheckResult e = PeppolCertificateChecker.peppolTestAP ().checkCertificate (null);
    assertEquals (EPeppolCertificateCheckResult.NO_CERTIFICATE_PROVIDED, e);

    e = PeppolCertificateChecker.peppolTestAP ()
                                .checkCertificate (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP,
                                                   PDTFactory.createOffsetDateTime (2000, Month.JANUARY, 1));
    assertEquals (EPeppolCertificateCheckResult.NOT_YET_VALID, e);

    e = PeppolCertificateChecker.peppolTestAP ()
                                .checkCertificate (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP,
                                                   PDTFactory.createOffsetDateTime (2099, Month.JANUARY, 1));
    assertEquals (EPeppolCertificateCheckResult.EXPIRED, e);

    // It's the same certificate, but we need one issued by the pilot AP
    e = PeppolCertificateChecker.peppolTestAP ()
                                .checkCertificate (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP);
    assertEquals (EPeppolCertificateCheckResult.UNSUPPORTED_ISSUER, e);
  }

  @Test
  public void testRealAPCert () throws Exception
  {
    // As keystores are usually not in the repository, this test is no-op if the
    // file is not present
    final File fAP = new File ("src/test/resources/test-ap-2023.p12");
    if (fAP.exists ())
    {
      LOGGER.info ("Checking the local AP test certificate");

      final KeyStore aKS = KeyStoreHelper.loadKeyStore (EKeyStoreType.PKCS12,
                                                        fAP.getAbsolutePath (),
                                                        "peppol".toCharArray ())
                                         .getKeyStore ();
      assertNotNull (aKS);

      final X509Certificate aCert = (X509Certificate) aKS.getCertificate (aKS.aliases ().nextElement ());
      assertNotNull (aCert);

      EPeppolCertificateCheckResult e;

      LOGGER.info ("Checking with OCSP_BEFORE_CRL");
      e = PeppolCertificateChecker.peppolTestAP ()
                                  .checkCertificate (aCert,
                                                     null,
                                                     ETriState.FALSE,
                                                     ERevocationCheckMode.OCSP_BEFORE_CRL);
      assertEquals (EPeppolCertificateCheckResult.VALID, e);

      LOGGER.info ("Checking with OCSP");
      e = PeppolCertificateChecker.peppolTestAP ()
                                  .checkCertificate (aCert, null, ETriState.FALSE, ERevocationCheckMode.OCSP);
      assertEquals (EPeppolCertificateCheckResult.VALID, e);

      LOGGER.info ("Checking with CRL_BEFORE_OCSP");
      e = PeppolCertificateChecker.peppolTestAP ()
                                  .checkCertificate (aCert,
                                                     null,
                                                     ETriState.FALSE,
                                                     ERevocationCheckMode.CRL_BEFORE_OCSP);
      assertEquals (EPeppolCertificateCheckResult.VALID, e);

      LOGGER.info ("Checking with CRL");
      e = PeppolCertificateChecker.peppolTestAP ()
                                  .checkCertificate (aCert, null, ETriState.FALSE, ERevocationCheckMode.CRL);
      assertEquals (EPeppolCertificateCheckResult.VALID, e);

      // Try again with CRL only to ensure it's not downloaded again
      LOGGER.info ("Checking with CRL");
      e = PeppolCertificateChecker.peppolTestAP ()
                                  .checkCertificate (aCert, null, ETriState.FALSE, ERevocationCheckMode.CRL);
      assertEquals (EPeppolCertificateCheckResult.VALID, e);

      LOGGER.info ("Checking with NONE");
      e = PeppolCertificateChecker.peppolTestAP ()
                                  .checkCertificate (aCert, null, ETriState.FALSE, ERevocationCheckMode.NONE);
      assertEquals (EPeppolCertificateCheckResult.VALID, e);
    }
    else
      LOGGER.info ("Expected AP keystore was not found");
  }

  @Test
  public void testRealSMPCert () throws Exception
  {
    // As keystores are usually not in the repository, this test is no-op if the
    // file is not present
    final File fSMP = new File ("src/test/resources/test-smp-2023.p12");
    if (fSMP.exists ())
    {
      LOGGER.info ("Checking the local SMP test certificate");

      final KeyStore aKS = KeyStoreHelper.loadKeyStore (EKeyStoreType.PKCS12,
                                                        fSMP.getAbsolutePath (),
                                                        "peppol".toCharArray ())
                                         .getKeyStore ();
      assertNotNull (aKS);

      final X509Certificate aCert = (X509Certificate) aKS.getCertificate (aKS.aliases ().nextElement ());
      assertNotNull (aCert);

      EPeppolCertificateCheckResult e;

      LOGGER.info ("Checking with OCSP_BEFORE_CRL");
      e = PeppolCertificateChecker.peppolTestSMP ()
                                  .checkCertificate (aCert,
                                                     null,
                                                     ETriState.FALSE,
                                                     ERevocationCheckMode.OCSP_BEFORE_CRL);
      assertEquals (EPeppolCertificateCheckResult.VALID, e);

      LOGGER.info ("Checking with OCSP");
      e = PeppolCertificateChecker.peppolTestSMP ()
                                  .checkCertificate (aCert, null, ETriState.FALSE, ERevocationCheckMode.OCSP);
      assertEquals (EPeppolCertificateCheckResult.VALID, e);

      LOGGER.info ("Checking with CRL_BEFORE_OCSP");
      e = PeppolCertificateChecker.peppolTestSMP ()
                                  .checkCertificate (aCert,
                                                     null,
                                                     ETriState.FALSE,
                                                     ERevocationCheckMode.CRL_BEFORE_OCSP);
      assertEquals (EPeppolCertificateCheckResult.VALID, e);

      LOGGER.info ("Checking with CRL");
      e = PeppolCertificateChecker.peppolTestSMP ()
                                  .checkCertificate (aCert, null, ETriState.FALSE, ERevocationCheckMode.CRL);
      assertEquals (EPeppolCertificateCheckResult.VALID, e);

      // Try again with CRL only to ensure it's not downloaded again
      LOGGER.info ("Checking with CRL");
      e = PeppolCertificateChecker.peppolTestSMP ()
                                  .checkCertificate (aCert, null, ETriState.FALSE, ERevocationCheckMode.CRL);
      assertEquals (EPeppolCertificateCheckResult.VALID, e);

      LOGGER.info ("Checking with NONE");
      e = PeppolCertificateChecker.peppolTestSMP ()
                                  .checkCertificate (aCert, null, ETriState.FALSE, ERevocationCheckMode.NONE);
      assertEquals (EPeppolCertificateCheckResult.VALID, e);
    }
    else
      LOGGER.info ("Expected SMP keystore was not found");
  }
}
