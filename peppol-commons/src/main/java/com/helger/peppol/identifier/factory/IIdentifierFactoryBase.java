/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
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
package com.helger.peppol.identifier.factory;

import java.io.Serializable;
import java.util.Locale;
import java.util.function.BiFunction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.collection.ext.ICommonsList;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.CIdentifier;
import com.helger.peppol.identifier.IIdentifier;

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
   * @return The created {@link IIdentifier} instance or <code>null</code> if
   *         the passed identifier is not a valid URI encoded identifier
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
