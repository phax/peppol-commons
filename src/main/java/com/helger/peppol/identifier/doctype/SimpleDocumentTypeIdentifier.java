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
package com.helger.peppol.identifier.doctype;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.ICloneable;
import com.helger.commons.annotations.ReturnsMutableCopy;
import com.helger.peppol.identifier.CIdentifier;
import com.helger.peppol.identifier.DocumentIdentifierType;
import com.helger.peppol.identifier.IReadonlyDocumentTypeIdentifier;
import com.helger.peppol.identifier.IdentifierUtils;
import com.helger.peppol.identifier.participant.SimpleParticipantIdentifier;

/**
 * This is a sanity class around the {@link DocumentIdentifierType} class with
 * easier construction and some sanity access methods. It may be used in all
 * places where {@link DocumentIdentifierType} objects are required.<br>
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@NotThreadSafe
public class SimpleDocumentTypeIdentifier extends DocumentIdentifierType implements IPeppolDocumentTypeIdentifier, Comparable <SimpleDocumentTypeIdentifier>, ICloneable <SimpleDocumentTypeIdentifier>
{
  public SimpleDocumentTypeIdentifier (@Nonnull final IReadonlyDocumentTypeIdentifier aIdentifier)
  {
    this (aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  public SimpleDocumentTypeIdentifier (@Nullable final String sScheme, @Nonnull final String sValue)
  {
    if (!IdentifierUtils.isValidIdentifierScheme (sScheme))
      throw new IllegalArgumentException ("Document Type identifier scheme '" + sScheme + "' is invalid!");
    if (!IdentifierUtils.isValidDocumentTypeIdentifierValue (sValue))
      throw new IllegalArgumentException ("Document Type identifier value '" + sValue + "' is invalid!");
    setScheme (sScheme);
    setValue (sValue);
  }

  public boolean isDefaultScheme ()
  {
    return IdentifierUtils.hasDefaultDocumentTypeIdentifierScheme (this);
  }

  @Nonnull
  public String getURIEncoded ()
  {
    return IdentifierUtils.getIdentifierURIEncoded (this);
  }

  @Nonnull
  public String getURIPercentEncoded ()
  {
    return IdentifierUtils.getIdentifierURIPercentEncoded (this);
  }

  @Nonnull
  public IPeppolDocumentTypeIdentifierParts getParts ()
  {
    try
    {
      return PeppolDocumentTypeIdentifierParts.extractFromString (getValue ());
    }
    catch (final IllegalArgumentException ex)
    {
      // Not PEPPOL - try OpenPEPPOL
      return OpenPeppolDocumentTypeIdentifierParts.extractFromString (getValue ());
    }
  }

  public int compareTo (@Nonnull final SimpleDocumentTypeIdentifier aOther)
  {
    return IdentifierUtils.compareIdentifiers (this, aOther);
  }

  @Nonnull
  @ReturnsMutableCopy
  public SimpleDocumentTypeIdentifier getClone ()
  {
    return new SimpleDocumentTypeIdentifier (this);
  }

  /**
   * Create a new document type identifier that uses the default schema
   * {@link CIdentifier#DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME}
   *
   * @param sValue
   *        The identifier value like
   *        <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0::2.0</code>
   * @return The created {@link SimpleParticipantIdentifier} and never
   *         <code>null</code>.
   */
  @Nonnull
  public static SimpleDocumentTypeIdentifier createWithDefaultScheme (@Nonnull final String sValue)
  {
    return new SimpleDocumentTypeIdentifier (CIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME, sValue);
  }

  /**
   * Create a new document type identifier from the URI representation. This is
   * the inverse operation of {@link #getURIEncoded()}.
   *
   * @param sURIPart
   *        The URI part
   *        <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0::2.0</code>
   *        . It must NOT be percent encoded!
   * @return The created {@link SimpleDocumentTypeIdentifier} and never
   *         <code>null</code>.
   * @throws IllegalArgumentException
   *         If the passed identifier is not a valid URI encoded identifier
   */
  @Nonnull
  public static SimpleDocumentTypeIdentifier createFromURIPart (@Nonnull final String sURIPart) throws IllegalArgumentException
  {
    return IdentifierUtils.createDocumentTypeIdentifierFromURIPart (sURIPart);
  }

  /**
   * Create a new document type identifier from the URI representation. This is
   * the inverse operation of {@link #getURIEncoded()}.
   *
   * @param sURIPart
   *        The URI part
   *        <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0::2.0</code>
   *        . It must NOT be percent encoded!
   * @return The created {@link SimpleDocumentTypeIdentifier} or
   *         <code>null</code> if the passed identifier is not a valid URI
   *         encoded identifier
   */
  @Nullable
  public static SimpleDocumentTypeIdentifier createFromURIPartOrNull (@Nonnull final String sURIPart)
  {
    return IdentifierUtils.createDocumentTypeIdentifierFromURIPartOrNull (sURIPart);
  }
}
