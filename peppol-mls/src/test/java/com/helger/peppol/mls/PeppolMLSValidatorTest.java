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

import javax.annotation.Nonnull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.collection.impl.CommonsTreeSet;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.commons.io.file.FileSystemRecursiveIterator;
import com.helger.commons.io.file.IFileFilter;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.io.resource.FileSystemResource;
import com.helger.commons.string.StringHelper;
import com.helger.schematron.svrl.AbstractSVRLMessage;
import com.helger.schematron.svrl.SVRLHelper;
import com.helger.schematron.svrl.SVRLMarshaller;
import com.helger.schematron.svrl.jaxb.SchematronOutputType;

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

      final SchematronOutputType aSVRL = PeppolMLSValidator.getSchematronMLS_100 ()
                                                           .applySchematronValidationToSVRL (new FileSystemResource (f));
      assertNotNull (aSVRL);
      final ICommonsList <AbstractSVRLMessage> aAllFailed = SVRLHelper.getAllFailedAssertionsAndSuccessfulReports (aSVRL);
      assertTrue (aAllFailed.toString (), aAllFailed.isEmpty ());
    }
  }

  @Nonnull
  private static ICommonsSet <String> _getAllFailedIDs (@Nonnull final String sFilename) throws Exception
  {
    final ClassPathResource f = new ClassPathResource ("external/test-files/bad/" + sFilename,
                                                       PeppolMLSValidatorTest.class.getClassLoader ());
    assertNotNull ("The file '" + f.getPath () + "' is not XSD compliant", new PeppolMLSMarshaller ().read (f));

    final SchematronOutputType aSVRL = PeppolMLSValidator.getSchematronMLS_100 ().applySchematronValidationToSVRL (f);
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

  private static void _checkFailedID (@Nonnull final String sFilename, final String sExpected) throws Exception
  {
    final ICommonsSet <String> aFailed = _getAllFailedIDs (sFilename);
    final boolean bRet = aFailed.contains (sExpected);
    assertTrue ("[" + sFilename + "] Expected " + sExpected + " but got " + aFailed, bRet);
  }

  private static void _checkFailedIDs (final int nErrorCode, final int nCount) throws Exception
  {
    final String sErrorCode = StringHelper.getLeadingZero (nErrorCode, 2);
    for (int i = 0; i < nCount; ++i)
    {
      final String sFilename = "mls-" + sErrorCode + ((char) ('a' + i)) + ".xml";
      _checkFailedID (sFilename, "SCH-MLS-" + sErrorCode);
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
