/**
 * Copyright (C) 2015-2019 Philip Helger (www.helger.com)
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

import com.helger.peppol.identifier.bdxr.BDXRIdentifierHelper;
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
  @Override
  public String getDefaultDocumentTypeIdentifierScheme ()
  {
    return CBDXRIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME;
  }

  @Override
  public boolean isDocumentTypeIdentifierCaseInsensitive (@Nullable final String sScheme)
  {
    return CBDXRIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME.equals (sScheme);
  }

  public boolean isDocumentTypeIdentifierSchemeValid (@Nullable final String sScheme)
  {
    return BDXRIdentifierHelper.isValidIdentifierScheme (sScheme);
  }

  public boolean isDocumentTypeIdentifierValueValid (@Nullable final String sValue)
  {
    return BDXRIdentifierHelper.isValidIdentifierValue (sValue);
  }

  @Nullable
  public BDXRDocumentTypeIdentifier createDocumentTypeIdentifier (@Nullable final String sScheme,
                                                                  @Nullable final String sValue)
  {
    final String sRealScheme = nullNotEmpty (sScheme);
    final String sRealValue = nullNotEmpty (isDocumentTypeIdentifierCaseInsensitive (sRealScheme) ? getUnifiedValue (sValue)
                                                                                                  : sValue);
    if (isDocumentTypeIdentifierSchemeValid (sRealScheme) && isDocumentTypeIdentifierValueValid (sRealValue))
      return new BDXRDocumentTypeIdentifier (sRealScheme, sRealValue);
    return null;
  }

  // No default participant identifier scheme

  @Override
  public boolean isParticipantIdentifierCaseInsensitive (@Nullable final String sScheme)
  {
    return PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME.equals (sScheme);
  }

  public boolean isParticipantIdentifierSchemeValid (@Nullable final String sScheme)
  {
    return BDXRIdentifierHelper.isValidIdentifierScheme (sScheme);
  }

  public boolean isParticipantIdentifierValueValid (@Nullable final String sValue)
  {
    return BDXRIdentifierHelper.isValidIdentifierValue (sValue);
  }

  @Nullable
  public BDXRParticipantIdentifier createParticipantIdentifier (@Nullable final String sScheme,
                                                                @Nullable final String sValue)
  {
    final String sRealScheme = nullNotEmpty (sScheme);
    final String sRealValue = nullNotEmpty (isParticipantIdentifierCaseInsensitive (sRealScheme) ? getUnifiedValue (sValue)
                                                                                                 : sValue);
    if (isParticipantIdentifierSchemeValid (sRealScheme) && isParticipantIdentifierValueValid (sRealValue))
      return new BDXRParticipantIdentifier (sRealScheme, sRealValue);
    return null;
  }

  @Nonnull
  @Override
  public String getDefaultProcessIdentifierScheme ()
  {
    return CBDXRIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME;
  }

  @Override
  public boolean isProcessIdentifierCaseInsensitive (@Nullable final String sScheme)
  {
    return CBDXRIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME.equals (sScheme);
  }

  public boolean isProcessIdentifierValueValid (final String sValue)
  {
    return BDXRIdentifierHelper.isValidIdentifierValue (sValue);
  }

  @Nullable
  public BDXRProcessIdentifier createProcessIdentifier (@Nullable final String sScheme, @Nullable final String sValue)
  {
    final String sRealScheme = nullNotEmpty (sScheme);
    final String sRealValue = nullNotEmpty (isProcessIdentifierCaseInsensitive (sRealScheme) ? getUnifiedValue (sValue)
                                                                                             : sValue);

    if (isProcessIdentifierSchemeValid (sRealScheme) && isProcessIdentifierValueValid (sRealValue))
      return new BDXRProcessIdentifier (sRealScheme, sRealValue);
    return null;
  }
}
