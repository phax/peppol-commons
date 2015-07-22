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
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.UnsupportedOperation;
import com.helger.peppol.identifier.CIdentifier;
import com.helger.peppol.identifier.DocumentIdentifierType;
import com.helger.peppol.identifier.IIdentifier;
import com.helger.peppol.identifier.IdentifierHelper;

/**
 * This is an immutable sanity class around the {@link DocumentIdentifierType}
 * class with easier construction and some sanity access methods. It may be used
 * in all places where {@link DocumentIdentifierType} objects are required.<br>
 * For a mutable version, please check {@link SimpleDocumentTypeIdentifier}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Immutable
public class ReadOnlyDocumentTypeIdentifier extends DocumentIdentifierType implements IMutablePeppolDocumentTypeIdentifier
{
  public ReadOnlyDocumentTypeIdentifier (@Nonnull final IIdentifier aIdentifier)
  {
    this (aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  public ReadOnlyDocumentTypeIdentifier (@Nullable final String sScheme, @Nullable final String sValue)
  {
    if (!IdentifierHelper.isValidIdentifierScheme (sScheme))
      throw new IllegalArgumentException ("Document Type identifier scheme '" + sScheme + "' is invalid!");
    if (!IdentifierHelper.isValidDocumentTypeIdentifierValue (sValue))
      throw new IllegalArgumentException ("Document Type identifier value '" + sValue + "' is invalid!");

    // Explicitly use the super methods, as the methods of this class throw an
    // exception!
    super.setScheme (sScheme);
    super.setValue (sValue);
  }

  @Override
  @UnsupportedOperation
  public void setValue (@Nullable final String sValue)
  {
    // This is how we make things read-only :)
    throw new UnsupportedOperationException ("setValue is forbidden on this class!");
  }

  @Override
  @UnsupportedOperation
  public void setScheme (@Nullable final String sScheme)
  {
    // This is how we make things read-only :)
    throw new UnsupportedOperationException ("setScheme is forbidden on this class!");
  }

  public boolean isDefaultScheme ()
  {
    return IdentifierHelper.hasDefaultDocumentTypeIdentifierScheme (this);
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

  @Nonnull
  public IPeppolDocumentTypeIdentifierParts getParts ()
  {
    return IdentifierHelper.getDocumentTypeIdentifierParts (this);
  }

  /**
   * Create a document type identifier with the common scheme as defined by
   * {@link CIdentifier#DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME}
   *
   * @param sValue
   *        The document type identifier value
   * @return The readonly document identifier
   */
  @Nonnull
  public static ReadOnlyDocumentTypeIdentifier createWithDefaultScheme (@Nullable final String sValue)
  {
    return new ReadOnlyDocumentTypeIdentifier (CIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME, sValue);
  }
}
