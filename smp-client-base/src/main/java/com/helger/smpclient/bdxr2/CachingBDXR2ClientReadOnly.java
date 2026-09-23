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
package com.helger.smpclient.bdxr2;

import java.net.URI;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.Nonempty;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.edelivery.sml.ISMLBase;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.smpclient.cache.BDXR2ClientCache;
import com.helger.smpclient.exception.SMPClientException;
import com.helger.smpclient.redirect.ISMPFollowRedirectCallback;
import com.helger.smpclient.url.ISMPURLProvider;
import com.helger.smpclient.url.SMPDNSResolutionException;
import com.helger.xsds.bdxr.smp2.ServiceGroupType;
import com.helger.xsds.bdxr.smp2.ServiceMetadataType;

/**
 * A caching wrapper around {@link BDXR2ClientReadOnly} that caches the results of
 * {@link #getServiceGroup(IParticipantIdentifier)} and
 * {@link #getServiceMetadata(IParticipantIdentifier, IDocumentTypeIdentifier, ISMPFollowRedirectCallback)}
 * in memory with a configurable TTL (time-to-live).
 * <p>
 * This is useful in high-throughput scenarios where repeated lookups for the same participant or
 * document type would otherwise result in unnecessary HTTP requests to the SMP server.
 * </p>
 * <p>
 * The cache content is not held by this class but by an {@link BDXR2ClientCache} instance, so that
 * the cache content can be shared between arbitrary many client instances. If no specific cache is
 * assigned via {@link #setCache(BDXR2ClientCache)}, the shared default cache
 * {@link BDXR2ClientCache#getDefaultInstance()} is used. That is important, because an SMP client
 * that uses SML/NAPTR resolution is bound to a single participant identifier, so that callers
 * usually create one client instance per message - with an instance-local cache, such a client
 * would never see a cache hit.
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
public class CachingBDXR2ClientReadOnly extends BDXR2ClientReadOnly
{
  private static final Logger LOGGER = LoggerFactory.getLogger (CachingBDXR2ClientReadOnly.class);

  private BDXR2ClientCache m_aCache;

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
  public CachingBDXR2ClientReadOnly (@NonNull final ISMPURLProvider aURLProvider,
                                   @NonNull final IParticipantIdentifier aParticipantIdentifier,
                                   @NonNull final ISMLBase aSMLInfo) throws SMPDNSResolutionException
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
  public CachingBDXR2ClientReadOnly (@NonNull final ISMPURLProvider aURLProvider,
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
  public CachingBDXR2ClientReadOnly (@NonNull final URI aSMPHost)
  {
    super (aSMPHost);
  }

  /**
   * @return The cache used by this client. If no specific cache was set via
   *         {@link #setCache(BDXR2ClientCache)}, the current
   *         {@link BDXR2ClientCache#getDefaultInstance()} is returned. Never <code>null</code>.
   * @since 13.0.0
   */
  @NonNull
  public final BDXR2ClientCache getCache ()
  {
    final BDXR2ClientCache ret = m_aCache;
    return ret != null ? ret : BDXR2ClientCache.getDefaultInstance ();
  }

  /**
   * Set the cache to be used by this client. Note that the cache may be shared with other clients,
   * as all cache keys contain the SMP host URI.
   *
   * @param aCache
   *        The cache to be used. May be <code>null</code> to use the shared default cache
   *        {@link BDXR2ClientCache#getDefaultInstance()}.
   * @return this for chaining
   * @since 13.0.0
   */
  @NonNull
  public final CachingBDXR2ClientReadOnly setCache (@Nullable final BDXR2ClientCache aCache)
  {
    m_aCache = aCache;
    return this;
  }

  @Override
  @NonNull
  public ServiceGroupType getServiceGroup (@NonNull final IParticipantIdentifier aServiceGroupID) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");

    final BDXR2ClientCache aCache = getCache ();
    final String sSMPHostURI = getSMPHostURI ();

    // Check cache
    final ServiceGroupType aCached = aCache.getServiceGroup (sSMPHostURI, aServiceGroupID);
    if (aCached != null)
    {
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Cache hit for ServiceGroup '" + aServiceGroupID.getURIEncoded () + "' of '" + sSMPHostURI + "'");
      return aCached;
    }

    // Cache miss or expired — fetch from SMP
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Cache miss for ServiceGroup '" +
                    aServiceGroupID.getURIEncoded () +
                    "' of '" +
                    sSMPHostURI +
                    "' - fetching from SMP");

    final ServiceGroupType ret = super.getServiceGroup (aServiceGroupID);

    // Store in cache (only on success)
    aCache.putServiceGroup (sSMPHostURI, aServiceGroupID, ret);

    return ret;
  }

  @Override
  @NonNull
  public ServiceMetadataType getServiceMetadata (@NonNull final IParticipantIdentifier aServiceGroupID,
                                                       @NonNull final IDocumentTypeIdentifier aDocumentTypeID,
                                                       @Nullable final ISMPFollowRedirectCallback aFollowRedirectCallback) throws SMPClientException
  {
    ValueEnforcer.notNull (aServiceGroupID, "ServiceGroupID");
    ValueEnforcer.notNull (aDocumentTypeID, "DocumentTypeID");

    final BDXR2ClientCache aCache = getCache ();
    final String sSMPHostURI = getSMPHostURI ();

    // Check cache
    final ServiceMetadataType aCached = aCache.getServiceMetadata (sSMPHostURI, aServiceGroupID, aDocumentTypeID);
    if (aCached != null)
    {
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Cache hit for ServiceMetadata '" +
                      aServiceGroupID.getURIEncoded () +
                      "' / '" +
                      aDocumentTypeID.getURIEncoded () +
                      "' of '" +
                      sSMPHostURI +
                      "'");
      // Note: ISMPFollowRedirectCallback is NOT invoked on cache hits
      return aCached;
    }

    // Cache miss or expired — fetch from SMP
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Cache miss for ServiceMetadata '" +
                    aServiceGroupID.getURIEncoded () +
                    "' / '" +
                    aDocumentTypeID.getURIEncoded () +
                    "' of '" +
                    sSMPHostURI +
                    "' - fetching from SMP");

    final ServiceMetadataType ret = super.getServiceMetadata (aServiceGroupID,
                                                                    aDocumentTypeID,
                                                                    aFollowRedirectCallback);

    // Store in cache (only on success)
    aCache.putServiceMetadata (sSMPHostURI, aServiceGroupID, aDocumentTypeID, ret);

    return ret;
  }

  /**
   * Clear all cached entries of the SMP host of this client (both service groups and service
   * metadata). Entries of other SMP hosts in the same cache are not touched. Use
   * {@link BDXR2ClientCache#clearCache()} to clear the entries of all SMP hosts.
   */
  public void clearCache ()
  {
    getCache ().removeAllOfSMPHost (getSMPHostURI ());
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

    getCache ().removeServiceGroup (getSMPHostURI (), aParticipantID);
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

    getCache ().removeServiceMetadata (getSMPHostURI (), aParticipantID, aDocumentTypeID);
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

    getCache ().removeAllServiceMetadataOfParticipant (getSMPHostURI (), aParticipantID);
  }
}
