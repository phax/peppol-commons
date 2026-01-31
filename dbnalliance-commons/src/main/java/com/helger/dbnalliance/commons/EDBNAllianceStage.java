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
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.base.id.IHasID;
import com.helger.base.lang.EnumHelper;

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

  EDBNAllianceStage (@NonNull @Nonempty final String sID, @NonNull final EDBNAllianceSML eSML)
  {
    m_sID = sID;
    m_eSML = eSML;
  }

  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @NonNull
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
