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
package com.helger.hredelivery.commons;

import com.helger.annotation.Nonempty;

import jakarta.annotation.Nonnull;

/**
 * The list of supported HR eDelivery SML zones
 *
 * @author Philip Helger
 * @since 12.0.2
 */
public enum EHREDeliverySML
{
  DEMO ("demo.ams.porezna-uprava.hr."),
  PRODUCTION ("prod.ams.porezna-uprava.hr.");

  private final String m_sZoneName;

  EHREDeliverySML (@Nonnull @Nonempty final String sZoneName)
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
