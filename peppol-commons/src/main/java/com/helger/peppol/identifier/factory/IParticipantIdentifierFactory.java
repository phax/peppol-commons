/**
 * Copyright (C) 2015-2018 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.identifier.factory;

import javax.annotation.Nullable;

import com.helger.peppol.identifier.IIdentifier;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;

/**
 * A generic factory interface for participant identifiers.
 *
 * @author Philip Helger
 * @see IIdentifierFactory
 */
public interface IParticipantIdentifierFactory extends IIdentifierFactoryBase
{
  /**
   * @return <code>true</code> if this identifier type requires a mandatory
   *         participant identifier scheme, <code>false</code> if not.
   */
  default boolean isParticipantIdentifierSchemeMandatory ()
  {
    return false;
  }

  /**
   * @return The default participant identifier scheme to be used for this
   *         identifier type. May be <code>null</code>.
   */
  @Nullable
  default String getDefaultParticipantIdentifierScheme ()
  {
    return null;
  }

  /**
   * @param sScheme
   *        The identifier scheme in use. May be <code>null</code> or empty if
   *        {@link #isParticipantIdentifierSchemeMandatory()} is
   *        <code>false</code>.
   * @return <code>true</code> if all participant identifiers need to be handled
   *         case insensitive (so "abc" equals "ABC"), <code>false</code> if not.
   */
  default boolean isParticipantIdentifierCaseInsensitive (@Nullable final String sScheme)
  {
    return false;
  }

  /**
   * Create a new participant identifier. This method returns a unified identifier
   * value if {@link #isParticipantIdentifierCaseInsensitive(String)} is
   * <code>true</code> for the provided scheme.
   *
   * @param sScheme
   *        The scheme to be used.
   * @param sValue
   *        The value to be used.
   * @return <code>null</code> if the provided scheme and/or value are/is invalid
   *         according to the rules of the implementation.
   * @see #createParticipantIdentifierWithDefaultScheme(String)
   * @see #getUnifiedValue(String)
   * @see #isParticipantIdentifierCaseInsensitive(String)
   */
  @Nullable
  IParticipantIdentifier createParticipantIdentifier (@Nullable String sScheme, @Nullable String sValue);

  /**
   * Create a new participant identifier using the default identifier scheme. This
   * may result in an <code>null</code> object if no default identifier scheme is
   * present, but no scheme is forbidden! This method returns a unified identifier
   * value if {@link #isParticipantIdentifierCaseInsensitive(String)} is
   * <code>true</code> for the default scheme.
   *
   * @param sValue
   *        The value to be used.
   * @return <code>null</code> if the default scheme and/or the provided value
   *         are/is invalid according to the rules of the implementation.
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
   * Parse the provided URI encoded identifier as a participant identifier. This
   * is the reverse operation of {@link IIdentifier#getURIEncoded()}
   *
   * @param sURIEncodedIdentifier
   *        The URI encoded identifier in the format <code>scheme::value</code>.
   *        It must NOT be percent encoded!
   * @return The created identifier or <code>null</code> if the passed identifier
   *         is not a valid URI encoded identifier according to the rules of the
   *         implementation.
   */
  @Nullable
  default IParticipantIdentifier parseParticipantIdentifier (@Nullable final String sURIEncodedIdentifier)
  {
    return parseURIPartOrNull (sURIEncodedIdentifier, this::createParticipantIdentifier);
  }

  /**
   * Create a clone of the passed participant identifier using the correct
   * implementation type.
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
