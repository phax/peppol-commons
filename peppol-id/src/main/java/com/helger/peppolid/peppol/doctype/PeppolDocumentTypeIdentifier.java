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
package com.helger.peppolid.peppol.doctype;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.misc.DevelopersNote;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.clone.ICloneable;
import com.helger.base.compare.CompareHelper;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IMutableIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.peppol.IPeppolIdentifier;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.xsds.peppol.id1.DocumentIdentifierType;

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
  @NonNull
  private static String _verifyScheme (@NonNull final IIdentifierFactory aIF, @Nullable final String sScheme)
  {
    if (!aIF.isDocumentTypeIdentifierSchemeValid (sScheme))
      throw new IllegalArgumentException ("Peppol Document Type identifier scheme '" + sScheme + "' is invalid!");
    return sScheme;
  }

  @NonNull
  private static String _verifyValue (@NonNull final IIdentifierFactory aIF,
                                      @Nullable final String sScheme,
                                      @NonNull final String sValue)
  {
    if (!aIF.isDocumentTypeIdentifierValueValid (sScheme, sValue))
      throw new IllegalArgumentException ("Peppol Document Type identifier value '" +
                                          sValue +
                                          "' is invalid for scheme '" +
                                          sScheme +
                                          "'!");
    return sValue;
  }

  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public PeppolDocumentTypeIdentifier (@NonNull final IIdentifierFactory aIF,
                                       @NonNull final IDocumentTypeIdentifier aIdentifier)
  {
    this (aIF, aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public PeppolDocumentTypeIdentifier (@NonNull final IIdentifierFactory aIF,
                                       @Nullable final String sScheme,
                                       @NonNull final String sValue)
  {
    this (true, _verifyScheme (aIF, sScheme), _verifyValue (aIF, sScheme, sValue));
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
                                          @NonNull final String sScheme,
                                          @NonNull final String sValue)
  {
    setScheme (sScheme);
    setValue (sValue);
  }

  public boolean hasDefaultScheme ()
  {
    // Difficult if this should also contain "peppol-doctype-wildcard"
    return hasScheme (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS);
  }

  public int compareTo (@NonNull final PeppolDocumentTypeIdentifier aOther)
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
  @NonNull
  public IPeppolGenericDocumentTypeIdentifierParts getParts ()
  {
    return PeppolGenericDocumentTypeIdentifierParts.extractFromIdentifier (this);
  }

  @Override
  @NonNull
  @ReturnsMutableCopy
  public PeppolDocumentTypeIdentifier getClone ()
  {
    return new PeppolDocumentTypeIdentifier (true, getScheme (), getValue ());
  }

  @Override
  public boolean equals (final Object o)
  {
    // for compareTo
    return super.equals (o);
  }

  @Override
  public int hashCode ()
  {
    // for compareTo
    return super.hashCode ();
  }

  /**
   * Take the passed identifier scheme and value and create a new
   * {@link PeppolDocumentTypeIdentifier}. Internal method.
   *
   * @param sScheme
   *        The identifier scheme. May be <code>null</code> in which case
   *        <code>null</code> is returned.
   * @param sValue
   *        The identifier value. May be <code>null</code> in which case
   *        <code>null</code> is returned.
   * @return The document type identifier or <code>null</code> if any of the
   *         parts is invalid.
   */
  @NonNull
  public static PeppolDocumentTypeIdentifier internalCreatePreVerified (@Nullable final String sScheme,
                                                                        @Nullable final String sValue)
  {
    return new PeppolDocumentTypeIdentifier (true, sScheme, sValue);
  }
}
