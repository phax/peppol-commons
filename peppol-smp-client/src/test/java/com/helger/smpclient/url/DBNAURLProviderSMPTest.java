/*
 * Copyright (C) 2015-2025 Philip Helger
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
package com.helger.smpclient.url;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

import com.helger.dbnalliance.commons.EDBNAllianceSML;
import com.helger.peppolid.simple.participant.SimpleParticipantIdentifier;

/**
 * Test class for class {@link DBNAURLProviderSMP}.
 *
 * @author Philip Helger
 */
public final class DBNAURLProviderSMPTest
{
  @Test
  public void testGetDnsNameOfExample () throws SMPDNSResolutionException
  {
    final SimpleParticipantIdentifier aPI = new SimpleParticipantIdentifier ("GLN", "1234567890123");

    final String s = DBNAURLProviderSMP.INSTANCE.getDNSNameOfParticipant (aPI, EDBNAllianceSML.TEST.getZoneName ());
    assertEquals ("QCIE7F2NY3ZE5NMHQSE7Z5J6JERDS3GC437BFJL2K6VQ6MINB47A.sml.dbnalliance.com", s);
  }

  @Test
  public void testGetDnsNameStorecove () throws SMPDNSResolutionException
  {
    final SimpleParticipantIdentifier aPI = new SimpleParticipantIdentifier ("GLN", "1200109963131");

    final String s = DBNAURLProviderSMP.INSTANCE.getDNSNameOfParticipant (aPI, EDBNAllianceSML.TEST.getZoneName ());
    assertEquals ("7TPLWXYHOFKMPVRL5KHYXHCE5AFZMHLTHDBVEE2EQ43ZMUNZKYXA.sml.dbnalliance.com", s);
  }

  @Test
  public void testGetSMPURIOfStorecove () throws SMPDNSResolutionException
  {
    final SimpleParticipantIdentifier aPI = new SimpleParticipantIdentifier ("GLN", "1200109963131");
    final URI x = DBNAURLProviderSMP.INSTANCE.getSMPURIOfParticipant (aPI, EDBNAllianceSML.TEST.getZoneName ());
    assertEquals ("https://smo.dbna.storecove.com/", x.toString ());
  }

  @Test
  public void testGetSMPURIOfAvalara () throws SMPDNSResolutionException
  {
    final SimpleParticipantIdentifier aPI = new SimpleParticipantIdentifier ("DUNS", "079961550");
    final URI x = DBNAURLProviderSMP.INSTANCE.getSMPURIOfParticipant (aPI, EDBNAllianceSML.TEST.getZoneName ());
    assertEquals ("https://einvoicing.qa.avalara.io/", x.toString ());
  }

  @Test
  public void testGetSMPURIOfPilotSMP () throws SMPDNSResolutionException
  {
    final SimpleParticipantIdentifier aPI = new SimpleParticipantIdentifier ("DUNS", "556678558");
    final URI x = DBNAURLProviderSMP.INSTANCE.getSMPURIOfParticipant (aPI, EDBNAllianceSML.PILOT.getZoneName ());
    assertEquals ("https://smp.dbnalliancepilot.net/", x.toString ());
  }
}
