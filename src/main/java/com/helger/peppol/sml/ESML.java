/**
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.sml;

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.Nonnull;

import com.helger.commons.annotations.Nonempty;

/**
 * Simple enumeration for differentiating the different available SMLs.
 * <ul>
 * <li>PRODUCTION - used for production environment</li>
 * <li>TEST - used for test environment</li>
 * <li>DEVELOPMENT - used for development environment</li>
 * <li>DEVELOPMENT_LOCAL - used for development environment assuming the
 * management application is running on localhost in the ROOT context!</li>
 * </ul>
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public enum ESML implements ISMLInfo
{
  /** https://sml.peppolcentral.org */
  PRODUCTION ("sml.peppolcentral.org.", "https://sml.peppolcentral.org", "https://sml.peppolcentral.org"),
  /** https://smk.peppolcentral.org */
  TEST ("smk.peppolcentral.org.", "https://smk.peppolcentral.org", "https://smk.peppolcentral.org"),
  /** http://plixvde2 */
  DEVELOPMENT ("smj.peppolcentral.org.", "http://plixvde2", "http://plixvde2/ServiceMetadataLocatorManagement"),
  /** http://localhost:8080 */
  DEVELOPMENT_LOCAL ("smj.peppolcentral.org.", "http://localhost:8080", "http://localhost:8080");

  private final String m_sDNSZone;
  private final String m_sManagementHostName;
  private final String m_sManagementServiceURL;

  private ESML (@Nonnull @Nonempty final String sDNSZone,
                @Nonnull @Nonempty final String sManagementHostName,
                @Nonnull @Nonempty final String sManagementServiceURL)
  {
    m_sDNSZone = sDNSZone;
    m_sManagementHostName = sManagementHostName;
    m_sManagementServiceURL = sManagementServiceURL;
  }

  @Nonnull
  @Nonempty
  public String getDNSZone ()
  {
    return m_sDNSZone;
  }

  @Nonnull
  @Nonempty
  public String getPublisherDNSName ()
  {
    return CSMLDefault.DNS_PUBLISHER_SUBZONE + m_sDNSZone;
  }

  @Nonnull
  @Nonempty
  public String getManagementHostName ()
  {
    return m_sManagementHostName;
  }

  @Nonnull
  @Nonempty
  public String getManagementServiceURL ()
  {
    return m_sManagementServiceURL;
  }

  @Nonnull
  public URL getManageServiceMetaDataEndpointAddress ()
  {
    final String sURL = m_sManagementServiceURL + '/' + CSMLDefault.MANAGEMENT_SERVICE_METADATA;
    try
    {
      return new URL (sURL);
    }
    catch (final MalformedURLException ex)
    {
      throw new IllegalStateException ("Failed to convert '" + sURL + "' to URL", ex);
    }
  }

  @Nonnull
  public URL getManageParticipantIdentifierEndpointAddress ()
  {
    final String sURL = m_sManagementServiceURL + '/' + CSMLDefault.MANAGEMENT_SERVICE_PARTICIPANTIDENTIFIER;
    try
    {
      return new URL (sURL);
    }
    catch (final MalformedURLException ex)
    {
      throw new IllegalStateException ("Failed to convert '" + sURL + "' to URL", ex);
    }
  }

  public boolean requiresClientCertificate ()
  {
    return this == PRODUCTION || this == TEST;
  }
}
