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
package com.helger.peppol.identifier.generic.participant;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.ext.ICommonsList;
import com.helger.commons.lang.ICloneable;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.CIdentifier;
import com.helger.peppol.identifier.IdentifierHelper;
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
                                         IMutableParticipantIdentifier,
                                         Comparable <SimpleParticipantIdentifier>,
                                         ICloneable <SimpleParticipantIdentifier>
{
  public SimpleParticipantIdentifier (@Nonnull final IParticipantIdentifier aIdentifier)
  {
    this (aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  public SimpleParticipantIdentifier (@Nullable final String sScheme, @Nonnull final String sValue)
  {
    setScheme (sScheme);
    setValue (sValue);
  }

  public int compareTo (@Nonnull final SimpleParticipantIdentifier aOther)
  {
    return IdentifierHelper.compareParticipantIdentifiers (this, aOther);
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
   * the inverse operation of {@link #getURIEncoded()}. Take the passed URI part
   * and try to convert it back to a participant identifier. The URI part must
   * have the layout <code>scheme::value</code>. This method accepts all
   * identifier schemes and values.
   *
   * @param sURIPart
   *        The URI part <code>iso6523-actorid-upis::0088:12345678</code>. It
   *        must NOT be percent encoded! May be <code>null</code>.
   * @return The created {@link SimpleParticipantIdentifier} or
   *         <code>null</code> if the passed identifier is not a valid URI
   *         encoded identifier
   */
  @Nullable
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
  public static boolean isValidURIPart (@Nullable final String sURIPart)
  {
    return createFromURIPartOrNull (sURIPart) != null;
  }
}
