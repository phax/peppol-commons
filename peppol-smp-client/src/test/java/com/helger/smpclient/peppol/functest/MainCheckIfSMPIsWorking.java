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
package com.helger.smpclient.peppol.functest;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.http.basicauth.BasicAuthClientCredentials;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.simple.participant.SimpleParticipantIdentifier;
import com.helger.smpclient.peppol.MockSMPClientConfig;
import com.helger.smpclient.peppol.SMPClient;
import com.helger.xsds.peppol.smp1.ServiceGroupType;

/**
 * Check if an SMP installation is working. Prior to executing this class, make
 * sure that the file <code>functest.properties</code> filled correctly!
 *
 * @author Philip Helger
 */
public final class MainCheckIfSMPIsWorking
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainCheckIfSMPIsWorking.class);

  public static void main (final String [] args) throws Exception
  {
    final URI SMP_URI = MockSMPClientConfig.getSMPURI ();
    final BasicAuthClientCredentials SMP_CREDENTIALS = MockSMPClientConfig.getSMPCredentials ();
    final IParticipantIdentifier PARTICIPANT_ID = MockSMPClientConfig.getParticipantID ();

    // The main SMP client
    final SMPClient aClient = new SMPClient (SMP_URI);

    // Ensure that the service group does not exist
    LOGGER.info ("Ensuring that the service group is not existing!");
    if (aClient.getServiceGroupOrNull (PARTICIPANT_ID) != null)
    {
      LOGGER.info ("Deleting existing service group for init");
      aClient.deleteServiceGroup (PARTICIPANT_ID, SMP_CREDENTIALS);
      LOGGER.info ("Finished deletion of service group");
    }

    // Create, read and delete the service group
    LOGGER.info ("Creating the new service group");
    aClient.saveServiceGroup (PARTICIPANT_ID, SMP_CREDENTIALS);

    LOGGER.info ("Retrieving the service group");
    final ServiceGroupType aSGT = aClient.getServiceGroup (PARTICIPANT_ID);
    if (!SimpleParticipantIdentifier.wrap (aSGT.getParticipantIdentifier ()).equals (PARTICIPANT_ID))
      throw new IllegalStateException ("Participant identifiers are not equal!");

    LOGGER.info ("Deleting the service group again");
    aClient.deleteServiceGroup (PARTICIPANT_ID, SMP_CREDENTIALS);

    LOGGER.info ("Checking if the service group is really deleted");
    if (aClient.getServiceGroupOrNull (PARTICIPANT_ID) != null)
      throw new IllegalStateException ("Deletion of the service group failed!");

    LOGGER.info ("Seems like the SMP is working as expected!");
  }
}
