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
package com.helger.dbnalliance.commons.smp;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.network.smp.SMPTransportProfileRegistry;
import com.helger.peppolid.factory.IdentifierFactoryTypeRegistry;

/**
 * Test class for class {@link EDBNAllianceTransportProfile}.
 *
 * @author Philip Helger
 */
public final class EDBNAllianceTransportProfileTest
{
  @Test
  public void testRegisteredInTheCentralRegistries ()
  {
    for (final EDBNAllianceTransportProfile e : EDBNAllianceTransportProfile.values ())
    {
      assertTrue (SMPTransportProfileRegistry.containsTransportProfileOfID (e.getID ()));
      assertSame (e, SMPTransportProfileRegistry.getTransportProfileOfIDOrNull (e.getID ()));
    }
    // The Peppol profiles are visible here as well, because both SPI implementations are on the
    // class path
    assertTrue (SMPTransportProfileRegistry.containsTransportProfileOfID ("peppol-transport-as4-v2_0"));
    assertTrue (IdentifierFactoryTypeRegistry.containsIdentifierFactoryTypeOfID ("dbnalliance"));
  }
}
