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
package com.helger.peppol.sml;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.string.StringParser;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.MicroElement;
import com.helger.xml.microdom.convert.IMicroTypeConverter;
import com.helger.xml.microdom.util.MicroHelper;

/**
 * Micro type converter for {@link ISMLInfo} objects
 *
 * @author Philip Helger
 */
public final class SMLInfoMicroTypeConverter implements IMicroTypeConverter <SMLInfo>
{
  private static final String ATTR_ID = "id";
  private static final String ATTR_DISPLAY_NAME = "displayname";
  private static final String ELEMENT_DNS_ZONE = "dnszone";
  private static final String ELEMENT_MANAGEMENT_SERVICE = "mgmtsvc";
  private static final String ATTR_REQUIRES_CLIENT_CERT = "clientcert";

  @Nonnull
  public IMicroElement convertToMicroElement (@Nonnull final SMLInfo aValue,
                                              @Nullable final String sNamespaceURI,
                                              @Nonnull final String sTagName)
  {
    final IMicroElement aElement = new MicroElement (sNamespaceURI, sTagName);
    aElement.setAttribute (ATTR_ID, aValue.getID ());
    aElement.setAttribute (ATTR_DISPLAY_NAME, aValue.getDisplayName ());
    aElement.appendElement (sNamespaceURI, ELEMENT_DNS_ZONE).appendText (aValue.getDNSZone ());
    aElement.appendElement (sNamespaceURI, ELEMENT_MANAGEMENT_SERVICE).appendText (aValue.getManagementServiceURL ());
    aElement.setAttribute (ATTR_REQUIRES_CLIENT_CERT, aValue.isClientCertificateRequired ());
    return aElement;
  }

  @Nonnull
  public SMLInfo convertToNative (@Nonnull final IMicroElement aElement)
  {
    final String sID = aElement.getAttributeValue (ATTR_ID);
    final String sDisplayName = aElement.getAttributeValue (ATTR_DISPLAY_NAME);
    final String sDNSZone = MicroHelper.getChildTextContent (aElement, ELEMENT_DNS_ZONE);
    final String sManagementServiceURL = MicroHelper.getChildTextContent (aElement, ELEMENT_MANAGEMENT_SERVICE);
    final boolean bRequiresClientCert = StringParser.parseBool (aElement.getAttributeValue (ATTR_REQUIRES_CLIENT_CERT),
                                                                SMLInfo.DEFAULT_CLIENT_CERTIFICATE_REQUIRED);
    return new SMLInfo (sID, sDisplayName, sDNSZone, sManagementServiceURL, bRequiresClientCert);
  }
}
