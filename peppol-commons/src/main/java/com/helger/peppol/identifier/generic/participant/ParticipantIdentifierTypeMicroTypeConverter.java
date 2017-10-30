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
package com.helger.peppol.identifier.generic.participant;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.peppol.identifier.AbstractIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.ParticipantIdentifierType;

public final class ParticipantIdentifierTypeMicroTypeConverter extends
                                                               AbstractIdentifierMicroTypeConverter <ParticipantIdentifierType>
{
  @Override
  @Nonnull
  protected ParticipantIdentifierType getAsNative (@Nullable final String sScheme, @Nonnull final String sValue)
  {
    final ParticipantIdentifierType aPI = new ParticipantIdentifierType ();
    aPI.setScheme (sScheme);
    aPI.setValue (sValue);
    return aPI;
  }
}
