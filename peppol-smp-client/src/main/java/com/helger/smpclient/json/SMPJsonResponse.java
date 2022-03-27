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
package com.helger.smpclient.json;

import java.security.cert.X509Certificate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.security.auth.x500.X500Principal;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.base64.Base64;
import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.datetime.XMLOffsetDateTime;
import com.helger.json.IJsonArray;
import com.helger.json.IJsonObject;
import com.helger.json.JsonArray;
import com.helger.json.JsonObject;
import com.helger.peppol.sml.ESMPAPIType;
import com.helger.peppolid.CIdentifier;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.security.certificate.CertificateHelper;
import com.helger.smpclient.bdxr1.BDXRClientReadOnly;
import com.helger.smpclient.extension.SMPExtensionList;
import com.helger.smpclient.peppol.utils.SMPExtensionConverter;
import com.helger.smpclient.peppol.utils.W3CEndpointReferenceHelper;

/**
 * Utility class to convert SMP data structures to JSON
 *
 * @author Philip Helger
 */
@Immutable
public final class SMPJsonResponse
{
  public static final String JSON_SMPTYPE = "smptype";
  public static final String JSON_PARTICIPANT_ID = "participantID";
  public static final String JSON_HREF = "href";
  public static final String JSON_DOCUMENT_TYPE_ID = "documentTypeID";
  public static final String JSON_ERROR = "error";
  public static final String JSON_URLS = "urls";

  public static final String JSON_CERTIFICATE_UID = "certificateUID";
  public static final String JSON_REDIRECT = "redirect";
  public static final String JSON_PROCESS_ID = "processID";
  public static final String JSON_TRANSPORT_PROFILE = "transportProfile";
  public static final String JSON_ENDPOINT_REFERENCE = "endpointReference";
  public static final String JSON_REQUIRE_BUSINESS_LEVEL_SIGNATURE = "requireBusinessLevelSignature";
  public static final String JSON_MINIMUM_AUTHENTICATION_LEVEL = "minimumAuthenticationLevel";
  public static final String JSON_SERVICE_ACTIVATION_DATE = "serviceActivationDate";
  public static final String JSON_SERVICE_EXPIRATION_DATE = "serviceExpirationDate";
  public static final String JSON_CERTIFICATE = "certificate";
  public static final String JSON_CERTIFICATE_DETAILS = "certificateDetails";
  public static final String JSON_SERVICE_DESCRIPTION = "serviceDescription";
  public static final String JSON_TECHNICAL_CONTACT_URL = "technicalContactUrl";
  public static final String JSON_TECHNICAL_INFORMATION_URL = "technicalInformationUrl";
  public static final String JSON_ENDPOINTS = "endpoints";
  public static final String JSON_PROCESSES = "processes";
  public static final String JSON_EXTENSION = "extension";
  public static final String JSON_SERVICEINFO = "serviceinfo";

  private SMPJsonResponse ()
  {}

  @Nonnull
  public static IJsonObject convert (@Nonnull final ESMPAPIType eSMPAPIType,
                                     @Nonnull final IParticipantIdentifier aParticipantID,
                                     @Nonnull final Map <String, String> aSGHrefs,
                                     @Nonnull final IIdentifierFactory aIF)
  {
    ValueEnforcer.notNull (eSMPAPIType, "SMPAPIType");
    ValueEnforcer.notNull (aParticipantID, "ParticipantID");
    ValueEnforcer.notNull (aSGHrefs, "SGHrefs");
    ValueEnforcer.notNull (aIF, "IF");

    final IJsonObject aJson = new JsonObject ();
    aJson.add (JSON_SMPTYPE, eSMPAPIType.getID ());
    aJson.add (JSON_PARTICIPANT_ID, aParticipantID.getURIEncoded ());

    final String sPathStart = "/" + aParticipantID.getURIEncoded () + '/' + BDXRClientReadOnly.URL_PART_SERVICES + '/';
    final IJsonArray aURLsArray = new JsonArray ();
    // Show all ServiceGroup hrefs
    for (final Map.Entry <String, String> aEntry : aSGHrefs.entrySet ())
    {
      final String sHref = aEntry.getKey ();
      final String sOriginalHref = aEntry.getValue ();

      final IJsonObject aUrlEntry = new JsonObject ().add (JSON_HREF, sOriginalHref);
      // Should be case insensitive "indexOf" here
      final int nPathStart = sHref.toLowerCase (Locale.US).indexOf (sPathStart.toLowerCase (Locale.US));
      if (nPathStart >= 0)
      {
        final String sDocType = sHref.substring (nPathStart + sPathStart.length ());
        aUrlEntry.add (JSON_DOCUMENT_TYPE_ID, sDocType);
        final IDocumentTypeIdentifier aDocType = aIF.parseDocumentTypeIdentifier (sDocType);
        if (aDocType == null)
        {
          aUrlEntry.add (JSON_ERROR, "The document type ID could not be interpreted as a structured document type!");
        }
      }
      else
      {
        aUrlEntry.add (JSON_ERROR, "Contained href does not match the rules. Expected path part: '" + sPathStart + "'");
      }
      aURLsArray.add (aUrlEntry);
    }
    aJson.addJson (JSON_URLS, aURLsArray);
    return aJson;
  }

