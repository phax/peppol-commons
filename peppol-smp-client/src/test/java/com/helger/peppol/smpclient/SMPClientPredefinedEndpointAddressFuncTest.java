/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
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
package com.helger.peppol.smpclient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.cert.X509Certificate;

import javax.annotation.Nonnull;

import org.junit.Test;

import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.identifier.generic.participant.SimpleParticipantIdentifier;
import com.helger.peppol.identifier.peppol.doctype.EPredefinedDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.process.EPredefinedProcessIdentifier;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smp.ESMPTransportProfile;

/**
 * Test class for class {@link SMPClient}.
 *
 * @author philip
 */
public final class SMPClientPredefinedEndpointAddressFuncTest
{
  private static IParticipantIdentifier PI_AT_Test = SimpleParticipantIdentifier.createWithDefaultScheme ("9915:test");
  private static IParticipantIdentifier PI_AT_Prod = SimpleParticipantIdentifier.createWithDefaultScheme ("9915:b");

  static
  {
    // Ensure the network system properties are assigned
    SMPClientConfiguration.getConfigFile ().applyAllNetworkSystemProperties ();
  }

  @Nonnull
  private static SMPClient _createSMPClient (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                             @Nonnull final ISMLInfo aSMLInfo)
  {
    final SMPClient ret = new SMPClient (aParticipantIdentifier, aSMLInfo);
    return ret;
  }

  @Test
  public void testGetEndpointAddress () throws Throwable
  {
    String sEndpointAddress;

    sEndpointAddress = _createSMPClient (PI_AT_Test,
                                         ESML.DIGIT_PRODUCTION).getEndpointAddress (PI_AT_Test,
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

  @Test
  public void testGetEndpointCertificate () throws Throwable
  {
    X509Certificate aEndpointCertificate;

    aEndpointCertificate = _createSMPClient (PI_AT_Test,
                                             ESML.DIGIT_PRODUCTION).getEndpointCertificate (PI_AT_Test,
                                                                                            EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS4A_V20,
                                                                                            EPredefinedProcessIdentifier.BIS4A_V20,
                                                                                            ESMPTransportProfile.TRANSPORT_PROFILE_AS2);
    assertNotNull (aEndpointCertificate);
    assertEquals ("84830725788388898952694772524159165036", aEndpointCertificate.getSerialNumber ().toString ());

    aEndpointCertificate = _createSMPClient (PI_AT_Prod,
                                             ESML.DIGIT_PRODUCTION).getEndpointCertificate (PI_AT_Prod,
                                                                                            EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS4A_V20,
                                                                                            EPredefinedProcessIdentifier.BIS4A_V20,
                                                                                            ESMPTransportProfile.TRANSPORT_PROFILE_AS2);
    assertNotNull (aEndpointCertificate);
    assertEquals ("84830725788388898952694772524159165036", aEndpointCertificate.getSerialNumber ().toString ());
  }
}
