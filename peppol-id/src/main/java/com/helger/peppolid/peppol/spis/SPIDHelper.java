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
package com.helger.peppolid.peppol.spis;

import org.jspecify.annotations.Nullable;

import com.helger.annotation.concurrent.Immutable;
import com.helger.cache.regex.RegExHelper;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.peppolid.peppol.pidscheme.EPredefinedParticipantIdentifierScheme;

/**
 * Helper class for dealing with Peppol Service Provider IDs.
 *
 * @author Philip Helger
 * @since 12.3.6
 */
@Immutable
public final class SPIDHelper
{
  /** The official Participant Identifier Scheme for SPIS (0242) */
  public static final String SPIS_PARTICIPANT_ID_SCHEME = EPredefinedParticipantIdentifierScheme.SPIS.getISO6523Code ();

  public static final int LEN_MAIN_ID = 6;
  private static final String _MAIN_ID = "[0-9]{" + LEN_MAIN_ID + "}";
  public static final String REGEX_MAIN_ID = "^" + _MAIN_ID + "$";

  private static final String _USE_CASE_ID = "[0-9A-Z_]{3,12}";
  public static final String REGEX_USE_CASE_ID = "^(?i)" + _USE_CASE_ID + "$";

  private static final String _SERVICE_PROVIDER_SUFFIX = "[0-9A-Z\\-\\._~]{3,24}";
  public static final String REGEX_SERVICE_PROVIDER_SUFFIX = "^(?i)" + _SERVICE_PROVIDER_SUFFIX + "$";

  public static final String REGEX_COMPLETE = "^(?i)" +
                                              _MAIN_ID +
                                              "(-" +
                                              _USE_CASE_ID +
                                              "(\\." +
                                              _SERVICE_PROVIDER_SUFFIX +
                                              ")?)?$";

  private SPIDHelper ()
  {}

  public static boolean isValidMainID (@Nullable final String s)
  {
    return s != null && s.length () == LEN_MAIN_ID && RegExHelper.stringMatchesPattern (REGEX_MAIN_ID, s);
  }

  public static boolean isValidUseCaseID (@Nullable final String s)
  {
    return s != null &&
      s.length () >= 3 &&
      s.length () <= 12 &&
      RegExHelper.stringMatchesPattern (REGEX_USE_CASE_ID, s);
  }

  public static boolean isValidServiceProviderSuffix (@Nullable final String s)
  {
    return s != null &&
      s.length () >= 3 &&
      s.length () <= 24 &&
      RegExHelper.stringMatchesPattern (REGEX_SERVICE_PROVIDER_SUFFIX, s);
  }

  public static boolean isValidSPID (@Nullable final String s)
  {
    return s != null &&
      s.length () >= LEN_MAIN_ID &&
      s.length () <= 44 &&
      RegExHelper.stringMatchesPattern (REGEX_COMPLETE, s);
  }

  /**
   * Extract the Main ID from an SPID. The Main ID are the leading 6 digits of a valid SPID (before
   * an optional Use Case ID and Service Provider suffix).
   *
   * @param sSPID
   *        The SPID to extract the Main ID from. May be <code>null</code>.
   * @return <code>null</code> if the provided value is not a valid SPID (see
   *         {@link #isValidSPID(String)}), the 6-digit Main ID otherwise.
   * @since 12.6.1
   */
  @Nullable
  public static String getMainID (@Nullable final String sSPID)
  {
    if (!isValidSPID (sSPID))
      return null;
    return sSPID.substring (0, LEN_MAIN_ID);
  }

  /**
   * Extract the Use Case ID from an SPID. An SPID has the format
   * <code>MainID[-UseCaseID[.ServiceProviderSuffix]]</code>, so the Use Case ID is the part between
   * the first <code>-</code> and the first following <code>.</code> (or the end of the SPID).
   *
   * @param sSPID
   *        The SPID to extract the Use Case ID from. May be <code>null</code>.
   * @return <code>null</code> if the provided value is not a valid SPID (see
   *         {@link #isValidSPID(String)}) or if it consists of the Main ID only, the Use Case ID
   *         otherwise.
   * @since 12.6.1
   */
  @Nullable
  public static String getUseCaseID (@Nullable final String sSPID)
  {
    if (!isValidSPID (sSPID))
      return null;
    // The Main ID (6 digits) and the Use Case ID contain neither '-' nor '.', so the first '-'
    // separates the Main ID from the Use Case ID and the next '.' starts the suffix
    final int nDash = sSPID.indexOf ('-');
    if (nDash < 0)
      return null;
    final int nDot = sSPID.indexOf ('.', nDash + 1);
    return nDot < 0 ? sSPID.substring (nDash + 1) : sSPID.substring (nDash + 1, nDot);
  }

  /**
   * Extract the Service Provider suffix from an SPID. An SPID has the format
   * <code>MainID[-UseCaseID[.ServiceProviderSuffix]]</code>, so the suffix is everything after the
   * first <code>.</code>. Note that the suffix itself may contain further <code>.</code>
   * characters.
   *
   * @param sSPID
   *        The SPID to extract the Service Provider suffix from. May be <code>null</code>.
   * @return <code>null</code> if the provided value is not a valid SPID (see
   *         {@link #isValidSPID(String)}) or if it carries no suffix, the Service Provider suffix
   *         otherwise.
   * @since 12.6.1
   */
  @Nullable
  public static String getServiceProviderSuffix (@Nullable final String sSPID)
  {
    if (!isValidSPID (sSPID))
      return null;
    // The Main ID and the Use Case ID contain no '.', so the first '.' starts the suffix
    final int nDot = sSPID.indexOf ('.');
    return nDot < 0 ? null : sSPID.substring (nDot + 1);
  }

  /**
   * Extract the SPID Main ID from a Peppol Seat ID. A Seat ID has the format
   * <code>P&lt;2-letter country code&gt;&lt;6-digit Main ID&gt;</code> (see
   * {@link PeppolIdentifierHelper#REGEX_SEAT_ID}), so the Main ID are the trailing 6 digits.
   *
   * @param sSeatID
   *        The Seat ID to extract the Main ID from. May be <code>null</code>.
   * @return <code>null</code> if the provided value is not a valid Peppol Seat ID, the 6-digit Main
   *         ID otherwise.
   * @since 12.6.1
   */
  @Nullable
  public static String getMainIDFromSeatID (@Nullable final String sSeatID)
  {
    if (sSeatID == null || !RegExHelper.stringMatchesPattern (PeppolIdentifierHelper.REGEX_SEAT_ID, sSeatID))
      return null;
    return sSeatID.substring (3);
  }
}
