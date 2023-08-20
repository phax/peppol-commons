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
package com.helger.smpclient.peppol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.time.LocalDateTime;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.xml.crypto.dsig.XMLSignatureException;

import org.junit.Ignore;
import org.junit.Test;

import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.datetime.PDTWebDateHelper;
import com.helger.commons.mutable.MutableInt;
import com.helger.commons.state.EContinue;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.smp.ESMPTransportProfile;
import com.helger.peppolid.CIdentifier;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.peppolid.peppol.doctype.EPredefinedDocumentTypeIdentifier;
import com.helger.peppolid.peppol.process.EPredefinedProcessIdentifier;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.KeyStoreHelper;
import com.helger.smpclient.IgnoredNaptrTest;
import com.helger.smpclient.exception.SMPClientBadResponseException;
import com.helger.smpclient.exception.SMPClientException;
import com.helger.smpclient.peppol.marshal.SMPMarshallerServiceMetadataType;
import com.helger.smpclient.url.BDXLURLProvider;
import com.helger.smpclient.url.PeppolURLProvider;
import com.helger.smpclient.url.SMPDNSResolutionException;
import com.helger.xsds.peppol.smp1.EndpointType;
import com.helger.xsds.peppol.smp1.ServiceGroupType;
import com.helger.xsds.peppol.smp1.ServiceMetadataType;
import com.helger.xsds.peppol.smp1.SignedServiceMetadataType;

/**
 * Test class for class {@link SMPClientReadOnly}
 *
 * @author Philip Helger
 */
public final class SMPClientReadOnlyTest
{
  @Test
  public void testGetSMPHostURI_Peppol () throws SMPClientException, SMPDNSResolutionException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test");

    // Peppol URL provider
    final SMPClientReadOnly aSMPClient = new SMPClientReadOnly (PeppolURLProvider.INSTANCE, aPI, ESML.DIGIT_TEST);
    assertEquals ("http://B-85008b8279e07ab0392da75fa55856a2.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu/",
                  aSMPClient.getSMPHostURI ());

