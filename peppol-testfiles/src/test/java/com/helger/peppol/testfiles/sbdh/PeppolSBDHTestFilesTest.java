/**
 * Copyright (C) 2015-2020 Philip Helger
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
package com.helger.peppol.testfiles.sbdh;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.w3c.dom.Document;

import com.helger.commons.io.resource.ClassPathResource;
import com.helger.xml.serialize.read.DOMReader;

/**
 * Test class for class {@link PeppolSBDHTestFiles}
 *
 * @author Philip Helger
 */
public final class PeppolSBDHTestFilesTest
{
  private static void _testGoodXML (@Nonnull final ClassPathResource aRes)
  {
    assertTrue (aRes.getPath (), aRes.exists ());

    // Read as generic XML to verify that it is readable
    final Document aDoc = DOMReader.readXMLDOM (aRes);
    assertNotNull (aRes.getPath (), aDoc);
  }

  @Test
  public void testExists ()
  {
    for (final ClassPathResource aRes : PeppolSBDHTestFiles.getAllGoodCases ())
      _testGoodXML (aRes);
    for (final ClassPathResource aRes : PeppolSBDHTestFiles.getAllBadCases ())
      assertTrue (aRes.getPath (), aRes.exists ());
  }
}
