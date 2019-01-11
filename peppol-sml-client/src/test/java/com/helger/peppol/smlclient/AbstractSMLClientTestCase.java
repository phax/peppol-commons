/**
 * Copyright (C) 2015-2019 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.smlclient;

import java.security.KeyStore;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.helger.commons.random.RandomHelper;
import com.helger.commons.ws.TrustManagerTrustAll;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.KeyStoreHelper;

/**
 * Base class for SML client tests, with some utility content.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
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
    aSSLCtx.init (aKeyManagerFactory.getKeyManagers (),
                  new TrustManager [] { new TrustManagerTrustAll (bDebug) },
                  RandomHelper.getSecureRandom ());
    return aSSLCtx.getSocketFactory ();
  }
}
