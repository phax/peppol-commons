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

import java.util.Map;

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
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Immutable
public final class PredefinedDocumentTypeIdentifierManager
{
  private static final ICommonsMap <String, IPeppolPredefinedDocumentTypeIdentifier> s_aCodes = new CommonsHashMap<> ();

  static
  {
    // Add all predefined document identifier
    for (final EPredefinedDocumentTypeIdentifier eDocID : EPredefinedDocumentTypeIdentifier.values ())
      s_aCodes.put (eDocID.getValue (), eDocID);
  }

  @PresentForCodeCoverage
  private static final PredefinedDocumentTypeIdentifierManager s_aInstance = new PredefinedDocumentTypeIdentifierManager ();

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
    return s_aCodes.copyOfValues ();
  }

  /**
   * @return A non-<code>null</code> list of all PEPPOL document identifier IDs.
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public static ICommonsSet <String> getAllDocumentTypeIdentifierIDs ()
  {
    return s_aCodes.copyOfKeySet ();
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
      for (final Map.Entry <String, IPeppolPredefinedDocumentTypeIdentifier> aEntry : s_aCodes.entrySet ())
      {
        // PEPPOL: case sensitive document types
        if (sDocTypeIDValue.equals (aEntry.getKey ()))
          return aEntry.getValue ();
      }
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
