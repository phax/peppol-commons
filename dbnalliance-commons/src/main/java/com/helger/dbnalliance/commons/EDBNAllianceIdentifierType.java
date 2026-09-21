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
package com.helger.dbnalliance.commons;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.base.lang.EnumHelper;
import com.helger.peppolid.factory.DBNAllianceIdentifierFactory;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.factory.IIdentifierFactoryType;

/**
 * Defines the identifier types of DBNAlliance.
 *
 * @author Philip Helger
 * @since 13.0.0
 */
public enum EDBNAllianceIdentifierType implements IIdentifierFactoryType
{
  DBNALLIANCE ("dbnalliance", "DBNAlliance", DBNAllianceIdentifierFactory.INSTANCE);

  private final String m_sID;
  private final String m_sDisplayName;
  private final IIdentifierFactory m_aIF;

  EDBNAllianceIdentifierType (@NonNull @Nonempty final String sID,
                              @NonNull @Nonempty final String sDisplayName,
                              @NonNull final IIdentifierFactory aIF)
  {
    m_sID = sID;
    m_sDisplayName = sDisplayName;
    m_aIF = aIF;
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
  public IIdentifierFactory getIdentifierFactory ()
  {
    return m_aIF;
  }

  @Nullable
  public static EDBNAllianceIdentifierType getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EDBNAllianceIdentifierType.class, sID);
  }
}
