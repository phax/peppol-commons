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

import com.helger.commons.mock.CommonsTestHelper;
import com.helger.json.IJsonObject;

/**
 * Test class for class {@link PDIdentifier}.
 *
 * @author Philip Helger
 */
public final class PDIdentifierTest
{
  @Test
  public void testBasic ()
  {
    // Regular
    PDIdentifier aID = new PDIdentifier ("s", "v");
    assertEquals ("s", aID.getScheme ());
    assertEquals ("v", aID.getValue ());

    CommonsTestHelper.testDefaultSerialization (aID);
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aID, new PDIdentifier ("s", "v"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aID, new PDIdentifier ("s2", "v"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aID, new PDIdentifier ("s", "v2"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aID, new PDIdentifier (null, "v"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aID, new PDIdentifier ("s", null));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aID, new PDIdentifier (null, null));
    assertNotNull (aID.getAsMicroXML (null, "a"));
    assertNotNull (aID.getAsMicroXML ("urn:example.org", "a"));

    aID = new PDIdentifier (null, "v");
    assertNull (aID.getScheme ());
    assertEquals ("v", aID.getValue ());

    aID = new PDIdentifier ("s", null);
    assertEquals ("s", aID.getScheme ());
    assertNull (aID.getValue ());

    aID = new PDIdentifier (null, null);
    assertNull (aID.getScheme ());
    assertNull (aID.getValue ());

  }

  @Test
  public void testJson ()
  {
    PDIdentifier aID = new PDIdentifier ("s", "v");
    IJsonObject aJson = aID.getAsJson ();
    assertNotNull (aJson);
    PDIdentifier aID2 = PDIdentifier.of (aJson);
    assertNotNull (aID2);
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aID2, aID);

    // No scheme
    aID = new PDIdentifier (null, "v");
    aJson = aID.getAsJson ();
    assertNotNull (aJson);
    aID2 = PDIdentifier.of (aJson);
    assertNotNull (aID2);
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aID2, aID);

    // No value
    aID = new PDIdentifier ("v", null);
    aJson = aID.getAsJson ();
    assertNotNull (aJson);
    aID2 = PDIdentifier.of (aJson);
    assertNotNull (aID2);
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aID2, aID);

    // Neither nor
    aID = new PDIdentifier (null, null);
    aJson = aID.getAsJson ();
    assertNotNull (aJson);
    aID2 = PDIdentifier.of (aJson);
    assertNotNull (aID2);
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aID2, aID);
  }
}
