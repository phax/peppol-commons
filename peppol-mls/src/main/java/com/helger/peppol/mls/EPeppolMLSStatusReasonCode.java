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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;

/**
 * MLS Line Response Status Reason Code.
 *
 * @author Philip Helger
 */
public enum EPeppolMLSStatusReasonCode implements IHasID <String>
{
  /**
   * Business rule violation, fatal
   */
  BUSINESS_RULE_VIOLATION_FATAL ("BV"),
  /**
   * Business rule violation, warning
   */
  BUSINESS_RULE_VIOLATION_WARNING ("BW"),
  /**
   * Failure of delivery
   */
  FAILURE_OF_DELIVERY ("FD"),
  /**
   * Syntax violation
   */
  SYNTAX_VIOLATION ("SV");

  private final String m_sID;

  EPeppolMLSStatusReasonCode (@Nonnull @Nonempty final String sID)
  {
    m_sID = sID;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  public boolean isFatal ()
  {
    return this == BUSINESS_RULE_VIOLATION_FATAL || this == FAILURE_OF_DELIVERY || this == SYNTAX_VIOLATION;
  }

  @Nullable
  public static EPeppolMLSStatusReasonCode getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EPeppolMLSStatusReasonCode.class, sID);
  }

  @Nonnull
  public static EPeppolMLSStatusReasonCode getFromIDOrThrow (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrThrow (EPeppolMLSStatusReasonCode.class, sID);
  }
}
