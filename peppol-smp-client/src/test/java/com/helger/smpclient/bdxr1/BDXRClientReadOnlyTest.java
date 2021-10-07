/*
 * Copyright (C) 2015-2021 Philip Helger
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.security.KeyStore;

import org.junit.Ignore;
import org.junit.Test;

import com.helger.peppol.sml.ESML;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.sml.SMLInfo;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.factory.SimpleIdentifierFactory;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.KeyStoreHelper;
import com.helger.smpclient.IgnoredNaptrTest;
import com.helger.smpclient.exception.SMPClientBadResponseException;
import com.helger.smpclient.exception.SMPClientException;
import com.helger.smpclient.url.BDXLURLProvider;
import com.helger.smpclient.url.PeppolURLProvider;
import com.helger.smpclient.url.SMPDNSResolutionException;
import com.helger.xsds.bdxr.smp1.SignedServiceMetadataType;

/**
 * Test class for class {@link BDXRClientReadOnly}
 *
 * @author Philip Helger
 */
public final class BDXRClientReadOnlyTest
{
  @Test
  public void testGetBDXRHostURI_Peppol () throws SMPDNSResolutionException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test");

    // PEPPOL URL provider
    final BDXRClientReadOnly aBDXRClient = new BDXRClientReadOnly (PeppolURLProvider.INSTANCE, aPI, ESML.DIGIT_TEST);
    assertEquals ("http://B-85008b8279e07ab0392da75fa55856a2.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu/",
                  aBDXRClient.getSMPHostURI ());
  }

  @Test
  @Ignore
  @IgnoredNaptrTest
  public void testGetBDXRHostURI_BDXR () throws SMPDNSResolutionException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test");

    // BDXR URL provider
    final BDXRClientReadOnly aBDXRClient = new BDXRClientReadOnly (BDXLURLProvider.INSTANCE, aPI, ESML.DIGIT_TEST);
    assertEquals ("http://test-infra.peppol.at/", aBDXRClient.getSMPHostURI ());
  }

  @Test
  public void testGetBDXRHostURI_Peppol_WithBDXR () throws SMPClientException, SMPDNSResolutionException
  {
    // This instance has a BOM inside
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9917:5504033150");
    final BDXRClientReadOnly aBDXRClient = new BDXRClientReadOnly (PeppolURLProvider.INSTANCE, aPI, ESML.DIGIT_PRODUCTION);
    assertEquals ("http://B-2f67a0710cbc13c11ac8c0d64186ac5e.iso6523-actorid-upis.edelivery.tech.ec.europa.eu/",
                  aBDXRClient.getSMPHostURI ());

    // This fails because the PEPPOL server is configured for PEPPOL layout and
    // not OASIS!
    try
    {
      aBDXRClient.getServiceGroupOrNull (aPI);
      fail ();
    }
    catch (final SMPClientBadResponseException ex)
    {
      // Expected "Malformed XML document returned from SMP server"
    }
  }

  @Test
  @Ignore
  @IgnoredNaptrTest
  public void testReadTOOP () throws SMPDNSResolutionException, SMPClientException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9930:167064991");
    final IDocumentTypeIdentifier aDocTypeID = SimpleIdentifierFactory.INSTANCE.createDocumentTypeIdentifier ("toop-doctypeid-qns",
                                                                                                              "urn:eu:toop:ns:dataexchange-1p40::Response##urn:eu.toop.response.registeredorganization::1.40");

    // TOOP SML
    final ISMLInfo aSMLInfo = new SMLInfo ("toop",
                                           "SMK",
                                           "toop.acc.edelivery.tech.ec.europa.eu.",
                                           "https://acc.edelivery.tech.ec.europa.eu/edelivery-sml",
                                           true);

    // PEPPOL URL provider
    final BDXRClientReadOnly aBDXRClient = new BDXRClientReadOnly (BDXLURLProvider.INSTANCE, aPI, aSMLInfo);
    aBDXRClient.setVerifySignature (false);
    assertEquals ("http://smp.toop.egov.iwvi.uni-koblenz.de/", aBDXRClient.getSMPHostURI ());
    if (false)
    {
      final SignedServiceMetadataType aMetadata = aBDXRClient.getServiceMetadata (aPI, aDocTypeID);
      assertNotNull (aMetadata);
    }
  }

  @Test
  @Ignore
  @IgnoredNaptrTest
  public void testReadConnectivityTest () throws SMPDNSResolutionException, SMPClientException
  {
    final IParticipantIdentifier aPI = SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("connectivity-partid-qns",
                                                                                                     "dynceftest1party59gw");
    final IDocumentTypeIdentifier aDocTypeID = SimpleIdentifierFactory.INSTANCE.createDocumentTypeIdentifier ("connectivity-docid-qns",
                                                                                                              "doc_id1");

    // TOOP SML
    final ISMLInfo aSMLInfo = new SMLInfo ("cef-connectivity",
                                           "CEF ConnectivityTest",
                                           "connectivitytest.acc.edelivery.tech.ec.europa.eu.",
                                           "https://acc.edelivery.tech.ec.europa.eu/edelivery-sml",
                                           true);

    // PEPPOL URL provider
    final BDXRClientReadOnly aBDXRClient = new BDXRClientReadOnly (BDXLURLProvider.INSTANCE, aPI, aSMLInfo);
    // We don't have the truststore at hand - ignore it
    aBDXRClient.setVerifySignature (false);
    assertEquals ("https://gateway-edelivery.westeurope.cloudapp.azure.com:444/", aBDXRClient.getSMPHostURI ());

    final SignedServiceMetadataType aMetadata = aBDXRClient.getServiceMetadata (aPI, aDocTypeID);
    assertNotNull (aMetadata);
  }

  @Test
  @Ignore
  @IgnoredNaptrTest
  public void testReadDE4A () throws Exception
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9999:at000000271");
    final IDocumentTypeIdentifier aDocTypeID = SimpleIdentifierFactory.INSTANCE.createDocumentTypeIdentifier ("urn:de4a-eu:CanonicalEvidenceType",
                                                                                                              "CompanyRegistration");

    final KeyStore aTS = KeyStoreHelper.loadKeyStoreDirect (EKeyStoreType.JKS, "truststores/de4a-truststore-test-smp-pw-de4a.jks", "de4a");
    assertNotNull (aTS);

    // TOOP SML
    final ISMLInfo aSMLInfo = new SMLInfo ("dea4",
                                           "SMK",
                                           "de4a.acc.edelivery.tech.ec.europa.eu.",
                                           "https://acc.edelivery.tech.ec.europa.eu/edelivery-sml",
                                           true);

    // BDXL URL provider
    final BDXRClientReadOnly aBDXRClient = new BDXRClientReadOnly (BDXLURLProvider.INSTANCE, aPI, aSMLInfo);
    // Custom truststore is needed atm
    aBDXRClient.setTrustStore (aTS);
    assertEquals ("https://de4a-smp.egovlab.eu/", aBDXRClient.getSMPHostURI ());
    {
      final SignedServiceMetadataType aMetadata = aBDXRClient.getServiceMetadata (aPI, aDocTypeID);
      assertNotNull (aMetadata);
    }
  }
}
