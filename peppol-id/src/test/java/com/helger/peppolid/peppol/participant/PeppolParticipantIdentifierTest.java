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
package com.helger.peppolid.peppol.participant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helger.commons.mock.CommonsTestHelper;
import com.helger.commons.string.StringHelper;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.xml.mock.XMLTestHelper;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

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

  @Test
  public void testCtor ()
  {
    final PeppolParticipantIdentifier aID = new PeppolParticipantIdentifier ("scheme-actorid-test", "value");
    assertEquals ("scheme-actorid-test", aID.getScheme ());
    assertEquals ("value", aID.getValue ());

    final PeppolParticipantIdentifier aID2 = new PeppolParticipantIdentifier (aID);
    assertEquals ("scheme-actorid-test", aID2.getScheme ());
    assertEquals ("value", aID2.getValue ());

    assertTrue (aID.hasSameContent (aID2));
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
    final IIdentifierFactory aIF = PeppolIdentifierFactory.INSTANCE;
    final IParticipantIdentifier aID1 = new PeppolParticipantIdentifier ("scheme-actorid-test", "value1");
    assertEquals ("scheme-actorid-test::value1", aID1.getURIEncoded ());
    assertEquals ("scheme-actorid-test%3A%3Avalue1", aID1.getURIPercentEncoded ());
    final IParticipantIdentifier aID2 = aIF.parseParticipantIdentifier ("scheme-actorid-test::value1");
    assertTrue (aID1.hasSameContent (aID2));

    assertNull (aIF.parseParticipantIdentifier ("scheme1"));
    assertNull (aIF.parseParticipantIdentifier (null));
    assertNull (aIF.parseParticipantIdentifier (""));

    assertNotNull (aIF.parseParticipantIdentifier ("any-actorid-dummy::9908:976098897"));
    assertNotNull (aIF.parseParticipantIdentifier ("any-actorid-dummy::9908:976098897 "));
    assertNotNull (aIF.parseParticipantIdentifier ("any-actorid-dummy::990:976098897"));
    assertNotNull (aIF.parseParticipantIdentifier ("any-actorid-dummy::990976098897"));
    assertNotNull (aIF.parseParticipantIdentifier ("any-actorid-dummy::9909:976098896"));
    assertNotNull (aIF.parseParticipantIdentifier ("any-actorid-dummy::9908:976098896"));

    assertNull (aIF.parseParticipantIdentifier ("any-actorid-dummythatiswaytoolongforwhatisexpected::9908:976098896"));
    assertNotNull (aIF.parseParticipantIdentifier ("any-actorid-dummy::" + VALUE_MAX_LENGTH));
    assertNull (aIF.parseParticipantIdentifier ("any-actorid-dummy::" + VALUE_MAX_LENGTH_PLUS_1));
    assertNull (aIF.parseParticipantIdentifier ("any-actorid-dummy:9908:976098896"));
    assertNull (aIF.parseParticipantIdentifier ("any-actorid-dummy9908:976098896"));
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
      // invalid scheme
      new PeppolParticipantIdentifier ("", "value");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // separator is forbidden
      new PeppolParticipantIdentifier ("abc::def", "value");
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
      new PeppolParticipantIdentifier (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME + VALUE_MAX_LENGTH_PLUS_1,
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
    final IIdentifierFactory aIF = PeppolIdentifierFactory.INSTANCE;
    assertTrue (aIF.createParticipantIdentifierWithDefaultScheme ("abc")
                   .hasScheme (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME));
    assertFalse (new PeppolParticipantIdentifier ("dummy-actorid-upis", "abc").hasDefaultScheme ());
  }

  @Test
  public void testJacksonIssue34 () throws JsonProcessingException
  {
    final Map <String, Object> map = new HashMap <> ();
    map.put ("participantId", new PeppolParticipantIdentifier ("iso6523-actorid-upis", "9915:test"));
    try
    {
      new ObjectMapper ().writeValueAsString (map);
      fail ("Should have thrown a JsonMappingException due to a backing StackOverflowError");
    }
    catch (final JsonMappingException ex)
    {
      assertTrue (ex.getCause () instanceof StackOverflowError);
    }
  }
}
