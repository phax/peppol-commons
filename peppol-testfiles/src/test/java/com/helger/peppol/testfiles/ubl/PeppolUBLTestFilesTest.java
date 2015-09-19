package com.helger.peppol.testfiles.ubl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.helger.commons.io.resource.IReadableResource;
import com.helger.commons.locale.LocaleCache;
import com.helger.commons.xml.serialize.read.DOMReader;
import com.helger.peppol.testfiles.TestResource;

/**
 * Test class for class {@link PeppolUBLTestFiles}
 *
 * @author Philip Helger
 */
public final class PeppolUBLTestFilesTest
{
  private static void _testGoodXML (@Nonnull final IReadableResource aRes) throws SAXException
  {
    assertTrue (aRes.getPath (), aRes.exists ());

    // Read as generic XML to verify that it is readable
    final Document aDoc = DOMReader.readXMLDOM (aRes);
    assertNotNull (aRes.getPath (), aDoc);
  }

  @Test
  public void testExists () throws SAXException
  {
    final Locale AT = LocaleCache.getInstance ().getLocale ("de", "AT");
    for (final EPeppolUBLTestFileType e : EPeppolUBLTestFileType.values ())
    {
      for (final IReadableResource aRes : PeppolUBLTestFiles.getSuccessFiles (e))
        _testGoodXML (aRes);
      for (final IReadableResource aRes : PeppolUBLTestFiles.getSuccessFiles (e, AT))
        _testGoodXML (aRes);
      for (final TestResource aRes : PeppolUBLTestFiles.getErrorFiles (e))
        assertTrue (aRes.getPath (), aRes.getResource ().exists ());
      for (final TestResource aRes : PeppolUBLTestFiles.getErrorFiles (e, AT))
        assertTrue (aRes.getPath (), aRes.getResource ().exists ());
    }
  }
}
