/**
 * Copyright (C) 2015-2019 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppolid.simple.doctype;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.annotation.DevelopersNote;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.compare.CompareHelper;
import com.helger.commons.lang.ICloneable;
import com.helger.peppolid.DocumentIdentifierType;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IMutableIdentifier;

/**
 * This is a sanity class around the {@link DocumentIdentifierType} class with
 * easier construction and some sanity access methods. It may be used in all
 * places where {@link DocumentIdentifierType} objects are required.<br>
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@NotThreadSafe
public class SimpleDocumentTypeIdentifier extends DocumentIdentifierType implements
                                          IDocumentTypeIdentifier,
                                          IMutableIdentifier,
                                          Comparable <SimpleDocumentTypeIdentifier>,
                                          ICloneable <SimpleDocumentTypeIdentifier>
{
  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public SimpleDocumentTypeIdentifier (@Nonnull final IDocumentTypeIdentifier aIdentifier)
  {
    this (aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public SimpleDocumentTypeIdentifier (@Nullable final String sScheme, @Nonnull final String sValue)
  {
    setScheme (sScheme);
    setValue (sValue);
  }

  public int compareTo (@Nonnull final SimpleDocumentTypeIdentifier aOther)
  {
    int ret = CompareHelper.compare (getScheme (), aOther.getScheme ());
    if (ret == 0)
      ret = CompareHelper.compare (getValue (), aOther.getValue ());
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public SimpleDocumentTypeIdentifier getClone ()
  {
    return new SimpleDocumentTypeIdentifier (this);
  }

  @Nonnull
  public static SimpleDocumentTypeIdentifier wrap (@Nonnull final com.helger.peppolid.DocumentIdentifierType aID)
  {
    return new SimpleDocumentTypeIdentifier (aID.getScheme (), aID.getValue ());
  }

  @Nonnull
  public static SimpleDocumentTypeIdentifier wrap (@Nonnull final com.helger.xsds.bdxr.smp1.DocumentIdentifierType aID)
  {
    return new SimpleDocumentTypeIdentifier (aID.getScheme (), aID.getValue ());
  }

  @Nonnull
  public static SimpleDocumentTypeIdentifier wrap (@Nonnull final com.helger.xsds.ccts.cct.schemamodule.IdentifierType aID)
  {
    return new SimpleDocumentTypeIdentifier (aID.getSchemeID (), aID.getValue ());
  }
}
