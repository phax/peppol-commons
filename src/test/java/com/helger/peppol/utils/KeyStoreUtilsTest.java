/**
 * Copyright (C) 2015 Philip Helger (www.helger.com)
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
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V1CertificateGenerator;
import org.junit.BeforeClass;
import org.junit.Test;

import com.helger.commons.collections.CollectionHelper;
import com.helger.peppol.utils.KeyStoreUtils;

/**
 * Test class for class {@link KeyStoreUtils}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@SuppressWarnings ("deprecation")
public final class KeyStoreUtilsTest
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
    final X509V1CertificateGenerator certGen = new X509V1CertificateGenerator ();
    certGen.setSerialNumber (BigInteger.valueOf (System.currentTimeMillis ()));
    certGen.setIssuerDN (new X500Principal ("CN=Test Certificate"));
    certGen.setNotBefore (new Date (System.currentTimeMillis () - 50000));
    certGen.setNotAfter (new Date (System.currentTimeMillis () + 50000));
    certGen.setSubjectDN (new X500Principal ("CN=Test Certificate"));
    certGen.setPublicKey (aKeyPair.getPublic ());
    certGen.setSignatureAlgorithm ("SHA256WithRSAEncryption");
    return certGen.generate (aKeyPair.getPrivate (), "BC");
  }

  @Test
  public void testAll () throws Exception
  {
    final KeyPair aKeyPair = createKeyPair (1024);
    final Certificate [] certs = { createX509V1Certificate (aKeyPair), createX509V1Certificate (aKeyPair) };

    KeyStore ks = KeyStoreUtils.loadKeyStore ("keystores/keystore-no-pw.jks", (String) null);
    assertEquals (KeyStoreUtils.KEYSTORE_TYPE_JKS, ks.getType ());
    assertEquals (1, CollectionHelper.newList (ks.aliases ()).size ());
    assertTrue (ks.containsAlias ("1"));
    final Certificate c1 = ks.getCertificate ("1");
    assertNotNull (c1);
    ks.setKeyEntry ("2", aKeyPair.getPrivate (), "key2".toCharArray (), certs);

    ks = KeyStoreUtils.loadKeyStore ("keystores/keystore-pw-peppol.jks", (String) null);
    assertEquals (1, CollectionHelper.newList (ks.aliases ()).size ());
    assertTrue (ks.containsAlias ("1"));
    final Certificate c2 = ks.getCertificate ("1");
    assertNotNull (c2);
    assertEquals (c1, c2);
    ks.setKeyEntry ("2", aKeyPair.getPrivate (), "key2".toCharArray (), certs);

    ks = KeyStoreUtils.loadKeyStore ("keystores/keystore-pw-peppol.jks", "peppol");
    assertEquals (1, CollectionHelper.newList (ks.aliases ()).size ());
    assertTrue (ks.containsAlias ("1"));
    final Certificate c3 = ks.getCertificate ("1");
    assertNotNull (c3);
    assertEquals (c2, c3);
    ks.setKeyEntry ("2", aKeyPair.getPrivate (), "key2".toCharArray (), certs);

    try
    {
      // Non-existing file
      KeyStoreUtils.loadKeyStore ("keystores/keystore-not-existing.jks", (String) null);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Invalid password
      KeyStoreUtils.loadKeyStore ("keystores/keystore-pw-peppol.jks", "wrongpw");
      fail ();
    }
    catch (final IOException ex)
    {}
  }

  @Test
  public void testLoadTrustStore () throws Exception
  {
    // Load trust store
    final KeyStore aTrustStore = KeyStoreUtils.loadKeyStore (KeyStoreUtils.TRUSTSTORE_CLASSPATH,
                                                             KeyStoreUtils.TRUSTSTORE_PASSWORD);
    assertNotNull (aTrustStore);

    // Ensure all name entries are contained
    assertNotNull (aTrustStore.getCertificate (KeyStoreUtils.TRUSTSTORE_ALIAS_AP_OPENPEPPOL));
    assertNotNull (aTrustStore.getCertificate (KeyStoreUtils.TRUSTSTORE_ALIAS_SMP_OPENPEPPOL));

    // System.out.println (SystemProperties.getJavaVersion ());
    final X509Certificate aCertAPOld = (X509Certificate) aTrustStore.getCertificate (KeyStoreUtils.TRUSTSTORE_ALIAS_AP_OPENPEPPOL);
    final String sIssuerName = aCertAPOld.getIssuerX500Principal ().getName ();
    assertEquals ("CN=PEPPOL Root CA,O=NATIONAL IT AND TELECOM AGENCY,C=DK", sIssuerName);
    final String sSubjectName = aCertAPOld.getSubjectX500Principal ().getName ();
    assertEquals ("CN=PEPPOL ACCESS POINT CA,O=NATIONAL IT AND TELECOM AGENCY,C=DK", sSubjectName);
  }
}
