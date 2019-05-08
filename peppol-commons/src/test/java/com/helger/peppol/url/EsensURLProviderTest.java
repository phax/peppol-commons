/**
 * Copyright (C) 2015-2019 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.url;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.helger.commons.annotation.DevelopersNote;
import com.helger.peppol.identifier.factory.PeppolIdentifierFactory;
import com.helger.peppol.identifier.simple.participant.SimpleParticipantIdentifier;
import com.helger.peppol.sml.ESML;

/**
 * Test class for class {@link EsensURLProvider}.
 *
 * @author Philip Helger
 */
public final class EsensURLProviderTest
{
  @Test
  public void testDefault () throws PeppolDNSResolutionException
  {
    final IBDXLURLProvider aURLProvider = EsensURLProvider.INSTANCE;
    assertEquals ("4444WYPIXHSTJGGABKB7QMG63KJNR7IFMXRALGPORDXI6ZF64HUA.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null,
                                                                                         "urn:oasis:names:tc:ebcore:partyid-type:iso6523:0060:1234567890128"),
                                                        ESML.DIGIT_PRODUCTION.getDNSZone (),
                                                        false));

    assertEquals ("XJ4BNP4PAHH6UQKBIDPF3LRCEOYAGYNDSYLXVHFUCD7WD4QACWWQ.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null, "abc"),
                                                        ESML.DIGIT_PRODUCTION.getDNSZone (),
                                                        false));
    assertEquals ("XJ4BNP4PAHH6UQKBIDPF3LRCEOYAGYNDSYLXVHFUCD7WD4QACWWQ.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null, "ABC"),
                                                        ESML.DIGIT_PRODUCTION.getDNSZone (),
                                                        false));

    // Check case insensitivity
    assertEquals ("Y7DZFXAF3D4CJZ4KCGRXTEC6TWVCGA4KY7ZWA5BOIF6MSWD4TDRQ.iso6523-actorid-upis.toop.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis",
                                                                                         "0088:123abc"),
                                                        "toop.acc.edelivery.tech.ec.europa.eu.",
                                                        false));
    assertEquals ("Y7DZFXAF3D4CJZ4KCGRXTEC6TWVCGA4KY7ZWA5BOIF6MSWD4TDRQ.iso6523-actorid-upis.toop.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("iso6523-actorid-upis",
                                                                                         "0088:123ABC"),
                                                        "toop.acc.edelivery.tech.ec.europa.eu.",
                                                        false));
  }

  @Test
  public void testNoLowercase () throws PeppolDNSResolutionException
  {
    final EsensURLProvider aURLProvider = new EsensURLProvider ();
    aURLProvider.setLowercaseValueBeforeHashing (false);
    assertEquals ("4444WYPIXHSTJGGABKB7QMG63KJNR7IFMXRALGPORDXI6ZF64HUA.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null,
                                                                                         "urn:oasis:names:tc:ebcore:partyid-type:iso6523:0060:1234567890128"),
                                                        ESML.DIGIT_PRODUCTION.getDNSZone (),
                                                        false));
    assertEquals ("XJ4BNP4PAHH6UQKBIDPF3LRCEOYAGYNDSYLXVHFUCD7WD4QACWWQ.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null, "abc"),
                                                        ESML.DIGIT_PRODUCTION.getDNSZone (),
                                                        false));
    assertEquals ("WXKAIXB7IZX2SH7CZRVL46JDFINFPTPRAT32E3TRNYFB4J4J354A.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null, "ABC"),
                                                        ESML.DIGIT_PRODUCTION.getDNSZone (),
                                                        false));
    assertEquals ("EH5BOAVAKTMBGZYH2A63DZ4QOV33FVP5NSDVQKLUCFRAAYOODW6A.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test"),
                                                        ESML.DIGIT_TEST.getDNSZone (),
                                                        false));
  }

  @Test
  @DevelopersNote ("works only if DNS server is reachable")
  public void testResolve () throws PeppolDNSResolutionException
  {
    final IBDXLURLProvider aURLProvider = EsensURLProvider.INSTANCE;
    final String sURL = aURLProvider.getDNSNameOfParticipant (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test"),
                                                              ESML.DIGIT_TEST);
    assertNotNull (sURL);
    if (true)
      assertEquals ("test-infra.peppol.at", sURL);
    else
      assertEquals ("BRZ-TEST-SMP.publisher.acc.edelivery.tech.ec.europa.eu", sURL);
  }

  @Test
  public void testToop () throws PeppolDNSResolutionException
  {
    final IBDXLURLProvider aURLProvider = EsensURLProvider.INSTANCE;
    assertEquals ("Y7DZFXAF3D4CJZ4KCGRXTEC6TWVCGA4KY7ZWA5BOIF6MSWD4TDRQ.iso6523-actorid-upis.toop.acc.edelivery.tech.ec.europa.eu",
                  aURLProvider.getDNSNameOfParticipant (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0088:123abc"),
                                                        "toop.acc.edelivery.tech.ec.europa.eu.",
                                                        false));
  }
}
