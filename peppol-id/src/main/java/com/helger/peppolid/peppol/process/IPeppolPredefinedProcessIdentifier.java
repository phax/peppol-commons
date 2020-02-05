/**
 * Copyright (C) 2015-2020 Philip Helger
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

import javax.annotation.Nonnull;

import com.helger.commons.version.Version;
import com.helger.peppolid.IProcessIdentifier;
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
   * @return The ID of the corresponding PEPPOL BIS.
   */
  @Nonnull
  String getBISID ();

  /**
   * @return The {@link PeppolProcessIdentifier} version of this predefined
   *         process identifier.
   */
  @Nonnull
  PeppolProcessIdentifier getAsProcessIdentifier ();

  /**
   * @return The internal code list version in which the identifier was added.
   *         Never <code>null</code>.
   */
  @Nonnull
  Version getSince ();

  /**
   * @return <code>true</code> if this identifier is deprecated and should no
   *         longer be used, <code>false</code> if not.
   * @since 7.0.0
   */
  boolean isDeprecated ();
}
