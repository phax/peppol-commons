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
import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.Name;
import org.xbill.DNS.TextParseException;

import com.helger.peppol.sml.ESML;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.factory.SimpleIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.peppolid.simple.participant.SimpleParticipantIdentifier;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Test class for class {@link PeppolURLProvider}.
 *
 * @author Philip Helger
 */
public final class PeppolURLProviderTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolURLProviderTest.class);
  private static final IPeppolURLProvider INSTANCE = PeppolURLProvider.INSTANCE;
  private static final IIdentifierFactory IF = PeppolIdentifierFactory.INSTANCE;

  @Test
  @SuppressFBWarnings ("NP_NONNULL_PARAM_VIOLATION")
  public void testGetDNSNameOfParticipant () throws PeppolDNSResolutionException
  {
    assertEquals ("B-f5e78500450d37de5aabe6648ac3bb70.iso6523-actorid-upis.edelivery.tech.ec.europa.eu",
                  INSTANCE.getDNSNameOfParticipant (IF.createParticipantIdentifierWithDefaultScheme ("0088:123abc"),
                                                    ESML.DIGIT_PRODUCTION));
    // Same value but different casing
    assertEquals ("B-f5e78500450d37de5aabe6648ac3bb70.iso6523-actorid-upis.edelivery.tech.ec.europa.eu",
                  INSTANCE.getDNSNameOfParticipant (IF.createParticipantIdentifierWithDefaultScheme ("0088:123ABC"),
                                                    ESML.DIGIT_PRODUCTION));

    assertEquals ("B-85008b8279e07ab0392da75fa55856a2.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu",
                  INSTANCE.getDNSNameOfParticipant (IF.createParticipantIdentifierWithDefaultScheme ("9915:test"),
                                                    ESML.DIGIT_TEST));

    // No identifier scheme
    assertEquals ("B-f5e78500450d37de5aabe6648ac3bb70.edelivery.tech.ec.europa.eu",
                  INSTANCE.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier (null,
                                                                                                                  "0088:123abc"),
                                                    ESML.DIGIT_PRODUCTION));

    // No identifier value
    assertEquals ("B-d41d8cd98f00b204e9800998ecf8427e.iso6523-actorid-upis.edelivery.tech.ec.europa.eu",
                  INSTANCE.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME,
                                                                                                                  null),
                                                    ESML.DIGIT_PRODUCTION));

    // No identifier scheme and value
    assertEquals ("B-d41d8cd98f00b204e9800998ecf8427e.edelivery.tech.ec.europa.eu",
                  INSTANCE.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier (null,
                                                                                                                  null),
                                                    ESML.DIGIT_PRODUCTION));

    // No identifier scheme and value and no SML zone
    assertEquals ("B-d41d8cd98f00b204e9800998ecf8427e",
                  INSTANCE.getDNSNameOfParticipant (SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier (null,
                                                                                                                  null),
                                                    (String) null));

    // Wildcard
    assertEquals ("*.iso6523-actorid-upis.edelivery.tech.ec.europa.eu",
                  INSTANCE.getDNSNameOfParticipant (IF.createParticipantIdentifierWithDefaultScheme ("*"),
                                                    ESML.DIGIT_PRODUCTION));

    // Empty DNS zone
    assertEquals ("B-f5e78500450d37de5aabe6648ac3bb70.iso6523-actorid-upis",
                  INSTANCE.getDNSNameOfParticipant (IF.createParticipantIdentifierWithDefaultScheme ("0088:123ABC"),
                                                    (String) null));
    assertEquals ("B-f5e78500450d37de5aabe6648ac3bb70.iso6523-actorid-upis",
                  INSTANCE.getDNSNameOfParticipant (IF.createParticipantIdentifierWithDefaultScheme ("0088:123ABC"),
                                                    ""));

    // Very simple zone
    assertEquals ("B-f5e78500450d37de5aabe6648ac3bb70.iso6523-actorid-upis.at",
                  INSTANCE.getDNSNameOfParticipant (IF.createParticipantIdentifierWithDefaultScheme ("0088:123ABC"),
                                                    "at."));

    if (false)
      LOGGER.info (INSTANCE.getDNSNameOfParticipant (IF.createParticipantIdentifierWithDefaultScheme ("9915:b"),
                                                     ESML.DIGIT_PRODUCTION));

    // Test invalid
    try
    {
      INSTANCE.getDNSNameOfParticipant (null, "anyzone.org.");
      fail ();
    }
    catch (final NullPointerException ex)
    {
      // expected
    }

    try
    {
      // Invalid DNS zone (missing dot)
      INSTANCE.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("scheme", "value"), "anyzone");
      fail ();
    }
    catch (final PeppolDNSResolutionException ex)
    {
      // expected
    }
  }

  @Test
  public void testGetDNSNameOfParticipantWithDNSName () throws TextParseException
  {
    // The first part must always end with a DOT
    Name aName = Name.fromString ("B-f5e78500450d37de5aabe6648ac3bb70.iso6523-actorid-upis.sml.peppolcentral.org.",
                                  Name.fromString ("sml.peppolcentral.org."));
    assertEquals ("B-f5e78500450d37de5aabe6648ac3bb70.iso6523-actorid-upis.sml.peppolcentral.org.", aName.toString ());

    aName = Name.fromString ("B-f5e78500450d37de5aabe6648ac3bb70.iso6523-actorid-upis.sml.peppolcentral.org.",
                             Name.fromString ("sml.peppolcentral.org"));
    assertEquals ("B-f5e78500450d37de5aabe6648ac3bb70.iso6523-actorid-upis.sml.peppolcentral.org.", aName.toString ());
  }

  @Test
  public void testGetSMPURIOfParticipant () throws URISyntaxException,
                                            MalformedURLException,
                                            PeppolDNSResolutionException
  {
    final IParticipantIdentifier aPI = IF.createParticipantIdentifierWithDefaultScheme ("0088:123ABC");
    final URI aURI = INSTANCE.getSMPURIOfParticipant (aPI, ESML.DIGIT_PRODUCTION);
    assertEquals (new URI ("http://B-f5e78500450d37de5aabe6648ac3bb70.iso6523-actorid-upis.edelivery.tech.ec.europa.eu"),
                  aURI);

    final URL aURL = INSTANCE.getSMPURLOfParticipant (aPI, ESML.DIGIT_PRODUCTION);
    assertEquals (new URL ("http://B-f5e78500450d37de5aabe6648ac3bb70.iso6523-actorid-upis.edelivery.tech.ec.europa.eu"),
                  aURL);
  }
}
