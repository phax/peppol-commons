/*
 * Copyright (C) 2015-2024 Philip Helger
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
package com.helger.peppol.smlclient;

import java.security.KeyStore;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.helger.commons.ws.TrustManagerTrustAll;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.KeyStoreHelper;

/**
 * Base class for SML client tests, with some utility content.
 *
 * @author Philip Helger
 */
public abstract class AbstractSMLClientTestCase
{
  public static final ISMLInfo SML_INFO = ESML.DIGIT_TEST;
  protected static final EKeyStoreType KEYSTORE_TYPE = EKeyStoreType.JKS;
  protected static final String KEYSTORE_PATH = MockSMLClientConfig.getKeyStorePath ();
  protected static final String KEYSTORE_PASSWORD = MockSMLClientConfig.getKeyStorePassword ();

  @Nullable
  public static final SSLSocketFactory createConfiguredSSLSocketFactory (@Nonnull final ISMLInfo aSMLInfo,
                                                                         final boolean bDebug) throws Exception
  {
    if (!aSMLInfo.isClientCertificateRequired ())
      return null;

    // Main key storage
    final KeyStore aKeyStore = KeyStoreHelper.loadKeyStoreDirect (KEYSTORE_TYPE, KEYSTORE_PATH, KEYSTORE_PASSWORD);

    // Key manager
    final KeyManagerFactory aKeyManagerFactory = KeyManagerFactory.getInstance ("SunX509");
    aKeyManagerFactory.init (aKeyStore, KEYSTORE_PASSWORD.toCharArray ());

    // Assign key manager and empty trust manager to SSL context
    final SSLContext aSSLCtx = SSLContext.getInstance ("TLS");
    aSSLCtx.init (aKeyManagerFactory.getKeyManagers (), new TrustManager [] { new TrustManagerTrustAll (bDebug) }, null);
    return aSSLCtx.getSocketFactory ();
  }
}
