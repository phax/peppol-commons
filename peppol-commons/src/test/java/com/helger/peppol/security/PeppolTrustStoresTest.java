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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.KeyStore;
import java.security.cert.X509Certificate;

import org.junit.Test;

import com.helger.peppol.security.PeppolTrustStores.Config2018;
import com.helger.peppol.security.PeppolTrustStores.Config2025;

/**
 * Test class for class {@link PeppolTrustStores}.
 *
 * @author Philip Helger
 */
@SuppressWarnings ("deprecation")
public final class PeppolTrustStoresTest
{
  @Test
  public void testConstants2018 ()
  {
    assertNotNull (Config2018.TRUSTSTORE_AP_PRODUCTION);
    assertNotNull (Config2018.TRUSTSTORE_SMP_PRODUCTION);

    assertNotNull (Config2018.TRUSTSTORE_AP_PILOT);
    assertNotNull (Config2018.TRUSTSTORE_SMP_PILOT);

    assertNotNull (Config2018.CERTIFICATE_PRODUCTION_ROOT);
    assertNotNull (Config2018.CERTIFICATE_PRODUCTION_AP);
    assertNotNull (Config2018.CERTIFICATE_PRODUCTION_SMP);

    assertNotNull (Config2018.CERTIFICATE_PILOT_ROOT);
    assertNotNull (Config2018.CERTIFICATE_PILOT_AP);
    assertNotNull (Config2018.CERTIFICATE_PILOT_SMP);
  }

  @Test
  public void testLoadTrustStore2018ProductionAP () throws Exception
  {
    // Load trust store
    final KeyStore aTrustStore = Config2018.TRUSTSTORE_AP_PRODUCTION;
    assertNotNull (aTrustStore);

    // Ensure all name entries are contained
    assertNotNull (aTrustStore.getCertificate (Config2018.TRUSTSTORE_PRODUCTION_ALIAS_ROOT));
    assertNotNull (aTrustStore.getCertificate (Config2018.TRUSTSTORE_PRODUCTION_ALIAS_AP));
    assertNotNull (aTrustStore.getCertificate (Config2018.TRUSTSTORE_PRODUCTION_ALIAS_SMP));

    {
      final X509Certificate aCertAP = Config2018.CERTIFICATE_PRODUCTION_AP;
      final String sIssuerName = aCertAP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root CA - G2,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertAP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL ACCESS POINT CA - G2,O=OpenPEPPOL AISBL,C=BE", sSubjectName);
      aCertAP.verify (Config2018.CERTIFICATE_PRODUCTION_ROOT.getPublicKey ());
    }
    {
      final X509Certificate aCertSMP = Config2018.CERTIFICATE_PRODUCTION_SMP;
      final String sIssuerName = aCertSMP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root CA - G2,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertSMP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL SERVICE METADATA PUBLISHER CA - G2,O=OpenPEPPOL AISBL,C=BE", sSubjectName);
      aCertSMP.verify (Config2018.CERTIFICATE_PRODUCTION_ROOT.getPublicKey ());
    }
  }

  @Test
  public void testLoadTrustStore2018ProductionSMP () throws Exception
  {
    // Load trust store
    final KeyStore aTrustStore = Config2018.TRUSTSTORE_SMP_PRODUCTION;
    assertNotNull (aTrustStore);

    // Ensure all name entries are contained
    assertNotNull (aTrustStore.getCertificate (Config2018.TRUSTSTORE_PRODUCTION_ALIAS_ROOT));
    assertNotNull (aTrustStore.getCertificate (Config2018.TRUSTSTORE_PRODUCTION_ALIAS_AP));
    assertNotNull (aTrustStore.getCertificate (Config2018.TRUSTSTORE_PRODUCTION_ALIAS_SMP));

    {
      final X509Certificate aCertAP = Config2018.CERTIFICATE_PRODUCTION_AP;
      final String sIssuerName = aCertAP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root CA - G2,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertAP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL ACCESS POINT CA - G2,O=OpenPEPPOL AISBL,C=BE", sSubjectName);
      aCertAP.verify (Config2018.CERTIFICATE_PRODUCTION_ROOT.getPublicKey ());
    }
    {
      final X509Certificate aCertSMP = Config2018.CERTIFICATE_PRODUCTION_SMP;
      final String sIssuerName = aCertSMP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root CA - G2,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertSMP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL SERVICE METADATA PUBLISHER CA - G2,O=OpenPEPPOL AISBL,C=BE", sSubjectName);
      aCertSMP.verify (Config2018.CERTIFICATE_PRODUCTION_ROOT.getPublicKey ());
    }
  }

