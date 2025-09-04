/*
 * Copyright (C) 2025 Philip Helger
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
package com.helger.hredelivery.commons.sbdh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unece.cefact.namespaces.sbdh.StandardBusinessDocument;
import org.w3c.dom.Document;

import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.factory.SimpleIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.sbdh.SBDMarshaller;
import com.helger.unittest.support.TestHelper;
import com.helger.xml.serialize.read.DOMReader;

/**
 * Test class for class {@link HREDeliverySBDHDataWriter}.
 *
 * @author Philip Helger
 */
public final class HREDeliverySBDHDataWriterTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (HREDeliverySBDHDataWriterTest.class);

  @Test
  public void testCreateSBDH () throws HREDeliverySBDHDataReadException
  {
    // This is our fake business message
    final Document aDoc = DOMReader.readXMLDOM ("<root xmlns='urn:foobar'><child>a</child></root>");

    // Create the document data
    final HREDeliverySBDHData aData = HREDeliverySBDHData.createUBL21 (aDoc.getDocumentElement (),
                                                                       PeppolIdentifierFactory.INSTANCE)
                                                         .setSenderWithDefaultScheme ("0088:sender")
                                                         .setReceiverWithDefaultScheme ("0099:receiver");
    assertTrue (aData.areAllFieldsSet ());

    // Create the SBDH document
    final StandardBusinessDocument aSBD = new HREDeliverySBDHDataWriter ().createStandardBusinessDocument (aData);
    assertNotNull (aSBD);

    final String sXML = new SBDMarshaller ().getAsString (aSBD);
    assertNotNull (sXML);
    // For debugging
    if (false)
      LOGGER.info (sXML);

    // Read again and compare values
    final HREDeliverySBDHData aDataRead = new HREDeliverySBDHDataReader (SimpleIdentifierFactory.INSTANCE).extractData (aSBD);
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
    assertTrue (aDataRead.hasBusinessMessage ());
    assertEquals ("root", aDataRead.getBusinessMessage ().getLocalName ());

    TestHelper.testDefaultImplementationWithEqualContentObject (aData, aDataRead);
  }

  @Test
  public void testBadCase ()
  {
    final IIdentifierFactory aIF = SimpleIdentifierFactory.INSTANCE;
    // Empty data
    final HREDeliverySBDHData aData = new HREDeliverySBDHData (aIF);

    try
    {
      // Not all fields are set
      new HREDeliverySBDHDataWriter ().createStandardBusinessDocument (aData);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // Expected
    }
  }
}
