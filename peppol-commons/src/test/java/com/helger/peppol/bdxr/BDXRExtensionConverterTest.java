/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
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
package com.helger.peppol.bdxr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
    final String sJson2 = BDXRExtensionConverter.convertToString (new CommonsArrayList<> (aExtension));
    assertEquals (sJson, sJson2);

    try
    {
      // Cannot convert non-element
      BDXRExtensionConverter.convert ("Plain text");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }

    // Cannot convert non-element
    assertNull (BDXRExtensionConverter.convertOrNull ("Plain text"));
  }

  @Test
  public void testConvertFromExtensionType ()
  {
    // Try converting an empty extension
    assertNull (BDXRExtensionConverter.convertToString (null));
    assertEquals ("[]",
                  BDXRExtensionConverter.convertToString (new CommonsArrayList<> (new ObjectFactory ().createExtensionType ())));
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
