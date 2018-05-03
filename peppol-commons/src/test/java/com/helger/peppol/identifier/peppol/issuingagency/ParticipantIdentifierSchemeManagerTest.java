/**
 * Copyright (C) 2015-2018 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.identifier.peppol.issuingagency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.state.ETriState;

/**
 * Test class for class {@link ParticipantIdentifierSchemeManager}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class ParticipantIdentifierSchemeManagerTest
{
  @Test
  public void testAll ()
  {
    assertNotNull (ParticipantIdentifierSchemeManager.getAllAgencies ());
    assertEquals (70, ParticipantIdentifierSchemeManager.getAllAgencies ().size ());

    // test valid
    assertNotNull (ParticipantIdentifierSchemeManager.getAgencyOfISO6523Code ("0088"));
    assertTrue (ParticipantIdentifierSchemeManager.containsAgencyWithISO6523Code ("0088"));
    assertEquals ("GLN", ParticipantIdentifierSchemeManager.getSchemeIDOfISO6523Code ("0088"));
    assertNotNull (ParticipantIdentifierSchemeManager.getAgencyOfSchemeID ("GLN"));
    assertTrue (ParticipantIdentifierSchemeManager.containsAgencyWithSchemeID ("GLN"));
    assertEquals ("0088", ParticipantIdentifierSchemeManager.getISO6523CodeOfSchemeID ("GLN"));

    // test invalid
    assertNull (ParticipantIdentifierSchemeManager.getAgencyOfISO6523Code ("1024"));
    assertFalse (ParticipantIdentifierSchemeManager.containsAgencyWithISO6523Code ("1024"));
    assertNull (ParticipantIdentifierSchemeManager.getSchemeIDOfISO6523Code ("1024"));
    assertNull (ParticipantIdentifierSchemeManager.getAgencyOfISO6523Code (null));
    assertFalse (ParticipantIdentifierSchemeManager.containsAgencyWithISO6523Code (null));
    assertNull (ParticipantIdentifierSchemeManager.getSchemeIDOfISO6523Code (null));
    assertNull (ParticipantIdentifierSchemeManager.getAgencyOfSchemeID ("XYZ"));
    assertFalse (ParticipantIdentifierSchemeManager.containsAgencyWithSchemeID ("XYZ"));
    assertNull (ParticipantIdentifierSchemeManager.getISO6523CodeOfSchemeID ("XYZ"));
    assertNull (ParticipantIdentifierSchemeManager.getAgencyOfSchemeID (null));
    assertFalse (ParticipantIdentifierSchemeManager.containsAgencyWithSchemeID (null));
    assertNull (ParticipantIdentifierSchemeManager.getISO6523CodeOfSchemeID (null));

    // Test deprecated
    assertEquals (ETriState.TRUE, ParticipantIdentifierSchemeManager.isAgencyWithISO6523CodeDeprecated ("9916"));
    assertEquals (ETriState.TRUE, ParticipantIdentifierSchemeManager.isAgencyWithSchemeIDDeprecated ("AT:CID"));
    assertEquals (ETriState.FALSE, ParticipantIdentifierSchemeManager.isAgencyWithISO6523CodeDeprecated ("9914"));
    assertEquals (ETriState.FALSE, ParticipantIdentifierSchemeManager.isAgencyWithSchemeIDDeprecated ("AT:VAT"));
    assertEquals (ETriState.UNDEFINED, ParticipantIdentifierSchemeManager.isAgencyWithISO6523CodeDeprecated ("abcd"));
    assertEquals (ETriState.UNDEFINED, ParticipantIdentifierSchemeManager.isAgencyWithSchemeIDDeprecated ("XYZ"));
    assertEquals (ETriState.UNDEFINED, ParticipantIdentifierSchemeManager.isAgencyWithISO6523CodeDeprecated (null));
    assertEquals (ETriState.UNDEFINED, ParticipantIdentifierSchemeManager.isAgencyWithSchemeIDDeprecated (null));
  }
}
