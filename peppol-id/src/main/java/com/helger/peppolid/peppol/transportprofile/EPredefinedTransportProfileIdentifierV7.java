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
package com.helger.peppolid.peppol.transportprofile;

import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.version.Version;
import javax.annotation.Nonnull;


/**
 * This file was automatically generated.
 * Do NOT edit!
 */
@CodingStyleguideUnaware
public enum EPredefinedTransportProfileIdentifierV7
    implements IPredefinedTransportProfileIdentifier
{

    /**
     * <b>This item is deprecated since version 1.0.0 and should not be used to issue new identifiers!</b><br><code>busdox-transport-start</code><br>
     * 
     * @since code list 1.0.0
     */
    @Deprecated
    busdox_transport_start("START", "1.0.1", "busdox-transport-start", Version.parse("1.0.0"), true),

    /**
     * <code>busdox-transport-as2-ver1p0</code><br>
     * 
     * @since code list 1.0.0
     */
    busdox_transport_as2_ver1p0("AS2", "1.0", "busdox-transport-as2-ver1p0", Version.parse("1.0.0"), false),

    /**
     * <b>This item is deprecated since version 3 and should not be used to issue new identifiers!</b><br><code>peppol-transport-as4-v1_0</code><br>
     * 
     * @since code list 2
     */
    @Deprecated
    peppol_transport_as4_v1_0("AS4", "1.0", "peppol-transport-as4-v1_0", Version.parse("2"), true),

    /**
     * <code>peppol-transport-as4-v2_0</code><br>
     * 
     * @since code list 3
     */
    peppol_transport_as4_v2_0("AS4", "2.0", "peppol-transport-as4-v2_0", Version.parse("3"), false),

    /**
     * <code>busdox-transport-as2-ver2p0</code><br>
     * 
     * @since code list 5
     */
    busdox_transport_as2_ver2p0("AS2", "2.0", "busdox-transport-as2-ver2p0", Version.parse("5"), false);
    /**
     * Same as {@link #busdox_transport_start}
     */
    @Deprecated
    public static final EPredefinedTransportProfileIdentifierV7 START_1_0_1 = EPredefinedTransportProfileIdentifierV7 .busdox_transport_start;
    /**
     * Same as {@link #busdox_transport_as2_ver1p0}
     */
    public static final EPredefinedTransportProfileIdentifierV7 AS2_1_0 = EPredefinedTransportProfileIdentifierV7 .busdox_transport_as2_ver1p0;
    /**
     * Same as {@link #peppol_transport_as4_v1_0}
     */
    @Deprecated
    public static final EPredefinedTransportProfileIdentifierV7 AS4_1_0 = EPredefinedTransportProfileIdentifierV7 .peppol_transport_as4_v1_0;
    /**
     * Same as {@link #peppol_transport_as4_v2_0}
     */
    public static final EPredefinedTransportProfileIdentifierV7 AS4_2_0 = EPredefinedTransportProfileIdentifierV7 .peppol_transport_as4_v2_0;
    /**
     * Same as {@link #busdox_transport_as2_ver2p0}
     */
    public static final EPredefinedTransportProfileIdentifierV7 AS2_2_0 = EPredefinedTransportProfileIdentifierV7 .busdox_transport_as2_ver2p0;
    private final String m_sProtocol;
    private final String m_sProfileVersion;
    private final String m_sProfileID;
    private final Version m_aSince;
    private final boolean m_bDeprecated;

    private EPredefinedTransportProfileIdentifierV7(@Nonnull @Nonempty final String sProtocol,
        @Nonnull @Nonempty final String sProfileVersion,
        @Nonnull @Nonempty final String sProfileID,
        @Nonnull final Version aSince,
        final boolean bDeprecated) {
        m_sProtocol = sProtocol;
        m_sProfileVersion = sProfileVersion;
        m_sProfileID = sProfileID;
        m_aSince = aSince;
        m_bDeprecated = bDeprecated;
    }

    @Nonnull
    @Nonempty
    public String getProtocol() {
        return m_sProtocol;
    }

    @Nonnull
    @Nonempty
    public String getProfileVersion() {
        return m_sProfileVersion;
    }

    @Nonnull
    @Nonempty
    public String getProfileID() {
        return m_sProfileID;
    }

    @Nonnull
    public Version getSince() {
        return m_aSince;
    }

    public boolean isDeprecated() {
        return m_bDeprecated;
    }
}
