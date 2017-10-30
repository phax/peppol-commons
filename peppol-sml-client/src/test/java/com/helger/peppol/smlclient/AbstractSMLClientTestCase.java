/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Version: MPL 2.0/EUPL 1.2
 * -
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 * -
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.2 or - as soon they will be approved
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
 * -
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
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
  protected static final String KEYSTORE_PATH = MockSMLClientConfig.getKeystorePath ();
  protected static final String KEYSTORE_PASSWORD = MockSMLClientConfig.getKeystorePassword ();

  @Nullable
  public static final SSLSocketFactory createConfiguredSSLSocketFactory (@Nonnull final ISMLInfo aSMLInfo) throws Exception
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
                  new TrustManager [] { new TrustManagerTrustAll (true) },
                  RandomHelper.getSecureRandom ());
    return aSSLCtx.getSocketFactory ();
  }
}
