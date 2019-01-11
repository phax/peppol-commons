/**
 * Copyright (C) 2015-2019 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.smp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;
import com.helger.xml.mock.XMLTestHelper;

/**
 * Test class for class {@link SMPTransportProfile}.
 *
 * @author Philip Helger
 */
public final class SMPTransportProfileTest
{
  @Test
  public void testBasic ()
  {
    final SMPTransportProfile aTP = new SMPTransportProfile ("id", "name");
    assertEquals ("id", aTP.getID ());
    assertEquals ("name", aTP.getName ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aTP, new SMPTransportProfile ("id", "name"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aTP, new SMPTransportProfile ("id2", "name"));
    CommonsTestHelper.testDefaultSerialization (aTP);
    XMLTestHelper.testMicroTypeConversion (aTP);

    assertTrue (aTP.setName ("bla").isChanged ());
    assertEquals ("bla", aTP.getName ());
    assertFalse (aTP.setName ("bla").isChanged ());
    assertEquals ("bla", aTP.getName ());

    CommonsTestHelper.testDefaultSerialization (aTP);
    XMLTestHelper.testMicroTypeConversion (aTP);
  }
}
