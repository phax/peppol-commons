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
package com.helger.peppol.identifier.peppol.doctype;

import java.nio.charset.StandardCharsets;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.peppol.identifier.generic.doctype.IDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.IPeppolIdentifier;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;

/**
 * Base interface for a PEPPOL read-only document type identifier.
 *
 * @author Philip Helger
 */
public interface IPeppolDocumentTypeIdentifier extends IPeppolIdentifier, IDocumentTypeIdentifier
{
  default boolean hasDefaultScheme ()
  {
    return hasScheme (PeppolIdentifierHelper.DEFAULT_DOCUMENT_TYPE_SCHEME);
  }

  /**
   * Extract the different identifier parts that are contained in a PEPPOL
   * document type identifier.
   *
   * @return A new object encapsulating the different document type identifier
   *         parts.
   */
  @Nonnull
  default IPeppolDocumentTypeIdentifierParts getParts ()
  {
    return PeppolIdentifierHelper.getDocumentTypeIdentifierParts (this);
  }

  /**
   * Check if the passed document type identifier scheme is valid or not. For
   * processes no additional rules apply than the standard rules.
   *
   * @param sScheme
   *        The scheme to check.
   * @return <code>true</code> if the passed scheme is a valid identifier
   *         scheme, <code>false</code> otherwise.
   * @see PeppolIdentifierHelper#isValidIdentifierScheme(String)
   */
  static boolean isValidScheme (@Nullable final String sScheme)
  {
    // No special rules
    return PeppolIdentifierHelper.isValidIdentifierScheme (sScheme);
  }

  /**
   * Check if the passed document type identifier value is valid. A valid
   * identifier must have at least 1 character and at last
   * {@link PeppolIdentifierHelper#MAX_DOCUEMNT_TYPE_VALUE_LENGTH} characters.
   * Also it must be ISO-8859-1 encoded.
   *
   * @param sValue
   *        The document type identifier value to be checked (without the
   *        scheme). May be <code>null</code>.
   * @return <code>true</code> if the document type identifier value is valid,
   *         <code>false</code> otherwise
   */
  public static boolean isValidValue (@Nullable final String sValue)
  {
    if (sValue == null)
      return false;

    final int nLength = sValue.length ();
    if (nLength == 0 || nLength > PeppolIdentifierHelper.MAX_DOCUEMNT_TYPE_VALUE_LENGTH)
      return false;

    // Check if the value is ISO-8859-1 encoded
    return PeppolIdentifierHelper.areCharsetChecksDisabled () ||
           StandardCharsets.ISO_8859_1.newEncoder ().canEncode (sValue);
  }
}
