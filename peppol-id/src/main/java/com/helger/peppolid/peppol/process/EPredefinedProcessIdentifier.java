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
package com.helger.peppolid.peppol.process;

import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.annotation.Nonempty;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.peppol.EPeppolCodeListItemState;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;


/**
 * This file was automatically generated.
 * Do NOT edit!
 */
@CodingStyleguideUnaware
public enum EPredefinedProcessIdentifier
    implements IPeppolPredefinedProcessIdentifier
{

    /**
     * ID: <code>cenbii-procid-ubl::none</code><br>
     * 
     * @deprecated This item should not be used to issue new identifiers!
     */
    @Deprecated
    none("cenbii-procid-ubl", "none", EPeppolCodeListItemState.DEPRECATED),

    /**
     * ID: <code>cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii01:ver1.0</code><br>
     * Same as {@link #BIS1A_V1}
     * 
     * @deprecated This item should not be used to issue new identifiers!
     */
    @Deprecated
    urn_www_cenbii_eu_profile_bii01_ver1_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii01:ver1.0", EPeppolCodeListItemState.DEPRECATED),

    /**
     * ID: <code>cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii01:ver2.0</code><br>
     * Same as {@link #BIS1A_V2}
     */
    urn_www_cenbii_eu_profile_bii01_ver2_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii01:ver2.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii03:ver1.0</code><br>
     * Same as {@link #BIS3A_V1}
     * 
     * @deprecated This item should not be used to issue new identifiers!
     */
    @Deprecated
    urn_www_cenbii_eu_profile_bii03_ver1_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii03:ver1.0", EPeppolCodeListItemState.DEPRECATED),

    /**
     * ID: <code>cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii03:ver2.0</code><br>
     * Same as {@link #BIS3A_V2}
     */
    urn_www_cenbii_eu_profile_bii03_ver2_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii03:ver2.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii04:ver1.0</code><br>
     * Same as {@link #BIS4A_V1}
     */
    urn_www_cenbii_eu_profile_bii04_ver1_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii04:ver1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii04:ver2.0</code><br>
     * Same as {@link #BIS4A_V2}
     * 
     * @deprecated This item should not be used to issue new identifiers!
     */
    @Deprecated
    urn_www_cenbii_eu_profile_bii04_ver2_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii04:ver2.0", EPeppolCodeListItemState.DEPRECATED),

    /**
     * ID: <code>cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii05:ver1.0</code><br>
     * Same as {@link #BIS5A_V1}
     * 
     * @deprecated This item should not be used to issue new identifiers!
     */
    @Deprecated
    urn_www_cenbii_eu_profile_bii05_ver1_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii05:ver1.0", EPeppolCodeListItemState.DEPRECATED),

    /**
     * ID: <code>cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii05:ver2.0</code><br>
     * Same as {@link #BIS5A_V2}
     */
    urn_www_cenbii_eu_profile_bii05_ver2_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii05:ver2.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii06:ver1.0</code><br>
     * Same as {@link #BIS6A_V1}
     * 
     * @deprecated This item should not be used to issue new identifiers!
     */
    @Deprecated
    urn_www_cenbii_eu_profile_bii06_ver1_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii06:ver1.0", EPeppolCodeListItemState.DEPRECATED),

    /**
     * ID: <code>cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii28:ver2.0</code><br>
     * Same as {@link #BIS28A_V2}
     */
    urn_www_cenbii_eu_profile_bii28_ver2_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii28:ver2.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii30:ver2.0</code><br>
     * Same as {@link #BIS30A_V2}
     */
    urn_www_cenbii_eu_profile_bii30_ver2_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii30:ver2.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii36:ver2.0</code><br>
     * Same as {@link #BIS36A_V2}
     * 
     * @deprecated This item should not be used to issue new identifiers!
     */
    @Deprecated
    urn_www_cenbii_eu_profile_bii36_ver2_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii36:ver2.0", EPeppolCodeListItemState.DEPRECATED),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0</code><br>
     * Same as {@link #BIS3_BILLING}
     */
    urn_fdc_peppol_eu_2017_poacc_billing_01_1_0("cenbii-procid-ubl", "urn:fdc:peppol.eu:2017:poacc:billing:01:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:2017:pracc:p001:01:1.0</code><br>
     */
    urn_fdc_peppol_eu_2017_pracc_p001_01_1_0("cenbii-procid-ubl", "urn:fdc:peppol.eu:2017:pracc:p001:01:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:2017:pracc:p002:01:1.0</code><br>
     */
    urn_fdc_peppol_eu_2017_pracc_p002_01_1_0("cenbii-procid-ubl", "urn:fdc:peppol.eu:2017:pracc:p002:01:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:2017:pracc:p003:01:1.0</code><br>
     */
    urn_fdc_peppol_eu_2017_pracc_p003_01_1_0("cenbii-procid-ubl", "urn:fdc:peppol.eu:2017:pracc:p003:01:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>oioubl-procid-ubl::Reference-Utility-1.0</code><br>
     */
    oioubl_procid_ubl_Reference_Utility_1_0("oioubl-procid-ubl", "Reference-Utility-1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>oioubl-procid-ubl::Procurement-ReminderOnly-1.0</code><br>
     */
    oioubl_procid_ubl_Procurement_ReminderOnly_1_0("oioubl-procid-ubl", "Procurement-ReminderOnly-1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:www.peppol.eu:profile:bis63a:ver1.0</code><br>
     * 
     * @deprecated This item should not be used to issue new identifiers!
     */
    @Deprecated
    urn_www_peppol_eu_profile_bis63a_ver1_0("cenbii-procid-ubl", "urn:www.peppol.eu:profile:bis63a:ver1.0", EPeppolCodeListItemState.DEPRECATED),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:catalogue_only:3</code><br>
     * Same as {@link #BIS3_CATALOGUE}
     */
    urn_fdc_peppol_eu_poacc_bis_catalogue_only_3("cenbii-procid-ubl", "urn:fdc:peppol.eu:poacc:bis:catalogue_only:3", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:catalogue_wo_response:3</code><br>
     * Same as {@link #BIS3_CATALOGUE_WO_RESPONSE}
     */
    urn_fdc_peppol_eu_poacc_bis_catalogue_wo_response_3("cenbii-procid-ubl", "urn:fdc:peppol.eu:poacc:bis:catalogue_wo_response:3", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:ordering:3</code><br>
     * Same as {@link #BIS3_ORDERING}
     */
    urn_fdc_peppol_eu_poacc_bis_ordering_3("cenbii-procid-ubl", "urn:fdc:peppol.eu:poacc:bis:ordering:3", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:order_only:3</code><br>
     * Same as {@link #BIS3_ORDER_ONLY}
     */
    urn_fdc_peppol_eu_poacc_bis_order_only_3("cenbii-procid-ubl", "urn:fdc:peppol.eu:poacc:bis:order_only:3", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:invoice_response:3</code><br>
     * Same as {@link #BIS3_INVOICE_RESPONSE}
     */
    urn_fdc_peppol_eu_poacc_bis_invoice_response_3("cenbii-procid-ubl", "urn:fdc:peppol.eu:poacc:bis:invoice_response:3", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:punch_out:3</code><br>
     * Same as {@link #BIS3_PUNCH_OUT}
     */
    urn_fdc_peppol_eu_poacc_bis_punch_out_3("cenbii-procid-ubl", "urn:fdc:peppol.eu:poacc:bis:punch_out:3", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:despatch_advice:3</code><br>
     * Same as {@link #BIS3_DESPATCH_ADVICE}
     */
    urn_fdc_peppol_eu_poacc_bis_despatch_advice_3("cenbii-procid-ubl", "urn:fdc:peppol.eu:poacc:bis:despatch_advice:3", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:order_agreement:3</code><br>
     * Same as {@link #BIS3_ORDER_AGREEMENT}
     */
    urn_fdc_peppol_eu_poacc_bis_order_agreement_3("cenbii-procid-ubl", "urn:fdc:peppol.eu:poacc:bis:order_agreement:3", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:mlr:3</code><br>
     * Same as {@link #BIS3_MLR}
     */
    urn_fdc_peppol_eu_poacc_bis_mlr_3("cenbii-procid-ubl", "urn:fdc:peppol.eu:poacc:bis:mlr:3", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:www.efaktura.gov.pl:ver1.0:account_corr:ver1.0</code><br>
     */
    urn_fdc_www_efaktura_gov_pl_ver1_0_account_corr_ver1_0("cenbii-procid-ubl", "urn:fdc:www.efaktura.gov.pl:ver1.0:account_corr:ver1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:www.efaktura.gov.pl:ver1.0:corr_inv:ver1.0</code><br>
     */
    urn_fdc_www_efaktura_gov_pl_ver1_0_corr_inv_ver1_0("cenbii-procid-ubl", "urn:fdc:www.efaktura.gov.pl:ver1.0:corr_inv:ver1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:www.efaktura.gov.pl:ver1.0:receipt_advice:ver1.0</code><br>
     */
    urn_fdc_www_efaktura_gov_pl_ver1_0_receipt_advice_ver1_0("cenbii-procid-ubl", "urn:fdc:www.efaktura.gov.pl:ver1.0:receipt_advice:ver1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:selfbilling:01:1.0</code><br>
     */
    urn_fdc_peppol_eu_2017_poacc_selfbilling_01_1_0("cenbii-procid-ubl", "urn:fdc:peppol.eu:2017:poacc:selfbilling:01:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:09:1.0</code><br>
     */
    urn_fdc_anskaffelser_no_2019_ehf_postaward_g3_09_1_0("cenbii-procid-ubl", "urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:09:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:en16931:any</code><br>
     */
    urn_fdc_peppol_eu_poacc_en16931_any("cenbii-procid-ubl", "urn:fdc:peppol.eu:poacc:en16931:any", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:kosit:profile:reporting:1.0</code><br>
     */
    urn_kosit_profile_reporting_1_0("cenbii-procid-ubl", "urn:kosit:profile:reporting:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:02:1.0</code><br>
     */
    urn_fdc_anskaffelser_no_2019_ehf_postaward_g3_02_1_0("cenbii-procid-ubl", "urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:02:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:01:1.0</code><br>
     */
    urn_fdc_bits_no_2017_profile_01_1_0("cenbii-procid-ubl", "urn:fdc:bits.no:2017:profile:01:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:03:1.0</code><br>
     */
    urn_fdc_bits_no_2017_profile_03_1_0("cenbii-procid-ubl", "urn:fdc:bits.no:2017:profile:03:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:02:1.0</code><br>
     */
    urn_fdc_bits_no_2017_profile_02_1_0("cenbii-procid-ubl", "urn:fdc:bits.no:2017:profile:02:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:04:1.0</code><br>
     */
    urn_fdc_bits_no_2017_profile_04_1_0("cenbii-procid-ubl", "urn:fdc:bits.no:2017:profile:04:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:09:1.0</code><br>
     */
    urn_fdc_bits_no_2017_profile_09_1_0("cenbii-procid-ubl", "urn:fdc:bits.no:2017:profile:09:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:10:1.0</code><br>
     */
    urn_fdc_bits_no_2017_profile_10_1_0("cenbii-procid-ubl", "urn:fdc:bits.no:2017:profile:10:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:11:1.0</code><br>
     */
    urn_fdc_bits_no_2017_profile_11_1_0("cenbii-procid-ubl", "urn:fdc:bits.no:2017:profile:11:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p004:1.0</code><br>
     */
    urn_fdc_peppol_eu_prac_bis_p004_1_0("cenbii-procid-ubl", "urn:fdc:peppol.eu:prac:bis:p004:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p005:1.0</code><br>
     */
    urn_fdc_peppol_eu_prac_bis_p005_1_0("cenbii-procid-ubl", "urn:fdc:peppol.eu:prac:bis:p005:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:www.cenbii.eu:profile:biixy:ver2.0</code><br>
     */
    urn_www_cenbii_eu_profile_biixy_ver2_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:biixy:ver2.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:01:1.0</code><br>
     */
    urn_fdc_anskaffelser_no_2019_ehf_postaward_g3_01_1_0("cenbii-procid-ubl", "urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:01:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:03:1.0</code><br>
     */
    urn_fdc_anskaffelser_no_2019_ehf_postaward_g3_03_1_0("cenbii-procid-ubl", "urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:03:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:05:1.0</code><br>
     */
    urn_fdc_anskaffelser_no_2019_ehf_postaward_g3_05_1_0("cenbii-procid-ubl", "urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:05:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:06:1.0</code><br>
     */
    urn_fdc_anskaffelser_no_2019_ehf_postaward_g3_06_1_0("cenbii-procid-ubl", "urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:06:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:07:1.0</code><br>
     */
    urn_fdc_anskaffelser_no_2019_ehf_postaward_g3_07_1_0("cenbii-procid-ubl", "urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:07:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:08:1.0</code><br>
     */
    urn_fdc_anskaffelser_no_2019_ehf_postaward_g3_08_1_0("cenbii-procid-ubl", "urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:08:1.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:www.efaktura.gov.pl:ver2.0:plinv:ver1.4</code><br>
     */
    urn_fdc_www_efaktura_gov_pl_ver2_0_plinv_ver1_4("cenbii-procid-ubl", "urn:fdc:www.efaktura.gov.pl:ver2.0:plinv:ver1.4", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:www.efaktura.gov.pl:ver2.0:corr_inv:ver4.0</code><br>
     */
    urn_fdc_www_efaktura_gov_pl_ver2_0_corr_inv_ver4_0("cenbii-procid-ubl", "urn:fdc:www.efaktura.gov.pl:ver2.0:corr_inv:ver4.0", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:www.efaktura.gov.pl:ver2.0:sbcn:ver1.4</code><br>
     */
    urn_fdc_www_efaktura_gov_pl_ver2_0_sbcn_ver1_4("cenbii-procid-ubl", "urn:fdc:www.efaktura.gov.pl:ver2.0:sbcn:ver1.4", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p001:1.1</code><br>
     */
    urn_fdc_peppol_eu_prac_bis_p001_1_1("cenbii-procid-ubl", "urn:fdc:peppol.eu:prac:bis:p001:1.1", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p002:1.1</code><br>
     */
    urn_fdc_peppol_eu_prac_bis_p002_1_1("cenbii-procid-ubl", "urn:fdc:peppol.eu:prac:bis:p002:1.1", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p003:1.1</code><br>
     */
    urn_fdc_peppol_eu_prac_bis_p003_1_1("cenbii-procid-ubl", "urn:fdc:peppol.eu:prac:bis:p003:1.1", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p004:1.1</code><br>
     */
    urn_fdc_peppol_eu_prac_bis_p004_1_1("cenbii-procid-ubl", "urn:fdc:peppol.eu:prac:bis:p004:1.1", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p005:1.1</code><br>
     */
    urn_fdc_peppol_eu_prac_bis_p005_1_1("cenbii-procid-ubl", "urn:fdc:peppol.eu:prac:bis:p005:1.1", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p006:1.1</code><br>
     */
    urn_fdc_peppol_eu_prac_bis_p006_1_1("cenbii-procid-ubl", "urn:fdc:peppol.eu:prac:bis:p006:1.1", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p007:1.1</code><br>
     */
    urn_fdc_peppol_eu_prac_bis_p007_1_1("cenbii-procid-ubl", "urn:fdc:peppol.eu:prac:bis:p007:1.1", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p008:1.1</code><br>
     */
    urn_fdc_peppol_eu_prac_bis_p008_1_1("cenbii-procid-ubl", "urn:fdc:peppol.eu:prac:bis:p008:1.1", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p009:1.1</code><br>
     */
    urn_fdc_peppol_eu_prac_bis_p009_1_1("cenbii-procid-ubl", "urn:fdc:peppol.eu:prac:bis:p009:1.1", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:oo:bis:reporting:1</code><br>
     */
    urn_fdc_peppol_eu_oo_bis_reporting_1("cenbii-procid-ubl", "urn:fdc:peppol.eu:oo:bis:reporting:1", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:logistics:bis:despatch_advice_only:1</code><br>
     */
    urn_fdc_peppol_eu_logistics_bis_despatch_advice_only_1("cenbii-procid-ubl", "urn:fdc:peppol.eu:logistics:bis:despatch_advice_only:1", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:logistics:bis:despatch_advice_w_response:1</code><br>
     */
    urn_fdc_peppol_eu_logistics_bis_despatch_advice_w_response_1("cenbii-procid-ubl", "urn:fdc:peppol.eu:logistics:bis:despatch_advice_w_response:1", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:peppol.eu:logistics:bis:weight_statement:1</code><br>
     */
    urn_fdc_peppol_eu_logistics_bis_weight_statement_1("cenbii-procid-ubl", "urn:fdc:peppol.eu:logistics:bis:weight_statement:1", EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>cenbii-procid-ubl::urn:fdc:www.efaktura.gov.pl:ver2.0:us:ver1.0</code><br>
     */
    urn_fdc_www_efaktura_gov_pl_ver2_0_us_ver1_0("cenbii-procid-ubl", "urn:fdc:www.efaktura.gov.pl:ver2.0:us:ver1.0", EPeppolCodeListItemState.ACTIVE);
    public static final String CODE_LIST_VERSION = "8.2";
    public static final int CODE_LIST_ENTRY_COUNT = 70;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii01_ver1_0}
     * 
     * @deprecated This item should not be used to issue new identifiers!
     */
    @Deprecated
    public static final EPredefinedProcessIdentifier BIS1A_V1 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii01_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii01_ver2_0}
     */
    public static final EPredefinedProcessIdentifier BIS1A_V2 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii01_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii03_ver1_0}
     * 
     * @deprecated This item should not be used to issue new identifiers!
     */
    @Deprecated
    public static final EPredefinedProcessIdentifier BIS3A_V1 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii03_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii03_ver2_0}
     */
    public static final EPredefinedProcessIdentifier BIS3A_V2 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii03_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii04_ver1_0}
     */
    public static final EPredefinedProcessIdentifier BIS4A_V1 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii04_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii04_ver2_0}
     * 
     * @deprecated This item should not be used to issue new identifiers!
     */
    @Deprecated
    public static final EPredefinedProcessIdentifier BIS4A_V2 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii04_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii05_ver1_0}
     * 
     * @deprecated This item should not be used to issue new identifiers!
     */
    @Deprecated
    public static final EPredefinedProcessIdentifier BIS5A_V1 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii05_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii05_ver2_0}
     */
    public static final EPredefinedProcessIdentifier BIS5A_V2 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii05_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii06_ver1_0}
     * 
     * @deprecated This item should not be used to issue new identifiers!
     */
    @Deprecated
    public static final EPredefinedProcessIdentifier BIS6A_V1 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii06_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii28_ver2_0}
     */
    public static final EPredefinedProcessIdentifier BIS28A_V2 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii28_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii30_ver2_0}
     */
    public static final EPredefinedProcessIdentifier BIS30A_V2 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii30_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii36_ver2_0}
     * 
     * @deprecated This item should not be used to issue new identifiers!
     */
    @Deprecated
    public static final EPredefinedProcessIdentifier BIS36A_V2 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii36_ver2_0;
    /**
     * Same as {@link #urn_fdc_peppol_eu_2017_poacc_billing_01_1_0}
     */
    public static final EPredefinedProcessIdentifier BIS3_BILLING = EPredefinedProcessIdentifier.urn_fdc_peppol_eu_2017_poacc_billing_01_1_0;
    /**
     * Same as {@link #urn_fdc_peppol_eu_poacc_bis_catalogue_only_3}
     */
    public static final EPredefinedProcessIdentifier BIS3_CATALOGUE = EPredefinedProcessIdentifier.urn_fdc_peppol_eu_poacc_bis_catalogue_only_3;
    /**
     * Same as {@link #urn_fdc_peppol_eu_poacc_bis_catalogue_wo_response_3}
     */
    public static final EPredefinedProcessIdentifier BIS3_CATALOGUE_WO_RESPONSE = EPredefinedProcessIdentifier.urn_fdc_peppol_eu_poacc_bis_catalogue_wo_response_3;
    /**
     * Same as {@link #urn_fdc_peppol_eu_poacc_bis_ordering_3}
     */
    public static final EPredefinedProcessIdentifier BIS3_ORDERING = EPredefinedProcessIdentifier.urn_fdc_peppol_eu_poacc_bis_ordering_3;
    /**
     * Same as {@link #urn_fdc_peppol_eu_poacc_bis_order_only_3}
     */
    public static final EPredefinedProcessIdentifier BIS3_ORDER_ONLY = EPredefinedProcessIdentifier.urn_fdc_peppol_eu_poacc_bis_order_only_3;
    /**
     * Same as {@link #urn_fdc_peppol_eu_poacc_bis_invoice_response_3}
     */
    public static final EPredefinedProcessIdentifier BIS3_INVOICE_RESPONSE = EPredefinedProcessIdentifier.urn_fdc_peppol_eu_poacc_bis_invoice_response_3;
    /**
     * Same as {@link #urn_fdc_peppol_eu_poacc_bis_punch_out_3}
     */
    public static final EPredefinedProcessIdentifier BIS3_PUNCH_OUT = EPredefinedProcessIdentifier.urn_fdc_peppol_eu_poacc_bis_punch_out_3;
    /**
     * Same as {@link #urn_fdc_peppol_eu_poacc_bis_despatch_advice_3}
     */
    public static final EPredefinedProcessIdentifier BIS3_DESPATCH_ADVICE = EPredefinedProcessIdentifier.urn_fdc_peppol_eu_poacc_bis_despatch_advice_3;
    /**
     * Same as {@link #urn_fdc_peppol_eu_poacc_bis_order_agreement_3}
     */
    public static final EPredefinedProcessIdentifier BIS3_ORDER_AGREEMENT = EPredefinedProcessIdentifier.urn_fdc_peppol_eu_poacc_bis_order_agreement_3;
    /**
     * Same as {@link #urn_fdc_peppol_eu_poacc_bis_mlr_3}
     */
    public static final EPredefinedProcessIdentifier BIS3_MLR = EPredefinedProcessIdentifier.urn_fdc_peppol_eu_poacc_bis_mlr_3;
    /**
     * @deprecated Use BIS3_BILLING instead!
     */
    @Deprecated
    public static final EPredefinedProcessIdentifier BIS5A_V3 = BIS3_BILLING;
    private final String m_sScheme;
    private final String m_sValue;
    private final EPeppolCodeListItemState m_eState;

    EPredefinedProcessIdentifier(@Nonnull @Nonempty final String sScheme, @Nonnull @Nonempty final String sValue, @Nonnull final EPeppolCodeListItemState eState) {
        m_sScheme = sScheme;
        m_sValue = sValue;
        m_eState = eState;
    }

    @Nonnull
    @Nonempty
    public String getScheme() {
        return m_sScheme;
    }

    @Nonnull
    @Nonempty
    public String getValue() {
        return m_sValue;
    }

    @Nonnull
    public EPeppolCodeListItemState getState() {
        return m_eState;
    }

    @Nonnull
    public PeppolProcessIdentifier getAsProcessIdentifier() {
        return new PeppolProcessIdentifier(this);
    }

    @Nullable
    public static EPredefinedProcessIdentifier getFromProcessIdentifierOrNull(@Nullable final IProcessIdentifier aProcessID) {
        if (aProcessID!= null) {
            for (EPredefinedProcessIdentifier e: EPredefinedProcessIdentifier.values()) {
                if (e.hasScheme(aProcessID.getScheme())&&e.hasValue(aProcessID.getValue())) {
                    return e;
                }
            }
        }
        return null;
    }
}
