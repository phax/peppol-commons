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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.net.URI;
import java.time.Duration;

import org.junit.Test;

import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.doctype.EPredefinedDocumentTypeIdentifier;

/**
 * Test class for class {@link CachingSMPClientReadOnly}
 *
 * @author Philip Helger
 */
public final class CachingSMPClientReadOnlyTest
{
  @Test
  public void testConstructorURI ()
  {
    final CachingSMPClientReadOnly aClient = new CachingSMPClientReadOnly (URI.create ("http://localhost:8080"));
    assertNotNull (aClient);
    assertEquals ("http://localhost:8080/", aClient.getSMPHostURI ());
  }

  @Test
  public void testDefaultCache ()
  {
    final CachingSMPClientReadOnly aClient = new CachingSMPClientReadOnly (URI.create ("http://localhost:8080"));
    // By default the shared default cache is used
    assertSame (SMPClientCache.getDefaultInstance (), aClient.getCache ());
  }

  @Test
  public void testSetGetCache ()
  {
    final CachingSMPClientReadOnly aClient = new CachingSMPClientReadOnly (URI.create ("http://localhost:8080"));
    final SMPClientCache aCache = new SMPClientCache (Duration.ofSeconds (10), 5);
    assertSame (aClient, aClient.setCache (aCache));
    assertSame (aCache, aClient.getCache ());

    // null resets to the default cache
    aClient.setCache (null);
    assertSame (SMPClientCache.getDefaultInstance (), aClient.getCache ());
  }

  @Test
  public void testClearCacheOnEmpty ()
  {
    final CachingSMPClientReadOnly aClient = new CachingSMPClientReadOnly (URI.create ("http://localhost:8080"));
    aClient.setCache (new SMPClientCache ());
    // Should not throw on empty caches
    aClient.clearCache ();
    aClient.clearServiceGroupCache (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test"));
    aClient.clearServiceMetadataCache (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test"),
                                       EPredefinedDocumentTypeIdentifier.INVOICE_EN16931_PEPPOL_V30);
    aClient.clearServiceMetadataCacheOfParticipant (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test"));
  }

  @Test
  public void testTwoClientsShareTheDefaultCache ()
  {
    final SMPClientCache aCache = new SMPClientCache ();
    final CachingSMPClientReadOnly aClient1 = new CachingSMPClientReadOnly (URI.create ("http://localhost:8080")).setCache (aCache);
    final CachingSMPClientReadOnly aClient2 = new CachingSMPClientReadOnly (URI.create ("http://localhost:8080")).setCache (aCache);
    assertSame (aClient1.getCache (), aClient2.getCache ());
    assertEquals (aClient1.getSMPHostURI (), aClient2.getSMPHostURI ());
  }
}
