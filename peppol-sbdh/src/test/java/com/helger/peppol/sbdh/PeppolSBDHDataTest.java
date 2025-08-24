/*
 * Copyright (C) 2014-2025 Philip Helger
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
package com.helger.peppol.sbdh;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.w3c.dom.Document;

import com.helger.mime.CMimeType;
import com.helger.peppol.sbdh.spec12.BinaryContentType;
import com.helger.peppol.sbdh.spec12.TextContentType;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.factory.SimpleIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.peppolid.peppol.doctype.EPredefinedDocumentTypeIdentifier;
import com.helger.peppolid.peppol.process.EPredefinedProcessIdentifier;
import com.helger.unittest.support.TestHelper;
import com.helger.xml.serialize.read.DOMReader;
import com.helger.xml.serialize.write.XMLWriter;

/**
 * Test class for class {@link PeppolSBDHData}.
 *
 * @author Philip Helger
 */
public final class PeppolSBDHDataTest
{
  @Test
  public void testBasic ()
  {
    final IIdentifierFactory aIF = SimpleIdentifierFactory.INSTANCE;
    final PeppolSBDHData aData = new PeppolSBDHData (aIF);
    assertNull (aData.getSenderScheme ());
    assertNull (aData.getSenderValue ());
    assertNull (aData.getReceiverScheme ());
    assertNull (aData.getReceiverValue ());
    assertNull (aData.getDocumentTypeScheme ());
    assertNull (aData.getDocumentTypeValue ());
    assertNull (aData.getProcessScheme ());
    assertNull (aData.getProcessValue ());
    assertNull (aData.getCountryC1 ());
    assertNull (aData.getStandard ());
    assertNull (aData.getTypeVersion ());
    assertNull (aData.getType ());
    assertNull (aData.getInstanceIdentifier ());
    assertNull (aData.getCreationDateAndTime ());
    assertFalse (aData.hasBusinessMessage ());
    assertNull (aData.getBusinessMessage ());
    assertEquals (0, aData.additionalAttributes ().size ());
    TestHelper.testDefaultImplementationWithEqualContentObject (aData, new PeppolSBDHData (aIF));

    assertNotNull (aData.getSenderAsIdentifier ());
    assertNotNull (aData.getReceiverAsIdentifier ());
    assertNotNull (aData.getDocumentTypeAsIdentifier ());
    assertNotNull (aData.getProcessAsIdentifier ());
    assertFalse (aData.areAllFieldsSet ());
    assertTrue (aData.areAllAdditionalAttributesValid ());

    // Sender
    aData.setSenderWithDefaultScheme ("abc");
    assertEquals (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, aData.getSenderScheme ());
    assertEquals ("abc", aData.getSenderValue ());
    TestHelper.testDefaultImplementationWithDifferentContentObject (aData,
                                                                    new PeppolSBDHData (aIF).setSender ("scheme",
                                                                                                        "abc"));
    TestHelper.testDefaultImplementationWithDifferentContentObject (aData, new PeppolSBDHData (aIF));
    TestHelper.testDefaultImplementationWithEqualContentObject (aData,
                                                                new PeppolSBDHData (aIF).setSenderWithDefaultScheme ("abc"));

    // Receiver
    aData.setReceiverWithDefaultScheme ("def");
    assertEquals (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, aData.getReceiverScheme ());
    assertEquals ("def", aData.getReceiverValue ());
    TestHelper.testDefaultImplementationWithDifferentContentObject (aData,
                                                                    new PeppolSBDHData (aIF).setSenderWithDefaultScheme ("abc"));
    TestHelper.testDefaultImplementationWithDifferentContentObject (aData,
                                                                    new PeppolSBDHData (aIF).setSenderWithDefaultScheme ("abc")
                                                                                            .setReceiver ("scheme",
                                                                                                          "def"));
    TestHelper.testDefaultImplementationWithEqualContentObject (aData,
                                                                new PeppolSBDHData (aIF).setSenderWithDefaultScheme ("abc")
                                                                                        .setReceiverWithDefaultScheme ("def"));

    // Document type 1
    aData.setDocumentTypeWithBusdoxDocidQns ("doctype");
    assertEquals (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS, aData.getDocumentTypeScheme ());
    assertEquals ("doctype", aData.getDocumentTypeValue ());
    TestHelper.testDefaultImplementationWithDifferentContentObject (aData,
                                                                    new PeppolSBDHData (aIF).setSenderWithDefaultScheme ("abc")
                                                                                            .setReceiverWithDefaultScheme ("def")
                                                                                            .setDocumentTypeWithBusdoxDocidQns ("other"));
    TestHelper.testDefaultImplementationWithEqualContentObject (aData,
                                                                new PeppolSBDHData (aIF).setSenderWithDefaultScheme ("abc")
                                                                                        .setReceiverWithDefaultScheme ("def")
                                                                                        .setDocumentTypeWithBusdoxDocidQns ("doctype"));

    // Document type 2
    aData.setDocumentTypeWithPeppolDoctypeWildcard ("doctype");
    assertEquals (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD, aData.getDocumentTypeScheme ());
    assertEquals ("doctype", aData.getDocumentTypeValue ());
    TestHelper.testDefaultImplementationWithDifferentContentObject (aData,
                                                                    new PeppolSBDHData (aIF).setSenderWithDefaultScheme ("abc")
                                                                                            .setReceiverWithDefaultScheme ("def")
                                                                                            .setDocumentTypeWithPeppolDoctypeWildcard ("other"));
    TestHelper.testDefaultImplementationWithEqualContentObject (aData,
                                                                new PeppolSBDHData (aIF).setSenderWithDefaultScheme ("abc")
                                                                                        .setReceiverWithDefaultScheme ("def")
                                                                                        .setDocumentTypeWithPeppolDoctypeWildcard ("doctype"));

    // Process type
    aData.setProcessWithDefaultScheme ("proctype");
    assertEquals (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME, aData.getProcessScheme ());
    assertEquals ("proctype", aData.getProcessValue ());
    TestHelper.testDefaultImplementationWithDifferentContentObject (aData,
                                                                    new PeppolSBDHData (aIF).setSenderWithDefaultScheme ("abc")
                                                                                            .setReceiverWithDefaultScheme ("def")
                                                                                            .setDocumentTypeWithPeppolDoctypeWildcard ("doctype")
                                                                                            .setProcessWithDefaultScheme ("other"));
    TestHelper.testDefaultImplementationWithEqualContentObject (aData,
                                                                new PeppolSBDHData (aIF).setSenderWithDefaultScheme ("abc")
                                                                                        .setReceiverWithDefaultScheme ("def")
                                                                                        .setDocumentTypeWithPeppolDoctypeWildcard ("doctype")
                                                                                        .setProcessWithDefaultScheme ("proctype"));
  }

