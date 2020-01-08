/**
 * Copyright (C) 2015-2020 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.bdxrclient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

import com.helger.peppol.sml.ESML;
import com.helger.peppol.smpclient.exception.SMPClientException;
import com.helger.peppol.url.BDXLURLProvider;
import com.helger.peppol.url.PeppolDNSResolutionException;
import com.helger.peppol.url.PeppolURLProvider;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;

/**
 * Test class for class {@link BDXRClientReadOnly}
 *
 * @author Philip Helger
 */
public final class BDXRClientReadOnlyTest
{
  @Test
  @Ignore
  public void testGetBDXRHostURI () throws SMPClientException, PeppolDNSResolutionException
  {
    IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test");

    // PEPPOL URL provider
    BDXRClientReadOnly aBDXRClient = new BDXRClientReadOnly (PeppolURLProvider.INSTANCE, aPI, ESML.DIGIT_TEST);
    assertEquals ("http://B-85008b8279e07ab0392da75fa55856a2.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu/",
                  aBDXRClient.getSMPHostURI ());

    // E-SENS URL provider
    aBDXRClient = new BDXRClientReadOnly (BDXLURLProvider.INSTANCE, aPI, ESML.DIGIT_TEST);
    if (true)
      assertEquals ("http://test-infra.peppol.at/", aBDXRClient.getSMPHostURI ());
    else
      assertEquals ("http://BRZ-TEST-BDXR.publisher.acc.edelivery.tech.ec.europa.eu/", aBDXRClient.getSMPHostURI ());

    // This instance has a BOM inside
    aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9917:5504033150");
    aBDXRClient = new BDXRClientReadOnly (PeppolURLProvider.INSTANCE, aPI, ESML.DIGIT_PRODUCTION);

    // This fails because the PEPPOL server is configured for PEPPOL layout and
    // not OASIS!
    assertNotNull (aBDXRClient.getServiceGroupOrNull (aPI));
  }
}
