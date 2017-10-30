/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Version: MPL 2.0/EUPL 1.2
 * -
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 * -
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.2 or - as soon they will be approved
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
 * -
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
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
