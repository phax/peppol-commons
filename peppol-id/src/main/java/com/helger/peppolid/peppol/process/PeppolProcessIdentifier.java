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
package com.helger.peppolid.peppol.process;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.misc.DevelopersNote;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.clone.ICloneable;
import com.helger.base.compare.CompareHelper;
import com.helger.peppolid.IMutableIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.peppol.IPeppolIdentifier;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.xsds.peppol.id1.ProcessIdentifierType;

/**
 * This is a sanity class around the {@link ProcessIdentifierType} class with
 * easier construction and some sanity access methods. It may be used in all
 * places where {@link ProcessIdentifierType} objects are required.<br>
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class PeppolProcessIdentifier extends ProcessIdentifierType implements
                                     IPeppolIdentifier,
                                     IProcessIdentifier,
                                     IMutableIdentifier,
                                     Comparable <PeppolProcessIdentifier>,
                                     ICloneable <PeppolProcessIdentifier>
{
  @NonNull
  private static String _verifyScheme (@NonNull final IIdentifierFactory aIF, @Nullable final String sScheme)
  {
    if (!aIF.isProcessIdentifierSchemeValid (sScheme))
      throw new IllegalArgumentException ("Peppol Process identifier scheme '" + sScheme + "' is invalid!");
    return sScheme;
  }

  @NonNull
  private static String _verifyValue (@NonNull final IIdentifierFactory aIF,
                                      @Nullable final String sScheme,
                                      @NonNull final String sValue)
  {
    if (!aIF.isProcessIdentifierValueValid (sScheme, sValue))
      throw new IllegalArgumentException ("Peppol Process identifier value '" +
                                          sValue +
                                          "' is invalid for scheme '" +
                                          sScheme +
                                          "!");
    return sValue;
  }

  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public PeppolProcessIdentifier (@NonNull final IIdentifierFactory aIF, @NonNull final IProcessIdentifier aIdentifier)
  {
    this (aIF, aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public PeppolProcessIdentifier (@NonNull final IIdentifierFactory aIF,
                                  @NonNull final String sScheme,
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
  protected PeppolProcessIdentifier (final boolean bVerified,
                                     @NonNull final String sScheme,
                                     @NonNull final String sValue)
  {
    setScheme (sScheme);
    setValue (sValue);
  }

  public boolean hasDefaultScheme ()
  {
    return hasScheme (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME);
  }

  public int compareTo (@NonNull final PeppolProcessIdentifier aOther)
  {
    int ret = CompareHelper.compare (getScheme (), aOther.getScheme ());
    if (ret == 0)
      ret = CompareHelper.compare (getValue (), aOther.getValue ());
    return ret;
  }

  @Override
  @NonNull
  @ReturnsMutableCopy
  public PeppolProcessIdentifier getClone ()
  {
    return new PeppolProcessIdentifier (true, getScheme (), getValue ());
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
   * {@link PeppolProcessIdentifier}. This method is for internal use only.
   *
   * @param sScheme
   *        The identifier scheme. May be <code>null</code> in which case
   *        <code>null</code> is returned.
   * @param sValue
   *        The identifier value. May be <code>null</code> in which case
   *        <code>null</code> is returned.
   * @return The process identifier or <code>null</code> if any of the parts is
   *         invalid.
   */
  @NonNull
  public static PeppolProcessIdentifier internalCreatePreVerified (@Nullable final String sScheme,
                                                                   @Nullable final String sValue)
  {
    return new PeppolProcessIdentifier (true, sScheme, sValue);
  }
}
