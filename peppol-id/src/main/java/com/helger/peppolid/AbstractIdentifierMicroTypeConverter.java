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

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.MicroElement;
import com.helger.xml.microdom.convert.IMicroTypeConverter;

public abstract class AbstractIdentifierMicroTypeConverter <T extends IIdentifier> implements IMicroTypeConverter <T>
{
  private static final String ATTR_SCHEME = "scheme";
  private static final String ATTR_VALUE = "value";

  @NonNull
  public final IMicroElement convertToMicroElement (@NonNull final T aValue,
                                                    @Nullable final String sNamespaceURI,
                                                    @NonNull @Nonempty final String sTagName)
  {
    final IMicroElement aElement = new MicroElement (sNamespaceURI, sTagName);
    if (aValue.hasScheme ())
      aElement.setAttribute (ATTR_SCHEME, aValue.getScheme ());
    aElement.setAttribute (ATTR_VALUE, aValue.getValue ());
    return aElement;
  }

  @NonNull
  protected abstract T getAsNative (@NonNull String sScheme, @NonNull String sValue);

  @NonNull
  public final T convertToNative (@NonNull final IMicroElement aElement)
  {
    final String sScheme = aElement.getAttributeValue (ATTR_SCHEME);
    final String sValue = aElement.getAttributeValue (ATTR_VALUE);
    return getAsNative (sScheme, sValue);
  }
}
