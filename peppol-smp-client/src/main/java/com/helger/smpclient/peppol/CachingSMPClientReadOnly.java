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

import java.net.URI;
import java.time.Duration;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.Nonempty;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.datetime.expiration.ExpiringObject;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.smpclient.exception.SMPClientException;
import com.helger.smpclient.redirect.ISMPFollowRedirectCallback;
import com.helger.smpclient.url.ISMPURLProvider;
import com.helger.smpclient.url.SMPDNSResolutionException;
import com.helger.statistics.api.IMutableStatisticsHandlerCache;
import com.helger.statistics.api.IMutableStatisticsHandlerCounter;
import com.helger.statistics.impl.StatisticsManager;
import com.helger.xsds.peppol.smp1.ServiceGroupType;
import com.helger.xsds.peppol.smp1.SignedServiceMetadataType;

/**
 * A caching wrapper around {@link SMPClientReadOnly} that caches the results of
 * {@link #getServiceGroup(IParticipantIdentifier)} and
 * {@link #getServiceMetadata(IParticipantIdentifier, IDocumentTypeIdentifier, ISMPFollowRedirectCallback)}
 * in memory with a configurable TTL (time-to-live).
 * <p>
 * This is useful in high-throughput scenarios where repeated lookups for the same participant or
 * document type would otherwise result in unnecessary HTTP requests to the SMP server.
 * </p>
 * <p>
 * Important notes:
 * </p>
 * <ul>
 * <li>Exceptions (failures) are NOT cached — only successful responses are stored.</li>
 * <li>The {@link ISMPFollowRedirectCallback} will NOT be invoked on cache hits for
 * {@link #getServiceMetadata(IParticipantIdentifier, IDocumentTypeIdentifier, ISMPFollowRedirectCallback)}.</li>
 * <li>The cached JAXB objects are mutable. Callers should NOT modify the returned objects, as
 * modifications would affect all subsequent cache reads.</li>
 * </ul>
 *
 * @author Philip Helger
 * @since 12.3.10
 */
public class CachingSMPClientReadOnly extends SMPClientReadOnly
{
  /** Default cache TTL: 15 minutes */
  public static final Duration DEFAULT_CACHE_TTL = Duration.ofMinutes (15);

  private static final Logger LOGGER = LoggerFactory.getLogger (CachingSMPClientReadOnly.class);

  private static final String STATISTICS_PREFIX = "CachingSMPClientReadOnly$";
  private static final IMutableStatisticsHandlerCache STATS_CACHE_SG = StatisticsManager.getCacheHandler (STATISTICS_PREFIX +
                                                                                                          "ServiceGroup$access");
  private static final IMutableStatisticsHandlerCache STATS_CACHE_SM = StatisticsManager.getCacheHandler (STATISTICS_PREFIX +
                                                                                                          "ServiceMetadata$access");
  private static final IMutableStatisticsHandlerCounter STATS_COUNT_CLEAR = StatisticsManager.getCounterHandler (STATISTICS_PREFIX +
                                                                                                                 "clear");

  private final ConcurrentHashMap <String, ExpiringObject <ServiceGroupType>> m_aServiceGroupCache = new ConcurrentHashMap <> ();
  private final ConcurrentHashMap <String, ExpiringObject <SignedServiceMetadataType>> m_aServiceMetadataCache = new ConcurrentHashMap <> ();
  private Duration m_aCacheTTL = DEFAULT_CACHE_TTL;

  /**
   * Constructor with SML lookup
   *
   * @param aURLProvider
   *        The URL provider to be used. May not be <code>null</code>.
   * @param aParticipantIdentifier
   *        The participant identifier to be used. Required to build the SMP access URI.
   * @param aSMLInfo
   *        The SML to be used. Required to build the SMP access URI.
   * @throws SMPDNSResolutionException
   *         if DNS resolution fails
   */
  public CachingSMPClientReadOnly (@NonNull final ISMPURLProvider aURLProvider,
                                   @NonNull final IParticipantIdentifier aParticipantIdentifier,
                                   @NonNull final ISMLInfo aSMLInfo) throws SMPDNSResolutionException
  {
    super (aURLProvider, aParticipantIdentifier, aSMLInfo);
  }

  /**
   * Constructor with SML lookup
   *
   * @param aURLProvider
   *        The URL provider to be used. May not be <code>null</code>.
   * @param aParticipantIdentifier
   *        The participant identifier to be used. Required to build the SMP access URI.
   * @param sSMLZoneName
   *        The SML DNS zone name to be used. Required to build the SMP access URI.
   * @throws SMPDNSResolutionException
   *         if DNS resolution fails
   */
  public CachingSMPClientReadOnly (@NonNull final ISMPURLProvider aURLProvider,
                                   @NonNull final IParticipantIdentifier aParticipantIdentifier,
                                   @NonNull @Nonempty final String sSMLZoneName) throws SMPDNSResolutionException
  {
    super (aURLProvider, aParticipantIdentifier, sSMLZoneName);
  }

  /**
   * Constructor with a direct SMP URL.
   *
   * @param aSMPHost
   *        The address of the SMP service.
   */
  public CachingSMPClientReadOnly (@NonNull final URI aSMPHost)
  {
    super (aSMPHost);
  }

  /**
   * Set the cache TTL (time-to-live) in milliseconds. Cached entries older than this value will be
   * considered expired and re-fetched from the SMP server.
   *
   * @param aCacheTTL
   *        The TTL to use. Must not be <code>null</code>.
   * @return this for chaining
   */
  @NonNull
  public CachingSMPClientReadOnly setCacheTTL (@NonNull final Duration aCacheTTL)
  {
    ValueEnforcer.notNull (aCacheTTL, "CacheTTL");
    m_aCacheTTL = aCacheTTL;
    return this;
  }

