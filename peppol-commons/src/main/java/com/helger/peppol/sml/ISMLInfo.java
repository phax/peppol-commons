/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Version: MPL 2.0/EUPL 1.2
 * -
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 * -
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.2 or - as soon they will be approved
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
 * -
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
import com.helger.commons.name.IHasDisplayName;
import com.helger.commons.type.ITypedObject;

/**
 * Specifies the different properties an SML implementation uses. A set of
 * predefined SML information can be found at {@link ESML} whereas a generic
 * implementation can be found at {@link SMLInfo}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
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
   * @return The DNS zone on which this SML is operating. Never
   *         <code>null</code>. It must be ensured that the value consists only
   *         of lower case characters!<br>
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
  default String getPublisherDNSZone ()
  {
    return CSMLDefault.DNS_PUBLISHER_SUBZONE + getDNSZone ();
  }

  /**
   * @return The service URL where the management application is running on
   *         including the host name. Never <code>null</code>. The difference to
   *         the host name is the eventually present context path. This path may
   *         <b>never</b> end with a slash.
   */
  @Nonnull
  @Nonempty
  String getManagementServiceURL ();

  /**
   * @return The service endpoint URL to manage SMP meta data. Never
   *         <code>null</code>. This is usually the URL corresponding to <code>
   *
  {@link #getManagementServiceURL()} + "/" +
  {@link CSMLDefault#MANAGEMENT_SERVICE_METADATA}</code>
   */
  @Nonnull
  URL getManageServiceMetaDataEndpointAddress ();

  /**
   * @return The service endpoint URL to manage participant identifiers. Never
   *         <code>null</code>. This is usually the URL corresponding to <code>
   *
  {@link #getManagementServiceURL()} + "/" +
  {@link CSMLDefault#MANAGEMENT_SERVICE_PARTICIPANTIDENTIFIER}</code>
   */
  @Nonnull
  URL getManageParticipantIdentifierEndpointAddress ();

  @Deprecated
  default boolean requiresClientCertificate ()
  {
    return isClientCertificateRequired ();
  }

  /**
   * @return <code>true</code> if this SML requires a client certificate for
   *         access, <code>false</code> otherwise. Both PEPPOL production SML
   *         and SMK require a client certificate. Only a locally running SML
   *         software may not require a client certificate.
   */
  boolean isClientCertificateRequired ();
}
