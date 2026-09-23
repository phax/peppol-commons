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
package com.helger.smpclient.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.BDXR2IdentifierFactory;
import com.helger.xsds.bdxr.smp2.ServiceGroupType;
import com.helger.xsds.bdxr.smp2.ServiceMetadataType;

/**
 * Test class for class {@link BDXR2ClientCache}.
 *
 * @author Philip Helger
 */
public final class BDXR2ClientCacheTest
{
  private static final String SMP_HOST = "http://smp.example.org/";

  @Test
  public void testServiceGroupRoundtrip ()
  {
    final BDXR2ClientCache aCache = new BDXR2ClientCache ();
    final IParticipantIdentifier aPID = BDXR2IdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                     "9915:test");
    assertNotNull (aPID);
    assertNull (aCache.getServiceGroup (SMP_HOST, aPID));

    final ServiceGroupType aSG = new ServiceGroupType ();
    aCache.putServiceGroup (SMP_HOST, aPID, aSG);
    assertSame (aSG, aCache.getServiceGroup (SMP_HOST, aPID));
    assertEquals (1, aCache.getServiceGroupCacheSize ());

    // A different SMP host must not see the entry
    assertNull (aCache.getServiceGroup ("http://other.example.org/", aPID));

    assertTrue (aCache.removeServiceGroup (SMP_HOST, aPID).isChanged ());
    assertNull (aCache.getServiceGroup (SMP_HOST, aPID));
  }

  @Test
  public void testServiceMetadataRoundtrip ()
  {
    final BDXR2ClientCache aCache = new BDXR2ClientCache ();
    final IParticipantIdentifier aPID = BDXR2IdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                     "9915:test");
    final IDocumentTypeIdentifier aDTID = BDXR2IdentifierFactory.INSTANCE.createDocumentTypeIdentifier ("bdx-docid-qns",
                                                                                                        "urn:test::doc##urn:test:1");
    assertNotNull (aPID);
    assertNotNull (aDTID);
    assertNull (aCache.getServiceMetadata (SMP_HOST, aPID, aDTID));

    final ServiceMetadataType aSM = new ServiceMetadataType ();
    aCache.putServiceMetadata (SMP_HOST, aPID, aDTID, aSM);
    assertSame (aSM, aCache.getServiceMetadata (SMP_HOST, aPID, aDTID));

    aCache.clearCache ();
    assertNull (aCache.getServiceMetadata (SMP_HOST, aPID, aDTID));
  }

  @Test
  public void testDefaultInstance ()
  {
    assertNotNull (BDXR2ClientCache.getDefaultInstance ());
    // Peppol and BDXR2 use separate default instances
    assertNotNull (BDXR2ClientCache.getDefaultInstance ().toString ());
  }
}
