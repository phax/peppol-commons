/*
 * Copyright (C) 2025 Philip Helger
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
package com.helger.hredelivery.commons;

import java.net.MalformedURLException;
import java.net.URL;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.base.lang.EnumHelper;
import com.helger.base.type.ObjectType;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.sml.SMLInfo;

/**
 * The list of supported HR eDelivery SML zones
 *
 * @author Philip Helger
 * @since 12.0.2
 */
public enum EHREDeliverySML implements ISMLInfo
{
  DEMO ("hr-demo", "HR Demo SML", "demo.ams.porezna-uprava.hr.", "https://cis.porezna-uprava.hr:8515/proxy", true),
  PRODUCTION ("hr-prod",
              "HR Production SML",
              "prod.ams.porezna-uprava.hr.",
              "https://cis.porezna-uprava.hr:8513/proxy",
              true);

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
  EHREDeliverySML (@NonNull @Nonempty final String sID,
                   @NonNull @Nonempty final String sDisplayName,
                   @NonNull @Nonempty final String sDNSZone,
                   @NonNull @Nonempty final String sManagementServiceURL,
                   final boolean bRequiresClientCertificate)
  {
    // HR uses special URL suffixes
    m_aProxy = SMLInfo.builder ()
                      .id (sID)
                      .displayName (sDisplayName)
                      .dnsZone (sDNSZone)
                      .managementServiceURL (sManagementServiceURL)
                      .urlSuffixManageSMP ("")
                      .urlSuffixManageParticipant ("")
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
  @Nonempty
  public String getManagementServiceURL ()
  {
    return m_aProxy.getManagementServiceURL ();
  }

  @NonNull
  public String getURLSuffixManageSMP ()
  {
    return m_aProxy.getURLSuffixManageSMP ();
  }

  @NonNull
  public URL getManageServiceMetaDataEndpointAddress ()
  {
    return m_aProxy.getManageServiceMetaDataEndpointAddress ();
  }

  @NonNull
  public String getURLSuffixManageParticipant ()
  {
    return m_aProxy.getURLSuffixManageParticipant ();
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
  public static EHREDeliverySML getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EHREDeliverySML.class, sID);
  }

  @Nullable
  public static EHREDeliverySML getFromIDOrDefault (@Nullable final String sID,
                                                    @Nullable final EHREDeliverySML eDefault)
  {
    return EnumHelper.getFromIDOrDefault (EHREDeliverySML.class, sID, eDefault);
  }
}
