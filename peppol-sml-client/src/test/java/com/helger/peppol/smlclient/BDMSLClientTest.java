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
package com.helger.peppol.smlclient;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.ws.WSHelper;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.smlclient.bdmsl.ParticipantListItem;

/**
 * Test class for class {@link BDMSLClient}.
 *
 * @author Philip Helger
 */
@Ignore ("Requires a running SML and a configured certificate")
public final class BDMSLClientTest extends AbstractSMLClientTestCase
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (BDMSLClientTest.class);

  static
  {
    assertSame (SML_INFO, ESML.DIGIT_TEST);
  }

  @Test
  public void testCreateAndDeletePublisher () throws Exception
  {
    WSHelper.enableSoapLogging (true);
    try
    {
      // Create client
      final BDMSLClient aClient = new BDMSLClient (SML_INFO);
      aClient.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO));

      s_aLogger.info ("IS_ALIVE");
      aClient.isAlive ();

      s_aLogger.info ("CLEAR_CACHE");
      aClient.clearCache ();

      s_aLogger.info ("LIST_PARTICIPANTS");
      final ICommonsList <ParticipantListItem> aList = aClient.listParticipants ();
      assertNull (aList);
    }
    finally
    {
      WSHelper.enableSoapLogging (false);
    }
  }
}
