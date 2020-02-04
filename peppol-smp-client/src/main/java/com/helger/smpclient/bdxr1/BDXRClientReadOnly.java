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

import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.bind.JAXBElement;

import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smp.ISMPTransportProfile;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.IProcessIdentifier;
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
import com.helger.smpclient.url.IPeppolURLProvider;
import com.helger.smpclient.url.PeppolDNSResolutionException;
import com.helger.xsds.bdxr.smp1.EndpointType;
import com.helger.xsds.bdxr.smp1.ProcessType;
import com.helger.xsds.bdxr.smp1.RedirectType;
import com.helger.xsds.bdxr.smp1.ServiceGroupType;
import com.helger.xsds.bdxr.smp1.ServiceInformationType;
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
   * @throws PeppolDNSResolutionException
   *         If DNS resolution failed
   * @see IPeppolURLProvider#getSMPURIOfParticipant(IParticipantIdentifier,
   *      ISMLInfo)
   */
  public BDXRClientReadOnly (@Nonnull final IPeppolURLProvider aURLProvider,
                             @Nonnull final IParticipantIdentifier aParticipantIdentifier,
                             @Nonnull final ISMLInfo aSMLInfo) throws PeppolDNSResolutionException
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
   * @throws PeppolDNSResolutionException
   *         if DNS resolution failed
   * @see IPeppolURLProvider#getSMPURIOfParticipant(IParticipantIdentifier,
   *      String)
   */
  public BDXRClientReadOnly (@Nonnull final IPeppolURLProvider aURLProvider,
                             @Nonnull final IParticipantIdentifier aParticipantIdentifier,
                             @Nonnull @Nonempty final String sSMLZoneName) throws PeppolDNSResolutionException
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
    super (aSMPHost);
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
    return executeGenericRequest (aRequest,
                                  new SMPHttpResponseHandlerUnsigned <> (new BDXR1MarshallerServiceGroupType ()));
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
   * @see #getServiceMetadataOrNull(IParticipantIdentifier,
   *      IDocumentTypeIdentifier)
   * @deprecated Use
   *             {@link #getServiceMetadata(IParticipantIdentifier,IDocumentTypeIdentifier)}
   *             instead
   */
  @Deprecated
  @Nonnull
  public SignedServiceMetadataType getServiceRegistration (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                                           @Nonnull final IDocumentTypeIdentifier aDocumentTypeID) throws SMPClientException
  {
    return getServiceMetadata (aServiceGroupID, aDocumentTypeID);
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

    HttpGet aRequest = new HttpGet (sURI);
    SignedServiceMetadataType aMetadata = executeGenericRequest (aRequest,
                                                                 new SMPHttpResponseHandlerSigned <> (new BDXR1MarshallerSignedServiceMetadataType ()).setCheckCertificate (isCheckCertificate ()));

    // If the Redirect element is present, then follow 1 redirect.
    if (isFollowSMPRedirects ())
      if (aMetadata.getServiceMetadata () != null && aMetadata.getServiceMetadata ().getRedirect () != null)
      {
        final RedirectType aRedirect = aMetadata.getServiceMetadata ().getRedirect ();

        // Follow the redirect
        if (LOGGER.isInfoEnabled ())
          LOGGER.info ("Following a redirect from '" + sURI + "' to '" + aRedirect.getHref () + "'");
        aRequest = new HttpGet (aRedirect.getHref ());
        aMetadata = executeGenericRequest (aRequest,
                                           new SMPHttpResponseHandlerSigned <> (new BDXR1MarshallerSignedServiceMetadataType ()).setCheckCertificate (isCheckCertificate ()));

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
   * @see #getServiceMetadata(IParticipantIdentifier, IDocumentTypeIdentifier)
   * @deprecated Use
   *             {@link #getServiceMetadataOrNull(IParticipantIdentifier,IDocumentTypeIdentifier)}
   *             instead
   */
  @Deprecated
  @Nullable
  public SignedServiceMetadataType getServiceRegistrationOrNull (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                                                 @Nonnull final IDocumentTypeIdentifier aDocumentTypeID) throws SMPClientException
  {
    return getServiceMetadataOrNull (aServiceGroupID, aDocumentTypeID);
  }

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
        if (SimpleProcessIdentifier.wrap (aProcessType.getProcessIdentifier ()).hasSameContent (aProcessID))
        {
          // Filter endpoints by required transport profile
          final ICommonsList <EndpointType> aRelevantEndpoints = new CommonsArrayList <> ();
          for (final EndpointType aEndpoint : aProcessType.getServiceEndpointList ().getEndpoint ())
            if (aTransportProfile.getID ().equals (aEndpoint.getTransportProfile ()))
              aRelevantEndpoints.add (aEndpoint);

          if (aRelevantEndpoints.size () != 1)
          {
            if (LOGGER.isWarnEnabled ())
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
          return aRelevantEndpoints.getFirst ();
        }
      }
    }
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
   * @deprecated in v7.0.6. Use
   *             {@link #getEndpointCertificateBytes(EndpointType)} instead
   */
  @Deprecated
  @Nullable
  public static byte [] getEndpointCertificateString (@Nullable final EndpointType aEndpoint)
  {
    return getEndpointCertificateBytes (aEndpoint);
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
   * @deprecated in v7.0.6. Use
   *             {@link #getEndpointCertificateBytes(IParticipantIdentifier,IDocumentTypeIdentifier,IProcessIdentifier,ISMPTransportProfile)}
   *             instead
   */
  @Deprecated
  @Nullable
  public byte [] getEndpointCertificateString (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                               @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                               @Nonnull final IProcessIdentifier aProcessID,
                                               @Nonnull final ISMPTransportProfile aTransportProfile) throws SMPClientException
  {
    return getEndpointCertificateBytes (aServiceGroupID, aDocumentTypeID, aProcessID, aTransportProfile);
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
   * @throws PeppolDNSResolutionException
   *         if DNS resolution fails
   */
  @Nonnull
  public static ServiceGroupType getServiceGroupByDNS (@Nonnull final IPeppolURLProvider aURLProvider,
                                                       @Nonnull final ISMLInfo aSMLInfo,
                                                       @Nonnull final IParticipantIdentifier aServiceGroupID) throws SMPClientException,
                                                                                                              PeppolDNSResolutionException
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
   * @throws PeppolDNSResolutionException
   *         if DNS resolution fails
   */
  @Nonnull
  public static SignedServiceMetadataType getServiceRegistrationByDNS (@Nonnull final IPeppolURLProvider aURLProvider,
                                                                       @Nonnull final ISMLInfo aSMLInfo,
                                                                       @Nonnull final IParticipantIdentifier aServiceGroupID,
                                                                       @Nonnull final IDocumentTypeIdentifier aDocumentTypeID) throws SMPClientException,
                                                                                                                               PeppolDNSResolutionException
  {
    return new BDXRClientReadOnly (aURLProvider, aServiceGroupID, aSMLInfo).getServiceMetadata (aServiceGroupID,
                                                                                                aDocumentTypeID);
  }
}
