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

import javax.annotation.CheckForSigned;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.version.Version;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IProcessIdentifier;
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

  /**
   * Get the version since when this item is deprecated.
   *
   * @return <code>null</code> if this item is not deprecated.
   * @see #isDeprecated()
   * @since 8.0.7
   */
  @Nullable
  Version getDeprecatedSince ();

  /**
   * @return <code>true</code> if this item was officially issued by OpenPEPPOL,
   *         <code>false</code> if it is contained upon a request of a certain
   *         PA.
   * @since 8.0.7
   */
  boolean isIssuedByOpenPEPPOL ();

  /**
   * @return The Peppol BIS major version this belongs to, or -1 if the item is
   *         not issued by OpenPeppol.
   * @see #isIssuedByOpenPEPPOL()
   * @since 8.0.7
   */
  @CheckForSigned
  int getBISVersion ();

  /**
   * @return The abbreviation of the Peppol domain community this item belongs
   *         to. Neither <code>null</code> nor empty.
   * @since 8.0.7
   */
  @Nonnull
  @Nonempty
  String getDomainCommunity ();

  /**
   * @return A non-<code>null</code>, non-empty list of process identifiers to
   *         be used with this item.
   * @since 8.0.7
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  ICommonsList <IProcessIdentifier> getAllProcessIDs ();
}
