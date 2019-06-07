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
package com.helger.peppol.smpclient.functest;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.peppol.smp.ServiceGroupType;
import com.helger.peppol.smpclient.MockSMPClientConfig;
import com.helger.peppol.smpclient.SMPClient;
import com.helger.peppol.smpclient.utils.SMPDebugHelper;
import com.helger.peppolid.IParticipantIdentifier;

/**
 * @author Philip Helger
 */
public final class MainSMPServiceGroupList
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainSMPServiceGroupList.class);

  public static void main (final String [] args) throws Exception
  {
    final URI SMP_URI = MockSMPClientConfig.getSMPURI ();
    final IParticipantIdentifier PARTICIPANT_ID = MockSMPClientConfig.getParticipantID ();

    // The main SMP client
    final SMPClient aClient = new SMPClient (SMP_URI);

    // Get the service group information
    final ServiceGroupType aServiceGroup = aClient.getServiceGroupOrNull (PARTICIPANT_ID);
    if (aServiceGroup == null)
      LOGGER.error ("Failed to get service group infos for " + PARTICIPANT_ID);
    else
      LOGGER.info (SMPDebugHelper.getAsString (aServiceGroup));

    LOGGER.info ("Done");
  }
}
