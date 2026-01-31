/*
 * Copyright (C) 2015-2026 Philip Helger
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

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.builder.IBuilder;
import com.helger.base.clone.ICloneable;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.hashcode.HashCodeGenerator;
import com.helger.base.id.factory.GlobalIDFactory;
import com.helger.base.string.StringHelper;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.base.type.ObjectType;

/**
 * Use instances of this class if you're not happy with the {@link ESML} enumeration value but need
 * an instance of {@link ISMLInfo}.
 *
 * @author Philip Helger
 */
@Immutable
public class SMLInfo implements ISMLInfo, ICloneable <SMLInfo>
{
  public static final String DEFAULT_SUFFIX_MANAGE_SMP = '/' + CSMLDefault.MANAGEMENT_SERVICE_METADATA;
  public static final String DEFAULT_SUFFIX_MANAGE_PARTICIPANT = '/' +
                                                                 CSMLDefault.MANAGEMENT_SERVICE_PARTICIPANTIDENTIFIER;
  public static final boolean DEFAULT_CLIENT_CERTIFICATE_REQUIRED = true;
  public static final ObjectType OT = new ObjectType ("sml.info");

  private final String m_sID;
  private final String m_sDisplayName;
  private final String m_sDNSZone;
  private final String m_sManagementServiceURL;
  private final String m_sURLSuffixManageSMP;
  private final String m_sURLSuffixManageParticipant;
  private final boolean m_bClientCertificateRequired;

  // Cache for efficiency
  private final URL m_aManageServiceMetaDataEndpointAddress;
  private final URL m_aManageParticipantIdentifierEndpointAddress;

  /**
   * Copy constructor.
   *
   * @param aOther
   *        The object to copy from. May not be <code>null</code>.
   * @deprecated Use the build instead
   */
  @Deprecated (forRemoval = true, since = "12.1.0")
  public SMLInfo (@NonNull final ISMLInfo aOther)
  {
    this (aOther.getID (),
          aOther.getDisplayName (),
          aOther.getDNSZone (),
          aOther.getManagementServiceURL (),
          aOther.isClientCertificateRequired ());
  }

  /**
   * @param sDisplayName
   *        The "shorthand" display name like "SML" or "SMK". May neither be <code>null</code> nor
   *        empty.
   * @param sDNSZone
   *        The DNS zone on which this SML is operating. May not be <code>null</code>. It must be
   *        ensured that the value consists only of lower case characters for comparability!
   *        Example: <code>sml.peppolcentral.org</code>
   * @param sManagementServiceURL
   *        The service URL where the management application is running on incl. the host name. May
   *        not be <code>null</code>. The difference to the host name is the eventually present
   *        context path.
   * @param bClientCertificateRequired
   *        <code>true</code> if this SML requires a client certificate for access,
   *        <code>false</code> otherwise. Both production SML and SMK require a client certificate.
   *        Only a locally running SML software may not require a client certificate.
   * @deprecated Use the builder instead
   */
  @Deprecated (forRemoval = true, since = "12.1.0")
  public SMLInfo (@NonNull @Nonempty final String sDisplayName,
                  @NonNull @Nonempty final String sDNSZone,
                  @NonNull @Nonempty final String sManagementServiceURL,
                  final boolean bClientCertificateRequired)
  {
    this (GlobalIDFactory.getNewPersistentStringID (),
          sDisplayName,
          sDNSZone,
          sManagementServiceURL,
          bClientCertificateRequired);
  }