  @Test
  public void testLoadTrustStore2018PilotAP () throws Exception
  {
    // Load trust store
    final KeyStore aTrustStore = Config2018.TRUSTSTORE_AP_PILOT;
    assertNotNull (aTrustStore);

    // Ensure all name entries are contained
    assertNotNull (aTrustStore.getCertificate (Config2018.TRUSTSTORE_PILOT_ALIAS_ROOT));
    assertNotNull (aTrustStore.getCertificate (Config2018.TRUSTSTORE_PILOT_ALIAS_AP));
    assertNotNull (aTrustStore.getCertificate (Config2018.TRUSTSTORE_PILOT_ALIAS_SMP));

    {
      final X509Certificate aCertAP = Config2018.CERTIFICATE_PILOT_AP;
      final String sIssuerName = aCertAP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root TEST CA - G2,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertAP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL ACCESS POINT TEST CA - G2,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE", sSubjectName);
      aCertAP.verify (Config2018.CERTIFICATE_PILOT_ROOT.getPublicKey ());
    }
    {
      final X509Certificate aCertSMP = Config2018.CERTIFICATE_PILOT_SMP;
      final String sIssuerName = aCertSMP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root TEST CA - G2,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertSMP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL SERVICE METADATA PUBLISHER TEST CA - G2,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE",
                    sSubjectName);
      aCertSMP.verify (Config2018.CERTIFICATE_PILOT_ROOT.getPublicKey ());
    }
  }

  @Test
  public void testLoadTrustStore2018PilotSMP () throws Exception
  {
    // Load trust store
    final KeyStore aTrustStore = Config2018.TRUSTSTORE_SMP_PILOT;
    assertNotNull (aTrustStore);

    // Ensure all name entries are contained
    assertNotNull (aTrustStore.getCertificate (Config2018.TRUSTSTORE_PILOT_ALIAS_ROOT));
    assertNotNull (aTrustStore.getCertificate (Config2018.TRUSTSTORE_PILOT_ALIAS_AP));
    assertNotNull (aTrustStore.getCertificate (Config2018.TRUSTSTORE_PILOT_ALIAS_SMP));

    {
      final X509Certificate aCertAP = Config2018.CERTIFICATE_PILOT_AP;
      final String sIssuerName = aCertAP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root TEST CA - G2,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertAP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL ACCESS POINT TEST CA - G2,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE", sSubjectName);
      aCertAP.verify (Config2018.CERTIFICATE_PILOT_ROOT.getPublicKey ());
    }
    {
      final X509Certificate aCertSMP = Config2018.CERTIFICATE_PILOT_SMP;
      final String sIssuerName = aCertSMP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root TEST CA - G2,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertSMP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL SERVICE METADATA PUBLISHER TEST CA - G2,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE",
                    sSubjectName);
      aCertSMP.verify (Config2018.CERTIFICATE_PILOT_ROOT.getPublicKey ());
    }
  }

  @Test
  public void testConstants2025 ()
  {
    assertNotNull (Config2025.TRUSTSTORE_AP_PRODUCTION);
    assertNotNull (Config2025.TRUSTSTORE_SMP_PRODUCTION);

    assertNotNull (Config2025.TRUSTSTORE_AP_TEST);
    assertNotNull (Config2025.TRUSTSTORE_SMP_TEST);

    assertNotNull (Config2025.CERTIFICATE_PRODUCTION_ROOT);
    assertNotNull (Config2025.CERTIFICATE_PRODUCTION_AP);
    assertNotNull (Config2025.CERTIFICATE_PRODUCTION_SMP);

    assertNotNull (Config2025.CERTIFICATE_TEST_ROOT);
    assertNotNull (Config2025.CERTIFICATE_TEST_AP);
    assertNotNull (Config2025.CERTIFICATE_TEST_SMP);
  }

  @Test
  public void testLoadTrustStore2025ProductionAP () throws Exception
  {
    // Load trust store
    final KeyStore aTrustStore = Config2025.TRUSTSTORE_AP_PRODUCTION;
    assertNotNull (aTrustStore);

    // Ensure all name entries are contained
    assertNotNull (aTrustStore.getCertificate (Config2025.TRUSTSTORE_PRODUCTION_ALIAS_ROOT));
    assertNotNull (aTrustStore.getCertificate (Config2025.TRUSTSTORE_PRODUCTION_ALIAS_AP));
    assertNotNull (aTrustStore.getCertificate (Config2025.TRUSTSTORE_PRODUCTION_ALIAS_SMP));

    {
      final X509Certificate aCertAP = Config2025.CERTIFICATE_PRODUCTION_AP;
      final String sIssuerName = aCertAP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root CA - G3,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertAP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL ACCESS POINT CA - G3,O=OpenPEPPOL AISBL,C=BE", sSubjectName);
      aCertAP.verify (Config2025.CERTIFICATE_PRODUCTION_ROOT.getPublicKey ());
    }
    {
      final X509Certificate aCertSMP = Config2025.CERTIFICATE_PRODUCTION_SMP;
      final String sIssuerName = aCertSMP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root CA - G3,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertSMP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL SERVICE METADATA PUBLISHER CA - G3,O=OpenPEPPOL AISBL,C=BE", sSubjectName);
      aCertSMP.verify (Config2025.CERTIFICATE_PRODUCTION_ROOT.getPublicKey ());
    }
  }

  @Test
  public void testLoadTrustStore2025ProductionSMP () throws Exception
  {
    // Load trust store
    final KeyStore aTrustStore = Config2025.TRUSTSTORE_SMP_PRODUCTION;
    assertNotNull (aTrustStore);

    // Ensure all name entries are contained
    assertNotNull (aTrustStore.getCertificate (Config2025.TRUSTSTORE_PRODUCTION_ALIAS_ROOT));
    assertNotNull (aTrustStore.getCertificate (Config2025.TRUSTSTORE_PRODUCTION_ALIAS_AP));
    assertNotNull (aTrustStore.getCertificate (Config2025.TRUSTSTORE_PRODUCTION_ALIAS_SMP));

    {
      final X509Certificate aCertAP = Config2025.CERTIFICATE_PRODUCTION_AP;
      final String sIssuerName = aCertAP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root CA - G3,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertAP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL ACCESS POINT CA - G3,O=OpenPEPPOL AISBL,C=BE", sSubjectName);
      aCertAP.verify (Config2025.CERTIFICATE_PRODUCTION_ROOT.getPublicKey ());
    }
    {
      final X509Certificate aCertSMP = Config2025.CERTIFICATE_PRODUCTION_SMP;
      final String sIssuerName = aCertSMP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root CA - G3,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertSMP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL SERVICE METADATA PUBLISHER CA - G3,O=OpenPEPPOL AISBL,C=BE", sSubjectName);
      aCertSMP.verify (Config2025.CERTIFICATE_PRODUCTION_ROOT.getPublicKey ());
    }
  }

  @Test
  public void testLoadTrustStore2025PilotAP () throws Exception
  {
    // Load trust store
    final KeyStore aTrustStore = Config2025.TRUSTSTORE_AP_TEST;
    assertNotNull (aTrustStore);

    // Ensure all name entries are contained
    assertNotNull (aTrustStore.getCertificate (Config2025.TRUSTSTORE_TEST_ALIAS_ROOT));
    assertNotNull (aTrustStore.getCertificate (Config2025.TRUSTSTORE_TEST_ALIAS_AP));
    assertNotNull (aTrustStore.getCertificate (Config2025.TRUSTSTORE_TEST_ALIAS_SMP));

    {
      final X509Certificate aCertAP = Config2025.CERTIFICATE_TEST_AP;
      final String sIssuerName = aCertAP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root TEST CA - G3,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertAP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL ACCESS POINT TEST CA - G3,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE", sSubjectName);
      aCertAP.verify (Config2025.CERTIFICATE_TEST_ROOT.getPublicKey ());
    }
    {
      final X509Certificate aCertSMP = Config2025.CERTIFICATE_TEST_SMP;
      final String sIssuerName = aCertSMP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root TEST CA - G3,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertSMP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL SERVICE METADATA PUBLISHER TEST CA - G3,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE",
                    sSubjectName);
      aCertSMP.verify (Config2025.CERTIFICATE_TEST_ROOT.getPublicKey ());
    }
  }

  @Test
  public void testLoadTrustStore2025PilotSMP () throws Exception
  {
    // Load trust store
    final KeyStore aTrustStore = Config2025.TRUSTSTORE_SMP_TEST;
    assertNotNull (aTrustStore);

    // Ensure all name entries are contained
    assertNotNull (aTrustStore.getCertificate (Config2025.TRUSTSTORE_TEST_ALIAS_ROOT));
    assertNotNull (aTrustStore.getCertificate (Config2025.TRUSTSTORE_TEST_ALIAS_AP));
    assertNotNull (aTrustStore.getCertificate (Config2025.TRUSTSTORE_TEST_ALIAS_SMP));

    {
      final X509Certificate aCertAP = Config2025.CERTIFICATE_TEST_AP;
      final String sIssuerName = aCertAP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root TEST CA - G3,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertAP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL ACCESS POINT TEST CA - G3,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE", sSubjectName);
      aCertAP.verify (Config2025.CERTIFICATE_TEST_ROOT.getPublicKey ());
    }
    {
      final X509Certificate aCertSMP = Config2025.CERTIFICATE_TEST_SMP;
      final String sIssuerName = aCertSMP.getIssuerX500Principal ().getName ();
      assertEquals ("CN=PEPPOL Root TEST CA - G3,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE", sIssuerName);
      final String sSubjectName = aCertSMP.getSubjectX500Principal ().getName ();
      assertEquals ("CN=PEPPOL SERVICE METADATA PUBLISHER TEST CA - G3,OU=FOR TEST ONLY,O=OpenPEPPOL AISBL,C=BE",
                    sSubjectName);
      aCertSMP.verify (Config2025.CERTIFICATE_TEST_ROOT.getPublicKey ());
    }
  }
}
