package com.helger.hredelivery.commons.url;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.helger.hredelivery.commons.EHREDeliverySML;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.smpclient.url.IBDXLURLProvider;
import com.helger.smpclient.url.SMPDNSResolutionException;

/**
 * Test class {@link HREDeliveryNaptrURLProvider}.
 *
 * @author Philip Helger
 */
public class HREDeliveryNaptrURLProviderTest
{
  @Test
  public void testResolveNameDemo () throws SMPDNSResolutionException
  {
    final IBDXLURLProvider aURLProvider = HREDeliveryNaptrURLProvider.INSTANCE;
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9934:18683136487");
    final String sDomain = aURLProvider.getDNSNameOfParticipant (aPI, EHREDeliverySML.DEMO);
    assertEquals ("yktnekuybobtefs3pxs5gbbgk24grlivhxrz52ayvi7pi34siagq.iso6523-actorid-upis.demo.ams.porezna-uprava.hr",
                  sDomain);
  }
}
