/*
 * Copyright (C) 2015-2021 Philip Helger
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
