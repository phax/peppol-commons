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
package com.helger.peppolid.peppol.doctype;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.version.Version;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.peppol.IPeppolIdentifier;

/**
 * Base interface for predefined document identifiers.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public interface IPeppolPredefinedDocumentTypeIdentifier extends
                                                         IDocumentTypeIdentifier,
                                                         IPeppolIdentifier,
                                                         IPeppolDocumentTypeIdentifierParts
{
  default boolean hasDefaultScheme ()
  {
    return true;
  }

  /**
   * @return The common name under which a document is known. This is e.g.
   *         "Order" or "Invoice".
   */
  @Nullable
  String getCommonName ();

  /**
   * @return The {@link PeppolDocumentTypeIdentifier} version of this predefined
   *         document type identifier.
   */
  @Nonnull
  PeppolDocumentTypeIdentifier getAsDocumentTypeIdentifier ();

  /**
   * @return The internal code list version in which the identifier was added.
   *         Never <code>null</code>.
   */
  @Nonnull
  Version getSince ();

  /**
   * @return <code>true</code> if this identifier is deprecated,
   *         <code>false</code> if not.
   * @since 7.0.0
   */
  boolean isDeprecated ();
}
