/*
 * Copyright (C) 2025 Philip Helger
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
package com.helger.peppol.mls;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.base.string.StringHelper;
import com.helger.collection.commons.CommonsTreeSet;
import com.helger.collection.commons.ICommonsList;
import com.helger.collection.commons.ICommonsSet;
import com.helger.io.file.FileSystemRecursiveIterator;
import com.helger.io.file.IFileFilter;
import com.helger.io.resource.ClassPathResource;
import com.helger.io.resource.FileSystemResource;
import com.helger.schematron.svrl.AbstractSVRLMessage;
import com.helger.schematron.svrl.SVRLHelper;
import com.helger.schematron.svrl.SVRLMarshaller;
import com.helger.schematron.svrl.jaxb.SchematronOutputType;
import com.helger.ubl21.UBL21Marshaller;
import com.helger.xml.microdom.IMicroDocument;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.MicroDocument;
import com.helger.xml.microdom.serialize.MicroReader;
import com.helger.xml.microdom.serialize.MicroWriter;
import com.helger.xml.namespace.MapBasedNamespaceContext;
import com.helger.xml.serialize.write.XMLWriterSettings;

import jakarta.annotation.Nonnull;

/**
 * Test class for class {@link PeppolMLSValidator}.
 *
 * @author Philip Helger
 */
