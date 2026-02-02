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
package com.helger.smpclient.httpclient;

import java.security.GeneralSecurityException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import com.helger.http.security.TrustManagerTrustAll;
import com.helger.http.tls.ETLSVersion;
import com.helger.httpclient.HttpClientSettings;
import com.helger.peppol.commons.CPeppolCommonsVersion;
import com.helger.smpclient.config.SMPClientConfiguration;

/**
 * Special SMP client {@link HttpClientSettings} that are fed from the configuration file (see
 * {@link SMPClientConfiguration}).
 *
 * @author Philip Helger
 * @since 8.0.1
 */
public class SMPHttpClientSettings extends HttpClientSettings
{
  /**
   * Constructor. Initializes all settings from configuration file. Any changes made afterwards
   *
   * @see #resetToConfiguration()
   */
  public SMPHttpClientSettings ()
  {
    // Currently the Peppol SMP requires to use "http" only - so no upgrade should be performed
    setProtocolUpgradeEnabled (false);

    // According to the Peppol SMP specification, a client should not follow HTTP 3xx redirects - so
    // we don't (see chapter 5.1 of SMP spec 1.4.0)
    setFollowRedirects (false);

    try
    {
      // Peppol requires TLS v1.2
      final SSLContext aSSLContext = SSLContext.getInstance (ETLSVersion.TLS_12.getID ());
      // But we're basically trusting all hosts - the exact list is hard to
      // determine
      aSSLContext.init (null, new TrustManager [] { new TrustManagerTrustAll (false) }, null);
      setSSLContext (aSSLContext);
    }
    catch (final GeneralSecurityException ex)
    {
      throw new IllegalStateException ("Failed to initialize SSLContext for SMPHttpClientSettings", ex);
    }

    // Set an explicit user agent
    setUserAgent ("phax/peppol-commons smp-client/" + CPeppolCommonsVersion.BUILD_VERSION);

    resetToConfiguration ();
  }

  /**
   * Overwrite all settings that can appear in the configuration file.
   */
  public final void resetToConfiguration ()
  {
    getGeneralProxy ().setProxyHost (SMPClientConfiguration.getHttpProxy ());
    getGeneralProxy ().setProxyCredentials (SMPClientConfiguration.getHttpProxyCredentials ());
    getGeneralProxy ().setNonProxyHostsFromPipeString (SMPClientConfiguration.getNonProxyHosts ());
    setUseDNSClientCache (SMPClientConfiguration.isUseDNSClientCache ());
    setConnectTimeout (SMPClientConfiguration.getConnectTimeout ());
    setResponseTimeout (SMPClientConfiguration.getResponseTimeout ());
  }
}
