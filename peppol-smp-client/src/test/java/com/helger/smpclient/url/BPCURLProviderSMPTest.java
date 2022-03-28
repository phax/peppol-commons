/*
 * Copyright (C) 2015-2022 Philip Helger
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

import org.junit.Ignore;
import org.junit.Test;

import com.helger.peppolid.simple.participant.SimpleParticipantIdentifier;
import com.helger.smpclient.IgnoredNaptrTest;

/**
 * Test class for class {@link BPCURLProviderSMP}.
 *
 * @author Philip Helger
 */
public final class BPCURLProviderSMPTest
{
  @Test
  public void testGetDNSNameOfParticipant () throws SMPDNSResolutionException
  {
    final SimpleParticipantIdentifier aPI = new SimpleParticipantIdentifier ("urn:oasis:names:tc:ebcore:partyid-type:iso6523:0060",
                                                                             "123456789");

    final String s = BPCURLProviderSMP.INSTANCE.getDNSNameOfParticipant (aPI, "bpc02.b2bei.us.");
    assertEquals ("64YIBI3W4XKI6USPE6LXNMDAXTO3EEZHPD3UQWEB7SYBMYVFRNHA.bpc02.b2bei.us", s);
  }

  @Test
  public void testGetDNSNameOfParticipant2 () throws SMPDNSResolutionException
  {
    final SimpleParticipantIdentifier aPI = new SimpleParticipantIdentifier ("urn:oasis:names:tc:ebcore:partyid-type:unregistered:disney",
                                                                             "goofy");

    final String s = BPCURLProviderSMP.INSTANCE.getDNSNameOfParticipant (aPI, "bpc02.b2bei.us.");
    assertEquals ("F64Y2CXXVX4VJF4E45OQVE6RUZML7AWG4SN4NKTYXLJSZEVUQKCQ.bpc02.b2bei.us", s);
  }

  @Test
  @Ignore ("Will not work, because no SML domain is available")
  @IgnoredNaptrTest
  public void testGetSMPURIOfParticipant () throws SMPDNSResolutionException
  {
    final SimpleParticipantIdentifier aPI = new SimpleParticipantIdentifier ("urn:oasis:names:tc:ebcore:partyid-type:iso6523:0060",
                                                                             "123456789");
    final URI x = BPCURLProviderSMP.INSTANCE.getSMPURIOfParticipant (aPI, "bpc02.b2bei.us.");
    assertEquals ("https://bpc-smp.bdxhub.com/", x.toString ());
  }
}
