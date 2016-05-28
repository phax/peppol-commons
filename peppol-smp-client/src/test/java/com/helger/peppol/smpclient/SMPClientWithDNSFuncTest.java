/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.smpclient;

import static org.junit.Assert.assertNotNull;

import java.net.URI;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.helger.commons.url.URLHelper;
import com.helger.peppol.identifier.DocumentIdentifierType;
import com.helger.peppol.identifier.ParticipantIdentifierType;
import com.helger.peppol.identifier.generic.doctype.IDocumentTypeIdentifier;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.identifier.peppol.doctype.EPredefinedDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.doctype.PeppolDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.issuingagency.EPredefinedIdentifierIssuingAgency;
import com.helger.peppol.identifier.peppol.participant.PeppolParticipantIdentifier;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smp.ServiceGroupType;
import com.helger.peppol.smp.SignedServiceMetadataType;
import com.helger.peppol.smpclient.exception.SMPClientNotFoundException;
import com.helger.web.http.basicauth.BasicAuthClientCredentials;

/**
 * Expects a local SMP up and running with DNS enabled at port 80 at the ROOT
 * context. See {@link #SMP_URI} constant
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
  public static final URI SMP_URI = URLHelper.getAsURI ("http://localhost/");

  @BeforeClass
  public static void init () throws Exception
  {
    final SMPClient aClient = new SMPClient (SMP_URI);

    // Ensure to delete TEST_BUSINESS_IDENTIFIER
    try
    {
      final ParticipantIdentifierType aServiceGroupID = PeppolParticipantIdentifier.createWithDefaultScheme (TEST_BUSINESS_IDENTIFIER);
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

    final ParticipantIdentifierType aServiceGroupID = PeppolParticipantIdentifier.createWithDefaultScheme (sParticipantID);
    final DocumentIdentifierType aDocumentTypeID = PeppolDocumentTypeIdentifier.createWithDefaultScheme (sDocumentID);

    final ServiceGroupType aGroup = SMPClientReadOnly.getServiceGroupByDNS (SML_INFO, aServiceGroupID);
    assertNotNull (aGroup);

    final SignedServiceMetadataType aMetadata = SMPClientReadOnly.getServiceRegistrationByDNS (SML_INFO,
                                                                                               aServiceGroupID,
                                                                                               aDocumentTypeID);
    assertNotNull (aMetadata);
  }

  @Test
  public void getByDNSTestForDocs () throws Exception
  {
    // ServiceGroup = participant identifier; GLN = 0088
    final IParticipantIdentifier aServiceGroupID = EPredefinedIdentifierIssuingAgency.GLN.createParticipantIdentifier ("5798000000001");
    // Document type identifier from enumeration
    final IDocumentTypeIdentifier aDocumentTypeID = EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS4A.getAsDocumentTypeIdentifier ();
    // Main call to the SMP client with the correct SML to use
    final SignedServiceMetadataType aMetadata = SMPClientReadOnly.getServiceRegistrationByDNS (ESML.DEVELOPMENT_LOCAL,
                                                                                               aServiceGroupID,
                                                                                               aDocumentTypeID);
    assertNotNull (aMetadata);
  }

  @Test
  public void redirectTest () throws Exception
  {
    final String sParticipantID = "0088:5798000009997";
    final String sDocumentID = "urn:oasis:names:specification:ubl:schema:xsd:SubmitCatalogue-2::SubmitCatalogue##UBL-2.0";

    final IParticipantIdentifier aServiceGroupID = PeppolParticipantIdentifier.createWithDefaultScheme (sParticipantID);
    final IDocumentTypeIdentifier aDocumentTypeID = PeppolDocumentTypeIdentifier.createWithDefaultScheme (sDocumentID);

    final SignedServiceMetadataType aMetadata = SMPClientReadOnly.getServiceRegistrationByDNS (SML_INFO,
                                                                                               aServiceGroupID,
                                                                                               aDocumentTypeID);
    assertNotNull (aMetadata);
  }
}
