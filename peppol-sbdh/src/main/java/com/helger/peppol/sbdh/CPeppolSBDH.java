/*
 * Copyright (C) 2014-2023 Philip Helger
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
package com.helger.peppol.sbdh;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.io.resource.ClassPathResource;

/**
 * Constants for the usage of SBDH headers in Peppol.
 *
 * @author Philip Helger
 */
@Immutable
public final class CPeppolSBDH
{
  /** The expected SBDH header version */
  public static final String HEADER_VERSION = "1.0";

  /** Constant for the Scope of the document type identifier */
  public static final String SCOPE_DOCUMENT_TYPE_ID = "DOCUMENTID";

  /** Constant for the Scope of the process identifier */
  public static final String SCOPE_PROCESS_ID = "PROCESSID";

  /** UBL 2.0 constant */
  public static final String TYPE_VERSION_20 = "2.0";

  /** UBL 2.1 constant */
  public static final String TYPE_VERSION_21 = "2.1";

  /** UBL 2.2 constant */
  public static final String TYPE_VERSION_22 = "2.2";

  /** UBL 2.3 constant */
  public static final String TYPE_VERSION_23 = "2.3";

  /** CII D16B constant */
  public static final String TYPE_VERSION_CII_D16B = "D16B";

  @Nonnull
  private static ClassLoader _getCL ()
  {
    return CPeppolSBDH.class.getClassLoader ();
  }

  /** XML Schema resources for special payloads */
  @CodingStyleguideUnaware
  public static final List <ClassPathResource> PEPPOL_SPECIAL_PAYLOADS_XSDS = new CommonsArrayList <> (new ClassPathResource ("/schemas/external/PEPPOL-EDN-Business-Message-Envelope-1.2-2019-02-01.xsd",
                                                                                                                              _getCL ())).getAsUnmodifiable ();

  @PresentForCodeCoverage
  private static final CPeppolSBDH INSTANCE = new CPeppolSBDH ();

  private CPeppolSBDH ()
  {}
}
