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
package com.helger.peppolid.peppol.pidscheme;

import com.helger.annotation.Nonempty;
import com.helger.annotation.style.PresentForCodeCoverage;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.state.ETriState;
import com.helger.base.string.StringHelper;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.ICommonsList;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * This class manages the Peppol Participant identifier schemes using the
 * <b>iso6523-actorid-upis</b> scheme.
 *
 * @author Philip Helger
 */
public final class PeppolParticipantIdentifierSchemeManager
{
  private static final ICommonsList <IPeppolParticipantIdentifierScheme> PI_SCHEMES = new CommonsArrayList <> ();

  static
  {
    // Add all predefined identifier issuing agencies
    for (final EPredefinedParticipantIdentifierScheme eIIA : EPredefinedParticipantIdentifierScheme.values ())
      PI_SCHEMES.add (eIIA);
  }

  @PresentForCodeCoverage
  private static final PeppolParticipantIdentifierSchemeManager INSTANCE = new PeppolParticipantIdentifierSchemeManager ();

  private PeppolParticipantIdentifierSchemeManager ()
  {}

  /**
   * @return A non-modifiable list of all Peppol identifier issuing agencies.
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public static ICommonsList <? extends IPeppolParticipantIdentifierScheme> getAllSchemes ()
  {
    return PI_SCHEMES.getClone ();
  }

  /**
   * Find the agency with the respective ISO6523 value.
   *
   * @param sISO6523Code
   *        The value to search. May be <code>null</code>.
   * @return <code>null</code> if no such agency exists.
   */
  @Nullable
  public static IPeppolParticipantIdentifierScheme getSchemeOfISO6523Code (@Nullable final String sISO6523Code)
  {
    if (StringHelper.isNotEmpty (sISO6523Code))
      for (final IPeppolParticipantIdentifierScheme aScheme : PI_SCHEMES)
        if (aScheme.getISO6523Code ().equalsIgnoreCase (sISO6523Code))
          return aScheme;
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
  public static boolean containsSchemeWithISO6523Code (@Nullable final String sISO6523Code)
  {
    return getSchemeOfISO6523Code (sISO6523Code) != null;
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
    final IPeppolParticipantIdentifierScheme aScheme = getSchemeOfISO6523Code (sISO6523Code);
    return aScheme == null ? null : aScheme.getSchemeID ();
  }

  /**
   * Find the agency with the respective schemeID value.
   *
   * @param sSchemeID
   *        The value to search. May be <code>null</code>.
   * @return <code>null</code> if no such agency exists.
   */
  @Nullable
  public static IPeppolParticipantIdentifierScheme getSchemeOfSchemeID (@Nullable final String sSchemeID)
  {
    if (StringHelper.isNotEmpty (sSchemeID))
      for (final IPeppolParticipantIdentifierScheme aScheme : PI_SCHEMES)
        if (aScheme.getSchemeID ().equalsIgnoreCase (sSchemeID))
          return aScheme;
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
  public static boolean containsSchemeWithSchemeID (@Nullable final String sSchemeID)
  {
    return getSchemeOfSchemeID (sSchemeID) != null;
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
    final IPeppolParticipantIdentifierScheme aScheme = getSchemeOfSchemeID (sSchemeID);
    return aScheme == null ? null : aScheme.getISO6523Code ();
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
  public static ETriState isSchemeWithISO6523CodeDeprecated (@Nullable final String sISO6523Code)
  {
    final IPeppolParticipantIdentifierScheme aScheme = getSchemeOfISO6523Code (sISO6523Code);
    return aScheme == null ? ETriState.UNDEFINED : ETriState.valueOf (aScheme.isDeprecated ());
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
  public static ETriState isSchemeWithSchemeIDDeprecated (@Nullable final String sSchemeID)
  {
    final IPeppolParticipantIdentifierScheme aScheme = getSchemeOfSchemeID (sSchemeID);
    return aScheme == null ? ETriState.UNDEFINED : ETriState.valueOf (aScheme.isDeprecated ());
  }

  /**
   * Find the identifier scheme that matches the provided participant ID. This
   * method checks only participants that use the default Peppol identifier
   * scheme `iso6523-actorid-upis`.
   *
   * @param aParticipantID
   *        The participant ID to search. May be <code>null</code>.
   * @return <code>null</code> if no such agency exists or if the participant ID
   *         is not suitable.
   * @since 5.2.5
   */
  @Nullable
  public static IPeppolParticipantIdentifierScheme getSchemeOfIdentifier (@Nullable final IParticipantIdentifier aParticipantID)
  {
    if (aParticipantID != null &&
        aParticipantID.hasScheme (PeppolIdentifierHelper.PARTICIPANT_SCHEME_ISO6523_ACTORID_UPIS))
    {
      final String sValue = aParticipantID.getValue ();
      // Value must be at least something like "1234:"
      if (sValue.length () > 5)
        return getSchemeOfISO6523Code (sValue.substring (0, 4));
    }
    return null;
  }
}
