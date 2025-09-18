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
import com.helger.base.id.IHasID;
import com.helger.base.lang.EnumHelper;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * The list of supported HR eDelivery stages
 *
 * @author Philip Helger
 * @since 12.0.2
 */
public enum EHREDeliveryStage implements IHasID <String>
{
  DEMO ("demo", EHREDeliverySML.DEMO),
  PRODUCTION ("prod", EHREDeliverySML.PRODUCTION);

  private final String m_sID;
  private final EHREDeliverySML m_eSML;

  EHREDeliveryStage (@Nonnull @Nonempty final String sID, @Nonnull final EHREDeliverySML eSML)
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
  public EHREDeliverySML getSML ()
  {
    return m_eSML;
  }

  @Nullable
  public static EHREDeliveryStage getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EHREDeliveryStage.class, sID);
  }
}
