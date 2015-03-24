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
 * This file is generated from Genericode file TaxExemptionReasonCode.gc. Do NOT edit!
 * 
 */
public enum ETaxExemptionReasonCode
    implements IHasID<String> , IHasDisplayName
{
    EXEMPTION_OF_EXPORTS_FROM_THE_COMMUNITY_AND_LIKE_TRANSACTIONS_AND_INTERNATIONAL_TRANSPORT_("AAA Exempt", "Exemption of exports from the Community and like transactions and international transport."),
    SPECIAL_EXEMPTIONS_LINKED_TO_INTERNATIONAL_GOODS_TRAFFIC_("AAB Exempt", "Special exemptions linked to international goods traffic."),
    EXEMPT_INTRA_COMMUNITY_SUPPLIES_OF_GOODS_("AAC Exempt", "Exempt Intra-Community supplies of goods."),
    REVERSE_CHARGE_INTRA_COMMUNITY_TRANSPORT_SERVICES_("AAE Reverse Charge", "Reverse Charge Intra-Community transport services."),
    EXEMPTION_UNDER_THE_SPECIAL_SCHEME_FOR_INVESTMENT_GOLD_("AAF Exempt", "Exemption under the special scheme for investment gold."),
    EXEMPT_WITHIN_THE_TERRITORY_OF_THE_COUNTRY_("AAG Exempt", "Exempt within the territory of the country."),
    ARTICLE_26A_OF_DIRECTIVE_77_388_EC("AAH Margin Scheme", "Article 26a of Directive 77/388/EC"),
    MARGIN_SCHEME_FOR_TRAVEL_AGENTS("AAI Margin Scheme", "Margin scheme for travel agents"),
    REVERSE_CHARGE_PROCEDURE_APPLYING_TO_SUPPLIES_OF_GOLD_("AAJ Reverse Charge", "Reverse charge procedure applying to supplies of gold."),
    REVERSE_CHARGE_PROCEDURE_SPECIAL_SCHEME_FOR_NON_VAT_REGISTERED_COMPANIES_WITHIN_AN_EC_COUNTRY_IN_CASE_OF_DOMESTIC_SUPPLY_OF_GOODS_AND_SERVICES_TO_A_VAT_REGISTERED_PURCHASER_IN_THAT_EC_COUNTRY_("AAK Reverse Charge", "Reverse charge procedure. Special scheme for non VAT registered companies within an EC\ncountry in case of domestic supply of goods and services to a VAT registered purchaser in\nthat EC country."),
    REVERSE_CHARGE_PROCEDURE_WHEN_GOODS_CEASE_TO_BE_COVERED_BY_WAREHOUSING_ARRANGEMENTS_("AAL Reverse Charge Exempt", "Reverse charge procedure when goods cease to be covered by warehousing arrangements."),
    INTRA_COMMUNITY_SUPPLY_OF_A_NEW_MEANS_OF_TRANSPORT_("AAM Exempt New Means of Transport", "Intra-Community supply of a new means of transport."),
    TRIANGULATION("AAN Exempt Triangulation", "Triangulation"),
    REVERSE_CHARGE_PROCEDURE_FOR_SERVICES_SUCH_AS_CONSULTANT_LAWYER_INFORMATION_ADB_AND_TRANSLATION_("AAO Reverse Charge", "Reverse charge procedure for services such as consultant, lawyer, information, ADB and translation.");
    private final String m_sID;
    private final String m_sDisplayName;

    private ETaxExemptionReasonCode(
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
    public static ETaxExemptionReasonCode getFromIDOrNull(
        @Nullable
        final String sID) {
        return EnumHelper.getFromIDOrNull(ETaxExemptionReasonCode.class, sID);
    }

    @Nullable
    public static String getDisplayNameFromIDOrNull(
        @Nullable
        final String sID) {
        final ETaxExemptionReasonCode eValue = ETaxExemptionReasonCode.getFromIDOrNull(sID);
        return ((eValue == null)?null:eValue.getDisplayName());
    }
}
