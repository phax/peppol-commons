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
package com.helger.peppolid.peppol.validator;

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
  public void testNorwayOrgNumber ()
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
