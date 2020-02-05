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
package com.helger.smpclient.peppol.supplementary.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.url.URLHelper;
import com.helger.peppol.smp.ESMPTransportProfile;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.doctype.EPredefinedDocumentTypeIdentifier;
import com.helger.peppolid.peppol.process.EPredefinedProcessIdentifier;
import com.helger.smpclient.peppol.SMPClientReadOnly;

/**
 * Example application that shows how to invoke the {@link SMPClientReadOnly}
 *
 * @author Philip Helger
 */
public final class MainSMPClientExampleNoDNSLookup
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainSMPClientExampleNoDNSLookup.class);

  public static void main (final String [] args) throws Exception
  {
    // The PEPPOL participant identifier
    final IParticipantIdentifier aPI_AT_Test = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test");

    // Create the main SMP client using the production SML
    final SMPClientReadOnly aSMPClient = new SMPClientReadOnly (URLHelper.getAsURI ("http://B-85008b8279e07ab0392da75fa55856a2.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu"));
    final String sEndpointAddress = aSMPClient.getEndpointAddress (aPI_AT_Test,
                                                                   EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS4A_V20,
                                                                   EPredefinedProcessIdentifier.BIS4A_V2,
                                                                   ESMPTransportProfile.TRANSPORT_PROFILE_AS2);

    // Endpoint address should be "https://test.erechnung.gv.at/as2"
    LOGGER.info ("The Austrian government test AS2 AP that handles invoices in BIS4A V2.0 is located at: " +
                    sEndpointAddress);
  }
}
