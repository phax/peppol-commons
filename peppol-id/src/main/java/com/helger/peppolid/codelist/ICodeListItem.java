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
package com.helger.peppolid.codelist;

import org.jspecify.annotations.NonNull;

/**
 * Base interface for a single entry of a code list. Every code list entry has a state that
 * determines whether it may still be used or not.
 *
 * @author Philip Helger
 * @since 13.0.0
 */
public interface ICodeListItem
{
  /**
   * @return The state of this item. Never <code>null</code>.
   */
  @NonNull
  ECodeListItemState getState ();

  /**
   * @return <code>true</code> if this item is deprecated and should no longer be used.
   */
  default boolean isDeprecated ()
  {
    return getState ().isDeprecated ();
  }
}
