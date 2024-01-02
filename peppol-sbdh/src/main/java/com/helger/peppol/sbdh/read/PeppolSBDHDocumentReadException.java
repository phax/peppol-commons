/*
 * Copyright (C) 2014-2023 Philip Helger
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

import com.helger.xsds.peppol.id1.ChangeV10;

/**
 * Exception that can occur during the reading of SBDH documents.
 *
 * @author Philip Helger
 */
@ChangeV10 ("Move to package of PeppolSBDHData")
public class PeppolSBDHDocumentReadException extends Exception
{
  private final EPeppolSBDHDocumentReadError m_eErrorCode;

  PeppolSBDHDocumentReadException (@Nonnull final String sErrorMsg,
                                   @Nonnull final EPeppolSBDHDocumentReadError eErrorCode)
  {
    super (sErrorMsg);
    m_eErrorCode = eErrorCode;
  }

  public PeppolSBDHDocumentReadException (@Nonnull final EPeppolSBDHDocumentReadError eErrorCode)
  {
    super (eErrorCode.getErrorMessage ());
    m_eErrorCode = eErrorCode;
  }

  @Deprecated (forRemoval = true, since = "9.2.0")
  public PeppolSBDHDocumentReadException (@Nonnull final EPeppolSBDHDocumentReadError eErrorCode,
                                          @Nullable final Object... aArgs)
  {
    super (eErrorCode.getErrorMessage (aArgs));
    m_eErrorCode = eErrorCode;
  }

  @Nonnull
  public final EPeppolSBDHDocumentReadError getErrorCode ()
  {
    return m_eErrorCode;
  }
}
