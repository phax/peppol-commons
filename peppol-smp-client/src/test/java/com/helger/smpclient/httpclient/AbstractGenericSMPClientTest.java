/*
 * Copyright (C) 2026 Philip Helger
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.junit.Test;

import com.helger.httpclient.HttpClientManager;
import com.helger.smpclient.peppol.SMPClientReadOnly;
import com.sun.net.httpserver.HttpServer;

/**
 * Test class for class {@link AbstractGenericSMPClient}.
 *
 * @author Greg Taube
 */
public final class AbstractGenericSMPClientTest
{
  @Test
  public void testSharedHttpClientManagerReusesConnection () throws IOException
  {
    final Set <Integer> aRemotePorts = ConcurrentHashMap.newKeySet ();
    final HttpServer aServer = HttpServer.create (new InetSocketAddress ("127.0.0.1", 0), 0);
    aServer.createContext ("/", aExchange -> {
      aRemotePorts.add (Integer.valueOf (aExchange.getRemoteAddress ().getPort ()));
      final byte [] aResponse = "ok".getBytes (StandardCharsets.UTF_8);
      aExchange.sendResponseHeaders (200, aResponse.length);
      aExchange.getResponseBody ().write (aResponse);
      aExchange.close ();
    });
    aServer.start ();

    try
    {
      final URI aURI = URI.create ("http://127.0.0.1:" + aServer.getAddress ().getPort () + '/');
      final SMPClientReadOnly aClient = new SMPClientReadOnly (aURI);
      try (final HttpClientManager aHttpClientManager = HttpClientManager.create (aClient.httpClientSettings ()))
      {
        assertNull (aClient.getSharedHttpClientManager ());
        assertSame (aClient, aClient.setSharedHttpClientManager (aHttpClientManager));
        assertSame (aHttpClientManager, aClient.getSharedHttpClientManager ());

        assertEquals ("ok", _executeRequest (aClient, aURI));
        assertEquals ("ok", _executeRequest (aClient, aURI));

        assertEquals (1, aRemotePorts.size ());
        assertFalse (aHttpClientManager.isClosed ());
      }
    }
    finally
    {
      aServer.stop (0);
    }
  }

  private static String _executeRequest (final SMPClientReadOnly aClient, final URI aURI) throws IOException
  {
    return aClient.executeRequest (new HttpGet (aURI), aResponse -> EntityUtils.toString (aResponse.getEntity ()));
  }
}
