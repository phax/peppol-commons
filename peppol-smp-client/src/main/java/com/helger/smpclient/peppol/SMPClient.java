/*
 * Copyright (C) 2015-2021 Philip Helger
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

import java.net.URI;

import javax.annotation.Nonnull;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.http.CHttpHeader;
import com.helger.http.basicauth.BasicAuthClientCredentials;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppolid.CIdentifier;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.simple.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppolid.simple.participant.SimpleParticipantIdentifier;
import com.helger.smpclient.exception.SMPClientBadRequestException;
import com.helger.smpclient.exception.SMPClientException;
import com.helger.smpclient.exception.SMPClientNotFoundException;
import com.helger.smpclient.exception.SMPClientUnauthorizedException;
import com.helger.smpclient.httpclient.SMPHttpResponseHandlerWriteOperations;
import com.helger.smpclient.peppol.marshal.SMPMarshallerServiceGroupType;
import com.helger.smpclient.peppol.marshal.SMPMarshallerServiceMetadataType;
import com.helger.smpclient.url.ISMPURLProvider;
import com.helger.smpclient.url.SMPDNSResolutionException;
import com.helger.xsds.peppol.smp1.RedirectType;
import com.helger.xsds.peppol.smp1.ServiceGroupType;
import com.helger.xsds.peppol.smp1.ServiceInformationType;
import com.helger.xsds.peppol.smp1.ServiceMetadataReferenceCollectionType;
import com.helger.xsds.peppol.smp1.ServiceMetadataType;

/**
 * This class is used for calling the SMP REST interface. This particular class
 * contains the writing methods. It inherits all reading methods from
 * {@link SMPClientReadOnly}.
 *
 * @author Philip Helger
 */
public class SMPClient extends SMPClientReadOnly
{
  private static final Logger LOGGER = LoggerFactory.getLogger (SMPClient.class);

  /**
   * Constructor with SML lookup
   *
   * @param aURLProvider
   *        The URL provider to be used. May not be <code>null</code>.
   * @param aParticipantIdentifier
   *        The participant identifier to be used. Required to build the SMP
   *        access URI.
   * @param aSMLInfo
   *        The SML to be used. Required to build the SMP access URI.
   * @throws SMPDNSResolutionException
   *         if DNS resolution fails
   * @see ISMPURLProvider#getSMPURIOfParticipant(IParticipantIdentifier,
   *      ISMLInfo)
   */
  public SMPClient (@Nonnull final ISMPURLProvider aURLProvider,
                    @Nonnull final IParticipantIdentifier aParticipantIdentifier,
                    @Nonnull final ISMLInfo aSMLInfo) throws SMPDNSResolutionException
  {
    super (aURLProvider, aParticipantIdentifier, aSMLInfo);
  }

  /**
   * Constructor with SML lookup
   *
   * @param aURLProvider
   *        The URL provider to be used. May not be <code>null</code>.
   * @param aParticipantIdentifier
   *        The participant identifier to be used. Required to build the SMP
   *        access URI.
   * @param sSMLZoneName
   *        The SML DNS zone name to be used. Required to build the SMP access
   *        URI. Must end with a trailing dot (".") and may neither be
   *        <code>null</code> nor empty to build a correct URL. May not start
   *        with "http://". Example: <code>sml.peppolcentral.org.</code>
   * @throws SMPDNSResolutionException
   *         if DNS resolution fails
   * @see ISMPURLProvider#getSMPURIOfParticipant(IParticipantIdentifier, String)
   */
  public SMPClient (@Nonnull final ISMPURLProvider aURLProvider,
                    @Nonnull final IParticipantIdentifier aParticipantIdentifier,
                    @Nonnull @Nonempty final String sSMLZoneName) throws SMPDNSResolutionException
  {
    super (aURLProvider, aParticipantIdentifier, sSMLZoneName);
  }

  /**
   * Constructor with a direct SMP URL.<br>
   * Remember: must be HTTP and using port 80 only!
   *
   * @param aSMPHost
   *        The address of the SMP service. Must be port 80 and basic http only
   *        (no https!). Example: http://smpcompany.company.org
   */
  public SMPClient (@Nonnull final URI aSMPHost)
  {
    super (aSMPHost);
  }

