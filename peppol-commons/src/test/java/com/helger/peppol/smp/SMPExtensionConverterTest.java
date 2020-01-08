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
package com.helger.peppol.smp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

    // Cannot convert non-element
    assertNull (SMPExtensionConverter.convert ("Plain text"));
  }

  @Test
  public void testConvertFromExtensionType ()
  {
    // Try converting an empty extension
    assertNull (SMPExtensionConverter.convertToString ((ExtensionType) null));
    assertNull (SMPExtensionConverter.convertToString (new ObjectFactory ().createExtensionType ()));
  }
}
