/*
 * Copyright (C) 2015-2026 Philip Helger
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

import java.util.Locale;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.base.id.IHasID;
import com.helger.base.lang.EnumHelper;
import com.helger.text.display.IHasDisplayText;

/**
 * This enum defines the potential states for SMP transport profiles.
 *
 * @author Philip Helger
 * @since 8.8.3
 */
public enum ESMPTransportProfileState implements IHasID <String>, IHasDisplayText
{
  /**
   * A transport profile with this state can be used without limitation.
   */
  ACTIVE ("active", ESMPTransportProfileStateText.ACTIVE),

  /**
   * A transport profile can still be used, but should not be used for new SMP
   * entries.
   */
  DEPRECATED ("deprecated", ESMPTransportProfileStateText.DEPRECATED),

  /**
   * This transport profile can no longer be used.
   */
  DELETED ("deleted", ESMPTransportProfileStateText.DELETED);

  private final String m_sID;
  private final ESMPTransportProfileStateText m_eText;

  ESMPTransportProfileState (@NonNull @Nonempty final String sID, @NonNull final ESMPTransportProfileStateText eText)
  {
    m_sID = sID;
    m_eText = eText;
  }

  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nullable
  public String getDisplayText (@NonNull final Locale aContentLocale)
  {
    return m_eText.getDisplayText (aContentLocale);
  }

  public boolean isActive ()
  {
    return this == ACTIVE;
  }

  public boolean isDeprecated ()
  {
    return this == DEPRECATED;
  }

  public boolean isDeleted ()
  {
    return this == DELETED;
  }

  /**
   * Find the pre-defined transport profile state with the passed ID.
   *
   * @param sID
   *        The ID to be searched. May be <code>null</code>.
   * @return <code>null</code> if no such transport profile was found.
   */
  @Nullable
  public static ESMPTransportProfileState getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (ESMPTransportProfileState.class, sID);
  }
}
