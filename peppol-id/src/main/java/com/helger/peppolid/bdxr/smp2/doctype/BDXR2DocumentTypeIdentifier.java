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
package com.helger.peppolid.bdxr.smp2.doctype;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.annotation.DevelopersNote;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.compare.CompareHelper;
import com.helger.commons.lang.ICloneable;
import com.helger.commons.string.StringHelper;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IMutableIdentifier;
import com.helger.xsds.bdxr.smp2.bc.IDType;

/**
 * This is a sanity class around the {@link IDType} class with easier
 * construction and some sanity access methods. It may be used in all places
 * where {@link IDType} objects are required.<br>
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class BDXR2DocumentTypeIdentifier extends IDType implements
                                         IDocumentTypeIdentifier,
                                         IMutableIdentifier,
                                         Comparable <BDXR2DocumentTypeIdentifier>,
                                         ICloneable <BDXR2DocumentTypeIdentifier>
{
  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public BDXR2DocumentTypeIdentifier (@Nonnull final IDocumentTypeIdentifier aIdentifier)
  {
    this (aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public BDXR2DocumentTypeIdentifier (@Nullable final String sScheme, @Nonnull final String sValue)
  {
    // Change "" to null
    setSchemeID (StringHelper.hasNoText (sScheme) ? null : sScheme);
    setValue (sValue);
  }

  @Nonnull
  public final String getScheme ()
  {
    return getSchemeID ();
  }

  public final void setScheme (@Nullable final String sScheme)
  {
    setSchemeID (sScheme);
  }

  public int compareTo (@Nonnull final BDXR2DocumentTypeIdentifier aOther)
  {
    int ret = CompareHelper.compare (getScheme (), aOther.getScheme ());
    if (ret == 0)
      ret = CompareHelper.compare (getValue (), aOther.getValue ());
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public BDXR2DocumentTypeIdentifier getClone ()
  {
    return new BDXR2DocumentTypeIdentifier (this);
  }
}
