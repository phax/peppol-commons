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

import java.net.URI;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.bind.JAXBElement;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.http.CHttpHeader;
import com.helger.commons.state.EContinue;
import com.helger.commons.string.StringHelper;
import com.helger.commons.wrapper.Wrapper;
import com.helger.http.basicauth.BasicAuthClientCredentials;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smp.ISMPTransportProfile;
import com.helger.peppolid.CIdentifier;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.peppolid.peppol.doctype.IPeppolDocumentTypeIdentifierParts;
import com.helger.peppolid.peppol.doctype.PeppolDocumentTypeIdentifierParts;
import com.helger.security.certificate.CertificateHelper;
import com.helger.smpclient.exception.SMPClientBadRequestException;
import com.helger.smpclient.exception.SMPClientException;
import com.helger.smpclient.exception.SMPClientNotFoundException;
import com.helger.smpclient.exception.SMPClientUnauthorizedException;
import com.helger.smpclient.httpclient.AbstractGenericSMPClient;
import com.helger.smpclient.httpclient.SMPHttpResponseHandlerSigned;
import com.helger.smpclient.httpclient.SMPHttpResponseHandlerUnsigned;
import com.helger.smpclient.peppol.marshal.SMPMarshallerCompleteServiceGroupType;
import com.helger.smpclient.peppol.marshal.SMPMarshallerServiceGroupReferenceListType;
import com.helger.smpclient.peppol.marshal.SMPMarshallerServiceGroupType;
import com.helger.smpclient.peppol.marshal.SMPMarshallerSignedServiceMetadataType;
import com.helger.smpclient.peppol.utils.W3CEndpointReferenceHelper;
import com.helger.smpclient.url.ISMPURLProvider;
import com.helger.smpclient.url.SMPDNSResolutionException;
import com.helger.xsds.peppol.id1.ProcessIdentifierType;
import com.helger.xsds.peppol.smp1.CompleteServiceGroupType;
import com.helger.xsds.peppol.smp1.EndpointType;
import com.helger.xsds.peppol.smp1.ProcessType;
import com.helger.xsds.peppol.smp1.RedirectType;
import com.helger.xsds.peppol.smp1.ServiceGroupReferenceListType;
import com.helger.xsds.peppol.smp1.ServiceGroupType;
import com.helger.xsds.peppol.smp1.ServiceInformationType;
import com.helger.xsds.peppol.smp1.ServiceMetadataReferenceType;
import com.helger.xsds.peppol.smp1.ServiceMetadataType;
import com.helger.xsds.peppol.smp1.SignedServiceMetadataType;
import com.helger.xsds.xmldsig.X509DataType;

/**
 * This class is used for calling the Peppol SMP REST interface. This particular
 * class only contains the read-only methods including the ones defined in the
 * Peppol SMP specification!
 *
 * @author Philip Helger
 */
