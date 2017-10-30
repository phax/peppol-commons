/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Version: MPL 2.0/EUPL 1.2
 * -
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 * -
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.2 or - as soon they will be approved
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
 * -
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.identifier.peppol.participant;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.annotation.DevelopersNote;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.compare.CompareHelper;
import com.helger.commons.lang.ICloneable;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.CIdentifier;
import com.helger.peppol.identifier.ParticipantIdentifierType;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;

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
   * Create a new participant identifier that uses the default schema
   * {@link PeppolIdentifierHelper#DEFAULT_PARTICIPANT_SCHEME}
   *
   * @param sValue
   *        The identifier value like <code>0088:12345678</code>
   * @return The created {@link PeppolParticipantIdentifier} and never
   *         <code>null</code>.
   * @deprecated Use
   *             {@link com.helger.peppol.identifier.factory.IIdentifierFactory#createParticipantIdentifierWithDefaultScheme(String)}
   *             instead
   */
  @Nonnull
  @Deprecated
  public static PeppolParticipantIdentifier createWithDefaultScheme (@Nonnull final String sValue)
  {
    return new PeppolParticipantIdentifier (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, sValue);
  }

  /**
   * Create a new participant identifier from the URI representation. This is
   * the inverse operation of {@link #getURIEncoded()}.
   *
   * @param sURIPart
   *        The URI part in the format <code>scheme::value</code> (e.g.
   *        <code>iso6523-actorid-upis::0088:12345678</code>). It must NOT be
   *        percent encoded!
   * @return The created {@link PeppolParticipantIdentifier} and never
   *         <code>null</code>.
   * @throws IllegalArgumentException
   *         If the passed identifier is not a valid URI encoded identifier
   */
  @Nonnull
  @Deprecated
  public static PeppolParticipantIdentifier createFromURIPart (@Nonnull final String sURIPart) throws IllegalArgumentException
  {
    final PeppolParticipantIdentifier ret = createFromURIPartOrNull (sURIPart);
    if (ret == null)
      throw new IllegalArgumentException ("Peppol Participant identifier '" +
                                          sURIPart +
                                          "' did not include correct delimiter: " +
                                          CIdentifier.URL_SCHEME_VALUE_SEPARATOR);

    return ret;
  }

  /**
   * Create a new participant identifier from the URI representation. This is
   * the inverse operation of {@link #getURIEncoded()}. Take the passed URI part
   * and try to convert it back to a participant identifier. The URI part must
   * have the layout <code>scheme::value</code>. This method accepts only valid
   * PEPPOL participant identifier schemes and valid participant identifier
   * values.
   *
   * @param sURIPart
   *        The URI part in the format <code>scheme::value</code> (e.g.
   *        <code>iso6523-actorid-upis::0088:12345678</code>). It must NOT be
   *        percent encoded!
   * @return The created {@link PeppolParticipantIdentifier} or
   *         <code>null</code> if the passed identifier is not a valid URI
   *         encoded identifier
   * @deprecated Use
   *             {@link com.helger.peppol.identifier.factory.IIdentifierFactory#parseParticipantIdentifier(String)}
   *             instead
   */
  @Nullable
  @Deprecated
  public static PeppolParticipantIdentifier createFromURIPartOrNull (@Nullable final String sURIPart)
  {
    if (sURIPart == null)
      return null;

    // This is quicker than splitting with RegEx!
    final ICommonsList <String> aSplitted = StringHelper.getExploded (CIdentifier.URL_SCHEME_VALUE_SEPARATOR,
                                                                      sURIPart,
                                                                      2);
    if (aSplitted.size () != 2)
      return null;

    return createIfValid (aSplitted.get (0), aSplitted.get (1));
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

  /**
   * Check if the passed participant identifier is valid. This method checks for
   * the existence of the scheme and the value and validates both.
   *
   * @param sURIPart
   *        The participant identifier to be checked (including the scheme). May
   *        be <code>null</code>.
   * @return <code>true</code> if the participant identifier is valid,
   *         <code>false</code> otherwise
   */
  @Deprecated
  public static boolean isValidURIPart (@Nullable final String sURIPart)
  {
    return createFromURIPartOrNull (sURIPart) != null;
  }
}
