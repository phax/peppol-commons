package com.helger.peppol.smlclient.bdmsl;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;
import com.helger.peppol.identifier.IParticipantIdentifier;
import com.helger.peppol.identifier.participant.IPeppolParticipantIdentifier;
import com.helger.peppol.identifier.participant.SimpleParticipantIdentifier;

/**
 * A single item of the participant list.
 *
 * @author Philip Helger
 */
@Immutable
public class ParticipantListItem implements Serializable
{
  private final String m_sSMPID;
  private final SimpleParticipantIdentifier m_aParticipantID;

  public ParticipantListItem (@Nonnull @Nonempty final String sSMPID, @Nonnull final IParticipantIdentifier aParticipantID)
  {
    m_sSMPID = ValueEnforcer.notEmpty (sSMPID, "SMPID");
    // Same type, guaranteed to not be changed inside
    m_aParticipantID = new SimpleParticipantIdentifier (aParticipantID);
  }

  @Nonnull
  @Nonempty
  public String getSMPID ()
  {
    return m_sSMPID;
  }

  @Nonnull
  public IPeppolParticipantIdentifier getParticipantID ()
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
    return new ToStringGenerator (null).append ("SMPID", m_sSMPID).append ("ParticipantID", m_aParticipantID).toString ();
  }
}
