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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.w3c.dom.Document;

import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.factory.SimpleIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.unittest.support.TestHelper;
import com.helger.xml.serialize.read.DOMReader;

/**
 * Test class for class {@link HREDeliverySBDHData}.
 *
 * @author Philip Helger
 */
public final class HREDeliverySBDHDataTest
{
  @Test
  public void testBasic ()
  {
    final IIdentifierFactory aIF = SimpleIdentifierFactory.INSTANCE;
    final HREDeliverySBDHData aData = new HREDeliverySBDHData (aIF);
    assertNull (aData.getSenderScheme ());
    assertNull (aData.getSenderValue ());
    assertNull (aData.getReceiverScheme ());
    assertNull (aData.getReceiverValue ());
    assertNull (aData.getStandard ());
    assertNull (aData.getTypeVersion ());
    assertNull (aData.getType ());
    assertNull (aData.getInstanceIdentifier ());
    assertNull (aData.getCreationDateAndTime ());
    assertFalse (aData.hasBusinessMessage ());
    assertNull (aData.getBusinessMessage ());
    TestHelper.testDefaultImplementationWithEqualContentObject (aData, new HREDeliverySBDHData (aIF));

    assertNotNull (aData.getSenderAsIdentifier ());
    assertNotNull (aData.getReceiverAsIdentifier ());
    assertFalse (aData.areAllFieldsSet ());

    // Sender
    aData.setSenderWithDefaultScheme ("abc");
    assertEquals (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, aData.getSenderScheme ());
    assertEquals ("abc", aData.getSenderValue ());
    TestHelper.testDefaultImplementationWithDifferentContentObject (aData,
                                                                    new HREDeliverySBDHData (aIF).setSender ("scheme",
                                                                                                             "abc"));
    TestHelper.testDefaultImplementationWithDifferentContentObject (aData, new HREDeliverySBDHData (aIF));
    TestHelper.testDefaultImplementationWithEqualContentObject (aData,
                                                                new HREDeliverySBDHData (aIF).setSenderWithDefaultScheme ("abc"));

    // Receiver
    aData.setReceiverWithDefaultScheme ("def");
    assertEquals (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, aData.getReceiverScheme ());
    assertEquals ("def", aData.getReceiverValue ());
    TestHelper.testDefaultImplementationWithDifferentContentObject (aData,
                                                                    new HREDeliverySBDHData (aIF).setSenderWithDefaultScheme ("abc"));
    TestHelper.testDefaultImplementationWithDifferentContentObject (aData,
                                                                    new HREDeliverySBDHData (aIF).setSenderWithDefaultScheme ("abc")
                                                                                                 .setReceiver ("scheme",
                                                                                                               "def"));
    TestHelper.testDefaultImplementationWithEqualContentObject (aData,
                                                                new HREDeliverySBDHData (aIF).setSenderWithDefaultScheme ("abc")
                                                                                             .setReceiverWithDefaultScheme ("def"));
  }

  @Test
  public void testValid ()
  {
    // This is our fake business message
    final Document aDoc = DOMReader.readXMLDOM ("<root xmlns='urn:foobar'><child>a</child></root>");

    final IIdentifierFactory aIF = SimpleIdentifierFactory.INSTANCE;
    final HREDeliverySBDHData aData = HREDeliverySBDHData.createUBL21 (aDoc.getDocumentElement (), aIF);

    aData.setSenderWithDefaultScheme ("9915:sender").setReceiverWithDefaultScheme ("9915:receiver");

    assertTrue (aData.areAllFieldsSet (true));
  }
}
