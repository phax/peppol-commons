/*
 * Copyright (C) 2015-2025 Philip Helger
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
package com.helger.peppolid.peppol.process;

import java.util.Map;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.PresentForCodeCoverage;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.string.StringHelper;
import com.helger.collection.commons.CommonsHashMap;
import com.helger.collection.commons.ICommonsCollection;
import com.helger.collection.commons.ICommonsMap;
import com.helger.collection.commons.ICommonsSet;
import com.helger.peppolid.CIdentifier;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;

/**
 * This class manages the predefined Peppol process identifiers the
 * <b>cenbii-procid-ubl</b> scheme. This class provides sanity methods around
 * {@link EPredefinedProcessIdentifier} which would be to bogus to generate
 * them.
 *
 * @author Philip Helger
 */
@Immutable
public final class PredefinedProcessIdentifierManager
{
  private static final ICommonsMap <String, IPeppolPredefinedProcessIdentifier> CODES = new CommonsHashMap <> ();

  static
  {
    // Add all predefined process identifiers
    for (final EPredefinedProcessIdentifier eProcID : EPredefinedProcessIdentifier.values ())
      CODES.put (eProcID.getURIEncoded (), eProcID);
  }

  @PresentForCodeCoverage
  private static final PredefinedProcessIdentifierManager INSTANCE = new PredefinedProcessIdentifierManager ();

  private PredefinedProcessIdentifierManager ()
  {}

  /**
   * @return A non-<code>null</code> list of all Peppol process identifiers.
   */
  @NonNull
  @Nonempty
  @ReturnsMutableCopy
  public static ICommonsCollection <IPeppolPredefinedProcessIdentifier> getAllProcessIdentifiers ()
  {
    return CODES.copyOfValues ();
  }

  /**
   * @return A non-<code>null</code> list of all Peppol process identifier IDs.
   *         Since v9.2.4 this include the identifier scheme.
   */
  @NonNull
  @Nonempty
  @ReturnsMutableCopy
  public static ICommonsSet <String> getAllProcessIdentifierIDs ()
  {
    return CODES.copyOfKeySet ();
  }

  /**
   * Find the process identifier with the given ID. This search is done case
   * insensitive.
   *
   * @param sProcID
   *        The URI encoded process ID or the process value to search. May or
   *        may not include the scheme ID. May be <code>null</code>.
   * @return <code>null</code> if no such process identifier exists.
   */
  @Nullable
  public static IPeppolPredefinedProcessIdentifier getProcessIdentifierOfID (@Nullable final String sProcID)
  {
    if (StringHelper.isNotEmpty (sProcID))
    {
      // make sure a scheme is present
      final String sRealProcID = sProcID.contains ("::") ? sProcID
                                                         : CIdentifier.getURIEncoded (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME,
                                                                                      sProcID);

      for (final Map.Entry <String, IPeppolPredefinedProcessIdentifier> aEntry : CODES.entrySet ())
      {
        // Peppol: Case sensitive comparison
        if (sRealProcID.equals (aEntry.getKey ()))
          return aEntry.getValue ();
      }
    }
    return null;
  }

  /**
   * Check if a process identifier with the given ID exists.
   *
   * @param sProcID
   *        The URI encoded process ID or the process value to search. May or
   *        may not include the scheme ID. May be <code>null</code>.
   * @return <code>true</code> if such a process identifier exists,
   *         <code>false</code> otherwise.
   */
  public static boolean containsProcessIdentifierWithID (@Nullable final String sProcID)
  {
    return getProcessIdentifierOfID (sProcID) != null;
  }
}
