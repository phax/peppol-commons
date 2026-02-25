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
import static org.junit.Assert.assertTrue;

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

  @SuppressWarnings ("cast")
  @Test
  public void testTypeCompatibility ()
  {
    final CachingSMPClientReadOnly aClient = new CachingSMPClientReadOnly (URI.create ("http://localhost:8080"));
    assertTrue (aClient instanceof SMPClientReadOnly);
    assertTrue (aClient instanceof ISMPServiceGroupProvider);
    assertTrue (aClient instanceof ISMPExtendedServiceMetadataProvider);
  }

  @Test
  public void testDefaultTTL ()
  {
    final CachingSMPClientReadOnly aClient = new CachingSMPClientReadOnly (URI.create ("http://localhost:8080"));
    assertEquals (CachingSMPClientReadOnly.DEFAULT_CACHE_TTL, aClient.getCacheTTL ());
  }

  @Test
  public void testSetGetTTL ()
  {
    final CachingSMPClientReadOnly aClient = new CachingSMPClientReadOnly (URI.create ("http://localhost:8080"));
    aClient.setCacheTTL (Duration.ofSeconds (10));
    assertEquals (Duration.ofSeconds (10), aClient.getCacheTTL ());
    aClient.setCacheTTL (Duration.ofMillis (1));
    assertEquals (Duration.ofMillis (1), aClient.getCacheTTL ());
  }

  @Test
  public void testClearCacheOnEmpty ()
  {
    final CachingSMPClientReadOnly aClient = new CachingSMPClientReadOnly (URI.create ("http://localhost:8080"));
    // Should not throw on empty caches
    aClient.clearCache ();
    aClient.clearServiceGroupCache (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test"));
    aClient.clearServiceMetadataCache (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test"),
                                       EPredefinedDocumentTypeIdentifier.INVOICE_EN16931_PEPPOL_V30);
    aClient.clearServiceMetadataCacheOfParticipant (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test"));
  }
}
