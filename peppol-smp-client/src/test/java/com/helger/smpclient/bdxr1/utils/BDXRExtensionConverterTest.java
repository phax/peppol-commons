/*
 * Copyright (C) 2015-2022 Philip Helger
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
package com.helger.smpclient.bdxr1.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.w3c.dom.Node;

import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.xsds.bdxr.smp1.ExtensionType;
import com.helger.xsds.bdxr.smp1.ObjectFactory;

/**
 * Test class for class {@link BDXR1ExtensionConverter}.
 *
 * @author Philip Helger
 */
public final class BDXRExtensionConverterTest
{
  @Test
  public void testConvertFromString ()
  {
    // Use elements
    final String sJson = "[{\"ID\":\"a\",\"Name\":\"b\",\"AgencyID\":\"c\",\"AgencyName\":\"d\",\"AgencyURI\":\"e\",\"VersionID\":\"f\",\"URI\":\"g\",\"ReasonCode\":\"h\",\"Reason\":\"i\"," +
                         "\"Any\":\"<any xmlns=\\\"urn:foo\\\"><child>text1</child><child2 /></any>\"}]";
    final ICommonsList <ExtensionType> aExtensions = BDXR1ExtensionConverter.convert (sJson);
    assertNotNull (aExtensions);
    assertEquals (1, aExtensions.size ());
    final ExtensionType aExtension = aExtensions.get (0);
    assertNotNull (aExtension.getAny ());
    assertTrue (aExtension.getAny () instanceof Node);

    assertNull (BDXR1ExtensionConverter.convert ((String) null));
    assertNull (BDXR1ExtensionConverter.convert (""));

    // Convert back to String
    final String sJson2 = BDXR1ExtensionConverter.convertToString (new CommonsArrayList <> (aExtension));
    assertEquals (sJson, sJson2);

    // Cannot convert non-element
    assertNull (BDXR1ExtensionConverter.convert ("Plain text"));
  }

  @Test
  public void testConvertFromExtensionType ()
  {
    // Try converting an empty extension
    assertNull (BDXR1ExtensionConverter.convertToString (null));
    assertEquals ("[]", BDXR1ExtensionConverter.convertToString (new CommonsArrayList <> (new ObjectFactory ().createExtensionType ())));
  }

  @Test
  public void testConvertFromXML ()
  {
    assertNull (BDXR1ExtensionConverter.convertXMLToSingleExtension (null));
    assertNull (BDXR1ExtensionConverter.convertXMLToSingleExtension (""));
    final ICommonsList <ExtensionType> aExts = BDXR1ExtensionConverter.convertXMLToSingleExtension ("<foobar />");
    assertNotNull (aExts);
    assertEquals (1, aExts.size ());
    assertEquals ("[{\"Any\":\"<foobar />\"}]", BDXR1ExtensionConverter.convertToString (aExts));
  }
}
