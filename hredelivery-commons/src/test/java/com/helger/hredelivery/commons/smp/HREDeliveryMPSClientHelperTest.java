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

import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.smpclient.bdxr1.BDXRClientReadOnly;
import com.helger.smpclient.url.SMPDNSResolutionException;

/**
 * Test class {@link HREDeliveryMPSClientHelper}.
 *
 * @author Philip Helger
 */
public final class HREDeliveryMPSClientHelperTest
{
  @Test
  public void testResolveSMPDemo () throws SMPDNSResolutionException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9934:18683136487");

    final BDXRClientReadOnly aMPSCLient = HREDeliveryMPSClientHelper.createForDemo (aPI);
    final String sSMPHost = aMPSCLient.getSMPHostURI ();
    assertEquals ("https://mpsdemo.moj-eracun.hr/", sSMPHost);
  }
}
