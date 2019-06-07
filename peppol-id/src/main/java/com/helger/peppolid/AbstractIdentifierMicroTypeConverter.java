/**
 * Copyright (C) 2015-2019 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppolid;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.MicroElement;
import com.helger.xml.microdom.convert.IMicroTypeConverter;

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
