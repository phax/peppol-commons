/*
 * Copyright (C) 2015-2026 Philip Helger (www.helger.com)
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
package com.helger.smpclient.phosssmp;

import java.nio.charset.StandardCharsets;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.w3c.dom.Document;

import com.helger.annotation.Nonempty;
import com.helger.annotation.Nonnegative;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.state.ESuccess;
import com.helger.base.string.StringHelper;
import com.helger.base.string.StringParser;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.ICommonsList;
import com.helger.http.CHttpHeader;
import com.helger.http.IHttpClientCredentials;
import com.helger.mime.CMimeType;
import com.helger.peppol.businesscard.v3.PD3BusinessCardMarshaller;
import com.helger.peppol.businesscard.v3.PD3BusinessCardType;
import com.helger.peppolid.CIdentifier;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.smpclient.exception.SMPClientBadRequestException;
import com.helger.smpclient.exception.SMPClientBadResponseException;
import com.helger.smpclient.exception.SMPClientException;
import com.helger.smpclient.exception.SMPClientNotFoundException;
import com.helger.smpclient.exception.SMPClientUnauthorizedException;
import com.helger.smpclient.httpclient.AbstractGenericSMPClient;
import com.helger.smpclient.httpclient.SMPHttpResponseHandlerByteArray;
import com.helger.smpclient.httpclient.SMPHttpResponseHandlerWriteOperations;
import com.helger.xml.microdom.IMicroDocument;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.convert.MicroTypeConverter;
import com.helger.xml.microdom.serialize.MicroReader;
import com.helger.xml.microdom.serialize.MicroWriter;
import com.helger.xml.microdom.util.MicroHelper;
import com.helger.xml.serialize.read.DOMReader;

/**
 * This interface contains all the phoss SMP specific REST APIs that are independent of the used SMP
 * data format (Peppol SMP, OASIS BDXR SMP v1 or OASIS BDXR SMP v2). It is implemented by the phoss
 * SMP specific clients. All of these APIs are NOT part of any SMP specification and only work with
 * phoss SMP.
 *
 * @author Philip Helger
 * @param <ENDPOINTTYPE>
 *        The data format specific Endpoint type
 * @since 13.2.0
 */
public interface IPhossSmpClient <ENDPOINTTYPE>
{
  /**
   * @return The SMP host URI string we're operating on. Never <code>null</code>. Always has a
   *         trailing "/".
   * @see AbstractGenericSMPClient#getSMPHostURI()
   */
  @NonNull
  @Nonempty
  String getSMPHostURI ();

  /**
   * Execute a generic request on the SMP, including the conversion of Exceptions to
   * {@link SMPClientException} objects.
   *
   * @param aRequest
   *        The request to be executed. May not be <code>null</code>.
   * @param aResponseHandler
   *        The response handler to be used. May not be <code>null</code>.
   * @return The return value of the response handler.
   * @throws SMPClientException
   *         One of the converted exceptions
   * @param <T>
   *        Expected response type
   * @see AbstractGenericSMPClient#executeGenericRequest(HttpUriRequestBase,
   *      HttpClientResponseHandler)
   */
  <T> T executeGenericRequest (@NonNull HttpUriRequestBase aRequest,
                               @NonNull HttpClientResponseHandler <T> aResponseHandler) throws SMPClientException;

  private static void _setCredentials (@NonNull final HttpUriRequestBase aRequest,
                                       @Nullable final IHttpClientCredentials aCredentials)
  {
    if (aCredentials != null)
      aRequest.addHeader (CHttpHeader.AUTHORIZATION, aCredentials.getRequestValue ());
  }

