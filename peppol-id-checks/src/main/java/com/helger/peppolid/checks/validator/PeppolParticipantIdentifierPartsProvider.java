/*
 * Copyright (C) 2026 Philip Helger
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
package com.helger.peppolid.checks.validator;

import java.util.List;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.concurrent.Immutable;
import com.helger.base.string.StringHelper;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;

/**
 * The {@link IParticipantIdentifierPartsProvider} for the Peppol Network. It splits the value of an
 * <code>iso6523-actorid-upis</code> identifier at the first colon - e.g.
 * <code>iso6523-actorid-upis::0088:123456</code> is split into the issuing agency <code>0088</code>
 * and the local participant ID <code>123456</code>.
 *
 * @author Philip Helger
 * @since 13.0.0
 */
@Immutable
public class PeppolParticipantIdentifierPartsProvider implements IParticipantIdentifierPartsProvider
{
  /** The global instance to be used */
  public static final PeppolParticipantIdentifierPartsProvider INSTANCE = new PeppolParticipantIdentifierPartsProvider ();

  public PeppolParticipantIdentifierPartsProvider ()
  {}

  @Nullable
  public ParticipantIdentifierParts getParts (@NonNull final IParticipantIdentifier aParticipantID)
  {
    // Only the ISO 6523 based scheme can be split
    if (!PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME.equals (aParticipantID.getScheme ()))
      return null;

    final List <String> aParts = StringHelper.getExploded (':', aParticipantID.getValue (), 2);
    if (aParts.size () < 2)
      return null;

    final String sIssuingAgencyID = aParts.get (0);
    final String sLocalParticipantID = aParts.get (1);
    if (StringHelper.isEmpty (sIssuingAgencyID) || StringHelper.isEmpty (sLocalParticipantID))
      return null;

    return new ParticipantIdentifierParts (sIssuingAgencyID, sLocalParticipantID);
  }
}
