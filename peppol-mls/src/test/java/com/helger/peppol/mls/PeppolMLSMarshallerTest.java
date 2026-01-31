/*
 * Copyright (C) 2025-2026 Philip Helger
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

import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.io.file.FileSystemRecursiveIterator;
import com.helger.io.file.IFileFilter;

import oasis.names.specification.ubl.schema.xsd.applicationresponse_21.ApplicationResponseType;

/**
 * Test class for class {@link PeppolMLSMarshaller}.
 *
 * @author Philip Helger
 */
public final class PeppolMLSMarshallerTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolMLSMarshallerTest.class);

  @Test
  public void testReadGood ()
  {
    for (final File f : new FileSystemRecursiveIterator (new File ("src/test/resources/external/test-files/good")).withFilter (IFileFilter.filenameEndsWith (".xml")))
    {
      LOGGER.info ("Reading '" + f.getName () + "'");
      final ApplicationResponseType aMLS = new PeppolMLSMarshaller ().read (f);
      assertNotNull (aMLS);
    }
  }
}