  private void _executeWrite (@NonNull final HttpUriRequestBase aRequest,
                              @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aCredentials, "Credentials");
    _setCredentials (aRequest, aCredentials);
    executeGenericRequest (aRequest, new SMPHttpResponseHandlerWriteOperations ());
  }

  @NonNull
  private byte [] _executeBytes (@NonNull final HttpUriRequestBase aRequest,
                                 @Nullable final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    _setCredentials (aRequest, aCredentials);
    final byte [] ret = executeGenericRequest (aRequest, new SMPHttpResponseHandlerByteArray ());
    if (ret == null)
      throw new SMPClientBadResponseException ("The SMP response to '" + aRequest + "' contains no payload");
    return ret;
  }

  @NonNull
  private String _executeText (@NonNull final HttpUriRequestBase aRequest,
                               @Nullable final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    return new String (_executeBytes (aRequest, aCredentials), StandardCharsets.UTF_8);
  }

  @NonNull
  private IMicroElement _executeMicroXML (@NonNull final HttpUriRequestBase aRequest,
                                          @Nullable final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    final IMicroDocument aDoc = MicroReader.readMicroXML (_executeBytes (aRequest, aCredentials));
    if (aDoc == null || aDoc.getDocumentElement () == null)
      throw new SMPClientBadResponseException ("The SMP response to '" + aRequest + "' is not valid XML");
    return aDoc.getDocumentElement ();
  }

  @NonNull
  private static Document _parseDOM (@NonNull final byte [] aBytes) throws SMPClientException
  {
    final Document ret = DOMReader.readXMLDOM (aBytes);
    if (ret == null)
      throw new SMPClientBadResponseException ("The SMP response is not valid XML");
    return ret;
  }

  @NonNull
  private static String _getEncoded (@NonNull final String s)
  {
    return CIdentifier.createPercentEncoded (s);
  }

  @NonNull
  private String _getServicesURI (@NonNull final IParticipantIdentifier aServiceGroupID)
  {
    return getSMPHostURI () + aServiceGroupID.getURIPercentEncoded () + "/services";
  }

  @NonNull
  private String _getCustomPropertiesURI (@NonNull final IParticipantIdentifier aServiceGroupID)
  {
    return getSMPHostURI () + aServiceGroupID.getURIPercentEncoded () + "/customproperties";
  }

  @NonNull
  private String _getBusinessCardURI (@NonNull final IParticipantIdentifier aServiceGroupID)
  {
    return getSMPHostURI () + "businesscard/" + aServiceGroupID.getURIPercentEncoded ();
  }

  // Service Metadata

  /**
   * Serialize the provided data format specific Endpoint to an XML string, as expected by
   * {@link #addServiceEndpoint(IParticipantIdentifier, IDocumentTypeIdentifier, IProcessIdentifier, Object, IHttpClientCredentials)}.
   *
   * @param aEndpoint
   *        The Endpoint to serialize. May not be <code>null</code>.
   * @return <code>null</code> if serialization failed.
   */
  @Nullable
  String getEndpointAsXMLString (@NonNull ENDPOINTTYPE aEndpoint);

  /**
   * Add a single Endpoint to the referenced Process of the provided Service Group and Document
   * Type. In contrast to saving the whole Service Metadata, all existing Processes and Endpoints
   * are kept. If the Service Metadata or the Process does not exist yet, they are created on the
   * fly. Redirects are not touched by this call.<br>
   * Uses <code>PUT /{ServiceGroupId}/services/{DocumentTypeId}/{ProcessId}</code> (since phoss SMP
   * 8.5.0).
   *
   * @param aServiceGroupID
   *        The Service Group ID to use. May not be <code>null</code>.
   * @param aDocumentTypeID
   *        The Document Type ID to use. May not be <code>null</code>.
   * @param aProcessID
   *        The Process ID the Endpoint belongs to. May not be <code>null</code>.
   * @param aEndpoint
   *        The Endpoint to be added. May not be <code>null</code>.
   * @param aCredentials
   *        The credentials to use (e.g. Basic Auth or Bearer token). May not be <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientBadRequestException
   *         if the Endpoint is invalid, e.g. if the transport profile is missing or the validity
   *         period overlaps with another Endpoint of the same transport profile
   */
  default void addServiceEndpoint (@NonNull final IParticipantIdentifier aServiceGroupID,
                                   @NonNull final IDocumentTypeIdentifier aDocumentTypeID,
                                   @NonNull final IProcessIdentifier aProcessID,
                                   @NonNull final ENDPOINTTYPE aEndpoint,
                                   @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");
    ValueEnforcer.notNull (aDocumentTypeID, "DocumentTypeID");
    ValueEnforcer.notNull (aProcessID, "ProcessID");
    ValueEnforcer.notNull (aEndpoint, "Endpoint");

    final String sBody = getEndpointAsXMLString (aEndpoint);
    if (sBody == null)
      throw new IllegalArgumentException ("Failed to serialize Endpoint: " + aEndpoint);

    final HttpPut aRequest = new HttpPut (_getServicesURI (aServiceGroupID) +
                                          '/' +
                                          aDocumentTypeID.getURIPercentEncoded () +
                                          '/' +
                                          aProcessID.getURIPercentEncoded ());
    aRequest.setEntity (new StringEntity (sBody, AbstractGenericSMPClient.CONTENT_TYPE_TEXT_XML));
    _executeWrite (aRequest, aCredentials);
  }

  /**
   * Delete a single Process (with all its Endpoints) from the Service Metadata of the provided
   * Service Group and Document Type. All other Processes are kept. Redirects are not touched by
   * this call.<br>
   * Uses <code>DELETE /{ServiceGroupId}/services/{DocumentTypeId}/{ProcessId}</code> (since phoss
   * SMP 8.1.8).
   *
   * @param aServiceGroupID
   *        The Service Group ID to use. May not be <code>null</code>.
   * @param aDocumentTypeID
   *        The Document Type ID to use. May not be <code>null</code>.
   * @param aProcessID
   *        The Process ID to delete. May not be <code>null</code>.
   * @param aCredentials
   *        The credentials to use (e.g. Basic Auth or Bearer token). May not be <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientNotFoundException
   *         if the Service Group, the Service Metadata or the Process does not exist
   */
  default void deleteServiceProcess (@NonNull final IParticipantIdentifier aServiceGroupID,
                                     @NonNull final IDocumentTypeIdentifier aDocumentTypeID,
                                     @NonNull final IProcessIdentifier aProcessID,
                                     @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");
    ValueEnforcer.notNull (aDocumentTypeID, "DocumentTypeID");
    ValueEnforcer.notNull (aProcessID, "ProcessID");

    _executeWrite (new HttpDelete (_getServicesURI (aServiceGroupID) +
                                   '/' +
                                   aDocumentTypeID.getURIPercentEncoded () +
                                   '/' +
                                   aProcessID.getURIPercentEncoded ()), aCredentials);
  }

  /**
   * Delete all Endpoints and Redirects of the provided Service Group. The Service Group itself is
   * kept.<br>
   * Uses <code>DELETE /{ServiceGroupId}/services</code> (since phoss SMP 5.2.4).
   *
   * @param aServiceGroupID
   *        The Service Group ID to use. May not be <code>null</code>.
   * @param aCredentials
   *        The credentials to use (e.g. Basic Auth or Bearer token). May not be <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   */
  default void deleteAllServiceRegistrations (@NonNull final IParticipantIdentifier aServiceGroupID,
                                              @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");

    _executeWrite (new HttpDelete (_getServicesURI (aServiceGroupID)), aCredentials);
  }

  // Service Group IDs

  /**
   * Get the IDs of all Service Groups registered on this SMP.<br>
   * Uses <code>GET /servicegroupids/all</code> (since phoss SMP 8.1.0).
   *
   * @param aCredentials
   *        The credentials to use (e.g. Basic Auth or Bearer token). May not be <code>null</code>.
   * @return A list with all Service Group ID strings in the form <code>scheme::value</code>. Never
   *         <code>null</code> but maybe empty.
   * @throws SMPClientException
   *         in case something goes wrong
   */
  @NonNull
  @ReturnsMutableCopy
  default ICommonsList <String> getAllServiceGroupIDs (@NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aCredentials, "Credentials");

    final IMicroElement eRoot = _executeMicroXML (new HttpGet (getSMPHostURI () + "servicegroupids/all"), aCredentials);
    final ICommonsList <String> ret = new CommonsArrayList <> ();
    for (final IMicroElement eChild : eRoot.getAllChildElements ("servicegroupid"))
      ret.add (eChild.getTextContentTrimmed ());
    return ret;
  }

  // Business Card

  /**
   * Create or update the Business Card of the provided Service Group. The Business Card is sent in
   * the latest (v3) format.<br>
   * Uses <code>PUT /businesscard/{ServiceGroupId}</code> (since phoss SMP 5.0.2).
   *
   * @param aServiceGroupID
   *        The Service Group ID to use. May not be <code>null</code>.
   * @param aBusinessCard
   *        The Business Card to save. May not be <code>null</code>.
   * @param aCredentials
   *        The credentials to use (e.g. Basic Auth or Bearer token). May not be <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   */
  default void saveBusinessCard (@NonNull final IParticipantIdentifier aServiceGroupID,
                                 @NonNull final PD3BusinessCardType aBusinessCard,
                                 @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");
    ValueEnforcer.notNull (aBusinessCard, "BusinessCard");

    final String sBody = new PD3BusinessCardMarshaller ().getAsString (aBusinessCard);
    if (sBody == null)
      throw new IllegalArgumentException ("Failed to serialize BusinessCard: " + aBusinessCard);

    final HttpPut aRequest = new HttpPut (_getBusinessCardURI (aServiceGroupID));
    aRequest.setEntity (new StringEntity (sBody, AbstractGenericSMPClient.CONTENT_TYPE_TEXT_XML));
    _executeWrite (aRequest, aCredentials);
  }

  /**
   * Delete the Business Card of the provided Service Group.<br>
   * Uses <code>DELETE /businesscard/{ServiceGroupId}</code> (since phoss SMP 5.0.2).
   *
   * @param aServiceGroupID
   *        The Service Group ID to use. May not be <code>null</code>.
   * @param aCredentials
   *        The credentials to use (e.g. Basic Auth or Bearer token). May not be <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   */
  default void deleteBusinessCard (@NonNull final IParticipantIdentifier aServiceGroupID,
                                   @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");

    _executeWrite (new HttpDelete (_getBusinessCardURI (aServiceGroupID)), aCredentials);
  }

  /**
   * Explicitly push the Business Card of the provided Service Group to the configured
   * Directory.<br>
   * Uses <code>POST /businesscard/{ServiceGroupId}/push</code> (since phoss SMP 7.1.5).
   *
   * @param aServiceGroupID
   *        The Service Group ID to use. May not be <code>null</code>.
   * @param aCredentials
   *        The credentials to use (e.g. Basic Auth or Bearer token). May not be <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   */
  default void pushBusinessCard (@NonNull final IParticipantIdentifier aServiceGroupID,
                                 @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");

    _executeWrite (new HttpPost (_getBusinessCardURI (aServiceGroupID) + "/push"), aCredentials);
  }

  // Custom Properties

  /**
   * Get all custom properties of the provided Service Group. If the credentials of the Service
   * Group owner are provided, all properties are returned, otherwise only the public ones.<br>
   * Uses <code>GET /{ServiceGroupId}/customproperties</code> (since phoss SMP 8.1.0).
   *
   * @param aServiceGroupID
   *        The Service Group ID to use. May not be <code>null</code>.
   * @param aCredentials
   *        The optional credentials to use. May be <code>null</code> to only retrieve the public
   *        properties.
   * @return The custom properties. Never <code>null</code> but maybe empty.
   * @throws SMPClientException
   *         in case something goes wrong
   */
  @NonNull
  default SGCustomPropertyList getAllCustomProperties (@NonNull final IParticipantIdentifier aServiceGroupID,
                                                       @Nullable final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");

    final IMicroElement eRoot = _executeMicroXML (new HttpGet (_getCustomPropertiesURI (aServiceGroupID)),
                                                  aCredentials);
    try
    {
      final SGCustomPropertyList ret = MicroTypeConverter.convertToNative (eRoot, SGCustomPropertyList.class);
      if (ret == null)
        throw new SMPClientBadResponseException ("Failed to read the custom properties from the SMP response");
      return ret;
    }
    catch (final IllegalArgumentException | IllegalStateException ex)
    {
      throw new SMPClientBadResponseException ("The SMP response contains invalid custom properties", ex);
    }
  }

  /**
   * Get the value of a single custom property of the provided Service Group. If the credentials of
   * the Service Group owner are provided, private properties are found as well, otherwise only
   * public ones.<br>
   * Uses <code>GET /{ServiceGroupId}/customproperties/{PropertyName}</code> (since phoss SMP
   * 8.1.0).
   *
   * @param aServiceGroupID
   *        The Service Group ID to use. May not be <code>null</code>.
   * @param sPropertyName
   *        The name of the custom property. Must be a valid name.
   * @param aCredentials
   *        The optional credentials to use. May be <code>null</code> to only find public
   *        properties.
   * @return The value of the custom property. Never <code>null</code> but maybe empty.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientNotFoundException
   *         if the Service Group or the custom property does not exist or the custom property is
   *         not visible to the caller
   * @see SGCustomProperty#isValidName(String)
   */
  @NonNull
  default String getCustomPropertyValue (@NonNull final IParticipantIdentifier aServiceGroupID,
                                         @NonNull @Nonempty final String sPropertyName,
                                         @Nullable final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");
    ValueEnforcer.isTrue (() -> SGCustomProperty.isValidName (sPropertyName), "PropertyName is invalid");

    return _executeText (new HttpGet (_getCustomPropertiesURI (aServiceGroupID) + '/' + _getEncoded (sPropertyName)),
                         aCredentials);
  }

  /**
   * Replace all custom properties of the provided Service Group with the provided list.<br>
   * Uses <code>PUT /{ServiceGroupId}/customproperties</code> (since phoss SMP 8.1.0).
   *
   * @param aServiceGroupID
   *        The Service Group ID to use. May not be <code>null</code>.
   * @param aCustomProperties
   *        The new custom properties. May not be <code>null</code> but maybe empty.
   * @param aCredentials
   *        The credentials of the Service Group owner (e.g. Basic Auth or Bearer token). May not be
   *        <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   */
  default void setAllCustomProperties (@NonNull final IParticipantIdentifier aServiceGroupID,
                                       @NonNull final SGCustomPropertyList aCustomProperties,
                                       @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");
    ValueEnforcer.notNull (aCustomProperties, "CustomProperties");

    final IMicroElement eRoot = MicroTypeConverter.convertToMicroElement (aCustomProperties,
                                                                          SGCustomPropertyListMicroTypeConverter.ELEMENT_CUSTOM_PROPERTIES);
    final String sBody = MicroWriter.getNodeAsString (eRoot);
    if (sBody == null)
      throw new IllegalArgumentException ("Failed to serialize custom properties: " + aCustomProperties);

    final HttpPut aRequest = new HttpPut (_getCustomPropertiesURI (aServiceGroupID));
    aRequest.setEntity (new StringEntity (sBody, AbstractGenericSMPClient.CONTENT_TYPE_TEXT_XML));
    _executeWrite (aRequest, aCredentials);
  }

  /**
   * Set a single custom property of the provided Service Group. If a property with the same name
   * already exists, it is replaced.<br>
   * Uses <code>PUT /{ServiceGroupId}/customproperties/{PropertyType}/{PropertyName}</code> (since
   * phoss SMP 8.1.0).
   *
   * @param aServiceGroupID
   *        The Service Group ID to use. May not be <code>null</code>.
   * @param aCustomProperty
   *        The custom property to set. May not be <code>null</code>.
   * @param aCredentials
   *        The credentials of the Service Group owner (e.g. Basic Auth or Bearer token). May not be
   *        <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   */
  default void setCustomProperty (@NonNull final IParticipantIdentifier aServiceGroupID,
                                  @NonNull final SGCustomProperty aCustomProperty,
                                  @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");
    ValueEnforcer.notNull (aCustomProperty, "CustomProperty");

    final HttpPut aRequest = new HttpPut (_getCustomPropertiesURI (aServiceGroupID) +
                                          '/' +
                                          aCustomProperty.getType ().getID () +
                                          '/' +
                                          _getEncoded (aCustomProperty.getName ()));
    aRequest.setEntity (new StringEntity (aCustomProperty.getValue (),
                                          ContentType.create (CMimeType.TEXT_PLAIN.getAsString (),
                                                              StandardCharsets.UTF_8)));
    _executeWrite (aRequest, aCredentials);
  }

  /**
   * Delete all custom properties of the provided Service Group.<br>
   * Uses <code>DELETE /{ServiceGroupId}/customproperties</code> (since phoss SMP 8.1.0).
   *
   * @param aServiceGroupID
   *        The Service Group ID to use. May not be <code>null</code>.
   * @param aCredentials
   *        The credentials of the Service Group owner (e.g. Basic Auth or Bearer token). May not be
   *        <code>null</code>.
   * @return The number of deleted custom properties. Always &ge; 0.
   * @throws SMPClientException
   *         in case something goes wrong
   */
  @Nonnegative
  default int deleteAllCustomProperties (@NonNull final IParticipantIdentifier aServiceGroupID,
                                         @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");
    ValueEnforcer.notNull (aCredentials, "Credentials");

    final String sCount = _executeText (new HttpDelete (_getCustomPropertiesURI (aServiceGroupID)), aCredentials);
    final int ret = StringParser.parseInt (StringHelper.trim (sCount), -1);
    if (ret < 0)
      throw new SMPClientBadResponseException ("The SMP response '" + sCount + "' is not a valid number");
    return ret;
  }

  /**
   * Delete a single custom property of the provided Service Group.<br>
   * Uses <code>DELETE /{ServiceGroupId}/customproperties/{PropertyName}</code> (since phoss SMP
   * 8.1.0).
   *
   * @param aServiceGroupID
   *        The Service Group ID to use. May not be <code>null</code>.
   * @param sPropertyName
   *        The name of the custom property to delete. Must be a valid name.
   * @param aCredentials
   *        The credentials of the Service Group owner (e.g. Basic Auth or Bearer token). May not be
   *        <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientNotFoundException
   *         if the Service Group or the custom property does not exist
   * @see SGCustomProperty#isValidName(String)
   */
  default void deleteCustomProperty (@NonNull final IParticipantIdentifier aServiceGroupID,
                                     @NonNull @Nonempty final String sPropertyName,
                                     @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");
    ValueEnforcer.isTrue (() -> SGCustomProperty.isValidName (sPropertyName), "PropertyName is invalid");

    _executeWrite (new HttpDelete (_getCustomPropertiesURI (aServiceGroupID) + '/' + _getEncoded (sPropertyName)),
                   aCredentials);
  }

  // Participant Migration

  /**
   * Start the outbound migration of the provided Service Group. This also registers the migration
   * in the SML.<br>
   * Uses <code>PUT /migration/outbound/start/{ServiceGroupId}</code> (since phoss SMP 5.6.0).
   *
   * @param aServiceGroupID
   *        The Service Group ID to migrate. May not be <code>null</code>.
   * @param aCredentials
   *        The credentials to use (e.g. Basic Auth or Bearer token). May not be <code>null</code>.
   * @return The created migration key, that needs to be passed to the new SMP. Neither
   *         <code>null</code> nor empty.
   * @throws SMPClientException
   *         in case something goes wrong
   */
  @NonNull
  @Nonempty
  default String startParticipantMigrationOutbound (@NonNull final IParticipantIdentifier aServiceGroupID,
                                                    @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");
    ValueEnforcer.notNull (aCredentials, "Credentials");

    final IMicroElement eRoot = _executeMicroXML (new HttpPut (getSMPHostURI () +
                                                               "migration/outbound/start/" +
                                                               aServiceGroupID.getURIPercentEncoded ()), aCredentials);
    final String ret = MicroHelper.getChildTextContentTrimmed (eRoot, "migrationKey");
    if (StringHelper.isEmpty (ret))
      throw new SMPClientBadResponseException ("The SMP response contains no migration key");
    return ret;
  }

  /**
   * Cancel the outbound migration of the provided Service Group.<br>
   * Uses <code>PUT /migration/outbound/cancel/{ServiceGroupId}</code> (since phoss SMP 5.6.0).
   *
   * @param aServiceGroupID
   *        The Service Group ID of the migration. May not be <code>null</code>.
   * @param aCredentials
   *        The credentials to use (e.g. Basic Auth or Bearer token). May not be <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   */
  default void cancelParticipantMigrationOutbound (@NonNull final IParticipantIdentifier aServiceGroupID,
                                                   @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");

    _executeWrite (new HttpPut (getSMPHostURI () +
                                "migration/outbound/cancel/" +
                                aServiceGroupID.getURIPercentEncoded ()), aCredentials);
  }

  /**
   * Finalize the outbound migration of the provided Service Group. This deletes the Service Group
   * on this SMP.<br>
   * Uses <code>PUT /migration/outbound/finalize/{ServiceGroupId}</code> (since phoss SMP 5.6.0).
   *
   * @param aServiceGroupID
   *        The Service Group ID of the migration. May not be <code>null</code>.
   * @param aCredentials
   *        The credentials to use (e.g. Basic Auth or Bearer token). May not be <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   */
  default void finalizeParticipantMigrationOutbound (@NonNull final IParticipantIdentifier aServiceGroupID,
                                                     @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");

    _executeWrite (new HttpPut (getSMPHostURI () +
                                "migration/outbound/finalize/" +
                                aServiceGroupID.getURIPercentEncoded ()), aCredentials);
  }

  /**
   * Perform the inbound migration of the provided Service Group using the migration key received
   * from the old SMP. This performs the migration in the SML and creates the Service Group on this
   * SMP.<br>
   * Uses <code>PUT /migration/inbound/{ServiceGroupId}/{MigrationKey}</code> (since phoss SMP
   * 5.6.0).
   *
   * @param aServiceGroupID
   *        The Service Group ID to migrate. May not be <code>null</code>.
   * @param sMigrationKey
   *        The migration key from the old SMP. May neither be <code>null</code> nor empty.
   * @param aCredentials
   *        The credentials to use (e.g. Basic Auth or Bearer token). May not be <code>null</code>.
   * @return {@link ESuccess#SUCCESS} if both the Service Group and the migration were created.
   *         Never <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   */
  @NonNull
  default ESuccess performParticipantMigrationInbound (@NonNull final IParticipantIdentifier aServiceGroupID,
                                                       @NonNull @Nonempty final String sMigrationKey,
                                                       @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");
    ValueEnforcer.notEmpty (sMigrationKey, "MigrationKey");
    ValueEnforcer.notNull (aCredentials, "Credentials");

    final IMicroElement eRoot = _executeMicroXML (new HttpPut (getSMPHostURI () +
                                                               "migration/inbound/" +
                                                               aServiceGroupID.getURIPercentEncoded () +
                                                               '/' +
                                                               _getEncoded (sMigrationKey)), aCredentials);
    return ESuccess.valueOf ("true".equals (eRoot.getAttributeValue ("success")));
  }

  // Exchange

  @NonNull
  private byte [] _exportAsBytes (@NonNull final String sPath,
                                  final boolean bIncludeBusinessCards,
                                  @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aCredentials, "Credentials");

    return _executeBytes (new HttpGet (getSMPHostURI () +
                                       "exchange/export/" +
                                       sPath +
                                       "/xml/v1?include-business-cards=" +
                                       bIncludeBusinessCards), aCredentials);
  }

  /**
   * Export all Service Groups of this SMP in the phoss SMP specific XML format v1.<br>
   * Uses <code>GET /exchange/export/all/xml/v1</code> (since phoss SMP 5.6.0).
   *
   * @param bIncludeBusinessCards
   *        <code>true</code> to include the Business Cards in the export.
   * @param aCredentials
   *        The credentials to use (e.g. Basic Auth or Bearer token). May not be <code>null</code>.
   * @return The exported XML as a byte array. Never <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @see #exportAllServiceGroupsAsDocument(boolean, IHttpClientCredentials)
   */
  @NonNull
  default byte [] exportAllServiceGroupsAsBytes (final boolean bIncludeBusinessCards,
                                                 @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    return _exportAsBytes ("all", bIncludeBusinessCards, aCredentials);
  }

  /**
   * Export all Service Groups of this SMP in the phoss SMP specific XML format v1.<br>
   * Uses <code>GET /exchange/export/all/xml/v1</code> (since phoss SMP 5.6.0).
   *
   * @param bIncludeBusinessCards
   *        <code>true</code> to include the Business Cards in the export.
   * @param aCredentials
   *        The credentials to use (e.g. Basic Auth or Bearer token). May not be <code>null</code>.
   * @return The exported XML as a DOM document. Never <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @see #exportAllServiceGroupsAsBytes(boolean, IHttpClientCredentials)
   */
  @NonNull
  default Document exportAllServiceGroupsAsDocument (final boolean bIncludeBusinessCards,
                                                     @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    return _parseDOM (exportAllServiceGroupsAsBytes (bIncludeBusinessCards, aCredentials));
  }

  /**
   * Export all Service Groups owned by the provided user in the phoss SMP specific XML format
   * v1.<br>
   * Uses <code>GET /exchange/export/byowner/{UserId}/xml/v1</code> (since phoss SMP 5.6.0).
   *
   * @param sUserID
   *        The login name of the user, whose Service Groups should be exported. Must match the user
   *        of the credentials. May neither be <code>null</code> nor empty.
   * @param bIncludeBusinessCards
   *        <code>true</code> to include the Business Cards in the export.
   * @param aCredentials
   *        The credentials to use (e.g. Basic Auth or Bearer token). May not be <code>null</code>.
   * @return The exported XML as a byte array. Never <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         if the user ID does not match the user of the credentials
   * @see #exportServiceGroupsOfOwnerAsDocument(String, boolean, IHttpClientCredentials)
   */
  @NonNull
  default byte [] exportServiceGroupsOfOwnerAsBytes (@NonNull @Nonempty final String sUserID,
                                                     final boolean bIncludeBusinessCards,
                                                     @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notEmpty (sUserID, "UserID");

    return _exportAsBytes ("byowner/" + _getEncoded (sUserID), bIncludeBusinessCards, aCredentials);
  }

  /**
   * Export all Service Groups owned by the provided user in the phoss SMP specific XML format
   * v1.<br>
   * Uses <code>GET /exchange/export/byowner/{UserId}/xml/v1</code> (since phoss SMP 5.6.0).
   *
   * @param sUserID
   *        The login name of the user, whose Service Groups should be exported. Must match the user
   *        of the credentials. May neither be <code>null</code> nor empty.
   * @param bIncludeBusinessCards
   *        <code>true</code> to include the Business Cards in the export.
   * @param aCredentials
   *        The credentials to use (e.g. Basic Auth or Bearer token). May not be <code>null</code>.
   * @return The exported XML as a DOM document. Never <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         if the user ID does not match the user of the credentials
   * @see #exportServiceGroupsOfOwnerAsBytes(String, boolean, IHttpClientCredentials)
   */
  @NonNull
  default Document exportServiceGroupsOfOwnerAsDocument (@NonNull @Nonempty final String sUserID,
                                                         final boolean bIncludeBusinessCards,
                                                         @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    return _parseDOM (exportServiceGroupsOfOwnerAsBytes (sUserID, bIncludeBusinessCards, aCredentials));
  }

  /**
   * Export a single Service Group in the phoss SMP specific XML format v1.<br>
   * Uses <code>GET /exchange/export/specific/{ServiceGroupId}/xml/v1</code>.
   *
   * @param aServiceGroupID
   *        The Service Group ID to export. May not be <code>null</code>.
   * @param bIncludeBusinessCards
   *        <code>true</code> to include the Business Card in the export.
   * @param aCredentials
   *        The credentials to use (e.g. Basic Auth or Bearer token). May not be <code>null</code>.
   * @return The exported XML as a byte array. Never <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientNotFoundException
   *         if the Service Group does not exist
   * @see #exportServiceGroupAsDocument(IParticipantIdentifier, boolean, IHttpClientCredentials)
   */
  @NonNull
  default byte [] exportServiceGroupAsBytes (@NonNull final IParticipantIdentifier aServiceGroupID,
                                             final boolean bIncludeBusinessCards,
                                             @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");

    return _exportAsBytes ("specific/" + aServiceGroupID.getURIPercentEncoded (), bIncludeBusinessCards, aCredentials);
  }

  /**
   * Export a single Service Group in the phoss SMP specific XML format v1.<br>
   * Uses <code>GET /exchange/export/specific/{ServiceGroupId}/xml/v1</code>.
   *
   * @param aServiceGroupID
   *        The Service Group ID to export. May not be <code>null</code>.
   * @param bIncludeBusinessCards
   *        <code>true</code> to include the Business Card in the export.
   * @param aCredentials
   *        The credentials to use (e.g. Basic Auth or Bearer token). May not be <code>null</code>.
   * @return The exported XML as a DOM document. Never <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientNotFoundException
   *         if the Service Group does not exist
   * @see #exportServiceGroupAsBytes(IParticipantIdentifier, boolean, IHttpClientCredentials)
   */
  @NonNull
  default Document exportServiceGroupAsDocument (@NonNull final IParticipantIdentifier aServiceGroupID,
                                                 final boolean bIncludeBusinessCards,
                                                 @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    return _parseDOM (exportServiceGroupAsBytes (aServiceGroupID, bIncludeBusinessCards, aCredentials));
  }

  /**
   * Export all Service Groups with an outbound migration in progress in the phoss SMP specific XML
   * format v1.<br>
   * Uses <code>GET /exchange/export/outboundmigip/xml/v1</code>.
   *
   * @param bIncludeBusinessCards
   *        <code>true</code> to include the Business Cards in the export.
   * @param aCredentials
   *        The credentials to use (e.g. Basic Auth or Bearer token). May not be <code>null</code>.
   * @return The exported XML as a byte array. Never <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @see #exportOutboundMigrationInProgressAsDocument(boolean, IHttpClientCredentials)
   */
  @NonNull
  default byte [] exportOutboundMigrationInProgressAsBytes (final boolean bIncludeBusinessCards,
                                                            @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    return _exportAsBytes ("outboundmigip", bIncludeBusinessCards, aCredentials);
  }

  /**
   * Export all Service Groups with an outbound migration in progress in the phoss SMP specific XML
   * format v1.<br>
   * Uses <code>GET /exchange/export/outboundmigip/xml/v1</code>.
   *
   * @param bIncludeBusinessCards
   *        <code>true</code> to include the Business Cards in the export.
   * @param aCredentials
   *        The credentials to use (e.g. Basic Auth or Bearer token). May not be <code>null</code>.
   * @return The exported XML as a DOM document. Never <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @see #exportOutboundMigrationInProgressAsBytes(boolean, IHttpClientCredentials)
   */
  @NonNull
  default Document exportOutboundMigrationInProgressAsDocument (final boolean bIncludeBusinessCards,
                                                                @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    return _parseDOM (exportOutboundMigrationInProgressAsBytes (bIncludeBusinessCards, aCredentials));
  }

  /**
   * Import all Service Groups and Business Cards contained in the provided phoss SMP specific XML
   * format v1, as created by one of the export methods.<br>
   * Uses <code>PUT /exchange/import/xml/v1/{UserId}</code> (since phoss SMP 5.6.0).
   *
   * @param aPayload
   *        The XML to import. May not be <code>null</code>.
   * @param sUserID
   *        The login name of the user that becomes the owner of the new records. May differ from
   *        the user of the credentials. May neither be <code>null</code> nor empty.
   * @param bOverwriteExisting
   *        <code>true</code> to overwrite Service Groups already existing in the SMP.
   * @param aCredentials
   *        The credentials to use (e.g. Basic Auth or Bearer token). May not be <code>null</code>.
   * @return The XML action log of the import as a byte array. Never <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @see #importServiceGroupsAsDocument(byte[], String, boolean, IHttpClientCredentials)
   */
  @NonNull
  default byte [] importServiceGroupsAsBytes (@NonNull final byte [] aPayload,
                                              @NonNull @Nonempty final String sUserID,
                                              final boolean bOverwriteExisting,
                                              @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (aPayload, "Payload");
    ValueEnforcer.notEmpty (sUserID, "UserID");
    ValueEnforcer.notNull (aCredentials, "Credentials");

    final HttpPut aRequest = new HttpPut (getSMPHostURI () +
                                          "exchange/import/xml/v1/" +
                                          _getEncoded (sUserID) +
                                          "?overwrite-existing=" +
                                          bOverwriteExisting);
    aRequest.setEntity (new ByteArrayEntity (aPayload, AbstractGenericSMPClient.CONTENT_TYPE_TEXT_XML));
    return _executeBytes (aRequest, aCredentials);
  }

  /**
   * Import all Service Groups and Business Cards contained in the provided phoss SMP specific XML
   * format v1, as created by one of the export methods.<br>
   * Uses <code>PUT /exchange/import/xml/v1/{UserId}</code> (since phoss SMP 5.6.0).
   *
   * @param aPayload
   *        The XML to import. May not be <code>null</code>.
   * @param sUserID
   *        The login name of the user that becomes the owner of the new records. May differ from
   *        the user of the credentials. May neither be <code>null</code> nor empty.
   * @param bOverwriteExisting
   *        <code>true</code> to overwrite Service Groups already existing in the SMP.
   * @param aCredentials
   *        The credentials to use (e.g. Basic Auth or Bearer token). May not be <code>null</code>.
   * @return The XML action log of the import as a DOM document. Never <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @see #importServiceGroupsAsBytes(byte[], String, boolean, IHttpClientCredentials)
   */
  @NonNull
  default Document importServiceGroupsAsDocument (@NonNull final byte [] aPayload,
                                                  @NonNull @Nonempty final String sUserID,
                                                  final boolean bOverwriteExisting,
                                                  @NonNull final IHttpClientCredentials aCredentials) throws SMPClientException
  {
    return _parseDOM (importServiceGroupsAsBytes (aPayload, sUserID, bOverwriteExisting, aCredentials));
  }
}
