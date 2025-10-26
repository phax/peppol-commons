package com.helger.hredelivery.commons.smp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.smpclient.bdxr1.BDXRClientReadOnly;
import com.helger.smpclient.url.SMPDNSResolutionException;

/**
 * Test class {@link HREDeliveryMPSClientHelper}.
 *
 * @author Philip Helger
 */
public class HREDeliveryMPSClientHelperTest
{
  @Test
  public void testResolveSMPDemo () throws SMPDNSResolutionException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9934:18683136487");

    final BDXRClientReadOnly aMPSCLient = HREDeliveryMPSClientHelper.createForDemo (aPI);
    final String sSMPHost = aMPSCLient.getSMPHostURI ();
    assertEquals ("https://cis.porezna-uprava.hr:8411/EracunMPSCT/", sSMPHost);
  }
}
