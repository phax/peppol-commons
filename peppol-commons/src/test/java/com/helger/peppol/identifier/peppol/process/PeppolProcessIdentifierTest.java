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
package com.helger.peppol.identifier.peppol.process;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.peppol.CPeppolIdentifier;
import com.helger.peppol.identifier.peppol.process.IPeppolProcessIdentifier;
import com.helger.peppol.identifier.peppol.process.PeppolProcessIdentifier;

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

    assertEquals (aID, aID2);
    CommonsTestHelper.testMicroTypeConversion (aID2);
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
    final PeppolProcessIdentifier aID1 = new PeppolProcessIdentifier ("scheme1", "value1");
    assertEquals ("scheme1::value1", aID1.getURIEncoded ());
    assertEquals ("scheme1%3A%3Avalue1", aID1.getURIPercentEncoded ());
    final PeppolProcessIdentifier aID2 = PeppolProcessIdentifier.createFromURIPart ("scheme1::value1");
    assertEquals (aID1, aID2);

    assertNull (PeppolProcessIdentifier.createFromURIPartOrNull ("scheme1"));
    assertNull (PeppolProcessIdentifier.createFromURIPartOrNull (null));
    try
    {
      // No separator
      PeppolProcessIdentifier.createFromURIPart ("scheme1");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }

  @Test
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
      // null value not allowed
      new PeppolProcessIdentifier (IPeppolProcessIdentifier.DEFAULT_SCHEME, null);
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
      new PeppolProcessIdentifier (IPeppolProcessIdentifier.DEFAULT_SCHEME, "");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Cannot be mapped to ISO-8859-1:
      new PeppolProcessIdentifier (IPeppolProcessIdentifier.DEFAULT_SCHEME, "Љ");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Scheme too long
      new PeppolProcessIdentifier (IPeppolProcessIdentifier.DEFAULT_SCHEME +
                                   StringHelper.getRepeated ('a', CPeppolIdentifier.MAX_IDENTIFIER_SCHEME_LENGTH + 1),
                                   "abc");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Value too long
      new PeppolProcessIdentifier (IPeppolProcessIdentifier.DEFAULT_SCHEME,
                                   StringHelper.getRepeated ('a',
                                                             IPeppolProcessIdentifier.MAX_VALUE_LENGTH +
                                                                  1));
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }

  @Test
  public void testIsValidProcessIdentifierValue ()
  {
    assertFalse (IPeppolProcessIdentifier.isValidValue (null));
    assertFalse (IPeppolProcessIdentifier.isValidValue (""));

    assertTrue (IPeppolProcessIdentifier.isValidValue ("proc1"));
    assertTrue (IPeppolProcessIdentifier.isValidValue ("proc2 "));

    assertTrue (IPeppolProcessIdentifier.isValidValue (StringHelper.getRepeated ('a',
                                                                                 IPeppolProcessIdentifier.MAX_VALUE_LENGTH)));
    assertFalse (IPeppolProcessIdentifier.isValidValue (StringHelper.getRepeated ('a',
                                                                                  IPeppolProcessIdentifier.MAX_VALUE_LENGTH +
                                                                                       1)));
  }

  @Test
  public void testHasDefaultProcessIdentifierScheme ()
  {
    assertTrue (PeppolProcessIdentifier.createWithDefaultScheme ("abc").hasDefaultScheme ());
    assertFalse (new PeppolProcessIdentifier ("proctype", "abc").hasDefaultScheme ());
  }

  @Test
  public void testIsValidProcessIdentifier ()
  {
    assertFalse (PeppolProcessIdentifier.isValidURIPart (null));
    assertFalse (PeppolProcessIdentifier.isValidURIPart (""));

    assertTrue (PeppolProcessIdentifier.isValidURIPart ("process::proc1"));
    assertTrue (PeppolProcessIdentifier.isValidURIPart ("process::proc2 "));

    assertFalse (PeppolProcessIdentifier.isValidURIPart ("processany-actorid-dummythatiswaytoolongforwhatisexpected::proc2"));
    assertFalse (PeppolProcessIdentifier.isValidURIPart ("process::" +
                                                         StringHelper.getRepeated ('a',
                                                                                   IPeppolProcessIdentifier.MAX_VALUE_LENGTH +
                                                                                        1)));
    assertFalse (PeppolProcessIdentifier.isValidURIPart ("process:proc2"));
    assertFalse (PeppolProcessIdentifier.isValidURIPart ("processproc2"));
  }
}
