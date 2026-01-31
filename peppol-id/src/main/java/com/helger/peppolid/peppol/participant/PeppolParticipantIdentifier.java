/*
 * Copyright (C) 2015-2026 Philip Helger
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
package com.helger.peppolid.peppol.participant;

import java.util.List;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.misc.DevelopersNote;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.clone.ICloneable;
import com.helger.base.compare.CompareHelper;
import com.helger.base.string.StringHelper;
import com.helger.base.string.StringParser;
import com.helger.peppolid.IMutableIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.peppol.IPeppolIdentifier;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.peppolid.peppol.validator.IdentifierValidator;
import com.helger.xsds.peppol.id1.ParticipantIdentifierType;

/**
 * A special Peppol participant identifier handling all the special constraints of Peppol.
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
  @NonNull
  private static String _verifyScheme (@NonNull final IIdentifierFactory aIF, @Nullable final String sScheme)
  {
    if (!aIF.isParticipantIdentifierSchemeValid (sScheme))
      throw new IllegalArgumentException ("Peppol Participant identifier scheme '" + sScheme + "' is invalid!");
    return sScheme;
  }

  @NonNull
  private static String _verifyValue (@NonNull final IIdentifierFactory aIF,
                                      @Nullable final String sScheme,
                                      @NonNull final String sValue)
  {
    if (!aIF.isParticipantIdentifierValueValid (sScheme, sValue))
      throw new IllegalArgumentException ("Peppol Participant identifier value '" +
                                          sValue +
                                          "' is invalid for scheme '" +
                                          sScheme +
                                          "'!");
    return sValue;
  }

  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public PeppolParticipantIdentifier (@NonNull final IIdentifierFactory aIF,
                                      @NonNull final IParticipantIdentifier aIdentifier)
  {
    this (aIF, aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  /**
   * Create a new object.
   *
   * @param aIF
   *        The Identifier Factory to use. May not be <code>null</code>.
   * @param sScheme
   *        The scheme to use. May not be <code>null</code>.
   * @param sValue
   *        The value to use. May not be <code>null</code>.
   * @throws IllegalArgumentException
   *         If either scheme or value are invalid
   */
  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public PeppolParticipantIdentifier (@NonNull final IIdentifierFactory aIF,
                                      @Nullable final String sScheme,
                                      @NonNull final String sValue)
  {
    this (true, _verifyScheme (aIF, sScheme), _verifyValue (aIF, sScheme, sValue));
  }

  /**
   * Private constructor that passed the pre-checked values directly to the super class. Has a dummy
   * parameter for a unique signature.
   *
   * @param bVerified
   *        dummy
   * @param sScheme
   *        Identifier scheme. May not be <code>null</code>.
   * @param sValue
   *        Identifier value. May not be <code>null</code>.
   */
  protected PeppolParticipantIdentifier (final boolean bVerified,
                                         @NonNull final String sScheme,
                                         @NonNull final String sValue)
  {
    setScheme (sScheme);
    setValue (sValue);
  }

  public boolean hasDefaultScheme ()
  {
    return hasScheme (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME);
  }

  /**
   * @return <code>true</code> if the identifier is valid according to the internal and external
   *         validation rules as defined by
   *         {@link com.helger.peppolid.peppol.validator.IParticipantIdentifierValidatorSPI}
   *         implementations.
   */
  public boolean isSemanticallyValid ()
  {
    return IdentifierValidator.isValidParticipantIdentifier (this);
  }

  /**
   * Extract the issuing agency ID from the passed participant identifier value. <br>
   * Example: extract the <code>0088</code> from the participant identifier
   * <code>iso6523-actorid-upis::0088:123456</code><br>
   * Note: this only works for participant identifiers that are using the default scheme
   * (iso6523-actorid-upis) because for the other schemes, I just can't tell!
   *
   * @return <code>null</code> if the identifier is not of default scheme or if the identifier is
   *         malformed.
   */
  @Nullable
  public String getIssuingAgencyID ()
  {
    if (hasDefaultScheme ())
      return StringHelper.getExploded (':', getValue (), 2).get (0);
    return null;
  }

  /**
   * Extract the local participant ID from the passed participant identifier value.<br>
   * Example: extract the <code>123456</code> from the participant identifier
   * <code>iso6523-actorid-upis::0088:123456</code><br>
   * Note: this only works for participant identifiers that are using the default scheme
   * (iso6523-actorid-upis) because for the other schemes, I just can't tell!
   *
   * @return <code>null</code> if the identifier is not of default scheme or if the identifier is
   *         malformed.
   */
  @Nullable
  public String getLocalParticipantID ()
  {
    if (hasDefaultScheme ())
    {
      final List <String> aParts = StringHelper.getExploded (':', getValue (), 2);
      return aParts.size () >= 2 ? aParts.get (1) : null;
    }
    return null;
  }

  public int compareTo (@NonNull final PeppolParticipantIdentifier aOther)
  {
    int ret = CompareHelper.compare (getScheme (), aOther.getScheme ());
    if (ret == 0)
      ret = CompareHelper.compare (getValue (), aOther.getValue ());
    return ret;
  }

  @Override
  @NonNull
  @ReturnsMutableCopy
  public PeppolParticipantIdentifier getClone ()
  {
    return new PeppolParticipantIdentifier (true, getScheme (), getValue ());
  }

  @Override
  public boolean equals (final Object o)
  {
    // for compareTo
    return super.equals (o);
  }

  @Override
  public int hashCode ()
  {
    // for compareTo
    return super.hashCode ();
  }

  /**
   * Take the passed identifier scheme and value create a new {@link PeppolParticipantIdentifier}.
   * This is for internal usage only.
   *
   * @param sScheme
   *        The identifier scheme. May be <code>null</code> in which case <code>null</code> is
   *        returned.
   * @param sValue
   *        The identifier value. May be <code>null</code> in which case <code>null</code> is
   *        returned.
   * @return The participant identifier or <code>null</code> if any of the parts is invalid.
   */
  @NonNull
  public static PeppolParticipantIdentifier internalCreatePreVerified (@Nullable final String sScheme,
                                                                       @Nullable final String sValue)
  {
    return new PeppolParticipantIdentifier (true, sScheme, sValue);
  }

  /**
   * Check if the passed identifier value is valid in the PEPPOL default participant identifier
   * scheme (iso6523-actorid-upis).
   *
   * @param sValue
   *        The value to be validated. Must not be <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code> otherwise.
   */
  public static boolean isValidValueWithDefaultScheme (@NonNull final String sValue)
  {
    // Check if the separator between identifier issuing agency and value is
    // present - can only be done if the default scheme is present
    final List <String> aParts = StringHelper.getExploded (':', sValue, 2);
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
