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
package com.helger.peppol.utils;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.system.SystemProperties;

/**
 * Utility methods for this library.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Immutable
public final class PeppolTechnicalSetup
{
  @PresentForCodeCoverage
  private static final PeppolTechnicalSetup s_aInstance = new PeppolTechnicalSetup ();

  private PeppolTechnicalSetup ()
  {}

  /**
   * Enable the JAX-WS SOAP debugging. This shows the exchanged SOAP messages in
   * the log file. By default this logging is disabled.
   *
   * @param bDebug
   *        <code>true</code> to enable debugging, <code>false</code> to disable
   *        it.
   */
  public static void enableSoapLogging (final boolean bDebug)
  {
    SystemProperties.setPropertyValue ("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", bDebug);
    SystemProperties.setPropertyValue ("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", bDebug);
  }

  /**
   * Enable advanced JAX-WS debugging on more or less all relevant layers. This
   * method internally calls {@link #enableSoapLogging(boolean)} so it does not
   * need to be called explicitly. By default all this logging is disabled.
   *
   * @param bDebug
   *        <code>true</code> to enabled debugging, <code>false</code> to
   *        disable it.
   */
  public static void setMetroDebugSystemProperties (final boolean bDebug)
  {
    // Depending on the used JAX-WS version, the property names are
    // different....
    enableSoapLogging (bDebug);

    SystemProperties.setPropertyValue ("com.sun.xml.ws.transport.http.HttpAdapter.dump", bDebug);
    SystemProperties.setPropertyValue ("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", bDebug);

    SystemProperties.setPropertyValue ("com.sun.xml.ws.fault.SOAPFaultBuilder.disableCaptureStackTrace",
                                       bDebug ? null : "false");

    SystemProperties.setPropertyValue ("com.sun.metro.soap.dump", bDebug);
    SystemProperties.setPropertyValue ("com.sun.xml.wss.provider.wsit.SecurityTubeFactory.dump", bDebug);
    SystemProperties.setPropertyValue ("com.sun.xml.wss.jaxws.impl.SecurityServerTube.dump", bDebug);
    SystemProperties.setPropertyValue ("com.sun.xml.wss.jaxws.impl.SecurityClientTube.dump", bDebug);
    SystemProperties.setPropertyValue ("com.sun.xml.ws.rx.rm.runtime.ClientTube.dump", bDebug);
  }

  /**
   * Get a set of system property names which are relevant for network
   * debugging/proxy handling. This method is meant to be used for reading the
   * appropriate settings from a configuration file.
   *
   * @return An array with all system property names which are relevant for
   *         debugging/proxy handling. Never <code>null</code> and never empty.
   *         Each call returns a new array.
   */
  @Nonnull
  @ReturnsMutableCopy
  public static String [] getAllJavaNetSystemProperties ()
  {
    // http://docs.oracle.com/javase/7/docs/technotes/guides/security/jsse/ReadDebug.html
    // http://download.oracle.com/javase/6/docs/technotes/guides/net/proxies.html
    // The first 2 (*.debug) should both be set to "all" to have the most
    // effects
    return new String [] { "javax.net.debug",
                           "java.security.debug",
                           "java.net.useSystemProxies",
                           "http.proxyHost",
                           "http.proxyPort",
                           "http.nonProxyHosts",
                           "https.proxyHost",
                           "https.proxyPort" };
  }
}
