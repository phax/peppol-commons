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
package com.helger.peppolid.checks.validator;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.peppolid.IParticipantIdentifier;

/**
 * Split a participant identifier into the parts the validation works on. Every network defines its
 * own way of doing that, therefore this is a strategy and not a fixed rule.
 *
 * @author Philip Helger
 * @since 13.0.0
 */
@FunctionalInterface
public interface IParticipantIdentifierPartsProvider
{
  /**
   * Split the provided participant identifier.
   *
   * @param aParticipantID
   *        The participant identifier to split. May not be <code>null</code>.
   * @return <code>null</code> if this provider cannot handle the provided identifier - e.g. because
   *         it uses a different scheme. In that case no validation takes place.
   */
  @Nullable
  ParticipantIdentifierParts getParts (@NonNull IParticipantIdentifier aParticipantID);
}
