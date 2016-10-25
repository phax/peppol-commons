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
package com.helger.peppol.identifier.factory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.peppol.identifier.bdxr.CBDXRIdentifier;
import com.helger.peppol.identifier.bdxr.doctype.BDXRDocumentTypeIdentifier;
import com.helger.peppol.identifier.bdxr.participant.BDXRParticipantIdentifier;
import com.helger.peppol.identifier.bdxr.process.BDXRProcessIdentifier;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;

/**
 * Default implementation of {@link IIdentifierFactory} for BDXR identifiers.
 *
 * @author Philip Helger
 */
public class BDXRIdentifierFactory implements IIdentifierFactory
{
  /** Global instance to be used. */
  public static final BDXRIdentifierFactory INSTANCE = new BDXRIdentifierFactory ();

  public BDXRIdentifierFactory ()
  {}

  @Nonnull
  public String getDefaultDocumentTypeIdentifierScheme ()
  {
    return CBDXRIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME;
  }

  public boolean isDocumentTypeIdentifierCaseInsensitive (@Nullable final String sScheme)
  {
    return CBDXRIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME.equals (sScheme);
  }

  @Nullable
  public BDXRDocumentTypeIdentifier createDocumentTypeIdentifier (@Nullable final String sScheme,
                                                                  @Nullable final String sValue)
  {
    final String sRealValue = isDocumentTypeIdentifierCaseInsensitive (sScheme) ? getUnifiedValue (sValue) : sValue;
    return BDXRDocumentTypeIdentifier.createIfValid (sScheme, sRealValue);
  }

  @Nullable
  public BDXRDocumentTypeIdentifier parseDocumentTypeIdentifier (@Nullable final String sURIEncodedIdentifier)
  {
    return BDXRDocumentTypeIdentifier.createFromURIPartOrNull (sURIEncodedIdentifier);
  }

  // No default participant identifier scheme

  public boolean isParticipantIdentifierCaseInsensitive (@Nullable final String sScheme)
  {
    return PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME.equals (sScheme);
  }

  @Nullable
  public BDXRParticipantIdentifier createParticipantIdentifier (@Nullable final String sScheme,
                                                                @Nullable final String sValue)
  {
    return BDXRParticipantIdentifier.createIfValid (sScheme, sValue);
  }

  @Nullable
  public BDXRParticipantIdentifier parseParticipantIdentifier (@Nullable final String sURIEncodedIdentifier)
  {
    return BDXRParticipantIdentifier.createFromURIPartOrNull (sURIEncodedIdentifier);
  }

  @Nonnull
  public String getDefaultProcessIdentifierScheme ()
  {
    return CBDXRIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME;
  }

  public boolean isProcessIdentifierCaseInsensitive (@Nullable final String sScheme)
  {
    return CBDXRIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME.equals (sScheme);
  }

  @Nullable
  public BDXRProcessIdentifier createProcessIdentifier (@Nullable final String sScheme, @Nullable final String sValue)
  {
    return BDXRProcessIdentifier.createIfValid (sScheme, sValue);
  }

  @Nullable
  public BDXRProcessIdentifier parseProcessIdentifier (@Nullable final String sURIEncodedIdentifier)
  {
    return BDXRProcessIdentifier.createFromURIPartOrNull (sURIEncodedIdentifier);
  }
}
