/*
 * Copyright (C) 2025 Philip Helger
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;

/**
 * The list of supported DBNAlliance stages
 *
 * @author Philip Helger
 */
public enum EDBNAllianceStage implements IHasID <String>
{
  PILOT ("pilot", EDBNAllianceSML.PILOT),
  TEST ("test", EDBNAllianceSML.TEST),
  PRODUCTION ("prod", EDBNAllianceSML.PRODUCTION);

  private final String m_sID;
  private final EDBNAllianceSML m_eSML;

  EDBNAllianceStage (@Nonnull @Nonempty final String sID, @Nonnull final EDBNAllianceSML eSML)
  {
    m_sID = sID;
    m_eSML = eSML;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nonnull
  public EDBNAllianceSML getSML ()
  {
    return m_eSML;
  }

  @Nullable
  public static EDBNAllianceStage getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EDBNAllianceStage.class, sID);
  }
}
