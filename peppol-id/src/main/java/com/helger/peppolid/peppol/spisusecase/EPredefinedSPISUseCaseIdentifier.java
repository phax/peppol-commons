/*
 * Copyright (C) 2015-2026 Philip Helger
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
package com.helger.peppolid.peppol.spisusecase;

import java.time.LocalDate;
import com.helger.annotation.Nonempty;
import com.helger.annotation.style.CodingStyleguideUnaware;
import com.helger.base.version.Version;
import com.helger.peppolid.peppol.EPeppolCodeListItemState;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;


/**
 * This file was automatically generated.
 * Do NOT edit!
 */
@CodingStyleguideUnaware
public enum EPredefinedSPISUseCaseIdentifier
    implements IPredefinedSPISUseCaseIdentifier
{

    /**
     * ID: <code>MLS</code><br>
     * 
     * @since code list 9.4
     */
    MLS("MLS", Version.parse("9.4"), EPeppolCodeListItemState.ACTIVE, null, null);
    public static final String CODE_LIST_VERSION = "9.5";
    public static final int CODE_LIST_ENTRY_COUNT = 1;
    private final String m_sUseCaseID;
    private final Version m_aInitialRelease;
    private final EPeppolCodeListItemState m_eState;
    private final Version m_aDeprecationRelease;
    private final LocalDate m_aRemovalDate;

    EPredefinedSPISUseCaseIdentifier(@NonNull @Nonempty final String sProfileID,
        @NonNull final Version aInitialRelease,
        @NonNull final EPeppolCodeListItemState eState,
        @Nullable final Version aDeprecationRelease,
        @Nullable final LocalDate aRemovalDate) {
        m_sUseCaseID = sProfileID;
        m_aInitialRelease = aInitialRelease;
        m_eState = eState;
        m_aDeprecationRelease = aDeprecationRelease;
        m_aRemovalDate = aRemovalDate;
    }

    @NonNull
    @Nonempty
    public String getUseCaseID() {
        return m_sUseCaseID;
    }

    @NonNull
    public Version getInitialRelease() {
        return m_aInitialRelease;
    }

    @NonNull
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
}
