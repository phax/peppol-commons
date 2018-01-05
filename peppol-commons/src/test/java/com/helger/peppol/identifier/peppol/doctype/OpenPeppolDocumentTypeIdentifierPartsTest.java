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
package com.helger.peppol.identifier.peppol.doctype;

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
import com.helger.commons.string.StringHelper;

/**
 * Test class for class {@link OpenPeppolDocumentTypeIdentifierParts}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class OpenPeppolDocumentTypeIdentifierPartsTest
{
  @Test
  public void testBasic ()
  {
    final IPeppolDocumentTypeIdentifierParts aParts = OpenPeppolDocumentTypeIdentifierParts.extractFromString ("root::local##basic:extended:subtype:extended:ext1:extended:ext2::ver1");
    assertNotNull (aParts);
    assertEquals ("root", aParts.getRootNS ());
    assertEquals ("local", aParts.getLocalName ());
    assertEquals ("basic:extended:subtype:extended:ext1:extended:ext2::ver1", aParts.getSubTypeIdentifier ());
    assertEquals ("basic", aParts.getTransactionID ());
    assertEquals (new CommonsArrayList <> ("subtype", "ext1", "ext2"), aParts.getExtensionIDs ());
    assertEquals ("ver1", aParts.getVersion ());
  }

  @Test
  public void testInvalid ()
  {
    try
    {
      // No subtype present
      OpenPeppolDocumentTypeIdentifierParts.extractFromString ("root::local");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // No version separator
      OpenPeppolDocumentTypeIdentifierParts.extractFromString ("root::local##subtype");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Empty version
      OpenPeppolDocumentTypeIdentifierParts.extractFromString ("root::local##subtype::");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // No transaction separator
      OpenPeppolDocumentTypeIdentifierParts.extractFromString ("root::local##subtype::version");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // No transactions
      OpenPeppolDocumentTypeIdentifierParts.extractFromString ("root::local##subtype::version");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // empty transaction (before :extended:)
      OpenPeppolDocumentTypeIdentifierParts.extractFromString ("root::local##:extended:foo::version");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // empty customization ID (after :extended:)
      OpenPeppolDocumentTypeIdentifierParts.extractFromString ("root::local##bar:extended::version");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // empty customization ID and transaction
      OpenPeppolDocumentTypeIdentifierParts.extractFromString ("root::local##:extended::extended::version");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // empty version
      OpenPeppolDocumentTypeIdentifierParts.extractFromString ("root::local:extended:subtype:extended:ext1:extended:ext2::");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }

  @Test
  public void testList () throws IOException
  {
    try (final CSVWriter aCSV = new CSVWriter (StreamHelper.createWriter (FileHelper.getOutputStream (new File ("doctypes.csv")),
                                                                          StandardCharsets.ISO_8859_1)).setSeparatorChar (';'))
    {
      aCSV.writeNext ("Status",
                      "Namespace URI",
                      "Local name",
                      "Transaction ID",
                      "Extension",
                      "Version",
                      "Customization ID");
      SimpleFileIO.readFileLines (new File ("src/test/resources/doctypes.txt"), StandardCharsets.UTF_8, sDocTypeID -> {
        final ICommonsList <String> aResult = new CommonsArrayList <> ();
        IPeppolDocumentTypeIdentifierParts aParts = null;
        try
        {
          aParts = OpenPeppolDocumentTypeIdentifierParts.extractFromString (sDocTypeID);
        }
        catch (final IllegalArgumentException ex)
        {
          try
          {
            aParts = PeppolDocumentTypeIdentifierParts.extractFromString (sDocTypeID);
          }
          catch (final IllegalArgumentException ex2)
          {
            aResult.add ("Error");
            aResult.add (ex.getMessage ());
            aResult.add (ex2.getMessage ());
            aResult.add (sDocTypeID);
          }
        }
        if (aParts != null)
        {
          aResult.add ("OK");
          aResult.add (aParts.getRootNS ());
          aResult.add (aParts.getLocalName ());
          aResult.add (aParts.getTransactionID ());
          aResult.add (StringHelper.getImploded (':', aParts.getExtensionIDs ()));
          aResult.add (aParts.getVersion ());
          aResult.add (aParts.getAsUBLCustomizationID ());
        }
        aCSV.writeNext (aResult);
      });
    }
  }
}
