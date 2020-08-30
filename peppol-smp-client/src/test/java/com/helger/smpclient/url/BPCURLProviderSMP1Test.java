package com.helger.smpclient.url;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

import com.helger.peppolid.simple.participant.SimpleParticipantIdentifier;

/**
 * Test class for class {@link BPCURLProviderSMP2}.
 *
 * @author Philip Helger
 */
public final class BPCURLProviderSMP1Test
{
  @Test
  public void testBasic () throws PeppolDNSResolutionException
  {
    final SimpleParticipantIdentifier aPI = new SimpleParticipantIdentifier ("urn:oasis:names:tc:ebcore:partyid-type:iso6523:0060",
                                                                             "123456789");

    final String s = BPCURLProviderSMP2.INSTANCE.getDNSNameOfParticipant (aPI, "bpc02.b2bei.us.");
    assertEquals ("64YIBI3W4XKI6USPE6LXNMDAXTO3EEZHPD3UQWEB7SYBMYVFRNHA.bpc02.b2bei.us", s);

    final URI x = BPCURLProviderSMP2.INSTANCE.getSMPURIOfParticipant (aPI, "bpc02.b2bei.us.");
    assertEquals ("https://bpc-smp.bdxhub.com/", x.toString ());
  }
}
