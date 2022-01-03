/*
 * Copyright (C) 2015-2022 Philip Helger
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

import javax.annotation.Nonnull;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.io.resource.IReadableResource;

/**
 * UBL test file type.
 *
 * @author Philip Helger
 */
public enum EPeppolUBLTestFileType
{
  CALLFORTENDERS ("test-callfortenders"),
  CATALOGUE ("test-catalogues"),
  CREDITNOTE ("test-creditnotes"),
  INVOICE ("test-invoices"),
  ORDER ("test-orders"),
  ORDERRESPONSE ("test-orderresponses"),
  TENDER ("test-tenders"),
  TENDERINGCATALOGUE ("test-tenderingcatalogues");

  private final String m_sDirName;

  EPeppolUBLTestFileType (@Nonnull @Nonempty final String sDirName)
  {
    m_sDirName = sDirName;
  }

  @Nonnull
  public IReadableResource getSuccessResource (@Nonnull @Nonempty final String sFilename)
  {
    return new ClassPathResource ("/peppol-ubl/" + m_sDirName + "/success/" + sFilename);
  }

  @Nonnull
  public IReadableResource getErrorResource (@Nonnull @Nonempty final String sFilename)
  {
    return new ClassPathResource ("/peppol-ubl/" + m_sDirName + "/error/" + sFilename);
  }
}
