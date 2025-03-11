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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.io.file.FileSystemRecursiveIterator;
import com.helger.commons.io.file.IFileFilter;
import com.helger.commons.io.resource.FileSystemResource;
import com.helger.schematron.svrl.AbstractSVRLMessage;
import com.helger.schematron.svrl.SVRLHelper;
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
    for (final File f : new FileSystemRecursiveIterator (new File ("src/test/resources/external/test-files")).withFilter (IFileFilter.filenameEndsWith (".xml")))
    {
      LOGGER.info ("Reading '" + f.getName () + "'");

      final SchematronOutputType aSVRL = PeppolMLSValidator.getSchematronMLS_100 ()
                                                           .applySchematronValidationToSVRL (new FileSystemResource (f));
      assertNotNull (aSVRL);
      final ICommonsList <AbstractSVRLMessage> aAllFailed = SVRLHelper.getAllFailedAssertionsAndSuccessfulReports (aSVRL);
      assertTrue (aAllFailed.toString (), aAllFailed.isEmpty ());
    }
  }
}
