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
package com.helger.smpclient.bdxr2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import java.util.function.BiFunction;

import org.junit.Test;

import com.helger.datetime.helper.PDTFactory;
import com.helger.edelivery.smp.ISMPTransportProfile;
import com.helger.edelivery.smp.SMPTransportProfile;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.BDXR2IdentifierFactory;
import com.helger.smpclient.bdxr2.marshal.BDXR2MarshallerServiceMetadata;
import com.helger.xsds.bdxr.smp2.ServiceMetadataType;
import com.helger.xsds.bdxr.smp2.ac.EndpointType;

/**
 * Test class for class {@link BDXR2ClientReadOnly}.
 *
 * @author Philip Helger
 */
public final class BDXR2ClientReadOnlyTest
{
  private static final ISMPTransportProfile TP = new SMPTransportProfile ("bdxr-as4-1.0", "Test AS4");
  private static final IProcessIdentifier PROCESS_ID = BDXR2IdentifierFactory.INSTANCE.createProcessIdentifier ("bdx-procid-transport",
                                                                                                               "urn:test:process");

  /**
   * Build a ServiceMetadata with exactly one Endpoint, optionally carrying an ActivationDate and an
   * ExpirationDate.
   *
   * @param aActivation
   *        Activation date or <code>null</code> to omit the element.
   * @param aExpiration
   *        Expiration date or <code>null</code> to omit the element.
   * @return The serialized ServiceMetadata. Never <code>null</code>.
   */
  private static String _buildServiceMetadata (final LocalDate aActivation, final LocalDate aExpiration)
  {
    final StringBuilder aSB = new StringBuilder ();
    aSB.append ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
       .append ("<ServiceMetadata xmlns=\"http://docs.oasis-open.org/bdxr/ns/SMP/2/ServiceMetadata\"")
       .append (" xmlns:sma=\"http://docs.oasis-open.org/bdxr/ns/SMP/2/AggregateComponents\"")
       .append (" xmlns:smb=\"http://docs.oasis-open.org/bdxr/ns/SMP/2/BasicComponents\">")
       .append ("<smb:SMPVersionID>2.0</smb:SMPVersionID>")
       .append ("<smb:ID schemeID=\"bdx-docid-qns\">urn:test:doctype</smb:ID>")
       .append ("<smb:ParticipantID schemeID=\"iso6523-actorid-upis\">9915:test</smb:ParticipantID>")
       .append ("<sma:ProcessMetadata>")
       .append ("<sma:Process><smb:ID schemeID=\"bdx-procid-transport\">urn:test:process</smb:ID></sma:Process>")
       .append ("<sma:Endpoint>")
       .append ("<smb:TransportProfileID>bdxr-as4-1.0</smb:TransportProfileID>")
       .append ("<smb:AddressURI>https://example.org/as4</smb:AddressURI>");
    if (aActivation != null)
      aSB.append ("<smb:ActivationDate>").append (aActivation.toString ()).append ("</smb:ActivationDate>");
    if (aExpiration != null)
      aSB.append ("<smb:ExpirationDate>").append (aExpiration.toString ()).append ("</smb:ExpirationDate>");
    aSB.append ("</sma:Endpoint>").append ("</sma:ProcessMetadata>").append ("</ServiceMetadata>");
    return aSB.toString ();
  }

