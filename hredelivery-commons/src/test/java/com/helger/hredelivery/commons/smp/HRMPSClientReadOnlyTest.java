/*
 * Copyright (C) 2025 Philip Helger
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
package com.helger.hredelivery.commons.smp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.helger.hredelivery.commons.CHREDeliveryID;
import com.helger.hredelivery.commons.EHREDeliverySML;
import com.helger.hredelivery.commons.security.HREDeliveryTrustStores;
import com.helger.peppol.smp.ESMPTransportProfile;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.smpclient.url.SMPDNSResolutionException;
import com.helger.xsds.bdxr.smp1.EndpointType;

/**
 * Test class {@link HRMPSClientReadOnly}.
 *
 * @author Philip Helger
 */
public final class HRMPSClientReadOnlyTest
{
  @Test
  // @Ignore ("The current SMP response is totally broken")
  public void testResolveDemoParticipant () throws Exception
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9934:99999999994");

    final HRMPSClientReadOnly aMPSClient = new HRMPSClientReadOnly (aPI, EHREDeliverySML.DEMO);
    aMPSClient.setTrustStore (HREDeliveryTrustStores.Fina2015.TRUSTSTORE_DEMO);

    final String sSMPHost = aMPSClient.getSMPHostURI ();
    assertEquals (true ? "https://hr-smp-test.zzi.si/" : "https://cis.porezna-uprava.hr:8411/EracunMPSCT/", sSMPHost);

    // aMPSClient.setVerifySignature (false);
    // aMPSClient.setXMLSchemaValidation (false);
    final EndpointType aEndpoint = aMPSClient.getEndpoint (aPI,
                                                           CHREDeliveryID.DOC_TYPE_ID_HR_ERACUN_INVOICE_EXT_2025_1_0,
                                                           CHREDeliveryID.PROCESS_ID_HR_ERACUN,
                                                           ESMPTransportProfile.TRANSPORT_PROFILE_ERACUN_AS4_V1);
    assertNotNull (aEndpoint);
  }

  @Test
  public void testResolveDemoParticipant2 () throws Exception
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9934:92265244213");

    final HRMPSClientReadOnly aMPSClient = new HRMPSClientReadOnly (aPI, EHREDeliverySML.DEMO);
    aMPSClient.setTrustStore (HREDeliveryTrustStores.Fina2015.TRUSTSTORE_DEMO);

    final String sSMPHost = aMPSClient.getSMPHostURI ();
    assertEquals ("https://mpsdemo.moj-eracun.hr/", sSMPHost);

    // aMPSClient.setVerifySignature (false);
    aMPSClient.setXMLSchemaValidation (false);
    final EndpointType aEndpoint = aMPSClient.getEndpoint (aPI,
                                                           CHREDeliveryID.DOC_TYPE_ID_HR_ERACUN_INVOICE_EXT_2025_1_0,
                                                           CHREDeliveryID.PROCESS_ID_HR_ERACUN,
                                                           ESMPTransportProfile.TRANSPORT_PROFILE_ERACUN_AS4_V1);
    assertNotNull (aEndpoint);
  }

  @Test
  public void testResolveOpusCapita () throws SMPDNSResolutionException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9934:52424909202");

    final HRMPSClientReadOnly aMPSClient = new HRMPSClientReadOnly (aPI, EHREDeliverySML.DEMO);
    final String sSMPHost = aMPSClient.getSMPHostURI ();
    assertEquals ("https://edelivery-croatia-smp.stage.messagesnetwork.com/", sSMPHost);
  }

  @Test
  public void testResolveMarkant () throws Exception
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9934:29071087912");

    final HRMPSClientReadOnly aMPSClient = new HRMPSClientReadOnly (aPI, EHREDeliverySML.DEMO);
    final String sSMPHost = aMPSClient.getSMPHostURI ();
    assertEquals ("https://acc.mps.markant.services/", sSMPHost);
    aMPSClient.setVerifySignature (false);

    final EndpointType aEndpoint = aMPSClient.getEndpoint (aPI,
                                                           PeppolIdentifierFactory.INSTANCE.parseDocumentTypeIdentifier ("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:mfin.gov.hr:cius-2025:1.0#conformant#urn:mfin.gov.hr:ext-2025:1.0::2.1"),
                                                           PeppolIdentifierFactory.INSTANCE.parseProcessIdentifier ("cenbii-procid-ubl::urn:fdc:eracun.hr:poacc:en16931:any"),
                                                           ESMPTransportProfile.TRANSPORT_PROFILE_ERACUN_AS4_V1);
    assertNotNull (aEndpoint);
    assertEquals ("https://acc.as4-hr.markant.services/as4", aEndpoint.getEndpointURI ());
  }

  @Test
  public void testResolveComarch () throws SMPDNSResolutionException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9934:70583020747");

    final HRMPSClientReadOnly aMPSClient = new HRMPSClientReadOnly (aPI, EHREDeliverySML.DEMO);
    final String sSMPHost = aMPSClient.getSMPHostURI ();
    assertEquals ("https://edi-trn-hr.edoc-online.com/test/smp/", sSMPHost);
  }

  @Test
  public void testResolveOmniSight () throws SMPDNSResolutionException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9934:00419068787");

    final HRMPSClientReadOnly aMPSClient = new HRMPSClientReadOnly (aPI, EHREDeliverySML.DEMO);
    final String sSMPHost = aMPSClient.getSMPHostURI ();
    assertEquals ("http://mps.omnizon.net/", sSMPHost);
  }

  @Test
  // @Ignore ("Back to test portal")
  public void testResolvePWC () throws SMPDNSResolutionException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9934:54648952583");

    final HRMPSClientReadOnly aMPSClient = new HRMPSClientReadOnly (aPI, EHREDeliverySML.DEMO);
    final String sSMPHost = aMPSClient.getSMPHostURI ();
    assertEquals (true ? "https://cis.porezna-uprava.hr:8411/EracunMPSCT/"
                       : "https://hroir-dev.westeurope.cloudapp.azure.com/", sSMPHost);
  }
}
