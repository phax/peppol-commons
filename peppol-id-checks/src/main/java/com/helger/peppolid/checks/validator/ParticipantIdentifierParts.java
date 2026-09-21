/*
 * Copyright (C) 2015-2026 Philip Helger
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
package com.helger.peppolid.checks.validator;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.Immutable;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.tostring.ToStringGenerator;

/**
 * The parts of a participant identifier value that the validation works on - the ID of the issuing
 * agency and the local participant ID inside that agency. How a participant identifier is split
 * into these two parts differs between the networks; see {@link IParticipantIdentifierPartsProvider}.
 *
 * @author Philip Helger
 * @since 13.0.0
 */
@Immutable
public class ParticipantIdentifierParts
{
  private final String m_sIssuingAgencyID;
  private final String m_sLocalParticipantID;

  public ParticipantIdentifierParts (@NonNull @Nonempty final String sIssuingAgencyID,
                                     @NonNull @Nonempty final String sLocalParticipantID)
  {
    m_sIssuingAgencyID = ValueEnforcer.notEmpty (sIssuingAgencyID, "IssuingAgencyID");
    m_sLocalParticipantID = ValueEnforcer.notEmpty (sLocalParticipantID, "LocalParticipantID");
  }

  /**
   * @return The ID of the issuing agency. Neither <code>null</code> nor empty.
   */
  @NonNull
  @Nonempty
  public final String getIssuingAgencyID ()
  {
    return m_sIssuingAgencyID;
  }

  /**
   * @return The local participant ID inside the issuing agency. Neither <code>null</code> nor
   *         empty.
   */
  @NonNull
  @Nonempty
  public final String getLocalParticipantID ()
  {
    return m_sLocalParticipantID;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("IssuingAgencyID", m_sIssuingAgencyID)
                                       .append ("LocalParticipantID", m_sLocalParticipantID)
                                       .getToString ();
  }
}
