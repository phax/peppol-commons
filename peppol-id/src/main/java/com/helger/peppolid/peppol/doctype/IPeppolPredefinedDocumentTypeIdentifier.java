/**
 * Copyright (C) 2015-2020 Philip Helger
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
package com.helger.peppolid.peppol.doctype;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.version.Version;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.peppol.IPeppolIdentifier;

/**
 * Base interface for predefined document identifiers.
 *
 * @author Philip Helger
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
