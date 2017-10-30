/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.identifier.peppol.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.peppol.identifier.peppol.issuingagency.EPredefinedIdentifierIssuingAgency;

/**
 * Test class for class {@link IdentifierValidator}.
 *
 * @author philip
 */
public final class IdentifierValidatorTest
{
  @Test
  public void testNorwayOrgNumber ()
  {
    assertFalse (IdentifierValidator.isValidParticipantIdentifier (EPredefinedIdentifierIssuingAgency.NO_ORGNR.createParticipantIdentifier ("")));
    assertFalse (IdentifierValidator.isValidParticipantIdentifier (EPredefinedIdentifierIssuingAgency.NO_ORGNR.createParticipantIdentifier ("123456789")));
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (EPredefinedIdentifierIssuingAgency.NO_ORGNR.createParticipantIdentifier ("123456785")));
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (EPredefinedIdentifierIssuingAgency.NO_ORGNR.createParticipantIdentifier ("968218743")));
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (EPredefinedIdentifierIssuingAgency.NO_ORGNR.createParticipantIdentifier ("961329310")));

    assertFalse (IdentifierValidator.isValidParticipantIdentifier (EPredefinedIdentifierIssuingAgency.NO_VAT.createParticipantIdentifier ("")));
    assertFalse (IdentifierValidator.isValidParticipantIdentifier (EPredefinedIdentifierIssuingAgency.NO_VAT.createParticipantIdentifier ("123456789")));
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (EPredefinedIdentifierIssuingAgency.NO_VAT.createParticipantIdentifier ("123456785")));
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (EPredefinedIdentifierIssuingAgency.NO_VAT.createParticipantIdentifier ("968218743")));
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (EPredefinedIdentifierIssuingAgency.NO_VAT.createParticipantIdentifier ("961329310")));
  }

  @Test
  public void testWithoutRules ()
  {
    // No special rules available -> all valid!
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (EPredefinedIdentifierIssuingAgency.AD_VAT.createParticipantIdentifier ("123456789")));
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (EPredefinedIdentifierIssuingAgency.AD_VAT.createParticipantIdentifier ("123456785")));
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (EPredefinedIdentifierIssuingAgency.AD_VAT.createParticipantIdentifier ("968218743")));
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (EPredefinedIdentifierIssuingAgency.AD_VAT.createParticipantIdentifier ("961329310")));
  }
}
