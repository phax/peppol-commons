/*
 * Copyright (C) 2015-2023 Philip Helger
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
package com.helger.smpclient.bdxr1;

import java.net.URI;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Locale;
import java.util.function.Consumer;

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
import com.helger.commons.string.StringHelper;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smp.ISMPTransportProfile;
import com.helger.peppolid.CIdentifier;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.simple.process.SimpleProcessIdentifier;
import com.helger.security.certificate.CertificateHelper;
import com.helger.smpclient.bdxr1.marshal.BDXR1MarshallerServiceGroupType;
import com.helger.smpclient.bdxr1.marshal.BDXR1MarshallerSignedServiceMetadataType;
import com.helger.smpclient.exception.SMPClientBadRequestException;
import com.helger.smpclient.exception.SMPClientException;
import com.helger.smpclient.exception.SMPClientNotFoundException;
import com.helger.smpclient.exception.SMPClientUnauthorizedException;
import com.helger.smpclient.httpclient.AbstractGenericSMPClient;
import com.helger.smpclient.httpclient.SMPHttpResponseHandlerSigned;
import com.helger.smpclient.httpclient.SMPHttpResponseHandlerUnsigned;
import com.helger.smpclient.url.ISMPURLProvider;
import com.helger.smpclient.url.SMPDNSResolutionException;
import com.helger.xsds.bdxr.smp1.EndpointType;
import com.helger.xsds.bdxr.smp1.ProcessType;
import com.helger.xsds.bdxr.smp1.RedirectType;
import com.helger.xsds.bdxr.smp1.ServiceGroupType;
import com.helger.xsds.bdxr.smp1.ServiceInformationType;
import com.helger.xsds.bdxr.smp1.ServiceMetadataReferenceType;
import com.helger.xsds.bdxr.smp1.ServiceMetadataType;
import com.helger.xsds.bdxr.smp1.SignedServiceMetadataType;
import com.helger.xsds.xmldsig.X509DataType;

/**
 * This class is used for calling the OASIS BDXR SMP v1 REST interface. This
 * class only contains the read-only methods defined in the SMP specification
 * and nothing else.
 * <p>
 * Note: this class is also licensed under Apache 2 license, as it was not part
 * of the original implementation
 * </p>
 *
 * @author Philip Helger
 */
