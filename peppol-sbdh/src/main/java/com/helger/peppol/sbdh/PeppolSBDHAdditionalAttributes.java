/*
 * Copyright (C) 2014-2025 Philip Helger
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

import com.helger.annotation.concurrent.Immutable;

import jakarta.annotation.Nullable;

/**
 * Contains an ordered set of custom variables to be provided in PEPPOL SBDH 1.1 documents.
 *
 * @author Philip Helger
 * @since 6.1.4
 */
@Immutable
public final class PeppolSBDHAdditionalAttributes
{
  public static final String COUNTRY_C1 = CPeppolSBDH.SCOPE_COUNTRY_C1;
  public static final String COUNTRY_C4 = "COUNTRY_C4";
  public static final String DOCUMENTID = CPeppolSBDH.SCOPE_DOCUMENT_TYPE_ID;
  public static final String PROCESSID = CPeppolSBDH.SCOPE_PROCESS_ID;
  public static final String MLS_TO = CPeppolSBDH.SCOPE_MLS_TO;
  public static final String MLS_TYPE = CPeppolSBDH.SCOPE_MLS_TYPE;
  public static final String TECHNICAL_VALIDATION_URL = "TECHNICAL_VALIDATION_URL";
  public static final String TECHNICAL_VALIDATION_REQUIRED = "TECHNICAL_VALIDATION_REQUIRED";

  private PeppolSBDHAdditionalAttributes ()
  {}

  /**
   * Check if the passed attribute name is reserved according to the specification or not. Attribute
   * names are case sensitive!
   *
   * @param sName
   *        Name of the attribute to check. May be <code>null</code>.
   * @return <code>true</code> if the name is not <code>null</code> and inside the list of reserved
   *         names.
   */
  public static boolean isReservedAttributeName (@Nullable final String sName)
  {
    if (sName == null)
      return false;

    // COUNTRY_C1 and COUNTRY_C4 were added in v2.0
    return sName.equals (COUNTRY_C1) ||
           sName.equals (COUNTRY_C4) ||
           sName.equals (DOCUMENTID) ||
           sName.equals (PROCESSID) ||
           sName.equals (MLS_TO) ||
           sName.equals (MLS_TYPE) ||
           sName.equals (TECHNICAL_VALIDATION_URL) ||
           sName.equals (TECHNICAL_VALIDATION_REQUIRED);
  }
}