  @Test
  public void testValid ()
  {
    // This is our fake business message
    final Document aDoc = DOMReader.readXMLDOM ("<root xmlns='urn:foobar'><child>a</child></root>");

    final IIdentifierFactory aIF = SimpleIdentifierFactory.INSTANCE;
    final PeppolSBDHData aData = PeppolSBDHData.createUBL21 (aDoc.getDocumentElement (), aIF);

    aData.setSenderWithDefaultScheme ("9915:sender")
         .setReceiverWithDefaultScheme ("9915:receiver")
         .setCountryC1 ("AT")
         .setDocumentType (EPredefinedDocumentTypeIdentifier.INVOICE_EN16931_PEPPOL_V30)
         .setProcess (EPredefinedProcessIdentifier.BIS3_BILLING);

    assertTrue (aData.areAllFieldsSet (true));
    assertTrue (aData.areAllAdditionalAttributesValid ());
    assertEquals (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS, aData.getDocumentTypeScheme ());
    assertEquals ("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0::2.1",
                  aData.getDocumentTypeValue ());
    assertEquals (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME, aData.getProcessScheme ());
    assertEquals ("urn:fdc:peppol.eu:2017:poacc:billing:01:1.0", aData.getProcessValue ());
    assertEquals ("AT", aData.getCountryC1 ());
    assertEquals (0, aData.additionalAttributes ().size ());
  }

