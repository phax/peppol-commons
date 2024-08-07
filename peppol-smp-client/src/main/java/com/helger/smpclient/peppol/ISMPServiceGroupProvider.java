/*
 * Copyright (C) 2015-2024 Philip Helger
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
package com.helger.smpclient.peppol;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.peppolid.IParticipantIdentifier;
import com.helger.smpclient.exception.SMPClientException;
import com.helger.xsds.peppol.smp1.ServiceGroupType;

/**
 * Abstract interface to retrieve a Peppol Service Group instance.
 *
 * @author Philip Helger
 * @since 7.0.6
 */
public interface ISMPServiceGroupProvider
{
  /**
   * Returns a service group. A service group references to the service
   * metadata. This is a specification compliant method.
   *
   * @param aServiceGroupID
   *        The ID of the service group to retrieve. May not be
   *        <code>null</code>.
   * @return The service group. Maybe <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @see #getServiceGroupOrNull(IParticipantIdentifier)
   */
  @Nonnull
  ServiceGroupType getServiceGroup (@Nonnull IParticipantIdentifier aServiceGroupID) throws SMPClientException;

  /**
   * Returns a service group. A service group references to the service
   * metadata. This is a specification compliant method.
   *
   * @param aServiceGroupID
   *        The ID of the service group to retrieve. May not be
   *        <code>null</code>.
   * @return The service group. Maybe <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @see #getServiceGroup(IParticipantIdentifier)
   */
  @Nullable
  ServiceGroupType getServiceGroupOrNull (@Nonnull IParticipantIdentifier aServiceGroupID) throws SMPClientException;
}
