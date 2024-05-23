/*
 * Copyright (C) 2015-2024 Philip Helger
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
package com.helger.peppol.smlclient.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.net.InetAddress;
import java.util.Arrays;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.CNAMERecord;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.Type;

import com.helger.commons.collection.ArrayHelper;
import com.helger.commons.concurrent.ThreadHelper;
import com.helger.commons.lang.ClassHelper;
import com.helger.peppol.smlclient.AbstractSMLClientTestCase;
import com.helger.peppol.smlclient.ManageParticipantIdentifierServiceCaller;
import com.helger.peppol.smlclient.ManageServiceMetadataServiceCaller;
import com.helger.peppol.smlclient.smp.NotFoundFault;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.participant.PeppolParticipantIdentifier;
import com.helger.smpclient.url.PeppolURLProvider;

/**
 * This class is for BRZ internal use only!
 *
 * @author Philip Helger
 */
@Ignore ("Requires an SML with active DNS connection to be available")
public final class DNSRegistrationFuncTest extends AbstractSMLClientTestCase
{
  private static final Logger LOGGER = LoggerFactory.getLogger (DNSRegistrationFuncTest.class);

  /*
   * Wildcard user.
   */
  private static final String SMP_ID = "dns-test1";

  private static final String SMP_1_LOGICAL_ADDRESS = "http://mySMP.com";
  private static final String SMP_1_PHYSICAL_ADDRESS = "127.0.0.1";
  private static final String SMP_1_LOGICAL_ADDRESS_VALIDATION = "mySMP.com.";

  private static final String SMP_2_LOGICAL_ADDRESS = "http://mySMP2.com";
  private static final String SMP_2_PHYSICAL_ADDRESS = "127.0.0.1";
  private static final String SMP_2_LOGICAL_ADDRESS_VALIDATION = "mySMP2.com.";

  private static final String PI_VALUE = "0088:1111199991111";
  private static final String PI_SCHEME = "dns-actorid-test";
  private static final String PI_WILDCARD_SCHEME = "wildcard-actorid-allowed";

  private static final String INTERNAL_DNS_SERVER = "blixdns0";

  @Nullable
  private static String _DNSLookupPI (@Nonnull final IParticipantIdentifier aPI) throws Exception
  {
    final String sHost = PeppolURLProvider.INSTANCE.getDNSNameOfParticipant (aPI, SML_INFO);
    return _DNSLookup (sHost);
  }

  @Nullable
  private static String _DNSLookupPublisher (@Nonnull final String sSMPID) throws Exception
  {
    return _DNSLookup (sSMPID + "." + SML_INFO.getPublisherDNSZone ());
  }

  @Nullable
  private static String _DNSLookup (final String sHost) throws Exception
  {
    // Wait to let dns propagate : DNS TTL = 60 secs
    LOGGER.info ("Waiting 10 seconds to lookup '" + sHost + "'");
    ThreadHelper.sleepSeconds (10);

    final Lookup aDNSLookup = new Lookup (sHost, Type.ANY);
    aDNSLookup.setResolver (new SimpleResolver (INTERNAL_DNS_SERVER));
    aDNSLookup.setCache (null);

    final Record [] aRecords = aDNSLookup.run ();
    LOGGER.info ("Lookup returned [" + ArrayHelper.getSize (aRecords) + "]: " + Arrays.toString (aRecords));

    if (aRecords == null || aRecords.length == 0)
      return null;

    final Record aRecord = aRecords[0];
    if (aRecord instanceof CNAMERecord)
      return ((CNAMERecord) aRecord).getName ().toString ();

    if (aRecord instanceof ARecord)
    {
      final InetAddress aInetAddress = ((ARecord) aRecord).getAddress ();
      return aInetAddress.getHostAddress ();
    }

    LOGGER.info ("Unknown record type found: " + ClassHelper.getClassLocalName (aRecord));
    return aRecord.toString ();
  }

