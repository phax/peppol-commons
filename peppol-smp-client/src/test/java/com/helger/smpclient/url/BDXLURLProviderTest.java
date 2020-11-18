/**
 * Copyright (C) 2015-2020 Philip Helger
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

import org.junit.Ignore;
import org.junit.Test;

import com.helger.network.port.NetworkOnlineStatusDeterminator;
import com.helger.peppol.sml.ESML;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.simple.participant.SimpleParticipantIdentifier;
import com.helger.smpclient.IgnoredNaptrTest;

/**
 * Test class for class {@link BDXLURLProvider}.
 *
 * @author Philip Helger
 */
public final class BDXLURLProviderTest
{
  @Test
  public void testGetDNSNameOfParticipant () throws SMPDNSResolutionException
  {
    final IBDXLURLProvider aURLProvider = BDXLURLProvider.INSTANCE;
    assertEquals ("4444WYPIXHSTJGGABKB7QMG63KJNR7IFMXRALGPORDXI6ZF64HUA.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null,
                                                                                         "urn:oasis:names:tc:ebcore:partyid-type:iso6523:0060:1234567890128"),
                                                        ESML.DIGIT_PRODUCTION));

    assertEquals ("XJ4BNP4PAHH6UQKBIDPF3LRCEOYAGYNDSYLXVHFUCD7WD4QACWWQ.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null, "abc"), ESML.DIGIT_PRODUCTION));
    assertEquals ("XJ4BNP4PAHH6UQKBIDPF3LRCEOYAGYNDSYLXVHFUCD7WD4QACWWQ.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null, "ABC"), ESML.DIGIT_PRODUCTION));
    assertEquals ("HSH3FMC5CYERDV5J6LN6MMQCN2PP2UCYVCZRWUEAHOSOBVIKB6KQ.iso6523-actorid-upis.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis", "9999:elonia"),
                                                        ESML.DIGIT_PRODUCTION));

    // Check case insensitivity
    assertEquals ("Y7DZFXAF3D4CJZ4KCGRXTEC6TWVCGA4KY7ZWA5BOIF6MSWD4TDRQ.iso6523-actorid-upis.toop.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis", "0088:123abc"),
                                                        "toop.acc.edelivery.tech.ec.europa.eu."));
    assertEquals ("Y7DZFXAF3D4CJZ4KCGRXTEC6TWVCGA4KY7ZWA5BOIF6MSWD4TDRQ.iso6523-actorid-upis.toop.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis", "0088:123ABC"),
                                                        "toop.acc.edelivery.tech.ec.europa.eu."));
    assertEquals ("BA6JO7LWBY53J47UM53XPFZ6FZRTK7LHJJTB32DV5A74IFCOEGWQ.iso6523-actorid-upis.toop.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis", "9915:tooptest"),
                                                        "toop.acc.edelivery.tech.ec.europa.eu."));
    assertEquals ("BA6JO7LWBY53J47UM53XPFZ6FZRTK7LHJJTB32DV5A74IFCOEGWQ.iso6523-actorid-upis.toop.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis", "9915:ToopTest"),
                                                        "toop.acc.edelivery.tech.ec.europa.eu."));
  }

  @Test
  public void testGetDNSNameOfParticipantNoLowercase () throws SMPDNSResolutionException
  {
    final BDXLURLProvider aURLProvider = new BDXLURLProvider ();
    aURLProvider.setLowercaseValueBeforeHashing (false);
    assertEquals ("4444WYPIXHSTJGGABKB7QMG63KJNR7IFMXRALGPORDXI6ZF64HUA.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null,
                                                                                         "urn:oasis:names:tc:ebcore:partyid-type:iso6523:0060:1234567890128"),
                                                        ESML.DIGIT_PRODUCTION));
    assertEquals ("XJ4BNP4PAHH6UQKBIDPF3LRCEOYAGYNDSYLXVHFUCD7WD4QACWWQ.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null, "abc"), ESML.DIGIT_PRODUCTION));
    assertEquals ("WXKAIXB7IZX2SH7CZRVL46JDFINFPTPRAT32E3TRNYFB4J4J354A.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null, "ABC"), ESML.DIGIT_PRODUCTION));
    assertEquals ("EH5BOAVAKTMBGZYH2A63DZ4QOV33FVP5NSDVQKLUCFRAAYOODW6A.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test"),
                                                        ESML.DIGIT_TEST));
  }

  @Test
  @Ignore
  @IgnoredNaptrTest
  public void testResolvePeppol () throws SMPDNSResolutionException
  {
    // Only if online
    if (NetworkOnlineStatusDeterminator.getNetworkStatus ().isOnline ())
    {
      final IBDXLURLProvider aURLProvider = BDXLURLProvider.INSTANCE;
      final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test");
      final String sDomain = aURLProvider.getDNSNameOfParticipant (aPI, ESML.DIGIT_TEST);
      assertEquals ("EH5BOAVAKTMBGZYH2A63DZ4QOV33FVP5NSDVQKLUCFRAAYOODW6A.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu", sDomain);

      final URL x = aURLProvider.getSMPURLOfParticipant (aPI, ESML.DIGIT_TEST);
      assertNotNull (x);
      if (true)
        assertEquals ("http://test-infra.peppol.at", x.toString ());
      else
        assertEquals ("BRZ-TEST-SMP.publisher.acc.edelivery.tech.ec.europa.eu", x.toString ());
    }
  }

  @Test
  public void testResolveToop () throws SMPDNSResolutionException
  {
    final IBDXLURLProvider aURLProvider = BDXLURLProvider.INSTANCE;
    assertEquals ("Y7DZFXAF3D4CJZ4KCGRXTEC6TWVCGA4KY7ZWA5BOIF6MSWD4TDRQ.iso6523-actorid-upis.toop.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0088:123abc"),
                                                        "toop.acc.edelivery.tech.ec.europa.eu."));
  }
}
