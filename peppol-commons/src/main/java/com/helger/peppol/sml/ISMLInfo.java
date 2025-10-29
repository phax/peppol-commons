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

import java.io.Serializable;
import java.net.URL;

import com.helger.annotation.Nonempty;
import com.helger.base.name.IHasDisplayName;
import com.helger.base.type.ITypedObject;

import jakarta.annotation.Nonnull;

/**
 * Specifies the different properties an SML implementation uses. A set of predefined SML
 * information can be found at {@link ESML} whereas a generic implementation can be found at
 * {@link SMLInfo}.
 *
 * @author Philip Helger
 */
public interface ISMLInfo extends ITypedObject <String>, IHasDisplayName, Serializable
{
  /**
   * @return The "shorthand" display name like "SML" or "SMK".
   */
  @Nonnull
  @Nonempty
  String getDisplayName ();

  /**
   * @return The DNS zone on which this SML is operating. Never <code>null</code>. It must be
   *         ensured that the value consists only of lower case characters!<br>
   *         Example: <code>sml.peppolcentral.org</code>
   */
  @Nonnull
  @Nonempty
  String getDNSZone ();

  /**
   * @return The DNS sub zone name that is used for SMP publishers. This is done by prepending
   *         {@link CSMLDefault#DNS_PUBLISHER_SUBZONE} to the DNS zone name - never starts with a
   *         dot! May not be <code>null</code>. This is only needed when the Peppol way of
   *         registration is used.
   * @see #getDNSZone()
   */
  @Nonnull
  @Nonempty
  default String getPublisherDNSZone ()
  {
    return CSMLDefault.DNS_PUBLISHER_SUBZONE + getDNSZone ();
  }

  /**
   * @return The service URL where the management application is running on including the host name.
   *         Never <code>null</code>. The difference to the host name is the eventually present
   *         context path. This path may <b>never</b> end with a slash.
   */
  @Nonnull
  @Nonempty
  String getManagementServiceURL ();

  /**
   * @return The relative sub path for managing the service metadata on the SML server. If not
   *         empty, it must start with a slash ('/'). May not be <code>null</code> but maybe empty.
   * @since 12.1.0
   */
  @Nonnull
  String getURLSuffixManageSMP ();

  /**
   * @return The service endpoint URL to manage SMP meta data. Never <code>null</code>. This is
   *         usually the URL corresponding to <code>
   *
  {@link #getManagementServiceURL()} + {@link #getURLSuffixManageSMP()}</code>
   */
  @Nonnull
  URL getManageServiceMetaDataEndpointAddress ();

  /**
   * @return The relative sub path for managing the participants on the SML server. If not empty, it
   *         must start with a slash ('/'). May not be <code>null</code> but maybe empty.
   * @since 12.1.0
   */
  @Nonnull
  String getURLSuffixManageParticipant ();

  /**
   * @return The service endpoint URL to manage participant identifiers. Never <code>null</code>.
   *         This is usually the URL corresponding to <code>
   *
  {@link #getManagementServiceURL()} + {@link #getURLSuffixManageParticipant()}</code>
   */
  @Nonnull
  URL getManageParticipantIdentifierEndpointAddress ();

  /**
   * @return <code>true</code> if this SML requires a client certificate for access,
   *         <code>false</code> otherwise. Both PEPPOL production SML and SMK require a client
   *         certificate. Only a locally running SML software may not require a client certificate.
   */
  boolean isClientCertificateRequired ();
}
