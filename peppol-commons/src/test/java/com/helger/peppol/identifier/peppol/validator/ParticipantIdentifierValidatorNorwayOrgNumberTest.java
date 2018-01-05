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
package com.helger.peppol.identifier.peppol.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for class {@link ParticipantIdentifierValidatorNorwayOrgNumber}.
 *
 * @author Philip Helger
 */
public final class ParticipantIdentifierValidatorNorwayOrgNumberTest
{
  @Test
  public void test02 ()
  {
    assertTrue (ParticipantIdentifierValidatorNorwayOrgNumber.isValidOrganisationNumber ("123456785"));
    assertTrue (ParticipantIdentifierValidatorNorwayOrgNumber.isValidOrganisationNumber ("976098897"));
    assertTrue (ParticipantIdentifierValidatorNorwayOrgNumber.isValidOrganisationNumber ("991618112"));

    assertFalse (ParticipantIdentifierValidatorNorwayOrgNumber.isValidOrganisationNumber (null));
    assertFalse (ParticipantIdentifierValidatorNorwayOrgNumber.isValidOrganisationNumber (""));
    assertFalse (ParticipantIdentifierValidatorNorwayOrgNumber.isValidOrganisationNumber ("976098897 "));
    assertFalse (ParticipantIdentifierValidatorNorwayOrgNumber.isValidOrganisationNumber ("12345678-"));
    assertFalse (ParticipantIdentifierValidatorNorwayOrgNumber.isValidOrganisationNumber ("-23456789"));
    assertFalse (ParticipantIdentifierValidatorNorwayOrgNumber.isValidOrganisationNumber ("123456784"));
    assertFalse (ParticipantIdentifierValidatorNorwayOrgNumber.isValidOrganisationNumber ("323456780"));
  }
}
