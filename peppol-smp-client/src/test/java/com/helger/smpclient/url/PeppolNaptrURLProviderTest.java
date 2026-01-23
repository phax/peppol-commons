/*
 * Copyright (C) 2015-2025 Philip Helger
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
package com.helger.smpclient.url;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.network.port.NetworkOnlineStatusDeterminator;
import com.helger.peppol.sml.ESML;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.smpclient.IgnoredNaptrTest;

/**
 * Test class for class {@link PeppolNaptrURLProvider}.
 *
 * @author Philip Helger
 */
public final class PeppolNaptrURLProviderTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolNaptrURLProviderTest.class);

  @Test
  // @Ignore ("Because it may take long to execute")
  @IgnoredNaptrTest
  public void testResolvePeppol () throws SMPDNSResolutionException
  {
    // Only if online
    if (NetworkOnlineStatusDeterminator.getNetworkStatus ().isOnline ())
    {
      final IPeppolURLProvider aURLProvider = PeppolNaptrURLProvider.INSTANCE;
      final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test");
      final String sDomain = aURLProvider.getDNSNameOfParticipant (aPI, ESML.DIGIT_TEST);
      assertEquals ("eh5boavaktmbgzyh2a63dz4qov33fvp5nsdvqklucfraayoodw6a.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu",
                    sDomain);

      final URI x = aURLProvider.getSMPURIOfParticipant (aPI, ESML.DIGIT_TEST);
      assertNotNull (x);
      assertEquals ("https://test.erechnung.gv.at/smp", x.toString ());
    }
  }

  @Test
  public void testResolveNamePeppol () throws SMPDNSResolutionException
  {
    final IPeppolURLProvider aURLProvider = PeppolNaptrURLProvider.INSTANCE;
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0088:1234567890123");
    final String sDomain = aURLProvider.getDNSNameOfParticipant (aPI, ESML.DIGIT_TEST);
    assertEquals ("sjsyvccmqyjxk3weuapffq4x3umcrf4qryherj4vovhmonh7gccq.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu",
                  sDomain);

    // Policy for use of Identifiers POLICY 7 example
    assertEquals ("y7dzfxaf3d4cjz4kcgrxtec6twvcga4ky7zwa5boif6mswd4tdrq.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0088:123abc"),
                                                        ESML.DIGIT_TEST));
    assertEquals ("y7dzfxaf3d4cjz4kcgrxtec6twvcga4ky7zwa5boif6mswd4tdrq.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0088:123ABC"),
                                                        ESML.DIGIT_TEST));

    // Peppol SML Spec example
    assertEquals ("xukhfqabqziki3ykvr2fhr4snfa3pf5vpq6k4tonv3lmvsy5arvq.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0010:5798000000001"),
                                                        ESML.DIGIT_TEST));
  }

  @Test
  public void testFindRegisteredSMPURL () throws SMPDNSResolutionException
  {
    // Only if online
    if (NetworkOnlineStatusDeterminator.getNetworkStatus ().isOnline ())
    {
      final IPeppolURLProvider aURLProvider = PeppolNaptrURLProvider.INSTANCE;
      final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0007:123456");

      final URI x = aURLProvider.getSMPURIOfParticipant (aPI, ESML.DIGIT_TEST);
      assertNotNull (x);
      LOGGER.info ("PID " + aPI.getValue () + " is registered at '" + x.toString () + "'");
    }
  }
}
