/**
 * Copyright (C) 2014-2015 Philip Helger
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.unece.cefact.namespaces.sbdh.StandardBusinessDocument;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.helger.commons.mock.CommonsTestHelper;
import com.helger.commons.xml.serialize.read.DOMReader;
import com.helger.peppol.identifier.CIdentifier;
import com.helger.peppol.sbdh.DocumentData;
import com.helger.peppol.sbdh.read.DocumentDataReadException;
import com.helger.peppol.sbdh.read.DocumentDataReader;
import com.helger.sbdh.SBDMarshaller;

public final class DocumentDataWriterTest
{
  @Test
  public void testCreateSBDH () throws SAXException, DocumentDataReadException
  {
    // This is our fake business message
    final Document aDoc = DOMReader.readXMLDOM ("<root xmlns='urn:foobar'><child>a</child></root>");

    // Create the document data
    final DocumentData aData = DocumentData.create (aDoc.getDocumentElement ())
                                           .setSenderWithDefaultScheme ("0088:sender")
                                           .setReceiverWithDefaultScheme ("0099:receiver")
                                           .setDocumentTypeWithDefaultScheme ("doctypeid")
                                           .setProcessWithDefaultScheme ("procid");
    assertTrue (aData.areAllFieldsSet ());

    // Create the SBDH document
    final StandardBusinessDocument aSBD = new DocumentDataWriter ().createStandardBusinessDocument (aData);
    assertNotNull (aSBD);

    // For debugging
    if (false)
      System.out.println (new SBDMarshaller ().getAsXMLString (aSBD));

    // Read again and compare values
    final DocumentData aDataRead = new DocumentDataReader ().extractData (aSBD);
    assertNotNull (aDataRead);

    assertEquals (CIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME, aDataRead.getSenderScheme ());
    assertEquals ("0088:sender", aDataRead.getSenderValue ());
    assertEquals (CIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME, aDataRead.getReceiverScheme ());
    assertEquals ("0099:receiver", aDataRead.getReceiverValue ());
    assertEquals ("urn:foobar", aDataRead.getStandard ());
    assertEquals ("2.1", aDataRead.getTypeVersion ());
    assertEquals ("root", aDataRead.getType ());
    assertEquals (aData.getInstanceIdentifier (), aDataRead.getInstanceIdentifier ());
    assertEquals (aData.getCreationDateAndTime ().toString (), aDataRead.getCreationDateAndTime ().toString ());
    assertEquals (CIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME, aDataRead.getDocumentTypeScheme ());
    assertEquals ("doctypeid", aDataRead.getDocumentTypeValue ());
    assertEquals (CIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME, aDataRead.getProcessScheme ());
    assertEquals ("procid", aDataRead.getProcessValue ());
    assertTrue (aDataRead.hasBusinessMessage ());
    assertEquals ("root", aDataRead.getBusinessMessage ().getLocalName ());

    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aData, aDataRead);
  }

  @Test
  public void testBadCase ()
  {
    final DocumentData aData = new DocumentData ();
    try
    {
      // Not all fields are set
      new DocumentDataWriter ().createStandardBusinessDocument (aData);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // Expected
    }
  }
}
