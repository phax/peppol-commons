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
package com.helger.peppol.identifier.peppol.participant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;
import com.helger.xml.mock.XMLTestHelper;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Test class for class {@link PeppolParticipantIdentifier}.
 *
 * @author Philip Helger
 */
public final class PeppolParticipantIdentifierTest
{
  private static final String [] PARTICIPANT_SCHEME_VALID = { "busdox-actorid-upis",
                                                              "BUSDOX-ACTORID-UPIS",
                                                              PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME,
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
  private static final String VALUE_MAX_LENGTH = StringHelper.getRepeated ('a',
                                                                           PeppolIdentifierHelper.MAX_PARTICIPANT_VALUE_LENGTH);
  private static final String VALUE_MAX_LENGTH_PLUS_1 = VALUE_MAX_LENGTH + 'a';

  @Test
  public void testIsValidParticipantIdentifierScheme ()
  {
    // valid
    for (final String scheme : PARTICIPANT_SCHEME_VALID)
      assertTrue (IPeppolParticipantIdentifier.isValidScheme (scheme));

    // invalid
    for (final String scheme : PARTIFCIPANT_SCHEME_INVALID)
      assertFalse (IPeppolParticipantIdentifier.isValidScheme (scheme));
  }

  @Test
  public void testIsValidParticipantIdentifierValue ()
  {
    assertFalse (IPeppolParticipantIdentifier.isValidValue (null));
    assertFalse (IPeppolParticipantIdentifier.isValidValue (""));

    assertTrue (IPeppolParticipantIdentifier.isValidValue ("9908:976098897"));
    assertTrue (IPeppolParticipantIdentifier.isValidValue ("9908:976098897 "));
    assertTrue (IPeppolParticipantIdentifier.isValidValue ("990:976098897"));
    assertTrue (IPeppolParticipantIdentifier.isValidValue ("990976098897"));
    assertTrue (IPeppolParticipantIdentifier.isValidValue ("9909:976098896"));
    assertTrue (IPeppolParticipantIdentifier.isValidValue ("9908:976098896"));
    assertTrue (IPeppolParticipantIdentifier.isValidValue ("9956:DE:EPROC:BMIEVG:BeschA"));

    assertTrue (IPeppolParticipantIdentifier.isValidValue (VALUE_MAX_LENGTH));
    assertFalse (IPeppolParticipantIdentifier.isValidValue (VALUE_MAX_LENGTH_PLUS_1));
  }

  @Test
  public void testCtor ()
  {
    final PeppolParticipantIdentifier aID = new PeppolParticipantIdentifier ("scheme-actorid-test", "value");
    assertEquals ("scheme-actorid-test", aID.getScheme ());
    assertEquals ("value", aID.getValue ());

    final PeppolParticipantIdentifier aID2 = new PeppolParticipantIdentifier (aID);
    assertEquals ("scheme-actorid-test", aID2.getScheme ());
    assertEquals ("value", aID2.getValue ());

    assertEquals (aID, aID2);
    XMLTestHelper.testMicroTypeConversion (aID2);
  }

  @Test
  public void testBasicMethods ()
  {
    final PeppolParticipantIdentifier aID1 = new PeppolParticipantIdentifier ("scheme-actorid-test", "value");
    final PeppolParticipantIdentifier aID2 = new PeppolParticipantIdentifier ("scheme-actorid-test", "value");
    final PeppolParticipantIdentifier aID3 = new PeppolParticipantIdentifier ("scheme2-actorid-test", "value");
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aID1, aID2);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aID1, aID3);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aID2, aID3);
  }

  @Test
  public void testURIStuff ()
  {
    final PeppolParticipantIdentifier aID1 = new PeppolParticipantIdentifier ("scheme-actorid-test", "value1");
    assertEquals ("scheme-actorid-test::value1", aID1.getURIEncoded ());
    assertEquals ("scheme-actorid-test%3A%3Avalue1", aID1.getURIPercentEncoded ());
    final PeppolParticipantIdentifier aID2 = PeppolParticipantIdentifier.createFromURIPartOrNull ("scheme-actorid-test::value1");
    assertEquals (aID1, aID2);

    assertNull (PeppolParticipantIdentifier.createFromURIPartOrNull ("scheme1"));
    assertNull (PeppolParticipantIdentifier.createFromURIPartOrNull (null));

    assertNull (PeppolParticipantIdentifier.createFromURIPartOrNull (null));
    assertNull (PeppolParticipantIdentifier.createFromURIPartOrNull (""));

    assertNotNull (PeppolParticipantIdentifier.createFromURIPartOrNull ("any-actorid-dummy::9908:976098897"));
    assertNotNull (PeppolParticipantIdentifier.createFromURIPartOrNull ("any-actorid-dummy::9908:976098897 "));
    assertNotNull (PeppolParticipantIdentifier.createFromURIPartOrNull ("any-actorid-dummy::990:976098897"));
    assertNotNull (PeppolParticipantIdentifier.createFromURIPartOrNull ("any-actorid-dummy::990976098897"));
    assertNotNull (PeppolParticipantIdentifier.createFromURIPartOrNull ("any-actorid-dummy::9909:976098896"));
    assertNotNull (PeppolParticipantIdentifier.createFromURIPartOrNull ("any-actorid-dummy::9908:976098896"));

    assertNull (PeppolParticipantIdentifier.createFromURIPartOrNull ("any-actorid-dummythatiswaytoolongforwhatisexpected::9908:976098896"));
    assertNotNull (PeppolParticipantIdentifier.createFromURIPartOrNull ("any-actorid-dummy::" + VALUE_MAX_LENGTH));
    assertNull (PeppolParticipantIdentifier.createFromURIPartOrNull ("any-actorid-dummy::" + VALUE_MAX_LENGTH_PLUS_1));
    assertNull (PeppolParticipantIdentifier.createFromURIPartOrNull ("any-actorid-dummy:9908:976098896"));
    assertNull (PeppolParticipantIdentifier.createFromURIPartOrNull ("any-actorid-dummy9908:976098896"));
  }

  @Test
  @SuppressFBWarnings ("NP_NONNULL_PARAM_VIOLATION")
  public void testConstraints ()
  {
    try
    {
      // null key not allowed
      new PeppolParticipantIdentifier (null, "value");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // null value not allowed
      new PeppolParticipantIdentifier (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, null);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Both null not allowed
      new PeppolParticipantIdentifier (null, null);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Empty is not allowed
      new PeppolParticipantIdentifier (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, "");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Cannot be mapped to ISO-8859-1:
      new PeppolParticipantIdentifier (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, "Љ");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Scheme too long
      new PeppolParticipantIdentifier (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME +
                                       VALUE_MAX_LENGTH_PLUS_1,
                                       "abc");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Value too long
      new PeppolParticipantIdentifier (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, VALUE_MAX_LENGTH_PLUS_1);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }

  @Test
  public void testHasDefaultScheme ()
  {
    assertTrue (PeppolParticipantIdentifier.createWithDefaultScheme ("abc").hasDefaultScheme ());
    assertFalse (new PeppolParticipantIdentifier ("dummy-actorid-upis", "abc").hasDefaultScheme ());
  }
}
