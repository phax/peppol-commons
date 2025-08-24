/*
 * Copyright (C) 2015-2025 Philip Helger
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
package com.helger.peppolid.peppol.participant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.base.string.StringHelper;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.unittest.support.TestHelper;
import com.helger.xml.mock.XMLTestHelper;

/**
 * Test class for class {@link PeppolParticipantIdentifier}.
 *
 * @author Philip Helger
 */
public final class PeppolParticipantIdentifierTest
{
  private static final String VALUE_MAX_LENGTH = StringHelper.getRepeated ('a',
                                                                           PeppolIdentifierHelper.MAX_PARTICIPANT_VALUE_LENGTH);
  private static final String VALUE_MAX_LENGTH_PLUS_1 = VALUE_MAX_LENGTH + 'a';
  private static final IIdentifierFactory IF = PeppolIdentifierFactory.INSTANCE;

  @Test
  public void testCtor ()
  {
    final PeppolParticipantIdentifier aID = new PeppolParticipantIdentifier (IF, "scheme-actorid-test", "value");
    assertEquals ("scheme-actorid-test", aID.getScheme ());
    assertEquals ("value", aID.getValue ());

    final PeppolParticipantIdentifier aID2 = new PeppolParticipantIdentifier (IF, aID);
    assertEquals ("scheme-actorid-test", aID2.getScheme ());
    assertEquals ("value", aID2.getValue ());

    assertTrue (aID.hasSameContent (aID2));
    XMLTestHelper.testMicroTypeConversion (aID2);
  }

  @Test
  public void testBasicMethods ()
  {
    final PeppolParticipantIdentifier aID1 = new PeppolParticipantIdentifier (IF, "scheme-actorid-test", "value");
    final PeppolParticipantIdentifier aID2 = new PeppolParticipantIdentifier (IF, "scheme-actorid-test", "value");
    final PeppolParticipantIdentifier aID3 = new PeppolParticipantIdentifier (IF, "scheme2-actorid-test", "value");
    TestHelper.testDefaultImplementationWithEqualContentObject (aID1, aID2);
    TestHelper.testDefaultImplementationWithDifferentContentObject (aID1, aID3);
    TestHelper.testDefaultImplementationWithDifferentContentObject (aID2, aID3);
  }

  @Test
  public void testURIStuff ()
  {
    final IParticipantIdentifier aID1 = new PeppolParticipantIdentifier (IF, "scheme-actorid-test", "value1");
    assertEquals ("scheme-actorid-test::value1", aID1.getURIEncoded ());
    assertEquals ("scheme-actorid-test%3A%3Avalue1", aID1.getURIPercentEncoded ());
    final IParticipantIdentifier aID2 = IF.parseParticipantIdentifier ("scheme-actorid-test::value1");
    assertTrue (aID1.hasSameContent (aID2));

    assertNull (IF.parseParticipantIdentifier ("scheme1"));
    assertNull (IF.parseParticipantIdentifier (null));
    assertNull (IF.parseParticipantIdentifier (""));

    assertNotNull (IF.parseParticipantIdentifier ("any-actorid-dummy::9908:976098897"));
    assertNotNull (IF.parseParticipantIdentifier ("any-actorid-dummy::9908:976098897 "));
    assertNotNull (IF.parseParticipantIdentifier ("any-actorid-dummy::990:976098897"));
    assertNotNull (IF.parseParticipantIdentifier ("any-actorid-dummy::990976098897"));
    assertNotNull (IF.parseParticipantIdentifier ("any-actorid-dummy::9909:976098896"));
    assertNotNull (IF.parseParticipantIdentifier ("any-actorid-dummy::9908:976098896"));

    assertNull (IF.parseParticipantIdentifier ("any-actorid-dummythatiswaytoolongforwhatisexpected::9908:976098896"));
    assertNotNull (IF.parseParticipantIdentifier ("any-actorid-dummy::" + VALUE_MAX_LENGTH));
    assertNull (IF.parseParticipantIdentifier ("any-actorid-dummy::" + VALUE_MAX_LENGTH_PLUS_1));
    assertNull (IF.parseParticipantIdentifier ("any-actorid-dummy:9908:976098896"));
    assertNull (IF.parseParticipantIdentifier ("any-actorid-dummy9908:976098896"));
  }

  @Test
  public void testConstraints ()
  {
    try
    {
      // null key not allowed
      new PeppolParticipantIdentifier (IF, null, "value");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // invalid scheme
      new PeppolParticipantIdentifier (IF, "", "value");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // separator is forbidden
      new PeppolParticipantIdentifier (IF, "abc::def", "value");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // null value not allowed
      new PeppolParticipantIdentifier (IF, PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, null);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Both null not allowed
      new PeppolParticipantIdentifier (IF, null, null);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Empty is not allowed
      new PeppolParticipantIdentifier (IF, PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, "");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Cannot be mapped to ISO-8859-1:
      new PeppolParticipantIdentifier (IF, PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, "Љ");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Scheme too long
      new PeppolParticipantIdentifier (IF,
                                       PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME + VALUE_MAX_LENGTH_PLUS_1,
                                       "abc");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Value too long
      new PeppolParticipantIdentifier (IF, PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, VALUE_MAX_LENGTH_PLUS_1);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }

  @Test
  public void testHasDefaultScheme ()
  {
    assertTrue (IF.createParticipantIdentifierWithDefaultScheme ("0000:abc")
                  .hasScheme (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME));
    assertFalse (new PeppolParticipantIdentifier (IF, "dummy-actorid-upis", "abc").hasDefaultScheme ());
  }
}
