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
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public enum ESMPTransportProfile implements ISMPTransportProfile
{
  /** The START transport profile to be used in EndPointType objects */
  @Deprecated
  TRANSPORT_PROFILE_START ("busdox-transport-start", "START")
  {
    public boolean isDeprecated ()
    {
      return true;
    }
  },

  /** The AS2 transport profile to be used in EndPointType objects */
  TRANSPORT_PROFILE_AS2 ("busdox-transport-as2-ver1p0", "AS2"),

  /** The AS4 transport profile to be used in EndPointType objects */
  @Deprecated
  TRANSPORT_PROFILE_AS4 ("busdox-transport-ebms3-as4", "AS4")
  {
    public boolean isDeprecated ()
    {
      return true;
    }
  },

  /** The BDXR AS4 transport profile to be used in EndPointType objects */
  TRANSPORT_PROFILE_BDXR_AS4 ("bdxr-transport-ebms3-as4-v1p0", "BDXR AS4"),

  /** Special PEPPOL AS4 profile */
  TRANSPORT_PROFILE_PEPPOL_AS4 ("peppol-transport-as4-v1_0", "PEPPOL AS4");

  private final String m_sID;
  private final String m_sName;

  private ESMPTransportProfile (@Nonnull @Nonempty final String sID, @Nonnull @Nonempty final String sName)
  {
    m_sID = sID;
    m_sName = sName;
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