  @Nonnull
  public static IJsonObject getJsonPrincipal (@Nonnull final X500Principal aPrincipal)
  {
    ValueEnforcer.notNull (aPrincipal, "Principal");

    final IJsonObject ret = new JsonObject ();
    ret.add ("name", aPrincipal.getName ());
    try
    {
      for (final Rdn aRdn : new LdapName (aPrincipal.getName ()).getRdns ())
        ret.add (aRdn.getType (), aRdn.getValue ());
    }
    catch (final InvalidNameException ex)
    {
      // shit happens
    }
    return ret;
  }

  @Nullable
  public static String getLDT (@Nullable final OffsetDateTime aLDT)
  {
    return aLDT == null ? null : DateTimeFormatter.ISO_LOCAL_DATE_TIME.format (aLDT);
  }

  @Nullable
  public static String getLDT (@Nullable final XMLOffsetDateTime aLDT)
  {
    return aLDT == null ? null : DateTimeFormatter.ISO_LOCAL_DATE_TIME.format (aLDT);
  }

  public static void convertCertificate (@Nonnull final IJsonObject aTarget, @Nonnull final String sCert)
  {
    ValueEnforcer.notNull (aTarget, "Target");
    ValueEnforcer.notNull (sCert, "Cert");

    aTarget.add (JSON_CERTIFICATE, sCert);

    final X509Certificate aCert = CertificateHelper.convertStringToCertficateOrNull (sCert);
    final IJsonObject aDetails = new JsonObject ();
    aDetails.add ("parsable", aCert != null);
    if (aCert != null)
    {
      aDetails.addJson ("subject", getJsonPrincipal (aCert.getSubjectX500Principal ()));
      aDetails.addJson ("issuer", getJsonPrincipal (aCert.getIssuerX500Principal ()));
      aDetails.add ("serial10", aCert.getSerialNumber ());
      aDetails.add ("serial16", aCert.getSerialNumber ().toString (16));
      aDetails.addIfNotNull ("notBefore", getLDT (PDTFactory.createOffsetDateTime (aCert.getNotBefore ())));
      aDetails.addIfNotNull ("notAfter", getLDT (PDTFactory.createOffsetDateTime (aCert.getNotAfter ())));
      aDetails.add ("validByDate", CertificateHelper.isCertificateValidPerNow (aCert));
      aDetails.add ("sigAlgName", aCert.getSigAlgName ());
    }
    aTarget.addJson (JSON_CERTIFICATE_DETAILS, aDetails);
  }

  @Nonnull
  public static IJsonObject convertEndpoint (@Nonnull final com.helger.xsds.peppol.smp1.EndpointType aEndpoint)
  {
    final String sEndpointRef = aEndpoint.getEndpointReference () == null ? null
                                                                          : W3CEndpointReferenceHelper.getAddress (aEndpoint.getEndpointReference ());
    final IJsonObject aJsonEP = new JsonObject ().add (JSON_TRANSPORT_PROFILE, aEndpoint.getTransportProfile ())
                                                 .add (JSON_ENDPOINT_REFERENCE, sEndpointRef)
                                                 .add (JSON_REQUIRE_BUSINESS_LEVEL_SIGNATURE,
                                                       aEndpoint.isRequireBusinessLevelSignature ())
                                                 .add (JSON_MINIMUM_AUTHENTICATION_LEVEL,
                                                       aEndpoint.getMinimumAuthenticationLevel ());

    aJsonEP.addIfNotNull (JSON_SERVICE_ACTIVATION_DATE, getLDT (aEndpoint.getServiceActivationDate ()));
    aJsonEP.addIfNotNull (JSON_SERVICE_EXPIRATION_DATE, getLDT (aEndpoint.getServiceExpirationDate ()));
    convertCertificate (aJsonEP, aEndpoint.getCertificate ());
    aJsonEP.add (JSON_SERVICE_DESCRIPTION, aEndpoint.getServiceDescription ())
           .add (JSON_TECHNICAL_CONTACT_URL, aEndpoint.getTechnicalContactUrl ())
           .add (JSON_TECHNICAL_INFORMATION_URL, aEndpoint.getTechnicalInformationUrl ())
           .add (JSON_EXTENSION, SMPExtensionConverter.convertToString (aEndpoint.getExtension ()));
    return aJsonEP;
  }

