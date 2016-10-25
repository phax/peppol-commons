/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.identifier.peppol.process;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.annotation.DevelopersNote;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.ext.ICommonsList;
import com.helger.commons.lang.ICloneable;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.CIdentifier;
import com.helger.peppol.identifier.IdentifierHelper;
import com.helger.peppol.identifier.ProcessIdentifierType;
import com.helger.peppol.identifier.generic.process.IProcessIdentifier;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;

/**
 * This is a sanity class around the {@link ProcessIdentifierType} class with
 * easier construction and some sanity access methods. It may be used in all
 * places where {@link ProcessIdentifierType} objects are required.<br>
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class PeppolProcessIdentifier extends ProcessIdentifierType implements
                                     IMutablePeppolProcessIdentifier,
                                     Comparable <PeppolProcessIdentifier>,
                                     ICloneable <PeppolProcessIdentifier>
{
  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public PeppolProcessIdentifier (@Nonnull final IProcessIdentifier aIdentifier)
  {
    this (aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  @Nonnull
  private static String _verifyScheme (@Nullable final String sScheme)
  {
    if (!IPeppolProcessIdentifier.isValidScheme (sScheme))
      throw new IllegalArgumentException ("Peppol Process identifier scheme '" + sScheme + "' is invalid!");
    return sScheme;
  }

  @Nonnull
  private static String _verifyValue (@Nonnull final String sValue)
  {
    if (!IPeppolProcessIdentifier.isValidValue (sValue))
      throw new IllegalArgumentException ("Peppol Process identifier value '" + sValue + "' is invalid!");
    return sValue;
  }

  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public PeppolProcessIdentifier (@Nonnull final String sScheme, @Nonnull final String sValue)
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
  protected PeppolProcessIdentifier (final boolean bVerified,
                                     @Nonnull final String sScheme,
                                     @Nonnull final String sValue)
  {
    setScheme (sScheme);
    setValue (sValue);
  }

  public int compareTo (@Nonnull final PeppolProcessIdentifier aOther)
  {
    return IdentifierHelper.compareProcessIdentifiers (this, aOther);
  }

  @Override
  @Nonnull
  @ReturnsMutableCopy
  public PeppolProcessIdentifier getClone ()
  {
    return new PeppolProcessIdentifier (this);
  }

  /**
   * Create a new process identifier that uses the default schema
   * {@link PeppolIdentifierHelper#DEFAULT_PROCESS_SCHEME}
   *
   * @param sValue
   *        The identifier value like
   *        <code>urn:www.cenbii.eu:profile:bii01:ver1.0</code>
   * @return The created {@link PeppolProcessIdentifier} and never
   *         <code>null</code>.
   */
  @Nonnull
  @Deprecated
  public static PeppolProcessIdentifier createWithDefaultScheme (@Nonnull final String sValue)
  {
    return new PeppolProcessIdentifier (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME, sValue);
  }

  /**
   * Create a new process identifier from the URI representation. This is the
   * inverse operation of {@link #getURIEncoded()}.
   *
   * @param sURIPart
   *        The URI part in the format <code>scheme::value</code> (e.g.
   *        <code>cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii01:ver1.0</code>
   *        ). It must NOT be percent encoded!
   * @return The created {@link PeppolProcessIdentifier} and never
   *         <code>null</code>.
   * @throws IllegalArgumentException
   *         If the passed identifier is not a valid URI encoded identifier
   */
  @Nonnull
  @Deprecated
  public static PeppolProcessIdentifier createFromURIPart (@Nonnull final String sURIPart) throws IllegalArgumentException
  {
    final PeppolProcessIdentifier ret = createFromURIPartOrNull (sURIPart);
    if (ret == null)
      throw new IllegalArgumentException ("Peppol Process identifier '" +
                                          sURIPart +
                                          "' did not include correct delimiter: " +
                                          CIdentifier.URL_SCHEME_VALUE_SEPARATOR);

    return ret;
  }

  /**
   * Create a new process identifier from the URI representation. This is the
   * inverse operation of {@link #getURIEncoded()}.
   *
   * @param sURIPart
   *        The URI part in the format <code>scheme::value</code> (e.g.
   *        <code>cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii01:ver1.0</code>
   *        ). It must NOT be percent encoded! May be <code>null</code>.
   * @return The created {@link PeppolProcessIdentifier} or <code>null</code> if
   *         the passed identifier is not a valid URI encoded identifier
   */
  @Nullable
  @Deprecated
  public static PeppolProcessIdentifier createFromURIPartOrNull (@Nullable final String sURIPart)
  {
    if (sURIPart == null)
      return null;

    // This is quicker than splitting with RegEx!
    final ICommonsList <String> aSplitted = StringHelper.getExploded (CIdentifier.URL_SCHEME_VALUE_SEPARATOR,
                                                                      sURIPart,
                                                                      2);
    if (aSplitted.size () != 2)
      return null;

    return createIfValid (aSplitted.get (0), aSplitted.get (1));
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
   * @see IPeppolProcessIdentifier#isValidScheme(String)
   * @see IPeppolProcessIdentifier#isValidValue(String)
   */
  @Nullable
  public static PeppolProcessIdentifier createIfValid (@Nullable final String sScheme, @Nullable final String sValue)
  {
    if (IPeppolProcessIdentifier.isValidScheme (sScheme) && IPeppolProcessIdentifier.isValidValue (sValue))
      return new PeppolProcessIdentifier (true, sScheme, sValue);
    return null;
  }

  /**
   * Check if the passed process identifier is valid. This method checks for the
   * existence of the scheme and the value and validates both.
   *
   * @param sURIPart
   *        The process identifier to be checked (including the scheme). May be
   *        <code>null</code>.
   * @return <code>true</code> if the process identifier is valid,
   *         <code>false</code> otherwise
   */
  @Deprecated
  public static boolean isValidURIPart (@Nullable final String sURIPart)
  {
    return createFromURIPartOrNull (sURIPart) != null;
  }
}
