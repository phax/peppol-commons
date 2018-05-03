/**
 * Copyright (C) 2015-2018 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.identifier.peppol.issuingagency;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.state.ETriState;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.factory.PeppolIdentifierFactory;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;

/**
 * This class manages the PEPPOL identifier issuing agencies using the
 * <b>iso6523-actorid-upis</b> scheme.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class ParticipantIdentifierSchemeManager
{
  private static final ICommonsList <IParticipantIdentifierScheme> s_aCodes = new CommonsArrayList <> ();

  static
  {
    // Add all predefined identifier issuing agencies
    for (final EPredefinedParticipantIdentifierScheme eIIA : EPredefinedParticipantIdentifierScheme.values ())
      s_aCodes.add (eIIA);
  }

  @PresentForCodeCoverage
  private static final ParticipantIdentifierSchemeManager s_aInstance = new ParticipantIdentifierSchemeManager ();

  private ParticipantIdentifierSchemeManager ()
  {}

  /**
   * @return A non-modifiable list of all PEPPOL identifier issuing agencies.
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public static ICommonsList <? extends IParticipantIdentifierScheme> getAllAgencies ()
  {
    return s_aCodes.getClone ();
  }

  /**
   * Find the agency with the respective ISO6523 value.
   *
   * @param sISO6523Code
   *        The value to search. May be <code>null</code>.
   * @return <code>null</code> if no such agency exists.
   */
  @Nullable
  public static IParticipantIdentifierScheme getAgencyOfISO6523Code (@Nullable final String sISO6523Code)
  {
    if (StringHelper.hasText (sISO6523Code))
      for (final IParticipantIdentifierScheme aAgency : s_aCodes)
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
    final IParticipantIdentifierScheme aAgency = getAgencyOfISO6523Code (sISO6523Code);
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
  public static IParticipantIdentifierScheme getAgencyOfSchemeID (@Nullable final String sSchemeID)
  {
    if (StringHelper.hasText (sSchemeID))
      for (final IParticipantIdentifierScheme aAgency : s_aCodes)
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
    final IParticipantIdentifierScheme aAgency = getAgencyOfSchemeID (sSchemeID);
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
    final IParticipantIdentifierScheme aAgency = getAgencyOfISO6523Code (sISO6523Code);
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
    final IParticipantIdentifierScheme aAgency = getAgencyOfSchemeID (sSchemeID);
    return aAgency == null ? ETriState.UNDEFINED : ETriState.valueOf (aAgency.isDeprecated ());
  }

  /**
   * Find the agency that matches the provided participant ID. This method
   * checks only participants that use the default PEPPOL identifier scheme
   * `iso6523-actorid-upis`.
   *
   * @param aParticipantID
   *        The participant ID to search. May be <code>null</code>.
   * @return <code>null</code> if no such agency exists or if the participant ID
   *         is not suitable.
   * @since 5.2.5
   */
  @Nullable
  public static IParticipantIdentifierScheme getAgencyOfIdentifier (@Nullable final IParticipantIdentifier aParticipantID)
  {
    if (aParticipantID != null &&
        aParticipantID.hasScheme (PeppolIdentifierFactory.INSTANCE.getDefaultParticipantIdentifierScheme ()))
    {
      final String sValue = aParticipantID.getValue ();
      // Value must be at least something like "1234:"
      if (sValue.length () > 5)
        return getAgencyOfISO6523Code (sValue.substring (0, 4));
    }
    return null;
  }
}
