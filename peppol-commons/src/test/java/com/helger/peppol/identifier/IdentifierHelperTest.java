/**
 * Copyright (C) 2015 Philip Helger (www.helger.com)
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
import com.helger.peppol.identifier.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppol.identifier.issuingagency.EPredefinedIdentifierIssuingAgency;
import com.helger.peppol.identifier.participant.SimpleParticipantIdentifier;
import com.helger.peppol.identifier.process.SimpleProcessIdentifier;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Test class for class {@link IdentifierHelper}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class IdentifierHelperTest
{
  private static final String [] PARTICIPANT_SCHEME_VALID = { "busdox-actorid-upis",
                                                              "BUSDOX-ACTORID-UPIS",
                                                              CIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME,
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
      assertTrue (IdentifierHelper.isValidParticipantIdentifierScheme (scheme));

    // invalid
    for (final String scheme : PARTIFCIPANT_SCHEME_INVALID)
      assertFalse (IdentifierHelper.isValidParticipantIdentifierScheme (scheme));
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
    final SimpleDocumentTypeIdentifier aDI1 = SimpleDocumentTypeIdentifier.createWithDefaultScheme ("urn:doc:anydoc");
    final SimpleDocumentTypeIdentifier aDI2 = SimpleDocumentTypeIdentifier.createWithDefaultScheme ("urn:doc:anydoc");
    final SimpleDocumentTypeIdentifier aDI3a = SimpleDocumentTypeIdentifier.createWithDefaultScheme ("urn:doc:anyotherdoc");
    final SimpleDocumentTypeIdentifier aDI3b = new SimpleDocumentTypeIdentifier ("my-docid-test",
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

    final SimpleDocumentTypeIdentifier aDI = SimpleDocumentTypeIdentifier.createWithDefaultScheme ("urn:doc:anydoc");
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
    assertFalse (IdentifierHelper.isValidDocumentTypeIdentifierValue (null));
    assertFalse (IdentifierHelper.isValidDocumentTypeIdentifierValue (""));

    assertTrue (IdentifierHelper.isValidDocumentTypeIdentifierValue ("invoice"));
    assertTrue (IdentifierHelper.isValidDocumentTypeIdentifierValue ("order "));

    assertTrue (IdentifierHelper.isValidDocumentTypeIdentifierValue (StringHelper.getRepeated ('a',
                                                                                               CIdentifier.MAX_DOCUMENT_TYPE_IDENTIFIER_VALUE_LENGTH)));
    assertFalse (IdentifierHelper.isValidDocumentTypeIdentifierValue (StringHelper.getRepeated ('a',
                                                                                                CIdentifier.MAX_DOCUMENT_TYPE_IDENTIFIER_VALUE_LENGTH +
                                                                                                     1)));
  }

  @Test
  public void testIsValidDocumentTypeIdentifier ()
  {
    assertFalse (IdentifierHelper.isValidDocumentTypeIdentifier (null));
    assertFalse (IdentifierHelper.isValidDocumentTypeIdentifier (""));

    assertTrue (IdentifierHelper.isValidDocumentTypeIdentifier ("doctype::invoice"));
    assertTrue (IdentifierHelper.isValidDocumentTypeIdentifier ("doctype::order "));

    assertFalse (IdentifierHelper.isValidDocumentTypeIdentifier ("doctypethatiswaytoolongforwhatisexpected::order"));
    assertFalse (IdentifierHelper.isValidDocumentTypeIdentifier ("doctype::" +
                                                                 StringHelper.getRepeated ('a',
                                                                                           CIdentifier.MAX_DOCUMENT_TYPE_IDENTIFIER_VALUE_LENGTH +
                                                                                                1)));
    assertFalse (IdentifierHelper.isValidDocumentTypeIdentifier ("doctype:order"));
    assertFalse (IdentifierHelper.isValidDocumentTypeIdentifier ("doctypeorder"));
  }

  @Test
  public void testIsValidParticipantIdentifierValue ()
  {
    assertFalse (IdentifierHelper.isValidParticipantIdentifierValue (null));
    assertFalse (IdentifierHelper.isValidParticipantIdentifierValue (""));

    assertTrue (IdentifierHelper.isValidParticipantIdentifierValue ("9908:976098897"));
    assertTrue (IdentifierHelper.isValidParticipantIdentifierValue ("9908:976098897 "));
    assertTrue (IdentifierHelper.isValidParticipantIdentifierValue ("990:976098897"));
    assertTrue (IdentifierHelper.isValidParticipantIdentifierValue ("990976098897"));
    assertTrue (IdentifierHelper.isValidParticipantIdentifierValue ("9909:976098896"));
    assertTrue (IdentifierHelper.isValidParticipantIdentifierValue ("9908:976098896"));
    assertTrue (IdentifierHelper.isValidParticipantIdentifierValue ("9956:DE:EPROC:BMIEVG:BeschA"));

    assertTrue (IdentifierHelper.isValidParticipantIdentifierValue (StringHelper.getRepeated ('a',
                                                                                              CIdentifier.MAX_PARTICIPANT_IDENTIFIER_VALUE_LENGTH)));
    assertFalse (IdentifierHelper.isValidParticipantIdentifierValue (StringHelper.getRepeated ('a',
                                                                                               CIdentifier.MAX_PARTICIPANT_IDENTIFIER_VALUE_LENGTH +
                                                                                                    1)));
  }

  @Test
  public void testIsValidParticipantIdentifier ()
  {
    assertFalse (IdentifierHelper.isValidParticipantIdentifier (null));
    assertFalse (IdentifierHelper.isValidParticipantIdentifier (""));

    assertTrue (IdentifierHelper.isValidParticipantIdentifier ("any-actorid-dummy::9908:976098897"));
    assertTrue (IdentifierHelper.isValidParticipantIdentifier ("any-actorid-dummy::9908:976098897 "));
    assertTrue (IdentifierHelper.isValidParticipantIdentifier ("any-actorid-dummy::990:976098897"));
    assertTrue (IdentifierHelper.isValidParticipantIdentifier ("any-actorid-dummy::990976098897"));
    assertTrue (IdentifierHelper.isValidParticipantIdentifier ("any-actorid-dummy::9909:976098896"));
    assertTrue (IdentifierHelper.isValidParticipantIdentifier ("any-actorid-dummy::9908:976098896"));

    assertFalse (IdentifierHelper.isValidParticipantIdentifier ("any-actorid-dummythatiswaytoolongforwhatisexpected::9908:976098896"));
    assertFalse (IdentifierHelper.isValidParticipantIdentifier ("any-actorid-dummy::" +
                                                                StringHelper.getRepeated ('a',
                                                                                          CIdentifier.MAX_PARTICIPANT_IDENTIFIER_VALUE_LENGTH +
                                                                                               1)));
    assertFalse (IdentifierHelper.isValidParticipantIdentifier ("any-actorid-dummy:9908:976098896"));
    assertFalse (IdentifierHelper.isValidParticipantIdentifier ("any-actorid-dummy9908:976098896"));
  }

  @Test
  public void testIsValidProcessIdentifierValue ()
  {
    assertFalse (IdentifierHelper.isValidProcessIdentifierValue (null));
    assertFalse (IdentifierHelper.isValidProcessIdentifierValue (""));

    assertTrue (IdentifierHelper.isValidProcessIdentifierValue ("proc1"));
    assertTrue (IdentifierHelper.isValidProcessIdentifierValue ("proc2 "));

    assertTrue (IdentifierHelper.isValidProcessIdentifierValue (StringHelper.getRepeated ('a',
                                                                                          CIdentifier.MAX_PROCESS_IDENTIFIER_VALUE_LENGTH)));
    assertFalse (IdentifierHelper.isValidProcessIdentifierValue (StringHelper.getRepeated ('a',
                                                                                           CIdentifier.MAX_PROCESS_IDENTIFIER_VALUE_LENGTH +
                                                                                                1)));
  }

  @Test
  public void testIsValidProcessIdentifier ()
  {
    assertFalse (IdentifierHelper.isValidProcessIdentifier (null));
    assertFalse (IdentifierHelper.isValidProcessIdentifier (""));

    assertTrue (IdentifierHelper.isValidProcessIdentifier ("process::proc1"));
    assertTrue (IdentifierHelper.isValidProcessIdentifier ("process::proc2 "));

    assertFalse (IdentifierHelper.isValidProcessIdentifier ("processany-actorid-dummythatiswaytoolongforwhatisexpected::proc2"));
    assertFalse (IdentifierHelper.isValidProcessIdentifier ("process::" +
                                                            StringHelper.getRepeated ('a',
                                                                                      CIdentifier.MAX_PROCESS_IDENTIFIER_VALUE_LENGTH +
                                                                                           1)));
    assertFalse (IdentifierHelper.isValidProcessIdentifier ("process:proc2"));
    assertFalse (IdentifierHelper.isValidProcessIdentifier ("processproc2"));
  }

  @Test
  public void testHasDefaultParticipantIdentifierScheme ()
  {
    assertTrue (IdentifierHelper.hasDefaultParticipantIdentifierScheme (SimpleParticipantIdentifier.createWithDefaultScheme ("abc")));
    assertFalse (IdentifierHelper.hasDefaultParticipantIdentifierScheme (new SimpleParticipantIdentifier ("dummy-actorid-upis",
                                                                                                          "abc")));
    assertTrue (IdentifierHelper.hasDefaultParticipantIdentifierScheme (CIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME +
                                                                        "::abc"));
    assertFalse (IdentifierHelper.hasDefaultParticipantIdentifierScheme (CIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME +
                                                                         ":abc"));
    assertFalse (IdentifierHelper.hasDefaultParticipantIdentifierScheme (CIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME +
                                                                         "abc"));
    assertFalse (IdentifierHelper.hasDefaultParticipantIdentifierScheme ("dummy-actorid-upis::abc"));
  }

  @Test
  public void testHasDefaultDocumentTypeIdentifierScheme ()
  {
    assertTrue (IdentifierHelper.hasDefaultDocumentTypeIdentifierScheme (SimpleDocumentTypeIdentifier.createWithDefaultScheme ("abc")));
    assertFalse (IdentifierHelper.hasDefaultDocumentTypeIdentifierScheme (new SimpleDocumentTypeIdentifier ("doctype",
                                                                                                            "abc")));
    assertTrue (IdentifierHelper.hasDefaultDocumentTypeIdentifierScheme (CIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME +
                                                                         "::abc"));
    assertFalse (IdentifierHelper.hasDefaultDocumentTypeIdentifierScheme (CIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME +
                                                                          ":abc"));
    assertFalse (IdentifierHelper.hasDefaultDocumentTypeIdentifierScheme (CIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME +
                                                                          "abc"));
    assertFalse (IdentifierHelper.hasDefaultDocumentTypeIdentifierScheme ("doctype::abc"));
  }

  @Test
  public void testHasDefaultProcessIdentifierScheme ()
  {
    assertTrue (IdentifierHelper.hasDefaultProcessIdentifierScheme (SimpleProcessIdentifier.createWithDefaultScheme ("abc")));
    assertFalse (IdentifierHelper.hasDefaultProcessIdentifierScheme (new SimpleProcessIdentifier ("proctype", "abc")));
    assertTrue (IdentifierHelper.hasDefaultProcessIdentifierScheme (CIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME +
                                                                    "::abc"));
    assertFalse (IdentifierHelper.hasDefaultProcessIdentifierScheme (CIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME +
                                                                     ":abc"));
    assertFalse (IdentifierHelper.hasDefaultProcessIdentifierScheme (CIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME +
                                                                     "abc"));
    assertFalse (IdentifierHelper.hasDefaultProcessIdentifierScheme ("proctype::abc"));
  }
}
