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
package com.helger.peppol.identifier.factory;

import javax.annotation.Nullable;

import com.helger.peppol.identifier.IIdentifier;
import com.helger.peppol.identifier.generic.doctype.IDocumentTypeIdentifier;

/**
 * A generic factory interface for document type identifiers.
 *
 * @author Philip Helger
 * @see IIdentifierFactory
 */
public interface IDocumentTypeIdentifierFactory extends IIdentifierFactoryBase
{
  /**
   * @return <code>true</code> if this identifier type requires a mandatory
   *         document type identifier scheme, <code>false</code> if not.
   */
  default boolean isDocumentTypeIdentifierSchemeMandatory ()
  {
    return false;
  }

  /**
   * @return The default document type identifier scheme to be used for this
   *         identifier type. May be <code>null</code>.
   */
  @Nullable
  default String getDefaultDocumentTypeIdentifierScheme ()
  {
    return null;
  }

  /**
   * @param sScheme
   *        The identifier scheme in use. May be <code>null</code> or empty if
   *        {@link #isDocumentTypeIdentifierSchemeMandatory()} is
   *        <code>false</code>.
   * @return <code>true</code> if all document type identifiers need to be
   *         handled case insensitive (so "abc" equals "ABC"),
   *         <code>false</code> if not.
   */
  default boolean isDocumentTypeIdentifierCaseInsensitive (@Nullable final String sScheme)
  {
    return false;
  }

  /**
   * Create a new document type identifier. This method returns a unified
   * identifier value if
   * {@link #isDocumentTypeIdentifierCaseInsensitive(String)} is
   * <code>true</code> for the provided scheme.
   *
   * @param sScheme
   *        The scheme to be used.
   * @param sValue
   *        The value to be used.
   * @return <code>null</code> if the provided scheme and/or value are/is
   *         invalid according to the rules of the implementation.
   * @see #createDocumentTypeIdentifierWithDefaultScheme(String)
   * @see #getUnifiedValue(String)
   * @see #isDocumentTypeIdentifierCaseInsensitive(String)
   */
  @Nullable
  IDocumentTypeIdentifier createDocumentTypeIdentifier (@Nullable String sScheme, @Nullable String sValue);

  /**
   * Create a new document type identifier using the default identifier scheme.
   * This may result in an <code>null</code> object if no default identifier
   * scheme is present, but no scheme is forbidden! This method returns a
   * unified identifier value if
   * {@link #isDocumentTypeIdentifierCaseInsensitive(String)} is
   * <code>true</code> for the default scheme.
   *
   * @param sValue
   *        The value to be used.
   * @return <code>null</code> if the default scheme and/or the provided value
   *         are/is invalid according to the rules of the implementation.
   * @see #createDocumentTypeIdentifier(String, String)
   * @see #getDefaultDocumentTypeIdentifierScheme()
   * @see #getUnifiedValue(String)
   * @see #isDocumentTypeIdentifierCaseInsensitive(String)
   */
  @Nullable
  default IDocumentTypeIdentifier createDocumentTypeIdentifierWithDefaultScheme (@Nullable final String sValue)
  {
    return createDocumentTypeIdentifier (getDefaultDocumentTypeIdentifierScheme (), sValue);
  }

  /**
   * Parse the provided URI encoded identifier as a document type identifier.
   * This is the reverse operation of {@link IIdentifier#getURIEncoded()}
   *
   * @param sURIEncodedIdentifier
   *        The URI encoded identifier in the format <code>scheme::value</code>.
   *        It must NOT be percent encoded!
   * @return The created identifier or <code>null</code> if the passed
   *         identifier is not a valid URI encoded identifier according to the
   *         rules of the implementation.
   */
  @Nullable
  default IDocumentTypeIdentifier parseDocumentTypeIdentifier (@Nullable final String sURIEncodedIdentifier)
  {
    return parseURIPartOrNull (sURIEncodedIdentifier, (s, v) -> createDocumentTypeIdentifier (s, v));
  }

  /**
   * Create a clone of the passed document type identifier using the correct
   * implementation type.
   *
   * @param aDocTypeID
   *        Source identifier to clone. May be <code>null</code>.
   * @return <code>null</code> if the passed parameter is <code>null</code>.
   */
  @Nullable
  default IDocumentTypeIdentifier getClone (@Nullable final IDocumentTypeIdentifier aDocTypeID)
  {
    return aDocTypeID == null ? null : createDocumentTypeIdentifier (aDocTypeID.getScheme (), aDocTypeID.getValue ());
  }
}
