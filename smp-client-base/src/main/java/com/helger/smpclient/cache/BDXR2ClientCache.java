/*
 * Copyright (C) 2015-2026 Philip Helger (www.helger.com)
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
package com.helger.smpclient.cache;

import java.time.Duration;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.CheckForSigned;
import com.helger.annotation.concurrent.GuardedBy;
import com.helger.annotation.concurrent.ThreadSafe;
import com.helger.base.concurrent.SimpleReadWriteLock;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.xsds.bdxr.smp2.ServiceGroupType;
import com.helger.xsds.bdxr.smp2.ServiceMetadataType;

/**
 * A shareable cache for OASIS BDXR SMP v2 Service Group and Service Metadata objects. See
 * {@link AbstractSMPClientCache} for the details.
 *
 * @author Philip Helger
 * @since 13.0.0
 */
@ThreadSafe
public class BDXR2ClientCache extends AbstractSMPClientCache <ServiceGroupType, ServiceMetadataType>
{
  /** The statistics and log name of the internal Service Group cache */
  public static final String CACHE_NAME_SERVICE_GROUP = "bdxr2-smp-client$ServiceGroup";
  /** The statistics and log name of the internal Service Metadata cache */
  public static final String CACHE_NAME_SERVICE_METADATA = "bdxr2-smp-client$ServiceMetadata";

  private static final SimpleReadWriteLock RW_LOCK = new SimpleReadWriteLock ();
  @GuardedBy ("RW_LOCK")
  private static BDXR2ClientCache s_aDefaultInstance = new BDXR2ClientCache ();

  /**
   * Constructor using {@link #DEFAULT_CACHE_TTL} and {@link #DEFAULT_MAX_SIZE}.
   */
  public BDXR2ClientCache ()
  {
    super (CACHE_NAME_SERVICE_GROUP, CACHE_NAME_SERVICE_METADATA);
  }

  /**
   * Constructor without background eviction. Expired entries are removed when they are read the
   * next time, or when {@link #evictExpired()} is called.
   *
   * @param aCacheTTL
   *        The time to live of each cache entry. May not be <code>null</code> and must be positive.
   * @param nMaxSize
   *        The maximum number of entries of each of the two internal caches. All values &le; 0
   *        indicate an unlimited size.
   */
  public BDXR2ClientCache (@NonNull final Duration aCacheTTL, @CheckForSigned final int nMaxSize)
  {
    this (aCacheTTL, nMaxSize, null);
  }

  /**
   * Constructor.
   *
   * @param aCacheTTL
   *        The time to live of each cache entry. May not be <code>null</code> and must be positive.
   * @param nMaxSize
   *        The maximum number of entries of each of the two internal caches. All values &le; 0
   *        indicate an unlimited size.
   * @param aEvictionInterval
   *        The interval in which expired entries are actively removed by the shared eviction
   *        scheduler thread. May be <code>null</code>, zero or negative to disable background
   *        eviction.
   */
  public BDXR2ClientCache (@NonNull final Duration aCacheTTL,
                           @CheckForSigned final int nMaxSize,
                           @Nullable final Duration aEvictionInterval)
  {
    super (aCacheTTL, nMaxSize, aEvictionInterval, CACHE_NAME_SERVICE_GROUP, CACHE_NAME_SERVICE_METADATA);
  }

  /**
   * @return The default instance to be used, if no dedicated cache was provided. Never
   *         <code>null</code>.
   */
  @NonNull
  public static BDXR2ClientCache getDefaultInstance ()
  {
    return RW_LOCK.readLockedGet ( () -> s_aDefaultInstance);
  }

  /**
   * Set the default instance to be used, if no dedicated cache was provided.
   *
   * @param aDefaultInstance
   *        The new default instance. May not be <code>null</code>.
   * @return The previous default instance. Never <code>null</code>.
   */
  @NonNull
  public static BDXR2ClientCache setDefaultInstance (@NonNull final BDXR2ClientCache aDefaultInstance)
  {
    ValueEnforcer.notNull (aDefaultInstance, "DefaultInstance");

    return RW_LOCK.writeLockedGet ( () -> {
      final BDXR2ClientCache aOld = s_aDefaultInstance;
      s_aDefaultInstance = aDefaultInstance;
      return aOld;
    });
  }
}
