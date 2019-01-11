/**
 * Copyright (C) 2014-2019 Philip Helger
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

import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.PresentForCodeCoverage;

/**
 * Constants for the usage of SBDH headers in PEPPOL.
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

  @PresentForCodeCoverage
  private static final CPeppolSBDH s_aInstance = new CPeppolSBDH ();

  private CPeppolSBDH ()
  {}
}
