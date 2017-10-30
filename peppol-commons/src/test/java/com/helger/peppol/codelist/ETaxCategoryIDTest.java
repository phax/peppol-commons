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
package com.helger.peppol.codelist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.string.StringHelper;

/**
 * Test class for class {@link ETaxCategoryID}.
 *
 * @author Philip Helger
 */
public final class ETaxCategoryIDTest
{
  @Test
  public void testAll ()
  {
    for (final ETaxCategoryID e : ETaxCategoryID.values ())
    {
      assertTrue (StringHelper.hasText (e.getID ()));
      assertTrue (StringHelper.hasText (e.getDisplayName ()));
      assertSame (e, ETaxCategoryID.getFromIDOrNull (e.getID ()));
      assertEquals (e.getDisplayName (), ETaxCategoryID.getDisplayNameFromIDOrNull (e.getID ()));
    }
  }
}
