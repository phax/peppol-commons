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
package com.helger.peppolid;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.PresentForCodeCoverage;
import com.helger.base.string.StringHelper;
import com.helger.url.codec.URLCoder;

/**
 * Constants on BUSDOX identifiers that are not PEPPOL specific.
 *
 * @author Philip Helger
 */
@Immutable
public final class CIdentifier
{
  /**
   * The default process identifier to indicate that no default process belongs to it.<br>
   * See PEPPOL Common definitions chapter 3.6
   */
  public static final String DEFAULT_PROCESS_IDENTIFIER_NOPROCESS = "busdox:noprocess";

  /**
   * The delimiter used between the identifier scheme and the value ("::").
   */
  public static final String URL_SCHEME_VALUE_SEPARATOR = "::";

  @PresentForCodeCoverage
  private static final CIdentifier INSTANCE = new CIdentifier ();

  private CIdentifier ()
  {}

  /**
   * Get the identifier URI encoded (without percent encoding) as in <code>scheme::value</code>.
   *
   * @param aID
   *        The ID to be encoded. May not be <code>null</code>.
   * @return The URI encoded identifier value. (E.g. <code>iso6523-actorid-upis::0088:123456</code>)
   */
  @NonNull
  @Nonempty
  public static String getURIEncoded (@NonNull final IIdentifier aID)
  {
    return getURIEncoded (aID.getScheme (), aID.getValue ());
  }

  @NonNull
  @Nonempty
  public static String getURIEncoded (final com.helger.xsds.peppol.id1.@NonNull ParticipantIdentifierType aID)
  {
    return getURIEncoded (aID.getScheme (), aID.getValue ());
  }

  @NonNull
  @Nonempty
  public static String getURIEncoded (final com.helger.xsds.peppol.id1.@NonNull DocumentIdentifierType aID)
  {
    return getURIEncoded (aID.getScheme (), aID.getValue ());
  }

  @NonNull
  @Nonempty
  public static String getURIEncoded (final com.helger.xsds.peppol.id1.@NonNull ProcessIdentifierType aID)
  {
    return getURIEncoded (aID.getScheme (), aID.getValue ());
  }

  @NonNull
  @Nonempty
  public static String getURIEncoded (final com.helger.xsds.bdxr.smp1.@NonNull ParticipantIdentifierType aID)
  {
    return getURIEncoded (aID.getScheme (), aID.getValue ());
  }

  @NonNull
  @Nonempty
  public static String getURIEncoded (final com.helger.xsds.bdxr.smp1.@NonNull DocumentIdentifierType aID)
  {
    return getURIEncoded (aID.getScheme (), aID.getValue ());
  }

  @NonNull
  @Nonempty
  public static String getURIEncoded (final com.helger.xsds.bdxr.smp1.@NonNull ProcessIdentifierType aID)
  {
    return getURIEncoded (aID.getScheme (), aID.getValue ());
  }

  // This one handles SMP v2 as well
  @NonNull
  @Nonempty
  public static String getURIEncoded (final com.helger.xsds.ccts.cct.schemamodule.@NonNull IdentifierType aID)
  {
    return getURIEncoded (aID.getSchemeID (), aID.getValue ());
  }

  /**
   * Get the identifier URI encoded (without percent encoding) as in <code>scheme::value</code>. If
   * no scheme is present, the result is <code>::value</code>.
   *
   * @param sScheme
   *        The scheme part to be encoded. May be <code>null</code>.
   * @param sValue
   *        The value part to be encoded. May be <code>null</code>.
   * @return The URI encoded identifier value in the form <code><i>scheme</i>::<i>value</i></code>
   *         or <code>::<i>value</i></code>
   */
  @NonNull
  @Nonempty
  public static String getURIEncoded (@Nullable final String sScheme, @Nullable final String sValue)
  {
    // Empty scheme may be allowed, depending on the implementation
    final String sRealScheme = StringHelper.getNotNull (sScheme);

    // Empty value may be allowed, depending on the implementation
    final String sRealValue = StringHelper.getNotNull (sValue);

    // Combine scheme and value
    return sRealScheme + URL_SCHEME_VALUE_SEPARATOR + sRealValue;
  }

