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
package com.helger.peppolid.simple.doctype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;
import com.helger.peppolid.simple.doctype.SimpleDocumentTypeIdentifier;
import com.helger.xml.mock.XMLTestHelper;

/**
 * Test class for class {@link SimpleDocumentTypeIdentifier}.
 *
 * @author Philip Helger
 */
public final class SimpleDocumentTypeIdentifierTest
{
  @Test
  public void testCtor ()
  {
    final SimpleDocumentTypeIdentifier aID = new SimpleDocumentTypeIdentifier ("scheme", "value");
    assertEquals ("scheme", aID.getScheme ());
    assertEquals ("value", aID.getValue ());

    final SimpleDocumentTypeIdentifier aID2 = new SimpleDocumentTypeIdentifier (aID);
    assertEquals ("scheme", aID2.getScheme ());
    assertEquals ("value", aID2.getValue ());

    assertTrue (aID.hasSameContent (aID2));
    XMLTestHelper.testMicroTypeConversion (aID2);
  }

  @Test
  public void testBasicMethods ()
  {
    final SimpleDocumentTypeIdentifier aID1 = new SimpleDocumentTypeIdentifier ("scheme", "value");
    final SimpleDocumentTypeIdentifier aID2 = new SimpleDocumentTypeIdentifier ("scheme", "value");
    final SimpleDocumentTypeIdentifier aID3 = new SimpleDocumentTypeIdentifier ("scheme2", "value");
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aID1, aID2);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aID1, aID3);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aID2, aID3);
  }
}
