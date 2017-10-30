/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
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

import java.security.cert.X509Certificate;

import javax.annotation.Nonnull;

import org.junit.Test;

import com.helger.peppol.identifier.factory.PeppolIdentifierFactory;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.identifier.peppol.doctype.EPredefinedDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.process.EPredefinedProcessIdentifier;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smp.ESMPTransportProfile;
import com.helger.peppol.url.IPeppolURLProvider;
import com.helger.peppol.url.PeppolURLProvider;

/**
 * Test class for class {@link SMPClient}.
 *
 * @author philip
 */
public final class SMPClientPredefinedEndpointAddressFuncTest
{
  private static final IParticipantIdentifier PI_AT_Test = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test");
  private static final IParticipantIdentifier PI_AT_Prod = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:b");
  private static final IPeppolURLProvider URL_PROVIDER = PeppolURLProvider.INSTANCE;

  static
  {
    // Ensure the network system properties are assigned
    SMPClientConfiguration.getConfigFile ().applyAllNetworkSystemProperties ();
  }

  @Nonnull
  private static SMPClient _createSMPClient (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                             @Nonnull final ISMLInfo aSMLInfo)
  {
    final SMPClient ret = new SMPClient (URL_PROVIDER, aParticipantIdentifier, aSMLInfo);
    return ret;
  }

  @Test
  public void testGetEndpointAddress () throws Throwable
  {
    String sEndpointAddress;

    sEndpointAddress = _createSMPClient (PI_AT_Test,
                                         ESML.DIGIT_TEST).getEndpointAddress (PI_AT_Test,
                                                                              EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS4A_V20,
                                                                              EPredefinedProcessIdentifier.BIS4A_V20,
                                                                              ESMPTransportProfile.TRANSPORT_PROFILE_AS2);
    assertEquals ("https://test.erechnung.gv.at/as2", sEndpointAddress);

    sEndpointAddress = _createSMPClient (PI_AT_Prod,
                                         ESML.DIGIT_PRODUCTION).getEndpointAddress (PI_AT_Prod,
                                                                                    EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS4A_V20,
                                                                                    EPredefinedProcessIdentifier.BIS4A_V20,
                                                                                    ESMPTransportProfile.TRANSPORT_PROFILE_AS2);
    assertEquals ("https://www.erechnung.gv.at/as2", sEndpointAddress);
  }

  /**
   * This test reads a live certificate and reads out the serial number. The
   * current certificates are valid from 03/2017 - 03/2019. If you run this test
   * afterwards and it fails, either fix the numbers or ignore the test.
   * 
   * @throws Throwable
   *         on error
   */
  @Test
  public void testGetEndpointCertificate () throws Throwable
  {
    X509Certificate aEndpointCertificate;

    aEndpointCertificate = _createSMPClient (PI_AT_Test,
                                             ESML.DIGIT_TEST).getEndpointCertificate (PI_AT_Test,
                                                                                      EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS4A_V20,
                                                                                      EPredefinedProcessIdentifier.BIS4A_V20,
                                                                                      ESMPTransportProfile.TRANSPORT_PROFILE_AS2);
    assertNotNull (aEndpointCertificate);
    assertEquals ("116734465033335658986050433351742789051", aEndpointCertificate.getSerialNumber ().toString ());

    aEndpointCertificate = _createSMPClient (PI_AT_Prod,
                                             ESML.DIGIT_PRODUCTION).getEndpointCertificate (PI_AT_Prod,
                                                                                            EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS4A_V20,
                                                                                            EPredefinedProcessIdentifier.BIS4A_V20,
                                                                                            ESMPTransportProfile.TRANSPORT_PROFILE_AS2);
    assertNotNull (aEndpointCertificate);
    assertEquals ("47242738920622652381827551755938905883", aEndpointCertificate.getSerialNumber ().toString ());
  }
}
