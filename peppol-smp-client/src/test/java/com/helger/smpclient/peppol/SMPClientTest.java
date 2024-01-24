/*
 * Copyright (C) 2015-2024 Philip Helger
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
package com.helger.smpclient.peppol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.net.URI;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.datetime.PDTFactory;
import com.helger.http.basicauth.BasicAuthClientCredentials;
import com.helger.peppol.smp.ESMPTransportProfile;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.simple.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppolid.simple.participant.SimpleParticipantIdentifier;
import com.helger.peppolid.simple.process.SimpleProcessIdentifier;
import com.helger.smpclient.exception.SMPClientException;
import com.helger.smpclient.exception.SMPClientNotFoundException;
import com.helger.smpclient.exception.SMPClientUnauthorizedException;
import com.helger.smpclient.peppol.utils.SMPExtensionConverter;
import com.helger.xsds.peppol.id1.ParticipantIdentifierType;
import com.helger.xsds.peppol.smp1.CompleteServiceGroupType;
import com.helger.xsds.peppol.smp1.EndpointType;
import com.helger.xsds.peppol.smp1.ProcessListType;
import com.helger.xsds.peppol.smp1.ProcessType;
import com.helger.xsds.peppol.smp1.ServiceEndpointList;
import com.helger.xsds.peppol.smp1.ServiceGroupReferenceListType;
import com.helger.xsds.peppol.smp1.ServiceGroupReferenceType;
import com.helger.xsds.peppol.smp1.ServiceGroupType;
import com.helger.xsds.peppol.smp1.ServiceInformationType;
import com.helger.xsds.peppol.smp1.SignedServiceMetadataType;

import jakarta.xml.ws.wsaddressing.W3CEndpointReference;
import jakarta.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;

/**
 * Expects an running SMP, depending on the configuration file. DNS is not
 * needed. See {@link #SMP_URI} constant.
 *
 * @author Philip Helger
 */
@Ignore ("because its the writing API test")
public final class SMPClientTest
{
  @ClassRule
  public static final SMPClientTestConfigRule RULE = new SMPClientTestConfigRule ();

  private static final Logger LOGGER = LoggerFactory.getLogger ("dummy");
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
    final IParticipantIdentifier aServiceGroupID = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0088:surleyNotExisting");

    final SMPClient aSMPClient = new SMPClient (SMP_URI);
    assertNull (aSMPClient.getServiceMetadataOrNull (aServiceGroupID, MockSMPClientConfig.getDocumentTypeID ()));
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
    aServiceGroupCreate.setExtension (SMPExtensionConverter.convert (sExtensionXML));
    aSMPClient.saveServiceGroup (aServiceGroupCreate, SMP_CREDENTIALS);

    final ParticipantIdentifierType aServiceGroupID = aServiceGroupCreate.getParticipantIdentifier ();
    final ServiceGroupType aServiceGroupRead = aSMPClient.getServiceGroup (SimpleParticipantIdentifier.wrap (aServiceGroupID));
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
    final BasicAuthClientCredentials aCredentials = new BasicAuthClientCredentials (SMP_USERNAME + "wronguser",
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
  public void testCRUDServiceRegistration () throws Exception
  {
    final SMPClient aSMPClient = new SMPClient (SMP_URI);
    aSMPClient.saveServiceGroup (MockSMPClientConfig.getParticipantID (), SMP_CREDENTIALS);

    final IParticipantIdentifier aServiceGroupID = MockSMPClientConfig.getParticipantID ();
    final IDocumentTypeIdentifier aDocumentID = MockSMPClientConfig.getDocumentTypeID ();
    final IProcessIdentifier aProcessID = MockSMPClientConfig.getProcessTypeID ();

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
            aEndpoint.setTransportProfile (ESMPTransportProfile.TRANSPORT_PROFILE_PEPPOL_AS4_V2.getID ());
            // Certificate: Base64.encodeBytes (certificate.getEncoded ());
            aEndpoint.setCertificate ("1234567890");
            aEndpoint.setServiceActivationDate (PDTFactory.getCurrentXMLOffsetDateTime ());
            aEndpoint.setServiceDescription ("TEST DESCRIPTION");
            aEndpoint.setServiceExpirationDate (PDTFactory.getCurrentXMLOffsetDateTime ().plusYears (1));
            aEndpoint.setTechnicalContactUrl ("mailto:smpclient.unittest@helger.com");
            aEndpoint.setMinimumAuthenticationLevel ("2");
            aEndpoint.setRequireBusinessLevelSignature (false);

            aServiceEndpointList.getEndpoint ().add (aEndpoint);
          }

          aProcess.setProcessIdentifier (new SimpleProcessIdentifier (aProcessID));
          aProcess.setServiceEndpointList (aServiceEndpointList);
        }

        aProcessList.getProcess ().add (aProcess);
      }

      aServiceInformation.setDocumentIdentifier (new SimpleDocumentTypeIdentifier (aDocumentID));
      aServiceInformation.setParticipantIdentifier (new SimpleParticipantIdentifier (aServiceGroupID));
      aServiceInformation.setProcessList (aProcessList);
    }
    aSMPClient.saveServiceInformation (aServiceInformation, SMP_CREDENTIALS);

    final SignedServiceMetadataType aSignedServiceMetadata = aSMPClient.getServiceMetadata (aServiceGroupID,
                                                                                            aDocumentID);
    LOGGER.info ("Service Metadata ID: " +
                 aSignedServiceMetadata.getServiceMetadata ()
                                       .getServiceInformation ()
                                       .getParticipantIdentifier ()
                                       .getValue ());

    aSMPClient.deleteServiceRegistration (aServiceGroupID, aDocumentID, SMP_CREDENTIALS);
    aSMPClient.deleteServiceGroup (MockSMPClientConfig.getParticipantID (), SMP_CREDENTIALS);
  }
}
