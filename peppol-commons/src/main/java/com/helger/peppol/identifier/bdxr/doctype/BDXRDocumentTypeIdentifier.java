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
package com.helger.peppol.identifier.bdxr.doctype;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.annotation.DevelopersNote;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.compare.CompareHelper;
import com.helger.commons.lang.ICloneable;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.bdxr.DocumentIdentifierType;
import com.helger.peppol.identifier.generic.doctype.IDocumentTypeIdentifier;

/**
 * This is a sanity class around the {@link DocumentIdentifierType} class with
 * easier construction and some sanity access methods. It may be used in all
 * places where {@link DocumentIdentifierType} objects are required.<br>
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class BDXRDocumentTypeIdentifier extends DocumentIdentifierType implements
                                        IBDXRDocumentTypeIdentifier,
                                        Comparable <BDXRDocumentTypeIdentifier>,
                                        ICloneable <BDXRDocumentTypeIdentifier>
{
  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public BDXRDocumentTypeIdentifier (@Nonnull final IDocumentTypeIdentifier aIdentifier)
  {
    this (aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public BDXRDocumentTypeIdentifier (@Nullable final String sScheme, @Nonnull final String sValue)
  {
    // Change "" to null
    setScheme (StringHelper.hasNoText (sScheme) ? null : sScheme);
    setValue (sValue);
  }

  public int compareTo (@Nonnull final BDXRDocumentTypeIdentifier aOther)
  {
    int ret = CompareHelper.compare (getScheme (), aOther.getScheme ());
    if (ret == 0)
      ret = CompareHelper.compare (getValue (), aOther.getValue ());
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public BDXRDocumentTypeIdentifier getClone ()
  {
    return new BDXRDocumentTypeIdentifier (this);
  }

  /**
   * Take the passed identifier scheme and value try to convert it back to a
   * document type identifier. If the passed scheme or value are invalid
   * <code>null</code> is returned.
   *
   * @param sScheme
   *        The identifier scheme. May be <code>null</code> in which case
   *        <code>null</code> is returned.
   * @param sValue
   *        The identifier value. May be <code>null</code> in which case
   *        <code>null</code> is returned.
   * @return The document type identifier or <code>null</code> if any of the
   *         parts is invalid.
   * @see IBDXRDocumentTypeIdentifier#isValidScheme(String)
   * @see IBDXRDocumentTypeIdentifier#isValidValue(String)
   */
  @Nullable
  public static BDXRDocumentTypeIdentifier createIfValid (@Nullable final String sScheme, @Nullable final String sValue)
  {
    if (IBDXRDocumentTypeIdentifier.isValidScheme (sScheme) && IBDXRDocumentTypeIdentifier.isValidValue (sValue))
      return new BDXRDocumentTypeIdentifier (sScheme, sValue);
    return null;
  }
}
