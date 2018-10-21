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
package com.helger.peppol.smpclient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.helger.peppol.identifier.factory.PeppolIdentifierFactory;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.smpclient.exception.SMPClientException;
import com.helger.peppol.url.EsensURLProvider;
import com.helger.peppol.url.PeppolDNSResolutionException;
import com.helger.peppol.url.PeppolURLProvider;

/**
 * Test class for class {@link SMPClientReadOnly}
 *
 * @author Philip Helger
 */
public final class SMPClientReadOnlyTest
{
  @Test
  public void testGetSMPHostURI () throws SMPClientException, PeppolDNSResolutionException
  {
    IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test");

    // PEPPOL URL provider
    SMPClientReadOnly aSMPClient = new SMPClientReadOnly (PeppolURLProvider.INSTANCE, aPI, ESML.DIGIT_TEST);
    assertEquals ("http://B-85008b8279e07ab0392da75fa55856a2.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu/",
                  aSMPClient.getSMPHostURI ());

    // E-SENS URL provider
    aSMPClient = new SMPClientReadOnly (EsensURLProvider.INSTANCE, aPI, ESML.DIGIT_TEST);
    if (true)
      assertEquals ("http://test-infra.peppol.at/", aSMPClient.getSMPHostURI ());
    else
      assertEquals ("http://BRZ-TEST-SMP.publisher.acc.edelivery.tech.ec.europa.eu/", aSMPClient.getSMPHostURI ());

    // This instance has a BOM inside
    aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9917:5504033150");
    aSMPClient = new SMPClientReadOnly (PeppolURLProvider.INSTANCE, aPI, ESML.DIGIT_PRODUCTION);
    assertNotNull (aSMPClient.getServiceGroupOrNull (aPI));
  }
}
