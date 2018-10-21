/**
 * Copyright (C) 2015-2018 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
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

  /**
   * @return <code>true</code> if this SML requires a client certificate for
   *         access, <code>false</code> otherwise. Both PEPPOL production SML
   *         and SMK require a client certificate. Only a locally running SML
   *         software may not require a client certificate.
   */
  boolean isClientCertificateRequired ();
}
