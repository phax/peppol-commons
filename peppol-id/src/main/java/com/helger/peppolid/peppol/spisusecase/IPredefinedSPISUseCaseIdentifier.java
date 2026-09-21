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
package com.helger.peppolid.peppol.spisusecase;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.Nonempty;
import com.helger.peppolid.codelist.ICodeListItemWithRelease;

/**
 * Base interface for predefined SPIS Use Case identifiers.
 *
 * @author Philip Helger
 * @since 11.0.3
 */
public interface IPredefinedSPISUseCaseIdentifier extends ICodeListItemWithRelease
{
  /**
   * @return The unique ID of this SPIS Use case profile. This identifier is the one used in SMP
   *         endpoints.
   */
  @NonNull
  @Nonempty
  String getUseCaseID ();
}
