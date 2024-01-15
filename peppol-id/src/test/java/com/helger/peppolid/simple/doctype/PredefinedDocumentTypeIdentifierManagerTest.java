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
