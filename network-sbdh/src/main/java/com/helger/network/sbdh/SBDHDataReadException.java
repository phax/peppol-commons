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
package com.helger.network.sbdh;

import org.jspecify.annotations.NonNull;

/**
 * Exception that can occur during the reading of Standard Business Documents. The error code is
 * network specific, therefore only the base interface is used here.
 *
 * @author Philip Helger
 * @since 13.0.0
 */
public class SBDHDataReadException extends Exception
{
  private final ISBDHDataError m_aErrorCode;

  public SBDHDataReadException (@NonNull final String sErrorMsg, @NonNull final ISBDHDataError aErrorCode)
  {
    super (sErrorMsg);
    m_aErrorCode = aErrorCode;
  }

  public SBDHDataReadException (@NonNull final ISBDHDataError aErrorCode)
  {
    this (aErrorCode.getErrorMessage (), aErrorCode);
  }

  /**
   * @return The error code that lead to this exception. Never <code>null</code>.
   */
  @NonNull
  public ISBDHDataError getErrorCode ()
  {
    return m_aErrorCode;
  }
}
