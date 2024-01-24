/*
 * Copyright (C) 2023-2024 Philip Helger
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
package com.helger.peppol.mlr;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.io.file.FileSystemRecursiveIterator;
import com.helger.commons.io.file.IFileFilter;

import oasis.names.specification.ubl.schema.xsd.applicationresponse_21.ApplicationResponseType;

/**
 * Test class for class {@link PeppolMLRMarshaller}.
 *
 * @author Philip Helger
 */
public final class PeppolMLRMarshallerTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolMLRMarshallerTest.class);

  @Test
  public void testRead ()
  {
    for (final File f : new FileSystemRecursiveIterator (new File ("src/test/resources/external/test-files")).withFilter (IFileFilter.filenameEndsWith (".xml")))
    {
      LOGGER.info ("Reading '" + f.getName () + "'");
      final ApplicationResponseType aMLR = new PeppolMLRMarshaller ().read (f);
      assertNotNull (aMLR);
    }
  }
}
