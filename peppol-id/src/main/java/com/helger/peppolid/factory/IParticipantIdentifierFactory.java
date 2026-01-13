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
package com.helger.peppolid.factory;

import org.jspecify.annotations.Nullable;

import com.helger.peppolid.IIdentifier;
import com.helger.peppolid.IParticipantIdentifier;

/**
 * A generic factory interface for participant identifiers.
 *
 * @author Philip Helger
 * @see IIdentifierFactory
 */
public interface IParticipantIdentifierFactory extends IIdentifierFactoryBase
{
  /**
   * @return <code>true</code> if this identifier type requires a mandatory participant identifier
   *         scheme, <code>false</code> if not.
   */
  default boolean isParticipantIdentifierSchemeMandatory ()
  {
    return false;
  }

  /**
   * @return The default participant identifier scheme to be used for this identifier type. May be
   *         <code>null</code>.
   */
  @Nullable
  default String getDefaultParticipantIdentifierScheme ()
  {
    return null;
  }

  /**
   * @param sScheme
   *        The identifier scheme in use. May be <code>null</code> or empty if
   *        {@link #isParticipantIdentifierSchemeMandatory()} is <code>false</code>.
   * @return <code>true</code> if all participant identifiers need to be handled case insensitive
   *         (so "abc" equals "ABC"), <code>false</code> if not.
   */
  default boolean isParticipantIdentifierCaseInsensitive (@Nullable final String sScheme)
  {
    return false;
  }

  /**
   * Check if the given scheme is a valid participant identifier scheme.
   *
   * @param sScheme
   *        The scheme to check.
   * @return <code>true</code> if the passed scheme is a valid participant identifier scheme,
   *         <code>false</code> otherwise.
   */
  default boolean isParticipantIdentifierSchemeValid (@Nullable final String sScheme)
  {
    return true;
  }

  /**
   * Check if the passed participant identifier value is valid.
   *
   * @param sScheme
   *        The scheme of the participant identifier value to be checked. May be <code>null</code>.
   * @param sValue
   *        The participant identifier value to be checked (without the scheme). May be
   *        <code>null</code>.
   * @return <code>true</code> if the participant identifier value is valid, <code>false</code>
   *         otherwise.
   * @since 9.1.1
   */
  default boolean isParticipantIdentifierValueValid (@Nullable final String sScheme, @Nullable final String sValue)
  {
    return true;
  }

  /**
   * Create a new participant identifier. This method returns a unified identifier value if
   * {@link #isParticipantIdentifierCaseInsensitive(String)} is <code>true</code> for the provided
   * scheme.
   *
   * @param sScheme
   *        The scheme to be used.
   * @param sValue
   *        The value to be used.
   * @return <code>null</code> if the provided scheme and/or value are/is invalid according to the
   *         rules of the implementation.
   * @see #createParticipantIdentifierWithDefaultScheme(String)
   * @see #getUnifiedValue(String)
   * @see #isParticipantIdentifierCaseInsensitive(String)
   */
  @Nullable
  IParticipantIdentifier createParticipantIdentifier (@Nullable String sScheme, @Nullable String sValue);

  /**
   * Create a new participant identifier. This method may be used to convert an identifier created
   * from a different Identifier Factory.
   *
   * @param aSrc
   *        The source identifier to be used. May be <code>null</code>.
   * @return <code>null</code> if the provided scheme and/or value are/is invalid according to the
   *         rules of the implementation.
   * @see #createParticipantIdentifier(String, String)
   * @since 12.3.6
   */
  @Nullable
  default IParticipantIdentifier createParticipantIdentifier (@Nullable final IParticipantIdentifier aSrc)
  {
    return aSrc == null ? null : createParticipantIdentifier (aSrc.getScheme (), aSrc.getValue ());
  }

  /**
   * Create a new participant identifier using the default identifier scheme. This may result in an
   * <code>null</code> object if no default identifier scheme is present, but no scheme is
   * forbidden! This method returns a unified identifier value if
   * {@link #isParticipantIdentifierCaseInsensitive(String)} is <code>true</code> for the default
   * scheme.
   *
   * @param sValue
   *        The value to be used.
   * @return <code>null</code> if the default scheme and/or the provided value are/is invalid
   *         according to the rules of the implementation.
   * @see #createParticipantIdentifier(String, String)
   * @see #getDefaultParticipantIdentifierScheme()
   * @see #getUnifiedValue(String)
   * @see #isParticipantIdentifierCaseInsensitive(String)
   */
  @Nullable
  default IParticipantIdentifier createParticipantIdentifierWithDefaultScheme (@Nullable final String sValue)
  {
    return createParticipantIdentifier (getDefaultParticipantIdentifierScheme (), sValue);
  }

  /**
   * Parse the provided URI encoded identifier as a participant identifier. This is the reverse
   * operation of {@link IIdentifier#getURIEncoded()}
   *
   * @param sURIEncodedIdentifier
   *        The URI encoded identifier in the format <code>scheme::value</code>. It must NOT be
   *        percent encoded!
   * @return The created identifier or <code>null</code> if the passed identifier is not a valid URI
   *         encoded identifier according to the rules of the implementation.
   */
  @Nullable
  default IParticipantIdentifier parseParticipantIdentifier (@Nullable final String sURIEncodedIdentifier)
  {
    return parseURIPartOrNull (sURIEncodedIdentifier, this::createParticipantIdentifier);
  }

  /**
   * Create a clone of the passed participant identifier using the correct implementation type.
   *
   * @param aParticipantID
   *        Source identifier to clone. May be <code>null</code>.
   * @return <code>null</code> if the passed parameter is <code>null</code>.
   */
  @Nullable
  default IParticipantIdentifier getClone (@Nullable final IParticipantIdentifier aParticipantID)
  {
    return aParticipantID == null ? null : createParticipantIdentifier (aParticipantID.getScheme (),
                                                                        aParticipantID.getValue ());
  }
}
