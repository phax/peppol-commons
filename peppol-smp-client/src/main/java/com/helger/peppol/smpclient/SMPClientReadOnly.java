/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
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

import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.bind.JAXBElement;

import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.OverrideOnDemand;
import com.helger.commons.collection.CollectionHelper;
import com.helger.peppol.identifier.IDocumentTypeIdentifier;
import com.helger.peppol.identifier.IParticipantIdentifier;
import com.helger.peppol.identifier.IProcessIdentifier;
import com.helger.peppol.identifier.IdentifierHelper;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smp.CompleteServiceGroupType;
import com.helger.peppol.smp.EndpointType;
import com.helger.peppol.smp.ISMPTransportProfile;
import com.helger.peppol.smp.ProcessType;
import com.helger.peppol.smp.RedirectType;
import com.helger.peppol.smp.ServiceGroupReferenceListType;
import com.helger.peppol.smp.ServiceGroupType;
import com.helger.peppol.smp.ServiceInformationType;
import com.helger.peppol.smp.SignedServiceMetadataType;
import com.helger.peppol.smpclient.exception.SMPClientBadRequestException;
import com.helger.peppol.smpclient.exception.SMPClientException;
import com.helger.peppol.smpclient.exception.SMPClientNotFoundException;
import com.helger.peppol.smpclient.exception.SMPClientUnauthorizedException;
import com.helger.peppol.utils.BusdoxURLHelper;
import com.helger.peppol.utils.CertificateHelper;
import com.helger.peppol.utils.W3CEndpointReferenceHelper;
import com.helger.peppol.xmldsig.X509DataType;
import com.helger.web.http.CHTTPHeader;
import com.helger.web.http.basicauth.BasicAuthClientCredentials;

