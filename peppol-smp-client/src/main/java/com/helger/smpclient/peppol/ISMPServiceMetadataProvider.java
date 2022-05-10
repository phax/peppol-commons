/*
 * Copyright (C) 2015-2022 Philip Helger
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

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.datetime.PDTFactory;
import com.helger.peppol.smp.ISMPTransportProfile;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.security.certificate.CertificateHelper;
import com.helger.smpclient.exception.SMPClientBadRequestException;
import com.helger.smpclient.exception.SMPClientException;
import com.helger.smpclient.exception.SMPClientUnauthorizedException;
import com.helger.xsds.peppol.smp1.EndpointType;
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
   * transport profile. This method checks the validity of the endpoint at the
   * current point in time.<br>
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
    return getEndpointAt (aServiceGroupID,
                          aDocumentTypeID,
                          aProcessID,
                          aTransportProfile,
                          PDTFactory.getCurrentLocalDateTime ());
  }

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
   * @param aCheckDT
   *        The date and time for when the endpoint is meant to be valid if the
   *        end point contains a ServiceActivationDate and/or a
   *        ServiceExpirationDate. May not be <code>null</code>.
   * @return The endpoint from the signed service metadata object or
   *         <code>null</code> if no such registration is present.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   * @see #getServiceMetadataOrNull(IParticipantIdentifier,IDocumentTypeIdentifier)
   * @since 8.7.3
   */
  @Nullable
  default EndpointType getEndpointAt (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                      @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                      @Nonnull final IProcessIdentifier aProcessID,
                                      @Nonnull final ISMPTransportProfile aTransportProfile,
                                      @Nonnull final LocalDateTime aCheckDT) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");
    ValueEnforcer.notNull (aDocumentTypeID, "DocumentTypeID");
    ValueEnforcer.notNull (aProcessID, "ProcessID");
    ValueEnforcer.notNull (aTransportProfile, "TransportProfile");
    ValueEnforcer.notNull (aCheckDT, "CheckDT");

    // Get meta data for participant/documentType
    final SignedServiceMetadataType aSignedServiceMetadata = getServiceMetadataOrNull (aServiceGroupID,
                                                                                       aDocumentTypeID);
    return aSignedServiceMetadata == null ? null
                                          : SMPClientReadOnly.getEndpointAt (aSignedServiceMetadata,
                                                                             aProcessID,
                                                                             aTransportProfile,
                                                                             aCheckDT);
  }

  /**
   * Get the endpoint address URI from the specified endpoint. This method
   * checks the validity of the endpoint at the current point in time.
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
    return SMPClientReadOnly.getEndpointAddress (aEndpoint);
  }

  /**
   * Get the endpoint address URI from the specified endpoint.
   *
   * @param aServiceGroupID
   *        Service group ID. May not be <code>null</code>.
   * @param aDocumentTypeID
   *        Document type ID. May not be <code>null</code>.
   * @param aProcessID
   *        Process ID. May not be <code>null</code>.
   * @param aTransportProfile
   *        Transport profile. May not be <code>null</code>.
   * @param aCheckDT
   *        The date and time for when the endpoint is meant to be valid if the
   *        end point contains a ServiceActivationDate and/or a
   *        ServiceExpirationDate. May not be <code>null</code>.
   * @return <code>null</code> if no such endpoint exists, or if the endpoint
   *         has no endpoint address URI
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   * @since 8.7.3
   */
  @Nullable
  default String getEndpointAddressAt (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                       @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                       @Nonnull final IProcessIdentifier aProcessID,
                                       @Nonnull final ISMPTransportProfile aTransportProfile,
                                       @Nonnull final LocalDateTime aCheckDT) throws SMPClientException
  {
    final EndpointType aEndpoint = getEndpointAt (aServiceGroupID,
                                                  aDocumentTypeID,
                                                  aProcessID,
                                                  aTransportProfile,
                                                  aCheckDT);
    return SMPClientReadOnly.getEndpointAddress (aEndpoint);
  }

  /**
   * Get the certificate string from the specified endpoint. This method checks
   * the validity of the endpoint at the current point in time.
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
  default String getEndpointCertificateString (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                               @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                               @Nonnull final IProcessIdentifier aProcessID,
                                               @Nonnull final ISMPTransportProfile aTransportProfile) throws SMPClientException
  {
    final EndpointType aEndpoint = getEndpoint (aServiceGroupID, aDocumentTypeID, aProcessID, aTransportProfile);
    return SMPClientReadOnly.getEndpointCertificateString (aEndpoint);
  }

  /**
   * Get the certificate string from the specified endpoint.
   *
   * @param aServiceGroupID
   *        Service group ID. May not be <code>null</code>.
   * @param aDocumentTypeID
   *        Document type ID. May not be <code>null</code>.
   * @param aProcessID
   *        Process ID. May not be <code>null</code>.
   * @param aTransportProfile
   *        Transport profile. May not be <code>null</code>.
   * @param aCheckDT
   *        The date and time for when the endpoint is meant to be valid if the
   *        end point contains a ServiceActivationDate and/or a
   *        ServiceExpirationDate. May not be <code>null</code>.
   * @return <code>null</code> if no such endpoint exists, or if the endpoint
   *         has no certificate
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   * @since 8.7.3
   */
  @Nullable
  default String getEndpointCertificateStringAt (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                                 @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                                 @Nonnull final IProcessIdentifier aProcessID,
                                                 @Nonnull final ISMPTransportProfile aTransportProfile,
                                                 @Nonnull final LocalDateTime aCheckDT) throws SMPClientException
  {
    final EndpointType aEndpoint = getEndpointAt (aServiceGroupID,
                                                  aDocumentTypeID,
                                                  aProcessID,
                                                  aTransportProfile,
                                                  aCheckDT);
    return SMPClientReadOnly.getEndpointCertificateString (aEndpoint);
  }

  /**
   * Get the certificate from the specified endpoint. This method checks the
   * validity of the endpoint at the current point in time.
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
    final String sCertString = getEndpointCertificateString (aServiceGroupID,
                                                             aDocumentTypeID,
                                                             aProcessID,
                                                             aTransportProfile);
    return CertificateHelper.convertStringToCertficate (sCertString);
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
   * @param aCheckDT
   *        The date and time for when the endpoint is meant to be valid if the
   *        end point contains a ServiceActivationDate and/or a
   *        ServiceExpirationDate. May not be <code>null</code>.
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
  default X509Certificate getEndpointCertificateAt (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                                    @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                                    @Nonnull final IProcessIdentifier aProcessID,
                                                    @Nonnull final ISMPTransportProfile aTransportProfile,
                                                    @Nonnull final LocalDateTime aCheckDT) throws SMPClientException,
                                                                                           CertificateException
  {
    final String sCertString = getEndpointCertificateStringAt (aServiceGroupID,
                                                               aDocumentTypeID,
                                                               aProcessID,
                                                               aTransportProfile,
                                                               aCheckDT);
    return CertificateHelper.convertStringToCertficate (sCertString);
  }
}
