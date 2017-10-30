/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Test class for class {@link SMPExtensionConverter}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class SMPExtensionConverterTest
{
  @Test
  public void testConvertFromString ()
  {
    // Use elements
    final String sXML = "<any xmlns=\"urn:foo\"><child>text1</child><child2 /></any>";
    final ExtensionType aExtension = SMPExtensionConverter.convert (sXML);
    assertNotNull (aExtension);
    assertNotNull (aExtension.getAny ());

    assertNull (SMPExtensionConverter.convert ((String) null));
    assertNull (SMPExtensionConverter.convert (""));

    // Convert back to String
    final String sXML2 = SMPExtensionConverter.convertToString (aExtension);
    assertEquals (sXML, sXML2);

    try
    {
      // Cannot convert non-element
      SMPExtensionConverter.convert ("Plain text");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }

    // Cannot convert non-element
    assertNull (SMPExtensionConverter.convertOrNull ("Plain text"));
  }

  @Test
  public void testConvertFromExtensionType ()
  {
    // Try converting an empty extension
    assertNull (SMPExtensionConverter.convertToString ((ExtensionType) null));
    assertNull (SMPExtensionConverter.convertToString (new ObjectFactory ().createExtensionType ()));
  }
}
