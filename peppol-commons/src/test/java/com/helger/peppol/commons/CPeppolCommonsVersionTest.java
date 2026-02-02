/*
 * Copyright (C) 2015-2026 Philip Helger (www.helger.com)
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
package com.helger.peppol.commons;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Test class for class {@link CPeppolCommonsVersion}
 *
 * @author Philip Helger
 */
public final class CPeppolCommonsVersionTest
{
  @Test
  public void testBasic ()
  {
    assertNotEquals ("undefined", CPeppolCommonsVersion.BUILD_VERSION);
    assertNotEquals ("undefined", CPeppolCommonsVersion.BUILD_TIMESTAMP);

    // Check variable resolution
    assertFalse (CPeppolCommonsVersion.BUILD_VERSION.contains ("${"));
    assertFalse (CPeppolCommonsVersion.BUILD_TIMESTAMP.contains ("${"));
  }
}
