/**
 * Copyright (C) 2015-2020 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppolid.factory;

import javax.annotation.Nullable;

import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IIdentifier;

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
   * Check if the passed document type identifier scheme is valid or not.
   *
   * @param sScheme
   *        The scheme to check.
   * @return <code>true</code> if the passed scheme is a valid identifier
   *         scheme, <code>false</code> otherwise.
   */
  default boolean isDocumentTypeIdentifierSchemeValid (@Nullable final String sScheme)
  {
    return true;
  }

  /**
   * Check if the passed document type identifier value is valid.
   *
   * @param sValue
   *        The document type identifier value to be checked (without the
   *        scheme). May be <code>null</code>.
   * @return <code>true</code> if the document type identifier value is valid,
   *         <code>false</code> otherwise
   */
  default boolean isDocumentTypeIdentifierValueValid (@Nullable final String sValue)
  {
    return true;
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
    return parseURIPartOrNull (sURIEncodedIdentifier, this::createDocumentTypeIdentifier);
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
