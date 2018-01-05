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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for class {@link PredefinedDocumentTypeIdentifierManager}.
 * 
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class PredefinedDocumentTypeIdentifierManagerTest
{
  @Test
  public void testAll ()
  {
    assertNotNull (PredefinedDocumentTypeIdentifierManager.getAllDocumentTypeIdentifiers ());
    assertNotNull (PredefinedDocumentTypeIdentifierManager.getAllDocumentTypeIdentifierIDs ());

    assertNotNull (PredefinedDocumentTypeIdentifierManager.getDocumentTypeIdentifierOfID ("urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0::2.0"));
    assertNull (PredefinedDocumentTypeIdentifierManager.getDocumentTypeIdentifierOfID ("urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0::2.0a"));

    assertTrue (PredefinedDocumentTypeIdentifierManager.containsDocumentTypeIdentifierWithID ("urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0::2.0"));
    assertFalse (PredefinedDocumentTypeIdentifierManager.containsDocumentTypeIdentifierWithID ("urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0::2.0a"));

    // all enum ones must be contained
    for (final EPredefinedDocumentTypeIdentifier eDocID : EPredefinedDocumentTypeIdentifier.values ())
    {
      assertSame (eDocID, PredefinedDocumentTypeIdentifierManager.getDocumentTypeIdentifierOfID (eDocID.getValue ()));
      assertTrue (PredefinedDocumentTypeIdentifierManager.containsDocumentTypeIdentifierWithID (eDocID.getValue ()));
    }
  }
}
