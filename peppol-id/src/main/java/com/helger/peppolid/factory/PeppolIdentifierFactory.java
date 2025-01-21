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
package com.helger.peppolid.factory;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.peppolid.peppol.doctype.IPeppolGenericDocumentTypeIdentifierParts;
import com.helger.peppolid.peppol.doctype.PeppolDocumentTypeIdentifier;
import com.helger.peppolid.peppol.doctype.PeppolGenericDocumentTypeIdentifierParts;
import com.helger.peppolid.peppol.participant.PeppolParticipantIdentifier;
import com.helger.peppolid.peppol.process.PeppolProcessIdentifier;

/**
 * Default implementation of {@link IIdentifierFactory} for Peppol identifiers.
 *
 * @author Philip Helger
 */
public class PeppolIdentifierFactory implements IIdentifierFactory
{
  /** Global instance to be used. */
  public static final PeppolIdentifierFactory INSTANCE = new PeppolIdentifierFactory ();

  public static final boolean DEFAULT_STRICT = true;

  private final boolean m_bStrict;

  public PeppolIdentifierFactory ()
  {
    // Always strict
    this (DEFAULT_STRICT);
  }

  protected PeppolIdentifierFactory (final boolean bStrict)
  {
    m_bStrict = bStrict;
  }

  @Override
  public boolean isDocumentTypeIdentifierSchemeMandatory ()
  {
    return true;
  }

  /**
   * This method is deprecated for this class. Always use
   * {@link #getDefaultDocumentTypeIdentifierScheme(String)} instead.
   */
  @Nonnull
  @Override
  @Deprecated
  public String getDefaultDocumentTypeIdentifierScheme ()
  {
    // For backwards compatibility reason
    return PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS;
  }

  @Nullable
  public String getDefaultDocumentTypeIdentifierScheme (@Nullable final String sValue)
  {
    if (StringHelper.hasNoText (sValue))
      return null;

    // Current PINT determination - the best we have
    if (sValue.contains ("##urn:peppol:pint:"))
    {
      // This scheme is only used for PINT atm
      return PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD;
    }
    // This is the default
    return PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS;
  }

  @Override
  public boolean isDocumentTypeIdentifierSchemeValid (@Nullable final String sScheme)
  {
    return PeppolIdentifierHelper.isValidIdentifierScheme (sScheme);
  }

  private static boolean _isForbiddenCustomizationIDCharBusdoxDocidQns (final char c)
  {
    return c == '*' || c == 9 || c == 10 || c == 11 || c == 12 || c == 13 || c == ' ' || c == 133 || c == 160;
  }

  public static boolean isValidCustomizationIDBusdoxDocidQns (@Nullable final String s)
  {
    if (StringHelper.hasNoText (s))
      return false;

    // POLICY 17 (applies identical only to busdox-docid-qns)
    for (final char c : s.toCharArray ())
      if (_isForbiddenCustomizationIDCharBusdoxDocidQns (c))
        return false;

    return true;
  }

  private static boolean _isForbiddenCustomizationIDCharPeppolDoctypeWildcard (final char c)
  {
    // "*" is allowed
    return c == 9 || c == 10 || c == 11 || c == 12 || c == 13 || c == ' ' || c == 133 || c == 160;
  }

  public static boolean isValidCustomizationIDPeppolDoctypeWildcard (@Nullable final String s)
  {
    if (StringHelper.hasNoText (s))
      return false;

    // chapter 5.1.2
    for (final char c : s.toCharArray ())
      if (_isForbiddenCustomizationIDCharPeppolDoctypeWildcard (c))
        return false;

    return true;
  }

  @Override
  public boolean isDocumentTypeIdentifierValueValid (@Nullable final String sScheme, @Nullable final String sValue)
  {
    if (sValue == null)
      return false;

    final int nLength = sValue.length ();

    // POLICY 1 - general rules
    // at least 1 char
    if (nLength == 0)
      return false;

    // <= 500 chars
    if (nLength > PeppolIdentifierHelper.MAX_DOCUMENT_TYPE_VALUE_LENGTH)
      return false;

    // Check if the value is ISO-8859-1 encoded
    if (!PeppolIdentifierHelper.areCharsetChecksDisabled ())
      if (!StandardCharsets.ISO_8859_1.newEncoder ().canEncode (sValue))
        return false;

    if (m_bStrict)
      try
      {
        final IPeppolGenericDocumentTypeIdentifierParts aParts = PeppolGenericDocumentTypeIdentifierParts.extractFromString (sValue);

        if (sScheme != null)
          switch (sScheme)
          {
            case PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS:
              // POLICY 17
              if (!isValidCustomizationIDBusdoxDocidQns (aParts.getCustomizationID ()))
                return false;
              break;
            case PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD:
              // Chapter 5.1.2
              if (!isValidCustomizationIDPeppolDoctypeWildcard (aParts.getCustomizationID ()))
                return false;
              break;
            default:
              // Ignore - no special Peppol rules
          }

      }
      catch (final IllegalArgumentException ex)
      {
        // Syntax error - not valid
        return false;
      }

    return true;
  }

