/**
 * Copyright (C) 2015-2019 Philip Helger (www.helger.com)
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
import javax.annotation.Nullable;

import com.helger.commons.annotation.IsSPIImplementation;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.string.StringHelper;
import com.helger.peppolid.peppol.pidscheme.EPredefinedParticipantIdentifierScheme;

/**
 * Implementation of {@link IParticipantIdentifierValidatorSPI} for the
 * Norwegian Organisation Number.
 *
 * @author Philip Helger
 */
@IsSPIImplementation
public final class ParticipantIdentifierValidatorNorwayOrgNumber implements IParticipantIdentifierValidatorSPI
{
  private static final int [] WEIGHTS = new int [] { 3, 2, 7, 6, 5, 4, 3, 2 };

  @SuppressWarnings ("deprecation")
  public boolean isSupportedIssuingAgency (@Nonnull @Nonempty final String sIssuingAgencyID)
  {
    return EPredefinedParticipantIdentifierScheme.NO_ORGNR.getISO6523Code ().equals (sIssuingAgencyID) ||
           EPredefinedParticipantIdentifierScheme.NO_ORG.getISO6523Code ().equals (sIssuingAgencyID) ||
           EPredefinedParticipantIdentifierScheme.NO_VAT.getISO6523Code ().equals (sIssuingAgencyID);
  }

  public boolean isValueValid (@Nonnull @Nonempty final String sValue)
  {
    return isValidOrganisationNumber (sValue);
  }

  /**
   * Static check method.
   *
   * @param sValue
   *        The value to be checked.
   * @return <code>true</code> if the passed value is a valid NO organisation
   *         number.
   */
  public static boolean isValidOrganisationNumber (@Nullable final String sValue)
  {
    if (StringHelper.getLength (sValue) != 9)
      return false;

    final char [] aData = sValue.toCharArray ();
    if (!Character.isDigit (aData[8]))
      return false;

    final int nActualCheckDigit = aData[8] - 48;
    int nSum = 0;

    for (int i = 0; i < 8; i++)
    {
      final char cNext = aData[i];
      if (!Character.isDigit (cNext))
        return false;

      final int nDigit = cNext - 48;
      nSum += nDigit * WEIGHTS[i];
    }

    final int nModulus = nSum % 11;

    /** don't subtract from length if the modulus is 0 */
    if (nModulus == 0 && nActualCheckDigit == 0)
      return true;

    final int nCalculatedCheckDigit = 11 - nModulus;
    return nActualCheckDigit == nCalculatedCheckDigit;
  }
}
