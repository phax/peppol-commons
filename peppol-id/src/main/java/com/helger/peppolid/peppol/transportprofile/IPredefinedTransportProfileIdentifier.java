/*
 * Copyright (C) 2015-2023 Philip Helger
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
import java.time.LocalDate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.version.Version;
import com.helger.peppolid.peppol.EPeppolCodeListItemState;

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
   * @return The internal code list version in which the identifier was added.
   *         Never <code>null</code>.
   * @since 8.7.1
   */
  @Nonnull
  Version getInitialRelease ();

  /**
   * @return <code>true</code> if this transport profile is deprecated and
   *         should no longer be used, <code>false</code> if not.
   */
  default boolean isDeprecated ()
  {
    return getState ().isDeprecated ();
  }

  /**
   * @return The state of the item. Never <code>null</code>.
   * @since 8.7.1
   */
  @Nonnull
  EPeppolCodeListItemState getState ();

  /**
   * Get the version since when this item is deprecated.
   *
   * @return <code>null</code> if this item is not deprecated.
   * @see #getState()
   * @see #isDeprecated()
   * @since 8.7.1
   */
  @Nullable
  Version getDeprecationRelease ();

  /**
   * @return <code>true</code> if this item has a removal date,
   *         <code>false</code> if not.
   * @since 8.7.1
   */
  default boolean hasRemovalDate ()
  {
    return getRemovalDate () != null;
  }

  /**
   * Get the date, when this particular entry will be removed. This may be set,
   * even if the state is not "removed". This date may be in the future.
   *
   * @return <code>null</code> if no removal date is scheduled yet.
   * @since 8.7.1
   */
  @Nullable
  LocalDate getRemovalDate ();
}
