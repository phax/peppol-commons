/**
 * Copyright (C) 2015-2020 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.smpclient.peppol.functest;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.peppolid.IParticipantIdentifier;
import com.helger.smpclient.peppol.MockSMPClientConfig;
import com.helger.smpclient.peppol.SMPClient;
import com.helger.smpclient.peppol.jaxb.CompleteServiceGroupType;
import com.helger.smpclient.peppol.jaxb.ServiceMetadataType;
import com.helger.smpclient.peppol.utils.SMPDebugHelper;

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
