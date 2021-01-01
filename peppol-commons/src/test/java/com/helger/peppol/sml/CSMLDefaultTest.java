/**
 * Copyright (C) 2015-2021 Philip Helger
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
package com.helger.peppol.sml;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.regex.RegExHelper;

/**
 * Test class for class {@link CSMLDefault}.
 *
 * @author Philip Helger
 */
public final class CSMLDefaultTest
{
  @Test
  public void testSMLMigrationCode ()
  {
    // Straight forward
    assertTrue (RegExHelper.stringMatchesPattern (CSMLDefault.MIGRATION_CODE_PATTERN, "11AAbb$$"));
    assertTrue (RegExHelper.stringMatchesPattern (CSMLDefault.MIGRATION_CODE_PATTERN, "11AAbb$$11AAbb$$11AAbb$$"));

    // Mixed order
    assertTrue (RegExHelper.stringMatchesPattern (CSMLDefault.MIGRATION_CODE_PATTERN, "1Ab$1Ab$"));
    assertTrue (RegExHelper.stringMatchesPattern (CSMLDefault.MIGRATION_CODE_PATTERN, "1Ab$1Ab$1Ab$1Ab$1Ab$1Ab$"));

    // Too long
    assertFalse (RegExHelper.stringMatchesPattern (CSMLDefault.MIGRATION_CODE_PATTERN, "11AAbb$$11AAbb$$11AAbb$$1"));
    assertFalse (RegExHelper.stringMatchesPattern (CSMLDefault.MIGRATION_CODE_PATTERN,
                                                   "1111111111111111111111111111111111111111111111111111111111111111111111111111"));
    // Too short
    assertFalse (RegExHelper.stringMatchesPattern (CSMLDefault.MIGRATION_CODE_PATTERN, "11AAbb$"));
    assertFalse (RegExHelper.stringMatchesPattern (CSMLDefault.MIGRATION_CODE_PATTERN, ""));
    // To few numbers
    assertFalse (RegExHelper.stringMatchesPattern (CSMLDefault.MIGRATION_CODE_PATTERN, "1zAAbb$$"));
    // No numbers
    assertFalse (RegExHelper.stringMatchesPattern (CSMLDefault.MIGRATION_CODE_PATTERN, "zzAAbb$$"));
    // Too few upper case chars
    assertFalse (RegExHelper.stringMatchesPattern (CSMLDefault.MIGRATION_CODE_PATTERN, "11Azbb$$"));
    // No upper case chars
    assertFalse (RegExHelper.stringMatchesPattern (CSMLDefault.MIGRATION_CODE_PATTERN, "11zzbb$$"));
    // To few lower case chars
    assertFalse (RegExHelper.stringMatchesPattern (CSMLDefault.MIGRATION_CODE_PATTERN, "11AAZb$$"));
    // No lower case chars
    assertFalse (RegExHelper.stringMatchesPattern (CSMLDefault.MIGRATION_CODE_PATTERN, "11AAZZ$$"));
    // To few special chars
    assertFalse (RegExHelper.stringMatchesPattern (CSMLDefault.MIGRATION_CODE_PATTERN, "11AAbbz$"));
    // No special chars
    assertFalse (RegExHelper.stringMatchesPattern (CSMLDefault.MIGRATION_CODE_PATTERN, "11AAbbzz"));
    // Wrong special chars
    assertFalse (RegExHelper.stringMatchesPattern (CSMLDefault.MIGRATION_CODE_PATTERN, "11AAbb§§"));
    // Blanks
    assertFalse (RegExHelper.stringMatchesPattern (CSMLDefault.MIGRATION_CODE_PATTERN, " 11AAbb$$"));
    assertFalse (RegExHelper.stringMatchesPattern (CSMLDefault.MIGRATION_CODE_PATTERN, "11AA bb$$"));
    assertFalse (RegExHelper.stringMatchesPattern (CSMLDefault.MIGRATION_CODE_PATTERN, "11AAbb$$ "));
  }
}
