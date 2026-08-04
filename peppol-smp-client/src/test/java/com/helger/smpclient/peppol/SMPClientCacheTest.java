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
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.time.Duration;

import org.junit.Test;

import com.helger.base.state.EChange;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.doctype.EPredefinedDocumentTypeIdentifier;
import com.helger.xsds.peppol.smp1.ServiceGroupType;
import com.helger.xsds.peppol.smp1.SignedServiceMetadataType;

/**
 * Test class for class {@link SMPClientCache}
 *
 * @author Philip Helger
 */
public final class SMPClientCacheTest
{
  private static final String HOST1 = "http://smp1.example.org/";
  private static final String HOST2 = "http://smp2.example.org/";

  private static final IParticipantIdentifier PID1 = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test1");
  private static final IParticipantIdentifier PID2 = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test2");
  private static final IDocumentTypeIdentifier DTID1 = EPredefinedDocumentTypeIdentifier.INVOICE_EN16931_PEPPOL_V30.getAsDocumentTypeIdentifier ();
  private static final IDocumentTypeIdentifier DTID2 = EPredefinedDocumentTypeIdentifier.XRECHNUNG_INVOICE_UBL_V30.getAsDocumentTypeIdentifier ();

  @Test
  public void testDefaults ()
  {
    final SMPClientCache aCache = new SMPClientCache ();
    assertEquals (SMPClientCache.DEFAULT_CACHE_TTL, aCache.getCacheTTL ());
    assertEquals (SMPClientCache.DEFAULT_MAX_SIZE, aCache.getMaxSize ());
    assertEquals (0, aCache.getServiceGroupCacheSize ());
    assertEquals (0, aCache.getServiceMetadataCacheSize ());
    assertNotNull (aCache.toString ());
  }

  @Test
  public void testInvalidTTL ()
  {
    try
    {
      new SMPClientCache (Duration.ZERO, 10);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }
    try
    {
      new SMPClientCache (Duration.ofSeconds (-1), 10);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }
  }

