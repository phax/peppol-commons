/**
 * Copyright (C) 2015-2019 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.utils;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
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
  EXPIRED ("expired", "certificate is already expirted"),
  UNSUPPORTED_ISSUER ("unsupportedissuer", "unsupported certificate issuer"),
  REVOKED ("revoked", "certificate is revoked");

  private final String m_sID;
  private final String m_sReason;

  private EPeppolCertificateCheckResult (@Nonnull @Nonempty final String sID, @Nonnull @Nonempty final String sReason)
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
}
