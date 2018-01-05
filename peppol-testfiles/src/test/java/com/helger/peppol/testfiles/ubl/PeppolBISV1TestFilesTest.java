/**
 * Copyright (C) 2015-2018 Philip Helger
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
import com.helger.peppol.testfiles.TestResource;
import com.helger.xml.serialize.read.DOMReader;

/**
 * Test class for class {@link PeppolBISV1TestFiles}
 *
 * @author Philip Helger
 */
public final class PeppolBISV1TestFilesTest
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
      for (final IReadableResource aRes : PeppolBISV1TestFiles.getSuccessFiles (e))
        _testGoodXML (aRes);
      for (final IReadableResource aRes : PeppolBISV1TestFiles.getSuccessFiles (e, AT))
        _testGoodXML (aRes);
      for (final TestResource aRes : PeppolBISV1TestFiles.getErrorFiles (e))
        assertTrue (aRes.getPath (), aRes.getResource ().exists ());
      for (final TestResource aRes : PeppolBISV1TestFiles.getErrorFiles (e, AT))
        assertTrue (aRes.getPath (), aRes.getResource ().exists ());
    }
  }
}
