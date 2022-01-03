/*
 * Copyright (C) 2015-2022 Philip Helger
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

import com.helger.peppolid.IParticipantIdentifier;
import com.helger.smpclient.peppol.MockSMPClientConfig;
import com.helger.smpclient.peppol.SMPClient;
import com.helger.smpclient.peppol.utils.SMPDebugHelper;
import com.helger.xsds.peppol.smp1.CompleteServiceGroupType;
import com.helger.xsds.peppol.smp1.ServiceMetadataType;

/**
 * @author Philip Helger
 */
public final class MainSMPServiceGroupCompleteList
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainSMPServiceGroupCompleteList.class);

  public static void main (final String [] args) throws Exception
  {
    final URI SMP_URI = MockSMPClientConfig.getSMPURI ();
    final IParticipantIdentifier PARTICIPANT_ID = MockSMPClientConfig.getParticipantID ();

    // The main SMP client
    final SMPClient aClient = new SMPClient (SMP_URI);

    // Get the service group reference list
    final CompleteServiceGroupType aCompleteServiceGroup = aClient.getCompleteServiceGroupOrNull (PARTICIPANT_ID);

    if (aCompleteServiceGroup == null)
      LOGGER.error ("Failed to get complete service group for " + PARTICIPANT_ID);
    else
    {
      LOGGER.info (SMPDebugHelper.getAsString (aCompleteServiceGroup.getServiceGroup ()));
      for (final ServiceMetadataType aServiceMetadata : aCompleteServiceGroup.getServiceMetadata ())
        LOGGER.info (SMPDebugHelper.getAsString (aServiceMetadata));
    }

    LOGGER.info ("Done");
  }
}
