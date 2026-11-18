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
package com.helger.peppol.smlclient;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import com.helger.peppol.sml.ESML;
import com.helger.wsclient.WSHelper;

/**
 * Test class for class {@link BDMSLMonitoringClient}.
 *
 * @author Philip Helger
 */
@Ignore ("Requires a running SML with BDMSL 5.0 or later and a configured certificate")
public final class BDMSLMonitoringClientTest extends AbstractSMLClientTestCase
{
  static
  {
    assertSame (ESML.PEPPOL_TEST, SML_INFO);
  }

  @Test
  public void testIsAlive () throws Exception
  {
    WSHelper.enableSoapLogging (true);
    try
    {
      final BDMSLMonitoringClient aClient = new BDMSLMonitoringClient (SML_INFO);
      aClient.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));
      aClient.setRequestTimeoutMS (100_000);
      assertTrue (aClient.isAlive ());
    }
    finally
    {
      WSHelper.enableSoapLogging (false);
    }
  }
}
