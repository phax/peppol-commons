/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
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
