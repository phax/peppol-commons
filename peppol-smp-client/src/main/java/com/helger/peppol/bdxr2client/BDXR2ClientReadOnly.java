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
package com.helger.peppol.bdxr2client;

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
import com.helger.commons.io.stream.NonBlockingByteArrayInputStream;
import com.helger.peppol.bdxr.smp2.marshal.BDXR2ServiceGroupMarshaller;
import com.helger.peppol.bdxr.smp2.marshal.BDXR2ServiceMetadataMarshaller;
import com.helger.peppol.httpclient.AbstractGenericSMPClient;
import com.helger.peppol.httpclient.SMPHttpResponseHandlerSigned;
import com.helger.peppol.httpclient.SMPHttpResponseHandlerUnsigned;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smp.ISMPTransportProfile;
import com.helger.peppol.smpclient.exception.SMPClientBadRequestException;
import com.helger.peppol.smpclient.exception.SMPClientException;
import com.helger.peppol.smpclient.exception.SMPClientNotFoundException;
import com.helger.peppol.smpclient.exception.SMPClientUnauthorizedException;
import com.helger.peppol.url.IPeppolURLProvider;
import com.helger.peppol.url.PeppolDNSResolutionException;
import com.helger.peppolid.CIdentifier;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.simple.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppolid.simple.process.SimpleProcessIdentifier;
import com.helger.security.certificate.CertificateHelper;
import com.helger.xsds.bdxr.smp2.ServiceGroupType;
import com.helger.xsds.bdxr.smp2.ServiceMetadataType;
import com.helger.xsds.bdxr.smp2.ac.CertificateType;
import com.helger.xsds.bdxr.smp2.ac.EndpointType;
import com.helger.xsds.bdxr.smp2.ac.ProcessMetadataType;
import com.helger.xsds.bdxr.smp2.ac.ProcessType;
import com.helger.xsds.bdxr.smp2.ac.RedirectType;
import com.helger.xsds.xmldsig.X509DataType;

/**
 * This class is used for calling the OASIS BDXR SMP v2 REST interface. This
 * class only contains the read-only methods defined in the SMP specification
 * and nothing else.
 * <p>
 * Note: this class is also licensed under Apache 2 license, as it was not part
 * of the original implementation
 * </p>
 *
 * @author Philip Helger
 */
public class BDXR2ClientReadOnly extends AbstractGenericSMPClient <BDXR2ClientReadOnly>
{
  public static final String PATH_OASIS_BDXR_SMP_2 = "bdxr-smp-2/";

