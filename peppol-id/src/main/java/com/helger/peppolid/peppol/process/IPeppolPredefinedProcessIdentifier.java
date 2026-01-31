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
package com.helger.peppolid.peppol.process;

import org.jspecify.annotations.NonNull;

import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.peppol.EPeppolCodeListItemState;
import com.helger.peppolid.peppol.IPeppolIdentifier;

/**
 * Base interface for predefined process identifiers.
 *
 * @author Philip Helger
 */
public interface IPeppolPredefinedProcessIdentifier extends IProcessIdentifier, IPeppolIdentifier
{
  default boolean hasDefaultScheme ()
  {
    return true;
  }

  /**
   * @return The {@link PeppolProcessIdentifier} version of this predefined
   *         process identifier.
   */
  @NonNull
  PeppolProcessIdentifier getAsProcessIdentifier ();

  /**
   * @return <code>true</code> if this identifier is deprecated and should no
   *         longer be used, <code>false</code> if not.
   * @since 7.0.0
   */
  default boolean isDeprecated ()
  {
    return getState ().isDeprecated ();
  }

  /**
   * @return The state of the item. Never <code>null</code>.
   * @since 8.7.1
   */
  @NonNull
  EPeppolCodeListItemState getState ();
}
