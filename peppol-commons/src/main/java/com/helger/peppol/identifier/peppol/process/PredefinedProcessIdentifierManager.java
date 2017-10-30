/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.identifier.peppol.process;

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
 * This class manages the predefined PEPPOL process identifiers the
 * <b>cenbii-procid-ubl</b> scheme. This class provides sanity methods around
 * {@link EPredefinedProcessIdentifier} which would be to bogus to generate
 * them.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Immutable
public final class PredefinedProcessIdentifierManager
{
  private static final ICommonsMap <String, IPeppolPredefinedProcessIdentifier> s_aCodes = new CommonsHashMap<> ();

  static
  {
    // Add all predefined process identifiers
    for (final EPredefinedProcessIdentifier eProcID : EPredefinedProcessIdentifier.values ())
      s_aCodes.put (eProcID.getValue (), eProcID);
  }

  @PresentForCodeCoverage
  private static final PredefinedProcessIdentifierManager s_aInstance = new PredefinedProcessIdentifierManager ();

  private PredefinedProcessIdentifierManager ()
  {}

  /**
   * @return A non-<code>null</code> list of all PEPPOL process identifiers.
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public static ICommonsCollection <IPeppolPredefinedProcessIdentifier> getAllProcessIdentifiers ()
  {
    return s_aCodes.copyOfValues ();
  }

  /**
   * @return A non-<code>null</code> list of all PEPPOL process identifier IDs.
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public static ICommonsSet <String> getAllProcessIdentifierIDs ()
  {
    return s_aCodes.copyOfKeySet ();
  }

  /**
   * Find the process identifier with the given ID. This search is done case
   * insensitive.
   *
   * @param sProcIDValue
   *        The value to search. Without any identifier scheme! May be
   *        <code>null</code>.
   * @return <code>null</code> if no such process identifier exists.
   */
  @Nullable
  public static IPeppolPredefinedProcessIdentifier getProcessIdentifierOfID (@Nullable final String sProcIDValue)
  {
    if (sProcIDValue != null)
      for (final Map.Entry <String, IPeppolPredefinedProcessIdentifier> aEntry : s_aCodes.entrySet ())
      {
        // PEPPOL: Case sensitive comparison
        if (sProcIDValue.equals (aEntry.getKey ()))
          return aEntry.getValue ();
      }
    return null;
  }

  /**
   * Check if a process identifier with the given ID exists.
   *
   * @param sProcIDValue
   *        The value to search. Without any identifier scheme! May be
   *        <code>null</code>.
   * @return <code>true</code> if such a process identifier exists,
   *         <code>false</code> otherwise.
   */
  public static boolean containsProcessIdentifierWithID (@Nullable final String sProcIDValue)
  {
    return getProcessIdentifierOfID (sProcIDValue) != null;
  }
}
