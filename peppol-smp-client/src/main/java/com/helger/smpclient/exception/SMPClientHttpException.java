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
package com.helger.smpclient.exception;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.Nonnegative;

/**
 * Base class for all SMP client exceptions that are based on HTTP errors
 *
 * @author Philip Helger
 * @since 12.3.2
 */
public class SMPClientHttpException extends SMPClientException
{
  private final int m_nResponseStatusCode;

  public SMPClientHttpException (@Nonnegative final int nResponseStatusCode, @NonNull final String sMsg)
  {
    super (sMsg);
    m_nResponseStatusCode = nResponseStatusCode;
  }

  public SMPClientHttpException (@Nonnegative final int nResponseStatusCode, @NonNull final Throwable aCause)
  {
    super (aCause);
    m_nResponseStatusCode = nResponseStatusCode;
  }

  public SMPClientHttpException (@Nonnegative final int nResponseStatusCode,
                                 @NonNull final String sMsg,
                                 @NonNull final Throwable aCause)
  {
    super (sMsg, aCause);
    m_nResponseStatusCode = nResponseStatusCode;
  }

  /**
   * @return The HTTP response status code from the SMP server.
   */
  @Nonnegative
  public final int getResponseStatusCode ()
  {
    return m_nResponseStatusCode;
  }
}
