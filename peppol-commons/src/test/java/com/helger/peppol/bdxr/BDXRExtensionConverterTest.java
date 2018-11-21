/**
 * Copyright (C) 2015-2018 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.bdxr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.w3c.dom.Node;

import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;

/**
 * Test class for class {@link BDXRExtensionConverter}.
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
    final ICommonsList <ExtensionType> aExtensions = BDXRExtensionConverter.convert (sJson);
    assertNotNull (aExtensions);
    assertEquals (1, aExtensions.size ());
    final ExtensionType aExtension = aExtensions.get (0);
    assertNotNull (aExtension.getAny ());
    assertTrue (aExtension.getAny () instanceof Node);

    assertNull (BDXRExtensionConverter.convert ((String) null));
    assertNull (BDXRExtensionConverter.convert (""));

    // Convert back to String
    final String sJson2 = BDXRExtensionConverter.convertToString (new CommonsArrayList <> (aExtension));
    assertEquals (sJson, sJson2);

    // Cannot convert non-element
    assertNull (BDXRExtensionConverter.convert ("Plain text"));
  }

  @Test
  public void testConvertFromExtensionType ()
  {
    // Try converting an empty extension
    assertNull (BDXRExtensionConverter.convertToString (null));
    assertEquals ("[]",
                  BDXRExtensionConverter.convertToString (new CommonsArrayList <> (new ObjectFactory ().createExtensionType ())));
  }

  @Test
  public void testConvertFromXML ()
  {
    assertNull (BDXRExtensionConverter.convertXMLToSingleExtension (null));
    assertNull (BDXRExtensionConverter.convertXMLToSingleExtension (""));
    final ICommonsList <ExtensionType> aExts = BDXRExtensionConverter.convertXMLToSingleExtension ("<foobar />");
    assertNotNull (aExts);
    assertEquals (1, aExts.size ());
    assertEquals ("[{\"Any\":\"<foobar />\"}]", BDXRExtensionConverter.convertToString (aExts));
  }
}
