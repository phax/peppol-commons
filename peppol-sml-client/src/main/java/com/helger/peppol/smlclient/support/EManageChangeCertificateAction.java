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
package com.helger.peppol.smlclient.support;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.base.id.IHasID;
import com.helger.base.lang.EnumHelper;

/**
 * Enum with all actions of the BDMSL <code>ManageChangeCertificate</code> operation, that is
 * available since BDMSL 5.1.
 *
 * @author Philip Helger
 * @since 13.3.0
 */
@SinceBDMSL ("5.1")
public enum EManageChangeCertificateAction implements IHasID <String>
{
  /**
   * Create or update the prepared certificate change, consisting of the new certificate and the
   * migration date.
   */
  UPSERT ("UPSERT"),
  /**
   * Read the prepared certificate change. A provided certificate is ignored.
   */
  READ ("READ"),
  /**
   * Delete the prepared certificate change. A provided certificate is ignored.
   */
  DELETE ("DELETE"),
  /**
   * Activate the provided certificate immediately. If no certificate is provided, the prepared
   * certificate change is activated immediately.
   */
  ACTIVATE ("ACTIVATE");

  private final String m_sID;

  EManageChangeCertificateAction (@NonNull @Nonempty final String sID)
  {
    m_sID = sID;
  }

  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nullable
  public static EManageChangeCertificateAction getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDCaseInsensitiveOrNull (EManageChangeCertificateAction.class, sID);
  }
}
