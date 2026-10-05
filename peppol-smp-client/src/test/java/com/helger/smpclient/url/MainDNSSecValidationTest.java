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
package com.helger.smpclient.url;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.dns.config.DNSConfig;
import com.helger.peppol.sml.ESML;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.smpclient.url.dns.PeppolNaptrURLProvider;

/**
 * Manual test for DNSSEC validated NAPTR lookups against the Peppol SML DNS zones. Requires network
 * access - therefore not a unit test.
 *
 * @author Philip Helger
 */
public final class MainDNSSecValidationTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainDNSSecValidationTest.class);

  public static void main (final String [] args)
  {
    final PeppolNaptrURLProvider aURLProvider = new PeppolNaptrURLProvider ();
    aURLProvider.setDnsSecValidation (true);
    // The DNS server must forward the DNSSEC records
    aURLProvider.customDNSServers ().add (DNSConfig.DNS_CLOUDFLARE_1);

    for (final ESML eSML : new ESML [] { ESML.PEPPOL_TEST, ESML.PEPPOL_PRODUCTION })
      for (final String sParticipantID : new String [] { "9915:test", "9999:this-is-not-registered" })
      {
        final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (sParticipantID);
        try
        {
          LOGGER.info ("[" + eSML.getID () + "] " + sParticipantID + " -> " + aURLProvider.getSMPURIOfParticipant (aPI, eSML));
        }
        catch (final SMPDNSResolutionException ex)
        {
          LOGGER.info ("[" + eSML.getID () + "] " + sParticipantID + " -> " + ex.getErrorCode () + ": " + ex.getMessage ());
        }
      }
  }
}
