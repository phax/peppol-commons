/*
 * Copyright (C) 2015-2024 Philip Helger
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
package com.helger.peppolid.factory;

import javax.annotation.Nullable;

import com.helger.peppolid.IIdentifier;
import com.helger.peppolid.IProcessIdentifier;

/**
 * A generic factory interface for process identifiers.
 *
 * @author Philip Helger
 * @see IIdentifierFactory
 */
public interface IProcessIdentifierFactory extends IIdentifierFactoryBase
{
  /**
   * @return <code>true</code> if this identifier type requires a mandatory
   *         process identifier scheme, <code>false</code> if not.
   */
  default boolean isProcessIdentifierSchemeMandatory ()
  {
    return true;
  }

  /**
   * @return The default process identifier scheme to be used for this
   *         identifier type. May be <code>null</code>.
   */
  @Nullable
  default String getDefaultProcessIdentifierScheme ()
  {
    return null;
  }

  /**
   * @param sScheme
   *        The identifier scheme in use. May be <code>null</code> or empty if
   *        {@link #isProcessIdentifierSchemeMandatory()} is <code>false</code>.
   * @return <code>true</code> if all process identifiers need to be handled
   *         case insensitive (so "abc" equals "ABC"), <code>false</code> if
   *         not.
   */
  default boolean isProcessIdentifierCaseInsensitive (@Nullable final String sScheme)
  {
    return false;
  }

  /**
   * Check if the passed process identifier scheme is valid or not.
   *
   * @param sScheme
   *        The scheme to check.
   * @return <code>true</code> if the passed scheme is a valid identifier
   *         scheme, <code>false</code> otherwise.
   */
  default boolean isProcessIdentifierSchemeValid (@Nullable final String sScheme)
  {
    return true;
  }

  /**
   * Check if the passed process identifier value is valid.
   *
   * @param sValue
   *        The process identifier value to be checked (without the scheme). May
   *        be <code>null</code>.
   * @return <code>true</code> if the process identifier value is valid,
   *         <code>false</code> otherwise
   */
  @Deprecated (forRemoval = true, since = "9.1.1")
  default boolean isProcessIdentifierValueValid (@Nullable final String sValue)
  {
    return isProcessIdentifierValueValid (null, sValue);
  }

  /**
   * Check if the passed process identifier value is valid.
   *
   * @param sScheme
   *        The process identifier scheme of the value to be checked. May be
   *        <code>null</code>.
   * @param sValue
   *        The process identifier value to be checked (without the scheme). May
   *        be <code>null</code>.
   * @return <code>true</code> if the process identifier value is valid,
   *         <code>false</code> otherwise
   * @since 9.1.1
   */
  default boolean isProcessIdentifierValueValid (@Nullable final String sScheme, @Nullable final String sValue)
  {
    return true;
  }

  /**
   * Create a new process identifier. This method returns a unified identifier
   * value if {@link #isProcessIdentifierCaseInsensitive(String)} is
   * <code>true</code> for the provided scheme.
   *
   * @param sScheme
   *        The scheme to be used.
   * @param sValue
   *        The value to be used.
   * @return <code>null</code> if the provided scheme and/or value are/is
   *         invalid according to the rules of the implementation.
   * @see #createProcessIdentifierWithDefaultScheme(String)
   * @see #getUnifiedValue(String)
   * @see #isProcessIdentifierCaseInsensitive(String)
   */
  @Nullable
  IProcessIdentifier createProcessIdentifier (@Nullable String sScheme, @Nullable String sValue);

  /**
   * Create a new process identifier using the default identifier scheme. This
   * may result in an <code>null</code> object if no default identifier scheme
   * is present, but no scheme is forbidden! This method returns a unified
   * identifier value if {@link #isProcessIdentifierCaseInsensitive(String)} is
   * <code>true</code> for the default scheme.
   *
   * @param sValue
   *        The value to be used.
   * @return <code>null</code> if the default scheme and/or the provided value
   *         are/is invalid according to the rules of the implementation.
   * @see #createProcessIdentifier(String, String)
   * @see #getDefaultProcessIdentifierScheme()
   * @see #getUnifiedValue(String)
   * @see #isProcessIdentifierCaseInsensitive(String)
   */
  @Nullable
  default IProcessIdentifier createProcessIdentifierWithDefaultScheme (@Nullable final String sValue)
  {
    return createProcessIdentifier (getDefaultProcessIdentifierScheme (), sValue);
  }

  /**
   * Parse the provided URI encoded identifier as a process identifier. This is
   * the reverse operation of {@link IIdentifier#getURIEncoded()}
   *
   * @param sURIEncodedIdentifier
   *        The URI encoded identifier in the format <code>scheme::value</code>.
   *        It must NOT be percent encoded!
   * @return The created identifier or <code>null</code> if the passed
   *         identifier is not a valid URI encoded identifier according to the
   *         rules of the implementation.
   */
  @Nullable
  default IProcessIdentifier parseProcessIdentifier (@Nullable final String sURIEncodedIdentifier)
  {
    return parseURIPartOrNull (sURIEncodedIdentifier, this::createProcessIdentifier);
  }

  /**
   * Create a clone of the passed process identifier using the correct
   * implementation type.
   *
   * @param aProcessID
   *        Source identifier to clone. May be <code>null</code>.
   * @return <code>null</code> if the passed parameter is <code>null</code>.
   */
  @Nullable
  default IProcessIdentifier getClone (@Nullable final IProcessIdentifier aProcessID)
  {
    return aProcessID == null ? null : createProcessIdentifier (aProcessID.getScheme (), aProcessID.getValue ());
  }
}
