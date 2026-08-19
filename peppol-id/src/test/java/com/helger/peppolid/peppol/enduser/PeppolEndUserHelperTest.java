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
package com.helger.peppolid.peppol.enduser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import com.helger.base.state.EChange;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.factory.PeppolIdentifierFactory;

/**
 * Test class for class {@link PeppolEndUserHelper}.
 *
 * @author Philip Helger
 */
public final class PeppolEndUserHelperTest
{
  private static final IIdentifierFactory IF = PeppolIdentifierFactory.INSTANCE;

  @After
  public void afterTest ()
  {
    // Ensure the global state is consistent for all other tests
    PeppolEndUserHelper.setToDefaultMappings ();
  }

  @Test
  public void testDefaultMappingBelgium ()
  {
    // 9925 with country code prefix
    assertEquals ("iso6523-actorid-upis::0208:0123456789",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("9925:BE0123456789")));
    // Case insensitive prefix
    assertEquals ("iso6523-actorid-upis::0208:0123456789",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("9925:be0123456789")));
    // 9925 without country code prefix
    assertEquals ("iso6523-actorid-upis::0208:0123456789",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("9925:0123456789")));
    // 0208 stays as it is
    assertEquals ("iso6523-actorid-upis::0208:0123456789",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("0208:0123456789")));
  }

  @Test
  public void testDefaultMappingGermany ()
  {
    // The value is not modified, but unified (lower cased)
    assertEquals ("iso6523-actorid-upis::0246:de123456789",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("9930:DE123456789")));
    // 0246 stays as it is
    assertEquals ("iso6523-actorid-upis::0246:de123456789",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("0246:DE123456789")));
  }

  @Test
  public void testDefaultMappingFinland ()
  {
    // 0037 with the OVT prefix
    assertEquals ("iso6523-actorid-upis::0216:00371234567800001",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("0037:00371234567800001")));
    // 0037 without the OVT prefix - the same End User
    assertEquals ("iso6523-actorid-upis::0216:00371234567800001",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("0037:1234567800001")));
    // 0037 with a hyphen in the Business ID and without a suffix
    assertEquals ("iso6523-actorid-upis::0216:003712345678",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("0037:1234567-8")));
    // A Business ID starting with "0037" but without the OVT prefix
    assertEquals ("iso6523-actorid-upis::0216:003700371234",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("0037:00371234")));
    // The 0216 example value of the code list
    assertEquals ("iso6523-actorid-upis::0216:003704944842tst01",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("0037:04944842TST01")));
    // 0216 stays as it is
    assertEquals ("iso6523-actorid-upis::0216:003704944842tst01",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("0216:003704944842TST01")));

    // Values that are no valid OVT codes are not mapped
    assertEquals ("iso6523-actorid-upis::0037:12345",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("0037:12345")));
    assertEquals ("iso6523-actorid-upis::0037:12345678901234567890",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("0037:12345678901234567890")));
  }

  @Test
  public void testNoMapping ()
  {
    // No mapping for 0088 - but the value is unified
    assertEquals ("iso6523-actorid-upis::0088:abc12345",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("0088:ABC12345")));
    // Non-default scheme - identifier values are case sensitive there
    assertEquals ("scheme-actorid-test::ABC12345",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifier ("scheme-actorid-test",
                                                                                    "ABC12345")));
  }

  @Test
  public void testInvalidIdentifiers ()
  {
    assertNull (PeppolEndUserHelper.getEffectiveEndUserID (null));
    assertNull (PeppolEndUserHelper.getEffectiveEndUserID (null, "9925:BE0123456789"));
    assertNull (PeppolEndUserHelper.getEffectiveEndUserID ("iso6523-actorid-upis", null));

    // Identifiers that cannot be created by the identifier factory are used as-is
    assertEquals ("iso6523-actorid-upis::abc", PeppolEndUserHelper.getEffectiveEndUserID ("iso6523-actorid-upis", "abc"));
    assertEquals ("bla::9925:BE0123456789", PeppolEndUserHelper.getEffectiveEndUserID ("bla", "9925:BE0123456789"));
  }

  @Test
  public void testRegisterMapping ()
  {
    assertEquals (3, PeppolEndUserHelper.getAllMappings ().size ());

    // A custom mapping - for testing purposes only
    PeppolEndUserHelper.registerMapping (PeppolEndUserIDMapping.createValueWithoutPrefix ("0088", "0060", "X"));
    assertEquals (4, PeppolEndUserHelper.getAllMappings ().size ());
    assertEquals ("iso6523-actorid-upis::0060:1234567",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("0088:X1234567")));
    // The mapped value would be empty, so the mapping is not applicable
    assertEquals ("iso6523-actorid-upis::0088:x",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("0088:X")));

    PeppolEndUserHelper.setToDefaultMappings ();
    assertEquals (3, PeppolEndUserHelper.getAllMappings ().size ());
    assertEquals ("iso6523-actorid-upis::0088:x1234567",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("0088:X1234567")));
  }

  @Test
  public void testUnregisterMapping ()
  {
    assertTrue (PeppolEndUserHelper.unregisterMapping (PeppolEndUserHelper.MAPPING_BE_VAT_TO_BE_EN).isChanged ());
    assertEquals (EChange.UNCHANGED,
                  PeppolEndUserHelper.unregisterMapping (PeppolEndUserHelper.MAPPING_BE_VAT_TO_BE_EN));
    assertEquals (EChange.UNCHANGED, PeppolEndUserHelper.unregisterMapping (null));

    // No longer mapped
    assertEquals ("iso6523-actorid-upis::9925:be0123456789",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("9925:BE0123456789")));
    // Germany is still mapped
    assertEquals ("iso6523-actorid-upis::0246:de123456789",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("9930:DE123456789")));
  }

  @Test
  public void testUnregisterAllMappingsOfSourceISO6523Code ()
  {
    assertEquals (EChange.UNCHANGED, PeppolEndUserHelper.unregisterAllMappingsOfSourceISO6523Code (null));
    assertEquals (EChange.UNCHANGED, PeppolEndUserHelper.unregisterAllMappingsOfSourceISO6523Code ("0088"));
    assertTrue (PeppolEndUserHelper.unregisterAllMappingsOfSourceISO6523Code ("9930").isChanged ());

    assertEquals ("iso6523-actorid-upis::9930:de123456789",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("9930:DE123456789")));
  }

  @Test
  public void testRemoveAllMappings ()
  {
    assertTrue (PeppolEndUserHelper.removeAllMappings ().isChanged ());
    assertEquals (EChange.UNCHANGED, PeppolEndUserHelper.removeAllMappings ());
    assertTrue (PeppolEndUserHelper.getAllMappings ().isEmpty ());

    // Only unified, but not mapped
    assertEquals ("iso6523-actorid-upis::9925:be0123456789",
                  PeppolEndUserHelper.getEffectiveEndUserID (IF.createParticipantIdentifierWithDefaultScheme ("9925:BE0123456789")));
  }

  @Test
  public void testIdentifierFactory ()
  {
    assertSame (PeppolIdentifierFactory.INSTANCE, PeppolEndUserHelper.getIdentifierFactory ());
  }
}