public final class PeppolMLSValidatorTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolMLSValidatorTest.class);

  @Test
  public void testReadGood () throws Exception
  {
    for (final File f : new FileSystemRecursiveIterator (new File ("src/test/resources/external/test-files/good")).withFilter (IFileFilter.filenameEndsWith (".xml")))
    {
      LOGGER.info ("Reading '" + f.getName () + "'");

      final SchematronOutputType aSVRL = PeppolMLSValidator.getSchematronMLS_101 ()
                                                           .applySchematronValidationToSVRL (new FileSystemResource (f));
      assertNotNull (aSVRL);
      final ICommonsList <AbstractSVRLMessage> aAllFailed = SVRLHelper.getAllFailedAssertionsAndSuccessfulReports (aSVRL);
      assertTrue (aAllFailed.toString (), aAllFailed.isEmpty ());
    }
  }

  @Nonnull
  private static ICommonsSet <String> _getAllFailedIDs (@Nonnull final String sFilename) throws Exception
  {
    final ClassPathResource f = new ClassPathResource (sFilename, PeppolMLSValidatorTest.class.getClassLoader ());
    assertNotNull ("The file '" + f.getPath () + "' is not XSD compliant", new PeppolMLSMarshaller ().read (f));

    final SchematronOutputType aSVRL = PeppolMLSValidator.getSchematronMLS_101 ().applySchematronValidationToSVRL (f);
    assertNotNull (aSVRL);

    if (false)
      LOGGER.info (new SVRLMarshaller ().getAsString (aSVRL));

    final ICommonsList <AbstractSVRLMessage> aList = SVRLHelper.getAllFailedAssertionsAndSuccessfulReports (aSVRL);
    assertFalse ("Found no errors in " + sFilename, aList.isEmpty ());

    final ICommonsSet <String> ret = new CommonsTreeSet <> (aList, AbstractSVRLMessage::getID);
    if (false)
      LOGGER.info ("Failures found: " + ret);
    return ret;
  }

  // Enable this flag, to create the unit test cases for PDK
  private static final boolean CREATE_PDK_FILES = false;
  private static final String PDK_NS = "urn:fdc:schunit.com:2020:v1";

  private static void _checkFailedIDs (final int nErrorCode, final int nCount) throws Exception
  {
    final String sErrorCode = StringHelper.getLeadingZero (nErrorCode, 2);
    final String sFullErrorCode = "SCH-MLS-" + sErrorCode;

    final IMicroDocument aTestsDoc = new MicroDocument ();
    final IMicroElement eTestsRoot = aTestsDoc.addElementNS (PDK_NS, "Tests");
    eTestsRoot.addComment (" This file is generated - do not edit. ");
    eTestsRoot.addText ("\n  ");
    eTestsRoot.addElementNS (PDK_NS, "Description").addText ("Negative MLS tests");
    eTestsRoot.addElementNS (PDK_NS, "Scope").addText (sFullErrorCode);

    for (int i = 0; i < nCount; ++i)
    {
      final String sFilename = "external/test-files/bad/mls-" + sErrorCode + ((char) ('a' + i)) + ".xml";
      final ICommonsSet <String> aFailed = _getAllFailedIDs (sFilename);
      final boolean bRet = aFailed.contains (sFullErrorCode);
      assertTrue ("[" + sFilename + "] Expected " + sFullErrorCode + " but got " + aFailed, bRet);

      if (CREATE_PDK_FILES)
      {
        final IMicroDocument aDoc = MicroReader.readMicroXML (new ClassPathResource (sFilename,
                                                                                     PeppolMLSValidator.class.getClassLoader ()));
        final IMicroElement eTest = eTestsRoot.addElementNS (PDK_NS, "Test");
        eTest.addElementNS (PDK_NS, "Trigger").addText (sFullErrorCode);
        eTest.addChild (aDoc.getDocumentElement ().detachFromParent ());
      }
    }

    if (CREATE_PDK_FILES)
    {
      // This one already sets the default namespace prefix
      final MapBasedNamespaceContext aNSCtx = ((MapBasedNamespaceContext) UBL21Marshaller.applicationResponse ()
                                                                                         .getNamespaceContext ()).getClone ();
      aNSCtx.addMapping ("pdk", PDK_NS);
      MicroWriter.writeToFile (aTestsDoc,
                               new File ("generated/pdk-mls-bad-" + sErrorCode + ".xml"),
                               new XMLWriterSettings ().setNamespaceContext (aNSCtx)
                                                       .setPutNamespaceContextPrefixesInRoot (true));
    }
  }

  @Test
  public void testReadBad () throws Exception
  {
    int nErrorCode = 0;
    // 1
    _checkFailedIDs (++nErrorCode, 2);
    _checkFailedIDs (++nErrorCode, 2);
    _checkFailedIDs (++nErrorCode, 2);
    _checkFailedIDs (++nErrorCode, 3);
    _checkFailedIDs (++nErrorCode, 1);
    _checkFailedIDs (++nErrorCode, 1);
    _checkFailedIDs (++nErrorCode, 1);
    _checkFailedIDs (++nErrorCode, 2);
    _checkFailedIDs (++nErrorCode, 3);
    _checkFailedIDs (++nErrorCode, 1);
    // 11
    _checkFailedIDs (++nErrorCode, 3);
    _checkFailedIDs (++nErrorCode, 1);
    _checkFailedIDs (++nErrorCode, 2);
    _checkFailedIDs (++nErrorCode, 3);
    _checkFailedIDs (++nErrorCode, 1);
    _checkFailedIDs (++nErrorCode, 3);
    _checkFailedIDs (++nErrorCode, 2);
    _checkFailedIDs (++nErrorCode, 1);
    _checkFailedIDs (++nErrorCode, 4);
    _checkFailedIDs (++nErrorCode, 1);
    // 21
    _checkFailedIDs (++nErrorCode, 2);
    _checkFailedIDs (++nErrorCode, 1);
    _checkFailedIDs (++nErrorCode, 1);
    _checkFailedIDs (++nErrorCode, 2);
    _checkFailedIDs (++nErrorCode, 2);
    _checkFailedIDs (++nErrorCode, 2);
    _checkFailedIDs (++nErrorCode, 4);
    _checkFailedIDs (++nErrorCode, 2);
    _checkFailedIDs (++nErrorCode, 4);
    _checkFailedIDs (++nErrorCode, 1);
    // 31
    _checkFailedIDs (++nErrorCode, 4);
  }
}
