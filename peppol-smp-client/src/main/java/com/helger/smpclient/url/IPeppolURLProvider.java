/*
 * Copyright (C) 2015-2026 Philip Helger
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

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.base.enforce.ValueEnforcer;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppolid.IParticipantIdentifier;

/**
 * Base interface for a customizable URL provider so that different URL encoding schemes can be
 * used.
 *
 * @author Philip Helger
 */
public interface IPeppolURLProvider extends ISMPURLProvider
{
  /**
   * Get DNS record from ParticipantIdentifier.<br>
   * Example PEPPOL PI <code>iso6523-actorid-upis::0010:1234</code> using BDX scheme would result in
   * <code>B-&lt;hash over PI-Value&gt;.&lt;PI-Scheme&gt;.&lt;sml-zone-name&gt;</code> . This method
   * ensures that the hash value is created from the UTF-8 lower case value of the identifier. The
   * result string never ends with a dot!
   *
   * @param aParticipantIdentifier
   *        Participant identifier. May not be <code>null</code>.
   * @param sSMLZoneName
   *        e.g. <code>sml.peppolcentral.org.</code>. May be empty. If it is not empty, it must end
   *        with a dot!
   * @return DNS record. It does not contain any prefix like <code>http://</code> or any path
   *         suffix. It is the plain DNS host name. Since version 1.1.4 this method returns the DNS
   *         name without the trailing dot!
   * @throws SMPDNSResolutionException
   *         If the URL resolution failed.
   * @throws IllegalArgumentException
   *         In case one argument is invalid
   */
  @NonNull
  String getDNSNameOfParticipant (@NonNull IParticipantIdentifier aParticipantIdentifier, @Nullable String sSMLZoneName)
                                                                                                                         throws SMPDNSResolutionException;

  /**
   * Get DNS record from ParticipantIdentifier.<br>
   * Example PEPPOL PI <code>iso6523-actorid-upis::0010:1234</code> using BDX scheme would result in
   * <code>B-&lt;hash over PI-Value&gt;.&lt;PI-Scheme&gt;.&lt;sml-zone-name&gt;</code> . This method
   * ensures that the hash value is created from the UTF-8 lower case value of the identifier. The
   * result string never ends with a dot!
   *
   * @param aParticipantIdentifier
   *        Participant identifier. May not be <code>null</code>.
   * @param aSMLInfo
   *        The SML information object to be used. May not be <code>null</code>.
   * @return DNS record
   * @throws SMPDNSResolutionException
   *         If the URL resolution failed.
   */
  @NonNull
  default String getDNSNameOfParticipant (@NonNull final IParticipantIdentifier aParticipantIdentifier,
                                          @NonNull final ISMLInfo aSMLInfo) throws SMPDNSResolutionException
  {
    ValueEnforcer.notNull (aParticipantIdentifier, "ParticipantIdentifier");
    ValueEnforcer.notNull (aSMLInfo, "SMLInfo");
    return getDNSNameOfParticipant (aParticipantIdentifier, aSMLInfo.getDNSZone ());
  }
}
