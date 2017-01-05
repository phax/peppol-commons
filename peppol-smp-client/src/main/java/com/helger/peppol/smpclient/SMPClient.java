/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Note: some files, that were not part of the original package are currently
 *   licensed under Apache 2.0 license - https://www.apache.org/licenses/LICENSE-2.0
 *   The respective files contain a special class header!
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.smpclient;

import java.net.URI;

import javax.annotation.Nonnull;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.charset.CCharset;
import com.helger.commons.mime.CMimeType;
import com.helger.http.CHTTPHeader;
import com.helger.http.basicauth.BasicAuthClientCredentials;
import com.helger.peppol.httpclient.SMPHttpResponseHandlerWriteOperations;
import com.helger.peppol.identifier.generic.doctype.IDocumentTypeIdentifier;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.identifier.generic.participant.SimpleParticipantIdentifier;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smp.RedirectType;
import com.helger.peppol.smp.ServiceGroupType;
import com.helger.peppol.smp.ServiceInformationType;
import com.helger.peppol.smp.ServiceMetadataType;
import com.helger.peppol.smp.marshal.SMPMarshallerServiceGroupType;
import com.helger.peppol.smp.marshal.SMPMarshallerServiceMetadataType;
import com.helger.peppol.smpclient.exception.SMPClientBadRequestException;
import com.helger.peppol.smpclient.exception.SMPClientException;
import com.helger.peppol.smpclient.exception.SMPClientNotFoundException;
import com.helger.peppol.smpclient.exception.SMPClientUnauthorizedException;
import com.helger.peppol.url.IPeppolURLProvider;