  @Nonnull
  public static IJsonObject convert (@Nonnull final IParticipantIdentifier aParticipantID,
                                     @Nonnull final IDocumentTypeIdentifier aDocTypeID,
                                     @Nonnull final com.helger.xsds.peppol.smp1.ServiceMetadataType aSM)
  {
    ValueEnforcer.notNull (aParticipantID, "ParticipantID");
    ValueEnforcer.notNull (aDocTypeID, "DocTypeID");
    ValueEnforcer.notNull (aSM, "SM");

    final IJsonObject ret = new JsonObject ();
    ret.add (JSON_SMPTYPE, ESMPAPIType.PEPPOL.getID ());
    ret.add (JSON_PARTICIPANT_ID, aParticipantID.getURIEncoded ());
    ret.add (JSON_DOCUMENT_TYPE_ID, aDocTypeID.getURIEncoded ());

    final com.helger.xsds.peppol.smp1.RedirectType aRedirect = aSM.getRedirect ();
    if (aRedirect != null)
    {
      final IJsonObject aJsonRedirect = new JsonObject ().add (JSON_HREF, aRedirect.getHref ())
                                                         .add (JSON_CERTIFICATE_UID, aRedirect.getCertificateUID ())
                                                         .add (JSON_EXTENSION,
                                                               SMPExtensionConverter.convertToString (aRedirect.getExtension ()));
      ret.addJson (JSON_REDIRECT, aJsonRedirect);
    }
    else
    {
      final com.helger.xsds.peppol.smp1.ServiceInformationType aSI = aSM.getServiceInformation ();
      final IJsonObject aJsonSI = new JsonObject ();
      {
        final IJsonArray aJsonProcs = new JsonArray ();
        // For all processes
        if (aSI.getProcessList () != null)
          for (final com.helger.xsds.peppol.smp1.ProcessType aProcess : aSI.getProcessList ().getProcess ())
            if (aProcess.getProcessIdentifier () != null)
            {
              final IJsonObject aJsonProc = new JsonObject ().add (JSON_PROCESS_ID,
                                                                   CIdentifier.getURIEncoded (aProcess.getProcessIdentifier ()));
              final IJsonArray aJsonEPs = new JsonArray ();
              // For all endpoints
              if (aProcess.getServiceEndpointList () != null)
                for (final com.helger.xsds.peppol.smp1.EndpointType aEndpoint : aProcess.getServiceEndpointList ()
                                                                                        .getEndpoint ())
                {
                  aJsonEPs.add (convertEndpoint (aEndpoint));
                }
              aJsonProc.addJson (JSON_ENDPOINTS, aJsonEPs)
                       .add (JSON_EXTENSION, SMPExtensionConverter.convertToString (aProcess.getExtension ()));
              aJsonProcs.add (aJsonProc);
            }
        aJsonSI.addJson (JSON_PROCESSES, aJsonProcs)
               .add (JSON_EXTENSION, SMPExtensionConverter.convertToString (aSI.getExtension ()));
      }
      ret.addJson (JSON_SERVICEINFO, aJsonSI);
    }
    return ret;
  }

