/*
 * Copyright (C) 2015-2026 Philip Helger
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
package com.helger.peppolid.peppol.enduser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.function.UnaryOperator;

import org.junit.Test;

/**
 * Test class for class {@link PeppolEndUserIDMapping}.
 *
 * @author Philip Helger
 */
public final class PeppolEndUserIDMappingTest
{
  @Test
  public void testValueUnchanged ()
  {
    final PeppolEndUserIDMapping aMapping = PeppolEndUserIDMapping.createValueUnchanged ("9930", "0246");
    assertEquals ("9930", aMapping.getSourceISO6523Code ());
    assertEquals ("0246", aMapping.getTargetISO6523Code ());
    assertEquals ("de123456789", aMapping.getMappedValue ("de123456789"));
  }

  @Test
  public void testValueWithoutPrefix ()
  {
    final PeppolEndUserIDMapping aMapping = PeppolEndUserIDMapping.createValueWithoutPrefix ("9925", "0208", "BE");
    assertEquals ("9925", aMapping.getSourceISO6523Code ());
    assertEquals ("0208", aMapping.getTargetISO6523Code ());
    // Prefix is case insensitive
    assertEquals ("0123456789", aMapping.getMappedValue ("BE0123456789"));
    assertEquals ("0123456789", aMapping.getMappedValue ("be0123456789"));
    assertEquals ("0123456789", aMapping.getMappedValue ("Be0123456789"));
    // No prefix present
    assertEquals ("0123456789", aMapping.getMappedValue ("0123456789"));
    // Nothing left after removing the prefix
    assertNull (aMapping.getMappedValue ("BE"));
  }

  @Test
  public void testInvalidISO6523Codes ()
  {
    final UnaryOperator <String> aIdentity = UnaryOperator.identity ();
    for (final String sInvalid : new String [] { "", "123", "12345", "abcd", "092 " })
    {
      try
      {
        new PeppolEndUserIDMapping (sInvalid, "0208", aIdentity);
        fail ();
      }
      catch (final IllegalArgumentException ex)
      {
        // Expected
      }

      try
      {
        new PeppolEndUserIDMapping ("9925", sInvalid, aIdentity);
        fail ();
      }
      catch (final IllegalArgumentException ex)
      {
        // Expected
      }
    }
  }
}
