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

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.base.CGlobal;
import com.helger.http.tls.ETLSVersion;
import com.helger.http.tls.TLSConfigurationMode;
import com.helger.httpclient.HttpClientSettings;
import com.helger.httpclient.HttpClientSettingsConfig;
import com.helger.peppol.commons.CPeppolCommonsVersion;
import com.helger.security.revocation.CertificateRevocationCheckerDefaults;
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
  public static final String USER_AGENT = "phax/peppol-commons smp-client/" + CPeppolCommonsVersion.BUILD_VERSION;
  private static final Logger LOGGER = LoggerFactory.getLogger (SMPHttpClientSettings.class);

  /**
   * Constructor. Initializes all settings from configuration file. Any changes made afterwards
   */
  public SMPHttpClientSettings ()
  {
    // According to the Peppol SMP specification, a client should not follow HTTP 3xx redirects - so
    // we don't (see chapter 5.1 of SMP spec 1.4.0)
    setFollowRedirects (false);

    // TLS 1.3 is allowed in Peppol, TLS 1.2 is the minimum
    setTLSConfigurationMode (new TLSConfigurationMode (new ETLSVersion [] { ETLSVersion.TLS_13, ETLSVersion.TLS_12 },
                                                       CGlobal.EMPTY_STRING_ARRAY));

    // Also do TLS certificate revocation check
    setRevocationCheckMode (CertificateRevocationCheckerDefaults.DEFAULT_REVOCATION_CHECK_MODE);

    // Set an explicit user agent
    setUserAgent (USER_AGENT);
  }

  /**
   * Overwrite all settings that can appear in the configuration file.
   */
  @SuppressWarnings ("removal")
  @Deprecated (forRemoval = true, since = "12.5.0")
  public final void resetToConfiguration ()
  {
    getGeneralProxy ().setProxyHost (SMPClientConfiguration.getHttpProxy ());
    getGeneralProxy ().setProxyCredentials (SMPClientConfiguration.getHttpProxyCredentials ());
    getGeneralProxy ().setNonProxyHostsFromPipeString (SMPClientConfiguration.getNonProxyHosts ());
    setUseDNSClientCache (SMPClientConfiguration.isUseDNSClientCache ());
    setConnectTimeout (SMPClientConfiguration.getConnectTimeout ());
    setResponseTimeout (SMPClientConfiguration.getResponseTimeout ());
  }

  private void _verifySettings ()
  {
    if (isFollowRedirects ())
      LOGGER.warn ("The SMP Client is configured to follow HTTP redirects - this is against the Peppol SMP specification");
  }

  @NonNull
  @Deprecated (forRemoval = true, since = "12.5.0")
  public static SMPHttpClientSettings fromLegacyConfiguration ()
  {
    final SMPHttpClientSettings ret = new SMPHttpClientSettings ();
    ret.resetToConfiguration ();
    ret._verifySettings ();
    return ret;
  }

  @NonNull
  public static SMPHttpClientSettings fromConfiguration ()
  {
    final SMPHttpClientSettings ret = new SMPHttpClientSettings ();
    HttpClientSettingsConfig.assignConfigValues (ret, SMPClientConfiguration.getConfig (), "smpclient", "");
    ret._verifySettings ();
    return ret;
  }
}
