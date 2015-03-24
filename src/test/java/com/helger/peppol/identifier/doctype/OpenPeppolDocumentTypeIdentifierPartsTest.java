/**
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
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
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.identifier.doctype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.commons.collections.CollectionHelper;

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
    assertEquals (CollectionHelper.newList ("subtype", "ext1", "ext2"), aParts.getExtensionIDs ());
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
}
