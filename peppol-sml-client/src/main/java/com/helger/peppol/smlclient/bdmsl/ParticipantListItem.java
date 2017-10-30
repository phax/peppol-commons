/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
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
                                       .getToString ();
  }
}
