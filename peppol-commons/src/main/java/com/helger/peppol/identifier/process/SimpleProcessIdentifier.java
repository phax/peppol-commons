/**
 * Copyright (C) 2015 Philip Helger (www.helger.com)
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
package com.helger.peppol.identifier.process;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.lang.ICloneable;
import com.helger.peppol.identifier.CIdentifier;
import com.helger.peppol.identifier.IProcessIdentifier;
import com.helger.peppol.identifier.IdentifierHelper;
import com.helger.peppol.identifier.ProcessIdentifierType;

/**
 * This is a sanity class around the {@link ProcessIdentifierType} class with
 * easier construction and some sanity access methods. It may be used in all
 * places where {@link ProcessIdentifierType} objects are required.<br>
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@NotThreadSafe
public class SimpleProcessIdentifier extends ProcessIdentifierType implements IMutablePeppolProcessIdentifier, Comparable <SimpleProcessIdentifier>, ICloneable <SimpleProcessIdentifier>
{
  public SimpleProcessIdentifier (@Nonnull final IProcessIdentifier aIdentifier)
  {
    this (aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  public SimpleProcessIdentifier (@Nonnull final String sScheme, @Nonnull final String sValue)
  {
    if (!IdentifierHelper.isValidIdentifierScheme (sScheme))
      throw new IllegalArgumentException ("Process identifier scheme '" + sScheme + "' is invalid!");
    if (!IdentifierHelper.isValidProcessIdentifierValue (sValue))
      throw new IllegalArgumentException ("Process identifier value '" + sValue + "' is invalid!");
    setScheme (sScheme);
    setValue (sValue);
  }

  public boolean isDefaultScheme ()
  {
    return IdentifierHelper.hasDefaultProcessIdentifierScheme (this);
  }

  @Nonnull
  public String getURIEncoded ()
  {
    return IdentifierHelper.getIdentifierURIEncoded (this);
  }

  @Nonnull
  public String getURIPercentEncoded ()
  {
    return IdentifierHelper.getIdentifierURIPercentEncoded (this);
  }

  public int compareTo (@Nonnull final SimpleProcessIdentifier aOther)
  {
    return IdentifierHelper.compareProcessIdentifiers (this, aOther);
  }

  @Nonnull
  @ReturnsMutableCopy
  public SimpleProcessIdentifier getClone ()
  {
    return new SimpleProcessIdentifier (this);
  }

  /**
   * Create a new process identifier that uses the default schema
   * {@link CIdentifier#DEFAULT_PROCESS_IDENTIFIER_SCHEME}
   *
   * @param sValue
   *        The identifier value like
   *        <code>urn:www.cenbii.eu:profile:bii01:ver1.0</code>
   * @return The created {@link SimpleProcessIdentifier} and never
   *         <code>null</code>.
   */
  @Nonnull
  public static SimpleProcessIdentifier createWithDefaultScheme (@Nonnull final String sValue)
  {
    return new SimpleProcessIdentifier (CIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME, sValue);
  }

  /**
   * Create a new process identifier from the URI representation. This is the
   * inverse operation of {@link #getURIEncoded()}.
   *
   * @param sURIPart
   *        The URI part
   *        <code>cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii01:ver1.0</code>
   *        . It must NOT be percent encoded!
   * @return The created {@link SimpleProcessIdentifier} and never
   *         <code>null</code>.
   * @throws IllegalArgumentException
   *         If the passed identifier is not a valid URI encoded identifier
   */
  @Nonnull
  public static SimpleProcessIdentifier createFromURIPart (@Nonnull final String sURIPart) throws IllegalArgumentException
  {
    return IdentifierHelper.createProcessIdentifierFromURIPart (sURIPart);
  }

  /**
   * Create a new process identifier from the URI representation. This is the
   * inverse operation of {@link #getURIEncoded()}.
   *
   * @param sURIPart
   *        The URI part
   *        <code>cenbii-procid-ubl::urn:www.cenbii.eu:profile:bii01:ver1.0</code>
   *        . It must NOT be percent encoded! May be <code>null</code>.
   * @return The created {@link SimpleProcessIdentifier} or <code>null</code> if
   *         the passed identifier is not a valid URI encoded identifier
   */
  @Nullable
  public static SimpleProcessIdentifier createFromURIPartOrNull (@Nullable final String sURIPart)
  {
    if (sURIPart == null)
      return null;
    return IdentifierHelper.createProcessIdentifierFromURIPartOrNull (sURIPart);
  }
}
