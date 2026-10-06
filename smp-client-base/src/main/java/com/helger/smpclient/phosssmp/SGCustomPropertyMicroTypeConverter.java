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
package com.helger.smpclient.phosssmp;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.MicroElement;
import com.helger.xml.microdom.MicroQName;
import com.helger.xml.microdom.convert.IMicroTypeConverter;

/**
 * This class is internally used to convert {@link SGCustomProperty} from and to XML. Copied from
 * phoss SMP.
 *
 * @author Philip Helger
 * @since 13.2.0
 */
public final class SGCustomPropertyMicroTypeConverter implements IMicroTypeConverter <SGCustomProperty>
{
  private static final MicroQName ATTR_TYPE = new MicroQName ("type");
  private static final MicroQName ATTR_NAME = new MicroQName ("name");
  private static final MicroQName ATTR_VALUE = new MicroQName ("value");

  @NonNull
  public IMicroElement convertToMicroElement (@NonNull final SGCustomProperty aValue,
                                              @Nullable final String sNamespaceURI,
                                              @NonNull @Nonempty final String sTagName)
  {
    final IMicroElement aElement = new MicroElement (sNamespaceURI, sTagName);
    aElement.setAttribute (ATTR_TYPE, aValue.getType ().getID ());
    aElement.setAttribute (ATTR_NAME, aValue.getName ());
    aElement.setAttribute (ATTR_VALUE, aValue.getValue ());
    return aElement;
  }

  @NonNull
  public SGCustomProperty convertToNative (@NonNull final IMicroElement aElement)
  {
    final String sType = aElement.getAttributeValue (ATTR_TYPE);
    final ESGCustomPropertyType eType = ESGCustomPropertyType.getFromIDOrNull (sType);
    if (eType == null)
      throw new IllegalStateException ("Failed to resolve SG custom property type '" + sType + "'");

    final String sName = aElement.getAttributeValue (ATTR_NAME);
    final String sValue = aElement.getAttributeValue (ATTR_VALUE);
    return new SGCustomProperty (eType, sName, sValue);
  }
}
