/**
 * Copyright (C) 2015-2018 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.identifier.peppol.process;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.generic.process.SimpleProcessIdentifier;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;

/**
 * Test class for class {@link EPredefinedProcessIdentifier}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class EPredefinedProcessIdentifierTest
{
  @Test
  public void testAll ()
  {
    for (final EPredefinedProcessIdentifier e : EPredefinedProcessIdentifier.values ())
    {
      assertEquals (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME, e.getScheme ());
      assertTrue (StringHelper.hasText (e.getValue ()));
      assertTrue (StringHelper.hasText (e.getBISID ()));
      assertSame (e, EPredefinedProcessIdentifier.valueOf (e.name ()));
      assertSame (e, EPredefinedProcessIdentifier.getFromProcessIdentifierOrNull (e));
    }
    assertNull (EPredefinedProcessIdentifier.getFromProcessIdentifierOrNull (null));
    assertNull (EPredefinedProcessIdentifier.getFromProcessIdentifierOrNull (new SimpleProcessIdentifier ("bla",
                                                                                                          "foo")));
  }
}
