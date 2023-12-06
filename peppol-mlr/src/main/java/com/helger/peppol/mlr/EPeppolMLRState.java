/*
 * Copyright (C) 2023 Philip Helger
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
package com.helger.peppol.mlr;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;
import com.helger.commons.state.ISuccessIndicator;

public enum EPeppolMLRState implements IHasID <String>, ISuccessIndicator
{
  ACCEPTANCE ("AP"),
  ACKNOWLEDGING ("AB"),
  REJECTION ("RE");

  private final String m_sID;

  EPeppolMLRState (@Nonnull @Nonempty final String sID)
  {
    m_sID = sID;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  public boolean isSuccess ()
  {
    return this == ACCEPTANCE || this == ACKNOWLEDGING;
  }

  public boolean isFailure ()
  {
    return this == REJECTION;
  }

  @Nullable
  public static EPeppolMLRState getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EPeppolMLRState.class, sID);
  }
}
