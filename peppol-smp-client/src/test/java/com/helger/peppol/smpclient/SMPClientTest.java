/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Note: some files, that were not part of the original package are currently
 *   licensed under Apache 2.0 license - https://www.apache.org/licenses/LICENSE-2.0
 *   The respective files contain a special class header!
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
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
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.smpclient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.net.URI;

import javax.xml.ws.wsaddressing.W3CEndpointReference;
import javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.annotation.DevelopersNote;
import com.helger.commons.datetime.PDTFactory;
import com.helger.http.basicauth.BasicAuthClientCredentials;
import com.helger.peppol.identifier.DocumentIdentifierType;
import com.helger.peppol.identifier.ParticipantIdentifierType;
import com.helger.peppol.identifier.ProcessIdentifierType;
import com.helger.peppol.identifier.peppol.participant.PeppolParticipantIdentifier;
import com.helger.peppol.smp.CompleteServiceGroupType;
import com.helger.peppol.smp.ESMPTransportProfile;
import com.helger.peppol.smp.EndpointType;
import com.helger.peppol.smp.ProcessListType;
import com.helger.peppol.smp.ProcessType;
import com.helger.peppol.smp.SMPExtensionConverter;
import com.helger.peppol.smp.ServiceEndpointList;
import com.helger.peppol.smp.ServiceGroupReferenceListType;
import com.helger.peppol.smp.ServiceGroupReferenceType;
import com.helger.peppol.smp.ServiceGroupType;
import com.helger.peppol.smp.ServiceInformationType;
import com.helger.peppol.smp.ServiceMetadataType;
import com.helger.peppol.smp.SignedServiceMetadataType;
import com.helger.peppol.smpclient.exception.SMPClientException;
import com.helger.peppol.smpclient.exception.SMPClientNotFoundException;
import com.helger.peppol.smpclient.exception.SMPClientUnauthorizedException;

