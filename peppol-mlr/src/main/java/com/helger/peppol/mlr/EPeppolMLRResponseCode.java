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
import com.helger.base.state.ISuccessIndicator;

/**
 * Code list for the top-level MLR response codes.
 *
 * @author Philip Helger
 * @deprecated Peppol MLR is phased out of Peppol in favour of Peppol MLS. According to the "Peppol
 *             MLR Deprecation and Phase-out Plan" v1.0.0 the phase-out starts on 2027-03-01 (T2),
 *             the MLR specification is deprecated on 2027-04-01 (T3) and MLR is fully retired on
 *             2027-05-01 (T4). From T4 onwards no MLR may be sent anymore. Use
 *             <code>EPeppolMLSResponseCode</code> from the <code>peppol-mls</code> module instead -
 *             it uses the same response codes and adds the ones MLR does not cover.
 */
@Deprecated (forRemoval = true, since = "13.1.0")
public enum EPeppolMLRResponseCode implements IHasID <String>, ISuccessIndicator
{
  @Deprecated (forRemoval = true, since = "13.1.0")
  ACCEPTANCE("AP"),
  @Deprecated (forRemoval = true, since = "13.1.0")
  ACKNOWLEDGING("AB"),
  @Deprecated (forRemoval = true, since = "13.1.0")
  REJECTION("RE");

  private final String m_sID;

  EPeppolMLRResponseCode (@NonNull @Nonempty final String sID)
  {
    m_sID = sID;
  }

  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  public boolean isSuccess ()
  {
    return this == ACCEPTANCE || this == ACKNOWLEDGING;
  }

  public boolean isFailure ()
  {
    return this == REJECTION;
  }

  @Nullable
  public static EPeppolMLRResponseCode getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EPeppolMLRResponseCode.class, sID);
  }
}
