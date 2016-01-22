/**
 * Copyright (C) 2014-2016 Philip Helger
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
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;
import com.helger.peppol.identifier.CIdentifier;

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
    final PeppolSBDHDocument dd = new PeppolSBDHDocument ();
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
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (dd, new PeppolSBDHDocument ());

    // Sender
    dd.setSenderWithDefaultScheme ("abc");
    assertEquals (CIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME, dd.getSenderScheme ());
    assertEquals ("abc", dd.getSenderValue ());
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (dd,
                                                                           new PeppolSBDHDocument ().setSender ("scheme",
                                                                                                          "abc"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (dd, new PeppolSBDHDocument ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (dd,
                                                                       new PeppolSBDHDocument ().setSenderWithDefaultScheme ("abc"));

    // Receiver
    dd.setReceiverWithDefaultScheme ("def");
    assertEquals (CIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME, dd.getReceiverScheme ());
    assertEquals ("def", dd.getReceiverValue ());
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (dd,
                                                                           new PeppolSBDHDocument ().setSenderWithDefaultScheme ("abc"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (dd,
                                                                           new PeppolSBDHDocument ().setSenderWithDefaultScheme ("abc")
                                                                                              .setReceiver ("scheme",
                                                                                                            "def"));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (dd,
                                                                       new PeppolSBDHDocument ().setSenderWithDefaultScheme ("abc")
                                                                                          .setReceiverWithDefaultScheme ("def"));

    // Document type
    dd.setDocumentTypeWithDefaultScheme ("doctype");
    assertEquals (CIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME, dd.getDocumentTypeScheme ());
    assertEquals ("doctype", dd.getDocumentTypeValue ());
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (dd,
                                                                           new PeppolSBDHDocument ().setSenderWithDefaultScheme ("abc")
                                                                                              .setReceiverWithDefaultScheme ("def")
                                                                                              .setDocumentTypeWithDefaultScheme ("other"));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (dd,
                                                                       new PeppolSBDHDocument ().setSenderWithDefaultScheme ("abc")
                                                                                          .setReceiverWithDefaultScheme ("def")
                                                                                          .setDocumentTypeWithDefaultScheme ("doctype"));

    // Process type
    dd.setProcessWithDefaultScheme ("proctype");
    assertEquals (CIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME, dd.getProcessScheme ());
    assertEquals ("proctype", dd.getProcessValue ());
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (dd,
                                                                           new PeppolSBDHDocument ().setSenderWithDefaultScheme ("abc")
                                                                                              .setReceiverWithDefaultScheme ("def")
                                                                                              .setDocumentTypeWithDefaultScheme ("doctype")
                                                                                              .setProcessWithDefaultScheme ("other"));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (dd,
                                                                       new PeppolSBDHDocument ().setSenderWithDefaultScheme ("abc")
                                                                                          .setReceiverWithDefaultScheme ("def")
                                                                                          .setDocumentTypeWithDefaultScheme ("doctype")
                                                                                          .setProcessWithDefaultScheme ("proctype"));
  }
}
