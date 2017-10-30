/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
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

import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;
import com.helger.peppol.identifier.peppol.doctype.PeppolDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.participant.PeppolParticipantIdentifier;
import com.helger.peppol.identifier.peppol.process.PeppolProcessIdentifier;

/**
 * Default implementation of {@link IIdentifierFactory} for PEPPOL identifiers.
 *
 * @author Philip Helger
 */
public class PeppolIdentifierFactory implements IIdentifierFactory
{
  /** Global instance to be used. */
  public static final PeppolIdentifierFactory INSTANCE = new PeppolIdentifierFactory ();

  public PeppolIdentifierFactory ()
  {}

  public boolean isDocumentTypeIdentifierSchemeMandatory ()
  {
    return true;
  }

  @Nonnull
  public String getDefaultDocumentTypeIdentifierScheme ()
  {
    return PeppolIdentifierHelper.DEFAULT_DOCUMENT_TYPE_SCHEME;
  }

  @Nullable
  public PeppolDocumentTypeIdentifier createDocumentTypeIdentifier (@Nullable final String sScheme,
                                                                    @Nullable final String sValue)
  {
    final String sRealValue = isDocumentTypeIdentifierCaseInsensitive (sScheme) ? getUnifiedValue (sValue) : sValue;
    return PeppolDocumentTypeIdentifier.createIfValid (nullNotEmpty (sScheme), nullNotEmpty (sRealValue));
  }

  public boolean isParticipantIdentifierSchemeMandatory ()
  {
    return true;
  }

  @Nonnull
  public String getDefaultParticipantIdentifierScheme ()
  {
    return PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME;
  }

  public boolean isParticipantIdentifierCaseInsensitive (@Nullable final String sScheme)
  {
    return PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME.equals (sScheme);
  }

  @Nullable
  public PeppolParticipantIdentifier createParticipantIdentifier (@Nullable final String sScheme,
                                                                  @Nullable final String sValue)
  {
    final String sRealValue = isParticipantIdentifierCaseInsensitive (sScheme) ? getUnifiedValue (sValue) : sValue;
    return PeppolParticipantIdentifier.createIfValid (nullNotEmpty (sScheme), nullNotEmpty (sRealValue));
  }

  @Nonnull
  public String getDefaultProcessIdentifierScheme ()
  {
    return PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME;
  }

  @Nullable
  public PeppolProcessIdentifier createProcessIdentifier (@Nullable final String sScheme, @Nullable final String sValue)
  {
    final String sRealValue = isProcessIdentifierCaseInsensitive (sScheme) ? getUnifiedValue (sValue) : sValue;
    return PeppolProcessIdentifier.createIfValid (nullNotEmpty (sScheme), nullNotEmpty (sRealValue));
  }
}
