/**
 * Copyright (C) 2015-2020 Philip Helger
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
package com.helger.peppol.sml;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;
import com.helger.commons.name.IHasDisplayName;

/**
 * Enumerates the different SMP APIs available.
 *
 * @author Philip Helger
 * @since 7.0.0
 */
public enum ESMPAPIType implements IHasID <String>, IHasDisplayName
{
  /**
   * An SMP using the "Peppol" SMP specification.
   */
  PEPPOL ("peppol", "Peppol"),
  /**
   * An SMP using the OASIS BDXR SMP v1 specification.
   */
  OASIS_BDXR_V1 ("bdxr1", "OASIS BDXR SMP v1"),
  /**
   * An SMP using the OASIS BDXR SMP v2 specification.
   */
  OASIS_BDXR_V2 ("bdxr2", "OASIS BDXR SMP v2");

  private final String m_sID;
  private final String m_sDisplayName;

  private ESMPAPIType (@Nonnull @Nonempty final String sID, @Nonnull @Nonempty final String sDisplayName)
  {
    m_sID = sID;
    m_sDisplayName = sDisplayName;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nonnull
  @Nonempty
  public String getDisplayName ()
  {
    return m_sDisplayName;
  }

  @Nullable
  public static ESMPAPIType getFromIDOrNull (@Nullable final String sID)
  {
    return getFromIDOrDefault (sID, null);
  }

  @Nullable
  public static ESMPAPIType getFromIDOrDefault (@Nullable final String sID, @Nullable final ESMPAPIType eDefault)
  {
    return EnumHelper.getFromIDOrDefault (ESMPAPIType.class, sID, eDefault);
  }
}
