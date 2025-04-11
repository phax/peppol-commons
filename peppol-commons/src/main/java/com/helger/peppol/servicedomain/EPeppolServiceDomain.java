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
package com.helger.peppol.servicedomain;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;
import com.helger.peppol.utils.PeppolCertificateChecker;
import com.helger.security.certificate.TrustedCAChecker;

/**
 * This enum lists all the Peppol Service Domains. The additional information are primarily around
 * the required certificates.
 *
 * @author Philip Helger
 * @since 9.6.0
 */
public enum EPeppolServiceDomain implements IHasID <String>
{
  /**
   * Managed by PoAC
   */
  POST_AWARD ("post-award",
              PeppolCertificateChecker.peppolTestAP (),
              PeppolCertificateChecker.peppolProductionAP (),
              PeppolCertificateChecker.peppolTestSMP (),
              PeppolCertificateChecker.peppolProductionSMP ()),
  /**
   * Managed by PrAC
   */
  PRE_AWARD ("pre-award",
             PeppolCertificateChecker.peppolTestAP (),
             PeppolCertificateChecker.peppolProductionAP (),
             PeppolCertificateChecker.peppolTestSMP (),
             PeppolCertificateChecker.peppolProductionSMP ()),
  /**
   * Enhanced B2B for Peppol-GENA bridge
   */
  ENHANCED_B2B ("eb2b",
                PeppolCertificateChecker.peppolTestEb2bAP (),
                null,
                PeppolCertificateChecker.peppolTestSMP (),
                null);

  private final String m_sID;
  private final TrustedCAChecker m_aTestAPChecker;
  private final TrustedCAChecker m_aProdAPChecker;
  private final TrustedCAChecker m_aTestSMPChecker;
  private final TrustedCAChecker m_aProdSMPChecker;

  EPeppolServiceDomain (@Nonnull @Nonempty final String sID,
                        @Nullable final TrustedCAChecker aTestAPChecker,
                        @Nullable final TrustedCAChecker aProdAPChecker,
                        @Nullable final TrustedCAChecker aTestSMPChecker,
                        @Nullable final TrustedCAChecker aProdSMPChecker)
  {
    m_sID = sID;
    m_aTestAPChecker = aTestAPChecker;
    m_aProdAPChecker = aProdAPChecker;
    m_aTestSMPChecker = aTestSMPChecker;
    m_aProdSMPChecker = aProdSMPChecker;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nullable
  public TrustedCAChecker getTestAPChecker ()
  {
    return m_aTestAPChecker;
  }

  @Nullable
  public TrustedCAChecker getProdAPChecker ()
  {
    return m_aProdAPChecker;
  }

  @Nullable
  public TrustedCAChecker getAPChecker (@Nonnull final EPeppolNetwork eNetwork)
  {
    return eNetwork.isTest () ? m_aTestAPChecker : m_aProdAPChecker;
  }

  @Nullable
  public TrustedCAChecker getTestSMPChecker ()
  {
    return m_aTestSMPChecker;
  }

  @Nullable
  public TrustedCAChecker getProdSMPChecker ()
  {
    return m_aProdSMPChecker;
  }

  @Nullable
  public TrustedCAChecker getSMPChecker (@Nonnull final EPeppolNetwork eNetwork)
  {
    return eNetwork.isTest () ? m_aTestSMPChecker : m_aProdSMPChecker;
  }

  @Nullable
  public static EPeppolServiceDomain getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EPeppolServiceDomain.class, sID);
  }
}
