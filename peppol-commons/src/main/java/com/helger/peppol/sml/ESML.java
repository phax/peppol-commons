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
package com.helger.peppol.sml;

import java.net.MalformedURLException;
import java.net.URL;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.base.lang.EnumHelper;
import com.helger.base.type.ObjectType;

/**
 * Simple enumeration for differentiating the different available SMLs.
 * <ul>
 * <li>DIGIT_PRODUCTION - DIGIT production URL - valid from June 9th, 2015
 * <li>DIGIT_TEST - DIGIT test URL - valid from June 9th, 2015
 * <li>DEVELOPMENT_LOCAL - used for development environment assuming the management application is
 * running on localhost in the ROOT context!</li>
 * </ul>
 *
 * @author Philip Helger
 */
public enum ESML implements ISMLInfo
{
  /** DIGIT production URL - valid from June 9th, 2015 */
  DIGIT_PRODUCTION ("digitprod",
                    "SML",
                    "edelivery.tech.ec.europa.eu.",
                    "https://edelivery.tech.ec.europa.eu/edelivery-sml",
                    true),
  /** DIGIT test URL - valid from June 9th, 2015 */
  DIGIT_TEST ("digittest",
              "SMK",
              "acc.edelivery.tech.ec.europa.eu.",
              "https://acc.edelivery.tech.ec.europa.eu/edelivery-sml",
              true),
  /** http://localhost:8080 */
  DEVELOPMENT_LOCAL ("local", "Development", "smj.peppolcentral.org.", "http://localhost:8080", false);

  private final SMLInfo m_aProxy;

  /**
   * Constructor
   *
   * @param sID
   *        The internal ID. Neither <code>null</code> nor empty.
   * @param sDNSZone
   *        DNS zone name. Must not start with a "." but must end with a "."
   * @param sManagementServiceURL
   *        The base service URL uses as the basis for participant and metadata management.
   * @param bRequiresClientCertificate
   *        <code>true</code> if this server requires a client certificate, <code>false</code> if
   *        not.
   * @throws MalformedURLException
   */
  ESML (@NonNull @Nonempty final String sID,
        @NonNull @Nonempty final String sDisplayName,
        @NonNull @Nonempty final String sDNSZone,
        @NonNull @Nonempty final String sManagementServiceURL,
        final boolean bRequiresClientCertificate)
  {
    m_aProxy = SMLInfo.builder ()
                      .id (sID)
                      .displayName (sDisplayName)
                      .dnsZone (sDNSZone)
                      .managementServiceURL (sManagementServiceURL)
                      .clientCertificateRequired (bRequiresClientCertificate)
                      .build ();
  }

  @NonNull
  public ObjectType getObjectType ()
  {
    return SMLInfo.OT;
  }

  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_aProxy.getID ();
  }

  @NonNull
  @Nonempty
  public String getDisplayName ()
  {
    return m_aProxy.getDisplayName ();
  }

  @NonNull
  @Nonempty
  public String getDNSZone ()
  {
    return m_aProxy.getDNSZone ();
  }

  @NonNull
  public String getURLSuffixManageSMP ()
  {
    return m_aProxy.getURLSuffixManageSMP ();
  }

  @NonNull
  @Nonempty
  public String getManagementServiceURL ()
  {
    return m_aProxy.getManagementServiceURL ();
  }

  @NonNull
  public String getURLSuffixManageParticipant ()
  {
    return m_aProxy.getURLSuffixManageParticipant ();
  }

  @NonNull
  public URL getManageServiceMetaDataEndpointAddress ()
  {
    return m_aProxy.getManageServiceMetaDataEndpointAddress ();
  }

  @NonNull
  public URL getManageParticipantIdentifierEndpointAddress ()
  {
    return m_aProxy.getManageParticipantIdentifierEndpointAddress ();
  }

  public boolean isClientCertificateRequired ()
  {
    return m_aProxy.isClientCertificateRequired ();
  }

  @Nullable
  public static ESML getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (ESML.class, sID);
  }

  @Nullable
  public static ESML getFromIDOrDefault (@Nullable final String sID, @Nullable final ESML eDefault)
  {
    return EnumHelper.getFromIDOrDefault (ESML.class, sID, eDefault);
  }
}
