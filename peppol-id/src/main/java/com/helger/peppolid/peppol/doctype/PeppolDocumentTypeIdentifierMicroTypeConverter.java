/*
 * Copyright (C) 2015-2025 Philip Helger
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
package com.helger.peppolid.peppol.doctype;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.peppolid.AbstractIdentifierMicroTypeConverter;
import com.helger.peppolid.factory.PeppolIdentifierFactory;

public final class PeppolDocumentTypeIdentifierMicroTypeConverter extends
                                                                  AbstractIdentifierMicroTypeConverter <PeppolDocumentTypeIdentifier>
{
  @Override
  @NonNull
  protected PeppolDocumentTypeIdentifier getAsNative (@Nullable final String sScheme, @NonNull final String sValue)
  {
    return new PeppolDocumentTypeIdentifier (PeppolIdentifierFactory.INSTANCE, sScheme, sValue);
  }
}