/**
 * This class is used for calling the SMP REST interface. This particular class
 * contains the writing methods. It inherits all reading methods from
 * {@link SMPClientReadOnly}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public class SMPClient extends SMPClientReadOnly
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (SMPClient.class);

  // The default text/xml content type uses iso-8859-1!
  private static final ContentType CONTENT_TYPE_TEXT_XML = ContentType.create (CMimeType.TEXT_XML.getAsString (),
                                                                               CCharset.CHARSET_UTF_8_OBJ);

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
   * @see IPeppolURLProvider#getSMPURIOfParticipant(IParticipantIdentifier,
   *      ISMLInfo)
   */
  public SMPClient (@Nonnull final IPeppolURLProvider aURLProvider,
                    @Nonnull final IParticipantIdentifier aParticipantIdentifier,
                    @Nonnull final ISMLInfo aSMLInfo)
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
   * @see IPeppolURLProvider#getSMPURIOfParticipant(IParticipantIdentifier,
   *      String)
   */
  public SMPClient (@Nonnull final IPeppolURLProvider aURLProvider,
                    @Nonnull final IParticipantIdentifier aParticipantIdentifier,
                    @Nonnull @Nonempty final String sSMLZoneName)
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
   *        The service group to save.
   * @param aCredentials
   *        The user name and password to use as aCredentials.
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

    final String sBody = new SMPMarshallerServiceGroupType ().getAsString (aServiceGroup);
    final String sURI = getSMPHostURI () + aServiceGroup.getParticipantIdentifier ().getURIPercentEncoded ();

    if (s_aLogger.isDebugEnabled ())
      s_aLogger.debug ("SMPClient saveServiceGroup@" + sURI);

    final HttpPut aRequest = new HttpPut (sURI);
    aRequest.addHeader (CHTTPHeader.AUTHORIZATION, aCredentials.getRequestValue ());
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
    saveServiceGroup (aServiceGroup, aCredentials);
    return aServiceGroup;
  }

  /**
   * Deletes a service group given by its service group id.
   *
   * @param aServiceGroupID
   *        The service group id of the service group to delete.
   * @param aCredentials
   *        The user name and password to use as aCredentials.
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
    if (s_aLogger.isDebugEnabled ())
      s_aLogger.debug ("SMPClient deleteServiceGroup@" + sURI);

    final HttpDelete aRequest = new HttpDelete (sURI);
    aRequest.addHeader (CHTTPHeader.AUTHORIZATION, aCredentials.getRequestValue ());
    executeGenericRequest (aRequest, new SMPHttpResponseHandlerWriteOperations ());
  }

  private void _saveServiceInformation (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                        @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                        @Nonnull final ServiceMetadataType aServiceMetadata,
                                        @Nonnull final BasicAuthClientCredentials aCredentials) throws SMPClientException
  {
    final String sBody = new SMPMarshallerServiceMetadataType ().getAsString (aServiceMetadata);

    final String sURI = getSMPHostURI () +
                        aServiceGroupID.getURIPercentEncoded () +
                        "/services/" +
                        aDocumentTypeID.getURIPercentEncoded ();
    if (s_aLogger.isDebugEnabled ())
      s_aLogger.debug ("SMPClient saveServiceRegistration@" + sURI);

    final HttpPut aRequest = new HttpPut (sURI);
    aRequest.addHeader (CHTTPHeader.AUTHORIZATION, aCredentials.getRequestValue ());
    aRequest.setEntity (new StringEntity (sBody, CONTENT_TYPE_TEXT_XML));
    executeGenericRequest (aRequest, new SMPHttpResponseHandlerWriteOperations ());
  }

  /**
   * Saves a service meta data object. This method can only used to save service
   * information objects!
   *
   * @param aServiceMetadata
   *        The service meta data object to save.
   * @param aCredentials
   *        The user name and password to use as aCredentials.
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
   * @see #saveServiceRedirect(IParticipantIdentifier, IDocumentTypeIdentifier,
   *      RedirectType, BasicAuthClientCredentials)
   */
  @Deprecated
  public void saveServiceRegistration (@Nonnull final ServiceMetadataType aServiceMetadata,
                                       @Nonnull final BasicAuthClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceMetadata, "ServiceMetadata");
    final ServiceInformationType aServiceInformation = aServiceMetadata.getServiceInformation ();
    ValueEnforcer.notNull (aServiceInformation, "ServiceMetadata.ServiceInformation");
    ValueEnforcer.notNull (aServiceInformation.getParticipantIdentifier (),
                           "ServiceMetadata.ServiceInformation.ParticipantIdentifier");
    ValueEnforcer.notNull (aServiceInformation.getDocumentIdentifier (),
                           "ServiceMetadata.ServiceInformation.DocumentIdentifier");
    ValueEnforcer.notNull (aCredentials, "Credentials");

    _saveServiceInformation (aServiceInformation.getParticipantIdentifier (),
                             aServiceInformation.getDocumentIdentifier (),
                             aServiceMetadata,
                             aCredentials);
  }

  /**
   * Saves a service information data object.
   *
   * @param aServiceInformation
   *        The service information object to save.
   * @param aCredentials
   *        The user name and password to use as aCredentials.
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
    ValueEnforcer.notNull (aServiceInformation.getParticipantIdentifier (),
                           "ServiceMetadata.ServiceInformation.ParticipantIdentifier");
    ValueEnforcer.notNull (aServiceInformation.getDocumentIdentifier (),
                           "ServiceMetadata.ServiceInformation.DocumentIdentifier");
    ValueEnforcer.notNull (aCredentials, "Credentials");

    final ServiceMetadataType aServiceMetadata = new ServiceMetadataType ();
    aServiceMetadata.setServiceInformation (aServiceInformation);
    _saveServiceInformation (aServiceInformation.getParticipantIdentifier (),
                             aServiceInformation.getDocumentIdentifier (),
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
   *        The user name and password to use as aCredentials.
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
   *        The service group id of the service meta data to delete.
   * @param aDocumentTypeID
   *        The document type of the service meta data to delete.
   * @param aCredentials
   *        The user name and password to use as aCredentials.
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
                        "/services/" +
                        aDocumentTypeID.getURIPercentEncoded ();
    if (s_aLogger.isDebugEnabled ())
      s_aLogger.debug ("SMPClient deleteServiceRegistration@" + sURI);

    final HttpDelete aRequest = new HttpDelete (sURI);
    aRequest.addHeader (CHTTPHeader.AUTHORIZATION, aCredentials.getRequestValue ());
    executeGenericRequest (aRequest, new SMPHttpResponseHandlerWriteOperations ());
  }
}
