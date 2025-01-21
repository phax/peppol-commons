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
package com.helger.smpclient.extension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Test class for class {@link SMPExtensionList}.
 *
 * @author Philip Helger
 */
public final class SMPExtensionListTest
{
  @Test
  public void testConvertFromString ()
  {
    // Use elements
    final String sJson = "[{\"ID\":\"a\",\"Name\":\"b\",\"AgencyID\":\"c\",\"AgencyName\":\"d\",\"AgencyURI\":\"e\",\"VersionID\":\"f\",\"URI\":\"g\",\"ReasonCode\":\"h\",\"Reason\":\"i\"," +
                         "\"Any\":\"<any xmlns=\\\"urn:foo\\\"><child>text1</child><child2 /></any>\"}]";
    final SMPExtensionList aExtensions = SMPExtensionList.ofString (sJson);
    assertNotNull (aExtensions);
    assertEquals (1, aExtensions.extensions ().size ());
    final SMPExtension aExtension = aExtensions.extensions ().get (0);
    assertNotNull (aExtension.getAny ());

    // Convert back to String
    final String sJson2 = SMPExtensionList.of (aExtension).getExtensionsAsJsonString ();
    assertEquals (sJson, sJson2);

    // Cannot convert non-element
    assertNull (SMPExtensionList.ofString ((String) null));
    assertNull (SMPExtensionList.ofString (""));
    assertNull (SMPExtensionList.ofString ("Plain text"));
    assertNull (SMPExtensionList.ofString ("<brokenXml>"));
  }

  @Test
  public void testConvertFromObject ()
  {
    // Try converting an empty extension
    assertNull (SMPExtensionList.of (new SMPExtension ()).getExtensionsAsJsonString ());
    assertNull (SMPExtensionList.of (new SMPExtension (), new SMPExtension ()).getExtensionsAsJsonString ());
    assertEquals ("[{\"ID\":\"a\"},{\"ID\":\"b\"}]",
                  SMPExtensionList.of (new SMPExtension ().setExtensionID ("a"),
                                       new SMPExtension ().setExtensionID ("b"))
                                  .getExtensionsAsJsonString ());
  }

  @Test
  public void testConvertFromXML ()
  {
    final SMPExtensionList aExts = SMPExtensionList.ofString ("<foobar />");
    assertNotNull (aExts);
    assertEquals (1, aExts.extensions ().size ());
    assertEquals ("[{\"Any\":\"<foobar />\"}]", aExts.getExtensionsAsJsonString ());
  }
}
