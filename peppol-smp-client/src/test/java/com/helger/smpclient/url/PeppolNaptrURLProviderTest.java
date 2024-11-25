/*
 * Copyright (C) 2015-2024 Philip Helger
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

import java.net.URL;

import org.junit.Test;

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
      assertEquals ("EH5BOAVAKTMBGZYH2A63DZ4QOV33FVP5NSDVQKLUCFRAAYOODW6A.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu",
                    sDomain);

      final URL x = aURLProvider.getSMPURLOfParticipant (aPI, ESML.DIGIT_TEST);
      assertNotNull (x);
      assertEquals ("http://test-infra.peppol.at", x.toString ());
    }
  }

  @Test
  public void testResolveNamePeppol () throws SMPDNSResolutionException
  {
    final IPeppolURLProvider aURLProvider = PeppolNaptrURLProvider.INSTANCE;
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0088:1234567890123");
    final String sDomain = aURLProvider.getDNSNameOfParticipant (aPI, ESML.DIGIT_TEST);
    assertEquals ("SJSYVCCMQYJXK3WEUAPFFQ4X3UMCRF4QRYHERJ4VOVHMONH7GCCQ.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu",
                  sDomain);

    // Policy for use of Identifiers POLICY 7 example
    assertEquals ("Y7DZFXAF3D4CJZ4KCGRXTEC6TWVCGA4KY7ZWA5BOIF6MSWD4TDRQ.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0088:123abc"),
                                                        ESML.DIGIT_TEST));
    assertEquals ("Y7DZFXAF3D4CJZ4KCGRXTEC6TWVCGA4KY7ZWA5BOIF6MSWD4TDRQ.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0088:123ABC"),
                                                        ESML.DIGIT_TEST));

    // Peppol SML Spec example
    assertEquals ("XUKHFQABQZIKI3YKVR2FHR4SNFA3PF5VPQ6K4TONV3LMVSY5ARVQ.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0010:5798000000001"),
                                                        ESML.DIGIT_TEST));
  }
}
