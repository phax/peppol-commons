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
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aTP,
                                                                           new SMPTransportProfile ("id2", "name"));
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
