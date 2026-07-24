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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
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
  public void testBasic ()
  {
    assertEquals ("0242", SPIDHelper.SPIS_PARTICIPANT_ID_SCHEME);
  }

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

    assertTrue (SPIDHelper.isValidSPID ("000001"));
    assertTrue (SPIDHelper.isValidSPID ("000270"));
    assertTrue (SPIDHelper.isValidSPID ("010101"));
    assertTrue (SPIDHelper.isValidSPID ("999999"));
    assertFalse (SPIDHelper.isValidSPID ("99999"));
    assertFalse (SPIDHelper.isValidSPID ("a99999"));
    assertFalse (SPIDHelper.isValidSPID ("9999999"));

    assertTrue (SPIDHelper.isValidSPID ("000001-AAA"));
    assertTrue (SPIDHelper.isValidSPID ("000001-Rprtng_MLS"));
    assertTrue (SPIDHelper.isValidSPID ("000270-1234567"));
    assertFalse (SPIDHelper.isValidSPID ("000270.12"));
    assertFalse (SPIDHelper.isValidSPID ("000270.1234567"));
    assertFalse (SPIDHelper.isValidSPID ("0002701234567"));
    assertFalse (SPIDHelper.isValidSPID ("000270--1234567"));

    assertTrue (SPIDHelper.isValidSPID ("000001-MLS.001"));
    assertTrue (SPIDHelper.isValidSPID ("000001-001.1234567"));
    assertTrue (SPIDHelper.isValidSPID ("000270-Rprtng_MLS.Japan.123"));
  }

  @Test
  public void testGetMainID ()
  {
    assertEquals ("000000", SPIDHelper.getMainID ("000000"));
    assertEquals ("123456", SPIDHelper.getMainID ("123456"));
    assertEquals ("999999", SPIDHelper.getMainID ("999999"));

    // Main ID is extracted from a full SPID (Use Case ID and Service Provider suffix stripped)
    assertEquals ("000000", SPIDHelper.getMainID ("000000-MLS"));
    assertEquals ("000000", SPIDHelper.getMainID ("000000-mls"));
    assertEquals ("000000", SPIDHelper.getMainID ("000000-mls.12345"));
    assertEquals ("000270", SPIDHelper.getMainID ("000270-Rprtng_MLS.Japan.123"));

    // Invalid SPIDs yield null
    assertNull (SPIDHelper.getMainID ("00000"));
    assertNull (SPIDHelper.getMainID ("0000000"));
    assertNull (SPIDHelper.getMainID ("00000a"));
    assertNull (SPIDHelper.getMainID ("000000-ml.12345"));
    assertNull (SPIDHelper.getMainID (""));
    assertNull (SPIDHelper.getMainID (null));
  }

  @Test
  public void testGetUseCaseID ()
  {
    // Main ID only -> no Use Case ID
    assertNull (SPIDHelper.getUseCaseID ("000000"));

    assertEquals ("MLS", SPIDHelper.getUseCaseID ("000000-MLS"));
    assertEquals ("mls", SPIDHelper.getUseCaseID ("000000-mls"));
    assertEquals ("mls", SPIDHelper.getUseCaseID ("000000-mls.12345"));
    assertEquals ("001", SPIDHelper.getUseCaseID ("000001-001.1234567"));
    assertEquals ("Rprtng_MLS", SPIDHelper.getUseCaseID ("000270-Rprtng_MLS.Japan.123"));

    // Invalid SPIDs yield null
    assertNull (SPIDHelper.getUseCaseID ("000000-ml.12345"));
    assertNull (SPIDHelper.getUseCaseID ("00000a"));
    assertNull (SPIDHelper.getUseCaseID (""));
    assertNull (SPIDHelper.getUseCaseID (null));
  }

  @Test
  public void testGetServiceProviderSuffix ()
  {
    // No suffix
    assertNull (SPIDHelper.getServiceProviderSuffix ("000000"));
    assertNull (SPIDHelper.getServiceProviderSuffix ("000000-MLS"));

    assertEquals ("12345", SPIDHelper.getServiceProviderSuffix ("000000-mls.12345"));
    assertEquals ("1234567", SPIDHelper.getServiceProviderSuffix ("000001-001.1234567"));
    // The suffix itself may contain '.' characters
    assertEquals ("Japan.123", SPIDHelper.getServiceProviderSuffix ("000270-Rprtng_MLS.Japan.123"));

    // Invalid SPIDs yield null
    assertNull (SPIDHelper.getServiceProviderSuffix ("000000-mls.12"));
    assertNull (SPIDHelper.getServiceProviderSuffix ("00000a"));
    assertNull (SPIDHelper.getServiceProviderSuffix (""));
    assertNull (SPIDHelper.getServiceProviderSuffix (null));
  }

  @Test
  public void testGetMainIDFromSeatID ()
  {
    assertEquals ("000001", SPIDHelper.getMainIDFromSeatID ("POP000001"));
    assertEquals ("123456", SPIDHelper.getMainIDFromSeatID ("PAP123456"));
    assertEquals ("000270", SPIDHelper.getMainIDFromSeatID ("PDE000270"));

    // Invalid Seat IDs yield null
    assertNull (SPIDHelper.getMainIDFromSeatID ("short"));
    assertNull (SPIDHelper.getMainIDFromSeatID ("TOOLONGID"));
    assertNull (SPIDHelper.getMainIDFromSeatID ("POP00001"));
    assertNull (SPIDHelper.getMainIDFromSeatID ("POP0000012"));
    assertNull (SPIDHelper.getMainIDFromSeatID ("Pop000001"));
    assertNull (SPIDHelper.getMainIDFromSeatID (""));
    assertNull (SPIDHelper.getMainIDFromSeatID (null));
  }
}
