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
    return BDXRDocumentTypeIdentifier.createIfValid (nullNotEmpty (sScheme), nullNotEmpty (sRealValue));
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
    final String sRealValue = isParticipantIdentifierCaseInsensitive (sScheme) ? getUnifiedValue (sValue) : sValue;
    return BDXRParticipantIdentifier.createIfValid (nullNotEmpty (sScheme), nullNotEmpty (sRealValue));
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
    final String sRealValue = isProcessIdentifierCaseInsensitive (sScheme) ? getUnifiedValue (sValue) : sValue;
    return BDXRProcessIdentifier.createIfValid (nullNotEmpty (sScheme), nullNotEmpty (sRealValue));
  }
}
