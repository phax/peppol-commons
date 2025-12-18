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

import java.net.URI;
import java.security.GeneralSecurityException;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.hredelivery.commons.EHREDeliverySML;
import com.helger.hredelivery.commons.url.HREDeliveryNaptrURLProvider;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.smpclient.bdxr1.BDXRClientReadOnly;
import com.helger.smpclient.url.SMPDNSResolutionException;

/**
 * Special SMP client for HR eDelivery MPS servers.
 *
 * @author Philip Helger
 * @since 12.1.1
 */
public class HRMPSClientReadOnly extends BDXRClientReadOnly
{
  private static final Logger LOGGER = LoggerFactory.getLogger (HRMPSClientReadOnly.class);

  private void _init ()
  {
    // Make sure to disable SSL server certificate checking
    try
    {
      httpClientSettings ().setSSLContextTrustAll ();
    }
    catch (final GeneralSecurityException ex)
    {
      LOGGER.error ("Error trusting all TLS server certificates", ex);
    }
  }

  public HRMPSClientReadOnly (@NonNull final IParticipantIdentifier aParticipantIdentifier,
                              @NonNull final EHREDeliverySML aSMLInfo) throws SMPDNSResolutionException
  {
    // Constant URL provider
    super (HREDeliveryNaptrURLProvider.INSTANCE, aParticipantIdentifier, aSMLInfo);
    _init ();
  }

  public HRMPSClientReadOnly (@NonNull final URI aSMPHost)
  {
    super (aSMPHost);
    _init ();
  }
}
