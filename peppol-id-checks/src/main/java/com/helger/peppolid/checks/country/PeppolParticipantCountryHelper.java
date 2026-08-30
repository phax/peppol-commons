/*
 * Copyright (C) 2026 Philip Helger
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
package com.helger.peppolid.checks.country;

import java.util.Locale;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.PresentForCodeCoverage;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.string.StringHelper;
import com.helger.collection.commons.CommonsHashMap;
import com.helger.collection.commons.ICommonsMap;
import com.helger.masterdata.ean.EGS1Prefix;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.peppolid.peppol.pidscheme.EPredefinedParticipantIdentifierScheme;

/**
 * Determine the country of a Peppol participant identifier that uses the default participant
 * identifier scheme <code>iso6523-actorid-upis</code>.
 * <p>
 * The mapping is derived from {@link EPredefinedParticipantIdentifierScheme} - every scheme that is
 * bound to a single country maps all its participants onto that country. For the schemes that carry
 * the country inside the identifier value - GLN ({@link #ICD_GLN}), GS1 ({@link #ICD_GS1}), EU VAT
 * ({@link #ICD_EU_VAT}) and IBAN ({@link #ICD_IBAN}) - the identifier value is evaluated instead.
 * </p>
 * <p>
 * The result is always an ISO 3166-1 alpha-2 country code in upper case, or <code>null</code> if
 * the country cannot be determined. Note that the country of a participant identifier is not
 * necessarily the country of the respective legal entity - e.g. a GS1 prefix only identifies the
 * GS1 Member Organisation that issued the company prefix.
 * </p>
 *
 * @author Philip Helger
 * @since 12.9.0
 */
@Immutable
public final class PeppolParticipantCountryHelper
{
  public static final String AU = "AU";
  public static final String BE = "BE";
  public static final String DE = "DE";
  public static final String DK = "DK";
  public static final String FR = "FR";
  public static final String JP = "JP";
  public static final String NZ = "NZ";
  public static final String SK = "SK";

  /** GLN - the identifier value is a GS1 identifier */
  public static final String ICD_GLN = "0088";
  /** GS1 - the identifier value is a GS1 identifier */
  public static final String ICD_GS1 = "0209";
  /** EU:VAT - the identifier value starts with the VAT country code */
  public static final String ICD_EU_VAT = "9912";
  /** IBAN - the identifier value starts with the country code */
  public static final String ICD_IBAN = "9918";

  private static boolean _isCountryCode (@Nullable final String sValue)
  {
    if (sValue == null || sValue.length () != 2)
      return false;

    final char cFirst = sValue.charAt (0);
    final char cSecond = sValue.charAt (1);
    return cFirst >= 'A' && cFirst <= 'Z' && cSecond >= 'A' && cSecond <= 'Z';
  }

  @NonNull
  private static ICommonsMap <String, String> _createICDToCountryMap ()
  {
    final ICommonsMap <String, String> ret = new CommonsHashMap <> ();
    for (final EPredefinedParticipantIdentifierScheme e : EPredefinedParticipantIdentifierScheme.values ())
    {
      // Non country specific schemes use "international" as the country code
      final String sCountryCode = e.getCountryCode ();
      if (_isCountryCode (sCountryCode))
        ret.put (e.getISO6523Code (), sCountryCode);
    }
    return ret;
  }

  /**
   * Maps the ISO 6523 code of all country specific Peppol participant identifier schemes to the
   * respective ISO 3166-1 alpha-2 country code.
   */
  private static final ICommonsMap <String, String> ICD_TO_COUNTRY = _createICDToCountryMap ();

  @PresentForCodeCoverage
  private static final PeppolParticipantCountryHelper INSTANCE = new PeppolParticipantCountryHelper ();

  private PeppolParticipantCountryHelper ()
  {}