  /**
   * @return The current cache TTL. Never <code>null</code>. Defaults to {@link #DEFAULT_CACHE_TTL}.
   */
  public Duration getCacheTTL ()
  {
    return m_aCacheTTL;
  }

  @NonNull
  private static String _createServiceGroupCacheKey (@NonNull final IParticipantIdentifier aServiceGroupID)
  {
    return aServiceGroupID.getURIEncoded ().toLowerCase (Locale.ROOT);
  }

  @NonNull
  private static String _createServiceMetadataCacheKey (@NonNull final IParticipantIdentifier aServiceGroupID,
                                                        @NonNull final IDocumentTypeIdentifier aDocumentTypeID)
  {
    // Document type IDs are case sensitive
    return _createServiceGroupCacheKey (aServiceGroupID) + "$$" + aDocumentTypeID.getURIEncoded ();
  }

  @Override
  @NonNull
  public ServiceGroupType getServiceGroup (@NonNull final IParticipantIdentifier aServiceGroupID) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");

    final String sCacheKey = _createServiceGroupCacheKey (aServiceGroupID);

    // Check cache
    final ExpiringObject <ServiceGroupType> aEntry = m_aServiceGroupCache.get (sCacheKey);
    if (aEntry != null && !aEntry.isExpiredNow ())
    {
      STATS_CACHE_SG.cacheHit ();
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Cache hit for ServiceGroup '" + sCacheKey + "'");
      return aEntry.getObject ();
    }

    // Cache miss or expired — fetch from SMP
    STATS_CACHE_SG.cacheMiss ();
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Cache miss for ServiceGroup '" + sCacheKey + "' - fetching from SMP");

    final ServiceGroupType ret = super.getServiceGroup (aServiceGroupID);

    // Store in cache (only on success)
    m_aServiceGroupCache.put (sCacheKey, ExpiringObject.ofDuration (ret, m_aCacheTTL));

    return ret;
  }

  @Override
  @NonNull
  public SignedServiceMetadataType getServiceMetadata (@NonNull final IParticipantIdentifier aServiceGroupID,
                                                       @NonNull final IDocumentTypeIdentifier aDocumentTypeID,
                                                       @Nullable final ISMPFollowRedirectCallback aFollowRedirectCallback) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");
    ValueEnforcer.notNull (aDocumentTypeID, "DocumentTypeID");

    final String sCacheKey = _createServiceMetadataCacheKey (aServiceGroupID, aDocumentTypeID);

    // Check cache
    final ExpiringObject <SignedServiceMetadataType> aEntry = m_aServiceMetadataCache.get (sCacheKey);
    if (aEntry != null && !aEntry.isExpiredNow ())
    {
      STATS_CACHE_SM.cacheHit ();
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Cache hit for ServiceMetadata '" + sCacheKey + "'");
      // Note: ISMPFollowRedirectCallback is NOT invoked on cache hits
      return aEntry.getObject ();
    }

    // Cache miss or expired — fetch from SMP
    STATS_CACHE_SM.cacheMiss ();
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Cache miss for ServiceMetadata '" + sCacheKey + "' - fetching from SMP");

    final SignedServiceMetadataType ret = super.getServiceMetadata (aServiceGroupID,
                                                                    aDocumentTypeID,
                                                                    aFollowRedirectCallback);

    // Store in cache (only on success)
    m_aServiceMetadataCache.put (sCacheKey, ExpiringObject.ofDuration (ret, m_aCacheTTL));

    return ret;
  }

  /**
   * Clear all cached entries (both service groups and service metadata).
   */
  public void clearCache ()
  {
    m_aServiceGroupCache.clear ();
    m_aServiceMetadataCache.clear ();
    STATS_COUNT_CLEAR.increment ();
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Cleared all SMP client caches");
  }

  /**
   * Clear the cached service group for a specific participant.
   *
   * @param aParticipantID
   *        The participant identifier. May not be <code>null</code>.
   */
  public void clearServiceGroupCache (@NonNull final IParticipantIdentifier aParticipantID)
  {
    ValueEnforcer.notNull (aParticipantID, "ParticipantID");

    m_aServiceGroupCache.remove (_createServiceGroupCacheKey (aParticipantID));
  }

  /**
   * Clear the cached service metadata for a specific participant and document type.
   *
   * @param aParticipantID
   *        The participant identifier. May not be <code>null</code>.
   * @param aDocumentTypeID
   *        The document type identifier. May not be <code>null</code>.
   */
  public void clearServiceMetadataCache (@NonNull final IParticipantIdentifier aParticipantID,
                                         @NonNull final IDocumentTypeIdentifier aDocumentTypeID)
  {
    ValueEnforcer.notNull (aParticipantID, "ParticipantID");
    ValueEnforcer.notNull (aDocumentTypeID, "DocumentTypeID");

    m_aServiceMetadataCache.remove (_createServiceMetadataCacheKey (aParticipantID, aDocumentTypeID));
  }

  /**
   * Clear all cached service metadata entries for a specific participant. This removes all document
   * type entries for the given participant from the service metadata cache.
   *
   * @param aParticipantID
   *        The participant identifier. May not be <code>null</code>.
   */
  public void clearServiceMetadataCacheOfParticipant (@NonNull final IParticipantIdentifier aParticipantID)
  {
    ValueEnforcer.notNull (aParticipantID, "ParticipantID");

    final String sPrefix = _createServiceGroupCacheKey (aParticipantID) + "$$";
    final var it = m_aServiceMetadataCache.entrySet ().iterator ();
    while (it.hasNext ())
    {
      if (it.next ().getKey ().startsWith (sPrefix))
        it.remove ();
    }
  }
}
