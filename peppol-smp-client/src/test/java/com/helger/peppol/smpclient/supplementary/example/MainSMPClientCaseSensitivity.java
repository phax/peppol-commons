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
package com.helger.peppol.smpclient.supplementary.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.peppol.identifier.factory.PeppolIdentifierFactory;
import com.helger.peppol.identifier.generic.doctype.IDocumentTypeIdentifier;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.identifier.generic.process.IProcessIdentifier;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.smp.ESMPTransportProfile;
import com.helger.peppol.smp.EndpointType;
import com.helger.peppol.smpclient.SMPClientReadOnly;
import com.helger.peppol.url.PeppolURLProvider;

public final class MainSMPClientCaseSensitivity
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (MainSMPClientCaseSensitivity.class);

  public static void main (final String [] args) throws Exception
  {
    final IParticipantIdentifier participantId = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0088:5060510050006");
    s_aLogger.info ("Participant: " + participantId.getURIEncoded ());
    s_aLogger.info ("Participant ID case INsensitive: " +
                    PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierCaseInsensitive (participantId.getScheme ()));

    final SMPClientReadOnly smpClient = new SMPClientReadOnly (PeppolURLProvider.INSTANCE,
                                                               participantId,
                                                               ESML.DIGIT_PRODUCTION);

    final IDocumentTypeIdentifier doctypeId = PeppolIdentifierFactory.INSTANCE.createDocumentTypeIdentifierWithDefaultScheme ("urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0::2.1");
    s_aLogger.info ("DocType: " + doctypeId.getURIEncoded ());
    s_aLogger.info ("DocType ID case INsensitive: " +
                    PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierCaseInsensitive (doctypeId.getScheme ()));
    IProcessIdentifier processId = PeppolIdentifierFactory.INSTANCE.createProcessIdentifierWithDefaultScheme ("URN:WWW.CENBII.EU:PROFILE:BII28:VER2.0");
    s_aLogger.info ("Process[1]: " + processId.getURIEncoded ());
    s_aLogger.info ("Process ID case INsensitive: " +
                    PeppolIdentifierFactory.INSTANCE.isProcessIdentifierCaseInsensitive (processId.getScheme ()));

    EndpointType endpoint = smpClient.getEndpoint (participantId,
                                                   doctypeId,
                                                   processId,
                                                   ESMPTransportProfile.TRANSPORT_PROFILE_AS2);
    s_aLogger.info ("1 - " + endpoint);

    // Won't work, because process identifiers in PEPPOL are case sensitive!
    processId = PeppolIdentifierFactory.INSTANCE.createProcessIdentifierWithDefaultScheme ("urn:www.cenbii.eu:profile:bii28:ver2.0");
    s_aLogger.info ("Process[2]: " + processId.getURIEncoded ());
    s_aLogger.info ("Process ID case INsensitive: " +
                    PeppolIdentifierFactory.INSTANCE.isProcessIdentifierCaseInsensitive (processId.getScheme ()));
    endpoint = smpClient.getEndpoint (participantId, doctypeId, processId, ESMPTransportProfile.TRANSPORT_PROFILE_AS2);
    s_aLogger.info ("2 - " + endpoint);
  }
}
