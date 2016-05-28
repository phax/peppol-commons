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
package com.helger.peppol.identifier.peppol.doctype;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.peppol.identifier.generic.doctype.IDocumentTypeIdentifier;
import com.helger.peppol.identifier.generic.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppol.identifier.generic.participant.SimpleParticipantIdentifier;
import com.helger.peppol.identifier.peppol.CPeppolIdentifier;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;

/**
 * A special document type identifier that handles the specialities of PEPPOL
 * (like fixed default scheme) etc.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class PeppolDocumentTypeIdentifier extends SimpleDocumentTypeIdentifier
                                          implements IMutablePeppolDocumentTypeIdentifier
{
  public PeppolDocumentTypeIdentifier (@Nonnull final IDocumentTypeIdentifier aIdentifier)
  {
    this (aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  @Nonnull
  private static String _verifyScheme (@Nullable final String sScheme)
  {
    if (!PeppolIdentifierHelper.isValidIdentifierScheme (sScheme))
      throw new IllegalArgumentException ("Peppol Document Type identifier scheme '" + sScheme + "' is invalid!");
    return sScheme;
  }

  @Nonnull
  private static String _verifyValue (@Nonnull final String sValue)
  {
    if (!PeppolIdentifierHelper.isValidDocumentTypeIdentifierValue (sValue))
      throw new IllegalArgumentException ("Peppol Document Type identifier value '" + sValue + "' is invalid!");
    return sValue;
  }

  public PeppolDocumentTypeIdentifier (@Nullable final String sScheme, @Nonnull final String sValue)
  {
    super (_verifyScheme (sScheme), _verifyValue (sValue));
  }

  @Override
  @Nonnull
  @ReturnsMutableCopy
  public PeppolDocumentTypeIdentifier getClone ()
  {
    return new PeppolDocumentTypeIdentifier (this);
  }

  /**
   * Create a new document type identifier that uses the default schema
   * {@link CPeppolIdentifier#DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME}
   *
   * @param sValue
   *        The identifier value like
   *        <code>urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0::2.0</code>
   * @return The created {@link SimpleParticipantIdentifier} and never
   *         <code>null</code>.
   */
  @Nonnull
  public static PeppolDocumentTypeIdentifier createWithDefaultScheme (@Nonnull final String sValue)
  {
    return new PeppolDocumentTypeIdentifier (IPeppolDocumentTypeIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME,
                                             sValue);
  }

  /**
   * Create a new document type identifier from the URI representation. This is
   * the inverse operation of {@link #getURIEncoded()}.
   *
   * @param sURIPart
   *        The URI part
   *        <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0::2.0</code>
   *        . It must NOT be percent encoded!
   * @return The created {@link PeppolDocumentTypeIdentifier} and never
   *         <code>null</code>.
   * @throws IllegalArgumentException
   *         If the passed identifier is not a valid URI encoded identifier
   */
  @Nonnull
  public static PeppolDocumentTypeIdentifier createFromURIPart (@Nonnull final String sURIPart) throws IllegalArgumentException
  {
    return PeppolIdentifierHelper.createDocumentTypeIdentifierFromURIPart (sURIPart);
  }

  /**
   * Create a new document type identifier from the URI representation. This is
   * the inverse operation of {@link #getURIEncoded()}.
   *
   * @param sURIPart
   *        The URI part
   *        <code>busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0::2.0</code>
   *        . It must NOT be percent encoded! May be <code>null</code>.
   * @return The created {@link PeppolDocumentTypeIdentifier} or
   *         <code>null</code> if the passed identifier is not a valid URI
   *         encoded identifier
   */
  @Nullable
  public static PeppolDocumentTypeIdentifier createFromURIPartOrNull (@Nullable final String sURIPart)
  {
    if (sURIPart == null)
      return null;
    return PeppolIdentifierHelper.createDocumentTypeIdentifierFromURIPartOrNull (sURIPart);
  }
}
