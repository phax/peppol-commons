/*
 * Copyright (C) 2025 Philip Helger
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
package com.helger.peppol.aphelper;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.Duration;

import org.junit.Test;

import com.helger.commons.timing.StopWatch;
import com.helger.peppol.sml.ESML;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.participant.PeppolParticipantIdentifier;
import com.helger.smpclient.httpclient.SMPHttpClientSettings;

/**
 * Test class for class {@link BusinessCardCache}.
 *
 * @author Philip Helger
 */
public final class BusinessCardCacheTest
{
  @Test
  public void testBasic () throws Exception
  {
    final BusinessCardCache aCache = new BusinessCardCache (ESML.DIGIT_TEST, new SMPHttpClientSettings ());
    final PeppolParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:helger");
    // Fetch initially
    final Duration aDuration1 = StopWatch.runMeasured ( () -> aCache.getBusinessCard (aPI));
    assertNotNull (aDuration1);
    for (int i = 0; i < 100; ++i)
    {
      // Should be from cache
      final Duration aDuration2 = StopWatch.runMeasured ( () -> aCache.getBusinessCard (aPI));
      assertNotNull (aDuration2);

      // Must always be shorter than the initial run
      assertTrue (aDuration2.compareTo (aDuration1) < 0);
      // Assumption....
      assertTrue (aDuration2.toMillis () < 20);
    }
  }
}
