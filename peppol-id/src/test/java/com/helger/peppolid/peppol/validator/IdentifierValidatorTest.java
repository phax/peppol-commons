/*
 * Copyright (C) 2015-2024 Philip Helger
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.BiFunction;

import org.junit.Test;

import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.participant.PeppolParticipantIdentifier;
import com.helger.peppolid.peppol.pidscheme.EPredefinedParticipantIdentifierScheme;

/**
 * Test class for class {@link IdentifierValidator}.
 *
 * @author Philip Helger
 */
public final class IdentifierValidatorTest
{
  private static final BiFunction <EPredefinedParticipantIdentifierScheme, String, PeppolParticipantIdentifier> F = (e,
                                                                                                                     v) -> PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (e.createIdentifierValue (v));

  @SuppressWarnings ("deprecation")
  @Test
  public void testNorwayOrgNumber ()
  {
    assertFalse (IdentifierValidator.isValidParticipantIdentifier (F.apply (EPredefinedParticipantIdentifierScheme.NO_ORGNR,
                                                                            "")));
    assertFalse (IdentifierValidator.isValidParticipantIdentifier (F.apply (EPredefinedParticipantIdentifierScheme.NO_ORGNR,
                                                                            "123456789")));
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (F.apply (EPredefinedParticipantIdentifierScheme.NO_ORGNR,
                                                                           "123456785")));
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (F.apply (EPredefinedParticipantIdentifierScheme.NO_ORGNR,
                                                                           "968218743")));
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (F.apply (EPredefinedParticipantIdentifierScheme.NO_ORGNR,
                                                                           "961329310")));

    assertFalse (IdentifierValidator.isValidParticipantIdentifier (F.apply (EPredefinedParticipantIdentifierScheme.NO_VAT,
                                                                            "")));
    assertFalse (IdentifierValidator.isValidParticipantIdentifier (F.apply (EPredefinedParticipantIdentifierScheme.NO_VAT,
                                                                            "123456789")));
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (F.apply (EPredefinedParticipantIdentifierScheme.NO_VAT,
                                                                           "123456785")));
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (F.apply (EPredefinedParticipantIdentifierScheme.NO_VAT,
                                                                           "968218743")));
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (F.apply (EPredefinedParticipantIdentifierScheme.NO_VAT,
                                                                           "961329310")));
  }

  @Test
  public void testWithoutRules ()
  {
    // No special rules available -> all valid!
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (F.apply (EPredefinedParticipantIdentifierScheme.AD_VAT,
                                                                           "123456789")));
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (F.apply (EPredefinedParticipantIdentifierScheme.AD_VAT,
                                                                           "123456785")));
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (F.apply (EPredefinedParticipantIdentifierScheme.AD_VAT,
                                                                           "968218743")));
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (F.apply (EPredefinedParticipantIdentifierScheme.AD_VAT,
                                                                           "961329310")));
  }
}
