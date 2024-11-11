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

import org.junit.Ignore;
import org.junit.Test;

import com.helger.network.port.NetworkOnlineStatusDeterminator;
import com.helger.peppol.sml.ESML;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.factory.SimpleIdentifierFactory;
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
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null, "abc"),
                                                        ESML.DIGIT_PRODUCTION));
    assertEquals ("XJ4BNP4PAHH6UQKBIDPF3LRCEOYAGYNDSYLXVHFUCD7WD4QACWWQ.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null, "ABC"),
                                                        ESML.DIGIT_PRODUCTION));
    assertEquals ("HSH3FMC5CYERDV5J6LN6MMQCN2PP2UCYVCZRWUEAHOSOBVIKB6KQ.iso6523-actorid-upis.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis",
                                                                                         "9999:elonia"),
                                                        ESML.DIGIT_PRODUCTION));
    assertEquals ("XUKHFQABQZIKI3YKVR2FHR4SNFA3PF5VPQ6K4TONV3LMVSY5ARVQ.iso6523-actorid-upis.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis",
                                                                                         "0010:5798000000001"),
                                                        ESML.DIGIT_PRODUCTION));

    // Check case insensitivity
    assertEquals ("Y7DZFXAF3D4CJZ4KCGRXTEC6TWVCGA4KY7ZWA5BOIF6MSWD4TDRQ.iso6523-actorid-upis.toop.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis",
                                                                                         "0088:123abc"),
                                                        "toop.acc.edelivery.tech.ec.europa.eu."));
    assertEquals ("Y7DZFXAF3D4CJZ4KCGRXTEC6TWVCGA4KY7ZWA5BOIF6MSWD4TDRQ.iso6523-actorid-upis.toop.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis",
                                                                                         "0088:123ABC"),
                                                        "toop.acc.edelivery.tech.ec.europa.eu."));
    assertEquals ("BA6JO7LWBY53J47UM53XPFZ6FZRTK7LHJJTB32DV5A74IFCOEGWQ.iso6523-actorid-upis.toop.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis",
                                                                                         "9915:tooptest"),
                                                        "toop.acc.edelivery.tech.ec.europa.eu."));
    assertEquals ("BA6JO7LWBY53J47UM53XPFZ6FZRTK7LHJJTB32DV5A74IFCOEGWQ.iso6523-actorid-upis.toop.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis",
                                                                                         "9915:ToopTest"),
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
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null, "abc"),
                                                        ESML.DIGIT_PRODUCTION));
    assertEquals ("WXKAIXB7IZX2SH7CZRVL46JDFINFPTPRAT32E3TRNYFB4J4J354A.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null, "ABC"),
                                                        ESML.DIGIT_PRODUCTION));
    assertEquals ("EH5BOAVAKTMBGZYH2A63DZ4QOV33FVP5NSDVQKLUCFRAAYOODW6A.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test"),
                                                        ESML.DIGIT_TEST));
  }

  @Test
  @Ignore ("Because it may take long to execute")
  @IgnoredNaptrTest
  public void testResolvePeppol () throws SMPDNSResolutionException
  {
    // Only if online
    if (NetworkOnlineStatusDeterminator.getNetworkStatus ().isOnline ())
    {
      final IBDXLURLProvider aURLProvider = BDXLURLProvider.INSTANCE;
      final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test");
      final String sDomain = aURLProvider.getDNSNameOfParticipant (aPI, ESML.DIGIT_TEST);
      assertEquals ("EH5BOAVAKTMBGZYH2A63DZ4QOV33FVP5NSDVQKLUCFRAAYOODW6A.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu",
                    sDomain);

      final URL x = aURLProvider.getSMPURLOfParticipant (aPI, ESML.DIGIT_TEST);
      assertNotNull (x);
      if (true)
        assertEquals ("http://test-infra.peppol.at", x.toString ());
      else
        assertEquals ("BRZ-TEST-SMP.publisher.acc.edelivery.tech.ec.europa.eu", x.toString ());
    }
  }

  @Test
  public void testResolveNamePeppol () throws SMPDNSResolutionException
  {
    // Only if online
    final IBDXLURLProvider aURLProvider = BDXLURLProvider.INSTANCE;
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0088:1234567890123");
    final String sDomain = aURLProvider.getDNSNameOfParticipant (aPI, ESML.DIGIT_TEST);
    assertEquals ("SJSYVCCMQYJXK3WEUAPFFQ4X3UMCRF4QRYHERJ4VOVHMONH7GCCQ.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu",
                  sDomain);
  }

  @Test
  public void testResolveNameToop () throws SMPDNSResolutionException
  {
    final IBDXLURLProvider aURLProvider = BDXLURLProvider.INSTANCE;
    assertEquals ("Y7DZFXAF3D4CJZ4KCGRXTEC6TWVCGA4KY7ZWA5BOIF6MSWD4TDRQ.iso6523-actorid-upis.toop.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0088:123abc"),
                                                        "toop.acc.edelivery.tech.ec.europa.eu."));
  }

  @Test
  public void testResolveNameDe4a () throws SMPDNSResolutionException
  {
    final IBDXLURLProvider aURLProvider = BDXLURLProvider.INSTANCE;
    assertEquals ("M2V5CJBJVUYEUKPUEGC4F3DPEBBCJZI3PBXFEGRUQK3R3J42X5VQ.iso6523-actorid-upis.de4a.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                                      "9946:pt501507930"),
                                                        "de4a.edelivery.tech.ec.europa.eu."));
    assertEquals ("JELLFD4EDMULXEZBQEOZE7QN7WOVMWOBE7QIKSTHQXKDRZPMKX2Q.iso6523-actorid-upis.de4a.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                                      "9946:pt501507930-it1"),
                                                        "de4a.edelivery.tech.ec.europa.eu."));
    assertEquals ("DRS634GQAKGKMKCBGQSJIXHXBCBAIUBWDWMAEYGI36XKU544JDIQ.iso6523-actorid-upis.de4a.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                                      "9946:pt508184509"),
                                                        "de4a.edelivery.tech.ec.europa.eu."));
    assertEquals ("HCBMVYDV33E2HRHT2KRWJT2VAUYDOVIAB6BBFXUBLLTE3RIL3GSA.iso6523-actorid-upis.de4a.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                                      "9946:pt508184509-it1"),
                                                        "de4a.edelivery.tech.ec.europa.eu."));
    assertEquals ("XRAXKYX5JUAZ4RFT2NHJG4PZBQH7JRFGMJDPBSVBXHO756J77WCA.iso6523-actorid-upis.de4a.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                                      "9991:pt000000026"),
                                                        "de4a.edelivery.tech.ec.europa.eu."));
    assertEquals ("SZMWJB7XEUEEZTLTAUMPLYZB3UTVNCEJUWX2UMT2VP3NXQMFBPXQ.iso6523-actorid-upis.de4a.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                                      "9991:si000000016"),
                                                        "de4a.edelivery.tech.ec.europa.eu."));
    assertEquals ("IKVZLP7CYTWCQ7DSIJLPI5YTEFODADKFM2P32WVR2JUDAXXSTPOA.iso6523-actorid-upis.de4a.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                                      "9991:si000000016-it1"),
                                                        "de4a.edelivery.tech.ec.europa.eu."));
    assertEquals ("SG76YLPBAZDTRNXSM4H7PXB2WWIG25HAKGIGA36FC4ENKL7H3KMQ.iso6523-actorid-upis.de4a.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                                      "9991:si000000018"),
                                                        "de4a.edelivery.tech.ec.europa.eu."));
    assertEquals ("3ZIZDJIJ367D2ROPIGWXUXZBEI5SEAQPEXWWIVDS2PHIBZKKM34A.iso6523-actorid-upis.de4a.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                                      "9991:si000000018-it1"),
                                                        "de4a.edelivery.tech.ec.europa.eu."));
    assertEquals ("54VMPCQA26DNZS74VHQOKJ7U6IRBBI5KPMQ6AO3KVCQC3F6YR2YA.iso6523-actorid-upis.de4a.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                                      "9915:de4atest"),
                                                        "de4a.edelivery.tech.ec.europa.eu."));
  }
}