/**
 * This class is used for calling the SMP REST interface. This particular class
 * only contains the read-only methods including the ones defined in the SMP
 * specification!
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public class SMPClientReadOnly
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (SMPClientReadOnly.class);

  /**
   * The string representation of the SMP host URL, always ending with a
   * trailing slash!
   */
  private final String m_sSMPHost;

  private HttpHost m_aProxy;

  /**
   * Constructor with SML lookup
   *
   * @param aParticipantIdentifier
   *        The participant identifier to be used. Required to build the SMP
   *        access URI.
   * @param aSMLInfo
   *        The SML to be used. Required to build the SMP access URI.
   * @see BusdoxURLHelper#getSMPURIOfParticipant(IParticipantIdentifier,
   *      ISMLInfo)
   */
  public SMPClientReadOnly (@Nonnull final IParticipantIdentifier aParticipantIdentifier, @Nonnull final ISMLInfo aSMLInfo)
  {
    this (BusdoxURLHelper.getSMPURIOfParticipant (aParticipantIdentifier, aSMLInfo));
  }

  /**
   * Constructor with SML lookup
   *
   * @param aParticipantIdentifier
   *        The participant identifier to be used. Required to build the SMP
   *        access URI.
   * @param sSMLZoneName
   *        The SML DNS zone name to be used. Required to build the SMP access
   *        URI. Must end with a trailing dot (".") and may neither be
   *        <code>null</code> nor empty to build a correct URL. May not start
   *        with "http://". Example: <code>sml.peppolcentral.org.</code>
   * @see BusdoxURLHelper#getSMPURIOfParticipant(IParticipantIdentifier, String)
   */
  public SMPClientReadOnly (@Nonnull final IParticipantIdentifier aParticipantIdentifier, @Nonnull @Nonempty final String sSMLZoneName)
  {
    this (BusdoxURLHelper.getSMPURIOfParticipant (aParticipantIdentifier, sSMLZoneName));
  }

  /**
   * Constructor with a direct SMP URL.<br>
   * Remember: must be HTTP and using port 80 only!
   *
   * @param aSMPHost
   *        The address of the SMP service. Must be port 80 and basic http only
   *        (no https!). Example: http://smpcompany.company.org
   */
  public SMPClientReadOnly (@Nonnull final URI aSMPHost)
  {
    ValueEnforcer.notNull (aSMPHost, "SMPHost");

    if (!"http".equals (aSMPHost.getScheme ()))
      s_aLogger.warn ("SMP URI " + aSMPHost + " does not use the expected http scheme!");
    // getPort () returns -1 if none was explicitly specified
    if (aSMPHost.getPort () != 80 && aSMPHost.getPort () != -1)
      s_aLogger.warn ("SMP URI " + aSMPHost + " is not running on port 80!");

    // Build string and ensure it ends with a "/"
    final String sSMPHost = aSMPHost.toString ();
    m_sSMPHost = sSMPHost.endsWith ("/") ? sSMPHost : sSMPHost + '/';

    // Set default proxy from configuration file
    m_aProxy = SMPClientConfiguration.getHttpProxy ();
  }

  /**
   * @return The SMP host URI string we're operating on. Never <code>null</code>
   *         . Always has a trailing "/".
   */
  @Nonnull
  public String getSMPHostURI ()
  {
    return m_sSMPHost;
  }

  /**
   * @return The HTTP proxy to be used to access the SMP server. Is
   *         <code>null</code> by default.
   */
  @Nullable
  public HttpHost getProxy ()
  {
    return m_aProxy;
  }

  /**
   * Set the proxy to be used to access the SMP server. Note: proxy
   * authentication is currently not supported!
   *
   * @param aProxy
   *        May be <code>null</code> to indicate no proxy.
   * @return this for chaining
   */
  @Nonnull
  public SMPClientReadOnly setProxy (@Nullable final HttpHost aProxy)
  {
    m_aProxy = aProxy;
    return this;
  }

  /**
   * The main execution routine. Overwrite this method to add additional
   * properties to the call.
   *
   * @param aRequest
   *        The request to be executed. Never <code>null</code>.
   * @return The HTTP execution response. Never <code>null</code>.
   * @throws IOException
   *         On HTTP error
   */
  @Nonnull
  @OverrideOnDemand
  protected Response executeRequest (@Nonnull final Request aRequest) throws IOException
  {
    if (m_aProxy != null)
      aRequest.viaProxy (m_aProxy);
    return aRequest.connectTimeout (5000).socketTimeout (10000).execute ();
  }

  /**
   * Convert the passed generic HTTP exception into a more specific exception.
   *
   * @param ex
   *        The generic exception. May not be <code>null</code>.
   * @return A new SMP specific exception, using the passed exception as the
   *         cause.
   */
  @Nonnull
  public static SMPClientException getConvertedException (@Nonnull final Exception ex)
  {
    if (ex instanceof SMPClientException)
      return (SMPClientException) ex;

    if (ex instanceof HttpResponseException)
    {
      final HttpResponseException hex = (HttpResponseException) ex;
      final int nHttpStatus = hex.getStatusCode ();
      switch (nHttpStatus)
      {
        case HttpStatus.SC_BAD_REQUEST:
          return new SMPClientBadRequestException (hex);
        case HttpStatus.SC_FORBIDDEN:
          return new SMPClientUnauthorizedException (hex);
        case HttpStatus.SC_NOT_FOUND:
          return new SMPClientNotFoundException (hex);
      }
      return new SMPClientException ("Error thrown with HTTP status code " + nHttpStatus, hex);
    }

    // Special case
    if (ex instanceof UnknownHostException)
      return new SMPClientNotFoundException ((UnknownHostException) ex);

    return new SMPClientException ("Unknown error thrown by SMP server (" + ex.getMessage () + ")", ex);
  }

  /**
   * Gets a list of references to the CompleteServiceGroup's owned by the
   * specified userId. This is a non-specification compliant method.
   *
   * @param sUserID
   *        The username for which to retrieve service groups.
   * @param aCredentials
   *        The user name and password to use as credentials.
   * @return A list of references to complete service groups and never
   *         <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         The username or password was not correct.
   * @throws SMPClientNotFoundException
   *         The userId did not exist.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   * @see #getServiceGroupReferenceListOrNull(String,
   *      BasicAuthClientCredentials)
   */
  @Nonnull
  public ServiceGroupReferenceListType getServiceGroupReferenceList (@Nonnull final String sUserID,
                                                                     @Nonnull final BasicAuthClientCredentials aCredentials) throws SMPClientException
  {
    ValueEnforcer.notNull (sUserID, "UserID");
    ValueEnforcer.notNull (aCredentials, "Credentials");

    try
    {
      final Request aRequest = Request.Get (m_sSMPHost + "list/" + BusdoxURLHelper.createPercentEncodedURL (sUserID))
                                      .addHeader (CHTTPHeader.AUTHORIZATION, aCredentials.getRequestValue ());
      return executeRequest (aRequest).handleResponse (SMPHttpResponseHandlerUnsigned.create (new SMPMarshallerServiceGroupReferenceListType ()));
    }
    catch (final Exception ex)
    {
      throw getConvertedException (ex);
    }
  }

  /**
   * Gets a list of references to the CompleteServiceGroup's owned by the
   * specified userId. This is a non-specification compliant method.
   *
   * @param sUserID
   *        The username for which to retrieve service groups.
   * @param aCredentials
   *        The user name and password to use as credentials.
   * @return A list of references to complete service groups or
   *         <code>null</code> if no such user exists.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         The username or password was not correct.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   * @see #getServiceGroupReferenceList(String, BasicAuthClientCredentials)
   */
  @Nullable
  public ServiceGroupReferenceListType getServiceGroupReferenceListOrNull (@Nonnull final String sUserID,
                                                                           @Nonnull final BasicAuthClientCredentials aCredentials) throws SMPClientException
  {
    try
    {
      return getServiceGroupReferenceList (sUserID, aCredentials);
    }
    catch (final SMPClientNotFoundException ex)
    {
      return null;
    }
  }

  /**
   * Returns a complete service group. A complete service group contains both
   * the service group and the service metadata. This is a non-specification
   * compliant method.
   *
   * @param sCompleteURL
   *        The complete URL for the full service group to query.
   * @return The complete service group containing service group and service
   *         metadata. Never <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientNotFoundException
   *         The service group id did not exist.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   * @see #getCompleteServiceGroup(IParticipantIdentifier)
   * @see #getCompleteServiceGroupOrNull(IParticipantIdentifier)
   */
  @Nonnull
  public CompleteServiceGroupType getCompleteServiceGroup (@Nonnull final String sCompleteURL) throws SMPClientException
  {
    ValueEnforcer.notEmpty (sCompleteURL, "CompleteURL");

    try
    {
      final Request aRequest = Request.Get (sCompleteURL);
      return executeRequest (aRequest).handleResponse (SMPHttpResponseHandlerUnsigned.create (new SMPMarshallerCompleteServiceGroupType ()));
    }
    catch (final Exception ex)
    {
      throw getConvertedException (ex);
    }
  }

  /**
   * Returns a complete service group. A complete service group contains both
   * the service group and the service metadata. This is a non-specification
   * compliant method.
   *
   * @param aServiceGroupID
   *        The service group id corresponding to the service group which one
   *        wants to get.
   * @return The complete service group containing service group and service
   *         metadata. Never <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientNotFoundException
   *         The service group id did not exist.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   * @see #getCompleteServiceGroup(String)
   * @see #getCompleteServiceGroupOrNull(IParticipantIdentifier)
   */
  @Nonnull
  public CompleteServiceGroupType getCompleteServiceGroup (@Nonnull final IParticipantIdentifier aServiceGroupID) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");

    return getCompleteServiceGroup (m_sSMPHost + "complete/" + IdentifierHelper.getIdentifierURIPercentEncoded (aServiceGroupID));
  }

  /**
   * Returns a complete service group. A complete service group contains both
   * the service group and the service metadata. This is a non-specification
   * compliant method.
   *
   * @param aServiceGroupID
   *        The service group id corresponding to the service group which one
   *        wants to get.
   * @return The complete service group containing service group and service
   *         metadata or <code>null</code> if no such service group exists.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   * @see #getCompleteServiceGroup(String)
   * @see #getCompleteServiceGroup(IParticipantIdentifier)
   */
  @Nullable
  public CompleteServiceGroupType getCompleteServiceGroupOrNull (@Nonnull final IParticipantIdentifier aServiceGroupID) throws SMPClientException
  {
    try
    {
      return getCompleteServiceGroup (aServiceGroupID);
    }
    catch (final SMPClientNotFoundException ex)
    {
      return null;
    }
  }

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
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientNotFoundException
   *         The service group id did not exist.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   * @see #getServiceGroupOrNull(IParticipantIdentifier)
   */
  @Nonnull
  public ServiceGroupType getServiceGroup (@Nonnull final IParticipantIdentifier aServiceGroupID) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");

    try
    {
      final Request aRequest = Request.Get (m_sSMPHost + IdentifierHelper.getIdentifierURIPercentEncoded (aServiceGroupID));
      return executeRequest (aRequest).handleResponse (SMPHttpResponseHandlerUnsigned.create (new SMPMarshallerServiceGroupType ()));
    }
    catch (final Exception ex)
    {
      throw getConvertedException (ex);
    }
  }

  /**
   * Returns a service group. A service group references to the service
   * metadata. This is a specification compliant method.
   *
   * @param aServiceGroupID
   *        The service group id corresponding to the service group which one
   *        wants to get.
   * @return The service group. May be <code>null</code> if no such service
   *         group exists.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   * @see #getServiceGroup(IParticipantIdentifier)
   */
  @Nullable
  public ServiceGroupType getServiceGroupOrNull (@Nonnull final IParticipantIdentifier aServiceGroupID) throws SMPClientException
  {
    try
    {
      return getServiceGroup (aServiceGroupID);
    }
    catch (final SMPClientNotFoundException ex)
    {
      return null;
    }
  }

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
   * @return A signed service metadata object. Never <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientNotFoundException
   *         The service group id or document type did not exist.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   * @see #getServiceRegistrationOrNull(IParticipantIdentifier,
   *      IDocumentTypeIdentifier)
   */
  @Nonnull
  public SignedServiceMetadataType getServiceRegistration (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                                           @Nonnull final IDocumentTypeIdentifier aDocumentTypeID) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");
    ValueEnforcer.notNull (aDocumentTypeID, "DocumentTypeID");

    try
    {
      final String sURI = m_sSMPHost +
                          IdentifierHelper.getIdentifierURIPercentEncoded (aServiceGroupID) +
                          "/services/" +
                          IdentifierHelper.getIdentifierURIPercentEncoded (aDocumentTypeID);
      Request aRequest = Request.Get (sURI);
      SignedServiceMetadataType aMetadata = executeRequest (aRequest).handleResponse (SMPHttpResponseHandlerSigned.create (new SMPMarshallerSignedServiceMetadataType ()));

      // If the Redirect element is present, then follow 1 redirect.
      if (aMetadata.getServiceMetadata () != null && aMetadata.getServiceMetadata ().getRedirect () != null)
      {
        final RedirectType aRedirect = aMetadata.getServiceMetadata ().getRedirect ();

        // Follow the redirect
        s_aLogger.info ("Following a redirect from '" + sURI + "' to '" + aRedirect.getHref () + "'");
        aRequest = Request.Get (aRedirect.getHref ());
        aMetadata = executeRequest (aRequest).handleResponse (SMPHttpResponseHandlerSigned.create (new SMPMarshallerSignedServiceMetadataType ()));

        // Check that the certificateUID is correct.
        boolean bCertificateSubjectFound = false;
        outer: for (final Object aObj : aMetadata.getSignature ().getKeyInfo ().getContent ())
        {
          final Object aInfoValue = ((JAXBElement <?>) aObj).getValue ();
          if (aInfoValue instanceof X509DataType)
          {
            final X509DataType aX509Data = (X509DataType) aInfoValue;
            for (final Object aX509Obj : aX509Data.getX509IssuerSerialOrX509SKIOrX509SubjectName ())
            {
              final JAXBElement <?> aX509element = (JAXBElement <?>) aX509Obj;
              // Find the first subject (of type string)
              if (aX509element.getValue () instanceof String)
              {
                final String sSubject = (String) aX509element.getValue ();
                if (!aRedirect.getCertificateUID ().equals (sSubject))
                {
                  throw new SMPClientException ("The certificate UID of the redirect did not match the certificate subject. Subject is '" +
                                                sSubject +
                                                "'. Required certificate UID is '" +
                                                aRedirect.getCertificateUID () +
                                                "'");
                }
                bCertificateSubjectFound = true;
                break outer;
              }
            }
          }
        }

        if (!bCertificateSubjectFound)
          throw new SMPClientException ("The X509 certificate did not contain a certificate subject.");
      }
      return aMetadata;
    }
    catch (final Exception ex)
    {
      throw getConvertedException (ex);
    }
  }

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
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   * @see #getServiceRegistration(IParticipantIdentifier,
   *      IDocumentTypeIdentifier)
   */
  @Nullable
  public SignedServiceMetadataType getServiceRegistrationOrNull (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                                                 @Nonnull final IDocumentTypeIdentifier aDocumentTypeID) throws SMPClientException
  {
    try
    {
      return getServiceRegistration (aServiceGroupID, aDocumentTypeID);
    }
    catch (final SMPClientNotFoundException ex)
    {
      return null;
    }
  }

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
   * @see #getServiceRegistrationOrNull(IParticipantIdentifier,IDocumentTypeIdentifier)
   */
  @Nullable
  public EndpointType getEndpoint (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                   @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                   @Nonnull final IProcessIdentifier aProcessID,
                                   @Nonnull final ISMPTransportProfile aTransportProfile) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "serviceGroupID");
    ValueEnforcer.notNull (aDocumentTypeID, "DocumentTypeID");
    ValueEnforcer.notNull (aProcessID, "ProcessID");
    ValueEnforcer.notNull (aTransportProfile, "TransportProfile");

    // Get meta data for participant/documentType
    final SignedServiceMetadataType aSignedServiceMetadata = getServiceRegistrationOrNull (aServiceGroupID, aDocumentTypeID);
    return aSignedServiceMetadata == null ? null : getEndpoint (aSignedServiceMetadata, aProcessID, aTransportProfile);
  }

  /**
   * Extract the Endpoint from the signedServiceMetadata that matches the passed
   * process ID and the optional required transport profile.
   *
   * @param aSignedServiceMetadata
   *        The signed service meta data object (e.g. from a call to
   *        {@link #getServiceRegistrationOrNull(IParticipantIdentifier, IDocumentTypeIdentifier)}
   *        . May not be <code>null</code>.
   * @param aProcessID
   *        The process identifier to be looked up. May not be <code>null</code>
   *        .
   * @param aTransportProfile
   *        The required transport profile to be used. May not be
   *        <code>null</code>.
   * @return <code>null</code> if no matching endpoint was found
   */
  @Nullable
  public static EndpointType getEndpoint (@Nonnull final SignedServiceMetadataType aSignedServiceMetadata,
                                          @Nonnull final IProcessIdentifier aProcessID,
                                          @Nonnull final ISMPTransportProfile aTransportProfile)
  {
    ValueEnforcer.notNull (aSignedServiceMetadata, "SignedServiceMetadata");
    ValueEnforcer.notNull (aSignedServiceMetadata.getServiceMetadata (), "SignedServiceMetadata.ServiceMetadata");
    if (aSignedServiceMetadata.getServiceMetadata ().getServiceInformation () == null)
    {
      // It seems to be a redirect and not service information
      return null;
    }
    ValueEnforcer.notNull (aSignedServiceMetadata.getServiceMetadata ().getServiceInformation ().getProcessList (),
                           "SignedServiceMetadata.ServiceMetadata.ServiceInformation.ProcessList");
    ValueEnforcer.notNull (aProcessID, "ProcessID");
    ValueEnforcer.notNull (aTransportProfile, "TransportProfile");

    // Iterate all processes
    final ServiceInformationType aServiceInformation = aSignedServiceMetadata.getServiceMetadata ().getServiceInformation ();
    if (aServiceInformation != null)
    {
      // Okay, it's not a redirect
      final List <ProcessType> aAllProcesses = aServiceInformation.getProcessList ().getProcess ();
      for (final ProcessType aProcessType : aAllProcesses)
      {
        // Matches the requested one?
        if (IdentifierHelper.areProcessIdentifiersEqual (aProcessType.getProcessIdentifier (), aProcessID))
        {
          // Get all endpoints
          final List <EndpointType> aEndpoints = aProcessType.getServiceEndpointList ().getEndpoint ();
          // Filter by required transport profile
          final List <EndpointType> aRelevantEndpoints = new ArrayList <EndpointType> ();
          for (final EndpointType aEndpoint : aEndpoints)
            if (aTransportProfile.getID ().equals (aEndpoint.getTransportProfile ()))
              aRelevantEndpoints.add (aEndpoint);

          if (aRelevantEndpoints.size () != 1)
          {
            s_aLogger.warn ("Found " +
                            aRelevantEndpoints.size () +
                            " endpoints for process " +
                            aProcessID +
                            " and transport profile " +
                            aTransportProfile.getID () +
                            (aRelevantEndpoints.isEmpty () ? "" : ": " + aRelevantEndpoints.toString () + " - using the first one"));
          }

          // Use the first endpoint or null
          return CollectionHelper.getFirstElement (aRelevantEndpoints);
        }
      }
    }
    return null;
  }

  @Nullable
  public static String getEndpointAddress (@Nullable final EndpointType aEndpoint)
  {
    return aEndpoint == null ? null : W3CEndpointReferenceHelper.getAddress (aEndpoint.getEndpointReference ());
  }

  @Nullable
  public String getEndpointAddress (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                    @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                    @Nonnull final IProcessIdentifier aProcessID,
                                    @Nonnull final ISMPTransportProfile aTransportProfile) throws SMPClientException
  {
    final EndpointType aEndpoint = getEndpoint (aServiceGroupID, aDocumentTypeID, aProcessID, aTransportProfile);
    return getEndpointAddress (aEndpoint);
  }

  @Nullable
  public static String getEndpointCertificateString (@Nullable final EndpointType aEndpoint)
  {
    return aEndpoint == null ? null : aEndpoint.getCertificate ();
  }

  @Nullable
  public String getEndpointCertificateString (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                              @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                              @Nonnull final IProcessIdentifier aProcessID,
                                              @Nonnull final ISMPTransportProfile aTransportProfile) throws SMPClientException
  {
    final EndpointType aEndpoint = getEndpoint (aServiceGroupID, aDocumentTypeID, aProcessID, aTransportProfile);
    return getEndpointCertificateString (aEndpoint);
  }

  @Nullable
  public X509Certificate getEndpointCertificate (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                                 @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                                 @Nonnull final IProcessIdentifier aProcessID,
                                                 @Nonnull final ISMPTransportProfile aTransportProfile) throws SMPClientException,
                                                                                                        CertificateException
  {
    final String sCertString = getEndpointCertificateString (aServiceGroupID, aDocumentTypeID, aProcessID, aTransportProfile);
    return CertificateHelper.convertStringToCertficate (sCertString);
  }

  @Nullable
  public static X509Certificate getEndpointCertificate (@Nullable final EndpointType aEndpoint) throws CertificateException
  {
    final String sCertString = getEndpointCertificateString (aEndpoint);
    return CertificateHelper.convertStringToCertficate (sCertString);
  }

  /**
   * Returns a complete service group. A complete service group contains both
   * the service group and the service metadata.
   *
   * @param aSMLInfo
   *        The SML object to be used
   * @param aServiceGroupID
   *        The service group id corresponding to the service group which one
   *        wants to get.
   * @return The complete service group containing service group and service
   *         metadata
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientNotFoundException
   *         The service group id did not exist.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   */
  @Nonnull
  public static CompleteServiceGroupType getCompleteServiceGroupByDNS (@Nonnull final ISMLInfo aSMLInfo,
                                                                       @Nonnull final IParticipantIdentifier aServiceGroupID) throws SMPClientException
  {
    return new SMPClientReadOnly (aServiceGroupID, aSMLInfo).getCompleteServiceGroup (aServiceGroupID);
  }

  /**
   * Returns a service group. A service group references to the service
   * metadata.
   *
   * @param aSMLInfo
   *        The SML object to be used
   * @param aServiceGroupID
   *        The service group id corresponding to the service group which one
   *        wants to get.
   * @return The service group
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientNotFoundException
   *         The service group id did not exist.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   */
  @Nonnull
  public static ServiceGroupType getServiceGroupByDNS (@Nonnull final ISMLInfo aSMLInfo,
                                                       @Nonnull final IParticipantIdentifier aServiceGroupID) throws SMPClientException
  {
    return new SMPClientReadOnly (aServiceGroupID, aSMLInfo).getServiceGroup (aServiceGroupID);
  }

  /**
   * Gets a signed service metadata object given by its service group id and its
   * document type.
   *
   * @param aSMLInfo
   *        The SML object to be used
   * @param aServiceGroupID
   *        The service group id of the service metadata to get.
   * @param aDocumentTypeID
   *        The document type of the service metadata to get.
   * @return A signed service metadata object.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientNotFoundException
   *         The service group id or document type did not exist.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   */
  @Nonnull
  public static SignedServiceMetadataType getServiceRegistrationByDNS (@Nonnull final ISMLInfo aSMLInfo,
                                                                       @Nonnull final IParticipantIdentifier aServiceGroupID,
                                                                       @Nonnull final IDocumentTypeIdentifier aDocumentTypeID) throws SMPClientException
  {
    return new SMPClientReadOnly (aServiceGroupID, aSMLInfo).getServiceRegistration (aServiceGroupID, aDocumentTypeID);
  }
}
