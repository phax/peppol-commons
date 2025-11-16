/*
 * Copyright (C) 2015-2025 Philip Helger
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
package com.helger.peppol.smlclient.bdmsl;

import java.io.Serializable;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.Immutable;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.hashcode.HashCodeGenerator;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.peppolid.IParticipantIdentifier;

/**
 * A single item of the participant list.
 * <p>
 * Note: this class is also licensed under Apache 2 license, as it was not part
 * of the original implementation
 * </p>
 *
 * @author Philip Helger
 */
@Immutable
public class ParticipantListItem implements Serializable
{
  private final String m_sSMPID;
  private final IParticipantIdentifier m_aParticipantID;

  /**
   * Constructor
   *
   * @param sSMPID
   *        The SMP ID to which the participant belongs. May neither be
   *        <code>null</code> nor empty.
   * @param aParticipantID
   *        The participant identifier. May not be <code>null</code>.
   */
  public ParticipantListItem (@NonNull @Nonempty final String sSMPID, @NonNull final IParticipantIdentifier aParticipantID)
  {
    ValueEnforcer.notEmpty (sSMPID, "SMPID");
    ValueEnforcer.notNull (aParticipantID, "ParticipantID");

    m_sSMPID = sSMPID;
    m_aParticipantID = aParticipantID;
  }

  /**
   * @return The SMP-ID to which this participant identifier belongs. Neither
   *         <code>null</code> nor empty.
   */
  @NonNull
  @Nonempty
  public String getSMPID ()
  {
    return m_sSMPID;
  }

  /**
   * @return The participant identifier as passed in the constructor. Never
   *         <code>null</code>.
   */
  @NonNull
  public IParticipantIdentifier getParticipantID ()
  {
    return m_aParticipantID;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final ParticipantListItem rhs = (ParticipantListItem) o;
    return m_sSMPID.equals (rhs.m_sSMPID) && m_aParticipantID.equals (rhs.m_aParticipantID);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sSMPID).append (m_aParticipantID).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("SMPID", m_sSMPID).append ("ParticipantID", m_aParticipantID).getToString ();
  }
}
