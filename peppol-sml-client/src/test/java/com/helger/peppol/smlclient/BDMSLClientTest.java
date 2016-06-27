/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.smlclient;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.collection.ext.ICommonsList;
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
