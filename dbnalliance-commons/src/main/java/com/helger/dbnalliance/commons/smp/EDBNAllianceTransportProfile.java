/*
 * Copyright (C) 2025-2026 Philip Helger
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
package com.helger.dbnalliance.commons.smp;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.base.lang.EnumHelper;
import com.helger.base.type.ObjectType;
import com.helger.edelivery.smp.ESMPTransportProfileState;
import com.helger.edelivery.smp.ISMPTransportProfile;
import com.helger.edelivery.smp.SMPTransportProfile;

/**
 * This class contains the predefined transport profiles of DBNAlliance.
 *
 * @author Philip Helger
 * @since 13.0.0
 */
public enum EDBNAllianceTransportProfile implements ISMPTransportProfile
{
  /** DBNAlliance AS4 profile v1 */
  AS4_V1 ("bdxr-as4-1.0#dbnalliance-1.0", "DBNAlliance AS4 1.0", ESMPTransportProfileState.ACTIVE);

  private final String m_sID;
  private final String m_sName;
  private final ESMPTransportProfileState m_eState;

  EDBNAllianceTransportProfile (@NonNull @Nonempty final String sID,
                                @NonNull @Nonempty final String sName,
                                @NonNull final ESMPTransportProfileState eState)
  {
    m_sID = sID;
    m_sName = sName;
    m_eState = eState;
  }

  @NonNull
  public ObjectType getObjectType ()
  {
    return SMPTransportProfile.OT;
  }

  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @NonNull
  @Nonempty
  public String getName ()
  {
    return m_sName;
  }

  @NonNull
  public ESMPTransportProfileState getState ()
  {
    return m_eState;
  }

  @Nullable
  public static EDBNAllianceTransportProfile getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EDBNAllianceTransportProfile.class, sID);
  }
}
