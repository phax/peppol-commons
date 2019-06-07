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
package com.helger.peppolid.factory;

import java.io.Serializable;
import java.util.Locale;
import java.util.function.BiFunction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.string.StringHelper;
import com.helger.peppolid.CIdentifier;
import com.helger.peppolid.IIdentifier;

/**
 * Common functionality for all identifier factories.
 *
 * @author Philip Helger
 */
public interface IIdentifierFactoryBase extends Serializable
{
  /**
   * Create a new identifier from the URI representation. This is the inverse
   * operation of {@link IIdentifier#getURIEncoded()}. The URI part must have
   * the layout <code>scheme::value</code>. This method accepts all identifier
   * schemes and values.
   *
   * @param sURIPart
   *        The URI part in the format <code>scheme::value</code>. It must NOT
   *        be percent encoded! May be <code>null</code>.
   * @param aConverter
   *        Converter function to be used. The first parameter is the scheme,
   *        the second parameter is the value. May not be <code>null</code>.
   * @return The created {@link IIdentifier} instance or <code>null</code> if
   *         the passed identifier is not a valid URI encoded identifier
   * @param <T>
   *        identifier target type
   */
  @Nullable
  default <T extends IIdentifier> T parseURIPartOrNull (@Nullable final String sURIPart,
                                                        @Nonnull final BiFunction <String, String, T> aConverter)
  {
    if (sURIPart == null)
      return null;

    // This is quicker than splitting with RegEx!
    final ICommonsList <String> aSplitted = StringHelper.getExploded (CIdentifier.URL_SCHEME_VALUE_SEPARATOR,
                                                                      sURIPart,
                                                                      2);
    if (aSplitted.size () != 2)
      return null;

    // Okay, we have exactly 2 parts -> create the identifier
    return aConverter.apply (aSplitted.get (0), aSplitted.get (1));
  }

  /**
   * This method should be used to create case-insensitive identifier values.
   * Internally this method lower cases the provided value with the rules of
   * {@link Locale#US}.
   *
   * @param sValue
   *        The source value. May be <code>null</code>.
   * @return <code>null</code> if the source value is <code>null</code>, the
   *         lowercase value otherwise.
   */
  @Nullable
  default String getUnifiedValue (@Nullable final String sValue)
  {
    return sValue == null ? null : sValue.toLowerCase (Locale.US);
  }

  /**
   * Ensure that <code>null</code> is used instead of an empty string.
   *
   * @param s
   *        Source string
   * @return <code>null</code> if the source string was "", the source string
   *         "as-is" otherwise
   */
  @Nullable
  default String nullNotEmpty (@Nullable final String s)
  {
    return "".equals (s) ? null : s;
  }
}
