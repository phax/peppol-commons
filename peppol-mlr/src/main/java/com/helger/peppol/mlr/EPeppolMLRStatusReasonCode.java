/*
 * Copyright (C) 2023 Philip Helger
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;

/**
 * MLR Line Response Status Reason Code. See
 * https://docs.peppol.eu/poacc/upgrade-3/codelist/StatusReason/
 *
 * @author Philip Helger
 */
public enum EPeppolMLRStatusReasonCode implements IHasID <String>
{
  BUSINESS_RULE_VIOLATION_FATAL ("BV"),
  BUSINESS_RULE_VIOLATION_WARNING ("BW"),
  SYNTAX_VIOLATION ("SV");

  private final String m_sID;

  EPeppolMLRStatusReasonCode (@Nonnull @Nonempty final String sID)
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
    return this == BUSINESS_RULE_VIOLATION_FATAL || this == SYNTAX_VIOLATION;
  }

  @Nullable
  public static EPeppolMLRStatusReasonCode getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EPeppolMLRStatusReasonCode.class, sID);
  }
}
