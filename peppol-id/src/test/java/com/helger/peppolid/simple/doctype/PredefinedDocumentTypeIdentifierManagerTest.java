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
package com.helger.peppolid.simple.doctype;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.peppolid.peppol.doctype.PredefinedDocumentTypeIdentifierManager;

/**
 * Test class for class {@link PredefinedDocumentTypeIdentifierManager}.
 *
 * @author Philip Helger
 */
public class PredefinedDocumentTypeIdentifierManagerTest
{
  @Test
  public void testBasic ()
  {
    final String sValid = "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0::2.1";
    assertTrue (PredefinedDocumentTypeIdentifierManager.containsDocumentTypeIdentifierWithID (sValid));
    assertFalse (PredefinedDocumentTypeIdentifierManager.containsDocumentTypeIdentifierWithID (sValid + "0"));

    assertNotNull (PredefinedDocumentTypeIdentifierManager.getDocumentTypeIdentifierOfID (sValid));
    assertNull (PredefinedDocumentTypeIdentifierManager.getDocumentTypeIdentifierOfID (sValid + "0"));
  }
}
