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
package com.helger.peppolid.peppol.spisusecase;

import java.time.LocalDate;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.base.version.Version;
import com.helger.peppolid.peppol.EPeppolCodeListItemState;

/**
 * Base interface for predefined SPIS Use Case identifiers.
 *
 * @author Philip Helger
 * @since 11.0.3
 */
public interface IPredefinedSPISUseCaseIdentifier
{
  /**
   * @return The unique ID of this SPIS Use case profile. This identifier is the one used in SMP
   *         endpoints.
   */
  @NonNull
  @Nonempty
  String getUseCaseID ();

  /**
   * @return The internal code list version in which the identifier was added. Never
   *         <code>null</code>.
   */
  @NonNull
  Version getInitialRelease ();

  /**
   * @return <code>true</code> if this transport profile is deprecated and should no longer be used,
   *         <code>false</code> if not.
   */
  default boolean isDeprecated ()
  {
    return getState ().isDeprecated ();
  }

  /**
   * @return The state of the item. Never <code>null</code>.
   */
  @NonNull
  EPeppolCodeListItemState getState ();

  /**
   * Get the version since when this item is deprecated.
   *
   * @return <code>null</code> if this item is not deprecated.
   * @see #getState()
   * @see #isDeprecated()
   */
  @Nullable
  Version getDeprecationRelease ();

  /**
   * @return <code>true</code> if this item has a removal date, <code>false</code> if not.
   */
  default boolean hasRemovalDate ()
  {
    return getRemovalDate () != null;
  }

  /**
   * Get the date, when this particular entry will be removed. This may be set, even if the state is
   * not "removed". This date may be in the future.
   *
   * @return <code>null</code> if no removal date is scheduled yet.
   */
  @Nullable
  LocalDate getRemovalDate ();
}