public class SMPClientReadOnly extends AbstractGenericSMPClient <SMPClientReadOnly> implements
                               ISMPServiceGroupProvider,
                               ISMPServiceMetadataProvider
{
  public static final String URL_PART_COMPLETE = "complete";
  public static final String URL_PART_LIST = "list";
  public static final String URL_PART_SERVICES = "services";

  private static final Logger LOGGER = LoggerFactory.getLogger (SMPClientReadOnly.class);

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
  public SMPClientReadOnly (@Nonnull final ISMPURLProvider aURLProvider,
                            @Nonnull final IParticipantIdentifier aParticipantIdentifier,
                            @Nonnull final ISMLInfo aSMLInfo) throws SMPDNSResolutionException
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
   * @throws SMPDNSResolutionException
   *         if DNS resolution fails
   * @see ISMPURLProvider#getSMPURIOfParticipant(IParticipantIdentifier, String)
   */
  public SMPClientReadOnly (@Nonnull final ISMPURLProvider aURLProvider,
                            @Nonnull final IParticipantIdentifier aParticipantIdentifier,
                            @Nonnull @Nonempty final String sSMLZoneName) throws SMPDNSResolutionException
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
    // Peppol limitations should be checked
    super (aSMPHost, true);
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

    final String sURI = getSMPHostURI () + URL_PART_LIST + "/" + CIdentifier.createPercentEncoded (sUserID);
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("SMPClient getServiceGroupReferenceList@" + sURI);

    final HttpGet aRequest = new HttpGet (sURI);
    aRequest.addHeader (CHttpHeader.AUTHORIZATION, aCredentials.getRequestValue ());
    final SMPMarshallerServiceGroupReferenceListType aMarshaller = new SMPMarshallerServiceGroupReferenceListType (isXMLSchemaValidation ());
    customizeMarshaller (aMarshaller);
    return executeGenericRequest (aRequest, new SMPHttpResponseHandlerUnsigned <> (aMarshaller));
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
   * compliant method.<br>
   * NOTE: this API is NOT supported by all SMP implementations. It is based on
   * a proprietary API provided by the Peppol reference implementation and now
   * supported by phoss SMP but not all other SMPs.
   *
   * @param sCompleteURI
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
  public CompleteServiceGroupType getCompleteServiceGroup (@Nonnull final String sCompleteURI) throws SMPClientException
  {
    ValueEnforcer.notEmpty (sCompleteURI, "CompleteURL");

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("SMPClient getCompleteServiceGroup@" + sCompleteURI);

    final HttpGet aRequest = new HttpGet (sCompleteURI);
    final SMPMarshallerCompleteServiceGroupType aMarshaller = new SMPMarshallerCompleteServiceGroupType (isXMLSchemaValidation ());
    customizeMarshaller (aMarshaller);
    return executeGenericRequest (aRequest, new SMPHttpResponseHandlerUnsigned <> (aMarshaller));
  }

  /**
   * Returns a complete service group. A complete service group contains both
   * the service group and the service metadata. This is a non-specification
   * compliant method.<br>
   * NOTE: this API is NOT supported by all SMP implementations. It is based on
   * a proprietary API provided by the Peppol reference implementation and now
   * supported by phoss SMP but not all other SMPs.
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
                                    URL_PART_COMPLETE +
                                    "/" +
                                    aServiceGroupID.getURIPercentEncoded ());
  }

  /**
   * Returns a complete service group. A complete service group contains both
   * the service group and the service metadata. This is a non-specification
   * compliant method.<br>
   * NOTE: this API is NOT supported by all SMP implementations. It is based on
   * a proprietary API provided by the Peppol reference implementation and now
   * supported by phoss SMP but not all other SMPs.
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

    final String sURI = getSMPHostURI () + aServiceGroupID.getURIPercentEncoded ();
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("SMPClient getServiceGroup@" + sURI);

    final HttpGet aRequest = new HttpGet (sURI);
    final SMPMarshallerServiceGroupType aMarshaller = new SMPMarshallerServiceGroupType (isXMLSchemaValidation ());
    customizeMarshaller (aMarshaller);
    final ServiceGroupType ret = executeGenericRequest (aRequest, new SMPHttpResponseHandlerUnsigned <> (aMarshaller));

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Received response: " + ret);

    return ret;
  }

  @Nullable
  public ServiceGroupType getServiceGroupOrNull (@Nonnull final IParticipantIdentifier aServiceGroupID) throws SMPClientException
  {
    try
    {
      return getServiceGroup (aServiceGroupID);
    }
    catch (final SMPClientNotFoundException ex)
    {
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Found no ServiceGroup");
      return null;
    }
  }

  /**
   * Extract all parsable document types from the passed Service group. This
   * method always uses {@link PeppolIdentifierFactory} to parse the document
   * type identifiers.
   *
   * @param aSG
   *        The service group to parse. May be <code>null</code>.
   * @return Never <code>null</code> but a maybe empty list.
   * @since 8.0.4
   */
  @Nonnull
  public static ICommonsList <IDocumentTypeIdentifier> getAllDocumentTypes (@Nullable final ServiceGroupType aSG)
  {
    return getAllDocumentTypes (aSG, PeppolIdentifierFactory.INSTANCE, null);
  }

  /**
   * Extract all parsable document types from the passed Service group. This
   * method always uses {@link PeppolIdentifierFactory} to parse the document
   * type identifiers.
   *
   * @param aSG
   *        The service group to parse. May be <code>null</code>.
   * @param aIdentifierFactory
   *        The identifier factory to be used. May not be <code>null</code>.
   * @param aUnhandledHrefHandler
   *        An optional consumer for Hrefs that could not be parsed into a
   *        document type identifier. May be <code>null</code>.
   * @return Never <code>null</code> but a maybe empty list.
   * @since 8.0.4
   */
  @Nonnull
  public static ICommonsList <IDocumentTypeIdentifier> getAllDocumentTypes (@Nullable final ServiceGroupType aSG,
                                                                            @Nonnull final IIdentifierFactory aIdentifierFactory,
                                                                            @Nullable final Consumer <String> aUnhandledHrefHandler)
  {
    ValueEnforcer.notNull (aIdentifierFactory, "IdentifierFactory");

    final ICommonsList <IDocumentTypeIdentifier> ret = new CommonsArrayList <> ();

    if (aSG != null && aSG.getParticipantIdentifier () != null && aSG.getServiceMetadataReferenceCollection () != null)
    {
      final String sPathStart = "/" +
                                CIdentifier.getURIEncoded (aSG.getParticipantIdentifier ()) +
                                "/" +
                                URL_PART_SERVICES +
                                "/";
      for (final ServiceMetadataReferenceType aSMR : aSG.getServiceMetadataReferenceCollection ()
                                                        .getServiceMetadataReference ())
      {
        final String sOriginalHref = aSMR.getHref ();
        // Decoded href is important for unification
        final String sHref = CIdentifier.createPercentDecoded (sOriginalHref);

        boolean bSuccess = false;

        // Case insensitive "indexOf" here
        final int nPathStart = StringHelper.getIndexOfIgnoreCase (sHref, sPathStart, Locale.US);
        if (nPathStart >= 0)
        {
          final String sDocType = sHref.substring (nPathStart + sPathStart.length ());
          final IDocumentTypeIdentifier aDocType = aIdentifierFactory.parseDocumentTypeIdentifier (sDocType);
          if (aDocType != null)
          {
            // Found a document type
            ret.add (aDocType);
            bSuccess = true;
          }
        }

        if (!bSuccess)
        {
          // Failed to parse as doc type
          if (LOGGER.isDebugEnabled ())
            LOGGER.debug ("Failed to parse '" + sOriginalHref + "' as a document type identifier");

          if (aUnhandledHrefHandler != null)
            aUnhandledHrefHandler.accept (sOriginalHref);
        }
      }
    }
    return ret;
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
   * @see #getServiceMetadataOrNull(IParticipantIdentifier,
   *      IDocumentTypeIdentifier)
   * @since v8.0.0
   */
  @Nonnull
  public SignedServiceMetadataType getServiceMetadata (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                                       @Nonnull final IDocumentTypeIdentifier aDocumentTypeID) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");
    ValueEnforcer.notNull (aDocumentTypeID, "DocumentTypeID");

    final String sURI = getSMPHostURI () +
                        aServiceGroupID.getURIPercentEncoded () +
                        "/" +
                        URL_PART_SERVICES +
                        "/" +
                        aDocumentTypeID.getURIPercentEncoded ();

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("SMPClient getServiceRegistration@" + sURI);

    final boolean bXSDValidation = isXMLSchemaValidation ();
    final boolean bVerifySignature = isVerifySignature ();
    final KeyStore aTrustStore = getTrustStore ();

    if (bVerifySignature && aTrustStore == null)
      LOGGER.error ("Peppol SMP client Verify Signature is enabled, but no TrustStore is provided. This will not work.");

    HttpGet aRequest = new HttpGet (sURI);
    SMPMarshallerSignedServiceMetadataType aMarshaller = new SMPMarshallerSignedServiceMetadataType (bXSDValidation);
    customizeMarshaller (aMarshaller);
    SignedServiceMetadataType aMetadata = executeGenericRequest (aRequest,
                                                                 new SMPHttpResponseHandlerSigned <> (aMarshaller,
                                                                                                      aTrustStore).setVerifySignature (bVerifySignature));

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Received response: " + aMetadata);

    // If the Redirect element is present, then follow 1 redirect.
    if (isFollowSMPRedirects ())
    {
      if (aMetadata.getServiceMetadata () != null && aMetadata.getServiceMetadata ().getRedirect () != null)
      {
        final RedirectType aRedirect = aMetadata.getServiceMetadata ().getRedirect ();

        // Follow the redirect
        LOGGER.info ("Following a redirect from '" + sURI + "' to '" + aRedirect.getHref () + "'");
        aRequest = new HttpGet (aRedirect.getHref ());

        // Create a new Marshaller to ensure customization is simple
        aMarshaller = new SMPMarshallerSignedServiceMetadataType (bXSDValidation);
        customizeMarshaller (aMarshaller);
        aMetadata = executeGenericRequest (aRequest,
                                           new SMPHttpResponseHandlerSigned <> (aMarshaller,
                                                                                aTrustStore).setVerifySignature (bVerifySignature));

        // Check that the certificateUID is correct.
        boolean bCertificateSubjectFound = false;
        for (final Object aObj : aMetadata.getSignature ().getKeyInfo ().getContent ())
        {
          final Object aInfoValue = ((JAXBElement <?>) aObj).getValue ();
          if (aInfoValue instanceof X509DataType)
          {
            final X509DataType aX509Data = (X509DataType) aInfoValue;
            if (containsRedirectSubject (aX509Data, aRedirect.getCertificateUID ()))
            {
              bCertificateSubjectFound = true;
              break;
            }
          }
        }

        if (!bCertificateSubjectFound)
          throw new SMPClientException ("The X509 certificate did not contain a certificate subject.");
      }
    }
    else
    {
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Following SMP redirects is disabled");
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
   * @see #getServiceMetadata(IParticipantIdentifier, IDocumentTypeIdentifier)
   * @since v8.0.0
   */
  @Nullable
  public SignedServiceMetadataType getServiceMetadataOrNull (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                                             @Nonnull final IDocumentTypeIdentifier aDocumentTypeID) throws SMPClientException
  {
    try
    {
      return getServiceMetadata (aServiceGroupID, aDocumentTypeID);
    }
    catch (final SMPClientNotFoundException ex)
    {
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Found no ServiceMetadata");
      return null;
    }
  }

  private static boolean _hasSameContent (@Nonnull final ProcessIdentifierType aPI1,
                                          @Nonnull final IProcessIdentifier aPI2)
  {
    return EqualsHelper.equals (aPI1.getScheme (), aPI2.getScheme ()) &&
           EqualsHelper.equals (aPI1.getValue (), aPI2.getValue ());
  }

  /**
   * Extract the Endpoint from the signedServiceMetadata that matches the passed
   * process ID and the optional required transport profile. This method checks
   * the validity of the endpoint at the current point in time.
   *
   * @param aSignedServiceMetadata
   *        The signed service meta data object (e.g. from a call to
   *        {@link #getServiceMetadataOrNull(IParticipantIdentifier, IDocumentTypeIdentifier)}
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
    return getEndpoint (aSignedServiceMetadata.getServiceMetadata (), aProcessID, aTransportProfile);
  }

  /**
   * Extract the Endpoint from the signedServiceMetadata that matches the passed
   * process ID and the optional required transport profile.
   *
   * @param aSignedServiceMetadata
   *        The signed service meta data object (e.g. from a call to
   *        {@link #getServiceMetadataOrNull(IParticipantIdentifier, IDocumentTypeIdentifier)}
   *        . May not be <code>null</code>.
   * @param aProcessID
   *        The process identifier to be looked up. May not be <code>null</code>
   *        .
   * @param aTransportProfile
   *        The required transport profile to be used. May not be
   *        <code>null</code>.
   * @param aCheckDT
   *        The date and time for when the endpoint is meant to be valid if the
   *        end point contains a ServiceActivationDate and/or a
   *        ServiceExpirationDate. May not be <code>null</code>.
   * @return <code>null</code> if no matching endpoint was found
   * @since 8.7.3
   */
  @Nullable
  public static EndpointType getEndpointAt (@Nonnull final SignedServiceMetadataType aSignedServiceMetadata,
                                            @Nonnull final IProcessIdentifier aProcessID,
                                            @Nonnull final ISMPTransportProfile aTransportProfile,
                                            @Nonnull final LocalDateTime aCheckDT)
  {
    ValueEnforcer.notNull (aSignedServiceMetadata, "SignedServiceMetadata");
    return getEndpointAt (aSignedServiceMetadata.getServiceMetadata (), aProcessID, aTransportProfile, aCheckDT);
  }

  /**
   * Extract the Endpoint from the ServiceMetadata that matches the passed
   * process ID and the optional required transport profile. This method checks
   * the validity of the endpoint at the current point in time.
   *
   * @param aServiceMetadata
   *        The unsigned service meta data object. May not be <code>null</code>.
   * @param aProcessID
   *        The process identifier to be looked up. May not be <code>null</code>
   *        .
   * @param aTransportProfile
   *        The required transport profile to be used. May not be
   *        <code>null</code>.
   * @return <code>null</code> if no matching endpoint was found
   * @since 8.2.6
   */
  @Nullable
  public static EndpointType getEndpoint (@Nonnull final ServiceMetadataType aServiceMetadata,
                                          @Nonnull final IProcessIdentifier aProcessID,
                                          @Nonnull final ISMPTransportProfile aTransportProfile)
  {
    return getEndpointAt (aServiceMetadata, aProcessID, aTransportProfile, PDTFactory.getCurrentLocalDateTime ());
  }

  /**
   * Check if the provided SMP endpoint is valid at the provided date and time.
   * This is to ensure the ServiceActionDate and ServiceExpirationDate values
   * are honoured according to the changes in the Peppol SMP 1.2.0
   * specification.
   *
   * @param aEndpoint
   *        The SMP endpoint to check. May not be <code>null</code>.
   * @param aCheckDT
   *        The date and time at which the check is performed. May not be
   *        <code>null</code>.
   * @return <code>true</code> if the endpoint is valid, <code>false</code> if
   *         not.
   * @since 8.7.3
   */
  public static boolean isEndpointValidAt (@Nonnull final EndpointType aEndpoint, @Nonnull final LocalDateTime aCheckDT)
  {
    ValueEnforcer.notNull (aEndpoint, "Endpoint");
    ValueEnforcer.notNull (aCheckDT, "CheckDT");

    // Check not before time
    final LocalDateTime aNotBefore = aEndpoint.getServiceActivationDateLocal ();
    if (aNotBefore != null)
    {
      if (aCheckDT.isBefore (aNotBefore))
      {
        if (LOGGER.isDebugEnabled ())
          LOGGER.debug ("SMP endpoint activation date " + aNotBefore + " is after the check DT " + aCheckDT);
        return false;
      }
    }

    // Check not after time
    final LocalDateTime aNotAfter = aEndpoint.getServiceExpirationDateLocal ();
    if (aNotAfter != null)
    {
      if (aCheckDT.isAfter (aNotAfter))
      {
        if (LOGGER.isDebugEnabled ())
          LOGGER.debug ("SMP endpoint expiration date " + aNotAfter + " is before the check DT " + aCheckDT);
        return false;
      }
    }

    return true;
  }

  /**
   * Extract the Endpoint from the ServiceMetadata that matches the passed
   * process ID and the optional required transport profile.
   *
   * @param aServiceMetadata
   *        The unsigned service meta data object. May not be <code>null</code>.
   * @param aProcessID
   *        The process identifier to be looked up. May not be <code>null</code>
   *        .
   * @param aTransportProfile
   *        The required transport profile to be used. May not be
   *        <code>null</code>.
   * @param aCheckDT
   *        The date and time for when the endpoint is meant to be valid if the
   *        end point contains a ServiceActivationDate and/or a
   *        ServiceExpirationDate. May not be <code>null</code>.
   * @return <code>null</code> if no matching endpoint was found
   * @since 8.7.3
   */
  @Nullable
  public static EndpointType getEndpointAt (@Nonnull final ServiceMetadataType aServiceMetadata,
                                            @Nonnull final IProcessIdentifier aProcessID,
                                            @Nonnull final ISMPTransportProfile aTransportProfile,
                                            @Nonnull final LocalDateTime aCheckDT)
  {
    ValueEnforcer.notNull (aServiceMetadata, "ServiceMetadata");
    final ServiceInformationType aServiceInformation = aServiceMetadata.getServiceInformation ();
    if (aServiceInformation == null)
    {
      // It seems to be a redirect and not service information
      return null;
    }
    ValueEnforcer.notNull (aServiceInformation.getProcessList (), "ServiceMetadata.ServiceInformation.ProcessList");
    ValueEnforcer.notNull (aProcessID, "ProcessID");
    ValueEnforcer.notNull (aTransportProfile, "TransportProfile");
    ValueEnforcer.notNull (aCheckDT, "CheckDT");

    // Iterate all processes
    for (final ProcessType aProcessType : aServiceInformation.getProcessList ().getProcess ())
    {
      // Matches the requested one?
      if (_hasSameContent (aProcessType.getProcessIdentifier (), aProcessID))
      {
        // Filter all endpoints by required transport profile
        final ICommonsList <EndpointType> aRelevantEndpoints = new CommonsArrayList <> ();
        for (final EndpointType aEndpoint : aProcessType.getServiceEndpointList ().getEndpoint ())
          if (aTransportProfile.getID ().equals (aEndpoint.getTransportProfile ()) &&
              isEndpointValidAt (aEndpoint, aCheckDT))
            aRelevantEndpoints.add (aEndpoint);

        if (aRelevantEndpoints.size () != 1)
        {
          LOGGER.warn ("Found " +
                       aRelevantEndpoints.size () +
                       " endpoints for process " +
                       aProcessID +
                       " and transport profile '" +
                       aTransportProfile.getID () +
                       "' valid at " +
                       aCheckDT +
                       (aRelevantEndpoints.isEmpty () ? ""
                                                      : ": " +
                                                        aRelevantEndpoints.toString () +
                                                        " - using the first one"));
        }

        // Use the first endpoint
        final EndpointType ret = aRelevantEndpoints.getFirst ();
        if (LOGGER.isDebugEnabled ())
          LOGGER.debug ("Found matching SMP endpoint: " + ret);
        return ret;
      }
    }

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Found no matching SMP endpoint");

    return null;
  }

  /**
   * Get the endpoint address URI from the provided SMP endpoint.
   *
   * @param aEndpoint
   *        The endpoint to be used. May be <code>null</code>.
   * @return <code>null</code> if the endpoint is <code>null</code> if the
   *         endpoint has no address URI.
   */
  @Nullable
  public static String getEndpointAddress (@Nullable final EndpointType aEndpoint)
  {
    return aEndpoint == null ||
           aEndpoint.getEndpointReference () == null ? null
                                                     : W3CEndpointReferenceHelper.getAddress (aEndpoint.getEndpointReference ());
  }

  /**
   * Get the certificate string from the provided SMP endpoint.
   *
   * @param aEndpoint
   *        The endpoint to be used. May be <code>null</code>.
   * @return <code>null</code> if the endpoint is <code>null</code> if the
   *         endpoint has no certificate.
   */
  @Nullable
  public static String getEndpointCertificateString (@Nullable final EndpointType aEndpoint)
  {
    return aEndpoint == null ? null : aEndpoint.getCertificate ();
  }

  /**
   * Get the certificate bytes from the specified endpoint.
   *
   * @param aEndpoint
   *        The endpoint to be used. May be <code>null</code>.
   * @return <code>null</code> if no such endpoint exists, or if the endpoint
   *         has no certificate
   * @throws CertificateException
   *         In case the conversion from byte to X509 certificate failed
   */
  @Nullable
  public static X509Certificate getEndpointCertificate (@Nullable final EndpointType aEndpoint) throws CertificateException
  {
    final String sCertString = getEndpointCertificateString (aEndpoint);
    return CertificateHelper.convertStringToCertficate (sCertString);
  }

  /**
   * Helper method to iterate all matching document type identifiers. This
   * method prefers direct matches ("busdox-docid-qns") over wildcard matches
   * ("peppol-doctype-wildcard").
   *
   * @param aBaseDocTypes
   *        The list of document types to filter. May not be <code>null</code>,
   *        but maybe empty.
   * @param sDocTypeValue
   *        The document type identifier value (!) <b>without</b> the scheme to
   *        search. The schemes are added internally automatically.
   * @param aMatchingDocTypeConsumer
   *        The consumer to be invoked for each match. May not be
   *        <code>null</code>.
   * @since 8.8.1
   */
  public static void forEachMatchingWildcardDocumentType (@Nonnull final ICommonsList <? extends IDocumentTypeIdentifier> aBaseDocTypes,
                                                          @Nonnull @Nonempty final String sDocTypeValue,
                                                          @Nonnull final Function <? super IDocumentTypeIdentifier, EContinue> aMatchingDocTypeConsumer)
  {
    ValueEnforcer.notNull (aBaseDocTypes, "BaseDocTypes");
    ValueEnforcer.notEmpty (sDocTypeValue, "DocTypeValue");
    ValueEnforcer.notNull (aMatchingDocTypeConsumer, "MatchingDocTypeConsumer");

    final BiFunction <String, String, IDocumentTypeIdentifier> aFuncCheckExistance = (sScheme, sValue) -> {
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Checking if document type ID '" +
                      CIdentifier.getURIEncoded (sScheme, sValue) +
                      "' is contained");
      if (aBaseDocTypes.containsAny (x -> x.hasScheme (sScheme) && x.hasValue (sValue)))
        return PeppolIdentifierFactory.INSTANCE.createDocumentTypeIdentifier (sScheme, sValue);
      return null;
    };

    // Try busdox-docid-qns first ("as is")
    IDocumentTypeIdentifier aSelectedDocTypeID = aFuncCheckExistance.apply (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS,
                                                                            sDocTypeValue);
    if (aSelectedDocTypeID != null)
    {
      // Old fashioned match
      if (aMatchingDocTypeConsumer.apply (aSelectedDocTypeID).isBreak ())
        return;
    }

    // No direct match found
    try
    {
      // Split the document type identifier value into pieces (throws
      // IllegalArgumentException)
      final IPeppolDocumentTypeIdentifierParts aParts = PeppolDocumentTypeIdentifierParts.extractFromString (sDocTypeValue);

      // Just change the customization ID of the parts
      final Function <String, String> aFuncCustIDToDocTypeIDValue = sCustomizationID -> new PeppolDocumentTypeIdentifierParts (aParts.getRootNS (),
                                                                                                                               aParts.getLocalName (),
                                                                                                                               sCustomizationID,
                                                                                                                               aParts.getVersion ()).getAsDocumentTypeIdentifierValue ();

      // Iterate all the peppol-doctype-wildcard stuff
      String sRemainingCustomizationID = aParts.getCustomizationID ();
      aSelectedDocTypeID = aFuncCheckExistance.apply (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD,
                                                      aFuncCustIDToDocTypeIDValue.apply (sRemainingCustomizationID +
                                                                                         PeppolIdentifierHelper.DOCUMENT_TYPE_WILDCARD_INDICATOR));
      if (aSelectedDocTypeID != null)
        if (aMatchingDocTypeConsumer.apply (aSelectedDocTypeID).isBreak ())
          return;

      while (sRemainingCustomizationID.indexOf (PeppolIdentifierHelper.DOCUMENT_TYPE_WILDCARD_PART_SEPARATOR) >= 0)
      {
        // Remove last part (after last '@')
        sRemainingCustomizationID = sRemainingCustomizationID.substring (0,
                                                                         sRemainingCustomizationID.lastIndexOf (PeppolIdentifierHelper.DOCUMENT_TYPE_WILDCARD_PART_SEPARATOR));

        // Try more corse-grain part
        aSelectedDocTypeID = aFuncCheckExistance.apply (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD,
                                                        aFuncCustIDToDocTypeIDValue.apply (sRemainingCustomizationID +
                                                                                           PeppolIdentifierHelper.DOCUMENT_TYPE_WILDCARD_INDICATOR));

        if (aSelectedDocTypeID != null)
          if (aMatchingDocTypeConsumer.apply (aSelectedDocTypeID).isBreak ())
            return;
      }
    }
    catch (final IllegalArgumentException ex)
    {
      LOGGER.error ("Failed to split document type ID into pieces: " + ex.getMessage ());
    }
  }

  /**
   * Wildcard (DDTS) aware SMP lookup. It interprets the wildcard character
   * (<code>*</code>) appropriately and tries all possibilities. Internally it
   * works by first querying all the document types via
   * {@link #getServiceGroupOrNull(IParticipantIdentifier)} and afterwards find
   * the closest possible match.
   *
   * @param aReceiverID
   *        Receiver ID. May not be <code>null</code>.
   * @param aDocTypeID
   *        Source document type ID. May not be <code>null</code>. The document
   *        type may use any document type identifier scheme.
   * @return <code>null</code> if no matching SMP entry was found
   * @throws SMPClientException
   *         In case of error
   * @since 8.8.1
   */
  @Nullable
  public SignedServiceMetadataType getWildcardServiceMetadataOrNull (@Nonnull final IParticipantIdentifier aReceiverID,
                                                                     @Nonnull final IDocumentTypeIdentifier aDocTypeID) throws SMPClientException
  {
    ValueEnforcer.notNull (aReceiverID, "ReceiverID");
    ValueEnforcer.notNull (aDocTypeID, "DocTypeID");

    final SignedServiceMetadataType aSSM;

    // PINT/DDTS specific lookup
    LOGGER.info ("Using SMP wildcard lookup for '" +
                 aReceiverID.getURIEncoded () +
                 "' on '" +
                 aDocTypeID.getURIEncoded () +
                 "'");

    // 1. query all document types from SMP
    final ServiceGroupType aSG = getServiceGroupOrNull (aReceiverID);
    if (aSG == null)
    {
      // Invalid participant ID
      aSSM = null;
    }
    else
    {
      // Extract all document types from SMP result
      final ICommonsList <IDocumentTypeIdentifier> aSupportedDocTypes = SMPClientReadOnly.getAllDocumentTypes (aSG);

      LOGGER.info ("Found " +
                   aSupportedDocTypes.size () +
                   " supported document types for '" +
                   aReceiverID.getURIEncoded () +
                   "'");

      // Main matching
      final Wrapper <IDocumentTypeIdentifier> aMatchingDocType = new Wrapper <> ();
      forEachMatchingWildcardDocumentType (aSupportedDocTypes, aDocTypeID.getValue (), dt -> {
        aMatchingDocType.set (dt);
        return EContinue.BREAK;
      });

      final IDocumentTypeIdentifier aSelectedDocTypeID = aMatchingDocType.get ();
      if (aSelectedDocTypeID != null)
      {
        LOGGER.info ("Using '" + aSelectedDocTypeID.getURIEncoded () + "' for defacto querying the SMP");
        // Do the main SMP lookup on the metadata
        aSSM = getServiceMetadataOrNull (aReceiverID, aSelectedDocTypeID);
      }
      else
      {
        LOGGER.info ("Found no matching document type ID to be queried via ´Wildcard");
        aSSM = null;
      }
    }
    return aSSM;
  }

  /**
   * Returns a complete service group. A complete service group contains both
   * the service group and the service metadata.<br>
   * NOTE: this API is NOT supported by all SMP implementations. It is based on
   * a proprietary API provided by the Peppol reference implementation and now
   * supported by phoss SMP but not all other SMPs.
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
   * @throws SMPDNSResolutionException
   *         if DNS resolution fails
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientNotFoundException
   *         The service group id did not exist.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   */
  @Nonnull
  public static CompleteServiceGroupType getCompleteServiceGroupByDNS (@Nonnull final ISMPURLProvider aURLProvider,
                                                                       @Nonnull final ISMLInfo aSMLInfo,
                                                                       @Nonnull final IParticipantIdentifier aServiceGroupID) throws SMPClientException,
                                                                                                                              SMPDNSResolutionException
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
   * @throws SMPDNSResolutionException
   *         If DNS resolution fails
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientNotFoundException
   *         The service group id did not exist.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   */
  @Nonnull
  public static ServiceGroupType getServiceGroupByDNS (@Nonnull final ISMPURLProvider aURLProvider,
                                                       @Nonnull final ISMLInfo aSMLInfo,
                                                       @Nonnull final IParticipantIdentifier aServiceGroupID) throws SMPClientException,
                                                                                                              SMPDNSResolutionException
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
   * @throws SMPDNSResolutionException
   *         if DNS resolution fails
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientNotFoundException
   *         The service group id or document type did not exist.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   */
  @Nonnull
  public static SignedServiceMetadataType getServiceRegistrationByDNS (@Nonnull final ISMPURLProvider aURLProvider,
                                                                       @Nonnull final ISMLInfo aSMLInfo,
                                                                       @Nonnull final IParticipantIdentifier aServiceGroupID,
                                                                       @Nonnull final IDocumentTypeIdentifier aDocumentTypeID) throws SMPClientException,
                                                                                                                               SMPDNSResolutionException
  {
    return new SMPClientReadOnly (aURLProvider, aServiceGroupID, aSMLInfo).getServiceMetadata (aServiceGroupID,
                                                                                               aDocumentTypeID);
  }
}
