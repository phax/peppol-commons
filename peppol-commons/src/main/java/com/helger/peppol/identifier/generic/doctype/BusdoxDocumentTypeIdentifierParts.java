/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
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
package com.helger.peppol.identifier.generic.doctype;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;

/**
 * A standalone wrapper class for the {@link IBusdoxDocumentTypeIdentifierParts}
 * interface. The implementation is immutable.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Immutable
public final class BusdoxDocumentTypeIdentifierParts implements IBusdoxDocumentTypeIdentifierParts
{
  private final String m_sRootNS;
  private final String m_sLocalName;
  private final String m_sSubTypeIdentifier;

  /**
   * Constructor.
   *
   * @param sRootNS
   *        The root namespace. May neither be <code>null</code> nor empty
   * @param sLocalName
   *        The local name. May neither be <code>null</code> nor empty.
   * @param sSubTypeIdentifier
   *        The sub-type identifier. May be <code>null</code>.
   */
  public BusdoxDocumentTypeIdentifierParts (@Nonnull @Nonempty final String sRootNS,
                                            @Nonnull @Nonempty final String sLocalName,
                                            @Nullable final String sSubTypeIdentifier)
  {
    ValueEnforcer.notEmpty (sRootNS, "RootNS");
    ValueEnforcer.notEmpty (sLocalName, "LocalName");
    m_sRootNS = sRootNS;
    m_sLocalName = sLocalName;
    m_sSubTypeIdentifier = sSubTypeIdentifier;
  }

  @Nonnull
  @Nonempty
  public String getRootNS ()
  {
    return m_sRootNS;
  }

  @Nonnull
  @Nonempty
  public String getLocalName ()
  {
    return m_sLocalName;
  }

  @Nullable
  public String getSubTypeIdentifier ()
  {
    return m_sSubTypeIdentifier;
  }

  @Nonnull
  @Nonempty
  public String getAsDocumentTypeIdentifierValue ()
  {
    return getAsDocumentTypeIdentifierValue (this);
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final BusdoxDocumentTypeIdentifierParts rhs = (BusdoxDocumentTypeIdentifierParts) o;
    return m_sRootNS.equals (rhs.m_sRootNS) &&
           m_sLocalName.equals (rhs.m_sLocalName) &&
           EqualsHelper.equals (m_sSubTypeIdentifier, rhs.m_sSubTypeIdentifier);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sRootNS)
                                       .append (m_sLocalName)
                                       .append (m_sSubTypeIdentifier)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("rootNS", m_sRootNS)
                                       .append ("localName", m_sLocalName)
                                       .append ("subTypeIdentifier", m_sSubTypeIdentifier)
                                       .getToString ();
  }

  /**
   * Convert an {@link IBusdoxDocumentTypeIdentifierParts} object to a full
   * document identifier value (without a scheme!)
   *
   * @param aParts
   *        The object to be converted. May not be <code>null</code>.
   * @return The assembled document identifier value. Never <code>null</code>
   *         nor empty.
   */
  @Nonnull
  @Nonempty
  public static String getAsDocumentTypeIdentifierValue (@Nonnull final IBusdoxDocumentTypeIdentifierParts aParts)
  {
    ValueEnforcer.notNull (aParts, "Parts");

    // See Busdox Common Specs version 3.5
    String ret = aParts.getRootNS () + NAMESPACE_SEPARATOR + aParts.getLocalName ();

    // Add optional subtype identifier
    final String sSubTypeIdentifier = aParts.getSubTypeIdentifier ();
    if (StringHelper.hasText (sSubTypeIdentifier))
      ret += SUBTYPE_SEPARATOR + sSubTypeIdentifier;
    return ret;
  }

  /**
   * Split an existing document identifier value (without the scheme) into the
   * different parts.<br>
   * A document identifier value has the following layout:<br>
   * <code>&lt;root NS&gt;::&lt;document element local name&gt;[##&lt;sub type
   * identifier&gt;]</code>
   *
   * @param sDocTypeID
   *        The document identifier value to be split. May neither be
   *        <code>null</code> nor empty.
   * @return The non-<code>null</code> object containing the different pieces.
   * @throws IllegalArgumentException
   *         if parsing fails
   */
  @Nonnull
  public static IBusdoxDocumentTypeIdentifierParts extractFromString (@Nonnull @Nonempty final String sDocTypeID)
  {
    ValueEnforcer.notEmpty (sDocTypeID, "DocumentTypeIdentifier");

    final ICommonsList <String> aMain = StringHelper.getExploded (SUBTYPE_SEPARATOR, sDocTypeID, 2);
    // List cannot be empty, because we're checking that docType is not empty a
    // few lines above
    final ICommonsList <String> aFirst = StringHelper.getExploded (NAMESPACE_SEPARATOR, aMain.get (0), 2);
    if (aFirst.size () < 2)
      throw new IllegalArgumentException ("The document identifier '" +
                                          sDocTypeID +
                                          "' is missing the separation between root namespace and local name!");

    final String sRootNS = aFirst.get (0);
    final String sLocalName = aFirst.get (1);
    final String sSubTypeIdentifier = aMain.size () == 1 ? null : aMain.get (1);
    return new BusdoxDocumentTypeIdentifierParts (sRootNS, sLocalName, sSubTypeIdentifier);
  }
}