  @Nonnull
  public static IJsonObject convertEndpoint (@Nonnull final com.helger.xsds.bdxr.smp1.EndpointType aEndpoint)
  {
    final IJsonObject aJsonEP = new JsonObject ().add (JSON_TRANSPORT_PROFILE, aEndpoint.getTransportProfile ())
                                                 .add (JSON_ENDPOINT_REFERENCE, aEndpoint.getEndpointURI ())
                                                 .add (JSON_REQUIRE_BUSINESS_LEVEL_SIGNATURE,
                                                       aEndpoint.isRequireBusinessLevelSignature ())
                                                 .add (JSON_MINIMUM_AUTHENTICATION_LEVEL,
                                                       aEndpoint.getMinimumAuthenticationLevel ());

    aJsonEP.addIfNotNull (JSON_SERVICE_ACTIVATION_DATE, getLDT (aEndpoint.getServiceActivationDate ()));
    aJsonEP.addIfNotNull (JSON_SERVICE_EXPIRATION_DATE, getLDT (aEndpoint.getServiceExpirationDate ()));
    convertCertificate (aJsonEP, Base64.encodeBytes (aEndpoint.getCertificate ()));
    aJsonEP.add (JSON_SERVICE_DESCRIPTION, aEndpoint.getServiceDescription ())
           .add (JSON_TECHNICAL_CONTACT_URL, aEndpoint.getTechnicalContactUrl ())
           .add (JSON_TECHNICAL_INFORMATION_URL, aEndpoint.getTechnicalInformationUrl ());
    final SMPExtensionList aExts = SMPExtensionList.ofBDXR1 (aEndpoint.getExtension ());
    if (aExts != null)
      aJsonEP.addIfNotNull (JSON_EXTENSION, aExts.getExtensionsAsJsonString ());
    return aJsonEP;
  }

  @Nonnull
  public static IJsonObject convert (@Nonnull final IParticipantIdentifier aParticipantID,
                                     @Nonnull final IDocumentTypeIdentifier aDocTypeID,
                                     @Nonnull final com.helger.xsds.bdxr.smp1.ServiceMetadataType aSM)
  {
    ValueEnforcer.notNull (aParticipantID, "ParticipantID");
    ValueEnforcer.notNull (aDocTypeID, "DocTypeID");
    ValueEnforcer.notNull (aSM, "SM");

    final IJsonObject ret = new JsonObject ();
    ret.add (JSON_SMPTYPE, ESMPAPIType.OASIS_BDXR_V1.getID ());
    ret.add (JSON_PARTICIPANT_ID, aParticipantID.getURIEncoded ());
    ret.add (JSON_DOCUMENT_TYPE_ID, aDocTypeID.getURIEncoded ());

    final com.helger.xsds.bdxr.smp1.RedirectType aRedirect = aSM.getRedirect ();
    if (aRedirect != null)
    {
      final IJsonObject aJsonRedirect = new JsonObject ().add (JSON_HREF, aRedirect.getHref ())
                                                         .add (JSON_CERTIFICATE_UID, aRedirect.getCertificateUID ());
      final SMPExtensionList aExts = SMPExtensionList.ofBDXR1 (aRedirect.getExtension ());
      if (aExts != null)
        aJsonRedirect.addIfNotNull (JSON_EXTENSION, aExts.getExtensionsAsJsonString ());
      ret.addJson (JSON_REDIRECT, aJsonRedirect);
    }
    else
    {
      final com.helger.xsds.bdxr.smp1.ServiceInformationType aSI = aSM.getServiceInformation ();
      final IJsonObject aJsonSI = new JsonObject ();
      {
        final IJsonArray aJsonProcs = new JsonArray ();
        // For all processes
        if (aSI.getProcessList () != null)
          for (final com.helger.xsds.bdxr.smp1.ProcessType aProcess : aSI.getProcessList ().getProcess ())
            if (aProcess.getProcessIdentifier () != null)
            {
              final IJsonObject aJsonProc = new JsonObject ().add (JSON_PROCESS_ID,
                                                                   CIdentifier.getURIEncoded (aProcess.getProcessIdentifier ()));
              final IJsonArray aJsonEPs = new JsonArray ();
              // For all endpoints
              if (aProcess.getServiceEndpointList () != null)
                for (final com.helger.xsds.bdxr.smp1.EndpointType aEndpoint : aProcess.getServiceEndpointList ()
                                                                                      .getEndpoint ())
                {
                  aJsonEPs.add (convertEndpoint (aEndpoint));
                }
              aJsonProc.addJson (JSON_ENDPOINTS, aJsonEPs);
              final SMPExtensionList aExts = SMPExtensionList.ofBDXR1 (aProcess.getExtension ());
              if (aExts != null)
                aJsonProc.addIfNotNull (JSON_EXTENSION, aExts.getExtensionsAsJsonString ());
              aJsonProcs.add (aJsonProc);
            }
        aJsonSI.addJson (JSON_PROCESSES, aJsonProcs);
        final SMPExtensionList aExts = SMPExtensionList.ofBDXR1 (aSI.getExtension ());
        if (aExts != null)
          aJsonSI.addIfNotNull (JSON_EXTENSION, aExts.getExtensionsAsJsonString ());
      }
      ret.addJson (JSON_SERVICEINFO, aJsonSI);
    }
    return ret;
  }
}
