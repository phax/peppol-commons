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
package com.helger.smpclient.url;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.helger.peppol.sml.ESML;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.factory.SimpleIdentifierFactory;
import com.helger.peppolid.simple.participant.SimpleParticipantIdentifier;

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
    assertEquals ("4444wypixhstjggabkb7qmg63kjnr7ifmxralgpordxi6zf64hua.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null,
                                                                                         "urn:oasis:names:tc:ebcore:partyid-type:iso6523:0060:1234567890128"),
                                                        ESML.DIGIT_PRODUCTION));

    assertEquals ("xj4bnp4pahh6uqkbidpf3lrceoyagyndsylxvhfucd7wd4qacwwq.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null, "abc"),
                                                        ESML.DIGIT_PRODUCTION));
    assertEquals ("xj4bnp4pahh6uqkbidpf3lrceoyagyndsylxvhfucd7wd4qacwwq.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null, "ABC"),
                                                        ESML.DIGIT_PRODUCTION));
    assertEquals ("hsh3fmc5cyerdv5j6ln6mmqcn2pp2ucyvczrwueahosobvikb6kq.iso6523-actorid-upis.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis",
                                                                                         "9999:elonia"),
                                                        ESML.DIGIT_PRODUCTION));
    assertEquals ("xukhfqabqziki3ykvr2fhr4snfa3pf5vpq6k4tonv3lmvsy5arvq.iso6523-actorid-upis.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis",
                                                                                         "0010:5798000000001"),
                                                        ESML.DIGIT_PRODUCTION));
    assertEquals ("y7dzfxaf3d4cjz4kcgrxtec6twvcga4ky7zwa5boif6mswd4tdrq.iso6523-actorid-upis.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis",
                                                                                         "0088:123abc"),
                                                        ESML.DIGIT_PRODUCTION));
    assertEquals ("y7dzfxaf3d4cjz4kcgrxtec6twvcga4ky7zwa5boif6mswd4tdrq.iso6523-actorid-upis.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis",
                                                                                         "0088:123ABC"),
                                                        ESML.DIGIT_PRODUCTION));

    {
      final BDXLURLProvider x = new BDXLURLProvider ();
      x.setAddIdentifierSchemeToZone (false);
      assertEquals ("beza6wfi6xjii32o5ypqnowxxywea6vfo2h3pnrfwrs5yifsrsaa.edelivery.tech.ec.europa.eu",
                    x.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis", "123ABC"),
                                               ESML.DIGIT_PRODUCTION));
    }

    // Check case insensitivity
    assertEquals ("y7dzfxaf3d4cjz4kcgrxtec6twvcga4ky7zwa5boif6mswd4tdrq.iso6523-actorid-upis.toop.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis",
                                                                                         "0088:123abc"),
                                                        "toop.acc.edelivery.tech.ec.europa.eu."));
    assertEquals ("y7dzfxaf3d4cjz4kcgrxtec6twvcga4ky7zwa5boif6mswd4tdrq.iso6523-actorid-upis.toop.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis",
                                                                                         "0088:123ABC"),
                                                        "toop.acc.edelivery.tech.ec.europa.eu."));
    assertEquals ("ba6jo7lwby53j47um53xpfz6fzrtk7lhjjtb32dv5a74ifcoegwq.iso6523-actorid-upis.toop.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis",
                                                                                         "9915:tooptest"),
                                                        "toop.acc.edelivery.tech.ec.europa.eu."));
    assertEquals ("ba6jo7lwby53j47um53xpfz6fzrtk7lhjjtb32dv5a74ifcoegwq.iso6523-actorid-upis.toop.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis",
                                                                                         "9915:ToopTest"),
                                                        "toop.acc.edelivery.tech.ec.europa.eu."));
  }

  @Test
  public void testGetDNSNameOfParticipantNoLowercase () throws SMPDNSResolutionException
  {
    final BDXLURLProvider aURLProvider = new BDXLURLProvider ();
    aURLProvider.setLowercaseValueBeforeHashing (false);
    assertEquals ("4444wypixhstjggabkb7qmg63kjnr7ifmxralgpordxi6zf64hua.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null,
                                                                                         "urn:oasis:names:tc:ebcore:partyid-type:iso6523:0060:1234567890128"),
                                                        ESML.DIGIT_PRODUCTION));
    assertEquals ("xj4bnp4pahh6uqkbidpf3lrceoyagyndsylxvhfucd7wd4qacwwq.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null, "abc"),
                                                        ESML.DIGIT_PRODUCTION));
    assertEquals ("wxkaixb7izx2sh7czrvl46jdfinfptprat32e3trnyfb4j4j354a.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null, "ABC"),
                                                        ESML.DIGIT_PRODUCTION));
    assertEquals ("eh5boavaktmbgzyh2a63dz4qov33fvp5nsdvqklucfraayoodw6a.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test"),
                                                        ESML.DIGIT_TEST));
  }

  @Test
  public void testResolveNameToop () throws SMPDNSResolutionException
  {
    final IBDXLURLProvider aURLProvider = BDXLURLProvider.INSTANCE;
    assertEquals ("y7dzfxaf3d4cjz4kcgrxtec6twvcga4ky7zwa5boif6mswd4tdrq.iso6523-actorid-upis.toop.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0088:123abc"),
                                                        "toop.acc.edelivery.tech.ec.europa.eu."));
  }

  @Test
  public void testResolveNameDe4a () throws SMPDNSResolutionException
  {
    final IBDXLURLProvider aURLProvider = BDXLURLProvider.INSTANCE;
    assertEquals ("m2v5cjbjvuyeukpuegc4f3dpebbcjzi3pbxfegruqk3r3j42x5vq.iso6523-actorid-upis.de4a.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                                      "9946:pt501507930"),
                                                        "de4a.edelivery.tech.ec.europa.eu."));
    assertEquals ("jellfd4edmulxezbqeoze7qn7wovmwobe7qiksthqxkdrzpmkx2q.iso6523-actorid-upis.de4a.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                                      "9946:pt501507930-it1"),
                                                        "de4a.edelivery.tech.ec.europa.eu."));
    assertEquals ("drs634gqakgkmkcbgqsjixhxbcbaiubwdwmaeygi36xku544jdiq.iso6523-actorid-upis.de4a.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                                      "9946:pt508184509"),
                                                        "de4a.edelivery.tech.ec.europa.eu."));
    assertEquals ("hcbmvydv33e2hrht2krwjt2vauydoviab6bbfxubllte3ril3gsa.iso6523-actorid-upis.de4a.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                                      "9946:pt508184509-it1"),
                                                        "de4a.edelivery.tech.ec.europa.eu."));
    assertEquals ("xraxkyx5juaz4rft2nhjg4pzbqh7jrfgmjdpbsvbxho756j77wca.iso6523-actorid-upis.de4a.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                                      "9991:pt000000026"),
                                                        "de4a.edelivery.tech.ec.europa.eu."));
    assertEquals ("szmwjb7xeueeztltaumplyzb3utvncejuwx2umt2vp3nxqmfbpxq.iso6523-actorid-upis.de4a.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                                      "9991:si000000016"),
                                                        "de4a.edelivery.tech.ec.europa.eu."));
    assertEquals ("ikvzlp7cytwcq7dsijlpi5ytefodadkfm2p32wvr2judaxxstpoa.iso6523-actorid-upis.de4a.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                                      "9991:si000000016-it1"),
                                                        "de4a.edelivery.tech.ec.europa.eu."));
    assertEquals ("sg76ylpbazdtrnxsm4h7pxb2wwig25hakgiga36fc4enkl7h3kmq.iso6523-actorid-upis.de4a.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                                      "9991:si000000018"),
                                                        "de4a.edelivery.tech.ec.europa.eu."));
    assertEquals ("3zizdjij367d2ropigwxuxzbei5seaqpexwwivds2phibzkkm34a.iso6523-actorid-upis.de4a.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                                      "9991:si000000018-it1"),
                                                        "de4a.edelivery.tech.ec.europa.eu."));
    assertEquals ("54vmpcqa26dnzs74vhqokj7u6irbbi5kpmq6ao3kvcqc3f6yr2ya.iso6523-actorid-upis.de4a.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                                      "9915:de4atest"),
                                                        "de4a.edelivery.tech.ec.europa.eu."));
  }
}
