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

import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.util.TimeValue;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.Nonnegative;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.httpclient.HttpClientRetryStrategy;

/**
 * Special HTTP retry strategy for the SMP client. In addition to the default behaviour it limits the
 * duration requested by the HTTP <code>Retry-After</code> response header: if the server requests a
 * longer waiting time than the configured maximum, no retry happens. Without this limit, the
 * default retry strategy of Apache HttpClient waits for any requested duration, which would block an
 * SMP lookup for an unlimited time.
 *
 * @author Philip Helger
 * @since 13.2.2
 */
public class SMPHttpClientRetryStrategy extends HttpClientRetryStrategy
{
  private static final Logger LOGGER = LoggerFactory.getLogger (SMPHttpClientRetryStrategy.class);

  private final Duration m_aMaxRetryAfter;

  public SMPHttpClientRetryStrategy (@Nonnegative final int nMaxRetries,
                                     @NonNull final TimeValue aRetryInterval,
                                     final boolean bRetryAlways,
                                     @NonNull final Duration aMaxRetryAfter)
  {
    super (nMaxRetries, aRetryInterval, bRetryAlways);
    ValueEnforcer.notNull (aMaxRetryAfter, "MaxRetryAfter");
    ValueEnforcer.isFalse (aMaxRetryAfter::isNegative, "MaxRetryAfter must not be negative");
    m_aMaxRetryAfter = aMaxRetryAfter;
  }

  /**
   * @return The maximum duration requested via the HTTP <code>Retry-After</code> response header,
   *         that is still waited for. Never <code>null</code>.
   */
  @NonNull
  public final Duration getMaxRetryAfter ()
  {
    return m_aMaxRetryAfter;
  }

  @Override
  public boolean retryRequest (final HttpResponse aResponse, final int nExecCount, final HttpContext aContext)
  {
    if (!super.retryRequest (aResponse, nExecCount, aContext))
      return false;

    // Contains the Retry-After value if present, the default retry interval otherwise
    final TimeValue aRetryInterval = getRetryInterval (aResponse, nExecCount, aContext);
    if (aRetryInterval.toMilliseconds () > m_aMaxRetryAfter.toMillis ())
    {
      LOGGER.warn ("The SMP server requested to retry after " +
                   aRetryInterval +
                   ", which exceeds the maximum of " +
                   m_aMaxRetryAfter +
                   " - not retrying");
      return false;
    }
    return true;
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ()).append ("MaxRetryAfter", m_aMaxRetryAfter).getToString ();
  }
}
