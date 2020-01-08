/**
 * Copyright (C) 2015-2020 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
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
