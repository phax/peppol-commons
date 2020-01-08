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
package com.helger.peppolid.peppol.doctype;

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
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.IPeppolIdentifier;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;

/**
 * A special document type identifier that handles the specialties of PEPPOL
 * (like fixed default scheme) etc.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class PeppolDocumentTypeIdentifier extends DocumentIdentifierType implements
                                          IPeppolIdentifier,
                                          IMutableIdentifier,
                                          IDocumentTypeIdentifier,
                                          Comparable <PeppolDocumentTypeIdentifier>,
                                          ICloneable <PeppolDocumentTypeIdentifier>
{

  @Nonnull
  private static String _verifyScheme (@Nullable final String sScheme)
  {
    if (!PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierSchemeValid (sScheme))
      throw new IllegalArgumentException ("Peppol Document Type identifier scheme '" + sScheme + "' is invalid!");
    return sScheme;
  }

  @Nonnull
  private static String _verifyValue (@Nonnull final String sValue)
  {
    if (!PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (sValue))
      throw new IllegalArgumentException ("Peppol Document Type identifier value '" + sValue + "' is invalid!");
    return sValue;
  }

  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public PeppolDocumentTypeIdentifier (@Nonnull final IDocumentTypeIdentifier aIdentifier)
  {
    this (aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public PeppolDocumentTypeIdentifier (@Nullable final String sScheme, @Nonnull final String sValue)
  {
    this (true, _verifyScheme (sScheme), _verifyValue (sValue));
  }

  /**
   * Private constructor that passed the pre-checked values directly to the
   * super class. Has a dummy parameter for a unique signature.
   *
   * @param bVerified
   *        dummy
   * @param sScheme
   *        Identifier scheme. May not be <code>null</code>.
   * @param sValue
   *        Identifier value. May not be <code>null</code>.
   */
  protected PeppolDocumentTypeIdentifier (final boolean bVerified,
                                          @Nonnull final String sScheme,
                                          @Nonnull final String sValue)
  {
    setScheme (sScheme);
    setValue (sValue);
  }

  public boolean hasDefaultScheme ()
  {
    return hasScheme (PeppolIdentifierHelper.DEFAULT_DOCUMENT_TYPE_SCHEME);
  }

  public int compareTo (@Nonnull final PeppolDocumentTypeIdentifier aOther)
  {
    int ret = CompareHelper.compare (getScheme (), aOther.getScheme ());
    if (ret == 0)
      ret = CompareHelper.compare (getValue (), aOther.getValue ());
    return ret;
  }

  /**
   * Extract the different identifier parts that are contained in a PEPPOL
   * document type identifier.
   *
   * @return A new object encapsulating the different document type identifier
   *         parts.
   */
  @Nonnull
  public IPeppolDocumentTypeIdentifierParts getParts ()
  {
    return PeppolDocumentTypeIdentifierParts.extractFromIdentifier (this);
  }

  @Override
  @Nonnull
  @ReturnsMutableCopy
  public PeppolDocumentTypeIdentifier getClone ()
  {
    return new PeppolDocumentTypeIdentifier (this);
  }

  /**
   * Take the passed identifier scheme and value try to convert it back to a
   * document identifier. If the passed scheme is invalid or if the passed value
   * is invalid, <code>null</code> is returned.
   *
   * @param sScheme
   *        The identifier scheme. May be <code>null</code> in which case
   *        <code>null</code> is returned.
   * @param sValue
   *        The identifier value. May be <code>null</code> in which case
   *        <code>null</code> is returned.
   * @return The document type identifier or <code>null</code> if any of the
   *         parts is invalid.
   * @see PeppolIdentifierFactory#isDocumentTypeIdentifierSchemeValid(String)
   * @see PeppolIdentifierFactory#isDocumentTypeIdentifierValueValid(String)
   */
  @Nullable
  public static PeppolDocumentTypeIdentifier createIfValid (@Nullable final String sScheme,
                                                            @Nullable final String sValue)
  {
    if (PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierSchemeValid (sScheme) &&
        PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (sValue))
      return new PeppolDocumentTypeIdentifier (true, sScheme, sValue);
    return null;
  }
}
