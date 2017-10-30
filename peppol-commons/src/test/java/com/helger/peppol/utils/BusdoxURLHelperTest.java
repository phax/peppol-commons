/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Test class for class {@link BusdoxURLHelper}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class BusdoxURLHelperTest
{
  @Test
  public void testCreatePercentEncodedURL ()
  {
    assertNull (BusdoxURLHelper.createPercentEncodedURL (null));
    assertEquals ("", BusdoxURLHelper.createPercentEncodedURL (""));
    assertEquals ("abc", BusdoxURLHelper.createPercentEncodedURL ("abc"));
    assertEquals ("a%25b", BusdoxURLHelper.createPercentEncodedURL ("a%b"));
    assertEquals ("a%25%25b", BusdoxURLHelper.createPercentEncodedURL ("a%%b"));
    assertEquals ("a%2Fb", BusdoxURLHelper.createPercentEncodedURL ("a/b"));
  }

  @Test
  public void testCreatePercentDecodedURL ()
  {
    assertNull (BusdoxURLHelper.createPercentDecodedURL (null));
    assertEquals ("", BusdoxURLHelper.createPercentDecodedURL (""));
    assertEquals ("abc", BusdoxURLHelper.createPercentDecodedURL ("abc"));
    assertEquals ("a%b", BusdoxURLHelper.createPercentDecodedURL ("a%25b"));
    assertEquals ("a%%b", BusdoxURLHelper.createPercentDecodedURL ("a%25%25b"));
    assertEquals ("a/b", BusdoxURLHelper.createPercentDecodedURL ("a%2Fb"));
    assertEquals ("äöü", BusdoxURLHelper.createPercentDecodedURL ("äöü"));
  }
}