  /**
   * Get the country code from the first 2 characters of the provided identifier value.
   *
   * @param sIdentifier
   *        The identifier value, without the ISO 6523 scheme. May not be <code>null</code>.
   * @return <code>null</code> if the identifier value does not start with a country code.
   */
  @Nullable
  private static String _getLeadingCountryCode (@NonNull final String sIdentifier)
  {
    if (sIdentifier.length () < 2)
      return null;

    final String sCountryCode = sIdentifier.substring (0, 2).toUpperCase (Locale.ROOT);
    return _isCountryCode (sCountryCode) ? sCountryCode : null;
  }

  /**
   * Get the country code of an EU VAT number. Two EU VAT prefixes differ from the ISO 3166-1
   * alpha-2 country code: "EL" is used for Greece and "XI" is used for Northern Ireland.
   *
   * @param sIdentifier
   *        The identifier value, without the ISO 6523 scheme. May not be <code>null</code>.
   * @return <code>null</code> if the identifier value does not start with a country code.
   */
  @Nullable
  private static String _getVATCountryCode (@NonNull final String sIdentifier)
  {
    final String sCountryCode = _getLeadingCountryCode (sIdentifier);
    if ("EL".equals (sCountryCode))
      return "GR";
    if ("XI".equals (sCountryCode))
      return "GB";
    return sCountryCode;
  }

  /**
   * Get a copy of the mapping from the ISO 6523 code of all country specific Peppol participant
   * identifier schemes onto the respective ISO 3166-1 alpha-2 country code. This map contains only
   * the schemes that are bound to a single country - the schemes that carry the country inside the
   * identifier value (see {@link #ICD_GLN}, {@link #ICD_GS1}, {@link #ICD_EU_VAT} and
   * {@link #ICD_IBAN}) are not contained.
   *
   * @return A non-<code>null</code> copy of the internal map. Modifications to the returned map
   *         have no effect on this class.
   */
  @NonNull
  @ReturnsMutableCopy
  public static ICommonsMap <String, String> getAllSchemeCountryCodes ()
  {
    return ICD_TO_COUNTRY.getClone ();
  }

  /**
   * Get the country code of the provided participant identifier value.
   *
   * @param sValue
   *        The participant identifier value, including the leading ISO 6523 code - e.g.
   *        <code>0088:1234567890128</code>. May be <code>null</code>.
   * @return <code>null</code> if the country cannot be determined, the ISO 3166-1 alpha-2 country
   *         code in upper case otherwise.
   */
  @Nullable
  public static String getCountryCode (@Nullable final String sValue)
  {
    if (sValue == null)
      return null;

    final int nSepIndex = sValue.indexOf (':');
    if (nSepIndex < 0)
      return null;

    // The ISO 6523 code is always numeric, so no case conversion is needed here
    final String sScheme = sValue.substring (0, nSepIndex);

    // The schemes that carry the country inside the identifier value
    if (ICD_GLN.equals (sScheme) || ICD_GS1.equals (sScheme))
      return EGS1Prefix.getCountryCodeFromCode (sValue.substring (nSepIndex + 1));
    if (ICD_IBAN.equals (sScheme))
      return _getLeadingCountryCode (sValue.substring (nSepIndex + 1));
    if (ICD_EU_VAT.equals (sScheme))
      return _getVATCountryCode (sValue.substring (nSepIndex + 1));

    // All country specific schemes
    return ICD_TO_COUNTRY.get (sScheme);
  }

  /**
   * Get the country code of the provided participant identifier. Only participant identifiers that
   * use the default Peppol participant identifier scheme
   * {@link PeppolIdentifierHelper#DEFAULT_PARTICIPANT_SCHEME} are evaluated, because only there the
   * ISO 6523 code is known.
   *
   * @param aParticipantID
   *        The participant identifier to evaluate. May be <code>null</code>.
   * @return <code>null</code> if the country cannot be determined, the ISO 3166-1 alpha-2 country
   *         code in upper case otherwise.
   * @see #getCountryCode(String)
   */
  @Nullable
  public static String getCountryCode (@Nullable final IParticipantIdentifier aParticipantID)
  {
    if (aParticipantID == null || !aParticipantID.hasScheme (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME))
      return null;

    final String sValue = aParticipantID.getValue ();
    return StringHelper.isEmpty (sValue) ? null : getCountryCode (sValue);
  }
}
