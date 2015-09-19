/**
 * Copyright (C) 2014-2015 Philip Helger
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
import com.helger.peppol.identifier.CIdentifier;

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

  /**
   * The default identifier scheme ID to be used for participants/businesses.<br>
   * The matching values have the format "agency:id" whereas agency should be
   * within the code-list.<br>
   * Please note that this is a change to the PEPPOL Common definitions chapter
   * 3.4!<br>
   * This is used for Sender and Receiver Authority checks.
   */
  @Deprecated
  public static final String DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME = CIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME;

  /**
   * The default document identifier scheme.<br>
   * See PEPPOL Common definitions chapter 3.5
   */
  @Deprecated
  public static final String DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME = CIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME;

  /**
   * The default process identifier scheme.<br>
   * Overrides PEPPOL Common definitions chapter 3.6!
   */
  @Deprecated
  public static final String DEFAULT_PROCESS_IDENTIFIER_SCHEME = CIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME;

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
