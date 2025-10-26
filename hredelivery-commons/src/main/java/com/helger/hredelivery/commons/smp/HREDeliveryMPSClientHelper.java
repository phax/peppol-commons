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

import com.helger.annotation.concurrent.Immutable;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.hredelivery.commons.EHREDeliverySML;
import com.helger.hredelivery.commons.security.HREDeliveryTrustStores;
import com.helger.hredelivery.commons.url.HREDeliveryNaptrURLProvider;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.smpclient.bdxr1.BDXRClientReadOnly;
import com.helger.smpclient.url.SMPDNSResolutionException;

import jakarta.annotation.Nonnull;

/**
 * Helper class to create value HR eDelivery MPS/SMP clients.
 *
 * @author Philip Helger
 */
@Immutable
public final class HREDeliveryMPSClientHelper
{
  private HREDeliveryMPSClientHelper ()
  {}

  @Nonnull
  public static BDXRClientReadOnly createForDemo (@Nonnull final IParticipantIdentifier aPI) throws SMPDNSResolutionException
  {
    ValueEnforcer.notNull (aPI, "PI");

    final HREDeliveryNaptrURLProvider aURLProvider = new HREDeliveryNaptrURLProvider ();
    // This is due to a bug in the test identifier
    aURLProvider.setNAPTRServiceName ("Meta:SMP");
    final BDXRClientReadOnly aMPSClient = new BDXRClientReadOnly (aURLProvider, aPI, EHREDeliverySML.DEMO);
    aMPSClient.setTrustStore (HREDeliveryTrustStores.Fina2015.TRUSTSTORE_DEMO);
    return aMPSClient;
  }
}
