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
package com.helger.peppol.sbdh.write;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unece.cefact.namespaces.sbdh.StandardBusinessDocument;
import org.w3c.dom.Document;

import com.helger.commons.mock.CommonsTestHelper;
import com.helger.peppol.sbdh.PeppolSBDHDocument;
import com.helger.peppol.sbdh.read.PeppolSBDHDocumentReadException;
import com.helger.peppol.sbdh.read.PeppolSBDHDocumentReader;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.factory.SimpleIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.sbdh.SBDMarshaller;
import com.helger.xml.serialize.read.DOMReader;

public final class PeppolSBDHDocumentWriterTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolSBDHDocumentWriterTest.class);

  @Test
  public void testCreateSBDH () throws PeppolSBDHDocumentReadException
  {
    // This is our fake business message
    final Document aDoc = DOMReader.readXMLDOM ("<root xmlns='urn:foobar'><child>a</child></root>");

    // Create the document data
    final PeppolSBDHDocument aData = PeppolSBDHDocument.createUBL21 (aDoc.getDocumentElement (),
                                                                     PeppolIdentifierFactory.INSTANCE)
                                                       .setSenderWithDefaultScheme ("0088:sender")
                                                       .setReceiverWithDefaultScheme ("0099:receiver")
                                                       .setDocumentTypeWithBusdoxDocidQns ("urn:foobar::root#doctypeid:2.1")
                                                       .setProcessWithDefaultScheme ("procid")
                                                       .setCountryC1 ("DE");
    assertTrue (aData.areAllFieldsSet ());
    assertTrue (aData.areAllAdditionalAttributesValid ());
    assertEquals (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS, aData.getDocumentTypeScheme ());
    assertEquals ("urn:foobar::root#doctypeid:2.1", aData.getDocumentTypeValue ());
    assertEquals (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME, aData.getProcessScheme ());
    assertEquals ("procid", aData.getProcessValue ());
    assertEquals ("DE", aData.getCountryC1 ());
    assertEquals (0, aData.additionalAttributes ().size ());

    // Create the SBDH document
    final StandardBusinessDocument aSBD = new PeppolSBDHDocumentWriter ().createStandardBusinessDocument (aData);
    assertNotNull (aSBD);

    final String sXML = new SBDMarshaller ().getAsString (aSBD);
    assertNotNull (sXML);
    // For debugging
    if (false)
      LOGGER.info (sXML);

    // Read again and compare values
    final PeppolSBDHDocument aDataRead = new PeppolSBDHDocumentReader (SimpleIdentifierFactory.INSTANCE).extractData (aSBD);
    assertNotNull (aDataRead);

    assertEquals (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, aDataRead.getSenderScheme ());
    assertEquals ("0088:sender", aDataRead.getSenderValue ());
    assertEquals (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, aDataRead.getReceiverScheme ());
    assertEquals ("0099:receiver", aDataRead.getReceiverValue ());
    assertEquals ("urn:foobar", aDataRead.getStandard ());
    assertEquals ("2.1", aDataRead.getTypeVersion ());
    assertEquals ("root", aDataRead.getType ());
    assertEquals (aData.getInstanceIdentifier (), aDataRead.getInstanceIdentifier ());
    assertEquals (aData.getCreationDateAndTime (), aDataRead.getCreationDateAndTime ());
    assertEquals (aData.getCreationDateAndTime ().toString (), aDataRead.getCreationDateAndTime ().toString ());
    assertEquals (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS, aDataRead.getDocumentTypeScheme ());
    assertEquals ("urn:foobar::root#doctypeid:2.1", aDataRead.getDocumentTypeValue ());
    assertEquals (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME, aDataRead.getProcessScheme ());
    assertEquals ("procid", aDataRead.getProcessValue ());
    assertEquals ("DE", aData.getCountryC1 ());
    assertTrue (aDataRead.hasBusinessMessage ());
    assertEquals ("root", aDataRead.getBusinessMessage ().getLocalName ());
    assertEquals (0, aDataRead.additionalAttributes ().size ());

    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aData, aDataRead);
  }

  @Test
  public void testCreateSBDHWithAdditionalAttributes () throws PeppolSBDHDocumentReadException
  {
    // This is our fake business message
    final Document aDoc = DOMReader.readXMLDOM ("<root xmlns='urn:foobar'><child>a</child></root>");

    // Create the document data
    final PeppolSBDHDocument aData = PeppolSBDHDocument.createUBL21 (aDoc.getDocumentElement (),
                                                                     PeppolIdentifierFactory.INSTANCE)
                                                       .setSenderWithDefaultScheme ("0088:sender")
                                                       .setReceiverWithDefaultScheme ("0099:receiver")
                                                       .setDocumentTypeWithPeppolDoctypeWildcard ("urn:foobar::root#doctypeid:2.1")
                                                       .setProcessWithDefaultScheme ("procid");
    assertTrue (aData.areAllFieldsSet ());
    assertTrue (aData.areAllAdditionalAttributesValid ());
    assertEquals (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD, aData.getDocumentTypeScheme ());
    assertEquals ("urn:foobar::root#doctypeid:2.1", aData.getDocumentTypeValue ());
    assertEquals (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME, aData.getProcessScheme ());
    assertEquals ("procid", aData.getProcessValue ());
    assertEquals (0, aData.additionalAttributes ().size ());
    aData.additionalAttributes ().add ("Attr1", "Value1");
    aData.additionalAttributes ().add ("Attr2", (String) null);
    aData.additionalAttributes ().add ("Attr3", "");
    assertTrue (aData.areAllAdditionalAttributesValid ());

    // Create the SBDH document
    final StandardBusinessDocument aSBD = new PeppolSBDHDocumentWriter ().createStandardBusinessDocument (aData);
    assertNotNull (aSBD);

    final String sXML = new SBDMarshaller ().getAsString (aSBD);
    assertNotNull (sXML);
    // For debugging
    if (false)
      LOGGER.info (sXML);

    // Read again and compare values
    final PeppolSBDHDocument aDataRead = new PeppolSBDHDocumentReader (SimpleIdentifierFactory.INSTANCE).extractData (aSBD);
    assertNotNull (aDataRead);

    assertEquals (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, aDataRead.getSenderScheme ());
    assertEquals ("0088:sender", aDataRead.getSenderValue ());
    assertEquals (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, aDataRead.getReceiverScheme ());
    assertEquals ("0099:receiver", aDataRead.getReceiverValue ());
    assertEquals ("urn:foobar", aDataRead.getStandard ());
    assertEquals ("2.1", aDataRead.getTypeVersion ());
    assertEquals ("root", aDataRead.getType ());
    assertEquals (aData.getInstanceIdentifier (), aDataRead.getInstanceIdentifier ());
    assertEquals (aData.getCreationDateAndTime ().toString (), aDataRead.getCreationDateAndTime ().toString ());
    assertEquals (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD,
                  aDataRead.getDocumentTypeScheme ());
    assertEquals ("urn:foobar::root#doctypeid:2.1", aDataRead.getDocumentTypeValue ());
    assertEquals (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME, aDataRead.getProcessScheme ());
    assertEquals ("procid", aDataRead.getProcessValue ());
    assertTrue (aDataRead.hasBusinessMessage ());
    assertEquals ("root", aDataRead.getBusinessMessage ().getLocalName ());
    assertEquals (3, aDataRead.additionalAttributes ().size ());
    assertEquals ("Value1", aDataRead.additionalAttributes ().get ("Attr1"));
    assertTrue (aDataRead.additionalAttributes ().containsKey ("Attr2"));
    assertNull (aDataRead.additionalAttributes ().get ("Attr2"));
    assertTrue (aDataRead.additionalAttributes ().containsKey ("Attr3"));
    assertNull (aDataRead.additionalAttributes ().get ("Attr3"));

    // Additional attribute Attr3 is different
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aData, aDataRead);

    // Now they should be equal
    aDataRead.additionalAttributes ().put ("Attr3", "");
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aData, aDataRead);
  }

  @Test
  public void testInvalidAdditionalAttributes ()
  {
    final Document aDoc = DOMReader.readXMLDOM ("<root xmlns='urn:foobar'><child>a</child></root>");

    // Create the document data
    final PeppolSBDHDocument aData = PeppolSBDHDocument.createUBL21 (aDoc.getDocumentElement (),
                                                                     PeppolIdentifierFactory.INSTANCE)
                                                       .setSenderWithDefaultScheme ("0088:sender")
                                                       .setReceiverWithDefaultScheme ("0099:receiver")
                                                       .setDocumentTypeWithBusdoxDocidQns ("doctypeid")
                                                       .setProcessWithDefaultScheme ("procid");
    assertTrue (aData.areAllAdditionalAttributesValid ());
    aData.additionalAttributes ().clear ();
    aData.additionalAttributes ().add ("DOCUMENTID", "false");
    assertFalse (aData.areAllAdditionalAttributesValid ());
    aData.additionalAttributes ().clear ();
    aData.additionalAttributes ().add ("PROCESSID", "false");
    assertFalse (aData.areAllAdditionalAttributesValid ());
    aData.additionalAttributes ().clear ();
    aData.additionalAttributes ().add ("TECHNICAL_VALIDATION_URL", "false");
    assertFalse (aData.areAllAdditionalAttributesValid ());
    aData.additionalAttributes ().clear ();
    aData.additionalAttributes ().add ("TECHNICAL_VALIDATION_REQUIRED", "false");
    assertFalse (aData.areAllAdditionalAttributesValid ());
    aData.additionalAttributes ().clear ();
    assertTrue (aData.areAllAdditionalAttributesValid ());
  }

  @Test
  public void testBadCase ()
  {
    final IIdentifierFactory aIF = SimpleIdentifierFactory.INSTANCE;
    final PeppolSBDHDocument aData = new PeppolSBDHDocument (aIF);
    try
    {
      // Not all fields are set
      new PeppolSBDHDocumentWriter ().createStandardBusinessDocument (aData);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // Expected
    }
  }
}
