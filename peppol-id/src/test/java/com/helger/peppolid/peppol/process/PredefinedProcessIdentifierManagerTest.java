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
package com.helger.peppolid.peppol.process;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for class {@link PredefinedProcessIdentifierManager}.
 *
 * @author Philip Helger
 */
public final class PredefinedProcessIdentifierManagerTest
{
  @Test
  public void testAll ()
  {
    assertNotNull (PredefinedProcessIdentifierManager.getAllProcessIdentifiers ());
    assertNotNull (PredefinedProcessIdentifierManager.getAllProcessIdentifierIDs ());

    final IPeppolPredefinedProcessIdentifier aPPI = PredefinedProcessIdentifierManager.getProcessIdentifierOfID ("urn:www.cenbii.eu:profile:bii01:ver1.0");
    assertNotNull (aPPI);

    assertNotNull (PredefinedProcessIdentifierManager.getProcessIdentifierOfID ("urn:www.cenbii.eu:profile:bii01:ver1.0"));
    assertNull (PredefinedProcessIdentifierManager.getProcessIdentifierOfID ("urn:www.cenbii.eu:profile:bii01:ver1.0a"));
    assertTrue (PredefinedProcessIdentifierManager.containsProcessIdentifierWithID ("urn:www.cenbii.eu:profile:bii01:ver1.0"));
    assertFalse (PredefinedProcessIdentifierManager.containsProcessIdentifierWithID ("urn:www.cenbii.eu:profile:bii01:ver1.0a"));
  }
}
