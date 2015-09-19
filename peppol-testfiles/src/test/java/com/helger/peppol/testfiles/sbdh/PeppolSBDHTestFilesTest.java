package com.helger.peppol.testfiles.sbdh;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.xml.serialize.read.DOMReader;

/**
 * Test class for class {@link PeppolSBDHTestFiles}
 *
 * @author Philip Helger
 */
public final class PeppolSBDHTestFilesTest
{
  private static void _testGoodXML (@Nonnull final ClassPathResource aRes) throws SAXException
  {
    assertTrue (aRes.getPath (), aRes.exists ());

    // Read as generic XML to verify that it is readable
    final Document aDoc = DOMReader.readXMLDOM (aRes);
    assertNotNull (aRes.getPath (), aDoc);
  }

  @Test
  public void testExists () throws SAXException
  {
    for (final ClassPathResource aRes : PeppolSBDHTestFiles.getAllGoodCases ())
      _testGoodXML (aRes);
    for (final ClassPathResource aRes : PeppolSBDHTestFiles.getAllBadCases ())
      assertTrue (aRes.getPath (), aRes.exists ());
  }
}
