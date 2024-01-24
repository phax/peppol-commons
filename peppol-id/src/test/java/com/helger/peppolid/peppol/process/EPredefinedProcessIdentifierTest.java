/*
 * Copyright (C) 2015-2024 Philip Helger
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
package com.helger.peppolid.peppol.process;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.string.StringHelper;
import com.helger.peppolid.simple.process.SimpleProcessIdentifier;

/**
 * Test class for class {@link EPredefinedProcessIdentifier}.
 *
 * @author Philip Helger
 */
public final class EPredefinedProcessIdentifierTest
{
  @Test
  public void testAll ()
  {
    for (final EPredefinedProcessIdentifier e : EPredefinedProcessIdentifier.values ())
    {
      assertTrue (StringHelper.hasText (e.getScheme ()));
      assertTrue (StringHelper.hasText (e.getValue ()));
      assertSame (e, EPredefinedProcessIdentifier.valueOf (e.name ()));
      assertSame (e, EPredefinedProcessIdentifier.getFromProcessIdentifierOrNull (e));
    }
    assertNull (EPredefinedProcessIdentifier.getFromProcessIdentifierOrNull (null));
    assertNull (EPredefinedProcessIdentifier.getFromProcessIdentifierOrNull (new SimpleProcessIdentifier ("bla", "foo")));
  }
}
