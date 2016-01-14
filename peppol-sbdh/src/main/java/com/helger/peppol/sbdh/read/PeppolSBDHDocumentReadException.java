/**
 * Copyright (C) 2014-2016 Philip Helger
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
package com.helger.peppol.sbdh.read;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.string.StringHelper;

/**
 * Exception that can occur during the reading of SBDH documents.
 * 
 * @author Philip Helger
 */
public class PeppolSBDHDocumentReadException extends Exception
{
  private final EPeppolSBDHDocumentReadError m_eErrorCode;

  public PeppolSBDHDocumentReadException (@Nonnull final EPeppolSBDHDocumentReadError eErrorCode)
  {
    this (eErrorCode, null);
  }

  public PeppolSBDHDocumentReadException (@Nonnull final EPeppolSBDHDocumentReadError eErrorCode,
                                    @Nullable final String sAdditionalInformation)
  {
    super ("[" +
           eErrorCode.getID () +
           "] " +
           eErrorCode.getErrorMessage () +
           (StringHelper.hasText (sAdditionalInformation) ? ": " + sAdditionalInformation : ""));
    m_eErrorCode = eErrorCode;
  }

  public PeppolSBDHDocumentReadException (@Nonnull final EPeppolSBDHDocumentReadError eErrorCode,
                                    @Nonnull final String sValue1,
                                    @Nonnull final String sValue2)
  {
    super ("[" +
           eErrorCode.getID () +
           "] " +
           eErrorCode.getErrorMessage () +
           ": '" +
           sValue1 +
           "' vs. '" +
           sValue2 +
           "'");
    m_eErrorCode = eErrorCode;
  }

  @Nonnull
  public EPeppolSBDHDocumentReadError getErrorCode ()
  {
    return m_eErrorCode;
  }
}