  @Test
  public void testIsEndpointValidAt ()
  {
    final LocalDate aDate = PDTFactory.createLocalDate (2026, Month.JUNE, 15);

    // No dates at all - always valid
    EndpointType aEndpoint = _readSingleEndpoint (null, null);
    assertTrue (BDXR2ClientReadOnly.isEndpointValidAt (aEndpoint, aDate));

    // Activation date is the check date - inclusive
    aEndpoint = _readSingleEndpoint (aDate, null);
    assertTrue (BDXR2ClientReadOnly.isEndpointValidAt (aEndpoint, aDate));
    assertFalse (BDXR2ClientReadOnly.isEndpointValidAt (aEndpoint, aDate.minusDays (1)));
    assertTrue (BDXR2ClientReadOnly.isEndpointValidAt (aEndpoint, aDate.plusDays (1)));

    // Expiration date is the check date - inclusive
    aEndpoint = _readSingleEndpoint (null, aDate);
    assertTrue (BDXR2ClientReadOnly.isEndpointValidAt (aEndpoint, aDate));
    assertTrue (BDXR2ClientReadOnly.isEndpointValidAt (aEndpoint, aDate.minusDays (1)));
    assertFalse (BDXR2ClientReadOnly.isEndpointValidAt (aEndpoint, aDate.plusDays (1)));

    // Both dates are the check date - valid on exactly one day
    aEndpoint = _readSingleEndpoint (aDate, aDate);
    assertTrue (BDXR2ClientReadOnly.isEndpointValidAt (aEndpoint, aDate));
    assertFalse (BDXR2ClientReadOnly.isEndpointValidAt (aEndpoint, aDate.minusDays (1)));
    assertFalse (BDXR2ClientReadOnly.isEndpointValidAt (aEndpoint, aDate.plusDays (1)));
  }

  private static EndpointType _readSingleEndpoint (final LocalDate aActivation, final LocalDate aExpiration)
  {
    final ServiceMetadataType aSM = new BDXR2MarshallerServiceMetadata ().setUseSchema (true)
                                                                         .read (_buildServiceMetadata (aActivation,
                                                                                                       aExpiration));
    assertNotNull (aSM);
    assertEquals (1, aSM.getProcessMetadataCount ());
    assertEquals (1, aSM.getProcessMetadataAtIndex (0).getEndpointCount ());
    return aSM.getProcessMetadataAtIndex (0).getEndpointAtIndex (0);
  }

  @Test
  public void testGetEndpointAt ()
  {
    final LocalDate aDate = PDTFactory.createLocalDate (2026, Month.JUNE, 15);

    final BiFunction <LocalDate, LocalDate, EndpointType> findEndpoint = (a, e) -> {
      final ServiceMetadataType aSM = new BDXR2MarshallerServiceMetadata ().setUseSchema (true)
                                                                           .read (_buildServiceMetadata (a, e));
      assertNotNull (aSM);
      return BDXR2ClientReadOnly.getEndpointAt (aSM, PROCESS_ID, TP, aDate);
    };

    // Valid cases - both dates are inclusive
    assertNotNull (findEndpoint.apply (null, null));
    assertNotNull (findEndpoint.apply (aDate, null));
    assertNotNull (findEndpoint.apply (null, aDate));
    assertNotNull (findEndpoint.apply (aDate, aDate));
    assertNotNull (findEndpoint.apply (aDate.minusDays (1), aDate.plusDays (1)));

    // Invalid cases - one day outside on either side is enough
    assertNull (findEndpoint.apply (aDate.plusDays (1), null));
    assertNull (findEndpoint.apply (null, aDate.minusDays (1)));
    assertNull (findEndpoint.apply (aDate.plusDays (1), aDate.minusDays (1)));
  }

  @Test
  public void testGetEndpointAtOtherProcessOrTransportProfile ()
  {
    final ServiceMetadataType aSM = new BDXR2MarshallerServiceMetadata ().setUseSchema (true)
                                                                         .read (_buildServiceMetadata (null, null));
    assertNotNull (aSM);
    final LocalDate aDate = PDTFactory.getCurrentLocalDate ();

    // The known combination is found
    assertNotNull (BDXR2ClientReadOnly.getEndpointAt (aSM, PROCESS_ID, TP, aDate));

    // An unknown process is not found
    final IProcessIdentifier aOtherProcID = BDXR2IdentifierFactory.INSTANCE.createProcessIdentifier ("bdx-procid-transport",
                                                                                                    "urn:test:other");
    assertNull (BDXR2ClientReadOnly.getEndpointAt (aSM, aOtherProcID, TP, aDate));

    // An unknown transport profile is not found
    final ISMPTransportProfile aOtherTP = new SMPTransportProfile ("other-tp", "Other");
    assertNull (BDXR2ClientReadOnly.getEndpointAt (aSM, PROCESS_ID, aOtherTP, aDate));
  }
}
