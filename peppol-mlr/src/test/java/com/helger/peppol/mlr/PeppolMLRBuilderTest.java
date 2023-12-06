/*
 * Copyright (C) 2023 Philip Helger
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
package com.helger.peppol.mlr;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.peppolid.factory.PeppolIdentifierFactory;

import oasis.names.specification.ubl.schema.xsd.applicationresponse_21.ApplicationResponseType;

/**
 * Test class for class {@link PeppolMLRBuilder}.
 *
 * @author Philip Helger
 */
public final class PeppolMLRBuilderTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolMLRBuilderTest.class);

  @Test
  public void testBasic ()
  {
    final PeppolIdentifierFactory aIF = PeppolIdentifierFactory.INSTANCE;
    final ApplicationResponseType aMLR = new PeppolMLRBuilder (EPeppolMLRState.ACCEPTANCE).randomID ()
                                                                                          .issueDateNow ()
                                                                                          .referenceId ("SBDH-12345")
                                                                                          .senderParticipantID (aIF.createParticipantIdentifierWithDefaultScheme ("9915:mlr-sender"))
                                                                                          .receiverParticipantID (aIF.createParticipantIdentifierWithDefaultScheme ("9915:mlr-receiver"))
                                                                                          .build ();
    assertNotNull (aMLR);

    LOGGER.info (new PeppolMLRMarshaller ().setFormattedOutput (true).getAsString (aMLR));
  }
}
