/**
 * Copyright (C) 2015-2020 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppolid.peppol.process;

import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.IPeppolIdentifier;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;


/**
 * This file was automatically generated.
 * Do NOT edit!
 */
@CodingStyleguideUnaware
public enum EPredefinedProcessIdentifierV7
    implements IProcessIdentifier, IPeppolIdentifier
{

    /**
     * <code>none</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:www.peppol.eu:schema:xsd:VirtualCompanyDossier-1::VirtualCompanyDossier##urn:www.cenbii.eu:transaction:biicoretrdm991:ver0.1:#urn:www.peppol.eu:bis:peppol991a:ver1.0::0.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:www.peppol.eu:schema:xsd:VirtualCompanyDossierPackage-1::VirtualCompanyDossierPackage##urn:www.cenbii.eu:transaction:biicoretrdm992:ver0.1:#urn:www.peppol.eu:bis:peppol992a:ver1.0::0.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:www.peppol.eu:schema:xsd:CatalogueTemplate-1::CatalogueTemplate##urn:www.cenbii.eu:transaction:biicoretrdm993:ver0.1:#urn:www.peppol.eu:bis:peppol993a:ver1.0::0.1</code><br>
     */
    none("cenbii-procid-ubl", "none", new CommonsArrayList<>("busdox-docid-qns::urn:www.peppol.eu:schema:xsd:VirtualCompanyDossier-1::VirtualCompanyDossier##urn:www.cenbii.eu:transaction:biicoretrdm991:ver0.1:#urn:www.peppol.eu:bis:peppol991a:ver1.0::0.1", "busdox-docid-qns::urn:www.peppol.eu:schema:xsd:VirtualCompanyDossierPackage-1::VirtualCompanyDossierPackage##urn:www.cenbii.eu:transaction:biicoretrdm992:ver0.1:#urn:www.peppol.eu:bis:peppol992a:ver1.0::0.1", "busdox-docid-qns::urn:www.peppol.eu:schema:xsd:CatalogueTemplate-1::CatalogueTemplate##urn:www.cenbii.eu:transaction:biicoretrdm993:ver0.1:#urn:www.peppol.eu:bis:peppol993a:ver1.0::0.1")),

    /**
     * <code>urn:www.cenbii.eu:profile:bii01:ver1.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:www.cenbii.eu:transaction:biicoretrdm019:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0::2.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.cenbii.eu:transaction:biicoretrdm057:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0::2.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.cenbii.eu:transaction:biicoretrdm058:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0::2.0</code><br>
     */
    urn_www_cenbii_eu_profile_bii01_ver1_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii01:ver1.0", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:www.cenbii.eu:transaction:biicoretrdm019:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0::2.0", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.cenbii.eu:transaction:biicoretrdm057:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0::2.0", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.cenbii.eu:transaction:biicoretrdm058:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0::2.0")),

    /**
     * <code>urn:www.cenbii.eu:profile:bii01:ver2.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:www.cenbii.eu:transaction:biicoretrdm019:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0::2.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.cenbii.eu:transaction:biicoretrdm058:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0::2.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:www.cenbii.eu:transaction:biitrns019:ver2.0:extended:urn:www.peppol.eu:bis:peppol1a:ver4.0::2.1</code><br>
     */
    urn_www_cenbii_eu_profile_bii01_ver2_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii01:ver2.0", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:www.cenbii.eu:transaction:biicoretrdm019:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0::2.0", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.cenbii.eu:transaction:biicoretrdm058:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0::2.0", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:www.cenbii.eu:transaction:biitrns019:ver2.0:extended:urn:www.peppol.eu:bis:peppol1a:ver4.0::2.1")),

    /**
     * <code>urn:www.cenbii.eu:profile:bii03:ver1.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0::2.0</code><br>
     */
    urn_www_cenbii_eu_profile_bii03_ver1_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii03:ver1.0", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0::2.0")),

    /**
     * <code>urn:www.cenbii.eu:profile:bii03:ver2.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol03a:ver2.0::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol3a:ver2.0::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol3a:ver2.0:extended:urn:www.simplerinvoicing.org:si:si-ubl:ver1.2::2.1</code><br>
     */
    urn_www_cenbii_eu_profile_bii03_ver2_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii03:ver2.0", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol03a:ver2.0::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol3a:ver2.0::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol3a:ver2.0:extended:urn:www.simplerinvoicing.org:si:si-ubl:ver1.2::2.1")),

    /**
     * <code>urn:www.cenbii.eu:profile:bii04:ver1.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol4a:ver1.0::2.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol4a:ver1.0#urn:www.difi.no:ehf:faktura:ver1::2.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol4a:ver2.0:extended:urn:www.simplerinvoicing.org:si:si-ubl:ver1.2::2.1</code><br>
     */
    urn_www_cenbii_eu_profile_bii04_ver1_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii04:ver1.0", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol4a:ver1.0::2.0", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol4a:ver1.0#urn:www.difi.no:ehf:faktura:ver1::2.0", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol4a:ver2.0:extended:urn:www.simplerinvoicing.org:si:si-ubl:ver1.2::2.1")),

    /**
     * <code>urn:www.cenbii.eu:profile:bii04:ver2.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol4a:ver2.0::2.1</code><br>
     */
    urn_www_cenbii_eu_profile_bii04_ver2_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii04:ver2.0", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol4a:ver2.0::2.1")),

    /**
     * <code>urn:www.cenbii.eu:profile:bii05:ver1.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0::2.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0::2.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm015:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0::2.0</code><br>
     */
    urn_www_cenbii_eu_profile_bii05_ver1_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii05:ver1.0", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0::2.0", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0::2.0", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm015:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0::2.0")),

    /**
     * <code>urn:www.cenbii.eu:profile:bii05:ver2.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol5a:ver2.0::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biitrns014:ver2.0:extended:urn:www.peppol.eu:bis:peppol5a:ver2.0::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.cenbii.eu:profile:biixx:ver1.0#urn:www.difi.no:ehf:kreditnota:ver1::2.0</code><br>
     */
    urn_www_cenbii_eu_profile_bii05_ver2_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii05:ver2.0", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol5a:ver2.0::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biitrns014:ver2.0:extended:urn:www.peppol.eu:bis:peppol5a:ver2.0::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.cenbii.eu:profile:biixx:ver1.0#urn:www.difi.no:ehf:kreditnota:ver1::2.0")),

    /**
     * <code>urn:www.cenbii.eu:profile:bii06:ver1.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:OrderResponseSimple-2::OrderResponseSimple##urn:www.cenbii.eu:transaction:biicoretrdm002:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:OrderResponseSimple-2::OrderResponseSimple##urn:www.cenbii.eu:transaction:biicoretrdm003:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm015:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0</code><br>
     */
    urn_www_cenbii_eu_profile_bii06_ver1_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii06:ver1.0", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:OrderResponseSimple-2::OrderResponseSimple##urn:www.cenbii.eu:transaction:biicoretrdm002:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:OrderResponseSimple-2::OrderResponseSimple##urn:www.cenbii.eu:transaction:biicoretrdm003:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm015:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0::2.0")),

    /**
     * <code>urn:www.cenbii.eu:profile:bii28:ver2.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::Order##urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::OrderResponse##urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0:extended:urn:fdc:peppol-authority.co.uk:spec:ordering:ver1.0::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::Order##urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0:extended:urn:fdc:peppol-authority.co.uk:spec:ordering:ver1.0::2.1</code><br>
     */
    urn_www_cenbii_eu_profile_bii28_ver2_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii28:ver2.0", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::Order##urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::OrderResponse##urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0:extended:urn:fdc:peppol-authority.co.uk:spec:ordering:ver1.0::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::Order##urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0:extended:urn:fdc:peppol-authority.co.uk:spec:ordering:ver1.0::2.1")),

    /**
     * <code>urn:www.cenbii.eu:profile:bii30:ver2.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2::DespatchAdvice##urn:www.cenbii.eu:transaction:biitrns016:ver1.0:extended:urn:www.peppol.eu:bis:peppol30a:ver1.0::2.1</code><br>
     */
    urn_www_cenbii_eu_profile_bii30_ver2_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii30:ver2.0", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2::DespatchAdvice##urn:www.cenbii.eu:transaction:biitrns016:ver1.0:extended:urn:www.peppol.eu:bis:peppol30a:ver1.0::2.1")),

    /**
     * <code>urn:www.cenbii.eu:profile:bii36:ver2.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.cenbii.eu:transaction:biitrns071:ver2.0:extended:urn:www.peppol.eu:bis:peppol36a:ver1.0::2.1</code><br>
     */
    urn_www_cenbii_eu_profile_bii36_ver2_0("cenbii-procid-ubl", "urn:www.cenbii.eu:profile:bii36:ver2.0", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.cenbii.eu:transaction:biitrns071:ver2.0:extended:urn:www.peppol.eu:bis:peppol36a:ver1.0::2.1")),

    /**
     * <code>urn:fdc:peppol.eu:2017:poacc:billing:01:1.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0::D16B</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.1::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.1::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.1::D16B</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#conformant#urn:UBL.BE:1.0.0.20180214::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#conformant#urn:UBL.BE:1.0.0.20180214::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:nen.nl:nlcius:v1.0::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:fdc:nen.nl:nlcius:v1.0::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:sg:3.0::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::CreditNote##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:sg:3.0::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:sg:3.0::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.2::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.2::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.2::D16B</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:aunz:3.0::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:aunz:3.0::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:selfbilling:international:aunz:3.0::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:selfbilling:international:aunz:3.0::2.1</code><br>
     */
    urn_fdc_peppol_eu_2017_poacc_billing_01_1_0("cenbii-procid-ubl", "urn:fdc:peppol.eu:2017:poacc:billing:01:1.0", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0::2.1", "busdox-docid-qns::urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0::D16B", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.1::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.1::2.1", "busdox-docid-qns::urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.1::D16B", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#conformant#urn:UBL.BE:1.0.0.20180214::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#conformant#urn:UBL.BE:1.0.0.20180214::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:nen.nl:nlcius:v1.0::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:fdc:nen.nl:nlcius:v1.0::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:sg:3.0::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::CreditNote##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:sg:3.0::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:sg:3.0::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.2::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.2::2.1", "busdox-docid-qns::urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100::CrossIndustryInvoice##urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.2::D16B", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:aunz:3.0::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:billing:international:aunz:3.0::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:selfbilling:international:aunz:3.0::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#conformant#urn:fdc:peppol.eu:2017:poacc:selfbilling:international:aunz:3.0::2.1")),

    /**
     * <code>urn:fdc:peppol.eu:2017:pracc:p001:01:1.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestRequest-2::ExpressionOfInterestRequest##urn:www.cenbii.eu:transaction:biitrdm081:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t001:ver1.0::2.2</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestResponse-2::ExpressionOfInterestResponse##urn:www.cenbii.eu:transaction:biitrdm082:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t002:ver1.0::2.2</code><br>
     */
    urn_fdc_peppol_eu_2017_pracc_p001_01_1_0("cenbii-procid-ubl", "urn:fdc:peppol.eu:2017:pracc:p001:01:1.0", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestRequest-2::ExpressionOfInterestRequest##urn:www.cenbii.eu:transaction:biitrdm081:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t001:ver1.0::2.2", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ExpressionOfInterestResponse-2::ExpressionOfInterestResponse##urn:www.cenbii.eu:transaction:biitrdm082:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t002:ver1.0::2.2")),

    /**
     * <code>urn:fdc:peppol.eu:2017:pracc:p002:01:1.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:TenderStatusRequest-2::TenderStatusRequest##urn:www.cenbii.eu:transaction:biitrdm097:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t003:ver1.0::2.2</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CallForTenders-2::CallForTenders##urn:www.cenbii.eu:transaction:biitrdm083:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t004:ver1.0::2.2</code><br>
     */
    urn_fdc_peppol_eu_2017_pracc_p002_01_1_0("cenbii-procid-ubl", "urn:fdc:peppol.eu:2017:pracc:p002:01:1.0", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:TenderStatusRequest-2::TenderStatusRequest##urn:www.cenbii.eu:transaction:biitrdm097:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t003:ver1.0::2.2", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CallForTenders-2::CallForTenders##urn:www.cenbii.eu:transaction:biitrdm083:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t004:ver1.0::2.2")),

    /**
     * <code>urn:fdc:peppol.eu:2017:pracc:p003:01:1.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:TenderReceipt-2::TenderReceipt##urn:www.cenbii.eu:transaction:biitrdm045:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t006:ver1.0::2.2</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Tender-2::Tender##urn:www.cenbii.eu:transaction:biitrdm090:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t005:ver1.0::2.2</code><br>
     */
    urn_fdc_peppol_eu_2017_pracc_p003_01_1_0("cenbii-procid-ubl", "urn:fdc:peppol.eu:2017:pracc:p003:01:1.0", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:TenderReceipt-2::TenderReceipt##urn:www.cenbii.eu:transaction:biitrdm045:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t006:ver1.0::2.2", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Tender-2::Tender##urn:www.cenbii.eu:transaction:biitrdm090:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t005:ver1.0::2.2")),

    /**
     * <code>Reference-Utility-1.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oioubl:names:specification:oioubl:schema:xsd:UtilityStatement-2::UtilityStatement##OIOUBL-2.02::2.0</code><br>
     */
    oioubl_procid_ubl_Reference_Utility_1_0("oioubl-procid-ubl", "Reference-Utility-1.0", new CommonsArrayList<>("busdox-docid-qns::urn:oioubl:names:specification:oioubl:schema:xsd:UtilityStatement-2::UtilityStatement##OIOUBL-2.02::2.0")),

    /**
     * <code>Procurement-ReminderOnly-1.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Reminder-2::Reminder##OIOUBL-2.02::2.0</code><br>
     */
    oioubl_procid_ubl_Procurement_ReminderOnly_1_0("oioubl-procid-ubl", "Procurement-ReminderOnly-1.0", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Reminder-2::Reminder##OIOUBL-2.02::2.0")),

    /**
     * <code>urn:www.peppol.eu:profile:bis63a:ver1.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.peppol.eu:transaction:biitrns111:ver1.0::2.1</code><br>
     */
    urn_www_peppol_eu_profile_bis63a_ver1_0("cenbii-procid-ubl", "urn:www.peppol.eu:profile:bis63a:ver1.0", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.peppol.eu:transaction:biitrns111:ver1.0::2.1")),

    /**
     * <code>urn:fdc:peppol.eu:poacc:bis:catalogue_only:3</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:fdc:peppol.eu:poacc:trns:catalogue:3::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:fdc:peppol.eu:poacc:trns:catalogue_response:3::2.1</code><br>
     */
    urn_fdc_peppol_eu_poacc_bis_catalogue_only_3("cenbii-procid-ubl", "urn:fdc:peppol.eu:poacc:bis:catalogue_only:3", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:fdc:peppol.eu:poacc:trns:catalogue:3::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:fdc:peppol.eu:poacc:trns:catalogue_response:3::2.1")),

    /**
     * <code>urn:fdc:peppol.eu:poacc:bis:catalogue_wo_response:3</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:fdc:peppol.eu:poacc:trns:catalogue:3::2.1</code><br>
     */
    urn_fdc_peppol_eu_poacc_bis_catalogue_wo_response_3("cenbii-procid-ubl", "urn:fdc:peppol.eu:poacc:bis:catalogue_wo_response:3", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:fdc:peppol.eu:poacc:trns:catalogue:3::2.1")),

    /**
     * <code>urn:fdc:peppol.eu:poacc:bis:ordering:3</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:fdc:peppol.eu:poacc:trns:order:3::2.1</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::OrderResponse##urn:fdc:peppol.eu:poacc:trns:order_response:3::2.1</code><br>
     */
    urn_fdc_peppol_eu_poacc_bis_ordering_3("cenbii-procid-ubl", "urn:fdc:peppol.eu:poacc:bis:ordering:3", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:fdc:peppol.eu:poacc:trns:order:3::2.1", "busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::OrderResponse##urn:fdc:peppol.eu:poacc:trns:order_response:3::2.1")),

    /**
     * <code>urn:fdc:peppol.eu:poacc:bis:order_only:3</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:fdc:peppol.eu:poacc:trns:order:3::2.1</code><br>
     */
    urn_fdc_peppol_eu_poacc_bis_order_only_3("cenbii-procid-ubl", "urn:fdc:peppol.eu:poacc:bis:order_only:3", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:fdc:peppol.eu:poacc:trns:order:3::2.1")),

    /**
     * <code>urn:fdc:peppol.eu:poacc:bis:invoice_response:3</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:fdc:peppol.eu:poacc:trns:invoice_response:3::2.1</code><br>
     */
    urn_fdc_peppol_eu_poacc_bis_invoice_response_3("cenbii-procid-ubl", "urn:fdc:peppol.eu:poacc:bis:invoice_response:3", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:fdc:peppol.eu:poacc:trns:invoice_response:3::2.1")),

    /**
     * <code>urn:fdc:peppol.eu:poacc:bis:punch_out:3</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:fdc:peppol.eu:poacc:trns:punch_out:3::2.1</code><br>
     */
    urn_fdc_peppol_eu_poacc_bis_punch_out_3("cenbii-procid-ubl", "urn:fdc:peppol.eu:poacc:bis:punch_out:3", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2::Catalogue##urn:fdc:peppol.eu:poacc:trns:punch_out:3::2.1")),

    /**
     * <code>urn:fdc:peppol.eu:poacc:bis:despatch_advice:3</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2::DespatchAdvice##urn:fdc:peppol.eu:poacc:trns:despatch_advice:3::2.1</code><br>
     */
    urn_fdc_peppol_eu_poacc_bis_despatch_advice_3("cenbii-procid-ubl", "urn:fdc:peppol.eu:poacc:bis:despatch_advice:3", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2::DespatchAdvice##urn:fdc:peppol.eu:poacc:trns:despatch_advice:3::2.1")),

    /**
     * <code>urn:fdc:peppol.eu:poacc:bis:order_agreement:3</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::OrderResponse##urn:fdc:peppol.eu:poacc:trns:order_agreement:3::2.1</code><br>
     */
    urn_fdc_peppol_eu_poacc_bis_order_agreement_3("cenbii-procid-ubl", "urn:fdc:peppol.eu:poacc:bis:order_agreement:3", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:OrderResponse-2::OrderResponse##urn:fdc:peppol.eu:poacc:trns:order_agreement:3::2.1")),

    /**
     * <code>urn:fdc:peppol.eu:poacc:bis:mlr:3</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:fdc:peppol.eu:poacc:trns:mlr:3::2.1</code><br>
     */
    urn_fdc_peppol_eu_poacc_bis_mlr_3("cenbii-procid-ubl", "urn:fdc:peppol.eu:poacc:bis:mlr:3", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:fdc:peppol.eu:poacc:trns:mlr:3::2.1")),

    /**
     * <code>urn:fdc:www.efaktura.gov.pl:ver1.0:account_corr:ver1.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:fdc:www.efaktura.gov.pl:ver1.0:trns:account_corr:ver1.0::2.1</code><br>
     */
    urn_fdc_www_efaktura_gov_pl_ver1_0_account_corr_ver1_0("cenbii-procid-ubl", "urn:fdc:www.efaktura.gov.pl:ver1.0:account_corr:ver1.0", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:fdc:www.efaktura.gov.pl:ver1.0:trns:account_corr:ver1.0::2.1")),

    /**
     * <code>urn:fdc:www.efaktura.gov.pl:ver1.0:corr_inv:ver1.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0#extended#urn:fdc:www.efaktura.gov.pl:ver1.0::2.1</code><br>
     */
    urn_fdc_www_efaktura_gov_pl_ver1_0_corr_inv_ver1_0("cenbii-procid-ubl", "urn:fdc:www.efaktura.gov.pl:ver1.0:corr_inv:ver1.0", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0#extended#urn:fdc:www.efaktura.gov.pl:ver1.0::2.1")),

    /**
     * <code>urn:fdc:www.efaktura.gov.pl:ver1.0:receipt_advice:ver1.0</code><br>
     * Use with DocTypeID <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ReceiptAdvice-2::ReceiptAdvice##urn:fdc:www.efaktura.gov.pl:ver1.0:trns:receipt_advice:ver1.0::2.1</code><br>
     */
    urn_fdc_www_efaktura_gov_pl_ver1_0_receipt_advice_ver1_0("cenbii-procid-ubl", "urn:fdc:www.efaktura.gov.pl:ver1.0:receipt_advice:ver1.0", new CommonsArrayList<>("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:ReceiptAdvice-2::ReceiptAdvice##urn:fdc:www.efaktura.gov.pl:ver1.0:trns:receipt_advice:ver1.0::2.1"));
    private final String m_sScheme;
    private final String m_sID;
    private final ICommonsList<IDocumentTypeIdentifier> m_aDocTypeIDs;

    private EPredefinedProcessIdentifierV7(@Nonnull @Nonempty final String sScheme, @Nonnull @Nonempty final String sID, @Nonnull @Nonempty final ICommonsList<String> aDocTypeIDs) {
        m_sScheme = sScheme;
        m_sID = sID;
        m_aDocTypeIDs = new CommonsArrayList<>(aDocTypeIDs, PeppolIdentifierFactory.INSTANCE::parseDocumentTypeIdentifier);
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
    @ReturnsMutableCopy
    public ICommonsList<IDocumentTypeIdentifier> getAllDocTypeIDs() {
        return m_aDocTypeIDs.getClone();
    }

    public boolean hasDefaultScheme() {
        return PeppolIdentifierHelper.DEFAULT_DOCUMENT_TYPE_SCHEME.equals(m_sScheme);
    }

    @Nonnull
    public PeppolProcessIdentifier getAsProcessIdentifier() {
        return new PeppolProcessIdentifier(this);
    }

    @Nullable
    public static EPredefinedProcessIdentifierV7 getFromProcessIdentifierOrNull(@Nullable final IProcessIdentifier aProcessID) {
        if (aProcessID!= null) {
            for (EPredefinedProcessIdentifierV7 e: EPredefinedProcessIdentifierV7 .values()) {
                if (e.hasScheme(aProcessID.getScheme())&&e.hasValue(aProcessID.getValue())) {
                    return e;
                }
            }
        }
        return null;
    }
}
