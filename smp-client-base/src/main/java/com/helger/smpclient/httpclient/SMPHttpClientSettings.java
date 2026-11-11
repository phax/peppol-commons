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
package com.helger.smpclient.httpclient;

import java.time.Duration;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.base.CGlobal;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.http.tls.ETLSVersion;
import com.helger.http.tls.TLSConfigurationMode;
import com.helger.httpclient.HttpClientSettings;
import com.helger.httpclient.HttpClientSettingsConfig;
import com.helger.security.revocation.CertificateRevocationCheckerDefaults;
import com.helger.smpclient.config.SMPClientConfiguration;

/**
 * Special SMP client {@link HttpClientSettings} that are fed from the configuration file (see
 * {@link SMPClientConfiguration}).
 *
 * @author Philip Helger
 * @since 8.0.1
 */
public class SMPHttpClientSettings extends HttpClientSettings
{
  public static final String USER_AGENT = "phax/peppol-commons smp-client/" + CSMPClientVersion.BUILD_VERSION;
  /**
   * The default number of retries. This is the same as the default of Apache HttpClient.
   *
   * @since 13.2.2
   */
  public static final int DEFAULT_SMP_RETRY_COUNT = 1;
  /**
   * The default maximum duration requested via the HTTP <code>Retry-After</code> response header,
   * that is still waited for.
   *
   * @since 13.2.2
   */
  public static final Duration DEFAULT_MAX_RETRY_AFTER = Duration.ofSeconds (30);
  private static final Logger LOGGER = LoggerFactory.getLogger (SMPHttpClientSettings.class);

  private Duration m_aMaxRetryAfter = DEFAULT_MAX_RETRY_AFTER;

  /**
   * Constructor. Initializes all settings from configuration file. Any changes made afterwards
   */
  public SMPHttpClientSettings ()
  {
    // According to the Peppol SMP specification, a client should not follow HTTP 3xx redirects - so
    // we don't (see chapter 5.1 of SMP spec 1.4.0)
    setFollowRedirects (false);

    // TLS 1.3 is allowed in Peppol, TLS 1.2 is the minimum
    setTLSConfigurationMode (new TLSConfigurationMode (new ETLSVersion [] { ETLSVersion.TLS_13, ETLSVersion.TLS_12 },
                                                       CGlobal.EMPTY_STRING_ARRAY));

    // Also do TLS certificate revocation check
    setRevocationCheckMode (CertificateRevocationCheckerDefaults.getRevocationCheckMode ());

    // Set an explicit user agent
    setUserAgent (USER_AGENT);

    // Retry like Apache HttpClient does by default, but with a limited Retry-After duration (see
    // SMPHttpClientFactory)
    setRetryCount (DEFAULT_SMP_RETRY_COUNT);
  }

  /**
   * @return The maximum duration requested via the HTTP <code>Retry-After</code> response header,
   *         that is still waited for. If the server requests a longer duration, no retry happens.
   *         Never <code>null</code>. Defaults to {@link #DEFAULT_MAX_RETRY_AFTER}.
   * @since 13.2.2
   */
  @NonNull
  public final Duration getMaxRetryAfter ()
  {
    return m_aMaxRetryAfter;
  }

  /**
   * Set the maximum duration requested via the HTTP <code>Retry-After</code> response header, that
   * is still waited for. If the server requests a longer duration, no retry happens. This is only
   * considered, if the SMP client creates the HTTP client itself (see {@link SMPHttpClientFactory}).
   *
   * @param aMaxRetryAfter
   *        The maximum duration. May neither be <code>null</code> nor negative.
   * @return this for chaining
   * @since 13.2.2
   */
  @NonNull
  public final SMPHttpClientSettings setMaxRetryAfter (@NonNull final Duration aMaxRetryAfter)
  {
    ValueEnforcer.notNull (aMaxRetryAfter, "MaxRetryAfter");
    ValueEnforcer.isFalse (aMaxRetryAfter::isNegative, "MaxRetryAfter must not be negative");
    m_aMaxRetryAfter = aMaxRetryAfter;
    return this;
  }

  private void _verifySettings ()
  {
    if (isFollowRedirects ())
      LOGGER.warn ("The SMP Client is configured to follow HTTP redirects - this is against the Peppol SMP specification");
  }

  @NonNull
  public static SMPHttpClientSettings fromConfiguration ()
  {
    final SMPHttpClientSettings ret = new SMPHttpClientSettings ();
    HttpClientSettingsConfig.assignConfigValues (ret, SMPClientConfiguration.getConfig (), "smpclient", "");
    ret._verifySettings ();
    return ret;
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ()).append ("MaxRetryAfter", m_aMaxRetryAfter).getToString ();
  }
}
