/*
 * Copyright (C) 2015-2024 Philip Helger
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
package com.helger.peppolid.peppol.process;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;
import com.helger.commons.string.StringHelper;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.xml.mock.XMLTestHelper;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Test class for class {@link PeppolProcessIdentifier}.
 *
 * @author Philip Helger
 */
public final class PeppolProcessIdentifierTest
{
  private static final IIdentifierFactory aIF = PeppolIdentifierFactory.INSTANCE;

  @Test
  public void testCtor ()
  {
    final PeppolProcessIdentifier aID = new PeppolProcessIdentifier (aIF, "scheme", "value");
    assertEquals ("scheme", aID.getScheme ());
    assertEquals ("value", aID.getValue ());

    // Create a copy
    final PeppolProcessIdentifier aID2 = new PeppolProcessIdentifier (aIF, aID);
    assertEquals ("scheme", aID2.getScheme ());
    assertEquals ("value", aID2.getValue ());

    assertTrue (aID.hasSameContent (aID2));
    XMLTestHelper.testMicroTypeConversion (aID2);
  }

  @Test
  public void testBasicMethods ()
  {
    final PeppolProcessIdentifier aID1 = new PeppolProcessIdentifier (aIF, "scheme", "value");
    final PeppolProcessIdentifier aID2 = new PeppolProcessIdentifier (aIF, "scheme", "value");
    final PeppolProcessIdentifier aID3 = new PeppolProcessIdentifier (aIF, "scheme2", "value");
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aID1, aID2);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aID1, aID3);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aID2, aID3);
  }

  @Test
  public void testURIStuff ()
  {
    final IProcessIdentifier aID1 = new PeppolProcessIdentifier (aIF, "scheme1", "value1");
    assertEquals ("scheme1::value1", aID1.getURIEncoded ());
    assertEquals ("scheme1%3A%3Avalue1", aID1.getURIPercentEncoded ());
    final IProcessIdentifier aID2 = aIF.parseProcessIdentifier ("scheme1::value1");
    assertEquals (aID1, aID2);

    assertNull (aIF.parseProcessIdentifier ("scheme1"));
    assertNull (aIF.parseProcessIdentifier (null));
    assertNull (aIF.parseProcessIdentifier (""));

    assertNotNull (aIF.parseProcessIdentifier ("process::proc1"));
    assertNotNull (aIF.parseProcessIdentifier ("process::proc2"));
    assertNull (aIF.parseProcessIdentifier ("process::proc2 "));

    assertNull (aIF.parseProcessIdentifier ("processany-actorid-dummythatiswaytoolongforwhatisexpected::proc2"));
    assertNull (aIF.parseProcessIdentifier ("process::" +
                                            StringHelper.getRepeated ('a',
                                                                      PeppolIdentifierHelper.MAX_PROCESS_VALUE_LENGTH +
                                                                           1)));
    assertNull (aIF.parseProcessIdentifier ("process:proc2"));
    assertNull (aIF.parseProcessIdentifier ("processproc2"));
  }

  @Test
  @SuppressFBWarnings ("NP_NONNULL_PARAM_VIOLATION")
  public void testConstraints ()
  {
    try
    {
      // null key not allowed
      new PeppolProcessIdentifier (aIF, null, "value");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // invalid scheme
      new PeppolProcessIdentifier (aIF, "", "value");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // separator is forbidden
      new PeppolProcessIdentifier (aIF, "abc::def", "value");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // null value not allowed
      new PeppolProcessIdentifier (aIF, PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME, null);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Both null not allowed
      new PeppolProcessIdentifier (aIF, null, null);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Empty is not allowed
      new PeppolProcessIdentifier (aIF, PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME, "");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Cannot be mapped to ISO-8859-1:
      new PeppolProcessIdentifier (aIF, PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME, "Љ");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Scheme too long
      new PeppolProcessIdentifier (aIF,
                                   PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME +
                                        StringHelper.getRepeated ('a',
                                                                  PeppolIdentifierHelper.MAX_IDENTIFIER_SCHEME_LENGTH +
                                                                       1),
                                   "abc");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Value too long
      new PeppolProcessIdentifier (aIF,
                                   PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME,
                                   StringHelper.getRepeated ('a', PeppolIdentifierHelper.MAX_PROCESS_VALUE_LENGTH + 1));
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }

  @Test
  public void testHasDefaultProcessIdentifierScheme ()
  {
    assertTrue (aIF.createProcessIdentifierWithDefaultScheme ("abc")
                   .hasScheme (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME));
    assertFalse (new PeppolProcessIdentifier (aIF, "proctype", "abc").hasDefaultScheme ());
  }
}
