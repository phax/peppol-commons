/**
 * Copyright (C) 2015-2020 Philip Helger
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

import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.smpclient.peppol.MockSMPClientConfig;
import com.helger.smpclient.peppol.SMPClient;
import com.helger.smpclient.peppol.jaxb.SignedServiceMetadataType;
import com.helger.smpclient.peppol.utils.SMPDebugHelper;

/**
 * @author Philip Helger
 */
public final class MainSMPServiceRegistrationList
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainSMPServiceRegistrationList.class);

  public static void main (final String [] args) throws Exception
  {
    final URI SMP_URI = MockSMPClientConfig.getSMPURI ();
    final IParticipantIdentifier PARTICIPANT_ID = MockSMPClientConfig.getParticipantID ();
    final IDocumentTypeIdentifier DOCUMENT_ID = MockSMPClientConfig.getDocumentTypeID ();

    // The main SMP client
    final SMPClient aClient = new SMPClient (SMP_URI);

    // Get the service group reference list
    final SignedServiceMetadataType aSignedServiceMetadata = aClient.getServiceMetadataOrNull (PARTICIPANT_ID,
                                                                                               DOCUMENT_ID);

    if (aSignedServiceMetadata == null)
      LOGGER.error ("Failed to get service registration for " + PARTICIPANT_ID + " and " + DOCUMENT_ID);
    else
      LOGGER.info (SMPDebugHelper.getAsString (aSignedServiceMetadata.getServiceMetadata ()));

    LOGGER.info ("Done");
  }
}
