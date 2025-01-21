/*
 * Copyright (C) 2015-2025 Philip Helger
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
package com.helger.peppol.servicedomain;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;
import com.helger.commons.name.IHasDisplayName;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.sml.ISMLInfo;

/**
 * This enum lists all the Peppol Network stages
 *
 * @author Philip Helger
 * @since 9.6.0
 */
public enum EPeppolNetwork implements IHasID <String>, IHasDisplayName
{
  /**
   * Peppol Test Network
   */
  TEST ("test", "Peppol Test Network", "https://test-directory.peppol.eu", ESML.DIGIT_TEST),
  /**
   * Peppol Production Network
   */
  PRODUCTION ("prod", "Peppol Production Network", "https://directory.peppol.eu", ESML.DIGIT_PRODUCTION);

  private final String m_sID;
  private final String m_sDisplayName;
  private final String m_sDirectoryURL;
  private final ISMLInfo m_aSMLInfo;

  EPeppolNetwork (@Nonnull @Nonempty final String sID,
                  @Nonnull @Nonempty final String sDisplayName,
                  @Nonnull @Nonempty final String sDirectoryURL,
                  @Nonnull final ISMLInfo aSMLInfo)
  {
    m_sID = sID;
    m_sDisplayName = sDisplayName;
    m_sDirectoryURL = sDirectoryURL;
    m_aSMLInfo = aSMLInfo;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nonnull
  @Nonempty
  public String getDisplayName ()
  {
    return m_sDisplayName;
  }

  /**
   * @return The URL of the Peppol Directory for this network stage. Ends with
   *         the domain name and without a trailing slash. Never
   *         <code>null</code>.
   */
  @Nonnull
  @Nonempty
  public String getDirectoryURL ()
  {
    return m_sDirectoryURL;
  }

  /**
   * @return The SML object to be used for this network stage. Never
   *         <code>null</code>.
   */
  @Nonnull
  public ISMLInfo getSMLInfo ()
  {
    return m_aSMLInfo;
  }

  public boolean isProduction ()
  {
    return this == PRODUCTION;
  }

  public boolean isTest ()
  {
    return this == TEST;
  }

  @Nullable
  public static EPeppolNetwork getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EPeppolNetwork.class, sID);
  }
}
