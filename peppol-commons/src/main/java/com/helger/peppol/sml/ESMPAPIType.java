/**
 * Copyright (C) 2015-2019 Philip Helger and contributors
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.sml;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;

/**
 * Enumerates the different SMP APIs available.
 *
 * @author Philip Helger
 * @since 7.0.0
 */
public enum ESMPAPIType implements IHasID <String>
{
  PEPPOL ("peppol"),
  OASIS_BDXR_V1 ("bdxr1"),
  OASIS_BDXR_V2 ("bdxr2");

  private final String m_sID;

  private ESMPAPIType (@Nonnull @Nonempty final String sID)
  {
    m_sID = sID;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nullable
  public static ESMPAPIType getFromIDOrNull (@Nullable final String sID)
  {
    return getFromIDOrDefault (sID, null);
  }

  @Nullable
  public static ESMPAPIType getFromIDOrDefault (@Nullable final String sID, @Nullable final ESMPAPIType eDefault)
  {
    return EnumHelper.getFromIDOrDefault (ESMPAPIType.class, sID, eDefault);
  }
}
