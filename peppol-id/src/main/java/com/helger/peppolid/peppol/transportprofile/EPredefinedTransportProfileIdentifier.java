package com.helger.peppolid.peppol.transportprofile;

import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.version.Version;
import com.helger.peppolid.peppol.EPeppolCodeListItemState;
import javax.annotation.Nonnull;


/**
 * This file was automatically generated.
 * Do NOT edit!
 */
@CodingStyleguideUnaware
public enum EPredefinedTransportProfileIdentifier
    implements IPredefinedTransportProfileIdentifier
{

    /**
     * ID: <code>busdox-transport-start</code><br>
     * Same as {@link #START_1_0_1}
     * 
     * @since code list 1.0.0
     * @deprecated since 1.0.0 - this item should not be used to issue new identifiers!
     */
    @Deprecated
    busdox_transport_start("START", "1.0.1", "busdox-transport-start", Version.parse("1.0.0"), EPeppolCodeListItemState.DEPRECATED),

    /**
     * ID: <code>busdox-transport-as2-ver1p0</code><br>
     * Same as {@link #AS2_1_0}
     * 
     * @since code list 1.0.0
     * @deprecated since 7 - this item should not be used to issue new identifiers!
     */
    @Deprecated
    busdox_transport_as2_ver1p0("AS2", "1.0", "busdox-transport-as2-ver1p0", Version.parse("1.0.0"), EPeppolCodeListItemState.DEPRECATED),

    /**
     * ID: <code>peppol-transport-as4-v1_0</code><br>
     * Same as {@link #AS4_1_0}
     * 
     * @since code list 2
     * @deprecated since 3 - this item should not be used to issue new identifiers!
     */
    @Deprecated
    peppol_transport_as4_v1_0("AS4", "1.0", "peppol-transport-as4-v1_0", Version.parse("2"), EPeppolCodeListItemState.DEPRECATED),

    /**
     * ID: <code>peppol-transport-as4-v2_0</code><br>
     * Same as {@link #AS4_2_0}
     * 
     * @since code list 3
     */
    peppol_transport_as4_v2_0("AS4", "2.0", "peppol-transport-as4-v2_0", Version.parse("3"), EPeppolCodeListItemState.ACTIVE),

    /**
     * ID: <code>busdox-transport-as2-ver2p0</code><br>
     * Same as {@link #AS2_2_0}
     * 
     * @since code list 5
     */
    busdox_transport_as2_ver2p0("AS2", "2.0", "busdox-transport-as2-ver2p0", Version.parse("5"), EPeppolCodeListItemState.ACTIVE);
    public static final String CODE_LIST_VERSION = "8";
    public static final int CODE_LIST_ENTRY_COUNT = 5;
    /**
     * Same as {@link #busdox_transport_start}
     * 
     * @deprecated since 1.0.0 - this item should not be used to issue new identifiers!
     */
    @Deprecated
    public static final EPredefinedTransportProfileIdentifier START_1_0_1 = EPredefinedTransportProfileIdentifier.busdox_transport_start;
    /**
     * Same as {@link #busdox_transport_as2_ver1p0}
     * 
     * @deprecated since 7 - this item should not be used to issue new identifiers!
     */
    @Deprecated
    public static final EPredefinedTransportProfileIdentifier AS2_1_0 = EPredefinedTransportProfileIdentifier.busdox_transport_as2_ver1p0;
    /**
     * Same as {@link #peppol_transport_as4_v1_0}
     * 
     * @deprecated since 3 - this item should not be used to issue new identifiers!
     */
    @Deprecated
    public static final EPredefinedTransportProfileIdentifier AS4_1_0 = EPredefinedTransportProfileIdentifier.peppol_transport_as4_v1_0;
    /**
     * Same as {@link #peppol_transport_as4_v2_0}
     */
    public static final EPredefinedTransportProfileIdentifier AS4_2_0 = EPredefinedTransportProfileIdentifier.peppol_transport_as4_v2_0;
    /**
     * Same as {@link #busdox_transport_as2_ver2p0}
     */
    public static final EPredefinedTransportProfileIdentifier AS2_2_0 = EPredefinedTransportProfileIdentifier.busdox_transport_as2_ver2p0;
    private final String m_sProtocol;
    private final String m_sProfileVersion;
    private final String m_sProfileID;
    private final Version m_aInitialRelease;
    private final EPeppolCodeListItemState m_eState;

    EPredefinedTransportProfileIdentifier(@Nonnull @Nonempty final String sProtocol,
        @Nonnull @Nonempty final String sProfileVersion,
        @Nonnull @Nonempty final String sProfileID,
        @Nonnull final Version aInitialRelease,
        @Nonnull final EPeppolCodeListItemState eState) {
        m_sProtocol = sProtocol;
        m_sProfileVersion = sProfileVersion;
        m_sProfileID = sProfileID;
        m_aInitialRelease = aInitialRelease;
        m_eState = eState;
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
    @Deprecated
    public Version getSince() {
        return m_aInitialRelease;
    }

    @Nonnull
    public Version getInitialRelease() {
        return m_aInitialRelease;
    }

    public boolean isDeprecated() {
        return m_eState.isDeprecated();
    }

    @Nonnull
    public EPeppolCodeListItemState getState() {
        return m_eState;
    }
}
