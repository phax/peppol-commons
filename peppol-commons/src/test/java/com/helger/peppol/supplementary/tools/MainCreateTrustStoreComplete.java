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
package com.helger.peppol.supplementary.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.collection.impl.CommonsHashSet;
import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.peppol.security.PeppolTrustStores;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.KeyStoreHelper;
import com.helger.security.keystore.LoadedKeyStore;

public class MainCreateTrustStoreComplete
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainCreateTrustStoreComplete.class);

  public static void main (final String [] args) throws Exception
  {
    final KeyStore aSMPTrustStore = EKeyStoreType.JKS.getKeyStore ();
    // null stream means: create new key store
    aSMPTrustStore.load (null, null);

    final ICommonsSet <String> aAdded = new CommonsHashSet <> ();
    for (final String sTS : new String [] { "directory",
                                            "sml",
                                            "2018/eb2b-ap-pilot",
                                            "2018/pilot",
                                            "2018/prod",
                                            "2018/smp-pilot",
                                            "2018/smp-prod" })
    {
      final LoadedKeyStore aLKS = KeyStoreHelper.loadKeyStore (EKeyStoreType.JKS,
                                                               "truststore/" + sTS + "-truststore.jks",
                                                               PeppolTrustStores.TRUSTSTORE_PASSWORD.toCharArray ());
      KeyStoreHelper.iterateKeyStore (aLKS.getKeyStore (), sAlias -> {
        if (aAdded.add (sAlias))
        {
          // No key password
          try
          {
            LOGGER.info ("Adding '" + sAlias + "'");
            aSMPTrustStore.setEntry (sAlias, aLKS.getKeyStore ().getEntry (sAlias, null), null);
          }
          catch (final NoSuchAlgorithmException | UnrecoverableEntryException ex)
          {
            throw new IllegalStateException (ex);
          }
        }
      });
    }

    final File fDest = new File ("src/main/resources/truststore/complete-truststore.jks");
    try (final OutputStream aFOS = new FileOutputStream (fDest))
    {
      aSMPTrustStore.store (aFOS, PeppolTrustStores.TRUSTSTORE_PASSWORD.toCharArray ());
    }
    LOGGER.info ("Wrote " + fDest.getPath ());
  }
}
