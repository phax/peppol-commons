/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.smp;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.MicroElement;
import com.helger.xml.microdom.convert.IMicroTypeConverter;

/**
 * Micro type converter for {@link ISMPTransportProfile} objects
 *
 * @author Philip Helger
 */
public class SMPTransportProfileMicroTypeConverter implements IMicroTypeConverter <SMPTransportProfile>
{
  private static final String ATTR_ID = "id";
  private static final String ATTR_NAME = "name";

  @Nonnull
  public IMicroElement convertToMicroElement (@Nonnull final SMPTransportProfile aValue,
                                              @Nullable final String sNamespaceURI,
                                              @Nonnull final String sTagName)
  {
    final IMicroElement aElement = new MicroElement (sNamespaceURI, sTagName);
    aElement.setAttribute (ATTR_ID, aValue.getID ());
    aElement.setAttribute (ATTR_NAME, aValue.getName ());
    return aElement;
  }

  @Nonnull
  public SMPTransportProfile convertToNative (@Nonnull final IMicroElement aElement)
  {
    final String sID = aElement.getAttributeValue (ATTR_ID);
    final String sName = aElement.getAttributeValue (ATTR_NAME);
    return new SMPTransportProfile (sID, sName);
  }
}
