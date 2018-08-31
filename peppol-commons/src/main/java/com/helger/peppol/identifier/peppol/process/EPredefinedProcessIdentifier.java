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
import com.helger.commons.version.Version;
import com.helger.peppol.identifier.generic.process.IProcessIdentifier;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;
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
     * <b>This item is deprecated since version 1.2.1 and should not be used to issue new identifiers!</b><br><code>urn:www.cenbii.eu:profile:bii01:ver1.0</code><br>
     * 
     * @since code list 1.0.0
     */
    @Deprecated
    urn_www_cenbii_eu_profile_bii01_ver1_0("urn:www.cenbii.eu:profile:bii01:ver1.0", "BIS 1A", Version.parse("1.0.0")),

    /**
     * <code>urn:www.cenbii.eu:profile:bii01:ver2.0</code><br>
     * 
     * @since code list 1.2.0
     */
    urn_www_cenbii_eu_profile_bii01_ver2_0("urn:www.cenbii.eu:profile:bii01:ver2.0", "BIS 1A", Version.parse("1.2.0")),

    /**
     * <b>This item is deprecated since version 1.2.1 and should not be used to issue new identifiers!</b><br><code>urn:www.cenbii.eu:profile:bii03:ver1.0</code><br>
     * 
     * @since code list 1.0.0
     */
    @Deprecated
    urn_www_cenbii_eu_profile_bii03_ver1_0("urn:www.cenbii.eu:profile:bii03:ver1.0", "BIS 3A", Version.parse("1.0.0")),

    /**
     * <code>urn:www.cenbii.eu:profile:bii03:ver2.0</code><br>
     * 
     * @since code list 1.2.0
     */
    urn_www_cenbii_eu_profile_bii03_ver2_0("urn:www.cenbii.eu:profile:bii03:ver2.0", "BIS 3A", Version.parse("1.2.0")),

    /**
     * <b>This item is deprecated since version 1.2.1 and should not be used to issue new identifiers!</b><br><code>urn:www.cenbii.eu:profile:bii04:ver1.0</code><br>
     * 
     * @since code list 1.0.0
     */
    @Deprecated
    urn_www_cenbii_eu_profile_bii04_ver1_0("urn:www.cenbii.eu:profile:bii04:ver1.0", "BIS 4A", Version.parse("1.0.0")),

    /**
     * <code>urn:www.cenbii.eu:profile:bii04:ver2.0</code><br>
     * 
     * @since code list 1.2.0
     */
    urn_www_cenbii_eu_profile_bii04_ver2_0("urn:www.cenbii.eu:profile:bii04:ver2.0", "BIS 4A", Version.parse("1.2.0")),

    /**
     * <b>This item is deprecated since version 1.2.1 and should not be used to issue new identifiers!</b><br><code>urn:www.cenbii.eu:profile:bii05:ver1.0</code><br>
     * 
     * @since code list 1.1.0
     */
    @Deprecated
    urn_www_cenbii_eu_profile_bii05_ver1_0("urn:www.cenbii.eu:profile:bii05:ver1.0", "BIS 5A", Version.parse("1.1.0")),

    /**
     * <code>urn:www.cenbii.eu:profile:bii05:ver2.0</code><br>
     * 
     * @since code list 1.2.0
     */
    urn_www_cenbii_eu_profile_bii05_ver2_0("urn:www.cenbii.eu:profile:bii05:ver2.0", "BIS 5A", Version.parse("1.2.0")),

    /**
     * <code>urn:fdc:peppol.eu:2017:poacc:billing:01:1.0</code><br>
     * 
     * @since code list 2
     */
    urn_fdc_peppol_eu_2017_poacc_billing_01_1_0("urn:fdc:peppol.eu:2017:poacc:billing:01:1.0", "BIS 5A", Version.parse("2")),

    /**
     * <b>This item is deprecated since version 1.2.1 and should not be used to issue new identifiers!</b><br><code>urn:www.cenbii.eu:profile:bii06:ver1.0</code><br>
     * 
     * @since code list 1.0.0
     */
    @Deprecated
    urn_www_cenbii_eu_profile_bii06_ver1_0("urn:www.cenbii.eu:profile:bii06:ver1.0", "BIS 6A", Version.parse("1.0.0")),

    /**
     * <code>urn:www.cenbii.eu:profile:bii28:ver2.0</code><br>
     * 
     * @since code list 1.2.0
     */
    urn_www_cenbii_eu_profile_bii28_ver2_0("urn:www.cenbii.eu:profile:bii28:ver2.0", "BIS 28A", Version.parse("1.2.0")),

    /**
     * <code>urn:www.cenbii.eu:profile:bii30:ver2.0</code><br>
     * 
     * @since code list 1.2.0
     */
    urn_www_cenbii_eu_profile_bii30_ver2_0("urn:www.cenbii.eu:profile:bii30:ver2.0", "BIS 30A", Version.parse("1.2.0")),

    /**
     * <code>urn:www.cenbii.eu:profile:bii36:ver2.0</code><br>
     * 
     * @since code list 1.2.0
     */
    urn_www_cenbii_eu_profile_bii36_ver2_0("urn:www.cenbii.eu:profile:bii36:ver2.0", "BIS 36A", Version.parse("1.2.0")),

    /**
     * <code>urn:fdc:peppol.eu:2017:pracc:p001:01:1.0</code><br>
     * 
     * @since code list 3
     */
    urn_fdc_peppol_eu_2017_pracc_p001_01_1_0("urn:fdc:peppol.eu:2017:pracc:p001:01:1.0", "P001", Version.parse("3")),

    /**
     * <code>urn:fdc:peppol.eu:2017:pracc:p002:01:1.0</code><br>
     * 
     * @since code list 3
     */
    urn_fdc_peppol_eu_2017_pracc_p002_01_1_0("urn:fdc:peppol.eu:2017:pracc:p002:01:1.0", "P002", Version.parse("3")),

    /**
     * <code>urn:fdc:peppol.eu:2017:pracc:p003:01:1.0</code><br>
     * 
     * @since code list 3
     */
    urn_fdc_peppol_eu_2017_pracc_p003_01_1_0("urn:fdc:peppol.eu:2017:pracc:p003:01:1.0", "P003", Version.parse("3"));
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii01_ver1_0}
     */
    @Deprecated
    public static final EPredefinedProcessIdentifier BIS1A_V1 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii01_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii01_ver2_0}
     */
    public static final EPredefinedProcessIdentifier BIS1A_V4 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii01_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii03_ver1_0}
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
    @Deprecated
    public static final EPredefinedProcessIdentifier BIS4A_V1 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii04_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii04_ver2_0}
     */
    public static final EPredefinedProcessIdentifier BIS4A_V2 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii04_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii05_ver1_0}
     */
    @Deprecated
    public static final EPredefinedProcessIdentifier BIS5A_V1 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii05_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii05_ver2_0}
     */
    public static final EPredefinedProcessIdentifier BIS5A_V2 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii05_ver2_0;
    /**
     * Same as {@link #urn_fdc_peppol_eu_2017_poacc_billing_01_1_0}
     */
    public static final EPredefinedProcessIdentifier BIS5A_V3 = EPredefinedProcessIdentifier.urn_fdc_peppol_eu_2017_poacc_billing_01_1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii06_ver1_0}
     */
    @Deprecated
    public static final EPredefinedProcessIdentifier BIS6A_V1 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii06_ver1_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii28_ver2_0}
     */
    public static final EPredefinedProcessIdentifier BIS28A_V1 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii28_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii30_ver2_0}
     */
    public static final EPredefinedProcessIdentifier BIS30A_V1 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii30_ver2_0;
    /**
     * Same as {@link #urn_www_cenbii_eu_profile_bii36_ver2_0}
     */
    public static final EPredefinedProcessIdentifier BIS36A_V1 = EPredefinedProcessIdentifier.urn_www_cenbii_eu_profile_bii36_ver2_0;
    /**
     * Same as {@link #urn_fdc_peppol_eu_2017_pracc_p001_01_1_0}
     */
    public static final EPredefinedProcessIdentifier P001_V1 = EPredefinedProcessIdentifier.urn_fdc_peppol_eu_2017_pracc_p001_01_1_0;
    /**
     * Same as {@link #urn_fdc_peppol_eu_2017_pracc_p002_01_1_0}
     */
    public static final EPredefinedProcessIdentifier P002_V1 = EPredefinedProcessIdentifier.urn_fdc_peppol_eu_2017_pracc_p002_01_1_0;
    /**
     * Same as {@link #urn_fdc_peppol_eu_2017_pracc_p003_01_1_0}
     */
    public static final EPredefinedProcessIdentifier P003_V1 = EPredefinedProcessIdentifier.urn_fdc_peppol_eu_2017_pracc_p003_01_1_0;
    private final String m_sID;
    private final String m_sBISID;
    private final Version m_aSince;

    private EPredefinedProcessIdentifier(
        @Nonnull
        @Nonempty
        final String sID,
        @Nonnull
        @Nonempty
        final String sBISID,
        @Nonnull
        final Version aSince) {
        m_sID = sID;
        m_sBISID = sBISID;
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
