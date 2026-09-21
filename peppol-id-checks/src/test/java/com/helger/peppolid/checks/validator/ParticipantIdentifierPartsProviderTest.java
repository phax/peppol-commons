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
package com.helger.peppolid.checks.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.SimpleIdentifierFactory;

/**
 * Test class for class {@link PeppolParticipantIdentifierPartsProvider} and the pluggability of
 * {@link IParticipantIdentifierPartsProvider}.
 *
 * @author Philip Helger
 */
public final class ParticipantIdentifierPartsProviderTest
{
  @Test
  public void testPeppolProvider ()
  {
    final IParticipantIdentifier aPID = SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("iso6523-actorid-upis",
                                                                                                      "9908:976098897");
    assertNotNull (aPID);
    final ParticipantIdentifierParts aParts = PeppolParticipantIdentifierPartsProvider.INSTANCE.getParts (aPID);
    assertNotNull (aParts);
    assertEquals ("9908", aParts.getIssuingAgencyID ());
    assertEquals ("976098897", aParts.getLocalParticipantID ());
  }

  @Test
  public void testPeppolProviderOtherScheme ()
  {
    // A scheme the Peppol provider knows nothing about
    final IParticipantIdentifier aPID = SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("email-rfc5322",
                                                                                                      "office@example.org");
    assertNotNull (aPID);
    assertNull (PeppolParticipantIdentifierPartsProvider.INSTANCE.getParts (aPID));
    // Nothing is known, so nothing is rejected
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (aPID));
  }

  @Test
  public void testOwnProvider ()
  {
    // A network that splits at the first hyphen instead
    final IParticipantIdentifierPartsProvider aProvider = aPID -> {
      final int n = aPID.getValue ().indexOf ('-');
      if (n <= 0 || n == aPID.getValue ().length () - 1)
        return null;
      return new ParticipantIdentifierParts (aPID.getValue ().substring (0, n),
                                             aPID.getValue ().substring (n + 1));
    };

    // 9908 is the Norwegian organisation number agency - the check digit is wrong here
    final IParticipantIdentifier aPID = SimpleIdentifierFactory.INSTANCE.createParticipantIdentifier ("whatever",
                                                                                                      "9908-976098898");
    assertNotNull (aPID);
    assertFalse (IdentifierValidator.isValidParticipantIdentifier (aPID, aProvider));
    // Without the provider the identifier cannot be split, so it is not rejected
    assertTrue (IdentifierValidator.isValidParticipantIdentifier (aPID));
  }
}
