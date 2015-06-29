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

package com.helger.peppol.identifier.doctype;

import java.util.List;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.CollectionHelper;
import com.helger.commons.version.Version;
import com.helger.peppol.identifier.CIdentifier;
import com.helger.peppol.identifier.IdentifierUtils;


/**
 * This file is generated. Do NOT edit!
 * 
 */
@CodingStyleguideUnaware
public enum EPredefinedDocumentTypeIdentifier
    implements IPeppolPredefinedDocumentTypeIdentifier
{

    /**
     * <code>urn:www.peppol.eu:schema:xsd:VirtualCompanyDossier-1::VirtualCompanyDossier##urn:www.cenbii.eu:transaction:biicoretrdm991:ver0.1:#urn:www.peppol.eu:bis:peppol991a:ver1.0::0.1</code>
     * @since code list 1.0.0
     * 
     */
    urn_www_peppol_eu_schema_xsd_VirtualCompanyDossier_1__VirtualCompanyDossier__urn_www_cenbii_eu_transaction_biicoretrdm991_ver0_1__urn_www_peppol_eu_bis_peppol991a_ver1_0__0_1(new PeppolDocumentTypeIdentifierParts("urn:www.peppol.eu:schema:xsd:VirtualCompanyDossier-1", "VirtualCompanyDossier", "urn:www.cenbii.eu:transaction:biicoretrdm991:ver0.1", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol991a:ver1.0"), "0.1"), "Virtual Company Dossier", new Version("1.0.0")),

    /**
     * <code>urn:www.peppol.eu:schema:xsd:VirtualCompanyDossierPackage-1::VirtualCompanyDossierPackage##urn:www.cenbii.eu:transaction:biicoretrdm992:ver0.1:#urn:www.peppol.eu:bis:peppol992a:ver1.0::0.1</code>
     * @since code list 1.0.0
     * 
     */
    urn_www_peppol_eu_schema_xsd_VirtualCompanyDossierPackage_1__VirtualCompanyDossierPackage__urn_www_cenbii_eu_transaction_biicoretrdm992_ver0_1__urn_www_peppol_eu_bis_peppol992a_ver1_0__0_1(new PeppolDocumentTypeIdentifierParts("urn:www.peppol.eu:schema:xsd:VirtualCompanyDossierPackage-1", "VirtualCompanyDossierPackage", "urn:www.cenbii.eu:transaction:biicoretrdm992:ver0.1", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol992a:ver1.0"), "0.1"), "Virtual Company Dossier Package", new Version("1.0.0")),

    /**
     * <code>urn:www.peppol.eu:schema:xsd:CatalogueTemplate-1::CatalogueTemplate##urn:www.cenbii.eu:transaction:biicoretrdm993:ver0.1:#urn:www.peppol.eu:bis:peppol993a:ver1.0::0.1</code>
     * @since code list 1.0.0
     * 
     */
    urn_www_peppol_eu_schema_xsd_CatalogueTemplate_1__CatalogueTemplate__urn_www_cenbii_eu_transaction_biicoretrdm993_ver0_1__urn_www_peppol_eu_bis_peppol993a_ver1_0__0_1(new PeppolDocumentTypeIdentifierParts("urn:www.peppol.eu:schema:xsd:CatalogueTemplate-1", "CatalogueTemplate", "urn:www.cenbii.eu:transaction:biicoretrdm993:ver0.1", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol993a:ver1.0"), "0.1"), "Catalogue Template", new Version("1.0.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:www.cenbii.eu:transaction:biicoretrdm019:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0::2.0</code>
     * @since code list 1.0.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_www_cenbii_eu_transaction_biicoretrdm019_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0(new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2", "Catalogue", "urn:www.cenbii.eu:transaction:biicoretrdm019:ver1.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol1a:ver1.0"), "2.0"), "PEPPOL Catalogue profile V1", new Version("1.0.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.cenbii.eu:transaction:biicoretrdm057:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0::2.0</code>
     * @since code list 1.0.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biicoretrdm057_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0(new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:www.cenbii.eu:transaction:biicoretrdm057:ver1.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol1a:ver1.0"), "2.0"), "PEPPOL Catalogue profile V1", new Version("1.0.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.cenbii.eu:transaction:biicoretrdm058:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0::2.0</code>
     * @since code list 1.0.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biicoretrdm058_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0(new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:www.cenbii.eu:transaction:biicoretrdm058:ver1.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol1a:ver1.0"), "2.0"), "PEPPOL Catalogue profile V1", new Version("1.0.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:www.cenbii.eu:transaction:biitrns019:ver2.0:extended:urn:www.peppol.eu:bis:peppol1a:ver4.0::2.1</code>
     * @since code list 1.2.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_www_cenbii_eu_transaction_biitrns019_ver2_0_extended_urn_www_peppol_eu_bis_peppol1a_ver4_0__2_1(new OpenPeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2", "Catalogue", "urn:www.cenbii.eu:transaction:biitrns019:ver2.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol1a:ver4.0"), "2.1"), "PEPPOL Catalogue profile V4", new Version("1.2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0::2.0</code>
     * @since code list 1.0.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biicoretrdm001_ver1_0__urn_www_peppol_eu_bis_peppol3a_ver1_0__2_0(new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol3a:ver1.0"), "2.0"), "PEPPOL Order profile V1", new Version("1.0.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol03a:ver2.0::2.1</code>
     * @since code list 1.2.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol03a_ver2_0__2_1(new OpenPeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:www.cenbii.eu:transaction:biitrns001:ver2.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol03a:ver2.0"), "2.1"), "PEPPOL Order profile V2", new Version("1.2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol4a:ver1.0::2.0</code>
     * @since code list 1.0.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol4a_ver1_0__2_0(new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol4a:ver1.0"), "2.0"), "PEPPOL Invoice profile V1", new Version("1.0.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol4a:ver2.0::2.1</code>
     * @since code list 1.2.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biitrns010_ver2_0_extended_urn_www_peppol_eu_bis_peppol4a_ver2_0__2_1(new OpenPeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biitrns010:ver2.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol4a:ver2.0"), "2.1"), "PEPPOL Invoice profile V2", new Version("1.2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0::2.0</code>
     * @since code list 1.1.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0(new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol5a:ver1.0"), "2.0"), "PEPPOL Billing profile V1", new Version("1.1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0::2.0</code>
     * @since code list 1.1.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0(new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol5a:ver1.0"), "2.0"), "PEPPOL Billing profile V1", new Version("1.1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm015:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0::2.0</code>
     * @since code list 1.1.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm015_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0(new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biicoretrdm015:ver1.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol5a:ver1.0"), "2.0"), "PEPPOL Billing profile V1", new Version("1.1.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol5a:ver2.0::2.1</code>
     * @since code list 1.2.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biitrns010_ver2_0_extended_urn_www_peppol_eu_bis_peppol5a_ver2_0__2_1(new OpenPeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biitrns010:ver2.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol5a:ver2.0"), "2.1"), "PEPPOL Billing profile V2", new Version("1.2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biitrns014:ver2.0:extended:urn:www.peppol.eu:bis:peppol5a:ver2.0::2.1</code>
     * @since code list 1.2.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biitrns014_ver2_0_extended_urn_www_peppol_eu_bis_peppol5a_ver2_0__2_1(new OpenPeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:www.cenbii.eu:transaction:biitrns014:ver2.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol5a:ver2.0"), "2.1"), "PEPPOL Billing profile V2", new Version("1.2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code>
     * @since code list 1.0.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biicoretrdm001_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0(new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol6a:ver1.0"), "2.0"), "PEPPOL Procurement profile V1", new Version("1.0.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponseSimple-2::OrderResponseSimple##urn:www.cenbii.eu:transaction:biicoretrdm002:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code>
     * @since code list 1.0.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponseSimple_2__OrderResponseSimple__urn_www_cenbii_eu_transaction_biicoretrdm002_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0(new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponseSimple-2", "OrderResponseSimple", "urn:www.cenbii.eu:transaction:biicoretrdm002:ver1.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol6a:ver1.0"), "2.0"), "PEPPOL Procurement profile V1", new Version("1.0.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponseSimple-2::OrderResponseSimple##urn:www.cenbii.eu:transaction:biicoretrdm003:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code>
     * @since code list 1.0.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponseSimple_2__OrderResponseSimple__urn_www_cenbii_eu_transaction_biicoretrdm003_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0(new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponseSimple-2", "OrderResponseSimple", "urn:www.cenbii.eu:transaction:biicoretrdm003:ver1.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol6a:ver1.0"), "2.0"), "PEPPOL Procurement profile V1", new Version("1.0.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code>
     * @since code list 1.0.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0(new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol6a:ver1.0"), "2.0"), "PEPPOL Procurement profile V1", new Version("1.0.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code>
     * @since code list 1.0.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0(new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol6a:ver1.0"), "2.0"), "PEPPOL Procurement profile V1", new Version("1.0.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm015:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code>
     * @since code list 1.0.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm015_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0(new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biicoretrdm015:ver1.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol6a:ver1.0"), "2.0"), "PEPPOL Procurement profile V1", new Version("1.0.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol4a:ver1.0#urn:www.difi.no:ehf:faktura:ver1::2.0</code>
     * @since code list 1.1.1
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol4a_ver1_0_urn_www_difi_no_ehf_faktura_ver1__2_0(new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol4a:ver1.0", "urn:www.difi.no:ehf:faktura:ver1"), "2.0"), "EHF Invoice V1", new Version("1.1.1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.cenbii.eu:profile:biixx:ver1.0#urn:www.difi.no:ehf:kreditnota:ver1::2.0</code>
     * @since code list 1.1.1
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_cenbii_eu_profile_biixx_ver1_0_urn_www_difi_no_ehf_kreditnota_ver1__2_0(new PeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote", "urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0", CollectionHelper.newList("urn:www.cenbii.eu:profile:biixx:ver1.0", "urn:www.difi.no:ehf:kreditnota:ver1"), "2.0"), "Standalone Credit Note according to EHF V1", new Version("1.1.1")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0::2.1</code>
     * @since code list 1.2.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1(new OpenPeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:Order-2", "Order", "urn:www.cenbii.eu:transaction:biitrns001:ver2.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol28a:ver1.0"), "2.1"), "PEPPOL Ordering profile V1", new Version("1.2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::Order##urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0::2.1</code>
     * @since code list 1.2.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__Order__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1(new OpenPeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2", "Order", "urn:www.cenbii.eu:transaction:biitrns076:ver2.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol28a:ver1.0"), "2.1"), "PEPPOL Ordering profile V1", new Version("1.2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2::DespatchAdvice##urn:www.cenbii.eu:transaction:biitrns016:ver1.0:extended:urn:www.peppol.eu:bis:peppol30a:ver1.0::2.1</code>
     * @since code list 1.2.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_www_cenbii_eu_transaction_biitrns016_ver1_0_extended_urn_www_peppol_eu_bis_peppol30a_ver1_0__2_1(new OpenPeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2", "DespatchAdvice", "urn:www.cenbii.eu:transaction:biitrns016:ver1.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol30a:ver1.0"), "2.1"), "PEPPOL Despatch Advice V1", new Version("1.2.0")),

    /**
     * <code>urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.cenbii.eu:transaction:biitrns071:ver2.0:extended:urn:www.peppol.eu:bis:peppol36a:ver1.0::2.1</code>
     * @since code list 1.2.0
     * 
     */
    urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biitrns071_ver2_0_extended_urn_www_peppol_eu_bis_peppol36a_ver1_0__2_1(new OpenPeppolDocumentTypeIdentifierParts("urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2", "ApplicationResponse", "urn:www.cenbii.eu:transaction:biitrns071:ver2.0", CollectionHelper.newList("urn:www.peppol.eu:bis:peppol36a:ver1.0"), "2.1"), "PEPPOL Message Level Response V1", new Version("1.2.0"));
    /**
     * Same as {@link #urn_www_peppol_eu_schema_xsd_VirtualCompanyDossier_1__VirtualCompanyDossier__urn_www_cenbii_eu_transaction_biicoretrdm991_ver0_1__urn_www_peppol_eu_bis_peppol991a_ver1_0__0_1}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier VIRTUALCOMPANYDOSSIER_T991_BIS991A = EPredefinedDocumentTypeIdentifier.urn_www_peppol_eu_schema_xsd_VirtualCompanyDossier_1__VirtualCompanyDossier__urn_www_cenbii_eu_transaction_biicoretrdm991_ver0_1__urn_www_peppol_eu_bis_peppol991a_ver1_0__0_1;
    /**
     * Same as {@link #urn_www_peppol_eu_schema_xsd_VirtualCompanyDossierPackage_1__VirtualCompanyDossierPackage__urn_www_cenbii_eu_transaction_biicoretrdm992_ver0_1__urn_www_peppol_eu_bis_peppol992a_ver1_0__0_1}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier VIRTUALCOMPANYDOSSIERPACKAGE_T992_BIS992A = EPredefinedDocumentTypeIdentifier.urn_www_peppol_eu_schema_xsd_VirtualCompanyDossierPackage_1__VirtualCompanyDossierPackage__urn_www_cenbii_eu_transaction_biicoretrdm992_ver0_1__urn_www_peppol_eu_bis_peppol992a_ver1_0__0_1;
    /**
     * Same as {@link #urn_www_peppol_eu_schema_xsd_CatalogueTemplate_1__CatalogueTemplate__urn_www_cenbii_eu_transaction_biicoretrdm993_ver0_1__urn_www_peppol_eu_bis_peppol993a_ver1_0__0_1}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier CATALOGUETEMPLATE_T993_BIS993A = EPredefinedDocumentTypeIdentifier.urn_www_peppol_eu_schema_xsd_CatalogueTemplate_1__CatalogueTemplate__urn_www_cenbii_eu_transaction_biicoretrdm993_ver0_1__urn_www_peppol_eu_bis_peppol993a_ver1_0__0_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_www_cenbii_eu_transaction_biicoretrdm019_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier CATALOGUE_T019_BIS1A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_www_cenbii_eu_transaction_biicoretrdm019_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biicoretrdm057_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier APPLICATIONRESPONSE_T057_BIS1A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biicoretrdm057_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biicoretrdm058_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier APPLICATIONRESPONSE_T058_BIS1A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biicoretrdm058_ver1_0__urn_www_peppol_eu_bis_peppol1a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_www_cenbii_eu_transaction_biitrns019_ver2_0_extended_urn_www_peppol_eu_bis_peppol1a_ver4_0__2_1}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier CATALOGUE_T019_BIS1A_V40 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Catalogue_2__Catalogue__urn_www_cenbii_eu_transaction_biitrns019_ver2_0_extended_urn_www_peppol_eu_bis_peppol1a_ver4_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biicoretrdm001_ver1_0__urn_www_peppol_eu_bis_peppol3a_ver1_0__2_0}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier ORDER_T001_BIS3A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biicoretrdm001_ver1_0__urn_www_peppol_eu_bis_peppol3a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol03a_ver2_0__2_1}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier ORDER_T001_BIS03A_V20 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol03a_ver2_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol4a_ver1_0__2_0}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier INVOICE_T010_BIS4A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol4a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biitrns010_ver2_0_extended_urn_www_peppol_eu_bis_peppol4a_ver2_0__2_1}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier INVOICE_T010_BIS4A_V20 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biitrns010_ver2_0_extended_urn_www_peppol_eu_bis_peppol4a_ver2_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier INVOICE_T010_BIS5A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier CREDITNOTE_T014_BIS5A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm015_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier INVOICE_T015_BIS5A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm015_ver1_0__urn_www_peppol_eu_bis_peppol5a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biitrns010_ver2_0_extended_urn_www_peppol_eu_bis_peppol5a_ver2_0__2_1}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier INVOICE_T010_BIS5A_V20 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biitrns010_ver2_0_extended_urn_www_peppol_eu_bis_peppol5a_ver2_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biitrns014_ver2_0_extended_urn_www_peppol_eu_bis_peppol5a_ver2_0__2_1}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier CREDITNOTE_T014_BIS5A_V20 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biitrns014_ver2_0_extended_urn_www_peppol_eu_bis_peppol5a_ver2_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biicoretrdm001_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier ORDER_T001_BIS6A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biicoretrdm001_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponseSimple_2__OrderResponseSimple__urn_www_cenbii_eu_transaction_biicoretrdm002_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier ORDERRESPONSESIMPLE_T002_BIS6A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderResponseSimple_2__OrderResponseSimple__urn_www_cenbii_eu_transaction_biicoretrdm002_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponseSimple_2__OrderResponseSimple__urn_www_cenbii_eu_transaction_biicoretrdm003_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier ORDERRESPONSESIMPLE_T003_BIS6A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderResponseSimple_2__OrderResponseSimple__urn_www_cenbii_eu_transaction_biicoretrdm003_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier INVOICE_T010_BIS6A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier CREDITNOTE_T014_BIS6A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm015_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier INVOICE_T015_BIS6A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm015_ver1_0__urn_www_peppol_eu_bis_peppol6a_ver1_0__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol4a_ver1_0_urn_www_difi_no_ehf_faktura_ver1__2_0}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier INVOICE_T010_BIS4A_WWW_DIFI_NO_EHF_FAKTURA_VER1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Invoice_2__Invoice__urn_www_cenbii_eu_transaction_biicoretrdm010_ver1_0__urn_www_peppol_eu_bis_peppol4a_ver1_0_urn_www_difi_no_ehf_faktura_ver1__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_cenbii_eu_profile_biixx_ver1_0_urn_www_difi_no_ehf_kreditnota_ver1__2_0}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier CREDITNOTE_T014_WWW_CENBII_EU_PROFILE_BIIXX_VER1_0_WWW_DIFI_NO_EHF_KREDITNOTA_VER1 = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_CreditNote_2__CreditNote__urn_www_cenbii_eu_transaction_biicoretrdm014_ver1_0__urn_www_cenbii_eu_profile_biixx_ver1_0_urn_www_difi_no_ehf_kreditnota_ver1__2_0;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier ORDER_T001_BIS28A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_Order_2__Order__urn_www_cenbii_eu_transaction_biitrns001_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__Order__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier ORDER_T076_BIS28A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_OrderResponse_2__Order__urn_www_cenbii_eu_transaction_biitrns076_ver2_0_extended_urn_www_peppol_eu_bis_peppol28a_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_www_cenbii_eu_transaction_biitrns016_ver1_0_extended_urn_www_peppol_eu_bis_peppol30a_ver1_0__2_1}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier DESPATCHADVICE_T016_BIS30A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_DespatchAdvice_2__DespatchAdvice__urn_www_cenbii_eu_transaction_biitrns016_ver1_0_extended_urn_www_peppol_eu_bis_peppol30a_ver1_0__2_1;
    /**
     * Same as {@link #urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biitrns071_ver2_0_extended_urn_www_peppol_eu_bis_peppol36a_ver1_0__2_1}
     * 
     */
    public final static EPredefinedDocumentTypeIdentifier APPLICATIONRESPONSE_T071_BIS36A = EPredefinedDocumentTypeIdentifier.urn_oasis_names_specification_ubl_schema_xsd_ApplicationResponse_2__ApplicationResponse__urn_www_cenbii_eu_transaction_biitrns071_ver2_0_extended_urn_www_peppol_eu_bis_peppol36a_ver1_0__2_1;
    private final IPeppolDocumentTypeIdentifierParts m_aParts;
    private final String m_sID;
    private final String m_sCommonName;
    private final Version m_aSince;

    private EPredefinedDocumentTypeIdentifier(
        @Nonnull
        final IPeppolDocumentTypeIdentifierParts aParts,
        @Nonnull
        @Nonempty
        final String sCommonName,
        @Nonnull
        final Version aSince) {
        m_aParts = aParts;
        m_sCommonName = sCommonName;
        m_sID = m_aParts.getAsDocumentTypeIdentifierValue();
        m_aSince = aSince;
    }

    @Nonnull
    @Nonempty
    public String getScheme() {
        return CIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME;
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
    public String getTransactionID() {
        return m_aParts.getTransactionID();
    }

    @Nonnull
    @ReturnsMutableCopy
    public List<String> getExtensionIDs() {
        return m_aParts.getExtensionIDs();
    }

    @Nonnull
    @Nonempty
    public String getAsUBLCustomizationID() {
        return m_aParts.getAsUBLCustomizationID();
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
    public String getAsDocumentTypeIdentifierValue() {
        return m_sID;
    }

    @Nonnull
    public SimpleDocumentTypeIdentifier getAsDocumentTypeIdentifier() {
        return new SimpleDocumentTypeIdentifier(this);
    }

    @Nonnull
    public Version getSince() {
        return m_aSince;
    }

    @Nonnull
    public boolean isDefaultScheme() {
        return true;
    }

    @Nonnull
    public String getURIEncoded() {
        return IdentifierUtils.getIdentifierURIEncoded(this);
    }

    @Nonnull
    public String getURIPercentEncoded() {
        return IdentifierUtils.getIdentifierURIPercentEncoded(this);
    }

    @Nonnull
    public IPeppolDocumentTypeIdentifierParts getParts() {
        return m_aParts;
    }
}
