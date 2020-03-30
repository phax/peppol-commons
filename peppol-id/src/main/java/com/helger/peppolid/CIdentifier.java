/**
 * Copyright (C) 2015-2020 Philip Helger
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.string.StringHelper;
import com.helger.commons.url.URLHelper;

/**
 * Constants on BUSDOX identifiers that are not PEPPOL specific.
 *
 * @author Philip Helger
 */
@Immutable
public final class CIdentifier
{
  /**
   * The default process identifier to indicate that no default process belongs
   * to it.<br>
   * See PEPPOL Common definitions chapter 3.6
   */
  public static final String DEFAULT_PROCESS_IDENTIFIER_NOPROCESS = "busdox:noprocess";

  /**
   * The delimiter used between the identifier scheme and the value ("::").
   */
  public static final String URL_SCHEME_VALUE_SEPARATOR = "::";

  @PresentForCodeCoverage
  private static final CIdentifier s_aInstance = new CIdentifier ();

  private CIdentifier ()
  {}

  /**
   * Get the identifier URI encoded (without percent encoding) as in
   * <code>scheme::value</code>.
   *
   * @param aID
   *        The ID to be encoded. May not be <code>null</code>.
   * @return The URI encoded identifier value. (E.g.
   *         <code>iso6523-actorid-upis::0088:123456</code>)
   */
  @Nonnull
  @Nonempty
  public static String getURIEncoded (@Nonnull final IIdentifier aID)
  {
    return getURIEncoded (aID.getScheme (), aID.getValue ());
  }

  @Nonnull
  @Nonempty
  public static String getURIEncoded (@Nonnull final ParticipantIdentifierType aID)
  {
    return getURIEncoded (aID.getScheme (), aID.getValue ());
  }

  @Nonnull
  @Nonempty
  public static String getURIEncoded (@Nonnull final DocumentIdentifierType aID)
  {
    return getURIEncoded (aID.getScheme (), aID.getValue ());
  }

  @Nonnull
  @Nonempty
  public static String getURIEncoded (@Nonnull final ProcessIdentifierType aID)
  {
    return getURIEncoded (aID.getScheme (), aID.getValue ());
  }

  @Nonnull
  @Nonempty
  public static String getURIEncoded (@Nonnull final com.helger.xsds.bdxr.smp1.ParticipantIdentifierType aID)
  {
    return getURIEncoded (aID.getScheme (), aID.getValue ());
  }

  @Nonnull
  @Nonempty
  public static String getURIEncoded (@Nonnull final com.helger.xsds.bdxr.smp1.DocumentIdentifierType aID)
  {
    return getURIEncoded (aID.getScheme (), aID.getValue ());
  }

  @Nonnull
  @Nonempty
  public static String getURIEncoded (@Nonnull final com.helger.xsds.bdxr.smp1.ProcessIdentifierType aID)
  {
    return getURIEncoded (aID.getScheme (), aID.getValue ());
  }

  @Nonnull
  @Nonempty
  public static String getURIEncoded (@Nonnull final com.helger.xsds.ccts.cct.schemamodule.IdentifierType aID)
  {
    return getURIEncoded (aID.getSchemeID (), aID.getValue ());
  }

  /**
   * Get the identifier URI encoded (without percent encoding) as in
   * <code>scheme::value</code>.
   *
   * @param sScheme
   *        The scheme part to be encoded. May be <code>null</code>.
   * @param sValue
   *        The value part to be encoded. May be <code>null</code>.
   * @return The URI encoded identifier value in the form
   *         <code><i>scheme</i>::<i>value</i></code>
   */
  @Nonnull
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
   * Escape the passed URL to use the percentage maskings.
   *
   * @param sURL
   *        The input URL or URL part. May be <code>null</code>.
   * @return <code>null</code> if the input string was <code>null</code>.
   */
  @Nullable
  public static String createPercentEncoded (@Nullable final String sURL)
  {
    return sURL == null ? null : URLHelper.urlEncode (sURL);
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
    return URLHelper.urlDecodeOrNull (sURL);
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
  @Nonnull
  public static String getURIPercentEncoded (@Nonnull final IIdentifier aID)
  {
    return getURIPercentEncoded (aID.getScheme (), aID.getValue ());
  }

  @Nonnull
  public static String getURIPercentEncoded (@Nonnull final ParticipantIdentifierType aID)
  {
    return getURIPercentEncoded (aID.getScheme (), aID.getValue ());
  }

  @Nonnull
  public static String getURIPercentEncoded (@Nonnull final DocumentIdentifierType aID)
  {
    return getURIPercentEncoded (aID.getScheme (), aID.getValue ());
  }

  @Nonnull
  public static String getURIPercentEncoded (@Nonnull final ProcessIdentifierType aID)
  {
    return getURIPercentEncoded (aID.getScheme (), aID.getValue ());
  }

  @Nonnull
  public static String getURIPercentEncoded (@Nonnull final com.helger.xsds.bdxr.smp1.ParticipantIdentifierType aID)
  {
    return getURIPercentEncoded (aID.getScheme (), aID.getValue ());
  }

  @Nonnull
  public static String getURIPercentEncoded (@Nonnull final com.helger.xsds.bdxr.smp1.DocumentIdentifierType aID)
  {
    return getURIPercentEncoded (aID.getScheme (), aID.getValue ());
  }

  @Nonnull
  public static String getURIPercentEncoded (@Nonnull final com.helger.xsds.bdxr.smp1.ProcessIdentifierType aID)
  {
    return getURIPercentEncoded (aID.getScheme (), aID.getValue ());
  }

  @Nonnull
  @Nonempty
  public static String getURIPercentEncoded (@Nonnull final com.helger.xsds.ccts.cct.schemamodule.IdentifierType aID)
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
  @Nonnull
  public static String getURIPercentEncoded (@Nullable final String sScheme, @Nullable final String sValue)
  {
    final String sURIEncoded = getURIEncoded (sScheme, sValue);
    return createPercentEncoded (sURIEncoded);
  }
}
