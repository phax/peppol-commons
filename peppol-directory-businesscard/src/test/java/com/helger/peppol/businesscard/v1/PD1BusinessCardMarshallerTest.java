/*
 * Copyright (C) 2015-2025 Philip Helger (www.helger.com)
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
package com.helger.peppol.businesscard.v1;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.nio.charset.Charset;

import org.jspecify.annotations.NonNull;
import org.junit.Test;

import com.helger.io.file.SimpleFileIO;
import com.helger.peppol.businesscard.helper.PDBusinessCardHelper;

/**
 * Test class for class {@link PD1BusinessCardMarshaller}.
 *
 * @author Philip Helger
 */
public final class PD1BusinessCardMarshallerTest
{
  private static void _testBC (@NonNull final String sFilename)
  {
    final byte [] aBytes = SimpleFileIO.getAllFileBytes (new File (sFilename));
    final PD1BusinessCardMarshaller aMarshaller = new PD1BusinessCardMarshaller ();
    final PD1BusinessCardType aBC = aMarshaller.read (aBytes);
    assertNotNull (aBC);
    assertNotNull (PD1APIHelper.createBusinessCard (aBC));
    assertNotNull (PD1APIHelper.createBusinessCard (aBC).getAsMicroXML ("urn:test", "bc"));
    assertNotNull (PD1APIHelper.createBusinessCard (aBC).getAsJson ());
    assertNotNull (PDBusinessCardHelper.parseBusinessCard (aBytes, (Charset) null));
  }

  @Test
  public void testBasic ()
  {
    _testBC ("src/test/resources/example/v1/business-card-test1.xml");
    _testBC ("src/test/resources/example/v1/business-card-example-spec-v1.xml");
    _testBC ("src/test/resources/example/v1/bc-9915-leckma.xml");
    _testBC ("src/test/resources/example/v1/bc-0088-5033466000005.xml");
  }
}
