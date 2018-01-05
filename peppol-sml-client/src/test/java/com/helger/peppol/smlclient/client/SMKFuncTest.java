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
package com.helger.peppol.smlclient.client;

import static org.junit.Assert.assertSame;

import javax.annotation.Nonnull;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.ws.WSHelper;
import com.helger.peppol.identifier.factory.PeppolIdentifierFactory;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.smlclient.AbstractSMLClientTestCase;
import com.helger.peppol.smlclient.ManageParticipantIdentifierServiceCaller;
import com.helger.peppol.smlclient.ManageServiceMetadataServiceCaller;
import com.helger.peppol.smlclient.MockSMLClientConfig;
import com.helger.peppol.smlclient.smp.PublisherEndpointType;
import com.helger.peppol.smlclient.smp.ServiceMetadataPublisherServiceType;

/**
 * @author Philip Helger
 */
@Ignore ("Requires a running SML and a configured certificate")
public final class SMKFuncTest extends AbstractSMLClientTestCase
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (SMKFuncTest.class);
  private static final String L_ENDPOINTADDRESS = "http://test-smp.example.org";
  private static final String P_ENDPOINTADDRESS = "127.0.0.1";
  private static final String SMP_ID = "SMP-TEST-ID-PH";

  static
  {
    assertSame (SML_INFO, ESML.DIGIT_TEST);

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
      aSMPClient.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO));

      final ManageParticipantIdentifierServiceCaller aPIClient = new ManageParticipantIdentifierServiceCaller (SML_INFO);
      aPIClient.setSSLSocketFactory (aSMPClient.getSSLSocketFactory ());

      // Create SMP - with network logging
      s_aLogger.info ("CREATE SMP");
      _createSMPData (aSMPClient, SMP_ID);
      try
      {
        s_aLogger.info ("CREATE PARTICIPANT");
        aPIClient.create (SMP_ID,
                          PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:philip"));
        try
        {}
        finally
        {
          s_aLogger.info ("DELETE PARTICIPANT");
          // The version with SMP_ID is required for SMK 3.0
          aPIClient.delete (SMP_ID,
                            PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:philip"));
        }
      }
      finally
      {
        // Delete SMP
        s_aLogger.info ("DELETE SMP");
        aSMPClient.delete (SMP_ID);
      }
    }
    finally
    {
      WSHelper.enableSoapLogging (false);
    }
  }
}
