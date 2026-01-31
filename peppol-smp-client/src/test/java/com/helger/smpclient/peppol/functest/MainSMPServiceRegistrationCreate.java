/*
 * Copyright (C) 2015-2026 Philip Helger
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
import java.time.Month;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.datetime.helper.PDTFactory;
import com.helger.http.basicauth.BasicAuthClientCredentials;
import com.helger.peppol.smp.ESMPTransportProfile;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.simple.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppolid.simple.participant.SimpleParticipantIdentifier;
import com.helger.peppolid.simple.process.SimpleProcessIdentifier;
import com.helger.smpclient.peppol.MockSMPClientConfig;
import com.helger.smpclient.peppol.SMPClient;
import com.helger.xsds.peppol.smp1.EndpointType;
import com.helger.xsds.peppol.smp1.ProcessListType;
import com.helger.xsds.peppol.smp1.ProcessType;
import com.helger.xsds.peppol.smp1.ServiceEndpointList;
import com.helger.xsds.peppol.smp1.ServiceInformationType;

import jakarta.xml.ws.wsaddressing.W3CEndpointReference;

/**
 * @author Philip Helger
 */
public final class MainSMPServiceRegistrationCreate
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainSMPServiceRegistrationCreate.class);

  public static void main (final String [] args) throws Exception
  {
    final URI SMP_URI = MockSMPClientConfig.getSMPURI ();
    final BasicAuthClientCredentials SMP_CREDENTIALS = MockSMPClientConfig.getSMPCredentials ();
    final IParticipantIdentifier PARTICIPANT_ID = MockSMPClientConfig.getParticipantID ();
    final IDocumentTypeIdentifier DOCUMENT_ID = MockSMPClientConfig.getDocumentTypeID ();
    final IProcessIdentifier PROCESS_ID = MockSMPClientConfig.getProcessTypeID ();
    final W3CEndpointReference START_AP_ENDPOINTREF = MockSMPClientConfig.getAPEndpointRef ();
    final String AP_CERT_STRING = MockSMPClientConfig.getAPCert ();
    final String AP_SERVICE_DESCRIPTION = MockSMPClientConfig.getAPServiceDescription ();
    final String AP_CONTACT_URL = MockSMPClientConfig.getAPContact ();
    final String AP_INFO_URL = MockSMPClientConfig.getAPInfo ();

    // The main SMP client
    final SMPClient aClient = new SMPClient (SMP_URI);

    // Create the service registration
    final ServiceInformationType aServiceInformation = new ServiceInformationType ();
    {
      final ProcessListType aProcessList = new ProcessListType ();
      {
        final ProcessType aProcess = new ProcessType ();
        {
          final ServiceEndpointList aServiceEndpointList = new ServiceEndpointList ();
          {
            final EndpointType aEndpoint = new EndpointType ();
            aEndpoint.setEndpointReference (START_AP_ENDPOINTREF);
            aEndpoint.setTransportProfile (ESMPTransportProfile.TRANSPORT_PROFILE_PEPPOL_AS4_V2.getID ());
            aEndpoint.setCertificate (AP_CERT_STRING);
            aEndpoint.setServiceActivationDate (PDTFactory.createXMLOffsetDateTime (2011, Month.JANUARY, 1));
            aEndpoint.setServiceExpirationDate (PDTFactory.createXMLOffsetDateTime (2020, Month.DECEMBER, 31));
            aEndpoint.setServiceDescription (AP_SERVICE_DESCRIPTION);
            aEndpoint.setTechnicalContactUrl (AP_CONTACT_URL);
            aEndpoint.setTechnicalInformationUrl (AP_INFO_URL);
            aEndpoint.setMinimumAuthenticationLevel ("1");
            aEndpoint.setRequireBusinessLevelSignature (false);
            aServiceEndpointList.getEndpoint ().add (aEndpoint);
          }
          aProcess.setProcessIdentifier (new SimpleProcessIdentifier (PROCESS_ID));
          aProcess.setServiceEndpointList (aServiceEndpointList);
        }
        aProcessList.getProcess ().add (aProcess);
      }
      aServiceInformation.setDocumentIdentifier (new SimpleDocumentTypeIdentifier (DOCUMENT_ID));
      aServiceInformation.setParticipantIdentifier (new SimpleParticipantIdentifier (PARTICIPANT_ID));
      aServiceInformation.setProcessList (aProcessList);
    }
    aClient.saveServiceInformation (aServiceInformation, SMP_CREDENTIALS);

    LOGGER.info ("Done");
  }
}
