/*
 * Copyright (C) 2015-2023 Philip Helger
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
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsHashMap;
import com.helger.commons.collection.impl.ICommonsCollection;
import com.helger.commons.collection.impl.ICommonsMap;
import com.helger.commons.collection.impl.ICommonsSet;

/**
 * This class manages the predefined PEPPOL document identifiers the
 * <b>busdox-docid-qns</b> scheme.<br>
 * This class provides sanity methods around
 * {@link EPredefinedDocumentTypeIdentifier} which would be to bogus to generate
 * them.
 *
 * @author Philip Helger
 */
@Immutable
public final class PredefinedDocumentTypeIdentifierManager
{
  private static final ICommonsMap <String, IPeppolPredefinedDocumentTypeIdentifier> CODES = new CommonsHashMap <> ();

  static
  {
    // Add all predefined document identifier
    for (final EPredefinedDocumentTypeIdentifier eDocID : EPredefinedDocumentTypeIdentifier.values ())
      CODES.put (eDocID.getValue (), eDocID);
  }

  @PresentForCodeCoverage
  private static final PredefinedDocumentTypeIdentifierManager INSTANCE = new PredefinedDocumentTypeIdentifierManager ();

  private PredefinedDocumentTypeIdentifierManager ()
  {}

  /**
   * @return A non-modifiable list of all PEPPOL document identifiers.
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public static ICommonsCollection <IPeppolPredefinedDocumentTypeIdentifier> getAllDocumentTypeIdentifiers ()
  {
    return CODES.copyOfValues ();
  }

  /**
   * @return A non-<code>null</code> list of all PEPPOL document identifier IDs.
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public static ICommonsSet <String> getAllDocumentTypeIdentifierIDs ()
  {
    return CODES.copyOfKeySet ();
  }

  /**
   * Find the document identifier with the given ID. This search is done case
   * sensitive.
   *
   * @param sDocTypeIDValue
   *        The value to search. Without any identifier scheme! May be
   *        <code>null</code>.
   * @return <code>null</code> if no such document identifier exists.
   */
  @Nullable
  public static IPeppolPredefinedDocumentTypeIdentifier getDocumentTypeIdentifierOfID (@Nullable final String sDocTypeIDValue)
  {
    if (sDocTypeIDValue != null)
    {
      // PEPPOL: case sensitive document types
      return CODES.get (sDocTypeIDValue);
    }
    return null;
  }

  /**
   * Check if a document identifier with the given ID exists.
   *
   * @param sDocTypeIDValue
   *        The value to search. Without any identifier scheme! May be
   *        <code>null</code>.
   * @return <code>true</code> if such a document identifier exists,
   *         <code>false</code> otherwise.
   */
  public static boolean containsDocumentTypeIdentifierWithID (@Nullable final String sDocTypeIDValue)
  {
    return getDocumentTypeIdentifierOfID (sDocTypeIDValue) != null;
  }
}
