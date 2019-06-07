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
package com.helger.peppolid.peppol.participant;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.annotation.DevelopersNote;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.compare.CompareHelper;
import com.helger.commons.lang.ICloneable;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.StringParser;
import com.helger.peppolid.IMutableIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.ParticipantIdentifierType;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.IPeppolIdentifier;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.peppolid.peppol.validator.IdentifierValidator;

/**
 * A special PEPPOL participant identifier handling all the special constraints
 * of PEPPOL.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class PeppolParticipantIdentifier extends ParticipantIdentifierType implements
                                         IParticipantIdentifier,
                                         IPeppolIdentifier,
                                         IMutableIdentifier,
                                         Comparable <PeppolParticipantIdentifier>,
                                         ICloneable <PeppolParticipantIdentifier>
{

  @Nonnull
  private static String _verifyScheme (@Nullable final String sScheme)
  {
    if (!PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierSchemeValid (sScheme))
      throw new IllegalArgumentException ("Peppol Participant identifier scheme '" + sScheme + "' is invalid!");
    return sScheme;
  }

  @Nonnull
  private static String _verifyValue (@Nonnull final String sValue)
  {
    if (!PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid (sValue))
      throw new IllegalArgumentException ("Peppol Participant identifier value '" + sValue + "' is invalid!");
    return sValue;
  }

  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public PeppolParticipantIdentifier (@Nonnull final IParticipantIdentifier aIdentifier)
  {
    this (aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  /**
   * Create a new object.
   *
   * @param sScheme
   *        The scheme to use. May not be <code>null</code>.
   * @param sValue
   *        The value to use. May not be <code>null</code>.
   * @throws IllegalArgumentException
   *         If either scheme or value are invalid
   */
  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public PeppolParticipantIdentifier (@Nullable final String sScheme, @Nonnull final String sValue)
  {
    this (true, _verifyScheme (sScheme), _verifyValue (sValue));
  }

  /**
   * Private constructor that passed the pre-checked values directly to the
   * super class. Has a dummy parameter for a unique signature.
   *
   * @param bVerified
   *        dummy
   * @param sScheme
   *        Identifier scheme. May not be <code>null</code>.
   * @param sValue
   *        Identifier value. May not be <code>null</code>.
   */
  protected PeppolParticipantIdentifier (final boolean bVerified,
                                         @Nonnull final String sScheme,
                                         @Nonnull final String sValue)
  {
    setScheme (sScheme);
    setValue (sValue);
  }

  public boolean hasDefaultScheme ()
  {
    return hasScheme (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME);
  }

  /**
   * @return <code>true</code> if the identifier is valid according to the
   *         internal and external validation rules as defined by
   *         {@link com.helger.peppolid.peppol.validator.IParticipantIdentifierValidatorSPI}
   *         implementations.
   */
  public boolean isSemanticallyValid ()
  {
    return IdentifierValidator.isValidParticipantIdentifier (this);
  }

  /**
   * Extract the issuing agency ID from the passed participant identifier value.
   * <br>
   * Example: extract the <code>0088</code> from the participant identifier
   * <code>iso6523-actorid-upis::0088:123456</code><br>
   * Note: this only works for participant identifiers that are using the
   * default scheme (iso6523-actorid-upis) because for the other schemes, I just
   * can't tell!
   *
   * @return <code>null</code> if the identifier is not of default scheme or if
   *         the identifier is malformed.
   */
  @Nullable
  public String getIssuingAgencyID ()
  {
    if (hasDefaultScheme ())
      return StringHelper.getExploded (':', getValue (), 2).getAtIndex (0);
    return null;
  }

  /**
   * Extract the local participant ID from the passed participant identifier
   * value.<br>
   * Example: extract the <code>123456</code> from the participant identifier
   * <code>iso6523-actorid-upis::0088:123456</code><br>
   * Note: this only works for participant identifiers that are using the
   * default scheme (iso6523-actorid-upis) because for the other schemes, I just
   * can't tell!
   *
   * @return <code>null</code> if the identifier is not of default scheme or if
   *         the identifier is malformed.
   */
  @Nullable
  public String getLocalParticipantID ()
  {
    if (hasDefaultScheme ())
      return StringHelper.getExploded (':', getValue (), 2).getAtIndex (1);
    return null;
  }

  public int compareTo (@Nonnull final PeppolParticipantIdentifier aOther)
  {
    int ret = CompareHelper.compare (getScheme (), aOther.getScheme ());
    if (ret == 0)
      ret = CompareHelper.compare (getValue (), aOther.getValue ());
    return ret;
  }

  @Override
  @Nonnull
  @ReturnsMutableCopy
  public PeppolParticipantIdentifier getClone ()
  {
    return new PeppolParticipantIdentifier (this);
  }

  /**
   * Take the passed identifier scheme and value try to convert it back to a
   * participant identifier. If the passed scheme is invalid or if the passed
   * value is invalid, <code>null</code> is returned.
   *
   * @param sScheme
   *        The identifier scheme. May be <code>null</code> in which case
   *        <code>null</code> is returned.
   * @param sValue
   *        The identifier value. May be <code>null</code> in which case
   *        <code>null</code> is returned.
   * @return The participant identifier or <code>null</code> if any of the parts
   *         is invalid.
   * @see PeppolIdentifierFactory#isParticipantIdentifierSchemeValid(String)
   * @see PeppolIdentifierFactory#isParticipantIdentifierValueValid(String)
   */
  @Nullable
  public static PeppolParticipantIdentifier createIfValid (@Nullable final String sScheme,
                                                           @Nullable final String sValue)
  {
    if (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierSchemeValid (sScheme) &&
        PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid (sValue))
      return new PeppolParticipantIdentifier (true, sScheme, sValue);
    return null;
  }

  /**
   * Check if the passed identifier value is valid in the PEPPOL default
   * participant identifier scheme (iso6523-actorid-upis).
   *
   * @param sValue
   *        The value to be validated. Must not be <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  public static boolean isValidValueWithDefaultScheme (@Nonnull final String sValue)
  {
    // Check if the separator between identifier issuing agency and value is
    // present - can only be done if the default scheme is present
    final ICommonsList <String> aParts = StringHelper.getExploded (':', sValue, 2);
    if (aParts.size () != 2)
      return false;

    final String sIdentifierIssuingAgencyID = aParts.get (0);
    // First part must be 4 digit numeric
    if (sIdentifierIssuingAgencyID.length () != 4 || !StringParser.isUnsignedInt (sIdentifierIssuingAgencyID))
      return false;

    // Remaining part must not be empty
    final String sIdentifierValue = aParts.get (1).trim ();
    return sIdentifierValue.length () > 0;
  }
}
