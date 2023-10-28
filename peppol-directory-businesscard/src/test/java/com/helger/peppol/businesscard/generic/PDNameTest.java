/*
 * Copyright (C) 2015-2023 Philip Helger (www.helger.com)
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;
import com.helger.json.IJsonObject;

/**
 * Test class for class {@link PDName}.
 *
 * @author Philip Helger
 */
public final class PDNameTest
{
  @Test
  public void testBasic ()
  {
    // Regular
    PDName aName = new PDName ("ACME", "en");
    assertEquals ("ACME", aName.getName ());
    assertEquals ("en", aName.getLanguageCode ());
    assertFalse (aName.hasNoLanguageCode ());

    CommonsTestHelper.testDefaultSerialization (aName);
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aName, new PDName ("ACME", "en"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aName, new PDName ("ACME2", "en"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aName, new PDName ("ACME", "de"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aName, new PDName ("ACME"));
    assertNotNull (aName.getAsMicroXML (null, "a"));
    assertNotNull (aName.getAsMicroXML ("urn:example.org", "a"));

    // Upper case language name
    aName = new PDName ("ACME", "EN");
    assertEquals ("ACME", aName.getName ());
    assertEquals ("en", aName.getLanguageCode ());
    assertFalse (aName.hasNoLanguageCode ());
    assertNotNull (aName.getAsMicroXML (null, "a"));
    assertNotNull (aName.getAsMicroXML ("urn:example.org", "a"));

    // No language
    aName = new PDName ("ACME");
    assertEquals ("ACME", aName.getName ());
    assertNull (aName.getLanguageCode ());
    assertTrue (aName.hasNoLanguageCode ());
    assertNotNull (aName.getAsMicroXML (null, "a"));
    assertNotNull (aName.getAsMicroXML ("urn:example.org", "a"));

    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aName, new PDName ("ACME"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aName, new PDName ("ACME2"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aName, new PDName ("ACME", "en"));
  }

  @SuppressWarnings ("unused")
  @Test
  public void testInvalid ()
  {
    // No such language
    try
    {
      new PDName ("ACME", "123");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }

    // Invalid name
    try
    {
      new PDName ("", "en");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }

    // Invalid name
    try
    {
      new PDName (null, "en");
      fail ();
    }
    catch (final NullPointerException ex)
    {
      // expected
    }
  }

  @Test
  public void testJson ()
  {
    PDName aName = new PDName ("ACME", "en");
    IJsonObject aJson = aName.getAsJson ();
    assertNotNull (aJson);
    PDName aName2 = PDName.of (aJson);
    assertNotNull (aName2);
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aName2, aName);

    // No language
    aName = new PDName ("ACME");
    aJson = aName.getAsJson ();
    assertNotNull (aJson);
    aName2 = PDName.of (aJson);
    assertNotNull (aName2);
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aName2, aName);
  }
}
