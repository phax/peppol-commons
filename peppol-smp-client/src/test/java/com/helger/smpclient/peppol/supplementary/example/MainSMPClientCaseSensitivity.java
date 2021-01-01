/**
 * Copyright (C) 2015-2021 Philip Helger
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
package com.helger.smpclient.peppol.supplementary.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.peppol.sml.ESML;
import com.helger.peppol.smp.ESMPTransportProfile;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.smpclient.peppol.SMPClientReadOnly;
import com.helger.smpclient.peppol.jaxb.EndpointType;
import com.helger.smpclient.url.PeppolURLProvider;

public final class MainSMPClientCaseSensitivity
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainSMPClientCaseSensitivity.class);

  public static void main (final String [] args) throws Exception
  {
    final IParticipantIdentifier participantId = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0088:5060510050006");
    LOGGER.info ("Participant: " + participantId.getURIEncoded ());
    LOGGER.info ("Participant ID case INsensitive: " +
                 PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierCaseInsensitive (participantId.getScheme ()));

    final SMPClientReadOnly smpClient = new SMPClientReadOnly (PeppolURLProvider.INSTANCE, participantId, ESML.DIGIT_PRODUCTION);
    smpClient.setXMLSchemaValidation (false);

    final IDocumentTypeIdentifier doctypeId = PeppolIdentifierFactory.INSTANCE.createDocumentTypeIdentifierWithDefaultScheme ("urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0::2.1");
    LOGGER.info ("DocType: " + doctypeId.getURIEncoded ());
    LOGGER.info ("DocType ID case INsensitive: " +
                 PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierCaseInsensitive (doctypeId.getScheme ()));
    IProcessIdentifier processId = PeppolIdentifierFactory.INSTANCE.createProcessIdentifierWithDefaultScheme ("URN:WWW.CENBII.EU:PROFILE:BII28:VER2.0");
    LOGGER.info ("Process[1]: " + processId.getURIEncoded ());
    LOGGER.info ("Process ID case INsensitive: " +
                 PeppolIdentifierFactory.INSTANCE.isProcessIdentifierCaseInsensitive (processId.getScheme ()));

    EndpointType endpoint = smpClient.getEndpoint (participantId, doctypeId, processId, ESMPTransportProfile.TRANSPORT_PROFILE_AS2);
    LOGGER.info ("1 - " + endpoint);

    // Won't work, because process identifiers in PEPPOL are case sensitive!
    processId = PeppolIdentifierFactory.INSTANCE.createProcessIdentifierWithDefaultScheme ("urn:www.cenbii.eu:profile:bii28:ver2.0");
    LOGGER.info ("Process[2]: " + processId.getURIEncoded ());
    LOGGER.info ("Process ID case INsensitive: " +
                 PeppolIdentifierFactory.INSTANCE.isProcessIdentifierCaseInsensitive (processId.getScheme ()));
    endpoint = smpClient.getEndpoint (participantId, doctypeId, processId, ESMPTransportProfile.TRANSPORT_PROFILE_AS2);
    LOGGER.info ("2 - " + endpoint);
  }
}
