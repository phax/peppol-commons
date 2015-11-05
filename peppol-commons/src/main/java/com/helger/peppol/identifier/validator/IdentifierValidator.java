/**
 * Copyright (C) 2015 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.identifier.validator;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.lang.ServiceLoaderHelper;
import com.helger.peppol.identifier.CIdentifier;
import com.helger.peppol.identifier.participant.IPeppolParticipantIdentifier;

/**
 * A wrapper around the custom identifier validator implementations.
 *
 * @author philip
 */
@Immutable
public final class IdentifierValidator
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (IdentifierValidator.class);
  private static final List <IParticipantIdentifierValidatorSPI> s_aParticipantIDValidators;

  static
  {
    s_aParticipantIDValidators = ServiceLoaderHelper.getAllSPIImplementations (IParticipantIdentifierValidatorSPI.class);
    if (!s_aParticipantIDValidators.isEmpty ())
      s_aLogger.info ("Loaded " +
                      s_aParticipantIDValidators.size () +
                      " SPI implementations of IParticipantIdentifierValidatorSPI");
  }

  @SuppressWarnings ("unused")
  @PresentForCodeCoverage
  private static final IdentifierValidator s_aInstance = new IdentifierValidator ();

  private IdentifierValidator ()
  {}

  /**
   * Check if the passed participant ID matches all custom rules. But only
   * participant IDs with the default scheme
   * {@link CIdentifier#DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME} are validated, as
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
    if (!aParticipantID.isDefaultScheme ())
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
