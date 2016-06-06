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
package com.helger.peppol.identifier.peppol.participant;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.charset.CCharset;
import com.helger.commons.collection.ext.ICommonsList;
import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.StringParser;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.identifier.peppol.IPeppolIdentifier;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;
import com.helger.peppol.identifier.peppol.validator.IdentifierValidator;
import com.helger.peppol.url.BDXURLProvider;

/**
 * Base interface for a PEPPOL read-only participant identifier.
 *
 * @author philip
 */
public interface IPeppolParticipantIdentifier extends IPeppolIdentifier, IParticipantIdentifier
{
  /**
   * The regular expression to be used for validating participant identifier
   * schemes (not values!). See BusDox specification 1.0.1, chapter 2.3
   */
  String PARTICIPANT_IDENTIFIER_SCHEME_REGEX = "[a-z0-9]+-[a-z0-9]+-[a-z0-9]+";

  default boolean hasDefaultScheme ()
  {
    return hasScheme (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME);
  }

  /**
   * @return <code>true</code> if the identifier is valid according to the
   *         internal and external validation rules as defined by
   *         {@link com.helger.peppol.identifier.peppol.validator.IParticipantIdentifierValidatorSPI}
   *         implementations.
   */
  default boolean isSemanticallyValid ()
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
  default String getIssuingAgencyID ()
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
  default String getLocalParticipantID ()
  {
    if (hasDefaultScheme ())
      return StringHelper.getExploded (':', getValue (), 2).getAtIndex (1);
    return null;
  }

  /**
   * Check if the given scheme is a valid participant identifier scheme (like
   * {@link PeppolIdentifierHelper#DEFAULT_PARTICIPANT_SCHEME}). It is valid if
   * it has at least 1 character and at last 25 characters (see
   * {@link PeppolIdentifierHelper#MAX_IDENTIFIER_SCHEME_LENGTH}}) and matches a
   * certain regular expression (see
   * {@link #PARTICIPANT_IDENTIFIER_SCHEME_REGEX}). Please note that the regular
   * expression is applied case insensitive!<br>
   * This limitation is important, because the participant identifier scheme is
   * directly encoded into the SML DNS name record.
   *
   * @param sScheme
   *        The scheme to check.
   * @return <code>true</code> if the passed scheme is a valid participant
   *         identifier scheme, <code>false</code> otherwise.
   * @see PeppolIdentifierHelper#isValidIdentifierScheme(String)
   */
  static boolean isValidScheme (@Nullable final String sScheme)
  {
    if (!PeppolIdentifierHelper.isValidIdentifierScheme (sScheme))
      return false;
    return RegExHelper.stringMatchesPattern (PARTICIPANT_IDENTIFIER_SCHEME_REGEX,
                                             sScheme.toLowerCase (BDXURLProvider.URL_LOCALE));
  }

  /**
   * Check if the passed participant identifier value is valid. A valid
   * identifier must have at least 1 character and at last
   * {@link PeppolIdentifierHelper#MAX_PARTICIPANT_VALUE_LENGTH} characters.
   * Also it must be US ASCII encoded. This check method considers only the
   * value and not the identifier scheme!
   *
   * @param sValue
   *        The participant identifier value to be checked (without the scheme).
   *        May be <code>null</code>.
   * @return <code>true</code> if the participant identifier value is valid,
   *         <code>false</code> otherwise.
   */
  static boolean isValidValue (@Nullable final String sValue)
  {
    if (sValue == null)
      return false;

    final int nLength = sValue.length ();
    if (nLength == 0 || nLength > PeppolIdentifierHelper.MAX_PARTICIPANT_VALUE_LENGTH)
      return false;

    // Check if the separator between identifier issuing agency and value is
    // present - can only be done if the default scheme is present
    if (false)
      if (sValue.indexOf (':') < 0)
        return false;

    // Check if the value is US ASCII encoded
    return PeppolIdentifierHelper.areCharsetChecksDisabled () ||
           CCharset.CHARSET_US_ASCII_OBJ.newEncoder ().canEncode (sValue);
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
  static boolean isValidValueWithDefaultScheme (@Nonnull final String sValue)
  {
    // Check if the separator between identifier issuing agency and value is
    // present - can only be done if the default scheme is present
    final ICommonsList <String> aParts = StringHelper.getExploded (':', sValue, 2);
    if (aParts.size () != 2)
      return false;

    final String sIdentifierIssuingAgencyID = aParts.get (0);
    if (sIdentifierIssuingAgencyID.length () != 4 || !StringParser.isUnsignedInt (sIdentifierIssuingAgencyID))
      return false;

    final String sIdentifierValue = aParts.get (1).trim ();
    return sIdentifierValue.length () > 0;
  }
}
