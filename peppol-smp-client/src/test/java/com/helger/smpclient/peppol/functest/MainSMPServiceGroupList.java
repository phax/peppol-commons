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
import com.helger.smpclient.peppol.jaxb.ServiceGroupType;
import com.helger.smpclient.peppol.utils.SMPDebugHelper;

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
