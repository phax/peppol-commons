package com.helger.hredelivery.commons.smp;

import java.security.GeneralSecurityException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.hredelivery.commons.EHREDeliverySML;
import com.helger.hredelivery.commons.url.HREDeliveryNaptrURLProvider;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.smpclient.bdxr1.BDXRClientReadOnly;
import com.helger.smpclient.url.SMPDNSResolutionException;

import jakarta.annotation.Nonnull;

/**
 * Special SMP client for HR eDelivery MPS servers.
 *
 * @author Philip Helger
 * @since 12.1.1
 */
public class HRMPSClientReadOnly extends BDXRClientReadOnly
{
  private static final Logger LOGGER = LoggerFactory.getLogger (HRMPSClientReadOnly.class);

  public HRMPSClientReadOnly (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                              @Nonnull final EHREDeliverySML aSMLInfo) throws SMPDNSResolutionException
  {
    // Constant URL provider
    super (HREDeliveryNaptrURLProvider.INSTANCE, aParticipantIdentifier, aSMLInfo);

    // Make sure to disable SSL server certificate checking
    try
    {
      httpClientSettings ().setSSLContextTrustAll ();
    }
    catch (final GeneralSecurityException ex)
    {
      LOGGER.error ("Error trusting all TLS server certificates", ex);
    }
  }
}
