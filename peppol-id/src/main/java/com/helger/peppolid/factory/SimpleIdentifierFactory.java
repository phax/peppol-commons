/*
 * Copyright (C) 2015-2022 Philip Helger
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.peppolid.factory;

import javax.annotation.Nullable;

import com.helger.commons.string.ToStringGenerator;
import com.helger.peppolid.simple.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppolid.simple.participant.SimpleParticipantIdentifier;
import com.helger.peppolid.simple.process.SimpleProcessIdentifier;

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

  @Override
  public boolean isProcessIdentifierSchemeMandatory ()
  {
    return false;
  }

  @Nullable
  public SimpleDocumentTypeIdentifier createDocumentTypeIdentifier (@Nullable final String sScheme, @Nullable final String sValue)
  {
    final String sRealValue = isDocumentTypeIdentifierCaseInsensitive (sScheme) ? getUnifiedValue (sValue) : sValue;
    return new SimpleDocumentTypeIdentifier (nullNotEmpty (sScheme), nullNotEmpty (sRealValue));
  }

  @Nullable
  public SimpleParticipantIdentifier createParticipantIdentifier (@Nullable final String sScheme, @Nullable final String sValue)
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

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).getToString ();
  }
}
