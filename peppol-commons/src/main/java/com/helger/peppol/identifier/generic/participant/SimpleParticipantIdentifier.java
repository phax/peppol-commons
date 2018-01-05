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
package com.helger.peppol.identifier.generic.participant;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.annotation.DevelopersNote;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.compare.CompareHelper;
import com.helger.commons.lang.ICloneable;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.CIdentifier;
import com.helger.peppol.identifier.ParticipantIdentifierType;

/**
 * This is a sanity class around the {@link ParticipantIdentifierType} class
 * with easier construction and some sanity access methods. It may be used in
 * all places where {@link ParticipantIdentifierType} objects are required.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@NotThreadSafe
public class SimpleParticipantIdentifier extends ParticipantIdentifierType implements
                                         Comparable <SimpleParticipantIdentifier>,
                                         ICloneable <SimpleParticipantIdentifier>
{
  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public SimpleParticipantIdentifier (@Nonnull final IParticipantIdentifier aIdentifier)
  {
    this (aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public SimpleParticipantIdentifier (@Nullable final String sScheme, @Nonnull final String sValue)
  {
    setScheme (sScheme);
    setValue (sValue);
  }

  public int compareTo (@Nonnull final SimpleParticipantIdentifier aOther)
  {
    int ret = CompareHelper.compare (getScheme (), aOther.getScheme ());
    if (ret == 0)
      ret = CompareHelper.compare (getValue (), aOther.getValue ());
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public SimpleParticipantIdentifier getClone ()
  {
    return new SimpleParticipantIdentifier (this);
  }

  /**
   * Create a new document type identifier from the URI representation. This is
   * the inverse operation of {@link #getURIEncoded()}. The URI part must have
   * the layout <code>scheme::value</code>. This method accepts all identifier
   * schemes and values.
   *
   * @param sURIPart
   *        The URI part in the format <code>scheme::value</code>. It must NOT
   *        be percent encoded!
   * @return The created {@link SimpleParticipantIdentifier} and never
   *         <code>null</code>.
   * @throws IllegalArgumentException
   *         If the passed identifier is not a valid URI encoded identifier
   */
  @Nonnull
  @Deprecated
  public static SimpleParticipantIdentifier createFromURIPart (@Nonnull final String sURIPart) throws IllegalArgumentException
  {
    final SimpleParticipantIdentifier ret = createFromURIPartOrNull (sURIPart);
    if (ret == null)
      throw new IllegalArgumentException ("Participant identifier '" +
                                          sURIPart +
                                          "' did not include correct delimiter: " +
                                          CIdentifier.URL_SCHEME_VALUE_SEPARATOR);
    return ret;
  }

  /**
   * Create a new participant identifier from the URI representation. This is
   * the inverse operation of {@link #getURIEncoded()}. The URI part must have
   * the layout <code>scheme::value</code>. This method accepts all identifier
   * schemes and values.
   *
   * @param sURIPart
   *        The URI part in the format <code>scheme::value</code>. It must NOT
   *        be percent encoded!
   * @return The created {@link SimpleParticipantIdentifier} or
   *         <code>null</code> if the passed identifier is not a valid URI
   *         encoded identifier
   * @deprecated Use
   *             {@link com.helger.peppol.identifier.factory.IIdentifierFactory#parseParticipantIdentifier(String)}
   *             instead
   */
  @Nullable
  @Deprecated
  public static SimpleParticipantIdentifier createFromURIPartOrNull (@Nullable final String sURIPart)
  {
    if (sURIPart == null)
      return null;

    // This is quicker than splitting with RegEx!
    final ICommonsList <String> aSplitted = StringHelper.getExploded (CIdentifier.URL_SCHEME_VALUE_SEPARATOR,
                                                                      sURIPart,
                                                                      2);
    if (aSplitted.size () != 2)
      return null;

    return new SimpleParticipantIdentifier (aSplitted.get (0), aSplitted.get (1));
  }

  /**
   * Check if the passed participant identifier is valid. This method checks for
   * the existence of the scheme and the value.
   *
   * @param sURIPart
   *        The participant identifier to be checked (including the scheme). May
   *        be <code>null</code>.
   * @return <code>true</code> if the participant identifier is valid,
   *         <code>false</code> otherwise
   */
  @Deprecated
  public static boolean isValidURIPart (@Nullable final String sURIPart)
  {
    return createFromURIPartOrNull (sURIPart) != null;
  }
}
