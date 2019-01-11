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
package com.helger.peppol.identifier.peppol.participant;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.annotation.DevelopersNote;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.compare.CompareHelper;
import com.helger.commons.lang.ICloneable;
import com.helger.peppol.identifier.ParticipantIdentifierType;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;

/**
 * A special PEPPOL participant identifier handling all the special constraints
 * of PEPPOL.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class PeppolParticipantIdentifier extends ParticipantIdentifierType implements
                                         IMutablePeppolParticipantIdentifier,
                                         Comparable <PeppolParticipantIdentifier>,
                                         ICloneable <PeppolParticipantIdentifier>
{
  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public PeppolParticipantIdentifier (@Nonnull final IParticipantIdentifier aIdentifier)
  {
    this (aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  @Nonnull
  private static String _verifyScheme (@Nullable final String sScheme)
  {
    if (!IPeppolParticipantIdentifier.isValidScheme (sScheme))
      throw new IllegalArgumentException ("Peppol Participant identifier scheme '" + sScheme + "' is invalid!");
    return sScheme;
  }

  @Nonnull
  private static String _verifyValue (@Nonnull final String sValue)
  {
    if (!IPeppolParticipantIdentifier.isValidValue (sValue))
      throw new IllegalArgumentException ("Peppol Participant identifier value '" + sValue + "' is invalid!");
    return sValue;
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
   * @see IPeppolParticipantIdentifier#isValidScheme(String)
   * @see IPeppolParticipantIdentifier#isValidValue(String)
   */
  @Nullable
  public static PeppolParticipantIdentifier createIfValid (@Nullable final String sScheme,
                                                           @Nullable final String sValue)
  {
    if (IPeppolParticipantIdentifier.isValidScheme (sScheme) && IPeppolParticipantIdentifier.isValidValue (sValue))
      return new PeppolParticipantIdentifier (true, sScheme, sValue);
    return null;
  }
}
