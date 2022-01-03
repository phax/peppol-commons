/*
 * Copyright (C) 2015-2022 Philip Helger
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
package com.helger.peppol.smlclient.client;

import static org.junit.Assert.assertSame;

import javax.annotation.Nonnull;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.annotation.Nonempty;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.smlclient.AbstractSMLClientTestCase;
import com.helger.peppol.smlclient.ManageParticipantIdentifierServiceCaller;
import com.helger.peppol.smlclient.ManageServiceMetadataServiceCaller;
import com.helger.peppol.smlclient.MockSMLClientConfig;
import com.helger.peppol.smlclient.smp.PublisherEndpointType;
import com.helger.peppol.smlclient.smp.ServiceMetadataPublisherServiceType;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.wsclient.WSHelper;

/**
 * @author Philip Helger
 */
@Ignore ("Requires a running SML and a configured certificate")
public final class SMKFuncTest extends AbstractSMLClientTestCase
{
  private static final Logger LOGGER = LoggerFactory.getLogger (SMKFuncTest.class);
  private static final String L_ENDPOINTADDRESS = "http://test-smp.example.org";
  private static final String P_ENDPOINTADDRESS = "127.0.0.1";
  private static final String SMP_ID = "SMP-TEST-ID-PH";

  static
  {
    assertSame (ESML.DIGIT_TEST, SML_INFO);

    // To get an eventual proxy setting correct
    MockSMLClientConfig.getKeyStorePath ();
  }

  @Nonnull
  private static ServiceMetadataPublisherServiceType _createSMPData (@Nonnull final ManageServiceMetadataServiceCaller aSMPClient,
                                                                     @Nonnull @Nonempty final String sSMPID) throws Exception
  {
    final ServiceMetadataPublisherServiceType aServiceMetadataCreate = new ServiceMetadataPublisherServiceType ();
    aServiceMetadataCreate.setServiceMetadataPublisherID (sSMPID);
    final PublisherEndpointType aEndpoint = new PublisherEndpointType ();
    aEndpoint.setLogicalAddress (L_ENDPOINTADDRESS);
    aEndpoint.setPhysicalAddress (P_ENDPOINTADDRESS);
    aServiceMetadataCreate.setPublisherEndpoint (aEndpoint);

    aSMPClient.create (aServiceMetadataCreate);
    return aServiceMetadataCreate;
  }

  @Test
  public void testCreateAndDeletePublisher () throws Exception
  {
    WSHelper.enableSoapLogging (true);
    try
    {
      // Create client
      final ManageServiceMetadataServiceCaller aSMPClient = new ManageServiceMetadataServiceCaller (SML_INFO);
      aSMPClient.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));

      final ManageParticipantIdentifierServiceCaller aPIClient = new ManageParticipantIdentifierServiceCaller (SML_INFO);
      aPIClient.setSSLSocketFactory (aSMPClient.getSSLSocketFactory ());

      // Create SMP - with network logging
      LOGGER.info ("CREATE SMP");
      _createSMPData (aSMPClient, SMP_ID);
      try
      {
        LOGGER.info ("CREATE PARTICIPANT");
        aPIClient.create (SMP_ID, PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:philip"));
        try
        {}
        finally
        {
          LOGGER.info ("DELETE PARTICIPANT");
          // The version with SMP_ID is required for SMK 3.0
          aPIClient.delete (SMP_ID, PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:philip"));
        }
      }
      finally
      {
        // Delete SMP
        LOGGER.info ("DELETE SMP");
        aSMPClient.delete (SMP_ID);
      }
    }
    finally
    {
      WSHelper.enableSoapLogging (false);
    }
  }
}
