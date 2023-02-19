/*
 * Copyright (C) 2015-2023 Philip Helger
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
package com.helger.peppol.smp;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.ContainsSoftMigration;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.IMicroQName;
import com.helger.xml.microdom.MicroElement;
import com.helger.xml.microdom.MicroQName;
import com.helger.xml.microdom.convert.IMicroTypeConverter;

/**
 * Micro type converter for {@link ISMPTransportProfile} objects
 *
 * @author Philip Helger
 */
public class SMPTransportProfileMicroTypeConverter implements IMicroTypeConverter <SMPTransportProfile>
{
  private static final IMicroQName ATTR_ID = new MicroQName ("id");
  private static final IMicroQName ATTR_NAME = new MicroQName ("name");
  @Deprecated (forRemoval = false)
  private static final IMicroQName ATTR_DEPRECATED = new MicroQName ("deprecated");
  private static final IMicroQName ATTR_STATE = new MicroQName ("state");

  @Nonnull
  public IMicroElement convertToMicroElement (@Nonnull final SMPTransportProfile aValue,
                                              @Nullable final String sNamespaceURI,
                                              @Nonnull final String sTagName)
  {
    final IMicroElement aElement = new MicroElement (sNamespaceURI, sTagName);
    aElement.setAttribute (ATTR_ID, aValue.getID ());
    aElement.setAttribute (ATTR_NAME, aValue.getName ());
    aElement.setAttribute (ATTR_STATE, aValue.getState ().getID ());
    return aElement;
  }

  @Nonnull
  @ContainsSoftMigration
  public SMPTransportProfile convertToNative (@Nonnull final IMicroElement aElement)
  {
    final String sID = aElement.getAttributeValue (ATTR_ID);
    final String sName = aElement.getAttributeValue (ATTR_NAME);
    final String sStateID = aElement.getAttributeValue (ATTR_STATE);
    ESMPTransportProfileState eState = ESMPTransportProfileState.getFromIDOrNull (sStateID);
    if (eState == null)
    {
      // Fallback to old data
      final boolean bIsDeprecated = aElement.getAttributeValueAsBool (ATTR_DEPRECATED, false);
      eState = SMPTransportProfile.internalGetDeprecatedState (bIsDeprecated);
    }
    return new SMPTransportProfile (sID, sName, eState);
  }
}
