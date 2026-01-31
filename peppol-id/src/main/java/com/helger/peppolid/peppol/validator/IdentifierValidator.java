/*
 * Copyright (C) 2015-2026 Philip Helger
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
package com.helger.peppolid.peppol.validator;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.PresentForCodeCoverage;
import com.helger.base.spi.ServiceLoaderHelper;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.peppolid.peppol.participant.PeppolParticipantIdentifier;

/**
 * A wrapper around the custom identifier validator implementations.
 *
 * @author Philip Helger
 */
@Immutable
public final class IdentifierValidator
{
  private static final Logger LOGGER = LoggerFactory.getLogger (IdentifierValidator.class);
  private static final List <IParticipantIdentifierValidatorSPI> PID_VALIDATOR;

  static
  {
    PID_VALIDATOR = ServiceLoaderHelper.getAllSPIImplementations (IParticipantIdentifierValidatorSPI.class);
    if (!PID_VALIDATOR.isEmpty ())
      LOGGER.info ("Loaded " + PID_VALIDATOR.size () + " SPI implementations of IParticipantIdentifierValidatorSPI");
  }

  @PresentForCodeCoverage
  private static final IdentifierValidator INSTANCE = new IdentifierValidator ();

  private IdentifierValidator ()
  {}

  /**
   * Check if the passed participant ID matches all custom rules. But only participant IDs with the
   * default scheme {@link PeppolIdentifierHelper#DEFAULT_PARTICIPANT_SCHEME} are validated, as we
   * don't know the details of the other schemes.<br>
   * This method can be used to generically check the consistency of certain numbering schemes.
   *
   * @param aParticipantID
   *        The participant ID to validate. May be <code>null</code>.
   * @return <code>true</code> if a) the identifier is not the default scheme, b) if at least one
   *         validator matched or c) if no matching validator was found at all. The method returns
   *         <code>false</code> if a matching validator was found, but the ID did not match.
   */
  public static boolean isValidParticipantIdentifier (@Nullable final PeppolParticipantIdentifier aParticipantID)
  {
    if (aParticipantID == null)
      return false;

    // Only validate our default scheme
    if (!aParticipantID.hasDefaultScheme ())
      return true;

    boolean bAtLeastOneSupported = false;
    final String sIssuingAgencyID = aParticipantID.getIssuingAgencyID ();
    final String sLocal = aParticipantID.getLocalParticipantID ();

    // For all SPI instances
    for (final IParticipantIdentifierValidatorSPI aValidator : PID_VALIDATOR)
      if (aValidator.isSupportedIssuingAgency (sIssuingAgencyID))
      {
        if (aValidator.isValueValid (sLocal))
        {
          // At least one matching is fine for us
          return true;
        }
        bAtLeastOneSupported = true;
      }

    // None matched
    if (bAtLeastOneSupported)
      return false;

    // No validator found!
    return true;
  }
}