/**
 * Expects an running SMP, depending on the configuration file. DNS is not
 * needed. See {@link #SMP_URI} constant.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Ignore
public final class SMPClientTest
{
  private static final Logger s_aLogger = LoggerFactory.getLogger ("dummy");
  private static final String SMP_USERNAME = MockSMPClientConfig.getSMPUserName ();
  private static final String SMP_PASSWORD = MockSMPClientConfig.getSMPPassword ();
  private static final BasicAuthClientCredentials SMP_CREDENTIALS = new BasicAuthClientCredentials (SMP_USERNAME,
                                                                                                    SMP_PASSWORD);
  public static final URI SMP_URI = MockSMPClientConfig.getSMPURI ();

  @BeforeClass
  public static void init () throws Throwable
  {
    final SMPClient aSMPClient = new SMPClient (SMP_URI);

    try
    {
      // Ensure to delete the test service group
      aSMPClient.deleteServiceGroup (MockSMPClientConfig.getParticipantID (), SMP_CREDENTIALS);
    }
    catch (final SMPClientNotFoundException ex)
    {
      // This is ok
    }
    catch (final SMPClientException ex)
    {
      // e.g. thrown when the SMP is not reachable
      if (ex.getCause () != null)
        throw ex.getCause ();
      throw ex;
    }
  }

  @Test
  public void testGetServiceMetadataNotExistsOnExistingSMP () throws SMPClientException
  {
    final ParticipantIdentifierType aServiceGroupID = PeppolParticipantIdentifier.createWithDefaultScheme ("0088:surleyNotExisting");

    final SMPClient aSMPClient = new SMPClient (SMP_URI);
    assertNull (aSMPClient.getServiceRegistrationOrNull (aServiceGroupID, MockSMPClientConfig.getDocumentTypeID ()));
  }

  @Test
  public void testGetServiceGroupReferenceList () throws SMPClientException
  {
    final SMPClient aSMPClient = new SMPClient (SMP_URI);
    final ServiceGroupReferenceListType aServiceGroupReferenceList = aSMPClient.getServiceGroupReferenceList (SMP_USERNAME,
                                                                                                              SMP_CREDENTIALS);
    assertNotNull (aServiceGroupReferenceList);
    for (final ServiceGroupReferenceType aServiceGroupReference : aServiceGroupReferenceList.getServiceGroupReference ())
    {
      final CompleteServiceGroupType aCSG = aSMPClient.getCompleteServiceGroup (aServiceGroupReference.getHref ());
      assertNotNull (aCSG);
    }
  }

  @Test
  public void testCRUDServiceGroup () throws SMPClientException
  {
    final String sContent = "Test";
    final String sElement = "TestElement";
    final String sExtensionXML = "<" + sElement + ">" + sContent + "</" + sElement + ">";

    final SMPClient aSMPClient = new SMPClient (SMP_URI);
    final ServiceGroupType aServiceGroupCreate = aSMPClient.saveServiceGroup (MockSMPClientConfig.getParticipantID (),
                                                                              SMP_CREDENTIALS);
    aServiceGroupCreate.setExtension (SMPExtensionConverter.convertOrNull (sExtensionXML));
    aSMPClient.saveServiceGroup (aServiceGroupCreate, SMP_CREDENTIALS);

    final ParticipantIdentifierType aServiceGroupID = aServiceGroupCreate.getParticipantIdentifier ();
    final ServiceGroupType aServiceGroupRead = aSMPClient.getServiceGroup (aServiceGroupID);
    assertNotNull (aServiceGroupRead);
    assertNotNull (aServiceGroupRead.getExtension ());
    assertNotNull (aServiceGroupRead.getExtension ().getAny ());
    assertEquals (sContent, aServiceGroupRead.getExtension ().getAny ().getTextContent ());
    assertEquals (sElement, aServiceGroupRead.getExtension ().getAny ().getLocalName ());

    aSMPClient.deleteServiceGroup (MockSMPClientConfig.getParticipantID (), SMP_CREDENTIALS);
  }

  @Test
  public void testUnauthorizedUser () throws SMPClientException
  {
    final SMPClient aSMPClient = new SMPClient (SMP_URI);
    final BasicAuthClientCredentials aCredentials = new BasicAuthClientCredentials (SMP_USERNAME +
                                                                                    "wronguser",
                                                                                    SMP_PASSWORD);
    try
    {
      aSMPClient.saveServiceGroup (MockSMPClientConfig.getParticipantID (), aCredentials);
      fail ();
    }
    catch (final SMPClientUnauthorizedException ex)
    {}
  }

  @Test
  public void testUnauthorizedPassword () throws SMPClientException
  {
    final SMPClient aSMPClient = new SMPClient (SMP_URI);
    final BasicAuthClientCredentials aCredentials = new BasicAuthClientCredentials (SMP_USERNAME,
                                                                                    SMP_PASSWORD + "wrongpass");
    try
    {
      aSMPClient.saveServiceGroup (MockSMPClientConfig.getParticipantID (), aCredentials);
      fail ();
    }
    catch (final SMPClientUnauthorizedException ex)
    {}
  }

  @Test
  @DevelopersNote ("May fails to validate the signed response because of test keystore")
  public void testCRUDServiceRegistration () throws Exception
  {
    final SMPClient aSMPClient = new SMPClient (SMP_URI);
    aSMPClient.saveServiceGroup (MockSMPClientConfig.getParticipantID (), SMP_CREDENTIALS);

    final ParticipantIdentifierType aServiceGroupID = MockSMPClientConfig.getParticipantID ();
    final DocumentIdentifierType aDocumentID = MockSMPClientConfig.getDocumentTypeID ();
    final ProcessIdentifierType aProcessID = MockSMPClientConfig.getProcessTypeID ();

    final ServiceMetadataType aServiceMetadata = new ServiceMetadataType ();
    {
      final ServiceInformationType aServiceInformation = new ServiceInformationType ();
      {
        final ProcessListType aProcessList = new ProcessListType ();
        {
          final ProcessType aProcess = new ProcessType ();
          {
            final ServiceEndpointList aServiceEndpointList = new ServiceEndpointList ();
            {
              final EndpointType aEndpoint = new EndpointType ();
              final W3CEndpointReference aEndpointReferenceType = new W3CEndpointReferenceBuilder ().address ("http://peppol.eu/sampleService/")
                                                                                                    .build ();
              aEndpoint.setEndpointReference (aEndpointReferenceType);
              aEndpoint.setTransportProfile (ESMPTransportProfile.TRANSPORT_PROFILE_AS2.getID ());
              // Certificate: Base64.encodeBytes (certificate.getEncoded ());
              aEndpoint.setCertificate ("1234567890");
              aEndpoint.setServiceActivationDate (PDTFactory.getCurrentLocalDateTime ());
              aEndpoint.setServiceDescription ("TEST DESCRIPTION");
              aEndpoint.setServiceExpirationDate (PDTFactory.getCurrentLocalDateTime ().plusYears (1));
              aEndpoint.setTechnicalContactUrl ("mailto:smpclient.unittest@helger.com");
              aEndpoint.setMinimumAuthenticationLevel ("2");
              aEndpoint.setRequireBusinessLevelSignature (false);

              aServiceEndpointList.getEndpoint ().add (aEndpoint);
            }

            aProcess.setProcessIdentifier (aProcessID);
            aProcess.setServiceEndpointList (aServiceEndpointList);
          }

          aProcessList.getProcess ().add (aProcess);
        }

        aServiceInformation.setDocumentIdentifier (aDocumentID);
        aServiceInformation.setParticipantIdentifier (aServiceGroupID);
        aServiceInformation.setProcessList (aProcessList);
      }

      aServiceMetadata.setServiceInformation (aServiceInformation);
    }
    aSMPClient.saveServiceRegistration (aServiceMetadata, SMP_CREDENTIALS);

    final SignedServiceMetadataType aSignedServiceMetadata = aSMPClient.getServiceRegistration (aServiceGroupID,
                                                                                                aDocumentID);
    s_aLogger.info ("Service aMetadata ID:" +
                    aSignedServiceMetadata.getServiceMetadata ()
                                          .getServiceInformation ()
                                          .getParticipantIdentifier ()
                                          .getValue ());

    aSMPClient.deleteServiceRegistration (aServiceGroupID, aDocumentID, SMP_CREDENTIALS);
    aSMPClient.deleteServiceGroup (MockSMPClientConfig.getParticipantID (), SMP_CREDENTIALS);
  }
}
