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
package com.helger.edelivery.smp;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.style.IsSPIInterface;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.collection.commons.ICommonsList;

/**
 * SPI interface to be implemented by all networks that want to contribute their transport profiles
 * to the {@link SMPTransportProfileRegistry}. That way a new network does not need to modify an
 * existing enum to make its transport profiles known.
 *
 * @author Philip Helger
 * @since 13.0.0
 */
@IsSPIInterface
public interface ISMPTransportProfileProviderSPI
{
  /**
   * @return All transport profiles of a single network. May not be <code>null</code> but maybe
   *         empty.
   */
  @NonNull
  @ReturnsMutableCopy
  ICommonsList <ISMPTransportProfile> getAllTransportProfiles ();
}
