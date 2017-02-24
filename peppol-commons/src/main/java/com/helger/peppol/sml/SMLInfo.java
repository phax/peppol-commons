/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
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
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.id.factory.GlobalIDFactory;
import com.helger.commons.state.EChange;
import com.helger.commons.string.ToStringGenerator;
import com.helger.commons.type.ObjectType;

/**
 * Use instances of this class if you're not happy with the {@link ESML}
 * enumeration value but need an instance of {@link ISMLInfo}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@NotThreadSafe
public class SMLInfo implements ISMLInfo
{
  public static final ObjectType OT = new ObjectType ("sml.info");

  private final String m_sID;
  private String m_sDisplayName;
  private String m_sDNSZone;
  private String m_sManagementServiceURL;
  private boolean m_bClientCertificateRequired;
  // Cache for status
  private URL m_aManageServiceMetaDataEndpointAddress;
  private URL m_aManageParticipantIdentifierEndpointAddress;

  /**
   * @param sDisplayName
   *        The "shorthand" display name like "SML" or "SMK".
   * @param sDNSZone
   *        The DNS zone on which this SML is operating. May not be
   *        <code>null</code>. It must be ensured that the value consists only
   *        of lower case characters for comparability! Example:
   *        <code>sml.peppolcentral.org</code>
   * @param sManagementServiceURL
   *        The service URL where the management application is running on incl.
   *        the host name. May not be <code>null</code>. The difference to the
   *        host name is the eventually present context path.
   * @param bClientCertificateRequired
   *        <code>true</code> if this SML requires a client certificate for
   *        access, <code>false</code> otherwise. Both production SML and SMK
   *        require a client certificate. Only a locally running SML software
   *        may not require a client certificate.
   */
  public SMLInfo (@Nonnull @Nonempty final String sDisplayName,
                  @Nonnull @Nonempty final String sDNSZone,
                  @Nonnull @Nonempty final String sManagementServiceURL,
                  final boolean bClientCertificateRequired)
  {
    this (GlobalIDFactory.getNewPersistentStringID (),
          sDisplayName,
          sDNSZone,
          sManagementServiceURL,
          bClientCertificateRequired);
  }

  SMLInfo (@Nonnull @Nonempty final String sID,
           @Nonnull @Nonempty final String sDisplayName,
           @Nonnull @Nonempty final String sDNSZone,
           @Nonnull @Nonempty final String sManagementServiceURL,
           final boolean bClientCertificateRequired)
  {
    m_sID = ValueEnforcer.notEmpty (sID, "ID");
    setDisplayName (sDisplayName);
    setDNSZone (sDNSZone);
    setManagementServiceURL (sManagementServiceURL);
    setClientCertificateRequired (bClientCertificateRequired);
  }

  @Nonnull
  public ObjectType getObjectType ()
  {
    return OT;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nonnull
  @Nonempty
  public String getDisplayName ()
  {
    return m_sDisplayName;
  }

  @Nonnull
  public EChange setDisplayName (@Nonnull @Nonempty final String sDisplayName)
  {
    ValueEnforcer.notEmpty (sDisplayName, "DisplayName");
    if (sDisplayName.equals (m_sDisplayName))
      return EChange.UNCHANGED;
    m_sDisplayName = sDisplayName;
    return EChange.CHANGED;
  }

  @Nonnull
  @Nonempty
  public String getDNSZone ()
  {
    return m_sDNSZone;
  }

  @Nonnull
  public EChange setDNSZone (@Nonnull @Nonempty final String sDNSZone)
  {
    ValueEnforcer.notEmpty (sDNSZone, "DNSZone");
    if (sDNSZone.equals (m_sDNSZone))
      return EChange.UNCHANGED;
    m_sDNSZone = sDNSZone;
    return EChange.CHANGED;
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

  @Nonnull
  public EChange setManagementServiceURL (@Nonnull @Nonempty final String sManagementServiceURL)
  {
    ValueEnforcer.notEmpty (sManagementServiceURL, "ManagementServiceURL");
    if (sManagementServiceURL.equals (m_sManagementServiceURL))
      return EChange.UNCHANGED;

    // Management service without the trailing slash
    m_sManagementServiceURL = sManagementServiceURL.endsWith ("/") ? sManagementServiceURL.substring (0,
                                                                                                      sManagementServiceURL.length () -
                                                                                                         1)
                                                                   : sManagementServiceURL;
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
      throw new IllegalArgumentException ("Failed to convert to URL", ex);
    }
    return EChange.CHANGED;
  }

  public boolean isClientCertificateRequired ()
  {
    return m_bClientCertificateRequired;
  }

  @Nonnull
  public EChange setClientCertificateRequired (final boolean bRequiresClientCertificate)
  {
    if (bRequiresClientCertificate == m_bClientCertificateRequired)
      return EChange.UNCHANGED;
    m_bClientCertificateRequired = bRequiresClientCertificate;
    return EChange.CHANGED;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final SMLInfo rhs = (SMLInfo) o;
    return m_sDNSZone.equals (rhs.m_sDNSZone) &&
           m_sManagementServiceURL.equals (rhs.m_sManagementServiceURL) &&
           m_bClientCertificateRequired == rhs.m_bClientCertificateRequired;
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sDNSZone)
                                       .append (m_sManagementServiceURL)
                                       .append (m_bClientCertificateRequired)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("DisplayName", m_sDisplayName)
                                       .append ("DNSZone", m_sDNSZone)
                                       .append ("ManagementServiceURL", m_sManagementServiceURL)
                                       .append ("ManageServiceMetaDataEndpointAddress",
                                                m_aManageServiceMetaDataEndpointAddress)
                                       .append ("ManageParticipantIdentifierEndpointAddress",
                                                m_aManageParticipantIdentifierEndpointAddress)
                                       .append ("ClientCertificateRequired", m_bClientCertificateRequired)
                                       .getToString ();
  }

  @Nonnull
  public static SMLInfo create (@Nonnull final ISMLInfo aOther)
  {
    return new SMLInfo (aOther.getID (),
                        aOther.getDisplayName (),
                        aOther.getDNSZone (),
                        aOther.getManagementServiceURL (),
                        aOther.isClientCertificateRequired ());
  }
}
