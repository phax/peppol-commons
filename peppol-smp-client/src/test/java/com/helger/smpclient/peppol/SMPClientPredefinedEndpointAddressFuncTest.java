/*
 * Copyright (C) 2015-2021 Philip Helger
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
package com.helger.smpclient.peppol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.cert.X509Certificate;

import javax.annotation.Nonnull;

import org.junit.ClassRule;
import org.junit.Test;

import com.helger.peppol.sml.ESML;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smp.ESMPTransportProfile;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.doctype.EPredefinedDocumentTypeIdentifier;
import com.helger.peppolid.peppol.process.EPredefinedProcessIdentifier;
import com.helger.smpclient.url.IPeppolURLProvider;
import com.helger.smpclient.url.PeppolURLProvider;
import com.helger.smpclient.url.SMPDNSResolutionException;

/**
 * Test class for class {@link SMPClient}.
 *
 * @author Philip Helger
 */
public final class SMPClientPredefinedEndpointAddressFuncTest
{
  @ClassRule
  public static final SMPClientTestConfigRule RULE = new SMPClientTestConfigRule ();

  private static final IParticipantIdentifier PI_AT_TEST = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:test");
  private static final IParticipantIdentifier PI_AT_PROD = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9915:b");
  private static final IPeppolURLProvider URL_PROVIDER = PeppolURLProvider.INSTANCE;

  @Nonnull
  private static SMPClient _createSMPClient (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                             @Nonnull final ISMLInfo aSMLInfo) throws SMPDNSResolutionException
  {
    return new SMPClient (URL_PROVIDER, aParticipantIdentifier, aSMLInfo);
  }

  @Test
  public void testGetEndpointAddress () throws Exception
  {
    String sEndpointAddress;

    sEndpointAddress = _createSMPClient (PI_AT_TEST,
                                         ESML.DIGIT_TEST).getEndpointAddress (PI_AT_TEST,
                                                                              EPredefinedDocumentTypeIdentifier.INVOICE_EN16931_PEPPOL_V30,
                                                                              EPredefinedProcessIdentifier.BIS3_BILLING,
                                                                              ESMPTransportProfile.TRANSPORT_PROFILE_PEPPOL_AS4_V2);
    assertEquals ("https://test.erechnung.gv.at/as4", sEndpointAddress);

    sEndpointAddress = _createSMPClient (PI_AT_PROD,
                                         ESML.DIGIT_PRODUCTION).getEndpointAddress (PI_AT_PROD,
                                                                                    EPredefinedDocumentTypeIdentifier.INVOICE_EN16931_PEPPOL_V30,
                                                                                    EPredefinedProcessIdentifier.BIS3_BILLING,
                                                                                    ESMPTransportProfile.TRANSPORT_PROFILE_PEPPOL_AS4_V2);
    assertEquals ("https://www.erechnung.gv.at/as4", sEndpointAddress);
  }

  /**
   * This test reads a live certificate and reads out the serial number. The
   * current certificates are valid until March 2021. If you run this test
   * afterwards and it fails, either fix the numbers or ignore the test.
   *
   * @throws Exception
   *         on error
   */
  @Test
  public void testGetEndpointCertificate () throws Exception
  {
    X509Certificate aEndpointCertificate;

    aEndpointCertificate = _createSMPClient (PI_AT_TEST,
                                             ESML.DIGIT_TEST).getEndpointCertificate (PI_AT_TEST,
                                                                                      EPredefinedDocumentTypeIdentifier.INVOICE_EN16931_PEPPOL_V30,
                                                                                      EPredefinedProcessIdentifier.BIS3_BILLING,
                                                                                      ESMPTransportProfile.TRANSPORT_PROFILE_PEPPOL_AS4_V2);
    assertNotNull (aEndpointCertificate);
    // Updated February 2021
    assertEquals ("143028143061865284867902645043809287292", aEndpointCertificate.getSerialNumber ().toString ());

    aEndpointCertificate = _createSMPClient (PI_AT_PROD,
                                             ESML.DIGIT_PRODUCTION).getEndpointCertificate (PI_AT_PROD,
                                                                                            EPredefinedDocumentTypeIdentifier.INVOICE_EN16931_PEPPOL_V30,
                                                                                            EPredefinedProcessIdentifier.BIS3_BILLING,
                                                                                            ESMPTransportProfile.TRANSPORT_PROFILE_PEPPOL_AS4_V2);
    assertNotNull (aEndpointCertificate);
    // Updated February 2021
    assertEquals ("79389956454415861562211383723980662233", aEndpointCertificate.getSerialNumber ().toString ());
  }
}
