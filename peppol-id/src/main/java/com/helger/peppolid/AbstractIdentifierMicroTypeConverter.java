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
package com.helger.peppolid;

import com.helger.annotation.Nonempty;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.MicroElement;
import com.helger.xml.microdom.convert.IMicroTypeConverter;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public abstract class AbstractIdentifierMicroTypeConverter <T extends IIdentifier> implements IMicroTypeConverter <T>
{
  private static final String ATTR_SCHEME = "scheme";
  private static final String ATTR_VALUE = "value";

  @Nonnull
  public final IMicroElement convertToMicroElement (@Nonnull final T aValue,
                                                    @Nullable final String sNamespaceURI,
                                                    @Nonnull @Nonempty final String sTagName)
  {
    final IMicroElement aElement = new MicroElement (sNamespaceURI, sTagName);
    if (aValue.hasScheme ())
      aElement.setAttribute (ATTR_SCHEME, aValue.getScheme ());
    aElement.setAttribute (ATTR_VALUE, aValue.getValue ());
    return aElement;
  }

  @Nonnull
  protected abstract T getAsNative (@Nonnull String sScheme, @Nonnull String sValue);

  @Nonnull
  public final T convertToNative (@Nonnull final IMicroElement aElement)
  {
    final String sScheme = aElement.getAttributeValue (ATTR_SCHEME);
    final String sValue = aElement.getAttributeValue (ATTR_VALUE);
    return getAsNative (sScheme, sValue);
  }
}
