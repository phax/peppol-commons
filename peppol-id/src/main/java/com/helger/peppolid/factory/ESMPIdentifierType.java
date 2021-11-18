/*
 * Copyright (C) 2015-2021 Philip Helger
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
package com.helger.peppolid.factory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;
import com.helger.commons.name.IHasDisplayName;

/**
 * Defines the identifier types to be used - simple (allows all), Peppol
 * (special schemes) or BDXR (different implementation type).
 *
 * @author Philip Helger
 * @since 8.0.2
 */
public enum ESMPIdentifierType implements IHasID <String>, IHasDisplayName
{
  SIMPLE ("simple", "Simple", SimpleIdentifierFactory.INSTANCE),
  PEPPOL ("peppol", "Peppol", PeppolIdentifierFactory.INSTANCE),
  BDXR1 ("bdxr1", "OASIS BDXR v1", BDXR1IdentifierFactory.INSTANCE),
  BDXR2 ("bdxr2", "OASIS BDXR v2", BDXR2IdentifierFactory.INSTANCE);

  private final String m_sID;
  private final String m_sDisplayName;
  private final IIdentifierFactory m_aIF;

  ESMPIdentifierType (@Nonnull @Nonempty final String sID,
                      @Nonnull @Nonempty final String sDisplayName,
                      @Nonnull final IIdentifierFactory aIF)
  {
    m_sID = sID;
    m_sDisplayName = sDisplayName;
    m_aIF = aIF;
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

  @Nonnull
  public IIdentifierFactory getIdentifierFactory ()
  {
    return m_aIF;
  }

  @Nullable
  public static ESMPIdentifierType getFromIDOrNull (@Nullable final String sID)
  {
    return getFromIDOrDefault (sID, null);
  }

  @Nullable
  public static ESMPIdentifierType getFromIDOrDefault (@Nullable final String sID, @Nullable final ESMPIdentifierType eDefault)
  {
    // Legacy ID
    if ("bdxr".equals (sID))
      return BDXR1;

    return EnumHelper.getFromIDOrDefault (ESMPIdentifierType.class, sID, eDefault);
  }
}
