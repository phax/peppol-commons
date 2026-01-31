/*
 * Copyright (C) 2015-2026 Philip Helger
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

import static org.junit.Assert.assertNotNull;

import java.net.URI;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import com.helger.base.url.URLHelper;
import com.helger.http.basicauth.BasicAuthClientCredentials;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.doctype.EPredefinedDocumentTypeIdentifier;
import com.helger.peppolid.peppol.pidscheme.EPredefinedParticipantIdentifierScheme;
import com.helger.smpclient.exception.SMPClientNotFoundException;
import com.helger.smpclient.url.IPeppolURLProvider;
import com.helger.smpclient.url.PeppolNaptrURLProvider;
import com.helger.xsds.peppol.smp1.ServiceGroupType;
import com.helger.xsds.peppol.smp1.SignedServiceMetadataType;

/**
 * Expects a local SMP up and running with DNS enabled at port 80 at the ROOT context. See SMP_URI
 * constant
 *
 * @author Philip Helger
 */
@Ignore
public final class SMPClientWithDNSFuncTest
{
  @ClassRule
  public static final SMPClientTestConfigRule RULE = new SMPClientTestConfigRule ();

  private static final ISMLInfo SML_INFO = ESML.DEVELOPMENT_LOCAL;

  private static final String TEST_BUSINESS_IDENTIFIER = "0088:5798000999988";

  private static final String SMP_USERNAME = MockSMPClientConfig.getSMPUserName ();
  private static final String SMP_PASSWORD = MockSMPClientConfig.getSMPPassword ();
  private static final BasicAuthClientCredentials SMP_CREDENTIALS = new BasicAuthClientCredentials (SMP_USERNAME,
                                                                                                    SMP_PASSWORD);
  private static final URI SMP_URI = URLHelper.getAsURI ("http://localhost/");
  private static final IPeppolURLProvider URL_PROVIDER = PeppolNaptrURLProvider.INSTANCE;

  @BeforeClass
  public static void beforeClass () throws Exception
  {
    final SMPClient aClient = new SMPClient (SMP_URI);

    // Ensure to delete TEST_BUSINESS_IDENTIFIER
    try
    {
      final IParticipantIdentifier aServiceGroupID = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (TEST_BUSINESS_IDENTIFIER);
      aClient.deleteServiceGroup (aServiceGroupID, SMP_CREDENTIALS);
    }
    catch (final SMPClientNotFoundException e)
    {
      // This is ok
    }
  }

  @Test
  public void testGetByDNS () throws Exception
  {
    // Make sure that the dns exists.
    final String sParticipantID = "0088:5798000000001";
    final String sDocumentID = "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::InvoiceDisputeDisputeInvoice##UBL-2.0";

    final IParticipantIdentifier aServiceGroupID = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (sParticipantID);
    final IDocumentTypeIdentifier aDocumentTypeID = PeppolIdentifierFactory.INSTANCE.createDocumentTypeIdentifierWithDefaultScheme (sDocumentID);

    final ServiceGroupType aGroup = SMPClientReadOnly.getServiceGroupByDNS (URL_PROVIDER, SML_INFO, aServiceGroupID);
    assertNotNull (aGroup);

    final SignedServiceMetadataType aMetadata = SMPClientReadOnly.getServiceRegistrationByDNS (URL_PROVIDER,
                                                                                               SML_INFO,
                                                                                               aServiceGroupID,
                                                                                               aDocumentTypeID);
    assertNotNull (aMetadata);
  }

  @Test
  public void testGetByDNSForDocs () throws Exception
  {
    // ServiceGroup = participant identifier; GLN = 0088
    final IParticipantIdentifier aServiceGroupID = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (EPredefinedParticipantIdentifierScheme.GLN.createIdentifierValue ("5798000000001"));
    // Document type identifier from enumeration
    @SuppressWarnings ("deprecation")
    final IDocumentTypeIdentifier aDocumentTypeID = EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS4A_V20.getAsDocumentTypeIdentifier ();
    // Main call to the SMP client with the correct SML to use
    final SignedServiceMetadataType aMetadata = SMPClientReadOnly.getServiceRegistrationByDNS (URL_PROVIDER,
                                                                                               ESML.DIGIT_TEST,
                                                                                               aServiceGroupID,
                                                                                               aDocumentTypeID);
    assertNotNull (aMetadata);
  }

  @Test
  public void testRedirect () throws Exception
  {
    final String sParticipantID = "0088:5798000009997";
    final String sDocumentID = "urn:oasis:names:specification:ubl:schema:xsd:SubmitCatalogue-2::SubmitCatalogue##UBL-2.0";

    final IParticipantIdentifier aServiceGroupID = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (sParticipantID);
    final IDocumentTypeIdentifier aDocumentTypeID = PeppolIdentifierFactory.INSTANCE.createDocumentTypeIdentifierWithDefaultScheme (sDocumentID);

    final SignedServiceMetadataType aMetadata = SMPClientReadOnly.getServiceRegistrationByDNS (URL_PROVIDER,
                                                                                               SML_INFO,
                                                                                               aServiceGroupID,
                                                                                               aDocumentTypeID);
    assertNotNull (aMetadata);
  }
}
