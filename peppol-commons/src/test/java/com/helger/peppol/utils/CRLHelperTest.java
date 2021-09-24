/**
 * Copyright (C) 2015-2021 Philip Helger
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
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;

import org.junit.Test;

import com.helger.commons.collection.impl.ICommonsList;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.KeyStoreHelper;

/**
 * Test class for class {@link CRLHelper}.
 *
 * @author Philip Helger
 */
public final class CRLHelperTest
{
  @Test
  public void testGetAllDistributionPoints () throws KeyStoreException
  {
    // As keystores are usually not in the repository, this test is no-op if the
    // file is not present
    final File fAP = new File ("src/test/resources/test-ap-2021.p12");
    if (fAP.exists ())
    {
      final KeyStore aKS = KeyStoreHelper.loadKeyStore (EKeyStoreType.PKCS12, fAP.getAbsolutePath (), "peppol").getKeyStore ();
      assertNotNull (aKS);

      final X509Certificate aCert = (X509Certificate) aKS.getCertificate (aKS.aliases ().nextElement ());
      assertNotNull (aCert);

      final ICommonsList <String> aList = CRLHelper.getAllDistributionPoints (aCert);
      assertNotNull (aList);
      assertEquals (1, aList.size ());
      assertEquals ("http://pki-crl.symauth.com/ca_6a937734a393a0805bf33cda8b331093/LatestCRL.crl", aList.get (0));
    }
  }
}
