/*
 * Copyright (C) 2014-2024 Philip Helger
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
package com.helger.peppol.xhe.read;

import javax.annotation.Nonnull;

/**
 * Exception that can occur during the reading of XHE documents.
 *
 * @author Robinson Garcia
 */
public class DBNAllianceXHEDocumentReadException extends Exception {
  private final EDBNAllianceXHEDocumentReadError m_eErrorCode;

  DBNAllianceXHEDocumentReadException (@Nonnull final String sErrorMsg,
                                   @Nonnull final EDBNAllianceXHEDocumentReadError eErrorCode)
  {
    super (sErrorMsg);
    m_eErrorCode = eErrorCode;
  }

  public DBNAllianceXHEDocumentReadException (@Nonnull final EDBNAllianceXHEDocumentReadError eErrorCode)
  {
    super (eErrorCode.getErrorMessage ());
    m_eErrorCode = eErrorCode;
  }

  @Nonnull
  public final EDBNAllianceXHEDocumentReadError getErrorCode ()
  {
    return m_eErrorCode;
  }
}
