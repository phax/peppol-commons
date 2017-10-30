/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
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
