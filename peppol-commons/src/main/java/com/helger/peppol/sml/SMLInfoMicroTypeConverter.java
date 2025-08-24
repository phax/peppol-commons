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
package com.helger.peppol.sml;

import com.helger.base.string.StringParser;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.MicroElement;
import com.helger.xml.microdom.convert.IMicroTypeConverter;
import com.helger.xml.microdom.util.MicroHelper;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

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
    aElement.addElementNS (sNamespaceURI, ELEMENT_DNS_ZONE).addText (aValue.getDNSZone ());
    aElement.addElementNS (sNamespaceURI, ELEMENT_MANAGEMENT_SERVICE).addText (aValue.getManagementServiceURL ());
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
