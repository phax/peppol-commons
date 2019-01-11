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
package com.helger.peppol.utils;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.codec.URLCodec;
import com.helger.peppol.url.PeppolURLProvider;

/**
 * Utility methods for assembling URLs and URL elements required for BusDox.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Immutable
public final class BusdoxURLHelper
{
  private static final URLCodec CODEC = new URLCodec ();

  @PresentForCodeCoverage
  private static final BusdoxURLHelper s_aInstance = new BusdoxURLHelper ();

  private BusdoxURLHelper ()
  {}

  /**
   * Escape the passed URL to use the percentage maskings.
   *
   * @param sURL
   *        The input URL or URL part. May be <code>null</code>.
   * @return <code>null</code> if the input string was <code>null</code>.
   */
  @Nullable
  public static String createPercentEncodedURL (@Nullable final String sURL)
  {
    return CODEC.getEncodedAsString (sURL, PeppolURLProvider.URL_CHARSET);
  }

  /**
   * Unescape the passed URL to NOT use the percentage maskings.
   *
   * @param sURL
   *        The input URL or URL part. May be <code>null</code>.
   * @return <code>null</code> if the input string was <code>null</code>.
   */
  @Nullable
  public static String createPercentDecodedURL (@Nullable final String sURL)
  {
    return CODEC.getDecodedAsString (sURL, PeppolURLProvider.URL_CHARSET);
  }
}
