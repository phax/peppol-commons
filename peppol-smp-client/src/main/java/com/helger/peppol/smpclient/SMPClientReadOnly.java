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

import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.bind.JAXBElement;

import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.collection.ext.CommonsArrayList;
import com.helger.commons.collection.ext.ICommonsList;
import com.helger.peppol.httpclient.AbstractGenericSMPClient;
import com.helger.peppol.httpclient.SMPHttpResponseHandlerSigned;
import com.helger.peppol.httpclient.SMPHttpResponseHandlerUnsigned;
import com.helger.peppol.identifier.IdentifierHelper;
import com.helger.peppol.identifier.generic.doctype.IDocumentTypeIdentifier;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.identifier.generic.process.IProcessIdentifier;
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
import com.helger.peppol.url.IPeppolURLProvider;
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
public class SMPClientReadOnly extends AbstractGenericSMPClient <SMPClientReadOnly>
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (SMPClientReadOnly.class);

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
  public SMPClientReadOnly (@Nonnull final IPeppolURLProvider aURLProvider,
                            @Nonnull final IParticipantIdentifier aParticipantIdentifier,
                            @Nonnull final ISMLInfo aSMLInfo)
  {
    this (aURLProvider.getSMPURIOfParticipant (aParticipantIdentifier, aSMLInfo));
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
  public SMPClientReadOnly (@Nonnull final IPeppolURLProvider aURLProvider,
                            @Nonnull final IParticipantIdentifier aParticipantIdentifier,
                            @Nonnull @Nonempty final String sSMLZoneName)
  {
    this (aURLProvider.getSMPURIOfParticipant (aParticipantIdentifier, sSMLZoneName));
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
    super (aSMPHost);
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

    final Request aRequest = Request.Get (getSMPHostURI () +
                                          "list/" +
                                          BusdoxURLHelper.createPercentEncodedURL (sUserID))
                                    .addHeader (CHTTPHeader.AUTHORIZATION, aCredentials.getRequestValue ());
    return executeGenericRequest (aRequest,
                                  SMPHttpResponseHandlerUnsigned.create (new SMPMarshallerServiceGroupReferenceListType ()));
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

    final Request aRequest = Request.Get (sCompleteURL);
    return executeGenericRequest (aRequest,
                                  SMPHttpResponseHandlerUnsigned.create (new SMPMarshallerCompleteServiceGroupType ()));
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

    return getCompleteServiceGroup (getSMPHostURI () +
                                    "complete/" +
                                    IdentifierHelper.getIdentifierURIPercentEncoded (aServiceGroupID));
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

    final Request aRequest = Request.Get (getSMPHostURI () +
                                          IdentifierHelper.getIdentifierURIPercentEncoded (aServiceGroupID));
    return executeGenericRequest (aRequest,
                                  SMPHttpResponseHandlerUnsigned.create (new SMPMarshallerServiceGroupType ()));
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

    final String sURI = getSMPHostURI () +
                        IdentifierHelper.getIdentifierURIPercentEncoded (aServiceGroupID) +
                        "/services/" +
                        IdentifierHelper.getIdentifierURIPercentEncoded (aDocumentTypeID);
    Request aRequest = Request.Get (sURI);
    SignedServiceMetadataType aMetadata = executeGenericRequest (aRequest,
                                                                 SMPHttpResponseHandlerSigned.create (new SMPMarshallerSignedServiceMetadataType ()));

    // If the Redirect element is present, then follow 1 redirect.
    if (aMetadata.getServiceMetadata () != null && aMetadata.getServiceMetadata ().getRedirect () != null)
    {
      final RedirectType aRedirect = aMetadata.getServiceMetadata ().getRedirect ();

      // Follow the redirect
      s_aLogger.info ("Following a redirect from '" + sURI + "' to '" + aRedirect.getHref () + "'");
      aRequest = Request.Get (aRedirect.getHref ());
      aMetadata = executeGenericRequest (aRequest,
                                         SMPHttpResponseHandlerSigned.create (new SMPMarshallerSignedServiceMetadataType ()));

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
    final SignedServiceMetadataType aSignedServiceMetadata = getServiceRegistrationOrNull (aServiceGroupID,
                                                                                           aDocumentTypeID);
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
    final ServiceInformationType aServiceInformation = aSignedServiceMetadata.getServiceMetadata ()
                                                                             .getServiceInformation ();
    if (aServiceInformation != null)
    {
      // Okay, it's not a redirect
      for (final ProcessType aProcessType : aServiceInformation.getProcessList ().getProcess ())
      {
        // Matches the requested one?
        if (IdentifierHelper.areProcessIdentifiersEqual (aProcessType.getProcessIdentifier (), aProcessID))
        {
          // Filter all endpoints by required transport profile
          final ICommonsList <EndpointType> aRelevantEndpoints = new CommonsArrayList <> ();
          for (final EndpointType aEndpoint : aProcessType.getServiceEndpointList ().getEndpoint ())
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
                            (aRelevantEndpoints.isEmpty () ? ""
                                                           : ": " +
                                                             aRelevantEndpoints.toString () +
                                                             " - using the first one"));
          }

          // Use the first endpoint or null
          return aRelevantEndpoints.getFirst ();
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
    final String sCertString = getEndpointCertificateString (aServiceGroupID,
                                                             aDocumentTypeID,
                                                             aProcessID,
                                                             aTransportProfile);
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
   * @param aURLProvider
   *        The URL provider to be used. May not be <code>null</code>.
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
  public static CompleteServiceGroupType getCompleteServiceGroupByDNS (@Nonnull final IPeppolURLProvider aURLProvider,
                                                                       @Nonnull final ISMLInfo aSMLInfo,
                                                                       @Nonnull final IParticipantIdentifier aServiceGroupID) throws SMPClientException
  {
    return new SMPClientReadOnly (aURLProvider, aServiceGroupID, aSMLInfo).getCompleteServiceGroup (aServiceGroupID);
  }

  /**
   * Returns a service group. A service group references to the service
   * metadata.
   *
   * @param aURLProvider
   *        The URL provider to be used. May not be <code>null</code>.
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
  public static ServiceGroupType getServiceGroupByDNS (@Nonnull final IPeppolURLProvider aURLProvider,
                                                       @Nonnull final ISMLInfo aSMLInfo,
                                                       @Nonnull final IParticipantIdentifier aServiceGroupID) throws SMPClientException
  {
    return new SMPClientReadOnly (aURLProvider, aServiceGroupID, aSMLInfo).getServiceGroup (aServiceGroupID);
  }

  /**
   * Gets a signed service metadata object given by its service group id and its
   * document type.
   *
   * @param aURLProvider
   *        The URL provider to be used. May not be <code>null</code>.
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
  public static SignedServiceMetadataType getServiceRegistrationByDNS (@Nonnull final IPeppolURLProvider aURLProvider,
                                                                       @Nonnull final ISMLInfo aSMLInfo,
                                                                       @Nonnull final IParticipantIdentifier aServiceGroupID,
                                                                       @Nonnull final IDocumentTypeIdentifier aDocumentTypeID) throws SMPClientException
  {
    return new SMPClientReadOnly (aURLProvider, aServiceGroupID, aSMLInfo).getServiceRegistration (aServiceGroupID,
                                                                                                   aDocumentTypeID);
  }
}
