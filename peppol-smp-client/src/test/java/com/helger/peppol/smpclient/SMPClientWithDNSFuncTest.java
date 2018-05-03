/**
 * Copyright (C) 2015-2018 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.smpclient;

import static org.junit.Assert.assertNotNull;

import java.net.URI;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.helger.commons.url.URLHelper;
import com.helger.http.basicauth.BasicAuthClientCredentials;
import com.helger.peppol.identifier.factory.PeppolIdentifierFactory;
import com.helger.peppol.identifier.generic.doctype.IDocumentTypeIdentifier;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.identifier.peppol.doctype.EPredefinedDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.pidscheme.EPredefinedParticipantIdentifierScheme;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smp.ServiceGroupType;
import com.helger.peppol.smp.SignedServiceMetadataType;
import com.helger.peppol.smpclient.exception.SMPClientNotFoundException;
import com.helger.peppol.url.IPeppolURLProvider;
import com.helger.peppol.url.PeppolURLProvider;

/**
 * Expects a local SMP up and running with DNS enabled at port 80 at the ROOT
 * context. See SMP_URI constant
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Ignore
public final class SMPClientWithDNSFuncTest
{
  private static final ISMLInfo SML_INFO = ESML.DEVELOPMENT_LOCAL;

  private static final String TEST_BUSINESS_IDENTIFIER = "0088:5798000999988";

  private static final String SMP_USERNAME = MockSMPClientConfig.getSMPUserName ();
  private static final String SMP_PASSWORD = MockSMPClientConfig.getSMPPassword ();
  private static final BasicAuthClientCredentials SMP_CREDENTIALS = new BasicAuthClientCredentials (SMP_USERNAME,
                                                                                                    SMP_PASSWORD);
  private static final URI SMP_URI = URLHelper.getAsURI ("http://localhost/");
  private static final IPeppolURLProvider URL_PROVIDER = PeppolURLProvider.INSTANCE;

  @BeforeClass
  public static void init () throws Exception
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
  public void getByDNSTest () throws Exception
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
  public void getByDNSTestForDocs () throws Exception
  {
    // ServiceGroup = participant identifier; GLN = 0088
    final IParticipantIdentifier aServiceGroupID = EPredefinedParticipantIdentifierScheme.GLN.createParticipantIdentifier ("5798000000001");
    // Document type identifier from enumeration
    final IDocumentTypeIdentifier aDocumentTypeID = EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS4A_V20.getAsDocumentTypeIdentifier ();
    // Main call to the SMP client with the correct SML to use
    final SignedServiceMetadataType aMetadata = SMPClientReadOnly.getServiceRegistrationByDNS (URL_PROVIDER,
                                                                                               ESML.DIGIT_TEST,
                                                                                               aServiceGroupID,
                                                                                               aDocumentTypeID);
    assertNotNull (aMetadata);
  }

  @Test
  public void redirectTest () throws Exception
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