  /**
   * @param sID
   *        Object ID. May neither be <code>null</code> nor empty.
   * @param sDisplayName
   *        The "shorthand" display name like "SML" or "SMK". May neither be <code>null</code> nor
   *        empty.
   * @param sDNSZone
   *        The DNS zone on which this SML is operating. May not be <code>null</code>. It must be
   *        ensured that the value consists only of lower case characters for comparability!
   *        Example: <code>sml.peppolcentral.org</code>
   * @param sManagementServiceURL
   *        The service URL where the management application is running on incl. the host name. May
   *        not be <code>null</code>. The difference to the host name is the eventually present
   *        context path.
   * @param bClientCertificateRequired
   *        <code>true</code> if this SML requires a client certificate for access,
   *        <code>false</code> otherwise. Both production SML and SMK require a client certificate.
   *        Only a locally running SML software may not require a client certificate.
   * @deprecated Use the builder instead
   */
  @Deprecated (forRemoval = true, since = "12.1.0")
  public SMLInfo (@NonNull @Nonempty final String sID,
                  @NonNull @Nonempty final String sDisplayName,
                  @NonNull @Nonempty final String sDNSZone,
                  @NonNull @Nonempty final String sManagementServiceURL,
                  final boolean bClientCertificateRequired)
  {
    this (sID,
          sDisplayName,
          sDNSZone,
          sManagementServiceURL,
          DEFAULT_SUFFIX_MANAGE_SMP,
          DEFAULT_SUFFIX_MANAGE_PARTICIPANT,
          bClientCertificateRequired);
  }

  /**
   * @param sID
   *        Object ID. May neither be <code>null</code> nor empty.
   * @param sDisplayName
   *        The "shorthand" display name like "SML" or "SMK". May neither be <code>null</code> nor
   *        empty.
   * @param sDNSZone
   *        The DNS zone on which this SML is operating. May not be <code>null</code>. It must be
   *        ensured that the value consists only of lower case characters for comparability!
   *        Example: <code>sml.peppolcentral.org</code>
   * @param sManagementServiceURL
   *        The service URL where the management application is running on incl. the host name. May
   *        not be <code>null</code>. The difference to the host name is the eventually present
   *        context path.
   * @param sURLSuffixManageSMP
   *        The sub-path to be used for managing SMP data in the SML. May not be <code>null</code>,
   *        may be empty. If not empty it must start with a slash ("/").
   * @param sURLSuffixManageParticipant
   *        The sub-path to be used for managing participant data in the SML. May not be
   *        <code>null</code>, may be empty. If not empty it must start with a slash ("/").
   * @param bClientCertificateRequired
   *        <code>true</code> if this SML requires a client certificate for access,
   *        <code>false</code> otherwise. Both production SML and SMK require a client certificate.
   *        Only a locally running SML software may not require a client certificate.
   * @throws IllegalArgumentException
   *         If the manage service URLs cannot be created
   */
  protected SMLInfo (@NonNull @Nonempty final String sID,
                     @NonNull @Nonempty final String sDisplayName,
                     @NonNull @Nonempty final String sDNSZone,
                     @NonNull @Nonempty final String sManagementServiceURL,
                     @NonNull final String sURLSuffixManageSMP,
                     @NonNull final String sURLSuffixManageParticipant,
                     final boolean bClientCertificateRequired)
  {
    ValueEnforcer.notEmpty (sID, "ID");
    ValueEnforcer.notEmpty (sDisplayName, "DisplayName");
    ValueEnforcer.notEmpty (sDNSZone, "DNSZone");
    ValueEnforcer.notEmpty (sManagementServiceURL, "ManagementServiceURL");
    ValueEnforcer.isFalse ( () -> sManagementServiceURL.endsWith ("/"),
                            "ManagementServiceURL must not end with a slash");
    ValueEnforcer.notNull (sURLSuffixManageSMP, "URLSuffixManageSMP");
    ValueEnforcer.notNull (sURLSuffixManageParticipant, "URLSuffixManageParticipant");

    m_sID = sID;
    m_sDisplayName = sDisplayName;
    m_sDNSZone = sDNSZone;
    m_sManagementServiceURL = sManagementServiceURL;
    m_sURLSuffixManageSMP = sURLSuffixManageSMP;
    m_sURLSuffixManageParticipant = sURLSuffixManageParticipant;
    m_bClientCertificateRequired = bClientCertificateRequired;
    try
    {
      // Create once
      m_aManageServiceMetaDataEndpointAddress = new URL (m_sManagementServiceURL + m_sURLSuffixManageSMP);
      m_aManageParticipantIdentifierEndpointAddress = new URL (m_sManagementServiceURL + m_sURLSuffixManageParticipant);
    }
    catch (final MalformedURLException ex)
    {
      throw new IllegalArgumentException ("Failed to convert to URL", ex);
    }
  }

