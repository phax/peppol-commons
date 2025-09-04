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
package com.helger.hredelivery.commons.sbdh;

import jakarta.annotation.Nonnull;

/**
 * Exception that can occur during the reading of HR eDelivery SBDH documents.
 *
 * @author Philip Helger
 */
public class HREDeliverySBDHDataReadException extends Exception
{
  private final EHREDeliverySBDHDataError m_eErrorCode;

  HREDeliverySBDHDataReadException (@Nonnull final String sErrorMsg, @Nonnull final EHREDeliverySBDHDataError eErrorCode)
  {
    super (sErrorMsg);
    m_eErrorCode = eErrorCode;
  }

  public HREDeliverySBDHDataReadException (@Nonnull final EHREDeliverySBDHDataError eErrorCode)
  {
    super (eErrorCode.getErrorMessage ());
    m_eErrorCode = eErrorCode;
  }

  @Nonnull
  public final EHREDeliverySBDHDataError getErrorCode ()
  {
    return m_eErrorCode;
  }
}
