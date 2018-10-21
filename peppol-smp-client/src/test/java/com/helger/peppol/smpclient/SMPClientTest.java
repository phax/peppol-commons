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
import com.helger.peppol.identifier.ParticipantIdentifierType;
import com.helger.peppol.identifier.factory.PeppolIdentifierFactory;
import com.helger.peppol.identifier.generic.doctype.IDocumentTypeIdentifier;
import com.helger.peppol.identifier.generic.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.identifier.generic.participant.SimpleParticipantIdentifier;
import com.helger.peppol.identifier.generic.process.IProcessIdentifier;
import com.helger.peppol.identifier.generic.process.SimpleProcessIdentifier;
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
  @DevelopersNote ("May fails to validate the signed response because of test keystore")
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

    final SignedServiceMetadataType aSignedServiceMetadata = aSMPClient.getServiceRegistration (aServiceGroupID,
                                                                                                aDocumentID);
    LOGGER.info ("Service aMetadata ID:" +
                 aSignedServiceMetadata.getServiceMetadata ()
                                       .getServiceInformation ()
                                       .getParticipantIdentifier ()
                                       .getValue ());

    aSMPClient.deleteServiceRegistration (aServiceGroupID, aDocumentID, SMP_CREDENTIALS);
    aSMPClient.deleteServiceGroup (MockSMPClientConfig.getParticipantID (), SMP_CREDENTIALS);
  }
}