  @NonNull
  public ObjectType getObjectType ()
  {
    return OT;
  }

  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @NonNull
  @Nonempty
  public String getDisplayName ()
  {
    return m_sDisplayName;
  }

  @NonNull
  @Nonempty
  public String getDNSZone ()
  {
    return m_sDNSZone;
  }

  @NonNull
  @Nonempty
  public String getManagementServiceURL ()
  {
    return m_sManagementServiceURL;
  }

  @NonNull
  public String getURLSuffixManageSMP ()
  {
    return m_sURLSuffixManageSMP;
  }

  @NonNull
  public URL getManageServiceMetaDataEndpointAddress ()
  {
    return m_aManageServiceMetaDataEndpointAddress;
  }

  @NonNull
  public String getURLSuffixManageParticipant ()
  {
    return m_sURLSuffixManageParticipant;
  }

  @NonNull
  public URL getManageParticipantIdentifierEndpointAddress ()
  {
    return m_aManageParticipantIdentifierEndpointAddress;
  }

  public boolean isClientCertificateRequired ()
  {
    return m_bClientCertificateRequired;
  }

  @NonNull
  @ReturnsMutableCopy
  public SMLInfo getClone ()
  {
    return builder (this).build ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final SMLInfo rhs = (SMLInfo) o;
    return m_sID.equals (rhs.m_sID) &&
           m_sDisplayName.equals (rhs.m_sDisplayName) &&
           m_sDNSZone.equals (rhs.m_sDNSZone) &&
           m_sManagementServiceURL.equals (rhs.m_sManagementServiceURL) &&
           m_sURLSuffixManageSMP.equals (rhs.m_sURLSuffixManageSMP) &&
           m_sURLSuffixManageParticipant.equals (rhs.m_sURLSuffixManageParticipant) &&
           m_bClientCertificateRequired == rhs.m_bClientCertificateRequired;
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sID)
                                       .append (m_sDisplayName)
                                       .append (m_sDNSZone)
                                       .append (m_sManagementServiceURL)
                                       .append (m_sURLSuffixManageSMP)
                                       .append (m_sURLSuffixManageParticipant)
                                       .append (m_bClientCertificateRequired)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("ID", m_sID)
                                       .append ("DisplayName", m_sDisplayName)
                                       .append ("DNSZone", m_sDNSZone)
                                       .append ("ManagementServiceURL", m_sManagementServiceURL)
                                       .append ("URLSuffixManageSMP", m_sURLSuffixManageSMP)
                                       .append ("URLSuffixManageParticipant", m_sURLSuffixManageParticipant)
                                       .append ("ClientCertificateRequired", m_bClientCertificateRequired)
                                       .getToString ();
  }

  /**
   * @return A new empty builder. Never <code>null</code>.
   * @since 12.1.0
   */
  @NonNull
  public static SMLInfoBuilder builder ()
  {
    return new SMLInfoBuilder ();
  }

  /**
   * @param aInfo
   *        The object to use as the basis for the builder. May not be <code>null</code>.
   * @return A new builder that is prefilled with the data of the parameter object. Never
   *         <code>null</code>.
   * @since 12.1.0
   */
  @NonNull
  public static SMLInfoBuilder builder (@NonNull final ISMLInfo aInfo)
  {
    return new SMLInfoBuilder (aInfo);
  }

  public static boolean isValidURLSuffix (@NonNull final String s)
  {
    return s.length () == 0 || (s.length () > 1 && s.startsWith ("/"));
  }

  /**
   * Builder class for {@link SMLInfo} objects.
   *
   * @author Philip Helger
   * @since 12.1.0
   */
  public static class SMLInfoBuilder implements IBuilder <SMLInfo>
  {
    private String m_sID;
    private String m_sDisplayName;
    private String m_sDNSZone;
    private String m_sManagementServiceURL;
    private String m_sURLSuffixManageSMP;
    private String m_sURLSuffixManageParticipant;
    private boolean m_bClientCertificateRequired;

    public SMLInfoBuilder ()
    {
      urlSuffixManageSMP (DEFAULT_SUFFIX_MANAGE_SMP);
      urlSuffixManageParticipant (DEFAULT_SUFFIX_MANAGE_PARTICIPANT);
    }

    public SMLInfoBuilder (@NonNull final ISMLInfo a)
    {
      ValueEnforcer.notNull (a, "SMLInfo");
      id (a.getID ()).displayName (a.getDisplayName ())
                     .dnsZone (a.getDNSZone ())
                     .managementServiceURL (a.getManagementServiceURL ())
                     .urlSuffixManageSMP (a.getURLSuffixManageSMP ())
                     .urlSuffixManageParticipant (a.getURLSuffixManageParticipant ())
                     .clientCertificateRequired (a.isClientCertificateRequired ());
    }

    @NonNull
    public SMLInfoBuilder idNew ()
    {
      return id (GlobalIDFactory.getNewStringID ());
    }

    @NonNull
    public SMLInfoBuilder idNewPersistent ()
    {
      return id (GlobalIDFactory.getNewPersistentStringID ());
    }

    @NonNull
    public SMLInfoBuilder id (@NonNull @Nonempty final String sID)
    {
      ValueEnforcer.notEmpty (sID, "ID");
      m_sID = sID;
      return this;
    }

    @NonNull
    public SMLInfoBuilder displayName (@NonNull @Nonempty final String sDisplayName)
    {
      ValueEnforcer.notEmpty (sDisplayName, "DisplayName");
      m_sDisplayName = sDisplayName;
      return this;
    }

    @NonNull
    public SMLInfoBuilder dnsZone (@NonNull @Nonempty final String sDNSZone)
    {
      ValueEnforcer.notEmpty (sDNSZone, "DNSZone");
      m_sDNSZone = sDNSZone;
      return this;
    }

    @NonNull
    public SMLInfoBuilder managementServiceURL (@NonNull @Nonempty final String sManagementServiceURL)
    {
      ValueEnforcer.notEmpty (sManagementServiceURL, "ManagementServiceURL");

      // Management service without the trailing slash
      m_sManagementServiceURL = StringHelper.trimEnd (sManagementServiceURL, '/');
      return this;
    }

    @NonNull
    public SMLInfoBuilder urlSuffixManageSMP (@NonNull final String s)
    {
      ValueEnforcer.notNull (s, "URLSuffixManageSMP");
      ValueEnforcer.isTrue ( () -> isValidURLSuffix (s), "URLSuffixManageSMP");

      m_sURLSuffixManageSMP = s;
      return this;
    }

    @NonNull
    public SMLInfoBuilder urlSuffixManageParticipant (@NonNull final String s)
    {
      ValueEnforcer.notNull (s, "URLSuffixManageParticipant");
      ValueEnforcer.isTrue ( () -> isValidURLSuffix (s), "URLSuffixManageParticipant");

      m_sURLSuffixManageParticipant = s;
      return this;
    }

    @NonNull
    public SMLInfoBuilder clientCertificateRequired (final boolean b)
    {
      m_bClientCertificateRequired = b;
      return this;
    }

    @NonNull
    public SMLInfo build ()
    {
      if (StringHelper.isEmpty (m_sID))
        throw new IllegalStateException ("The ID must be set");
      if (StringHelper.isEmpty (m_sDisplayName))
        throw new IllegalStateException ("The Display Name must be set");
      if (StringHelper.isEmpty (m_sDNSZone))
        throw new IllegalStateException ("The DNS Zone must be set");
      if (StringHelper.isEmpty (m_sManagementServiceURL))
        throw new IllegalStateException ("The Management Service URL must be set");
      if (m_sURLSuffixManageSMP == null)
        throw new IllegalStateException ("The Manage SMP URL Suffix must be set");
      if (m_sURLSuffixManageParticipant == null)
        throw new IllegalStateException ("The Manage Participant URL Suffix must be set");

      return new SMLInfo (m_sID,
                          m_sDisplayName,
                          m_sDNSZone,
                          m_sManagementServiceURL,
                          m_sURLSuffixManageSMP,
                          m_sURLSuffixManageParticipant,
                          m_bClientCertificateRequired);
    }
  }
}
