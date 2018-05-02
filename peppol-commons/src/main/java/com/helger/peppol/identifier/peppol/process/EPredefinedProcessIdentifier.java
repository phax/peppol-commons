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
package com.helger.peppol.identifier.peppol.process;

import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.CollectionHelper;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.version.Version;
import com.helger.peppol.identifier.generic.process.IProcessIdentifier;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;
import com.helger.peppol.identifier.peppol.doctype.EPredefinedDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.doctype.IPeppolPredefinedDocumentTypeIdentifier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;


/**
 * This file is generated. Do NOT edit!
 */
@CodingStyleguideUnaware
public enum EPredefinedProcessIdentifier
    implements IPeppolPredefinedProcessIdentifier
{

    /**
     * urn:www.cenbii.eu:profile:bii01:ver1.0
     * 
     * @since code list 1.0.0
     */
    urn_www_cenbii_eu_profile_bii01_ver1_0("urn:www.cenbii.eu:profile:bii01:ver1.0", "urn:www.peppol.eu:bis:peppol1a:ver1.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.CATALOGUE_T019_BIS1A, EPredefinedDocumentTypeIdentifier.APPLICATIONRESPONSE_T057_BIS1A, EPredefinedDocumentTypeIdentifier.APPLICATIONRESPONSE_T058_BIS1A }, Version.parse("1.0.0")),

    /**
     * urn:www.cenbii.eu:profile:bii01:ver2.0
     * 
     * @since code list 1.2.0
     */
    urn_www_cenbii_eu_profile_bii01_ver2_0("urn:www.cenbii.eu:profile:bii01:ver2.0", "urn:www.peppol.eu:bis:peppol1a:ver4.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.CATALOGUE_T019_BIS1A_V40 }, Version.parse("1.2.0")),

    /**
     * urn:www.cenbii.eu:profile:bii03:ver1.0
     * 
     * @since code list 1.0.0
     */
    urn_www_cenbii_eu_profile_bii03_ver1_0("urn:www.cenbii.eu:profile:bii03:ver1.0", "urn:www.peppol.eu:bis:peppol3a:ver1.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.ORDER_T001_BIS3A }, Version.parse("1.0.0")),

    /**
     * urn:www.cenbii.eu:profile:bii03:ver2.0
     * 
     * @since code list 1.2.0
     */
    urn_www_cenbii_eu_profile_bii03_ver2_0("urn:www.cenbii.eu:profile:bii03:ver2.0", "urn:www.peppol.eu:bis:peppol03a:ver2.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.ORDER_T001_BIS03A_V20 }, Version.parse("1.2.0")),

    /**
     * urn:www.cenbii.eu:profile:bii04:ver1.0
     * 
     * @since code list 1.0.0
     */
    urn_www_cenbii_eu_profile_bii04_ver1_0("urn:www.cenbii.eu:profile:bii04:ver1.0", "urn:www.peppol.eu:bis:peppol4a:ver1.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS4A }, Version.parse("1.0.0")),

    /**
     * urn:www.cenbii.eu:profile:bii04:ver2.0
     * 
     * @since code list 1.2.0
     */
    urn_www_cenbii_eu_profile_bii04_ver2_0("urn:www.cenbii.eu:profile:bii04:ver2.0", "urn:www.peppol.eu:bis:peppol4a:ver2.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS4A_V20 }, Version.parse("1.2.0")),

    /**
     * urn:www.cenbii.eu:profile:bii05:ver1.0
     * 
     * @since code list 1.1.0
     */
    urn_www_cenbii_eu_profile_bii05_ver1_0("urn:www.cenbii.eu:profile:bii05:ver1.0", "urn:www.peppol.eu:bis:peppol5a:ver1.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS5A, EPredefinedDocumentTypeIdentifier.CREDITNOTE_T014_BIS5A, EPredefinedDocumentTypeIdentifier.INVOICE_T015_BIS5A }, Version.parse("1.1.0")),

    /**
     * urn:www.cenbii.eu:profile:bii05:ver2.0
     * 
     * @since code list 1.2.0
     */
    urn_www_cenbii_eu_profile_bii05_ver2_0("urn:www.cenbii.eu:profile:bii05:ver2.0", "urn:www.peppol.eu:bis:peppol5a:ver2.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS5A_V20, EPredefinedDocumentTypeIdentifier.CREDITNOTE_T014_BIS5A_V20 }, Version.parse("1.2.0")),

    /**
     * urn:www.cenbii.eu:profile:bii06:ver1.0
     * 
     * @since code list 1.0.0
     */
    urn_www_cenbii_eu_profile_bii06_ver1_0("urn:www.cenbii.eu:profile:bii06:ver1.0", "urn:www.peppol.eu:bis:peppol6a:ver1.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.ORDER_T001_BIS6A, EPredefinedDocumentTypeIdentifier.ORDERRESPONSESIMPLE_T002_BIS6A, EPredefinedDocumentTypeIdentifier.ORDERRESPONSESIMPLE_T003_BIS6A, EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS6A, EPredefinedDocumentTypeIdentifier.CREDITNOTE_T014_BIS6A, EPredefinedDocumentTypeIdentifier.INVOICE_T015_BIS6A }, Version.parse("1.0.0")),

    /**
     * urn:www.cenbii.eu:profile:bii28:ver2.0
     * 
     * @since code list 1.2.0
     */
    urn_www_cenbii_eu_profile_bii28_ver2_0("urn:www.cenbii.eu:profile:bii28:ver2.0", "urn:www.peppol.eu:bis:peppol28a:ver1.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.ORDER_T001_BIS28A, EPredefinedDocumentTypeIdentifier.ORDER_T076_BIS28A }, Version.parse("1.2.0")),

    /**
     * urn:www.cenbii.eu:profile:bii30:ver2.0
     * 
     * @since code list 1.2.0
     */
    urn_www_cenbii_eu_profile_bii30_ver2_0("urn:www.cenbii.eu:profile:bii30:ver2.0", "urn:www.peppol.eu:bis:peppol30a:ver1.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.DESPATCHADVICE_T016_BIS30A }, Version.parse("1.2.0")),

    /**
     * urn:www.cenbii.eu:profile:bii36:ver2.0
     * 
     * @since code list 1.2.0
     */
    urn_www_cenbii_eu_profile_bii36_ver2_0("urn:www.cenbii.eu:profile:bii36:ver2.0", "urn:www.peppol.eu:bis:peppol36a:ver1.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.APPLICATIONRESPONSE_T071_BIS36A }, Version.parse("1.2.0")),

    /**
     * urn:fdc:peppol.eu:2017:poacc:billing:01:1.0
     * 
     * @since code list 4.0.0
     */
    urn_fdc_peppol_eu_2017_poacc_billing_01_1_0("urn:fdc:peppol.eu:2017:poacc:billing:01:1.0", "urn:www.peppol.eu:bis:peppol5a:ver3.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.INVOICE_EN16931_PEPPOL_V30 }, Version.parse("4.0.0"));
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii01_ver1_0}
     */
    public final static EPredefinedProcessIdentifier BIS1A = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii01_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii01_ver2_0}
     */
    public final static EPredefinedProcessIdentifier BIS1A_V40 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii01_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii03_ver1_0}
     */
    public final static EPredefinedProcessIdentifier BIS3A = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii03_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii03_ver2_0}
     */
    public final static EPredefinedProcessIdentifier BIS03A_V20 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii03_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii04_ver1_0}
     */
    public final static EPredefinedProcessIdentifier BIS4A = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii04_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii04_ver2_0}
     */
    public final static EPredefinedProcessIdentifier BIS4A_V20 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii04_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii05_ver1_0}
     */
    public final static EPredefinedProcessIdentifier BIS5A = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii05_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii05_ver2_0}
     */
    public final static EPredefinedProcessIdentifier BIS5A_V20 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii05_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii06_ver1_0}
     */
    public final static EPredefinedProcessIdentifier BIS6A = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii06_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii28_ver2_0}
     */
    public final static EPredefinedProcessIdentifier BIS28A = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii28_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii30_ver2_0}
     */
    public final static EPredefinedProcessIdentifier BIS30A = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii30_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii36_ver2_0}
     */
    public final static EPredefinedProcessIdentifier BIS36A = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii36_ver2_0;
    /**
     * Same as {@link #urn_fdc_peppol_eu_2017_poacc_billing_01_1_0}
     */
    public final static EPredefinedProcessIdentifier BIS5A_V30 = EPredefinedProcessIdentifier.urn_fdc_peppol_eu_2017_poacc_billing_01_1_0;
    private final String m_sID;
    private final String m_sBISID;
    private final EPredefinedDocumentTypeIdentifier[] m_aDocIDs;
    private final Version m_aSince;

    private EPredefinedProcessIdentifier(
        @Nonnull
        @Nonempty
        final String sID,
        @Nonnull
        @Nonempty
        final String sBISID,
        @Nonnull
        @Nonempty
        final EPredefinedDocumentTypeIdentifier[] aDocIDs,
        @Nonnull
        final Version aSince) {
        m_sID = sID;
        m_sBISID = sBISID;
        m_aDocIDs = aDocIDs;
        m_aSince = aSince;
    }

    @Nonnull
    @Nonempty
    public String getScheme() {
        return PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME;
    }

    @Nonnull
    @Nonempty
    public String getValue() {
        return m_sID;
    }

    @Nonnull
    @Nonempty
    public String getBISID() {
        return m_sBISID;
    }

    @Nonnull
    @ReturnsMutableCopy
    public ICommonsList<? extends IPeppolPredefinedDocumentTypeIdentifier> getDocumentTypeIdentifiers() {
        return CollectionHelper.newList(m_aDocIDs);
    }

    @Nonnull
    public PeppolProcessIdentifier getAsProcessIdentifier() {
        return new PeppolProcessIdentifier(this);
    }

    @Nonnull
    public Version getSince() {
        return m_aSince;
    }

    public boolean isDefaultScheme() {
        return true;
    }

    @Nullable
    public static EPredefinedProcessIdentifier getFromProcessIdentifierOrNull(
        @Nullable
        final IProcessIdentifier aProcessID) {
        if ((aProcessID!= null)&&aProcessID.hasScheme(PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME)) {
            for (EPredefinedProcessIdentifier e: EPredefinedProcessIdentifier.values()) {
                if (e.getValue().equals(aProcessID.getValue())) {
                    return e;
                }
            }
        }
        return null;
    }
}
