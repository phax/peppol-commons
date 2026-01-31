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
package com.helger.peppolid.peppol.spis;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.peppolid.peppol.spisusecase.EPredefinedSPISUseCaseIdentifier;

/**
 * Test class for {@link SPIDHelper}
 *
 * @author Philip Helger
 */
public final class SPIDHelperTest
{
  @Test
  public void testIsValidMainID ()
  {
    assertTrue (SPIDHelper.isValidMainID ("000000"));
    assertTrue (SPIDHelper.isValidMainID ("123456"));
    assertTrue (SPIDHelper.isValidMainID ("999999"));

    assertFalse (SPIDHelper.isValidMainID ("00000"));
    assertFalse (SPIDHelper.isValidMainID ("00123"));

    assertFalse (SPIDHelper.isValidMainID ("00000a"));
    assertFalse (SPIDHelper.isValidMainID ("0000a1"));
    assertFalse (SPIDHelper.isValidMainID (" 999999"));
    assertFalse (SPIDHelper.isValidMainID ("999999 "));

    assertFalse (SPIDHelper.isValidMainID ("0000123"));
    assertFalse (SPIDHelper.isValidMainID ("a000123"));
    assertFalse (SPIDHelper.isValidMainID ("000123b"));

    assertFalse (SPIDHelper.isValidMainID (""));
    assertFalse (SPIDHelper.isValidMainID (null));
  }

  @Test
  public void testIsValidUseCaseID ()
  {
    for (final EPredefinedSPISUseCaseIdentifier e : EPredefinedSPISUseCaseIdentifier.values ())
      assertTrue (SPIDHelper.isValidUseCaseID (e.getUseCaseID ()));

    assertFalse (SPIDHelper.isValidUseCaseID ("12"));
    assertTrue (SPIDHelper.isValidUseCaseID ("123"));
    assertTrue (SPIDHelper.isValidUseCaseID ("123456789012"));
    assertFalse (SPIDHelper.isValidUseCaseID ("1234567890123"));

    assertFalse (SPIDHelper.isValidUseCaseID (""));
    assertFalse (SPIDHelper.isValidUseCaseID (null));
  }

  @Test
  public void testIsValidServiceProviderSuffix ()
  {
    assertTrue (SPIDHelper.isValidServiceProviderSuffix ("abc"));
    assertTrue (SPIDHelper.isValidServiceProviderSuffix ("ABC"));
    assertTrue (SPIDHelper.isValidServiceProviderSuffix ("123"));
    assertTrue (SPIDHelper.isValidServiceProviderSuffix ("123456789012345678901234"));

    assertFalse (SPIDHelper.isValidServiceProviderSuffix ("12"));
    assertFalse (SPIDHelper.isValidServiceProviderSuffix ("1234567890123456789012345"));

    assertFalse (SPIDHelper.isValidServiceProviderSuffix (""));
    assertFalse (SPIDHelper.isValidServiceProviderSuffix (null));
  }

  @Test
  public void testIsValidSPID ()
  {
    assertTrue (SPIDHelper.isValidSPID ("000000"));
    assertTrue (SPIDHelper.isValidSPID ("000000-MLS"));
    assertTrue (SPIDHelper.isValidSPID ("000000-mls"));
    assertTrue (SPIDHelper.isValidSPID ("000000-mls.12345"));

    assertFalse (SPIDHelper.isValidSPID ("00000a-mls.12345"));
    assertFalse (SPIDHelper.isValidSPID ("00000-mls.12345"));
    assertFalse (SPIDHelper.isValidSPID ("0000000-mls.12345"));

    assertFalse (SPIDHelper.isValidSPID ("000000-ml.12345"));
    assertFalse (SPIDHelper.isValidSPID ("000000-1234567890123.12345"));

    assertFalse (SPIDHelper.isValidSPID ("000000-mls.12"));
    assertFalse (SPIDHelper.isValidSPID ("000000-mls.1234567890123456789012345"));

    assertFalse (SPIDHelper.isValidSPID (""));
    assertFalse (SPIDHelper.isValidSPID (null));
  }
}
