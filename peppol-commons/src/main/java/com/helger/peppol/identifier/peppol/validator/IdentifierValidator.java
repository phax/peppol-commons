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
package com.helger.peppol.identifier.peppol.validator;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.lang.ServiceLoaderHelper;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;
import com.helger.peppol.identifier.peppol.participant.IPeppolParticipantIdentifier;

/**
 * A wrapper around the custom identifier validator implementations.
 *
 * @author Philip Helger
 */
@Immutable
public final class IdentifierValidator
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (IdentifierValidator.class);
  private static final ICommonsList <IParticipantIdentifierValidatorSPI> s_aParticipantIDValidators;

  static
  {
    s_aParticipantIDValidators = ServiceLoaderHelper.getAllSPIImplementations (IParticipantIdentifierValidatorSPI.class);
    if (s_aParticipantIDValidators.isNotEmpty ())
      s_aLogger.info ("Loaded " +
                      s_aParticipantIDValidators.size () +
                      " SPI implementations of IParticipantIdentifierValidatorSPI");
  }

  @PresentForCodeCoverage
  private static final IdentifierValidator s_aInstance = new IdentifierValidator ();

  private IdentifierValidator ()
  {}

  /**
   * Check if the passed participant ID matches all custom rules. But only
   * participant IDs with the default scheme
   * {@link PeppolIdentifierHelper#DEFAULT_PARTICIPANT_SCHEME} are validated, as
   * we don't know the details of the other schemes.<br>
   * This method can be used to generically check the consistency of certain
   * numbering schemes.
   *
   * @param aParticipantID
   *        The participant ID to validate. May not be <code>null</code>.
   * @return <code>true</code> if a) the identifier is not the default scheme,
   *         b) if at least one validator matched or c) if no matching validator
   *         was found at all. The method returns <code>false</code> if a
   *         matching validator was found, but the ID did not match.
   */
  public static boolean isValidParticipantIdentifier (@Nonnull final IPeppolParticipantIdentifier aParticipantID)
  {
    ValueEnforcer.notNull (aParticipantID, "ParticipantID");

    // Only validate our default scheme
    if (!aParticipantID.hasDefaultScheme ())
      return true;

    boolean bAtLeastOneSupported = false;
    final String sIssuingAgencyID = aParticipantID.getIssuingAgencyID ();
    final String sLocal = aParticipantID.getLocalParticipantID ();

    // For all SPI instances
    for (final IParticipantIdentifierValidatorSPI aValidator : s_aParticipantIDValidators)
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
