/*
 * Copyright (C) 2014-2023 Philip Helger
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

import com.helger.commons.mime.CMimeType;
import com.helger.commons.mock.CommonsTestHelper;
import com.helger.peppol.sbdh.spec12.BinaryContentType;
import com.helger.peppol.sbdh.spec12.TextContentType;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.factory.SimpleIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.xml.serialize.write.XMLWriter;

/**
 * Test class for class {@link PeppolSBDHDocument}.
 *
 * @author Philip Helger
 */
public final class PeppolSBDHDocumentTest
{
  @Test
  public void testBasic ()
  {
    final IIdentifierFactory aIF = SimpleIdentifierFactory.INSTANCE;
    final PeppolSBDHDocument dd = new PeppolSBDHDocument (aIF);
    assertNull (dd.getSenderScheme ());
    assertNull (dd.getSenderValue ());
    assertNull (dd.getReceiverScheme ());
    assertNull (dd.getReceiverValue ());
    assertNull (dd.getDocumentTypeScheme ());
    assertNull (dd.getDocumentTypeValue ());
    assertNull (dd.getProcessScheme ());
    assertNull (dd.getProcessValue ());
    assertNull (dd.getStandard ());
    assertNull (dd.getTypeVersion ());
    assertNull (dd.getType ());
    assertNull (dd.getInstanceIdentifier ());
    assertNull (dd.getCreationDateAndTime ());
    assertFalse (dd.hasBusinessMessage ());
    assertNull (dd.getBusinessMessage ());
    assertEquals (0, dd.additionalAttributes ().size ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (dd, new PeppolSBDHDocument (aIF));

    assertNotNull (dd.getSenderAsIdentifier ());
    assertNotNull (dd.getReceiverAsIdentifier ());
    assertNotNull (dd.getDocumentTypeAsIdentifier ());
    assertNotNull (dd.getProcessAsIdentifier ());
    assertFalse (dd.areAllFieldsSet ());
    assertTrue (dd.areAllAdditionalAttributesValid ());

    // Sender
    dd.setSenderWithDefaultScheme ("abc");
    assertEquals (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, dd.getSenderScheme ());
    assertEquals ("abc", dd.getSenderValue ());
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (dd, new PeppolSBDHDocument (aIF).setSender ("scheme", "abc"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (dd, new PeppolSBDHDocument (aIF));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (dd, new PeppolSBDHDocument (aIF).setSenderWithDefaultScheme ("abc"));

    // Receiver
    dd.setReceiverWithDefaultScheme ("def");
    assertEquals (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, dd.getReceiverScheme ());
    assertEquals ("def", dd.getReceiverValue ());
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (dd,
                                                                           new PeppolSBDHDocument (aIF).setSenderWithDefaultScheme ("abc"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (dd,
                                                                           new PeppolSBDHDocument (aIF).setSenderWithDefaultScheme ("abc")
                                                                                                       .setReceiver ("scheme", "def"));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (dd,
                                                                       new PeppolSBDHDocument (aIF).setSenderWithDefaultScheme ("abc")
                                                                                                   .setReceiverWithDefaultScheme ("def"));

    // Document type 1
    dd.setDocumentTypeWithBusdoxDocidQns ("doctype");
    assertEquals (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS, dd.getDocumentTypeScheme ());
    assertEquals ("doctype", dd.getDocumentTypeValue ());
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (dd,
                                                                           new PeppolSBDHDocument (aIF).setSenderWithDefaultScheme ("abc")
                                                                                                       .setReceiverWithDefaultScheme ("def")
                                                                                                       .setDocumentTypeWithBusdoxDocidQns ("other"));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (dd,
                                                                       new PeppolSBDHDocument (aIF).setSenderWithDefaultScheme ("abc")
                                                                                                   .setReceiverWithDefaultScheme ("def")
                                                                                                   .setDocumentTypeWithBusdoxDocidQns ("doctype"));

    // Document type 2
    dd.setDocumentTypeWithPeppolDoctypeWildcard ("doctype");
    assertEquals (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD, dd.getDocumentTypeScheme ());
    assertEquals ("doctype", dd.getDocumentTypeValue ());
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (dd,
                                                                           new PeppolSBDHDocument (aIF).setSenderWithDefaultScheme ("abc")
                                                                                                       .setReceiverWithDefaultScheme ("def")
                                                                                                       .setDocumentTypeWithPeppolDoctypeWildcard ("other"));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (dd,
                                                                       new PeppolSBDHDocument (aIF).setSenderWithDefaultScheme ("abc")
                                                                                                   .setReceiverWithDefaultScheme ("def")
                                                                                                   .setDocumentTypeWithPeppolDoctypeWildcard ("doctype"));

    // Process type
    dd.setProcessWithDefaultScheme ("proctype");
    assertEquals (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME, dd.getProcessScheme ());
    assertEquals ("proctype", dd.getProcessValue ());
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (dd,
                                                                           new PeppolSBDHDocument (aIF).setSenderWithDefaultScheme ("abc")
                                                                                                       .setReceiverWithDefaultScheme ("def")
                                                                                                       .setDocumentTypeWithPeppolDoctypeWildcard ("doctype")
                                                                                                       .setProcessWithDefaultScheme ("other"));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (dd,
                                                                       new PeppolSBDHDocument (aIF).setSenderWithDefaultScheme ("abc")
                                                                                                   .setReceiverWithDefaultScheme ("def")
                                                                                                   .setDocumentTypeWithPeppolDoctypeWildcard ("doctype")
                                                                                                   .setProcessWithDefaultScheme ("proctype"));
  }

  @Test
  public void testBinary ()
  {
    final IIdentifierFactory aIF = SimpleIdentifierFactory.INSTANCE;
    final PeppolSBDHDocument dd = new PeppolSBDHDocument (aIF);

    final byte [] aBinaryPayload = "abc".getBytes (StandardCharsets.UTF_8);
    dd.setBusinessMessageBinaryOnly (aBinaryPayload, CMimeType.APPLICATION_OCTET_STREAM, StandardCharsets.UTF_8);
    assertNotNull (dd.getBusinessMessage ());
    assertEquals ("<BinaryContent xmlns=\"http://peppol.eu/xsd/ticc/envelope/1.0\" encoding=\"UTF-8\" mimeType=\"application/octet-stream\">YWJj</BinaryContent>",
                  XMLWriter.getNodeAsString (dd.getBusinessMessage ()).trim ());

    BinaryContentType aBC = dd.getBusinessMessageAsBinaryContent ();
    assertNotNull (aBC);
    assertArrayEquals (aBinaryPayload, aBC.getValue ());
    assertEquals (CMimeType.APPLICATION_OCTET_STREAM.getAsString (), aBC.getMimeType ());
    assertEquals (StandardCharsets.UTF_8.name (), aBC.getEncoding ());
    assertNull (dd.getBusinessMessageAsTextContent ());

    dd.setBusinessMessageBinaryOnly (aBinaryPayload, CMimeType.APPLICATION_XML, null);
    assertNotNull (dd.getBusinessMessage ());
    assertEquals ("<BinaryContent xmlns=\"http://peppol.eu/xsd/ticc/envelope/1.0\" mimeType=\"application/xml\">YWJj</BinaryContent>",
                  XMLWriter.getNodeAsString (dd.getBusinessMessage ()).trim ());

    aBC = dd.getBusinessMessageAsBinaryContent ();
    assertNotNull (aBC);
    assertArrayEquals (aBinaryPayload, aBC.getValue ());
    assertEquals (CMimeType.APPLICATION_XML.getAsString (), aBC.getMimeType ());
    assertNull (aBC.getEncoding ());
    assertNull (dd.getBusinessMessageAsTextContent ());
  }

  @Test
  public void testText ()
  {
    final IIdentifierFactory aIF = SimpleIdentifierFactory.INSTANCE;
    final PeppolSBDHDocument dd = new PeppolSBDHDocument (aIF);

    dd.setBusinessMessageTextOnly ("abc", CMimeType.APPLICATION_OCTET_STREAM);
    assertNotNull (dd.getBusinessMessage ());
    assertEquals ("<TextContent xmlns=\"http://peppol.eu/xsd/ticc/envelope/1.0\" mimeType=\"application/octet-stream\">abc</TextContent>",
                  XMLWriter.getNodeAsString (dd.getBusinessMessage ()).trim ());

    final TextContentType aTC = dd.getBusinessMessageAsTextContent ();
    assertNotNull (aTC);
    assertEquals ("abc", aTC.getValue ());
    assertEquals (CMimeType.APPLICATION_OCTET_STREAM.getAsString (), aTC.getMimeType ());
    assertNull (dd.getBusinessMessageAsBinaryContent ());
  }
}