  @Nullable
  @Override
  public PeppolDocumentTypeIdentifier createDocumentTypeIdentifierWithDefaultScheme (@Nullable final String sValue)
  {
    return createDocumentTypeIdentifier (getDefaultDocumentTypeIdentifierScheme (sValue), sValue);
  }

  @Nullable
  public PeppolDocumentTypeIdentifier createDocumentTypeIdentifier (@Nullable final String sScheme,
                                                                    @Nullable final String sValue)
  {
    final String sRealScheme = nullNotEmpty (sScheme);
    final String sRealValue = nullNotEmpty (isDocumentTypeIdentifierCaseInsensitive (sRealScheme) ? getUnifiedValue (sValue)
                                                                                                  : sValue);
    if (isDocumentTypeIdentifierSchemeValid (sRealScheme) &&
        isDocumentTypeIdentifierValueValid (sRealScheme, sRealValue))
      return PeppolDocumentTypeIdentifier.internalCreatePreVerified (sRealScheme, sRealValue);
    return null;
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
   */
  @Override
  public boolean isParticipantIdentifierSchemeValid (@Nullable final String sScheme)
  {
    if (!PeppolIdentifierHelper.isValidIdentifierScheme (sScheme))
      return false;

    return RegExHelper.stringMatchesPattern (PeppolIdentifierHelper.PARTICIPANT_IDENTIFIER_SCHEME_REGEX,
                                             sScheme.toLowerCase (Locale.US));
  }

  /**
   * Check if the passed participant identifier value is valid. A valid
   * identifier must have at least 1 character and at last
   * {@link PeppolIdentifierHelper#MAX_PARTICIPANT_VALUE_LENGTH} characters.
   * Also it must be US ASCII encoded. This check method considers only the
   * value and not the identifier scheme!
   */
  @Override
  public boolean isParticipantIdentifierValueValid (@Nullable final String sScheme, @Nullable final String sValue)
  {
    if (sValue == null)
      return false;

    final int nLength = sValue.length ();

    // POLICY 1
    // At least one characters
    if (nLength == 0)
      return false;

    // <= 50 characters
    if (nLength > PeppolIdentifierHelper.MAX_PARTICIPANT_VALUE_LENGTH)
      return false;

    // Check if the value is ISO-8859-1 encoded
    if (!PeppolIdentifierHelper.areCharsetChecksDisabled ())
      if (!StandardCharsets.ISO_8859_1.newEncoder ().canEncode (sValue))
        return false;

    // Check if the separator between identifier issuing agency and value is
    // present - can only be done if the default scheme is present
    // Also does not work in certain representations when the numeric scheme is
    // extracted into an attribute (see e.g. POLICY 14)
    if (false)
      if (sValue.indexOf (':') < 0)
        return false;

    return true;
  }

  @Nullable
  @Override
  public PeppolParticipantIdentifier createParticipantIdentifierWithDefaultScheme (@Nullable final String sValue)
  {
    return createParticipantIdentifier (getDefaultParticipantIdentifierScheme (), sValue);
  }

  @Nullable
  public PeppolParticipantIdentifier createParticipantIdentifier (@Nullable final String sScheme,
                                                                  @Nullable final String sValue)
  {
    final String sRealScheme = nullNotEmpty (sScheme);
    final String sRealValue = nullNotEmpty (isParticipantIdentifierCaseInsensitive (sRealScheme) ? getUnifiedValue (sValue)
                                                                                                 : sValue);
    if (isParticipantIdentifierSchemeValid (sScheme) && isParticipantIdentifierValueValid (sScheme, sValue))
      return PeppolParticipantIdentifier.internalCreatePreVerified (sRealScheme, sRealValue);
    return null;
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
  public boolean isProcessIdentifierValueValid (@Nullable final String sScheme, @Nullable final String sValue)
  {
    final int nLength = StringHelper.getLength (sValue);

    // POLICY 1
    // At least one char
    if (nLength == 0)
      return false;

    // <= 200 chars
    if (nLength > PeppolIdentifierHelper.MAX_PROCESS_VALUE_LENGTH)
      return false;

    // Check if the value is ISO-8859-1 encoded
    if (!PeppolIdentifierHelper.areCharsetChecksDisabled ())
      if (!StandardCharsets.ISO_8859_1.newEncoder ().canEncode (sValue))
        return false;

    // POLICY 25
    for (final char c : sValue.toCharArray ())
      if (Character.isWhitespace (c))
        return false;

    return true;
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
    final String sRealScheme = nullNotEmpty (sScheme);
    final String sRealValue = nullNotEmpty (isProcessIdentifierCaseInsensitive (sRealScheme) ? getUnifiedValue (sValue)
                                                                                             : sValue);
    if (isProcessIdentifierSchemeValid (sScheme) && isProcessIdentifierValueValid (sScheme, sValue))
      return PeppolProcessIdentifier.internalCreatePreVerified (sRealScheme, sRealValue);
    return null;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("Strict", m_bStrict).getToString ();
  }
}
