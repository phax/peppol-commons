/*
 * Copyright (C) 2015-2025 Philip Helger
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
package com.helger.smpclient.bdxr2;

import java.net.URI;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smp.ISMPTransportProfile;
import com.helger.peppolid.CIdentifier;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.simple.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppolid.simple.process.SimpleProcessIdentifier;
import com.helger.security.certificate.CertificateHelper;
import com.helger.smpclient.bdxr2.marshal.BDXR2MarshallerServiceGroup;
import com.helger.smpclient.bdxr2.marshal.BDXR2MarshallerServiceMetadata;
import com.helger.smpclient.exception.SMPClientBadRequestException;
import com.helger.smpclient.exception.SMPClientException;
import com.helger.smpclient.exception.SMPClientNotFoundException;
import com.helger.smpclient.exception.SMPClientParticipantNotFoundException;
import com.helger.smpclient.exception.SMPClientUnauthorizedException;
import com.helger.smpclient.httpclient.AbstractGenericSMPClient;
import com.helger.smpclient.httpclient.SMPHttpResponseHandlerSigned;
import com.helger.smpclient.httpclient.SMPHttpResponseHandlerUnsigned;
import com.helger.smpclient.redirect.ISMPFollowRedirectCallback;
import com.helger.smpclient.url.ISMPURLProvider;
import com.helger.smpclient.url.SMPDNSResolutionException;
import com.helger.xsds.bdxr.smp2.ServiceGroupType;
import com.helger.xsds.bdxr.smp2.ServiceMetadataType;
import com.helger.xsds.bdxr.smp2.ac.CertificateType;
import com.helger.xsds.bdxr.smp2.ac.EndpointType;
import com.helger.xsds.bdxr.smp2.ac.ProcessMetadataType;
import com.helger.xsds.bdxr.smp2.ac.ProcessType;
import com.helger.xsds.bdxr.smp2.ac.RedirectType;
import com.helger.xsds.bdxr.smp2.ac.ServiceReferenceType;
import com.helger.xsds.bdxr.smp2.bc.IDType;
import com.helger.xsds.xmldsig.X509DataType;

import jakarta.xml.bind.JAXBElement;

/**
 * This class is used for calling the OASIS BDXR SMP v2 REST interface. This class only contains the
 * read-only methods defined in the SMP specification and nothing else.
 * <p>
 * Note: this class is also licensed under Apache 2 license, as it was not part of the original
 * implementation
 * </p>
 *
 * @author Philip Helger
 */
