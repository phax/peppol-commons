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

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.PresentForCodeCoverage;
import com.helger.base.spi.ServiceLoaderHelper;
import org.jspecify.annotations.NonNull;

import com.helger.base.enforce.ValueEnforcer;
import com.helger.peppolid.IParticipantIdentifier;

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
   * Check if the passed participant ID matches all custom rules, using the Peppol way of splitting
   * a participant identifier - see {@link PeppolParticipantIdentifierPartsProvider}.
   *
   * @param aParticipantID
   *        The participant ID to validate. May be <code>null</code>.
   * @return <code>true</code> if a) the identifier cannot be split, b) if at least one validator
   *         matched or c) if no matching validator was found at all. The method returns
   *         <code>false</code> if a matching validator was found, but the ID did not match.
   */
  public static boolean isValidParticipantIdentifier (@Nullable final IParticipantIdentifier aParticipantID)
  {
    return isValidParticipantIdentifier (aParticipantID, PeppolParticipantIdentifierPartsProvider.INSTANCE);
  }

  /**
   * Check if the passed participant ID matches all custom rules. Only identifiers that the provided
   * parts provider can split are validated, as the details of the other schemes are unknown.<br>
   * This method can be used to generically check the consistency of certain numbering schemes.
   *
   * @param aParticipantID
   *        The participant ID to validate. May be <code>null</code>.
   * @param aPartsProvider
   *        The strategy to split the participant identifier into issuing agency and local
   *        participant ID. May not be <code>null</code>.
   * @return <code>true</code> if a) the identifier cannot be split by the provided parts provider,
   *         b) if at least one validator matched or c) if no matching validator was found at all.
   *         The method returns <code>false</code> if a matching validator was found, but the ID did
   *         not match.
   */
  public static boolean isValidParticipantIdentifier (@Nullable final IParticipantIdentifier aParticipantID,
                                                      @NonNull final IParticipantIdentifierPartsProvider aPartsProvider)
  {
    ValueEnforcer.notNull (aPartsProvider, "PartsProvider");

    if (aParticipantID == null)
      return false;

    final ParticipantIdentifierParts aParts = aPartsProvider.getParts (aParticipantID);
    if (aParts == null)
    {
      // The identifier uses a scheme we know nothing about
      return true;
    }

    boolean bAtLeastOneSupported = false;
    final String sIssuingAgencyID = aParts.getIssuingAgencyID ();
    final String sLocal = aParts.getLocalParticipantID ();

    // For all SPI instances
    for (final IParticipantIdentifierValidatorSPI aValidator : PID_VALIDATOR)
      if (aValidator.isSupportedIssuingAgency (sIssuingAgencyID))
      {
        if (aValidator.isValueValid (sIssuingAgencyID, sLocal))
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
