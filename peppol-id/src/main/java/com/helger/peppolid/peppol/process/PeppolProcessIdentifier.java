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
package com.helger.peppolid.peppol.process;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.annotation.DevelopersNote;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.compare.CompareHelper;
import com.helger.commons.lang.ICloneable;
import com.helger.peppolid.IMutableIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
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
  @Nonnull
  private static String _verifyScheme (@Nonnull final IIdentifierFactory aIF, @Nullable final String sScheme)
  {
    if (!aIF.isProcessIdentifierSchemeValid (sScheme))
      throw new IllegalArgumentException ("Peppol Process identifier scheme '" + sScheme + "' is invalid!");
    return sScheme;
  }

  @Nonnull
  private static String _verifyValue (@Nonnull final IIdentifierFactory aIF,
                                      @Nullable final String sScheme,
                                      @Nonnull final String sValue)
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
  public PeppolProcessIdentifier (@Nonnull final IIdentifierFactory aIF, @Nonnull final IProcessIdentifier aIdentifier)
  {
    this (aIF, aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public PeppolProcessIdentifier (@Nonnull final IIdentifierFactory aIF,
                                  @Nonnull final String sScheme,
                                  @Nonnull final String sValue)
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
                                     @Nonnull final String sScheme,
                                     @Nonnull final String sValue)
  {
    setScheme (sScheme);
    setValue (sValue);
  }

  public boolean hasDefaultScheme ()
  {
    return hasScheme (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME);
  }

  public int compareTo (@Nonnull final PeppolProcessIdentifier aOther)
  {
    int ret = CompareHelper.compare (getScheme (), aOther.getScheme ());
    if (ret == 0)
      ret = CompareHelper.compare (getValue (), aOther.getValue ());
    return ret;
  }

  @Override
  @Nonnull
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
   * Take the passed identifier scheme and value try to convert it back to a
   * process identifier. If the passed scheme is invalid or if the passed value
   * is invalid, <code>null</code> is returned.
   *
   * @param sScheme
   *        The identifier scheme. May be <code>null</code> in which case
   *        <code>null</code> is returned.
   * @param sValue
   *        The identifier value. May be <code>null</code> in which case
   *        <code>null</code> is returned.
   * @return The process identifier or <code>null</code> if any of the parts is
   *         invalid.
   * @see PeppolIdentifierFactory#isProcessIdentifierSchemeValid(String)
   * @see PeppolIdentifierFactory#isProcessIdentifierValueValid(String, String)
   */
  @Nullable
  @Deprecated (forRemoval = true, since = "9.3.7")
  public static PeppolProcessIdentifier createIfValid (@Nullable final String sScheme, @Nullable final String sValue)
  {
    if (PeppolIdentifierFactory.INSTANCE.isProcessIdentifierSchemeValid (sScheme) &&
        PeppolIdentifierFactory.INSTANCE.isProcessIdentifierValueValid (sScheme, sValue))
      return internalCreatePreVerified (sScheme, sValue);
    return null;
  }

  @Nonnull
  public static PeppolProcessIdentifier internalCreatePreVerified (@Nullable final String sScheme,
                                                                   @Nullable final String sValue)
  {
    return new PeppolProcessIdentifier (true, sScheme, sValue);
  }
}
