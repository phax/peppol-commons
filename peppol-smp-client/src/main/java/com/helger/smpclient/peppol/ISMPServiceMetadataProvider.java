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

import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.smpclient.exception.SMPClientException;
import com.helger.xsds.peppol.smp1.SignedServiceMetadataType;

/**
 * Abstract interface to retrieve a Peppol Service Metadata instance.
 *
 * @author Philip Helger
 * @since 7.0.6
 */
public interface ISMPServiceMetadataProvider
{
  /**
   * Gets a signed service metadata object given by its service group id and its
   * document type.<br>
   * This is a specification compliant method.
   *
   * @param aServiceGroupID
   *        The service group id of the service metadata to get. May not be
   *        <code>null</code>.
   * @param aDocumentTypeID
   *        The document type of the service metadata to get. May not be
   *        <code>null</code>.
   * @return A signed service metadata object. Never <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @see #getServiceMetadataOrNull(IParticipantIdentifier,
   *      IDocumentTypeIdentifier)
   * @since 9.5.1 in this interface
   */
  @Nonnull
  SignedServiceMetadataType getServiceMetadata (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                                @Nonnull final IDocumentTypeIdentifier aDocumentTypeID) throws SMPClientException;

  /**
   * Gets a signed service metadata object given by its service group id and its
   * document type.<br>
   * This is a specification compliant method.
   *
   * @param aServiceGroupID
   *        The ID of the service group to query. May not be <code>null</code>.
   * @param aDocumentTypeID
   *        The document type of the service metadata to retrieve. May not be
   *        <code>null</code>.
   * @return A service metadata object or <code>null</code> if no such
   *         registration is present.
   * @throws SMPClientException
   *         in case something goes wrong
   * @see #getServiceMetadata(IParticipantIdentifier, IDocumentTypeIdentifier)
   */
  @Nullable
  SignedServiceMetadataType getServiceMetadataOrNull (@Nonnull IParticipantIdentifier aServiceGroupID,
                                                      @Nonnull IDocumentTypeIdentifier aDocumentTypeID) throws SMPClientException;
}
