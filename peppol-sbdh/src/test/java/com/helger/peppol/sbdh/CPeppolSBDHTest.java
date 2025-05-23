/*
 * Copyright (C) 2014-2025 Philip Helger
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
package com.helger.peppol.sbdh;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Test class for class {@link CPeppolSBDH}.
 *
 * @author Philip Helger
 */
public final class CPeppolSBDHTest
{
  @Test
  @SuppressFBWarnings ("DMI_DOH")
  public void testLoading ()
  {
    assertNotNull (CPeppolSBDH.class);
  }
}
