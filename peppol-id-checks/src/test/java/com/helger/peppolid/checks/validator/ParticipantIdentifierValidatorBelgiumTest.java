/*
 * Copyright (C) 2026 Philip Helger
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
package com.helger.peppolid.checks.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.peppolid.peppol.pidscheme.EPredefinedParticipantIdentifierScheme;

/**
 * Test class for class {@link ParticipantIdentifierValidatorBelgium}.
 *
 * @author Philip Helger
 */
public final class ParticipantIdentifierValidatorBelgiumTest
{
  private static final String ICD_BE_EN = EPredefinedParticipantIdentifierScheme.BE_EN.getISO6523Code ();
  private static final String ICD_BE_VAT = EPredefinedParticipantIdentifierScheme.BE_VAT.getISO6523Code ();

  @Test
  public void testValidEnterpriseNumber ()
  {
    // Existing Belgian enterprise numbers
    assertTrue (ParticipantIdentifierValidatorBelgium.isValidEnterpriseNumber ("0417497106"));
    assertTrue (ParticipantIdentifierValidatorBelgium.isValidEnterpriseNumber ("0203201340"));
    // The "1" range was opened when the "0" range was exhausted - these must not be rejected
    assertTrue (ParticipantIdentifierValidatorBelgium.isValidEnterpriseNumber ("1000000021"));
    assertTrue (ParticipantIdentifierValidatorBelgium.isValidEnterpriseNumber ("1234567894"));
  }

  @Test
  public void testInvalidEnterpriseNumber ()
  {
    assertFalse (ParticipantIdentifierValidatorBelgium.isValidEnterpriseNumber (null));
    assertFalse (ParticipantIdentifierValidatorBelgium.isValidEnterpriseNumber (""));
    // Too short
    assertFalse (ParticipantIdentifierValidatorBelgium.isValidEnterpriseNumber ("041749710"));
    // Too long
    assertFalse (ParticipantIdentifierValidatorBelgium.isValidEnterpriseNumber ("04174971060"));
    // Wrong check digits
    assertFalse (ParticipantIdentifierValidatorBelgium.isValidEnterpriseNumber ("0417497107"));
    // The first digit must be "0" or "1"
    assertFalse (ParticipantIdentifierValidatorBelgium.isValidEnterpriseNumber ("2417497106"));
    assertFalse (ParticipantIdentifierValidatorBelgium.isValidEnterpriseNumber ("9417497106"));
    // Not numeric
    assertFalse (ParticipantIdentifierValidatorBelgium.isValidEnterpriseNumber ("041749710a"));
    // Surrounding whitespace is not allowed
    assertFalse (ParticipantIdentifierValidatorBelgium.isValidEnterpriseNumber (" 041749710"));
    assertFalse (ParticipantIdentifierValidatorBelgium.isValidEnterpriseNumber ("0417497106 "));
    // The VAT prefix does not belong into scheme 0208
    assertFalse (ParticipantIdentifierValidatorBelgium.isValidEnterpriseNumber ("BE0417497106"));
  }

  @Test
  public void testValidVATNumber ()
  {
    // With the VAT country code prefix
    assertTrue (ParticipantIdentifierValidatorBelgium.isValidVATNumber ("BE0417497106"));
    // Peppol identifier values are commonly stored in lower case
    assertTrue (ParticipantIdentifierValidatorBelgium.isValidVATNumber ("be0417497106"));
    assertTrue (ParticipantIdentifierValidatorBelgium.isValidVATNumber ("Be0417497106"));
    assertTrue (ParticipantIdentifierValidatorBelgium.isValidVATNumber ("BE1000000021"));
  }

  @Test
  public void testInvalidVATNumber ()
  {
    assertFalse (ParticipantIdentifierValidatorBelgium.isValidVATNumber (null));
    assertFalse (ParticipantIdentifierValidatorBelgium.isValidVATNumber (""));
    // Prefix only
    assertFalse (ParticipantIdentifierValidatorBelgium.isValidVATNumber ("BE"));
    // The prefix is mandatory in scheme 9925
    assertFalse (ParticipantIdentifierValidatorBelgium.isValidVATNumber ("0417497106"));
    assertFalse (ParticipantIdentifierValidatorBelgium.isValidVATNumber ("1000000021"));
    // Wrong check digits
    assertFalse (ParticipantIdentifierValidatorBelgium.isValidVATNumber ("BE0417497107"));
    // A different country code is not stripped, so the remainder is too long
    assertFalse (ParticipantIdentifierValidatorBelgium.isValidVATNumber ("FR0417497106"));
    // The prefix must not be repeated
    assertFalse (ParticipantIdentifierValidatorBelgium.isValidVATNumber ("BEBE0417497106"));
  }

  @Test
  public void testIsSupportedIssuingAgency ()
  {
    final ParticipantIdentifierValidatorBelgium aValidator = new ParticipantIdentifierValidatorBelgium ();
    assertTrue (aValidator.isSupportedIssuingAgency (ICD_BE_EN));
    assertTrue (aValidator.isSupportedIssuingAgency (ICD_BE_VAT));
    assertFalse (aValidator.isSupportedIssuingAgency ("0088"));
    assertFalse (aValidator.isSupportedIssuingAgency ("9930"));
  }

  @Test
  public void testIsValueValidPerIssuingAgency ()
  {
    final ParticipantIdentifierValidatorBelgium aValidator = new ParticipantIdentifierValidatorBelgium ();

    // Scheme 0208 must not carry the VAT prefix, scheme 9925 must carry it
    assertTrue (aValidator.isValueValid (ICD_BE_EN, "0417497106"));
    assertFalse (aValidator.isValueValid (ICD_BE_EN, "BE0417497106"));

    assertTrue (aValidator.isValueValid (ICD_BE_VAT, "BE0417497106"));
    assertFalse (aValidator.isValueValid (ICD_BE_VAT, "0417497106"));

    // Wrong check digits are invalid in both schemes
    assertFalse (aValidator.isValueValid (ICD_BE_EN, "0417497107"));
    assertFalse (aValidator.isValueValid (ICD_BE_VAT, "BE0417497107"));
  }
}
