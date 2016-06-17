/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
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
public class SMLInfoMicroTypeConverter implements IMicroTypeConverter
{
  private static final String ATTR_ID = "id";
  private static final String ATTR_DISPLAY_NAME = "displayname";
  private static final String ELEMENT_DNS_ZONE = "dnszone";
  private static final String ELEMENT_MANAGEMENT_SERVICE = "mgmtsvc";
  private static final String ATTR_REQUIRES_CLIENT_CERT = "clientcert";

  @Nonnull
  public IMicroElement convertToMicroElement (@Nonnull final Object aObject,
                                              @Nullable final String sNamespaceURI,
                                              @Nonnull final String sTagName)
  {
    final ISMLInfo aValue = (ISMLInfo) aObject;
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
                                                                true);
    return new SMLInfo (sID, sDisplayName, sDNSZone, sManagementServiceURL, bRequiresClientCert);
  }
}
