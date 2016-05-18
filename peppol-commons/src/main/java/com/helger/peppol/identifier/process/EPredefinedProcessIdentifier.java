package com.helger.peppol.identifier.process;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.CollectionHelper;
import com.helger.commons.collection.ext.ICommonsList;
import com.helger.commons.version.Version;
import com.helger.peppol.identifier.CIdentifier;
import com.helger.peppol.identifier.IProcessIdentifier;
import com.helger.peppol.identifier.IdentifierHelper;
import com.helger.peppol.identifier.doctype.EPredefinedDocumentTypeIdentifier;
import com.helger.peppol.identifier.doctype.IPeppolPredefinedDocumentTypeIdentifier;


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
    urn_www_cenbii_eu_profile_bii36_ver2_0("urn:www.cenbii.eu:profile:bii36:ver2.0", "urn:www.peppol.eu:bis:peppol36a:ver1.0", new EPredefinedDocumentTypeIdentifier[] {EPredefinedDocumentTypeIdentifier.APPLICATIONRESPONSE_T071_BIS36A }, Version.parse("1.2.0"));
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii01_ver1_0}
     */
    public final static EPredefinedProcessIdentifier BIS1A = urn_www_cenbii_eu_profile_bii01_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii01_ver2_0}
     */
    public final static EPredefinedProcessIdentifier BIS1A_V40 = urn_www_cenbii_eu_profile_bii01_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii03_ver1_0}
     */
    public final static EPredefinedProcessIdentifier BIS3A = urn_www_cenbii_eu_profile_bii03_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii03_ver2_0}
     */
    public final static EPredefinedProcessIdentifier BIS03A_V20 = urn_www_cenbii_eu_profile_bii03_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii04_ver1_0}
     */
    public final static EPredefinedProcessIdentifier BIS4A = urn_www_cenbii_eu_profile_bii04_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii04_ver2_0}
     */
    public final static EPredefinedProcessIdentifier BIS4A_V20 = urn_www_cenbii_eu_profile_bii04_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii05_ver1_0}
     */
    public final static EPredefinedProcessIdentifier BIS5A = urn_www_cenbii_eu_profile_bii05_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii05_ver2_0}
     */
    public final static EPredefinedProcessIdentifier BIS5A_V20 = urn_www_cenbii_eu_profile_bii05_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii06_ver1_0}
     */
    public final static EPredefinedProcessIdentifier BIS6A = urn_www_cenbii_eu_profile_bii06_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii28_ver2_0}
     */
    public final static EPredefinedProcessIdentifier BIS28A = urn_www_cenbii_eu_profile_bii28_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii30_ver2_0}
     */
    public final static EPredefinedProcessIdentifier BIS30A = urn_www_cenbii_eu_profile_bii30_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii36_ver2_0}
     */
    public final static EPredefinedProcessIdentifier BIS36A = urn_www_cenbii_eu_profile_bii36_ver2_0;
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
        return CIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME;
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
    public SimpleProcessIdentifier getAsProcessIdentifier() {
        return new SimpleProcessIdentifier(this);
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
        return IdentifierHelper.getIdentifierURIEncoded(this);
    }

    @Nonnull
    public String getURIPercentEncoded() {
        return IdentifierHelper.getIdentifierURIPercentEncoded(this);
    }

    @Nullable
    public static EPredefinedProcessIdentifier getFromProcessIdentifierOrNull(
        @Nullable
        final IProcessIdentifier aProcessID) {
        if ((aProcessID!= null)&&CIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME.equals(aProcessID.getScheme())) {
            for (EPredefinedProcessIdentifier e: EPredefinedProcessIdentifier.values()) {
                if (e.getValue().equals(aProcessID.getValue())) {
                    return e;
                }
            }
        }
        return null;
    }
}
