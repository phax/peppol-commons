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
package com.helger.smpclient.dbna;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.Nonempty;

/**
 * The list of supported DBNA SML zones
 *
 * @author Philip Helger
 * @since 9.3.4
 */
public enum EDBNASML
{
  PRODUCTION ("sml.dbnalliance.net."),
  TEST ("sml.dbnalliance.com."),
  PILOT ("sml.dbnalliancepilot.net.");

  private final String m_sZoneName;

  EDBNASML (@Nonnull @Nonempty final String sZoneName)
  {
    m_sZoneName = sZoneName;
  }

  @Nonnull
  @Nonempty
  public String getZoneName ()
  {
    return m_sZoneName;
  }
}
