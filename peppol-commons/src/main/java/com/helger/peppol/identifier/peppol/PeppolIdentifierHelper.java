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
import com.helger.commons.collection.ext.ICommonsList;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.CIdentifier;
import com.helger.peppol.identifier.generic.doctype.IDocumentTypeIdentifier;
import com.helger.peppol.identifier.generic.process.IProcessIdentifier;
import com.helger.peppol.identifier.peppol.doctype.PeppolDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.doctype.part.IPeppolDocumentTypeIdentifierParts;
import com.helger.peppol.identifier.peppol.doctype.part.OpenPeppolDocumentTypeIdentifierParts;
import com.helger.peppol.identifier.peppol.doctype.part.PeppolDocumentTypeIdentifierParts;

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

  private static final AtomicBoolean s_aCharsetChecksDisabled = new AtomicBoolean (DEFAULT_CHARSET_CHECKS_DISABLED);
  private static final AtomicBoolean s_aSchemeMaxLengthChecksDisabled = new AtomicBoolean (DEFAULT_SCHEME_MAX_LENGTH_CHECKS_DISABLED);

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
  public static PeppolDocumentTypeIdentifier createDocumentTypeIdentifierOrNull (@Nullable final String sScheme,
                                                                                 @Nullable final String sValue)
  {
    if (isValidIdentifierScheme (sScheme) && isValidDocumentTypeIdentifierValue (sValue))
      return new PeppolDocumentTypeIdentifier (sScheme, sValue);
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
  public static PeppolDocumentTypeIdentifier createDocumentTypeIdentifierFromURIPartOrNull (@Nonnull final String sURIPart)
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
  public static PeppolDocumentTypeIdentifier createDocumentTypeIdentifierFromURIPart (@Nonnull final String sURIPart)
  {
    final PeppolDocumentTypeIdentifier ret = createDocumentTypeIdentifierFromURIPartOrNull (sURIPart);
    if (ret == null)
      throw new IllegalArgumentException ("Document type identifier '" +
                                          sURIPart +
                                          "' did not include correct delimiter: " +
                                          CIdentifier.URL_SCHEME_VALUE_SEPARATOR);
    return ret;
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
