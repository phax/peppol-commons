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
package com.helger.peppol.identifier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.utils.BusdoxURLHelper;

/**
 * Constants on BUSDOX identifiers that are not PEPPOL specific.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
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
  public static String getURIEncoded (@Nonnull final com.helger.xsds.bdxr.smp2.bc.ParticipantIDType aID)
  {
    return getURIEncoded (aID.getSchemeID (), aID.getValue ());
  }

  @Nonnull
  @Nonempty
  public static String getURIEncoded (@Nonnull final com.helger.xsds.bdxr.smp2.bc.IDType aID)
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
  public static String getURIPercentEncoded (@Nonnull final com.helger.xsds.bdxr.smp2.bc.ParticipantIDType aID)
  {
    return getURIPercentEncoded (aID.getSchemeID (), aID.getValue ());
  }

  @Nonnull
  @Nonempty
  public static String getURIPercentEncoded (@Nonnull final com.helger.xsds.bdxr.smp2.bc.IDType aID)
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
    return BusdoxURLHelper.createPercentEncodedURL (sURIEncoded);
  }
}
