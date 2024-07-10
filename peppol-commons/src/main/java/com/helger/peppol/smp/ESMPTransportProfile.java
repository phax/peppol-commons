/*
 * Copyright (C) 2015-2024 Philip Helger
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.lang.EnumHelper;
import com.helger.commons.type.ObjectType;

/**
 * This class contains predefined transport profiles for service registrations.
 * A generic implementation of {@link ISMPTransportProfile} can be found in
 * class {@link SMPTransportProfile}.
 *
 * @author Philip Helger
 */
public enum ESMPTransportProfile implements ISMPTransportProfile
{
  /**
   * The Peppol START transport profile
   */
  @Deprecated (forRemoval = false)
  TRANSPORT_PROFILE_START("busdox-transport-start", "START", ESMPTransportProfileState.DELETED),

  /**
   * The Peppol AS2 transport profile v1 (SHA-1).<br>
   * Updated with AS2 v2 on 2020-02-01<br>
   * Removed from Peppol per 2023-02-24
   */
  @Deprecated (forRemoval = false)
  TRANSPORT_PROFILE_AS2("busdox-transport-as2-ver1p0", "Peppol AS2 v1", ESMPTransportProfileState.DELETED),

  /**
   * The Peppol AS2 v2 transport profile v2 (SHA-256).<br>
   * Mandatory (when using AS2) in Peppol since 2020-02-01.<br>
   * Removed from Peppol per 2023-02-24
   */
  @Deprecated (forRemoval = false)
  TRANSPORT_PROFILE_AS2_V2("busdox-transport-as2-ver2p0", "Peppol AS2 v2", ESMPTransportProfileState.DELETED),

  /** The AS4 transport profile - too unspecific. Don't use */
  @Deprecated (forRemoval = false)
  TRANSPORT_PROFILE_AS4("busdox-transport-ebms3-as4", "AS4", ESMPTransportProfileState.DEPRECATED),

  /** The CEF AS4 transport profile */
  TRANSPORT_PROFILE_BDXR_AS4 ("bdxr-transport-ebms3-as4-v1p0", "CEF AS4", ESMPTransportProfileState.ACTIVE),

  /**
   * The Peppol AS4 profile v1.
   *
   * @deprecated for {@link #TRANSPORT_PROFILE_PEPPOL_AS4_V2}
   */
  @Deprecated (forRemoval = false)
  TRANSPORT_PROFILE_PEPPOL_AS4("peppol-transport-as4-v1_0", "Peppol AS4 v1", ESMPTransportProfileState.DELETED),

  /** The Peppol AS4 profile v2 */
  TRANSPORT_PROFILE_PEPPOL_AS4_V2 ("peppol-transport-as4-v2_0", "Peppol AS4 v2", ESMPTransportProfileState.ACTIVE),

  /**
   * DBNA AS4 profile v1
   *
   * @since 9.3.2
   */
  TRANSPORT_PROFILE_DBNA_AS4_v1 ("bdxr-as4-1.0#dbnalliance-1.0", "DBNA AS4 1.0", ESMPTransportProfileState.ACTIVE);

  private final String m_sID;
  private final String m_sName;
  private final ESMPTransportProfileState m_eState;

  ESMPTransportProfile (@Nonnull @Nonempty final String sID,
                        @Nonnull @Nonempty final String sName,
                        @Nonnull final ESMPTransportProfileState eState)
  {
    m_sID = sID;
    m_sName = sName;
    m_eState = eState;
  }

  @Nonnull
  public ObjectType getObjectType ()
  {
    return SMPTransportProfile.OT;
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
  public ESMPTransportProfileState getState ()
  {
    return m_eState;
  }

  /**
   * Find the pre-defined transport profile with the passed ID.
   *
   * @param sID
   *        The ID to be searched. May be <code>null</code>.
   * @return <code>null</code> if no such transport profile was found.
   */
  @Nullable
  public static ESMPTransportProfile getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (ESMPTransportProfile.class, sID);
  }
}
