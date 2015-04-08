/**
 * Copyright (C) 2015 Philip Helger (www.helger.com)
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

package com.helger.peppol.codelist;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.helger.commons.annotations.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;
import com.helger.commons.name.IHasDisplayName;


/**
 * This file is generated from Genericode file TaxSchemeID.gc. Do NOT edit!
 * 
 */
public enum ETaxSchemeID
    implements IHasID<String> , IHasDisplayName
{
    PETROLEUM_TAX("AAA", "Petroleum tax"),
    PROVISIONAL_COUNTERVAILING_DUTY_CASH("AAB", "Provisional countervailing duty cash"),
    PROVISIONAL_COUNTERVAILING_DUTY_BOND("AAC", "Provisional countervailing duty bond"),
    TOBACCO_TAX("AAD", "Tobacco tax"),
    ENERGY_FEE("AAE", "Energy fee"),
    COFFEE_TAX("AAF", "Coffee tax"),
    HARMONISED_SALES_TAX_CANADIAN("AAG", "Harmonised sales tax, Canadian"),
    QUEBEC_SALES_TAX("AAH", "Quebec sales tax"),
    CANADIAN_PROVINCIAL_SALES_TAX("AAI", "Canadian provincial sales tax"),
    TAX_ON_REPLACEMENT_PART("AAJ", "Tax on replacement part"),
    MINERAL_OIL_TAX("AAK", "Mineral oil tax"),
    SPECIAL_TAX("AAL", "Special tax"),
    ANTI_DUMPING_DUTY("ADD", "Anti-dumping duty"),
    STAMP_DUTY_IMPOSTA_DI_BOLLO_("BOL", "Stamp duty (Imposta di Bollo)"),
    AGRICULTURAL_LEVY("CAP", "Agricultural levy"),
    CAR_TAX("CAR", "Car tax"),
    PAPER_CONSORTIUM_TAX_ITALY_("COC", "Paper consortium tax (Italy)"),
    COMMODITY_SPECIFIC_TAX("CST", "Commodity specific tax"),
    CUSTOMS_DUTY("CUD", "Customs duty"),
    COUNTERVAILING_DUTY("CVD", "Countervailing duty"),
    ENVIRONMENTAL_TAX("ENV", "Environmental tax"),
    EXCISE_DUTY("EXC", "Excise duty"),
    AGRICULTURAL_EXPORT_REBATE("EXP", "Agricultural export rebate"),
    FEDERAL_EXCISE_TAX("FET", "Federal excise tax"),
    FREE("FRE", "Free"),
    GENERAL_CONSTRUCTION_TAX("GCN", "General construction tax"),
    GOODS_AND_SERVICES_TAX("GST", "Goods and services tax"),
    ILLUMINANTS_TAX("ILL", "Illuminants tax"),
    IMPORT_TAX("IMP", "Import tax"),
    INDIVIDUAL_TAX("IND", "Individual tax"),
    BUSINESS_LICENSE_FEE("LAC", "Business license fee"),
    LOCAL_CONSTRUCTION_TAX("LCN", "Local construction tax"),
    LIGHT_DUES_PAYABLE("LDP", "Light dues payable"),
    LOCAL_SALES_TAX("LOC", "Local sales tax"),
    LUST_TAX("LST", "Lust tax"),
    MONETARY_COMPENSATORY_AMOUNT("MCA", "Monetary compensatory amount"),
    MISCELLANEOUS_CASH_DEPOSIT("MCD", "Miscellaneous cash deposit"),
    OTHER_TAXES("OTH", "Other taxes"),
    PROVISIONAL_DUTY_BOND("PDB", "Provisional duty bond"),
    PROVISIONAL_DUTY_CASH("PDC", "Provisional duty cash"),
    PREFERENCE_DUTY("PRF", "Preference duty"),
    SPECIAL_CONSTRUCTION_TAX("SCN", "Special construction tax"),
    SHIFTED_SOCIAL_SECURITIES("SSS", "Shifted social securities"),
    STATE_PROVINCIAL_SALES_TAX("STT", "State/provincial sales tax"),
    SUSPENDED_DUTY("SUP", "Suspended duty"),
    SURTAX("SUR", "Surtax"),
    SHIFTED_WAGE_TAX("SWT", "Shifted wage tax"),
    ALCOHOL_MARK_TAX("TAC", "Alcohol mark tax"),
    TOTAL("TOT", "Total"),
    TURNOVER_TAX("TOX", "Turnover tax"),
    TONNAGE_TAXES("TTA", "Tonnage taxes"),
    VALUATION_DEPOSIT("VAD", "Valuation deposit"),
    VALUE_ADDED_TAX("VAT", "Value added tax");
    private final String m_sID;
    private final String m_sDisplayName;

    private ETaxSchemeID(
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
    public static ETaxSchemeID getFromIDOrNull(
        @Nullable
        final String sID) {
        return EnumHelper.getFromIDOrNull(ETaxSchemeID.class, sID);
    }

    @Nullable
    public static String getDisplayNameFromIDOrNull(
        @Nullable
        final String sID) {
        final ETaxSchemeID eValue = ETaxSchemeID.getFromIDOrNull(sID);
        return ((eValue == null)?null:eValue.getDisplayName());
    }
}
