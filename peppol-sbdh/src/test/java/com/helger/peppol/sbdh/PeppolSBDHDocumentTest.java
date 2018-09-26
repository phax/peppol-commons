/**
 * Copyright (C) 2014-2018 Philip Helger
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;
import com.helger.peppol.identifier.factory.IIdentifierFactory;
import com.helger.peppol.identifier.factory.SimpleIdentifierFactory;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;

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
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (dd,
                                                                           new PeppolSBDHDocument (aIF).setSender ("scheme",
                                                                                                                   "abc"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (dd, new PeppolSBDHDocument (aIF));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (dd,
                                                                       new PeppolSBDHDocument (aIF).setSenderWithDefaultScheme ("abc"));

    // Receiver
    dd.setReceiverWithDefaultScheme ("def");
    assertEquals (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, dd.getReceiverScheme ());
    assertEquals ("def", dd.getReceiverValue ());
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (dd,
                                                                           new PeppolSBDHDocument (aIF).setSenderWithDefaultScheme ("abc"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (dd,
                                                                           new PeppolSBDHDocument (aIF).setSenderWithDefaultScheme ("abc")
                                                                                                       .setReceiver ("scheme",
                                                                                                                     "def"));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (dd,
                                                                       new PeppolSBDHDocument (aIF).setSenderWithDefaultScheme ("abc")
                                                                                                   .setReceiverWithDefaultScheme ("def"));

    // Document type
    dd.setDocumentTypeWithDefaultScheme ("doctype");
    assertEquals (PeppolIdentifierHelper.DEFAULT_DOCUMENT_TYPE_SCHEME, dd.getDocumentTypeScheme ());
    assertEquals ("doctype", dd.getDocumentTypeValue ());
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (dd,
                                                                           new PeppolSBDHDocument (aIF).setSenderWithDefaultScheme ("abc")
                                                                                                       .setReceiverWithDefaultScheme ("def")
                                                                                                       .setDocumentTypeWithDefaultScheme ("other"));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (dd,
                                                                       new PeppolSBDHDocument (aIF).setSenderWithDefaultScheme ("abc")
                                                                                                   .setReceiverWithDefaultScheme ("def")
                                                                                                   .setDocumentTypeWithDefaultScheme ("doctype"));

    // Process type
    dd.setProcessWithDefaultScheme ("proctype");
    assertEquals (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME, dd.getProcessScheme ());
    assertEquals ("proctype", dd.getProcessValue ());
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (dd,
                                                                           new PeppolSBDHDocument (aIF).setSenderWithDefaultScheme ("abc")
                                                                                                       .setReceiverWithDefaultScheme ("def")
                                                                                                       .setDocumentTypeWithDefaultScheme ("doctype")
                                                                                                       .setProcessWithDefaultScheme ("other"));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (dd,
                                                                       new PeppolSBDHDocument (aIF).setSenderWithDefaultScheme ("abc")
                                                                                                   .setReceiverWithDefaultScheme ("def")
                                                                                                   .setDocumentTypeWithDefaultScheme ("doctype")
                                                                                                   .setProcessWithDefaultScheme ("proctype"));
  }
}
