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
package com.helger.peppol.bdxr.smp1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.w3c.dom.Node;

import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.peppol.bdxr.smp1.BDXR1ExtensionConverter;
import com.helger.xsds.bdxr.smp1.ExtensionType;
import com.helger.xsds.bdxr.smp1.ObjectFactory;

/**
 * Test class for class {@link BDXR1ExtensionConverter}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
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
    assertEquals ("[]",
                  BDXR1ExtensionConverter.convertToString (new CommonsArrayList <> (new ObjectFactory ().createExtensionType ())));
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