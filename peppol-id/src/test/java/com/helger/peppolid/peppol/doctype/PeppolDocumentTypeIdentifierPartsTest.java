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
import com.helger.commons.io.stream.StreamHelper;
import com.helger.peppolid.peppol.doctype.EPredefinedDocumentTypeIdentifier;
import com.helger.peppolid.peppol.doctype.IPeppolDocumentTypeIdentifierParts;
import com.helger.peppolid.peppol.doctype.PeppolDocumentTypeIdentifierParts;

/**
 * Test class for class {@link PeppolDocumentTypeIdentifierParts}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
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
    final IPeppolDocumentTypeIdentifierParts aParts = PeppolDocumentTypeIdentifierParts.extractFromString ("urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestRequest-2::ExpressionOfInterestRequest##urn:www.cenbii.eu:transaction:biitrdm081:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t001:ver1.0::2.2");
    assertNotNull (aParts);
    assertEquals ("urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestRequest-2", aParts.getRootNS ());
    assertEquals ("ExpressionOfInterestRequest", aParts.getLocalName ());
    assertEquals ("urn:www.cenbii.eu:transaction:biitrdm081:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t001:ver1.0::2.2",
                  aParts.getSubTypeIdentifier ());
    assertEquals ("urn:www.cenbii.eu:transaction:biitrdm081:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t001:ver1.0",
                  aParts.getCustomizationID ());
    assertEquals ("2.2", aParts.getVersion ());
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
    try (final CSVWriter aCSV = new CSVWriter (StreamHelper.createWriter (FileHelper.getOutputStream (new File ("doctypes.csv")),
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
