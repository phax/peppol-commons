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
package com.helger.peppol.identifier.peppol;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.charset.CCharset;
import com.helger.commons.collection.ArrayHelper;
import com.helger.commons.collection.ext.ICommonsList;
import com.helger.commons.compare.CompareHelper;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.exception.InitializationException;
import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.CIdentifier;
import com.helger.peppol.identifier.IDocumentTypeIdentifier;
import com.helger.peppol.identifier.IParticipantIdentifier;
import com.helger.peppol.identifier.IProcessIdentifier;
import com.helger.peppol.identifier.doctype.IPeppolDocumentTypeIdentifierParts;
import com.helger.peppol.identifier.doctype.OpenPeppolDocumentTypeIdentifierParts;
import com.helger.peppol.identifier.doctype.PeppolDocumentTypeIdentifierParts;
import com.helger.peppol.identifier.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppol.identifier.participant.SimpleParticipantIdentifier;
import com.helger.peppol.identifier.process.SimpleProcessIdentifier;
import com.helger.peppol.utils.BusdoxURLHelper;

/**
 * This class contains several identifier related utility methods.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@ThreadSafe
public final class PeppolIdentifierHelper
{
  public static final boolean DEFAULT_CHARSET_CHECKS_DISABLED = false;
  public static final boolean DEFAULT_SCHEME_MAX_LENGTH_CHECKS_DISABLED = false;

  private static final String PATTERN_PARTICIPANT_ID = "^([^:]*):(.*)$";
  private static final String PREFIX_PARTICIPANT_IDENTIFIER_SCHEME = CPeppolIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME +
                                                                     CIdentifier.URL_SCHEME_VALUE_SEPARATOR;
  private static final String PREFIX_DOCUMENT_TYPE_IDENTIFIER_SCHEME = CPeppolIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME +
                                                                       CIdentifier.URL_SCHEME_VALUE_SEPARATOR;
  private static final String PREFIX_PROCESS_IDENTIFIER_SCHEME = CPeppolIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME +
                                                                 CIdentifier.URL_SCHEME_VALUE_SEPARATOR;

  private static final AtomicBoolean s_aCharsetChecksDisabled = new AtomicBoolean (DEFAULT_CHARSET_CHECKS_DISABLED);
  private static final AtomicBoolean s_aSchemeMaxLengthChecksDisabled = new AtomicBoolean (DEFAULT_SCHEME_MAX_LENGTH_CHECKS_DISABLED);

  static
  {
    // Check that the default participant scheme is valid
    if (!isValidParticipantIdentifierScheme (CPeppolIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME))
      throw new InitializationException ("The default participant scheme '" +
                                         CPeppolIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME +
                                         "' is not valid!");

    // Check that the default document type scheme is valid
    if (!isValidIdentifierScheme (CPeppolIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME))
      throw new InitializationException ("The default document type scheme '" +
                                         CPeppolIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME +
                                         "' is not valid!");

    // Check that the default process scheme is valid
    if (!isValidIdentifierScheme (CPeppolIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME))
      throw new InitializationException ("The default process scheme '" +
                                         CPeppolIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME +
                                         "' is not valid!");
  }

  @SuppressWarnings ("unused")
  @PresentForCodeCoverage
  private static final PeppolIdentifierHelper s_aInstance = new PeppolIdentifierHelper ();

  private PeppolIdentifierHelper ()
  {}

  /**
   * @return <code>true</code> if the charset checks for identifier values are
   *         disabled, <code>false</code> if they are enabled
   */
  public static boolean areCharsetChecksDisabled ()
  {
    return s_aCharsetChecksDisabled.get ();
  }

  /**
   * Enable or disable the charset checks. You may disable charset checks, if
   * you previously checked them for consistency. Charset checks are by default
   * enabled and check if a participant, document type and process identifier
   * value can be encoded in US-ASCII (participant) or ISO-8859-1 (document type
   * and process).
   *
   * @param bDisable
   *        if <code>true</code> all charset checks are disabled. If
   *        <code>false</code> charset checks are enabled
   */
  public static void disableCharsetChecks (final boolean bDisable)
  {
    s_aCharsetChecksDisabled.set (bDisable);
  }

  /**
   * @return <code>true</code> if the maximum length checks for identifier
   *         schemes is disabled. This is required for BDSML but must be
   *         <code>false</code> for the old SML.
   */
  public static boolean areSchemeMaxLengthChecksDisabled ()
  {
    return s_aSchemeMaxLengthChecksDisabled.get ();
  }

  /**
   * Disable the maximum length check for identifier scheme values.
   *
   * @param bDisable
   *        <code>true</code> to disable the check (for BDSML) or
   *        <code>false</code> to enabled the check (for old SML).
   */
  public static void disableSchemeMaxLengthChecks (final boolean bDisable)
  {
    s_aSchemeMaxLengthChecksDisabled.set (bDisable);
  }

  /**
   * Check if the given identifier is valid. It is valid if it has at least 1
   * character and at last 25 characters (see
   * {@link CPeppolIdentifier#MAX_IDENTIFIER_SCHEME_LENGTH}). This method
   * applies to all identifier schemes, but there is a special version for
   * participant identifier schemes, as they are used in DNS names!
   *
   * @param sScheme
   *        The scheme to check.
   * @return <code>true</code> if the passed scheme is a valid identifier
   *         scheme, <code>false</code> otherwise.
   * @see #isValidParticipantIdentifierScheme(String)
   * @see #areSchemeMaxLengthChecksDisabled()
   */
  public static boolean isValidIdentifierScheme (@Nullable final String sScheme)
  {
    if (sScheme == null)
      return false;
    final int nLength = sScheme.length ();
    if (nLength == 0)
      return false;
    if (areSchemeMaxLengthChecksDisabled ())
      return true;
    return nLength <= CPeppolIdentifier.MAX_IDENTIFIER_SCHEME_LENGTH;
  }

  /**
   * Check if the given scheme is a valid participant identifier scheme (like
   * {@link CPeppolIdentifier#DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME}). It is
   * valid if it has at least 1 character and at last 25 characters (see
   * {@link CPeppolIdentifier#MAX_IDENTIFIER_SCHEME_LENGTH}}) and matches a
   * certain regular expression (see
   * {@link CPeppolIdentifier#PARTICIPANT_IDENTIFIER_SCHEME_REGEX}). Please note
   * that the regular expression is applied case insensitive!<br>
   * This limitation is important, because the participant identifier scheme is
   * directly encoded into the SML DNS name record.
   *
   * @param sScheme
   *        The scheme to check.
   * @return <code>true</code> if the passed scheme is a valid participant
   *         identifier scheme, <code>false</code> otherwise.
   * @see #isValidIdentifierScheme(String)
   */
  public static boolean isValidParticipantIdentifierScheme (@Nullable final String sScheme)
  {
    if (!isValidIdentifierScheme (sScheme))
      return false;
    return RegExHelper.stringMatchesPattern (CPeppolIdentifier.PARTICIPANT_IDENTIFIER_SCHEME_REGEX,
                                             sScheme.toLowerCase (BusdoxURLHelper.URL_LOCALE));
  }

  /**
   * Check if the passed participant identifier value is valid. A valid
   * identifier must have at least 1 character and at last
   * {@link CPeppolIdentifier#MAX_PARTICIPANT_IDENTIFIER_VALUE_LENGTH}
   * characters. Also it must be US ASCII encoded. This check method considers
   * only the value and not the identifier scheme!
   *
   * @param sValue
   *        The participant identifier value to be checked (without the scheme).
   *        May be <code>null</code>.
   * @return <code>true</code> if the participant identifier value is valid,
   *         <code>false</code> otherwise.
   */
  public static boolean isValidParticipantIdentifierValue (@Nullable final String sValue)
  {
    final int nLength = StringHelper.getLength (sValue);
    if (nLength == 0 || nLength > CPeppolIdentifier.MAX_PARTICIPANT_IDENTIFIER_VALUE_LENGTH)
      return false;

    // Check if the value is US ASCII encoded
    return areCharsetChecksDisabled () || CCharset.CHARSET_US_ASCII_OBJ.newEncoder ().canEncode (sValue);
  }

  /**
   * Check if the passed document type identifier value is valid. A valid
   * identifier must have at least 1 character and at last
   * {@link CPeppolIdentifier#MAX_DOCUMENT_TYPE_IDENTIFIER_VALUE_LENGTH}
   * characters. Also it must be ISO-8859-1 encoded.
   *
   * @param sValue
   *        The document type identifier value to be checked (without the
   *        scheme). May be <code>null</code>.
   * @return <code>true</code> if the document type identifier value is valid,
   *         <code>false</code> otherwise
   */
  public static boolean isValidDocumentTypeIdentifierValue (@Nullable final String sValue)
  {
    final int nLength = StringHelper.getLength (sValue);
    if (nLength == 0 || nLength > CPeppolIdentifier.MAX_DOCUMENT_TYPE_IDENTIFIER_VALUE_LENGTH)
      return false;

    // Check if the value is ISO-8859-1 encoded
    return areCharsetChecksDisabled () || CCharset.CHARSET_ISO_8859_1_OBJ.newEncoder ().canEncode (sValue);
  }

  /**
   * Check if the passed process identifier value is valid. A valid identifier
   * must have at least 1 character and at last
   * {@link CPeppolIdentifier#MAX_PROCESS_IDENTIFIER_VALUE_LENGTH} characters.
   * Also it must be ISO-8859-1 encoded.
   *
   * @param sValue
   *        The process identifier value to be checked (without the scheme). May
   *        be <code>null</code>.
   * @return <code>true</code> if the process identifier value is valid,
   *         <code>false</code> otherwise
   */
  public static boolean isValidProcessIdentifierValue (@Nullable final String sValue)
  {
    final int nLength = StringHelper.getLength (sValue);
    if (nLength == 0 || nLength > CPeppolIdentifier.MAX_PROCESS_IDENTIFIER_VALUE_LENGTH)
      return false;

    // Check if the value is ISO-8859-1 encoded
    return areCharsetChecksDisabled () || CCharset.CHARSET_ISO_8859_1_OBJ.newEncoder ().canEncode (sValue);
  }

  /**
   * Check if the passed participant identifier is valid. This method checks for
   * the existence of the scheme and the value and validates both.
   *
   * @param sValue
   *        The participant identifier to be checked (including the scheme). May
   *        be <code>null</code>.
   * @return <code>true</code> if the document type identifier is valid,
   *         <code>false</code> otherwise
   */
  public static boolean isValidParticipantIdentifier (@Nullable final String sValue)
  {
    return sValue != null && createParticipantIdentifierFromURIPartOrNull (sValue) != null;
  }

  /**
   * Check if the passed document type identifier is valid. This method checks
   * for the existence of the scheme and the value and validates both.
   *
   * @param sValue
   *        The document type identifier to be checked (including the scheme).
   *        May be <code>null</code>.
   * @return <code>true</code> if the document type identifier is valid,
   *         <code>false</code> otherwise
   */
  public static boolean isValidDocumentTypeIdentifier (@Nullable final String sValue)
  {
    return sValue != null && createDocumentTypeIdentifierFromURIPartOrNull (sValue) != null;
  }

  /**
   * Check if the passed process identifier is valid. This method checks for the
   * existence of the scheme and the value and validates both.
   *
   * @param sValue
   *        The process identifier to be checked (including the scheme). May be
   *        <code>null</code>.
   * @return <code>true</code> if the process identifier is valid,
   *         <code>false</code> otherwise
   */
  public static boolean isValidProcessIdentifier (@Nullable final String sValue)
  {
    return sValue != null && createProcessIdentifierFromURIPartOrNull (sValue) != null;
  }

  /**
   * According to the specification, two participant identifiers are equal if
   * their parts are equal case insensitive.
   *
   * @param sIdentifierValue1
   *        First identifier value to compare. May be <code>null</code>.
   * @param sIdentifierValue2
   *        Second identifier value to compare. May be <code>null</code>.
   * @return <code>true</code> if the identifier values are equal,
   *         <code>false</code> otherwise. If both are <code>null</code> they
   *         are considered equal.
   */
  public static boolean areParticipantIdentifierValuesEqual (@Nullable final String sIdentifierValue1,
                                                             @Nullable final String sIdentifierValue2)
  {
    // equal case insensitive!
    return EqualsHelper.equalsIgnoreCase (sIdentifierValue1, sIdentifierValue2);
  }

  /**
   * According to the specification, two document identifiers are equal if their
   * parts are equal case sensitive.
   *
   * @param sIdentifierValue1
   *        First identifier value to compare. May be <code>null</code>.
   * @param sIdentifierValue2
   *        Second identifier value to compare. May be <code>null</code>.
   * @return <code>true</code> if the identifier values are equal,
   *         <code>false</code> otherwise. If both are <code>null</code> they
   *         are considered equal.
   */
  public static boolean areDocumentTypeIdentifierValuesEqual (@Nullable final String sIdentifierValue1,
                                                              @Nullable final String sIdentifierValue2)
  {
    // Case sensitive!
    return EqualsHelper.equals (sIdentifierValue1, sIdentifierValue2);
  }

  /**
   * According to the specification, two process identifiers are equal if their
   * parts are equal case sensitive.
   *
   * @param sIdentifierValue1
   *        First identifier value to compare. May be <code>null</code>.
   * @param sIdentifierValue2
   *        Second identifier value to compare. May be <code>null</code>.
   * @return <code>true</code> if the identifier values are equal,
   *         <code>false</code> otherwise. If both are <code>null</code> they
   *         are considered equal.
   */
  public static boolean areProcessIdentifierValuesEqual (@Nullable final String sIdentifierValue1,
                                                         @Nullable final String sIdentifierValue2)
  {
    // Case sensitive!
    return EqualsHelper.equals (sIdentifierValue1, sIdentifierValue2);
  }

  /**
   * According to the specification, two participant identifiers are equal if
   * their parts are equal case insensitive.
   *
   * @param aIdentifier1
   *        First identifier to compare. May not be null.
   * @param aIdentifier2
   *        Second identifier to compare. May not be null.
   * @return <code>true</code> if the identifiers are equal, <code>false</code>
   *         otherwise.
   */
  public static boolean areParticipantIdentifiersEqual (@Nonnull final IParticipantIdentifier aIdentifier1,
                                                        @Nonnull final IParticipantIdentifier aIdentifier2)
  {
    ValueEnforcer.notNull (aIdentifier1, "ParticipantIdentifier1");
    ValueEnforcer.notNull (aIdentifier2, "ParticipantIdentifier2");

    // Identifiers are equal, if both scheme and value match case insensitive!
    return EqualsHelper.equalsIgnoreCase (aIdentifier1.getScheme (), aIdentifier2.getScheme ()) &&
           areParticipantIdentifierValuesEqual (aIdentifier1.getValue (), aIdentifier2.getValue ());
  }

  /**
   * According to the specification, two document identifiers are equal if their
   * parts are equal case sensitive.
   *
   * @param aIdentifier1
   *        First identifier to compare. May not be null.
   * @param aIdentifier2
   *        Second identifier to compare. May not be null.
   * @return <code>true</code> if the identifiers are equal, <code>false</code>
   *         otherwise.
   */
  public static boolean areDocumentTypeIdentifiersEqual (@Nonnull final IDocumentTypeIdentifier aIdentifier1,
                                                         @Nonnull final IDocumentTypeIdentifier aIdentifier2)
  {
    ValueEnforcer.notNull (aIdentifier1, "DocumentTypeIdentifier1");
    ValueEnforcer.notNull (aIdentifier2, "DocumentTypeIdentifier2");

    // Identifiers are equal, if both scheme and value match case sensitive!
    return EqualsHelper.equals (aIdentifier1.getScheme (), aIdentifier2.getScheme ()) &&
           areDocumentTypeIdentifierValuesEqual (aIdentifier1.getValue (), aIdentifier2.getValue ());
  }

  /**
   * According to the specification, two process identifiers are equal if their
   * parts are equal case sensitive.
   *
   * @param aIdentifier1
   *        First identifier to compare. May not be null.
   * @param aIdentifier2
   *        Second identifier to compare. May not be null.
   * @return <code>true</code> if the identifiers are equal, <code>false</code>
   *         otherwise.
   */
  public static boolean areProcessIdentifiersEqual (@Nonnull final IProcessIdentifier aIdentifier1,
                                                    @Nonnull final IProcessIdentifier aIdentifier2)
  {
    ValueEnforcer.notNull (aIdentifier1, "ProcessIdentifier1");
    ValueEnforcer.notNull (aIdentifier2, "ProcessIdentifier2");

    // Identifiers are equal, if both scheme and value match case sensitive!
    return EqualsHelper.equals (aIdentifier1.getScheme (), aIdentifier2.getScheme ()) &&
           areProcessIdentifierValuesEqual (aIdentifier1.getValue (), aIdentifier2.getValue ());
  }

  /**
   * According to the specification, two participant identifiers are equal if
   * their parts are equal case insensitive.
   *
   * @param aIdentifier1
   *        First identifier to compare. May not be null.
   * @param aIdentifier2
   *        Second identifier to compare. May not be null.
   * @return 0 if the identifiers are equal, -1 or +1 otherwise.
   */
  public static int compareParticipantIdentifiers (@Nonnull final IParticipantIdentifier aIdentifier1,
                                                   @Nonnull final IParticipantIdentifier aIdentifier2)
  {
    ValueEnforcer.notNull (aIdentifier1, "ParticipantIdentifier1");
    ValueEnforcer.notNull (aIdentifier2, "ParticipantIdentifier2");

    // Compare case insensitive
    int ret = CompareHelper.compareIgnoreCase (aIdentifier1.getScheme (), aIdentifier2.getScheme ());
    if (ret == 0)
      ret = CompareHelper.compareIgnoreCase (aIdentifier1.getValue (), aIdentifier2.getValue ());
    return ret;
  }

  /**
   * According to the specification, two document identifiers are equal if their
   * parts are equal case sensitive.
   *
   * @param aIdentifier1
   *        First identifier to compare. May not be null.
   * @param aIdentifier2
   *        Second identifier to compare. May not be null.
   * @return 0 if the identifiers are equal, -1 or +1 otherwise.
   */
  public static int compareDocumentTypeIdentifiers (@Nonnull final IDocumentTypeIdentifier aIdentifier1,
                                                    @Nonnull final IDocumentTypeIdentifier aIdentifier2)
  {
    ValueEnforcer.notNull (aIdentifier1, "DocumentTypeIdentifier1");
    ValueEnforcer.notNull (aIdentifier2, "DocumentTypeIdentifier2");

    // Compare case sensitive
    int ret = CompareHelper.compare (aIdentifier1.getScheme (), aIdentifier2.getScheme ());
    if (ret == 0)
      ret = CompareHelper.compare (aIdentifier1.getValue (), aIdentifier2.getValue ());
    return ret;
  }

  /**
   * According to the specification, two process identifiers are equal if their
   * parts are equal case sensitive.
   *
   * @param aIdentifier1
   *        First identifier to compare. May not be null.
   * @param aIdentifier2
   *        Second identifier to compare. May not be null.
   * @return 0 if the identifiers are equal, -1 or +1 otherwise.
   */
  public static int compareProcessIdentifiers (@Nonnull final IProcessIdentifier aIdentifier1,
                                               @Nonnull final IProcessIdentifier aIdentifier2)
  {
    ValueEnforcer.notNull (aIdentifier1, "ProcessIdentifier1");
    ValueEnforcer.notNull (aIdentifier2, "ProcessIdentifier2");

    // Compare case sensitive
    int ret = CompareHelper.compare (aIdentifier1.getScheme (), aIdentifier2.getScheme ());
    if (ret == 0)
      ret = CompareHelper.compare (aIdentifier1.getValue (), aIdentifier2.getValue ());
    return ret;
  }

  /**
   * Check if the passed participant identifier is using the default scheme.
   *
   * @param aIdentifier
   *        The identifier to be checked. May not be <code>null</code>.
   * @return <code>true</code> if the passed identifier uses the default scheme,
   *         <code>false</code> otherwise
   */
  public static boolean hasDefaultParticipantIdentifierScheme (@Nonnull final IParticipantIdentifier aIdentifier)
  {
    ValueEnforcer.notNull (aIdentifier, "ParticipantIdentifier");

    return CPeppolIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME.equals (aIdentifier.getScheme ());
  }

  /**
   * Check if the passed participant identifier is using the default scheme.
   *
   * @param sIdentifier
   *        The identifier to be checked. May be <code>null</code>.
   * @return <code>true</code> if the passed identifier uses the default scheme,
   *         <code>false</code> otherwise
   */
  public static boolean hasDefaultParticipantIdentifierScheme (@Nullable final String sIdentifier)
  {
    return StringHelper.startsWith (sIdentifier, PREFIX_PARTICIPANT_IDENTIFIER_SCHEME);
  }

  /**
   * Check if the passed document type identifier is using the default scheme.
   *
   * @param aIdentifier
   *        The identifier to be checked. May not be <code>null</code>.
   * @return <code>true</code> if the passed identifier uses the default scheme,
   *         <code>false</code> otherwise
   */
  public static boolean hasDefaultDocumentTypeIdentifierScheme (@Nonnull final IDocumentTypeIdentifier aIdentifier)
  {
    ValueEnforcer.notNull (aIdentifier, "DocumentTypeIdentifier");

    return CPeppolIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME.equals (aIdentifier.getScheme ());
  }

  /**
   * Check if the passed document type identifier is using the default scheme.
   *
   * @param sIdentifier
   *        The identifier to be checked. May be <code>null</code>.
   * @return <code>true</code> if the passed identifier uses the default scheme,
   *         <code>false</code> otherwise
   */
  public static boolean hasDefaultDocumentTypeIdentifierScheme (@Nullable final String sIdentifier)
  {
    return StringHelper.startsWith (sIdentifier, PREFIX_DOCUMENT_TYPE_IDENTIFIER_SCHEME);
  }

  /**
   * Check if the passed process identifier is using the default scheme.
   *
   * @param aIdentifier
   *        The identifier to be checked. May not be <code>null</code>.
   * @return <code>true</code> if the passed identifier uses the default scheme,
   *         <code>false</code> otherwise
   */
  public static boolean hasDefaultProcessIdentifierScheme (@Nonnull final IProcessIdentifier aIdentifier)
  {
    ValueEnforcer.notNull (aIdentifier, "ProcessIdentifier");

    return CPeppolIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME.equals (aIdentifier.getScheme ());
  }

  /**
   * Check if the passed process identifier is using the default scheme.
   *
   * @param sIdentifier
   *        The identifier to be checked. May be <code>null</code>.
   * @return <code>true</code> if the passed identifier uses the default scheme,
   *         <code>false</code> otherwise
   */
  public static boolean hasDefaultProcessIdentifierScheme (@Nullable final String sIdentifier)
  {
    return StringHelper.startsWith (sIdentifier, PREFIX_PROCESS_IDENTIFIER_SCHEME);
  }

  /**
   * Take the passed identifier scheme and value try to convert it back to a
   * document identifier. If the passed scheme is invalid or if the passed value
   * is invalid, <code>null</code> is returned.
   *
   * @param sScheme
   *        The identifier scheme. May be <code>null</code> in which case
   *        <code>null</code> is returned.
   * @param sValue
   *        The identifier value. May be <code>null</code> in which case
   *        <code>null</code> is returned.
   * @return The document type identifier or <code>null</code> if any of the
   *         parts is invalid.
   * @see #isValidIdentifierScheme(String)
   * @see #isValidDocumentTypeIdentifierValue(String)
   */
  @Nullable
  public static SimpleDocumentTypeIdentifier createDocumentTypeIdentifierOrNull (@Nullable final String sScheme,
                                                                                 @Nullable final String sValue)
  {
    if (isValidIdentifierScheme (sScheme) && isValidDocumentTypeIdentifierValue (sValue))
      return new SimpleDocumentTypeIdentifier (sScheme, sValue);
    return null;
  }

  /**
   * Take the passed URI part and try to convert it back to a document
   * identifier. The URI part must have the layout <code>scheme::value</code>.
   * This method returns only valid document type identifier schemes and
   * document type identifier values.
   *
   * @param sURIPart
   *        The URI part to be scanned. May not be <code>null</code> if a
   *        correct result is expected.
   * @return The document type identifier matching the passed URI part or
   *         <code>null</code> if this string is in an illegal format.
   */
  @Nullable
  public static SimpleDocumentTypeIdentifier createDocumentTypeIdentifierFromURIPartOrNull (@Nonnull final String sURIPart)
  {
    ValueEnforcer.notNull (sURIPart, "URIPart");

    // This is quicker than splitting with RegEx!
    final ICommonsList <String> aSplitted = StringHelper.getExploded (CIdentifier.URL_SCHEME_VALUE_SEPARATOR,
                                                                      sURIPart,
                                                                      2);
    if (aSplitted.size () != 2)
      return null;

    return createDocumentTypeIdentifierOrNull (aSplitted.get (0), aSplitted.get (1));
  }

  /**
   * Take the passed URI part and try to convert it back to a document
   * identifier. The URI part must have the layout <code>scheme::value</code>
   *
   * @param sURIPart
   *        The URI part to be scanned. May not be <code>null</code> if a
   *        correct result is expected.
   * @return The document type identifier matching the passed URI part. Never
   *         <code>null</code>.
   * @throws IllegalArgumentException
   *         If the passed identifier is not a valid URI encoded identifier
   * @see #createDocumentTypeIdentifierFromURIPartOrNull(String)
   */
  @Nonnull
  public static SimpleDocumentTypeIdentifier createDocumentTypeIdentifierFromURIPart (@Nonnull final String sURIPart)
  {
    final SimpleDocumentTypeIdentifier ret = createDocumentTypeIdentifierFromURIPartOrNull (sURIPart);
    if (ret == null)
      throw new IllegalArgumentException ("Document type identifier '" +
                                          sURIPart +
                                          "' did not include correct delimiter: " +
                                          CIdentifier.URL_SCHEME_VALUE_SEPARATOR);
    return ret;
  }

  /**
   * Take the passed identifier scheme and value try to convert it back to a
   * document identifier. If the passed scheme is invalid or if the passed value
   * is invalid, <code>null</code> is returned.
   *
   * @param sScheme
   *        The identifier scheme. May be <code>null</code> in which case
   *        <code>null</code> is returned.
   * @param sValue
   *        The identifier value. May be <code>null</code> in which case
   *        <code>null</code> is returned.
   * @return The participant identifier or <code>null</code> if any of the parts
   *         is invalid.
   * @see #isValidParticipantIdentifierScheme(String)
   * @see #isValidParticipantIdentifierValue(String)
   */
  @Nullable
  public static SimpleParticipantIdentifier createParticipantIdentifierOrNull (@Nullable final String sScheme,
                                                                               @Nullable final String sValue)
  {
    if (isValidParticipantIdentifierScheme (sScheme) && isValidParticipantIdentifierValue (sValue))
      return new SimpleParticipantIdentifier (sScheme, sValue);
    return null;
  }

  /**
   * Take the passed URI part and try to convert it back to a participant
   * identifier. The URI part must have the layout <code>scheme::value</code>.
   * This method accepts only valid participant identifier schemes and valid
   * participant identifier values.
   *
   * @param sURIPart
   *        The URI part to be scanned. May not be <code>null</code> if a
   *        correct result is expected.
   * @return The participant identifier matching the passed URI part or
   *         <code>null</code> if this string is in an illegal format.
   */
  @Nullable
  public static SimpleParticipantIdentifier createParticipantIdentifierFromURIPartOrNull (@Nonnull final String sURIPart)
  {
    ValueEnforcer.notNull (sURIPart, "URIPart");

    // This is quicker than splitting with RegEx!
    final ICommonsList <String> aSplitted = StringHelper.getExploded (CIdentifier.URL_SCHEME_VALUE_SEPARATOR,
                                                                      sURIPart,
                                                                      2);
    if (aSplitted.size () != 2)
      return null;

    return createParticipantIdentifierOrNull (aSplitted.get (0), aSplitted.get (1));
  }

  /**
   * Take the passed URI part and try to convert it back to a participant
   * identifier. The URI part must have the layout <code>scheme::value</code>
   *
   * @param sURIPart
   *        The URI part to be scanned. May not be <code>null</code> if a
   *        correct result is expected.
   * @return The participant identifier matching the passed URI part
   * @throws IllegalArgumentException
   *         If the passed identifier is not a valid URI encoded identifier
   * @see #createParticipantIdentifierFromURIPartOrNull(String)
   */
  @Nonnull
  public static SimpleParticipantIdentifier createParticipantIdentifierFromURIPart (@Nonnull final String sURIPart)
  {
    final SimpleParticipantIdentifier ret = createParticipantIdentifierFromURIPartOrNull (sURIPart);
    if (ret == null)
      throw new IllegalArgumentException ("Participant identifier '" +
                                          sURIPart +
                                          "' did not include correct delimiter: " +
                                          CIdentifier.URL_SCHEME_VALUE_SEPARATOR);

    return ret;
  }

  /**
   * Take the passed identifier scheme and value try to convert it back to a
   * document identifier. If the passed scheme is invalid or if the passed value
   * is invalid, <code>null</code> is returned.
   *
   * @param sScheme
   *        The identifier scheme. May be <code>null</code> in which case
   *        <code>null</code> is returned.
   * @param sValue
   *        The identifier value. May be <code>null</code> in which case
   *        <code>null</code> is returned.
   * @return The process identifier or <code>null</code> if any of the parts is
   *         invalid.
   * @see #isValidIdentifierScheme(String)
   * @see #isValidProcessIdentifierValue(String)
   */
  @Nullable
  public static SimpleProcessIdentifier createProcessIdentifierOrNull (@Nullable final String sScheme,
                                                                       @Nullable final String sValue)
  {
    if (isValidIdentifierScheme (sScheme) && isValidProcessIdentifierValue (sValue))
      return new SimpleProcessIdentifier (sScheme, sValue);
    return null;
  }

  /**
   * Take the passed URI part and try to convert it back to a process
   * identifier. The URI part must have the layout <code>scheme::value</code>.
   * This value returns only if a valid process identifier scheme and process
   * identifier value is used.
   *
   * @param sURIPart
   *        The URI part to be scanned. May not be <code>null</code> if a
   *        correct result is expected.
   * @return The process identifier matching the passed URI part or
   *         <code>null</code> if this string is in an illegal format.
   */
  @Nullable
  public static SimpleProcessIdentifier createProcessIdentifierFromURIPartOrNull (@Nonnull final String sURIPart)
  {
    ValueEnforcer.notNull (sURIPart, "URIPart");

    // This is quicker than splitting with RegEx!
    final ICommonsList <String> aSplitted = StringHelper.getExploded (CIdentifier.URL_SCHEME_VALUE_SEPARATOR,
                                                                      sURIPart,
                                                                      2);
    if (aSplitted.size () != 2)
      return null;

    return createProcessIdentifierOrNull (aSplitted.get (0), aSplitted.get (1));
  }

  /**
   * Take the passed URI part and try to convert it back to a process
   * identifier. The URI part must have the layout <code>scheme::value</code>
   *
   * @param sURIPart
   *        The URI part to be scanned. May not be <code>null</code> if a
   *        correct result is expected.
   * @return The process identifier matching the passed URI part
   * @throws IllegalArgumentException
   *         If the passed identifier is not a valid URI encoded identifier
   * @see #createProcessIdentifierFromURIPartOrNull(String)
   */
  @Nonnull
  public static SimpleProcessIdentifier createProcessIdentifierFromURIPart (@Nonnull final String sURIPart)
  {
    final SimpleProcessIdentifier ret = createProcessIdentifierFromURIPartOrNull (sURIPart);
    if (ret == null)
      throw new IllegalArgumentException ("Process identifier '" +
                                          sURIPart +
                                          "' did not include correct delimiter: " +
                                          CIdentifier.URL_SCHEME_VALUE_SEPARATOR);

    return ret;
  }

  /**
   * Extract the issuing agency ID from the passed participant identifier value.
   * <br>
   * Example: extract the <code>0088</code> from the participant identifier
   * <code>iso6523-actorid-upis::0088:123456</code>.<br>
   * Note: this only works for participant identifiers that are using the
   * default scheme (iso6523-actorid-upis) because for the other schemes, I just
   * can't tell!
   *
   * @param aIdentifier
   *        The participant identifier to extract the value from. May not be
   *        <code>null</code>.
   * @return <code>null</code> if the identifier is not of default scheme or if
   *         the identifier is malformed.
   */
  @Nullable
  public static String getIssuingAgencyIDFromParticipantIDValue (@Nonnull final IPeppolParticipantIdentifier aIdentifier)
  {
    ValueEnforcer.notNull (aIdentifier, "Identifier");

    if (!hasDefaultParticipantIdentifierScheme (aIdentifier))
      return null;
    return ArrayHelper.getSafeElement (RegExHelper.getAllMatchingGroupValues (PATTERN_PARTICIPANT_ID,
                                                                              aIdentifier.getValue ()),
                                       0);
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
   * @param aIdentifier
   *        The participant identifier to extract the value from. May not be
   *        <code>null</code>.
   * @return <code>null</code> if the identifier is not of default scheme or if
   *         the identifier is malformed.
   */
  @Nullable
  public static String getLocalParticipantIDFromParticipantIDValue (@Nonnull final IPeppolParticipantIdentifier aIdentifier)
  {
    ValueEnforcer.notNull (aIdentifier, "Identifier");

    if (!hasDefaultParticipantIdentifierScheme (aIdentifier))
      return null;
    return ArrayHelper.getSafeElement (RegExHelper.getAllMatchingGroupValues (PATTERN_PARTICIPANT_ID,
                                                                              aIdentifier.getValue ()),
                                       1);
  }

  /**
   * Convert the passed document type identifier into its parts. First the old
   * PEPPOL scheme for identifiers is tried, and afterwards the OpenPEPPOL
   * scheme for document type identifiers is used.
   *
   * @param aIdentifier
   *        The document type identifier to be split. May not be
   *        <code>null</code>.
   * @return Never <code>null</code>.
   * @throws IllegalArgumentException
   *         If the passed document type identifier is neither a PEPPOL nor an
   *         OpenPEPPOL document type identifier.
   */
  @Nonnull
  public static IPeppolDocumentTypeIdentifierParts getDocumentTypeIdentifierParts (@Nonnull final IDocumentTypeIdentifier aIdentifier)
  {
    ValueEnforcer.notNull (aIdentifier, "Identifier");

    try
    {
      return PeppolDocumentTypeIdentifierParts.extractFromString (aIdentifier.getValue ());
    }
    catch (final IllegalArgumentException ex)
    {
      // Not PEPPOL - try OpenPEPPOL
      return OpenPeppolDocumentTypeIdentifierParts.extractFromString (aIdentifier.getValue ());
    }
  }
}
