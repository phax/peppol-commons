/*
 * Copyright (C) 2015-2023 Philip Helger (www.helger.com)
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
package com.helger.pd.businesscard.v2;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.nio.charset.Charset;

import javax.annotation.Nonnull;

import org.junit.Test;

import com.helger.commons.io.file.SimpleFileIO;
import com.helger.pd.businesscard.helper.PDBusinessCardHelper;

/**
 * Test class for class {@link PD2BusinessCardMarshaller}.
 *
 * @author Philip Helger
 */
public final class PD2BusinessCardMarshallerTest
{
  private static void _testBC (@Nonnull final String sFilename)
  {
    final byte [] aBytes = SimpleFileIO.getAllFileBytes (new File (sFilename));
    final PD2BusinessCardMarshaller aMarshaller = new PD2BusinessCardMarshaller ();
    final PD2BusinessCardType aBC = aMarshaller.read (aBytes);
    assertNotNull (aBC);
    assertNotNull (PD2APIHelper.createBusinessCard (aBC));
    assertNotNull (PD2APIHelper.createBusinessCard (aBC).getAsMicroXML ("urn:test", "bc"));
    assertNotNull (PD2APIHelper.createBusinessCard (aBC).getAsJson ());
    assertNotNull (PDBusinessCardHelper.parseBusinessCard (aBytes, (Charset) null));
  }

  @Test
  public void testBasic ()
  {
    _testBC ("src/test/resources/example/v2/business-card-test1.xml");
    _testBC ("src/test/resources/example/v2/business-card-example-spec-v2.xml");
    _testBC ("src/test/resources/example/v2/bc-9915-leckma.xml");
    _testBC ("src/test/resources/example/v2/bc-0088-5033466000005.xml");
    _testBC ("src/test/resources/example/v2/nemhandel.xml");
  }
}
