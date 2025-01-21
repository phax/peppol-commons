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
package com.helger.peppol.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.Since;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;
import com.helger.commons.state.IValidityIndicator;

/**
 * Enumeration for all Peppol certificate checks
 *
 * @author Philip Helger
 * @since 7.0.4
 */
public enum EPeppolCertificateCheckResult implements IHasID <String>, IValidityIndicator
{
  VALID ("valid", "certificate is valid"),
  NO_CERTIFICATE_PROVIDED ("nocert", "no certificate provided"),
  NOT_YET_VALID ("notyetvalid", "certificate is not yet valid"),
  EXPIRED ("expired", "certificate is already expired"),
  UNSUPPORTED_ISSUER ("unsupportedissuer", "unsupported certificate issuer"),
  REVOKED ("revoked", "certificate is revoked"),
  @Since ("9.0.8")
  NOT_CHECKED("not-checked", "the certificate was not checked");

  private final String m_sID;
  private final String m_sReason;

  EPeppolCertificateCheckResult (@Nonnull @Nonempty final String sID, @Nonnull @Nonempty final String sReason)
  {
    m_sID = sID;
    m_sReason = sReason;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nonnull
  @Nonempty
  public String getReason ()
  {
    return m_sReason;
  }

  public boolean isValid ()
  {
    return this == VALID;
  }

  /**
   * Find the item with the passed ID.
   *
   * @param sID
   *        The ID to be searched. May be <code>null</code>.
   * @return <code>null</code> if no such item was found.
   */
  @Nullable
  public static EPeppolCertificateCheckResult getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EPeppolCertificateCheckResult.class, sID);
  }

  /**
   * Find the item with the passed ID.
   *
   * @param sID
   *        The ID to be searched. May be <code>null</code>.
   * @param eDefault
   *        The default value to be returned if no such ID is contained. May be
   *        <code>null</code>.
   * @return <code>eDefault</code> if no such item was found.
   */
  @Nullable
  public static EPeppolCertificateCheckResult getFromIDOrDefault (@Nullable final String sID,
                                                                  @Nullable final EPeppolCertificateCheckResult eDefault)
  {
    return EnumHelper.getFromIDOrDefault (EPeppolCertificateCheckResult.class, sID, eDefault);
  }
}
