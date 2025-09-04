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

import com.helger.annotation.Nonempty;
import com.helger.base.lang.EnumHelper;
import com.helger.base.type.ObjectType;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.sml.SMLInfo;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * The list of supported HR eDelivery SML zones
 *
 * @author Philip Helger
 * @since 12.0.2
 */
public enum EHREDeliverySML implements ISMLInfo
{
  DEMO ("hr-demo",
        "HR Demo SML",
        "demo.ams.porezna-uprava.hr.",
        "https://demo.ams.porezna-uprava.hr/edelivery-sml",
        true),
  PRODUCTION ("hr-prod",
              "HR Production SML",
              "prod.ams.porezna-uprava.hr.",
              "https://prod.ams.porezna-uprava.hr/edelivery-sml",
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
  EHREDeliverySML (@Nonnull @Nonempty final String sID,
                   @Nonnull @Nonempty final String sDisplayName,
                   @Nonnull @Nonempty final String sDNSZone,
                   @Nonnull @Nonempty final String sManagementServiceURL,
                   final boolean bRequiresClientCertificate)
  {
    m_aProxy = new SMLInfo (sID, sDisplayName, sDNSZone, sManagementServiceURL, bRequiresClientCertificate);
  }

  @Nonnull
  public ObjectType getObjectType ()
  {
    return SMLInfo.OT;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_aProxy.getID ();
  }

  @Nonnull
  @Nonempty
  public String getDisplayName ()
  {
    return m_aProxy.getDisplayName ();
  }

  @Nonnull
  @Nonempty
  public String getDNSZone ()
  {
    return m_aProxy.getDNSZone ();
  }

  @Nonnull
  @Nonempty
  public String getManagementServiceURL ()
  {
    return m_aProxy.getManagementServiceURL ();
  }

  @Nonnull
  public URL getManageServiceMetaDataEndpointAddress ()
  {
    return m_aProxy.getManageServiceMetaDataEndpointAddress ();
  }

  @Nonnull
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
