/*
 * Copyright (C) 2023-2026 Philip Helger
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

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.base.id.IHasID;
import com.helger.base.lang.EnumHelper;

/**
 * MLR Line Response Status Reason Code. See
 * https://docs.peppol.eu/poacc/upgrade-3/codelist/StatusReason/
 *
 * @author Philip Helger
 * @deprecated Peppol MLR is phased out of Peppol in favour of Peppol MLS. According to the "Peppol
 *             MLR Deprecation and Phase-out Plan" v1.0.0 the phase-out starts on 2027-03-01 (T2),
 *             the MLR specification is deprecated on 2027-04-01 (T3) and MLR is fully retired on
 *             2027-05-01 (T4). From T4 onwards no MLR may be sent anymore. Use
 *             <code>EPeppolMLSStatusReasonCode</code> from the <code>peppol-mls</code> module
 *             instead - contrary to the open ended MLR list it is the exhaustive list of permitted
 *             rejection reasons.
 */
@Deprecated (forRemoval = true, since = "13.0.1")
public enum EPeppolMLRStatusReasonCode implements IHasID <String>
{
  @Deprecated (forRemoval = true, since = "13.0.1")
  BUSINESS_RULE_VIOLATION_FATAL ("BV"),
  @Deprecated (forRemoval = true, since = "13.0.1")
  BUSINESS_RULE_VIOLATION_WARNING ("BW"),
  @Deprecated (forRemoval = true, since = "13.0.1")
  SYNTAX_VIOLATION ("SV");

  private final String m_sID;

  EPeppolMLRStatusReasonCode (@NonNull @Nonempty final String sID)
  {
    m_sID = sID;
  }

  @NonNull
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
