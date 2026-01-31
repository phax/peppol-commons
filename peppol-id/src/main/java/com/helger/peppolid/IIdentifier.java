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
package com.helger.peppolid;

import java.io.Serializable;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.annotation.style.MustImplementEqualsAndHashcode;
import com.helger.base.equals.EqualsHelper;
import com.helger.base.string.StringHelper;

/**
 * Base interface for a single read-only identifier independent of its usage
 * (participant, document or process).
 *
 * @author Philip Helger
 */
@MustImplementEqualsAndHashcode
public interface IIdentifier extends Serializable
{
  /**
   * @return The scheme used. Never <code>null</code>.
   */
  @NonNull
  String getScheme ();

  /**
   * Since v9.3.4 non-null but empty identifier schemes are allowed (required
   * for DBNA)
   *
   * @return <code>true</code> if a non-<code>null</code> is present.
   */
  default boolean hasScheme ()
  {
    return getScheme () != null;
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
  @NonNull
  String getValue ();

  /**
   * @return <code>true</code> if a non-<code>null</code> and non-empty value is
   *         present.
   */
  default boolean hasValue ()
  {
    return StringHelper.isNotEmpty (getValue ());
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
  @NonNull
  @Nonempty
  default String getURIEncoded ()
  {
    return CIdentifier.getURIEncoded (this);
  }

  /**
   * Get the identifier URI and percent encoded (with percent encoding) as in
   * <code>scheme%3A%3Avalue</code>.
   *
   * @return The URI encoded identifier value. (E.g.
   *         <code>iso6523-actorid-upis%3A%3A0088%3A123456</code>)
   */
  @NonNull
  default String getURIPercentEncoded ()
  {
    return CIdentifier.getURIPercentEncoded (this);
  }
}
