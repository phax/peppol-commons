/**
 * Copyright (C) 2015-2018 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.codelist;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;
import com.helger.commons.name.IHasDisplayName;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;


/**
 * This file is generated from Genericode file TaxCategoryID.gc. Do NOT edit!
 */
public enum ETaxCategoryID
    implements IHasID<String> , IHasDisplayName
{
    MIXED_TAX_RATE("A", "Mixed tax rate"),
    LOWER_RATE("AA", "Lower rate"),
    EXEMPT_FOR_RESALE("AB", "Exempt for resale"),
    VALUE_ADDED_TAX_VAT_NOT_NOW_DUE_FOR_PAYMENT("AC", "Value Added Tax (VAT) not now due for payment"),
    VALUE_ADDED_TAX_VAT_DUE_FROM_A_PREVIOUS_INVOICE("AD", "Value Added Tax (VAT) due from a previous invoice"),
    VAT_REVERSE_CHARGE("AE", "VAT Reverse Charge"),
    TRANSFERRED_VAT_("B", "Transferred (VAT)"),
    DUTY_PAID_BY_SUPPLIER("C", "Duty paid by supplier"),
    EXEMPT_FROM_TAX("E", "Exempt from tax"),
    FREE_EXPORT_ITEM_TAX_NOT_CHARGED("G", "Free export item, tax not charged"),
    HIGHER_RATE("H", "Higher rate"),
    SERVICES_OUTSIDE_SCOPE_OF_TAX("O", "Services outside scope of tax"),
    STANDARD_RATE("S", "Standard rate"),
    ZERO_RATED_GOODS("Z", "Zero rated goods");
    private final String m_sID;
    private final String m_sDisplayName;

    private ETaxCategoryID(
        @Nonnull
        @Nonempty
        final String sID,
        @Nonnull
        final String sDisplayName) {
        m_sID = sID;
        m_sDisplayName = sDisplayName;
    }

    @Nonnull
    @Nonempty
    public String getID() {
        return m_sID;
    }

    @Nonnull
    public String getDisplayName() {
        return m_sDisplayName;
    }

    @Nullable
    public static ETaxCategoryID getFromIDOrNull(
        @Nullable
        final String sID) {
        return EnumHelper.getFromIDOrNull(ETaxCategoryID.class, sID);
    }

    @Nullable
    public static String getDisplayNameFromIDOrNull(
        @Nullable
        final String sID) {
        final ETaxCategoryID eValue = ETaxCategoryID.getFromIDOrNull(sID);
        return ((eValue == null)?null:eValue.getDisplayName());
    }
}
