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
package com.helger.peppol.smp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.network.smp.ISMPTransportProfile;
import com.helger.network.smp.SMPTransportProfileRegistry;

/**
 * Test class for class {@link SMPTransportProfileRegistry} with the Peppol transport profiles.
 *
 * @author Philip Helger
 */
public final class SMPTransportProfileRegistryTest
{
  @Test
  public void testAllPeppolProfilesAreRegistered ()
  {
    for (final ESMPTransportProfile e : ESMPTransportProfile.values ())
    {
      assertTrue (SMPTransportProfileRegistry.containsTransportProfileOfID (e.getID ()));
      assertSame (e, SMPTransportProfileRegistry.getTransportProfileOfIDOrNull (e.getID ()));
    }
  }

  @Test
  public void testForeignNetworksAreNotContained ()
  {
    // DBNAlliance and HR eDelivery provide their transport profiles themselves - they are not
    // visible from this module
    assertFalse (SMPTransportProfileRegistry.containsTransportProfileOfID ("bdxr-as4-1.0#dbnalliance-1.0"));
    assertFalse (SMPTransportProfileRegistry.containsTransportProfileOfID ("eracun-transport-as4-v1_0"));
  }

  @Test
  public void testRuntimeRegistration ()
  {
    final ISMPTransportProfile aProfile = new com.helger.network.smp.SMPTransportProfile ("unit-test-profile",
                                                                                          "Unit Test");
    assertTrue (SMPTransportProfileRegistry.registerTransportProfile (aProfile).isChanged ());
    assertSame (aProfile, SMPTransportProfileRegistry.getTransportProfileOfIDOrNull ("unit-test-profile"));
    // A second registration of the same ID does not overwrite
    assertTrue (SMPTransportProfileRegistry.registerTransportProfile (aProfile).isUnchanged ());
    SMPTransportProfileRegistry.reinitialize ();
    assertFalse (SMPTransportProfileRegistry.containsTransportProfileOfID ("unit-test-profile"));
  }
}
