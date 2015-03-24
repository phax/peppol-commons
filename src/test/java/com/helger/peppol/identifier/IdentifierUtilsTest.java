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
import static org.junit.Assert.assertNull;
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
 * Test class for class {@link IdentifierUtils}.
 * 
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class IdentifierUtilsTest
{
  private static final String [] PARTICIPANT_SCHEME_VALID = { "busdox-actorid-upis",
                                                             "BUSDOX-ACTORID-UPIS",
                                                             CIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME,
                                                             "any-actorid-any",
                                                             "any-ACTORID-any" };
  private static final String [] PARTIFCIPANT_SCHEME_INVALID = { null,
                                                                "",
                                                                "busdox_actorid_upis",
                                                                "busdox-notactorid-upis",
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
      assertTrue (IdentifierUtils.isValidParticipantIdentifierScheme (scheme));

    // invalid
    for (final String scheme : PARTIFCIPANT_SCHEME_INVALID)
      assertFalse (IdentifierUtils.isValidParticipantIdentifierScheme (scheme));
  }

  @Test
  public void testAreIdentifiersEqualPariticpantIdentifier ()
  {
    final SimpleParticipantIdentifier aPI1 = SimpleParticipantIdentifier.createWithDefaultScheme ("0088:123abc");
    final SimpleParticipantIdentifier aPI2 = SimpleParticipantIdentifier.createWithDefaultScheme ("0088:123ABC");
    final SimpleParticipantIdentifier aPI3a = SimpleParticipantIdentifier.createWithDefaultScheme ("0088:123456");
    final SimpleParticipantIdentifier aPI3b = new SimpleParticipantIdentifier ("my-actorid-scheme", "0088:12345");
    assertTrue (IdentifierUtils.areIdentifiersEqual (aPI1, aPI1));
    assertTrue (IdentifierUtils.areIdentifiersEqual (aPI1, aPI2));
    assertTrue (IdentifierUtils.areIdentifiersEqual (aPI2, aPI1));
    assertFalse (IdentifierUtils.areIdentifiersEqual (aPI1, aPI3a));
    assertFalse (IdentifierUtils.areIdentifiersEqual (aPI1, aPI3b));
    assertFalse (IdentifierUtils.areIdentifiersEqual (aPI2, aPI3a));
    assertFalse (IdentifierUtils.areIdentifiersEqual (aPI2, aPI3b));
    assertFalse (IdentifierUtils.areIdentifiersEqual (aPI3a, aPI3b));

    try
    {
      IdentifierUtils.areIdentifiersEqual (aPI1, null);
      fail ("null parameter not allowed");
    }
    catch (final NullPointerException ex)
    {
      // expected
    }

    try
    {
      IdentifierUtils.areIdentifiersEqual (null, aPI1);
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
    final SimpleDocumentTypeIdentifier aDI3b = new SimpleDocumentTypeIdentifier ("my-docid-test", "urn:doc:anyotherdoc");
    assertTrue (IdentifierUtils.areIdentifiersEqual (aDI1, aDI1));
    assertTrue (IdentifierUtils.areIdentifiersEqual (aDI1, aDI2));
    assertTrue (IdentifierUtils.areIdentifiersEqual (aDI2, aDI1));
    assertFalse (IdentifierUtils.areIdentifiersEqual (aDI1, aDI3a));
    assertFalse (IdentifierUtils.areIdentifiersEqual (aDI1, aDI3b));
    assertFalse (IdentifierUtils.areIdentifiersEqual (aDI2, aDI3a));
    assertFalse (IdentifierUtils.areIdentifiersEqual (aDI2, aDI3b));
    assertFalse (IdentifierUtils.areIdentifiersEqual (aDI3a, aDI3b));

    try
    {
      IdentifierUtils.areIdentifiersEqual (aDI1, null);
      fail ("null parameter not allowed");
    }
    catch (final NullPointerException ex)
    {
      // expected
    }

    try
    {
      IdentifierUtils.areIdentifiersEqual (null, aDI1);
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
    assertTrue (IdentifierUtils.areIdentifiersEqual (aDI1, aDI1));
    assertTrue (IdentifierUtils.areIdentifiersEqual (aDI1, aDI2));
    assertTrue (IdentifierUtils.areIdentifiersEqual (aDI2, aDI1));
    assertFalse (IdentifierUtils.areIdentifiersEqual (aDI1, aDI3a));
    assertFalse (IdentifierUtils.areIdentifiersEqual (aDI1, aDI3b));
    assertFalse (IdentifierUtils.areIdentifiersEqual (aDI2, aDI3a));
    assertFalse (IdentifierUtils.areIdentifiersEqual (aDI2, aDI3b));
    assertFalse (IdentifierUtils.areIdentifiersEqual (aDI3a, aDI3b));

    try
    {
      IdentifierUtils.areIdentifiersEqual (aDI1, null);
      fail ("null parameter not allowed");
    }
    catch (final NullPointerException ex)
    {
      // expected
    }

    try
    {
      IdentifierUtils.areIdentifiersEqual (null, aDI1);
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
    assertEquals ("iso6523-actorid-upis::0088:123abc", IdentifierUtils.getIdentifierURIEncoded (aPI));

    final SimpleDocumentTypeIdentifier aDI = SimpleDocumentTypeIdentifier.createWithDefaultScheme ("urn:doc:anydoc");
    assertEquals ("busdox-docid-qns::urn:doc:anydoc", IdentifierUtils.getIdentifierURIEncoded (aDI));

    try
    {
      IdentifierUtils.getIdentifierURIEncoded (new SimpleParticipantIdentifier (null, "0088:12345"));
      fail ("Empty scheme should trigger an error!");
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }

    try
    {
      IdentifierUtils.getIdentifierURIEncoded (new SimpleParticipantIdentifier ("", "0088:12345"));
      fail ("Empty scheme should trigger an error!");
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }

    try
    {
      IdentifierUtils.getIdentifierURIEncoded (SimpleParticipantIdentifier.createWithDefaultScheme (null));
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
    assertEquals ("iso6523-actorid-upis%3A%3A0088%3A123abc", IdentifierUtils.getIdentifierURIPercentEncoded (aPI));
    aPI = SimpleParticipantIdentifier.createWithDefaultScheme (EPredefinedIdentifierIssuingAgency.GLN.createIdentifierValue ("123abc"));
    assertEquals ("iso6523-actorid-upis%3A%3A0088%3A123abc", IdentifierUtils.getIdentifierURIPercentEncoded (aPI));
    aPI = EPredefinedIdentifierIssuingAgency.GLN.createParticipantIdentifier ("123abc");
    assertEquals ("iso6523-actorid-upis%3A%3A0088%3A123abc", IdentifierUtils.getIdentifierURIPercentEncoded (aPI));

    // Different value
    aPI = SimpleParticipantIdentifier.createWithDefaultScheme ("0088/123abc");
    assertEquals ("iso6523-actorid-upis%3A%3A0088%2F123abc", IdentifierUtils.getIdentifierURIPercentEncoded (aPI));
  }

  @Test
  public void testIsValidDocumentTypeIdentifierValue ()
  {
    assertFalse (IdentifierUtils.isValidDocumentTypeIdentifierValue (null));
    assertFalse (IdentifierUtils.isValidDocumentTypeIdentifierValue (""));

    assertTrue (IdentifierUtils.isValidDocumentTypeIdentifierValue ("invoice"));
    assertTrue (IdentifierUtils.isValidDocumentTypeIdentifierValue ("order "));

    assertTrue (IdentifierUtils.isValidDocumentTypeIdentifierValue (StringHelper.getRepeated ('a',
                                                                                              CIdentifier.MAX_DOCUMENT_TYPE_IDENTIFIER_VALUE_LENGTH)));
    assertFalse (IdentifierUtils.isValidDocumentTypeIdentifierValue (StringHelper.getRepeated ('a',
                                                                                               CIdentifier.MAX_DOCUMENT_TYPE_IDENTIFIER_VALUE_LENGTH + 1)));
  }

  @Test
  public void testIsValidDocumentTypeIdentifier ()
  {
    assertFalse (IdentifierUtils.isValidDocumentTypeIdentifier (null));
    assertFalse (IdentifierUtils.isValidDocumentTypeIdentifier (""));

    assertTrue (IdentifierUtils.isValidDocumentTypeIdentifier ("doctype::invoice"));
    assertTrue (IdentifierUtils.isValidDocumentTypeIdentifier ("doctype::order "));

    assertFalse (IdentifierUtils.isValidDocumentTypeIdentifier ("doctypethatiswaytoolongforwhatisexpected::order"));
    assertFalse (IdentifierUtils.isValidDocumentTypeIdentifier ("doctype::" +
                                                                StringHelper.getRepeated ('a',
                                                                                          CIdentifier.MAX_DOCUMENT_TYPE_IDENTIFIER_VALUE_LENGTH + 1)));
    assertFalse (IdentifierUtils.isValidDocumentTypeIdentifier ("doctype:order"));
    assertFalse (IdentifierUtils.isValidDocumentTypeIdentifier ("doctypeorder"));
  }

  @Test
  public void testIsValidParticipantIdentifierValue ()
  {
    assertFalse (IdentifierUtils.isValidParticipantIdentifierValue (null));
    assertFalse (IdentifierUtils.isValidParticipantIdentifierValue (""));

    assertTrue (IdentifierUtils.isValidParticipantIdentifierValue ("9908:976098897"));
    assertTrue (IdentifierUtils.isValidParticipantIdentifierValue ("9908:976098897 "));
    assertTrue (IdentifierUtils.isValidParticipantIdentifierValue ("990:976098897"));
    assertTrue (IdentifierUtils.isValidParticipantIdentifierValue ("990976098897"));
    assertTrue (IdentifierUtils.isValidParticipantIdentifierValue ("9909:976098896"));
    assertTrue (IdentifierUtils.isValidParticipantIdentifierValue ("9908:976098896"));

    assertTrue (IdentifierUtils.isValidParticipantIdentifierValue (StringHelper.getRepeated ('a',
                                                                                             CIdentifier.MAX_PARTICIPANT_IDENTIFIER_VALUE_LENGTH)));
    assertFalse (IdentifierUtils.isValidParticipantIdentifierValue (StringHelper.getRepeated ('a',
                                                                                              CIdentifier.MAX_PARTICIPANT_IDENTIFIER_VALUE_LENGTH + 1)));
  }

  @Test
  public void testIsValidParticipantIdentifier ()
  {
    assertFalse (IdentifierUtils.isValidParticipantIdentifier (null));
    assertFalse (IdentifierUtils.isValidParticipantIdentifier (""));

    assertTrue (IdentifierUtils.isValidParticipantIdentifier ("any-actorid-dummy::9908:976098897"));
    assertTrue (IdentifierUtils.isValidParticipantIdentifier ("any-actorid-dummy::9908:976098897 "));
    assertTrue (IdentifierUtils.isValidParticipantIdentifier ("any-actorid-dummy::990:976098897"));
    assertTrue (IdentifierUtils.isValidParticipantIdentifier ("any-actorid-dummy::990976098897"));
    assertTrue (IdentifierUtils.isValidParticipantIdentifier ("any-actorid-dummy::9909:976098896"));
    assertTrue (IdentifierUtils.isValidParticipantIdentifier ("any-actorid-dummy::9908:976098896"));

    assertFalse (IdentifierUtils.isValidParticipantIdentifier ("any-actorid-dummythatiswaytoolongforwhatisexpected::9908:976098896"));
    assertFalse (IdentifierUtils.isValidParticipantIdentifier ("any-actorid-dummy::" +
                                                               StringHelper.getRepeated ('a',
                                                                                         CIdentifier.MAX_PARTICIPANT_IDENTIFIER_VALUE_LENGTH + 1)));
    assertFalse (IdentifierUtils.isValidParticipantIdentifier ("any-actorid-dummy:9908:976098896"));
    assertFalse (IdentifierUtils.isValidParticipantIdentifier ("any-actorid-dummy9908:976098896"));
  }

  @Test
  public void testIsValidProcessIdentifierValue ()
  {
    assertFalse (IdentifierUtils.isValidProcessIdentifierValue (null));
    assertFalse (IdentifierUtils.isValidProcessIdentifierValue (""));

    assertTrue (IdentifierUtils.isValidProcessIdentifierValue ("proc1"));
    assertTrue (IdentifierUtils.isValidProcessIdentifierValue ("proc2 "));

    assertTrue (IdentifierUtils.isValidProcessIdentifierValue (StringHelper.getRepeated ('a',
                                                                                         CIdentifier.MAX_PROCESS_IDENTIFIER_VALUE_LENGTH)));
    assertFalse (IdentifierUtils.isValidProcessIdentifierValue (StringHelper.getRepeated ('a',
                                                                                          CIdentifier.MAX_PROCESS_IDENTIFIER_VALUE_LENGTH + 1)));
  }

  @Test
  public void testIsValidProcessIdentifier ()
  {
    assertFalse (IdentifierUtils.isValidProcessIdentifier (null));
    assertFalse (IdentifierUtils.isValidProcessIdentifier (""));

    assertTrue (IdentifierUtils.isValidProcessIdentifier ("process::proc1"));
    assertTrue (IdentifierUtils.isValidProcessIdentifier ("process::proc2 "));

    assertFalse (IdentifierUtils.isValidProcessIdentifier ("processany-actorid-dummythatiswaytoolongforwhatisexpected::proc2"));
    assertFalse (IdentifierUtils.isValidProcessIdentifier ("process::" +
                                                           StringHelper.getRepeated ('a',
                                                                                     CIdentifier.MAX_PROCESS_IDENTIFIER_VALUE_LENGTH + 1)));
    assertFalse (IdentifierUtils.isValidProcessIdentifier ("process:proc2"));
    assertFalse (IdentifierUtils.isValidProcessIdentifier ("processproc2"));
  }

  @Test
  public void testGetUnifiedParticipantDBValue ()
  {
    assertNull (IdentifierUtils.getUnifiedParticipantDBValue (null));
    assertEquals ("", IdentifierUtils.getUnifiedParticipantDBValue (""));
    assertEquals ("abc", IdentifierUtils.getUnifiedParticipantDBValue ("abc"));
    assertEquals ("abc", IdentifierUtils.getUnifiedParticipantDBValue ("ABC"));
    assertEquals ("abc", IdentifierUtils.getUnifiedParticipantDBValue ("AbC"));
  }

  @Test
  public void testHasDefaultParticipantIdentifierScheme ()
  {
    assertTrue (IdentifierUtils.hasDefaultParticipantIdentifierScheme (SimpleParticipantIdentifier.createWithDefaultScheme ("abc")));
    assertFalse (IdentifierUtils.hasDefaultParticipantIdentifierScheme (new SimpleParticipantIdentifier ("dummy-actorid-upis",
                                                                                                         "abc")));
    assertTrue (IdentifierUtils.hasDefaultParticipantIdentifierScheme (CIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME +
                                                                       "::abc"));
    assertFalse (IdentifierUtils.hasDefaultParticipantIdentifierScheme (CIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME +
                                                                        ":abc"));
    assertFalse (IdentifierUtils.hasDefaultParticipantIdentifierScheme (CIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME +
                                                                        "abc"));
    assertFalse (IdentifierUtils.hasDefaultParticipantIdentifierScheme ("dummy-actorid-upis::abc"));
  }

  @Test
  public void testHasDefaultDocumentTypeIdentifierScheme ()
  {
    assertTrue (IdentifierUtils.hasDefaultDocumentTypeIdentifierScheme (SimpleDocumentTypeIdentifier.createWithDefaultScheme ("abc")));
    assertFalse (IdentifierUtils.hasDefaultDocumentTypeIdentifierScheme (new SimpleDocumentTypeIdentifier ("doctype",
                                                                                                           "abc")));
    assertTrue (IdentifierUtils.hasDefaultDocumentTypeIdentifierScheme (CIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME +
                                                                        "::abc"));
    assertFalse (IdentifierUtils.hasDefaultDocumentTypeIdentifierScheme (CIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME +
                                                                         ":abc"));
    assertFalse (IdentifierUtils.hasDefaultDocumentTypeIdentifierScheme (CIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME +
                                                                         "abc"));
    assertFalse (IdentifierUtils.hasDefaultDocumentTypeIdentifierScheme ("doctype::abc"));
  }

  @Test
  public void testHasDefaultProcessIdentifierScheme ()
  {
    assertTrue (IdentifierUtils.hasDefaultProcessIdentifierScheme (SimpleProcessIdentifier.createWithDefaultScheme ("abc")));
    assertFalse (IdentifierUtils.hasDefaultProcessIdentifierScheme (new SimpleProcessIdentifier ("proctype", "abc")));
    assertTrue (IdentifierUtils.hasDefaultProcessIdentifierScheme (CIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME +
                                                                   "::abc"));
    assertFalse (IdentifierUtils.hasDefaultProcessIdentifierScheme (CIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME +
                                                                    ":abc"));
    assertFalse (IdentifierUtils.hasDefaultProcessIdentifierScheme (CIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME +
                                                                    "abc"));
    assertFalse (IdentifierUtils.hasDefaultProcessIdentifierScheme ("proctype::abc"));
  }
}
