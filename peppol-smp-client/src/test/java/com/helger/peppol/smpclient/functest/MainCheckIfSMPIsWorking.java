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
package com.helger.peppol.smpclient.functest;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.http.basicauth.BasicAuthClientCredentials;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.smp.ServiceGroupType;
import com.helger.peppol.smpclient.MockSMPClientConfig;
import com.helger.peppol.smpclient.SMPClient;

/**
 * Check if an SMP installation is working. Prior to executing this class, make
 * sure that the file <code>functest.properties</code> filled correctly!
 *
 * @author philip
 */
public final class MainCheckIfSMPIsWorking
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (MainCheckIfSMPIsWorking.class);

  public static void main (final String [] args) throws Exception
  {
    final URI SMP_URI = MockSMPClientConfig.getSMPURI ();
    final BasicAuthClientCredentials SMP_CREDENTIALS = MockSMPClientConfig.getSMPCredentials ();
    final IParticipantIdentifier PARTICIPANT_ID = MockSMPClientConfig.getParticipantID ();

    // The main SMP client
    final SMPClient aClient = new SMPClient (SMP_URI);

    // Ensure that the service group does not exist
    s_aLogger.info ("Ensuring that the service group is not existing!");
    if (aClient.getServiceGroupOrNull (PARTICIPANT_ID) != null)
    {
      s_aLogger.info ("Deleting existing service group for init");
      aClient.deleteServiceGroup (PARTICIPANT_ID, SMP_CREDENTIALS);
      s_aLogger.info ("Finished deletion of service group");
    }

    // Create, read and delete the service group
    s_aLogger.info ("Creating the new service group");
    aClient.saveServiceGroup (PARTICIPANT_ID, SMP_CREDENTIALS);

    s_aLogger.info ("Retrieving the service group");
    final ServiceGroupType aSGT = aClient.getServiceGroup (PARTICIPANT_ID);
    if (!aSGT.getParticipantIdentifier ().equals (PARTICIPANT_ID))
      throw new IllegalStateException ("Participant identifiers are not equal!");

    s_aLogger.info ("Deleting the service group again");
    aClient.deleteServiceGroup (PARTICIPANT_ID, SMP_CREDENTIALS);

    s_aLogger.info ("Checking if the service group is really deleted");
    if (aClient.getServiceGroupOrNull (PARTICIPANT_ID) != null)
      throw new IllegalStateException ("Deletion of the service group failed!");

    s_aLogger.info ("Seems like the SMP is working as expected!");
  }
}
