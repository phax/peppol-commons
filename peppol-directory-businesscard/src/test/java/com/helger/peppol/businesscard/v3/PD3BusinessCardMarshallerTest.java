/*
 * Copyright (C) 2015-2026 Philip Helger (www.helger.com)
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
package com.helger.peppol.businesscard.v3;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.nio.charset.Charset;

import org.jspecify.annotations.NonNull;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.io.file.SimpleFileIO;
import com.helger.peppol.businesscard.helper.PDBusinessCardHelper;

/**
 * Test class for class {@link PD3BusinessCardMarshaller}.
 *
 * @author Philip Helger
 */
public final class PD3BusinessCardMarshallerTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PD3BusinessCardMarshallerTest.class);

  private static void _testBC (@NonNull final String sFilename)
  {
    final byte [] aBytes = SimpleFileIO.getAllFileBytes (new File (sFilename));
    final PD3BusinessCardMarshaller aMarshaller = new PD3BusinessCardMarshaller ();
    final PD3BusinessCardType aBC = aMarshaller.read (aBytes);
    assertNotNull (aBC);
    assertNotNull (PD3APIHelper.createBusinessCard (aBC));
    assertNotNull (PD3APIHelper.createBusinessCard (aBC).getAsMicroXML ("urn:test", "bc"));
    assertNotNull (PD3APIHelper.createBusinessCard (aBC).getAsJson ());
    assertNotNull (PDBusinessCardHelper.parseBusinessCard (aBytes, (Charset) null));
  }

  @Test
  public void testBasic ()
  {
    _testBC ("src/test/resources/example/v3/bc-0088-5033466000005.xml");
    _testBC ("src/test/resources/example/v3/bc-9915-leckma.xml");
    _testBC ("src/test/resources/example/v3/bc-9930-de811152493.xml");
    _testBC ("src/test/resources/example/v3/business-card-cctf-103.xml");
    _testBC ("src/test/resources/example/v3/business-card-example-spec-v3.xml");
    _testBC ("src/test/resources/example/v3/business-card-test1.xml");
    _testBC ("src/test/resources/example/v3/business-card-test2.xml");
    _testBC ("src/test/resources/example/v3/bc1.xml");
  }

  @Test
  public void testBCFromScratch ()
  {
    final PD3BusinessCardType aBC = new PD3BusinessCardType ();
    {
      final PD3IdentifierType aParticipantIdentifier = new PD3IdentifierType ();
      aParticipantIdentifier.setScheme ("iso6523-actorid-upis");
      aParticipantIdentifier.setValue ("9915:helger");
      aBC.setParticipantIdentifier (aParticipantIdentifier);
    }

    final PD3BusinessEntityType aBusinessEntity = new PD3BusinessEntityType ();

    // Mandatory
    final PD3MultilingualNameType aName = new PD3MultilingualNameType ();
    aName.setLanguage ("en");
    aName.setValue ("Acme Inc.");
    aBusinessEntity.addName (aName);

    // Mandatory
    aBusinessEntity.setCountryCode ("AT");

    // Optional
    final PD3IdentifierType aAdditionalIdentifier = new PD3IdentifierType ();
    aAdditionalIdentifier.setScheme ("VAT");
    aAdditionalIdentifier.setValue ("ATU00000000");
    aBusinessEntity.addIdentifier (aAdditionalIdentifier);

    // Mandatory
    aBC.addBusinessEntity (aBusinessEntity);

    final String sBusinessCard = new PD3BusinessCardMarshaller ().setFormattedOutput (true).getAsString (aBC);
    assertNotNull (sBusinessCard);

    LOGGER.info (sBusinessCard);
  }
}
