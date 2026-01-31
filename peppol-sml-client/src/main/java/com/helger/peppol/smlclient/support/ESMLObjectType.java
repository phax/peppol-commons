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
package com.helger.peppol.smlclient.support;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.base.lang.EnumHelper;
import com.helger.base.name.IHasName;

/**
 * Enumeration with all object types relevant to the SML.
 *
 * @author Philip Helger
 */
public enum ESMLObjectType implements IHasName
{
  PARTICIPANT ("participant"),
  METADATA ("metadata");

  private final String m_sName;

  ESMLObjectType (@NonNull @Nonempty final String sName)
  {
    m_sName = sName;
  }

  @NonNull
  @Nonempty
  public String getName ()
  {
    return m_sName;
  }

  @Nullable
  public static ESMLObjectType getFromNameOrNull (@Nullable final String sName)
  {
    return EnumHelper.getFromNameCaseInsensitiveOrNull (ESMLObjectType.class, sName);
  }
}
