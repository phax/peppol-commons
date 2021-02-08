/**
 * Copyright (C) 2015-2021 Philip Helger
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
package com.helger.peppolid.factory;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.peppolid.peppol.doctype.PeppolDocumentTypeIdentifier;
import com.helger.peppolid.peppol.participant.PeppolParticipantIdentifier;
import com.helger.peppolid.peppol.process.PeppolProcessIdentifier;

/**
 * Default implementation of {@link IIdentifierFactory} for PEPPOL identifiers.
 *
 * @author Philip Helger
 */
public class PeppolIdentifierFactory implements IIdentifierFactory
{
  /** Global instance to be used. */
  public static final PeppolIdentifierFactory INSTANCE = new PeppolIdentifierFactory ();

  public PeppolIdentifierFactory ()
  {}

  @Override
  public boolean isDocumentTypeIdentifierSchemeMandatory ()
  {
    return true;
  }

  @Nonnull
  @Override
  public String getDefaultDocumentTypeIdentifierScheme ()
  {
    // For backwards compatibility reason
    return PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS;
  }

  @Override
  public boolean isDocumentTypeIdentifierSchemeValid (@Nullable final String sScheme)
  {
    return PeppolIdentifierHelper.isValidIdentifierScheme (sScheme);
  }

  @Override
  public boolean isDocumentTypeIdentifierValueValid (@Nullable final String sValue)
  {
    if (sValue == null)
      return false;

    final int nLength = sValue.length ();

    // > 0 and <= 500 chars
    if (nLength == 0 || nLength > PeppolIdentifierHelper.MAX_DOCUMENT_TYPE_VALUE_LENGTH)
      return false;

    // Check if the value is ISO-8859-1 encoded
    return PeppolIdentifierHelper.areCharsetChecksDisabled () || StandardCharsets.ISO_8859_1.newEncoder ().canEncode (sValue);
  }

  @Nullable
  @Override
  public PeppolDocumentTypeIdentifier createDocumentTypeIdentifierWithDefaultScheme (@Nullable final String sValue)
  {
    return createDocumentTypeIdentifier (getDefaultDocumentTypeIdentifierScheme (), sValue);
  }

  @Nullable
  public PeppolDocumentTypeIdentifier createDocumentTypeIdentifier (@Nullable final String sScheme, @Nullable final String sValue)
  {
    final String sRealValue = isDocumentTypeIdentifierCaseInsensitive (sScheme) ? getUnifiedValue (sValue) : sValue;
    return PeppolDocumentTypeIdentifier.createIfValid (nullNotEmpty (sScheme), nullNotEmpty (sRealValue));
  }

  @Override
  public boolean isParticipantIdentifierSchemeMandatory ()
  {
    return true;
  }

  @Nonnull
  @Override
  public String getDefaultParticipantIdentifierScheme ()
  {
    return PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME;
  }

  @Override
  public boolean isParticipantIdentifierCaseInsensitive (@Nullable final String sScheme)
  {
    return PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME.equals (sScheme);
  }

  /**
   * Check if the given scheme is a valid participant identifier scheme (like
   * {@link PeppolIdentifierHelper#DEFAULT_PARTICIPANT_SCHEME}). It is valid if
   * it has at least 1 character and at last 25 characters (see
   * {@link PeppolIdentifierHelper#MAX_IDENTIFIER_SCHEME_LENGTH}}) and matches a
   * certain regular expression (see
   * {@link PeppolIdentifierHelper#PARTICIPANT_IDENTIFIER_SCHEME_REGEX}). Please
   * note that the regular expression is applied case insensitive!<br>
   * This limitation is important, because the participant identifier scheme is
   * directly encoded into the SML DNS name record.
   *
   * @param sScheme
   *        The scheme to check. May be <code>null</code>.
   * @return <code>true</code> if the passed scheme is a valid participant
   *         identifier scheme, <code>false</code> otherwise.
   * @see PeppolIdentifierHelper#isValidIdentifierScheme(String)
   */
  @Override
  public boolean isParticipantIdentifierSchemeValid (@Nullable final String sScheme)
  {
    if (!PeppolIdentifierHelper.isValidIdentifierScheme (sScheme))
      return false;
    return RegExHelper.stringMatchesPattern (PeppolIdentifierHelper.PARTICIPANT_IDENTIFIER_SCHEME_REGEX, sScheme.toLowerCase (Locale.US));
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
  @Override
  public boolean isParticipantIdentifierValueValid (@Nullable final String sValue)
  {
    if (sValue == null)
      return false;

    final int nLength = sValue.length ();
    // > 0 and <= 50 characters
    if (nLength == 0 || nLength > PeppolIdentifierHelper.MAX_PARTICIPANT_VALUE_LENGTH)
      return false;

    // Check if the separator between identifier issuing agency and value is
    // present - can only be done if the default scheme is present
    if (false)
      if (sValue.indexOf (':') < 0)
        return false;

    // Check if the value is US ASCII encoded
    return PeppolIdentifierHelper.areCharsetChecksDisabled () || StandardCharsets.US_ASCII.newEncoder ().canEncode (sValue);
  }

  @Nullable
  @Override
  public PeppolParticipantIdentifier createParticipantIdentifierWithDefaultScheme (@Nullable final String sValue)
  {
    return createParticipantIdentifier (getDefaultParticipantIdentifierScheme (), sValue);
  }

  @Nullable
  public PeppolParticipantIdentifier createParticipantIdentifier (@Nullable final String sScheme, @Nullable final String sValue)
  {
    final String sRealValue = isParticipantIdentifierCaseInsensitive (sScheme) ? getUnifiedValue (sValue) : sValue;
    return PeppolParticipantIdentifier.createIfValid (nullNotEmpty (sScheme), nullNotEmpty (sRealValue));
  }

  @Nonnull
  @Override
  public String getDefaultProcessIdentifierScheme ()
  {
    return PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME;
  }

  @Override
  public boolean isProcessIdentifierSchemeValid (@Nullable final String sScheme)
  {
    return PeppolIdentifierHelper.isValidIdentifierScheme (sScheme);
  }

  @Override
  public boolean isProcessIdentifierValueValid (@Nullable final String sValue)
  {
    final int nLength = StringHelper.getLength (sValue);
    // > 0 or <= 200 chars
    if (nLength == 0 || nLength > PeppolIdentifierHelper.MAX_PROCESS_VALUE_LENGTH)
      return false;

    // Check if the value is ISO-8859-1 encoded
    return PeppolIdentifierHelper.areCharsetChecksDisabled () || StandardCharsets.ISO_8859_1.newEncoder ().canEncode (sValue);
  }

  @Nullable
  @Override
  public PeppolProcessIdentifier createProcessIdentifierWithDefaultScheme (@Nullable final String sValue)
  {
    return createProcessIdentifier (getDefaultProcessIdentifierScheme (), sValue);
  }

  @Nullable
  public PeppolProcessIdentifier createProcessIdentifier (@Nullable final String sScheme, @Nullable final String sValue)
  {
    final String sRealValue = isProcessIdentifierCaseInsensitive (sScheme) ? getUnifiedValue (sValue) : sValue;
    return PeppolProcessIdentifier.createIfValid (nullNotEmpty (sScheme), nullNotEmpty (sRealValue));
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).getToString ();
  }
}
