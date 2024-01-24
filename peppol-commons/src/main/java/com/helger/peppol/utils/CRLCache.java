/*
 * Copyright (C) 2015-2024 Philip Helger
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
package com.helger.peppol.utils;

import java.security.cert.CRL;
import java.time.Duration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.cache.Cache;
import com.helger.commons.string.StringHelper;

/**
 * A cache for CRLs read from remote locations.
 *
 * @author Philip Helger
 * @since 9.3.0
 */
public class CRLCache extends Cache <String, ExpiringObject <CRL>>
{
  public static final Duration DEFAULT_CACHING_DURATION = Duration.ofHours (24);
  private static final Logger LOGGER = LoggerFactory.getLogger (CRLCache.class);

  private final CRLDownloader m_aDownloader;
  private final Duration m_aCachingDuration;

  /**
   * Constructor
   *
   * @param aDownloader
   *        The downloader to be used to grab data from the Internet.
   * @param aCachingDuration
   *        The caching duration to be used. Must not be <code>null</code>.
   */
  public CRLCache (@Nonnull final CRLDownloader aDownloader, @Nonnull final Duration aCachingDuration)
  {
    super (url -> {
      final CRL aCRL = aDownloader.downloadCRL (url);
      return aCRL == null ? null : ExpiringObject.ofDuration (aCRL, aCachingDuration);
    }, 100, "CRL Cache", true);
    ValueEnforcer.notNull (aDownloader, "CRLDownloader");
    ValueEnforcer.notNull (aCachingDuration, "CachingDuration");
    ValueEnforcer.isFalse (aCachingDuration::isNegative, "CachingDuration must not be negative");
    m_aDownloader = aDownloader;
    m_aCachingDuration = aCachingDuration;
  }

  @Nonnull
  public final CRLDownloader getDownloader ()
  {
    return m_aDownloader;
  }

  @Nonnull
  public final Duration getCachingDuration ()
  {
    return m_aCachingDuration;
  }

  /**
   * Get the CRL object from the provided URL. Uses caching internally.
   *
   * @param sCRLURL
   *        The URL to read the CRL from.
   * @return <code>null</code> if the CRL could not be read from that URL.
   */
  @Nullable
  public CRL getCRLFromURL (@Nullable final String sCRLURL)
  {
    if (StringHelper.hasText (sCRLURL))
    {
      ExpiringObject <CRL> aObject = getFromCache (sCRLURL);
      if (aObject != null)
      {
        // maximum life time check
        if (aObject.isExpiredNow ())
        {
          LOGGER.info ("The cached entry for CRL URL '" + sCRLURL + "' is expired and needs to be re-fetched.");

          // Object expired - re-fetch
          removeFromCache (sCRLURL);
          aObject = getFromCache (sCRLURL);
        }
        if (aObject != null)
          return aObject.getObject ();
      }
    }
    return null;
  }

  /**
   * Allow to manually add a downloaded CRL into the cache, for the provided
   * URL.
   *
   * @param sCRLURL
   *        The URL for which the cached URL should be used. May neither be
   *        <code>null</code> nor empty.
   * @param aCRL
   *        The CRL to be used. May not be <code>null</code>.
   */
  public void setCRLOfURL (@Nonnull @Nonempty final String sCRLURL, @Nonnull final CRL aCRL)
  {
    ValueEnforcer.notEmpty (sCRLURL, "CRLURL");
    ValueEnforcer.notNull (aCRL, "CRL");
    super.putInCache (sCRLURL, ExpiringObject.ofDuration (aCRL, m_aCachingDuration));
  }

  @Nonnull
  public static CRLCache createDefault ()
  {
    return new CRLCache (new CRLDownloader (), DEFAULT_CACHING_DURATION);
  }
}
