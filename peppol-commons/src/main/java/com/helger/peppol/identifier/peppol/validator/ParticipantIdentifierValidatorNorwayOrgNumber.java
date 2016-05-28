/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.identifier.peppol.validator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.IsSPIImplementation;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.peppol.issuingagency.EPredefinedIdentifierIssuingAgency;

/**
 * Implementation of {@link IParticipantIdentifierValidatorSPI} for the
 * Norwegian Organisation Number.
 * 
 * @author philip
 */
@IsSPIImplementation
public final class ParticipantIdentifierValidatorNorwayOrgNumber implements IParticipantIdentifierValidatorSPI
{
  private static final int [] WEIGHTS = new int [] { 3, 2, 7, 6, 5, 4, 3, 2 };

  public boolean isSupportedIssuingAgency (@Nonnull @Nonempty final String sIssuingAgencyID)
  {
    return EPredefinedIdentifierIssuingAgency.NO_ORGNR.getISO6523Code ().equals (sIssuingAgencyID) ||
           EPredefinedIdentifierIssuingAgency.NO_VAT.getISO6523Code ().equals (sIssuingAgencyID);
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
