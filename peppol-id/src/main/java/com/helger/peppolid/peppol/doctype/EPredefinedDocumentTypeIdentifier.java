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
package com.helger.peppolid.peppol.doctype;

import java.time.LocalDate;
import java.time.Month;
import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.version.Version;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.EPeppolCodeListItemState;
import javax.annotation.CheckForSigned;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;


/**
 * This file was automatically generated.
 * Do NOT edit!
 */
@CodingStyleguideUnaware
public enum EPredefinedDocumentTypeIdentifier
    implements IPeppolPredefinedDocumentTypeIdentifier
{

    /**
     * <code>urn:www.peppol.eu:schema:xsd:VirtualCompanyDossier-1::VirtualCompanyDossier##urn:www.cenbii.eu:transaction:biicoretrdm991:ver0.1:#urn:www.peppol.eu:bis:peppol991a:ver1.0::0.1</code><br>
     * Same as {@link #VIRTUALCOMPANYDOSSIER_T991_BIS991A}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     * @since code list 1.0.0
     */
    @Deprecated(forRemoval = false)
    urn_www_peppol_eu_schema_xsd_VirtualCompanyDossier_1__VirtualCompanyDossier__urn_www_cenbii_eu_transaction_biicoretrdm991_ver0_1__urn_www_peppol_eu_bis_peppol991a_ver1_0__0_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:www.peppol.eu:schema:xsd:VirtualCompanyDossier-1", "VirtualCompanyDossier", "urn:www.cenbii.eu:transaction:biicoretrdm991:ver0.1:#urn:www.peppol.eu:bis:peppol991a:ver1.0", "0.1"), "Virtual Company Dossier", Version.parse("1.0.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("2"), null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::none")),

    /**
     * <code>urn:www.peppol.eu:schema:xsd:VirtualCompanyDossierPackage-1::VirtualCompanyDossierPackage##urn:www.cenbii.eu:transaction:biicoretrdm992:ver0.1:#urn:www.peppol.eu:bis:peppol992a:ver1.0::0.1</code><br>
     * Same as {@link #VIRTUALCOMPANYDOSSIERPACKAGE_T992_BIS992A}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     * @since code list 1.0.0
     */
    @Deprecated(forRemoval = false)
    urn_www_peppol_eu_schema_xsd_VirtualCompanyDossierPackage_1__VirtualCompanyDossierPackage__urn_www_cenbii_eu_transaction_biicoretrdm992_ver0_1__urn_www_peppol_eu_bis_peppol992a_ver1_0__0_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:www.peppol.eu:schema:xsd:VirtualCompanyDossierPackage-1", "VirtualCompanyDossierPackage", "urn:www.cenbii.eu:transaction:biicoretrdm992:ver0.1:#urn:www.peppol.eu:bis:peppol992a:ver1.0", "0.1"), "Virtual Company Dossier Package", Version.parse("1.0.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("2"), null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::none")),

    /**
     * <code>urn:www.peppol.eu:schema:xsd:CatalogueTemplate-1::CatalogueTemplate##urn:www.cenbii.eu:transaction:biicoretrdm993:ver0.1:#urn:www.peppol.eu:bis:peppol993a:ver1.0::0.1</code><br>
     * Same as {@link #CATALOGUETEMPLATE_T993_BIS993A}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     * @since code list 1.0.0
     */
    @Deprecated(forRemoval = false)
    urn_www_peppol_eu_schema_xsd_CatalogueTemplate_1__CatalogueTemplate__urn_www_cenbii_eu_transaction_biicoretrdm993_ver0_1__urn_www_peppol_eu_bis_peppol993a_ver1_0__0_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:www.peppol.eu:schema:xsd:CatalogueTemplate-1", "CatalogueTemplate", "urn:www.cenbii.eu:transaction:biicoretrdm993:ver0.1:#urn:www.peppol.eu:bis:peppol993a:ver1.0", "0.1"), "Catalogue Template", Version.parse("1.0.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("2"), null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::none")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:www.cenbii.eu:transaction:biicoretrdm019:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0::2.0</code><br>
     * Same as {@link #CATALOGUE_T019_BIS1A}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     * @since code list 1.0.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_www_cenbii_eu_transaction_biicoretrdm019_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2", "Catalogue", "urn:www.cenbii.eu:transaction:biicoretrdm019:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0", "2.0"), "PEPPOL Catalogue profile Catalogue V1", Version.parse("1.0.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("2"), null, false, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii01:ver1.0", "cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii01:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.cenbii.eu:transaction:biicoretrdm057:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0::2.0</code><br>
     * Same as {@link #APPLICATIONRESPONSE_T057_BIS1A}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     * @since code list 1.0.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biicoretrdm057_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:www.cenbii.eu:transaction:biicoretrdm057:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0", "2.0"), "PEPPOL Catalogue profile ApplicationResponse V1", Version.parse("1.0.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("2"), null, false, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii01:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.cenbii.eu:transaction:biicoretrdm058:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0::2.0</code><br>
     * Same as {@link #APPLICATIONRESPONSE_T058_BIS1A}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     * @since code list 1.0.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biicoretrdm058_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:www.cenbii.eu:transaction:biicoretrdm058:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0", "2.0"), "PEPPOL Catalogue profile ApplicationResponse V1", Version.parse("1.0.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("2"), null, false, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii01:ver1.0", "cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii01:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:www.cenbii.eu:transaction:biitrns019:ver2.0:extended:urn:www.peppol.eu:bis:peppol1a:ver4.0::2.1</code><br>
     * Same as {@link #CATALOGUE_T019_BIS1A_V40}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     * @since code list 1.2.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_www_cenbii_eu_transaction_biitrns019_ver2_0_extended_urn_www_peppol_eu_bis_peppol1a_ver4_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2", "Catalogue", "urn:www.cenbii.eu:transaction:biitrns019:ver2.0:extended:urn:www.peppol.eu:bis:peppol1a:ver4.0", "2.1"), "PEPPOL Catalogue profile V4", Version.parse("1.2.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("7"), null, false, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii01:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0::2.0</code><br>
     * Same as {@link #ORDER_T001_BIS3A}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     * @since code list 1.0.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biicoretrdm001_ver1_0__urn_www_peppol_eu_bis_peppol3a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0", "2.0"), "PEPPOL Order profile V1", Version.parse("1.0.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("2"), null, false, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii03:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol03a:ver2.0::2.1</code><br>
     * Same as {@link #ORDER_T001_BIS03A_V20}
     * 
     * @deprecated since v3 - this item should not be used to issue new identifiers!
     * @since code list 1.2.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol03a_ver2_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol03a:ver2.0", "2.1"), "PEPPOL Order profile V2 (invalid)", Version.parse("1.2.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("3"), null, false, true, 2, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii03:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol3a:ver2.0::2.1</code><br>
     * Same as {@link #ORDER_T001_BIS3A_V20}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     * @since code list 3
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol3a_ver2_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol3a:ver2.0", "2.1"), "PEPPOL Order profile V2", Version.parse("3"), EPeppolCodeListItemState.DEPRECATED, Version.parse("7"), null, false, true, 2, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii03:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol4a:ver1.0::2.0</code><br>
     * Same as {@link #INVOICE_T010_BIS4A}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     * @since code list 1.0.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol4a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol4a:ver1.0", "2.0"), "PEPPOL Invoice profile V1", Version.parse("1.0.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("2"), null, false, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii04:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol4a:ver2.0::2.1</code><br>
     * Same as {@link #INVOICE_T010_BIS4A_V20}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     * @since code list 1.2.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biitrns010_ver2_0_extended_urn_www_peppol_eu_bis_peppol4a_ver2_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol4a:ver2.0", "2.1"), "PEPPOL Invoice profile V2", Version.parse("1.2.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("7"), null, false, true, 2, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii04:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0::2.0</code><br>
     * Same as {@link #INVOICE_T010_BIS5A}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     * @since code list 1.1.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0", "2.0"), "PEPPOL Billing profile Invoice V1", Version.parse("1.1.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("2"), null, false, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii05:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0::2.0</code><br>
     * Same as {@link #CREDITNOTE_T014_BIS5A}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     * @since code list 1.1.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0", "2.0"), "PEPPOL Billing profile Credit Note V1", Version.parse("1.1.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("2"), null, false, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii05:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm015:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0::2.0</code><br>
     * Same as {@link #INVOICE_T015_BIS5A}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     * @since code list 1.1.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm015_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biicoretrdm015:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0", "2.0"), "PEPPOL Billing profile Invoice V1", Version.parse("1.1.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("2"), null, false, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii05:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol5a:ver2.0::2.1</code><br>
     * Same as {@link #INVOICE_T010_BIS5A_V20}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     * @since code list 1.2.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biitrns010_ver2_0_extended_urn_www_peppol_eu_bis_peppol5a_ver2_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol5a:ver2.0", "2.1"), "PEPPOL Billing profile Invoice V2", Version.parse("1.2.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("7"), null, false, true, 2, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii05:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biitrns014:ver2.0:extended:urn:www.peppol.eu:bis:peppol5a:ver2.0::2.1</code><br>
     * Same as {@link #CREDITNOTE_T014_BIS5A_V20}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     * @since code list 1.2.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biitrns014_ver2_0_extended_urn_www_peppol_eu_bis_peppol5a_ver2_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:www.cenbii.eu:transaction:biitrns014:ver2.0:extended:urn:www.peppol.eu:bis:peppol5a:ver2.0", "2.1"), "PEPPOL Billing profile Credit Note V2", Version.parse("1.2.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("7"), null, false, true, 2, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii05:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code><br>
     * Same as {@link #ORDER_T001_BIS6A}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     * @since code list 1.0.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biicoretrdm001_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0", "2.0"), "PEPPOL Procurement profile Order V1", Version.parse("1.0.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("7"), null, false, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii06:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponseSimple-2::OrderResponseSimple##urn:www.cenbii.eu:transaction:biicoretrdm002:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code><br>
     * Same as {@link #ORDERRESPONSESIMPLE_T002_BIS6A}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     * @since code list 1.0.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponseSimple_2__OrderResponseSimple__urn_www_cenbii_eu_transaction_biicoretrdm002_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponseSimple-2", "OrderResponseSimple", "urn:www.cenbii.eu:transaction:biicoretrdm002:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0", "2.0"), "PEPPOL Procurement profile OrderResponseSimple V1", Version.parse("1.0.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("7"), null, false, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii06:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponseSimple-2::OrderResponseSimple##urn:www.cenbii.eu:transaction:biicoretrdm003:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code><br>
     * Same as {@link #ORDERRESPONSESIMPLE_T003_BIS6A}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     * @since code list 1.0.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponseSimple_2__OrderResponseSimple__urn_www_cenbii_eu_transaction_biicoretrdm003_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponseSimple-2", "OrderResponseSimple", "urn:www.cenbii.eu:transaction:biicoretrdm003:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0", "2.0"), "PEPPOL Procurement profile OrderResponseSimple V1", Version.parse("1.0.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("7"), null, false, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii06:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code><br>
     * Same as {@link #INVOICE_T010_BIS6A}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     * @since code list 1.0.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0", "2.0"), "PEPPOL Procurement profile Invoice V1", Version.parse("1.0.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("7"), null, false, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii06:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code><br>
     * Same as {@link #CREDITNOTE_T014_BIS6A}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     * @since code list 1.0.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0", "2.0"), "PEPPOL Procurement profile Credit Note V1", Version.parse("1.0.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("7"), null, false, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii06:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm015:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code><br>
     * Same as {@link #INVOICE_T015_BIS6A}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     * @since code list 1.0.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm015_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biicoretrdm015:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0", "2.0"), "PEPPOL Procurement profile Invoice V1", Version.parse("1.0.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("7"), null, false, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii06:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol4a:ver1.0#urn:www.difi.no:ehf:faktura:ver1::2.0</code><br>
     * Same as {@link #INVOICE_T010_BIS4A_WWW_DIFI_NO_EHF_FAKTURA_VER1}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     * @since code list 1.1.1
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol4a_ver1_0_urn_www_difi_no_ehf_faktura_ver1__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol4a:ver1.0#urn:www.difi.no:ehf:faktura:ver1", "2.0"), "EHF Invoice V1", Version.parse("1.1.1"), EPeppolCodeListItemState.DEPRECATED, Version.parse("2"), null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii04:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.cenbii.eu:profile:biixx:ver1.0#urn:www.difi.no:ehf:kreditnota:ver1::2.0</code><br>
     * Same as {@link #CREDITNOTE_T014_WWW_CENBII_EU_PROFILE_BIIXX_VER1_0_WWW_DIFI_NO_EHF_KREDITNOTA_VER1}
     * 
     * @since code list 1.1.1
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_cenbii_eu_profile_biixx_ver1_0_urn_www_difi_no_ehf_kreditnota_ver1__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.cenbii.eu:profile:biixx:ver1.0#urn:www.difi.no:ehf:kreditnota:ver1", "2.0"), "Standalone Credit Note according to EHF V1", Version.parse("1.1.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii05:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0::2.1</code><br>
     * Same as {@link #ORDER_T001_BIS28A}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     * @since code list 1.2.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0", "2.1"), "PEPPOL Ordering profile Order V1", Version.parse("1.2.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("7"), null, false, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii28:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::Order##urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0::2.1</code><br>
     * Same as {@link #ORDER_T076_BIS28A}
     * 
     * @deprecated since v3 - this item should not be used to issue new identifiers!
     * @since code list 1.2.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__Order__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2", "Order", "urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0", "2.1"), "PEPPOL Ordering profile OrderResponse V1 (invalid)", Version.parse("1.2.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("3"), null, false, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii28:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::OrderResponse##urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0::2.1</code><br>
     * Same as {@link #ORDER_T076_BIS28A2}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     * @since code list 3
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2", "OrderResponse", "urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0", "2.1"), "PEPPOL Ordering profile OrderResponse V1", Version.parse("3"), EPeppolCodeListItemState.DEPRECATED, Version.parse("7"), null, false, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii28:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2::DespatchAdvice##urn:www.cenbii.eu:transaction:biitrns016:ver1.0:extended:urn:www.peppol.eu:bis:peppol30a:ver1.0::2.1</code><br>
     * Same as {@link #DESPATCHADVICE_T016_BIS30A}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     * @since code list 1.2.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_www_cenbii_eu_transaction_biitrns016_ver1_0_extended_urn_www_peppol_eu_bis_peppol30a_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2", "DespatchAdvice", "urn:www.cenbii.eu:transaction:biitrns016:ver1.0:extended:urn:www.peppol.eu:bis:peppol30a:ver1.0", "2.1"), "PEPPOL Despatch Advice V1", Version.parse("1.2.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("7"), null, false, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii30:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.cenbii.eu:transaction:biitrns071:ver2.0:extended:urn:www.peppol.eu:bis:peppol36a:ver1.0::2.1</code><br>
     * Same as {@link #APPLICATIONRESPONSE_T071_BIS36A}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     * @since code list 1.2.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biitrns071_ver2_0_extended_urn_www_peppol_eu_bis_peppol36a_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:www.cenbii.eu:transaction:biitrns071:ver2.0:extended:urn:www.peppol.eu:bis:peppol36a:ver1.0", "2.1"), "PEPPOL Message Level Response V1", Version.parse("1.2.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("7"), null, false, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii36:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0::2.1</code><br>
     * Same as {@link #INVOICE_EN16931_PEPPOL_V30}
     * 
     * @since code list 2
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0", "2.1"), "Peppol BIS Billing UBL Invoice V3", Version.parse("2"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0", "cenbii-procid-ubl::urn:peppol:france:billing:regulated", "cenbii-procid-ubl::urn:peppol:france:billing:non-regulated")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0::2.1</code><br>
     * Same as {@link #CREDITNOTE_EN16931_PEPPOL_V30}
     * 
     * @since code list 2
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0", "2.1"), "Peppol BIS Billing UBL Credit Note V3", Version.parse("2"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0", "cenbii-procid-ubl::urn:peppol:france:billing:regulated", "cenbii-procid-ubl::urn:peppol:france:billing:non-regulated")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0:extended:urn:fdc:peppol-authority.co.uk:spec:ordering:ver1.0::2.1</code><br>
     * Same as {@link #DHSC_CUSTOMIZED_ORDERING_PROFILE_V1_ORDER}
     * 
     * @since code list 3
     */
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0_extended_urn_fdc_peppol_authority_co_uk_spec_ordering_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0:extended:urn:fdc:peppol-authority.co.uk:spec:ordering:ver1.0", "2.1"), "DHSC Customized Ordering profile Order V1", Version.parse("3"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii28:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::Order##urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0:extended:urn:fdc:peppol-authority.co.uk:spec:ordering:ver1.0::2.1</code><br>
     * Same as {@link #DHSC_CUSTOMIZED_ORDERING_PROFILE_V1_ORDER_RESPONSE}
     * 
     * @deprecated since v8.0 - this item should not be used to issue new identifiers!
     * @since code list 3
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__Order__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0_extended_urn_fdc_peppol_authority_co_uk_spec_ordering_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2", "Order", "urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0:extended:urn:fdc:peppol-authority.co.uk:spec:ordering:ver1.0", "2.1"), "DHSC Customized Ordering profile OrderResponse V1 (invalid)", Version.parse("3"), EPeppolCodeListItemState.DEPRECATED, Version.parse("8.0"), null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii28:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::OrderResponse##urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0:extended:urn:fdc:peppol-authority.co.uk:spec:ordering:ver1.0::2.1</code><br>
     * Same as {@link #DHSC_CUSTOMIZED_ORDERING_PROFILE_V1_ORDER_RESPONSE2}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0_extended_urn_fdc_peppol_authority_co_uk_spec_ordering_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2", "OrderResponse", "urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0:extended:urn:fdc:peppol-authority.co.uk:spec:ordering:ver1.0", "2.1"), "DHSC Customized Ordering profile OrderResponse V1", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii28:ver2.0")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0::D16B</code><br>
     * Same as {@link #INVOICE_CII_EN16931_PEPPOL_V30}
     * 
     * @since code list 3
     */
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0__D16B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0", "D16B"), "Peppol BIS Billing CII Invoice V3", Version.parse("3"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestRequest-2::ExpressionOfInterestRequest##urn:www.cenbii.eu:transaction:biitrdm081:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t001:ver1.0::2.2</code><br>
     * Same as {@link #EXPRESSION_OF_INTEREST_REQUEST_V1}
     * 
     * @deprecated since v8.5 - this item should not be used to issue new identifiers!
     * @since code list 3
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestRequest_2__ExpressionOfInterestRequest__urn_www_cenbii_eu_transaction_biitrdm081_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t001_ver1_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestRequest-2", "ExpressionOfInterestRequest", "urn:www.cenbii.eu:transaction:biitrdm081:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t001:ver1.0", "2.2"), "Peppol Procurement procedure subscription Request V1", Version.parse("3"), EPeppolCodeListItemState.DEPRECATED, Version.parse("8.5"), null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:pracc:p001:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestResponse-2::ExpressionOfInterestResponse##urn:www.cenbii.eu:transaction:biitrdm082:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t002:ver1.0::2.2</code><br>
     * Same as {@link #EXPRESSION_OF_INTEREST_RESPONSE_V1}
     * 
     * @deprecated since v8.5 - this item should not be used to issue new identifiers!
     * @since code list 3
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestResponse_2__ExpressionOfInterestResponse__urn_www_cenbii_eu_transaction_biitrdm082_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t002_ver1_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestResponse-2", "ExpressionOfInterestResponse", "urn:www.cenbii.eu:transaction:biitrdm082:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t002:ver1.0", "2.2"), "Peppol Procurement procedure subscription Response V1", Version.parse("3"), EPeppolCodeListItemState.DEPRECATED, Version.parse("8.5"), null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:pracc:p001:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:TenderStatusRequest-2::TenderStatusRequest##urn:www.cenbii.eu:transaction:biitrdm097:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t003:ver1.0::2.2</code><br>
     * Same as {@link #TENDER_STATUS_REQUEST_V1}
     * 
     * @deprecated since v8.5 - this item should not be used to issue new identifiers!
     * @since code list 3
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_TenderStatusRequest_2__TenderStatusRequest__urn_www_cenbii_eu_transaction_biitrdm097_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t003_ver1_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:TenderStatusRequest-2", "TenderStatusRequest", "urn:www.cenbii.eu:transaction:biitrdm097:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t003:ver1.0", "2.2"), "Peppol Procurement document access TenderStatusRequest V1", Version.parse("3"), EPeppolCodeListItemState.DEPRECATED, Version.parse("8.5"), null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:pracc:p002:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CallForTenders-2::CallForTenders##urn:www.cenbii.eu:transaction:biitrdm083:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t004:ver1.0::2.2</code><br>
     * Same as {@link #CALL_FOR_TENDERS_V1}
     * 
     * @deprecated since v8.5 - this item should not be used to issue new identifiers!
     * @since code list 3
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_CallForTenders_2__CallForTenders__urn_www_cenbii_eu_transaction_biitrdm083_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t004_ver1_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CallForTenders-2", "CallForTenders", "urn:www.cenbii.eu:transaction:biitrdm083:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t004:ver1.0", "2.2"), "Peppol Procurement document access CallForTenders V1", Version.parse("3"), EPeppolCodeListItemState.DEPRECATED, Version.parse("8.5"), null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:pracc:p002:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:TenderReceipt-2::TenderReceipt##urn:www.cenbii.eu:transaction:biitrdm045:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t006:ver1.0::2.2</code><br>
     * Same as {@link #TENDER_RECEIPT_V1}
     * 
     * @deprecated since v8.5 - this item should not be used to issue new identifiers!
     * @since code list 3
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_TenderReceipt_2__TenderReceipt__urn_www_cenbii_eu_transaction_biitrdm045_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t006_ver1_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:TenderReceipt-2", "TenderReceipt", "urn:www.cenbii.eu:transaction:biitrdm045:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t006:ver1.0", "2.2"), "Peppol Tender Submission TenderReceipt V1", Version.parse("3"), EPeppolCodeListItemState.DEPRECATED, Version.parse("8.5"), null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:pracc:p003:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Tender-2::Tender##urn:www.cenbii.eu:transaction:biitrdm090:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t005:ver1.0::2.2</code><br>
     * Same as {@link #TENDER_V1}
     * 
     * @deprecated since v8.5 - this item should not be used to issue new identifiers!
     * @since code list 3
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Tender_2__Tender__urn_www_cenbii_eu_transaction_biitrdm090_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t005_ver1_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Tender-2", "Tender", "urn:www.cenbii.eu:transaction:biitrdm090:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t005:ver1.0", "2.2"), "Peppol Tender Submission Tender V1", Version.parse("3"), EPeppolCodeListItemState.DEPRECATED, Version.parse("8.5"), null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:pracc:p003:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.1::2.1</code><br>
     * Same as {@link #XRECHNUNG_INVOICE_UBL_V11}
     * 
     * @deprecated since v5 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     * @since code list 3
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.1", "2.1"), "XRechnung UBL Invoice V1.1", Version.parse("3"), EPeppolCodeListItemState.REMOVED, Version.parse("5"), PDTFactory.createLocalDate(2023, Month.of(5), 24), false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.1::2.1</code><br>
     * Same as {@link #XRECHNUNG_CREDIT_NOTE_UBL_V11}
     * 
     * @deprecated since v5 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     * @since code list 3
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.1", "2.1"), "XRechnung UBL Credit Note V1.1", Version.parse("3"), EPeppolCodeListItemState.REMOVED, Version.parse("5"), PDTFactory.createLocalDate(2023, Month.of(5), 24), false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.1::D16B</code><br>
     * Same as {@link #XRECHNUNG_INVOICE_CII_V11}
     * 
     * @deprecated since v5 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     * @since code list 3
     */
    @Deprecated(forRemoval = false)
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_1__D16B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.1", "D16B"), "XRechnung CII Invoice V1.1", Version.parse("3"), EPeppolCodeListItemState.REMOVED, Version.parse("5"), PDTFactory.createLocalDate(2023, Month.of(5), 24), false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oioubl:names:specification:oioubl:schema:xsd:UtilityStatement-2::UtilityStatement##OIOUBL-2.02::2.0</code><br>
     * Same as {@link #OIOUBL_UTILITY_STATEMENT_202}
     * 
     * @since code list 3
     */
    urn_oioubl_names_specification_oioubl_schema_xsd_UtilityStatement_2__UtilityStatement__OIOUBL_2_02__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oioubl:names:specification:oioubl:schema:xsd:UtilityStatement-2", "UtilityStatement", "OIOUBL-2.02", "2.0"), "OIOUBL UtilityStatement V2.02", Version.parse("3"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("oioubl-procid-ubl::Reference-Utility-1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Reminder-2::Reminder##OIOUBL-2.02::2.0</code><br>
     * Same as {@link #OIOUBL_REMINDER_202}
     * 
     * @since code list 3
     */
    urn_oasis_names_specification_ubl_schema_xsd_Reminder_2__Reminder__OIOUBL_2_02__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Reminder-2", "Reminder", "OIOUBL-2.02", "2.0"), "OIOUBL Reminder V2.02", Version.parse("3"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("oioubl-procid-ubl::Procurement-ReminderOnly-1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#conformant#urn:UBL.BE:1.0.0.20180214::2.1</code><br>
     * Same as {@link #UBL_BE_INVOICE_UBL_V11}
     * 
     * @since code list 3
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_UBL_BE_1_0_0_20180214__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#conformant#urn:UBL.BE:1.0.0.20180214", "2.1"), "UBL.BE Invoice 3.0", Version.parse("3"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#conformant#urn:UBL.BE:1.0.0.20180214::2.1</code><br>
     * Same as {@link #UBL_BE_CREDIT_NOTE_UBL_V11}
     * 
     * @since code list 3
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_UBL_BE_1_0_0_20180214__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#conformant#urn:UBL.BE:1.0.0.20180214", "2.1"), "UBL.BE Credit Note 3.0", Version.parse("3"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.peppol.eu:transaction:biitrns111:ver1.0::2.1</code><br>
     * Same as {@link #APPLICATIONRESPONSE_WWW_PEPPOL_EU_TRANSACTION_BIITRNS111_VER1_0}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     * @since code list 4
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_peppol_eu_transaction_biitrns111_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:www.peppol.eu:transaction:biitrns111:ver1.0", "2.1"), "Peppol Invoice Response V1", Version.parse("4"), EPeppolCodeListItemState.DEPRECATED, Version.parse("7"), null, false, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.peppol.eu:profile:bis63a:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:fdc:peppol.eu:poacc:trns:catalogue:3::2.1</code><br>
     * Same as {@link #CATALOGUE_FDC_PEPPOL_EU_POACC_TRNS_CATALOGUE_3}
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_fdc_peppol_eu_poacc_trns_catalogue_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2", "Catalogue", "urn:fdc:peppol.eu:poacc:trns:catalogue:3", "2.1"), "Peppol Catalogue transaction 3.0", Version.parse("4"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:catalogue_only:3", "cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:catalogue_wo_response:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:fdc:peppol.eu:poacc:trns:catalogue_response:3::2.1</code><br>
     * Same as {@link #APPLICATIONRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_CATALOGUE_RESPONSE_3}
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_poacc_trns_catalogue_response_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:fdc:peppol.eu:poacc:trns:catalogue_response:3", "2.1"), "Peppol Catalogue Response transaction 3.0", Version.parse("4"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:catalogue_only:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:fdc:peppol.eu:poacc:trns:order:3::2.1</code><br>
     * Same as {@link #ORDER_FDC_PEPPOL_EU_POACC_TRNS_ORDER_3}
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_fdc_peppol_eu_poacc_trns_order_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:fdc:peppol.eu:poacc:trns:order:3", "2.1"), "Peppol Order transaction 3.0", Version.parse("4"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:ordering:3", "cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:order_only:3", "cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:advanced_ordering:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:fdc:peppol.eu:poacc:trns:invoice_response:3::2.1</code><br>
     * Same as {@link #APPLICATIONRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_INVOICE_RESPONSE_3}
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_poacc_trns_invoice_response_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:fdc:peppol.eu:poacc:trns:invoice_response:3", "2.1"), "Peppol Invoice Response transaction 3.0", Version.parse("4"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:invoice_response:3", "cenbii-procid-ubl::urn:peppol:france:billing:regulated", "cenbii-procid-ubl::urn:peppol:france:billing:non-regulated")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:fdc:peppol.eu:poacc:trns:punch_out:3::2.1</code><br>
     * Same as {@link #CATALOGUE_FDC_PEPPOL_EU_POACC_TRNS_PUNCH_OUT_3}
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_fdc_peppol_eu_poacc_trns_punch_out_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2", "Catalogue", "urn:fdc:peppol.eu:poacc:trns:punch_out:3", "2.1"), "Peppol Punch Out transaction 3.0", Version.parse("4"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:punch_out:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::OrderResponse##urn:fdc:peppol.eu:poacc:trns:order_response:3::2.1</code><br>
     * Same as {@link #ORDERRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_ORDER_RESPONSE_3}
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_response_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2", "OrderResponse", "urn:fdc:peppol.eu:poacc:trns:order_response:3", "2.1"), "Peppol Order Response transaction 3.0", Version.parse("4"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:ordering:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2::DespatchAdvice##urn:fdc:peppol.eu:poacc:trns:despatch_advice:3::2.1</code><br>
     * Same as {@link #DESPATCHADVICE_FDC_PEPPOL_EU_POACC_TRNS_DESPATCH_ADVICE_3}
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_fdc_peppol_eu_poacc_trns_despatch_advice_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2", "DespatchAdvice", "urn:fdc:peppol.eu:poacc:trns:despatch_advice:3", "2.1"), "Peppol Despatch Advice transaction 3.0", Version.parse("4"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:despatch_advice:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::OrderResponse##urn:fdc:peppol.eu:poacc:trns:order_agreement:3::2.1</code><br>
     * Same as {@link #ORDERRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_ORDER_AGREEMENT_3}
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_agreement_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2", "OrderResponse", "urn:fdc:peppol.eu:poacc:trns:order_agreement:3", "2.1"), "Peppol Order Agreement transaction 3.0", Version.parse("4"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:order_agreement:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:fdc:peppol.eu:poacc:trns:mlr:3::2.1</code><br>
     * Same as {@link #APPLICATIONRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_MLR_3}
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_poacc_trns_mlr_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:fdc:peppol.eu:poacc:trns:mlr:3", "2.1"), "Peppol Message Level Response transaction 3.0", Version.parse("4"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:mlr:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:nen.nl:nlcius:v1.0::2.1</code><br>
     * Same as {@link #INVOICE_CEN_EU_EN16931_2017_COMPLIANT_FDC_NEN_NL_NLCIUS_V1_0}
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_nen_nl_nlcius_v1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:fdc:nen.nl:nlcius:v1.0", "2.1"), "SI-UBL 2.0 Invoice", Version.parse("4"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:fdc:nen.nl:nlcius:v1.0::2.1</code><br>
     * Same as {@link #CREDITNOTE_CEN_EU_EN16931_2017_COMPLIANT_FDC_NEN_NL_NLCIUS_V1_0}
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_nen_nl_nlcius_v1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:fdc:nen.nl:nlcius:v1.0", "2.1"), "SI-UBL 2.0 Credit Note", Version.parse("4"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:sg:3.0::2.1</code><br>
     * Same as {@link #INVOICE_CEN_EU_EN16931_2017_CONFORMANT_FDC_PEPPOL_EU_2017_POACC_BILLING_INTERNATIONAL_SG_3_0}
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_sg_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:sg:3.0", "2.1"), "SG Peppol BIS Billing 3.0 Invoice", Version.parse("4"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::CreditNote##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:sg:3.0::2.1</code><br>
     * Same as {@link #CREDITNOTE_CEN_EU_EN16931_2017_CONFORMANT_FDC_PEPPOL_EU_2017_POACC_BILLING_INTERNATIONAL_SG_3_0}
     * 
     * @deprecated since v6 - this item should not be used to issue new identifiers!
     * @since code list 4
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_sg_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "CreditNote", "urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:sg:3.0", "2.1"), "SG PEPPOL BIS Billing 3.0 Credit Note (invalid)", Version.parse("4"), EPeppolCodeListItemState.DEPRECATED, Version.parse("6"), null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:sg:3.0::2.1</code><br>
     * Same as {@link #CREDITNOTE_CEN_EU_EN16931_2017_CONFORMANT_FDC_PEPPOL_EU_2017_POACC_BILLING_INTERNATIONAL_SG_3_02}
     * 
     * @since code list 6
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_sg_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:sg:3.0", "2.1"), "SG Peppol BIS Billing 3.0 Credit Note", Version.parse("6"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.2::2.1</code><br>
     * Same as {@link #XRECHNUNG_INVOICE_UBL_V12}
     * 
     * @deprecated since v8.3 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     * @since code list 5
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_2__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.2", "2.1"), "XRechnung UBL Invoice V1.2", Version.parse("5"), EPeppolCodeListItemState.REMOVED, Version.parse("8.3"), PDTFactory.createLocalDate(2023, Month.of(5), 24), false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.2::2.1</code><br>
     * Same as {@link #XRECHNUNG_CREDIT_NOTE_UBL_V12}
     * 
     * @deprecated since v8.3 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     * @since code list 5
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_2__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.2", "2.1"), "XRechnung UBL Credit Note V1.2", Version.parse("5"), EPeppolCodeListItemState.REMOVED, Version.parse("8.3"), PDTFactory.createLocalDate(2023, Month.of(5), 24), false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.2::D16B</code><br>
     * Same as {@link #XRECHNUNG_INVOICE_CII_V12}
     * 
     * @deprecated since v8.3 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     * @since code list 5
     */
    @Deprecated(forRemoval = false)
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_2__D16B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.2", "D16B"), "XRechnung CII Invoice V1.2", Version.parse("5"), EPeppolCodeListItemState.REMOVED, Version.parse("8.3"), PDTFactory.createLocalDate(2023, Month.of(5), 24), false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:fdc:www.efaktura.gov.pl:ver1.0:trns:account_corr:ver1.0::2.1</code><br>
     * Same as {@link #CREDITNOTE_FDC_WWW_EFAKTURA_GOV_PL_VER1_0_TRNS_ACCOUNT_CORR_VER1_0}
     * 
     * @since code list 6
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_fdc_www_efaktura_gov_pl_ver1_0_trns_account_corr_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:fdc:www.efaktura.gov.pl:ver1.0:trns:account_corr:ver1.0", "2.1"), "PEF.PL Accounting Note v1", Version.parse("6"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:www.efaktura.gov.pl:ver1.0:account_corr:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0#extended#urn:fdc:www.efaktura.gov.pl:ver1.0::2.1</code><br>
     * Same as {@link #CREDITNOTE_CEN_EU_EN16931_2017_COMPLIANT_FDC_PEPPOL_EU_2017_POACC_BILLING_3_0_EXTENDED_FDC_WWW_EFAKTURA_GOV_PL_VER1_0}
     * 
     * @since code list 6
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_extended_urn_fdc_www_efaktura_gov_pl_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0#extended#urn:fdc:www.efaktura.gov.pl:ver1.0", "2.1"), "PEF.PL Correcting Invoice v1", Version.parse("6"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:www.efaktura.gov.pl:ver1.0:corr_inv:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ReceiptAdvice-2::ReceiptAdvice##urn:fdc:www.efaktura.gov.pl:ver1.0:trns:receipt_advice:ver1.0::2.1</code><br>
     * Same as {@link #RECEIPTADVICE_FDC_WWW_EFAKTURA_GOV_PL_VER1_0_TRNS_RECEIPT_ADVICE_VER1_0}
     * 
     * @since code list 6
     */
    urn_oasis_names_specification_ubl_schema_xsd_ReceiptAdvice_2__ReceiptAdvice__urn_fdc_www_efaktura_gov_pl_ver1_0_trns_receipt_advice_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ReceiptAdvice-2", "ReceiptAdvice", "urn:fdc:www.efaktura.gov.pl:ver1.0:trns:receipt_advice:ver1.0", "2.1"), "PEF.PL Receipt Advice v1", Version.parse("6"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:www.efaktura.gov.pl:ver1.0:receipt_advice:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:aunz:3.0::2.1</code><br>
     * Same as {@link #INVOICE_CEN_EU_EN16931_2017_CONFORMANT_FDC_PEPPOL_EU_2017_POACC_BILLING_INTERNATIONAL_AUNZ_3_0}
     * 
     * @deprecated since v9.2 - this item should not be used to issue new identifiers!
     * @since code list 6
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_aunz_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:aunz:3.0", "2.1"), "AU-NZ Peppol BIS Billing 3.0 Invoice", Version.parse("6"), EPeppolCodeListItemState.DEPRECATED, Version.parse("9.2"), null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:aunz:3.0::2.1</code><br>
     * Same as {@link #CREDITNOTE_CEN_EU_EN16931_2017_CONFORMANT_FDC_PEPPOL_EU_2017_POACC_BILLING_INTERNATIONAL_AUNZ_3_0}
     * 
     * @deprecated since v9.2 - this item should not be used to issue new identifiers!
     * @since code list 6
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_aunz_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:aunz:3.0", "2.1"), "AU-NZ Peppol BIS Billing 3.0 Credit Note", Version.parse("6"), EPeppolCodeListItemState.DEPRECATED, Version.parse("9.2"), null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:selfbilling:international:aunz:3.0::2.1</code><br>
     * Same as {@link #INVOICE_CEN_EU_EN16931_2017_CONFORMANT_FDC_PEPPOL_EU_2017_POACC_SELFBILLING_INTERNATIONAL_AUNZ_3_0}
     * 
     * @deprecated since v9.2 - this item should not be used to issue new identifiers!
     * @since code list 6
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_selfbilling_international_aunz_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:selfbilling:international:aunz:3.0", "2.1"), "AU-NZ Self-Billing 3.0 Invoice", Version.parse("6"), EPeppolCodeListItemState.DEPRECATED, Version.parse("9.2"), null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:selfbilling:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:selfbilling:international:aunz:3.0::2.1</code><br>
     * Same as {@link #CREDITNOTE_CEN_EU_EN16931_2017_CONFORMANT_FDC_PEPPOL_EU_2017_POACC_SELFBILLING_INTERNATIONAL_AUNZ_3_0}
     * 
     * @deprecated since v9.2 - this item should not be used to issue new identifiers!
     * @since code list 6
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_selfbilling_international_aunz_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:selfbilling:international:aunz:3.0", "2.1"), "AU-NZ Self-Billing 3.0 Credit Note", Version.parse("6"), EPeppolCodeListItemState.DEPRECATED, Version.parse("9.2"), null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:selfbilling:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol4a:ver2.0:extended:urn:www.simplerinvoicing.org:si:si-ubl:ver1.2::2.1</code><br>
     * Same as {@link #INVOICE_WWW_CENBII_EU_TRANSACTION_BIITRNS010_VER2_0_EXTENDED_WWW_PEPPOL_EU_BIS_PEPPOL4A_VER2_0_EXTENDED_WWW_SIMPLERINVOICING_ORG_SI_SI_UBL_VER1_2}
     * 
     * @deprecated since v8.7 - this item should not be used to issue new identifiers!<br>Removed per 2024-01-01
     * @since code list 6
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biitrns010_ver2_0_extended_urn_www_peppol_eu_bis_peppol4a_ver2_0_extended_urn_www_simplerinvoicing_org_si_si_ubl_ver1_2__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol4a:ver2.0:extended:urn:www.simplerinvoicing.org:si:si-ubl:ver1.2", "2.1"), "SI-UBL 1.2 Invoice", Version.parse("6"), EPeppolCodeListItemState.REMOVED, Version.parse("8.7"), PDTFactory.createLocalDate(2024, Month.of(1), 1), false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii04:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol3a:ver2.0:extended:urn:www.simplerinvoicing.org:si:si-ubl:ver1.2::2.1</code><br>
     * Same as {@link #ORDER_WWW_CENBII_EU_TRANSACTION_BIITRNS001_VER2_0_EXTENDED_WWW_PEPPOL_EU_BIS_PEPPOL3A_VER2_0_EXTENDED_WWW_SIMPLERINVOICING_ORG_SI_SI_UBL_VER1_2}
     * 
     * @deprecated since v8.7 - this item should not be used to issue new identifiers!<br>Removed per 2024-01-01
     * @since code list 6
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol3a_ver2_0_extended_urn_www_simplerinvoicing_org_si_si_ubl_ver1_2__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol3a:ver2.0:extended:urn:www.simplerinvoicing.org:si:si-ubl:ver1.2", "2.1"), "SI-UBL 1.2 Order", Version.parse("6"), EPeppolCodeListItemState.REMOVED, Version.parse("8.7"), PDTFactory.createLocalDate(2024, Month.of(1), 1), false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii03:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.3::2.1</code><br>
     * Same as {@link #XRECHNUNG_INVOICE_UBL_V13}
     * 
     * @deprecated since v7.1 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     * @since code list 7
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.3", "2.1"), "XRechnung UBL Invoice V1.3", Version.parse("7"), EPeppolCodeListItemState.REMOVED, Version.parse("7.1"), PDTFactory.createLocalDate(2023, Month.of(5), 24), false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.3::2.1</code><br>
     * Same as {@link #XRECHNUNG_CREDIT_NOTE_UBL_V13}
     * 
     * @deprecated since v7.1 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     * @since code list 7
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.3", "2.1"), "XRechnung UBL Credit Note V1.3", Version.parse("7"), EPeppolCodeListItemState.REMOVED, Version.parse("7.1"), PDTFactory.createLocalDate(2023, Month.of(5), 24), false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.3::16B</code><br>
     * Same as {@link #XRECHNUNG_INVOICE_CII_V13}
     * 
     * @deprecated since v7.1 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     * @since code list 7
     */
    @Deprecated(forRemoval = false)
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_3__16B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.3", "16B"), "XRechnung CII Invoice V1.3", Version.parse("7"), EPeppolCodeListItemState.REMOVED, Version.parse("7.1"), PDTFactory.createLocalDate(2023, Month.of(5), 24), false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.0::2.1</code><br>
     * Same as {@link #XRECHNUNG_INVOICE_UBL_V20}
     * 
     * @since code list 7
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.0", "2.1"), "XRechnung UBL Invoice V2.0", Version.parse("7"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.0::2.1</code><br>
     * Same as {@link #XRECHNUNG_CREDIT_NOTE_UBL_V20}
     * 
     * @since code list 7
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.0", "2.1"), "XRechnung UBL Credit Note V2.0", Version.parse("7"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.0::16B</code><br>
     * Same as {@link #XRECHNUNG_INVOICE_CII_V20}
     * 
     * @deprecated since v7.4 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     * @since code list 7
     */
    @Deprecated(forRemoval = false)
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0__16B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.0", "16B"), "XRechnung CII Invoice V2.0 (invalid)", Version.parse("7"), EPeppolCodeListItemState.REMOVED, Version.parse("7.4"), PDTFactory.createLocalDate(2023, Month.of(5), 24), false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.0::D16B</code><br>
     * Same as {@link #XRECHNUNG_INVOICE_CII_V202}
     * 
     * @since code list 7.4
     */
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0__D16B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.0", "D16B"), "XRechnung CII Invoice V2.0", Version.parse("7.4"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.3#conformant#urn:xoev-de:kosit:extension:xrechnung_1.3::2.1</code><br>
     * Same as {@link #XRECHNUNG_EXTENSION_INVOICE_UBL_V13}
     * 
     * @deprecated since v7.1 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     * @since code list 7
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_3_conformant_urn_xoev_de_kosit_extension_xrechnung_1_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.3#conformant#urn:xoev-de:kosit:extension:xrechnung_1.3", "2.1"), "XRechnung UBL Invoice V1.3 Extension", Version.parse("7"), EPeppolCodeListItemState.REMOVED, Version.parse("7.1"), PDTFactory.createLocalDate(2023, Month.of(5), 24), false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.3#conformant#urn:xoev-de:kosit:extension:xrechnung_1.3::2.1</code><br>
     * Same as {@link #XRECHNUNG_EXTENSION_CREDIT_NOTE_UBL_V13}
     * 
     * @deprecated since v7.1 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     * @since code list 7
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_3_conformant_urn_xoev_de_kosit_extension_xrechnung_1_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.3#conformant#urn:xoev-de:kosit:extension:xrechnung_1.3", "2.1"), "XRechnung UBL Credit Note V1.3 Extension", Version.parse("7"), EPeppolCodeListItemState.REMOVED, Version.parse("7.1"), PDTFactory.createLocalDate(2023, Month.of(5), 24), false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.3#conformant#urn:xoev-de:kosit:extension:xrechnung_1.3::16B</code><br>
     * Same as {@link #XRECHNUNG_EXTENSION_INVOICE_CII_V13}
     * 
     * @deprecated since v7.1 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     * @since code list 7
     */
    @Deprecated(forRemoval = false)
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_3_conformant_urn_xoev_de_kosit_extension_xrechnung_1_3__16B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.3#conformant#urn:xoev-de:kosit:extension:xrechnung_1.3", "16B"), "XRechnung CII Invoice V1.3 Extension", Version.parse("7"), EPeppolCodeListItemState.REMOVED, Version.parse("7.1"), PDTFactory.createLocalDate(2023, Month.of(5), 24), false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:nen.nl:nlcius:v1.0#conformant#urn:fdc:nen.nl:gaccount:v1.0::2.1</code><br>
     * Same as {@link #INVOICE_CEN_EU_EN16931_2017_COMPLIANT_FDC_NEN_NL_NLCIUS_V1_0_CONFORMANT_FDC_NEN_NL_GACCOUNT_V1_0}
     * 
     * @since code list 7
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_nen_nl_nlcius_v1_0_conformant_urn_fdc_nen_nl_gaccount_v1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:fdc:nen.nl:nlcius:v1.0#conformant#urn:fdc:nen.nl:gaccount:v1.0", "2.1"), "SI-UBL 2.0 G-Account Extension", Version.parse("7"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:fdc:peppol.eu:poacc:trns:order:3:extended:urn:fdc:anskaffelser.no:2019:ehf:spec:3.0::2.1</code><br>
     * Same as {@link #ORDER_FDC_PEPPOL_EU_POACC_TRNS_ORDER_3_EXTENDED_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_3_0}
     * 
     * @deprecated since v7.3 - this item should not be used to issue new identifiers!
     * @since code list 7
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_fdc_peppol_eu_poacc_trns_order_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:fdc:peppol.eu:poacc:trns:order:3:extended:urn:fdc:anskaffelser.no:2019:ehf:spec:3.0", "2.1"), "EHF Advanced Order Initiation 3.0 (invalid)", Version.parse("7"), EPeppolCodeListItemState.DEPRECATED, Version.parse("7.3"), null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:09:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderChange-2::OrderChange##urn:fdc:anskaffelser.no:2019:ehf:spec:adv-order-change:3.0::2.1</code><br>
     * Same as {@link #ORDERCHANGE_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_ADV_ORDER_CHANGE_3_0}
     * 
     * @deprecated since v7.3 - this item should not be used to issue new identifiers!
     * @since code list 7
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_OrderChange_2__OrderChange__urn_fdc_anskaffelser_no_2019_ehf_spec_adv_order_change_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderChange-2", "OrderChange", "urn:fdc:anskaffelser.no:2019:ehf:spec:adv-order-change:3.0", "2.1"), "EHF Advanced Order Change 3.0 (invalid)", Version.parse("7"), EPeppolCodeListItemState.DEPRECATED, Version.parse("7.3"), null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:09:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2::OrderCancellation##urn:fdc:anskaffelser.no:2019:ehf:spec:adv-order-cancellation:3.0::2.1</code><br>
     * Same as {@link #ORDERCANCELLATION_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_ADV_ORDER_CANCELLATION_3_0}
     * 
     * @deprecated since v7.3 - this item should not be used to issue new identifiers!
     * @since code list 7
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_OrderCancellation_2__OrderCancellation__urn_fdc_anskaffelser_no_2019_ehf_spec_adv_order_cancellation_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2", "OrderCancellation", "urn:fdc:anskaffelser.no:2019:ehf:spec:adv-order-cancellation:3.0", "2.1"), "EHF Advanced Order Cancellation 3.0 (invalid)", Version.parse("7"), EPeppolCodeListItemState.DEPRECATED, Version.parse("7.3"), null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:09:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::OrderResponse##urn:fdc:peppol.eu:poacc:trns:order_response:3:extended:urn:fdc:anskaffelser.no:2019:ehf:spec:3.0::2.1</code><br>
     * Same as {@link #ORDERRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_ORDER_RESPONSE_3_EXTENDED_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_3_0}
     * 
     * @deprecated since v7.3 - this item should not be used to issue new identifiers!
     * @since code list 7
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_response_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2", "OrderResponse", "urn:fdc:peppol.eu:poacc:trns:order_response:3:extended:urn:fdc:anskaffelser.no:2019:ehf:spec:3.0", "2.1"), "EHF Advanced Order Response 3.0 (invalid)", Version.parse("7"), EPeppolCodeListItemState.DEPRECATED, Version.parse("7.3"), null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:09:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017::2.1</code><br>
     * Same as {@link #INVOICE_CEN_EU_EN16931_2017}
     * 
     * @since code list 7
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017", "2.1"), "EN 16931 UBL Invoice", Version.parse("7"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:en16931:any")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017::2.1</code><br>
     * Same as {@link #CREDITNOTE_CEN_EU_EN16931_2017}
     * 
     * @since code list 7
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017", "2.1"), "EN 16931 UBL Credit Note", Version.parse("7"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:en16931:any")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017::D16B</code><br>
     * Same as {@link #CROSSINDUSTRYINVOICE_CEN_EU_EN16931_2017}
     * 
     * @since code list 7
     */
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017__D16B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017", "D16B"), "EN 16931 CII Invoice", Version.parse("7"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:en16931:any")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.0#conformant#urn:xoev-de:kosit:extension:xrechnung_2.0::2.1</code><br>
     * Same as {@link #XRECHNUNG_EXTENSION_INVOICE_UBL_V20}
     * 
     * @since code list 7.1
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0_conformant_urn_xoev_de_kosit_extension_xrechnung_2_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.0#conformant#urn:xoev-de:kosit:extension:xrechnung_2.0", "2.1"), "XRechnung UBL Invoice V2.0 Extension", Version.parse("7.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.0#conformant#urn:xoev-de:kosit:extension:xrechnung_2.0::2.1</code><br>
     * Same as {@link #XRECHNUNG_EXTENSION_CREDIT_NOTE_UBL_V20}
     * 
     * @since code list 7.1
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0_conformant_urn_xoev_de_kosit_extension_xrechnung_2_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.0#conformant#urn:xoev-de:kosit:extension:xrechnung_2.0", "2.1"), "XRechnung UBL Credit Note V2.0 Extension", Version.parse("7.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.0#conformant#urn:xoev-de:kosit:extension:xrechnung_2.0::16B</code><br>
     * Same as {@link #XRECHNUNG_EXTENSION_INVOICE_CII_V20}
     * 
     * @deprecated since v7.4 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     * @since code list 7.1
     */
    @Deprecated(forRemoval = false)
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0_conformant_urn_xoev_de_kosit_extension_xrechnung_2_0__16B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.0#conformant#urn:xoev-de:kosit:extension:xrechnung_2.0", "16B"), "XRechnung CII Invoice V2.0 Extension (invalid)", Version.parse("7.1"), EPeppolCodeListItemState.REMOVED, Version.parse("7.4"), PDTFactory.createLocalDate(2023, Month.of(5), 24), false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.0#conformant#urn:xoev-de:kosit:extension:xrechnung_2.0::D16B</code><br>
     * Same as {@link #XRECHNUNG_EXTENSION_INVOICE_CII_V202}
     * 
     * @since code list 7.4
     */
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0_conformant_urn_xoev_de_kosit_extension_xrechnung_2_0__D16B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.0#conformant#urn:xoev-de:kosit:extension:xrechnung_2.0", "D16B"), "XRechnung CII Invoice V2.0 Extension", Version.parse("7.4"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:fdc:peppol.eu:poacc:trns:order:3:restrictive:urn:www.agid.gov.it:trns:ordine:3.1::2.1</code><br>
     * Same as {@link #ORDER_FDC_PEPPOL_EU_POACC_TRNS_ORDER_3_RESTRICTIVE_WWW_AGID_GOV_IT_TRNS_ORDINE_3_1}
     * 
     * @since code list 7.1
     */
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_fdc_peppol_eu_poacc_trns_order_3_restrictive_urn_www_agid_gov_it_trns_ordine_3_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:fdc:peppol.eu:poacc:trns:order:3:restrictive:urn:www.agid.gov.it:trns:ordine:3.1", "2.1"), "SimpleOrder_IT", Version.parse("7.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:order_only:3", "cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:ordering:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::OrderResponse##urn:fdc:peppol.eu:poacc:trns:order_response:3:restrictive:urn:www.agid.gov.it:trns:risposta_ordine:3.0::2.1</code><br>
     * Same as {@link #ORDERRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_ORDER_RESPONSE_3_RESTRICTIVE_WWW_AGID_GOV_IT_TRNS_RISPOSTA_ORDINE_3_0}
     * 
     * @since code list 7.1
     */
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_response_3_restrictive_urn_www_agid_gov_it_trns_risposta_ordine_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2", "OrderResponse", "urn:fdc:peppol.eu:poacc:trns:order_response:3:restrictive:urn:www.agid.gov.it:trns:risposta_ordine:3.0", "2.1"), "OrderResponse_IT", Version.parse("7.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:ordering:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2::DespatchAdvice##urn:fdc:peppol.eu:poacc:trns:despatch_advice:3:extended:urn:www.agid.gov.it:trns:ddt:3.1::2.1</code><br>
     * Same as {@link #DESPATCHADVICE_FDC_PEPPOL_EU_POACC_TRNS_DESPATCH_ADVICE_3_EXTENDED_WWW_AGID_GOV_IT_TRNS_DDT_3_1}
     * 
     * @since code list 7.2
     */
    urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_fdc_peppol_eu_poacc_trns_despatch_advice_3_extended_urn_www_agid_gov_it_trns_ddt_3_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2", "DespatchAdvice", "urn:fdc:peppol.eu:poacc:trns:despatch_advice:3:extended:urn:www.agid.gov.it:trns:ddt:3.1", "2.1"), "DespatchAdvice_IT", Version.parse("7.2"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:despatch_advice:3")),

    /**
     * <code>urn:kosit:names:spec:peppol-reporting:schema:xsd:Reporting-1::APData##Reporting::1.0</code><br>
     * Same as {@link #APDATA_REPORTING}
     * 
     * @deprecated since v8.8 - this item should not be used to issue new identifiers!<br>Removed per 2024-06-30
     * @since code list 7.3
     */
    @Deprecated(forRemoval = false)
    urn_kosit_names_spec_peppol_reporting_schema_xsd_Reporting_1__APData__Reporting__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:kosit:names:spec:peppol-reporting:schema:xsd:Reporting-1", "APData", "Reporting", "1.0"), "German Peppol Usage Evaluation AP", Version.parse("7.3"), EPeppolCodeListItemState.REMOVED, Version.parse("8.8"), PDTFactory.createLocalDate(2024, Month.of(6), 30), false, false, -1, null, new CommonsArrayList<>("cenbii-procid-ubl::urn:kosit:profile:reporting:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:fdc:peppol.eu:poacc:trns:order:3:extended:urn:fdc:anskaffelser.no:2019:ehf:spec:3.0::2.2</code><br>
     * Same as {@link #ORDER_FDC_PEPPOL_EU_POACC_TRNS_ORDER_3_EXTENDED_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_3_02}
     * 
     * @since code list 7.3
     */
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_fdc_peppol_eu_poacc_trns_order_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:fdc:peppol.eu:poacc:trns:order:3:extended:urn:fdc:anskaffelser.no:2019:ehf:spec:3.0", "2.2"), "EHF Advanced Order Initiation 3.0", Version.parse("7.3"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:09:1.0", "cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:02:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderChange-2::OrderChange##urn:fdc:anskaffelser.no:2019:ehf:spec:adv-order-change:3.0::2.2</code><br>
     * Same as {@link #ORDERCHANGE_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_ADV_ORDER_CHANGE_3_02}
     * 
     * @since code list 7.3
     */
    urn_oasis_names_specification_ubl_schema_xsd_OrderChange_2__OrderChange__urn_fdc_anskaffelser_no_2019_ehf_spec_adv_order_change_3_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderChange-2", "OrderChange", "urn:fdc:anskaffelser.no:2019:ehf:spec:adv-order-change:3.0", "2.2"), "EHF Advanced Order Change 3.0", Version.parse("7.3"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:09:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2::OrderCancellation##urn:fdc:anskaffelser.no:2019:ehf:spec:adv-order-cancellation:3.0::2.2</code><br>
     * Same as {@link #ORDERCANCELLATION_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_ADV_ORDER_CANCELLATION_3_02}
     * 
     * @since code list 7.3
     */
    urn_oasis_names_specification_ubl_schema_xsd_OrderCancellation_2__OrderCancellation__urn_fdc_anskaffelser_no_2019_ehf_spec_adv_order_cancellation_3_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2", "OrderCancellation", "urn:fdc:anskaffelser.no:2019:ehf:spec:adv-order-cancellation:3.0", "2.2"), "EHF Advanced Order Cancellation 3.0", Version.parse("7.3"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:09:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::OrderResponse##urn:fdc:peppol.eu:poacc:trns:order_response:3:extended:urn:fdc:anskaffelser.no:2019:ehf:spec:3.0::2.2</code><br>
     * Same as {@link #ORDERRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_ORDER_RESPONSE_3_EXTENDED_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_3_02}
     * 
     * @since code list 7.3
     */
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_response_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2", "OrderResponse", "urn:fdc:peppol.eu:poacc:trns:order_response:3:extended:urn:fdc:anskaffelser.no:2019:ehf:spec:3.0", "2.2"), "EHF Advanced Order Response 3.0", Version.parse("7.3"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:09:1.0", "cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:02:1.0")),

    /**
     * <code>urn:iso:std:iso:20022:tech:xsd:pain.001.001.03::Document##urn:fdc:bits.no:2017:iso20022:1.5::03</code><br>
     * Same as {@link #DOCUMENT_FDC_BITS_NO_2017_ISO20022_1_5}
     * 
     * @since code list 8.0
     */
    urn_iso_std_iso_20022_tech_xsd_pain_001_001_03__Document__urn_fdc_bits_no_2017_iso20022_1_5__03("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:iso:std:iso:20022:tech:xsd:pain.001.001.03", "Document", "urn:fdc:bits.no:2017:iso20022:1.5", "03"), "EHF Payment initiation (Profile 01) (Pain.001 Message)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:01:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:03:1.0")),

    /**
     * <code>urn:fdc:difi.no:2017:payment:extras-1::ReceptionAcknowledgement##urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:pain.002.001.03:restricted:urn:fdc:bits.no:2017:iso20022:1.5::1.0</code><br>
     * Same as {@link #RECEPTIONACKNOWLEDGEMENT_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_PAIN_002_001_03_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5}
     * 
     * @since code list 8.0
     */
    urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_pain_002_001_03_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:difi.no:2017:payment:extras-1", "ReceptionAcknowledgement", "urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:pain.002.001.03:restricted:urn:fdc:bits.no:2017:iso20022:1.5", "1.0"), "EHF Payment initiation (Profile 01) (Pain.002 Reception Acknowledgement)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:01:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:02:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:03:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:04:1.0")),

    /**
     * <code>urn:fdc:difi.no:2017:payment:extras-1::HandlingException##urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:pain.002.001.03:restricted:urn:fdc:bits.no:2017:iso20022:1.5::1.0</code><br>
     * Same as {@link #HANDLINGEXCEPTION_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_PAIN_002_001_03_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5}
     * 
     * @since code list 8.0
     */
    urn_fdc_difi_no_2017_payment_extras_1__HandlingException__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_pain_002_001_03_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:difi.no:2017:payment:extras-1", "HandlingException", "urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:pain.002.001.03:restricted:urn:fdc:bits.no:2017:iso20022:1.5", "1.0"), "EHF Payment initiation (Profile 01) (Pain.002 Handling Exception)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:01:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:02:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:03:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:04:1.0")),

    /**
     * <code>urn:fdc:difi.no:2017:payment:extras-1::ReceptionAcknowledgement##urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:camt.054.001.02:restricted:urn:fdc:bits.no:2017:iso20022:1.5::1.0</code><br>
     * Same as {@link #RECEPTIONACKNOWLEDGEMENT_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_054_001_02_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5}
     * 
     * @since code list 8.0
     */
    urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_054_001_02_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:difi.no:2017:payment:extras-1", "ReceptionAcknowledgement", "urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:camt.054.001.02:restricted:urn:fdc:bits.no:2017:iso20022:1.5", "1.0"), "EHF Payment initiation (Profile 01) (Camt.054 Reception Acknowledgement)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:01:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:03:1.0")),

    /**
     * <code>urn:fdc:difi.no:2017:payment:extras-1::HandlingException##urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:camt.054.001.02:restricted:urn:fdc:bits.no:2017:iso20022:1.5::1.0</code><br>
     * Same as {@link #HANDLINGEXCEPTION_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_054_001_02_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5}
     * 
     * @since code list 8.0
     */
    urn_fdc_difi_no_2017_payment_extras_1__HandlingException__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_054_001_02_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:difi.no:2017:payment:extras-1", "HandlingException", "urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:camt.054.001.02:restricted:urn:fdc:bits.no:2017:iso20022:1.5", "1.0"), "EHF Payment initiation (Profile 01) (Camt.054 Handling Exception)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:01:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:03:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:09:1.0")),

    /**
     * <code>urn:fdc:difi.no:2017:payment:extras-1::ReceptionAcknowledgement##urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:pain.001.001.03:restricted:urn:fdc:bits.no:2017:iso20022:1.5::1.0</code><br>
     * Same as {@link #RECEPTIONACKNOWLEDGEMENT_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_PAIN_001_001_03_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5}
     * 
     * @since code list 8.0
     */
    urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_pain_001_001_03_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:difi.no:2017:payment:extras-1", "ReceptionAcknowledgement", "urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:pain.001.001.03:restricted:urn:fdc:bits.no:2017:iso20022:1.5", "1.0"), "EHF Payment initiation (Profile 01) (Pain.001 Reception Acknowledgement)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:01:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:03:1.0")),

    /**
     * <code>urn:fdc:difi.no:2017:payment:extras-1::HandlingException##urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:pain.001.001.03:restricted:urn:fdc:bits.no:2017:iso20022:1.5::1.0</code><br>
     * Same as {@link #HANDLINGEXCEPTION_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_PAIN_001_001_03_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5}
     * 
     * @since code list 8.0
     */
    urn_fdc_difi_no_2017_payment_extras_1__HandlingException__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_pain_001_001_03_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:difi.no:2017:payment:extras-1", "HandlingException", "urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:pain.001.001.03:restricted:urn:fdc:bits.no:2017:iso20022:1.5", "1.0"), "EHF Payment initiation (Profile 01) (Pain.001 Handling Exception)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:01:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:03:1.0")),

    /**
     * <code>urn:iso:std:iso:20022:tech:xsd:pain.002.001.03::Document##urn:fdc:bits.no:2017:iso20022:1.5::03</code><br>
     * Same as {@link #DOCUMENT_FDC_BITS_NO_2017_ISO20022_1_52}
     * 
     * @since code list 8.0
     */
    urn_iso_std_iso_20022_tech_xsd_pain_002_001_03__Document__urn_fdc_bits_no_2017_iso20022_1_5__03("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:iso:std:iso:20022:tech:xsd:pain.002.001.03", "Document", "urn:fdc:bits.no:2017:iso20022:1.5", "03"), "EHF Payment initiation (Profile 01) (Pain.002 Message)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:01:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:02:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:03:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:04:1.0")),

    /**
     * <code>urn:iso:std:iso:20022:tech:xsd:camt.054.001.02::Document##urn:fdc:bits.no:2017:iso20022:1.5::02</code><br>
     * Same as {@link #DOCUMENT_FDC_BITS_NO_2017_ISO20022_1_53}
     * 
     * @since code list 8.0
     */
    urn_iso_std_iso_20022_tech_xsd_camt_054_001_02__Document__urn_fdc_bits_no_2017_iso20022_1_5__02("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:iso:std:iso:20022:tech:xsd:camt.054.001.02", "Document", "urn:fdc:bits.no:2017:iso20022:1.5", "02"), "EHF Payment initiation (Profile 01) (Camt.054 Message)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:01:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:03:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:09:1.0")),

    /**
     * <code>urn:iso:std:iso:20022:tech:xsd:camt.055.001.01::Document##urn:fdc:bits.no:2017:iso20022:1.5::01</code><br>
     * Same as {@link #DOCUMENT_FDC_BITS_NO_2017_ISO20022_1_54}
     * 
     * @since code list 8.0
     */
    urn_iso_std_iso_20022_tech_xsd_camt_055_001_01__Document__urn_fdc_bits_no_2017_iso20022_1_5__01("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:iso:std:iso:20022:tech:xsd:camt.055.001.01", "Document", "urn:fdc:bits.no:2017:iso20022:1.5", "01"), "EHF Cancellation of General Credit transfer Initiation (Profile 02) (Camt.055 Message)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:02:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:04:1.0")),

    /**
     * <code>urn:fdc:difi.no:2017:payment:extras-1::ReceptionAcknowledgement##urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:camt.029.001.03:restricted:urn:fdc:bits.no:2017:iso20022:1.5::1.0</code><br>
     * Same as {@link #RECEPTIONACKNOWLEDGEMENT_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_029_001_03_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5}
     * 
     * @since code list 8.0
     */
    urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_029_001_03_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:difi.no:2017:payment:extras-1", "ReceptionAcknowledgement", "urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:camt.029.001.03:restricted:urn:fdc:bits.no:2017:iso20022:1.5", "1.0"), "EHF Cancellation of General Credit transfer Initiation (Profile 02) (Camt.029 Reception Acknowledgement)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:02:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:04:1.0")),

    /**
     * <code>urn:fdc:difi.no:2017:payment:extras-1::HandlingException##urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:camt.029.001.03:restricted:urn:fdc:bits.no:2017:iso20022:1.5::1.0</code><br>
     * Same as {@link #HANDLINGEXCEPTION_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_029_001_03_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5}
     * 
     * @since code list 8.0
     */
    urn_fdc_difi_no_2017_payment_extras_1__HandlingException__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_029_001_03_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:difi.no:2017:payment:extras-1", "HandlingException", "urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:camt.029.001.03:restricted:urn:fdc:bits.no:2017:iso20022:1.5", "1.0"), "EHF Cancellation of General Credit transfer Initiation (Profile 02) (Camt.029 Handling Exception)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:02:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:04:1.0")),

    /**
     * <code>urn:fdc:difi.no:2017:payment:extras-1::ReceptionAcknowledgement##urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:camt.055.001.01:restricted:urn:fdc:bits.no:2017:iso20022:1.5::1.0</code><br>
     * Same as {@link #RECEPTIONACKNOWLEDGEMENT_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_055_001_01_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5}
     * 
     * @since code list 8.0
     */
    urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_055_001_01_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:difi.no:2017:payment:extras-1", "ReceptionAcknowledgement", "urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:camt.055.001.01:restricted:urn:fdc:bits.no:2017:iso20022:1.5", "1.0"), "EHF Cancellation of General Credit transfer Initiation (Profile 02) (Camt.055 Reception Acknowledgement)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:02:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:04:1.0")),

    /**
     * <code>urn:fdc:difi.no:2017:payment:extras-1::HandlingException##urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:camt.055.001.01:restricted:urn:fdc:bits.no:2017:iso20022:1.5::1.0</code><br>
     * Same as {@link #HANDLINGEXCEPTION_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_055_001_01_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5}
     * 
     * @since code list 8.0
     */
    urn_fdc_difi_no_2017_payment_extras_1__HandlingException__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_055_001_01_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:difi.no:2017:payment:extras-1", "HandlingException", "urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:camt.055.001.01:restricted:urn:fdc:bits.no:2017:iso20022:1.5", "1.0"), "EHF Cancellation of General Credit transfer Initiation (Profile 02) (Camt.055 Handling Exception)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:02:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:04:1.0")),

    /**
     * <code>urn:iso:std:iso:20022:tech:xsd:camt.029.001.03::Document##urn:fdc:bits.no:2017:iso20022:1.5::03</code><br>
     * Same as {@link #DOCUMENT_FDC_BITS_NO_2017_ISO20022_1_55}
     * 
     * @since code list 8.0
     */
    urn_iso_std_iso_20022_tech_xsd_camt_029_001_03__Document__urn_fdc_bits_no_2017_iso20022_1_5__03("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:iso:std:iso:20022:tech:xsd:camt.029.001.03", "Document", "urn:fdc:bits.no:2017:iso20022:1.5", "03"), "EHF Cancellation of General Credit transfer Initiation (Profile 02) (Camt.029 Message)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:02:1.0", "cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:04:1.0")),

    /**
     * <code>urn:fdc:difi.no:2017:payment:extras-1::ReceptionAcknowledgement##urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:urn:iso:std:iso:20022:tech:xsd:camt.054.001.02:restricted:urn:fdc:bits.no:2017:iso20022:1.5::1.0</code><br>
     * Same as {@link #RECEPTIONACKNOWLEDGEMENT_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_054_001_02_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_52}
     * 
     * @since code list 8.0
     */
    urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_urn_iso_std_iso_20022_tech_xsd_camt_054_001_02_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:difi.no:2017:payment:extras-1", "ReceptionAcknowledgement", "urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:urn:iso:std:iso:20022:tech:xsd:camt.054.001.02:restricted:urn:fdc:bits.no:2017:iso20022:1.5", "1.0"), "EHF Accounting/General Ledger/cash management (Profile 09) (Camt.054 Reception Acknowledgement)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:09:1.0")),

    /**
     * <code>urn:iso:std:iso:20022:tech:xsd:camt.053.001.02::Document##urn:fdc:bits.no:2017:iso20022:1.5::02</code><br>
     * Same as {@link #DOCUMENT_FDC_BITS_NO_2017_ISO20022_1_56}
     * 
     * @since code list 8.0
     */
    urn_iso_std_iso_20022_tech_xsd_camt_053_001_02__Document__urn_fdc_bits_no_2017_iso20022_1_5__02("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:iso:std:iso:20022:tech:xsd:camt.053.001.02", "Document", "urn:fdc:bits.no:2017:iso20022:1.5", "02"), "EHF Payment Profile 10 (B2C account statement)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:10:1.0")),

    /**
     * <code>urn:fdc:difi.no:2017:payment:extras-1::ReceptionAcknowledgement##urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:camt.053.001.02:restricted:urn:fdc:bits.no:2017:iso20022:1.5::1.0</code><br>
     * Same as {@link #RECEPTIONACKNOWLEDGEMENT_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_053_001_02_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5}
     * 
     * @since code list 8.0
     */
    urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_053_001_02_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:difi.no:2017:payment:extras-1", "ReceptionAcknowledgement", "urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:camt.053.001.02:restricted:urn:fdc:bits.no:2017:iso20022:1.5", "1.0"), "EHF Payment Profile 10 (Camt.053 Reception Acknowledgement)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:10:1.0")),

    /**
     * <code>urn:fdc:difi.no:2017:payment:extras-1::HandlingException##urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:camt.053.001.02:restricted:urn:fdc:bits.no:2017:iso20022:1.5::1.0</code><br>
     * Same as {@link #HANDLINGEXCEPTION_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_053_001_02_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5}
     * 
     * @since code list 8.0
     */
    urn_fdc_difi_no_2017_payment_extras_1__HandlingException__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_053_001_02_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:difi.no:2017:payment:extras-1", "HandlingException", "urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:camt.053.001.02:restricted:urn:fdc:bits.no:2017:iso20022:1.5", "1.0"), "EHF Payment Profile 10 (Camt.053 Handling Exception)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:10:1.0")),

    /**
     * <code>urn:iso:std:iso:20022:tech:xsd:camt.052.001.02::Document##urn:fdc:bits.no:2017:iso20022:1.5::02</code><br>
     * Same as {@link #DOCUMENT_FDC_BITS_NO_2017_ISO20022_1_57}
     * 
     * @since code list 8.0
     */
    urn_iso_std_iso_20022_tech_xsd_camt_052_001_02__Document__urn_fdc_bits_no_2017_iso20022_1_5__02("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:iso:std:iso:20022:tech:xsd:camt.052.001.02", "Document", "urn:fdc:bits.no:2017:iso20022:1.5", "02"), "EHF Payment Profile 11 (B2C account report)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:11:1.0")),

    /**
     * <code>urn:fdc:difi.no:2017:payment:extras-1::ReceptionAcknowledgement##urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:camt.052.001.02:restricted:urn:fdc:bits.no:2017:iso20022:1.5::1.0</code><br>
     * Same as {@link #RECEPTIONACKNOWLEDGEMENT_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_052_001_02_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5}
     * 
     * @since code list 8.0
     */
    urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_052_001_02_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:difi.no:2017:payment:extras-1", "ReceptionAcknowledgement", "urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:camt.052.001.02:restricted:urn:fdc:bits.no:2017:iso20022:1.5", "1.0"), "EHF Payment Profile 11 (Camt.052 Reception Acknowledgement)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:11:1.0")),

    /**
     * <code>urn:fdc:difi.no:2017:payment:extras-1::HandlingException##urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:camt.052.001.02:restricted:urn:fdc:bits.no:2017:iso20022:1.5::1.0</code><br>
     * Same as {@link #HANDLINGEXCEPTION_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_052_001_02_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5}
     * 
     * @since code list 8.0
     */
    urn_fdc_difi_no_2017_payment_extras_1__HandlingException__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_052_001_02_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:difi.no:2017:payment:extras-1", "HandlingException", "urn:fdc:difi.no:2017:payment:handling:1.0:for:urn:iso:std:iso:20022:tech:xsd:camt.052.001.02:restricted:urn:fdc:bits.no:2017:iso20022:1.5", "1.0"), "EHF Payment Profile 11 (Camt.052 Handling Exception)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Norwegian Payment", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:bits.no:2017:profile:11:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Enquiry-2::Enquiry##urn:fdc:peppol.eu:prac:trns:t007:1.0::2.2</code><br>
     * Same as {@link #ENQUIRY_FDC_PEPPOL_EU_PRAC_TRNS_T007_1_0}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Enquiry_2__Enquiry__urn_fdc_peppol_eu_prac_trns_t007_1_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Enquiry-2", "Enquiry", "urn:fdc:peppol.eu:prac:trns:t007:1.0", "2.2"), "Peppol Tendering Questions V1.0", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p004:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:EnquiryResponse-2::EnquiryResponse##urn:fdc:peppol.eu:prac:trns:t008:1.0::2.2</code><br>
     * Same as {@link #ENQUIRYRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T008_1_0}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_EnquiryResponse_2__EnquiryResponse__urn_fdc_peppol_eu_prac_trns_t008_1_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:EnquiryResponse-2", "EnquiryResponse", "urn:fdc:peppol.eu:prac:trns:t008:1.0", "2.2"), "Peppol Tendering Answers V1.0", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p004:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Enquiry-2::Enquiry##urn:fdc:peppol.eu:prac:trns:t009:1.0::2.2</code><br>
     * Same as {@link #ENQUIRY_FDC_PEPPOL_EU_PRAC_TRNS_T009_1_0}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Enquiry_2__Enquiry__urn_fdc_peppol_eu_prac_trns_t009_1_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Enquiry-2", "Enquiry", "urn:fdc:peppol.eu:prac:trns:t009:1.0", "2.2"), "Peppol Tender Clarification Request V1.0", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p005:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:EnquiryResponse-2::EnquiryResponse##urn:fdc:peppol.eu:prac:trns:t010:1.0::2.2</code><br>
     * Same as {@link #ENQUIRYRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T010_1_0}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_EnquiryResponse_2__EnquiryResponse__urn_fdc_peppol_eu_prac_trns_t010_1_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:EnquiryResponse-2", "EnquiryResponse", "urn:fdc:peppol.eu:prac:trns:t010:1.0", "2.2"), "Peppol Tender Clarification Response V1.0", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p005:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:www.cenbii.eu:transaction:biitrns019:ver2.0:extended:urn:www.peppol.eu:bis:peppol1a:ver2.0:extended:urn:www.difi.no:ehf:katalog:ver1.0::2.1</code><br>
     * Same as {@link #CATALOGUE_WWW_CENBII_EU_TRANSACTION_BIITRNS019_VER2_0_EXTENDED_WWW_PEPPOL_EU_BIS_PEPPOL1A_VER2_0_EXTENDED_WWW_DIFI_NO_EHF_KATALOG_VER1_0}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_www_cenbii_eu_transaction_biitrns019_ver2_0_extended_urn_www_peppol_eu_bis_peppol1a_ver2_0_extended_urn_www_difi_no_ehf_katalog_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2", "Catalogue", "urn:www.cenbii.eu:transaction:biitrns019:ver2.0:extended:urn:www.peppol.eu:bis:peppol1a:ver2.0:extended:urn:www.difi.no:ehf:katalog:ver1.0", "2.1"), "EHF Catalogue 1.0 (Profile 1A) (Catalogue)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii01:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.cenbii.eu:transaction:biitrns058:ver2.0:extended:urn:www.difi.no:ehf:katalogbekreftelse:ver1.0::2.1</code><br>
     * Same as {@link #APPLICATIONRESPONSE_WWW_CENBII_EU_TRANSACTION_BIITRNS058_VER2_0_EXTENDED_WWW_DIFI_NO_EHF_KATALOGBEKREFTELSE_VER1_0}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biitrns058_ver2_0_extended_urn_www_difi_no_ehf_katalogbekreftelse_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:www.cenbii.eu:transaction:biitrns058:ver2.0:extended:urn:www.difi.no:ehf:katalogbekreftelse:ver1.0", "2.1"), "EHF Catalogue 1.0 (Profile 1A) (Catalogue Response)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii01:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0:extended:urn:www.difi.no:ehf:ordre:ver1.0::2.1</code><br>
     * Same as {@link #ORDER_WWW_CENBII_EU_TRANSACTION_BIITRNS001_VER2_0_EXTENDED_WWW_PEPPOL_EU_BIS_PEPPOL28A_VER1_0_EXTENDED_WWW_DIFI_NO_EHF_ORDRE_VER1_0}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0_extended_urn_www_difi_no_ehf_ordre_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0:extended:urn:www.difi.no:ehf:ordre:ver1.0", "2.1"), "EHF Ordering 1.0 (Profile 28A) (Order)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii28:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::OrderResponse##urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0:extended:urn:www.difi.no:ehf:ordrebekreftelse:ver1.0::2.1</code><br>
     * Same as {@link #ORDERRESPONSE_WWW_CENBII_EU_TRANSACTION_BIITRNS076_VER2_0_EXTENDED_WWW_PEPPOL_EU_BIS_PEPPOL28A_VER1_0_EXTENDED_WWW_DIFI_NO_EHF_ORDREBEKREFTELSE_VER1_0}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0_extended_urn_www_difi_no_ehf_ordrebekreftelse_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2", "OrderResponse", "urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0:extended:urn:www.difi.no:ehf:ordrebekreftelse:ver1.0", "2.1"), "EHF Ordering 1.0 (Profile 28A) (Order Response)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii28:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2::DespatchAdvice##urn:www.cenbii.eu:transaction:biitrns016:ver1.0:extended:urn:www.peppol.eu:bis:peppol30a:ver1.0:extended:urn:www.difi.no:ehf:pakkseddel:ver1.0::2.1</code><br>
     * Same as {@link #DESPATCHADVICE_WWW_CENBII_EU_TRANSACTION_BIITRNS016_VER1_0_EXTENDED_WWW_PEPPOL_EU_BIS_PEPPOL30A_VER1_0_EXTENDED_WWW_DIFI_NO_EHF_PAKKSEDDEL_VER1_0}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_www_cenbii_eu_transaction_biitrns016_ver1_0_extended_urn_www_peppol_eu_bis_peppol30a_ver1_0_extended_urn_www_difi_no_ehf_pakkseddel_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2", "DespatchAdvice", "urn:www.cenbii.eu:transaction:biitrns016:ver1.0:extended:urn:www.peppol.eu:bis:peppol30a:ver1.0:extended:urn:www.difi.no:ehf:pakkseddel:ver1.0", "2.1"), "EHF Despatch Advise 1.0 (Profile 30) (Despatch Advice)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii30:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Reminder-2::Reminder##urn:www.cenbii.eu:transaction:biicoretrdm017:ver1.0:#urn:www.cenbii.eu:profile:biixy:ver1.0#urn:www.difi.no:ehf:purring:ver1::2.0</code><br>
     * Same as {@link #REMINDER_WWW_CENBII_EU_TRANSACTION_BIICORETRDM017_VER1_0__WWW_CENBII_EU_PROFILE_BIIXY_VER1_0_WWW_DIFI_NO_EHF_PURRING_VER1}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Reminder_2__Reminder__urn_www_cenbii_eu_transaction_biicoretrdm017_ver1_0__urn_www_cenbii_eu_profile_biixy_ver1_0_urn_www_difi_no_ehf_purring_ver1__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Reminder-2", "Reminder", "urn:www.cenbii.eu:transaction:biicoretrdm017:ver1.0:#urn:www.cenbii.eu:profile:biixy:ver1.0#urn:www.difi.no:ehf:purring:ver1", "2.0"), "EHF Reminder 1.1 (Profile XY) (Reminder)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:biixy:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:fdc:peppol.eu:poacc:trns:catalogue:3:extended:urn:fdc:anskaffelser.no:2019:ehf:spec:3.0::2.2</code><br>
     * Same as {@link #CATALOGUE_FDC_PEPPOL_EU_POACC_TRNS_CATALOGUE_3_EXTENDED_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_3_0}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_fdc_peppol_eu_poacc_trns_catalogue_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2", "Catalogue", "urn:fdc:peppol.eu:poacc:trns:catalogue:3:extended:urn:fdc:anskaffelser.no:2019:ehf:spec:3.0", "2.2"), "EHF Catalogue 3.0 (Catalogue)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:fdc:peppol.eu:poacc:trns:catalogue_response:3:extended:urn:fdc:anskaffelser.no:2019:ehf:spec:3.0::2.2</code><br>
     * Same as {@link #APPLICATIONRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_CATALOGUE_RESPONSE_3_EXTENDED_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_3_0}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_poacc_trns_catalogue_response_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:fdc:peppol.eu:poacc:trns:catalogue_response:3:extended:urn:fdc:anskaffelser.no:2019:ehf:spec:3.0", "2.2"), "EHF Catalogue 3.0 (Catalogue Response)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::OrderResponse##urn:fdc:peppol.eu:poacc:trns:order_agreement:3:extended:urn:fdc:anskaffelser.no:2019:ehf:spec:3.0::2.2</code><br>
     * Same as {@link #ORDERRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_ORDER_AGREEMENT_3_EXTENDED_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_3_0}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_agreement_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2", "OrderResponse", "urn:fdc:peppol.eu:poacc:trns:order_agreement:3:extended:urn:fdc:anskaffelser.no:2019:ehf:spec:3.0", "2.2"), "EHF Order Agreement 3.0 (Order Agreement)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:03:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2::DespatchAdvice##urn:fdc:peppol.eu:poacc:trns:despatch_advice:3:extended:urn:fdc:anskaffelser.no:2019:ehf:spec:3.0::2.2</code><br>
     * Same as {@link #DESPATCHADVICE_FDC_PEPPOL_EU_POACC_TRNS_DESPATCH_ADVICE_3_EXTENDED_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_3_0}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_fdc_peppol_eu_poacc_trns_despatch_advice_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2", "DespatchAdvice", "urn:fdc:peppol.eu:poacc:trns:despatch_advice:3:extended:urn:fdc:anskaffelser.no:2019:ehf:spec:3.0", "2.2"), "EHF Despatch Advice 3.0 (Despatch Advice)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:05:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0#conformant#urn:fdc:anskaffelser.no:2019:ehf:reminder:3.0::2.2</code><br>
     * Same as {@link #INVOICE_CEN_EU_EN16931_2017_COMPLIANT_FDC_PEPPOL_EU_2017_POACC_BILLING_3_0_CONFORMANT_FDC_ANSKAFFELSER_NO_2019_EHF_REMINDER_3_0}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_conformant_urn_fdc_anskaffelser_no_2019_ehf_reminder_3_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0#conformant#urn:fdc:anskaffelser.no:2019:ehf:reminder:3.0", "2.2"), "EHF Reminder 3.0 (Reminder)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:06:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:fdc:anskaffelser.no:2019:ehf:spec:payment-request:3.0::2.2</code><br>
     * Same as {@link #INVOICE_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_PAYMENT_REQUEST_3_0}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_fdc_anskaffelser_no_2019_ehf_spec_payment_request_3_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:fdc:anskaffelser.no:2019:ehf:spec:payment-request:3.0", "2.2"), "EHF Payment Request 3.0 (Payment Request)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:07:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0#conformant#urn:fdc:anskaffelser.no:2019:ehf:forward-billing:3.0::2.2</code><br>
     * Same as {@link #INVOICE_CEN_EU_EN16931_2017_COMPLIANT_FDC_PEPPOL_EU_2017_POACC_BILLING_3_0_CONFORMANT_FDC_ANSKAFFELSER_NO_2019_EHF_FORWARD_BILLING_3_0}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_conformant_urn_fdc_anskaffelser_no_2019_ehf_forward_billing_3_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0#conformant#urn:fdc:anskaffelser.no:2019:ehf:forward-billing:3.0", "2.2"), "EHF Forward Billing 3.0 (Invoice)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:08:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0#conformant#urn:fdc:anskaffelser.no:2019:ehf:forward-billing:3.0::2.2</code><br>
     * Same as {@link #CREDITNOTE_CEN_EU_EN16931_2017_COMPLIANT_FDC_PEPPOL_EU_2017_POACC_BILLING_3_0_CONFORMANT_FDC_ANSKAFFELSER_NO_2019_EHF_FORWARD_BILLING_3_0}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_conformant_urn_fdc_anskaffelser_no_2019_ehf_forward_billing_3_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0#conformant#urn:fdc:anskaffelser.no:2019:ehf:forward-billing:3.0", "2.2"), "EHF Forward Billing 3.0 (Credit Note)", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:anskaffelser.no:2019:ehf:postaward:g3:08:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.1::2.1</code><br>
     * Same as {@link #XRECHNUNG_INVOICE_UBL_V21}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.1", "2.1"), "XRechnung UBL Invoice V2.1", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.1::2.1</code><br>
     * Same as {@link #XRECHNUNG_CREDIT_NOTE_UBL_V21}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.1", "2.1"), "XRechnung UBL Credit Note V2.1", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.1::D16B</code><br>
     * Same as {@link #XRECHNUNG_INVOICE_CII_V21}
     * 
     * @since code list 8.0
     */
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_1__D16B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.1", "D16B"), "XRechnung CII Invoice V2.1", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.1#conformant#urn:xoev-de:kosit:extension:xrechnung_2.1::2.1</code><br>
     * Same as {@link #XRECHNUNG_EXTENSION_INVOICE_UBL_V21}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_1_conformant_urn_xoev_de_kosit_extension_xrechnung_2_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.1#conformant#urn:xoev-de:kosit:extension:xrechnung_2.1", "2.1"), "XRechnung UBL Invoice V2.1 Extension", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.1#conformant#urn:xoev-de:kosit:extension:xrechnung_2.1::2.1</code><br>
     * Same as {@link #XRECHNUNG_EXTENSION_CREDIT_NOTE_UBL_V21}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_1_conformant_urn_xoev_de_kosit_extension_xrechnung_2_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.1#conformant#urn:xoev-de:kosit:extension:xrechnung_2.1", "2.1"), "XRechnung UBL Credit Note V2.1 Extension", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.1#conformant#urn:xoev-de:kosit:extension:xrechnung_2.1::D16B</code><br>
     * Same as {@link #XRECHNUNG_EXTENSION_INVOICE_CII_V21}
     * 
     * @since code list 8.0
     */
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_1_conformant_urn_xoev_de_kosit_extension_xrechnung_2_1__D16B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.1#conformant#urn:xoev-de:kosit:extension:xrechnung_2.1", "D16B"), "XRechnung CII Invoice V2.1 Extension", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0#extended#urn:fdc:www.efaktura.gov.pl:ver2.0::2.1</code><br>
     * Same as {@link #INVOICE_CEN_EU_EN16931_2017_COMPLIANT_FDC_PEPPOL_EU_2017_POACC_BILLING_3_0_EXTENDED_FDC_WWW_EFAKTURA_GOV_PL_VER2_0}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_extended_urn_fdc_www_efaktura_gov_pl_ver2_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0#extended#urn:fdc:www.efaktura.gov.pl:ver2.0", "2.1"), "PL Faktura specjalizowana ver. 1.4", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:www.efaktura.gov.pl:ver2.0:plinv:ver1.4")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0#extended#urn:fdc:www.efaktura.gov.pl:ver2.0::2.1</code><br>
     * Same as {@link #CREDITNOTE_CEN_EU_EN16931_2017_COMPLIANT_FDC_PEPPOL_EU_2017_POACC_BILLING_3_0_EXTENDED_FDC_WWW_EFAKTURA_GOV_PL_VER2_0}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_extended_urn_fdc_www_efaktura_gov_pl_ver2_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0#extended#urn:fdc:www.efaktura.gov.pl:ver2.0", "2.1"), "PL Faktura koryguj\u0105ca ver. 4.0", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:www.efaktura.gov.pl:ver2.0:corr_inv:ver4.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:SelfBilledCreditNote-2::SelfBilledCreditNote##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0#extended#urn:fdc:www.efaktura.gov.pl:ver2.0::2.1</code><br>
     * Same as {@link #SELFBILLEDCREDITNOTE_CEN_EU_EN16931_2017_COMPLIANT_FDC_PEPPOL_EU_2017_POACC_BILLING_3_0_EXTENDED_FDC_WWW_EFAKTURA_GOV_PL_VER2_0}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_SelfBilledCreditNote_2__SelfBilledCreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_extended_urn_fdc_www_efaktura_gov_pl_ver2_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:SelfBilledCreditNote-2", "SelfBilledCreditNote", "urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0#extended#urn:fdc:www.efaktura.gov.pl:ver2.0", "2.1"), "PL Nota koryguj\u0105ca ver. 1.4", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:www.efaktura.gov.pl:ver2.0:sbcn:ver1.4")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestRequest-2::ExpressionOfInterestRequest##urn:fdc:peppol.eu:prac:trns:t001:1.1::2.2</code><br>
     * Same as {@link #EXPRESSIONOFINTERESTREQUEST_FDC_PEPPOL_EU_PRAC_TRNS_T001_1_1}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestRequest_2__ExpressionOfInterestRequest__urn_fdc_peppol_eu_prac_trns_t001_1_1__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestRequest-2", "ExpressionOfInterestRequest", "urn:fdc:peppol.eu:prac:trns:t001:1.1", "2.2"), "Procurement procedure subscription / Subscribe to Procedure V1.1", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p001:1.1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestResponse-2::ExpressionOfInterestResponse##urn:fdc:peppol.eu:prac:trns:t002:1.1::2.2</code><br>
     * Same as {@link #EXPRESSIONOFINTERESTRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T002_1_1}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestResponse_2__ExpressionOfInterestResponse__urn_fdc_peppol_eu_prac_trns_t002_1_1__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestResponse-2", "ExpressionOfInterestResponse", "urn:fdc:peppol.eu:prac:trns:t002:1.1", "2.2"), "Procurement procedure subscription / Subscribe to Procedure Confirmation V1.1", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p001:1.1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:TenderStatusRequest-2::TenderStatusRequest##urn:fdc:peppol.eu:prac:trns:t003:1.1::2.2</code><br>
     * Same as {@link #TENDERSTATUSREQUEST_FDC_PEPPOL_EU_PRAC_TRNS_T003_1_1}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_TenderStatusRequest_2__TenderStatusRequest__urn_fdc_peppol_eu_prac_trns_t003_1_1__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:TenderStatusRequest-2", "TenderStatusRequest", "urn:fdc:peppol.eu:prac:trns:t003:1.1", "2.2"), "Procurement document access / Tender Status Inquiry V1.1", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p002:1.1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CallForTenders-2::CallForTenders##urn:fdc:peppol.eu:prac:trns:t004:1.1::2.2</code><br>
     * Same as {@link #CALLFORTENDERS_FDC_PEPPOL_EU_PRAC_TRNS_T004_1_1}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_CallForTenders_2__CallForTenders__urn_fdc_peppol_eu_prac_trns_t004_1_1__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CallForTenders-2", "CallForTenders", "urn:fdc:peppol.eu:prac:trns:t004:1.1", "2.2"), "Procurement document access / Call for Tenders V1.1", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p002:1.1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Tender-2::Tender##urn:fdc:peppol.eu:prac:trns:t005:1.1::2.2</code><br>
     * Same as {@link #TENDER_FDC_PEPPOL_EU_PRAC_TRNS_T005_1_1}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Tender_2__Tender__urn_fdc_peppol_eu_prac_trns_t005_1_1__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Tender-2", "Tender", "urn:fdc:peppol.eu:prac:trns:t005:1.1", "2.2"), "Tender Submission / Tender V1.1", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p003:1.1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:TenderReceipt-2::TenderReceipt##urn:fdc:peppol.eu:prac:trns:t006:1.1::2.2</code><br>
     * Same as {@link #TENDERRECEIPT_FDC_PEPPOL_EU_PRAC_TRNS_T006_1_1}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_TenderReceipt_2__TenderReceipt__urn_fdc_peppol_eu_prac_trns_t006_1_1__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:TenderReceipt-2", "TenderReceipt", "urn:fdc:peppol.eu:prac:trns:t006:1.1", "2.2"), "Tender Submission / Tender Tender Reception Notification V1.1", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p003:1.1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Enquiry-2::Enquiry##urn:fdc:peppol.eu:prac:trns:t007:1.1::2.2</code><br>
     * Same as {@link #ENQUIRY_FDC_PEPPOL_EU_PRAC_TRNS_T007_1_1}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Enquiry_2__Enquiry__urn_fdc_peppol_eu_prac_trns_t007_1_1__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Enquiry-2", "Enquiry", "urn:fdc:peppol.eu:prac:trns:t007:1.1", "2.2"), "Call for Tender Question and Answers / Tendering Questions V1.1", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p004:1.1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:EnquiryResponse-2::EnquiryResponse##urn:fdc:peppol.eu:prac:trns:t008:1.1::2.2</code><br>
     * Same as {@link #ENQUIRYRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T008_1_1}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_EnquiryResponse_2__EnquiryResponse__urn_fdc_peppol_eu_prac_trns_t008_1_1__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:EnquiryResponse-2", "EnquiryResponse", "urn:fdc:peppol.eu:prac:trns:t008:1.1", "2.2"), "Call for Tender Question and Answers / Tendering Answers V1.1", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p004:1.1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Enquiry-2::Enquiry##urn:fdc:peppol.eu:prac:trns:t009:1.1::2.2</code><br>
     * Same as {@link #ENQUIRY_FDC_PEPPOL_EU_PRAC_TRNS_T009_1_1}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Enquiry_2__Enquiry__urn_fdc_peppol_eu_prac_trns_t009_1_1__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Enquiry-2", "Enquiry", "urn:fdc:peppol.eu:prac:trns:t009:1.1", "2.2"), "Tender Clarification / Tender Clarification Request V1.1", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p005:1.1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:EnquiryResponse-2::EnquiryResponse##urn:fdc:peppol.eu:prac:trns:t010:1.1::2.2</code><br>
     * Same as {@link #ENQUIRYRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T010_1_1}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_EnquiryResponse_2__EnquiryResponse__urn_fdc_peppol_eu_prac_trns_t010_1_1__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:EnquiryResponse-2", "EnquiryResponse", "urn:fdc:peppol.eu:prac:trns:t010:1.1", "2.2"), "Tender Clarification / Tender Clarification V1.1", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p005:1.1")),

    /**
     * <code>urn:oasis:names:tc:ebxml-regrep:xsd:query:4.0::QueryRequest##urn:fdc:peppol.eu:prac:trns:t011:1.1::4.0</code><br>
     * Same as {@link #QUERYREQUEST_FDC_PEPPOL_EU_PRAC_TRNS_T011_1_1}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_tc_ebxml_regrep_xsd_query_4_0__QueryRequest__urn_fdc_peppol_eu_prac_trns_t011_1_1__4_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:tc:ebxml-regrep:xsd:query:4.0", "QueryRequest", "urn:fdc:peppol.eu:prac:trns:t011:1.1", "4.0"), "Search Notices / Search Notice Request V1.1", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p006:1.1")),

    /**
     * <code>urn:oasis:names:tc:ebxml-regrep:xsd:query:4.0::QueryResponse##urn:fdc:peppol.eu:prac:trns:t012:1.1::4.0</code><br>
     * Same as {@link #QUERYRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T012_1_1}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_tc_ebxml_regrep_xsd_query_4_0__QueryResponse__urn_fdc_peppol_eu_prac_trns_t012_1_1__4_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:tc:ebxml-regrep:xsd:query:4.0", "QueryResponse", "urn:fdc:peppol.eu:prac:trns:t012:1.1", "4.0"), "Search Notices / Search Notice Response V1.1", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p006:1.1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:TenderWithdrawal-2::TenderWithdrawal##urn:fdc:peppol.eu:prac:trns:t013:1.1::2.2</code><br>
     * Same as {@link #TENDERWITHDRAWAL_FDC_PEPPOL_EU_PRAC_TRNS_T013_1_1}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_TenderWithdrawal_2__TenderWithdrawal__urn_fdc_peppol_eu_prac_trns_t013_1_1__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:TenderWithdrawal-2", "TenderWithdrawal", "urn:fdc:peppol.eu:prac:trns:t013:1.1", "2.2"), "Tender Withdrawal / Tender Withdrawal V1.1", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p007:1.1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:TenderReceipt-2::TenderReceipt##urn:fdc:peppol.eu:prac:trns:t014:1.1::2.2</code><br>
     * Same as {@link #TENDERRECEIPT_FDC_PEPPOL_EU_PRAC_TRNS_T014_1_1}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_TenderReceipt_2__TenderReceipt__urn_fdc_peppol_eu_prac_trns_t014_1_1__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:TenderReceipt-2", "TenderReceipt", "urn:fdc:peppol.eu:prac:trns:t014:1.1", "2.2"), "Tender Withdrawal / Tender Withdrawal Notification V1.1", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p007:1.1")),

    /**
     * <code>urn:oasis:names:tc:ebxml-regrep:xsd:lcm:4.0::SubmitObjectsRequest##urn:fdc:peppol.eu:prac:trns:t015:1.1::4.0</code><br>
     * Same as {@link #SUBMITOBJECTSREQUEST_FDC_PEPPOL_EU_PRAC_TRNS_T015_1_1}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_tc_ebxml_regrep_xsd_lcm_4_0__SubmitObjectsRequest__urn_fdc_peppol_eu_prac_trns_t015_1_1__4_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:tc:ebxml-regrep:xsd:lcm:4.0", "SubmitObjectsRequest", "urn:fdc:peppol.eu:prac:trns:t015:1.1", "4.0"), "Publish Notice / Publish Notice V1.1", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p008:1.1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:fdc:peppol.eu:prac:trns:t016:1.1::2.2</code><br>
     * Same as {@link #APPLICATIONRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T016_1_1}
     * 
     * @since code list 8.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_prac_trns_t016_1_1__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:fdc:peppol.eu:prac:trns:t016:1.1", "2.2"), "Publish Notice / Notice Publication Response V1.1", Version.parse("8.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p008:1.1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:AwardedNotification-2::AwardedNotification##urn:fdc:peppol.eu:prac:trns:t017:1.4::2.2</code><br>
     * Same as {@link #AWARDEDNOTIFICATION_FDC_PEPPOL_EU_PRAC_TRNS_T017_1_4}
     * 
     * @deprecated since v8.5 - this item should not be used to issue new identifiers!
     * @since code list 8.0
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_AwardedNotification_2__AwardedNotification__urn_fdc_peppol_eu_prac_trns_t017_1_4__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:AwardedNotification-2", "AwardedNotification", "urn:fdc:peppol.eu:prac:trns:t017:1.4", "2.2"), "Notify Awarding / Awarding Notification V1.1 (invalid)", Version.parse("8.0"), EPeppolCodeListItemState.DEPRECATED, Version.parse("8.5"), null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p009:1.1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:AwardedNotification-2::AwardedNotification##urn:fdc:peppol.eu:prac:trns:t017:1.1::2.2</code><br>
     * Same as {@link #AWARDEDNOTIFICATION_FDC_PEPPOL_EU_PRAC_TRNS_T017_1_1}
     * 
     * @since code list 8.5
     */
    urn_oasis_names_specification_ubl_schema_xsd_AwardedNotification_2__AwardedNotification__urn_fdc_peppol_eu_prac_trns_t017_1_1__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:AwardedNotification-2", "AwardedNotification", "urn:fdc:peppol.eu:prac:trns:t017:1.1", "2.2"), "Notify Awarding / Awarding Notification V1.1", Version.parse("8.5"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p009:1.1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.2::2.1</code><br>
     * Same as {@link #XRECHNUNG_INVOICE_UBL_V22}
     * 
     * @since code list 8.1
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_2__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.2", "2.1"), "XRechnung UBL Invoice V2.2", Version.parse("8.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.2::2.1</code><br>
     * Same as {@link #XRECHNUNG_CREDIT_NOTE_UBL_V22}
     * 
     * @since code list 8.1
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_2__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.2", "2.1"), "XRechnung UBL Credit Note V2.2", Version.parse("8.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.2::D16B</code><br>
     * Same as {@link #XRECHNUNG_INVOICE_CII_V22}
     * 
     * @since code list 8.1
     */
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_2__D16B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.2", "D16B"), "XRechnung CII Invoice V2.2", Version.parse("8.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.2#conformant#urn:xoev-de:kosit:extension:xrechnung_2.2::2.1</code><br>
     * Same as {@link #XRECHNUNG_EXTENSION_INVOICE_UBL_V22}
     * 
     * @since code list 8.1
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_2_conformant_urn_xoev_de_kosit_extension_xrechnung_2_2__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.2#conformant#urn:xoev-de:kosit:extension:xrechnung_2.2", "2.1"), "XRechnung UBL Invoice V2.2 Extension", Version.parse("8.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.2#conformant#urn:xoev-de:kosit:extension:xrechnung_2.2::2.1</code><br>
     * Same as {@link #XRECHNUNG_EXTENSION_CREDIT_NOTE_UBL_V22}
     * 
     * @since code list 8.1
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_2_conformant_urn_xoev_de_kosit_extension_xrechnung_2_2__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.2#conformant#urn:xoev-de:kosit:extension:xrechnung_2.2", "2.1"), "XRechnung UBL Credit Note V2.2 Extension", Version.parse("8.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.2#conformant#urn:xoev-de:kosit:extension:xrechnung_2.2::D16B</code><br>
     * Same as {@link #XRECHNUNG_EXTENSION_INVOICE_CII_V22}
     * 
     * @since code list 8.1
     */
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_2_conformant_urn_xoev_de_kosit_extension_xrechnung_2_2__D16B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.2#conformant#urn:xoev-de:kosit:extension:xrechnung_2.2", "D16B"), "XRechnung CII Invoice V2.2 Extension", Version.parse("8.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:fdc:peppol:end-user-reporting:1.0::EndUserReport##urn:fdc:peppol.eu:oo:trns:end-user-report:1::1.0</code><br>
     * Same as {@link #ENDUSERREPORT_FDC_PEPPOL_EU_OO_TRNS_END_USER_REPORT_1}
     * 
     * @deprecated since v8.6 - this item should not be used to issue new identifiers!<br>Removed per 2025-02-01
     * @since code list 8.1
     */
    @Deprecated(forRemoval = false)
    urn_fdc_peppol_end_user_reporting_1_0__EndUserReport__urn_fdc_peppol_eu_oo_trns_end_user_report_1__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:peppol:end-user-reporting:1.0", "EndUserReport", "urn:fdc:peppol.eu:oo:trns:end-user-report:1", "1.0"), "Peppol End User Report", Version.parse("8.1"), EPeppolCodeListItemState.REMOVED, Version.parse("8.6"), PDTFactory.createLocalDate(2025, Month.of(2), 1), false, true, -1, "OO", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:oo:bis:reporting:1")),

    /**
     * <code>urn:fdc:peppol:transaction-statistics-reporting:1.0::TransactionStatisticsReport##urn:fdc:peppol.eu:oo:trns:transaction-statistics-reporting:1::1.0</code><br>
     * Same as {@link #TRANSACTIONSTATISTICSREPORT_FDC_PEPPOL_EU_OO_TRNS_TRANSACTION_STATISTICS_REPORTING_1}
     * 
     * @deprecated since v8.6 - this item should not be used to issue new identifiers!<br>Removed per 2025-02-01
     * @since code list 8.1
     */
    @Deprecated(forRemoval = false)
    urn_fdc_peppol_transaction_statistics_reporting_1_0__TransactionStatisticsReport__urn_fdc_peppol_eu_oo_trns_transaction_statistics_reporting_1__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:peppol:transaction-statistics-reporting:1.0", "TransactionStatisticsReport", "urn:fdc:peppol.eu:oo:trns:transaction-statistics-reporting:1", "1.0"), "Peppol Transaction Statistics Report", Version.parse("8.1"), EPeppolCodeListItemState.REMOVED, Version.parse("8.6"), PDTFactory.createLocalDate(2025, Month.of(2), 1), false, true, -1, "OO", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:oo:bis:reporting:1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2::DespatchAdvice##urn:fdc:peppol.eu:logistics:trns:advanced_despatch_advice:1::2.1</code><br>
     * Same as {@link #DESPATCHADVICE_FDC_PEPPOL_EU_LOGISTICS_TRNS_ADVANCED_DESPATCH_ADVICE_1}
     * 
     * @since code list 8.1
     */
    urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_fdc_peppol_eu_logistics_trns_advanced_despatch_advice_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2", "DespatchAdvice", "urn:fdc:peppol.eu:logistics:trns:advanced_despatch_advice:1", "2.1"), "Advanced Despatch Advice", Version.parse("8.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "Logistics Incubator", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:logistics:bis:despatch_advice_only:1", "cenbii-procid-ubl::urn:fdc:peppol.eu:logistics:bis:despatch_advice_w_response:1", "cenbii-procid-ubl::urn:fdc:peppol.eu:logistics:bis:despatch_advice_w_receipt_advice:1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:fdc:peppol.eu:logistics:trns:despatch_advice_response:1::2.1</code><br>
     * Same as {@link #APPLICATIONRESPONSE_FDC_PEPPOL_EU_LOGISTICS_TRNS_DESPATCH_ADVICE_RESPONSE_1}
     * 
     * @since code list 8.1
     */
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_logistics_trns_despatch_advice_response_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:fdc:peppol.eu:logistics:trns:despatch_advice_response:1", "2.1"), "Despatch Advice Response", Version.parse("8.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "Logistics Incubator", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:logistics:bis:despatch_advice_w_response:1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:WeightStatement-2::WeightStatement##urn:fdc:peppol.eu:logistics:trns:weight_statement:1::2.3</code><br>
     * Same as {@link #WEIGHTSTATEMENT_FDC_PEPPOL_EU_LOGISTICS_TRNS_WEIGHT_STATEMENT_1}
     * 
     * @since code list 8.1
     */
    urn_oasis_names_specification_ubl_schema_xsd_WeightStatement_2__WeightStatement__urn_fdc_peppol_eu_logistics_trns_weight_statement_1__2_3("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:WeightStatement-2", "WeightStatement", "urn:fdc:peppol.eu:logistics:trns:weight_statement:1", "2.3"), "Weight Statement", Version.parse("8.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "Logistics Incubator", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:logistics:bis:weight_statement:1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:UtilityStatement-2::UtilityStatement##urn:fdc:www.efaktura.gov.pl:ver2.0:trns:us:ver1.0::2.1</code><br>
     * Same as {@link #UTILITYSTATEMENT_FDC_WWW_EFAKTURA_GOV_PL_VER2_0_TRNS_US_VER1_0}
     * 
     * @since code list 8.2
     */
    urn_oasis_names_specification_ubl_schema_xsd_UtilityStatement_2__UtilityStatement__urn_fdc_www_efaktura_gov_pl_ver2_0_trns_us_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:UtilityStatement-2", "UtilityStatement", "urn:fdc:www.efaktura.gov.pl:ver2.0:trns:us:ver1.0", "2.1"), "PL Dokument pomocniczy ver. 1.0", Version.parse("8.2"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:www.efaktura.gov.pl:ver2.0:us:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:nen.nl:nlcius:v1.0#compliant#urn:fdc:setu.nl:invoice:v2.2::2.1</code><br>
     * Same as {@link #INVOICE_CEN_EU_EN16931_2017_COMPLIANT_FDC_NEN_NL_NLCIUS_V1_0_COMPLIANT_FDC_SETU_NL_INVOICE_V2_2}
     * 
     * @since code list 8.2
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_nen_nl_nlcius_v1_0_compliant_urn_fdc_setu_nl_invoice_v2_2__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:fdc:nen.nl:nlcius:v1.0#compliant#urn:fdc:setu.nl:invoice:v2.2", "2.1"), "SETU Invoice v2.2", Version.parse("8.2"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:fdc:peppol:jp:billing:3.0::2.1</code><br>
     * Same as {@link #INVOICE_FDC_PEPPOL_JP_BILLING_3_0}
     * 
     * @deprecated since v8.9 - this item should not be used to issue new identifiers!<br>Removed per 2024-09-30
     * @since code list 8.2
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_fdc_peppol_jp_billing_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:fdc:peppol:jp:billing:3.0", "2.1"), "JP PINT invoice", Version.parse("8.2"), EPeppolCodeListItemState.REMOVED, Version.parse("8.9"), PDTFactory.createLocalDate(2024, Month.of(9), 30), false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderChange-2::OrderChange##urn:fdc:peppol.eu:poacc:trns:order_change:3::2.3</code><br>
     * Same as {@link #ORDERCHANGE_FDC_PEPPOL_EU_POACC_TRNS_ORDER_CHANGE_3}
     * 
     * @since code list 8.3
     */
    urn_oasis_names_specification_ubl_schema_xsd_OrderChange_2__OrderChange__urn_fdc_peppol_eu_poacc_trns_order_change_3__2_3("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderChange-2", "OrderChange", "urn:fdc:peppol.eu:poacc:trns:order_change:3", "2.3"), "Peppol Order Change", Version.parse("8.3"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:advanced_ordering:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2::OrderCancellation##urn:fdc:peppol.eu:poacc:trns:order_cancellation:3::2.3</code><br>
     * Same as {@link #ORDERCANCELLATION_FDC_PEPPOL_EU_POACC_TRNS_ORDER_CANCELLATION_3}
     * 
     * @since code list 8.3
     */
    urn_oasis_names_specification_ubl_schema_xsd_OrderCancellation_2__OrderCancellation__urn_fdc_peppol_eu_poacc_trns_order_cancellation_3__2_3("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderCancellation-2", "OrderCancellation", "urn:fdc:peppol.eu:poacc:trns:order_cancellation:3", "2.3"), "Peppol Order Cancellation", Version.parse("8.3"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:advanced_ordering:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::OrderResponse##urn:fdc:peppol.eu:poacc:trns:order_response_advanced:3::2.3</code><br>
     * Same as {@link #ORDERRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_ORDER_RESPONSE_ADVANCED_3}
     * 
     * @since code list 8.3
     */
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_response_advanced_3__2_3("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2", "OrderResponse", "urn:fdc:peppol.eu:poacc:trns:order_response_advanced:3", "2.3"), "Peppol Order Response Advanced", Version.parse("8.3"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:advanced_ordering:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.3::2.1</code><br>
     * Same as {@link #XRECHNUNG_INVOICE_UBL_V23}
     * 
     * @since code list 8.4
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.3", "2.1"), "XRechnung UBL Invoice V2.3", Version.parse("8.4"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.3::2.1</code><br>
     * Same as {@link #XRECHNUNG_CREDIT_NOTE_UBL_V23}
     * 
     * @since code list 8.4
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.3", "2.1"), "XRechnung UBL Credit Note V2.3", Version.parse("8.4"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.3::D16B</code><br>
     * Same as {@link #XRECHNUNG_INVOICE_CII_V23}
     * 
     * @since code list 8.4
     */
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_3__D16B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.3", "D16B"), "XRechnung CII Invoice V2.3", Version.parse("8.4"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.3#conformant#urn:xoev-de:kosit:extension:xrechnung_2.3::2.1</code><br>
     * Same as {@link #XRECHNUNG_EXTENSION_INVOICE_UBL_V23}
     * 
     * @since code list 8.4
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_3_conformant_urn_xoev_de_kosit_extension_xrechnung_2_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.3#conformant#urn:xoev-de:kosit:extension:xrechnung_2.3", "2.1"), "XRechnung UBL Invoice V2.3 Extension", Version.parse("8.4"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.3#conformant#urn:xoev-de:kosit:extension:xrechnung_2.3::2.1</code><br>
     * Same as {@link #XRECHNUNG_EXTENSION_CREDIT_NOTE_UBL_V23}
     * 
     * @since code list 8.4
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_3_conformant_urn_xoev_de_kosit_extension_xrechnung_2_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.3#conformant#urn:xoev-de:kosit:extension:xrechnung_2.3", "2.1"), "XRechnung UBL Credit Note V2.3 Extension", Version.parse("8.4"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.3#conformant#urn:xoev-de:kosit:extension:xrechnung_2.3::D16B</code><br>
     * Same as {@link #XRECHNUNG_EXTENSION_INVOICE_CII_V23}
     * 
     * @since code list 8.4
     */
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_3_conformant_urn_xoev_de_kosit_extension_xrechnung_2_3__D16B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.3#conformant#urn:xoev-de:kosit:extension:xrechnung_2.3", "D16B"), "XRechnung CII Invoice V2.3 Extension", Version.parse("8.4"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:selfbilling-1@jp-1::2.1</code><br>
     * Same as {@link #INVOICE_PEPPOL_PINT_SELFBILLING_1_JP_1}
     * 
     * @deprecated since v8.9 - this item should not be used to issue new identifiers!<br>Removed per 2024-09-30
     * @since code list 8.5
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_selfbilling_1_jp_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:peppol:pint:selfbilling-1@jp-1", "2.1"), "JP BIS Self-Billing Invoice", Version.parse("8.5"), EPeppolCodeListItemState.REMOVED, Version.parse("8.9"), PDTFactory.createLocalDate(2024, Month.of(9), 30), false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:selfbilling")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestRequest-2::ExpressionOfInterestRequest##urn:fdc:peppol.eu:prac:trns:t001:1.2::2.2</code><br>
     * Same as {@link #EXPRESSIONOFINTERESTREQUEST_FDC_PEPPOL_EU_PRAC_TRNS_T001_1_2}
     * 
     * @since code list 8.5
     */
    urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestRequest_2__ExpressionOfInterestRequest__urn_fdc_peppol_eu_prac_trns_t001_1_2__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestRequest-2", "ExpressionOfInterestRequest", "urn:fdc:peppol.eu:prac:trns:t001:1.2", "2.2"), "Procurement procedure subscription / Subscribe to Procedure V1.2", Version.parse("8.5"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p001:1.2")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestResponse-2::ExpressionOfInterestResponse##urn:fdc:peppol.eu:prac:trns:t002:1.2::2.2</code><br>
     * Same as {@link #EXPRESSIONOFINTERESTRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T002_1_2}
     * 
     * @since code list 8.5
     */
    urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestResponse_2__ExpressionOfInterestResponse__urn_fdc_peppol_eu_prac_trns_t002_1_2__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestResponse-2", "ExpressionOfInterestResponse", "urn:fdc:peppol.eu:prac:trns:t002:1.2", "2.2"), "Procurement procedure subscription / Subscribe to Procedure Confirmation V1.2", Version.parse("8.5"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p001:1.2")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestRequest-2::ExpressionOfInterestRequest##urn:fdc:peppol.eu:prac:trns:t021:1.2::2.2</code><br>
     * Same as {@link #EXPRESSIONOFINTERESTREQUEST_FDC_PEPPOL_EU_PRAC_TRNS_T021_1_2}
     * 
     * @since code list 8.5
     */
    urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestRequest_2__ExpressionOfInterestRequest__urn_fdc_peppol_eu_prac_trns_t021_1_2__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestRequest-2", "ExpressionOfInterestRequest", "urn:fdc:peppol.eu:prac:trns:t021:1.2", "2.2"), "Procurement procedure subscription / Unsubscribe from procedure V1.2", Version.parse("8.5"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p001:1.2")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestResponse-2::ExpressionOfInterestResponse##urn:fdc:peppol.eu:prac:trns:t022:1.2::2.2</code><br>
     * Same as {@link #EXPRESSIONOFINTERESTRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T022_1_2}
     * 
     * @since code list 8.5
     */
    urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestResponse_2__ExpressionOfInterestResponse__urn_fdc_peppol_eu_prac_trns_t022_1_2__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestResponse-2", "ExpressionOfInterestResponse", "urn:fdc:peppol.eu:prac:trns:t022:1.2", "2.2"), "Procurement procedure subscription / Unsubscribe from procedure confirmation V1.2", Version.parse("8.5"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p001:1.2")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:TenderStatusRequest-2::TenderStatusRequest##urn:fdc:peppol.eu:prac:trns:t003:1.2::2.2</code><br>
     * Same as {@link #TENDERSTATUSREQUEST_FDC_PEPPOL_EU_PRAC_TRNS_T003_1_2}
     * 
     * @since code list 8.5
     */
    urn_oasis_names_specification_ubl_schema_xsd_TenderStatusRequest_2__TenderStatusRequest__urn_fdc_peppol_eu_prac_trns_t003_1_2__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:TenderStatusRequest-2", "TenderStatusRequest", "urn:fdc:peppol.eu:prac:trns:t003:1.2", "2.2"), "Procurement document access / Tender Status Inquiry V1.2", Version.parse("8.5"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p002:1.2")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CallForTenders-2::CallForTenders##urn:fdc:peppol.eu:prac:trns:t004:1.2::2.2</code><br>
     * Same as {@link #CALLFORTENDERS_FDC_PEPPOL_EU_PRAC_TRNS_T004_1_2}
     * 
     * @since code list 8.5
     */
    urn_oasis_names_specification_ubl_schema_xsd_CallForTenders_2__CallForTenders__urn_fdc_peppol_eu_prac_trns_t004_1_2__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CallForTenders-2", "CallForTenders", "urn:fdc:peppol.eu:prac:trns:t004:1.2", "2.2"), "Procurement document access / Call for Tenders V1.2", Version.parse("8.5"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p002:1.2")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Tender-2::Tender##urn:fdc:peppol.eu:prac:trns:t005:1.2::2.2</code><br>
     * Same as {@link #TENDER_FDC_PEPPOL_EU_PRAC_TRNS_T005_1_2}
     * 
     * @since code list 8.5
     */
    urn_oasis_names_specification_ubl_schema_xsd_Tender_2__Tender__urn_fdc_peppol_eu_prac_trns_t005_1_2__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Tender-2", "Tender", "urn:fdc:peppol.eu:prac:trns:t005:1.2", "2.2"), "Tender Submission / Tender V1.2", Version.parse("8.5"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p003:1.2")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:TenderReceipt-2::TenderReceipt##urn:fdc:peppol.eu:prac:trns:t006:1.2::2.2</code><br>
     * Same as {@link #TENDERRECEIPT_FDC_PEPPOL_EU_PRAC_TRNS_T006_1_2}
     * 
     * @since code list 8.5
     */
    urn_oasis_names_specification_ubl_schema_xsd_TenderReceipt_2__TenderReceipt__urn_fdc_peppol_eu_prac_trns_t006_1_2__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:TenderReceipt-2", "TenderReceipt", "urn:fdc:peppol.eu:prac:trns:t006:1.2", "2.2"), "Tender Submission / Tender Tender Reception Notification V1.2", Version.parse("8.5"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p003:1.2")),

    /**
     * <code>urn:oasis:names:tc:ebxml-regrep:xsd:query:4.0::QueryRequest##urn:fdc:peppol.eu:prac:trns:t011:1.2::4.0</code><br>
     * Same as {@link #QUERYREQUEST_FDC_PEPPOL_EU_PRAC_TRNS_T011_1_2}
     * 
     * @since code list 8.5
     */
    urn_oasis_names_tc_ebxml_regrep_xsd_query_4_0__QueryRequest__urn_fdc_peppol_eu_prac_trns_t011_1_2__4_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:tc:ebxml-regrep:xsd:query:4.0", "QueryRequest", "urn:fdc:peppol.eu:prac:trns:t011:1.2", "4.0"), "Search Notices / Search Notice Request V1.2", Version.parse("8.5"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p006:1.2")),

    /**
     * <code>urn:oasis:names:tc:ebxml-regrep:xsd:query:4.0::QueryResponse##urn:fdc:peppol.eu:prac:trns:t012:1.2::4.0</code><br>
     * Same as {@link #QUERYRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T012_1_2}
     * 
     * @since code list 8.5
     */
    urn_oasis_names_tc_ebxml_regrep_xsd_query_4_0__QueryResponse__urn_fdc_peppol_eu_prac_trns_t012_1_2__4_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:tc:ebxml-regrep:xsd:query:4.0", "QueryResponse", "urn:fdc:peppol.eu:prac:trns:t012:1.2", "4.0"), "Search Notices / Search Notice Response V1.2", Version.parse("8.5"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p006:1.2")),

    /**
     * <code>urn:oasis:names:tc:ebxml-regrep:xsd:lcm:4.0::SubmitObjectsRequest##urn:fdc:peppol.eu:prac:trns:t015:1.2::4.0</code><br>
     * Same as {@link #SUBMITOBJECTSREQUEST_FDC_PEPPOL_EU_PRAC_TRNS_T015_1_2}
     * 
     * @since code list 8.5
     */
    urn_oasis_names_tc_ebxml_regrep_xsd_lcm_4_0__SubmitObjectsRequest__urn_fdc_peppol_eu_prac_trns_t015_1_2__4_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:tc:ebxml-regrep:xsd:lcm:4.0", "SubmitObjectsRequest", "urn:fdc:peppol.eu:prac:trns:t015:1.2", "4.0"), "Publish Notice / Publish Notice V1.2", Version.parse("8.5"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p008:1.2")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:fdc:peppol.eu:prac:trns:t016:1.2::2.2</code><br>
     * Same as {@link #APPLICATIONRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T016_1_2}
     * 
     * @since code list 8.5
     */
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_prac_trns_t016_1_2__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:fdc:peppol.eu:prac:trns:t016:1.2", "2.2"), "Publish Notice / Notice Publication Response V1.2", Version.parse("8.5"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p008:1.2")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:fdc:peppol.eu:prac:trns:t018:1.1::2.2</code><br>
     * Same as {@link #APPLICATIONRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T018_1_1}
     * 
     * @since code list 8.5
     */
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_prac_trns_t018_1_1__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:fdc:peppol.eu:prac:trns:t018:1.1", "2.2"), "Tendering Message Response / Tendering Message Response V1.1", Version.parse("8.5"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p010:1.1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Qualification-2::Qualification##urn:fdc:peppol.eu:prac:trns:t019:1.1::2.2</code><br>
     * Same as {@link #QUALIFICATION_FDC_PEPPOL_EU_PRAC_TRNS_T019_1_1}
     * 
     * @since code list 8.5
     */
    urn_oasis_names_specification_ubl_schema_xsd_Qualification_2__Qualification__urn_fdc_peppol_eu_prac_trns_t019_1_1__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Qualification-2", "Qualification", "urn:fdc:peppol.eu:prac:trns:t019:1.1", "2.2"), "Qualification / Qualification V1.1", Version.parse("8.5"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p011:1.1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:TenderReceipt-2::TenderReceipt##urn:fdc:peppol.eu:prac:trns:t020:1.1::2.2</code><br>
     * Same as {@link #TENDERRECEIPT_FDC_PEPPOL_EU_PRAC_TRNS_T020_1_1}
     * 
     * @since code list 8.5
     */
    urn_oasis_names_specification_ubl_schema_xsd_TenderReceipt_2__TenderReceipt__urn_fdc_peppol_eu_prac_trns_t020_1_1__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:TenderReceipt-2", "TenderReceipt", "urn:fdc:peppol.eu:prac:trns:t020:1.1", "2.2"), "Qualification / Qualification Reception Confirmation V1.1", Version.parse("8.5"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:prac:bis:p011:1.1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:TransportExecutionPlanRequest-2::TransportExecutionPlanRequest##urn:fdc:peppol.eu:logistics:trns:transport_execution_plan_request:1::2.3</code><br>
     * Same as {@link #TRANSPORTEXECUTIONPLANREQUEST_FDC_PEPPOL_EU_LOGISTICS_TRNS_TRANSPORT_EXECUTION_PLAN_REQUEST_1}
     * 
     * @since code list 8.6
     */
    urn_oasis_names_specification_ubl_schema_xsd_TransportExecutionPlanRequest_2__TransportExecutionPlanRequest__urn_fdc_peppol_eu_logistics_trns_transport_execution_plan_request_1__2_3("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:TransportExecutionPlanRequest-2", "TransportExecutionPlanRequest", "urn:fdc:peppol.eu:logistics:trns:transport_execution_plan_request:1", "2.3"), "Peppol Transport Execution Plan Request", Version.parse("8.6"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "Logistics Incubator", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:logistics:bis:transport_execution_plan_w_request:1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:TransportExecutionPlan-2::TransportExecutionPlan##urn:fdc:peppol.eu:logistics:trns:transport_execution_plan:1::2.3</code><br>
     * Same as {@link #TRANSPORTEXECUTIONPLAN_FDC_PEPPOL_EU_LOGISTICS_TRNS_TRANSPORT_EXECUTION_PLAN_1}
     * 
     * @since code list 8.6
     */
    urn_oasis_names_specification_ubl_schema_xsd_TransportExecutionPlan_2__TransportExecutionPlan__urn_fdc_peppol_eu_logistics_trns_transport_execution_plan_1__2_3("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:TransportExecutionPlan-2", "TransportExecutionPlan", "urn:fdc:peppol.eu:logistics:trns:transport_execution_plan:1", "2.3"), "Peppol Transport Execution Plan", Version.parse("8.6"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "Logistics Incubator", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:logistics:bis:transport_execution_plan_only:1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Waybill-2::Waybill##urn:fdc:peppol.eu:logistics:trns:waybill:1::2.3</code><br>
     * Same as {@link #WAYBILL_FDC_PEPPOL_EU_LOGISTICS_TRNS_WAYBILL_1}
     * 
     * @since code list 8.6
     */
    urn_oasis_names_specification_ubl_schema_xsd_Waybill_2__Waybill__urn_fdc_peppol_eu_logistics_trns_waybill_1__2_3("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Waybill-2", "Waybill", "urn:fdc:peppol.eu:logistics:trns:waybill:1", "2.3"), "Peppol Waybill", Version.parse("8.6"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "Logistics Incubator", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:logistics:bis:waybill:1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:TransportationStatusRequest-2::TransportationStatusRequest##urn:fdc:peppol.eu:logistics:trns:transportation_status_request:1::2.3</code><br>
     * Same as {@link #TRANSPORTATIONSTATUSREQUEST_FDC_PEPPOL_EU_LOGISTICS_TRNS_TRANSPORTATION_STATUS_REQUEST_1}
     * 
     * @since code list 8.6
     */
    urn_oasis_names_specification_ubl_schema_xsd_TransportationStatusRequest_2__TransportationStatusRequest__urn_fdc_peppol_eu_logistics_trns_transportation_status_request_1__2_3("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:TransportationStatusRequest-2", "TransportationStatusRequest", "urn:fdc:peppol.eu:logistics:trns:transportation_status_request:1", "2.3"), "Peppol Transportation Status Request", Version.parse("8.6"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "Logistics Incubator", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:logistics:bis:transportation_status_w_request:1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:TransportationStatus-2::TransportationStatus##urn:fdc:peppol.eu:logistics:trns:transportation_status:1::2.3</code><br>
     * Same as {@link #TRANSPORTATIONSTATUS_FDC_PEPPOL_EU_LOGISTICS_TRNS_TRANSPORTATION_STATUS_1}
     * 
     * @since code list 8.6
     */
    urn_oasis_names_specification_ubl_schema_xsd_TransportationStatus_2__TransportationStatus__urn_fdc_peppol_eu_logistics_trns_transportation_status_1__2_3("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:TransportationStatus-2", "TransportationStatus", "urn:fdc:peppol.eu:logistics:trns:transportation_status:1", "2.3"), "Peppol Transportation Status", Version.parse("8.6"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "Logistics Incubator", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:logistics:bis:transportation_status_only:1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ReceiptAdvice-2::ReceiptAdvice##urn:fdc:peppol.eu:logistics:trns:receipt_advice:1::2.3</code><br>
     * Same as {@link #RECEIPTADVICE_FDC_PEPPOL_EU_LOGISTICS_TRNS_RECEIPT_ADVICE_1}
     * 
     * @since code list 8.6
     */
    urn_oasis_names_specification_ubl_schema_xsd_ReceiptAdvice_2__ReceiptAdvice__urn_fdc_peppol_eu_logistics_trns_receipt_advice_1__2_3("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ReceiptAdvice-2", "ReceiptAdvice", "urn:fdc:peppol.eu:logistics:trns:receipt_advice:1", "2.3"), "Peppol Receipt Advice", Version.parse("8.6"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "Logistics Incubator", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:logistics:bis:despatch_advice_w_receipt_advice:1")),

    /**
     * <code>http://ns.hr-xml.org/2007-04-15::TimeCard##hr-xml@nl-1.4::2.5</code><br>
     * Same as {@link #TIMECARD_HR_XML_NL_1_4}
     * 
     * @since code list 8.6
     */
    http___ns_hr_xml_org_2007_04_15__TimeCard__hr_xml_nl_1_4__2_5("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("http://ns.hr-xml.org/2007-04-15", "TimeCard", "hr-xml@nl-1.4", "2.5"), "SETU HR-XML Timecard v1.4.1", Version.parse("8.6"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Extended use", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:hr-xml:2007:timesheet:1.0")),

    /**
     * <code>urn:fdc:peppol:end-user-statistics-report:1.1::EndUserStatisticsReport##urn:fdc:peppol.eu:edec:trns:end-user-statistics-report:1.1::1.1</code><br>
     * Same as {@link #ENDUSERSTATISTICSREPORT_FDC_PEPPOL_EU_EDEC_TRNS_END_USER_STATISTICS_REPORT_1_1}
     * 
     * @since code list 8.6
     */
    urn_fdc_peppol_end_user_statistics_report_1_1__EndUserStatisticsReport__urn_fdc_peppol_eu_edec_trns_end_user_statistics_report_1_1__1_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:peppol:end-user-statistics-report:1.1", "EndUserStatisticsReport", "urn:fdc:peppol.eu:edec:trns:end-user-statistics-report:1.1", "1.1"), "Peppol End User Statistics Report v1.1", Version.parse("8.6"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "OO", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:edec:bis:reporting:1.0")),

    /**
     * <code>urn:fdc:peppol:transaction-statistics-report:1.0::TransactionStatisticsReport##urn:fdc:peppol.eu:edec:trns:transaction-statistics-reporting:1.0::1.0</code><br>
     * Same as {@link #TRANSACTIONSTATISTICSREPORT_FDC_PEPPOL_EU_EDEC_TRNS_TRANSACTION_STATISTICS_REPORTING_1_0}
     * 
     * @since code list 8.6
     */
    urn_fdc_peppol_transaction_statistics_report_1_0__TransactionStatisticsReport__urn_fdc_peppol_eu_edec_trns_transaction_statistics_reporting_1_0__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:peppol:transaction-statistics-report:1.0", "TransactionStatisticsReport", "urn:fdc:peppol.eu:edec:trns:transaction-statistics-reporting:1.0", "1.0"), "Peppol Transaction Statistics Report v1.0", Version.parse("8.6"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "OO", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:edec:bis:reporting:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1::2.1</code><br>
     * Same as {@link #INVOICE_PEPPOL_PINT_BILLING_1}
     * 
     * @since code list 8.6
     */
    WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1__2_1("peppol-doctype-wildcard", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:peppol:pint:billing-1", "2.1"), "Peppol PINT Invoice v1.0", Version.parse("8.6"), EPeppolCodeListItemState.ACTIVE, null, null, true, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:billing")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:peppol:pint:billing-1::2.1</code><br>
     * Same as {@link #CREDITNOTE_PEPPOL_PINT_BILLING_1}
     * 
     * @since code list 8.6
     */
    WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_billing_1__2_1("peppol-doctype-wildcard", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:peppol:pint:billing-1", "2.1"), "Peppol PINT Credit Note v1.0", Version.parse("8.6"), EPeppolCodeListItemState.ACTIVE, null, null, true, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:billing")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@jp-1::2.1</code><br>
     * Same as {@link #INVOICE_PEPPOL_PINT_BILLING_1_JP_1}
     * 
     * @deprecated since v8.9 - this item should not be used to issue new identifiers!<br>Removed per 2024-09-30
     * @since code list 8.6
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_jp_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:peppol:pint:billing-1@jp-1", "2.1"), "JP PINT Invoice v1.0", Version.parse("8.6"), EPeppolCodeListItemState.REMOVED, Version.parse("8.9"), PDTFactory.createLocalDate(2024, Month.of(9), 30), false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:billing")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@jp-1::2.1</code><br>
     * Same as {@link #INVOICE_PEPPOL_PINT_BILLING_1_JP_12}
     * 
     * @since code list 8.6
     */
    WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_jp_1__2_1("peppol-doctype-wildcard", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:peppol:pint:billing-1@jp-1", "2.1"), "JP PINT Invoice v1.0", Version.parse("8.6"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:billing")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:selfbilling-1@jp-1::2.1</code><br>
     * Same as {@link #INVOICE_PEPPOL_PINT_SELFBILLING_1_JP_12}
     * 
     * @since code list 8.6
     */
    WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_selfbilling_1_jp_1__2_1("peppol-doctype-wildcard", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:peppol:pint:selfbilling-1@jp-1", "2.1"), "JP BIS Self-Billing Invoice", Version.parse("8.6"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:selfbilling")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:nontaxinvoice-1@jp-1::2.1</code><br>
     * Same as {@link #INVOICE_PEPPOL_PINT_NONTAXINVOICE_1_JP_1}
     * 
     * @deprecated since v8.9 - this item should not be used to issue new identifiers!<br>Removed per 2024-09-30
     * @since code list 8.6
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_nontaxinvoice_1_jp_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:peppol:pint:nontaxinvoice-1@jp-1", "2.1"), "JP BIS Invoice for Non-tax Registered Businesses", Version.parse("8.6"), EPeppolCodeListItemState.REMOVED, Version.parse("8.9"), PDTFactory.createLocalDate(2024, Month.of(9), 30), false, false, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:billing")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:nontaxinvoice-1@jp-1::2.1</code><br>
     * Same as {@link #INVOICE_PEPPOL_PINT_NONTAXINVOICE_1_JP_12}
     * 
     * @since code list 8.6
     */
    WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_nontaxinvoice_1_jp_1__2_1("peppol-doctype-wildcard", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:peppol:pint:nontaxinvoice-1@jp-1", "2.1"), "JP BIS Invoice for Non-tax Registered Businesses", Version.parse("8.6"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:billing")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@aunz-1::2.1</code><br>
     * Same as {@link #INVOICE_PEPPOL_PINT_BILLING_1_AUNZ_1}
     * 
     * @deprecated since v9.1 - this item should not be used to issue new identifiers!<br>Removed per 2025-03-01
     * @since code list 8.6
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_aunz_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:peppol:pint:billing-1@aunz-1", "2.1"), "A-NZ PINT Invoice v1.0", Version.parse("8.6"), EPeppolCodeListItemState.REMOVED, Version.parse("9.1"), PDTFactory.createLocalDate(2025, Month.of(3), 1), false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:billing")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:peppol:pint:billing-1@aunz-1::2.1</code><br>
     * Same as {@link #CREDITNOTE_PEPPOL_PINT_BILLING_1_AUNZ_1}
     * 
     * @deprecated since v9.1 - this item should not be used to issue new identifiers!<br>Removed per 2025-03-01
     * @since code list 8.6
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_billing_1_aunz_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:peppol:pint:billing-1@aunz-1", "2.1"), "A-NZ PINT Credit Note v1.0", Version.parse("8.6"), EPeppolCodeListItemState.REMOVED, Version.parse("9.1"), PDTFactory.createLocalDate(2025, Month.of(3), 1), false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:billing")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:selfbilling-1@aunz-1::2.1</code><br>
     * Same as {@link #INVOICE_PEPPOL_PINT_SELFBILLING_1_AUNZ_1}
     * 
     * @deprecated since v9.1 - this item should not be used to issue new identifiers!<br>Removed per 2025-03-01
     * @since code list 8.6
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_selfbilling_1_aunz_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:peppol:pint:selfbilling-1@aunz-1", "2.1"), "A-NZ PINT Self-Billing Invoice v1.0", Version.parse("8.6"), EPeppolCodeListItemState.REMOVED, Version.parse("9.1"), PDTFactory.createLocalDate(2025, Month.of(3), 1), false, false, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:selfbilling")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:peppol:pint:selfbilling-1@aunz-1::2.1</code><br>
     * Same as {@link #CREDITNOTE_PEPPOL_PINT_SELFBILLING_1_AUNZ_1}
     * 
     * @deprecated since v9.1 - this item should not be used to issue new identifiers!<br>Removed per 2025-03-01
     * @since code list 8.6
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_selfbilling_1_aunz_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:peppol:pint:selfbilling-1@aunz-1", "2.1"), "A-NZ PINT Self-Billing Credit Note v1.0", Version.parse("8.6"), EPeppolCodeListItemState.REMOVED, Version.parse("9.1"), PDTFactory.createLocalDate(2025, Month.of(3), 1), false, false, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:selfbilling")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@aunz-1::2.1</code><br>
     * Same as {@link #INVOICE_PEPPOL_PINT_BILLING_1_AUNZ_12}
     * 
     * @since code list 8.6
     */
    WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_aunz_1__2_1("peppol-doctype-wildcard", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:peppol:pint:billing-1@aunz-1", "2.1"), "A-NZ PINT Invoice v1.0", Version.parse("8.6"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:billing")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:peppol:pint:billing-1@aunz-1::2.1</code><br>
     * Same as {@link #CREDITNOTE_PEPPOL_PINT_BILLING_1_AUNZ_12}
     * 
     * @since code list 8.6
     */
    WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_billing_1_aunz_1__2_1("peppol-doctype-wildcard", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:peppol:pint:billing-1@aunz-1", "2.1"), "A-NZ PINT Credit Note v1.0", Version.parse("8.6"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:billing")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:selfbilling-1@aunz-1::2.1</code><br>
     * Same as {@link #INVOICE_PEPPOL_PINT_SELFBILLING_1_AUNZ_12}
     * 
     * @since code list 8.6
     */
    WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_selfbilling_1_aunz_1__2_1("peppol-doctype-wildcard", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:peppol:pint:selfbilling-1@aunz-1", "2.1"), "A-NZ PINT Self-Billing Invoice v1.0", Version.parse("8.6"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:selfbilling")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:peppol:pint:selfbilling-1@aunz-1::2.1</code><br>
     * Same as {@link #CREDITNOTE_PEPPOL_PINT_SELFBILLING_1_AUNZ_12}
     * 
     * @since code list 8.6
     */
    WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_selfbilling_1_aunz_1__2_1("peppol-doctype-wildcard", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:peppol:pint:selfbilling-1@aunz-1", "2.1"), "A-NZ PINT Self-Billing Credit Note v1.0", Version.parse("8.6"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:selfbilling")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0::2.1</code><br>
     * Same as {@link #XRECHNUNG_INVOICE_UBL_V30}
     * 
     * @since code list 8.7
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xeinkauf_de_kosit_xrechnung_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0", "2.1"), "XRechnung UBL Invoice V3.0", Version.parse("8.7"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0::2.1</code><br>
     * Same as {@link #XRECHNUNG_CREDIT_NOTE_UBL_V30}
     * 
     * @since code list 8.7
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xeinkauf_de_kosit_xrechnung_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0", "2.1"), "XRechnung UBL Credit Note V3.0", Version.parse("8.7"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0::D16B</code><br>
     * Same as {@link #XRECHNUNG_INVOICE_CII_V30}
     * 
     * @since code list 8.7
     */
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xeinkauf_de_kosit_xrechnung_3_0__D16B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0", "D16B"), "XRechnung CII Invoice V3.0", Version.parse("8.7"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0#conformant#urn:xeinkauf.de:kosit:extension:xrechnung_3.0::2.1</code><br>
     * Same as {@link #XRECHNUNG_EXTENSION_INVOICE_UBL_V30}
     * 
     * @since code list 8.7
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xeinkauf_de_kosit_xrechnung_3_0_conformant_urn_xeinkauf_de_kosit_extension_xrechnung_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0#conformant#urn:xeinkauf.de:kosit:extension:xrechnung_3.0", "2.1"), "XRechnung UBL Invoice V3.0 Extension", Version.parse("8.7"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0#conformant#urn:xeinkauf.de:kosit:extension:xrechnung_3.0::2.1</code><br>
     * Same as {@link #XRECHNUNG_EXTENSION_CREDIT_NOTE_UBL_V30}
     * 
     * @since code list 8.7
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xeinkauf_de_kosit_xrechnung_3_0_conformant_urn_xeinkauf_de_kosit_extension_xrechnung_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0#conformant#urn:xeinkauf.de:kosit:extension:xrechnung_3.0", "2.1"), "XRechnung UBL Credit Note V3.0 Extension", Version.parse("8.7"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0#conformant#urn:xeinkauf.de:kosit:extension:xrechnung_3.0::D16B</code><br>
     * Same as {@link #XRECHNUNG_EXTENSION_INVOICE_CII_V30}
     * 
     * @since code list 8.7
     */
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xeinkauf_de_kosit_xrechnung_3_0_conformant_urn_xeinkauf_de_kosit_extension_xrechnung_3_0__D16B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0#conformant#urn:xeinkauf.de:kosit:extension:xrechnung_3.0", "D16B"), "XRechnung CII Invoice V3.0 Extension", Version.parse("8.7"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@sg-1::2.1</code><br>
     * Same as {@link #INVOICE_PEPPOL_PINT_BILLING_1_SG_1}
     * 
     * @since code list 8.7
     */
    WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_sg_1__2_1("peppol-doctype-wildcard", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:peppol:pint:billing-1@sg-1", "2.1"), "SG PINT Invoice v1.0", Version.parse("8.7"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:billing")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:peppol:pint:billing-1@sg-1::2.1</code><br>
     * Same as {@link #CREDITNOTE_PEPPOL_PINT_BILLING_1_SG_1}
     * 
     * @since code list 8.7
     */
    WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_billing_1_sg_1__2_1("peppol-doctype-wildcard", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:peppol:pint:billing-1@sg-1", "2.1"), "SG PINT Credit Note v1.0", Version.parse("8.7"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:billing")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@my-1::2.1</code><br>
     * Same as {@link #INVOICE_PEPPOL_PINT_BILLING_1_MY_1}
     * 
     * @since code list 8.8
     */
    WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_my_1__2_1("peppol-doctype-wildcard", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:peppol:pint:billing-1@my-1", "2.1"), "MY PINT Invoice v1.0", Version.parse("8.8"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:billing")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:peppol:pint:billing-1@my-1::2.1</code><br>
     * Same as {@link #CREDITNOTE_PEPPOL_PINT_BILLING_1_MY_1}
     * 
     * @since code list 8.8
     */
    WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_billing_1_my_1__2_1("peppol-doctype-wildcard", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:peppol:pint:billing-1@my-1", "2.1"), "MY PINT Credit Note v1.0", Version.parse("8.8"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:billing")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:selfbilling-1@my-1::2.1</code><br>
     * Same as {@link #INVOICE_PEPPOL_PINT_SELFBILLING_1_MY_1}
     * 
     * @since code list 8.8
     */
    WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_selfbilling_1_my_1__2_1("peppol-doctype-wildcard", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:peppol:pint:selfbilling-1@my-1", "2.1"), "MY PINT Self-Billing Invoice v1.0", Version.parse("8.8"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:selfbilling")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:peppol:pint:selfbilling-1@my-1::2.1</code><br>
     * Same as {@link #CREDITNOTE_PEPPOL_PINT_SELFBILLING_1_MY_1}
     * 
     * @since code list 8.8
     */
    WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_selfbilling_1_my_1__2_1("peppol-doctype-wildcard", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:peppol:pint:selfbilling-1@my-1", "2.1"), "MY PINT Self-Billing Credit Note v1.0", Version.parse("8.8"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:selfbilling")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@my-1::2.1</code><br>
     * Same as {@link #INVOICE_PEPPOL_PINT_BILLING_1_MY_12}
     * 
     * @deprecated since v2025-05-15 - this item should not be used to issue new identifiers!<br>Removed per 2025-09-09
     * @since code list 8.8
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_my_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:peppol:pint:billing-1@my-1", "2.1"), "MY PINT Invoice v1.0", Version.parse("8.8"), EPeppolCodeListItemState.DEPRECATED, Version.parse("2025-05-15"), PDTFactory.createLocalDate(2025, Month.of(9), 9), false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:billing")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:peppol:pint:billing-1@my-1::2.1</code><br>
     * Same as {@link #CREDITNOTE_PEPPOL_PINT_BILLING_1_MY_12}
     * 
     * @deprecated since v2025-05-15 - this item should not be used to issue new identifiers!<br>Removed per 2025-09-09
     * @since code list 8.8
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_billing_1_my_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:peppol:pint:billing-1@my-1", "2.1"), "MY PINT Credit Note v1.0", Version.parse("8.8"), EPeppolCodeListItemState.DEPRECATED, Version.parse("2025-05-15"), PDTFactory.createLocalDate(2025, Month.of(9), 9), false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:billing")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:selfbilling-1@my-1::2.1</code><br>
     * Same as {@link #INVOICE_PEPPOL_PINT_SELFBILLING_1_MY_12}
     * 
     * @deprecated since v2025-05-15 - this item should not be used to issue new identifiers!<br>Removed per 2025-09-09
     * @since code list 8.8
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_selfbilling_1_my_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:peppol:pint:selfbilling-1@my-1", "2.1"), "MY PINT Self-Billing Invoice v1.0", Version.parse("8.8"), EPeppolCodeListItemState.DEPRECATED, Version.parse("2025-05-15"), PDTFactory.createLocalDate(2025, Month.of(9), 9), false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:selfbilling")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:peppol:pint:selfbilling-1@my-1::2.1</code><br>
     * Same as {@link #CREDITNOTE_PEPPOL_PINT_SELFBILLING_1_MY_12}
     * 
     * @deprecated since v2025-05-15 - this item should not be used to issue new identifiers!<br>Removed per 2025-09-09
     * @since code list 8.8
     */
    @Deprecated(forRemoval = false)
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_selfbilling_1_my_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:peppol:pint:selfbilling-1@my-1", "2.1"), "MY PINT Self-Billing Credit Note v1.0", Version.parse("8.8"), EPeppolCodeListItemState.DEPRECATED, Version.parse("2025-05-15"), PDTFactory.createLocalDate(2025, Month.of(9), 9), false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:selfbilling")),

    /**
     * <code>http://ns.hr-xml.org/2007-04-15::StaffingOrder##hr-xml@nl-1.4::2.5</code><br>
     * Same as {@link #STAFFINGORDER_HR_XML_NL_1_4}
     * 
     * @since code list 8.9
     */
    http___ns_hr_xml_org_2007_04_15__StaffingOrder__hr_xml_nl_1_4__2_5("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("http://ns.hr-xml.org/2007-04-15", "StaffingOrder", "hr-xml@nl-1.4", "2.5"), "SETU HR-XML StaffingOrder v1.4", Version.parse("8.9"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Extended use", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:hr-xml:2007:staffingorder:1.0")),

    /**
     * <code>http://ns.hr-xml.org/2007-04-15::StaffingOrder##hr-xml:status@nl-1.4::2.5</code><br>
     * Same as {@link #STAFFINGORDER_HR_XML_STATUS_NL_1_4}
     * 
     * @since code list 8.9
     */
    http___ns_hr_xml_org_2007_04_15__StaffingOrder__hr_xml_status_nl_1_4__2_5("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("http://ns.hr-xml.org/2007-04-15", "StaffingOrder", "hr-xml:status@nl-1.4", "2.5"), "SETU HR-XML StaffingOrder Status v1.4", Version.parse("8.9"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Extended use", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:hr-xml:2007:staffingorder:1.0")),

    /**
     * <code>http://ns.hr-xml.org/2007-04-15::HumanResource##hr-xml@nl-1.4::2.5</code><br>
     * Same as {@link #HUMANRESOURCE_HR_XML_NL_1_4}
     * 
     * @since code list 8.9
     */
    http___ns_hr_xml_org_2007_04_15__HumanResource__hr_xml_nl_1_4__2_5("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("http://ns.hr-xml.org/2007-04-15", "HumanResource", "hr-xml@nl-1.4", "2.5"), "SETU HR-XML HumanResource v1.4", Version.parse("8.9"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Extended use", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:hr-xml:2007:humanresource:1.0")),

    /**
     * <code>http://ns.hr-xml.org/2007-04-15::HumanResource##hr-xml:status@nl-1.4::2.5</code><br>
     * Same as {@link #HUMANRESOURCE_HR_XML_STATUS_NL_1_4}
     * 
     * @since code list 8.9
     */
    http___ns_hr_xml_org_2007_04_15__HumanResource__hr_xml_status_nl_1_4__2_5("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("http://ns.hr-xml.org/2007-04-15", "HumanResource", "hr-xml:status@nl-1.4", "2.5"), "SETU HR-XML HumanResource Status v1.4", Version.parse("8.9"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Extended use", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:hr-xml:2007:humanresource:1.0")),

    /**
     * <code>http://ns.hr-xml.org/2007-04-15::Assignment##hr-xml@nl-1.4.1::2.5</code><br>
     * Same as {@link #ASSIGNMENT_HR_XML_NL_1_4_1}
     * 
     * @since code list 8.9
     */
    http___ns_hr_xml_org_2007_04_15__Assignment__hr_xml_nl_1_4_1__2_5("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("http://ns.hr-xml.org/2007-04-15", "Assignment", "hr-xml@nl-1.4.1", "2.5"), "SETU HR-XML Assignment v1.4.1", Version.parse("8.9"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Extended use", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:hr-xml:2007:assignment:1.0")),

    /**
     * <code>http://ns.hr-xml.org/2007-04-15::Assignment##hr-xml:status@nl-1.4::2.5</code><br>
     * Same as {@link #ASSIGNMENT_HR_XML_STATUS_NL_1_4}
     * 
     * @since code list 8.9
     */
    http___ns_hr_xml_org_2007_04_15__Assignment__hr_xml_status_nl_1_4__2_5("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("http://ns.hr-xml.org/2007-04-15", "Assignment", "hr-xml:status@nl-1.4", "2.5"), "SETU HR-XML Assignment Status v1.4", Version.parse("8.9"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Extended use", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:hr-xml:2007:assignment:1.0")),

    /**
     * <code>urn:fdc:digdir.no:2020:innbyggerpost:xsd::innbyggerpost##urn:fdc:digdir.no:2020:innbyggerpost:schema:digital::1.0</code><br>
     * Same as {@link #INNBYGGERPOST_FDC_DIGDIR_NO_2020_INNBYGGERPOST_SCHEMA_DIGITAL}
     * 
     * @since code list 8.9
     */
    urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_digital__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:digdir.no:2020:innbyggerpost:xsd", "innbyggerpost", "urn:fdc:digdir.no:2020:innbyggerpost:schema:digital", "1.0"), "INNBYGGERPOST_DPI_DIGITAL_1_0", Version.parse("8.9"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Extended use", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:digdir.no:2020:profile:egovernment:innbyggerpost:digital:ver1.0")),

    /**
     * <code>urn:fdc:digdir.no:2020:innbyggerpost:xsd::innbyggerpost##urn:fdc:digdir.no:2020:innbyggerpost:schema:utskrift::1.0</code><br>
     * Same as {@link #INNBYGGERPOST_FDC_DIGDIR_NO_2020_INNBYGGERPOST_SCHEMA_UTSKRIFT}
     * 
     * @since code list 8.9
     */
    urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_utskrift__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:digdir.no:2020:innbyggerpost:xsd", "innbyggerpost", "urn:fdc:digdir.no:2020:innbyggerpost:schema:utskrift", "1.0"), "INNBYGGERPOST_DPI_UTSKRIFT_1_0", Version.parse("8.9"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Extended use", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:digdir.no:2020:profile:egovernment:innbyggerpost:utskrift:ver1.0")),

    /**
     * <code>urn:fdc:digdir.no:2020:innbyggerpost:xsd::innbyggerpost##urn:fdc:digdir.no:2020:innbyggerpost:schema:flyttet::1.0</code><br>
     * Same as {@link #INNBYGGERPOST_FDC_DIGDIR_NO_2020_INNBYGGERPOST_SCHEMA_FLYTTET}
     * 
     * @since code list 8.9
     */
    urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_flyttet__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:digdir.no:2020:innbyggerpost:xsd", "innbyggerpost", "urn:fdc:digdir.no:2020:innbyggerpost:schema:flyttet", "1.0"), "INNBYGGERPOST_DPI_FLYTTET_1_0", Version.parse("8.9"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Extended use", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:digdir.no:2020:profile:egovernment:innbyggerpost:flyttet:ver1.0")),

    /**
     * <code>urn:fdc:digdir.no:2020:innbyggerpost:xsd::innbyggerpost##urn:fdc:digdir.no:2020:innbyggerpost:schema:leveringskvittering::1.0</code><br>
     * Same as {@link #INNBYGGERPOST_FDC_DIGDIR_NO_2020_INNBYGGERPOST_SCHEMA_LEVERINGSKVITTERING}
     * 
     * @since code list 8.9
     */
    urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_leveringskvittering__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:digdir.no:2020:innbyggerpost:xsd", "innbyggerpost", "urn:fdc:digdir.no:2020:innbyggerpost:schema:leveringskvittering", "1.0"), "INNBYGGERPOST_DPI_LEVERINGSKVITTERING_1_0", Version.parse("8.9"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Extended use", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:digdir.no:2020:profile:egovernment:innbyggerpost:digital:ver1.0", "cenbii-procid-ubl::urn:fdc:digdir.no:2020:profile:egovernment:innbyggerpost:utskrift:ver1.0", "cenbii-procid-ubl::urn:fdc:digdir.no:2020:profile:egovernment:innbyggerpost:flyttet:ver1.0")),

    /**
     * <code>urn:fdc:digdir.no:2020:innbyggerpost:xsd::innbyggerpost##urn:fdc:digdir.no:2020:innbyggerpost:schema:feil::1.0</code><br>
     * Same as {@link #INNBYGGERPOST_FDC_DIGDIR_NO_2020_INNBYGGERPOST_SCHEMA_FEIL}
     * 
     * @since code list 8.9
     */
    urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_feil__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:digdir.no:2020:innbyggerpost:xsd", "innbyggerpost", "urn:fdc:digdir.no:2020:innbyggerpost:schema:feil", "1.0"), "INNBYGGERPOST_DPI_FEILKVITTERING_1_0", Version.parse("8.9"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Extended use", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:digdir.no:2020:profile:egovernment:innbyggerpost:digital:ver1.0", "cenbii-procid-ubl::urn:fdc:digdir.no:2020:profile:egovernment:innbyggerpost:utskrift:ver1.0", "cenbii-procid-ubl::urn:fdc:digdir.no:2020:profile:egovernment:innbyggerpost:flyttet:ver1.0")),

    /**
     * <code>urn:fdc:digdir.no:2020:innbyggerpost:xsd::innbyggerpost##urn:fdc:digdir.no:2020:innbyggerpost:schema:aapningskvittering::1.0</code><br>
     * Same as {@link #INNBYGGERPOST_FDC_DIGDIR_NO_2020_INNBYGGERPOST_SCHEMA_AAPNINGSKVITTERING}
     * 
     * @since code list 8.9
     */
    urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_aapningskvittering__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:digdir.no:2020:innbyggerpost:xsd", "innbyggerpost", "urn:fdc:digdir.no:2020:innbyggerpost:schema:aapningskvittering", "1.0"), "INNBYGGERPOST_DPI_AAPNINGSKVITTERING_1_0", Version.parse("8.9"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Extended use", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:digdir.no:2020:profile:egovernment:innbyggerpost:digital:ver1.0")),

    /**
     * <code>urn:fdc:digdir.no:2020:innbyggerpost:xsd::innbyggerpost##urn:fdc:digdir.no:2020:innbyggerpost:schema:mottakskvittering::1.0</code><br>
     * Same as {@link #INNBYGGERPOST_FDC_DIGDIR_NO_2020_INNBYGGERPOST_SCHEMA_MOTTAKSKVITTERING}
     * 
     * @since code list 8.9
     */
    urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_mottakskvittering__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:digdir.no:2020:innbyggerpost:xsd", "innbyggerpost", "urn:fdc:digdir.no:2020:innbyggerpost:schema:mottakskvittering", "1.0"), "INNBYGGERPOST_DPI_MOTTAKSKVITTERING_1_0", Version.parse("8.9"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Extended use", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:digdir.no:2020:profile:egovernment:innbyggerpost:utskrift:ver1.0")),

    /**
     * <code>urn:fdc:digdir.no:2020:innbyggerpost:xsd::innbyggerpost##urn:fdc:digdir.no:2020:innbyggerpost:schema:varslingfeiletkvittering::1.0</code><br>
     * Same as {@link #INNBYGGERPOST_FDC_DIGDIR_NO_2020_INNBYGGERPOST_SCHEMA_VARSLINGFEILETKVITTERING}
     * 
     * @since code list 8.9
     */
    urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_varslingfeiletkvittering__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:digdir.no:2020:innbyggerpost:xsd", "innbyggerpost", "urn:fdc:digdir.no:2020:innbyggerpost:schema:varslingfeiletkvittering", "1.0"), "INNBYGGERPOST_DPI_VARSLINGFEILETKVITTERING_1_0", Version.parse("8.9"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Extended use", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:digdir.no:2020:profile:egovernment:innbyggerpost:digital:ver1.0")),

    /**
     * <code>urn:fdc:digdir.no:2020:innbyggerpost:xsd::innbyggerpost##urn:fdc:digdir.no:2020:innbyggerpost:schema:returpostkvittering::1.0</code><br>
     * Same as {@link #INNBYGGERPOST_FDC_DIGDIR_NO_2020_INNBYGGERPOST_SCHEMA_RETURPOSTKVITTERING}
     * 
     * @since code list 8.9
     */
    urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_returpostkvittering__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:fdc:digdir.no:2020:innbyggerpost:xsd", "innbyggerpost", "urn:fdc:digdir.no:2020:innbyggerpost:schema:returpostkvittering", "1.0"), "INNBYGGERPOST_DPI_RETURPOSTKVITTERING_1_0", Version.parse("8.9"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Extended use", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:digdir.no:2020:profile:egovernment:innbyggerpost:utskrift:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:selfbilling:3.0::2.1</code><br>
     * Same as {@link #INVOICE_CEN_EU_EN16931_2017_COMPLIANT_FDC_PEPPOL_EU_2017_POACC_SELFBILLING_3_0}
     * 
     * @since code list 9.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_selfbilling_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:selfbilling:3.0", "2.1"), "Peppol BIS Self-Billing UBL Invoice V3", Version.parse("9.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:selfbilling:01:1.0", "cenbii-procid-ubl::urn:peppol:france:billing:regulated", "cenbii-procid-ubl::urn:peppol:france:billing:non-regulated")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:selfbilling:3.0::2.1</code><br>
     * Same as {@link #CREDITNOTE_CEN_EU_EN16931_2017_COMPLIANT_FDC_PEPPOL_EU_2017_POACC_SELFBILLING_3_0}
     * 
     * @since code list 9.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_selfbilling_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:selfbilling:3.0", "2.1"), "Peppol BIS Self-Billing UBL Credit Note V3", Version.parse("9.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:selfbilling:01:1.0", "cenbii-procid-ubl::urn:peppol:france:billing:regulated", "cenbii-procid-ubl::urn:peppol:france:billing:non-regulated")),

    /**
     * <code>http://www.ketenstandaard.nl/onderhoudsopdracht/SALES/005::MaintenanceInstruction##dico:maintenanceinstruction@nl-1.0::1.0</code><br>
     * Same as {@link #MAINTENANCEINSTRUCTION_DICO_MAINTENANCEINSTRUCTION_NL_1_0}
     * 
     * @since code list 9.0
     */
    http___www_ketenstandaard_nl_onderhoudsopdracht_SALES_005__MaintenanceInstruction__dico_maintenanceinstruction_nl_1_0__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("http://www.ketenstandaard.nl/onderhoudsopdracht/SALES/005", "MaintenanceInstruction", "dico:maintenanceinstruction@nl-1.0", "1.0"), "DICO Maintenance Instruction", Version.parse("9.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Extended use", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:dico:2018:maintenance:1.0")),

    /**
     * <code>urn:peppol:doctype:pdf+xml##urn:cen.eu:en16931:2017#conformant#urn:peppol:france:billing:Factur-X:1.0::D22B</code><br>
     * Same as {@link #_CEN_EU_EN16931_2017_CONFORMANT_PEPPOL_FRANCE_BILLING_FACTUR_X_1_0}
     * 
     * @since code list 9.0
     */
    urn_peppol_doctype_pdf_xml__urn_cen_eu_en16931_2017_conformant_urn_peppol_france_billing_Factur_X_1_0__D22B("busdox-docid-qns", new PeppolGenericDocumentTypeIdentifierParts("urn:peppol:doctype:pdf+xml", "urn:cen.eu:en16931:2017#conformant#urn:peppol:france:billing:Factur-X:1.0", "D22B"), "France Factur-X", Version.parse("9.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "POAC-France", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:france:billing:regulated", "cenbii-procid-ubl::urn:peppol:france:billing:non-regulated")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:peppol:france:billing:cius:1.0::2.1</code><br>
     * Same as {@link #INVOICE_CEN_EU_EN16931_2017_COMPLIANT_PEPPOL_FRANCE_BILLING_CIUS_1_0}
     * 
     * @since code list 9.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_peppol_france_billing_cius_1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:peppol:france:billing:cius:1.0", "2.1"), "France UBL Invoice CIUS", Version.parse("9.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "POAC-France", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:france:billing:regulated", "cenbii-procid-ubl::urn:peppol:france:billing:non-regulated")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#conformant#urn:peppol:france:billing:extended:1.0::2.1</code><br>
     * Same as {@link #INVOICE_CEN_EU_EN16931_2017_CONFORMANT_PEPPOL_FRANCE_BILLING_EXTENDED_1_0}
     * 
     * @since code list 9.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_peppol_france_billing_extended_1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#conformant#urn:peppol:france:billing:extended:1.0", "2.1"), "France UBL Invoice Extension", Version.parse("9.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "POAC-France", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:france:billing:regulated", "cenbii-procid-ubl::urn:peppol:france:billing:non-regulated")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:peppol:france:billing:cius:1.0::D22B</code><br>
     * Same as {@link #CROSSINDUSTRYINVOICE_CEN_EU_EN16931_2017_COMPLIANT_PEPPOL_FRANCE_BILLING_CIUS_1_0}
     * 
     * @since code list 9.0
     */
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_peppol_france_billing_cius_1_0__D22B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#compliant#urn:peppol:france:billing:cius:1.0", "D22B"), "France CII Invoice CIUS", Version.parse("9.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "POAC-France", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:france:billing:regulated", "cenbii-procid-ubl::urn:peppol:france:billing:non-regulated")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#conformant#urn:peppol:france:billing:extended:1.0::D22B</code><br>
     * Same as {@link #CROSSINDUSTRYINVOICE_CEN_EU_EN16931_2017_CONFORMANT_PEPPOL_FRANCE_BILLING_EXTENDED_1_0}
     * 
     * @since code list 9.0
     */
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_conformant_urn_peppol_france_billing_extended_1_0__D22B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#conformant#urn:peppol:france:billing:extended:1.0", "D22B"), "France CII Invoice Extension", Version.parse("9.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "POAC-France", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:france:billing:regulated", "cenbii-procid-ubl::urn:peppol:france:billing:non-regulated")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossDomainAcknowledgementAndResponse:100::CrossDomainAcknowledgementAndResponse##urn:peppol:france:billing:cdv:1.0::D22B</code><br>
     * Same as {@link #CROSSDOMAINACKNOWLEDGEMENTANDRESPONSE_PEPPOL_FRANCE_BILLING_CDV_1_0}
     * 
     * @since code list 9.0
     */
    urn_un_unece_uncefact_data_standard_CrossDomainAcknowledgementAndResponse_100__CrossDomainAcknowledgementAndResponse__urn_peppol_france_billing_cdv_1_0__D22B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossDomainAcknowledgementAndResponse:100", "CrossDomainAcknowledgementAndResponse", "urn:peppol:france:billing:cdv:1.0", "D22B"), "France CDAR", Version.parse("9.0"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "POAC-France", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:france:billing:regulated", "cenbii-procid-ubl::urn:peppol:france:billing:non-regulated")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:peppol:france:billing:cius:1.0::2.1</code><br>
     * Same as {@link #CREDITNOTE_CEN_EU_EN16931_2017_COMPLIANT_PEPPOL_FRANCE_BILLING_CIUS_1_0}
     * 
     * @since code list 9.1
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_peppol_france_billing_cius_1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:peppol:france:billing:cius:1.0", "2.1"), "France UBL Credit Note CIUS", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "POAC-France", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:france:billing:regulated", "cenbii-procid-ubl::urn:peppol:france:billing:non-regulated")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#conformant#urn:peppol:france:billing:extended:1.0::2.1</code><br>
     * Same as {@link #CREDITNOTE_CEN_EU_EN16931_2017_CONFORMANT_PEPPOL_FRANCE_BILLING_EXTENDED_1_0}
     * 
     * @since code list 9.1
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_peppol_france_billing_extended_1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#conformant#urn:peppol:france:billing:extended:1.0", "2.1"), "France UBL Credit Note Extension", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 1, "POAC-France", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:france:billing:regulated", "cenbii-procid-ubl::urn:peppol:france:billing:non-regulated")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@ae-1::2.1</code><br>
     * Same as {@link #INVOICE_PEPPOL_PINT_BILLING_1_AE_1}
     * 
     * @since code list 9.1
     */
    WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_ae_1__2_1("peppol-doctype-wildcard", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:peppol:pint:billing-1@ae-1", "2.1"), "AE PINT Invoice v1.0", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:billing")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:peppol:pint:billing-1@ae-1::2.1</code><br>
     * Same as {@link #CREDITNOTE_PEPPOL_PINT_BILLING_1_AE_1}
     * 
     * @since code list 9.1
     */
    WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_billing_1_ae_1__2_1("peppol-doctype-wildcard", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:peppol:pint:billing-1@ae-1", "2.1"), "AE PINT Credit Note v1.0", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:billing")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:selfbilling-1@ae-1::2.1</code><br>
     * Same as {@link #INVOICE_PEPPOL_PINT_SELFBILLING_1_AE_1}
     * 
     * @since code list 9.1
     */
    WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_selfbilling_1_ae_1__2_1("peppol-doctype-wildcard", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:peppol:pint:selfbilling-1@ae-1", "2.1"), "AE PINT Self-Billing Invoice v1.0", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:selfbilling")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:peppol:pint:selfbilling-1@ae-1::2.1</code><br>
     * Same as {@link #CREDITNOTE_PEPPOL_PINT_SELFBILLING_1_AE_1}
     * 
     * @since code list 9.1
     */
    WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_selfbilling_1_ae_1__2_1("peppol-doctype-wildcard", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:peppol:pint:selfbilling-1@ae-1", "2.1"), "AE PINT Self-Billing Credit Note v1.0", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:selfbilling")),

    /**
     * <code>urn:fdc:peppol:tax-data-document:1.0::TaxData##urn:peppol:pint:taxdata-1@ae-1::1.0</code><br>
     * Same as {@link #TAXDATA_PEPPOL_PINT_TAXDATA_1_AE_1}
     * 
     * @since code list 9.1
     */
    WILDCARD_urn_fdc_peppol_tax_data_document_1_0__TaxData__urn_peppol_pint_taxdata_1_ae_1__1_0("peppol-doctype-wildcard", new PeppolDocumentTypeIdentifierParts("urn:fdc:peppol:tax-data-document:1.0", "TaxData", "urn:peppol:pint:taxdata-1@ae-1", "1.0"), "AE Tax Data Document v1.0", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:taxreporting")),

    /**
     * <code>urn:fdc:peppol:tax-data-status:1.0::TaxDataStatus##urn:peppol:pint:taxdatastatus-1@ae-1::1.0</code><br>
     * Same as {@link #TAXDATASTATUS_PEPPOL_PINT_TAXDATASTATUS_1_AE_1}
     * 
     * @since code list 9.1
     */
    WILDCARD_urn_fdc_peppol_tax_data_status_1_0__TaxDataStatus__urn_peppol_pint_taxdatastatus_1_ae_1__1_0("peppol-doctype-wildcard", new PeppolDocumentTypeIdentifierParts("urn:fdc:peppol:tax-data-status:1.0", "TaxDataStatus", "urn:peppol:pint:taxdatastatus-1@ae-1", "1.0"), "AE Tax Data Status v1.0", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:bis:taxreporting")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#conformant#urn:peppol:eb2b:1.0::2.1</code><br>
     * Same as {@link #INVOICE_CEN_EU_EN16931_2017_CONFORMANT_PEPPOL_EB2B_1_0}
     * 
     * @since code list 9.1
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_peppol_eb2b_1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#conformant#urn:peppol:eb2b:1.0", "2.1"), "EN 16931 Invoice - eB2B Extension", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "eB2B", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:eb2b:billing", "cenbii-procid-ubl::urn:peppol:eb2b:order_desadv_billing", "cenbii-procid-ubl::urn:peppol:eb2b:ordering_desadv_billing")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#conformant#urn:peppol:eb2b:1.0::2.1</code><br>
     * Same as {@link #CREDITNOTE_CEN_EU_EN16931_2017_CONFORMANT_PEPPOL_EB2B_1_0}
     * 
     * @since code list 9.1
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_peppol_eb2b_1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#conformant#urn:peppol:eb2b:1.0", "2.1"), "EN 16931 Credit Note - eB2B Extension", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "eB2B", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:eb2b:billing", "cenbii-procid-ubl::urn:peppol:eb2b:order_desadv_billing", "cenbii-procid-ubl::urn:peppol:eb2b:ordering_desadv_billing")),

    /**
     * <code>urn:peppol:doctype:edifact:ORDERS##eb2b::0</code><br>
     * Same as {@link #_EB2B}
     * 
     * @since code list 9.1
     */
    urn_peppol_doctype_edifact_ORDERS__eb2b__0("busdox-docid-qns", new PeppolGenericDocumentTypeIdentifierParts("urn:peppol:doctype:edifact:ORDERS", "eb2b", "0"), "EDIFACT Order - eB2B Extension", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "eB2B", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:eb2b:order", "cenbii-procid-ubl::urn:peppol:eb2b:ordering", "cenbii-procid-ubl::urn:peppol:eb2b:order_desadv_billing", "cenbii-procid-ubl::urn:peppol:eb2b:ordering_desadv_billing")),

    /**
     * <code>urn:peppol:doctype:edifact:ORDRSP##eb2b::0</code><br>
     * Same as {@link #_EB2B2}
     * 
     * @since code list 9.1
     */
    urn_peppol_doctype_edifact_ORDRSP__eb2b__0("busdox-docid-qns", new PeppolGenericDocumentTypeIdentifierParts("urn:peppol:doctype:edifact:ORDRSP", "eb2b", "0"), "EDIFACT Order Response - eB2B Extension", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "eB2B", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:eb2b:ordering", "cenbii-procid-ubl::urn:peppol:eb2b:ordering_desadv_billing")),

    /**
     * <code>urn:peppol:doctype:edifact:DESADV##eb2b::0</code><br>
     * Same as {@link #_EB2B3}
     * 
     * @since code list 9.1
     */
    urn_peppol_doctype_edifact_DESADV__eb2b__0("busdox-docid-qns", new PeppolGenericDocumentTypeIdentifierParts("urn:peppol:doctype:edifact:DESADV", "eb2b", "0"), "EDIFACT Despatch Advice - eB2B Extension", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "eB2B", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:eb2b:order_desadv_billing", "cenbii-procid-ubl::urn:peppol:eb2b:ordering_desadv_billing")),

    /**
     * <code>urn:peppol:doctype:edifact:INVOIC##eb2b::0</code><br>
     * Same as {@link #_EB2B4}
     * 
     * @since code list 9.1
     */
    urn_peppol_doctype_edifact_INVOIC__eb2b__0("busdox-docid-qns", new PeppolGenericDocumentTypeIdentifierParts("urn:peppol:doctype:edifact:INVOIC", "eb2b", "0"), "EDIFACT Invoice - eB2B Extension", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "eB2B", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:eb2b:billing", "cenbii-procid-ubl::urn:peppol:eb2b:order_desadv_billing", "cenbii-procid-ubl::urn:peppol:eb2b:ordering_desadv_billing")),

    /**
     * <code>urn:peppol:doctype:x12:850##eb2b::0</code><br>
     * Same as {@link #_EB2B5}
     * 
     * @since code list 9.1
     */
    urn_peppol_doctype_x12_850__eb2b__0("busdox-docid-qns", new PeppolGenericDocumentTypeIdentifierParts("urn:peppol:doctype:x12:850", "eb2b", "0"), "X12 Order - eB2B Extension", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "eB2B", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:eb2b:order", "cenbii-procid-ubl::urn:peppol:eb2b:ordering", "cenbii-procid-ubl::urn:peppol:eb2b:order_desadv_billing", "cenbii-procid-ubl::urn:peppol:eb2b:ordering_desadv_billing")),

    /**
     * <code>urn:peppol:doctype:x12:855##eb2b::0</code><br>
     * Same as {@link #_EB2B6}
     * 
     * @since code list 9.1
     */
    urn_peppol_doctype_x12_855__eb2b__0("busdox-docid-qns", new PeppolGenericDocumentTypeIdentifierParts("urn:peppol:doctype:x12:855", "eb2b", "0"), "X12 Order Response - eB2B Extension", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "eB2B", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:eb2b:ordering", "cenbii-procid-ubl::urn:peppol:eb2b:ordering_desadv_billing")),

    /**
     * <code>urn:peppol:doctype:x12:856##eb2b::0</code><br>
     * Same as {@link #_EB2B7}
     * 
     * @since code list 9.1
     */
    urn_peppol_doctype_x12_856__eb2b__0("busdox-docid-qns", new PeppolGenericDocumentTypeIdentifierParts("urn:peppol:doctype:x12:856", "eb2b", "0"), "X12 Despatch Advice - eB2B Extension", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "eB2B", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:eb2b:order_desadv_billing", "cenbii-procid-ubl::urn:peppol:eb2b:ordering_desadv_billing")),

    /**
     * <code>urn:peppol:doctype:x12:810##eb2b::0</code><br>
     * Same as {@link #_EB2B8}
     * 
     * @since code list 9.1
     */
    urn_peppol_doctype_x12_810__eb2b__0("busdox-docid-qns", new PeppolGenericDocumentTypeIdentifierParts("urn:peppol:doctype:x12:810", "eb2b", "0"), "X12 Invoice - eB2B Extension", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "eB2B", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:eb2b:billing", "cenbii-procid-ubl::urn:peppol:eb2b:order_desadv_billing", "cenbii-procid-ubl::urn:peppol:eb2b:ordering_desadv_billing")),

    /**
     * <code>urn:peppol:doctype:pdf+xml##eb2b:order-x:1.0::0</code><br>
     * Same as {@link #_EB2B_ORDER_X_1_0}
     * 
     * @since code list 9.1
     */
    urn_peppol_doctype_pdf_xml__eb2b_order_x_1_0__0("busdox-docid-qns", new PeppolGenericDocumentTypeIdentifierParts("urn:peppol:doctype:pdf+xml", "eb2b:order-x:1.0", "0"), "Hybrid Order - eB2B Extension", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "eB2B", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:eb2b:order")),

    /**
     * <code>urn:peppol:doctype:pdf+xml##eb2b:factur-x:1.0::0</code><br>
     * Same as {@link #_EB2B_FACTUR_X_1_0}
     * 
     * @since code list 9.1
     */
    urn_peppol_doctype_pdf_xml__eb2b_factur_x_1_0__0("busdox-docid-qns", new PeppolGenericDocumentTypeIdentifierParts("urn:peppol:doctype:pdf+xml", "eb2b:factur-x:1.0", "0"), "Hybrid Invoice - eB2B Extension", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "eB2B", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:eb2b:billing")),

    /**
     * <code>urn:peppol:doctype:bilateral##eb2b:order::0</code><br>
     * Same as {@link #_EB2B_ORDER}
     * 
     * @since code list 9.1
     */
    urn_peppol_doctype_bilateral__eb2b_order__0("busdox-docid-qns", new PeppolGenericDocumentTypeIdentifierParts("urn:peppol:doctype:bilateral", "eb2b:order", "0"), "Bilaterally agreed Order - eB2B Extension", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "eB2B", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:eb2b:order", "cenbii-procid-ubl::urn:peppol:eb2b:ordering", "cenbii-procid-ubl::urn:peppol:eb2b:order_desadv_billing", "cenbii-procid-ubl::urn:peppol:eb2b:ordering_desadv_billing")),

    /**
     * <code>urn:peppol:doctype:bilateral##eb2b:order_response::0</code><br>
     * Same as {@link #_EB2B_ORDER_RESPONSE}
     * 
     * @since code list 9.1
     */
    urn_peppol_doctype_bilateral__eb2b_order_response__0("busdox-docid-qns", new PeppolGenericDocumentTypeIdentifierParts("urn:peppol:doctype:bilateral", "eb2b:order_response", "0"), "Bilaterally agreed Order Response - eB2B Extension", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "eB2B", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:eb2b:ordering", "cenbii-procid-ubl::urn:peppol:eb2b:ordering_desadv_billing")),

    /**
     * <code>urn:peppol:doctype:bilateral##eb2b:despatch_advice::0</code><br>
     * Same as {@link #_EB2B_DESPATCH_ADVICE}
     * 
     * @since code list 9.1
     */
    urn_peppol_doctype_bilateral__eb2b_despatch_advice__0("busdox-docid-qns", new PeppolGenericDocumentTypeIdentifierParts("urn:peppol:doctype:bilateral", "eb2b:despatch_advice", "0"), "Bilaterally agreed Despatch Advice - eB2B Extension", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "eB2B", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:eb2b:order_desadv_billing", "cenbii-procid-ubl::urn:peppol:eb2b:ordering_desadv_billing")),

    /**
     * <code>urn:peppol:doctype:bilateral##eb2b:invoice::0</code><br>
     * Same as {@link #_EB2B_INVOICE}
     * 
     * @since code list 9.1
     */
    urn_peppol_doctype_bilateral__eb2b_invoice__0("busdox-docid-qns", new PeppolGenericDocumentTypeIdentifierParts("urn:peppol:doctype:bilateral", "eb2b:invoice", "0"), "Bilaterally agreed Invoice - eB2B Extension", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "eB2B", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:eb2b:billing", "cenbii-procid-ubl::urn:peppol:eb2b:order_desadv_billing", "cenbii-procid-ubl::urn:peppol:eb2b:ordering_desadv_billing")),

    /**
     * <code>urn:peppol:doctype:bilateral##eb2b:bilateral::0</code><br>
     * Same as {@link #_EB2B_BILATERAL}
     * 
     * @since code list 9.1
     */
    urn_peppol_doctype_bilateral__eb2b_bilateral__0("busdox-docid-qns", new PeppolGenericDocumentTypeIdentifierParts("urn:peppol:doctype:bilateral", "eb2b:bilateral", "0"), "Bilaterally agreed Document - eB2B Extension", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "eB2B", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:eb2b:oneway")),

    /**
     * <code>http://www.ketenstandaard.nl/onderhoudsstatus/SALES/005::MaintenanceStatus##dico:maintenancestatus@nl-1.0::1.0</code><br>
     * Same as {@link #MAINTENANCESTATUS_DICO_MAINTENANCESTATUS_NL_1_0}
     * 
     * @since code list 9.1
     */
    http___www_ketenstandaard_nl_onderhoudsstatus_SALES_005__MaintenanceStatus__dico_maintenancestatus_nl_1_0__1_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("http://www.ketenstandaard.nl/onderhoudsstatus/SALES/005", "MaintenanceStatus", "dico:maintenancestatus@nl-1.0", "1.0"), "DICO Maintenance Status", Version.parse("9.1"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Extended use", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:dico:2018:maintenance:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:peppol:edec:mls:1.0::2.1</code><br>
     * Same as {@link #PEPPOL_MLS_1_0}
     * 
     * @since code list 9.2
     */
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_peppol_edec_mls_1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:peppol:edec:mls:1.0", "2.1"), "Peppol Message Level Status v1.0", Version.parse("9.2"), EPeppolCodeListItemState.ACTIVE, null, null, false, true, -1, "OO", new CommonsArrayList<>("cenbii-procid-ubl::urn:peppol:edec:mls")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:fdc:imda.gov.sg:trns:order_balance:1::2.1</code><br>
     * Same as {@link #ORDER_FDC_IMDA_GOV_SG_TRNS_ORDER_BALANCE_1}
     * 
     * @since code list 9.2
     */
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_fdc_imda_gov_sg_trns_order_balance_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:fdc:imda.gov.sg:trns:order_balance:1", "2.1"), "SG BIS Order Balance v1.0", Version.parse("9.2"), EPeppolCodeListItemState.ACTIVE, null, null, false, false, -1, "Extended use", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:imda.gov.sg:bis:order_balance:1"));
    public static final String CODE_LIST_VERSION = "9.2";
    public static final int CODE_LIST_ENTRY_COUNT = 304;
    /**
     * Same as {@link #urn_www_peppol_eu_schema_xsd_VirtualCompanyDossier_1__VirtualCompanyDossier__urn_www_cenbii_eu_transaction_biicoretrdm991_ver0_1__urn_www_peppol_eu_bis_peppol991a_ver1_0__0_1}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier VIRTUALCOMPANYDOSSIER_T991_BIS991A = EPredefinedDocumentTypeIdentifier.urn_www_peppol_eu_schema_xsd_VirtualCompanyDossier_1__VirtualCompanyDossier__urn_www_cenbii_eu_transaction_biicoretrdm991_ver0_1__urn_www_peppol_eu_bis_peppol991a_ver1_0__0_1;
    /**
     * Same as {@link #urn_www_peppol_eu_schema_xsd_VirtualCompanyDossierPackage_1__VirtualCompanyDossierPackage__urn_www_cenbii_eu_transaction_biicoretrdm992_ver0_1__urn_www_peppol_eu_bis_peppol992a_ver1_0__0_1}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier VIRTUALCOMPANYDOSSIERPACKAGE_T992_BIS992A = EPredefinedDocumentTypeIdentifier.urn_www_peppol_eu_schema_xsd_VirtualCompanyDossierPackage_1__VirtualCompanyDossierPackage__urn_www_cenbii_eu_transaction_biicoretrdm992_ver0_1__urn_www_peppol_eu_bis_peppol992a_ver1_0__0_1;
    /**
     * Same as {@link #urn_www_peppol_eu_schema_xsd_CatalogueTemplate_1__CatalogueTemplate__urn_www_cenbii_eu_transaction_biicoretrdm993_ver0_1__urn_www_peppol_eu_bis_peppol993a_ver1_0__0_1}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier CATALOGUETEMPLATE_T993_BIS993A = EPredefinedDocumentTypeIdentifier.urn_www_peppol_eu_schema_xsd_CatalogueTemplate_1__CatalogueTemplate__urn_www_cenbii_eu_transaction_biicoretrdm993_ver0_1__urn_www_peppol_eu_bis_peppol993a_ver1_0__0_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_www_cenbii_eu_transaction_biicoretrdm019_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier CATALOGUE_T019_BIS1A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_www_cenbii_eu_transaction_biicoretrdm019_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biicoretrdm057_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier APPLICATIONRESPONSE_T057_BIS1A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biicoretrdm057_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biicoretrdm058_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier APPLICATIONRESPONSE_T058_BIS1A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biicoretrdm058_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_www_cenbii_eu_transaction_biitrns019_ver2_0_extended_urn_www_peppol_eu_bis_peppol1a_ver4_0__2_1}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier CATALOGUE_T019_BIS1A_V40 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_www_cenbii_eu_transaction_biitrns019_ver2_0_extended_urn_www_peppol_eu_bis_peppol1a_ver4_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biicoretrdm001_ver1_0__urn_www_peppol_eu_bis_peppol3a_ver1_0__2_0}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier ORDER_T001_BIS3A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biicoretrdm001_ver1_0__urn_www_peppol_eu_bis_peppol3a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol03a_ver2_0__2_1}
     * 
     * @deprecated since v3 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier ORDER_T001_BIS03A_V20 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol03a_ver2_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol3a_ver2_0__2_1}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier ORDER_T001_BIS3A_V20 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol3a_ver2_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol4a_ver1_0__2_0}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier INVOICE_T010_BIS4A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol4a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biitrns010_ver2_0_extended_urn_www_peppol_eu_bis_peppol4a_ver2_0__2_1}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier INVOICE_T010_BIS4A_V20 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biitrns010_ver2_0_extended_urn_www_peppol_eu_bis_peppol4a_ver2_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier INVOICE_T010_BIS5A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_T014_BIS5A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm015_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier INVOICE_T015_BIS5A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm015_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biitrns010_ver2_0_extended_urn_www_peppol_eu_bis_peppol5a_ver2_0__2_1}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier INVOICE_T010_BIS5A_V20 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biitrns010_ver2_0_extended_urn_www_peppol_eu_bis_peppol5a_ver2_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biitrns014_ver2_0_extended_urn_www_peppol_eu_bis_peppol5a_ver2_0__2_1}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_T014_BIS5A_V20 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biitrns014_ver2_0_extended_urn_www_peppol_eu_bis_peppol5a_ver2_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biicoretrdm001_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier ORDER_T001_BIS6A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biicoretrdm001_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponseSimple_2__OrderResponseSimple__urn_www_cenbii_eu_transaction_biicoretrdm002_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier ORDERRESPONSESIMPLE_T002_BIS6A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderResponseSimple_2__OrderResponseSimple__urn_www_cenbii_eu_transaction_biicoretrdm002_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponseSimple_2__OrderResponseSimple__urn_www_cenbii_eu_transaction_biicoretrdm003_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier ORDERRESPONSESIMPLE_T003_BIS6A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderResponseSimple_2__OrderResponseSimple__urn_www_cenbii_eu_transaction_biicoretrdm003_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier INVOICE_T010_BIS6A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_T014_BIS6A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm015_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier INVOICE_T015_BIS6A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm015_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol4a_ver1_0_urn_www_difi_no_ehf_faktura_ver1__2_0}
     * 
     * @deprecated since v2 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier INVOICE_T010_BIS4A_WWW_DIFI_NO_EHF_FAKTURA_VER1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol4a_ver1_0_urn_www_difi_no_ehf_faktura_ver1__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_cenbii_eu_profile_biixx_ver1_0_urn_www_difi_no_ehf_kreditnota_ver1__2_0}
     */
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_T014_WWW_CENBII_EU_PROFILE_BIIXX_VER1_0_WWW_DIFI_NO_EHF_KREDITNOTA_VER1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_cenbii_eu_profile_biixx_ver1_0_urn_www_difi_no_ehf_kreditnota_ver1__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier ORDER_T001_BIS28A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__Order__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1}
     * 
     * @deprecated since v3 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier ORDER_T076_BIS28A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__Order__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier ORDER_T076_BIS28A2 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_www_cenbii_eu_transaction_biitrns016_ver1_0_extended_urn_www_peppol_eu_bis_peppol30a_ver1_0__2_1}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier DESPATCHADVICE_T016_BIS30A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_www_cenbii_eu_transaction_biitrns016_ver1_0_extended_urn_www_peppol_eu_bis_peppol30a_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biitrns071_ver2_0_extended_urn_www_peppol_eu_bis_peppol36a_ver1_0__2_1}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier APPLICATIONRESPONSE_T071_BIS36A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biitrns071_ver2_0_extended_urn_www_peppol_eu_bis_peppol36a_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_EN16931_PEPPOL_V30 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_EN16931_PEPPOL_V30 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0_extended_urn_fdc_peppol_authority_co_uk_spec_ordering_ver1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier DHSC_CUSTOMIZED_ORDERING_PROFILE_V1_ORDER = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0_extended_urn_fdc_peppol_authority_co_uk_spec_ordering_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__Order__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0_extended_urn_fdc_peppol_authority_co_uk_spec_ordering_ver1_0__2_1}
     * 
     * @deprecated since v8.0 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier DHSC_CUSTOMIZED_ORDERING_PROFILE_V1_ORDER_RESPONSE = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__Order__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0_extended_urn_fdc_peppol_authority_co_uk_spec_ordering_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0_extended_urn_fdc_peppol_authority_co_uk_spec_ordering_ver1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier DHSC_CUSTOMIZED_ORDERING_PROFILE_V1_ORDER_RESPONSE2 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0_extended_urn_fdc_peppol_authority_co_uk_spec_ordering_ver1_0__2_1;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0__D16B}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_CII_EN16931_PEPPOL_V30 = EPredefinedDocumentTypeIdentifier.urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0__D16B;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestRequest_2__ExpressionOfInterestRequest__urn_www_cenbii_eu_transaction_biitrdm081_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t001_ver1_0__2_2}
     * 
     * @deprecated since v8.5 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier EXPRESSION_OF_INTEREST_REQUEST_V1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestRequest_2__ExpressionOfInterestRequest__urn_www_cenbii_eu_transaction_biitrdm081_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t001_ver1_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestResponse_2__ExpressionOfInterestResponse__urn_www_cenbii_eu_transaction_biitrdm082_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t002_ver1_0__2_2}
     * 
     * @deprecated since v8.5 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier EXPRESSION_OF_INTEREST_RESPONSE_V1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestResponse_2__ExpressionOfInterestResponse__urn_www_cenbii_eu_transaction_biitrdm082_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t002_ver1_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_TenderStatusRequest_2__TenderStatusRequest__urn_www_cenbii_eu_transaction_biitrdm097_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t003_ver1_0__2_2}
     * 
     * @deprecated since v8.5 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier TENDER_STATUS_REQUEST_V1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_TenderStatusRequest_2__TenderStatusRequest__urn_www_cenbii_eu_transaction_biitrdm097_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t003_ver1_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CallForTenders_2__CallForTenders__urn_www_cenbii_eu_transaction_biitrdm083_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t004_ver1_0__2_2}
     * 
     * @deprecated since v8.5 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier CALL_FOR_TENDERS_V1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CallForTenders_2__CallForTenders__urn_www_cenbii_eu_transaction_biitrdm083_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t004_ver1_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_TenderReceipt_2__TenderReceipt__urn_www_cenbii_eu_transaction_biitrdm045_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t006_ver1_0__2_2}
     * 
     * @deprecated since v8.5 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier TENDER_RECEIPT_V1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_TenderReceipt_2__TenderReceipt__urn_www_cenbii_eu_transaction_biitrdm045_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t006_ver1_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Tender_2__Tender__urn_www_cenbii_eu_transaction_biitrdm090_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t005_ver1_0__2_2}
     * 
     * @deprecated since v8.5 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier TENDER_V1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Tender_2__Tender__urn_www_cenbii_eu_transaction_biitrdm090_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t005_ver1_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_1__2_1}
     * 
     * @deprecated since v5 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_INVOICE_UBL_V11 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_1__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_1__2_1}
     * 
     * @deprecated since v5 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_CREDIT_NOTE_UBL_V11 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_1__2_1;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_1__D16B}
     * 
     * @deprecated since v5 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_INVOICE_CII_V11 = EPredefinedDocumentTypeIdentifier.urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_1__D16B;
    /**
     * Same as {@link #urn_oioubl_names_specification_oioubl_schema_xsd_UtilityStatement_2__UtilityStatement__OIOUBL_2_02__2_0}
     */
    public static final EPredefinedDocumentTypeIdentifier OIOUBL_UTILITY_STATEMENT_202 = EPredefinedDocumentTypeIdentifier.urn_oioubl_names_specification_oioubl_schema_xsd_UtilityStatement_2__UtilityStatement__OIOUBL_2_02__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Reminder_2__Reminder__OIOUBL_2_02__2_0}
     */
    public static final EPredefinedDocumentTypeIdentifier OIOUBL_REMINDER_202 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Reminder_2__Reminder__OIOUBL_2_02__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_UBL_BE_1_0_0_20180214__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier UBL_BE_INVOICE_UBL_V11 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_UBL_BE_1_0_0_20180214__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_UBL_BE_1_0_0_20180214__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier UBL_BE_CREDIT_NOTE_UBL_V11 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_UBL_BE_1_0_0_20180214__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_peppol_eu_transaction_biitrns111_ver1_0__2_1}
     * 
     * @deprecated since v7 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier APPLICATIONRESPONSE_WWW_PEPPOL_EU_TRANSACTION_BIITRNS111_VER1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_peppol_eu_transaction_biitrns111_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_fdc_peppol_eu_poacc_trns_catalogue_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CATALOGUE_FDC_PEPPOL_EU_POACC_TRNS_CATALOGUE_3 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_fdc_peppol_eu_poacc_trns_catalogue_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_poacc_trns_catalogue_response_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier APPLICATIONRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_CATALOGUE_RESPONSE_3 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_poacc_trns_catalogue_response_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_fdc_peppol_eu_poacc_trns_order_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier ORDER_FDC_PEPPOL_EU_POACC_TRNS_ORDER_3 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_fdc_peppol_eu_poacc_trns_order_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_poacc_trns_invoice_response_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier APPLICATIONRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_INVOICE_RESPONSE_3 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_poacc_trns_invoice_response_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_fdc_peppol_eu_poacc_trns_punch_out_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CATALOGUE_FDC_PEPPOL_EU_POACC_TRNS_PUNCH_OUT_3 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_fdc_peppol_eu_poacc_trns_punch_out_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_response_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier ORDERRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_ORDER_RESPONSE_3 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_response_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_fdc_peppol_eu_poacc_trns_despatch_advice_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier DESPATCHADVICE_FDC_PEPPOL_EU_POACC_TRNS_DESPATCH_ADVICE_3 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_fdc_peppol_eu_poacc_trns_despatch_advice_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_agreement_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier ORDERRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_ORDER_AGREEMENT_3 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_agreement_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_poacc_trns_mlr_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier APPLICATIONRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_MLR_3 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_poacc_trns_mlr_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_nen_nl_nlcius_v1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_CEN_EU_EN16931_2017_COMPLIANT_FDC_NEN_NL_NLCIUS_V1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_nen_nl_nlcius_v1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_nen_nl_nlcius_v1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_CEN_EU_EN16931_2017_COMPLIANT_FDC_NEN_NL_NLCIUS_V1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_nen_nl_nlcius_v1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_sg_3_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_CEN_EU_EN16931_2017_CONFORMANT_FDC_PEPPOL_EU_2017_POACC_BILLING_INTERNATIONAL_SG_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_sg_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_sg_3_0__2_1}
     * 
     * @deprecated since v6 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_CEN_EU_EN16931_2017_CONFORMANT_FDC_PEPPOL_EU_2017_POACC_BILLING_INTERNATIONAL_SG_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_sg_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_sg_3_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_CEN_EU_EN16931_2017_CONFORMANT_FDC_PEPPOL_EU_2017_POACC_BILLING_INTERNATIONAL_SG_3_02 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_sg_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_2__2_1}
     * 
     * @deprecated since v8.3 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_INVOICE_UBL_V12 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_2__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_2__2_1}
     * 
     * @deprecated since v8.3 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_CREDIT_NOTE_UBL_V12 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_2__2_1;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_2__D16B}
     * 
     * @deprecated since v8.3 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_INVOICE_CII_V12 = EPredefinedDocumentTypeIdentifier.urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_2__D16B;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_fdc_www_efaktura_gov_pl_ver1_0_trns_account_corr_ver1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_FDC_WWW_EFAKTURA_GOV_PL_VER1_0_TRNS_ACCOUNT_CORR_VER1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_fdc_www_efaktura_gov_pl_ver1_0_trns_account_corr_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_extended_urn_fdc_www_efaktura_gov_pl_ver1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_CEN_EU_EN16931_2017_COMPLIANT_FDC_PEPPOL_EU_2017_POACC_BILLING_3_0_EXTENDED_FDC_WWW_EFAKTURA_GOV_PL_VER1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_extended_urn_fdc_www_efaktura_gov_pl_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ReceiptAdvice_2__ReceiptAdvice__urn_fdc_www_efaktura_gov_pl_ver1_0_trns_receipt_advice_ver1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier RECEIPTADVICE_FDC_WWW_EFAKTURA_GOV_PL_VER1_0_TRNS_RECEIPT_ADVICE_VER1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ReceiptAdvice_2__ReceiptAdvice__urn_fdc_www_efaktura_gov_pl_ver1_0_trns_receipt_advice_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_aunz_3_0__2_1}
     * 
     * @deprecated since v9.2 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier INVOICE_CEN_EU_EN16931_2017_CONFORMANT_FDC_PEPPOL_EU_2017_POACC_BILLING_INTERNATIONAL_AUNZ_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_aunz_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_aunz_3_0__2_1}
     * 
     * @deprecated since v9.2 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_CEN_EU_EN16931_2017_CONFORMANT_FDC_PEPPOL_EU_2017_POACC_BILLING_INTERNATIONAL_AUNZ_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_aunz_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_selfbilling_international_aunz_3_0__2_1}
     * 
     * @deprecated since v9.2 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier INVOICE_CEN_EU_EN16931_2017_CONFORMANT_FDC_PEPPOL_EU_2017_POACC_SELFBILLING_INTERNATIONAL_AUNZ_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_selfbilling_international_aunz_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_selfbilling_international_aunz_3_0__2_1}
     * 
     * @deprecated since v9.2 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_CEN_EU_EN16931_2017_CONFORMANT_FDC_PEPPOL_EU_2017_POACC_SELFBILLING_INTERNATIONAL_AUNZ_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_selfbilling_international_aunz_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biitrns010_ver2_0_extended_urn_www_peppol_eu_bis_peppol4a_ver2_0_extended_urn_www_simplerinvoicing_org_si_si_ubl_ver1_2__2_1}
     * 
     * @deprecated since v8.7 - this item should not be used to issue new identifiers!<br>Removed per 2024-01-01
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier INVOICE_WWW_CENBII_EU_TRANSACTION_BIITRNS010_VER2_0_EXTENDED_WWW_PEPPOL_EU_BIS_PEPPOL4A_VER2_0_EXTENDED_WWW_SIMPLERINVOICING_ORG_SI_SI_UBL_VER1_2 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biitrns010_ver2_0_extended_urn_www_peppol_eu_bis_peppol4a_ver2_0_extended_urn_www_simplerinvoicing_org_si_si_ubl_ver1_2__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol3a_ver2_0_extended_urn_www_simplerinvoicing_org_si_si_ubl_ver1_2__2_1}
     * 
     * @deprecated since v8.7 - this item should not be used to issue new identifiers!<br>Removed per 2024-01-01
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier ORDER_WWW_CENBII_EU_TRANSACTION_BIITRNS001_VER2_0_EXTENDED_WWW_PEPPOL_EU_BIS_PEPPOL3A_VER2_0_EXTENDED_WWW_SIMPLERINVOICING_ORG_SI_SI_UBL_VER1_2 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol3a_ver2_0_extended_urn_www_simplerinvoicing_org_si_si_ubl_ver1_2__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_3__2_1}
     * 
     * @deprecated since v7.1 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_INVOICE_UBL_V13 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_3__2_1}
     * 
     * @deprecated since v7.1 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_CREDIT_NOTE_UBL_V13 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_3__2_1;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_3__16B}
     * 
     * @deprecated since v7.1 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_INVOICE_CII_V13 = EPredefinedDocumentTypeIdentifier.urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_3__16B;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_INVOICE_UBL_V20 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_CREDIT_NOTE_UBL_V20 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0__2_1;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0__16B}
     * 
     * @deprecated since v7.4 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_INVOICE_CII_V20 = EPredefinedDocumentTypeIdentifier.urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0__16B;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0__D16B}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_INVOICE_CII_V202 = EPredefinedDocumentTypeIdentifier.urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0__D16B;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_3_conformant_urn_xoev_de_kosit_extension_xrechnung_1_3__2_1}
     * 
     * @deprecated since v7.1 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_EXTENSION_INVOICE_UBL_V13 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_3_conformant_urn_xoev_de_kosit_extension_xrechnung_1_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_3_conformant_urn_xoev_de_kosit_extension_xrechnung_1_3__2_1}
     * 
     * @deprecated since v7.1 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_EXTENSION_CREDIT_NOTE_UBL_V13 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_3_conformant_urn_xoev_de_kosit_extension_xrechnung_1_3__2_1;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_3_conformant_urn_xoev_de_kosit_extension_xrechnung_1_3__16B}
     * 
     * @deprecated since v7.1 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_EXTENSION_INVOICE_CII_V13 = EPredefinedDocumentTypeIdentifier.urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_3_conformant_urn_xoev_de_kosit_extension_xrechnung_1_3__16B;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_nen_nl_nlcius_v1_0_conformant_urn_fdc_nen_nl_gaccount_v1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_CEN_EU_EN16931_2017_COMPLIANT_FDC_NEN_NL_NLCIUS_V1_0_CONFORMANT_FDC_NEN_NL_GACCOUNT_V1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_nen_nl_nlcius_v1_0_conformant_urn_fdc_nen_nl_gaccount_v1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_fdc_peppol_eu_poacc_trns_order_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_1}
     * 
     * @deprecated since v7.3 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier ORDER_FDC_PEPPOL_EU_POACC_TRNS_ORDER_3_EXTENDED_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_fdc_peppol_eu_poacc_trns_order_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderChange_2__OrderChange__urn_fdc_anskaffelser_no_2019_ehf_spec_adv_order_change_3_0__2_1}
     * 
     * @deprecated since v7.3 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier ORDERCHANGE_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_ADV_ORDER_CHANGE_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderChange_2__OrderChange__urn_fdc_anskaffelser_no_2019_ehf_spec_adv_order_change_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderCancellation_2__OrderCancellation__urn_fdc_anskaffelser_no_2019_ehf_spec_adv_order_cancellation_3_0__2_1}
     * 
     * @deprecated since v7.3 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier ORDERCANCELLATION_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_ADV_ORDER_CANCELLATION_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderCancellation_2__OrderCancellation__urn_fdc_anskaffelser_no_2019_ehf_spec_adv_order_cancellation_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_response_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_1}
     * 
     * @deprecated since v7.3 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier ORDERRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_ORDER_RESPONSE_3_EXTENDED_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_response_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_CEN_EU_EN16931_2017 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_CEN_EU_EN16931_2017 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017__2_1;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017__D16B}
     */
    public static final EPredefinedDocumentTypeIdentifier CROSSINDUSTRYINVOICE_CEN_EU_EN16931_2017 = EPredefinedDocumentTypeIdentifier.urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017__D16B;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0_conformant_urn_xoev_de_kosit_extension_xrechnung_2_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_EXTENSION_INVOICE_UBL_V20 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0_conformant_urn_xoev_de_kosit_extension_xrechnung_2_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0_conformant_urn_xoev_de_kosit_extension_xrechnung_2_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_EXTENSION_CREDIT_NOTE_UBL_V20 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0_conformant_urn_xoev_de_kosit_extension_xrechnung_2_0__2_1;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0_conformant_urn_xoev_de_kosit_extension_xrechnung_2_0__16B}
     * 
     * @deprecated since v7.4 - this item should not be used to issue new identifiers!<br>Removed per 2023-05-24
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_EXTENSION_INVOICE_CII_V20 = EPredefinedDocumentTypeIdentifier.urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0_conformant_urn_xoev_de_kosit_extension_xrechnung_2_0__16B;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0_conformant_urn_xoev_de_kosit_extension_xrechnung_2_0__D16B}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_EXTENSION_INVOICE_CII_V202 = EPredefinedDocumentTypeIdentifier.urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_0_conformant_urn_xoev_de_kosit_extension_xrechnung_2_0__D16B;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_fdc_peppol_eu_poacc_trns_order_3_restrictive_urn_www_agid_gov_it_trns_ordine_3_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier ORDER_FDC_PEPPOL_EU_POACC_TRNS_ORDER_3_RESTRICTIVE_WWW_AGID_GOV_IT_TRNS_ORDINE_3_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_fdc_peppol_eu_poacc_trns_order_3_restrictive_urn_www_agid_gov_it_trns_ordine_3_1__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_response_3_restrictive_urn_www_agid_gov_it_trns_risposta_ordine_3_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier ORDERRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_ORDER_RESPONSE_3_RESTRICTIVE_WWW_AGID_GOV_IT_TRNS_RISPOSTA_ORDINE_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_response_3_restrictive_urn_www_agid_gov_it_trns_risposta_ordine_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_fdc_peppol_eu_poacc_trns_despatch_advice_3_extended_urn_www_agid_gov_it_trns_ddt_3_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier DESPATCHADVICE_FDC_PEPPOL_EU_POACC_TRNS_DESPATCH_ADVICE_3_EXTENDED_WWW_AGID_GOV_IT_TRNS_DDT_3_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_fdc_peppol_eu_poacc_trns_despatch_advice_3_extended_urn_www_agid_gov_it_trns_ddt_3_1__2_1;
    /**
     * Same as {@link #urn_kosit_names_spec_peppol_reporting_schema_xsd_Reporting_1__APData__Reporting__1_0}
     * 
     * @deprecated since v8.8 - this item should not be used to issue new identifiers!<br>Removed per 2024-06-30
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier APDATA_REPORTING = EPredefinedDocumentTypeIdentifier.urn_kosit_names_spec_peppol_reporting_schema_xsd_Reporting_1__APData__Reporting__1_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_fdc_peppol_eu_poacc_trns_order_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier ORDER_FDC_PEPPOL_EU_POACC_TRNS_ORDER_3_EXTENDED_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_3_02 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_fdc_peppol_eu_poacc_trns_order_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderChange_2__OrderChange__urn_fdc_anskaffelser_no_2019_ehf_spec_adv_order_change_3_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier ORDERCHANGE_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_ADV_ORDER_CHANGE_3_02 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderChange_2__OrderChange__urn_fdc_anskaffelser_no_2019_ehf_spec_adv_order_change_3_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderCancellation_2__OrderCancellation__urn_fdc_anskaffelser_no_2019_ehf_spec_adv_order_cancellation_3_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier ORDERCANCELLATION_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_ADV_ORDER_CANCELLATION_3_02 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderCancellation_2__OrderCancellation__urn_fdc_anskaffelser_no_2019_ehf_spec_adv_order_cancellation_3_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_response_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier ORDERRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_ORDER_RESPONSE_3_EXTENDED_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_3_02 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_response_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_2;
    /**
     * Same as {@link #urn_iso_std_iso_20022_tech_xsd_pain_001_001_03__Document__urn_fdc_bits_no_2017_iso20022_1_5__03}
     */
    public static final EPredefinedDocumentTypeIdentifier DOCUMENT_FDC_BITS_NO_2017_ISO20022_1_5 = EPredefinedDocumentTypeIdentifier.urn_iso_std_iso_20022_tech_xsd_pain_001_001_03__Document__urn_fdc_bits_no_2017_iso20022_1_5__03;
    /**
     * Same as {@link #urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_pain_002_001_03_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier RECEPTIONACKNOWLEDGEMENT_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_PAIN_002_001_03_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5 = EPredefinedDocumentTypeIdentifier.urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_pain_002_001_03_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0;
    /**
     * Same as {@link #urn_fdc_difi_no_2017_payment_extras_1__HandlingException__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_pain_002_001_03_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier HANDLINGEXCEPTION_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_PAIN_002_001_03_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5 = EPredefinedDocumentTypeIdentifier.urn_fdc_difi_no_2017_payment_extras_1__HandlingException__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_pain_002_001_03_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0;
    /**
     * Same as {@link #urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_054_001_02_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier RECEPTIONACKNOWLEDGEMENT_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_054_001_02_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5 = EPredefinedDocumentTypeIdentifier.urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_054_001_02_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0;
    /**
     * Same as {@link #urn_fdc_difi_no_2017_payment_extras_1__HandlingException__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_054_001_02_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier HANDLINGEXCEPTION_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_054_001_02_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5 = EPredefinedDocumentTypeIdentifier.urn_fdc_difi_no_2017_payment_extras_1__HandlingException__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_054_001_02_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0;
    /**
     * Same as {@link #urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_pain_001_001_03_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier RECEPTIONACKNOWLEDGEMENT_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_PAIN_001_001_03_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5 = EPredefinedDocumentTypeIdentifier.urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_pain_001_001_03_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0;
    /**
     * Same as {@link #urn_fdc_difi_no_2017_payment_extras_1__HandlingException__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_pain_001_001_03_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier HANDLINGEXCEPTION_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_PAIN_001_001_03_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5 = EPredefinedDocumentTypeIdentifier.urn_fdc_difi_no_2017_payment_extras_1__HandlingException__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_pain_001_001_03_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0;
    /**
     * Same as {@link #urn_iso_std_iso_20022_tech_xsd_pain_002_001_03__Document__urn_fdc_bits_no_2017_iso20022_1_5__03}
     */
    public static final EPredefinedDocumentTypeIdentifier DOCUMENT_FDC_BITS_NO_2017_ISO20022_1_52 = EPredefinedDocumentTypeIdentifier.urn_iso_std_iso_20022_tech_xsd_pain_002_001_03__Document__urn_fdc_bits_no_2017_iso20022_1_5__03;
    /**
     * Same as {@link #urn_iso_std_iso_20022_tech_xsd_camt_054_001_02__Document__urn_fdc_bits_no_2017_iso20022_1_5__02}
     */
    public static final EPredefinedDocumentTypeIdentifier DOCUMENT_FDC_BITS_NO_2017_ISO20022_1_53 = EPredefinedDocumentTypeIdentifier.urn_iso_std_iso_20022_tech_xsd_camt_054_001_02__Document__urn_fdc_bits_no_2017_iso20022_1_5__02;
    /**
     * Same as {@link #urn_iso_std_iso_20022_tech_xsd_camt_055_001_01__Document__urn_fdc_bits_no_2017_iso20022_1_5__01}
     */
    public static final EPredefinedDocumentTypeIdentifier DOCUMENT_FDC_BITS_NO_2017_ISO20022_1_54 = EPredefinedDocumentTypeIdentifier.urn_iso_std_iso_20022_tech_xsd_camt_055_001_01__Document__urn_fdc_bits_no_2017_iso20022_1_5__01;
    /**
     * Same as {@link #urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_029_001_03_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier RECEPTIONACKNOWLEDGEMENT_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_029_001_03_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5 = EPredefinedDocumentTypeIdentifier.urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_029_001_03_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0;
    /**
     * Same as {@link #urn_fdc_difi_no_2017_payment_extras_1__HandlingException__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_029_001_03_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier HANDLINGEXCEPTION_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_029_001_03_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5 = EPredefinedDocumentTypeIdentifier.urn_fdc_difi_no_2017_payment_extras_1__HandlingException__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_029_001_03_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0;
    /**
     * Same as {@link #urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_055_001_01_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier RECEPTIONACKNOWLEDGEMENT_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_055_001_01_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5 = EPredefinedDocumentTypeIdentifier.urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_055_001_01_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0;
    /**
     * Same as {@link #urn_fdc_difi_no_2017_payment_extras_1__HandlingException__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_055_001_01_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier HANDLINGEXCEPTION_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_055_001_01_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5 = EPredefinedDocumentTypeIdentifier.urn_fdc_difi_no_2017_payment_extras_1__HandlingException__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_055_001_01_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0;
    /**
     * Same as {@link #urn_iso_std_iso_20022_tech_xsd_camt_029_001_03__Document__urn_fdc_bits_no_2017_iso20022_1_5__03}
     */
    public static final EPredefinedDocumentTypeIdentifier DOCUMENT_FDC_BITS_NO_2017_ISO20022_1_55 = EPredefinedDocumentTypeIdentifier.urn_iso_std_iso_20022_tech_xsd_camt_029_001_03__Document__urn_fdc_bits_no_2017_iso20022_1_5__03;
    /**
     * Same as {@link #urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_urn_iso_std_iso_20022_tech_xsd_camt_054_001_02_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier RECEPTIONACKNOWLEDGEMENT_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_054_001_02_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_52 = EPredefinedDocumentTypeIdentifier.urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_urn_iso_std_iso_20022_tech_xsd_camt_054_001_02_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0;
    /**
     * Same as {@link #urn_iso_std_iso_20022_tech_xsd_camt_053_001_02__Document__urn_fdc_bits_no_2017_iso20022_1_5__02}
     */
    public static final EPredefinedDocumentTypeIdentifier DOCUMENT_FDC_BITS_NO_2017_ISO20022_1_56 = EPredefinedDocumentTypeIdentifier.urn_iso_std_iso_20022_tech_xsd_camt_053_001_02__Document__urn_fdc_bits_no_2017_iso20022_1_5__02;
    /**
     * Same as {@link #urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_053_001_02_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier RECEPTIONACKNOWLEDGEMENT_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_053_001_02_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5 = EPredefinedDocumentTypeIdentifier.urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_053_001_02_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0;
    /**
     * Same as {@link #urn_fdc_difi_no_2017_payment_extras_1__HandlingException__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_053_001_02_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier HANDLINGEXCEPTION_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_053_001_02_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5 = EPredefinedDocumentTypeIdentifier.urn_fdc_difi_no_2017_payment_extras_1__HandlingException__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_053_001_02_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0;
    /**
     * Same as {@link #urn_iso_std_iso_20022_tech_xsd_camt_052_001_02__Document__urn_fdc_bits_no_2017_iso20022_1_5__02}
     */
    public static final EPredefinedDocumentTypeIdentifier DOCUMENT_FDC_BITS_NO_2017_ISO20022_1_57 = EPredefinedDocumentTypeIdentifier.urn_iso_std_iso_20022_tech_xsd_camt_052_001_02__Document__urn_fdc_bits_no_2017_iso20022_1_5__02;
    /**
     * Same as {@link #urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_052_001_02_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier RECEPTIONACKNOWLEDGEMENT_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_052_001_02_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5 = EPredefinedDocumentTypeIdentifier.urn_fdc_difi_no_2017_payment_extras_1__ReceptionAcknowledgement__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_052_001_02_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0;
    /**
     * Same as {@link #urn_fdc_difi_no_2017_payment_extras_1__HandlingException__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_052_001_02_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier HANDLINGEXCEPTION_FDC_DIFI_NO_2017_PAYMENT_HANDLING_1_0_FOR_ISO_STD_ISO_20022_TECH_XSD_CAMT_052_001_02_RESTRICTED_FDC_BITS_NO_2017_ISO20022_1_5 = EPredefinedDocumentTypeIdentifier.urn_fdc_difi_no_2017_payment_extras_1__HandlingException__urn_fdc_difi_no_2017_payment_handling_1_0_for_urn_iso_std_iso_20022_tech_xsd_camt_052_001_02_restricted_urn_fdc_bits_no_2017_iso20022_1_5__1_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Enquiry_2__Enquiry__urn_fdc_peppol_eu_prac_trns_t007_1_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier ENQUIRY_FDC_PEPPOL_EU_PRAC_TRNS_T007_1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Enquiry_2__Enquiry__urn_fdc_peppol_eu_prac_trns_t007_1_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_EnquiryResponse_2__EnquiryResponse__urn_fdc_peppol_eu_prac_trns_t008_1_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier ENQUIRYRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T008_1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_EnquiryResponse_2__EnquiryResponse__urn_fdc_peppol_eu_prac_trns_t008_1_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Enquiry_2__Enquiry__urn_fdc_peppol_eu_prac_trns_t009_1_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier ENQUIRY_FDC_PEPPOL_EU_PRAC_TRNS_T009_1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Enquiry_2__Enquiry__urn_fdc_peppol_eu_prac_trns_t009_1_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_EnquiryResponse_2__EnquiryResponse__urn_fdc_peppol_eu_prac_trns_t010_1_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier ENQUIRYRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T010_1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_EnquiryResponse_2__EnquiryResponse__urn_fdc_peppol_eu_prac_trns_t010_1_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_www_cenbii_eu_transaction_biitrns019_ver2_0_extended_urn_www_peppol_eu_bis_peppol1a_ver2_0_extended_urn_www_difi_no_ehf_katalog_ver1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CATALOGUE_WWW_CENBII_EU_TRANSACTION_BIITRNS019_VER2_0_EXTENDED_WWW_PEPPOL_EU_BIS_PEPPOL1A_VER2_0_EXTENDED_WWW_DIFI_NO_EHF_KATALOG_VER1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_www_cenbii_eu_transaction_biitrns019_ver2_0_extended_urn_www_peppol_eu_bis_peppol1a_ver2_0_extended_urn_www_difi_no_ehf_katalog_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biitrns058_ver2_0_extended_urn_www_difi_no_ehf_katalogbekreftelse_ver1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier APPLICATIONRESPONSE_WWW_CENBII_EU_TRANSACTION_BIITRNS058_VER2_0_EXTENDED_WWW_DIFI_NO_EHF_KATALOGBEKREFTELSE_VER1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biitrns058_ver2_0_extended_urn_www_difi_no_ehf_katalogbekreftelse_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0_extended_urn_www_difi_no_ehf_ordre_ver1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier ORDER_WWW_CENBII_EU_TRANSACTION_BIITRNS001_VER2_0_EXTENDED_WWW_PEPPOL_EU_BIS_PEPPOL28A_VER1_0_EXTENDED_WWW_DIFI_NO_EHF_ORDRE_VER1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0_extended_urn_www_difi_no_ehf_ordre_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0_extended_urn_www_difi_no_ehf_ordrebekreftelse_ver1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier ORDERRESPONSE_WWW_CENBII_EU_TRANSACTION_BIITRNS076_VER2_0_EXTENDED_WWW_PEPPOL_EU_BIS_PEPPOL28A_VER1_0_EXTENDED_WWW_DIFI_NO_EHF_ORDREBEKREFTELSE_VER1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0_extended_urn_www_difi_no_ehf_ordrebekreftelse_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_www_cenbii_eu_transaction_biitrns016_ver1_0_extended_urn_www_peppol_eu_bis_peppol30a_ver1_0_extended_urn_www_difi_no_ehf_pakkseddel_ver1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier DESPATCHADVICE_WWW_CENBII_EU_TRANSACTION_BIITRNS016_VER1_0_EXTENDED_WWW_PEPPOL_EU_BIS_PEPPOL30A_VER1_0_EXTENDED_WWW_DIFI_NO_EHF_PAKKSEDDEL_VER1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_www_cenbii_eu_transaction_biitrns016_ver1_0_extended_urn_www_peppol_eu_bis_peppol30a_ver1_0_extended_urn_www_difi_no_ehf_pakkseddel_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Reminder_2__Reminder__urn_www_cenbii_eu_transaction_biicoretrdm017_ver1_0__urn_www_cenbii_eu_profile_biixy_ver1_0_urn_www_difi_no_ehf_purring_ver1__2_0}
     */
    public static final EPredefinedDocumentTypeIdentifier REMINDER_WWW_CENBII_EU_TRANSACTION_BIICORETRDM017_VER1_0__WWW_CENBII_EU_PROFILE_BIIXY_VER1_0_WWW_DIFI_NO_EHF_PURRING_VER1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Reminder_2__Reminder__urn_www_cenbii_eu_transaction_biicoretrdm017_ver1_0__urn_www_cenbii_eu_profile_biixy_ver1_0_urn_www_difi_no_ehf_purring_ver1__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_fdc_peppol_eu_poacc_trns_catalogue_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier CATALOGUE_FDC_PEPPOL_EU_POACC_TRNS_CATALOGUE_3_EXTENDED_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_fdc_peppol_eu_poacc_trns_catalogue_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_poacc_trns_catalogue_response_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier APPLICATIONRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_CATALOGUE_RESPONSE_3_EXTENDED_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_poacc_trns_catalogue_response_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_agreement_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier ORDERRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_ORDER_AGREEMENT_3_EXTENDED_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_agreement_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_fdc_peppol_eu_poacc_trns_despatch_advice_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier DESPATCHADVICE_FDC_PEPPOL_EU_POACC_TRNS_DESPATCH_ADVICE_3_EXTENDED_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_fdc_peppol_eu_poacc_trns_despatch_advice_3_extended_urn_fdc_anskaffelser_no_2019_ehf_spec_3_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_conformant_urn_fdc_anskaffelser_no_2019_ehf_reminder_3_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_CEN_EU_EN16931_2017_COMPLIANT_FDC_PEPPOL_EU_2017_POACC_BILLING_3_0_CONFORMANT_FDC_ANSKAFFELSER_NO_2019_EHF_REMINDER_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_conformant_urn_fdc_anskaffelser_no_2019_ehf_reminder_3_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_fdc_anskaffelser_no_2019_ehf_spec_payment_request_3_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_FDC_ANSKAFFELSER_NO_2019_EHF_SPEC_PAYMENT_REQUEST_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_fdc_anskaffelser_no_2019_ehf_spec_payment_request_3_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_conformant_urn_fdc_anskaffelser_no_2019_ehf_forward_billing_3_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_CEN_EU_EN16931_2017_COMPLIANT_FDC_PEPPOL_EU_2017_POACC_BILLING_3_0_CONFORMANT_FDC_ANSKAFFELSER_NO_2019_EHF_FORWARD_BILLING_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_conformant_urn_fdc_anskaffelser_no_2019_ehf_forward_billing_3_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_conformant_urn_fdc_anskaffelser_no_2019_ehf_forward_billing_3_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_CEN_EU_EN16931_2017_COMPLIANT_FDC_PEPPOL_EU_2017_POACC_BILLING_3_0_CONFORMANT_FDC_ANSKAFFELSER_NO_2019_EHF_FORWARD_BILLING_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_conformant_urn_fdc_anskaffelser_no_2019_ehf_forward_billing_3_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_INVOICE_UBL_V21 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_1__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_CREDIT_NOTE_UBL_V21 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_1__2_1;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_1__D16B}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_INVOICE_CII_V21 = EPredefinedDocumentTypeIdentifier.urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_1__D16B;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_1_conformant_urn_xoev_de_kosit_extension_xrechnung_2_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_EXTENSION_INVOICE_UBL_V21 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_1_conformant_urn_xoev_de_kosit_extension_xrechnung_2_1__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_1_conformant_urn_xoev_de_kosit_extension_xrechnung_2_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_EXTENSION_CREDIT_NOTE_UBL_V21 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_1_conformant_urn_xoev_de_kosit_extension_xrechnung_2_1__2_1;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_1_conformant_urn_xoev_de_kosit_extension_xrechnung_2_1__D16B}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_EXTENSION_INVOICE_CII_V21 = EPredefinedDocumentTypeIdentifier.urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_1_conformant_urn_xoev_de_kosit_extension_xrechnung_2_1__D16B;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_extended_urn_fdc_www_efaktura_gov_pl_ver2_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_CEN_EU_EN16931_2017_COMPLIANT_FDC_PEPPOL_EU_2017_POACC_BILLING_3_0_EXTENDED_FDC_WWW_EFAKTURA_GOV_PL_VER2_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_extended_urn_fdc_www_efaktura_gov_pl_ver2_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_extended_urn_fdc_www_efaktura_gov_pl_ver2_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_CEN_EU_EN16931_2017_COMPLIANT_FDC_PEPPOL_EU_2017_POACC_BILLING_3_0_EXTENDED_FDC_WWW_EFAKTURA_GOV_PL_VER2_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_extended_urn_fdc_www_efaktura_gov_pl_ver2_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_SelfBilledCreditNote_2__SelfBilledCreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_extended_urn_fdc_www_efaktura_gov_pl_ver2_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier SELFBILLEDCREDITNOTE_CEN_EU_EN16931_2017_COMPLIANT_FDC_PEPPOL_EU_2017_POACC_BILLING_3_0_EXTENDED_FDC_WWW_EFAKTURA_GOV_PL_VER2_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_SelfBilledCreditNote_2__SelfBilledCreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_extended_urn_fdc_www_efaktura_gov_pl_ver2_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestRequest_2__ExpressionOfInterestRequest__urn_fdc_peppol_eu_prac_trns_t001_1_1__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier EXPRESSIONOFINTERESTREQUEST_FDC_PEPPOL_EU_PRAC_TRNS_T001_1_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestRequest_2__ExpressionOfInterestRequest__urn_fdc_peppol_eu_prac_trns_t001_1_1__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestResponse_2__ExpressionOfInterestResponse__urn_fdc_peppol_eu_prac_trns_t002_1_1__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier EXPRESSIONOFINTERESTRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T002_1_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestResponse_2__ExpressionOfInterestResponse__urn_fdc_peppol_eu_prac_trns_t002_1_1__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_TenderStatusRequest_2__TenderStatusRequest__urn_fdc_peppol_eu_prac_trns_t003_1_1__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier TENDERSTATUSREQUEST_FDC_PEPPOL_EU_PRAC_TRNS_T003_1_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_TenderStatusRequest_2__TenderStatusRequest__urn_fdc_peppol_eu_prac_trns_t003_1_1__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CallForTenders_2__CallForTenders__urn_fdc_peppol_eu_prac_trns_t004_1_1__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier CALLFORTENDERS_FDC_PEPPOL_EU_PRAC_TRNS_T004_1_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CallForTenders_2__CallForTenders__urn_fdc_peppol_eu_prac_trns_t004_1_1__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Tender_2__Tender__urn_fdc_peppol_eu_prac_trns_t005_1_1__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier TENDER_FDC_PEPPOL_EU_PRAC_TRNS_T005_1_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Tender_2__Tender__urn_fdc_peppol_eu_prac_trns_t005_1_1__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_TenderReceipt_2__TenderReceipt__urn_fdc_peppol_eu_prac_trns_t006_1_1__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier TENDERRECEIPT_FDC_PEPPOL_EU_PRAC_TRNS_T006_1_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_TenderReceipt_2__TenderReceipt__urn_fdc_peppol_eu_prac_trns_t006_1_1__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Enquiry_2__Enquiry__urn_fdc_peppol_eu_prac_trns_t007_1_1__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier ENQUIRY_FDC_PEPPOL_EU_PRAC_TRNS_T007_1_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Enquiry_2__Enquiry__urn_fdc_peppol_eu_prac_trns_t007_1_1__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_EnquiryResponse_2__EnquiryResponse__urn_fdc_peppol_eu_prac_trns_t008_1_1__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier ENQUIRYRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T008_1_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_EnquiryResponse_2__EnquiryResponse__urn_fdc_peppol_eu_prac_trns_t008_1_1__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Enquiry_2__Enquiry__urn_fdc_peppol_eu_prac_trns_t009_1_1__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier ENQUIRY_FDC_PEPPOL_EU_PRAC_TRNS_T009_1_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Enquiry_2__Enquiry__urn_fdc_peppol_eu_prac_trns_t009_1_1__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_EnquiryResponse_2__EnquiryResponse__urn_fdc_peppol_eu_prac_trns_t010_1_1__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier ENQUIRYRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T010_1_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_EnquiryResponse_2__EnquiryResponse__urn_fdc_peppol_eu_prac_trns_t010_1_1__2_2;
    /**
     * Same as {@link #urn_oasis_names_tc_ebxml_regrep_xsd_query_4_0__QueryRequest__urn_fdc_peppol_eu_prac_trns_t011_1_1__4_0}
     */
    public static final EPredefinedDocumentTypeIdentifier QUERYREQUEST_FDC_PEPPOL_EU_PRAC_TRNS_T011_1_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_tc_ebxml_regrep_xsd_query_4_0__QueryRequest__urn_fdc_peppol_eu_prac_trns_t011_1_1__4_0;
    /**
     * Same as {@link #urn_oasis_names_tc_ebxml_regrep_xsd_query_4_0__QueryResponse__urn_fdc_peppol_eu_prac_trns_t012_1_1__4_0}
     */
    public static final EPredefinedDocumentTypeIdentifier QUERYRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T012_1_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_tc_ebxml_regrep_xsd_query_4_0__QueryResponse__urn_fdc_peppol_eu_prac_trns_t012_1_1__4_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_TenderWithdrawal_2__TenderWithdrawal__urn_fdc_peppol_eu_prac_trns_t013_1_1__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier TENDERWITHDRAWAL_FDC_PEPPOL_EU_PRAC_TRNS_T013_1_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_TenderWithdrawal_2__TenderWithdrawal__urn_fdc_peppol_eu_prac_trns_t013_1_1__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_TenderReceipt_2__TenderReceipt__urn_fdc_peppol_eu_prac_trns_t014_1_1__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier TENDERRECEIPT_FDC_PEPPOL_EU_PRAC_TRNS_T014_1_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_TenderReceipt_2__TenderReceipt__urn_fdc_peppol_eu_prac_trns_t014_1_1__2_2;
    /**
     * Same as {@link #urn_oasis_names_tc_ebxml_regrep_xsd_lcm_4_0__SubmitObjectsRequest__urn_fdc_peppol_eu_prac_trns_t015_1_1__4_0}
     */
    public static final EPredefinedDocumentTypeIdentifier SUBMITOBJECTSREQUEST_FDC_PEPPOL_EU_PRAC_TRNS_T015_1_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_tc_ebxml_regrep_xsd_lcm_4_0__SubmitObjectsRequest__urn_fdc_peppol_eu_prac_trns_t015_1_1__4_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_prac_trns_t016_1_1__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier APPLICATIONRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T016_1_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_prac_trns_t016_1_1__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_AwardedNotification_2__AwardedNotification__urn_fdc_peppol_eu_prac_trns_t017_1_4__2_2}
     * 
     * @deprecated since v8.5 - this item should not be used to issue new identifiers!
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier AWARDEDNOTIFICATION_FDC_PEPPOL_EU_PRAC_TRNS_T017_1_4 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_AwardedNotification_2__AwardedNotification__urn_fdc_peppol_eu_prac_trns_t017_1_4__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_AwardedNotification_2__AwardedNotification__urn_fdc_peppol_eu_prac_trns_t017_1_1__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier AWARDEDNOTIFICATION_FDC_PEPPOL_EU_PRAC_TRNS_T017_1_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_AwardedNotification_2__AwardedNotification__urn_fdc_peppol_eu_prac_trns_t017_1_1__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_2__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_INVOICE_UBL_V22 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_2__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_2__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_CREDIT_NOTE_UBL_V22 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_2__2_1;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_2__D16B}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_INVOICE_CII_V22 = EPredefinedDocumentTypeIdentifier.urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_2__D16B;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_2_conformant_urn_xoev_de_kosit_extension_xrechnung_2_2__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_EXTENSION_INVOICE_UBL_V22 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_2_conformant_urn_xoev_de_kosit_extension_xrechnung_2_2__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_2_conformant_urn_xoev_de_kosit_extension_xrechnung_2_2__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_EXTENSION_CREDIT_NOTE_UBL_V22 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_2_conformant_urn_xoev_de_kosit_extension_xrechnung_2_2__2_1;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_2_conformant_urn_xoev_de_kosit_extension_xrechnung_2_2__D16B}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_EXTENSION_INVOICE_CII_V22 = EPredefinedDocumentTypeIdentifier.urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_2_conformant_urn_xoev_de_kosit_extension_xrechnung_2_2__D16B;
    /**
     * Same as {@link #urn_fdc_peppol_end_user_reporting_1_0__EndUserReport__urn_fdc_peppol_eu_oo_trns_end_user_report_1__1_0}
     * 
     * @deprecated since v8.6 - this item should not be used to issue new identifiers!<br>Removed per 2025-02-01
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier ENDUSERREPORT_FDC_PEPPOL_EU_OO_TRNS_END_USER_REPORT_1 = EPredefinedDocumentTypeIdentifier.urn_fdc_peppol_end_user_reporting_1_0__EndUserReport__urn_fdc_peppol_eu_oo_trns_end_user_report_1__1_0;
    /**
     * Same as {@link #urn_fdc_peppol_transaction_statistics_reporting_1_0__TransactionStatisticsReport__urn_fdc_peppol_eu_oo_trns_transaction_statistics_reporting_1__1_0}
     * 
     * @deprecated since v8.6 - this item should not be used to issue new identifiers!<br>Removed per 2025-02-01
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier TRANSACTIONSTATISTICSREPORT_FDC_PEPPOL_EU_OO_TRNS_TRANSACTION_STATISTICS_REPORTING_1 = EPredefinedDocumentTypeIdentifier.urn_fdc_peppol_transaction_statistics_reporting_1_0__TransactionStatisticsReport__urn_fdc_peppol_eu_oo_trns_transaction_statistics_reporting_1__1_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_fdc_peppol_eu_logistics_trns_advanced_despatch_advice_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier DESPATCHADVICE_FDC_PEPPOL_EU_LOGISTICS_TRNS_ADVANCED_DESPATCH_ADVICE_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_fdc_peppol_eu_logistics_trns_advanced_despatch_advice_1__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_logistics_trns_despatch_advice_response_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier APPLICATIONRESPONSE_FDC_PEPPOL_EU_LOGISTICS_TRNS_DESPATCH_ADVICE_RESPONSE_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_logistics_trns_despatch_advice_response_1__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_WeightStatement_2__WeightStatement__urn_fdc_peppol_eu_logistics_trns_weight_statement_1__2_3}
     */
    public static final EPredefinedDocumentTypeIdentifier WEIGHTSTATEMENT_FDC_PEPPOL_EU_LOGISTICS_TRNS_WEIGHT_STATEMENT_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_WeightStatement_2__WeightStatement__urn_fdc_peppol_eu_logistics_trns_weight_statement_1__2_3;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_UtilityStatement_2__UtilityStatement__urn_fdc_www_efaktura_gov_pl_ver2_0_trns_us_ver1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier UTILITYSTATEMENT_FDC_WWW_EFAKTURA_GOV_PL_VER2_0_TRNS_US_VER1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_UtilityStatement_2__UtilityStatement__urn_fdc_www_efaktura_gov_pl_ver2_0_trns_us_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_nen_nl_nlcius_v1_0_compliant_urn_fdc_setu_nl_invoice_v2_2__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_CEN_EU_EN16931_2017_COMPLIANT_FDC_NEN_NL_NLCIUS_V1_0_COMPLIANT_FDC_SETU_NL_INVOICE_V2_2 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_nen_nl_nlcius_v1_0_compliant_urn_fdc_setu_nl_invoice_v2_2__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_fdc_peppol_jp_billing_3_0__2_1}
     * 
     * @deprecated since v8.9 - this item should not be used to issue new identifiers!<br>Removed per 2024-09-30
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier INVOICE_FDC_PEPPOL_JP_BILLING_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_fdc_peppol_jp_billing_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderChange_2__OrderChange__urn_fdc_peppol_eu_poacc_trns_order_change_3__2_3}
     */
    public static final EPredefinedDocumentTypeIdentifier ORDERCHANGE_FDC_PEPPOL_EU_POACC_TRNS_ORDER_CHANGE_3 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderChange_2__OrderChange__urn_fdc_peppol_eu_poacc_trns_order_change_3__2_3;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderCancellation_2__OrderCancellation__urn_fdc_peppol_eu_poacc_trns_order_cancellation_3__2_3}
     */
    public static final EPredefinedDocumentTypeIdentifier ORDERCANCELLATION_FDC_PEPPOL_EU_POACC_TRNS_ORDER_CANCELLATION_3 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderCancellation_2__OrderCancellation__urn_fdc_peppol_eu_poacc_trns_order_cancellation_3__2_3;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_response_advanced_3__2_3}
     */
    public static final EPredefinedDocumentTypeIdentifier ORDERRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_ORDER_RESPONSE_ADVANCED_3 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_response_advanced_3__2_3;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_INVOICE_UBL_V23 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_CREDIT_NOTE_UBL_V23 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_3__2_1;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_3__D16B}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_INVOICE_CII_V23 = EPredefinedDocumentTypeIdentifier.urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_3__D16B;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_3_conformant_urn_xoev_de_kosit_extension_xrechnung_2_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_EXTENSION_INVOICE_UBL_V23 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_3_conformant_urn_xoev_de_kosit_extension_xrechnung_2_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_3_conformant_urn_xoev_de_kosit_extension_xrechnung_2_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_EXTENSION_CREDIT_NOTE_UBL_V23 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_3_conformant_urn_xoev_de_kosit_extension_xrechnung_2_3__2_1;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_3_conformant_urn_xoev_de_kosit_extension_xrechnung_2_3__D16B}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_EXTENSION_INVOICE_CII_V23 = EPredefinedDocumentTypeIdentifier.urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_2_3_conformant_urn_xoev_de_kosit_extension_xrechnung_2_3__D16B;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_selfbilling_1_jp_1__2_1}
     * 
     * @deprecated since v8.9 - this item should not be used to issue new identifiers!<br>Removed per 2024-09-30
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier INVOICE_PEPPOL_PINT_SELFBILLING_1_JP_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_selfbilling_1_jp_1__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestRequest_2__ExpressionOfInterestRequest__urn_fdc_peppol_eu_prac_trns_t001_1_2__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier EXPRESSIONOFINTERESTREQUEST_FDC_PEPPOL_EU_PRAC_TRNS_T001_1_2 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestRequest_2__ExpressionOfInterestRequest__urn_fdc_peppol_eu_prac_trns_t001_1_2__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestResponse_2__ExpressionOfInterestResponse__urn_fdc_peppol_eu_prac_trns_t002_1_2__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier EXPRESSIONOFINTERESTRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T002_1_2 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestResponse_2__ExpressionOfInterestResponse__urn_fdc_peppol_eu_prac_trns_t002_1_2__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestRequest_2__ExpressionOfInterestRequest__urn_fdc_peppol_eu_prac_trns_t021_1_2__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier EXPRESSIONOFINTERESTREQUEST_FDC_PEPPOL_EU_PRAC_TRNS_T021_1_2 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestRequest_2__ExpressionOfInterestRequest__urn_fdc_peppol_eu_prac_trns_t021_1_2__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestResponse_2__ExpressionOfInterestResponse__urn_fdc_peppol_eu_prac_trns_t022_1_2__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier EXPRESSIONOFINTERESTRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T022_1_2 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestResponse_2__ExpressionOfInterestResponse__urn_fdc_peppol_eu_prac_trns_t022_1_2__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_TenderStatusRequest_2__TenderStatusRequest__urn_fdc_peppol_eu_prac_trns_t003_1_2__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier TENDERSTATUSREQUEST_FDC_PEPPOL_EU_PRAC_TRNS_T003_1_2 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_TenderStatusRequest_2__TenderStatusRequest__urn_fdc_peppol_eu_prac_trns_t003_1_2__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CallForTenders_2__CallForTenders__urn_fdc_peppol_eu_prac_trns_t004_1_2__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier CALLFORTENDERS_FDC_PEPPOL_EU_PRAC_TRNS_T004_1_2 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CallForTenders_2__CallForTenders__urn_fdc_peppol_eu_prac_trns_t004_1_2__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Tender_2__Tender__urn_fdc_peppol_eu_prac_trns_t005_1_2__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier TENDER_FDC_PEPPOL_EU_PRAC_TRNS_T005_1_2 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Tender_2__Tender__urn_fdc_peppol_eu_prac_trns_t005_1_2__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_TenderReceipt_2__TenderReceipt__urn_fdc_peppol_eu_prac_trns_t006_1_2__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier TENDERRECEIPT_FDC_PEPPOL_EU_PRAC_TRNS_T006_1_2 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_TenderReceipt_2__TenderReceipt__urn_fdc_peppol_eu_prac_trns_t006_1_2__2_2;
    /**
     * Same as {@link #urn_oasis_names_tc_ebxml_regrep_xsd_query_4_0__QueryRequest__urn_fdc_peppol_eu_prac_trns_t011_1_2__4_0}
     */
    public static final EPredefinedDocumentTypeIdentifier QUERYREQUEST_FDC_PEPPOL_EU_PRAC_TRNS_T011_1_2 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_tc_ebxml_regrep_xsd_query_4_0__QueryRequest__urn_fdc_peppol_eu_prac_trns_t011_1_2__4_0;
    /**
     * Same as {@link #urn_oasis_names_tc_ebxml_regrep_xsd_query_4_0__QueryResponse__urn_fdc_peppol_eu_prac_trns_t012_1_2__4_0}
     */
    public static final EPredefinedDocumentTypeIdentifier QUERYRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T012_1_2 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_tc_ebxml_regrep_xsd_query_4_0__QueryResponse__urn_fdc_peppol_eu_prac_trns_t012_1_2__4_0;
    /**
     * Same as {@link #urn_oasis_names_tc_ebxml_regrep_xsd_lcm_4_0__SubmitObjectsRequest__urn_fdc_peppol_eu_prac_trns_t015_1_2__4_0}
     */
    public static final EPredefinedDocumentTypeIdentifier SUBMITOBJECTSREQUEST_FDC_PEPPOL_EU_PRAC_TRNS_T015_1_2 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_tc_ebxml_regrep_xsd_lcm_4_0__SubmitObjectsRequest__urn_fdc_peppol_eu_prac_trns_t015_1_2__4_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_prac_trns_t016_1_2__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier APPLICATIONRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T016_1_2 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_prac_trns_t016_1_2__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_prac_trns_t018_1_1__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier APPLICATIONRESPONSE_FDC_PEPPOL_EU_PRAC_TRNS_T018_1_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_prac_trns_t018_1_1__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Qualification_2__Qualification__urn_fdc_peppol_eu_prac_trns_t019_1_1__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier QUALIFICATION_FDC_PEPPOL_EU_PRAC_TRNS_T019_1_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Qualification_2__Qualification__urn_fdc_peppol_eu_prac_trns_t019_1_1__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_TenderReceipt_2__TenderReceipt__urn_fdc_peppol_eu_prac_trns_t020_1_1__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifier TENDERRECEIPT_FDC_PEPPOL_EU_PRAC_TRNS_T020_1_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_TenderReceipt_2__TenderReceipt__urn_fdc_peppol_eu_prac_trns_t020_1_1__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_TransportExecutionPlanRequest_2__TransportExecutionPlanRequest__urn_fdc_peppol_eu_logistics_trns_transport_execution_plan_request_1__2_3}
     */
    public static final EPredefinedDocumentTypeIdentifier TRANSPORTEXECUTIONPLANREQUEST_FDC_PEPPOL_EU_LOGISTICS_TRNS_TRANSPORT_EXECUTION_PLAN_REQUEST_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_TransportExecutionPlanRequest_2__TransportExecutionPlanRequest__urn_fdc_peppol_eu_logistics_trns_transport_execution_plan_request_1__2_3;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_TransportExecutionPlan_2__TransportExecutionPlan__urn_fdc_peppol_eu_logistics_trns_transport_execution_plan_1__2_3}
     */
    public static final EPredefinedDocumentTypeIdentifier TRANSPORTEXECUTIONPLAN_FDC_PEPPOL_EU_LOGISTICS_TRNS_TRANSPORT_EXECUTION_PLAN_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_TransportExecutionPlan_2__TransportExecutionPlan__urn_fdc_peppol_eu_logistics_trns_transport_execution_plan_1__2_3;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Waybill_2__Waybill__urn_fdc_peppol_eu_logistics_trns_waybill_1__2_3}
     */
    public static final EPredefinedDocumentTypeIdentifier WAYBILL_FDC_PEPPOL_EU_LOGISTICS_TRNS_WAYBILL_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Waybill_2__Waybill__urn_fdc_peppol_eu_logistics_trns_waybill_1__2_3;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_TransportationStatusRequest_2__TransportationStatusRequest__urn_fdc_peppol_eu_logistics_trns_transportation_status_request_1__2_3}
     */
    public static final EPredefinedDocumentTypeIdentifier TRANSPORTATIONSTATUSREQUEST_FDC_PEPPOL_EU_LOGISTICS_TRNS_TRANSPORTATION_STATUS_REQUEST_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_TransportationStatusRequest_2__TransportationStatusRequest__urn_fdc_peppol_eu_logistics_trns_transportation_status_request_1__2_3;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_TransportationStatus_2__TransportationStatus__urn_fdc_peppol_eu_logistics_trns_transportation_status_1__2_3}
     */
    public static final EPredefinedDocumentTypeIdentifier TRANSPORTATIONSTATUS_FDC_PEPPOL_EU_LOGISTICS_TRNS_TRANSPORTATION_STATUS_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_TransportationStatus_2__TransportationStatus__urn_fdc_peppol_eu_logistics_trns_transportation_status_1__2_3;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ReceiptAdvice_2__ReceiptAdvice__urn_fdc_peppol_eu_logistics_trns_receipt_advice_1__2_3}
     */
    public static final EPredefinedDocumentTypeIdentifier RECEIPTADVICE_FDC_PEPPOL_EU_LOGISTICS_TRNS_RECEIPT_ADVICE_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ReceiptAdvice_2__ReceiptAdvice__urn_fdc_peppol_eu_logistics_trns_receipt_advice_1__2_3;
    /**
     * Same as {@link #http___ns_hr_xml_org_2007_04_15__TimeCard__hr_xml_nl_1_4__2_5}
     */
    public static final EPredefinedDocumentTypeIdentifier TIMECARD_HR_XML_NL_1_4 = EPredefinedDocumentTypeIdentifier.http___ns_hr_xml_org_2007_04_15__TimeCard__hr_xml_nl_1_4__2_5;
    /**
     * Same as {@link #urn_fdc_peppol_end_user_statistics_report_1_1__EndUserStatisticsReport__urn_fdc_peppol_eu_edec_trns_end_user_statistics_report_1_1__1_1}
     */
    public static final EPredefinedDocumentTypeIdentifier ENDUSERSTATISTICSREPORT_FDC_PEPPOL_EU_EDEC_TRNS_END_USER_STATISTICS_REPORT_1_1 = EPredefinedDocumentTypeIdentifier.urn_fdc_peppol_end_user_statistics_report_1_1__EndUserStatisticsReport__urn_fdc_peppol_eu_edec_trns_end_user_statistics_report_1_1__1_1;
    /**
     * Same as {@link #urn_fdc_peppol_transaction_statistics_report_1_0__TransactionStatisticsReport__urn_fdc_peppol_eu_edec_trns_transaction_statistics_reporting_1_0__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier TRANSACTIONSTATISTICSREPORT_FDC_PEPPOL_EU_EDEC_TRNS_TRANSACTION_STATISTICS_REPORTING_1_0 = EPredefinedDocumentTypeIdentifier.urn_fdc_peppol_transaction_statistics_report_1_0__TransactionStatisticsReport__urn_fdc_peppol_eu_edec_trns_transaction_statistics_reporting_1_0__1_0;
    /**
     * Same as {@link #WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_PEPPOL_PINT_BILLING_1 = EPredefinedDocumentTypeIdentifier.WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1__2_1;
    /**
     * Same as {@link #WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_billing_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_PEPPOL_PINT_BILLING_1 = EPredefinedDocumentTypeIdentifier.WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_billing_1__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_jp_1__2_1}
     * 
     * @deprecated since v8.9 - this item should not be used to issue new identifiers!<br>Removed per 2024-09-30
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier INVOICE_PEPPOL_PINT_BILLING_1_JP_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_jp_1__2_1;
    /**
     * Same as {@link #WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_jp_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_PEPPOL_PINT_BILLING_1_JP_12 = EPredefinedDocumentTypeIdentifier.WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_jp_1__2_1;
    /**
     * Same as {@link #WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_selfbilling_1_jp_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_PEPPOL_PINT_SELFBILLING_1_JP_12 = EPredefinedDocumentTypeIdentifier.WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_selfbilling_1_jp_1__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_nontaxinvoice_1_jp_1__2_1}
     * 
     * @deprecated since v8.9 - this item should not be used to issue new identifiers!<br>Removed per 2024-09-30
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier INVOICE_PEPPOL_PINT_NONTAXINVOICE_1_JP_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_nontaxinvoice_1_jp_1__2_1;
    /**
     * Same as {@link #WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_nontaxinvoice_1_jp_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_PEPPOL_PINT_NONTAXINVOICE_1_JP_12 = EPredefinedDocumentTypeIdentifier.WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_nontaxinvoice_1_jp_1__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_aunz_1__2_1}
     * 
     * @deprecated since v9.1 - this item should not be used to issue new identifiers!<br>Removed per 2025-03-01
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier INVOICE_PEPPOL_PINT_BILLING_1_AUNZ_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_aunz_1__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_billing_1_aunz_1__2_1}
     * 
     * @deprecated since v9.1 - this item should not be used to issue new identifiers!<br>Removed per 2025-03-01
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_PEPPOL_PINT_BILLING_1_AUNZ_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_billing_1_aunz_1__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_selfbilling_1_aunz_1__2_1}
     * 
     * @deprecated since v9.1 - this item should not be used to issue new identifiers!<br>Removed per 2025-03-01
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier INVOICE_PEPPOL_PINT_SELFBILLING_1_AUNZ_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_selfbilling_1_aunz_1__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_selfbilling_1_aunz_1__2_1}
     * 
     * @deprecated since v9.1 - this item should not be used to issue new identifiers!<br>Removed per 2025-03-01
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_PEPPOL_PINT_SELFBILLING_1_AUNZ_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_selfbilling_1_aunz_1__2_1;
    /**
     * Same as {@link #WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_aunz_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_PEPPOL_PINT_BILLING_1_AUNZ_12 = EPredefinedDocumentTypeIdentifier.WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_aunz_1__2_1;
    /**
     * Same as {@link #WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_billing_1_aunz_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_PEPPOL_PINT_BILLING_1_AUNZ_12 = EPredefinedDocumentTypeIdentifier.WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_billing_1_aunz_1__2_1;
    /**
     * Same as {@link #WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_selfbilling_1_aunz_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_PEPPOL_PINT_SELFBILLING_1_AUNZ_12 = EPredefinedDocumentTypeIdentifier.WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_selfbilling_1_aunz_1__2_1;
    /**
     * Same as {@link #WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_selfbilling_1_aunz_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_PEPPOL_PINT_SELFBILLING_1_AUNZ_12 = EPredefinedDocumentTypeIdentifier.WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_selfbilling_1_aunz_1__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xeinkauf_de_kosit_xrechnung_3_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_INVOICE_UBL_V30 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xeinkauf_de_kosit_xrechnung_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xeinkauf_de_kosit_xrechnung_3_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_CREDIT_NOTE_UBL_V30 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xeinkauf_de_kosit_xrechnung_3_0__2_1;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xeinkauf_de_kosit_xrechnung_3_0__D16B}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_INVOICE_CII_V30 = EPredefinedDocumentTypeIdentifier.urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xeinkauf_de_kosit_xrechnung_3_0__D16B;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xeinkauf_de_kosit_xrechnung_3_0_conformant_urn_xeinkauf_de_kosit_extension_xrechnung_3_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_EXTENSION_INVOICE_UBL_V30 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xeinkauf_de_kosit_xrechnung_3_0_conformant_urn_xeinkauf_de_kosit_extension_xrechnung_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xeinkauf_de_kosit_xrechnung_3_0_conformant_urn_xeinkauf_de_kosit_extension_xrechnung_3_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_EXTENSION_CREDIT_NOTE_UBL_V30 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xeinkauf_de_kosit_xrechnung_3_0_conformant_urn_xeinkauf_de_kosit_extension_xrechnung_3_0__2_1;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xeinkauf_de_kosit_xrechnung_3_0_conformant_urn_xeinkauf_de_kosit_extension_xrechnung_3_0__D16B}
     */
    public static final EPredefinedDocumentTypeIdentifier XRECHNUNG_EXTENSION_INVOICE_CII_V30 = EPredefinedDocumentTypeIdentifier.urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xeinkauf_de_kosit_xrechnung_3_0_conformant_urn_xeinkauf_de_kosit_extension_xrechnung_3_0__D16B;
    /**
     * Same as {@link #WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_sg_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_PEPPOL_PINT_BILLING_1_SG_1 = EPredefinedDocumentTypeIdentifier.WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_sg_1__2_1;
    /**
     * Same as {@link #WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_billing_1_sg_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_PEPPOL_PINT_BILLING_1_SG_1 = EPredefinedDocumentTypeIdentifier.WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_billing_1_sg_1__2_1;
    /**
     * Same as {@link #WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_my_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_PEPPOL_PINT_BILLING_1_MY_1 = EPredefinedDocumentTypeIdentifier.WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_my_1__2_1;
    /**
     * Same as {@link #WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_billing_1_my_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_PEPPOL_PINT_BILLING_1_MY_1 = EPredefinedDocumentTypeIdentifier.WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_billing_1_my_1__2_1;
    /**
     * Same as {@link #WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_selfbilling_1_my_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_PEPPOL_PINT_SELFBILLING_1_MY_1 = EPredefinedDocumentTypeIdentifier.WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_selfbilling_1_my_1__2_1;
    /**
     * Same as {@link #WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_selfbilling_1_my_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_PEPPOL_PINT_SELFBILLING_1_MY_1 = EPredefinedDocumentTypeIdentifier.WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_selfbilling_1_my_1__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_my_1__2_1}
     * 
     * @deprecated since v2025-05-15 - this item should not be used to issue new identifiers!<br>Removed per 2025-09-09
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier INVOICE_PEPPOL_PINT_BILLING_1_MY_12 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_my_1__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_billing_1_my_1__2_1}
     * 
     * @deprecated since v2025-05-15 - this item should not be used to issue new identifiers!<br>Removed per 2025-09-09
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_PEPPOL_PINT_BILLING_1_MY_12 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_billing_1_my_1__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_selfbilling_1_my_1__2_1}
     * 
     * @deprecated since v2025-05-15 - this item should not be used to issue new identifiers!<br>Removed per 2025-09-09
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier INVOICE_PEPPOL_PINT_SELFBILLING_1_MY_12 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_selfbilling_1_my_1__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_selfbilling_1_my_1__2_1}
     * 
     * @deprecated since v2025-05-15 - this item should not be used to issue new identifiers!<br>Removed per 2025-09-09
     */
    @Deprecated(forRemoval = false)
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_PEPPOL_PINT_SELFBILLING_1_MY_12 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_selfbilling_1_my_1__2_1;
    /**
     * Same as {@link #http___ns_hr_xml_org_2007_04_15__StaffingOrder__hr_xml_nl_1_4__2_5}
     */
    public static final EPredefinedDocumentTypeIdentifier STAFFINGORDER_HR_XML_NL_1_4 = EPredefinedDocumentTypeIdentifier.http___ns_hr_xml_org_2007_04_15__StaffingOrder__hr_xml_nl_1_4__2_5;
    /**
     * Same as {@link #http___ns_hr_xml_org_2007_04_15__StaffingOrder__hr_xml_status_nl_1_4__2_5}
     */
    public static final EPredefinedDocumentTypeIdentifier STAFFINGORDER_HR_XML_STATUS_NL_1_4 = EPredefinedDocumentTypeIdentifier.http___ns_hr_xml_org_2007_04_15__StaffingOrder__hr_xml_status_nl_1_4__2_5;
    /**
     * Same as {@link #http___ns_hr_xml_org_2007_04_15__HumanResource__hr_xml_nl_1_4__2_5}
     */
    public static final EPredefinedDocumentTypeIdentifier HUMANRESOURCE_HR_XML_NL_1_4 = EPredefinedDocumentTypeIdentifier.http___ns_hr_xml_org_2007_04_15__HumanResource__hr_xml_nl_1_4__2_5;
    /**
     * Same as {@link #http___ns_hr_xml_org_2007_04_15__HumanResource__hr_xml_status_nl_1_4__2_5}
     */
    public static final EPredefinedDocumentTypeIdentifier HUMANRESOURCE_HR_XML_STATUS_NL_1_4 = EPredefinedDocumentTypeIdentifier.http___ns_hr_xml_org_2007_04_15__HumanResource__hr_xml_status_nl_1_4__2_5;
    /**
     * Same as {@link #http___ns_hr_xml_org_2007_04_15__Assignment__hr_xml_nl_1_4_1__2_5}
     */
    public static final EPredefinedDocumentTypeIdentifier ASSIGNMENT_HR_XML_NL_1_4_1 = EPredefinedDocumentTypeIdentifier.http___ns_hr_xml_org_2007_04_15__Assignment__hr_xml_nl_1_4_1__2_5;
    /**
     * Same as {@link #http___ns_hr_xml_org_2007_04_15__Assignment__hr_xml_status_nl_1_4__2_5}
     */
    public static final EPredefinedDocumentTypeIdentifier ASSIGNMENT_HR_XML_STATUS_NL_1_4 = EPredefinedDocumentTypeIdentifier.http___ns_hr_xml_org_2007_04_15__Assignment__hr_xml_status_nl_1_4__2_5;
    /**
     * Same as {@link #urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_digital__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier INNBYGGERPOST_FDC_DIGDIR_NO_2020_INNBYGGERPOST_SCHEMA_DIGITAL = EPredefinedDocumentTypeIdentifier.urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_digital__1_0;
    /**
     * Same as {@link #urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_utskrift__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier INNBYGGERPOST_FDC_DIGDIR_NO_2020_INNBYGGERPOST_SCHEMA_UTSKRIFT = EPredefinedDocumentTypeIdentifier.urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_utskrift__1_0;
    /**
     * Same as {@link #urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_flyttet__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier INNBYGGERPOST_FDC_DIGDIR_NO_2020_INNBYGGERPOST_SCHEMA_FLYTTET = EPredefinedDocumentTypeIdentifier.urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_flyttet__1_0;
    /**
     * Same as {@link #urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_leveringskvittering__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier INNBYGGERPOST_FDC_DIGDIR_NO_2020_INNBYGGERPOST_SCHEMA_LEVERINGSKVITTERING = EPredefinedDocumentTypeIdentifier.urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_leveringskvittering__1_0;
    /**
     * Same as {@link #urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_feil__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier INNBYGGERPOST_FDC_DIGDIR_NO_2020_INNBYGGERPOST_SCHEMA_FEIL = EPredefinedDocumentTypeIdentifier.urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_feil__1_0;
    /**
     * Same as {@link #urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_aapningskvittering__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier INNBYGGERPOST_FDC_DIGDIR_NO_2020_INNBYGGERPOST_SCHEMA_AAPNINGSKVITTERING = EPredefinedDocumentTypeIdentifier.urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_aapningskvittering__1_0;
    /**
     * Same as {@link #urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_mottakskvittering__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier INNBYGGERPOST_FDC_DIGDIR_NO_2020_INNBYGGERPOST_SCHEMA_MOTTAKSKVITTERING = EPredefinedDocumentTypeIdentifier.urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_mottakskvittering__1_0;
    /**
     * Same as {@link #urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_varslingfeiletkvittering__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier INNBYGGERPOST_FDC_DIGDIR_NO_2020_INNBYGGERPOST_SCHEMA_VARSLINGFEILETKVITTERING = EPredefinedDocumentTypeIdentifier.urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_varslingfeiletkvittering__1_0;
    /**
     * Same as {@link #urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_returpostkvittering__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier INNBYGGERPOST_FDC_DIGDIR_NO_2020_INNBYGGERPOST_SCHEMA_RETURPOSTKVITTERING = EPredefinedDocumentTypeIdentifier.urn_fdc_digdir_no_2020_innbyggerpost_xsd__innbyggerpost__urn_fdc_digdir_no_2020_innbyggerpost_schema_returpostkvittering__1_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_selfbilling_3_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_CEN_EU_EN16931_2017_COMPLIANT_FDC_PEPPOL_EU_2017_POACC_SELFBILLING_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_selfbilling_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_selfbilling_3_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_CEN_EU_EN16931_2017_COMPLIANT_FDC_PEPPOL_EU_2017_POACC_SELFBILLING_3_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_selfbilling_3_0__2_1;
    /**
     * Same as {@link #http___www_ketenstandaard_nl_onderhoudsopdracht_SALES_005__MaintenanceInstruction__dico_maintenanceinstruction_nl_1_0__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier MAINTENANCEINSTRUCTION_DICO_MAINTENANCEINSTRUCTION_NL_1_0 = EPredefinedDocumentTypeIdentifier.http___www_ketenstandaard_nl_onderhoudsopdracht_SALES_005__MaintenanceInstruction__dico_maintenanceinstruction_nl_1_0__1_0;
    /**
     * Same as {@link #urn_peppol_doctype_pdf_xml__urn_cen_eu_en16931_2017_conformant_urn_peppol_france_billing_Factur_X_1_0__D22B}
     */
    public static final EPredefinedDocumentTypeIdentifier _CEN_EU_EN16931_2017_CONFORMANT_PEPPOL_FRANCE_BILLING_FACTUR_X_1_0 = EPredefinedDocumentTypeIdentifier.urn_peppol_doctype_pdf_xml__urn_cen_eu_en16931_2017_conformant_urn_peppol_france_billing_Factur_X_1_0__D22B;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_peppol_france_billing_cius_1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_CEN_EU_EN16931_2017_COMPLIANT_PEPPOL_FRANCE_BILLING_CIUS_1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_peppol_france_billing_cius_1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_peppol_france_billing_extended_1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_CEN_EU_EN16931_2017_CONFORMANT_PEPPOL_FRANCE_BILLING_EXTENDED_1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_peppol_france_billing_extended_1_0__2_1;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_peppol_france_billing_cius_1_0__D22B}
     */
    public static final EPredefinedDocumentTypeIdentifier CROSSINDUSTRYINVOICE_CEN_EU_EN16931_2017_COMPLIANT_PEPPOL_FRANCE_BILLING_CIUS_1_0 = EPredefinedDocumentTypeIdentifier.urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_peppol_france_billing_cius_1_0__D22B;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_conformant_urn_peppol_france_billing_extended_1_0__D22B}
     */
    public static final EPredefinedDocumentTypeIdentifier CROSSINDUSTRYINVOICE_CEN_EU_EN16931_2017_CONFORMANT_PEPPOL_FRANCE_BILLING_EXTENDED_1_0 = EPredefinedDocumentTypeIdentifier.urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_conformant_urn_peppol_france_billing_extended_1_0__D22B;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossDomainAcknowledgementAndResponse_100__CrossDomainAcknowledgementAndResponse__urn_peppol_france_billing_cdv_1_0__D22B}
     */
    public static final EPredefinedDocumentTypeIdentifier CROSSDOMAINACKNOWLEDGEMENTANDRESPONSE_PEPPOL_FRANCE_BILLING_CDV_1_0 = EPredefinedDocumentTypeIdentifier.urn_un_unece_uncefact_data_standard_CrossDomainAcknowledgementAndResponse_100__CrossDomainAcknowledgementAndResponse__urn_peppol_france_billing_cdv_1_0__D22B;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_peppol_france_billing_cius_1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_CEN_EU_EN16931_2017_COMPLIANT_PEPPOL_FRANCE_BILLING_CIUS_1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_peppol_france_billing_cius_1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_peppol_france_billing_extended_1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_CEN_EU_EN16931_2017_CONFORMANT_PEPPOL_FRANCE_BILLING_EXTENDED_1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_peppol_france_billing_extended_1_0__2_1;
    /**
     * Same as {@link #WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_ae_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_PEPPOL_PINT_BILLING_1_AE_1 = EPredefinedDocumentTypeIdentifier.WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_billing_1_ae_1__2_1;
    /**
     * Same as {@link #WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_billing_1_ae_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_PEPPOL_PINT_BILLING_1_AE_1 = EPredefinedDocumentTypeIdentifier.WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_billing_1_ae_1__2_1;
    /**
     * Same as {@link #WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_selfbilling_1_ae_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_PEPPOL_PINT_SELFBILLING_1_AE_1 = EPredefinedDocumentTypeIdentifier.WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_peppol_pint_selfbilling_1_ae_1__2_1;
    /**
     * Same as {@link #WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_selfbilling_1_ae_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_PEPPOL_PINT_SELFBILLING_1_AE_1 = EPredefinedDocumentTypeIdentifier.WILDCARD_urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_peppol_pint_selfbilling_1_ae_1__2_1;
    /**
     * Same as {@link #WILDCARD_urn_fdc_peppol_tax_data_document_1_0__TaxData__urn_peppol_pint_taxdata_1_ae_1__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier TAXDATA_PEPPOL_PINT_TAXDATA_1_AE_1 = EPredefinedDocumentTypeIdentifier.WILDCARD_urn_fdc_peppol_tax_data_document_1_0__TaxData__urn_peppol_pint_taxdata_1_ae_1__1_0;
    /**
     * Same as {@link #WILDCARD_urn_fdc_peppol_tax_data_status_1_0__TaxDataStatus__urn_peppol_pint_taxdatastatus_1_ae_1__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier TAXDATASTATUS_PEPPOL_PINT_TAXDATASTATUS_1_AE_1 = EPredefinedDocumentTypeIdentifier.WILDCARD_urn_fdc_peppol_tax_data_status_1_0__TaxDataStatus__urn_peppol_pint_taxdatastatus_1_ae_1__1_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_peppol_eb2b_1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier INVOICE_CEN_EU_EN16931_2017_CONFORMANT_PEPPOL_EB2B_1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_peppol_eb2b_1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_peppol_eb2b_1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier CREDITNOTE_CEN_EU_EN16931_2017_CONFORMANT_PEPPOL_EB2B_1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_peppol_eb2b_1_0__2_1;
    /**
     * Same as {@link #urn_peppol_doctype_edifact_ORDERS__eb2b__0}
     */
    public static final EPredefinedDocumentTypeIdentifier _EB2B = EPredefinedDocumentTypeIdentifier.urn_peppol_doctype_edifact_ORDERS__eb2b__0;
    /**
     * Same as {@link #urn_peppol_doctype_edifact_ORDRSP__eb2b__0}
     */
    public static final EPredefinedDocumentTypeIdentifier _EB2B2 = EPredefinedDocumentTypeIdentifier.urn_peppol_doctype_edifact_ORDRSP__eb2b__0;
    /**
     * Same as {@link #urn_peppol_doctype_edifact_DESADV__eb2b__0}
     */
    public static final EPredefinedDocumentTypeIdentifier _EB2B3 = EPredefinedDocumentTypeIdentifier.urn_peppol_doctype_edifact_DESADV__eb2b__0;
    /**
     * Same as {@link #urn_peppol_doctype_edifact_INVOIC__eb2b__0}
     */
    public static final EPredefinedDocumentTypeIdentifier _EB2B4 = EPredefinedDocumentTypeIdentifier.urn_peppol_doctype_edifact_INVOIC__eb2b__0;
    /**
     * Same as {@link #urn_peppol_doctype_x12_850__eb2b__0}
     */
    public static final EPredefinedDocumentTypeIdentifier _EB2B5 = EPredefinedDocumentTypeIdentifier.urn_peppol_doctype_x12_850__eb2b__0;
    /**
     * Same as {@link #urn_peppol_doctype_x12_855__eb2b__0}
     */
    public static final EPredefinedDocumentTypeIdentifier _EB2B6 = EPredefinedDocumentTypeIdentifier.urn_peppol_doctype_x12_855__eb2b__0;
    /**
     * Same as {@link #urn_peppol_doctype_x12_856__eb2b__0}
     */
    public static final EPredefinedDocumentTypeIdentifier _EB2B7 = EPredefinedDocumentTypeIdentifier.urn_peppol_doctype_x12_856__eb2b__0;
    /**
     * Same as {@link #urn_peppol_doctype_x12_810__eb2b__0}
     */
    public static final EPredefinedDocumentTypeIdentifier _EB2B8 = EPredefinedDocumentTypeIdentifier.urn_peppol_doctype_x12_810__eb2b__0;
    /**
     * Same as {@link #urn_peppol_doctype_pdf_xml__eb2b_order_x_1_0__0}
     */
    public static final EPredefinedDocumentTypeIdentifier _EB2B_ORDER_X_1_0 = EPredefinedDocumentTypeIdentifier.urn_peppol_doctype_pdf_xml__eb2b_order_x_1_0__0;
    /**
     * Same as {@link #urn_peppol_doctype_pdf_xml__eb2b_factur_x_1_0__0}
     */
    public static final EPredefinedDocumentTypeIdentifier _EB2B_FACTUR_X_1_0 = EPredefinedDocumentTypeIdentifier.urn_peppol_doctype_pdf_xml__eb2b_factur_x_1_0__0;
    /**
     * Same as {@link #urn_peppol_doctype_bilateral__eb2b_order__0}
     */
    public static final EPredefinedDocumentTypeIdentifier _EB2B_ORDER = EPredefinedDocumentTypeIdentifier.urn_peppol_doctype_bilateral__eb2b_order__0;
    /**
     * Same as {@link #urn_peppol_doctype_bilateral__eb2b_order_response__0}
     */
    public static final EPredefinedDocumentTypeIdentifier _EB2B_ORDER_RESPONSE = EPredefinedDocumentTypeIdentifier.urn_peppol_doctype_bilateral__eb2b_order_response__0;
    /**
     * Same as {@link #urn_peppol_doctype_bilateral__eb2b_despatch_advice__0}
     */
    public static final EPredefinedDocumentTypeIdentifier _EB2B_DESPATCH_ADVICE = EPredefinedDocumentTypeIdentifier.urn_peppol_doctype_bilateral__eb2b_despatch_advice__0;
    /**
     * Same as {@link #urn_peppol_doctype_bilateral__eb2b_invoice__0}
     */
    public static final EPredefinedDocumentTypeIdentifier _EB2B_INVOICE = EPredefinedDocumentTypeIdentifier.urn_peppol_doctype_bilateral__eb2b_invoice__0;
    /**
     * Same as {@link #urn_peppol_doctype_bilateral__eb2b_bilateral__0}
     */
    public static final EPredefinedDocumentTypeIdentifier _EB2B_BILATERAL = EPredefinedDocumentTypeIdentifier.urn_peppol_doctype_bilateral__eb2b_bilateral__0;
    /**
     * Same as {@link #http___www_ketenstandaard_nl_onderhoudsstatus_SALES_005__MaintenanceStatus__dico_maintenancestatus_nl_1_0__1_0}
     */
    public static final EPredefinedDocumentTypeIdentifier MAINTENANCESTATUS_DICO_MAINTENANCESTATUS_NL_1_0 = EPredefinedDocumentTypeIdentifier.http___www_ketenstandaard_nl_onderhoudsstatus_SALES_005__MaintenanceStatus__dico_maintenancestatus_nl_1_0__1_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_peppol_edec_mls_1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier PEPPOL_MLS_1_0 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_peppol_edec_mls_1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_fdc_imda_gov_sg_trns_order_balance_1__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifier ORDER_FDC_IMDA_GOV_SG_TRNS_ORDER_BALANCE_1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_fdc_imda_gov_sg_trns_order_balance_1__2_1;
    private final String m_sScheme;
    private final IPeppolGenericDocumentTypeIdentifierParts m_aParts;
    private final String m_sCommonName;
    private final String m_sID;
    private final Version m_aInitialRelease;
    private final EPeppolCodeListItemState m_eState;
    private final Version m_aDeprecationRelease;
    private final LocalDate m_aRemovalDate;
    private final boolean m_bAbstract;
    private final boolean m_bIssuedByOpenPeppol;
    private final int m_nBISVersion;
    private final String m_sDomainCommunity;
    private final ICommonsList<IProcessIdentifier> m_aProcessIDs;

    EPredefinedDocumentTypeIdentifier(@Nonnull @Nonempty final String sScheme,
        @Nonnull final IPeppolGenericDocumentTypeIdentifierParts aParts,
        @Nonnull @Nonempty final String sCommonName,
        @Nonnull final Version aInitialRelease,
        @Nonnull final EPeppolCodeListItemState eState,
        @Nullable final Version aDeprecationRelease,
        @Nullable final LocalDate aRemovalDate,
        final boolean bAbstract,
        final boolean bIssuedByOpenPeppol,
        final int nBISVersion,
        @Nullable final String sDomainCommunity,
        final ICommonsList<String> aProcessIDs) {
        m_sScheme = sScheme;
        m_aParts = aParts;
        m_sCommonName = sCommonName;
        m_sID = m_aParts.getAsDocumentTypeIdentifierValue();
        m_aInitialRelease = aInitialRelease;
        m_eState = eState;
        m_aDeprecationRelease = aDeprecationRelease;
        m_aRemovalDate = aRemovalDate;
        m_bAbstract = bAbstract;
        m_bIssuedByOpenPeppol = bIssuedByOpenPeppol;
        m_nBISVersion = nBISVersion;
        m_sDomainCommunity = sDomainCommunity;
        m_aProcessIDs = new CommonsArrayList<>(aProcessIDs, PeppolIdentifierFactory.INSTANCE::parseProcessIdentifier);
    }

    @Nonnull
    @Nonempty
    public String getScheme() {
        return m_sScheme;
    }

    @Nonnull
    public IPeppolGenericDocumentTypeIdentifierParts getParts() {
        return m_aParts;
    }

    @Nullable
    public IPeppolDocumentTypeIdentifierParts getXMLParts() {
        return ((m_aParts instanceof IPeppolDocumentTypeIdentifierParts)?((IPeppolDocumentTypeIdentifierParts) m_aParts):null);
    }

    @Nonnull
    @Nonempty
    public String getSyntaxSpecificID() {
        return m_aParts.getSyntaxSpecificID();
    }

    @Nonnull
    @Nonempty
    public String getCustomizationID() {
        return m_aParts.getCustomizationID();
    }

    @Nonnull
    @Nonempty
    public String getVersion() {
        return m_aParts.getVersion();
    }

    @Nonnull
    @Nonempty
    public String getCommonName() {
        return m_sCommonName;
    }

    @Nonnull
    @Nonempty
    public String getValue() {
        return m_sID;
    }

    @Nonnull
    @Nonempty
    public String getAsDocumentTypeIdentifierValue() {
        return m_sID;
    }

    @Nonnull
    public PeppolDocumentTypeIdentifier getAsDocumentTypeIdentifier() {
        return new PeppolDocumentTypeIdentifier(PeppolIdentifierFactory.INSTANCE, this);
    }

    @Nonnull
    public Version getInitialRelease() {
        return m_aInitialRelease;
    }

    @Nonnull
    public EPeppolCodeListItemState getState() {
        return m_eState;
    }

    @Nullable
    public Version getDeprecationRelease() {
        return m_aDeprecationRelease;
    }

    @Nullable
    public LocalDate getRemovalDate() {
        return m_aRemovalDate;
    }

    public boolean isAbstract() {
        return m_bAbstract;
    }

    public boolean isIssuedByOpenPeppol() {
        return m_bIssuedByOpenPeppol;
    }

    @CheckForSigned
    public int getBISVersion() {
        return m_nBISVersion;
    }

    @Nullable
    public String getDomainCommunity() {
        return m_sDomainCommunity;
    }

    @Nonnull
    @Nonempty
    @ReturnsMutableCopy
    public ICommonsList<IProcessIdentifier> getAllProcessIDs() {
        return m_aProcessIDs.getClone();
    }

    @Nullable
    public static EPredefinedDocumentTypeIdentifier getFromDocumentTypeIdentifierOrNull(@Nullable final IDocumentTypeIdentifier aDocTypeID) {
        if (aDocTypeID!= null) {
            for (EPredefinedDocumentTypeIdentifier e: EPredefinedDocumentTypeIdentifier.values()) {
                if (e.hasScheme(aDocTypeID.getScheme())&&e.hasValue(aDocTypeID.getValue())) {
                    return e;
                }
            }
        }
        return null;
    }
}
