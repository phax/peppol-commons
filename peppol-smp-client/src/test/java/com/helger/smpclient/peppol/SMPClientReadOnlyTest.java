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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import javax.xml.crypto.dsig.XMLSignatureException;

import org.junit.Ignore;
import org.junit.Test;

import com.helger.peppol.sml.ESML;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.doctype.EPredefinedDocumentTypeIdentifier;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.KeyStoreHelper;
import com.helger.smpclient.IgnoredNaptrTest;
import com.helger.smpclient.exception.SMPClientBadResponseException;
import com.helger.smpclient.exception.SMPClientException;
import com.helger.smpclient.url.BDXLURLProvider;
import com.helger.smpclient.url.PeppolURLProvider;
import com.helger.smpclient.url.SMPDNSResolutionException;
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

    // PEPPOL URL provider
    final SMPClientReadOnly aSMPClient = new SMPClientReadOnly (PeppolURLProvider.INSTANCE, aPI, ESML.DIGIT_TEST);
    assertEquals ("http://B-85008b8279e07ab0392da75fa55856a2.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu/",
                  aSMPClient.getSMPHostURI ());
    assertNotNull (aSMPClient.getServiceGroupOrNull (aPI));
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
  public void testInvalidTrustStore () throws SMPDNSResolutionException, SMPClientException, GeneralSecurityException, IOException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test");

    final SMPClientReadOnly aSMPClient = new SMPClientReadOnly (PeppolURLProvider.INSTANCE, aPI, ESML.DIGIT_TEST);
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
}
