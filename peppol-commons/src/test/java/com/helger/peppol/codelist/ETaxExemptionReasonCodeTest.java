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
package com.helger.peppol.codelist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.string.StringHelper;

/**
 * Test class for class {@link ETaxExemptionReasonCode}.
 *
 * @author Philip Helger
 */
@Deprecated
public final class ETaxExemptionReasonCodeTest
{
  @Test
  public void testAll ()
  {
    for (final ETaxExemptionReasonCode e : ETaxExemptionReasonCode.values ())
    {
      assertTrue (StringHelper.hasText (e.getID ()));
      assertTrue (StringHelper.hasText (e.getDisplayName ()));
      assertSame (e, ETaxExemptionReasonCode.getFromIDOrNull (e.getID ()));
      assertEquals (e.getDisplayName (), ETaxExemptionReasonCode.getDisplayNameFromIDOrNull (e.getID ()));
    }
  }
}
