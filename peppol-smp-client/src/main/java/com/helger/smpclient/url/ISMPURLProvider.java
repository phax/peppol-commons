/*
 * Copyright (C) 2015-2021 Philip Helger
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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppolid.IParticipantIdentifier;

/**
 * Base interface for a customizable SMP URL provider.
 *
 * @author Philip Helger
 * @since 8.1.7
 */
public interface ISMPURLProvider
{
  /**
   * Get the SMP URI of the passed participant ID in the provided SML DNS zone
   * name.
   *
   * @param aParticipantIdentifier
   *        The participant ID. May not be <code>null</code>.
   * @param sSMLZoneName
   *        The SML zone to use. May be <code>null</code>.
   * @return A new URI starting with "http://" and never ending with a slash.
   * @throws SMPDNSResolutionException
   *         If the URL resolution failed.
   * @see #getSMPURIOfParticipant(IParticipantIdentifier, ISMLInfo)
   * @see #getSMPURLOfParticipant(IParticipantIdentifier, ISMLInfo)
   * @see #getSMPURLOfParticipant(IParticipantIdentifier, String)
   */
  @Nonnull
  URI getSMPURIOfParticipant (@Nonnull IParticipantIdentifier aParticipantIdentifier,
                              @Nullable String sSMLZoneName) throws SMPDNSResolutionException;

  /**
   * Get the SMP URI of the passed participant ID in the provided SML DNS zone
   * name.
   *
   * @param aParticipantIdentifier
   *        The participant ID. May not be <code>null</code>.
   * @param aSMLInfo
   *        The SML zone to use. May not be <code>null</code>.
   * @return A new URI starting with "http://" and never ending with a slash.
   * @throws SMPDNSResolutionException
   *         If the URL resolution failed.
   * @see #getSMPURIOfParticipant(IParticipantIdentifier, String)
   * @see #getSMPURLOfParticipant(IParticipantIdentifier, ISMLInfo)
   * @see #getSMPURLOfParticipant(IParticipantIdentifier, String)
   */
  @Nonnull
  default URI getSMPURIOfParticipant (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                      @Nonnull final ISMLInfo aSMLInfo) throws SMPDNSResolutionException
  {
    ValueEnforcer.notNull (aParticipantIdentifier, "ParticipantIdentifier");
    ValueEnforcer.notNull (aSMLInfo, "SMLInfo");
    return getSMPURIOfParticipant (aParticipantIdentifier, aSMLInfo.getDNSZone ());
  }

  /**
   * Get the SMP URL of the passed participant ID in the provided SML DNS zone
   * name.
   *
   * @param aParticipantIdentifier
   *        The participant ID. May not be <code>null</code>.
   * @param aSMLInfo
   *        The SML zone to use. May not be <code>null</code>.
   * @return A new URL with scheme "http:" and never ending with a slash.
   * @throws SMPDNSResolutionException
   *         If the URL resolution failed.
   * @see #getSMPURIOfParticipant(IParticipantIdentifier, String)
   * @see #getSMPURIOfParticipant(IParticipantIdentifier, ISMLInfo)
   * @see #getSMPURLOfParticipant(IParticipantIdentifier, String)
   */
  @Nonnull
  default URL getSMPURLOfParticipant (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                      @Nonnull final ISMLInfo aSMLInfo) throws SMPDNSResolutionException
  {
    ValueEnforcer.notNull (aParticipantIdentifier, "ParticipantIdentifier");
    ValueEnforcer.notNull (aSMLInfo, "SMLInfo");

    return getSMPURLOfParticipant (aParticipantIdentifier, aSMLInfo.getDNSZone ());
  }

  /**
   * Get the SMP URL of the passed participant ID in the provided SML DNS zone
   * name.
   *
   * @param aParticipantIdentifier
   *        The participant ID. May not be <code>null</code>.
   * @param sSMLZoneName
   *        The SML zone name to use. May be <code>null</code>.
   * @return A new URL with scheme "http:" and never ending with a slash.
   * @throws SMPDNSResolutionException
   *         If the URL resolution failed.
   * @see #getSMPURIOfParticipant(IParticipantIdentifier, String)
   * @see #getSMPURIOfParticipant(IParticipantIdentifier, ISMLInfo)
   * @see #getSMPURLOfParticipant(IParticipantIdentifier, ISMLInfo)
   */
  @Nonnull
  default URL getSMPURLOfParticipant (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                      @Nullable final String sSMLZoneName) throws SMPDNSResolutionException
  {
    ValueEnforcer.notNull (aParticipantIdentifier, "ParticipantIdentifier");

    final URI aURI = getSMPURIOfParticipant (aParticipantIdentifier, sSMLZoneName);
    try
    {
      return aURI.toURL ();
    }
    catch (final MalformedURLException ex)
    {
      throw new IllegalArgumentException ("Error building SMP URL from URI: " + aURI, ex);
    }
  }
}
