/**
 * Copyright (C) 2015-2018 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.smlclient;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.charset.StandardCharsets;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.collection.impl.ICommonsList;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.smlclient.bdmsl.ParticipantListItem;
import com.helger.wsclient.WSHelper;
import com.sun.xml.ws.client.ClientTransportException;

/**
 * Test class for class {@link BDMSLClient}.
 *
 * @author Philip Helger
 */
@Ignore ("Requires a running SML and a configured certificate")
public final class BDMSLClientTest extends AbstractSMLClientTestCase
{
  private static final Logger LOGGER = LoggerFactory.getLogger (BDMSLClientTest.class);

  static
  {
    assertSame (ESML.DIGIT_TEST, SML_INFO);
  }

  @Test
  public void testCreateAndDeletePublisher () throws Exception
  {
    WSHelper.enableSoapLogging (true);
    try
    {
      // Create client
      final BDMSLClient aClient = new BDMSLClient (SML_INFO);
      aClient.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));
      aClient.setRequestTimeoutMS (100_000);

      LOGGER.info ("IS_ALIVE");
      final boolean bAlive = aClient.isAlive ();
      assertTrue (bAlive);

      LOGGER.info ("CLEAR_CACHE");
      aClient.clearCache ();

      LOGGER.info ("LIST_PARTICIPANTS");
      final ICommonsList <ParticipantListItem> aList = aClient.listParticipants ();
      assertNull (aList);

      LOGGER.info ("PREPARE_CHANGE_CERTIFICATE");
      try
      {
        aClient.prepareChangeCertificate ("dummy", null);
        fail ();
      }
      catch (final ClientTransportException ex)
      {
        // Expected HTTP 400
      }

      LOGGER.info ("CHANGE_CERTIFICATE");
      try
      {
        aClient.changeCertificate ("fake-smp-id", "dummy".getBytes (StandardCharsets.ISO_8859_1));
        fail ();
      }
      catch (final ClientTransportException ex)
      {
        // Expected HTTP 400
      }
    }
    finally
    {
      WSHelper.enableSoapLogging (false);
    }
  }
}
