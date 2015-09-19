package com.helger.peppol.testfiles.official;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.xml.serialize.read.DOMReader;

/**
 * Test class for class {@link OfficialTestFiles}
 *
 * @author Philip Helger
 */
public final class OfficialTestFilesTest
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
    for (final ClassPathResource aRes : OfficialTestFiles.getAllTestFilesCatalogue_01_T19 ())
      _testGoodXML (aRes);
    for (final ClassPathResource aRes : OfficialTestFiles.getAllTestFilesCatalogue_01_T58 ())
      _testGoodXML (aRes);
    for (final ClassPathResource aRes : OfficialTestFiles.getAllTestFilesOrder_03_T01 ())
      _testGoodXML (aRes);
    for (final ClassPathResource aRes : OfficialTestFiles.getAllTestFilesInvoice_04_T10 ())
      _testGoodXML (aRes);
    for (final ClassPathResource aRes : OfficialTestFiles.getAllTestFilesBilling_05_T14 ())
      _testGoodXML (aRes);
    for (final ClassPathResource aRes : OfficialTestFiles.getAllTestFilesOrdering_28_T01 ())
      _testGoodXML (aRes);
    for (final ClassPathResource aRes : OfficialTestFiles.getAllTestFilesOrdering_28_T76 ())
      _testGoodXML (aRes);
    for (final ClassPathResource aRes : OfficialTestFiles.getAllTestFilesDespatchAdvice_30_T16 ())
      _testGoodXML (aRes);
  }
}
