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
package com.helger.peppol.utils;

import javax.annotation.Nonnull;

/**
 * Small enum for setter method to identify whether an object is revoked or not.
 *
 * @author Philip Helger
 * @since 8.5.2
 */
public enum ERevoked implements IRevokedIndicator
{
  REVOKED,
  NOT_REVOKED;

  public boolean isRevoked ()
  {
    return this == REVOKED;
  }

  @Nonnull
  public static ERevoked valueOf (final boolean bEnabled)
  {
    return bEnabled ? REVOKED : NOT_REVOKED;
  }

  @Nonnull
  public static ERevoked valueOf (@Nonnull final IRevokedIndicator aEnabledIndicator)
  {
    return valueOf (aEnabledIndicator.isRevoked ());
  }
}
