/*
 * Copyright (C) 2015-2026 Philip Helger (www.helger.com)
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
package com.helger.peppol.businesscard.helper;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.base.id.IHasID;
import com.helger.base.lang.EnumHelper;

/**
 * Different business card syntax versions. In a separate file since 12.3.0
 *
 * @author Philip Helger
 */
public enum EBusinessCardVersion implements IHasID <String>
{
  V1 ("v1"),
  V2 ("v2"),
  V3 ("v3");

  static final EBusinessCardVersion LATEST = V3;

  private final String m_sID;

  EBusinessCardVersion (@NonNull @Nonempty final String sID)
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
  public static EBusinessCardVersion getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EBusinessCardVersion.class, sID);
  }
}
