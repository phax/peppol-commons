package com.helger.peppol.smpclient;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.peppol.smp.ServiceGroupType;
import com.helger.peppol.smpclient.exception.SMPClientException;
import com.helger.peppolid.IParticipantIdentifier;

/**
 * Abstract interface to retrieve a service group instance.
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
   *        The service group id corresponding to the service group which one
   *        wants to get.
   * @return The service group. Never <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   */
  @Nullable
  ServiceGroupType getServiceGroupOrNull (@Nonnull IParticipantIdentifier aServiceGroupID) throws SMPClientException;
}
