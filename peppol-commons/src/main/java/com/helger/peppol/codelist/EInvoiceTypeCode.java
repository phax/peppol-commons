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
package com.helger.peppol.codelist;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;
import com.helger.commons.name.IHasDisplayName;

/**
 * This file is generated from Genericode file InvoiceTypeCode.gc. Do NOT edit!
 */
public enum EInvoiceTypeCode implements IHasID <String>,IHasDisplayName
{
  COMMERCIAL_INVOICE ("380", "Commercial invoice"),
  FACTORED_INVOICE ("393", "Factored invoice");
  private final String m_sID;
  private final String m_sDisplayName;

  private EInvoiceTypeCode (@Nonnull @Nonempty final String sID, @Nonnull final String sDisplayName)
  {
    m_sID = sID;
    m_sDisplayName = sDisplayName;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nonnull
  public String getDisplayName ()
  {
    return m_sDisplayName;
  }

  @Nullable
  public static EInvoiceTypeCode getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EInvoiceTypeCode.class, sID);
  }

  @Nullable
  public static String getDisplayNameFromIDOrNull (@Nullable final String sID)
  {
    final EInvoiceTypeCode eValue = EInvoiceTypeCode.getFromIDOrNull (sID);
    return ((eValue == null) ? null : eValue.getDisplayName ());
  }
}
