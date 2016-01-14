/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
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

/**
 * Test class for class {@link PeppolDocumentTypeIdentifierParts}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class PeppolDocumentTypeIdentifierPartsTest
{
  @Test
  public void testPredefined ()
  {
    for (final EPredefinedDocumentTypeIdentifier e : EPredefinedDocumentTypeIdentifier.values ())
    {
      IPeppolDocumentTypeIdentifierParts aParts;
      if (e.getParts () instanceof PeppolDocumentTypeIdentifierParts)
        aParts = PeppolDocumentTypeIdentifierParts.extractFromString (e.getValue ());
      else
        aParts = OpenPeppolDocumentTypeIdentifierParts.extractFromString (e.getValue ());
      assertNotNull (aParts);

      // Check BusDox parts
      assertEquals (e.getRootNS (), aParts.getRootNS ());
      assertEquals (e.getLocalName (), aParts.getLocalName ());
      assertEquals (e.getSubTypeIdentifier (), aParts.getSubTypeIdentifier ());

      // Check PEPPOL parts
      assertEquals (e.getTransactionID (), aParts.getTransactionID ());
      assertEquals (e.getExtensionIDs (), aParts.getExtensionIDs ());
      assertEquals (e.getVersion (), aParts.getVersion ());
      assertEquals (e.getAsUBLCustomizationID (), aParts.getAsUBLCustomizationID ());
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
      PeppolDocumentTypeIdentifierParts.extractFromString ("root::local##subtype");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // No transaction separator
      PeppolDocumentTypeIdentifierParts.extractFromString ("root::local##subtype::");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // No transaction separator
      PeppolDocumentTypeIdentifierParts.extractFromString ("root::local##subtype::version");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // No transactions
      PeppolDocumentTypeIdentifierParts.extractFromString ("root::local##subtype:#::version");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // empty transaction (between ext2 and ext3)
      PeppolDocumentTypeIdentifierParts.extractFromString ("root::local##subtype:#ext1#ext2##ext3::version");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // empty transaction ID
      PeppolDocumentTypeIdentifierParts.extractFromString ("root::local##:#ext1#ext2::version");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // empty version
      PeppolDocumentTypeIdentifierParts.extractFromString ("root::local##subtype:#ext1#ext2::");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }
}
