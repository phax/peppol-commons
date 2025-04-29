/*
 * Copyright (C) 2015-2025 Philip Helger
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
package com.helger.smpclient.url;

import java.net.URI;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppolid.IParticipantIdentifier;

/**
 * A configurable URL provider for Peppol that allows switching between the old CNAME and the new
 * NAPTR based DNS lookup.
 *
 * @author Philip Helger
 * @since 10.3.2
 */
public class PeppolConfigurableURLProvider implements IPeppolURLProvider
{
  /** The default instance that should be used */
  public static final IPeppolURLProvider INSTANCE = new PeppolConfigurableURLProvider ();

  public static final boolean DEFAULT_USE_NATPR = true;

  // Change this to false to use the old CNAME based lookup
  public static final AtomicBoolean USE_NATPR = new AtomicBoolean (DEFAULT_USE_NATPR);

  @Nonnull
  @SuppressWarnings ("removal")
  private static IPeppolURLProvider _getRealProvider ()
  {
    return USE_NATPR.get () ? PeppolNaptrURLProvider.INSTANCE : PeppolURLProvider.INSTANCE;
  }

  @Nonnull
  public String getDNSNameOfParticipant (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                         @Nullable final String sSMLZoneName) throws SMPDNSResolutionException
  {
    return _getRealProvider ().getDNSNameOfParticipant (aParticipantIdentifier, sSMLZoneName);
  }

  @Nonnull
  public String getDNSNameOfParticipant (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                         @Nonnull final ISMLInfo aSMLInfo) throws SMPDNSResolutionException
  {
    return _getRealProvider ().getDNSNameOfParticipant (aParticipantIdentifier, aSMLInfo);
  }

  @Nonnull
  public URI getSMPURIOfParticipant (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                     @Nullable final String sSMLZoneName) throws SMPDNSResolutionException
  {
    return _getRealProvider ().getSMPURIOfParticipant (aParticipantIdentifier, sSMLZoneName);
  }
}
