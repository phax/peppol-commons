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

import java.net.URL;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.OverridingMethodsMustInvokeSuper;
import com.helger.annotation.style.OverrideOnDemand;
import com.helger.base.url.URLHelper;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smlclient.bdmslmonitoring.BDMSLMonitoringService;
import com.helger.peppol.smlclient.bdmslmonitoring.BDMSLMonitoringServiceSoap;
import com.helger.peppol.smlclient.bdmslmonitoring.InternalErrorFault;
import com.helger.peppol.smlclient.bdmslmonitoring.IsAliveType;
import com.helger.peppol.smlclient.support.SinceBDMSL;
import com.helger.wsclient.WSClientConfig;

import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.WebServiceException;

/**
 * A client for the BDMSL specific "BDMSL monitoring service", that was introduced in BDMSL 5.0.
 * For older SML implementations use {@link BDMSLClient#isAlive()} instead.
 * <p>
 * Note: this class is also licensed under Apache 2 license, as it was not part of the original
 * implementation
 * </p>
 *
 * @author Philip Helger
 * @since 13.3.0
 */
@SinceBDMSL ("5.0")
public class BDMSLMonitoringClient extends WSClientConfig
{
  private static final Logger LOGGER = LoggerFactory.getLogger (BDMSLMonitoringClient.class);

  public BDMSLMonitoringClient (@NonNull final ISMLInfo aSMLInfo)
  {
    super (URLHelper.getAsURL (aSMLInfo.getManagementServiceURL () + "/bdmslmonitoringservice"));
  }

  /**
   * Constructs a service caller for the BDMSL monitoring service.
   *
   * @param aEndpointAddress
   *        The URL of the BDMSL monitoring service. May not be <code>null</code>.
   */
  public BDMSLMonitoringClient (@NonNull final URL aEndpointAddress)
  {
    super (aEndpointAddress);
  }

  /**
   * Create the main WebService client for the specified endpoint address.
   *
   * @return The WebService port to be used. May not be <code>null</code>.
   */
  @NonNull
  @OverrideOnDemand
  @OverridingMethodsMustInvokeSuper
  protected BDMSLMonitoringServiceSoap createWSPort ()
  {
    final BDMSLMonitoringService aService = new BDMSLMonitoringService ();
    final BDMSLMonitoringServiceSoap aPort = aService.getBDMSLMonitoringServicePort ();
    applyWSSettingsToBindingProvider ((BindingProvider) aPort);
    return aPort;
  }

  /**
   * Check if the SML is alive.
   *
   * @return <code>true</code> if the SML is alive, <code>false</code> if not or if the call failed.
   */
  public boolean isAlive ()
  {
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("isAlive ()");

    try
    {
      final IsAliveType aDummy = new IsAliveType ();
      createWSPort ().isAlive (aDummy);
      return true;
    }
    catch (final InternalErrorFault | WebServiceException ex)
    {
      return false;
    }
  }
}