public class BDXR2ClientReadOnly extends AbstractGenericSMPClient <BDXR2ClientReadOnly> implements
                                 IBDXR2ServiceGroupProvider,
                                 IBDXR2ServiceMetadataProvider
{
  public static final String PATH_OASIS_BDXR_SMP_2 = "bdxr-smp-2/";
  public static final String URL_PART_SERVICES = "services";

  private static final Logger LOGGER = LoggerFactory.getLogger (BDXR2ClientReadOnly.class);

  /**
   * Constructor with SML lookup
   *
   * @param aURLProvider
   *        The URL provider to be used. May not be <code>null</code>.
   * @param aParticipantIdentifier
   *        The participant identifier to be used. Required to build the SMP access URI.
   * @param aSMLInfo
   *        The SML to be used. Required to build the SMP access URI.
   * @throws SMPDNSResolutionException
   *         If DNS resolution failed
   * @see ISMPURLProvider#getSMPURIOfParticipant(IParticipantIdentifier, ISMLInfo)
   */
  public BDXR2ClientReadOnly (@Nonnull final ISMPURLProvider aURLProvider,
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
   *        The participant identifier to be used. Required to build the SMP access URI.
   * @param sSMLZoneName
   *        The SML DNS zone name to be used. Required to build the SMP access URI. Must end with a
   *        trailing dot (".") and may neither be <code>null</code> nor empty to build a correct
   *        URL. May not start with "http://". Example: <code>sml.peppolcentral.org.</code>
   * @throws SMPDNSResolutionException
   *         if DNS resolution failed
   * @see ISMPURLProvider#getSMPURIOfParticipant(IParticipantIdentifier, String)
   */
  public BDXR2ClientReadOnly (@Nonnull final ISMPURLProvider aURLProvider,
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
   *        The address of the SMP service. Must be port 80 and basic http only (no https!).
   *        Example: http://smpcompany.company.org
   */
  public BDXR2ClientReadOnly (@Nonnull final URI aSMPHost)
  {
    super (aSMPHost, false);
  }

  /**
   * Returns a service group. A service group references to the service metadata. This is a
   * specification compliant method.
   *
   * @param aServiceGroupID
   *        The service group id corresponding to the service group which one wants to get.
   * @return The service group. Never <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientParticipantNotFoundException
   *         The service group id does not exist in the network.
   * @throws SMPClientNotFoundException
   *         The service group id or document types did not exist.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   * @see #getServiceGroupOrNull(IParticipantIdentifier)
   */
  @Nonnull
  public ServiceGroupType getServiceGroup (@Nonnull final IParticipantIdentifier aServiceGroupID) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");

    final String sURI = getSMPHostURI () + PATH_OASIS_BDXR_SMP_2 + aServiceGroupID.getURIPercentEncoded ();
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("BDXR2Client getServiceGroup@" + sURI);

    final HttpGet aRequest = new HttpGet (sURI);
    final BDXR2MarshallerServiceGroup aMarshaller = new BDXR2MarshallerServiceGroup ();
    aMarshaller.setUseSchema (isXMLSchemaValidation ());
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
    catch (final SMPClientNotFoundException | SMPClientParticipantNotFoundException ex)
    {
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Found no ServiceGroup");
      return null;
    }
  }

  /**
   * Extract all document types from the passed Service group.
   *
   * @param aSG
   *        The service group to parse. May be <code>null</code>.
   * @param aIdentifierFactory
   *        The identifier factory to be used. May not be <code>null</code>.
   * @return Never <code>null</code> but a maybe empty list.
   * @since 8.0.4
   */
  @Nonnull
  public static ICommonsList <IDocumentTypeIdentifier> getAllDocumentTypes (@Nullable final ServiceGroupType aSG,
                                                                            @Nonnull final IIdentifierFactory aIdentifierFactory)
  {
    ValueEnforcer.notNull (aIdentifierFactory, "IdentifierFactory");

    final ICommonsList <IDocumentTypeIdentifier> ret = new CommonsArrayList <> ();

    if (aSG != null)
      for (final ServiceReferenceType aSMR : aSG.getServiceReference ())
      {
        final IDType aID = aSMR.getID ();
        if (aID != null)
        {
          final IDocumentTypeIdentifier aDocType = aIdentifierFactory.createDocumentTypeIdentifier (aID.getSchemeID (),
                                                                                                    aID.getValue ());
          if (aDocType != null)
          {
            // Found a document type
            ret.add (aDocType);
          }
        }
      }

    return ret;
  }

  /**
   * Gets a signed service metadata object given by its service group id and its document type. This
   * is a specification compliant method.
   *
   * @param aServiceGroupID
   *        The service group id of the service metadata to get. May not be <code>null</code>.
   * @param aDocumentTypeID
   *        The document type of the service metadata to get. May not be <code>null</code>.
   * @return A signed service metadata object. Never <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientParticipantNotFoundException
   *         The service group id does not exist in the network.
   * @throws SMPClientNotFoundException
   *         The service group id or document types did not exist.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   * @see #getServiceMetadataOrNull(IParticipantIdentifier, IDocumentTypeIdentifier)
   */
  @Nonnull
  public ServiceMetadataType getServiceMetadata (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                                 @Nonnull final IDocumentTypeIdentifier aDocumentTypeID) throws SMPClientException
  {
    return getServiceMetadata (aServiceGroupID, aDocumentTypeID, null);
  }

  /**
   * Gets a signed service metadata object given by its service group id and its document type. This
   * is a specification compliant method.
   *
   * @param aServiceGroupID
   *        The service group id of the service metadata to get. May not be <code>null</code>.
   * @param aDocumentTypeID
   *        The document type of the service metadata to get. May not be <code>null</code>.
   * @param aFollowRedirectCallback
   *        The optional callback to be invoked in case of an SMP redirect. May be
   *        <code>null</code>.
   * @return A signed service metadata object. Never <code>null</code>.
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientParticipantNotFoundException
   *         The service group id does not exist in the network.
   * @throws SMPClientNotFoundException
   *         The service group id or document types did not exist.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   * @see #getServiceMetadataOrNull(IParticipantIdentifier, IDocumentTypeIdentifier)
   * @since 10.5.0
   */
  @Nonnull
  public ServiceMetadataType getServiceMetadata (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                                 @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                                 @Nullable final ISMPFollowRedirectCallback aFollowRedirectCallback) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");
    ValueEnforcer.notNull (aDocumentTypeID, "DocumentTypeID");

    final String sURI = getSMPHostURI () +
                        PATH_OASIS_BDXR_SMP_2 +
                        aServiceGroupID.getURIPercentEncoded () +
                        "/" +
                        URL_PART_SERVICES +
                        "/" +
                        aDocumentTypeID.getURIPercentEncoded ();
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("BDXR2Client getServiceRegistration@" + sURI);

    final boolean bXSDValidation = isXMLSchemaValidation ();
    final boolean bVerifySignature = isVerifySignature ();
    final boolean bSecureValidation = isSecureValidation ();
    final KeyStore aTrustStore = getTrustStore ();

    if (bVerifySignature && aTrustStore == null)
      LOGGER.error ("BDXR2 SMP client Verify Signature is enabled, but no TrustStore is provided. This will not work.");

    ServiceMetadataType aMetadata;
    {
      final HttpGet aRequest = new HttpGet (sURI);
      final BDXR2MarshallerServiceMetadata aMarshaller = new BDXR2MarshallerServiceMetadata ();
      aMarshaller.setUseSchema (bXSDValidation);
      customizeMarshaller (aMarshaller);

      // Deal with signed responses
      final SMPHttpResponseHandlerSigned <ServiceMetadataType> aResponseHandler = new SMPHttpResponseHandlerSigned <> (aMarshaller,
                                                                                                                       aTrustStore);
      aResponseHandler.setVerifySignature (bVerifySignature);
      aResponseHandler.setSecureValidation (bSecureValidation);

      // Main execution
      aMetadata = executeGenericRequest (aRequest, aResponseHandler);

      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Received response: " + aMetadata);
    }

    if (!SimpleDocumentTypeIdentifier.wrap (aMetadata.getID ()).hasSameContent (aDocumentTypeID))
    {
      // Inconsistency between request and response
      throw new SMPClientException ("Requested document type '" +
                                    aDocumentTypeID.getURIEncoded () +
                                    "' and received '" +
                                    CIdentifier.getURIEncoded (aMetadata.getID ()) +
                                    "' - mismatch. Ignoring request.");
    }

    // If a Redirect element is present, then follow 1 redirect.
    for (final ProcessMetadataType aPM : aMetadata.getProcessMetadata ())
    {
      final RedirectType aRedirect = aPM.getRedirect ();
      if (aRedirect != null)
      {
        final boolean bWillFollow = isFollowSMPRedirects ();

        // Call callback (if provided)
        if (aFollowRedirectCallback != null)
          aFollowRedirectCallback.onFollowSMPRedirect (bWillFollow, aRedirect.getPublisherURIValue ());

        if (bWillFollow)
        {
          // Follow the redirect
          LOGGER.info ("Following a redirect from '" + sURI + "' to '" + aRedirect.getPublisherURIValue () + "'");

          final HttpGet aRequest = new HttpGet (aRedirect.getPublisherURIValue ());

          // Create a new Marshaller to make sure customization is easy
          final BDXR2MarshallerServiceMetadata aMarshaller = new BDXR2MarshallerServiceMetadata ();
          aMarshaller.setUseSchema (bXSDValidation);
          customizeMarshaller (aMarshaller);

          // Deal with signed responses
          final SMPHttpResponseHandlerSigned <ServiceMetadataType> aResponseHandler = new SMPHttpResponseHandlerSigned <> (aMarshaller,
                                                                                                                           aTrustStore);
          aResponseHandler.setVerifySignature (bVerifySignature);
          aResponseHandler.setSecureValidation (bSecureValidation);

          // Main execution
          aMetadata = executeGenericRequest (aRequest, aResponseHandler);

          // Check that the certificateUID is correct.
          boolean bCertificateSubjectFound = false;
          if (aMetadata.hasSignatureEntries ())
            outer: for (final Object aObj : aMetadata.getSignatureAtIndex (0).getKeyInfo ().getContent ())
              if (aObj instanceof JAXBElement <?>)
              {
                final Object aInfoValue = ((JAXBElement <?>) aObj).getValue ();
                if (aInfoValue instanceof X509DataType)
                {
                  final X509DataType aX509Data = (X509DataType) aInfoValue;
                  for (final Object aX509Obj : aX509Data.getX509IssuerSerialOrX509SKIOrX509SubjectName ())
                    if (aX509Obj instanceof JAXBElement <?>)
                    {
                      final JAXBElement <?> aX509element = (JAXBElement <?>) aX509Obj;
                      // Find the first subject (of type string)
                      if (aX509element.getValue () instanceof X509Certificate)
                      {
                        final X509Certificate aSecondCert = (X509Certificate) aX509element.getValue ();

                        // Check all certs of the source redirect
                        boolean bFound = false;
                        final ICommonsList <X509Certificate> aAllRedirectCerts = new CommonsArrayList <> ();
                        for (final CertificateType aCT : aRedirect.getCertificate ())
                        {
                          try
                          {
                            final X509Certificate aRedirectCert = CertificateHelper.convertByteArrayToCertficate (aCT.getContentBinaryObjectValue ());
                            if (aRedirectCert != null)
                            {
                              aAllRedirectCerts.add (aRedirectCert);
                              // Certificate match?
                              if (aRedirectCert.equals (aSecondCert))
                              {
                                bFound = true;
                                break;
                              }
                            }
                          }
                          catch (final CertificateException ex)
                          {
                            // Error in certificate in SMP response
                            LOGGER.error ("SMP Redirect contains an invalid certificate", ex);
                          }
                        }

                        if (!bFound)
                          throw new SMPClientException ("No certificate of the redirect matched the provided certificate. Retrieved certificate is '" +
                                                        aSecondCert +
                                                        "'. Allowed certificates according to the redirect are: " +
                                                        aAllRedirectCerts);

                        bCertificateSubjectFound = true;
                        break outer;
                      }
                    }
                }
              }

          if (!bCertificateSubjectFound)
            throw new SMPClientException ("The X509 certificate did not contain a certificate subject.");
        }
        else
        {
          if (LOGGER.isDebugEnabled ())
            LOGGER.debug ("Following SMP redirects is disabled");
        }
        break;
      }
    }

    return aMetadata;
  }

  @Nullable
  public ServiceMetadataType getServiceMetadataOrNull (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                                       @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                                       @Nullable final ISMPFollowRedirectCallback aFollowRedirectCallback) throws SMPClientException
  {
    try
    {
      return getServiceMetadata (aServiceGroupID, aDocumentTypeID, aFollowRedirectCallback);
    }
    catch (final SMPClientNotFoundException | SMPClientParticipantNotFoundException ex)
    {
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Found no ServiceMetadata");
      return null;
    }
  }

  /**
   * Extract the Endpoint from the ServiceMetadata that matches the passed process ID and the
   * optional required transport profile.
   *
   * @param aServiceMetadata
   *        The service meta data object (e.g. from a call to
   *        {@link #getServiceMetadataOrNull(IParticipantIdentifier, IDocumentTypeIdentifier)} . May
   *        not be <code>null</code>.
   * @param aProcessID
   *        The process identifier to be looked up. May not be <code>null</code> .
   * @param aTransportProfile
   *        The required transport profile to be used. May not be <code>null</code>.
   * @return <code>null</code> if no matching endpoint was found
   */
  @Nullable
  public static EndpointType getEndpoint (@Nonnull final ServiceMetadataType aServiceMetadata,
                                          @Nonnull final IProcessIdentifier aProcessID,
                                          @Nonnull final ISMPTransportProfile aTransportProfile)
  {
    ValueEnforcer.notNull (aServiceMetadata, "SignedServiceMetadata");
    ValueEnforcer.notNull (aProcessID, "ProcessID");
    ValueEnforcer.notNull (aTransportProfile, "TransportProfile");

    // Iterate all processes
    for (final ProcessMetadataType aPM : aServiceMetadata.getProcessMetadata ())
    {
      boolean bMatchesProcess = false;
      for (final ProcessType aP : aPM.getProcess ())
        if (SimpleProcessIdentifier.wrap (aP.getID ()).hasSameContent (aProcessID))
        {
          bMatchesProcess = true;
          break;
        }

      if (bMatchesProcess)
      {
        final ICommonsList <EndpointType> aRelevantEndpoints = new CommonsArrayList <> ();
        for (final EndpointType aEndpoint : aPM.getEndpoint ())
          if (aTransportProfile.getID ().equals (aEndpoint.getTransportProfileIDValue ()))
            aRelevantEndpoints.add (aEndpoint);

        if (aRelevantEndpoints.size () != 1)
        {
          LOGGER.warn ("Found " +
                       aRelevantEndpoints.size () +
                       " endpoints for process '" +
                       aProcessID.getURIEncoded () +
                       "' and transport profile '" +
                       aTransportProfile.getID () +
                       "'" +
                       (aRelevantEndpoints.isEmpty () ? "" : ": " +
                                                             aRelevantEndpoints.toString () +
                                                             " - using the first one"));
        }

        // Use the first endpoint or null
        final EndpointType ret = aRelevantEndpoints.getFirstOrNull ();
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
   * @return <code>null</code> if the endpoint is <code>null</code> if the endpoint has no address
   *         URI.
   */
  @Nullable
  public static String getEndpointAddress (@Nullable final EndpointType aEndpoint)
  {
    return aEndpoint == null ? null : aEndpoint.getAddressURIValue ();
  }

  /**
   * Get the certificate bytes from the provided SMP endpoint.<br>
   * Note: if the endpoint has more than one certificate, the first one is returned.
   *
   * @param aEndpoint
   *        The endpoint to be used. May be <code>null</code>.
   * @return <code>null</code> if the endpoint is <code>null</code> if the endpoint has no
   *         certificate.
   */
  @Nullable
  public static byte [] getEndpointCertificateBytes (@Nullable final EndpointType aEndpoint)
  {
    if (aEndpoint == null)
      return null;
    if (aEndpoint.getCertificateCount () == 0)
      return null;
    return aEndpoint.getCertificateAtIndex (0).getContentBinaryObjectValue ();
  }

  /**
   * Get the certificate bytes from the specified endpoint.
   *
   * @param aEndpoint
   *        The endpoint to be used. May be <code>null</code>.
   * @return <code>null</code> if no such endpoint exists, or if the endpoint has no certificate
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
   * Returns a service group. A service group references to the service metadata.
   *
   * @param aURLProvider
   *        The URL provider to be used. May not be <code>null</code>.
   * @param aSMLInfo
   *        The SML object to be used
   * @param aServiceGroupID
   *        The service group id corresponding to the service group which one wants to get.
   * @return The service group
   * @throws SMPClientException
   *         in case something goes wrong
   * @throws SMPClientUnauthorizedException
   *         A HTTP Forbidden was received, should not happen.
   * @throws SMPClientParticipantNotFoundException
   *         The service group id does not exist in the network.
   * @throws SMPClientNotFoundException
   *         The service group id or document types did not exist.
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
    return new BDXR2ClientReadOnly (aURLProvider, aServiceGroupID, aSMLInfo).getServiceGroup (aServiceGroupID);
  }

  /**
   * Gets a signed service metadata object given by its service group id and its document type.
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
   * @throws SMPClientParticipantNotFoundException
   *         The service group id does not exist in the network.
   * @throws SMPClientNotFoundException
   *         The service group id or document types did not exist.
   * @throws SMPClientBadRequestException
   *         The request was not well formed.
   * @throws SMPDNSResolutionException
   *         if DNS resolution fails
   */
  @Nonnull
  public static ServiceMetadataType getServiceRegistrationByDNS (@Nonnull final ISMPURLProvider aURLProvider,
                                                                 @Nonnull final ISMLInfo aSMLInfo,
                                                                 @Nonnull final IParticipantIdentifier aServiceGroupID,
                                                                 @Nonnull final IDocumentTypeIdentifier aDocumentTypeID) throws SMPClientException,
                                                                                                                         SMPDNSResolutionException
  {
    return new BDXR2ClientReadOnly (aURLProvider, aServiceGroupID, aSMLInfo).getServiceMetadata (aServiceGroupID,
                                                                                                 aDocumentTypeID);
  }
}