  @Test
  public void testCacheKeys ()
  {
    // Participant IDs are case insensitive
    assertEquals (SMPClientCache.createServiceGroupCacheKey (HOST1, PID1),
                  SMPClientCache.createServiceGroupCacheKey (HOST1,
                                                             PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:TEST1")));
    // But the SMP host is part of the key
    assertNotEquals (SMPClientCache.createServiceGroupCacheKey (HOST1, PID1),
                     SMPClientCache.createServiceGroupCacheKey (HOST2, PID1));
    // As is the document type ID
    assertNotEquals (SMPClientCache.createServiceMetadataCacheKey (HOST1, PID1, DTID1),
                     SMPClientCache.createServiceMetadataCacheKey (HOST1, PID1, DTID2));
  }

  @Test
  public void testServiceGroup ()
  {
    final SMPClientCache aCache = new SMPClientCache ();
    final ServiceGroupType aSG = new ServiceGroupType ();

    assertNull (aCache.getServiceGroup (HOST1, PID1));

    aCache.putServiceGroup (HOST1, PID1, aSG);
    assertSame (aSG, aCache.getServiceGroup (HOST1, PID1));
    // Participant IDs are case insensitive
    assertSame (aSG,
                aCache.getServiceGroup (HOST1,
                                        PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:TEST1")));
    // Other participant and other SMP host are not affected
    assertNull (aCache.getServiceGroup (HOST1, PID2));
    assertNull (aCache.getServiceGroup (HOST2, PID1));
    assertEquals (1, aCache.getServiceGroupCacheSize ());

    assertEquals (EChange.CHANGED, aCache.removeServiceGroup (HOST1, PID1));
    assertEquals (EChange.UNCHANGED, aCache.removeServiceGroup (HOST1, PID1));
    assertNull (aCache.getServiceGroup (HOST1, PID1));
  }

  @Test
  public void testServiceMetadata ()
  {
    final SMPClientCache aCache = new SMPClientCache ();
    final SignedServiceMetadataType aSM = new SignedServiceMetadataType ();

    assertNull (aCache.getServiceMetadata (HOST1, PID1, DTID1));

    aCache.putServiceMetadata (HOST1, PID1, DTID1, aSM);
    assertSame (aSM, aCache.getServiceMetadata (HOST1, PID1, DTID1));
    // Other document type, other participant and other SMP host are not affected
    assertNull (aCache.getServiceMetadata (HOST1, PID1, DTID2));
    assertNull (aCache.getServiceMetadata (HOST1, PID2, DTID1));
    assertNull (aCache.getServiceMetadata (HOST2, PID1, DTID1));

    assertEquals (EChange.CHANGED, aCache.removeServiceMetadata (HOST1, PID1, DTID1));
    assertEquals (EChange.UNCHANGED, aCache.removeServiceMetadata (HOST1, PID1, DTID1));
    assertNull (aCache.getServiceMetadata (HOST1, PID1, DTID1));
  }

  @Test
  public void testRemoveAllServiceMetadataOfParticipant ()
  {
    final SMPClientCache aCache = new SMPClientCache ();
    aCache.putServiceMetadata (HOST1, PID1, DTID1, new SignedServiceMetadataType ());
    aCache.putServiceMetadata (HOST1, PID1, DTID2, new SignedServiceMetadataType ());
    aCache.putServiceMetadata (HOST1, PID2, DTID1, new SignedServiceMetadataType ());
    aCache.putServiceMetadata (HOST2, PID1, DTID1, new SignedServiceMetadataType ());
    assertEquals (4, aCache.getServiceMetadataCacheSize ());

    // All document types of PID1 on HOST1 only
    assertEquals (2, aCache.removeAllServiceMetadataOfParticipant (HOST1, PID1));
    assertEquals (0, aCache.removeAllServiceMetadataOfParticipant (HOST1, PID1));

    assertNull (aCache.getServiceMetadata (HOST1, PID1, DTID1));
    assertNull (aCache.getServiceMetadata (HOST1, PID1, DTID2));
    assertNotNull (aCache.getServiceMetadata (HOST1, PID2, DTID1));
    assertNotNull (aCache.getServiceMetadata (HOST2, PID1, DTID1));
  }

  @Test
  public void testRemoveAllOfSMPHost ()
  {
    final SMPClientCache aCache = new SMPClientCache ();
    aCache.putServiceGroup (HOST1, PID1, new ServiceGroupType ());
    aCache.putServiceGroup (HOST2, PID1, new ServiceGroupType ());
    aCache.putServiceMetadata (HOST1, PID1, DTID1, new SignedServiceMetadataType ());
    aCache.putServiceMetadata (HOST1, PID2, DTID1, new SignedServiceMetadataType ());
    aCache.putServiceMetadata (HOST2, PID1, DTID1, new SignedServiceMetadataType ());

    // 1 Service Group and 2 Service Metadata of HOST1
    assertEquals (3, aCache.removeAllOfSMPHost (HOST1));
    assertEquals (0, aCache.removeAllOfSMPHost (HOST1));

    assertNull (aCache.getServiceGroup (HOST1, PID1));
    assertNull (aCache.getServiceMetadata (HOST1, PID1, DTID1));
    assertNotNull (aCache.getServiceGroup (HOST2, PID1));
    assertNotNull (aCache.getServiceMetadata (HOST2, PID1, DTID1));
  }

  @Test
  public void testClearCache ()
  {
    final SMPClientCache aCache = new SMPClientCache ();
    aCache.putServiceGroup (HOST1, PID1, new ServiceGroupType ());
    aCache.putServiceMetadata (HOST2, PID2, DTID2, new SignedServiceMetadataType ());

    aCache.clearCache ();
    assertEquals (0, aCache.getServiceGroupCacheSize ());
    assertEquals (0, aCache.getServiceMetadataCacheSize ());
  }

  @Test
  public void testExpiration () throws InterruptedException
  {
    final SMPClientCache aCache = new SMPClientCache (Duration.ofMillis (1), 10);
    aCache.putServiceGroup (HOST1, PID1, new ServiceGroupType ());
    aCache.putServiceMetadata (HOST1, PID1, DTID1, new SignedServiceMetadataType ());

    Thread.sleep (50);

    // Expired entries are not returned
    assertNull (aCache.getServiceGroup (HOST1, PID1));
    assertNull (aCache.getServiceMetadata (HOST1, PID1, DTID1));

    // ... and were removed on read
    assertEquals (0, aCache.evictExpired ());
    assertEquals (0, aCache.getServiceGroupCacheSize ());
    assertEquals (0, aCache.getServiceMetadataCacheSize ());
  }

  @Test
  public void testEvictExpired () throws InterruptedException
  {
    final SMPClientCache aCache = new SMPClientCache (Duration.ofMillis (1), 10);
    aCache.putServiceGroup (HOST1, PID1, new ServiceGroupType ());
    aCache.putServiceMetadata (HOST1, PID1, DTID1, new SignedServiceMetadataType ());

    Thread.sleep (50);

    // Without reading, the entries are still in the internal maps
    assertEquals (2, aCache.evictExpired ());
    assertEquals (0, aCache.getServiceGroupCacheSize ());
    assertEquals (0, aCache.getServiceMetadataCacheSize ());
  }

  @Test
  public void testDefaultInstance ()
  {
    final SMPClientCache aOld = SMPClientCache.getDefaultInstance ();
    assertNotNull (aOld);
    try
    {
      final SMPClientCache aNew = new SMPClientCache (Duration.ofMinutes (30), 5000);
      assertSame (aOld, SMPClientCache.setDefaultInstance (aNew));
      assertSame (aNew, SMPClientCache.getDefaultInstance ());
    }
    finally
    {
      SMPClientCache.setDefaultInstance (aOld);
    }
    assertSame (aOld, SMPClientCache.getDefaultInstance ());
  }
}
