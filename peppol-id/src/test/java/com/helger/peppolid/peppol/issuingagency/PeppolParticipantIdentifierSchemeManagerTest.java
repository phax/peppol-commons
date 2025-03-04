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
package com.helger.peppolid.peppol.issuingagency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.state.ETriState;
import com.helger.peppolid.peppol.pidscheme.PeppolParticipantIdentifierSchemeManager;

/**
 * Test class for class {@link PeppolParticipantIdentifierSchemeManager}.
 *
 * @author Philip Helger
 */
public final class PeppolParticipantIdentifierSchemeManagerTest
{
  @Test
  public void testAll ()
  {
    assertNotNull (PeppolParticipantIdentifierSchemeManager.getAllSchemes ());
    assertEquals (99, PeppolParticipantIdentifierSchemeManager.getAllSchemes ().size ());

    // test valid
    assertNotNull (PeppolParticipantIdentifierSchemeManager.getSchemeOfISO6523Code ("0088"));
    assertTrue (PeppolParticipantIdentifierSchemeManager.containsSchemeWithISO6523Code ("0088"));
    assertEquals ("GLN", PeppolParticipantIdentifierSchemeManager.getSchemeIDOfISO6523Code ("0088"));
    assertNotNull (PeppolParticipantIdentifierSchemeManager.getSchemeOfSchemeID ("GLN"));
    assertTrue (PeppolParticipantIdentifierSchemeManager.containsSchemeWithSchemeID ("GLN"));
    assertEquals ("0088", PeppolParticipantIdentifierSchemeManager.getISO6523CodeOfSchemeID ("GLN"));

    // test invalid
    assertNull (PeppolParticipantIdentifierSchemeManager.getSchemeOfISO6523Code ("1024"));
    assertFalse (PeppolParticipantIdentifierSchemeManager.containsSchemeWithISO6523Code ("1024"));
    assertNull (PeppolParticipantIdentifierSchemeManager.getSchemeIDOfISO6523Code ("1024"));
    assertNull (PeppolParticipantIdentifierSchemeManager.getSchemeOfISO6523Code (null));
    assertFalse (PeppolParticipantIdentifierSchemeManager.containsSchemeWithISO6523Code (null));
    assertNull (PeppolParticipantIdentifierSchemeManager.getSchemeIDOfISO6523Code (null));
    assertNull (PeppolParticipantIdentifierSchemeManager.getSchemeOfSchemeID ("XYZ"));
    assertFalse (PeppolParticipantIdentifierSchemeManager.containsSchemeWithSchemeID ("XYZ"));
    assertNull (PeppolParticipantIdentifierSchemeManager.getISO6523CodeOfSchemeID ("XYZ"));
    assertNull (PeppolParticipantIdentifierSchemeManager.getSchemeOfSchemeID (null));
    assertFalse (PeppolParticipantIdentifierSchemeManager.containsSchemeWithSchemeID (null));
    assertNull (PeppolParticipantIdentifierSchemeManager.getISO6523CodeOfSchemeID (null));

    // Test deprecated
    assertEquals (ETriState.TRUE, PeppolParticipantIdentifierSchemeManager.isSchemeWithISO6523CodeDeprecated ("9916"));
    assertEquals (ETriState.TRUE, PeppolParticipantIdentifierSchemeManager.isSchemeWithSchemeIDDeprecated ("AT:CID"));
    assertEquals (ETriState.FALSE, PeppolParticipantIdentifierSchemeManager.isSchemeWithISO6523CodeDeprecated ("9914"));
    assertEquals (ETriState.FALSE, PeppolParticipantIdentifierSchemeManager.isSchemeWithSchemeIDDeprecated ("AT:VAT"));
    assertEquals (ETriState.UNDEFINED,
                  PeppolParticipantIdentifierSchemeManager.isSchemeWithISO6523CodeDeprecated ("abcd"));
    assertEquals (ETriState.UNDEFINED, PeppolParticipantIdentifierSchemeManager.isSchemeWithSchemeIDDeprecated ("XYZ"));
    assertEquals (ETriState.UNDEFINED,
                  PeppolParticipantIdentifierSchemeManager.isSchemeWithISO6523CodeDeprecated (null));
    assertEquals (ETriState.UNDEFINED, PeppolParticipantIdentifierSchemeManager.isSchemeWithSchemeIDDeprecated (null));
  }
}
