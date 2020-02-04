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
package com.helger.smpclient.bdxr1;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.peppol.smp.ISMPTransportProfile;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.security.certificate.CertificateHelper;
import com.helger.smpclient.exception.SMPClientBadRequestException;
import com.helger.smpclient.exception.SMPClientException;
import com.helger.smpclient.exception.SMPClientUnauthorizedException;
import com.helger.xsds.bdxr.smp1.EndpointType;
import com.helger.xsds.bdxr.smp1.SignedServiceMetadataType;

/**
 * Abstract interface to retrieve a service metadata instance.
 *
 * @author Philip Helger
 * @since 7.0.6
 */
public interface IBDXRServiceMetadataProvider
{
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
   */
  @Nullable
  SignedServiceMetadataType getServiceMetadataOrNull (@Nonnull IParticipantIdentifier aServiceGroupID,
                                                      @Nonnull IDocumentTypeIdentifier aDocumentTypeID) throws SMPClientException;

  /**
   * Retrieve the service metadata from the provided service group ID and
   * document type ID. Than find the matching endpoint from the process ID and
   * transport profile.<br>
   * This is a specification compliant method.
   *
   * @param aServiceGroupID
   *        The service group id of the service metadata to get. May not be
   *        <code>null</code>.
   * @param aDocumentTypeID
   *        The document type of the service metadata to get. May not be
   *        <code>null</code>.
   * @param aProcessID
   *        The process ID of the service metadata to get. May not be
   *        <code>null</code>.
   * @param aTransportProfile
   *        The transport profile of the service metadata to get. May not be
   *        <code>null</code>.
   * @return The endpoint from the signed service metadata object or
   *         <code>null</code> if no such registration is present.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   * @see #getServiceMetadataOrNull(IParticipantIdentifier,IDocumentTypeIdentifier)
   */
  @Nullable
  default EndpointType getEndpoint (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                    @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                    @Nonnull final IProcessIdentifier aProcessID,
                                    @Nonnull final ISMPTransportProfile aTransportProfile) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");
    ValueEnforcer.notNull (aDocumentTypeID, "DocumentTypeID");
    ValueEnforcer.notNull (aProcessID, "ProcessID");
    ValueEnforcer.notNull (aTransportProfile, "TransportProfile");

    // Get meta data for participant/documentType
    final SignedServiceMetadataType aSignedServiceMetadata = getServiceMetadataOrNull (aServiceGroupID,
                                                                                       aDocumentTypeID);
    return aSignedServiceMetadata == null ? null
                                          : BDXRClientReadOnly.getEndpoint (aSignedServiceMetadata,
                                                                            aProcessID,
                                                                            aTransportProfile);
  }

  /**
   * Get the endpoint address URI from the specified endpoint.<br>
   * This is a specification compliant method.
   *
   * @param aServiceGroupID
   *        Service group ID. May not be <code>null</code>.
   * @param aDocumentTypeID
   *        Document type ID. May not be <code>null</code>.
   * @param aProcessID
   *        Process ID. May not be <code>null</code>.
   * @param aTransportProfile
   *        Transport profile. May not be <code>null</code>.
   * @return <code>null</code> if no such endpoint exists, or if the endpoint
   *         has no endpoint address URI
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   */
  @Nullable
  default String getEndpointAddress (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                     @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                     @Nonnull final IProcessIdentifier aProcessID,
                                     @Nonnull final ISMPTransportProfile aTransportProfile) throws SMPClientException
  {
    final EndpointType aEndpoint = getEndpoint (aServiceGroupID, aDocumentTypeID, aProcessID, aTransportProfile);
    return BDXRClientReadOnly.getEndpointAddress (aEndpoint);
  }

  /**
   * Get the certificate bytes from the specified endpoint.
   *
   * @param aServiceGroupID
   *        Service group ID. May not be <code>null</code>.
   * @param aDocumentTypeID
   *        Document type ID. May not be <code>null</code>.
   * @param aProcessID
   *        Process ID. May not be <code>null</code>.
   * @param aTransportProfile
   *        Transport profile. May not be <code>null</code>.
   * @return <code>null</code> if no such endpoint exists, or if the endpoint
   *         has no certificate
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   */
  @Nullable
  default byte [] getEndpointCertificateBytes (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                               @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                               @Nonnull final IProcessIdentifier aProcessID,
                                               @Nonnull final ISMPTransportProfile aTransportProfile) throws SMPClientException
  {
    final EndpointType aEndpoint = getEndpoint (aServiceGroupID, aDocumentTypeID, aProcessID, aTransportProfile);
    return BDXRClientReadOnly.getEndpointCertificateBytes (aEndpoint);
  }

  /**
   * Get the certificate from the specified endpoint.
   *
   * @param aServiceGroupID
   *        Service group ID. May not be <code>null</code>.
   * @param aDocumentTypeID
   *        Document type ID. May not be <code>null</code>.
   * @param aProcessID
   *        Process ID. May not be <code>null</code>.
   * @param aTransportProfile
   *        Transport profile. May not be <code>null</code>.
   * @return <code>null</code> if no such endpoint exists, or if the endpoint
   *         has no certificate
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   * @throws CertificateException
   *         In case the conversion from byte to X509 certificate failed
   */
  @Nullable
  default X509Certificate getEndpointCertificate (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                                  @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                                  @Nonnull final IProcessIdentifier aProcessID,
                                                  @Nonnull final ISMPTransportProfile aTransportProfile) throws SMPClientException,
                                                                                                         CertificateException
  {
    final byte [] aCertBytes = getEndpointCertificateBytes (aServiceGroupID,
                                                            aDocumentTypeID,
                                                            aProcessID,
                                                            aTransportProfile);
    return CertificateHelper.convertByteArrayToCertficateDirect (aCertBytes);
  }
}
