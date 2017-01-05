/**
 * Copyright (C) 2014-2017 Philip Helger
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
import com.helger.peppol.identifier.factory.IIdentifierFactory;
import com.helger.peppol.identifier.factory.SimpleIdentifierFactory;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;
import com.helger.peppol.sbdh.PeppolSBDHDocument;
import com.helger.peppol.sbdh.read.PeppolSBDHDocumentReadException;
import com.helger.peppol.sbdh.read.PeppolSBDHDocumentReader;
import com.helger.sbdh.SBDMarshaller;
import com.helger.xml.serialize.read.DOMReader;

public final class PeppolSBDHDocumentWriterTest
{
  @Test
  public void testCreateSBDH () throws SAXException, PeppolSBDHDocumentReadException
  {
    // This is our fake business message
    final Document aDoc = DOMReader.readXMLDOM ("<root xmlns='urn:foobar'><child>a</child></root>");

    // Create the document data
    final PeppolSBDHDocument aData = PeppolSBDHDocument.create (aDoc.getDocumentElement ())
                                                       .setSenderWithDefaultScheme ("0088:sender")
                                                       .setReceiverWithDefaultScheme ("0099:receiver")
                                                       .setDocumentTypeWithDefaultScheme ("doctypeid")
                                                       .setProcessWithDefaultScheme ("procid");
    assertTrue (aData.areAllFieldsSet ());

    // Create the SBDH document
    final StandardBusinessDocument aSBD = new PeppolSBDHDocumentWriter ().createStandardBusinessDocument (aData);
    assertNotNull (aSBD);

    // For debugging
    if (false)
      System.out.println (new SBDMarshaller ().getAsString (aSBD));

    // Read again and compare values
    final PeppolSBDHDocument aDataRead = new PeppolSBDHDocumentReader ().extractData (aSBD);
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
    assertEquals (PeppolIdentifierHelper.DEFAULT_DOCUMENT_TYPE_SCHEME, aDataRead.getDocumentTypeScheme ());
    assertEquals ("doctypeid", aDataRead.getDocumentTypeValue ());
    assertEquals (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME, aDataRead.getProcessScheme ());
    assertEquals ("procid", aDataRead.getProcessValue ());
    assertTrue (aDataRead.hasBusinessMessage ());
    assertEquals ("root", aDataRead.getBusinessMessage ().getLocalName ());

    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aData, aDataRead);
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
