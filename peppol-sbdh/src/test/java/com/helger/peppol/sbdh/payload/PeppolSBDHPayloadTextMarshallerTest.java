/*
 * Copyright (C) 2014-2023 Philip Helger
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
package com.helger.peppol.sbdh.payload;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.nio.charset.StandardCharsets;

import org.junit.Test;

import com.helger.commons.base64.Base64;
import com.helger.commons.collection.ArrayHelper;
import com.helger.peppol.sbdh.spec12.BinaryContentType;

/**
 * Test class for class {@link PeppolSBDHPayloadTextMarshaller}.
 *
 * @author Philip Helger
 */
public final class PeppolSBDHPayloadTextMarshallerTest
{
  @Test
  public void testBinary ()
  {
    final byte [] aSrc = "Hallo Wält".getBytes (StandardCharsets.UTF_8);
    final String sEncoded = Base64.safeEncodeBytes (aSrc);
    final PeppolSBDHPayloadBinaryMarshaller m = new PeppolSBDHPayloadBinaryMarshaller ();

    // Regular case
    BinaryContentType aCT = m.read ("<BinaryContent xmlns='http://peppol.eu/xsd/ticc/envelope/1.0' mimeType='bla/foo'>" +
                                    sEncoded +
                                    "</BinaryContent>");
    assertNotNull (aCT);
    assertEquals ("bla/foo", aCT.getMimeType ());
    assertArrayEquals (aSrc, aCT.getValue ());
    assertNull (aCT.getEncoding ());

    // With encoding
    aCT = m.read ("<BinaryContent xmlns='http://peppol.eu/xsd/ticc/envelope/1.0' mimeType='bla/foo' encoding='utf16be'>" +
                  sEncoded +
                  "</BinaryContent>");
    assertNotNull (aCT);
    assertEquals ("bla/foo", aCT.getMimeType ());
    assertArrayEquals (aSrc, aCT.getValue ());
    assertEquals ("utf16be", aCT.getEncoding ());

    // Empty value
    aCT = m.read ("<BinaryContent xmlns='http://peppol.eu/xsd/ticc/envelope/1.0' mimeType='bla/foo' />");
    assertNotNull (aCT);
    assertEquals ("bla/foo", aCT.getMimeType ());
    assertArrayEquals (ArrayHelper.EMPTY_BYTE_ARRAY, aCT.getValue ());
    assertNull (aCT.getEncoding ());

    // Empty value again
    aCT = m.read ("<BinaryContent xmlns='http://peppol.eu/xsd/ticc/envelope/1.0' mimeType='bla/foo'></BinaryContent>");
    assertNotNull (aCT);
    assertEquals ("bla/foo", aCT.getMimeType ());
    assertArrayEquals (ArrayHelper.EMPTY_BYTE_ARRAY, aCT.getValue ());
    assertNull (aCT.getEncoding ());

    // Wrong NS
    assertNull (m.read ("<BinaryContent xmlns='ttp://peppol.eu/xsd/ticc/envelope/1.0' mimeType='bla/foo'>" +
                        sEncoded +
                        "</BinaryContent>"));
    // Wrong element name
    assertNull (m.read ("<BinaryContent2 xmlns='http://peppol.eu/xsd/ticc/envelope/1.0' mimeType='bla/foo'>" +
                        sEncoded +
                        "</BinaryContent2>"));
    // No MimeType attribute
    assertNull (m.read ("<BinaryContent xmlns='http://peppol.eu/xsd/ticc/envelope/1.0' mimeType2='bla/foo'>" +
                        sEncoded +
                        "</BinaryContent>"));
  }
}
