/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
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
import com.helger.commons.lang.ClassHelper;
import com.helger.peppol.identifier.ParticipantIdentifierType;
import com.helger.peppol.identifier.peppol.participant.PeppolParticipantIdentifier;
import com.helger.peppol.smlclient.AbstractSMLClientTestCase;
import com.helger.peppol.smlclient.ManageParticipantIdentifierServiceCaller;
import com.helger.peppol.smlclient.ManageServiceMetadataServiceCaller;
import com.helger.peppol.smlclient.smp.NotFoundFault;
import com.helger.peppol.url.PeppolURLProvider;

/**
 * This class is for BRZ internal use only!
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Ignore ("Requires an SML with active DNS connection to be available")
public final class DNSRegistrationFuncTest extends AbstractSMLClientTestCase
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (DNSRegistrationFuncTest.class);

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
  private static String _DNSLookupPI (@Nonnull final ParticipantIdentifierType aPI) throws Exception
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
    s_aLogger.info ("Waiting 10 seconds to lookup '" + sHost + "'");
    Thread.sleep (10000);

    final Lookup aDNSLookup = new Lookup (sHost, Type.ANY);
    aDNSLookup.setResolver (new SimpleResolver (INTERNAL_DNS_SERVER));
    aDNSLookup.setCache (null);

    final Record [] aRecords = aDNSLookup.run ();
    s_aLogger.info ("Lookup returned [" + ArrayHelper.getSize (aRecords) + "]: " + Arrays.toString (aRecords));

    if (aRecords == null || aRecords.length == 0)
      return null;

    final Record aRecord = aRecords[0];
    if (aRecord instanceof CNAMERecord)
      return ((CNAMERecord) aRecord).getAlias ().toString ();

    if (aRecord instanceof ARecord)
    {
      final InetAddress aInetAddress = ((ARecord) aRecord).getAddress ();
      return aInetAddress.getHostAddress ();
    }

    s_aLogger.info ("Unknown record type found: " + ClassHelper.getClassLocalName (aRecord));
    return aRecord.toString ();
  }

  @Before
  public void setupSMPBeforeTests () throws Exception
  {
    s_aLogger.info ("Creating an SMP");
    try
    {
      final ManageServiceMetadataServiceCaller manageServiceMetaData = new ManageServiceMetadataServiceCaller (SML_INFO);

      manageServiceMetaData.create (SMP_ID, SMP_1_PHYSICAL_ADDRESS, SMP_1_LOGICAL_ADDRESS);
      s_aLogger.info ("Created an SMP");
    }
    catch (final Exception ex)
    {
      s_aLogger.error ("Failed: " + ex.getMessage ());
      throw ex;
    }
  }

  @After
  public void deleteSMPAfterTests () throws Exception
  {
    s_aLogger.info ("Deleting an SMP");
    final ManageServiceMetadataServiceCaller manageServiceMetaData = new ManageServiceMetadataServiceCaller (SML_INFO);

    try
    {
      manageServiceMetaData.delete (SMP_ID);
      s_aLogger.info ("Deleted an SMP");
    }
    catch (final NotFoundFault e)
    {
      // this is ok
    }
    catch (final Exception ex)
    {
      s_aLogger.error ("Failed: " + ex.getMessage ());
      throw ex;
    }
  }

  // SMP

  @Test
  public void verifySMPInDNS () throws Exception
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
  public void verifyParticipantIdentifierInDNS () throws Exception
  {
    // @Before creates new SMP!

    // create PI
    final ManageParticipantIdentifierServiceCaller client = new ManageParticipantIdentifierServiceCaller (SML_INFO);
    final PeppolParticipantIdentifier aPI = new PeppolParticipantIdentifier (PI_SCHEME, PI_VALUE);
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
  public void verifyWildcardInDNS () throws Exception
  {
    // @Before creates new SMP!

    final ManageParticipantIdentifierServiceCaller client = new ManageParticipantIdentifierServiceCaller (SML_INFO);

    final PeppolParticipantIdentifier aPI = new PeppolParticipantIdentifier (PI_WILDCARD_SCHEME, "*");
    client.create (SMP_ID, aPI);

    // verify that PI can be found in Wildcard domain.
    final String piHost = _DNSLookupPI (new PeppolParticipantIdentifier (PI_WILDCARD_SCHEME, PI_VALUE));
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