  @Test
  public void testBinary ()
  {
    final IIdentifierFactory aIF = SimpleIdentifierFactory.INSTANCE;
    final PeppolSBDHData aData = new PeppolSBDHData (aIF);

    final byte [] aBinaryPayload = "abc".getBytes (StandardCharsets.UTF_8);

    // Set one with charset
    aData.setBusinessMessageBinaryOnly (aBinaryPayload, CMimeType.APPLICATION_OCTET_STREAM, StandardCharsets.UTF_8);
    assertNotNull (aData.getBusinessMessage ());
    assertEquals ("<BinaryContent xmlns=\"http://peppol.eu/xsd/ticc/envelope/1.0\" encoding=\"UTF-8\" mimeType=\"application/octet-stream\">YWJj</BinaryContent>",
                  XMLWriter.getNodeAsString (aData.getBusinessMessage ()).trim ());

    // Read
    BinaryContentType aBC = aData.getBusinessMessageAsBinaryContent ();
    assertNotNull (aBC);
    assertArrayEquals (aBinaryPayload, aBC.getValue ());
    assertEquals (CMimeType.APPLICATION_OCTET_STREAM.getAsString (), aBC.getMimeType ());
    assertEquals (StandardCharsets.UTF_8.name (), aBC.getEncoding ());
    assertNull (aData.getBusinessMessageAsTextContent ());

    // Set one without charset
    aData.setBusinessMessageBinaryOnly (aBinaryPayload, CMimeType.APPLICATION_XML, null);
    assertNotNull (aData.getBusinessMessage ());
    assertEquals ("<BinaryContent xmlns=\"http://peppol.eu/xsd/ticc/envelope/1.0\" mimeType=\"application/xml\">YWJj</BinaryContent>",
                  XMLWriter.getNodeAsString (aData.getBusinessMessage ()).trim ());

    // Read
    aBC = aData.getBusinessMessageAsBinaryContent ();
    assertNotNull (aBC);
    assertArrayEquals (aBinaryPayload, aBC.getValue ());
    assertEquals (CMimeType.APPLICATION_XML.getAsString (), aBC.getMimeType ());
    assertNull (aBC.getEncoding ());
    assertNull (aData.getBusinessMessageAsTextContent ());
  }

  @Test
  public void testText ()
  {
    final IIdentifierFactory aIF = SimpleIdentifierFactory.INSTANCE;
    final PeppolSBDHData aData = new PeppolSBDHData (aIF);

    final String sTextPayload = "abc";

    // Read
    aData.setBusinessMessageTextOnly (sTextPayload, CMimeType.APPLICATION_OCTET_STREAM);
    assertNotNull (aData.getBusinessMessage ());
    assertEquals ("<TextContent xmlns=\"http://peppol.eu/xsd/ticc/envelope/1.0\" mimeType=\"application/octet-stream\">" +
                  sTextPayload +
                  "</TextContent>",
                  XMLWriter.getNodeAsString (aData.getBusinessMessage ()).trim ());

    // Read
    final TextContentType aTC = aData.getBusinessMessageAsTextContent ();
    assertNotNull (aTC);
    assertEquals (sTextPayload, aTC.getValue ());
    assertEquals (CMimeType.APPLICATION_OCTET_STREAM.getAsString (), aTC.getMimeType ());
    assertNull (aData.getBusinessMessageAsBinaryContent ());
  }
}
