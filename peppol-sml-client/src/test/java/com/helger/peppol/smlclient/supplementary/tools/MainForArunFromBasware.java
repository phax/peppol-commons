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
import com.helger.commons.ws.WSHelper;
import com.helger.peppol.identifier.factory.PeppolIdentifierFactory;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smlclient.ManageParticipantIdentifierServiceCaller;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.KeyStoreHelper;

/**
 * Special tool for Arun :)
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class MainForArunFromBasware
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (MainForArunFromBasware.class);

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
    if (StringHelper.hasText (sProxyHostname) && nProxyPort > 0)
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
      s_aLogger.info ("Successfully created participant " + aServiceGroupID.getURIEncoded ());
    }
    else
    {
      // Delete
      aParticipantClient.delete (SMP_ID, aServiceGroupID);
      s_aLogger.info ("Successfully deleted participant " + aServiceGroupID.getURIEncoded ());
    }
  }
}
