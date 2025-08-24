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
package com.helger.peppol.smp;

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.base.clone.ICloneable;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.hashcode.HashCodeGenerator;
import com.helger.base.state.EChange;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.base.type.ObjectType;

import jakarta.annotation.Nonnull;

/**
 * A stand-alone implementation of the {@link ISMPTransportProfile}. For a set of predefined
 * transport profiles have a look at {@link ESMPTransportProfile}.
 */
@NotThreadSafe
public class SMPTransportProfile implements ISMPTransportProfile, ICloneable <SMPTransportProfile>
{
  public static final ObjectType OT = new ObjectType ("smp.transport.profile");
  public static final ESMPTransportProfileState DEFAULT_STATE = ESMPTransportProfileState.ACTIVE;

  private final String m_sID;
  private String m_sName;
  private ESMPTransportProfileState m_eState;

  @Nonnull
  static ESMPTransportProfileState internalGetDeprecatedState (final boolean bIsDeprecated)
  {
    return bIsDeprecated ? ESMPTransportProfileState.DEPRECATED : ESMPTransportProfileState.ACTIVE;
  }

  public SMPTransportProfile (@Nonnull final ISMPTransportProfile aOther)
  {
    this (aOther.getID (), aOther.getName (), aOther.getState ());
  }

  public SMPTransportProfile (@Nonnull @Nonempty final String sID, @Nonnull @Nonempty final String sName)
  {
    this (sID, sName, DEFAULT_STATE);
  }

  public SMPTransportProfile (@Nonnull @Nonempty final String sID,
                              @Nonnull @Nonempty final String sName,
                              @Nonnull final ESMPTransportProfileState eState)
  {
    m_sID = ValueEnforcer.notEmpty (sID, "ID");
    setName (sName);
    setState (eState);
  }

  @Nonnull
  public ObjectType getObjectType ()
  {
    return OT;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nonnull
  @Nonempty
  public String getName ()
  {
    return m_sName;
  }

  @Nonnull
  public final EChange setName (@Nonnull @Nonempty final String sName)
  {
    ValueEnforcer.notEmpty (sName, "Name");
    if (sName.equals (m_sName))
      return EChange.UNCHANGED;
    m_sName = sName;
    return EChange.CHANGED;
  }

  @Nonnull
  public ESMPTransportProfileState getState ()
  {
    return m_eState;
  }

  @Nonnull
  public final EChange setState (@Nonnull final ESMPTransportProfileState eState)
  {
    ValueEnforcer.notNull (eState, "State");
    if (eState.equals (m_eState))
      return EChange.UNCHANGED;
    m_eState = eState;
    return EChange.CHANGED;
  }

  @Nonnull
  public SMPTransportProfile getClone ()
  {
    return new SMPTransportProfile (this);
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final SMPTransportProfile rhs = (SMPTransportProfile) o;
    return m_sID.equals (rhs.m_sID);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sID).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("ID", m_sID)
                                       .append ("Name", m_sName)
                                       .append ("State", m_eState)
                                       .getToString ();
  }
}
