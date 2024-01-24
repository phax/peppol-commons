/*
 * Copyright (C) 2015-2024 Philip Helger
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for class {@link PredefinedDocumentTypeIdentifierManager}.
 *
 * @author Philip Helger
 */
public final class PredefinedDocumentTypeIdentifierManagerTest
{
  @Test
  public void testAll ()
  {
    assertNotNull (PredefinedDocumentTypeIdentifierManager.getAllDocumentTypeIdentifiers ());
    assertNotNull (PredefinedDocumentTypeIdentifierManager.getAllDocumentTypeIdentifierIDs ());

    assertNotNull (PredefinedDocumentTypeIdentifierManager.getDocumentTypeIdentifierOfID ("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0::2.0"));
    assertNull (PredefinedDocumentTypeIdentifierManager.getDocumentTypeIdentifierOfID ("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0::2.0a"));

    assertTrue (PredefinedDocumentTypeIdentifierManager.containsDocumentTypeIdentifierWithID ("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0::2.0"));
    assertFalse (PredefinedDocumentTypeIdentifierManager.containsDocumentTypeIdentifierWithID ("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0::2.0a"));

    // all enum ones must be contained
    for (final EPredefinedDocumentTypeIdentifier eDocID : EPredefinedDocumentTypeIdentifier.values ())
    {
      assertSame (eDocID,
                  PredefinedDocumentTypeIdentifierManager.getDocumentTypeIdentifierOfID (eDocID.getURIEncoded ()));
      assertTrue (PredefinedDocumentTypeIdentifierManager.containsDocumentTypeIdentifierWithID (eDocID.getURIEncoded ()));
    }
  }
}
