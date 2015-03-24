/**
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.uri;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Test;

import com.helger.peppol.identifier.IReadonlyParticipantIdentifier;
import com.helger.peppol.identifier.participant.SimpleParticipantIdentifier;
import com.helger.peppol.sml.ESML;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Test class for class {@link BusdoxURLUtils}.
 * 
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class BusdoxURLUtilsTest {
  @Test
  public void testCreatePercentEncodedURL () {
    assertNull (BusdoxURLUtils.createPercentEncodedURL (null));
    assertEquals ("", BusdoxURLUtils.createPercentEncodedURL (""));
    assertEquals ("abc", BusdoxURLUtils.createPercentEncodedURL ("abc"));
    assertEquals ("a%25b", BusdoxURLUtils.createPercentEncodedURL ("a%b"));
    assertEquals ("a%25%25b", BusdoxURLUtils.createPercentEncodedURL ("a%%b"));
    assertEquals ("a%2Fb", BusdoxURLUtils.createPercentEncodedURL ("a/b"));
  }

  @Test
  @SuppressFBWarnings ("NP_NONNULL_PARAM_VIOLATION")
  public void testGetDNSNameOfParticipant () {
    assertEquals ("B-f5e78500450d37de5aabe6648ac3bb70.iso6523-actorid-upis.sml.peppolcentral.org",
                  BusdoxURLUtils.getDNSNameOfParticipant (SimpleParticipantIdentifier.createWithDefaultScheme ("0088:123abc"),
                                                          ESML.PRODUCTION));
    // Same value but different casing
    assertEquals ("B-f5e78500450d37de5aabe6648ac3bb70.iso6523-actorid-upis.sml.peppolcentral.org",
                  BusdoxURLUtils.getDNSNameOfParticipant (SimpleParticipantIdentifier.createWithDefaultScheme ("0088:123ABC"),
                                                          ESML.PRODUCTION));
    // Wildcard
    assertEquals ("*.iso6523-actorid-upis.sml.peppolcentral.org",
                  BusdoxURLUtils.getDNSNameOfParticipant (SimpleParticipantIdentifier.createWithDefaultScheme ("*"),
                                                          ESML.PRODUCTION));

    // Empty DNS zone
    assertEquals ("B-f5e78500450d37de5aabe6648ac3bb70.iso6523-actorid-upis",
                  BusdoxURLUtils.getDNSNameOfParticipant (SimpleParticipantIdentifier.createWithDefaultScheme ("0088:123ABC"),
                                                          (String) null));
    assertEquals ("B-f5e78500450d37de5aabe6648ac3bb70.iso6523-actorid-upis",
                  BusdoxURLUtils.getDNSNameOfParticipant (SimpleParticipantIdentifier.createWithDefaultScheme ("0088:123ABC"),
                                                          ""));

    System.out.println (BusdoxURLUtils.getDNSNameOfParticipant (SimpleParticipantIdentifier.createWithDefaultScheme ("9915:b"),
                                                                ESML.PRODUCTION));

    // Test invalid
    try {
      BusdoxURLUtils.getDNSNameOfParticipant (null, "anyzone.org.");
      fail ();
    }
    catch (final NullPointerException ex) {
      // expected
    }

    try {
      // Invalid scheme
      BusdoxURLUtils.getDNSNameOfParticipant (new SimpleParticipantIdentifier (null, "0088:123"), "anyzone.org.");
      fail ();
    }
    catch (final IllegalArgumentException ex) {
      // expected
    }

    try {
      // Invalid scheme
      BusdoxURLUtils.getDNSNameOfParticipant (new SimpleParticipantIdentifier ("invalid.scheme", "0088:123"),
                                              "anyzone.org.");
      fail ();
    }
    catch (final IllegalArgumentException ex) {
      // expected
    }

    try {
      // Invalid value
      BusdoxURLUtils.getDNSNameOfParticipant (SimpleParticipantIdentifier.createWithDefaultScheme (null),
                                              "anyzone.org.");
      fail ();
    }
    catch (final IllegalArgumentException ex) {
      // expected
    }

    try {
      // Invalid DNS zone (missing dot)
      BusdoxURLUtils.getDNSNameOfParticipant (SimpleParticipantIdentifier.createWithDefaultScheme ("0088:valid"),
                                              "anyzone");
      fail ();
    }
    catch (final IllegalArgumentException ex) {
      // expected
    }
  }

  @Test
  public void testGetSMPURIOfParticipant () throws URISyntaxException, MalformedURLException {
    final IReadonlyParticipantIdentifier aPI = SimpleParticipantIdentifier.createWithDefaultScheme ("0088:123ABC");
    final URI aURI = BusdoxURLUtils.getSMPURIOfParticipant (aPI, ESML.PRODUCTION);
    assertEquals (new URI ("http://B-f5e78500450d37de5aabe6648ac3bb70.iso6523-actorid-upis.sml.peppolcentral.org"),
                  aURI);

    final URL aURL = BusdoxURLUtils.getSMPURLOfParticipant (aPI, ESML.PRODUCTION);
    assertEquals (new URL ("http://B-f5e78500450d37de5aabe6648ac3bb70.iso6523-actorid-upis.sml.peppolcentral.org"),
                  aURL);
  }
}