public class BDXRClientReadOnly extends AbstractGenericSMPClient <BDXRClientReadOnly> implements
                                IBDXRServiceGroupProvider,
                                IBDXRServiceMetadataProvider
{
  public static final String URL_PART_SERVICES = "services";

  private static final Logger LOGGER = LoggerFactory.getLogger (BDXRClientReadOnly.class);

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
   *         If DNS resolution failed
   * @see ISMPURLProvider#getSMPURIOfParticipant(IParticipantIdentifier,
   *      ISMLInfo)
   */
  public BDXRClientReadOnly (@Nonnull final ISMPURLProvider aURLProvider,
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
   *         if DNS resolution failed
   * @see ISMPURLProvider#getSMPURIOfParticipant(IParticipantIdentifier, String)
   */
  public BDXRClientReadOnly (@Nonnull final ISMPURLProvider aURLProvider,
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
  public BDXRClientReadOnly (@Nonnull final URI aSMPHost)
  {
    super (aSMPHost, false);
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
      LOGGER.debug ("BDXRClient getServiceGroup@" + sURI);

    final HttpGet aRequest = new HttpGet (sURI);
    final BDXR1MarshallerServiceGroupType aMarshaller = new BDXR1MarshallerServiceGroupType (isXMLSchemaValidation ());
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
   * Extract all parsable document types from the passed Service group.
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
                                '/' +
                                URL_PART_SERVICES +
                                '/';
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
      LOGGER.debug ("BDXRClient getServiceRegistration@" + sURI);

    final boolean bXSDValidation = isXMLSchemaValidation ();
    final boolean bVerifySignature = isVerifySignature ();
    final KeyStore aTrustStore = getTrustStore ();

    if (bVerifySignature && aTrustStore == null)
      LOGGER.error ("BDXR SMP client Verify Signature is enabled, but no TrustStore is provided. This will not work.");

    HttpGet aRequest = new HttpGet (sURI);
    BDXR1MarshallerSignedServiceMetadataType aMarshaller = new BDXR1MarshallerSignedServiceMetadataType (bXSDValidation);
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

        // Create a new Marshaller to make sure customization is easy
        aMarshaller = new BDXR1MarshallerSignedServiceMetadataType (bXSDValidation);
        customizeMarshaller (aMarshaller);
        aMetadata = executeGenericRequest (aRequest,
                                           new SMPHttpResponseHandlerSigned <> (aMarshaller,
                                                                                aTrustStore).setVerifySignature (bVerifySignature));

        // Check that the certificateUID is correct
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
   * @return <code>null</code> if no matching endpoint was found
   * @since 8.2.6
   */
  @Nullable
  public static EndpointType getEndpoint (@Nonnull final ServiceMetadataType aServiceMetadata,
                                          @Nonnull final IProcessIdentifier aProcessID,
                                          @Nonnull final ISMPTransportProfile aTransportProfile)
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

    // Iterate all processes
    for (final ProcessType aProcessType : aServiceInformation.getProcessList ().getProcess ())
    {
      // Matches the requested one?
      if (SimpleProcessIdentifier.wrap (aProcessType.getProcessIdentifier ()).hasSameContent (aProcessID))
      {
        // Filter endpoints by required transport profile
        final ICommonsList <EndpointType> aRelevantEndpoints = new CommonsArrayList <> ();
        for (final EndpointType aEndpoint : aProcessType.getServiceEndpointList ().getEndpoint ())
          if (aTransportProfile.getID ().equals (aEndpoint.getTransportProfile ()))
            aRelevantEndpoints.add (aEndpoint);

        if (aRelevantEndpoints.size () != 1)
        {
          LOGGER.warn ("Found " +
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
        final EndpointType ret = aRelevantEndpoints.getFirst ();

        if (LOGGER.isDebugEnabled ())
          LOGGER.debug ("Found matching endpoint: " + ret);
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
    return aEndpoint == null ? null : aEndpoint.getEndpointURI ();
  }

  /**
   * Get the certificate bytes from the provided SMP endpoint.
   *
   * @param aEndpoint
   *        The endpoint to be used. May be <code>null</code>.
   * @return <code>null</code> if the endpoint is <code>null</code> if the
   *         endpoint has no certificate.
   * @since 7.0.6
   */
  @Nullable
  public static byte [] getEndpointCertificateBytes (@Nullable final EndpointType aEndpoint)
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
    final byte [] aCertBytes = getEndpointCertificateBytes (aEndpoint);
    return CertificateHelper.convertByteArrayToCertficateDirect (aCertBytes);
  }

  /**
   * Get the certificate bytes from the specified endpoint.
   *
   * @param aEndpoint
   *        The endpoint to be used. May be <code>null</code>.
   * @return <code>null</code> if no such endpoint exists, or if the endpoint
   *         has no certificate or if the certificate is invalid.
   * @since 8.1.2
   */
  @Nullable
  public static X509Certificate getEndpointCertificateOrNull (@Nullable final EndpointType aEndpoint)
  {
    try
    {
      return getEndpointCertificate (aEndpoint);
    }
    catch (final CertificateException ex)
    {
      return null;
    }
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
   * @throws SMPDNSResolutionException
   *         if DNS resolution fails
   */
  @Nonnull
  public static ServiceGroupType getServiceGroupByDNS (@Nonnull final ISMPURLProvider aURLProvider,
                                                       @Nonnull final ISMLInfo aSMLInfo,
                                                       @Nonnull final IParticipantIdentifier aServiceGroupID) throws SMPClientException,
                                                                                                              SMPDNSResolutionException
  {
    return new BDXRClientReadOnly (aURLProvider, aServiceGroupID, aSMLInfo).getServiceGroup (aServiceGroupID);
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
   * @throws SMPDNSResolutionException
   *         if DNS resolution fails
   */
  @Nonnull
  public static SignedServiceMetadataType getServiceRegistrationByDNS (@Nonnull final ISMPURLProvider aURLProvider,
                                                                       @Nonnull final ISMLInfo aSMLInfo,
                                                                       @Nonnull final IParticipantIdentifier aServiceGroupID,
                                                                       @Nonnull final IDocumentTypeIdentifier aDocumentTypeID) throws SMPClientException,
                                                                                                                               SMPDNSResolutionException
  {
    return new BDXRClientReadOnly (aURLProvider, aServiceGroupID, aSMLInfo).getServiceMetadata (aServiceGroupID,
                                                                                                aDocumentTypeID);
  }
}
