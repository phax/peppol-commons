/*
 * Copyright (C) 2015-2026 Philip Helger (www.helger.com)
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
package com.helger.peppolid.codelist;

import java.time.LocalDate;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.base.version.Version;

/**
 * Extended interface for a single entry of a code list that additionally knows in which release it
 * appeared and when it is scheduled for removal. Not every code list carries this information.
 *
 * @author Philip Helger
 * @since 13.0.0
 */
public interface ICodeListItemWithRelease extends ICodeListItem
{
  /**
   * @return The code list release in which this item was added. Never <code>null</code>.
   */
  @NonNull
  Version getInitialRelease ();

  /**
   * @return The code list release in which this item was deprecated. May be <code>null</code> if
   *         this item is not deprecated.
   */
  @Nullable
  Version getDeprecationRelease ();

  /**
   * @return The date on which this item is to be removed. May be <code>null</code> if no removal is
   *         scheduled.
   */
  @Nullable
  LocalDate getRemovalDate ();

  /**
   * @return <code>true</code> if a removal date is present.
   */
  default boolean hasRemovalDate ()
  {
    return getRemovalDate () != null;
  }
}
