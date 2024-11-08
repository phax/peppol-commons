/*
 * Copyright (C) 2015-2024 Philip Helger
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
package com.helger.peppol.supplementary.tools;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.junit.Test;

import com.helger.commons.regex.RegExHelper;

public class RegExFuncTest
{
  @Test
  public void testRegExSPIS ()
  {
    final String sRegEx = "[0-9]{6}(-[0-9A-Z_]{3,12}(\\.[0-9A-Z\\-\\._~]{3,24})?)?";

    final Predicate <String> pred = x -> RegExHelper.stringMatchesPattern (sRegEx, Pattern.CASE_INSENSITIVE, x);

    assertTrue (pred.test ("000001"));
    assertTrue (pred.test ("000270"));
    assertTrue (pred.test ("010101"));
    assertTrue (pred.test ("999999"));
    assertFalse (pred.test ("99999"));
    assertFalse (pred.test ("a99999"));
    assertFalse (pred.test ("9999999"));

    assertTrue (pred.test ("000001-AAA"));
    assertTrue (pred.test ("000001-Rprtng_MLS"));
    assertTrue (pred.test ("000270-1234567"));
    assertFalse (pred.test ("000270.12"));
    assertFalse (pred.test ("000270.1234567"));
    assertFalse (pred.test ("0002701234567"));
    assertFalse (pred.test ("000270--1234567"));

    assertTrue (pred.test ("000001-MLS.001"));
    assertTrue (pred.test ("000001-001.1234567"));
    assertTrue (pred.test ("000270-Rprtng_MLS.Japan.123"));
  }
}
