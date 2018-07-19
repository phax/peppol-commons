/**
 * Copyright (C) 2015-2018 Philip Helger (www.helger.com)
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

import com.helger.http.basicauth.BasicAuthClientCredentials;
import com.helger.peppol.smp.ServiceGroupReferenceListType;
import com.helger.peppol.smp.ServiceGroupReferenceType;
import com.helger.peppol.smpclient.MockSMPClientConfig;
import com.helger.peppol.smpclient.SMPClient;

/**
 * @author Philip Helger
 */
public final class MainSMPServiceGroupReferenceList
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainSMPServiceGroupReferenceList.class);

  public static void main (final String [] args) throws Exception
  {
    final URI SMP_URI = MockSMPClientConfig.getSMPURI ();
    final BasicAuthClientCredentials SMP_CREDENTIALS = MockSMPClientConfig.getSMPCredentials ();
    final String SMP_USERNAME = MockSMPClientConfig.getSMPUserName ();

    // The main SMP client
    final SMPClient aClient = new SMPClient (SMP_URI);

    // Get the service group reference list
    final ServiceGroupReferenceListType aServiceGroupReferenceList = aClient.getServiceGroupReferenceListOrNull (SMP_USERNAME,
                                                                                                                 SMP_CREDENTIALS);

    if (aServiceGroupReferenceList == null)
      LOGGER.error ("Failed to get complete service group for " + SMP_USERNAME);
    else
    {
      LOGGER.info ("All service groups owned by " + SMP_USERNAME + ":");
      for (final ServiceGroupReferenceType aServiceGroupReference : aServiceGroupReferenceList.getServiceGroupReference ())
        LOGGER.info ("  " + aServiceGroupReference.getHref ());
    }

    LOGGER.info ("Done");
  }
}
