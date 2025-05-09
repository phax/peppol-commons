/*
 * Copyright (C) 2025 Philip Helger
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
package com.helger.peppol.mls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.peppolid.factory.PeppolIdentifierFactory;

import oasis.names.specification.ubl.schema.xsd.applicationresponse_21.ApplicationResponseType;

/**
 * Test class for class {@link PeppolMLSBuilder}.
 *
 * @author Philip Helger
 */
public final class PeppolMLSBuilderTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolMLSBuilderTest.class);

  @Test
  public void testMinimalNoLine ()
  {
    final PeppolIdentifierFactory aIF = PeppolIdentifierFactory.INSTANCE;
    final ApplicationResponseType aMLS = PeppolMLSBuilder.acceptance ()
                                                         .referenceId ("SBDH-12345")
                                                         .senderParticipantID (aIF.createParticipantIdentifierWithDefaultScheme ("9915:mls-sender"))
                                                         .receiverParticipantID (aIF.createParticipantIdentifierWithDefaultScheme ("9915:mls-receiver"))
                                                         .build ();
    assertNotNull (aMLS);
    final String sMLS1 = new PeppolMLSMarshaller ().getAsString (aMLS);
    assertNotNull (sMLS1);

    // Check with reverse Builder
    final ApplicationResponseType aMLS2 = PeppolMLSBuilder.createForApplicationResponse (aMLS).build ();
    final String sMLS2 = new PeppolMLSMarshaller ().getAsString (aMLS2);
    assertEquals (sMLS1, sMLS2);

    if (false)
      LOGGER.info (new PeppolMLSMarshaller ().setFormattedOutput (true).getAsString (aMLS));
    if (false)
      new PeppolMLSMarshaller ().setFormattedOutput (true).write (aMLS, new File ("generated/mls1.xml"));
  }

  @Test
  public void testMinimalWithOneLine ()
  {
    final PeppolIdentifierFactory aIF = PeppolIdentifierFactory.INSTANCE;
    final ApplicationResponseType aMLS = PeppolMLSBuilder.rejection ()
                                                         .referenceId ("SBDH-12345")
                                                         .senderParticipantID (aIF.createParticipantIdentifierWithDefaultScheme ("9915:mls-sender"))
                                                         .receiverParticipantID (aIF.createParticipantIdentifierWithDefaultScheme ("9915:mls-receiver"))
                                                         .addLineResponse (PeppolMLSLineResponseBuilder.rejection ()
                                                                                                       .errorField ("Invoice/ID")
                                                                                                       .statusReasonCodeBusinessRuleViolationFatal ()
                                                                                                       .description ("The ID seems to be missing"))
                                                         .build ();
    assertNotNull (aMLS);

    final String sMLS1 = new PeppolMLSMarshaller ().getAsString (aMLS);
    assertNotNull (sMLS1);

    // Check with reverse Builder
    final ApplicationResponseType aMLS2 = PeppolMLSBuilder.createForApplicationResponse (aMLS).build ();
    final String sMLS2 = new PeppolMLSMarshaller ().getAsString (aMLS2);
    assertEquals (sMLS1, sMLS2);

    if (false)
      LOGGER.info (new PeppolMLSMarshaller ().setFormattedOutput (true).getAsString (aMLS));
    if (false)
      new PeppolMLSMarshaller ().setFormattedOutput (true).write (aMLS, new File ("generated/mls2.xml"));
  }
}
