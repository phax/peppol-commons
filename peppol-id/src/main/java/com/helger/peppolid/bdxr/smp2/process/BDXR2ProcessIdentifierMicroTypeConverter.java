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
package com.helger.peppolid.bdxr.smp2.process;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.peppolid.AbstractIdentifierMicroTypeConverter;

public final class BDXR2ProcessIdentifierMicroTypeConverter extends AbstractIdentifierMicroTypeConverter <BDXR2ProcessIdentifier>
{
  @Override
  @Nonnull
  protected BDXR2ProcessIdentifier getAsNative (@Nullable final String sScheme, @Nonnull final String sValue)
  {
    return new BDXR2ProcessIdentifier (sScheme, sValue);
  }
}
