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
package com.helger.peppolid.peppol.validator;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.IsSPIInterface;
import com.helger.commons.annotation.Nonempty;

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
  boolean isSupportedIssuingAgency (@Nonnull @Nonempty String sIssuingAgencyID);

  /**
   * Check if the identifier value is valid. This method is only called if the
   * check for the scheme ({@link #isSupportedIssuingAgency(String)} returned
   * <code>true</code>.
   *
   * @param sValue
   *        The identifier value to be checked. Is neither null nor empty.
   * @return <code>true</code> if the identifier value is valid,
   *         <code>false</code> if not.
   */
  boolean isValueValid (@Nonnull @Nonempty String sValue);
}
