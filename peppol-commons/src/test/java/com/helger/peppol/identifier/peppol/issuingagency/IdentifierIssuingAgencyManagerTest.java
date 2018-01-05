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
 * Test class for class {@link IdentifierIssuingAgencyManager}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class IdentifierIssuingAgencyManagerTest
{
  @Test
  public void testAll ()
  {
    assertNotNull (IdentifierIssuingAgencyManager.getAllAgencies ());
    assertEquals (67, IdentifierIssuingAgencyManager.getAllAgencies ().size ());

    // test valid
    assertNotNull (IdentifierIssuingAgencyManager.getAgencyOfISO6523Code ("0088"));
    assertTrue (IdentifierIssuingAgencyManager.containsAgencyWithISO6523Code ("0088"));
    assertEquals ("GLN", IdentifierIssuingAgencyManager.getSchemeIDOfISO6523Code ("0088"));
    assertNotNull (IdentifierIssuingAgencyManager.getAgencyOfSchemeID ("GLN"));
    assertTrue (IdentifierIssuingAgencyManager.containsAgencyWithSchemeID ("GLN"));
    assertEquals ("0088", IdentifierIssuingAgencyManager.getISO6523CodeOfSchemeID ("GLN"));

    // test invalid
    assertNull (IdentifierIssuingAgencyManager.getAgencyOfISO6523Code ("1024"));
    assertFalse (IdentifierIssuingAgencyManager.containsAgencyWithISO6523Code ("1024"));
    assertNull (IdentifierIssuingAgencyManager.getSchemeIDOfISO6523Code ("1024"));
    assertNull (IdentifierIssuingAgencyManager.getAgencyOfISO6523Code (null));
    assertFalse (IdentifierIssuingAgencyManager.containsAgencyWithISO6523Code (null));
    assertNull (IdentifierIssuingAgencyManager.getSchemeIDOfISO6523Code (null));
    assertNull (IdentifierIssuingAgencyManager.getAgencyOfSchemeID ("XYZ"));
    assertFalse (IdentifierIssuingAgencyManager.containsAgencyWithSchemeID ("XYZ"));
    assertNull (IdentifierIssuingAgencyManager.getISO6523CodeOfSchemeID ("XYZ"));
    assertNull (IdentifierIssuingAgencyManager.getAgencyOfSchemeID (null));
    assertFalse (IdentifierIssuingAgencyManager.containsAgencyWithSchemeID (null));
    assertNull (IdentifierIssuingAgencyManager.getISO6523CodeOfSchemeID (null));

    // Test deprecated
    assertEquals (ETriState.TRUE, IdentifierIssuingAgencyManager.isAgencyWithISO6523CodeDeprecated ("9916"));
    assertEquals (ETriState.TRUE, IdentifierIssuingAgencyManager.isAgencyWithSchemeIDDeprecated ("AT:CID"));
    assertEquals (ETriState.FALSE, IdentifierIssuingAgencyManager.isAgencyWithISO6523CodeDeprecated ("9914"));
    assertEquals (ETriState.FALSE, IdentifierIssuingAgencyManager.isAgencyWithSchemeIDDeprecated ("AT:VAT"));
    assertEquals (ETriState.UNDEFINED, IdentifierIssuingAgencyManager.isAgencyWithISO6523CodeDeprecated ("abcd"));
    assertEquals (ETriState.UNDEFINED, IdentifierIssuingAgencyManager.isAgencyWithSchemeIDDeprecated ("XYZ"));
    assertEquals (ETriState.UNDEFINED, IdentifierIssuingAgencyManager.isAgencyWithISO6523CodeDeprecated (null));
    assertEquals (ETriState.UNDEFINED, IdentifierIssuingAgencyManager.isAgencyWithSchemeIDDeprecated (null));
  }
}
