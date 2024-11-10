/*
 * Copyright (C) 2015-2024 Philip Helger
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
import com.helger.peppol.utils.PeppolCAChecker;
import com.helger.peppol.utils.PeppolCertificateChecker;

/**
 * This enum lists all the Peppol Service Domains. The additional information
 * are primarily around the required certificates.
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
              PeppolCertificateChecker.peppolPilotAP (),
              PeppolCertificateChecker.peppolProductionAP (),
              PeppolCertificateChecker.peppolPilotSMP (),
              PeppolCertificateChecker.peppolProductionSMP ()),
  /**
   * Managed by PrAC
   */
  PRE_AWARD ("pre-award",
             PeppolCertificateChecker.peppolPilotAP (),
             PeppolCertificateChecker.peppolProductionAP (),
             PeppolCertificateChecker.peppolPilotSMP (),
             PeppolCertificateChecker.peppolProductionSMP ()),
  /**
   * Enhanced B2B for Peppol-GENA bridge
   */
  ENHANCED_B2B ("eb2b",
                PeppolCertificateChecker.peppolPilotEb2bAP (),
                null,
                PeppolCertificateChecker.peppolPilotSMP (),
                null);

  private final String m_sID;
  private final PeppolCAChecker m_aPilotAPChecker;
  private final PeppolCAChecker m_aProdAPChecker;
  private final PeppolCAChecker m_aPilotSMPChecker;
  private final PeppolCAChecker m_aProdSMPChecker;

  EPeppolServiceDomain (@Nonnull @Nonempty final String sID,
                        @Nullable final PeppolCAChecker aPilotAPChecker,
                        @Nullable final PeppolCAChecker aProdAPChecker,
                        @Nullable final PeppolCAChecker aPilotSMPChecker,
                        @Nullable final PeppolCAChecker aProdSMPChecker)
  {
    m_sID = sID;
    m_aPilotAPChecker = aPilotAPChecker;
    m_aProdAPChecker = aProdAPChecker;
    m_aPilotSMPChecker = aPilotSMPChecker;
    m_aProdSMPChecker = aProdSMPChecker;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nullable
  public final PeppolCAChecker getPilotAPChecker ()
  {
    return m_aPilotAPChecker;
  }

  @Nullable
  public final PeppolCAChecker getProdAPChecker ()
  {
    return m_aProdAPChecker;
  }

  @Nullable
  public final PeppolCAChecker getPilotSMPChecker ()
  {
    return m_aPilotSMPChecker;
  }

  @Nullable
  public final PeppolCAChecker getProdSMPChecker ()
  {
    return m_aProdSMPChecker;
  }

  @Nullable
  public static EPeppolServiceDomain getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EPeppolServiceDomain.class, sID);
  }
}