  /**
   * Get the identifier URI encoded (without percent encoding) as in <code>scheme::value</code>. If
   * no scheme is present, the result is <code>value</code> which is the difference compared to the
   * default {@link #getURIEncoded(String, String)}.
   *
   * @param sScheme
   *        The scheme part to be encoded. May be <code>null</code>.
   * @param sValue
   *        The value part to be encoded. May be <code>null</code>.
   * @return The URI encoded identifier value in the form <code><i>scheme</i>::<i>value</i></code>
   *         or <code><i>value</i></code>
   */
  @NonNull
  @Nonempty
  public static String getURIEncodedBDXR2 (@Nullable final String sScheme, @Nullable final String sValue)
  {
    // Empty scheme may be allowed, depending on the implementation
    final String sRealScheme = StringHelper.getNotNull (sScheme);

    // Empty value may be allowed, depending on the implementation
    final String sRealValue = StringHelper.getNotNull (sValue);

    // Combine scheme and value
    if (StringHelper.isNotEmpty (sRealScheme))
      return sRealScheme + CIdentifier.URL_SCHEME_VALUE_SEPARATOR + sRealValue;

    // No double colon
    return sRealValue;
  }

  /**
   * Escape the passed URL to use the percentage maskings.
   *
   * @param sURL
   *        The input URL or URL part. May be <code>null</code>.
   * @return <code>null</code> if the input string was <code>null</code>.
   */
  @Nullable
  public static String createPercentEncoded (@Nullable final String sURL)
  {
    return sURL == null ? null : URLCoder.urlEncode (sURL);
  }

  /**
   * Unescape the passed URL to NOT use the percentage maskings.
   *
   * @param sURL
   *        The input URL or URL part. May be <code>null</code>.
   * @return <code>null</code> if the input string was <code>null</code>.
   */
  @Nullable
  public static String createPercentDecoded (@Nullable final String sURL)
  {
    return URLCoder.urlDecodeOrNull (sURL);
  }

  /**
   * Get the identifier URI and percent encoded (with percent encoding) as in
   * <code>scheme%3A%3Avalue</code>.
   *
   * @param aID
   *        The ID to be encoded. May not be <code>null</code>.
   * @return The URI encoded identifier value. (E.g.
   *         <code>iso6523-actorid-upis%3A%3A0088%3A123456</code>)
   */
  @NonNull
  public static String getURIPercentEncoded (@NonNull final IIdentifier aID)
  {
    return getURIPercentEncoded (aID.getScheme (), aID.getValue ());
  }

  @NonNull
  public static String getURIPercentEncoded (final com.helger.xsds.peppol.id1.@NonNull ParticipantIdentifierType aID)
  {
    return getURIPercentEncoded (aID.getScheme (), aID.getValue ());
  }

  @NonNull
  public static String getURIPercentEncoded (final com.helger.xsds.peppol.id1.@NonNull DocumentIdentifierType aID)
  {
    return getURIPercentEncoded (aID.getScheme (), aID.getValue ());
  }

  @NonNull
  public static String getURIPercentEncoded (final com.helger.xsds.peppol.id1.@NonNull ProcessIdentifierType aID)
  {
    return getURIPercentEncoded (aID.getScheme (), aID.getValue ());
  }

  @NonNull
  public static String getURIPercentEncoded (final com.helger.xsds.bdxr.smp1.@NonNull ParticipantIdentifierType aID)
  {
    return getURIPercentEncoded (aID.getScheme (), aID.getValue ());
  }

  @NonNull
  public static String getURIPercentEncoded (final com.helger.xsds.bdxr.smp1.@NonNull DocumentIdentifierType aID)
  {
    return getURIPercentEncoded (aID.getScheme (), aID.getValue ());
  }

  @NonNull
  public static String getURIPercentEncoded (final com.helger.xsds.bdxr.smp1.@NonNull ProcessIdentifierType aID)
  {
    return getURIPercentEncoded (aID.getScheme (), aID.getValue ());
  }

  // This one handles SMP v2 as well
  @NonNull
  @Nonempty
  public static String getURIPercentEncoded (final com.helger.xsds.ccts.cct.schemamodule.@NonNull IdentifierType aID)
  {
    return getURIPercentEncoded (aID.getSchemeID (), aID.getValue ());
  }

  /**
   * Get the identifier URI and percent encoded (with percent encoding) as in
   * <code>scheme%3A%3Avalue</code>.
   *
   * @param sScheme
   *        The scheme part to be encoded. May be <code>null</code>.
   * @param sValue
   *        The value part to be encoded. May be <code>null</code>.
   * @return The URI encoded identifier value. (E.g.
   *         <code>iso6523-actorid-upis%3A%3A0088%3A123456</code>)
   */
  @NonNull
  public static String getURIPercentEncoded (@Nullable final String sScheme, @Nullable final String sValue)
  {
    final String sURIEncoded = getURIEncoded (sScheme, sValue);
    return createPercentEncoded (sURIEncoded);
  }
}
