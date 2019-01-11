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

import javax.annotation.Nullable;

import com.helger.peppol.identifier.generic.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppol.identifier.generic.participant.SimpleParticipantIdentifier;
import com.helger.peppol.identifier.generic.process.SimpleProcessIdentifier;

/**
 * Default implementation of {@link IIdentifierFactory} for default (simple)
 * identifiers.
 *
 * @author Philip Helger
 */
public class SimpleIdentifierFactory implements IIdentifierFactory
{
  /** Global instance to be used. */
  public static final SimpleIdentifierFactory INSTANCE = new SimpleIdentifierFactory ();

  public SimpleIdentifierFactory ()
  {}

  public boolean isProcessIdentifierSchemeMandatory ()
  {
    return false;
  }

  @Nullable
  public SimpleDocumentTypeIdentifier createDocumentTypeIdentifier (@Nullable final String sScheme,
                                                                    @Nullable final String sValue)
  {
    final String sRealValue = isDocumentTypeIdentifierCaseInsensitive (sScheme) ? getUnifiedValue (sValue) : sValue;
    return new SimpleDocumentTypeIdentifier (nullNotEmpty (sScheme), nullNotEmpty (sRealValue));
  }

  @Nullable
  public SimpleParticipantIdentifier createParticipantIdentifier (@Nullable final String sScheme,
                                                                  @Nullable final String sValue)
  {
    final String sRealValue = isParticipantIdentifierCaseInsensitive (sScheme) ? getUnifiedValue (sValue) : sValue;
    return new SimpleParticipantIdentifier (nullNotEmpty (sScheme), nullNotEmpty (sRealValue));
  }

  @Nullable
  public SimpleProcessIdentifier createProcessIdentifier (@Nullable final String sScheme, @Nullable final String sValue)
  {
    final String sRealValue = isProcessIdentifierCaseInsensitive (sScheme) ? getUnifiedValue (sValue) : sValue;
    return new SimpleProcessIdentifier (nullNotEmpty (sScheme), nullNotEmpty (sRealValue));
  }
}
