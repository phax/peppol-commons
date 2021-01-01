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
package com.helger.peppol.smlclient.client;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.io.stream.StreamHelper;
import com.helger.commons.ws.HostnameVerifierVerifyAll;
import com.helger.commons.ws.TrustManagerTrustAll;
import com.helger.peppol.smlclient.AbstractSMLClientTestCase;
import com.helger.security.keystore.KeyStoreHelper;

/**
 * This class tests the URL connection to the SML that is secured with client
 * certificates. It requires a valid keystore to be configured.
 *
 * @author Philip Helger
 */
@Ignore ("Requires a keystore to be present and configured")
public final class SSLConnectFuncTest extends AbstractSMLClientTestCase
{
  private static final Logger LOGGER = LoggerFactory.getLogger (SSLConnectFuncTest.class);

  @Test
  public void testConnect () throws Exception
  {
    // Load the client certificate
    final KeyStore aKeyStore = KeyStoreHelper.loadKeyStoreDirect (KEYSTORE_TYPE, KEYSTORE_PATH, KEYSTORE_PASSWORD);
    final KeyManagerFactory aKMF = KeyManagerFactory.getInstance ("SunX509");
    aKMF.init (aKeyStore, KEYSTORE_PASSWORD.toCharArray ());

    // Trust all
    final TrustManager [] aTrustMgrs = new TrustManager [] { new TrustManagerTrustAll (false) };

    // SSL context
    final SSLContext aSSLContext = SSLContext.getInstance ("TLS");
    aSSLContext.init (aKMF.getKeyManagers (), aTrustMgrs, null);

    // Configure and open connection
    final HttpsURLConnection aURLConn = (HttpsURLConnection) new URL (SML_INFO.getManagementServiceURL ()).openConnection ();
    aURLConn.setSSLSocketFactory (aSSLContext.getSocketFactory ());
    aURLConn.setHostnameVerifier (new HostnameVerifierVerifyAll (true));
    aURLConn.setRequestMethod ("GET");

    // Debug status on URL connection
    if (true)
    {
      LOGGER.info ("Status code:  " + aURLConn.getResponseCode ());
      LOGGER.info ("Cipher suite: " + aURLConn.getCipherSuite ());
      LOGGER.info ("Encoding:     " + aURLConn.getContentEncoding ());
      if (true)
      {
        int i = 0;
        for (final Certificate aCert : aURLConn.getServerCertificates ())
        {
          LOGGER.info (" Cert " + (++i) + ":");
          LOGGER.info ("  Cert type:  " + aCert.getType ());
          LOGGER.info ("  Hash code:  " + aCert.hashCode ());
          LOGGER.info ("  Algorithm:  " + aCert.getPublicKey ().getAlgorithm ());
          LOGGER.info ("  Format:     " + aCert.getPublicKey ().getFormat ());
          if (aCert instanceof X509Certificate)
          {
            final X509Certificate aX509 = (X509Certificate) aCert;
            LOGGER.info ("   Principal: " + aX509.getIssuerX500Principal ());
            LOGGER.info ("   Subject:   " + aX509.getSubjectX500Principal ());
          }
        }
      }
    }

    try
    {
      // Show success
      final String sResult = StreamHelper.getAllBytesAsString (aURLConn.getInputStream (), StandardCharsets.UTF_8);
      LOGGER.info ("\n" + sResult);
    }
    catch (final IOException ex)
    {
      // Show error
      final String sError = StreamHelper.getAllBytesAsString (aURLConn.getErrorStream (), StandardCharsets.UTF_8);
      LOGGER.info ("\n" + sError);
    }
  }
}
