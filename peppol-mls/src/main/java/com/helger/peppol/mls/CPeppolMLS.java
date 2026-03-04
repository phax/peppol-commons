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

import com.helger.annotation.RegEx;
import com.helger.annotation.concurrent.Immutable;
import com.helger.peppolid.peppol.doctype.EPredefinedDocumentTypeIdentifier;
import com.helger.peppolid.peppol.process.EPredefinedProcessIdentifier;
import com.helger.peppolid.peppol.spis.SPIDHelper;

/**
 * Constants for Peppol MLS (Message Level Status)
 *
 * @author Philip Helger
 */
@Immutable
public final class CPeppolMLS
{
  public static final EPredefinedDocumentTypeIdentifier MLS_DOCUMENT_TYPE_ID = EPredefinedDocumentTypeIdentifier.PEPPOL_MLS_1_0;
  public static final String MLS_CUSTOMIZATION_ID = MLS_DOCUMENT_TYPE_ID.getCustomizationID ();

  public static final EPredefinedProcessIdentifier MLS_PROCESS_ID = EPredefinedProcessIdentifier.urn_peppol_edec_mls;
  public static final String MLS_PROFILE_ID = MLS_PROCESS_ID.getValue ();

  public static final String LINE_ID_NOT_AVAILABLE = "NA";

  /** The official Participant Identifier Scheme for SPIS (0242) */
  @Deprecated (forRemoval = true, since = "12.3.12")
  public static final String SPIS_PARTICIPANT_ID_SCHEME = SPIDHelper.SPIS_PARTICIPANT_ID_SCHEME;

  /**
   * The official validation RegEx from the specification, section 3.4. Don't forget to apply this
   * regular expression case-insensitive!
   */
  @RegEx
  @Deprecated (forRemoval = true, since = "12.3.12")
  public static final String REGEX_SPID = SPIDHelper.REGEX_COMPLETE;

  private CPeppolMLS ()
  {}
}
