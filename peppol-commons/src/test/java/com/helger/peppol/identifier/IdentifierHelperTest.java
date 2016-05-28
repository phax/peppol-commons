/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
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
package com.helger.peppol.identifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.generic.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppol.identifier.generic.participant.SimpleParticipantIdentifier;
import com.helger.peppol.identifier.generic.process.SimpleProcessIdentifier;
import com.helger.peppol.identifier.peppol.CPeppolIdentifier;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;
import com.helger.peppol.identifier.peppol.doctype.PeppolDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.issuingagency.EPredefinedIdentifierIssuingAgency;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Test class for class {@link PeppolIdentifierHelper}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class IdentifierHelperTest
{
  private static final String [] PARTICIPANT_SCHEME_VALID = { "busdox-actorid-upis",
                                                              "BUSDOX-ACTORID-UPIS",
                                                              CPeppolIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME,
                                                              "any-actorid-any",
                                                              "any-ACTORID-any" };
  private static final String [] PARTIFCIPANT_SCHEME_INVALID = { null,
                                                                 "",
                                                                 "busdox_actorid_upis",
                                                                 "busdox-upis",
                                                                 "-actorid-upis",
                                                                 "actorid-upis",
                                                                 "busdox-actorid-",
                                                                 "busdox-actorid",
                                                                 "any-domain_actorid_any-type",
                                                                 "any-nonactoid-anybutmuchtoooooooooooooooooooooooolong" };

  @Test
  public void testIsValidParticipantIdentifierScheme ()
  {
    // valid
    for (final String scheme : PARTICIPANT_SCHEME_VALID)
      assertTrue (PeppolIdentifierHelper.isValidParticipantIdentifierScheme (scheme));

    // invalid
    for (final String scheme : PARTIFCIPANT_SCHEME_INVALID)
      assertFalse (PeppolIdentifierHelper.isValidParticipantIdentifierScheme (scheme));
  }

  @Test
  public void testAreIdentifiersEqualPariticpantIdentifier ()
  {
    final SimpleParticipantIdentifier aPI1 = SimpleParticipantIdentifier.createWithDefaultScheme ("0088:123abc");
    final SimpleParticipantIdentifier aPI2 = SimpleParticipantIdentifier.createWithDefaultScheme ("0088:123ABC");
    final SimpleParticipantIdentifier aPI3a = SimpleParticipantIdentifier.createWithDefaultScheme ("0088:123456");
    final SimpleParticipantIdentifier aPI3b = new SimpleParticipantIdentifier ("my-actorid-scheme", "0088:12345");
    assertTrue (IdentifierHelper.areParticipantIdentifiersEqual (aPI1, aPI1));
    assertTrue (IdentifierHelper.areParticipantIdentifiersEqual (aPI1, aPI2));
    assertTrue (IdentifierHelper.areParticipantIdentifiersEqual (aPI2, aPI1));
    assertFalse (IdentifierHelper.areParticipantIdentifiersEqual (aPI1, aPI3a));
    assertFalse (IdentifierHelper.areParticipantIdentifiersEqual (aPI1, aPI3b));
    assertFalse (IdentifierHelper.areParticipantIdentifiersEqual (aPI2, aPI3a));
    assertFalse (IdentifierHelper.areParticipantIdentifiersEqual (aPI2, aPI3b));
    assertFalse (IdentifierHelper.areParticipantIdentifiersEqual (aPI3a, aPI3b));

    try
    {
      IdentifierHelper.areParticipantIdentifiersEqual (aPI1, null);
      fail ("null parameter not allowed");
    }
    catch (final NullPointerException ex)
    {
      // expected
    }

    try
    {
      IdentifierHelper.areParticipantIdentifiersEqual (null, aPI1);
      fail ("null parameter not allowed");
    }
    catch (final NullPointerException ex)
    {
      // expected
    }
  }

  @Test
  public void testAreIdentifiersEqualDocumentIdentifier ()
  {
    final SimpleDocumentTypeIdentifier aDI1 = PeppolDocumentTypeIdentifier.createWithDefaultScheme ("urn:doc:anydoc");
    final SimpleDocumentTypeIdentifier aDI2 = PeppolDocumentTypeIdentifier.createWithDefaultScheme ("urn:doc:anydoc");
    final SimpleDocumentTypeIdentifier aDI3a = PeppolDocumentTypeIdentifier.createWithDefaultScheme ("urn:doc:anyotherdoc");
    final SimpleDocumentTypeIdentifier aDI3b = new PeppolDocumentTypeIdentifier ("my-docid-test",
                                                                                 "urn:doc:anyotherdoc");
    assertTrue (IdentifierHelper.areDocumentTypeIdentifiersEqual (aDI1, aDI1));
    assertTrue (IdentifierHelper.areDocumentTypeIdentifiersEqual (aDI1, aDI2));
    assertTrue (IdentifierHelper.areDocumentTypeIdentifiersEqual (aDI2, aDI1));
    assertFalse (IdentifierHelper.areDocumentTypeIdentifiersEqual (aDI1, aDI3a));
    assertFalse (IdentifierHelper.areDocumentTypeIdentifiersEqual (aDI1, aDI3b));
    assertFalse (IdentifierHelper.areDocumentTypeIdentifiersEqual (aDI2, aDI3a));
    assertFalse (IdentifierHelper.areDocumentTypeIdentifiersEqual (aDI2, aDI3b));
    assertFalse (IdentifierHelper.areDocumentTypeIdentifiersEqual (aDI3a, aDI3b));

    try
    {
      IdentifierHelper.areDocumentTypeIdentifiersEqual (aDI1, null);
      fail ("null parameter not allowed");
    }
    catch (final NullPointerException ex)
    {
      // expected
    }

    try
    {
      IdentifierHelper.areDocumentTypeIdentifiersEqual (null, aDI1);
      fail ("null parameter not allowed");
    }
    catch (final NullPointerException ex)
    {
      // expected
    }
  }

  @Test
  public void testAreIdentifiersEqualProcessIdentifier ()
  {
    final SimpleProcessIdentifier aDI1 = SimpleProcessIdentifier.createWithDefaultScheme ("urn:doc:anydoc");
    final SimpleProcessIdentifier aDI2 = SimpleProcessIdentifier.createWithDefaultScheme ("urn:doc:anydoc");
    final SimpleProcessIdentifier aDI3a = SimpleProcessIdentifier.createWithDefaultScheme ("urn:doc:anyotherdoc");
    final SimpleProcessIdentifier aDI3b = new SimpleProcessIdentifier ("my-procid-test", "urn:doc:anyotherdoc");
    assertTrue (IdentifierHelper.areProcessIdentifiersEqual (aDI1, aDI1));
    assertTrue (IdentifierHelper.areProcessIdentifiersEqual (aDI1, aDI2));
    assertTrue (IdentifierHelper.areProcessIdentifiersEqual (aDI2, aDI1));
    assertFalse (IdentifierHelper.areProcessIdentifiersEqual (aDI1, aDI3a));
    assertFalse (IdentifierHelper.areProcessIdentifiersEqual (aDI1, aDI3b));
    assertFalse (IdentifierHelper.areProcessIdentifiersEqual (aDI2, aDI3a));
    assertFalse (IdentifierHelper.areProcessIdentifiersEqual (aDI2, aDI3b));
    assertFalse (IdentifierHelper.areProcessIdentifiersEqual (aDI3a, aDI3b));

    try
    {
      IdentifierHelper.areProcessIdentifiersEqual (aDI1, null);
      fail ("null parameter not allowed");
    }
    catch (final NullPointerException ex)
    {
      // expected
    }

    try
    {
      IdentifierHelper.areProcessIdentifiersEqual (null, aDI1);
      fail ("null parameter not allowed");
    }
    catch (final NullPointerException ex)
    {
      // expected
    }
  }

  @Test
  @SuppressFBWarnings ("NP_NONNULL_PARAM_VIOLATION")
  public void getIdentifierURIEncoded ()
  {
    final SimpleParticipantIdentifier aPI = SimpleParticipantIdentifier.createWithDefaultScheme ("0088:123abc");
    assertEquals ("iso6523-actorid-upis::0088:123abc", IdentifierHelper.getIdentifierURIEncoded (aPI));

    final SimpleDocumentTypeIdentifier aDI = PeppolDocumentTypeIdentifier.createWithDefaultScheme ("urn:doc:anydoc");
    assertEquals ("busdox-docid-qns::urn:doc:anydoc", IdentifierHelper.getIdentifierURIEncoded (aDI));

    try
    {
      IdentifierHelper.getIdentifierURIEncoded (new SimpleParticipantIdentifier (null, "0088:12345"));
      fail ("Empty scheme should trigger an error!");
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }

    try
    {
      IdentifierHelper.getIdentifierURIEncoded (new SimpleParticipantIdentifier ("", "0088:12345"));
      fail ("Empty scheme should trigger an error!");
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }

    try
    {
      IdentifierHelper.getIdentifierURIEncoded (SimpleParticipantIdentifier.createWithDefaultScheme (null));
      fail ("null value should trigger an error!");
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }
  }

  @Test
  public void testGetIdentifierURIPercentEncoded ()
  {
    SimpleParticipantIdentifier aPI = SimpleParticipantIdentifier.createWithDefaultScheme ("0088:123abc");
    assertEquals ("iso6523-actorid-upis%3A%3A0088%3A123abc", IdentifierHelper.getIdentifierURIPercentEncoded (aPI));
    aPI = SimpleParticipantIdentifier.createWithDefaultScheme (EPredefinedIdentifierIssuingAgency.GLN.createIdentifierValue ("123abc"));
    assertEquals ("iso6523-actorid-upis%3A%3A0088%3A123abc", IdentifierHelper.getIdentifierURIPercentEncoded (aPI));
    aPI = EPredefinedIdentifierIssuingAgency.GLN.createParticipantIdentifier ("123abc");
    assertEquals ("iso6523-actorid-upis%3A%3A0088%3A123abc", IdentifierHelper.getIdentifierURIPercentEncoded (aPI));

    // Different value
    aPI = SimpleParticipantIdentifier.createWithDefaultScheme ("0088/123abc");
    assertEquals ("iso6523-actorid-upis%3A%3A0088%2F123abc", IdentifierHelper.getIdentifierURIPercentEncoded (aPI));
  }

  @Test
  public void testIsValidDocumentTypeIdentifierValue ()
  {
    assertFalse (PeppolIdentifierHelper.isValidDocumentTypeIdentifierValue (null));
    assertFalse (PeppolIdentifierHelper.isValidDocumentTypeIdentifierValue (""));

    assertTrue (PeppolIdentifierHelper.isValidDocumentTypeIdentifierValue ("invoice"));
    assertTrue (PeppolIdentifierHelper.isValidDocumentTypeIdentifierValue ("order "));

    assertTrue (PeppolIdentifierHelper.isValidDocumentTypeIdentifierValue (StringHelper.getRepeated ('a',
                                                                                                     CPeppolIdentifier.MAX_DOCUMENT_TYPE_IDENTIFIER_VALUE_LENGTH)));
    assertFalse (PeppolIdentifierHelper.isValidDocumentTypeIdentifierValue (StringHelper.getRepeated ('a',
                                                                                                      CPeppolIdentifier.MAX_DOCUMENT_TYPE_IDENTIFIER_VALUE_LENGTH +
                                                                                                           1)));
  }

  @Test
  public void testIsValidDocumentTypeIdentifier ()
  {
    assertFalse (PeppolIdentifierHelper.isValidDocumentTypeIdentifier (null));
    assertFalse (PeppolIdentifierHelper.isValidDocumentTypeIdentifier (""));

    assertTrue (PeppolIdentifierHelper.isValidDocumentTypeIdentifier ("doctype::invoice"));
    assertTrue (PeppolIdentifierHelper.isValidDocumentTypeIdentifier ("doctype::order "));

    assertFalse (PeppolIdentifierHelper.isValidDocumentTypeIdentifier ("doctypethatiswaytoolongforwhatisexpected::order"));
    assertFalse (PeppolIdentifierHelper.isValidDocumentTypeIdentifier ("doctype::" +
                                                                       StringHelper.getRepeated ('a',
                                                                                                 CPeppolIdentifier.MAX_DOCUMENT_TYPE_IDENTIFIER_VALUE_LENGTH +
                                                                                                      1)));
    assertFalse (PeppolIdentifierHelper.isValidDocumentTypeIdentifier ("doctype:order"));
    assertFalse (PeppolIdentifierHelper.isValidDocumentTypeIdentifier ("doctypeorder"));
  }

  @Test
  public void testIsValidParticipantIdentifierValue ()
  {
    assertFalse (PeppolIdentifierHelper.isValidParticipantIdentifierValue (null));
    assertFalse (PeppolIdentifierHelper.isValidParticipantIdentifierValue (""));

    assertTrue (PeppolIdentifierHelper.isValidParticipantIdentifierValue ("9908:976098897"));
    assertTrue (PeppolIdentifierHelper.isValidParticipantIdentifierValue ("9908:976098897 "));
    assertTrue (PeppolIdentifierHelper.isValidParticipantIdentifierValue ("990:976098897"));
    assertTrue (PeppolIdentifierHelper.isValidParticipantIdentifierValue ("990976098897"));
    assertTrue (PeppolIdentifierHelper.isValidParticipantIdentifierValue ("9909:976098896"));
    assertTrue (PeppolIdentifierHelper.isValidParticipantIdentifierValue ("9908:976098896"));
    assertTrue (PeppolIdentifierHelper.isValidParticipantIdentifierValue ("9956:DE:EPROC:BMIEVG:BeschA"));

    assertTrue (PeppolIdentifierHelper.isValidParticipantIdentifierValue (StringHelper.getRepeated ('a',
                                                                                                    CPeppolIdentifier.MAX_PARTICIPANT_IDENTIFIER_VALUE_LENGTH)));
    assertFalse (PeppolIdentifierHelper.isValidParticipantIdentifierValue (StringHelper.getRepeated ('a',
                                                                                                     CPeppolIdentifier.MAX_PARTICIPANT_IDENTIFIER_VALUE_LENGTH +
                                                                                                          1)));
  }

  @Test
  public void testIsValidParticipantIdentifier ()
  {
    assertFalse (PeppolIdentifierHelper.isValidParticipantIdentifier (null));
    assertFalse (PeppolIdentifierHelper.isValidParticipantIdentifier (""));

    assertTrue (PeppolIdentifierHelper.isValidParticipantIdentifier ("any-actorid-dummy::9908:976098897"));
    assertTrue (PeppolIdentifierHelper.isValidParticipantIdentifier ("any-actorid-dummy::9908:976098897 "));
    assertTrue (PeppolIdentifierHelper.isValidParticipantIdentifier ("any-actorid-dummy::990:976098897"));
    assertTrue (PeppolIdentifierHelper.isValidParticipantIdentifier ("any-actorid-dummy::990976098897"));
    assertTrue (PeppolIdentifierHelper.isValidParticipantIdentifier ("any-actorid-dummy::9909:976098896"));
    assertTrue (PeppolIdentifierHelper.isValidParticipantIdentifier ("any-actorid-dummy::9908:976098896"));

    assertFalse (PeppolIdentifierHelper.isValidParticipantIdentifier ("any-actorid-dummythatiswaytoolongforwhatisexpected::9908:976098896"));
    assertFalse (PeppolIdentifierHelper.isValidParticipantIdentifier ("any-actorid-dummy::" +
                                                                      StringHelper.getRepeated ('a',
                                                                                                CPeppolIdentifier.MAX_PARTICIPANT_IDENTIFIER_VALUE_LENGTH +
                                                                                                     1)));
    assertFalse (PeppolIdentifierHelper.isValidParticipantIdentifier ("any-actorid-dummy:9908:976098896"));
    assertFalse (PeppolIdentifierHelper.isValidParticipantIdentifier ("any-actorid-dummy9908:976098896"));
  }

  @Test
  public void testIsValidProcessIdentifierValue ()
  {
    assertFalse (PeppolIdentifierHelper.isValidProcessIdentifierValue (null));
    assertFalse (PeppolIdentifierHelper.isValidProcessIdentifierValue (""));

    assertTrue (PeppolIdentifierHelper.isValidProcessIdentifierValue ("proc1"));
    assertTrue (PeppolIdentifierHelper.isValidProcessIdentifierValue ("proc2 "));

    assertTrue (PeppolIdentifierHelper.isValidProcessIdentifierValue (StringHelper.getRepeated ('a',
                                                                                                CPeppolIdentifier.MAX_PROCESS_IDENTIFIER_VALUE_LENGTH)));
    assertFalse (PeppolIdentifierHelper.isValidProcessIdentifierValue (StringHelper.getRepeated ('a',
                                                                                                 CPeppolIdentifier.MAX_PROCESS_IDENTIFIER_VALUE_LENGTH +
                                                                                                      1)));
  }

  @Test
  public void testIsValidProcessIdentifier ()
  {
    assertFalse (PeppolIdentifierHelper.isValidProcessIdentifier (null));
    assertFalse (PeppolIdentifierHelper.isValidProcessIdentifier (""));

    assertTrue (PeppolIdentifierHelper.isValidProcessIdentifier ("process::proc1"));
    assertTrue (PeppolIdentifierHelper.isValidProcessIdentifier ("process::proc2 "));

    assertFalse (PeppolIdentifierHelper.isValidProcessIdentifier ("processany-actorid-dummythatiswaytoolongforwhatisexpected::proc2"));
    assertFalse (PeppolIdentifierHelper.isValidProcessIdentifier ("process::" +
                                                                  StringHelper.getRepeated ('a',
                                                                                            CPeppolIdentifier.MAX_PROCESS_IDENTIFIER_VALUE_LENGTH +
                                                                                                 1)));
    assertFalse (PeppolIdentifierHelper.isValidProcessIdentifier ("process:proc2"));
    assertFalse (PeppolIdentifierHelper.isValidProcessIdentifier ("processproc2"));
  }

  @Test
  public void testHasDefaultParticipantIdentifierScheme ()
  {
    assertTrue (PeppolIdentifierHelper.hasDefaultParticipantIdentifierScheme (SimpleParticipantIdentifier.createWithDefaultScheme ("abc")));
    assertFalse (PeppolIdentifierHelper.hasDefaultParticipantIdentifierScheme (new SimpleParticipantIdentifier ("dummy-actorid-upis",
                                                                                                                "abc")));
    assertTrue (PeppolIdentifierHelper.hasDefaultParticipantIdentifierScheme (CPeppolIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME +
                                                                              "::abc"));
    assertFalse (PeppolIdentifierHelper.hasDefaultParticipantIdentifierScheme (CPeppolIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME +
                                                                               ":abc"));
    assertFalse (PeppolIdentifierHelper.hasDefaultParticipantIdentifierScheme (CPeppolIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME +
                                                                               "abc"));
    assertFalse (PeppolIdentifierHelper.hasDefaultParticipantIdentifierScheme ("dummy-actorid-upis::abc"));
  }

  @Test
  public void testHasDefaultDocumentTypeIdentifierScheme ()
  {
    assertTrue (PeppolIdentifierHelper.hasDefaultDocumentTypeIdentifierScheme (PeppolDocumentTypeIdentifier.createWithDefaultScheme ("abc")));
    assertFalse (PeppolIdentifierHelper.hasDefaultDocumentTypeIdentifierScheme (new SimpleDocumentTypeIdentifier ("doctype",
                                                                                                                  "abc")));
    assertTrue (PeppolIdentifierHelper.hasDefaultDocumentTypeIdentifierScheme (CPeppolIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME +
                                                                               "::abc"));
    assertFalse (PeppolIdentifierHelper.hasDefaultDocumentTypeIdentifierScheme (CPeppolIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME +
                                                                                ":abc"));
    assertFalse (PeppolIdentifierHelper.hasDefaultDocumentTypeIdentifierScheme (CPeppolIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME +
                                                                                "abc"));
    assertFalse (PeppolIdentifierHelper.hasDefaultDocumentTypeIdentifierScheme ("doctype::abc"));
  }

  @Test
  public void testHasDefaultProcessIdentifierScheme ()
  {
    assertTrue (PeppolIdentifierHelper.hasDefaultProcessIdentifierScheme (SimpleProcessIdentifier.createWithDefaultScheme ("abc")));
    assertFalse (PeppolIdentifierHelper.hasDefaultProcessIdentifierScheme (new SimpleProcessIdentifier ("proctype",
                                                                                                        "abc")));
    assertTrue (PeppolIdentifierHelper.hasDefaultProcessIdentifierScheme (CPeppolIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME +
                                                                          "::abc"));
    assertFalse (PeppolIdentifierHelper.hasDefaultProcessIdentifierScheme (CPeppolIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME +
                                                                           ":abc"));
    assertFalse (PeppolIdentifierHelper.hasDefaultProcessIdentifierScheme (CPeppolIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME +
                                                                           "abc"));
    assertFalse (PeppolIdentifierHelper.hasDefaultProcessIdentifierScheme ("proctype::abc"));
  }
}
