/**
 * Copyright (C) 2015 Philip Helger (www.helger.com)
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
package com.helger.peppol.identifier.issuingagency;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotations.Nonempty;
import com.helger.commons.annotations.PresentForCodeCoverage;
import com.helger.commons.annotations.ReturnsMutableCopy;
import com.helger.commons.collections.CollectionHelper;
import com.helger.commons.state.ETriState;
import com.helger.commons.string.StringHelper;

/**
 * This class manages the PEPPOL identifier issuing agencies using the
 * <b>iso6523-actorid-upis</b> scheme.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class IdentifierIssuingAgencyManager
{
  private static final List <IIdentifierIssuingAgency> s_aCodes = new ArrayList <IIdentifierIssuingAgency> ();

  static
  {
    // Add all predefined identifier issuing agencies
    for (final EPredefinedIdentifierIssuingAgency eIIA : EPredefinedIdentifierIssuingAgency.values ())
      s_aCodes.add (eIIA);
  }

  @PresentForCodeCoverage
  @SuppressWarnings ("unused")
  private static final IdentifierIssuingAgencyManager s_aInstance = new IdentifierIssuingAgencyManager ();

  private IdentifierIssuingAgencyManager ()
  {}

  /**
   * @return A non-modifiable list of all PEPPOL identifier issuing agencies.
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public static List <? extends IIdentifierIssuingAgency> getAllAgencies ()
  {
    return CollectionHelper.newList (s_aCodes);
  }

  /**
   * Find the agency with the respective ISO6523 value.
   *
   * @param sISO6523Code
   *        The value to search. May be <code>null</code>.
   * @return <code>null</code> if no such agency exists.
   */
  @Nullable
  public static IIdentifierIssuingAgency getAgencyOfISO6523Code (@Nullable final String sISO6523Code)
  {
    if (StringHelper.hasText (sISO6523Code))
      for (final IIdentifierIssuingAgency aAgency : s_aCodes)
        if (aAgency.getISO6523Code ().equalsIgnoreCase (sISO6523Code))
          return aAgency;
    return null;
  }

  /**
   * Check if an agency with the given ISO6523 value exists.
   *
   * @param sISO6523Code
   *        The value to search. May be <code>null</code>.
   * @return <code>true</code> if such an agency exists, <code>false</code>
   *         otherwise.
   */
  public static boolean containsAgencyWithISO6523Code (@Nullable final String sISO6523Code)
  {
    return getAgencyOfISO6523Code (sISO6523Code) != null;
  }

  /**
   * Get the schemeID code of the passed ISO6523 code. If the passed ISO6523
   * code is unknown, <code>null</code> is returned.
   *
   * @param sISO6523Code
   *        The value to search. May be <code>null</code>.
   * @return The matching schemeID or <code>null</code> if no agency with the
   *         given ISO6523 code exists.
   */
  @Nullable
  public static String getSchemeIDOfISO6523Code (@Nullable final String sISO6523Code)
  {
    final IIdentifierIssuingAgency aAgency = getAgencyOfISO6523Code (sISO6523Code);
    return aAgency == null ? null : aAgency.getSchemeID ();
  }

  /**
   * Find the agency with the respective schemeID value.
   *
   * @param sSchemeID
   *        The value to search. May be <code>null</code>.
   * @return <code>null</code> if no such agency exists.
   */
  @Nullable
  public static IIdentifierIssuingAgency getAgencyOfSchemeID (@Nullable final String sSchemeID)
  {
    if (StringHelper.hasText (sSchemeID))
      for (final IIdentifierIssuingAgency aAgency : s_aCodes)
        if (aAgency.getSchemeID ().equalsIgnoreCase (sSchemeID))
          return aAgency;
    return null;
  }

  /**
   * Check if an agency with the given schemeID value exists.
   *
   * @param sSchemeID
   *        The value to search. May be <code>null</code>.
   * @return <code>true</code> if such an agency exists, <code>false</code>
   *         otherwise.
   */
  public static boolean containsAgencyWithSchemeID (@Nullable final String sSchemeID)
  {
    return getAgencyOfSchemeID (sSchemeID) != null;
  }

  /**
   * Get the ISO6523 code of the passed schemeID. If the passed schemeID is
   * unknown, <code>null</code> is returned.
   *
   * @param sSchemeID
   *        The value to search. May be <code>null</code>.
   * @return The matching ISO6523 code or <code>null</code> if no agency with
   *         the given schemeID exists.
   */
  @Nullable
  public static String getISO6523CodeOfSchemeID (@Nullable final String sSchemeID)
  {
    final IIdentifierIssuingAgency aAgency = getAgencyOfSchemeID (sSchemeID);
    return aAgency == null ? null : aAgency.getISO6523Code ();
  }

  /**
   * Check if the specified ISO6523 value references a deprecated issuing
   * agency.
   *
   * @param sISO6523Code
   *        The value to search. May be <code>null</code>.
   * @return {@link ETriState#TRUE} if and only if an agency with the passed
   *         value was found and is deprecated. {@link ETriState#FALSE} if the
   *         agency was found and is not deprecated. {@link ETriState#UNDEFINED}
   *         if no such agency exists.
   */
  @Nonnull
  public static ETriState isAgencyWithISO6523CodeDeprecated (@Nullable final String sISO6523Code)
  {
    final IIdentifierIssuingAgency aAgency = getAgencyOfISO6523Code (sISO6523Code);
    return aAgency == null ? ETriState.UNDEFINED : ETriState.valueOf (aAgency.isDeprecated ());
  }

  /**
   * Check if the specified scheme ID references a deprecated issuing agency.
   *
   * @param sSchemeID
   *        The value to search. May be <code>null</code>.
   * @return {@link ETriState#TRUE} if and only if an agency with the passed
   *         value was found and is deprecated. {@link ETriState#FALSE} if the
   *         agency was found and is not deprecated. {@link ETriState#UNDEFINED}
   *         if no such agency exists.
   */
  @Nonnull
  public static ETriState isAgencyWithSchemeIDDeprecated (@Nullable final String sSchemeID)
  {
    final IIdentifierIssuingAgency aAgency = getAgencyOfSchemeID (sSchemeID);
    return aAgency == null ? ETriState.UNDEFINED : ETriState.valueOf (aAgency.isDeprecated ());
  }
}
