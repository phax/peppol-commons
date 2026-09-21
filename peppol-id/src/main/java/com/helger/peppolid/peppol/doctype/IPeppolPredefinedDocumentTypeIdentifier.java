/*
 * Copyright (C) 2015-2026 Philip Helger
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

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.CheckForSigned;
import com.helger.annotation.Nonempty;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.collection.commons.ICommonsList;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.codelist.ICodeListItemWithRelease;
import com.helger.peppolid.peppol.IPeppolIdentifier;

/**
 * Base interface for predefined document identifiers.
 *
 * @author Philip Helger
 */
public interface IPeppolPredefinedDocumentTypeIdentifier extends
                                                         IDocumentTypeIdentifier,
                                                         IPeppolIdentifier,
                                                         IPeppolGenericDocumentTypeIdentifierParts,
                                                         ICodeListItemWithRelease
{
  default boolean hasDefaultScheme ()
  {
    return true;
  }

  /**
   * @return The common name under which a document is known. This is e.g. "Order" or "Invoice".
   */
  @Nullable
  String getCommonName ();

  /**
   * @return The {@link PeppolDocumentTypeIdentifier} version of this predefined document type
   *         identifier.
   */
  @NonNull
  PeppolDocumentTypeIdentifier getAsDocumentTypeIdentifier ();

  /**
   * @return <code>true</code> if this item was officially issued by OpenPEPPOL, <code>false</code>
   *         if it is contained upon a request of a certain PA.
   * @since 8.7.1
   */
  boolean isIssuedByOpenPeppol ();

  /**
   * @return The Peppol BIS major version this belongs to, or -1 if the item is not issued by
   *         OpenPeppol.
   * @see #isIssuedByOpenPeppol()
   * @since 8.0.7
   */
  @CheckForSigned
  int getBISVersion ();

  /**
   * @return The abbreviation of the Peppol domain community this item belongs to. Neither
   *         <code>null</code> nor empty.
   * @since 8.0.7
   */
  @NonNull
  @Nonempty
  String getDomainCommunity ();

  /**
   * @return A non-<code>null</code>, non-empty list of process identifiers to be used with this
   *         item.
   * @since 8.0.7
   */
  @NonNull
  @Nonempty
  @ReturnsMutableCopy
  ICommonsList <IProcessIdentifier> getAllProcessIDs ();
}