    final ServiceGroupType aServiceGroup = aSMPClient.getServiceGroupOrNull (aPI);
    assertNotNull (aServiceGroup);
  }

  @Test
  @Ignore ("Because it may take some time to resolve it")
  @IgnoredNaptrTest
  public void testGetSMPHostURI_BDXR () throws SMPClientException, SMPDNSResolutionException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test");

    // CEF URL provider
    final SMPClientReadOnly aSMPClient = new SMPClientReadOnly (BDXLURLProvider.INSTANCE, aPI, ESML.DIGIT_TEST);
    assertEquals ("http://test-infra.peppol.at/", aSMPClient.getSMPHostURI ());
    assertNotNull (aSMPClient.getServiceGroupOrNull (aPI));
  }

  @Test
  public void testGetSMPHostURI_Peppol_WithBOM () throws SMPClientException, SMPDNSResolutionException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9917:5504033150");

    // This instance has a BOM inside
    final SMPClientReadOnly aSMPClient = new SMPClientReadOnly (PeppolURLProvider.INSTANCE, aPI, ESML.DIGIT_PRODUCTION);
    assertEquals ("http://B-2f67a0710cbc13c11ac8c0d64186ac5e.iso6523-actorid-upis.edelivery.tech.ec.europa.eu/",
                  aSMPClient.getSMPHostURI ());
    assertNotNull (aSMPClient.getServiceGroupOrNull (aPI));
  }

  @Test
  public void testInvalidTrustStore () throws SMPDNSResolutionException,
                                       SMPClientException,
                                       GeneralSecurityException,
                                       IOException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test");

    final SMPClientReadOnly aSMPClient = new SMPClientReadOnly (PeppolURLProvider.INSTANCE,
                                                                aPI,
                                                                ESML.DIGIT_TEST).setSecureValidation (false);
    // Set old trust store
    {
      final KeyStore aTS = KeyStoreHelper.loadKeyStoreDirect (EKeyStoreType.JKS, "truststore-outdated.jks", "peppol");
      assertNotNull (aTS);
      aSMPClient.setTrustStore (aTS);
    }

    try
    {
      // Signature validation MUST fail
      aSMPClient.getServiceMetadataOrNull (aPI, EPredefinedDocumentTypeIdentifier.INVOICE_EN16931_PEPPOL_V30);
      fail ();
    }
    catch (final SMPClientBadResponseException ex)
    {
      assertNotNull (ex.getCause ());
      assertTrue (ex.getCause () instanceof XMLSignatureException);
    }
  }

  @Test
  @Ignore ("Failed with timeout on 2021-05-02")
  public void testIssue2303 () throws Exception
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9925:be0887290276");

    // PEPPOL URL provider
    final SMPClientReadOnly aSMPClient = new SMPClientReadOnly (PeppolURLProvider.INSTANCE, aPI, ESML.DIGIT_PRODUCTION);
    assertEquals ("http://B-c9f280672264cdb82eac528c265ed029.iso6523-actorid-upis.edelivery.tech.ec.europa.eu/",
                  aSMPClient.getSMPHostURI ());

    aSMPClient.setXMLSchemaValidation (true);
    final SignedServiceMetadataType aSM = aSMPClient.getServiceMetadataOrNull (aPI,
                                                                               EPredefinedDocumentTypeIdentifier.INVOICE_EN16931_PEPPOL_V30);
    assertNotNull (aSM);
  }

  @Test
  public void testActivatioDate ()
  {
    final String sTemplate = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" +
                             "<smp:ServiceMetadata xmlns:smp=\"http://busdox.org/serviceMetadata/publishing/1.0/\" xmlns:id=\"http://busdox.org/transport/identifiers/1.0/\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">\r\n" +
                             "  <smp:ServiceInformation>\r\n" +
                             "    <id:ParticipantIdentifier scheme=\"iso6523-actorid-upis\">9915:test</id:ParticipantIdentifier>\r\n" +
                             "    <id:DocumentIdentifier scheme=\"busdox-docid-qns\">urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0::2.1</id:DocumentIdentifier>\r\n" +
                             "    <smp:ProcessList>\r\n" +
                             "      <smp:Process>\r\n" +
                             "        <id:ProcessIdentifier scheme=\"cenbii-procid-ubl\">urn:fdc:peppol.eu:2017:poacc:billing:01:1.0</id:ProcessIdentifier>\r\n" +
                             "        <smp:ServiceEndpointList>\r\n" +
                             "          <smp:Endpoint transportProfile=\"peppol-transport-as4-v2_0\">\r\n" +
                             "            <wsa:EndpointReference>\r\n" +
                             "              <wsa:Address>https://test.erechnung.gv.at/as4</wsa:Address>\r\n" +
                             "            </wsa:EndpointReference>\r\n" +
                             "            <smp:RequireBusinessLevelSignature>false</smp:RequireBusinessLevelSignature>\r\n" +
                             "$SA$\r\n" +
                             "$SE$\r\n" +
                             "            <smp:Certificate>MIIFuzCCA6OgAwIBAgIQa5o35oabzTV6dBMndLD8fDANBgkqhkiG9w0BAQsFADBr\r\n" +
                             "MQswCQYDVQQGEwJCRTEZMBcGA1UEChMQT3BlblBFUFBPTCBBSVNCTDEWMBQGA1UE\r\n" +
                             "CxMNRk9SIFRFU1QgT05MWTEpMCcGA1UEAxMgUEVQUE9MIEFDQ0VTUyBQT0lOVCBU\r\n" +
                             "RVNUIENBIC0gRzIwHhcNMjEwMTEyMDAwMDAwWhcNMjMwMTAyMjM1OTU5WjBIMRIw\r\n" +
                             "EAYDVQQDDAlQREswMDAyNzAxFzAVBgNVBAsMDlBFUFBPTCBURVNUIEFQMQwwCgYD\r\n" +
                             "VQQKDANCUloxCzAJBgNVBAYTAkFUMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIB\r\n" +
                             "CgKCAQEA4SXy0YL041VD4d3ftfcKOfDyhAElrEuo1k/1fYctVPeS/6EiQ/LgFt1m\r\n" +
                             "0/j36yMNPCnyRjt/za2XiBoK1h/jogH4kUQWDAClRI1hxMlHYBmiq+nkeAxgQ22V\r\n" +
                             "EzahjhMPbYSK1sreZ0zkVxdx5tdIM8ZQGpm8QEFjk7v7Cdsup5UwXRs8fDgbZtmX\r\n" +
                             "9rZHHpbwmX5vQtTuhOGs7D7wJq/kBKo6o2I0bGLAF+r1pa1jDMOch3MPAe1mryJt\r\n" +
                             "40JtabiDW4pycVWB9nfhl2oWLIK0Bexx5STpg/daExzSeQd8C2RhdoB3jFGnl3lC\r\n" +
                             "viguoYUHlbdvoDPG2X5YeI5cSUNLyQIDAQABo4IBfDCCAXgwDAYDVR0TAQH/BAIw\r\n" +
                             "ADAOBgNVHQ8BAf8EBAMCA6gwFgYDVR0lAQH/BAwwCgYIKwYBBQUHAwIwHQYDVR0O\r\n" +
                             "BBYEFDFwo9t7piUB2FonPlkSzxnGMZwFMF0GA1UdHwRWMFQwUqBQoE6GTGh0dHA6\r\n" +
                             "Ly9wa2ktY3JsLnN5bWF1dGguY29tL2NhXzZhOTM3NzM0YTM5M2EwODA1YmYzM2Nk\r\n" +
                             "YThiMzMxMDkzL0xhdGVzdENSTC5jcmwwNwYIKwYBBQUHAQEEKzApMCcGCCsGAQUF\r\n" +
                             "BzABhhtodHRwOi8vcGtpLW9jc3Auc3ltYXV0aC5jb20wHwYDVR0jBBgwFoAUa29L\r\n" +
                             "tvE3uis8fxjNuiuyuXwqN+swLQYKYIZIAYb4RQEQAwQfMB0GE2CGSAGG+EUBEAEC\r\n" +
                             "AwEBgamQ4QMWBjk1NzYwODA5BgpghkgBhvhFARAFBCswKQIBABYkYUhSMGNITTZM\r\n" +
                             "eTl3YTJrdGNtRXVjM2x0WVhWMGFDNWpiMjA9MA0GCSqGSIb3DQEBCwUAA4ICAQCg\r\n" +
                             "pC0B372RZr6iXDPqQUbjKlCSlfTNDWY4rGz9sGIvilbKrPm+uboRM1wLkHqSHLH4\r\n" +
                             "4mA3RZS2sKJE73U9BR+OvG4qUXL6z6Q8DzgkVI8WhdB4N//1PfQC/LFDJOtEC7H6\r\n" +
                             "6VwhzDpUMr2hQunPa+QtlA7hw8FB5RtSwGiJJ7+4VsKuKg79cmWLJ+G++0N8kKDl\r\n" +
                             "3tXChRFSTga5b2gZ6NsBJkuqpDNZ5VpXn69bgPM3z2fivWf7pa4UnNjRxV/vE7n0\r\n" +
                             "/hwe4mOyd/BqLKDCvw0xxBUmhEkpBFNBGpGWkLESg4YJiTW2MZHSfiZXcGMHNdII\r\n" +
                             "h6GrfteM3Lcrd+B8mun3RCuLu/Pj1f9VV4lhmr0ux7ksOI421cfLp0D2SZeMnR4s\r\n" +
                             "zwpbxFUHIwyRZnEUi7hmOAiEt+T+XH+4imGbvu5VKBeyYcC/bIiLakUiKHNDGEbU\r\n" +
                             "9nDibSJ66PCjB228tjRXHMFK/HIkB/DwpnKxkxxQVRGWc57TN285nBqs/S7jwQ7W\r\n" +
                             "obgzhCwYuHsbJLpTB3ONeGlT5mpnjtatz0PUe3zyXIuJ8JjidGlh9pTuG38n0jDd\r\n" +
                             "WFv4eBF9whvNkL5xEnWieumSmAHMrRfl95nNb6wCJnV6e8zOO2w01oWdlDGzXJVR\r\n" +
                             "OcMnoITAcvN+F/ixfI13J0a3HqOVZRPy1dOV8fX/Bw==</smp:Certificate>\r\n" +
                             "            <smp:ServiceDescription>BRZ Test AP</smp:ServiceDescription>\r\n" +
                             "            <smp:TechnicalContactUrl>peppol-support@peppol.at</smp:TechnicalContactUrl>\r\n" +
                             "            <smp:TechnicalInformationUrl>http://www.peppol.at</smp:TechnicalInformationUrl>\r\n" +
                             "          </smp:Endpoint>\r\n" +
                             "        </smp:ServiceEndpointList>\r\n" +
                             "      </smp:Process>\r\n" +
                             "    </smp:ProcessList>\r\n" +
                             "  </smp:ServiceInformation>\r\n" +
                             "</smp:ServiceMetadata>\r\n";

    // XSD is limited to milliseconds precision
    final LocalDateTime aCheckDT = PDTFactory.getCurrentLocalDateTimeMillisOnly ();

    // Replace the string variables
    final BiFunction <LocalDateTime, LocalDateTime, String> formatter = (a, e) -> {
      String ret = sTemplate;
      if (a == null)
        ret = ret.replace ("$SA$", "");
      else
        ret = ret.replace ("$SA$",
                           "<smp:ServiceActivationDate>" +
                                   PDTWebDateHelper.getAsStringXSD (a) +
                                   "</smp:ServiceActivationDate>");
      if (e == null)
        ret = ret.replace ("$SE$", "");
      else
        ret = ret.replace ("$SE$",
                           "<smp:ServiceExpirationDate>" +
                                   PDTWebDateHelper.getAsStringXSD (e) +
                                   "</smp:ServiceExpirationDate>");
      return ret;
    };

    // Replace the variables, parse and find the endpoint
    final BiFunction <LocalDateTime, LocalDateTime, EndpointType> findEndpoint = (a, e) -> {
      // Modify String and parse it
      final ServiceMetadataType aSM = new SMPMarshallerServiceMetadataType ().read (formatter.apply (a, e));
      assertNotNull (aSM);

      // Extract endpoint for DT
      return SMPClientReadOnly.getEndpointAt (aSM,
                                              EPredefinedProcessIdentifier.BIS3_BILLING,
                                              ESMPTransportProfile.TRANSPORT_PROFILE_PEPPOL_AS4_V2,
                                              aCheckDT);
    };

    assertNotNull (findEndpoint.apply (null, null));
    assertNotNull (findEndpoint.apply (aCheckDT, null));
    assertNotNull (findEndpoint.apply (null, aCheckDT));
    assertNotNull (findEndpoint.apply (aCheckDT, aCheckDT));
    assertNotNull (findEndpoint.apply (aCheckDT.minusDays (1), aCheckDT.plusDays (1)));

    assertNull (findEndpoint.apply (aCheckDT.plusSeconds (1), null));
    assertNull (findEndpoint.apply (null, aCheckDT.minusSeconds (1)));
    assertNull (findEndpoint.apply (aCheckDT.plusSeconds (1), aCheckDT.minusSeconds (1)));
  }

  @Test
  public void testMatch ()
  {
    final PeppolIdentifierFactory aIF = PeppolIdentifierFactory.INSTANCE;

    final MutableInt aCount = new MutableInt (0);
    final Function <? super IDocumentTypeIdentifier, EContinue> aMatcher = x -> {
      aCount.inc ();
      return EContinue.CONTINUE;
    };
    final ICommonsList <IDocumentTypeIdentifier> aSupportedDocTypes = new CommonsArrayList <> ();
    SMPClientReadOnly.forEachMatchingWildcardDocumentType (aSupportedDocTypes,
                                                           "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-3.0@jp:peppol-1@sub-japan::2.1",
                                                           aMatcher);
    assertEquals (0, aCount.intValue ());

    aCount.set (0);
    // Match
    aSupportedDocTypes.add (aIF.createDocumentTypeIdentifier (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS,
                                                              "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-3.0@jp:peppol-1@sub-japan::2.1"));
    // No match
    aSupportedDocTypes.add (aIF.createDocumentTypeIdentifier (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD,
                                                              "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-3.0@jp:peppol-1@sub-japan*::2.1"));
    // Match
    aSupportedDocTypes.add (aIF.createDocumentTypeIdentifier (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD,
                                                              "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-3.0@jp:peppol-1*::2.1"));
    // Match
    aSupportedDocTypes.add (aIF.createDocumentTypeIdentifier (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD,
                                                              "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-3.0@*::2.1"));
    SMPClientReadOnly.forEachMatchingWildcardDocumentType (aSupportedDocTypes,
                                                           "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-3.0@jp:peppol-1@sub-japan::2.1",
                                                           aMatcher);
    assertEquals (3, aCount.intValue ());

    // Reset
    aCount.set (0);

    // First match only
    SMPClientReadOnly.forEachMatchingWildcardDocumentType (aSupportedDocTypes,
                                                           "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-3.0@jp:peppol-1@sub-japan::2.1",
                                                           x -> {
                                                             aCount.inc ();
                                                             return EContinue.BREAK;
                                                           });
    assertEquals (1, aCount.intValue ());
  }

  @Test
  public void testGetWildcardServiceMetadataOrNull () throws Exception
  {
    final PeppolIdentifierFactory aIF = PeppolIdentifierFactory.INSTANCE;
    final IParticipantIdentifier aReceiverID = aIF.createParticipantIdentifierWithDefaultScheme ("9915:helger");

    final SMPClientReadOnly aSMPClient = new SMPClientReadOnly (PeppolURLProvider.INSTANCE,
                                                                aReceiverID,
                                                                ESML.DIGIT_TEST).setSecureValidation (false);

    SignedServiceMetadataType aSSM;

    // pint@japan
    aSSM = aSMPClient.getWildcardServiceMetadataOrNull (aReceiverID,
                                                        aIF.createDocumentTypeIdentifier ("busdox-docid-qns",
                                                                                          "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@jp-1::2.1"));
    assertNotNull (aSSM);
    assertEquals ("peppol-doctype-wildcard::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@jp-1*::2.1",
                  CIdentifier.getURIEncoded (aSSM.getServiceMetadata ()
                                                 .getServiceInformation ()
                                                 .getDocumentIdentifier ()));

    // pint@japan@sub-japan
    aSSM = aSMPClient.getWildcardServiceMetadataOrNull (aReceiverID,
                                                        aIF.createDocumentTypeIdentifier ("busdox-docid-qns",
                                                                                          "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@jp-1@sub-japan::2.1"));
    assertNotNull (aSSM);
    assertEquals ("peppol-doctype-wildcard::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@jp-1*::2.1",
                  CIdentifier.getURIEncoded (aSSM.getServiceMetadata ()
                                                 .getServiceInformation ()
                                                 .getDocumentIdentifier ()));

    // pint@us
    aSSM = aSMPClient.getWildcardServiceMetadataOrNull (aReceiverID,
                                                        aIF.createDocumentTypeIdentifier ("busdox-docid-qns",
                                                                                          "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@us::2.1"));
    assertNotNull (aSSM);
    assertEquals ("peppol-doctype-wildcard::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1*::2.1",
                  CIdentifier.getURIEncoded (aSSM.getServiceMetadata ()
                                                 .getServiceInformation ()
                                                 .getDocumentIdentifier ()));

    // pint
    aSSM = aSMPClient.getWildcardServiceMetadataOrNull (aReceiverID,
                                                        aIF.createDocumentTypeIdentifier ("busdox-docid-qns",
                                                                                          "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1::2.1"));
    assertNotNull (aSSM);
    assertEquals ("peppol-doctype-wildcard::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1*::2.1",
                  CIdentifier.getURIEncoded (aSSM.getServiceMetadata ()
                                                 .getServiceInformation ()
                                                 .getDocumentIdentifier ()));

    // pont
    aSSM = aSMPClient.getWildcardServiceMetadataOrNull (aReceiverID,
                                                        aIF.createDocumentTypeIdentifier ("busdox-docid-qns",
                                                                                          "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pont:billing-3.0::2.1"));
    assertNull (aSSM);
  }
}
