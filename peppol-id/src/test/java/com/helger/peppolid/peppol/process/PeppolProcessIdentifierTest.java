/**
 * Copyright (C) 2015-2020 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
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
import com.helger.peppolid.peppol.process.PeppolProcessIdentifier;
import com.helger.xml.mock.XMLTestHelper;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Test class for class {@link PeppolProcessIdentifier}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class PeppolProcessIdentifierTest
{
  @Test
  public void testCtor ()
  {
    final PeppolProcessIdentifier aID = new PeppolProcessIdentifier ("scheme", "value");
    assertEquals ("scheme", aID.getScheme ());
    assertEquals ("value", aID.getValue ());

    // Create a copy
    final PeppolProcessIdentifier aID2 = new PeppolProcessIdentifier (aID);
    assertEquals ("scheme", aID2.getScheme ());
    assertEquals ("value", aID2.getValue ());

    assertTrue (aID.hasSameContent (aID2));
    XMLTestHelper.testMicroTypeConversion (aID2);
  }

  @Test
  public void testBasicMethods ()
  {
    final PeppolProcessIdentifier aID1 = new PeppolProcessIdentifier ("scheme", "value");
    final PeppolProcessIdentifier aID2 = new PeppolProcessIdentifier ("scheme", "value");
    final PeppolProcessIdentifier aID3 = new PeppolProcessIdentifier ("scheme2", "value");
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aID1, aID2);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aID1, aID3);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aID2, aID3);
  }

  @Test
  public void testURIStuff ()
  {
    final IIdentifierFactory aIF = PeppolIdentifierFactory.INSTANCE;
    final IProcessIdentifier aID1 = new PeppolProcessIdentifier ("scheme1", "value1");
    assertEquals ("scheme1::value1", aID1.getURIEncoded ());
    assertEquals ("scheme1%3A%3Avalue1", aID1.getURIPercentEncoded ());
    final IProcessIdentifier aID2 = aIF.parseProcessIdentifier ("scheme1::value1");
    assertEquals (aID1, aID2);

    assertNull (aIF.parseProcessIdentifier ("scheme1"));
    assertNull (aIF.parseProcessIdentifier (null));
    assertNull (aIF.parseProcessIdentifier (""));

    assertNotNull (aIF.parseProcessIdentifier ("process::proc1"));
    assertNotNull (aIF.parseProcessIdentifier ("process::proc2 "));

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
      new PeppolProcessIdentifier (null, "value");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // invalid scheme
      new PeppolProcessIdentifier ("", "value");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // separator is forbidden
      new PeppolProcessIdentifier ("abc::def", "value");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // null value not allowed
      new PeppolProcessIdentifier (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME, null);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Both null not allowed
      new PeppolProcessIdentifier (null, null);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Empty is not allowed
      new PeppolProcessIdentifier (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME, "");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Cannot be mapped to ISO-8859-1:
      new PeppolProcessIdentifier (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME, "Љ");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Scheme too long
      new PeppolProcessIdentifier (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME +
                                   StringHelper.getRepeated ('a',
                                                             PeppolIdentifierHelper.MAX_IDENTIFIER_SCHEME_LENGTH + 1),
                                   "abc");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Value too long
      new PeppolProcessIdentifier (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME,
                                   StringHelper.getRepeated ('a', PeppolIdentifierHelper.MAX_PROCESS_VALUE_LENGTH + 1));
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }

  @Test
  public void testHasDefaultProcessIdentifierScheme ()
  {
    final IIdentifierFactory aIF = PeppolIdentifierFactory.INSTANCE;
    assertTrue (aIF.createProcessIdentifierWithDefaultScheme ("abc")
                   .hasScheme (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME));
    assertFalse (new PeppolProcessIdentifier ("proctype", "abc").hasDefaultScheme ());
  }
}
