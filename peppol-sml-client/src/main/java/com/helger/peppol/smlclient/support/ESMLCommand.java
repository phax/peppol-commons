/*
 * Copyright (C) 2015-2025 Philip Helger
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

import com.helger.annotation.Nonempty;
import com.helger.base.lang.EnumHelper;
import com.helger.base.name.IHasName;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Enum with all SML client commands.
 *
 * @author Philip Helger
 */
public enum ESMLCommand implements IHasName
{
  CREATE ("create"),
  DELETE ("delete"),
  UPDATE ("update"),
  READ ("read"),
  LIST ("list"),
  PREPARETOMIGRATE ("preparetomigrate"),
  MIGRATE ("migrate");

  private final String m_sName;

  ESMLCommand (@Nonnull @Nonempty final String sName)
  {
    m_sName = sName;
  }

  @Nonnull
  @Nonempty
  public String getName ()
  {
    return m_sName;
  }

  @Nullable
  public static ESMLCommand getFromNameOrNull (@Nullable final String sName)
  {
    return EnumHelper.getFromNameCaseInsensitiveOrNull (ESMLCommand.class, sName);
  }
}
