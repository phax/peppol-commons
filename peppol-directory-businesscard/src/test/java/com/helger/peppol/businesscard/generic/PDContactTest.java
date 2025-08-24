/*
 * Copyright (C) 2015-2025 Philip Helger (www.helger.com)
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
package com.helger.peppol.businesscard.generic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.helger.json.IJsonObject;
import com.helger.unittest.support.TestHelper;

/**
 * Test class for class {@link PDContact}.
 *
 * @author Philip Helger
 */
public final class PDContactTest
{
  @Test
  public void testBasic ()
  {
    // Regular
    PDContact aContact = new PDContact ("t", "n", "p", "e");
    assertEquals ("t", aContact.getType ());
    assertEquals ("n", aContact.getName ());
    assertEquals ("p", aContact.getPhoneNumber ());
    assertEquals ("e", aContact.getEmail ());

    TestHelper.testDefaultSerialization (aContact);
    TestHelper.testDefaultImplementationWithEqualContentObject (aContact, new PDContact ("t", "n", "p", "e"));
    TestHelper.testDefaultImplementationWithDifferentContentObject (aContact, new PDContact ("t2", "n", "p", "e"));
    TestHelper.testDefaultImplementationWithDifferentContentObject (aContact, new PDContact ("t", "n2", "p", "e"));
    TestHelper.testDefaultImplementationWithDifferentContentObject (aContact, new PDContact ("t", "n", "p2", "e"));
    TestHelper.testDefaultImplementationWithDifferentContentObject (aContact, new PDContact ("t", "n", "p", "e2"));
    TestHelper.testDefaultImplementationWithDifferentContentObject (aContact, new PDContact (null, "n", "p", "e"));
    TestHelper.testDefaultImplementationWithDifferentContentObject (aContact, new PDContact ("t", null, "p", "e"));
    TestHelper.testDefaultImplementationWithDifferentContentObject (aContact, new PDContact ("t", "n", null, "e"));
    TestHelper.testDefaultImplementationWithDifferentContentObject (aContact, new PDContact ("t", "n", "p", null));
    TestHelper.testDefaultImplementationWithDifferentContentObject (aContact, new PDContact (null, null, null, null));
    assertNotNull (aContact.getAsMicroXML (null, "a"));
    assertNotNull (aContact.getAsMicroXML ("urn:example.org", "a"));

    aContact = new PDContact (null, null, null, null);
    assertNull (aContact.getType ());
    assertNull (aContact.getName ());
    assertNull (aContact.getPhoneNumber ());
    assertNull (aContact.getEmail ());
  }

  @Test
  public void testJson ()
  {
    PDContact aContact = new PDContact ("t", "n", "p", "e");
    IJsonObject aJson = aContact.getAsJson ();
    assertNotNull (aJson);
    PDContact aContact2 = PDContact.of (aJson);
    assertNotNull (aContact2);
    TestHelper.testDefaultImplementationWithEqualContentObject (aContact2, aContact);

    // No type
    aContact = new PDContact (null, "n", "p", "e");
    aJson = aContact.getAsJson ();
    assertNotNull (aJson);
    aContact2 = PDContact.of (aJson);
    assertNotNull (aContact2);
    TestHelper.testDefaultImplementationWithEqualContentObject (aContact2, aContact);

    // No name
    aContact = new PDContact ("t", null, "p", "e");
    aJson = aContact.getAsJson ();
    assertNotNull (aJson);
    aContact2 = PDContact.of (aJson);
    assertNotNull (aContact2);
    TestHelper.testDefaultImplementationWithEqualContentObject (aContact2, aContact);

    // No phone number
    aContact = new PDContact ("t", "n", null, "e");
    aJson = aContact.getAsJson ();
    assertNotNull (aJson);
    aContact2 = PDContact.of (aJson);
    assertNotNull (aContact2);
    TestHelper.testDefaultImplementationWithEqualContentObject (aContact2, aContact);

    // No email
    aContact = new PDContact ("t", "n", "p", null);
    aJson = aContact.getAsJson ();
    assertNotNull (aJson);
    aContact2 = PDContact.of (aJson);
    assertNotNull (aContact2);
    TestHelper.testDefaultImplementationWithEqualContentObject (aContact2, aContact);

    // None set
    aContact = new PDContact (null, null, null, null);
    aJson = aContact.getAsJson ();
    assertNotNull (aJson);
    aContact2 = PDContact.of (aJson);
    assertNotNull (aContact2);
    TestHelper.testDefaultImplementationWithEqualContentObject (aContact2, aContact);
  }
}
