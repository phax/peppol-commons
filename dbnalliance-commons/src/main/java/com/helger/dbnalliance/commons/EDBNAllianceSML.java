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
package com.helger.dbnalliance.commons;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.Nonempty;
import com.helger.base.type.ObjectType;
import com.helger.edelivery.sml.ISMLBase;

/**
 * The list of supported DBNAlliance SML zones
 *
 * @author Philip Helger
 */
public enum EDBNAllianceSML implements ISMLBase
{
  PILOT ("pilot", "DBNAlliance Pilot SML", "sml.dbnalliancepilot.net."),
  TEST ("test", "DBNAlliance Test SML", "sml.dbnalliance.com."),
  PRODUCTION ("prod", "DBNAlliance Production SML", "sml.dbnalliance.net.");

  public static final ObjectType OT = new ObjectType ("dbnalliance.sml");

  private final String m_sID;
  private final String m_sDisplayName;
  private final String m_sZoneName;

  EDBNAllianceSML (@NonNull @Nonempty final String sID,
                   @NonNull @Nonempty final String sDisplayName,
                   @NonNull @Nonempty final String sZoneName)
  {
    m_sID = sID;
    m_sDisplayName = sDisplayName;
    m_sZoneName = sZoneName;
  }

  @NonNull
  public ObjectType getObjectType ()
  {
    return OT;
  }

  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @NonNull
  @Nonempty
  public String getDisplayName ()
  {
    return m_sDisplayName;
  }

  @NonNull
  @Nonempty
  public String getDNSZone ()
  {
    return m_sZoneName;
  }

  /**
   * @return The DNS zone name. Same as {@link #getDNSZone()}.
   */
  @NonNull
  @Nonempty
  public String getZoneName ()
  {
    return m_sZoneName;
  }

  public boolean isClientCertificateRequired ()
  {
    // The DBNAlliance SML is read only - no client certificate needed
    return false;
  }
}
