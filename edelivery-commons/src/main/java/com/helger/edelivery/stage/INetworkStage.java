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
package com.helger.edelivery.stage;

import com.helger.base.id.IHasID;

/**
 * Base interface for a single stage of a four corner network - like "production", "test" or "pilot".
 * Every network defines its own set of stages; the only things they have in common is a unique ID
 * and the information, whether the stage is the productive one or not.
 *
 * @author Philip Helger
 * @since 13.0.0
 */
public interface INetworkStage extends IHasID <String>
{
  /**
   * @return <code>true</code> if this is the productive stage of the network, <code>false</code> if
   *         it is a test, pilot or demo stage.
   */
  boolean isProduction ();

  /**
   * @return <code>true</code> if this is not the productive stage of the network. The inverse of
   *         {@link #isProduction()}.
   */
  default boolean isNonProduction ()
  {
    return !isProduction ();
  }
}
