/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.smlclient.bdmsl;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;

/**
 * A single item of the participant list.
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
  public ParticipantListItem (@Nonnull @Nonempty final String sSMPID,
                              @Nonnull final IParticipantIdentifier aParticipantID)
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
  @Nonnull
  @Nonempty
  public String getSMPID ()
  {
    return m_sSMPID;
  }

  /**
   * @return The participant identifier as passed in the constructor. Never
   *         <code>null</code>.
   */
  @Nonnull
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
    return new ToStringGenerator (null).append ("SMPID", m_sSMPID)
                                       .append ("ParticipantID", m_aParticipantID)
                                       .toString ();
  }
}
