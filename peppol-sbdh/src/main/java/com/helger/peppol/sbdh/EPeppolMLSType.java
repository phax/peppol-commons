/*
 * Copyright (C) 2014-2025 Philip Helger
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
package com.helger.peppol.sbdh;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.base.id.IHasID;
import com.helger.base.lang.EnumHelper;

/**
 * The MLS type to be used inside the SBDH. Values are case sensitive.
 *
 * @author Philip Helger
 * @since 10.3.3
 */
public enum EPeppolMLSType implements IHasID <String>
{
  /**
   * The sender requests an MLS on failure only.
   */
  FAILURE_ONLY ("FAILURE_ONLY"),

  /**
   * The sender requests an MLS for all statuses.
   */
  ALWAYS_SEND ("ALWAYS_SEND");

  private final String m_sID;

  EPeppolMLSType (@NonNull @Nonempty final String sID)
  {
    m_sID = sID;
  }

  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nullable
  public static EPeppolMLSType getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EPeppolMLSType.class, sID);
  }

  @Nullable
  public static EPeppolMLSType getFromIDOrDefault (@Nullable final String sID, @Nullable final EPeppolMLSType eDefault)
  {
    return EnumHelper.getFromIDOrDefault (EPeppolMLSType.class, sID, eDefault);
  }
}
