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
 * This file is generated from Genericode file TaxCategoryID.gc. Do NOT edit!
 */
@Deprecated
@DevelopersNote ("Since 8.7.5 - build your own codelist")
public enum ETaxCategoryID implements IHasID <String>, IHasDisplayName
{
  MIXED_TAX_RATE ("A", "Mixed tax rate"),
  LOWER_RATE ("AA", "Lower rate"),
  EXEMPT_FOR_RESALE ("AB", "Exempt for resale"),
  VALUE_ADDED_TAX_VAT_NOT_NOW_DUE_FOR_PAYMENT ("AC", "Value Added Tax (VAT) not now due for payment"),
  VALUE_ADDED_TAX_VAT_DUE_FROM_A_PREVIOUS_INVOICE ("AD", "Value Added Tax (VAT) due from a previous invoice"),
  VAT_REVERSE_CHARGE ("AE", "VAT Reverse Charge"),
  TRANSFERRED_VAT_ ("B", "Transferred (VAT)"),
  DUTY_PAID_BY_SUPPLIER ("C", "Duty paid by supplier"),
  EXEMPT_FROM_TAX ("E", "Exempt from tax"),
  FREE_EXPORT_ITEM_TAX_NOT_CHARGED ("G", "Free export item, tax not charged"),
  HIGHER_RATE ("H", "Higher rate"),
  SERVICES_OUTSIDE_SCOPE_OF_TAX ("O", "Services outside scope of tax"),
  STANDARD_RATE ("S", "Standard rate"),
  ZERO_RATED_GOODS ("Z", "Zero rated goods");

  private final String m_sID;
  private final String m_sDisplayName;

  ETaxCategoryID (@Nonnull @Nonempty final String sID, @Nonnull final String sDisplayName)
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
  public static ETaxCategoryID getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (ETaxCategoryID.class, sID);
  }

  @Nullable
  public static String getDisplayNameFromIDOrNull (@Nullable final String sID)
  {
    final ETaxCategoryID eValue = ETaxCategoryID.getFromIDOrNull (sID);
    return ((eValue == null) ? null : eValue.getDisplayName ());
  }
}
