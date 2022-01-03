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
package com.helger.peppolid.peppol.issuingagency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.state.ETriState;
import com.helger.peppolid.peppol.pidscheme.ParticipantIdentifierSchemeManager;

/**
 * Test class for class {@link ParticipantIdentifierSchemeManager}.
 *
 * @author Philip Helger
 */
public final class ParticipantIdentifierSchemeManagerTest
{
  @Test
  public void testAll ()
  {
    assertNotNull (ParticipantIdentifierSchemeManager.getAllSchemes ());
    assertEquals (88, ParticipantIdentifierSchemeManager.getAllSchemes ().size ());

    // test valid
    assertNotNull (ParticipantIdentifierSchemeManager.getSchemeOfISO6523Code ("0088"));
    assertTrue (ParticipantIdentifierSchemeManager.containsSchemeWithISO6523Code ("0088"));
    assertEquals ("GLN", ParticipantIdentifierSchemeManager.getSchemeIDOfISO6523Code ("0088"));
    assertNotNull (ParticipantIdentifierSchemeManager.getSchemeOfSchemeID ("GLN"));
    assertTrue (ParticipantIdentifierSchemeManager.containsSchemeWithSchemeID ("GLN"));
    assertEquals ("0088", ParticipantIdentifierSchemeManager.getISO6523CodeOfSchemeID ("GLN"));

    // test invalid
    assertNull (ParticipantIdentifierSchemeManager.getSchemeOfISO6523Code ("1024"));
    assertFalse (ParticipantIdentifierSchemeManager.containsSchemeWithISO6523Code ("1024"));
    assertNull (ParticipantIdentifierSchemeManager.getSchemeIDOfISO6523Code ("1024"));
    assertNull (ParticipantIdentifierSchemeManager.getSchemeOfISO6523Code (null));
    assertFalse (ParticipantIdentifierSchemeManager.containsSchemeWithISO6523Code (null));
    assertNull (ParticipantIdentifierSchemeManager.getSchemeIDOfISO6523Code (null));
    assertNull (ParticipantIdentifierSchemeManager.getSchemeOfSchemeID ("XYZ"));
    assertFalse (ParticipantIdentifierSchemeManager.containsSchemeWithSchemeID ("XYZ"));
    assertNull (ParticipantIdentifierSchemeManager.getISO6523CodeOfSchemeID ("XYZ"));
    assertNull (ParticipantIdentifierSchemeManager.getSchemeOfSchemeID (null));
    assertFalse (ParticipantIdentifierSchemeManager.containsSchemeWithSchemeID (null));
    assertNull (ParticipantIdentifierSchemeManager.getISO6523CodeOfSchemeID (null));

    // Test deprecated
    assertEquals (ETriState.TRUE, ParticipantIdentifierSchemeManager.isSchemeWithISO6523CodeDeprecated ("9916"));
    assertEquals (ETriState.TRUE, ParticipantIdentifierSchemeManager.isSchemeWithSchemeIDDeprecated ("AT:CID"));
    assertEquals (ETriState.FALSE, ParticipantIdentifierSchemeManager.isSchemeWithISO6523CodeDeprecated ("9914"));
    assertEquals (ETriState.FALSE, ParticipantIdentifierSchemeManager.isSchemeWithSchemeIDDeprecated ("AT:VAT"));
    assertEquals (ETriState.UNDEFINED, ParticipantIdentifierSchemeManager.isSchemeWithISO6523CodeDeprecated ("abcd"));
    assertEquals (ETriState.UNDEFINED, ParticipantIdentifierSchemeManager.isSchemeWithSchemeIDDeprecated ("XYZ"));
    assertEquals (ETriState.UNDEFINED, ParticipantIdentifierSchemeManager.isSchemeWithISO6523CodeDeprecated (null));
    assertEquals (ETriState.UNDEFINED, ParticipantIdentifierSchemeManager.isSchemeWithSchemeIDDeprecated (null));
  }
}
