/*
 * Copyright (C) 2025-2026 Philip Helger
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
package com.helger.hredelivery.commons.url;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.helger.hredelivery.commons.EHREDeliverySML;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.smpclient.url.IBDXLURLProvider;
import com.helger.smpclient.url.SMPDNSResolutionException;

/**
 * Test class {@link HREDeliveryNaptrURLProvider}.
 *
 * @author Philip Helger
 */
public final class HREDeliveryNaptrURLProviderTest
{
  @Test
  public void testResolveNameDemo () throws SMPDNSResolutionException
  {
    final IBDXLURLProvider aURLProvider = HREDeliveryNaptrURLProvider.INSTANCE;
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9934:18683136487");
    final String sDomain = aURLProvider.getDNSNameOfParticipant (aPI, EHREDeliverySML.DEMO);
    assertEquals ("yktnekuybobtefs3pxs5gbbgk24grlivhxrz52ayvi7pi34siagq.iso6523-actorid-upis.demo.ams.porezna-uprava.hr",
                  sDomain);
  }

  @Test
  public void testResolveNameC1 () throws SMPDNSResolutionException
  {
    final IBDXLURLProvider aURLProvider = HREDeliveryNaptrURLProvider.INSTANCE;
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9934:38441291513");
    final String sDomain = aURLProvider.getDNSNameOfParticipant (aPI, EHREDeliverySML.DEMO);
    assertEquals ("fltznzcpekj5jacdbfpz4ld52nl6p37p4hm5x36shdbyvehtvr7a.iso6523-actorid-upis.demo.ams.porezna-uprava.hr",
                  sDomain);
  }

  @Test
  public void testResolveName3 () throws SMPDNSResolutionException
  {
    final IBDXLURLProvider aURLProvider = HREDeliveryNaptrURLProvider.INSTANCE;
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9934:00042644647");
    final String sDomain = aURLProvider.getDNSNameOfParticipant (aPI, EHREDeliverySML.DEMO);
    assertEquals ("647nqonsxbkn3gwsahjvc3kzwdksoj4kv7pqwf3kwwaln2udwo4q.iso6523-actorid-upis.demo.ams.porezna-uprava.hr",
                  sDomain);
  }

  @Test
  public void testResolveName4 () throws SMPDNSResolutionException
  {
    final IBDXLURLProvider aURLProvider = HREDeliveryNaptrURLProvider.INSTANCE;
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9934:52424909202");
    final String sDomain = aURLProvider.getDNSNameOfParticipant (aPI, EHREDeliverySML.DEMO);
    assertEquals ("q75pqiuxok4euroih6iwkvprpl2hm5pzivpnrwtuwfhcklc7byxq.iso6523-actorid-upis.demo.ams.porezna-uprava.hr",
                  sDomain);
  }
}
