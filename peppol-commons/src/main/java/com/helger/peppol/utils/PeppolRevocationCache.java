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

import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.cache.MappedCache;

/**
 * An revocation cache that checks the revocation status of each certificate and
 * keeps the status for up to 6 hours.
 *
 * @author Philip Helger
 */
@ThreadSafe
public final class PeppolRevocationCache extends MappedCache <X509Certificate, String, ExpiringObject <ERevoked>>
{
  public static final Duration DEFAULT_CACHING_DURATION = Duration.ofHours (6);

  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolRevocationCache.class);

  private final Function <X509Certificate, ERevoked> m_aRevocationChecker;
  private final Duration m_aCachingDuration;

  @Nonnull
  private static String _getKey (@Nonnull final X509Certificate aCert)
  {
    return aCert.getSubjectX500Principal ().getName () + "-" + aCert.getSerialNumber ().toString ();
  }

  public PeppolRevocationCache (@Nonnull final Function <X509Certificate, ERevoked> aRevocationChecker,
                                @Nonnull final Duration aCachingDuration)
  {
    super (PeppolRevocationCache::_getKey, cert -> {
      final ERevoked eRevoked = aRevocationChecker.apply (cert);
      return ExpiringObject.ofDuration (eRevoked, aCachingDuration);
    }, 1_000, "CertificateRevocationCache", false);
    ValueEnforcer.notNull (aCachingDuration, "CachingDuration");
    ValueEnforcer.isFalse (aCachingDuration::isNegative, "CachingDuration must not be negative");
    m_aRevocationChecker = aRevocationChecker;
    m_aCachingDuration = aCachingDuration;
  }

  @Nonnull
  public Function <X509Certificate, ERevoked> getRevocationChecker ()
  {
    return m_aRevocationChecker;
  }

  @Nonnull
  public Duration getCachingDuration ()
  {
    return m_aCachingDuration;
  }

  public boolean isRevoked (@Nonnull final X509Certificate aCert)
  {
    ValueEnforcer.notNull (aCert, "Cert");

    // Cannot return null
    ExpiringObject <ERevoked> aObject = getFromCache (aCert);
    // maximum life time check
    if (aObject.isExpiredNow ())
    {
      LOGGER.info ("The cached entry for certificate '" + _getKey (aCert) + "' is expired and needs to be re-fetched.");

      // Object expired - re-fetch
      removeFromCache (aCert);
      aObject = getFromCache (aCert);
    }
    return aObject.getObject ().isRevoked ();
  }
}
