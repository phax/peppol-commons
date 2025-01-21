/*
 * Copyright (C) 2024-2025 Philip Helger
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
package com.helger.peppol.xhe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.factory.SimpleIdentifierFactory;
import com.helger.xhe.v10.XHE10Marshaller;
import com.helger.xhe.v10.XHE10XHEType;
import com.helger.xml.serialize.read.DOMReader;

/**
 * Test class for class {@link DBNAllianceXHEData}.
 *
 * @author Philip Helger
 */
public final class DBNAllianceXHEDataTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (DBNAllianceXHEDataTest.class);

  @Test
  public void testBasic ()
  {
    final IIdentifierFactory aIF = SimpleIdentifierFactory.INSTANCE;
    final DBNAllianceXHEData a = new DBNAllianceXHEData (aIF);
    assertNull (a.getID ());
    assertFalse (a.hasID ());
    assertFalse (a.areAllFieldsSet (false));

    final Document aPayload = DOMReader.readXMLDOM ("<Invoice xmlns='anyns'><bla/></Invoice>");
    assertNotNull (aPayload);

    final DBNAlliancePayload aPayload1 = new DBNAlliancePayload (aIF).setContentTypeCodeXML ()
                                                                     .setPayloadContent (aPayload.getDocumentElement ());
    assertTrue (aPayload1.areAllMandatoryFieldsSet ());
    assertNotNull (aPayload1.getPayloadContent ());
    assertNotNull (aPayload1.getPayloadContentNoClone ());

    a.setID ("abc")
     .setCreationDateAndTimeNow ()
     .setFromParty ("fs", "fv")
     .setToParty ("ts", "tv")
     .addPayload (aPayload1);

    assertTrue (a.areAllFieldsSet (false));
    final XHE10XHEType aXHEDoc = a.getAsXHEDocument ();
    assertNotNull (aXHEDoc);

    // Write unformatted
    final String sXHE = new XHE10Marshaller ().setFormattedOutput (false).getAsString (aXHEDoc);
    assertNotNull (sXHE);
    if (false)
      LOGGER.info (sXHE);

    // Read again
    final XHE10XHEType aXHEDoc2 = new XHE10Marshaller ().read (sXHE);
    assertNotNull (aXHEDoc2);

    // Fails because of different payload content
    if (false)
      assertEquals (aXHEDoc, aXHEDoc2);

    aXHEDoc.getPayloads ().getPayloadAtIndex (0).getPayloadContent ().setContent (null);
    aXHEDoc2.getPayloads ().getPayloadAtIndex (0).getPayloadContent ().setContent (null);
    assertEquals (aXHEDoc, aXHEDoc2);
  }
}
