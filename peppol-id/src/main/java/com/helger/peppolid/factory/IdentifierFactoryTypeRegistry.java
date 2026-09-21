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
package com.helger.peppolid.factory;

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
 * A central registry for all identifier factory types of all networks. The content is filled via the SPI
 * interface {@link IIdentifierFactoryTypeProviderSPI} - each network provides its own transport
 * profiles - and can be extended at runtime.
 *
 * @author Philip Helger
 * @since 13.0.0
 */
@ThreadSafe
public final class IdentifierFactoryTypeRegistry
{
  private static final Logger LOGGER = LoggerFactory.getLogger (IdentifierFactoryTypeRegistry.class);
  private static final SimpleReadWriteLock RW_LOCK = new SimpleReadWriteLock ();

  @GuardedBy ("RW_LOCK")
  private static final ICommonsOrderedMap <String, IIdentifierFactoryType> MAP = new CommonsLinkedHashMap <> ();

  @PresentForCodeCoverage
  private static final IdentifierFactoryTypeRegistry INSTANCE = new IdentifierFactoryTypeRegistry ();

  static
  {
    reinitialize ();
  }

  private IdentifierFactoryTypeRegistry ()
  {}

  /**
   * Remove all registered identifier factory types and load them from the SPI implementations again.
   */
  public static void reinitialize ()
  {
    RW_LOCK.writeLocked ( () -> {
      MAP.clear ();
      for (final IIdentifierFactoryTypeProviderSPI aSPI : ServiceLoaderHelper.getAllSPIImplementations (IIdentifierFactoryTypeProviderSPI.class))
        for (final IIdentifierFactoryType aFactoryType : aSPI.getAllIdentifierFactoryTypes ())
        {
          final String sID = aFactoryType.getID ();
          final IIdentifierFactoryType aOld = MAP.put (sID, aFactoryType);
          if (aOld != null)
            LOGGER.warn ("The identifier factory type ID '" +
                         sID +
                         "' is provided by more then one SPI implementation - the last one wins");
        }
    });

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Reinitialized " + IdentifierFactoryTypeRegistry.class.getName () + " with " + getCount () + " items");
  }

  /**
   * Register a single identifier factory type at runtime. An already registered identifier factory type with
   * the same ID is not overwritten.
   *
   * @param aFactoryType
   *        The identifier factory type to be registered. May not be <code>null</code>.
   * @return {@link EChange#CHANGED} if the identifier factory type was registered.
   */
  @NonNull
  public static EChange registerIdentifierFactoryType (@NonNull final IIdentifierFactoryType aFactoryType)
  {
    ValueEnforcer.notNull (aFactoryType, "IdentifierFactoryType");

    final String sID = aFactoryType.getID ();
    return RW_LOCK.writeLockedGet ( () -> {
      if (MAP.containsKey (sID))
        return EChange.UNCHANGED;
      MAP.put (sID, aFactoryType);
      return EChange.CHANGED;
    });
  }

  /**
   * @param sID
   *        The identifier factory type ID to search. May be <code>null</code>.
   * @return <code>null</code> if no such identifier factory type is registered.
   */
  @Nullable
  public static IIdentifierFactoryType getIdentifierFactoryTypeOfIDOrNull (@Nullable final String sID)
  {
    if (sID == null)
      return null;
    return RW_LOCK.readLockedGet ( () -> MAP.get (sID));
  }

  /**
   * @param sID
   *        The identifier factory type ID to search. May be <code>null</code>.
   * @return <code>true</code> if a identifier factory type with the provided ID is registered.
   */
  public static boolean containsIdentifierFactoryTypeOfID (@Nullable final String sID)
  {
    return getIdentifierFactoryTypeOfIDOrNull (sID) != null;
  }

  /**
   * @return All registered identifier factory types in the order they were registered. Never
   *         <code>null</code>.
   */
  @NonNull
  @ReturnsMutableCopy
  public static ICommonsList <IIdentifierFactoryType> getAllIdentifierFactoryTypes ()
  {
    return RW_LOCK.readLockedGet ( () -> MAP.copyOfValues ());
  }

  /**
   * @return The IDs of all registered identifier factory types in the order they were registered. Never
   *         <code>null</code>.
   */
  @NonNull
  @ReturnsMutableCopy
  public static ICommonsSet <String> getAllIdentifierFactoryTypeIDs ()
  {
    return RW_LOCK.readLockedGet ( () -> MAP.copyOfKeySet ());
  }

  /**
   * @return The number of registered identifier factory types. Always &ge; 0.
   */
  @Nonnegative
  public static int getCount ()
  {
    return RW_LOCK.readLockedInt (MAP::size);
  }
}
