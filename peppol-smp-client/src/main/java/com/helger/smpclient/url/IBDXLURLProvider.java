/**
 * Copyright (C) 2015-2020 Philip Helger
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppolid.IParticipantIdentifier;

/**
 * BDXP URL provider. Layout:
 * <code>strip-trailing(base32(sha256(lowercase(ID-VALUE))),"=")+"."+ID-SCHEME+"."+SML-ZONE-NAME</code>
 *
 * @author Philip Helger
 * @since 6.1.2, reworked in 8.1.7
 */
public interface IBDXLURLProvider extends ISMPURLProvider
{
  /**
   * Get the name of the DNS NAPTR record.
   *
   * @param aParticipantIdentifier
   *        Participant identifier. May not be <code>null</code>.
   * @param sSMLZoneName
   *        e.g. <code>sml.peppolcentral.org.</code>. May be empty. If it is not
   *        empty, it must end with a dot!
   * @return DNS record. It does not contain any prefix like
   *         <code>http://</code> or any path suffix. It is the plain DNS host
   *         name.
   * @throws SMPDNSResolutionException
   *         If the URL resolution failed.
   * @throws IllegalArgumentException
   *         In case one argument is invalid
   */
  @Nonnull
  String getDNSNameOfParticipant (@Nonnull IParticipantIdentifier aParticipantIdentifier,
                                  @Nullable String sSMLZoneName) throws SMPDNSResolutionException;

  /**
   * Get the name of the DNS NAPTR record.
   *
   * @param aParticipantIdentifier
   *        Participant identifier. May not be <code>null</code>.
   * @param aSMLInfo
   *        The SML information object to be used. May not be <code>null</code>.
   * @return DNS record. It does not contain any prefix like
   *         <code>http://</code> or any path suffix. It is the plain DNS host
   *         name.
   * @throws SMPDNSResolutionException
   *         If the URL resolution failed.
   */
  @Nonnull
  default String getDNSNameOfParticipant (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                          @Nonnull final ISMLInfo aSMLInfo) throws SMPDNSResolutionException
  {
    ValueEnforcer.notNull (aParticipantIdentifier, "ParticipantIdentifier");
    ValueEnforcer.notNull (aSMLInfo, "SMLInfo");
    return getDNSNameOfParticipant (aParticipantIdentifier, aSMLInfo.getDNSZone ());
  }
}
