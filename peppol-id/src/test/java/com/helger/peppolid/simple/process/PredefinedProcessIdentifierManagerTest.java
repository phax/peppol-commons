/*
 * Copyright (C) 2015-2025 Philip Helger
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
package com.helger.peppolid.simple.process;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.peppolid.peppol.process.PredefinedProcessIdentifierManager;

/**
 * Test class for class {@link PredefinedProcessIdentifierManager}.
 *
 * @author Philip Helger
 */
public class PredefinedProcessIdentifierManagerTest
{
  @Test
  public void testBasicWithoutScheme ()
  {
    final String sValid = "urn:fdc:peppol.eu:2017:poacc:billing:01:1.0";
    assertTrue (PredefinedProcessIdentifierManager.containsProcessIdentifierWithID (sValid));
    assertFalse (PredefinedProcessIdentifierManager.containsProcessIdentifierWithID (sValid + "0"));

    assertNotNull (PredefinedProcessIdentifierManager.getProcessIdentifierOfID (sValid));
    assertNull (PredefinedProcessIdentifierManager.getProcessIdentifierOfID (sValid + "0"));
  }

  @Test
  public void testBasicWithScheme ()
  {
    final String sValid = "cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0";
    assertTrue (PredefinedProcessIdentifierManager.containsProcessIdentifierWithID (sValid));
    assertFalse (PredefinedProcessIdentifierManager.containsProcessIdentifierWithID (sValid + "0"));

    assertNotNull (PredefinedProcessIdentifierManager.getProcessIdentifierOfID (sValid));
    assertNull (PredefinedProcessIdentifierManager.getProcessIdentifierOfID (sValid + "0"));
  }
}
