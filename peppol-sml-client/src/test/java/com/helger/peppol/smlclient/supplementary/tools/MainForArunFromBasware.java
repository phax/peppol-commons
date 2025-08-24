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
package com.helger.peppol.smlclient.supplementary.tools;

import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.base.string.StringHelper;
import com.helger.base.system.SystemProperties;
import com.helger.http.security.TrustManagerTrustAll;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smlclient.ManageParticipantIdentifierServiceCaller;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.KeyStoreHelper;
import com.helger.wsclient.WSHelper;

/**
 * Special tool for Arun :)
 *
 * @author Philip Helger
 */
public final class MainForArunFromBasware
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainForArunFromBasware.class);

  public static void main (final String [] args) throws Exception
  {
    // START MODIFY BELOW

    // Your SMP ID
    final String SMP_ID = "TEST-SMP";
    // Use SMK or SML?
    final ISMLInfo aSMLInfo = ESML.DIGIT_TEST;
    // Keystore path and password
    final EKeyStoreType eKeyStoreType = EKeyStoreType.JKS;
    final String sKeystorePath = "keystore/smp.pilot.jks";
    final char [] aKeystorePassword = "peppol".toCharArray ();
    // Participant to be created
    final String sServiceGroupID = "0088:5798000000001";
    // Create (true) or delete (false) participant?
    final boolean bCreate = false;
    // Proxy server settings
    final String sProxyHostname = null;
    final int nProxyPort = 0;
    WSHelper.setMetroDebugSystemProperties (true);

    // STOP MODIFY ABOVE
    // Don't change anything below this line

    // Set proxy as system properties
    if (nProxyPort > 0 && StringHelper.isNotEmpty (sProxyHostname))
    {
      SystemProperties.setPropertyValue ("http.proxyHost", sProxyHostname);
      SystemProperties.setPropertyValue ("http.proxyPort", nProxyPort);
      SystemProperties.setPropertyValue ("https.proxyHost", sProxyHostname);
      SystemProperties.setPropertyValue ("https.proxyPort", nProxyPort);
    }

    final ManageParticipantIdentifierServiceCaller aParticipantClient = new ManageParticipantIdentifierServiceCaller (aSMLInfo);
    if (aSMLInfo.isClientCertificateRequired ())
    {
      // Main key storage
      final KeyStore aKeyStore = KeyStoreHelper.loadKeyStoreDirect (eKeyStoreType, sKeystorePath, aKeystorePassword);

      // Key manager
      final KeyManagerFactory aKeyManagerFactory = KeyManagerFactory.getInstance ("SunX509");
      aKeyManagerFactory.init (aKeyStore, aKeystorePassword);

      // Assign key manager and empty trust manager to SSL context
      final SSLContext aSSLCtx = SSLContext.getInstance ("TLS");
      aSSLCtx.init (aKeyManagerFactory.getKeyManagers (),
                    new TrustManager [] { new TrustManagerTrustAll (false) },
                    null);
      aParticipantClient.setSSLSocketFactory (aSSLCtx.getSocketFactory ());
    }

    // Main WS call
    final IParticipantIdentifier aServiceGroupID = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (sServiceGroupID);
    if (bCreate)
    {
      // Create
      aParticipantClient.create (SMP_ID, aServiceGroupID);
      LOGGER.info ("Successfully created participant " + aServiceGroupID.getURIEncoded ());
    }
    else
    {
      // Delete
      aParticipantClient.delete (SMP_ID, aServiceGroupID);
      LOGGER.info ("Successfully deleted participant " + aServiceGroupID.getURIEncoded ());
    }
  }
}
