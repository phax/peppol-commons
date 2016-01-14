/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
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
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.exception.InitializationException;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;

/**
 * Use instances of this class if you're not happy with the <code>ESML</code>
 * enumeration value but need an instance of {@link ISMLInfo}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Immutable
public class SimpleSMLInfo implements ISMLInfo
{
  private final String m_sDNSZone;
  private final String m_sManagementServiceURL;
  private final boolean m_bRequiresClientCertficate;
  // Cache for status
  private final URL m_aManageServiceMetaDataEndpointAddress;
  private final URL m_aManageParticipantIdentifierEndpointAddress;

  public SimpleSMLInfo (@Nonnull final ISMLInfo aSMLInfo)
  {
    this (aSMLInfo.getDNSZone (), aSMLInfo.getManagementServiceURL (), aSMLInfo.requiresClientCertificate ());
  }

  public SimpleSMLInfo (@Nonnull @Nonempty final String sDNSZone,
                        @Nonnull @Nonempty final String sManagementService,
                        final boolean bRequiresClientCertficate)
  {
    ValueEnforcer.notEmpty (sDNSZone, "DNSZone");
    ValueEnforcer.notEmpty (sManagementService, "ManagementService");

    m_sDNSZone = sDNSZone;
    // Management service without the trailing slash
    m_sManagementServiceURL = sManagementService.endsWith ("/") ? sManagementService.substring (0,
                                                                                                sManagementService.length () - 1)
                                                               : sManagementService;
    m_bRequiresClientCertficate = bRequiresClientCertficate;
    try
    {
      // Create once
      m_aManageServiceMetaDataEndpointAddress = new URL (m_sManagementServiceURL +
                                                         '/' +
                                                         CSMLDefault.MANAGEMENT_SERVICE_METADATA);
      m_aManageParticipantIdentifierEndpointAddress = new URL (m_sManagementServiceURL +
                                                               '/' +
                                                               CSMLDefault.MANAGEMENT_SERVICE_PARTICIPANTIDENTIFIER);
    }
    catch (final MalformedURLException ex)
    {
      throw new InitializationException ("Failed to convert to URL", ex);
    }
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
  public String getManagementServiceURL ()
  {
    return m_sManagementServiceURL;
  }

  @Nonnull
  public URL getManageServiceMetaDataEndpointAddress ()
  {
    return m_aManageServiceMetaDataEndpointAddress;
  }

  @Nonnull
  public URL getManageParticipantIdentifierEndpointAddress ()
  {
    return m_aManageParticipantIdentifierEndpointAddress;
  }

  public boolean requiresClientCertificate ()
  {
    return m_bRequiresClientCertficate;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final SimpleSMLInfo rhs = (SimpleSMLInfo) o;
    return m_sDNSZone.equals (rhs.m_sDNSZone) &&
           m_sManagementServiceURL.equals (rhs.m_sManagementServiceURL) &&
           m_bRequiresClientCertficate == rhs.m_bRequiresClientCertficate;
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sDNSZone)
                                       .append (m_sManagementServiceURL)
                                       .append (m_bRequiresClientCertficate)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("DNSZone", m_sDNSZone)
                                       .append ("ManagementServiceURL", m_sManagementServiceURL)
                                       .append ("RequiresClientCertificate", m_bRequiresClientCertficate)
                                       .append ("ManageServiceMetaDataEndpointAddress",
                                                m_aManageServiceMetaDataEndpointAddress)
                                       .append ("ManageParticipantIdentifierEndpointAddress",
                                                m_aManageParticipantIdentifierEndpointAddress)
                                       .toString ();
  }
}
