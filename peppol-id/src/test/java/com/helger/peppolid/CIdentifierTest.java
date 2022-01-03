/*
 * Copyright (C) 2015-2022 Philip Helger
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
package com.helger.peppolid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Test class for class {@link CIdentifier}.
 *
 * @author Philip Helger
 */
public final class CIdentifierTest
{
  @Test
  public void testCreatePercentEncodedURL ()
  {
    assertNull (CIdentifier.createPercentEncoded (null));
    assertEquals ("", CIdentifier.createPercentEncoded (""));
    assertEquals ("abc", CIdentifier.createPercentEncoded ("abc"));
    assertEquals ("a%25b", CIdentifier.createPercentEncoded ("a%b"));
    assertEquals ("a%25%25b", CIdentifier.createPercentEncoded ("a%%b"));
    assertEquals ("a%2Fb", CIdentifier.createPercentEncoded ("a/b"));
  }

  @Test
  public void testCreatePercentDecodedURL ()
  {
    assertNull (CIdentifier.createPercentDecoded (null));
    assertEquals ("", CIdentifier.createPercentDecoded (""));
    assertEquals ("abc", CIdentifier.createPercentDecoded ("abc"));
    assertEquals (":", CIdentifier.createPercentDecoded ("%3A"));
    assertEquals ("::", CIdentifier.createPercentDecoded ("%3A%3a"));
    assertEquals ("a%b", CIdentifier.createPercentDecoded ("a%25b"));
    assertEquals ("a%%b", CIdentifier.createPercentDecoded ("a%25%25b"));
    assertEquals ("a/b", CIdentifier.createPercentDecoded ("a%2Fb"));
    assertEquals ("äöü", CIdentifier.createPercentDecoded ("äöü"));
  }
}