  /**
   * Saves a service group. The meta data references should not be set and are
   * not used.
   *
   * @param aServiceGroup
   *        The service group to save. May not be <code>null</code>.
   * @param aCredentials
   *        The user name and password to use as credentials. May not be
   *        <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         The user name or password was not correct.
   * @throws SMPClientNotFoundException
   *         A HTTP Not Found was received. This can happen if the service was
   *         not found.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   */
  public void saveServiceGroup (@Nonnull final ServiceGroupType aServiceGroup,
                                @Nonnull final BasicAuthClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroup, "ServiceGroup");
    ValueEnforcer.notNull (aCredentials, "Credentials");

    final String sBody = new SMPMarshallerServiceGroupType (isXMLSchemaValidation ()).getAsString (aServiceGroup);
    if (sBody == null)
      throw new IllegalArgumentException ("Failed to serialize ServiceGroup: " + aServiceGroup);

    final String sURI = getSMPHostURI () + CIdentifier.getURIPercentEncoded (aServiceGroup.getParticipantIdentifier ());

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("SMPClient saveServiceGroup@" + sURI);

    final HttpPut aRequest = new HttpPut (sURI);
    aRequest.addHeader (CHttpHeader.AUTHORIZATION, aCredentials.getRequestValue ());
    aRequest.setEntity (new StringEntity (sBody, CONTENT_TYPE_TEXT_XML));
    executeGenericRequest (aRequest, new SMPHttpResponseHandlerWriteOperations ());
  }

  /**
   * Saves a service group. The meta data references should not be set and are
   * not used.
   *
   * @param aParticipantID
   *        The participant identifier for which the service group is to save.
   * @param aCredentials
   *        The user name and password to use as credentials.
   * @return The created {@link ServiceGroupType} object.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         The user name or password was not correct.
   * @throws SMPClientNotFoundException
   *         A HTTP Not Found was received. This can happen if the service was
   *         not found.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   */
  @Nonnull
  public ServiceGroupType saveServiceGroup (@Nonnull final IParticipantIdentifier aParticipantID,
                                            @Nonnull final BasicAuthClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aParticipantID, "ParticipantID");
    ValueEnforcer.notNull (aCredentials, "Credentials");

    final ServiceGroupType aServiceGroup = new ServiceGroupType ();
    // Explicit constructor call is needed here!
    aServiceGroup.setParticipantIdentifier (new SimpleParticipantIdentifier (aParticipantID));
    // Mandatory element, but can be empty
    aServiceGroup.setServiceMetadataReferenceCollection (new ServiceMetadataReferenceCollectionType ());
    saveServiceGroup (aServiceGroup, aCredentials);
    return aServiceGroup;
  }

  /**
   * Deletes a service group given by its service group id.
   *
   * @param aServiceGroupID
   *        The service group id of the service group to delete. May not be
   *        <code>null</code>.
   * @param aCredentials
   *        The user name and password to use as credentials. May not be
   *        <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientNotFoundException
   *         The service group id did not exist.
   * @throws SMPClientUnauthorizedException
   *         The user name or password was not correct.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   */
  public void deleteServiceGroup (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                  @Nonnull final BasicAuthClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aCredentials, "Credentials");

    final String sURI = getSMPHostURI () + aServiceGroupID.getURIPercentEncoded ();
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("SMPClient deleteServiceGroup@" + sURI);

    final HttpDelete aRequest = new HttpDelete (sURI);
    aRequest.addHeader (CHttpHeader.AUTHORIZATION, aCredentials.getRequestValue ());
    executeGenericRequest (aRequest, new SMPHttpResponseHandlerWriteOperations ());
  }

  private void _saveServiceInformation (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                        @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                        @Nonnull final ServiceMetadataType aServiceMetadata,
                                        @Nonnull final BasicAuthClientCredentials aCredentials) throws SMPClientException
  {
    final String sBody = new SMPMarshallerServiceMetadataType (isXMLSchemaValidation ()).getAsString (aServiceMetadata);
    if (sBody == null)
      throw new IllegalArgumentException ("Failed to serialize ServiceMetadata: " + aServiceMetadata);

    final String sURI = getSMPHostURI () +
                        aServiceGroupID.getURIPercentEncoded () +
                        "/" +
                        URL_PART_SERVICES +
                        "/" +
                        aDocumentTypeID.getURIPercentEncoded ();
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("SMPClient saveServiceRegistration@" + sURI);

    final HttpPut aRequest = new HttpPut (sURI);
    aRequest.addHeader (CHttpHeader.AUTHORIZATION, aCredentials.getRequestValue ());
    aRequest.setEntity (new StringEntity (sBody, CONTENT_TYPE_TEXT_XML));
    executeGenericRequest (aRequest, new SMPHttpResponseHandlerWriteOperations ());
  }

  /**
   * Saves a service information data object.
   *
   * @param aServiceInformation
   *        The service information object to save. May not be
   *        <code>null</code>.
   * @param aCredentials
   *        The user name and password to use as credentials. May not be
   *        <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         The user name or password was not correct.
   * @throws SMPClientNotFoundException
   *         A HTTP Not Found was received. This can happen if the service was
   *         not found.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   * @see #saveServiceRedirect(IParticipantIdentifier, IDocumentTypeIdentifier,
   *      RedirectType, BasicAuthClientCredentials)
   */
  public void saveServiceInformation (@Nonnull final ServiceInformationType aServiceInformation,
                                      @Nonnull final BasicAuthClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceInformation, "ServiceMetadata.ServiceInformation");
    ValueEnforcer.notNull (aServiceInformation.getParticipantIdentifier (), "ServiceMetadata.ServiceInformation.ParticipantIdentifier");
    ValueEnforcer.notNull (aServiceInformation.getDocumentIdentifier (), "ServiceMetadata.ServiceInformation.DocumentIdentifier");
    ValueEnforcer.notNull (aCredentials, "Credentials");

    final ServiceMetadataType aServiceMetadata = new ServiceMetadataType ();
    aServiceMetadata.setServiceInformation (aServiceInformation);
    _saveServiceInformation (SimpleParticipantIdentifier.wrap (aServiceInformation.getParticipantIdentifier ()),
                             SimpleDocumentTypeIdentifier.wrap (aServiceInformation.getDocumentIdentifier ()),
                             aServiceMetadata,
                             aCredentials);
  }

  /**
   * Saves a redirect data object.
   *
   * @param aServiceGroupID
   *        The service group ID to use. May not be <code>null</code>.
   * @param aDocumentTypeID
   *        The document type ID to use. May not be <code>null</code>.
   * @param aRedirect
   *        The redirect to be saved. May not be <code>null</code>.
   * @param aCredentials
   *        The user name and password to use as credentials. May not be
   *        <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         The user name or password was not correct.
   * @throws SMPClientNotFoundException
   *         A HTTP Not Found was received. This can happen if the service was
   *         not found.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   * @see #saveServiceInformation(ServiceInformationType,
   *      BasicAuthClientCredentials)
   */
  public void saveServiceRedirect (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                   @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                   @Nonnull final RedirectType aRedirect,
                                   @Nonnull final BasicAuthClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");
    ValueEnforcer.notNull (aDocumentTypeID, "DocumentTypeID");
    ValueEnforcer.notNull (aRedirect, "Redirect");
    ValueEnforcer.notNull (aCredentials, "Credentials");

    final ServiceMetadataType aServiceMetadata = new ServiceMetadataType ();
    aServiceMetadata.setRedirect (aRedirect);
    _saveServiceInformation (aServiceGroupID, aDocumentTypeID, aServiceMetadata, aCredentials);
  }

  /**
   * Deletes a service meta data object given by its service group id and its
   * document type.
   *
   * @param aServiceGroupID
   *        The service group id of the service meta data to delete. May not be
   *        <code>null</code>.
   * @param aDocumentTypeID
   *        The document type of the service meta data to delete. May not be
   *        <code>null</code>.
   * @param aCredentials
   *        The user name and password to use as credentials. May not be
   *        <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         The user name or password was not correct.
   * @throws SMPClientNotFoundException
   *         The service meta data object did not exist.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   */
  public void deleteServiceRegistration (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                         @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                         @Nonnull final BasicAuthClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");
    ValueEnforcer.notNull (aDocumentTypeID, "DocumentTypeID");
    ValueEnforcer.notNull (aCredentials, "Credentials");

    final String sURI = getSMPHostURI () +
                        aServiceGroupID.getURIPercentEncoded () +
                        "/" +
                        URL_PART_SERVICES +
                        "/" +
                        aDocumentTypeID.getURIPercentEncoded ();
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("SMPClient deleteServiceRegistration@" + sURI);

    final HttpDelete aRequest = new HttpDelete (sURI);
    aRequest.addHeader (CHttpHeader.AUTHORIZATION, aCredentials.getRequestValue ());
    executeGenericRequest (aRequest, new SMPHttpResponseHandlerWriteOperations ());
  }
}
