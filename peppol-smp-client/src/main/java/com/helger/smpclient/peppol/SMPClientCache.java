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
package com.helger.smpclient.peppol;

import java.time.Duration;
import java.util.Locale;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.CheckForSigned;
import com.helger.annotation.Nonempty;
import com.helger.annotation.Nonnegative;
import com.helger.annotation.concurrent.GuardedBy;
import com.helger.annotation.concurrent.ThreadSafe;
import com.helger.base.concurrent.SimpleReadWriteLock;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.equals.EqualsHelper;
import com.helger.base.state.EChange;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.cache.impl.ManualCache;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.xsds.peppol.smp1.ServiceGroupType;
import com.helger.xsds.peppol.smp1.SignedServiceMetadataType;

/**
 * A shareable cache for Peppol SMP Service Group and Service Metadata objects, as used by
 * {@link CachingSMPClientReadOnly}.
 * <p>
 * Because an {@link SMPClientReadOnly} instance is usually bound to a single receiver participant
 * (the SMP host URI is the result of an SML/NAPTR lookup), callers tend to create a new SMP client
 * per message. Therefore this cache is deliberately <em>not</em> bound to a single client instance:
 * all cache keys contain the SMP host URI, so that one cache instance can safely be shared between
 * arbitrary many clients, participants and SMP hosts. If no cache is provided to a
 * {@link CachingSMPClientReadOnly}, the static default instance of this class is used, so that even
 * per-message clients share their cache content.
 * </p>
 * <p>
 * Important notes:
 * </p>
 * <ul>
 * <li>Only successful responses are stored - failures (exceptions) are never cached.</li>
 * <li>The cached JAXB objects are mutable. Callers should NOT modify the returned objects, as
 * modifications would affect all subsequent cache reads.</li>
 * </ul>
 *
 * @author Philip Helger
 * @since 12.7.0
 */
@ThreadSafe
public class SMPClientCache
{
  /** Default cache TTL: 15 minutes */
  public static final Duration DEFAULT_CACHE_TTL = Duration.ofMinutes (15);
  /** Default maximum number of entries per internal cache: 1000 */
  public static final int DEFAULT_MAX_SIZE = 1000;

  /** The statistics and log name of the internal Service Group cache */
  public static final String CACHE_NAME_SERVICE_GROUP = "peppol-smp-client$ServiceGroup";
  /** The statistics and log name of the internal Service Metadata cache */
  public static final String CACHE_NAME_SERVICE_METADATA = "peppol-smp-client$ServiceMetadata";

  private static final Logger LOGGER = LoggerFactory.getLogger (SMPClientCache.class);

  private static final SimpleReadWriteLock RW_LOCK = new SimpleReadWriteLock ();
  @GuardedBy ("RW_LOCK")
  private static SMPClientCache s_aDefaultInstance = new SMPClientCache ();

  private final ManualCache <String, ServiceGroupType> m_aServiceGroupCache;
  private final ManualCache <String, SignedServiceMetadataType> m_aServiceMetadataCache;

