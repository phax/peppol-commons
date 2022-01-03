/*
 * Copyright (C) 2015-2022 Philip Helger
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
package com.helger.peppolid.peppol;

import com.helger.peppolid.IIdentifier;

/**
 * Base interface for all PEPPOL identifiers.
 *
 * @author Philip Helger
 */
public interface IPeppolIdentifier extends IIdentifier
{
  /**
   * Check if this identifier uses the default scheme. E.g. for participant
   * identifiers this would be <code>true</code> if the scheme equals
   * <code>iso6523-actorid-upis</code>.
   *
   * @return <code>true</code> if is the default scheme, <code>false</code>
   *         otherwise.
   */
  boolean hasDefaultScheme ();
}
