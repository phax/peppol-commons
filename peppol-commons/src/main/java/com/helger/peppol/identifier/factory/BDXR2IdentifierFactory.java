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

import com.helger.peppol.identifier.bdxr.smp2.BDXR2IdentifierHelper;
import com.helger.peppol.identifier.bdxr.smp2.CBDXR2Identifier;
import com.helger.peppol.identifier.bdxr.smp2.doctype.BDXR2DocumentTypeIdentifier;
import com.helger.peppol.identifier.bdxr.smp2.participant.BDXR2ParticipantIdentifier;
import com.helger.peppol.identifier.bdxr.smp2.process.BDXR2ProcessIdentifier;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;

/**
 * Default implementation of {@link IIdentifierFactory} for BDXR SMP v2
 * identifiers.
 *
 * @author Philip Helger
 */
public class BDXR2IdentifierFactory implements IIdentifierFactory
{
  /** Global instance to be used. */
  public static final BDXR2IdentifierFactory INSTANCE = new BDXR2IdentifierFactory ();

  public BDXR2IdentifierFactory ()
  {}

  @Nonnull
  @Override
  public String getDefaultDocumentTypeIdentifierScheme ()
  {
    return CBDXR2Identifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME;
  }

  @Override
  public boolean isDocumentTypeIdentifierCaseInsensitive (@Nullable final String sScheme)
  {
    return CBDXR2Identifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME.equals (sScheme);
  }

  @Override
  public boolean isDocumentTypeIdentifierSchemeValid (@Nullable final String sScheme)
  {
    return BDXR2IdentifierHelper.isValidIdentifierScheme (sScheme);
  }

  @Override
  public boolean isDocumentTypeIdentifierValueValid (@Nullable final String sValue)
  {
    return BDXR2IdentifierHelper.isValidIdentifierValue (sValue);
  }

  @Nullable
  public BDXR2DocumentTypeIdentifier createDocumentTypeIdentifier (@Nullable final String sScheme,
                                                                   @Nullable final String sValue)
  {
    final String sRealScheme = nullNotEmpty (sScheme);
    final String sRealValue = nullNotEmpty (isDocumentTypeIdentifierCaseInsensitive (sRealScheme) ? getUnifiedValue (sValue)
                                                                                                  : sValue);
    if (isDocumentTypeIdentifierSchemeValid (sRealScheme) && isDocumentTypeIdentifierValueValid (sRealValue))
      return new BDXR2DocumentTypeIdentifier (sRealScheme, sRealValue);
    return null;
  }

  // No default participant identifier scheme

  @Override
  public boolean isParticipantIdentifierCaseInsensitive (@Nullable final String sScheme)
  {
    return PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME.equals (sScheme);
  }

  @Override
  public boolean isParticipantIdentifierSchemeValid (@Nullable final String sScheme)
  {
    return BDXR2IdentifierHelper.isValidIdentifierScheme (sScheme);
  }

  @Override
  public boolean isParticipantIdentifierValueValid (@Nullable final String sValue)
  {
    return BDXR2IdentifierHelper.isValidIdentifierValue (sValue);
  }

  @Nullable
  public BDXR2ParticipantIdentifier createParticipantIdentifier (@Nullable final String sScheme,
                                                                 @Nullable final String sValue)
  {
    final String sRealScheme = nullNotEmpty (sScheme);
    final String sRealValue = nullNotEmpty (isParticipantIdentifierCaseInsensitive (sRealScheme) ? getUnifiedValue (sValue)
                                                                                                 : sValue);
    if (isParticipantIdentifierSchemeValid (sRealScheme) && isParticipantIdentifierValueValid (sRealValue))
      return new BDXR2ParticipantIdentifier (sRealScheme, sRealValue);
    return null;
  }

  @Nonnull
  @Override
  public String getDefaultProcessIdentifierScheme ()
  {
    return CBDXR2Identifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME;
  }

  @Override
  public boolean isProcessIdentifierCaseInsensitive (@Nullable final String sScheme)
  {
    return CBDXR2Identifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME.equals (sScheme);
  }

  @Override
  public boolean isProcessIdentifierValueValid (final String sValue)
  {
    return BDXR2IdentifierHelper.isValidIdentifierValue (sValue);
  }

  @Nullable
  public BDXR2ProcessIdentifier createProcessIdentifier (@Nullable final String sScheme, @Nullable final String sValue)
  {
    final String sRealScheme = nullNotEmpty (sScheme);
    final String sRealValue = nullNotEmpty (isProcessIdentifierCaseInsensitive (sRealScheme) ? getUnifiedValue (sValue)
                                                                                             : sValue);

    if (isProcessIdentifierSchemeValid (sRealScheme) && isProcessIdentifierValueValid (sRealValue))
      return new BDXR2ProcessIdentifier (sRealScheme, sRealValue);
    return null;
  }
}
