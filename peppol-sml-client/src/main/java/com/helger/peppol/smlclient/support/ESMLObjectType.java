/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.smlclient.support;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.lang.EnumHelper;
import com.helger.commons.name.IHasName;

/**
 * Enumeration with all object types relevant to the SML.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public enum ESMLObjectType implements IHasName
{
  PARTICIPANT ("participant"),
  METADATA ("metadata");

  private final String m_sName;

  private ESMLObjectType (@Nonnull @Nonempty final String sName)
  {
    m_sName = sName;
  }

  @Nonnull
  @Nonempty
  public String getName ()
  {
    return m_sName;
  }

  @Nullable
  public static ESMLObjectType getFromNameOrNull (@Nullable final String sName)
  {
    return EnumHelper.getFromNameCaseInsensitiveOrNull (ESMLObjectType.class, sName);
  }
}
