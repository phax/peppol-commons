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

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.annotation.style.IsSPIImplementation;
import com.helger.base.string.StringHelper;
import com.helger.masterdata.vat.VATINSyntaxChecker;
import com.helger.peppolid.peppol.pidscheme.EPredefinedParticipantIdentifierScheme;

/**
 * Implementation of {@link IParticipantIdentifierValidatorSPI} for the Belgian enterprise number
 * (scheme <code>0208</code>) and the Belgian VAT number (scheme <code>9925</code>).
 * <p>
 * Both schemes carry the same 10 digit CBE number (Banque-Carrefour des Entreprises / Kruispuntbank
 * van Ondernemingen), but they differ in the way it is written down: scheme <code>0208</code>
 * contains the plain number and must not carry the VAT country code <code>BE</code>, whereas scheme
 * <code>9925</code> contains the VAT number and must carry it.
 * </p>
 * <p>
 * This is a pure syntax check - it verifies the structure and the check digits only. Whether an
 * enterprise with that number is actually registered can only be answered by the CBE register
 * itself, which requires a remote lookup and is therefore out of scope for this SPI.
 * </p>
 *
 * @author Philip Helger
 * @since 12.9.0
 */
@IsSPIImplementation
public final class ParticipantIdentifierValidatorBelgium implements IParticipantIdentifierValidatorSPI
{
  /** The mandatory leading VAT country code of a Belgian VAT number */
  public static final String VAT_PREFIX = "BE";

  /** The number of digits of a Belgian enterprise number */
  public static final int ENTERPRISE_NUMBER_LENGTH = 10;

  /**
   * Static check method for a Belgian enterprise number, as used by scheme <code>0208</code>.<br>
   * It consists of exactly {@value #ENTERPRISE_NUMBER_LENGTH} digits, where the first digit is
   * either <code>0</code> or <code>1</code> - the <code>1</code> range was opened when the
   * <code>0</code> range was exhausted, so a check that only accepts a leading <code>0</code> would
   * reject valid numbers. The last 2 digits are the check digits and must be
   * <code>97 - (first 8 digits modulo 97)</code>.
   *
   * @param sValue
   *        The value to be checked. May be <code>null</code>.
   * @return <code>true</code> if the passed value is a valid Belgian enterprise number.
   */
  public static boolean isValidEnterpriseNumber (@Nullable final String sValue)
  {
    // Handles the length, the leading "0" or "1", the digits and the modulo 97 check digits
    return sValue != null && VATINSyntaxChecker.isValidVATIN_BE (sValue);
  }

  /**
   * Static check method for a Belgian VAT number, as used by scheme <code>9925</code>.<br>
   * The Belgian VAT number is the enterprise number prefixed with the VAT country code
   * {@value #VAT_PREFIX}. That prefix is mandatory, but it is evaluated case insensitively, because
   * Peppol identifier values are commonly stored in lower case. The remainder is checked with
   * {@link #isValidEnterpriseNumber(String)}.
   *
   * @param sValue
   *        The value to be checked. May be <code>null</code>.
   * @return <code>true</code> if the passed value is a valid Belgian VAT number.
   */
  public static boolean isValidVATNumber (@Nullable final String sValue)
  {
    if (sValue == null || !StringHelper.startsWithIgnoreCase (sValue, VAT_PREFIX))
      return false;

    return isValidEnterpriseNumber (sValue.substring (VAT_PREFIX.length ()));
  }

  public boolean isSupportedIssuingAgency (@NonNull @Nonempty final String sIssuingAgencyID)
  {
    return EPredefinedParticipantIdentifierScheme.BE_EN.getISO6523Code ().equals (sIssuingAgencyID) ||
           EPredefinedParticipantIdentifierScheme.BE_VAT.getISO6523Code ().equals (sIssuingAgencyID);
  }

  public boolean isValueValid (@NonNull @Nonempty final String sIssuingAgencyID, @NonNull @Nonempty final String sValue)
  {
    // Only scheme 9925 carries the leading VAT country code, and there it is mandatory
    if (EPredefinedParticipantIdentifierScheme.BE_VAT.getISO6523Code ().equals (sIssuingAgencyID))
      return isValidVATNumber (sValue);

    return isValidEnterpriseNumber (sValue);
  }
}
