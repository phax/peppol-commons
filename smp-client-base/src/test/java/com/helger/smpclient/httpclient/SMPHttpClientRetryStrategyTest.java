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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Duration;

import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.message.BasicHttpResponse;
import org.apache.hc.core5.util.TimeValue;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.junit.Test;

/**
 * Test class for class {@link SMPHttpClientRetryStrategy}.
 *
 * @author Philip Helger
 */
public final class SMPHttpClientRetryStrategyTest
{
  @NonNull
  private static BasicHttpResponse _createResponse (final int nStatusCode, @Nullable final String sRetryAfter)
  {
    final BasicHttpResponse ret = new BasicHttpResponse (nStatusCode);
    if (sRetryAfter != null)
      ret.addHeader (HttpHeaders.RETRY_AFTER, sRetryAfter);
    return ret;
  }

  @Test
  public void testRetryAfter ()
  {
    final SMPHttpClientRetryStrategy aStrategy = new SMPHttpClientRetryStrategy (1,
                                                                                 TimeValue.ofSeconds (1),
                                                                                 false,
                                                                                 Duration.ofSeconds (30));
    assertEquals (Duration.ofSeconds (30), aStrategy.getMaxRetryAfter ());

    // No Retry-After - default interval
    assertTrue (aStrategy.retryRequest (_createResponse (503, null), 1, null));
    // Within the limit
    assertTrue (aStrategy.retryRequest (_createResponse (503, "5"), 1, null));
    assertTrue (aStrategy.retryRequest (_createResponse (429, "30"), 1, null));
    // Exceeding the limit
    assertFalse (aStrategy.retryRequest (_createResponse (503, "31"), 1, null));
    assertFalse (aStrategy.retryRequest (_createResponse (503, "600"), 1, null));
    // Not retriable status code
    assertFalse (aStrategy.retryRequest (_createResponse (500, "5"), 1, null));
    // Maximum number of retries exceeded
    assertFalse (aStrategy.retryRequest (_createResponse (503, "5"), 2, null));
  }
}
