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

import com.helger.annotation.Nonempty;
import com.helger.annotation.style.IsSPIInterface;

/**
 * An SPI interface to validate arbitrary identifier values (independent of the
 * identifier type). This interface can e.g. be used to validate VATIN numbers
 * that are used as PEPPOL participant IDs.
 *
 * @author Philip Helger
 */
@IsSPIInterface
public interface IParticipantIdentifierValidatorSPI
{
  /**
   * Check if the passed issuing agency UD (like "9908") is supported by this
   * validator implementation.
   *
   * @param sIssuingAgencyID
   *        The identifier scheme to check for support. Is neither null nor
   *        empty.
   * @return <code>true</code> if this validator can validate values of the
   *         passed scheme, <code>false</code> otherwise.
   */
  boolean isSupportedIssuingAgency (@NonNull @Nonempty String sIssuingAgencyID);

  /**
   * Check if the identifier value is valid. This method is only called if the
   * check for the scheme ({@link #isSupportedIssuingAgency(String)} returned
   * <code>true</code>.
   *
   * @param sIssuingAgencyID
   *        The issuing agency ID (like "9908") the value belongs to. Is neither
   *        null nor empty. This is the same value for which
   *        {@link #isSupportedIssuingAgency(String)} returned <code>true</code>
   *        and it allows a single implementation to apply different rules per
   *        supported issuing agency.
   * @param sValue
   *        The identifier value to be checked. Is neither null nor empty.
   * @return <code>true</code> if the identifier value is valid,
   *         <code>false</code> if not.
   * @since 12.9.0 the issuing agency ID is passed in as well
   */
  boolean isValueValid (@NonNull @Nonempty String sIssuingAgencyID, @NonNull @Nonempty String sValue);
}
