/*
 * Copyright (C) 2015-2023 Philip Helger
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
package com.helger.peppolid.peppol.doctype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.csv.CSVWriter;
import com.helger.commons.io.file.FileHelper;
import com.helger.commons.io.file.SimpleFileIO;

/**
 * Test class for class {@link PeppolDocumentTypeIdentifierParts}.
 *
 * @author Philip Helger
 */
public final class PeppolDocumentTypeIdentifierPartsTest
{
  @Test
  public void testBasic1 ()
  {
    final IPeppolDocumentTypeIdentifierParts aParts = PeppolDocumentTypeIdentifierParts.extractFromString ("root::local##basic:extended:subtype:extended:ext1:extended:ext2::ver1");
    assertNotNull (aParts);
    assertEquals ("root", aParts.getRootNS ());
    assertEquals ("local", aParts.getLocalName ());
    assertEquals ("basic:extended:subtype:extended:ext1:extended:ext2::ver1", aParts.getSubTypeIdentifier ());
    assertEquals ("basic:extended:subtype:extended:ext1:extended:ext2", aParts.getCustomizationID ());
    assertEquals ("ver1", aParts.getVersion ());
  }

  @Test
  public void testBasic2 ()
  {
    IPeppolDocumentTypeIdentifierParts aParts = PeppolDocumentTypeIdentifierParts.extractFromString ("urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestRequest-2::ExpressionOfInterestRequest##urn:www.cenbii.eu:transaction:biitrdm081:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t001:ver1.0::2.2");
    assertNotNull (aParts);
    assertEquals ("urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestRequest-2", aParts.getRootNS ());
    assertEquals ("ExpressionOfInterestRequest", aParts.getLocalName ());
    assertEquals ("urn:www.cenbii.eu:transaction:biitrdm081:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t001:ver1.0::2.2",
                  aParts.getSubTypeIdentifier ());
    assertEquals ("urn:www.cenbii.eu:transaction:biitrdm081:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t001:ver1.0",
                  aParts.getCustomizationID ());
    assertEquals ("2.2", aParts.getVersion ());

    aParts = PeppolDocumentTypeIdentifierParts.extractFromString ("urn:kosit:names:spec:peppol-reporting:schema:xsd:Reporting-1::APData##Reporting::1.0");
    assertEquals ("urn:kosit:names:spec:peppol-reporting:schema:xsd:Reporting-1", aParts.getRootNS ());
    assertEquals ("APData", aParts.getLocalName ());
    assertEquals ("Reporting::1.0", aParts.getSubTypeIdentifier ());
    assertEquals ("Reporting", aParts.getCustomizationID ());
    assertEquals ("1.0", aParts.getVersion ());
  }

  @Test
  public void testPredefined ()
  {
    for (final EPredefinedDocumentTypeIdentifier e : EPredefinedDocumentTypeIdentifier.values ())
    {
      final IPeppolDocumentTypeIdentifierParts aParts = PeppolDocumentTypeIdentifierParts.extractFromString (e.getValue ());
      assertNotNull (aParts);

      // Check BusDox parts
      assertEquals (e.getRootNS (), aParts.getRootNS ());
      assertEquals (e.getLocalName (), aParts.getLocalName ());
      assertEquals (e.getSubTypeIdentifier (), aParts.getSubTypeIdentifier ());

      // Check PEPPOL parts
      final IPeppolDocumentTypeIdentifierParts p = e.getParts ();
      assertEquals (p.getCustomizationID (), aParts.getCustomizationID ());
      assertEquals (p.getVersion (), aParts.getVersion ());
    }
  }

  @Test
  public void testInvalid ()
  {
    try
    {
      // No subtype present
      PeppolDocumentTypeIdentifierParts.extractFromString ("root::local");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // No version separator
      PeppolDocumentTypeIdentifierParts.extractFromString ("root::local##customization");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Empty version
      PeppolDocumentTypeIdentifierParts.extractFromString ("root::local##customization::");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Empty customization
      PeppolDocumentTypeIdentifierParts.extractFromString ("root::local##::version");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    assertNotNull (PeppolDocumentTypeIdentifierParts.extractFromString ("root::local##customization::version"));
  }

  @Test
  public void testList () throws IOException
  {
    try (final CSVWriter aCSV = new CSVWriter (FileHelper.getBufferedWriter (new File ("target/doctypes.csv"),
                                                                             StandardCharsets.ISO_8859_1)))
    {
      aCSV.setSeparatorChar (';');
      aCSV.writeNext ("Status", "Namespace URI", "Local name", "Customization ID", "Version");
      SimpleFileIO.readFileLines (new File ("src/test/resources/doctypes.txt"), StandardCharsets.UTF_8, sDocTypeID -> {
        final ICommonsList <String> aResult = new CommonsArrayList <> ();
        try
        {
          final IPeppolDocumentTypeIdentifierParts aParts = PeppolDocumentTypeIdentifierParts.extractFromString (sDocTypeID);
          assertNotNull (aParts);
          aResult.add ("OK");
          aResult.add (aParts.getRootNS ());
          aResult.add (aParts.getLocalName ());
          aResult.add (aParts.getCustomizationID ());
          aResult.add (aParts.getVersion ());
        }
        catch (final IllegalArgumentException ex)
        {
          aResult.add ("Error");
          aResult.add (ex.getMessage ());
          aResult.add (sDocTypeID);
        }
        aCSV.writeNext (aResult);
      });
    }
  }
}
