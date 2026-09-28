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
package com.helger.smpclient.bdxr1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.function.BiFunction;

import org.junit.Test;

import com.helger.datetime.helper.PDTFactory;
import com.helger.datetime.web.PDTWebDateHelper;
import com.helger.edelivery.smp.ISMPTransportProfile;
import com.helger.edelivery.smp.SMPTransportProfile;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.BDXR1IdentifierFactory;
import com.helger.smpclient.bdxr1.marshal.BDXR1MarshallerServiceMetadataType;
import com.helger.xsds.bdxr.smp1.EndpointType;
import com.helger.xsds.bdxr.smp1.ServiceMetadataType;

/**
 * Test class for class {@link BDXRClientReadOnly}.
 *
 * @author Philip Helger
 */
public final class BDXRClientReadOnlyTest
{
  private static final ISMPTransportProfile TP = new SMPTransportProfile ("bdxr-transport-ebms3-as4-v1p0", "Test AS4");
  private static final IProcessIdentifier PROCESS_ID = BDXR1IdentifierFactory.INSTANCE.createProcessIdentifier ("bdx-procid-transport",
                                                                                                               "urn:test:process");

  /**
   * Build a ServiceMetadata with exactly one Endpoint, optionally carrying a ServiceActivationDate
   * and a ServiceExpirationDate.
   *
   * @param aActivation
   *        Activation date and time or <code>null</code> to omit the element.
   * @param aExpiration
   *        Expiration date and time or <code>null</code> to omit the element.
   * @return The serialized ServiceMetadata. Never <code>null</code>.
   */
  private static String _buildServiceMetadata (final LocalDateTime aActivation, final LocalDateTime aExpiration)
  {
    final StringBuilder aSB = new StringBuilder ();
    aSB.append ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
       .append ("<ServiceMetadata xmlns=\"http://docs.oasis-open.org/bdxr/ns/SMP/2016/05\">")
       .append ("<ServiceInformation>")
       .append ("<ParticipantIdentifier scheme=\"iso6523-actorid-upis\">9915:test</ParticipantIdentifier>")
       .append ("<DocumentIdentifier scheme=\"bdx-docid-qns\">urn:test:doctype</DocumentIdentifier>")
       .append ("<ProcessList><Process>")
       .append ("<ProcessIdentifier scheme=\"bdx-procid-transport\">urn:test:process</ProcessIdentifier>")
       .append ("<ServiceEndpointList>")
       .append ("<Endpoint transportProfile=\"bdxr-transport-ebms3-as4-v1p0\">")
       .append ("<EndpointURI>https://example.org/as4</EndpointURI>");
    if (aActivation != null)
      aSB.append ("<ServiceActivationDate>")
         .append (PDTWebDateHelper.getAsStringXSD (aActivation))
         .append ("</ServiceActivationDate>");
    if (aExpiration != null)
      aSB.append ("<ServiceExpirationDate>")
         .append (PDTWebDateHelper.getAsStringXSD (aExpiration))
         .append ("</ServiceExpirationDate>");
    aSB.append ("<Certificate>QQ==</Certificate>")
       .append ("<ServiceDescription>Test AP</ServiceDescription>")
       .append ("<TechnicalContactUrl>mailto:test@example.org</TechnicalContactUrl>")
       .append ("</Endpoint>")
       .append ("</ServiceEndpointList>")
       .append ("</Process></ProcessList>")
       .append ("</ServiceInformation>")
       .append ("</ServiceMetadata>");
    return aSB.toString ();
  }

  private static EndpointType _readSingleEndpoint (final LocalDateTime aActivation, final LocalDateTime aExpiration)
  {
    final ServiceMetadataType aSM = new BDXR1MarshallerServiceMetadataType ().setUseSchema (true)
                                                                            .read (_buildServiceMetadata (aActivation,
                                                                                                          aExpiration));
    assertNotNull (aSM);
    assertNotNull (aSM.getServiceInformation ());
    assertEquals (1, aSM.getServiceInformation ().getProcessList ().getProcessCount ());
    return aSM.getServiceInformation ()
              .getProcessList ()
              .getProcessAtIndex (0)
              .getServiceEndpointList ()
              .getEndpointAtIndex (0);
  }

  @Test
  public void testIsEndpointValidAt ()
  {
    // XSD is limited to milliseconds precision
    final LocalDateTime aDT = PDTFactory.getCurrentLocalDateTimeMillisOnly ();

    // No dates at all - always valid
    EndpointType aEndpoint = _readSingleEndpoint (null, null);
    assertTrue (BDXRClientReadOnly.isEndpointValidAt (aEndpoint, aDT));

    // Activation date is the check date and time - inclusive
    aEndpoint = _readSingleEndpoint (aDT, null);
    assertTrue (BDXRClientReadOnly.isEndpointValidAt (aEndpoint, aDT));
    assertFalse (BDXRClientReadOnly.isEndpointValidAt (aEndpoint, aDT.minusSeconds (1)));
    assertTrue (BDXRClientReadOnly.isEndpointValidAt (aEndpoint, aDT.plusSeconds (1)));

    // Expiration date is the check date and time - inclusive
    aEndpoint = _readSingleEndpoint (null, aDT);
    assertTrue (BDXRClientReadOnly.isEndpointValidAt (aEndpoint, aDT));
    assertTrue (BDXRClientReadOnly.isEndpointValidAt (aEndpoint, aDT.minusSeconds (1)));
    assertFalse (BDXRClientReadOnly.isEndpointValidAt (aEndpoint, aDT.plusSeconds (1)));
  }

  @Test
  public void testGetEndpointAt ()
  {
    // XSD is limited to milliseconds precision
    final LocalDateTime aDT = PDTFactory.getCurrentLocalDateTimeMillisOnly ();

    final BiFunction <LocalDateTime, LocalDateTime, EndpointType> findEndpoint = (a, e) -> {
      final ServiceMetadataType aSM = new BDXR1MarshallerServiceMetadataType ().setUseSchema (true)
                                                                              .read (_buildServiceMetadata (a, e));
      assertNotNull (aSM);
      return BDXRClientReadOnly.getEndpointAt (aSM, PROCESS_ID, TP, aDT);
    };

    // Valid cases - both dates are inclusive
    assertNotNull (findEndpoint.apply (null, null));
    assertNotNull (findEndpoint.apply (aDT, null));
    assertNotNull (findEndpoint.apply (null, aDT));
    assertNotNull (findEndpoint.apply (aDT, aDT));
    assertNotNull (findEndpoint.apply (aDT.minusDays (1), aDT.plusDays (1)));

    // Invalid cases
    assertNull (findEndpoint.apply (aDT.plusSeconds (1), null));
    assertNull (findEndpoint.apply (null, aDT.minusSeconds (1)));
    assertNull (findEndpoint.apply (aDT.plusSeconds (1), aDT.minusSeconds (1)));
  }
}
