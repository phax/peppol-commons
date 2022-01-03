/*
 * Copyright (C) 2015-2022 Philip Helger
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

import java.security.KeyStore;
import java.security.cert.X509Certificate;

import org.junit.Test;

import com.helger.security.keystore.KeyStoreHelper;

/**
 * Test class for class {@link PeppolKeyStoreHelper}.
 *
 * @author Philip Helger
 */
public final class PeppolKeyStoreHelperTest
{
  @Test
  public void testConstants ()
  {
    assertNotNull (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_ROOT);
    assertNotNull (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_AP);
    assertNotNull (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_SMP);

    assertNotNull (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_ROOT);
    assertNotNull (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP);
    assertNotNull (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_SMP);
  }

  @Test
  public void testLoadTrustStore2018ProductionAP () throws Exception
  {
    // Load trust store
    final KeyStore aTrustStore = KeyStoreHelper.loadKeyStoreDirect (PeppolKeyStoreHelper.TRUSTSTORE_TYPE,
                                                                    PeppolKeyStoreHelper.Config2018.TRUSTSTORE_PRODUCTION_CLASSPATH,
                                                                    PeppolKeyStoreHelper.TRUSTSTORE_PASSWORD);
    assertNotNull (aTrustStore);

    // Ensure all name entries are contained
    assertNotNull (aTrustStore.getCertificate (PeppolKeyStoreHelper.Config2018.TRUSTSTORE_PRODUCTION_ALIAS_ROOT));
    assertNotNull (aTrustStore.getCertificate (PeppolKeyStoreHelper.Config2018.TRUSTSTORE_PRODUCTION_ALIAS_AP));
    assertNotNull (aTrustStore.getCertificate (PeppolKeyStoreHelper.Config2018.TRUSTSTORE_PRODUCTION_ALIAS_SMP));

    {
      final X509Certificate aCertAP = PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_AP;
      final String sIssuerName = aCertAP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root CA - G2,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertAP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL ACCESS POINT CA - G2,O=OpenPEPPOL AISBL,C=BE", sSubjectName);
      aCertAP.verify (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_ROOT.getPublicKey ());
    }
    {
      final X509Certificate aCertSMP = PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_SMP;
      final String sIssuerName = aCertSMP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root CA - G2,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertSMP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL SERVICE METADATA PUBLISHER CA - G2,O=OpenPEPPOL AISBL,C=BE", sSubjectName);
      aCertSMP.verify (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_ROOT.getPublicKey ());
    }
  }

  @Test
  public void testLoadTrustStore2018ProductionSMP () throws Exception
  {
    // Load trust store
    final KeyStore aTrustStore = KeyStoreHelper.loadKeyStoreDirect (PeppolKeyStoreHelper.TRUSTSTORE_TYPE,
                                                                    PeppolKeyStoreHelper.Config2018.TRUSTSTORE_SMP_PRODUCTION_CLASSPATH,
                                                                    PeppolKeyStoreHelper.TRUSTSTORE_PASSWORD);
    assertNotNull (aTrustStore);

    // Ensure all name entries are contained
    assertNotNull (aTrustStore.getCertificate (PeppolKeyStoreHelper.Config2018.TRUSTSTORE_PRODUCTION_ALIAS_ROOT));
    assertNotNull (aTrustStore.getCertificate (PeppolKeyStoreHelper.Config2018.TRUSTSTORE_PRODUCTION_ALIAS_AP));
    assertNotNull (aTrustStore.getCertificate (PeppolKeyStoreHelper.Config2018.TRUSTSTORE_PRODUCTION_ALIAS_SMP));

    {
      final X509Certificate aCertAP = PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_AP;
      final String sIssuerName = aCertAP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root CA - G2,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertAP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL ACCESS POINT CA - G2,O=OpenPEPPOL AISBL,C=BE", sSubjectName);
      aCertAP.verify (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_ROOT.getPublicKey ());
    }
    {
      final X509Certificate aCertSMP = PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_SMP;
      final String sIssuerName = aCertSMP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root CA - G2,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertSMP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL SERVICE METADATA PUBLISHER CA - G2,O=OpenPEPPOL AISBL,C=BE", sSubjectName);
      aCertSMP.verify (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PRODUCTION_ROOT.getPublicKey ());
    }
  }

  @Test
  public void testLoadTrustStore2018PilotAP () throws Exception
  {
    // Load trust store
    final KeyStore aTrustStore = KeyStoreHelper.loadKeyStoreDirect (PeppolKeyStoreHelper.TRUSTSTORE_TYPE,
                                                                    PeppolKeyStoreHelper.Config2018.TRUSTSTORE_PILOT_CLASSPATH,
                                                                    PeppolKeyStoreHelper.TRUSTSTORE_PASSWORD);
    assertNotNull (aTrustStore);

    // Ensure all name entries are contained
    assertNotNull (aTrustStore.getCertificate (PeppolKeyStoreHelper.Config2018.TRUSTSTORE_PILOT_ALIAS_ROOT));
    assertNotNull (aTrustStore.getCertificate (PeppolKeyStoreHelper.Config2018.TRUSTSTORE_PILOT_ALIAS_AP));
    assertNotNull (aTrustStore.getCertificate (PeppolKeyStoreHelper.Config2018.TRUSTSTORE_PILOT_ALIAS_SMP));

    {
      final X509Certificate aCertAP = PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP;
      final String sIssuerName = aCertAP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root TEST CA - G2,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertAP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL ACCESS POINT TEST CA - G2,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE", sSubjectName);
      aCertAP.verify (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_ROOT.getPublicKey ());
    }
    {
      final X509Certificate aCertSMP = PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_SMP;
      final String sIssuerName = aCertSMP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root TEST CA - G2,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertSMP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL SERVICE METADATA PUBLISHER TEST CA - G2,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE", sSubjectName);
      aCertSMP.verify (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_ROOT.getPublicKey ());
    }
  }

  @Test
  public void testLoadTrustStore2018PilotSMP () throws Exception
  {
    // Load trust store
    final KeyStore aTrustStore = KeyStoreHelper.loadKeyStoreDirect (PeppolKeyStoreHelper.TRUSTSTORE_TYPE,
                                                                    PeppolKeyStoreHelper.Config2018.TRUSTSTORE_SMP_PILOT_CLASSPATH,
                                                                    PeppolKeyStoreHelper.TRUSTSTORE_PASSWORD);
    assertNotNull (aTrustStore);

    // Ensure all name entries are contained
    assertNotNull (aTrustStore.getCertificate (PeppolKeyStoreHelper.Config2018.TRUSTSTORE_PILOT_ALIAS_ROOT));
    assertNotNull (aTrustStore.getCertificate (PeppolKeyStoreHelper.Config2018.TRUSTSTORE_PILOT_ALIAS_AP));
    assertNotNull (aTrustStore.getCertificate (PeppolKeyStoreHelper.Config2018.TRUSTSTORE_PILOT_ALIAS_SMP));

    {
      final X509Certificate aCertAP = PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP;
      final String sIssuerName = aCertAP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root TEST CA - G2,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertAP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL ACCESS POINT TEST CA - G2,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE", sSubjectName);
      aCertAP.verify (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_ROOT.getPublicKey ());
    }
    {
      final X509Certificate aCertSMP = PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_SMP;
      final String sIssuerName = aCertSMP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root TEST CA - G2,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertSMP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL SERVICE METADATA PUBLISHER TEST CA - G2,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE", sSubjectName);
      aCertSMP.verify (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_ROOT.getPublicKey ());
    }
  }
}
