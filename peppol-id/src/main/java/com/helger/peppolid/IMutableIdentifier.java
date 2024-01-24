/*
 * Copyright (C) 2015-2024 Philip Helger
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
package com.helger.peppolid;

import javax.annotation.Nonnull;

/**
 * The writable version of an identifier interface.
 * 
 * @author Philip Helger
 */
public interface IMutableIdentifier extends IIdentifier
{
  /**
   * Set the identifier scheme.
   * 
   * @param sScheme
   *        The scheme to be set. May not be <code>null</code>.
   */
  void setScheme (@Nonnull String sScheme);

  /**
   * Set the identifier value.
   * 
   * @param sValue
   *        The value to be set. May not be <code>null</code>.
   */
  void setValue (@Nonnull String sValue);
}
