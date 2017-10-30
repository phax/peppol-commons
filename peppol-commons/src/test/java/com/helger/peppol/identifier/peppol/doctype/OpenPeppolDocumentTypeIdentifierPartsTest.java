/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Version: MPL 2.0/EUPL 1.2
 * -
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 * -
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.2 or - as soon they will be approved
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
 * -
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
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
