/**
 * Copyright (C) 2015-2019 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppolid.peppol.transportprofile;

import java.io.Serializable;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.version.Version;

/**
 * Base interface for predefined transport profile identifiers.
 *
 * @author Philip Helger
 * @since 7.0.0
 */
public interface IPredefinedTransportProfileIdentifier extends Serializable
{
  /**
   * @return The underlying protocol of the transport profile. May neither be
   *         <code>null</code> nor empty.
   */
  @Nonnull
  @Nonempty
  String getProtocol ();

  /**
   * @return The version string of the profile. Such as "1.0" or "2.0";
   */
  @Nonnull
  @Nonempty
  String getProfileVersion ();

  /**
   * @return The unique ID of this transport profile. This identifier is the one
   *         used in SMP endpoints.
   */
  @Nonnull
  @Nonempty
  String getProfileID ();

  /**
   * @return The codelist version in which it was introduced. Never
   *         <code>null</code>.
   */
  @Nonnull
  Version getSince ();

  /**
   * @return <code>true</code> if this transport profile is deprecated and
   *         should no longer be used, <code>false</code> if not.
   */
  boolean isDeprecated ();
}
