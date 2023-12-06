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
