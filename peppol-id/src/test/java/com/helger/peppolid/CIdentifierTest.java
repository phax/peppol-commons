/**
 * Copyright (C) 2015-2020 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
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