  /**
   * Constructor using {@link #DEFAULT_CACHE_TTL} and {@link #DEFAULT_MAX_SIZE}.
   */
  public SMPClientCache ()
  {
    this (DEFAULT_CACHE_TTL, DEFAULT_MAX_SIZE);
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
  public SMPClientCache (@NonNull final Duration aCacheTTL, @CheckForSigned final int nMaxSize)
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
  public SMPClientCache (@NonNull final Duration aCacheTTL,
                         @CheckForSigned final int nMaxSize,
                         @Nullable final Duration aEvictionInterval)
  {
    ValueEnforcer.notNull (aCacheTTL, "CacheTTL");
    ValueEnforcer.isTrue (() -> !aCacheTTL.isZero () && !aCacheTTL.isNegative (),
                          "CacheTTL must be a positive Duration");

    m_aServiceGroupCache = ManualCache.<String, ServiceGroupType> builder ()
                                      .name (CACHE_NAME_SERVICE_GROUP)
                                      .maxSize (nMaxSize)
                                      .expireAfterWrite (aCacheTTL)
                                      .evictionInterval (aEvictionInterval)
                                      .build ();
    m_aServiceMetadataCache = ManualCache.<String, SignedServiceMetadataType> builder ()
                                         .name (CACHE_NAME_SERVICE_METADATA)
                                         .maxSize (nMaxSize)
                                         .expireAfterWrite (aCacheTTL)
                                         .evictionInterval (aEvictionInterval)
                                         .build ();
  }

  /**
   * @return The time to live of each cache entry, as provided in the constructor. Never
   *         <code>null</code>.
   */
  @NonNull
  public final Duration getCacheTTL ()
  {
    // Both caches use the same TTL
    return m_aServiceGroupCache.getTimeToLive ();
  }

  /**
   * @return The maximum number of entries of each of the two internal caches, as provided in the
   *         constructor. Values &le; 0 indicate an unlimited size.
   */
  @CheckForSigned
  public final int getMaxSize ()
  {
    return m_aServiceGroupCache.getMaxSize ();
  }

  /**
   * Create the cache key of a Service Group. Peppol participant identifiers are case insensitive,
   * so they are unified to lowercase.
   *
   * @param sSMPHostURI
   *        The SMP host URI the query is performed on. May neither be <code>null</code> nor empty.
   * @param aServiceGroupID
   *        The participant identifier to be queried. May not be <code>null</code>.
   * @return The cache key to be used. Neither <code>null</code> nor empty.
   */
  @NonNull
  @Nonempty
  public static String createServiceGroupCacheKey (@NonNull @Nonempty final String sSMPHostURI,
                                                   @NonNull final IParticipantIdentifier aServiceGroupID)
  {
    ValueEnforcer.notEmpty (sSMPHostURI, "SMPHostURI");
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");

    return sSMPHostURI + aServiceGroupID.getURIEncoded ().toLowerCase (Locale.ROOT);
  }

  /**
   * @param sSMPHostURI
   *        The SMP host URI the query is performed on. May neither be <code>null</code> nor empty.
   * @param aServiceGroupID
   *        The participant identifier to be queried. May not be <code>null</code>.
   * @return The common prefix of all Service Metadata cache keys of a single participant on a
   *         single SMP host.
   */
  @NonNull
  @Nonempty
  private static String _createServiceMetadataCacheKeyPrefix (@NonNull @Nonempty final String sSMPHostURI,
                                                              @NonNull final IParticipantIdentifier aServiceGroupID)
  {
    return createServiceGroupCacheKey (sSMPHostURI, aServiceGroupID) + "$$";
  }

  /**
   * Create the cache key of a Service Metadata object.
   *
   * @param sSMPHostURI
   *        The SMP host URI the query is performed on. May neither be <code>null</code> nor empty.
   * @param aServiceGroupID
   *        The participant identifier to be queried. May not be <code>null</code>.
   * @param aDocumentTypeID
   *        The document type identifier to be queried. May not be <code>null</code>.
   * @return The cache key to be used. Neither <code>null</code> nor empty.
   */
  @NonNull
  @Nonempty
  public static String createServiceMetadataCacheKey (@NonNull @Nonempty final String sSMPHostURI,
                                                      @NonNull final IParticipantIdentifier aServiceGroupID,
                                                      @NonNull final IDocumentTypeIdentifier aDocumentTypeID)
  {
    ValueEnforcer.notNull (aDocumentTypeID, "DocumentTypeID");

    // Document type IDs are case sensitive
    return _createServiceMetadataCacheKeyPrefix (sSMPHostURI, aServiceGroupID) + aDocumentTypeID.getURIEncoded ();
  }

  /**
   * Get the cached Service Group of the provided participant.
   *
   * @param sSMPHostURI
   *        The SMP host URI the query is performed on. May neither be <code>null</code> nor empty.
   * @param aServiceGroupID
   *        The participant identifier to be queried. May not be <code>null</code>.
   * @return <code>null</code> if the object is not in the cache or if the cached object is expired.
   */
  @Nullable
  public ServiceGroupType getServiceGroup (@NonNull @Nonempty final String sSMPHostURI,
                                           @NonNull final IParticipantIdentifier aServiceGroupID)
  {
    return m_aServiceGroupCache.getFromCache (createServiceGroupCacheKey (sSMPHostURI, aServiceGroupID));
  }

  /**
   * Put the provided Service Group into the cache.
   *
   * @param sSMPHostURI
   *        The SMP host URI the query was performed on. May neither be <code>null</code> nor empty.
   * @param aServiceGroupID
   *        The participant identifier that was queried. May not be <code>null</code>.
   * @param aServiceGroup
   *        The Service Group to be cached. May not be <code>null</code>.
   */
  public void putServiceGroup (@NonNull @Nonempty final String sSMPHostURI,
                               @NonNull final IParticipantIdentifier aServiceGroupID,
                               @NonNull final ServiceGroupType aServiceGroup)
  {
    ValueEnforcer.notNull (aServiceGroup, "ServiceGroup");

    m_aServiceGroupCache.putInCache (createServiceGroupCacheKey (sSMPHostURI, aServiceGroupID), aServiceGroup);
  }

  /**
   * Get the cached Service Metadata of the provided participant and document type.
   *
   * @param sSMPHostURI
   *        The SMP host URI the query is performed on. May neither be <code>null</code> nor empty.
   * @param aServiceGroupID
   *        The participant identifier to be queried. May not be <code>null</code>.
   * @param aDocumentTypeID
   *        The document type identifier to be queried. May not be <code>null</code>.
   * @return <code>null</code> if the object is not in the cache or if the cached object is expired.
   */
  @Nullable
  public SignedServiceMetadataType getServiceMetadata (@NonNull @Nonempty final String sSMPHostURI,
                                                       @NonNull final IParticipantIdentifier aServiceGroupID,
                                                       @NonNull final IDocumentTypeIdentifier aDocumentTypeID)
  {
    return m_aServiceMetadataCache.getFromCache (createServiceMetadataCacheKey (sSMPHostURI,
                                                                                aServiceGroupID,
                                                                                aDocumentTypeID));
  }

  /**
   * Put the provided Service Metadata object into the cache.
   *
   * @param sSMPHostURI
   *        The SMP host URI the query was performed on. May neither be <code>null</code> nor empty.
   * @param aServiceGroupID
   *        The participant identifier that was queried. May not be <code>null</code>.
   * @param aDocumentTypeID
   *        The document type identifier that was queried. May not be <code>null</code>.
   * @param aServiceMetadata
   *        The Service Metadata object to be cached. May not be <code>null</code>.
   */
  public void putServiceMetadata (@NonNull @Nonempty final String sSMPHostURI,
                                  @NonNull final IParticipantIdentifier aServiceGroupID,
                                  @NonNull final IDocumentTypeIdentifier aDocumentTypeID,
                                  @NonNull final SignedServiceMetadataType aServiceMetadata)
  {
    ValueEnforcer.notNull (aServiceMetadata, "ServiceMetadata");

    m_aServiceMetadataCache.putInCache (createServiceMetadataCacheKey (sSMPHostURI, aServiceGroupID, aDocumentTypeID),
                                        aServiceMetadata);
  }

  /**
   * Remove the cached Service Group of a single participant on a single SMP host.
   *
   * @param sSMPHostURI
   *        The SMP host URI in question. May neither be <code>null</code> nor empty.
   * @param aServiceGroupID
   *        The participant identifier in question. May not be <code>null</code>.
   * @return {@link EChange#CHANGED} if something was removed, {@link EChange#UNCHANGED} otherwise.
   */
  @NonNull
  public EChange removeServiceGroup (@NonNull @Nonempty final String sSMPHostURI,
                                     @NonNull final IParticipantIdentifier aServiceGroupID)
  {
    return m_aServiceGroupCache.removeFromCache (createServiceGroupCacheKey (sSMPHostURI, aServiceGroupID));
  }

  /**
   * Remove the cached Service Metadata object of a single participant and document type on a single
   * SMP host.
   *
   * @param sSMPHostURI
   *        The SMP host URI in question. May neither be <code>null</code> nor empty.
   * @param aServiceGroupID
   *        The participant identifier in question. May not be <code>null</code>.
   * @param aDocumentTypeID
   *        The document type identifier in question. May not be <code>null</code>.
   * @return {@link EChange#CHANGED} if something was removed, {@link EChange#UNCHANGED} otherwise.
   */
  @NonNull
  public EChange removeServiceMetadata (@NonNull @Nonempty final String sSMPHostURI,
                                        @NonNull final IParticipantIdentifier aServiceGroupID,
                                        @NonNull final IDocumentTypeIdentifier aDocumentTypeID)
  {
    return m_aServiceMetadataCache.removeFromCache (createServiceMetadataCacheKey (sSMPHostURI,
                                                                                   aServiceGroupID,
                                                                                   aDocumentTypeID));
  }

  /**
   * Remove all cached Service Metadata objects of a single participant on a single SMP host,
   * independent of the document type.
   *
   * @param sSMPHostURI
   *        The SMP host URI in question. May neither be <code>null</code> nor empty.
   * @param aServiceGroupID
   *        The participant identifier in question. May not be <code>null</code>.
   * @return The number of removed cache entries. Always &ge; 0.
   */
  @Nonnegative
  public int removeAllServiceMetadataOfParticipant (@NonNull @Nonempty final String sSMPHostURI,
                                                    @NonNull final IParticipantIdentifier aServiceGroupID)
  {
    final String sPrefix = _createServiceMetadataCacheKeyPrefix (sSMPHostURI, aServiceGroupID);
    // Note: a participant identifier that itself contains "$$" could theoretically lead to the
    // removal of another participants entry - the only effect of that is an unnecessary SMP query
    return m_aServiceMetadataCache.removeFromCacheIf (x -> x.startsWith (sPrefix));
  }

  /**
   * Remove all cached Service Groups and Service Metadata objects of a single SMP host.
   *
   * @param sSMPHostURI
   *        The SMP host URI in question. May neither be <code>null</code> nor empty.
   * @return The number of removed cache entries of both internal caches. Always &ge; 0.
   */
  @Nonnegative
  public int removeAllOfSMPHost (@NonNull @Nonempty final String sSMPHostURI)
  {
    ValueEnforcer.notEmpty (sSMPHostURI, "SMPHostURI");

    return m_aServiceGroupCache.removeFromCacheIf (x -> x.startsWith (sSMPHostURI)) +
           m_aServiceMetadataCache.removeFromCacheIf (x -> x.startsWith (sSMPHostURI));
  }

  /**
   * Remove all cached entries (both Service Groups and Service Metadata objects) of all SMP hosts.
   */
  public void clearCache ()
  {
    m_aServiceGroupCache.clearCache ();
    m_aServiceMetadataCache.clearCache ();
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Cleared all SMP client caches");
  }

  /**
   * Remove all entries that are already expired but were not yet evicted. This is only needed if no
   * eviction interval was provided in the constructor.
   *
   * @return The number of removed cache entries of both internal caches. Always &ge; 0.
   */
  @Nonnegative
  public int evictExpired ()
  {
    return m_aServiceGroupCache.evictExpired () + m_aServiceMetadataCache.evictExpired ();
  }

  /**
   * @return The number of cached Service Groups, including the ones that are expired but not yet
   *         evicted. Always &ge; 0.
   */
  @Nonnegative
  public int getServiceGroupCacheSize ()
  {
    return m_aServiceGroupCache.size ();
  }

  /**
   * @return The number of cached Service Metadata objects, including the ones that are expired but
   *         not yet evicted. Always &ge; 0.
   */
  @Nonnegative
  public int getServiceMetadataCacheSize ()
  {
    return m_aServiceMetadataCache.size ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("ServiceGroupCache", m_aServiceGroupCache)
                                       .append ("ServiceMetadataCache", m_aServiceMetadataCache)
                                       .getToString ();
  }

  /**
   * @return The default cache instance that is used by all {@link CachingSMPClientReadOnly}
   *         instances that have no specific cache assigned. Never <code>null</code>.
   */
  @NonNull
  public static SMPClientCache getDefaultInstance ()
  {
    return RW_LOCK.readLockedGet (() -> s_aDefaultInstance);
  }

  /**
   * Overwrite the default cache instance to be used by all {@link CachingSMPClientReadOnly}
   * instances that have no specific cache assigned. This is the preferred way to change the cache
   * TTL or the maximum cache size globally.
   *
   * @param aDefaultInstance
   *        The new default cache instance to use. May not be <code>null</code>.
   * @return The previous default cache instance. Never <code>null</code>.
   */
  @NonNull
  public static SMPClientCache setDefaultInstance (@NonNull final SMPClientCache aDefaultInstance)
  {
    ValueEnforcer.notNull (aDefaultInstance, "DefaultInstance");

    final SMPClientCache ret;
    RW_LOCK.writeLock ().lock ();
    try
    {
      ret = s_aDefaultInstance;
      s_aDefaultInstance = aDefaultInstance;
    }
    finally
    {
      RW_LOCK.writeLock ().unlock ();
    }

    if (EqualsHelper.identityDifferent (ret, aDefaultInstance))
      LOGGER.info ("The default SMP client cache was changed to " + aDefaultInstance);
    return ret;
  }
}
