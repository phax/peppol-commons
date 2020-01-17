/**
 * Copyright (C) 2015-2020 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.bdxrclient;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.peppol.smpclient.exception.SMPClientException;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.xsds.bdxr.smp1.ServiceGroupType;

/**
 * Abstract interface to retrieve a service group instance.
 *
 * @author Philip Helger
 * @since 7.0.6
 */
public interface IBDXRServiceGroupProvider
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
   */
  @Nullable
  ServiceGroupType getServiceGroupOrNull (@Nonnull IParticipantIdentifier aServiceGroupID) throws SMPClientException;
}
