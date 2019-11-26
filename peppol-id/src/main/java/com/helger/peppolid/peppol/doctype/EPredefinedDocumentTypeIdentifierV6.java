/**
 * Copyright (C) 2015-2019 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppolid.peppol.doctype;

import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.version.Version;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import javax.annotation.CheckForSigned;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;


/**
 * This file was automatically generated.
 * Do NOT edit!
 */
@CodingStyleguideUnaware
public enum EPredefinedDocumentTypeIdentifierV6
    implements IPeppolPredefinedDocumentTypeIdentifier
{

    /**
     * <b>This item is deprecated since version 2 and should not be used to issue new identifiers!</b><br><code>urn:www.peppol.eu:schema:xsd:VirtualCompanyDossier-1::VirtualCompanyDossier##urn:www.cenbii.eu:transaction:biicoretrdm991:ver0.1:#urn:www.peppol.eu:bis:peppol991a:ver1.0::0.1</code><br>
     * 
     * @since code list 1.0.0
     */
    @Deprecated
    urn_www_peppol_eu_schema_xsd_VirtualCompanyDossier_1__VirtualCompanyDossier__urn_www_cenbii_eu_transaction_biicoretrdm991_ver0_1__urn_www_peppol_eu_bis_peppol991a_ver1_0__0_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:www.peppol.eu:schema:xsd:VirtualCompanyDossier-1", "VirtualCompanyDossier", "urn:www.cenbii.eu:transaction:biicoretrdm991:ver0.1:#urn:www.peppol.eu:bis:peppol991a:ver1.0", "0.1"), "Virtual Company Dossier", Version.parse("1.0.0"), true, Version.parse("2"), true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::none")),

    /**
     * <b>This item is deprecated since version 2 and should not be used to issue new identifiers!</b><br><code>urn:www.peppol.eu:schema:xsd:VirtualCompanyDossierPackage-1::VirtualCompanyDossierPackage##urn:www.cenbii.eu:transaction:biicoretrdm992:ver0.1:#urn:www.peppol.eu:bis:peppol992a:ver1.0::0.1</code><br>
     * 
     * @since code list 1.0.0
     */
    @Deprecated
    urn_www_peppol_eu_schema_xsd_VirtualCompanyDossierPackage_1__VirtualCompanyDossierPackage__urn_www_cenbii_eu_transaction_biicoretrdm992_ver0_1__urn_www_peppol_eu_bis_peppol992a_ver1_0__0_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:www.peppol.eu:schema:xsd:VirtualCompanyDossierPackage-1", "VirtualCompanyDossierPackage", "urn:www.cenbii.eu:transaction:biicoretrdm992:ver0.1:#urn:www.peppol.eu:bis:peppol992a:ver1.0", "0.1"), "Virtual Company Dossier Package", Version.parse("1.0.0"), true, Version.parse("2"), true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::none")),

    /**
     * <b>This item is deprecated since version 2 and should not be used to issue new identifiers!</b><br><code>urn:www.peppol.eu:schema:xsd:CatalogueTemplate-1::CatalogueTemplate##urn:www.cenbii.eu:transaction:biicoretrdm993:ver0.1:#urn:www.peppol.eu:bis:peppol993a:ver1.0::0.1</code><br>
     * 
     * @since code list 1.0.0
     */
    @Deprecated
    urn_www_peppol_eu_schema_xsd_CatalogueTemplate_1__CatalogueTemplate__urn_www_cenbii_eu_transaction_biicoretrdm993_ver0_1__urn_www_peppol_eu_bis_peppol993a_ver1_0__0_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:www.peppol.eu:schema:xsd:CatalogueTemplate-1", "CatalogueTemplate", "urn:www.cenbii.eu:transaction:biicoretrdm993:ver0.1:#urn:www.peppol.eu:bis:peppol993a:ver1.0", "0.1"), "Catalogue Template", Version.parse("1.0.0"), true, Version.parse("2"), true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::none")),

    /**
     * <b>This item is deprecated since version 2 and should not be used to issue new identifiers!</b><br><code>urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:www.cenbii.eu:transaction:biicoretrdm019:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0::2.0</code><br>
     * 
     * @since code list 1.0.0
     */
    @Deprecated
    urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_www_cenbii_eu_transaction_biicoretrdm019_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2", "Catalogue", "urn:www.cenbii.eu:transaction:biicoretrdm019:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0", "2.0"), "PEPPOL Catalogue profile Catalogue V1", Version.parse("1.0.0"), true, Version.parse("2"), true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii01:ver1.0", "cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii01:ver2.0")),

    /**
     * <b>This item is deprecated since version 2 and should not be used to issue new identifiers!</b><br><code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.cenbii.eu:transaction:biicoretrdm057:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0::2.0</code><br>
     * 
     * @since code list 1.0.0
     */
    @Deprecated
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biicoretrdm057_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:www.cenbii.eu:transaction:biicoretrdm057:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0", "2.0"), "PEPPOL Catalogue profile ApplicationResponse V1", Version.parse("1.0.0"), true, Version.parse("2"), true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii01:ver1.0")),

    /**
     * <b>This item is deprecated since version 2 and should not be used to issue new identifiers!</b><br><code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.cenbii.eu:transaction:biicoretrdm058:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0::2.0</code><br>
     * 
     * @since code list 1.0.0
     */
    @Deprecated
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biicoretrdm058_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:www.cenbii.eu:transaction:biicoretrdm058:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0", "2.0"), "PEPPOL Catalogue profile ApplicationResponse V1", Version.parse("1.0.0"), true, Version.parse("2"), true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii01:ver1.0", "cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii01:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:www.cenbii.eu:transaction:biitrns019:ver2.0:extended:urn:www.peppol.eu:bis:peppol1a:ver4.0::2.1</code><br>
     * 
     * @since code list 1.2.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_www_cenbii_eu_transaction_biitrns019_ver2_0_extended_urn_www_peppol_eu_bis_peppol1a_ver4_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2", "Catalogue", "urn:www.cenbii.eu:transaction:biitrns019:ver2.0:extended:urn:www.peppol.eu:bis:peppol1a:ver4.0", "2.1"), "PEPPOL Catalogue profile V4", Version.parse("1.2.0"), false, null, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii01:ver2.0")),

    /**
     * <b>This item is deprecated since version 2 and should not be used to issue new identifiers!</b><br><code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0::2.0</code><br>
     * 
     * @since code list 1.0.0
     */
    @Deprecated
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biicoretrdm001_ver1_0__urn_www_peppol_eu_bis_peppol3a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0", "2.0"), "PEPPOL Order profile V1", Version.parse("1.0.0"), true, Version.parse("2"), true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii03:ver1.0")),

    /**
     * <b>This item is deprecated since version 3 and should not be used to issue new identifiers!</b><br><code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol03a:ver2.0::2.1</code><br>
     * 
     * @since code list 1.2.0
     */
    @Deprecated
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol03a_ver2_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol03a:ver2.0", "2.1"), "PEPPOL Order profile V2", Version.parse("1.2.0"), true, Version.parse("3"), true, 2, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii03:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol3a:ver2.0::2.1</code><br>
     * 
     * @since code list 3
     */
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol3a_ver2_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol3a:ver2.0", "2.1"), "PEPPOL Order profile V2", Version.parse("3"), false, null, true, 2, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii03:ver2.0")),

    /**
     * <b>This item is deprecated since version 2 and should not be used to issue new identifiers!</b><br><code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol4a:ver1.0::2.0</code><br>
     * 
     * @since code list 1.0.0
     */
    @Deprecated
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol4a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol4a:ver1.0", "2.0"), "PEPPOL Invoice profile V1", Version.parse("1.0.0"), true, Version.parse("2"), true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii04:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol4a:ver2.0::2.1</code><br>
     * 
     * @since code list 1.2.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biitrns010_ver2_0_extended_urn_www_peppol_eu_bis_peppol4a_ver2_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol4a:ver2.0", "2.1"), "PEPPOL Invoice profile V2", Version.parse("1.2.0"), false, null, true, 2, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii04:ver2.0")),

    /**
     * <b>This item is deprecated since version 2 and should not be used to issue new identifiers!</b><br><code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0::2.0</code><br>
     * 
     * @since code list 1.1.0
     */
    @Deprecated
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0", "2.0"), "PEPPOL Billing profile Invoice V1", Version.parse("1.1.0"), true, Version.parse("2"), true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii05:ver1.0")),

    /**
     * <b>This item is deprecated since version 2 and should not be used to issue new identifiers!</b><br><code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0::2.0</code><br>
     * 
     * @since code list 1.1.0
     */
    @Deprecated
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0", "2.0"), "PEPPOL Billing profile CreditNote V1", Version.parse("1.1.0"), true, Version.parse("2"), true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii05:ver1.0")),

    /**
     * <b>This item is deprecated since version 2 and should not be used to issue new identifiers!</b><br><code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm015:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0::2.0</code><br>
     * 
     * @since code list 1.1.0
     */
    @Deprecated
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm015_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biicoretrdm015:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0", "2.0"), "PEPPOL Billing profile Invoice V1", Version.parse("1.1.0"), true, Version.parse("2"), true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii05:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol5a:ver2.0::2.1</code><br>
     * 
     * @since code list 1.2.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biitrns010_ver2_0_extended_urn_www_peppol_eu_bis_peppol5a_ver2_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol5a:ver2.0", "2.1"), "PEPPOL Billing profile Invoice V2", Version.parse("1.2.0"), false, null, true, 2, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii05:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biitrns014:ver2.0:extended:urn:www.peppol.eu:bis:peppol5a:ver2.0::2.1</code><br>
     * 
     * @since code list 1.2.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biitrns014_ver2_0_extended_urn_www_peppol_eu_bis_peppol5a_ver2_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:www.cenbii.eu:transaction:biitrns014:ver2.0:extended:urn:www.peppol.eu:bis:peppol5a:ver2.0", "2.1"), "PEPPOL Billing profile CreditNote V2", Version.parse("1.2.0"), false, null, true, 2, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii05:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code><br>
     * 
     * @since code list 1.0.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biicoretrdm001_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0", "2.0"), "PEPPOL Procurement profile Order V1", Version.parse("1.0.0"), false, null, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii06:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponseSimple-2::OrderResponseSimple##urn:www.cenbii.eu:transaction:biicoretrdm002:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code><br>
     * 
     * @since code list 1.0.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponseSimple_2__OrderResponseSimple__urn_www_cenbii_eu_transaction_biicoretrdm002_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponseSimple-2", "OrderResponseSimple", "urn:www.cenbii.eu:transaction:biicoretrdm002:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0", "2.0"), "PEPPOL Procurement profile OrderResponseSimple V1", Version.parse("1.0.0"), false, null, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii06:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponseSimple-2::OrderResponseSimple##urn:www.cenbii.eu:transaction:biicoretrdm003:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code><br>
     * 
     * @since code list 1.0.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponseSimple_2__OrderResponseSimple__urn_www_cenbii_eu_transaction_biicoretrdm003_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponseSimple-2", "OrderResponseSimple", "urn:www.cenbii.eu:transaction:biicoretrdm003:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0", "2.0"), "PEPPOL Procurement profile OrderResponseSimple V1", Version.parse("1.0.0"), false, null, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii06:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code><br>
     * 
     * @since code list 1.0.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0", "2.0"), "PEPPOL Procurement profile Invoice V1", Version.parse("1.0.0"), false, null, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii06:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code><br>
     * 
     * @since code list 1.0.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0", "2.0"), "PEPPOL Procurement profile CreditNote V1", Version.parse("1.0.0"), false, null, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii06:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm015:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code><br>
     * 
     * @since code list 1.0.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm015_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biicoretrdm015:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0", "2.0"), "PEPPOL Procurement profile Invoice V1", Version.parse("1.0.0"), false, null, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii06:ver1.0")),

    /**
     * <b>This item is deprecated since version 2 and should not be used to issue new identifiers!</b><br><code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol4a:ver1.0#urn:www.difi.no:ehf:faktura:ver1::2.0</code><br>
     * 
     * @since code list 1.1.1
     */
    @Deprecated
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol4a_ver1_0_urn_www_difi_no_ehf_faktura_ver1__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol4a:ver1.0#urn:www.difi.no:ehf:faktura:ver1", "2.0"), "EHF Invoice V1", Version.parse("1.1.1"), true, Version.parse("2"), false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii04:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.cenbii.eu:profile:biixx:ver1.0#urn:www.difi.no:ehf:kreditnota:ver1::2.0</code><br>
     * 
     * @since code list 1.1.1
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_cenbii_eu_profile_biixx_ver1_0_urn_www_difi_no_ehf_kreditnota_ver1__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.cenbii.eu:profile:biixx:ver1.0#urn:www.difi.no:ehf:kreditnota:ver1", "2.0"), "Standalone Credit Note according to EHF V1", Version.parse("1.1.1"), false, null, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii05:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0::2.1</code><br>
     * 
     * @since code list 1.2.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0", "2.1"), "PEPPOL Ordering profile Order V1", Version.parse("1.2.0"), false, null, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii28:ver2.0")),

    /**
     * <b>This item is deprecated since version 3 and should not be used to issue new identifiers!</b><br><code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::Order##urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0::2.1</code><br>
     * 
     * @since code list 1.2.0
     */
    @Deprecated
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__Order__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2", "Order", "urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0", "2.1"), "PEPPOL Ordering profile OrderResponse V1", Version.parse("1.2.0"), true, Version.parse("3"), true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii28:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::OrderResponse##urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0::2.1</code><br>
     * 
     * @since code list 3
     */
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2", "OrderResponse", "urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0", "2.1"), "PEPPOL Ordering profile OrderResponse V1", Version.parse("3"), false, null, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii28:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2::DespatchAdvice##urn:www.cenbii.eu:transaction:biitrns016:ver1.0:extended:urn:www.peppol.eu:bis:peppol30a:ver1.0::2.1</code><br>
     * 
     * @since code list 1.2.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_www_cenbii_eu_transaction_biitrns016_ver1_0_extended_urn_www_peppol_eu_bis_peppol30a_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2", "DespatchAdvice", "urn:www.cenbii.eu:transaction:biitrns016:ver1.0:extended:urn:www.peppol.eu:bis:peppol30a:ver1.0", "2.1"), "PEPPOL Despatch Advice V1", Version.parse("1.2.0"), false, null, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii30:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.cenbii.eu:transaction:biitrns071:ver2.0:extended:urn:www.peppol.eu:bis:peppol36a:ver1.0::2.1</code><br>
     * 
     * @since code list 1.2.0
     */
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biitrns071_ver2_0_extended_urn_www_peppol_eu_bis_peppol36a_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:www.cenbii.eu:transaction:biitrns071:ver2.0:extended:urn:www.peppol.eu:bis:peppol36a:ver1.0", "2.1"), "PEPPOL Message Level Response V1", Version.parse("1.2.0"), false, null, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii36:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0::2.1</code><br>
     * 
     * @since code list 2
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0", "2.1"), "PEPPOL BIS Billing UBL Invoice V3", Version.parse("2"), false, null, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0::2.1</code><br>
     * 
     * @since code list 2
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0", "2.1"), "PEPPOL BIS Billing UBL CreditNote V3", Version.parse("2"), false, null, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0:extended:urn:fdc:peppol-authority.co.uk:spec:ordering:ver1.0::2.1</code><br>
     * 
     * @since code list 3
     */
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0_extended_urn_fdc_peppol_authority_co_uk_spec_ordering_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0:extended:urn:fdc:peppol-authority.co.uk:spec:ordering:ver1.0", "2.1"), "DHSC Customized Ordering profile Order V1", Version.parse("3"), false, null, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii28:ver2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::Order##urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0:extended:urn:fdc:peppol-authority.co.uk:spec:ordering:ver1.0::2.1</code><br>
     * 
     * @since code list 3
     */
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__Order__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0_extended_urn_fdc_peppol_authority_co_uk_spec_ordering_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2", "Order", "urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0:extended:urn:fdc:peppol-authority.co.uk:spec:ordering:ver1.0", "2.1"), "DHSC Customized Ordering profile OrderResponse V1", Version.parse("3"), false, null, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii28:ver2.0")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0::D16B</code><br>
     * 
     * @since code list 3
     */
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0__D16B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0", "D16B"), "PEPPOL BIS Billing CII Invoice V3", Version.parse("3"), false, null, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestRequest-2::ExpressionOfInterestRequest##urn:www.cenbii.eu:transaction:biitrdm081:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t001:ver1.0::2.2</code><br>
     * 
     * @since code list 3
     */
    urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestRequest_2__ExpressionOfInterestRequest__urn_www_cenbii_eu_transaction_biitrdm081_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t001_ver1_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestRequest-2", "ExpressionOfInterestRequest", "urn:www.cenbii.eu:transaction:biitrdm081:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t001:ver1.0", "2.2"), "Procurement procedure subscription Request V1", Version.parse("3"), false, null, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:pracc:p001:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestResponse-2::ExpressionOfInterestResponse##urn:www.cenbii.eu:transaction:biitrdm082:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t002:ver1.0::2.2</code><br>
     * 
     * @since code list 3
     */
    urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestResponse_2__ExpressionOfInterestResponse__urn_www_cenbii_eu_transaction_biitrdm082_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t002_ver1_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestResponse-2", "ExpressionOfInterestResponse", "urn:www.cenbii.eu:transaction:biitrdm082:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t002:ver1.0", "2.2"), "Procurement procedure subscription Response V1", Version.parse("3"), false, null, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:pracc:p001:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:TenderStatusRequest-2::TenderStatusRequest##urn:www.cenbii.eu:transaction:biitrdm097:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t003:ver1.0::2.2</code><br>
     * 
     * @since code list 3
     */
    urn_oasis_names_specification_ubl_schema_xsd_TenderStatusRequest_2__TenderStatusRequest__urn_www_cenbii_eu_transaction_biitrdm097_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t003_ver1_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:TenderStatusRequest-2", "TenderStatusRequest", "urn:www.cenbii.eu:transaction:biitrdm097:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t003:ver1.0", "2.2"), "Procurement document access TenderStatusRequest V1", Version.parse("3"), false, null, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:pracc:p002:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CallForTenders-2::CallForTenders##urn:www.cenbii.eu:transaction:biitrdm083:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t004:ver1.0::2.2</code><br>
     * 
     * @since code list 3
     */
    urn_oasis_names_specification_ubl_schema_xsd_CallForTenders_2__CallForTenders__urn_www_cenbii_eu_transaction_biitrdm083_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t004_ver1_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CallForTenders-2", "CallForTenders", "urn:www.cenbii.eu:transaction:biitrdm083:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t004:ver1.0", "2.2"), "Procurement document access CallForTenders V1", Version.parse("3"), false, null, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:pracc:p002:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:TenderReceipt-2::TenderReceipt##urn:www.cenbii.eu:transaction:biitrdm045:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t006:ver1.0::2.2</code><br>
     * 
     * @since code list 3
     */
    urn_oasis_names_specification_ubl_schema_xsd_TenderReceipt_2__TenderReceipt__urn_www_cenbii_eu_transaction_biitrdm045_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t006_ver1_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:TenderReceipt-2", "TenderReceipt", "urn:www.cenbii.eu:transaction:biitrdm045:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t006:ver1.0", "2.2"), "Tender Submission TenderReceipt V1", Version.parse("3"), false, null, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:pracc:p003:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Tender-2::Tender##urn:www.cenbii.eu:transaction:biitrdm090:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t005:ver1.0::2.2</code><br>
     * 
     * @since code list 3
     */
    urn_oasis_names_specification_ubl_schema_xsd_Tender_2__Tender__urn_www_cenbii_eu_transaction_biitrdm090_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t005_ver1_0__2_2("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Tender-2", "Tender", "urn:www.cenbii.eu:transaction:biitrdm090:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t005:ver1.0", "2.2"), "Tender Submission Tender V1", Version.parse("3"), false, null, true, 1, "PRAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:pracc:p003:01:1.0")),

    /**
     * <b>This item is deprecated since version 5 and should not be used to issue new identifiers!</b><br><code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.1::2.1</code><br>
     * 
     * @since code list 3
     */
    @Deprecated
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.1", "2.1"), "XRechnung UBL Invoice V1.1", Version.parse("3"), true, Version.parse("5"), false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <b>This item is deprecated since version 5 and should not be used to issue new identifiers!</b><br><code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.1::2.1</code><br>
     * 
     * @since code list 3
     */
    @Deprecated
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_1__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.1", "2.1"), "XRechnung UBL CreditNote V1.1", Version.parse("3"), true, Version.parse("5"), false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <b>This item is deprecated since version 5 and should not be used to issue new identifiers!</b><br><code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.1::D16B</code><br>
     * 
     * @since code list 3
     */
    @Deprecated
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_1__D16B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.1", "D16B"), "XRechnung CII Invoice V1.1", Version.parse("3"), true, Version.parse("5"), false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oioubl:names:specification:oioubl:schema:xsd:UtilityStatement-2::UtilityStatement##OIOUBL-2.02::2.0</code><br>
     * 
     * @since code list 3
     */
    urn_oioubl_names_specification_oioubl_schema_xsd_UtilityStatement_2__UtilityStatement__OIOUBL_2_02__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oioubl:names:specification:oioubl:schema:xsd:UtilityStatement-2", "UtilityStatement", "OIOUBL-2.02", "2.0"), "OIOUBL UtilityStatement V2.02", Version.parse("3"), false, null, false, -1, "POAC", new CommonsArrayList<>("oioubl-procid-ubl::Reference-Utility-1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Reminder-2::Reminder##OIOUBL-2.02::2.0</code><br>
     * 
     * @since code list 3
     */
    urn_oasis_names_specification_ubl_schema_xsd_Reminder_2__Reminder__OIOUBL_2_02__2_0("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Reminder-2", "Reminder", "OIOUBL-2.02", "2.0"), "OIOUBL Reminder V2.02", Version.parse("3"), false, null, false, -1, "POAC", new CommonsArrayList<>("oioubl-procid-ubl::Procurement-ReminderOnly-1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#conformant#urn:UBL.BE:1.0.0.20180214::2.1</code><br>
     * 
     * @since code list 3
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_UBL_BE_1_0_0_20180214__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#conformant#urn:UBL.BE:1.0.0.20180214", "2.1"), "UBL.BE Invoice 3.0", Version.parse("3"), false, null, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#conformant#urn:UBL.BE:1.0.0.20180214::2.1</code><br>
     * 
     * @since code list 3
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_UBL_BE_1_0_0_20180214__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#conformant#urn:UBL.BE:1.0.0.20180214", "2.1"), "UBL.BE Credit Note 3.0", Version.parse("3"), false, null, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.peppol.eu:transaction:biitrns111:ver1.0::2.1</code><br>
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_peppol_eu_transaction_biitrns111_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:www.peppol.eu:transaction:biitrns111:ver1.0", "2.1"), "PEPPOL Invoice Response V1", Version.parse("4"), false, null, true, 1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:www.peppol.eu:profile:bis63a:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:fdc:peppol.eu:poacc:trns:catalogue:3::2.1</code><br>
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_fdc_peppol_eu_poacc_trns_catalogue_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2", "Catalogue", "urn:fdc:peppol.eu:poacc:trns:catalogue:3", "2.1"), "PEPPOL Catalogue transaction 3.0", Version.parse("4"), false, null, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:catalogue_only:3", "cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:catalogue_wo_response:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:fdc:peppol.eu:poacc:trns:catalogue_response:3::2.1</code><br>
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_poacc_trns_catalogue_response_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:fdc:peppol.eu:poacc:trns:catalogue_response:3", "2.1"), "PEPPOL Catalogue Response transaction 3.0", Version.parse("4"), false, null, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:catalogue_only:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:fdc:peppol.eu:poacc:trns:order:3::2.1</code><br>
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_fdc_peppol_eu_poacc_trns_order_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:fdc:peppol.eu:poacc:trns:order:3", "2.1"), "PEPPOL Order transaction 3.0", Version.parse("4"), false, null, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:ordering:3", "cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:order_only:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:fdc:peppol.eu:poacc:trns:invoice_response:3::2.1</code><br>
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_poacc_trns_invoice_response_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:fdc:peppol.eu:poacc:trns:invoice_response:3", "2.1"), "PEPPOL Invoice Response transaction 3.0", Version.parse("4"), false, null, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:invoice_response:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:fdc:peppol.eu:poacc:trns:punch_out:3::2.1</code><br>
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_fdc_peppol_eu_poacc_trns_punch_out_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2", "Catalogue", "urn:fdc:peppol.eu:poacc:trns:punch_out:3", "2.1"), "PEPPOL Punch Out transaction 3.0", Version.parse("4"), false, null, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:punch_out:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::OrderResponse##urn:fdc:peppol.eu:poacc:trns:order_response:3::2.1</code><br>
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_response_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2", "OrderResponse", "urn:fdc:peppol.eu:poacc:trns:order_response:3", "2.1"), "PEPPOL Order Response transaction 3.0", Version.parse("4"), false, null, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:ordering:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2::DespatchAdvice##urn:fdc:peppol.eu:poacc:trns:despatch_advice:3::2.1</code><br>
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_fdc_peppol_eu_poacc_trns_despatch_advice_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2", "DespatchAdvice", "urn:fdc:peppol.eu:poacc:trns:despatch_advice:3", "2.1"), "PEPPOL Despatch Advice transaction 3.0", Version.parse("4"), false, null, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:despatch_advice:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::OrderResponse##urn:fdc:peppol.eu:poacc:trns:order_agreement:3::2.1</code><br>
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_agreement_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2", "OrderResponse", "urn:fdc:peppol.eu:poacc:trns:order_agreement:3", "2.1"), "PEPPOL Order Agreement transaction 3.0", Version.parse("4"), false, null, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:order_agreement:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:fdc:peppol.eu:poacc:trns:mlr:3::2.1</code><br>
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_poacc_trns_mlr_3__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:fdc:peppol.eu:poacc:trns:mlr:3", "2.1"), "PEPPOL Message Level Response transaction 3.0", Version.parse("4"), false, null, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:poacc:bis:mlr:3")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:nen.nl:nlcius:v1.0::2.1</code><br>
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_nen_nl_nlcius_v1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:fdc:nen.nl:nlcius:v1.0", "2.1"), "SI-UBL 2.0 Invoice", Version.parse("4"), false, null, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:fdc:nen.nl:nlcius:v1.0::2.1</code><br>
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_nen_nl_nlcius_v1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:fdc:nen.nl:nlcius:v1.0", "2.1"), "SI-UBL 2.0 CreditNote", Version.parse("4"), false, null, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:sg:3.0::2.1</code><br>
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_sg_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:sg:3.0", "2.1"), "SG PEPPOL BIS Billing 3.0 Invoice", Version.parse("4"), false, null, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::CreditNote##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:sg:3.0::2.1</code><br>
     * 
     * @since code list 4
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_sg_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "CreditNote", "urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:sg:3.0", "2.1"), "SG PEPPOL BIS Billing 3.0 Credit Note", Version.parse("4"), false, null, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.2::2.1</code><br>
     * 
     * @since code list 5
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_2__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.2", "2.1"), "XRechnung UBL Invoice V1.2", Version.parse("5"), false, null, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.2::2.1</code><br>
     * 
     * @since code list 5
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_2__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.2", "2.1"), "XRechnung UBL CreditNote V1.2", Version.parse("5"), false, null, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.2::D16B</code><br>
     * 
     * @since code list 5
     */
    urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_2__D16B("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100", "CrossIndustryInvoice", "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.2", "D16B"), "XRechnung CII Invoice V1.2", Version.parse("5"), false, null, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:fdc:www.efaktura.gov.pl:ver1.0:trns:account_corr:ver1.0::2.1</code><br>
     * 
     * @since code list 6
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_fdc_www_efaktura_gov_pl_ver1_0_trns_account_corr_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:fdc:www.efaktura.gov.pl:ver1.0:trns:account_corr:ver1.0", "2.1"), "PEF.PL Accounting Note v1", Version.parse("6"), false, null, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:www.efaktura.gov.pl:ver1.0:account_corr:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0#extended#urn:fdc:www.efaktura.gov.pl:ver1.0::2.1</code><br>
     * 
     * @since code list 6
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_extended_urn_fdc_www_efaktura_gov_pl_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0#extended#urn:fdc:www.efaktura.gov.pl:ver1.0", "2.1"), "PEF.PL Correcting Invoice v1", Version.parse("6"), false, null, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:www.efaktura.gov.pl:ver1.0:corr_inv:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ReceiptAdvice-2::ReceiptAdvice##urn:fdc:www.efaktura.gov.pl:ver1.0:trns:receipt_advice:ver1.0::2.1</code><br>
     * 
     * @since code list 6
     */
    urn_oasis_names_specification_ubl_schema_xsd_ReceiptAdvice_2__ReceiptAdvice__urn_fdc_www_efaktura_gov_pl_ver1_0_trns_receipt_advice_ver1_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ReceiptAdvice-2", "ReceiptAdvice", "urn:fdc:www.efaktura.gov.pl:ver1.0:trns:receipt_advice:ver1.0", "2.1"), "PEF.PL Receipt Advice v1", Version.parse("6"), false, null, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:www.efaktura.gov.pl:ver1.0:receipt_advice:ver1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:aunz:3.0::2.1</code><br>
     * 
     * @since code list 6
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_aunz_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:aunz:3.0", "2.1"), "AU-NZ PEPPOL BIS Billing 3.0 Invoice", Version.parse("6"), false, null, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:aunz:3.0::2.1</code><br>
     * 
     * @since code list 6
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_aunz_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:aunz:3.0", "2.1"), "AU-NZ PEPPOL BIS Billing 3.0 CreditNote", Version.parse("6"), false, null, true, 3, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:selfbilling:international:aunz:3.0::2.1</code><br>
     * 
     * @since code list 6
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_selfbilling_international_aunz_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:selfbilling:international:aunz:3.0", "2.1"), "AU-NZ Self-Billing 3.0 Invoice", Version.parse("6"), false, null, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:selfbilling:international:aunz:3.0::2.1</code><br>
     * 
     * @since code list 6
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_selfbilling_international_aunz_3_0__2_1("busdox-docid-qns", new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:selfbilling:international:aunz:3.0", "2.1"), "AU-NZ Self-Billing 3.0 CreditNote", Version.parse("6"), false, null, false, -1, "POAC", new CommonsArrayList<>("cenbii-procid-ubl::urn:fdc:peppol.eu:2017:poacc:billing:01:1.0"));
    /**
     * Same as {@link #urn_www_peppol_eu_schema_xsd_VirtualCompanyDossier_1__VirtualCompanyDossier__urn_www_cenbii_eu_transaction_biicoretrdm991_ver0_1__urn_www_peppol_eu_bis_peppol991a_ver1_0__0_1}
     */
    @Deprecated
    public static final EPredefinedDocumentTypeIdentifierV6 VIRTUALCOMPANYDOSSIER_T991_BIS991A = EPredefinedDocumentTypeIdentifierV6 .urn_www_peppol_eu_schema_xsd_VirtualCompanyDossier_1__VirtualCompanyDossier__urn_www_cenbii_eu_transaction_biicoretrdm991_ver0_1__urn_www_peppol_eu_bis_peppol991a_ver1_0__0_1;
    /**
     * Same as {@link #urn_www_peppol_eu_schema_xsd_VirtualCompanyDossierPackage_1__VirtualCompanyDossierPackage__urn_www_cenbii_eu_transaction_biicoretrdm992_ver0_1__urn_www_peppol_eu_bis_peppol992a_ver1_0__0_1}
     */
    @Deprecated
    public static final EPredefinedDocumentTypeIdentifierV6 VIRTUALCOMPANYDOSSIERPACKAGE_T992_BIS992A = EPredefinedDocumentTypeIdentifierV6 .urn_www_peppol_eu_schema_xsd_VirtualCompanyDossierPackage_1__VirtualCompanyDossierPackage__urn_www_cenbii_eu_transaction_biicoretrdm992_ver0_1__urn_www_peppol_eu_bis_peppol992a_ver1_0__0_1;
    /**
     * Same as {@link #urn_www_peppol_eu_schema_xsd_CatalogueTemplate_1__CatalogueTemplate__urn_www_cenbii_eu_transaction_biicoretrdm993_ver0_1__urn_www_peppol_eu_bis_peppol993a_ver1_0__0_1}
     */
    @Deprecated
    public static final EPredefinedDocumentTypeIdentifierV6 CATALOGUETEMPLATE_T993_BIS993A = EPredefinedDocumentTypeIdentifierV6 .urn_www_peppol_eu_schema_xsd_CatalogueTemplate_1__CatalogueTemplate__urn_www_cenbii_eu_transaction_biicoretrdm993_ver0_1__urn_www_peppol_eu_bis_peppol993a_ver1_0__0_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_www_cenbii_eu_transaction_biicoretrdm019_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0}
     */
    @Deprecated
    public static final EPredefinedDocumentTypeIdentifierV6 CATALOGUE_T019_BIS1A = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_www_cenbii_eu_transaction_biicoretrdm019_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biicoretrdm057_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0}
     */
    @Deprecated
    public static final EPredefinedDocumentTypeIdentifierV6 APPLICATIONRESPONSE_T057_BIS1A = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biicoretrdm057_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biicoretrdm058_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0}
     */
    @Deprecated
    public static final EPredefinedDocumentTypeIdentifierV6 APPLICATIONRESPONSE_T058_BIS1A = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biicoretrdm058_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_www_cenbii_eu_transaction_biitrns019_ver2_0_extended_urn_www_peppol_eu_bis_peppol1a_ver4_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 CATALOGUE_T019_BIS1A_V40 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_www_cenbii_eu_transaction_biitrns019_ver2_0_extended_urn_www_peppol_eu_bis_peppol1a_ver4_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biicoretrdm001_ver1_0__urn_www_peppol_eu_bis_peppol3a_ver1_0__2_0}
     */
    @Deprecated
    public static final EPredefinedDocumentTypeIdentifierV6 ORDER_T001_BIS3A = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biicoretrdm001_ver1_0__urn_www_peppol_eu_bis_peppol3a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol03a_ver2_0__2_1}
     */
    @Deprecated
    public static final EPredefinedDocumentTypeIdentifierV6 ORDER_T001_BIS03A_V20 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol03a_ver2_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol3a_ver2_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 ORDER_T001_BIS3A_V20 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol3a_ver2_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol4a_ver1_0__2_0}
     */
    @Deprecated
    public static final EPredefinedDocumentTypeIdentifierV6 INVOICE_T010_BIS4A = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol4a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biitrns010_ver2_0_extended_urn_www_peppol_eu_bis_peppol4a_ver2_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 INVOICE_T010_BIS4A_V20 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biitrns010_ver2_0_extended_urn_www_peppol_eu_bis_peppol4a_ver2_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0}
     */
    @Deprecated
    public static final EPredefinedDocumentTypeIdentifierV6 INVOICE_T010_BIS5A = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0}
     */
    @Deprecated
    public static final EPredefinedDocumentTypeIdentifierV6 CREDITNOTE_T014_BIS5A = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm015_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0}
     */
    @Deprecated
    public static final EPredefinedDocumentTypeIdentifierV6 INVOICE_T015_BIS5A = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm015_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biitrns010_ver2_0_extended_urn_www_peppol_eu_bis_peppol5a_ver2_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 INVOICE_T010_BIS5A_V20 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biitrns010_ver2_0_extended_urn_www_peppol_eu_bis_peppol5a_ver2_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biitrns014_ver2_0_extended_urn_www_peppol_eu_bis_peppol5a_ver2_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 CREDITNOTE_T014_BIS5A_V20 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biitrns014_ver2_0_extended_urn_www_peppol_eu_bis_peppol5a_ver2_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biicoretrdm001_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 ORDER_T001_BIS6A = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biicoretrdm001_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponseSimple_2__OrderResponseSimple__urn_www_cenbii_eu_transaction_biicoretrdm002_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 ORDERRESPONSESIMPLE_T002_BIS6A = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_OrderResponseSimple_2__OrderResponseSimple__urn_www_cenbii_eu_transaction_biicoretrdm002_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponseSimple_2__OrderResponseSimple__urn_www_cenbii_eu_transaction_biicoretrdm003_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 ORDERRESPONSESIMPLE_T003_BIS6A = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_OrderResponseSimple_2__OrderResponseSimple__urn_www_cenbii_eu_transaction_biicoretrdm003_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 INVOICE_T010_BIS6A = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 CREDITNOTE_T014_BIS6A = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm015_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 INVOICE_T015_BIS6A = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm015_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol4a_ver1_0_urn_www_difi_no_ehf_faktura_ver1__2_0}
     */
    @Deprecated
    public static final EPredefinedDocumentTypeIdentifierV6 INVOICE_T010_BIS4A_WWW_DIFI_NO_EHF_FAKTURA_VER1 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol4a_ver1_0_urn_www_difi_no_ehf_faktura_ver1__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_cenbii_eu_profile_biixx_ver1_0_urn_www_difi_no_ehf_kreditnota_ver1__2_0}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 CREDITNOTE_T014_WWW_CENBII_EU_PROFILE_BIIXX_VER1_0_WWW_DIFI_NO_EHF_KREDITNOTA_VER1 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_cenbii_eu_profile_biixx_ver1_0_urn_www_difi_no_ehf_kreditnota_ver1__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 ORDER_T001_BIS28A = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__Order__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1}
     */
    @Deprecated
    public static final EPredefinedDocumentTypeIdentifierV6 ORDER_T076_BIS28A = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__Order__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 ORDER_T076_BIS28A2 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_www_cenbii_eu_transaction_biitrns016_ver1_0_extended_urn_www_peppol_eu_bis_peppol30a_ver1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 DESPATCHADVICE_T016_BIS30A = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_www_cenbii_eu_transaction_biitrns016_ver1_0_extended_urn_www_peppol_eu_bis_peppol30a_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biitrns071_ver2_0_extended_urn_www_peppol_eu_bis_peppol36a_ver1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 APPLICATIONRESPONSE_T071_BIS36A = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biitrns071_ver2_0_extended_urn_www_peppol_eu_bis_peppol36a_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 INVOICE_EN16931_PEPPOL_V30 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 CREDITNOTE_EN16931_PEPPOL_V30 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0_extended_urn_fdc_peppol_authority_co_uk_spec_ordering_ver1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 DHSC_CUSTOMIZED_ORDERING_PROFILE_V1_ORDER = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0_extended_urn_fdc_peppol_authority_co_uk_spec_ordering_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__Order__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0_extended_urn_fdc_peppol_authority_co_uk_spec_ordering_ver1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 DHSC_CUSTOMIZED_ORDERING_PROFILE_V1_ORDER_RESPONSE = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__Order__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0_extended_urn_fdc_peppol_authority_co_uk_spec_ordering_ver1_0__2_1;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0__D16B}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 INVOICE_CII_EN16931_PEPPOL_V30 = EPredefinedDocumentTypeIdentifierV6 .urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0__D16B;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestRequest_2__ExpressionOfInterestRequest__urn_www_cenbii_eu_transaction_biitrdm081_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t001_ver1_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 EXPRESSION_OF_INTEREST_REQUEST_V1 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestRequest_2__ExpressionOfInterestRequest__urn_www_cenbii_eu_transaction_biitrdm081_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t001_ver1_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestResponse_2__ExpressionOfInterestResponse__urn_www_cenbii_eu_transaction_biitrdm082_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t002_ver1_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 EXPRESSION_OF_INTEREST_RESPONSE_V1 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_ExpressionOfInterestResponse_2__ExpressionOfInterestResponse__urn_www_cenbii_eu_transaction_biitrdm082_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t002_ver1_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_TenderStatusRequest_2__TenderStatusRequest__urn_www_cenbii_eu_transaction_biitrdm097_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t003_ver1_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 TENDER_STATUS_REQUEST_V1 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_TenderStatusRequest_2__TenderStatusRequest__urn_www_cenbii_eu_transaction_biitrdm097_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t003_ver1_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CallForTenders_2__CallForTenders__urn_www_cenbii_eu_transaction_biitrdm083_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t004_ver1_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 CALL_FOR_TENDERS_V1 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_CallForTenders_2__CallForTenders__urn_www_cenbii_eu_transaction_biitrdm083_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t004_ver1_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_TenderReceipt_2__TenderReceipt__urn_www_cenbii_eu_transaction_biitrdm045_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t006_ver1_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 TENDER_RECEIPT_V1 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_TenderReceipt_2__TenderReceipt__urn_www_cenbii_eu_transaction_biitrdm045_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t006_ver1_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Tender_2__Tender__urn_www_cenbii_eu_transaction_biitrdm090_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t005_ver1_0__2_2}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 TENDER_V1 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Tender_2__Tender__urn_www_cenbii_eu_transaction_biitrdm090_ver3_0_extended_urn_fdc_peppol_eu_2017_pracc_t005_ver1_0__2_2;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_1__2_1}
     */
    @Deprecated
    public static final EPredefinedDocumentTypeIdentifierV6 XRECHNUNG_INVOICE_UBL_V11 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_1__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_1__2_1}
     */
    @Deprecated
    public static final EPredefinedDocumentTypeIdentifierV6 XRECHNUNG_CREDIT_NOTE_UBL_V11 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_1__2_1;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_1__D16B}
     */
    @Deprecated
    public static final EPredefinedDocumentTypeIdentifierV6 XRECHNUNG_INVOICE_CII_V11 = EPredefinedDocumentTypeIdentifierV6 .urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_1__D16B;
    /**
     * Same as {@link #urn_oioubl_names_specification_oioubl_schema_xsd_UtilityStatement_2__UtilityStatement__OIOUBL_2_02__2_0}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 OIOUBL_UTILITY_STATEMENT_202 = EPredefinedDocumentTypeIdentifierV6 .urn_oioubl_names_specification_oioubl_schema_xsd_UtilityStatement_2__UtilityStatement__OIOUBL_2_02__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Reminder_2__Reminder__OIOUBL_2_02__2_0}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 OIOUBL_REMINDER_202 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Reminder_2__Reminder__OIOUBL_2_02__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_UBL_BE_1_0_0_20180214__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 UBL_BE_INVOICE_UBL_V11 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_UBL_BE_1_0_0_20180214__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_UBL_BE_1_0_0_20180214__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 UBL_BE_CREDIT_NOTE_UBL_V11 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_UBL_BE_1_0_0_20180214__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_peppol_eu_transaction_biitrns111_ver1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 APPLICATIONRESPONSE_WWW_PEPPOL_EU_TRANSACTION_BIITRNS111_VER1_0 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_peppol_eu_transaction_biitrns111_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_fdc_peppol_eu_poacc_trns_catalogue_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 CATALOGUE_FDC_PEPPOL_EU_POACC_TRNS_CATALOGUE_3 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_fdc_peppol_eu_poacc_trns_catalogue_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_poacc_trns_catalogue_response_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 APPLICATIONRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_CATALOGUE_RESPONSE_3 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_poacc_trns_catalogue_response_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_fdc_peppol_eu_poacc_trns_order_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 ORDER_FDC_PEPPOL_EU_POACC_TRNS_ORDER_3 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_fdc_peppol_eu_poacc_trns_order_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_poacc_trns_invoice_response_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 APPLICATIONRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_INVOICE_RESPONSE_3 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_poacc_trns_invoice_response_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_fdc_peppol_eu_poacc_trns_punch_out_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 CATALOGUE_FDC_PEPPOL_EU_POACC_TRNS_PUNCH_OUT_3 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_fdc_peppol_eu_poacc_trns_punch_out_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_response_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 ORDERRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_ORDER_RESPONSE_3 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_response_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_fdc_peppol_eu_poacc_trns_despatch_advice_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 DESPATCHADVICE_FDC_PEPPOL_EU_POACC_TRNS_DESPATCH_ADVICE_3 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_fdc_peppol_eu_poacc_trns_despatch_advice_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_agreement_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 ORDERRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_ORDER_AGREEMENT_3 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__OrderResponse__urn_fdc_peppol_eu_poacc_trns_order_agreement_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_poacc_trns_mlr_3__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 APPLICATIONRESPONSE_FDC_PEPPOL_EU_POACC_TRNS_MLR_3 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_fdc_peppol_eu_poacc_trns_mlr_3__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_nen_nl_nlcius_v1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 INVOICE_CEN_EU_EN16931_2017_COMPLIANT_FDC_NEN_NL_NLCIUS_V1_0 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_fdc_nen_nl_nlcius_v1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_nen_nl_nlcius_v1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 CREDITNOTE_CEN_EU_EN16931_2017_COMPLIANT_FDC_NEN_NL_NLCIUS_V1_0 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_nen_nl_nlcius_v1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_sg_3_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 INVOICE_CEN_EU_EN16931_2017_CONFORMANT_FDC_PEPPOL_EU_2017_POACC_BILLING_INTERNATIONAL_SG_3_0 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_sg_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_sg_3_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 CREDITNOTE_CEN_EU_EN16931_2017_CONFORMANT_FDC_PEPPOL_EU_2017_POACC_BILLING_INTERNATIONAL_SG_3_0 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_sg_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_2__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 INVOICE_CEN_EU_EN16931_2017_COMPLIANT_XOEV_DE_KOSIT_STANDARD_XRECHNUNG_1_2 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_2__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_2__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 CREDITNOTE_CEN_EU_EN16931_2017_COMPLIANT_XOEV_DE_KOSIT_STANDARD_XRECHNUNG_1_2 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_2__2_1;
    /**
     * Same as {@link #urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_2__D16B}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 CROSSINDUSTRYINVOICE_CEN_EU_EN16931_2017_COMPLIANT_XOEV_DE_KOSIT_STANDARD_XRECHNUNG_1_2 = EPredefinedDocumentTypeIdentifierV6 .urn_un_unece_uncefact_data_standard_CrossIndustryInvoice_100__CrossIndustryInvoice__urn_cen_eu_en16931_2017_compliant_urn_xoev_de_kosit_standard_xrechnung_1_2__D16B;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_fdc_www_efaktura_gov_pl_ver1_0_trns_account_corr_ver1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 CREDITNOTE_FDC_WWW_EFAKTURA_GOV_PL_VER1_0_TRNS_ACCOUNT_CORR_VER1_0 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_fdc_www_efaktura_gov_pl_ver1_0_trns_account_corr_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_extended_urn_fdc_www_efaktura_gov_pl_ver1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 CREDITNOTE_CEN_EU_EN16931_2017_COMPLIANT_FDC_PEPPOL_EU_2017_POACC_BILLING_3_0_EXTENDED_FDC_WWW_EFAKTURA_GOV_PL_VER1_0 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_compliant_urn_fdc_peppol_eu_2017_poacc_billing_3_0_extended_urn_fdc_www_efaktura_gov_pl_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ReceiptAdvice_2__ReceiptAdvice__urn_fdc_www_efaktura_gov_pl_ver1_0_trns_receipt_advice_ver1_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 RECEIPTADVICE_FDC_WWW_EFAKTURA_GOV_PL_VER1_0_TRNS_RECEIPT_ADVICE_VER1_0 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_ReceiptAdvice_2__ReceiptAdvice__urn_fdc_www_efaktura_gov_pl_ver1_0_trns_receipt_advice_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_aunz_3_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 INVOICE_CEN_EU_EN16931_2017_CONFORMANT_FDC_PEPPOL_EU_2017_POACC_BILLING_INTERNATIONAL_AUNZ_3_0 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_aunz_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_aunz_3_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 CREDITNOTE_CEN_EU_EN16931_2017_CONFORMANT_FDC_PEPPOL_EU_2017_POACC_BILLING_INTERNATIONAL_AUNZ_3_0 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_billing_international_aunz_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_selfbilling_international_aunz_3_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 INVOICE_CEN_EU_EN16931_2017_CONFORMANT_FDC_PEPPOL_EU_2017_POACC_SELFBILLING_INTERNATIONAL_AUNZ_3_0 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_selfbilling_international_aunz_3_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_selfbilling_international_aunz_3_0__2_1}
     */
    public static final EPredefinedDocumentTypeIdentifierV6 CREDITNOTE_CEN_EU_EN16931_2017_CONFORMANT_FDC_PEPPOL_EU_2017_POACC_SELFBILLING_INTERNATIONAL_AUNZ_3_0 = EPredefinedDocumentTypeIdentifierV6 .urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_cen_eu_en16931_2017_conformant_urn_fdc_peppol_eu_2017_poacc_selfbilling_international_aunz_3_0__2_1;
    private final String m_sScheme;
    private final IPeppolDocumentTypeIdentifierParts m_aParts;
    private final String m_sID;
    private final String m_sProfileCode;
    private final Version m_aSince;
    private final boolean m_bDeprecated;
    private final Version m_aDeprecatedSince;
    private final boolean m_bIssuedByOpenPEPPOL;
    private final int m_nBISVersion;
    private final String m_sDomainCommunity;
    private final ICommonsList<IProcessIdentifier> m_aProcIDs;

    private EPredefinedDocumentTypeIdentifierV6(@Nonnull @Nonempty final String sScheme,
        @Nonnull final IPeppolDocumentTypeIdentifierParts aParts,
        @Nonnull @Nonempty final String sProfileCode,
        @Nonnull final Version aSince,
        final boolean bDeprecated,
        @Nullable final Version aDeprecatedSince,
        final boolean bIssuedByOpenPEPPOL,
        final int nBISVersion,
        @Nonnull @Nonempty final String sDomainCommunity,
        @Nonnull @Nonempty final ICommonsList<String> aProcIDs) {
        m_sScheme = sScheme;
        m_aParts = aParts;
        m_sProfileCode = sProfileCode;
        m_sID = m_aParts.getAsDocumentTypeIdentifierValue();
        m_aSince = aSince;
        m_bDeprecated = bDeprecated;
        m_aDeprecatedSince = aDeprecatedSince;
        m_bIssuedByOpenPEPPOL = bIssuedByOpenPEPPOL;
        m_nBISVersion = nBISVersion;
        m_sDomainCommunity = sDomainCommunity;
        m_aProcIDs = new CommonsArrayList<>(aProcIDs, PeppolIdentifierFactory.INSTANCE::parseProcessIdentifier);
    }

    @Nonnull
    @Nonempty
    public String getScheme() {
        return m_sScheme;
    }

    @Nonnull
    @Nonempty
    public String getValue() {
        return m_sID;
    }

    @Nonnull
    @Nonempty
    public String getRootNS() {
        return m_aParts.getRootNS();
    }

    @Nonnull
    @Nonempty
    public String getLocalName() {
        return m_aParts.getLocalName();
    }

    @Nonnull
    @Nonempty
    public String getSubTypeIdentifier() {
        return m_aParts.getSubTypeIdentifier();
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
        return m_sProfileCode;
    }

    @Nonnull
    @Nonempty
    public String getAsDocumentTypeIdentifierValue() {
        return m_sID;
    }

    @Nonnull
    public PeppolDocumentTypeIdentifier getAsDocumentTypeIdentifier() {
        return new PeppolDocumentTypeIdentifier(m_sScheme, m_sID);
    }

    @Nonnull
    public IPeppolDocumentTypeIdentifierParts getParts() {
        return m_aParts;
    }

    @Nonnull
    public Version getSince() {
        return m_aSince;
    }

    public boolean isDeprecated() {
        return m_bDeprecated;
    }

    @Nullable
    public Version getDeprecatedSince() {
        return m_aDeprecatedSince;
    }

    public boolean isIssuedByOpenPEPPOL() {
        return m_bIssuedByOpenPEPPOL;
    }

    @CheckForSigned
    public int getBISVersion() {
        return m_nBISVersion;
    }

    @Nonnull
    @Nonempty
    public String getDomainCommunity() {
        return m_sDomainCommunity;
    }

    @Nonnull
    @Nonempty
    @ReturnsMutableCopy
    public ICommonsList<IProcessIdentifier> getAllProcessIDs() {
        return m_aProcIDs.getClone();
    }

    @Nullable
    public static EPredefinedDocumentTypeIdentifierV6 getFromDocumentTypeIdentifierOrNull(@Nullable final IDocumentTypeIdentifier aDocTypeID) {
        if (aDocTypeID!= null) {
            for (EPredefinedDocumentTypeIdentifierV6 e: EPredefinedDocumentTypeIdentifierV6 .values()) {
                if (e.hasScheme(aDocTypeID.getScheme())&&e.hasValue(aDocTypeID.getValue())) {
                    return e;
                }
            }
        }
        return null;
    }
}