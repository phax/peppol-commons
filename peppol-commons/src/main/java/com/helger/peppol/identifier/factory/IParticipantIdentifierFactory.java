/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
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
   *         case insensitive (so "abc" equals "ABC"), <code>false</code> if
   *         not.
   */
  default boolean isParticipantIdentifierCaseInsensitive (@Nullable final String sScheme)
  {
    return false;
  }

  /**
   * Create a new participant identifier.
   *
   * @param sScheme
   *        The scheme to be used.
   * @param sValue
   *        The value to be used.
   * @return <code>null</code> if the provided scheme and/or value are/is
   *         invalid.
   */
  @Nullable
  IParticipantIdentifier createParticipantIdentifier (@Nullable String sScheme, @Nullable String sValue);

  /**
   * Parse the provided URI encoded identifier as a participant identifier. This
   * is the reverse operation of {@link IIdentifier#getURIEncoded()}
   *
   * @param sURIEncodedIdentifier
   *        The URI encoded identifier in the format <code>scheme::value</code>.
   *        It must NOT be percent encoded!
   * @return The created identifier or <code>null</code> if the passed
   *         identifier is not a valid URI encoded identifier
   */
  @Nullable
  default IParticipantIdentifier parseParticipantIdentifier (@Nullable final String sURIEncodedIdentifier)
  {
    return parseURIPartOrNull (sURIEncodedIdentifier, (s, v) -> createParticipantIdentifier (s, v));
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