  private static final Logger LOGGER = LoggerFactory.getLogger (BDXR2ClientReadOnly.class);

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
  public BDXR2ClientReadOnly (@Nonnull final IPeppolURLProvider aURLProvider,
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
  public BDXR2ClientReadOnly (@Nonnull final IPeppolURLProvider aURLProvider,
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
  public BDXR2ClientReadOnly (@Nonnull final URI aSMPHost)
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

    final String sURI = getSMPHostURI () + PATH_OASIS_BDXR_SMP_2 + aServiceGroupID.getURIPercentEncoded ();
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("BDXR2Client getServiceGroup@" + sURI);

    final HttpGet aRequest = new HttpGet (sURI);
    return executeGenericRequest (aRequest, new SMPHttpResponseHandlerUnsigned <> (new BDXR2ServiceGroupMarshaller ()));
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
  public ServiceMetadataType getServiceRegistration (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                                     @Nonnull final IDocumentTypeIdentifier aDocumentTypeID) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");
    ValueEnforcer.notNull (aDocumentTypeID, "DocumentTypeID");

    final String sURI = getSMPHostURI () +
                        PATH_OASIS_BDXR_SMP_2 +
                        aServiceGroupID.getURIPercentEncoded () +
                        "/services/" +
                        aDocumentTypeID.getURIPercentEncoded ();
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("BDXR2Client getServiceRegistration@" + sURI);

    HttpGet aRequest = new HttpGet (sURI);
    ServiceMetadataType aMetadata = executeGenericRequest (aRequest,
                                                           new SMPHttpResponseHandlerSigned <> (new BDXR2ServiceMetadataMarshaller ()).setCheckCertificate (isCheckCertificate ()));
    if (!SimpleDocumentTypeIdentifier.wrap (aMetadata.getID ()).equals (aDocumentTypeID))
    {
      // Inconsistency between request and response
      throw new SMPClientException ("Requested document type '" +
                                    aDocumentTypeID.getURIEncoded () +
                                    "' and received '" +
                                    CIdentifier.getURIEncoded (aMetadata.getID ()) +
                                    "' - mismatch. Ignoring request.");
    }

    // If the Redirect element is present, then follow 1 redirect.
    for (final ProcessMetadataType aPM : aMetadata.getProcessMetadata ())
    {
      final RedirectType aRedirect = aPM.getRedirect ();
      if (aRedirect != null)
      {
        // Follow the redirect
        if (LOGGER.isInfoEnabled ())
          LOGGER.info ("Following a redirect from '" + sURI + "' to '" + aRedirect.getPublisherURIValue () + "'");

        aRequest = new HttpGet (aRedirect.getPublisherURIValue ());
        aMetadata = executeGenericRequest (aRequest,
                                           new SMPHttpResponseHandlerSigned <> (new BDXR2ServiceMetadataMarshaller ()).setCheckCertificate (isCheckCertificate ()));

        // Check that the certificateUID is correct.
        boolean bCertificateSubjectFound = false;
        if (aMetadata.hasSignatureEntries ())
          outer: for (final Object aObj : aMetadata.getSignatureAtIndex (0).getKeyInfo ().getContent ())
          {
            final Object aInfoValue = ((JAXBElement <?>) aObj).getValue ();
            if (aInfoValue instanceof X509DataType)
            {
              final X509DataType aX509Data = (X509DataType) aInfoValue;
              for (final Object aX509Obj : aX509Data.getX509IssuerSerialOrX509SKIOrX509SubjectName ())
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
  public ServiceMetadataType getServiceRegistrationOrNull (@Nonnull final IParticipantIdentifier aServiceGroupID,
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
    final ServiceMetadataType aSignedServiceMetadata = getServiceRegistrationOrNull (aServiceGroupID, aDocumentTypeID);
    return aSignedServiceMetadata == null ? null : getEndpoint (aSignedServiceMetadata, aProcessID, aTransportProfile);
  }

  /**
   * Extract the Endpoint from the signedServiceMetadata that matches the passed
   * process ID and the optional required transport profile.
   *
   * @param aServiceMetadata
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
        if (SimpleProcessIdentifier.wrap (aP.getID ()).equals (aProcessID))
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
          if (LOGGER.isWarnEnabled ())
            LOGGER.warn ("Found " +
                         aRelevantEndpoints.size () +
                         " endpoints for process '" +
                         aProcessID.getURIEncoded () +
                         "' and transport profile '" +
                         aTransportProfile.getID () +
                         "'" +
                         (aRelevantEndpoints.isEmpty () ? ""
                                                        : ": " +
                                                          aRelevantEndpoints.toString () +
                                                          " - using the first one"));
        }

        // Use the first endpoint or null
        return aRelevantEndpoints.getFirst ();
      }
    }
    return null;
  }

  @Nullable
  public static String getEndpointAddress (@Nullable final EndpointType aEndpoint)
  {
    return aEndpoint == null ? null : aEndpoint.getAddressURIValue ();
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
  public static byte [] getEndpointCertificateBytes (@Nullable final EndpointType aEndpoint)
  {
    if (aEndpoint == null)
      return null;
    if (aEndpoint.getCertificateCount () == 0)
      return null;
    return aEndpoint.getCertificateAtIndex (0).getContentBinaryObjectValue ();
  }

  @Nullable
  public byte [] getEndpointCertificateBytes (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                              @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                              @Nonnull final IProcessIdentifier aProcessID,
                                              @Nonnull final ISMPTransportProfile aTransportProfile) throws SMPClientException
  {
    final EndpointType aEndpoint = getEndpoint (aServiceGroupID, aDocumentTypeID, aProcessID, aTransportProfile);
    return getEndpointCertificateBytes (aEndpoint);
  }

  @Nullable
  public X509Certificate getEndpointCertificate (@Nonnull final IParticipantIdentifier aServiceGroupID,
                                                 @Nonnull final IDocumentTypeIdentifier aDocumentTypeID,
                                                 @Nonnull final IProcessIdentifier aProcessID,
                                                 @Nonnull final ISMPTransportProfile aTransportProfile) throws SMPClientException,
                                                                                                        CertificateException
  {
    final byte [] aCertString = getEndpointCertificateBytes (aServiceGroupID,
                                                             aDocumentTypeID,
                                                             aProcessID,
                                                             aTransportProfile);
    if (aCertString == null)
      return null;
    return (X509Certificate) CertificateHelper.getX509CertificateFactory ()
                                              .generateCertificate (new NonBlockingByteArrayInputStream (aCertString));
  }

  @Nullable
  public static X509Certificate getEndpointCertificate (@Nullable final EndpointType aEndpoint) throws CertificateException
  {
    final byte [] aCertString = getEndpointCertificateBytes (aEndpoint);
    if (aCertString == null)
      return null;
    return (X509Certificate) CertificateHelper.getX509CertificateFactory ()
                                              .generateCertificate (new NonBlockingByteArrayInputStream (aCertString));
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
    return new BDXR2ClientReadOnly (aURLProvider, aServiceGroupID, aSMLInfo).getServiceGroup (aServiceGroupID);
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
  public static ServiceMetadataType getServiceRegistrationByDNS (@Nonnull final IPeppolURLProvider aURLProvider,
                                                                 @Nonnull final ISMLInfo aSMLInfo,
                                                                 @Nonnull final IParticipantIdentifier aServiceGroupID,
                                                                 @Nonnull final IDocumentTypeIdentifier aDocumentTypeID) throws SMPClientException,
                                                                                                                         PeppolDNSResolutionException
  {
    return new BDXR2ClientReadOnly (aURLProvider, aServiceGroupID, aSMLInfo).getServiceRegistration (aServiceGroupID,
                                                                                                     aDocumentTypeID);
  }
}
