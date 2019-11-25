/**
 * Copyright (C) 2015-2019 Philip Helger
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
package com.helger.peppol.url;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.ICommonsMap;
import com.helger.peppolid.IParticipantIdentifier;

/**
 * BDXP URL provider. Layout:
 * <code>strip-trailing(base32(sha256(lowercase(ID-VALUE))),"=")+"."+ID-SCHEME+"."+SML-ZONE-NAME</code>
 *
 * @author Philip Helger
 * @since 6.1.2
 */
public interface IBDXLURLProvider extends IPeppolURLProvider
{
  /**
   * @return <code>true</code> if value is lower cases before the value is
   *         hashed.
   */
  boolean isLowercaseValueBeforeHashing ();

  /**
   * @return <code>true</code> if internal DNS caching is enabled,
   *         <code>false</code> if not. By default it is enabled.
   */
  boolean isUseDNSCache ();

  /**
   * @return A copy of all entries currently in the cache. Never
   *         <code>null</code> but maybe empty.
   */
  @Nonnull
  @ReturnsMutableCopy
  ICommonsMap <String, String> getAllDNSCacheEntries ();

  @Nonnull
  default String getDNSNameOfParticipant (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                          @Nullable final String sSMLZoneName) throws PeppolDNSResolutionException
  {
    return getDNSNameOfParticipant (aParticipantIdentifier, sSMLZoneName, true);
  }

  @Nonnull
  default String getDNSNameOfParticipant (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                          @Nullable final String sSMLZoneName,
                                          final boolean bDoNAPTRResolving) throws PeppolDNSResolutionException
  {
    return getDNSNameOfParticipant (aParticipantIdentifier, sSMLZoneName, bDoNAPTRResolving, null);
  }

  @Nonnull
  String getDNSNameOfParticipant (@Nonnull IParticipantIdentifier aParticipantIdentifier,
                                  @Nullable String sSMLZoneName,
                                  boolean bDoNAPTRResolving,
                                  @Nullable String sPrimaryDNSServer) throws PeppolDNSResolutionException;
}
