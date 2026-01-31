/*
 * Copyright (C) 2015-2026 Philip Helger
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

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.misc.ContainsSoftMigration;
import com.helger.base.string.StringHelper;
import com.helger.base.string.StringParser;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.MicroElement;
import com.helger.xml.microdom.convert.IMicroTypeConverter;
import com.helger.xml.microdom.util.MicroHelper;

/**
 * Micro type converter for {@link SMLInfo} objects
 *
 * @author Philip Helger
 */
public final class SMLInfoMicroTypeConverter implements IMicroTypeConverter <SMLInfo>
{
  private static final String ATTR_ID = "id";
  private static final String ATTR_DISPLAY_NAME = "displayname";
  private static final String ELEMENT_DNS_ZONE = "dnszone";
  private static final String ELEMENT_MANAGEMENT_SERVICE = "mgmtsvc";
  private static final String ELEMENT_URL_SUFFIX_MANAGE_SMP = "suffix-manage-smp";
  private static final String ELEMENT_URL_SUFFIX_MANAGE_PARTICIPANT = "suffix-manage-participant";
  private static final String ATTR_REQUIRES_CLIENT_CERT = "clientcert";

  @NonNull
  public IMicroElement convertToMicroElement (@NonNull final SMLInfo aValue,
                                              @Nullable final String sNamespaceURI,
                                              @NonNull final String sTagName)
  {
    final IMicroElement aElement = new MicroElement (sNamespaceURI, sTagName);
    aElement.setAttribute (ATTR_ID, aValue.getID ());
    aElement.setAttribute (ATTR_DISPLAY_NAME, aValue.getDisplayName ());
    aElement.addElementNS (sNamespaceURI, ELEMENT_DNS_ZONE).addText (aValue.getDNSZone ());
    aElement.addElementNS (sNamespaceURI, ELEMENT_MANAGEMENT_SERVICE).addText (aValue.getManagementServiceURL ());
    aElement.addElementNS (sNamespaceURI, ELEMENT_URL_SUFFIX_MANAGE_SMP).addText (aValue.getURLSuffixManageSMP ());
    aElement.addElementNS (sNamespaceURI, ELEMENT_URL_SUFFIX_MANAGE_PARTICIPANT)
            .addText (aValue.getURLSuffixManageParticipant ());
    aElement.setAttribute (ATTR_REQUIRES_CLIENT_CERT, aValue.isClientCertificateRequired ());
    return aElement;
  }

  @Nullable
  private static String _getChildTextContentOrEmpty (@NonNull final IMicroElement eParentElement,
                                                     @NonNull final String sChildElementName)
  {
    final IMicroElement eChildElement = eParentElement.getFirstChildElement (sChildElementName);
    return eChildElement != null ? StringHelper.getNotNull (eChildElement.getTextContent (), "") : null;
  }

  @NonNull
  @ContainsSoftMigration
  public SMLInfo convertToNative (@NonNull final IMicroElement aElement)
  {
    final String sID = aElement.getAttributeValue (ATTR_ID);
    final String sDisplayName = aElement.getAttributeValue (ATTR_DISPLAY_NAME);
    final String sDNSZone = MicroHelper.getChildTextContent (aElement, ELEMENT_DNS_ZONE);
    final String sManagementServiceURL = MicroHelper.getChildTextContent (aElement, ELEMENT_MANAGEMENT_SERVICE);

    // Make sure the empty string is handled properly
    String sURLSuffixSMP = _getChildTextContentOrEmpty (aElement, ELEMENT_URL_SUFFIX_MANAGE_SMP);
    if (sURLSuffixSMP == null)
      sURLSuffixSMP = SMLInfo.DEFAULT_SUFFIX_MANAGE_SMP;
    String sURLSuffixParticipant = _getChildTextContentOrEmpty (aElement, ELEMENT_URL_SUFFIX_MANAGE_PARTICIPANT);
    if (sURLSuffixParticipant == null)
      sURLSuffixParticipant = SMLInfo.DEFAULT_SUFFIX_MANAGE_PARTICIPANT;
    final boolean bRequiresClientCert = StringParser.parseBool (aElement.getAttributeValue (ATTR_REQUIRES_CLIENT_CERT),
                                                                SMLInfo.DEFAULT_CLIENT_CERTIFICATE_REQUIRED);
    return SMLInfo.builder ()
                  .id (sID)
                  .displayName (sDisplayName)
                  .dnsZone (sDNSZone)
                  .managementServiceURL (sManagementServiceURL)
                  .urlSuffixManageSMP (sURLSuffixSMP)
                  .urlSuffixManageParticipant (sURLSuffixParticipant)
                  .clientCertificateRequired (bRequiresClientCert)
                  .build ();
  }
}
