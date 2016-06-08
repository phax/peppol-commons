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

import java.io.Serializable;
import java.net.URL;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.name.IHasDisplayName;

/**
 * Specifies the different properties an SML implementation uses. A set of
 * predefined SML information can be found at {@link ESML} whereas a generic
 * implementation can be found at {@link SimpleSMLInfo}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public interface ISMLInfo extends IHasDisplayName, IHasID <String>, Serializable
{
  /**
   * @return The "shorthand" display name like "SML" or "SMK".
   */
  @Nonnull
  @Nonempty
  String getDisplayName ();

  /**
   * @return The DNS zone on which this SML is operating. Never
   *         <code>null</code>. It must be ensured that the value consists only
   *         from lower case characters!<br>
   *         Example: <code>sml.peppolcentral.org</code>
   */
  @Nonnull
  @Nonempty
  String getDNSZone ();

  /**
   * @return The DNS sub zone name that is used for SMP publishers. This is done
   *         by prepending {@link CSMLDefault#DNS_PUBLISHER_SUBZONE} to the DNS
   *         zone name - never starts with a dot! May not be <code>null</code>.
   * @see #getDNSZone()
   */
  @Nonnull
  @Nonempty
  default String getPublisherDNSName ()
  {
    return CSMLDefault.DNS_PUBLISHER_SUBZONE + getDNSZone ();
  }

  /**
   * @return The service URL where the management application is running on
   *         incl. the host name. Never <code>null</code>. The difference to the
   *         host name is the eventually present context path.
   */
  @Nonnull
  @Nonempty
  String getManagementServiceURL ();

  /**
   * @return The service endpoint URL to manage SMP meta data. Never
   *         <code>null</code>. This is usually the URL corresponding to <code>
   *
  {@link #getManagementServiceURL()} + "/" + CSMLDefault.MANAGEMENT_SERVICE_METADATA</code>
   */
  @Nonnull
  URL getManageServiceMetaDataEndpointAddress ();

  /**
   * @return The service endpoint URL to manage participant identifiers. Never
   *         <code>null</code>. This is usually the URL corresponding to <code>
   *
  {@link #getManagementServiceURL()} + "/" + CSMLDefault.MANAGEMENT_SERVICE_PARTICIPANTIDENTIFIER</code>
   */
  @Nonnull
  URL getManageParticipantIdentifierEndpointAddress ();

  /**
   * @return <code>true</code> if this SML requires a client certificate for
   *         access, <code>false</code> otherwise. Both production SML and SMK
   *         require a client certificate. Only a locally running SML software
   *         may not require a client certificate.
   */
  boolean requiresClientCertificate ();
}
