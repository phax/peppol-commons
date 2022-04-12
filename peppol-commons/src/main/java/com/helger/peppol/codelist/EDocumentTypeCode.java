/*
 * Copyright (C) 2015-2022 Philip Helger
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
package com.helger.peppol.codelist;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.DevelopersNote;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;
import com.helger.commons.name.IHasDisplayName;

/**
 * This file is generated from Genericode file DocumentTypeCode.gc. Do NOT edit!
 */
@Deprecated
@DevelopersNote ("Since 8.7.5 - build your own codelist")
public enum EDocumentTypeCode implements IHasID <String>, IHasDisplayName
{
  QUERY ("21", "Query"),
  RESPONSE_TO_QUERY ("22", "Response to query"),
  INQUIRY ("251", "Inquiry"),
  STATUS_INFORMATION ("23", "Status information"),
  ORDER ("220", "Order"),
  RESPONSE_TO_REGISTRATION ("301", "Response to registration"),
  COMMERCIAL_INVOICE ("380", "Commercial invoice"),
  RELATED_DOCUMENT ("916", "Related document"),
  CREDIT_NOTE_RELATED_TO_GOODS_OR_SERVICES ("81", "Credit note related to goods or services");

  private final String m_sID;
  private final String m_sDisplayName;

  EDocumentTypeCode (@Nonnull @Nonempty final String sID, @Nonnull final String sDisplayName)
  {
    m_sID = sID;
    m_sDisplayName = sDisplayName;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nonnull
  public String getDisplayName ()
  {
    return m_sDisplayName;
  }

  @Nullable
  public static EDocumentTypeCode getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EDocumentTypeCode.class, sID);
  }

  @Nullable
  public static String getDisplayNameFromIDOrNull (@Nullable final String sID)
  {
    final EDocumentTypeCode eValue = EDocumentTypeCode.getFromIDOrNull (sID);
    return ((eValue == null) ? null : eValue.getDisplayName ());
  }
}
