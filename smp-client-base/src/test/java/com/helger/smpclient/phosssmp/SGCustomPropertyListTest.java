/*
 * Copyright (C) 2015-2026 Philip Helger (www.helger.com)
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
package com.helger.smpclient.phosssmp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.unittest.support.TestHelper;
import com.helger.xml.microdom.IMicroDocument;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.convert.MicroTypeConverter;
import com.helger.xml.microdom.serialize.MicroReader;
import com.helger.xml.mock.XMLTestHelper;

/**
 * Test class for class {@link SGCustomPropertyList}.
 *
 * @author Philip Helger
 */
public final class SGCustomPropertyListTest
{
  @Test
  public void testBasic ()
  {
    final SGCustomPropertyList aList = new SGCustomPropertyList (SGCustomProperty.createPublic ("website",
                                                                                               "https://example.org"),
                                                                 SGCustomProperty.createPrivate ("internal-note",
                                                                                                 "Test participant"));
    assertEquals (2, aList.size ());
    assertTrue (aList.containsName ("website"));
    assertEquals ("Test participant", aList.getValue ("internal-note"));
    assertNull (aList.getValue ("unknown"));
    // Duplicate name
    assertTrue (aList.add (SGCustomProperty.createPrivate ("website", "x")).isUnchanged ());
    assertEquals (1, aList.getFiltered (SGCustomProperty::isPublic).size ());

    TestHelper.testDefaultImplementationWithEqualContentObject (aList, new SGCustomPropertyList (aList));
    TestHelper.testDefaultImplementationWithDifferentContentObject (aList, new SGCustomPropertyList ());
    XMLTestHelper.testMicroTypeConversion (aList);
    XMLTestHelper.testMicroTypeConversion (SGCustomProperty.createPublic ("a", ""));

    assertEquals (aList, SGCustomPropertyList.fromJson (aList.getAsJson ()));
    assertFalse (aList.equals (null));
  }

  @Test
  public void testReadPhossSmpXML ()
  {
    // Example from the phoss SMP REST API documentation
    final IMicroDocument aDoc = MicroReader.readMicroXML ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                                          "<customproperties>\n" +
                                                          "  <customproperty type=\"public\" name=\"website\" value=\"https://example.org\"/>\n" +
                                                          "  <customproperty type=\"private\" name=\"internal-note\" value=\"Test participant\"/>\n" +
                                                          "</customproperties>");
    assertNotNull (aDoc);
    final SGCustomPropertyList aList = MicroTypeConverter.convertToNative (aDoc.getDocumentElement (),
                                                                          SGCustomPropertyList.class);
    assertNotNull (aList);
    assertEquals (2, aList.size ());
    assertEquals ("https://example.org", aList.getValue ("website"));

    final IMicroElement eRoot = MicroTypeConverter.convertToMicroElement (aList,
                                                                         SGCustomPropertyListMicroTypeConverter.ELEMENT_CUSTOM_PROPERTIES);
    assertEquals ("customproperties", eRoot.getTagName ());
    assertEquals (2, eRoot.getAllChildElements ().size ());
  }

  @Test
  public void testValidation ()
  {
    assertTrue (SGCustomProperty.isValidName ("hr.oib"));
    assertTrue (SGCustomProperty.isValidName ("a-b_c.1"));
    assertFalse (SGCustomProperty.isValidName (null));
    assertFalse (SGCustomProperty.isValidName (""));
    assertFalse (SGCustomProperty.isValidName ("a b"));
    assertFalse (SGCustomProperty.isValidName ("a/b"));

    assertTrue (SGCustomProperty.isValidValue (""));
    assertFalse (SGCustomProperty.isValidValue (null));
    assertFalse (SGCustomProperty.isValidValue ("a\nb"));
  }
}
