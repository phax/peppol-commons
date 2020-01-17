package com.helger.peppol.smpclient;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.peppol.smp.SignedServiceMetadataType;
import com.helger.peppol.smpclient.exception.SMPClientException;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;

/**
 * Abstract interface to retrieve a service metadata instance.
 *
 * @author Philip Helger
 * @since 7.0.6
 */
public interface ISMPServiceMetadataProvider
{
  /**
   * Gets a signed service metadata object given by its service group id and its
   * document type. This is a specification compliant method.
   *
   * @param aServiceGroupID
   *        The service group id of the service metadata to get. May not be
   *        <code>null</code>.
   * @param aDocumentTypeID
   *        The document type of the service metadata to get. May not be
   *        <code>null</code>.
   * @return A signed service metadata object or <code>null</code> if no such
   *         registration is present.
   * @throws SMPClientException
   *         in case something goes wrong
   */
  @Nullable
  SignedServiceMetadataType getServiceMetadataOrNull (@Nonnull IParticipantIdentifier aServiceGroupID,
                                                      @Nonnull IDocumentTypeIdentifier aDocumentTypeID) throws SMPClientException;
}
