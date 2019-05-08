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
package com.helger.peppol.smlclient.supplementary.tools;

import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.random.RandomHelper;
import com.helger.commons.string.StringHelper;
import com.helger.commons.system.SystemProperties;
import com.helger.commons.ws.TrustManagerTrustAll;
import com.helger.peppol.identifier.IParticipantIdentifier;
import com.helger.peppol.identifier.factory.PeppolIdentifierFactory;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smlclient.ManageParticipantIdentifierServiceCaller;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.KeyStoreHelper;
import com.helger.wsclient.WSHelper;

/**
 * Special tool for Arun :)
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
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
    final String sKeystorePassword = "peppol";
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
    if (nProxyPort > 0 && StringHelper.hasText (sProxyHostname))
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
      final KeyStore aKeyStore = KeyStoreHelper.loadKeyStoreDirect (eKeyStoreType, sKeystorePath, sKeystorePassword);

      // Key manager
      final KeyManagerFactory aKeyManagerFactory = KeyManagerFactory.getInstance ("SunX509");
      aKeyManagerFactory.init (aKeyStore, sKeystorePassword.toCharArray ());

      // Assign key manager and empty trust manager to SSL context
      final SSLContext aSSLCtx = SSLContext.getInstance ("TLS");
      aSSLCtx.init (aKeyManagerFactory.getKeyManagers (),
                    new TrustManager [] { new TrustManagerTrustAll (false) },
                    RandomHelper.getSecureRandom ());
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
