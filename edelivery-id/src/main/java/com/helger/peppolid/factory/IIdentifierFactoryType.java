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
package com.helger.peppolid.factory;

import org.jspecify.annotations.NonNull;

import com.helger.base.id.IHasID;
import com.helger.base.name.IHasDisplayName;

/**
 * A named identifier factory, so that an application can let the user choose the identifier rules
 * to be applied. Implementations are collected in {@link IdentifierFactoryTypeRegistry}.
 *
 * @author Philip Helger
 * @since 13.0.0
 */
public interface IIdentifierFactoryType extends IHasID <String>, IHasDisplayName
{
  /**
   * @return The identifier factory to be used. Never <code>null</code>.
   */
  @NonNull
  IIdentifierFactory getIdentifierFactory ();
}
