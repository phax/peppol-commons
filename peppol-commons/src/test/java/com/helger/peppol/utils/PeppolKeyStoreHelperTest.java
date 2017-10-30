/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.KeyStore;
import java.security.Security;
import java.security.cert.X509Certificate;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.BeforeClass;
import org.junit.Test;

import com.helger.security.keystore.KeyStoreHelper;

/**
 * Test class for class {@link PeppolKeyStoreHelper}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class PeppolKeyStoreHelperTest
{
  @BeforeClass
  public static void init ()
  {
    Security.addProvider (new BouncyCastleProvider ());
  }

  @Test
  public void testLoadTrustStoreProduction () throws Exception
  {
    // Load trust store
    final KeyStore aTrustStore = KeyStoreHelper.loadKeyStoreDirect (PeppolKeyStoreHelper.TRUSTSTORE_TYPE,
                                                                    PeppolKeyStoreHelper.TRUSTSTORE_PRODUCTION_CLASSPATH,
                                                                    PeppolKeyStoreHelper.TRUSTSTORE_PASSWORD);
    assertNotNull (aTrustStore);

    // Ensure all name entries are contained
    assertNotNull (aTrustStore.getCertificate (PeppolKeyStoreHelper.TRUSTSTORE_PRODUCTION_ALIAS_ROOT));
    assertNotNull (aTrustStore.getCertificate (PeppolKeyStoreHelper.TRUSTSTORE_PRODUCTION_ALIAS_AP));
    assertNotNull (aTrustStore.getCertificate (PeppolKeyStoreHelper.TRUSTSTORE_PRODUCTION_ALIAS_SMP));

    final X509Certificate aCertAPOld = (X509Certificate) aTrustStore.getCertificate (PeppolKeyStoreHelper.TRUSTSTORE_PRODUCTION_ALIAS_AP);
    final String sIssuerName = aCertAPOld.getIssuerX500Principal ().getName ();
    assertEquals ("CN=PEPPOL Root CA,O=NATIONAL IT AND TELECOM AGENCY,C=DK", sIssuerName);
    final String sSubjectName = aCertAPOld.getSubjectX500Principal ().getName ();
    assertEquals ("CN=PEPPOL ACCESS POINT CA,O=NATIONAL IT AND TELECOM AGENCY,C=DK", sSubjectName);
  }

  @Test
  public void testLoadTrustStorePilot () throws Exception
  {
    // Load trust store
    final KeyStore aTrustStore = KeyStoreHelper.loadKeyStoreDirect (PeppolKeyStoreHelper.TRUSTSTORE_TYPE,
                                                                    PeppolKeyStoreHelper.TRUSTSTORE_PILOT_CLASSPATH,
                                                                    PeppolKeyStoreHelper.TRUSTSTORE_PASSWORD);
    assertNotNull (aTrustStore);

    // Ensure all name entries are contained
    assertNotNull (aTrustStore.getCertificate (PeppolKeyStoreHelper.TRUSTSTORE_PILOT_ALIAS_ROOT));
    assertNotNull (aTrustStore.getCertificate (PeppolKeyStoreHelper.TRUSTSTORE_PILOT_ALIAS_AP));
    assertNotNull (aTrustStore.getCertificate (PeppolKeyStoreHelper.TRUSTSTORE_PILOT_ALIAS_SMP));

    final X509Certificate aCertAPOld = (X509Certificate) aTrustStore.getCertificate (PeppolKeyStoreHelper.TRUSTSTORE_PILOT_ALIAS_AP);
    final String sIssuerName = aCertAPOld.getIssuerX500Principal ().getName ();
    assertEquals ("CN=PEPPOL Root TEST CA,OU=FOR TEST PURPOSES ONLY,O=NATIONAL IT AND TELECOM AGENCY,C=DK",
                  sIssuerName);
    final String sSubjectName = aCertAPOld.getSubjectX500Principal ().getName ();
    assertEquals ("CN=PEPPOL ACCESS POINT TEST CA,OU=FOR TEST PURPOSES ONLY,O=NATIONAL IT AND TELECOM AGENCY,C=DK",
                  sSubjectName);
  }
}
