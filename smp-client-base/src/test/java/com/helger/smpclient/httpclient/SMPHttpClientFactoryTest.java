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

import java.io.IOException;
import java.net.InetAddress;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.impl.bootstrap.HttpServer;
import org.apache.hc.core5.http.impl.bootstrap.ServerBootstrap;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.io.CloseMode;
import org.jspecify.annotations.NonNull;
import org.junit.Test;

/**
 * Test class for class {@link SMPHttpClientFactory}.
 *
 * @author Philip Helger
 */
public final class SMPHttpClientFactoryTest
{
  private static int _countRequestsFor503 (@NonNull final SMPHttpClientSettings aSettings,
                                           @NonNull final String sRetryAfter) throws IOException
  {
    final AtomicInteger aRequestCount = new AtomicInteger (0);
    final HttpServer aServer = ServerBootstrap.bootstrap ()
                                              .setLocalAddress (InetAddress.getLoopbackAddress ())
                                              .setListenerPort (0)
                                              .setCanonicalHostName ("127.0.0.1")
                                              .register ("*", (aRequest, aResponse, aContext) -> {
                                                aRequestCount.incrementAndGet ();
                                                aResponse.setCode (503);
                                                aResponse.addHeader (HttpHeaders.RETRY_AFTER, sRetryAfter);
                                              })
                                              .create ();
    aServer.start ();
    try (final CloseableHttpClient aClient = new SMPHttpClientFactory (aSettings).createHttpClient ())
    {
      final int nStatusCode = aClient.execute (new HttpGet ("http://127.0.0.1:" + aServer.getLocalPort () + '/'),
                                               aResponse -> {
                                                 EntityUtils.consume (aResponse.getEntity ());
                                                 return Integer.valueOf (aResponse.getCode ());
                                               }).intValue ();
      assertEquals (503, nStatusCode);
      return aRequestCount.get ();
    }
    finally
    {
      aServer.close (CloseMode.IMMEDIATE);
    }
  }

  @Test
  public void testRetryAfter () throws IOException
  {
    final SMPHttpClientSettings aSettings = new SMPHttpClientSettings ();
    assertEquals (SMPHttpClientSettings.DEFAULT_SMP_RETRY_COUNT, aSettings.getRetryCount ());
    assertEquals (SMPHttpClientSettings.DEFAULT_MAX_RETRY_AFTER, aSettings.getMaxRetryAfter ());

    // Within the limit - one retry
    assertEquals (2, _countRequestsFor503 (aSettings, "1"));

    // Exceeding the limit - no retry and especially no waiting for 10 minutes
    assertEquals (1, _countRequestsFor503 (aSettings, "600"));

    // A lower limit also stops shorter durations
    aSettings.setMaxRetryAfter (Duration.ofSeconds (1));
    assertEquals (1, _countRequestsFor503 (aSettings, "2"));
  }

  @Test
  public void testNoRetries () throws IOException
  {
    // No retries configured - the default retry strategy of Apache HttpClient must not be used
    final SMPHttpClientSettings aSettings = new SMPHttpClientSettings ();
    aSettings.setRetryCount (0);
    assertEquals (1, _countRequestsFor503 (aSettings, "1"));
  }
}
