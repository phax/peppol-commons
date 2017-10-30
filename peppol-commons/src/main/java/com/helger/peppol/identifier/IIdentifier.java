/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Version: MPL 2.0/EUPL 1.2
 * -
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 * -
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.2 or - as soon they will be approved
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
 * -
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.identifier;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.MustImplementEqualsAndHashcode;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.utils.BusdoxURLHelper;

/**
 * Base interface for a single read-only identifier independent of its usage
 * (participant, document or process).
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@MustImplementEqualsAndHashcode
public interface IIdentifier extends Serializable
{
  /**
   * @return The scheme used. Never <code>null</code>.
   */
  @Nonnull
  String getScheme ();

  /**
   * @return <code>true</code> if a non-<code>null</code> and non-empty scheme
   *         is present.
   */
  default boolean hasScheme ()
  {
    return StringHelper.hasText (getScheme ());
  }

  /**
   * Check if this identifier has the passed scheme.
   *
   * @param sScheme
   *        The scheme to check. May be <code>null</code>.
   * @return <code>true</code> if the scheme of this identifier matches the
   *         passed scheme.
   */
  default boolean hasScheme (@Nullable final String sScheme)
  {
    return EqualsHelper.equals (getScheme (), sScheme);
  }

  /**
   * @return The identifier value, dependent on the scheme. Never
   *         <code>null</code>.
   */
  @Nonnull
  String getValue ();

  /**
   * @return <code>true</code> if a non-<code>null</code> and non-empty value is
   *         present.
   */
  default boolean hasValue ()
  {
    return StringHelper.hasText (getValue ());
  }

  /**
   * Check if this identifier has the passed value.
   *
   * @param sValue
   *        The value to check. May be <code>null</code>.
   * @return <code>true</code> if the value of this identifier matches the
   *         passed value.
   */
  default boolean hasValue (@Nullable final String sValue)
  {
    return EqualsHelper.equals (getValue (), sValue);
  }

  /**
   * Get the identifier URI encoded (without percent encoding) as in
   * <code>scheme::value</code>.
   *
   * @return The URI encoded identifier value. (E.g.
   *         <code>iso6523-actorid-upis::0088:123456</code>)
   */
  @Nonnull
  @Nonempty
  default String getURIEncoded ()
  {
    // Empty scheme is allowed
    final String sScheme = StringHelper.getNotNull (getScheme ());

    final String sValue = getValue ();
    if (sValue == null)
      throw new IllegalArgumentException ("Identifier has a null value: " + toString ());

    // Combine scheme and value
    return sScheme + CIdentifier.URL_SCHEME_VALUE_SEPARATOR + sValue;
  }

  /**
   * Get the identifier URI and percent encoded (with percent encoding) as in
   * <code>scheme%3A%3Avalue</code>.
   *
   * @return The URI encoded identifier value. (E.g.
   *         <code>iso6523-actorid-upis%3A%3A0088%3A123456</code>)
   */
  @Nonnull
  default String getURIPercentEncoded ()
  {
    final String sURIEncoded = getURIEncoded ();
    return BusdoxURLHelper.createPercentEncodedURL (sURIEncoded);
  }
}
