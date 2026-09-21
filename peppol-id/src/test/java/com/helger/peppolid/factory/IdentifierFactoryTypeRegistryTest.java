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
package com.helger.peppolid.factory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for class {@link IdentifierFactoryTypeRegistry}.
 *
 * @author Philip Helger
 */
public final class IdentifierFactoryTypeRegistryTest
{
  @Test
  public void testAllPredefinedTypesAreRegistered ()
  {
    for (final ESMPIdentifierType e : ESMPIdentifierType.values ())
    {
      assertTrue (IdentifierFactoryTypeRegistry.containsIdentifierFactoryTypeOfID (e.getID ()));
      assertSame (e, IdentifierFactoryTypeRegistry.getIdentifierFactoryTypeOfIDOrNull (e.getID ()));
    }
  }

  @Test
  public void testForeignNetworksAreNotContained ()
  {
    // DBNAlliance provides its identifier factory type itself
    assertFalse (IdentifierFactoryTypeRegistry.containsIdentifierFactoryTypeOfID ("dbnalliance"));
  }
}
