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

import org.apache.hc.client5.http.HttpRequestRetryStrategy;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.util.TimeValue;
import org.jspecify.annotations.NonNull;

import com.helger.annotation.Nonnegative;
import com.helger.httpclient.HttpClientFactory;

/**
 * Special {@link HttpClientFactory} for the SMP client, that uses the
 * {@link SMPHttpClientRetryStrategy} to limit the waiting time requested by the HTTP
 * <code>Retry-After</code> response header.
 *
 * @author Philip Helger
 * @since 13.2.2
 */
public class SMPHttpClientFactory extends HttpClientFactory
{
  private final SMPHttpClientSettings m_aSMPSettings;

  public SMPHttpClientFactory (@NonNull final SMPHttpClientSettings aSettings)
  {
    super (aSettings);
    m_aSMPSettings = aSettings;
  }

  @Override
  @NonNull
  public HttpRequestRetryStrategy createRequestRetryStrategy (@Nonnegative final int nMaxRetries,
                                                              @NonNull final TimeValue aRetryInterval,
                                                              final boolean bRetryAlways)
  {
    return new SMPHttpClientRetryStrategy (nMaxRetries,
                                           aRetryInterval,
                                           bRetryAlways,
                                           m_aSMPSettings.getMaxRetryAfter ());
  }

  @Override
  @NonNull
  public HttpClientBuilder createHttpClientBuilder ()
  {
    final HttpClientBuilder ret = super.createHttpClientBuilder ();

    // Without explicit retries, Apache HttpClient would install its default retry strategy that
    // waits for any Retry-After duration - so explicitly disable it
    if (!m_aSMPSettings.isDisableAutomaticRetries () && !m_aSMPSettings.hasRetries ())
      ret.disableAutomaticRetries ();
    return ret;
  }
}
