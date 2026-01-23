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
package com.helger.peppolid.peppol;

import java.util.concurrent.atomic.AtomicBoolean;

import org.jspecify.annotations.Nullable;

import com.helger.annotation.concurrent.ThreadSafe;
import com.helger.annotation.style.PresentForCodeCoverage;
import com.helger.peppolid.CIdentifier;

/**
 * This class contains several identifier related utility methods.
 *
 * @author Philip Helger
 */
@ThreadSafe
public final class PeppolIdentifierHelper
{
  /**
   * The maximum length of an identifier scheme. This applies to all identifier schemes
   * (participant, document type and process).
   */
  public static final int MAX_IDENTIFIER_SCHEME_LENGTH = 25;

  @Deprecated (forRemoval = true, since = "12.3.7")
  public static final boolean DEFAULT_CHARSET_CHECKS_DISABLED = false;

  /**
   * The regular expression to be used for validating participant identifier schemes (not values!).
   * See BusDox specification 1.0.1, chapter 2.3
   */
  public static final String PARTICIPANT_IDENTIFIER_SCHEME_REGEX = "[a-z0-9]+-[a-z0-9]+-[a-z0-9]+";

  @Deprecated (forRemoval = true, since = "12.3.7")
  private static final AtomicBoolean CHARSET_CHECKS_DISABLED = new AtomicBoolean (DEFAULT_CHARSET_CHECKS_DISABLED);

  /**
   * The default document identifier scheme.<br>
   * See Peppol Common definitions chapter 3.5
   *
   * @since 8.3.1
   */
  public static final String DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS = "busdox-docid-qns";

  /**
   * The new "Wildcard" document type identifier scheme, introduced in the Peppol Policy for use of
   * Identifiers 4.2.0.
   *
   * @since 8.3.1
   */
  public static final String DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD = "peppol-doctype-wildcard";

  /**
   * Document Type identifier value maximum length (excluding the scheme)
   */
  public static final int MAX_DOCUMENT_TYPE_VALUE_LENGTH = 500;

  /**
   * The default identifier scheme ID to be used for participants/businesses. <br>
   * The matching values have the format "agency:id" whereas agency should be within the code-list.
   *
   * @since 8.3.1
   */
  public static final String PARTICIPANT_SCHEME_ISO6523_ACTORID_UPIS = "iso6523-actorid-upis";

  /**
   * The default identifier scheme ID to be used for participants/businesses.
   */
  public static final String DEFAULT_PARTICIPANT_SCHEME = PARTICIPANT_SCHEME_ISO6523_ACTORID_UPIS;

  /**
   * Participant identifier value maximum length (excluding the scheme).<br>
   * PFUOI 4.3: 50 characters<br>
   * PFUOI 4.4: 4 + ":" + 130 characters
   */
  public static final int MAX_PARTICIPANT_VALUE_LENGTH = 4 + 1 + 130;

  /**
   * The default process identifier scheme.<br>
   * Overrides Peppol Common definitions chapter 3.6!
   *
   * @since 8.3.1
   */
  public static final String PROCESS_SCHEME_CENBII_PROCID_UBL = "cenbii-procid-ubl";

  /**
   * The default process identifier scheme.<br>
   * Overrides Peppol Common definitions chapter 3.6!
   */
  public static final String DEFAULT_PROCESS_SCHEME = PROCESS_SCHEME_CENBII_PROCID_UBL;

  /**
   * Process identifier value maximum length (excluding the scheme)
   */
  public static final int MAX_PROCESS_VALUE_LENGTH = 200;

  /**
   * The Wildcard (DDTS) Customization separator
   *
   * @since 8.8.1
   */
  public static final char DOCUMENT_TYPE_WILDCARD_PART_SEPARATOR = '@';
  /**
   * The Wildcard (DDTS) separator
   *
   * @since 8.8.1
   */
  public static final char DOCUMENT_TYPE_WILDCARD_INDICATOR = '*';

  private PeppolIdentifierHelper ()
  {}

  @PresentForCodeCoverage
  private static final PeppolIdentifierHelper INSTANCE = new PeppolIdentifierHelper ();

  /**
   * @return <code>true</code> if the charset checks for identifier values are disabled,
   *         <code>false</code> if they are enabled
   */
  @Deprecated (forRemoval = true, since = "12.3.7")
  public static boolean areCharsetChecksDisabled ()
  {
    return CHARSET_CHECKS_DISABLED.get ();
  }

  /**
   * Enable or disable the charset checks. You may disable charset checks, if you previously checked
   * them for consistency. Charset checks are by default enabled and check if a participant,
   * document type and process identifier value can be encoded in US-ASCII (participant) or
   * ISO-8859-1 (document type and process).
   *
   * @param bDisable
   *        if <code>true</code> all charset checks are disabled. If <code>false</code> charset
   *        checks are enabled
   */
  @Deprecated (forRemoval = true, since = "12.3.7")
  public static void disableCharsetChecks (final boolean bDisable)
  {
    CHARSET_CHECKS_DISABLED.set (bDisable);
  }

  private static boolean _isForbiddenSchemeIDChar (final char c)
  {
    // Disallow all whitespace chars
    return c == 9 || c == 10 || c == 11 || c == 12 || c == 13 || c == ' ' || c == 133 || c == 160;
  }

  /**
   * Check if the given identifier is valid. It is valid if it has at least 1 character and at last
   * 25 characters (see {@link #MAX_IDENTIFIER_SCHEME_LENGTH}). This method applies to all
   * identifier schemes, but there is a special version for participant identifier schemes, as they
   * are used in DNS names!
   *
   * @param sScheme
   *        The scheme to check.
   * @return <code>true</code> if the passed scheme is a valid identifier scheme, <code>false</code>
   *         otherwise.
   */
  public static boolean isValidIdentifierScheme (@Nullable final String sScheme)
  {
    if (sScheme == null)
      return false;

    final int nLength = sScheme.length ();
    if (nLength == 0)
      return false;

    // The separator may not be used inside the scheme (#27)
    if (sScheme.indexOf (CIdentifier.URL_SCHEME_VALUE_SEPARATOR) >= 0)
      return false;

    for (final char c : sScheme.toCharArray ())
      if (_isForbiddenSchemeIDChar (c))
        return false;

    if (nLength > MAX_IDENTIFIER_SCHEME_LENGTH)
      return false;

    return true;
  }
}
