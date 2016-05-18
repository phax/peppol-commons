/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v1CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import com.helger.commons.collection.CollectionHelper;

/**
 * Test class for class {@link KeyStoreHelper}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class KeyStoreHelperTest
{
  @BeforeClass
  public static void init ()
  {
    Security.addProvider (new BouncyCastleProvider ());
  }

  public KeyPair createKeyPair (final int nKeySizeInBits) throws Exception
  {
    final KeyPairGenerator aGenerator = KeyPairGenerator.getInstance ("RSA");
    aGenerator.initialize (nKeySizeInBits);
    final KeyPair keyPair = aGenerator.generateKeyPair ();
    return keyPair;
  }

  public static X509Certificate createX509V1Certificate (final KeyPair aKeyPair) throws Exception
  {
    // generate the certificate
    final PublicKey aPublicKey = aKeyPair.getPublic ();
    final PrivateKey aPrivateKey = aKeyPair.getPrivate ();
    final ContentSigner aContentSigner = new JcaContentSignerBuilder ("SHA1withRSA").setProvider (BouncyCastleProvider.PROVIDER_NAME)
                                                                                    .build (aPrivateKey);

    final X509CertificateHolder aCertHolder = new JcaX509v1CertificateBuilder (new X500Principal ("CN=Test Certificate"),
                                                                               BigInteger.valueOf (System.currentTimeMillis ()),
                                                                               new Date (System.currentTimeMillis () -
                                                                                         50000),
                                                                               new Date (System.currentTimeMillis () +
                                                                                         50000),
                                                                               new X500Principal ("CN=Test Certificate"),
                                                                               aPublicKey).build (aContentSigner);
    // Convert to JCA X509Certificate
    return new JcaX509CertificateConverter ().getCertificate (aCertHolder);
  }

  @Test
  public void testAll () throws Exception
  {
    final KeyPair aKeyPair = createKeyPair (1024);
    final Certificate [] certs = { createX509V1Certificate (aKeyPair), createX509V1Certificate (aKeyPair) };

    KeyStore ks = KeyStoreHelper.loadKeyStore ("keystores/keystore-no-pw.jks", (String) null);
    assertEquals (KeyStoreHelper.KEYSTORE_TYPE_JKS, ks.getType ());
    assertEquals (1, CollectionHelper.newList (ks.aliases ()).size ());
    assertTrue (ks.containsAlias ("1"));
    final Certificate c1 = ks.getCertificate ("1");
    assertNotNull (c1);
    ks.setKeyEntry ("2", aKeyPair.getPrivate (), "key2".toCharArray (), certs);

    ks = KeyStoreHelper.loadKeyStore ("keystores/keystore-pw-peppol.jks", (String) null);
    assertEquals (1, CollectionHelper.newList (ks.aliases ()).size ());
    assertTrue (ks.containsAlias ("1"));
    final Certificate c2 = ks.getCertificate ("1");
    assertNotNull (c2);
    assertEquals (c1, c2);
    ks.setKeyEntry ("2", aKeyPair.getPrivate (), "key2".toCharArray (), certs);

    ks = KeyStoreHelper.loadKeyStore ("keystores/keystore-pw-peppol.jks", "peppol");
    assertEquals (1, CollectionHelper.newList (ks.aliases ()).size ());
    assertTrue (ks.containsAlias ("1"));
    final Certificate c3 = ks.getCertificate ("1");
    assertNotNull (c3);
    assertEquals (c2, c3);
    ks.setKeyEntry ("2", aKeyPair.getPrivate (), "key2".toCharArray (), certs);

    try
    {
      // Non-existing file
      KeyStoreHelper.loadKeyStore ("keystores/keystore-not-existing.jks", (String) null);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Invalid password
      KeyStoreHelper.loadKeyStore ("keystores/keystore-pw-peppol.jks", "wrongpw");
      fail ();
    }
    catch (final IOException ex)
    {}
  }

  @Test
  public void testLoadTrustStoreProduction () throws Exception
  {
    // Load trust store
    final KeyStore aTrustStore = KeyStoreHelper.loadKeyStore (KeyStoreHelper.TRUSTSTORE_PRODUCTION_CLASSPATH,
                                                              KeyStoreHelper.TRUSTSTORE_PASSWORD);
    assertNotNull (aTrustStore);

    // Ensure all name entries are contained
    assertNotNull (aTrustStore.getCertificate (KeyStoreHelper.TRUSTSTORE_PRODUCTION_ALIAS_ROOT));
    assertNotNull (aTrustStore.getCertificate (KeyStoreHelper.TRUSTSTORE_PRODUCTION_ALIAS_AP));
    assertNotNull (aTrustStore.getCertificate (KeyStoreHelper.TRUSTSTORE_PRODUCTION_ALIAS_SMP));

    // System.out.println (SystemProperties.getJavaVersion ());
    final X509Certificate aCertAPOld = (X509Certificate) aTrustStore.getCertificate (KeyStoreHelper.TRUSTSTORE_PRODUCTION_ALIAS_AP);
    final String sIssuerName = aCertAPOld.getIssuerX500Principal ().getName ();
    assertEquals ("CN=PEPPOL Root CA,O=NATIONAL IT AND TELECOM AGENCY,C=DK", sIssuerName);
    final String sSubjectName = aCertAPOld.getSubjectX500Principal ().getName ();
    assertEquals ("CN=PEPPOL ACCESS POINT CA,O=NATIONAL IT AND TELECOM AGENCY,C=DK", sSubjectName);
  }

  @Test
  public void testLoadTrustStorePilot () throws Exception
  {
    // Load trust store
    final KeyStore aTrustStore = KeyStoreHelper.loadKeyStore (KeyStoreHelper.TRUSTSTORE_PILOT_CLASSPATH,
                                                              KeyStoreHelper.TRUSTSTORE_PASSWORD);
    assertNotNull (aTrustStore);

    // Ensure all name entries are contained
    assertNotNull (aTrustStore.getCertificate (KeyStoreHelper.TRUSTSTORE_PILOT_ALIAS_ROOT));
    assertNotNull (aTrustStore.getCertificate (KeyStoreHelper.TRUSTSTORE_PILOT_ALIAS_AP));
    assertNotNull (aTrustStore.getCertificate (KeyStoreHelper.TRUSTSTORE_PILOT_ALIAS_SMP));

    // System.out.println (SystemProperties.getJavaVersion ());
    final X509Certificate aCertAPOld = (X509Certificate) aTrustStore.getCertificate (KeyStoreHelper.TRUSTSTORE_PILOT_ALIAS_AP);
    final String sIssuerName = aCertAPOld.getIssuerX500Principal ().getName ();
    assertEquals ("CN=PEPPOL Root TEST CA,OU=FOR TEST PURPOSES ONLY,O=NATIONAL IT AND TELECOM AGENCY,C=DK",
                  sIssuerName);
    final String sSubjectName = aCertAPOld.getSubjectX500Principal ().getName ();
    assertEquals ("CN=PEPPOL ACCESS POINT TEST CA,OU=FOR TEST PURPOSES ONLY,O=NATIONAL IT AND TELECOM AGENCY,C=DK",
                  sSubjectName);
  }
}
