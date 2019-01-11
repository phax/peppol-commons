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
package com.helger.peppol.identifier.peppol;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.peppol.identifier.generic.doctype.IDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.doctype.IPeppolDocumentTypeIdentifierParts;
import com.helger.peppol.identifier.peppol.doctype.PeppolDocumentTypeIdentifierParts;

/**
 * This class contains several identifier related utility methods.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@ThreadSafe
public final class PeppolIdentifierHelper
{
  /**
   * The maximum length of an identifier scheme. This applies to all identifier
   * schemes (participant, document type and process).
   */
  public static final int MAX_IDENTIFIER_SCHEME_LENGTH = 25;

  /**
   * The identifier prefix for DNS name creation.
   */
  public static final String DNS_HASHED_IDENTIFIER_PREFIX = "B-";

  public static final boolean DEFAULT_CHARSET_CHECKS_DISABLED = false;
  public static final boolean DEFAULT_SCHEME_MAX_LENGTH_CHECKS_DISABLED = false;

  private static final AtomicBoolean s_aCharsetChecksDisabled = new AtomicBoolean (DEFAULT_CHARSET_CHECKS_DISABLED);
  private static final AtomicBoolean s_aSchemeMaxLengthChecksDisabled = new AtomicBoolean (DEFAULT_SCHEME_MAX_LENGTH_CHECKS_DISABLED);

  /**
   * The default document identifier scheme.<br>
   * See PEPPOL Common definitions chapter 3.5
   */
  public static final String DEFAULT_DOCUMENT_TYPE_SCHEME = "busdox-docid-qns";

  /**
   * Document Type identifier value maximum length (excluding the scheme)
   */
  public static final int MAX_DOCUEMNT_TYPE_VALUE_LENGTH = 500;

  /**
   * The default identifier scheme ID to be used for participants/businesses.
   * <br>
   * The matching values have the format "agency:id" whereas agency should be
   * within the code-list.<br>
   * Please note that this is a change to the PEPPOL Common definitions chapter
   * 3.4! <br>
   * See also
   * com.helger.peppol.identifier.issuingagency.IdentifierIssuingAgencyManager
   */
  public static final String DEFAULT_PARTICIPANT_SCHEME = "iso6523-actorid-upis";

  /**
   * Participant identifier value maximum length (excluding the scheme)
   */
  public static final int MAX_PARTICIPANT_VALUE_LENGTH = 50;

  /**
   * The default process identifier scheme.<br>
   * Overrides PEPPOL Common definitions chapter 3.6!
   */
  public static final String DEFAULT_PROCESS_SCHEME = "cenbii-procid-ubl";

  /**
   * Process identifier value maximum length (excluding the scheme)
   */
  public static final int MAX_PROCESS_VALUE_LENGTH = 200;

  private PeppolIdentifierHelper ()
  {}

  @PresentForCodeCoverage
  private static final PeppolIdentifierHelper s_aInstance = new PeppolIdentifierHelper ();

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
   * {@link #MAX_IDENTIFIER_SCHEME_LENGTH}). This method applies to all
   * identifier schemes, but there is a special version for participant
   * identifier schemes, as they are used in DNS names!
   *
   * @param sScheme
   *        The scheme to check.
   * @return <code>true</code> if the passed scheme is a valid identifier
   *         scheme, <code>false</code> otherwise.
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
    return nLength <= MAX_IDENTIFIER_SCHEME_LENGTH;
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
   *         If the passed document type identifier is not a PEPPOL document
   *         type identifier.
   */
  @Nullable
  public static IPeppolDocumentTypeIdentifierParts getDocumentTypeIdentifierParts (@Nonnull final IDocumentTypeIdentifier aIdentifier)
  {
    ValueEnforcer.notNull (aIdentifier, "Identifier");

    return PeppolDocumentTypeIdentifierParts.extractFromString (aIdentifier.getValue ());
  }
}