  @Before
  public void setupSMPBeforeTests () throws Exception
  {
    LOGGER.info ("Creating an SMP");
    try
    {
      final ManageServiceMetadataServiceCaller manageServiceMetaData = new ManageServiceMetadataServiceCaller (SML_INFO);

      manageServiceMetaData.create (SMP_ID, SMP_1_PHYSICAL_ADDRESS, SMP_1_LOGICAL_ADDRESS);
      LOGGER.info ("Created an SMP");
    }
    catch (final Exception ex)
    {
      LOGGER.error ("Failed: " + ex.getMessage ());
      throw ex;
    }
  }

  @After
  public void deleteSMPAfterTests () throws Exception
  {
    LOGGER.info ("Deleting an SMP");
    final ManageServiceMetadataServiceCaller manageServiceMetaData = new ManageServiceMetadataServiceCaller (SML_INFO);

    try
    {
      manageServiceMetaData.delete (SMP_ID);
      LOGGER.info ("Deleted an SMP");
    }
    catch (final NotFoundFault e)
    {
      // this is ok
    }
    catch (final Exception ex)
    {
      LOGGER.error ("Failed: " + ex.getMessage ());
      throw ex;
    }
  }

  // SMP

  @Test
  public void testVerifySMPInDNS () throws Exception
  {
    // @Before creates new SMP!

    // verify created
    final String publisher = _DNSLookupPublisher (SMP_ID);
    assertEquals (SMP_1_LOGICAL_ADDRESS_VALIDATION, publisher);

    // Update SML address
    final ManageServiceMetadataServiceCaller manageServiceMetaData = new ManageServiceMetadataServiceCaller (SML_INFO);

    manageServiceMetaData.update (SMP_ID, SMP_2_PHYSICAL_ADDRESS, SMP_2_LOGICAL_ADDRESS);

    // verify update
    final String updatedPublisher = _DNSLookupPublisher (SMP_ID);
    assertEquals (SMP_2_LOGICAL_ADDRESS_VALIDATION, updatedPublisher);

    // Delete SML
    manageServiceMetaData.delete (SMP_ID);

    // verify delete
    final String deletedPublisher = _DNSLookupPublisher (SMP_ID);
    assertNull (deletedPublisher);
  }

  // PI

  @Test
  public void testVerifyParticipantIdentifierInDNS () throws Exception
  {
    // @Before creates new SMP!

    // create PI
    final ManageParticipantIdentifierServiceCaller client = new ManageParticipantIdentifierServiceCaller (SML_INFO);
    final PeppolParticipantIdentifier aPI = new PeppolParticipantIdentifier (PeppolIdentifierFactory.INSTANCE,
                                                                             PI_SCHEME,
                                                                             PI_VALUE);
    client.create (SMP_ID, aPI);

    // verify PI in DNS
    final String host = _DNSLookupPI (aPI);
    assertEquals (SMP_ID + "." + SML_INFO.getPublisherDNSZone (), host);

    // delete PI
    client.delete (SMP_ID, aPI);

    final String deletedHost = _DNSLookupPI (aPI);
    assertNull (deletedHost);
  }

  // WildCard PI

  @Test
  public void testVerifyWildcardInDNS () throws Exception
  {
    // @Before creates new SMP!

    final ManageParticipantIdentifierServiceCaller client = new ManageParticipantIdentifierServiceCaller (SML_INFO);

    final PeppolParticipantIdentifier aPI = new PeppolParticipantIdentifier (PeppolIdentifierFactory.INSTANCE,
                                                                             PI_WILDCARD_SCHEME,
                                                                             "*");
    client.create (SMP_ID, aPI);

    // verify that PI can be found in Wildcard domain.
    final String piHost = _DNSLookupPI (new PeppolParticipantIdentifier (PeppolIdentifierFactory.INSTANCE,
                                                                         PI_WILDCARD_SCHEME,
                                                                         PI_VALUE));
    assertEquals (SMP_ID + "." + SML_INFO.getPublisherDNSZone (), piHost);

    // verify that Wildcard can be found
    final String wildHost = _DNSLookupPI (aPI);
    assertEquals (SMP_ID + "." + SML_INFO.getPublisherDNSZone (), wildHost);

    // delete wildcard
    client.delete (SMP_ID, aPI);

    // verify deleted
    final String deletedHost = _DNSLookupPI (aPI);
    assertNull (deletedHost);
  }
}
