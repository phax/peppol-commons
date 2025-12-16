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

import org.junit.Test;

import com.helger.hredelivery.commons.EHREDeliverySML;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.smpclient.url.SMPDNSResolutionException;

/**
 * Test class {@link HRMPSClientReadOnly}.
 *
 * @author Philip Helger
 */
public final class HRMPSClientReadOnlyTest
{
  @Test
  public void testResolveDemoParticipant () throws SMPDNSResolutionException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9934:99999999994");

    final HRMPSClientReadOnly aMPSCLient = new HRMPSClientReadOnly (aPI, EHREDeliverySML.DEMO);
    final String sSMPHost = aMPSCLient.getSMPHostURI ();
    assertEquals (true ? "https://mpsdemo.moj-eracun.hr/" : "https://cis.porezna-uprava.hr:8411/EracunMPSCT/",
                  sSMPHost);
  }

  @Test
  public void testResolveOpusCapita () throws SMPDNSResolutionException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9934:52424909202");

    final HRMPSClientReadOnly aMPSCLient = new HRMPSClientReadOnly (aPI, EHREDeliverySML.DEMO);
    final String sSMPHost = aMPSCLient.getSMPHostURI ();
    assertEquals ("https://edelivery-croatia-smp.stage.messagesnetwork.com/", sSMPHost);
  }

  @Test
  public void testResolveMarkant () throws SMPDNSResolutionException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9934:29071087912");

    final HRMPSClientReadOnly aMPSCLient = new HRMPSClientReadOnly (aPI, EHREDeliverySML.DEMO);
    final String sSMPHost = aMPSCLient.getSMPHostURI ();
    assertEquals ("https://acc.mps.markant.services/", sSMPHost);
  }

  @Test
  public void testResolveComarch () throws SMPDNSResolutionException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9934:70583020747");

    final HRMPSClientReadOnly aMPSCLient = new HRMPSClientReadOnly (aPI, EHREDeliverySML.DEMO);
    final String sSMPHost = aMPSCLient.getSMPHostURI ();
    assertEquals ("https://edi-trn-hr.edoc-online.com/test/smp/", sSMPHost);
  }

  @Test
  public void testResolveOmniSight () throws SMPDNSResolutionException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9934:00419068787");

    final HRMPSClientReadOnly aMPSCLient = new HRMPSClientReadOnly (aPI, EHREDeliverySML.DEMO);
    final String sSMPHost = aMPSCLient.getSMPHostURI ();
    assertEquals ("http://mps.omnizon.net/", sSMPHost);
  }
}
