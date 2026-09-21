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
package com.helger.network.smp;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.Nonnegative;
import com.helger.annotation.concurrent.GuardedBy;
import com.helger.annotation.concurrent.ThreadSafe;
import com.helger.annotation.style.PresentForCodeCoverage;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.concurrent.SimpleReadWriteLock;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.spi.ServiceLoaderHelper;
import com.helger.base.state.EChange;
import com.helger.collection.commons.CommonsLinkedHashMap;
import com.helger.collection.commons.ICommonsList;
import com.helger.collection.commons.ICommonsOrderedMap;
import com.helger.collection.commons.ICommonsSet;

/**
 * A central registry for all transport profiles of all networks. The content is filled via the SPI
 * interface {@link ISMPTransportProfileProviderSPI} - each network provides its own transport
 * profiles - and can be extended at runtime.
 *
 * @author Philip Helger
 * @since 13.0.0
 */
@ThreadSafe
public final class SMPTransportProfileRegistry
{
  private static final Logger LOGGER = LoggerFactory.getLogger (SMPTransportProfileRegistry.class);
  private static final SimpleReadWriteLock RW_LOCK = new SimpleReadWriteLock ();

  @GuardedBy ("RW_LOCK")
  private static final ICommonsOrderedMap <String, ISMPTransportProfile> MAP = new CommonsLinkedHashMap <> ();

  @PresentForCodeCoverage
  private static final SMPTransportProfileRegistry INSTANCE = new SMPTransportProfileRegistry ();

  static
  {
    reinitialize ();
  }

  private SMPTransportProfileRegistry ()
  {}

  /**
   * Remove all registered transport profiles and load them from the SPI implementations again.
   */
  public static void reinitialize ()
  {
    RW_LOCK.writeLocked ( () -> {
      MAP.clear ();
      for (final ISMPTransportProfileProviderSPI aSPI : ServiceLoaderHelper.getAllSPIImplementations (ISMPTransportProfileProviderSPI.class))
        for (final ISMPTransportProfile aTransportProfile : aSPI.getAllTransportProfiles ())
        {
          final String sID = aTransportProfile.getID ();
          final ISMPTransportProfile aOld = MAP.put (sID, aTransportProfile);
          if (aOld != null)
            LOGGER.warn ("The transport profile ID '" +
                         sID +
                         "' is provided by more then one SPI implementation - the last one wins");
        }
    });

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Reinitialized " + SMPTransportProfileRegistry.class.getName () + " with " + getCount () + " items");
  }

  /**
   * Register a single transport profile at runtime. An already registered transport profile with
   * the same ID is not overwritten.
   *
   * @param aTransportProfile
   *        The transport profile to be registered. May not be <code>null</code>.
   * @return {@link EChange#CHANGED} if the transport profile was registered.
   */
  @NonNull
  public static EChange registerTransportProfile (@NonNull final ISMPTransportProfile aTransportProfile)
  {
    ValueEnforcer.notNull (aTransportProfile, "TransportProfile");

    final String sID = aTransportProfile.getID ();
    return RW_LOCK.writeLockedGet ( () -> {
      if (MAP.containsKey (sID))
        return EChange.UNCHANGED;
      MAP.put (sID, aTransportProfile);
      return EChange.CHANGED;
    });
  }

  /**
   * @param sID
   *        The transport profile ID to search. May be <code>null</code>.
   * @return <code>null</code> if no such transport profile is registered.
   */
  @Nullable
  public static ISMPTransportProfile getTransportProfileOfIDOrNull (@Nullable final String sID)
  {
    if (sID == null)
      return null;
    return RW_LOCK.readLockedGet ( () -> MAP.get (sID));
  }

  /**
   * @param sID
   *        The transport profile ID to search. May be <code>null</code>.
   * @return <code>true</code> if a transport profile with the provided ID is registered.
   */
  public static boolean containsTransportProfileOfID (@Nullable final String sID)
  {
    return getTransportProfileOfIDOrNull (sID) != null;
  }

  /**
   * @return All registered transport profiles in the order they were registered. Never
   *         <code>null</code>.
   */
  @NonNull
  @ReturnsMutableCopy
  public static ICommonsList <ISMPTransportProfile> getAllTransportProfiles ()
  {
    return RW_LOCK.readLockedGet ( () -> MAP.copyOfValues ());
  }

  /**
   * @return The IDs of all registered transport profiles in the order they were registered. Never
   *         <code>null</code>.
   */
  @NonNull
  @ReturnsMutableCopy
  public static ICommonsSet <String> getAllTransportProfileIDs ()
  {
    return RW_LOCK.readLockedGet ( () -> MAP.copyOfKeySet ());
  }

  /**
   * @return The number of registered transport profiles. Always &ge; 0.
   */
  @Nonnegative
  public static int getCount ()
  {
    return RW_LOCK.readLockedInt (MAP::size);
  }
}
