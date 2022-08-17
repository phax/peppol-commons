/*
 * Copyright (C) 2015-2022 Philip Helger
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

import com.helger.httpclient.HttpClientSettings;
import com.helger.smpclient.config.SMPClientConfiguration;

/**
 * Special SMP client {@link HttpClientSettings} that are fed from the
 * configuration file (see {@link SMPClientConfiguration}).
 *
 * @author Philip Helger
 * @since 8.0.1
 */
public class SMPHttpClientSettings extends HttpClientSettings
{
  /**
   * Constructor. Initializes all settings from configuration file. Any changes
   * made afterwards
   *
   * @see #resetToConfiguration()
   */
  public SMPHttpClientSettings ()
  {
    resetToConfiguration ();
  }

  /**
   * Overwrite all settings that can appear in the configuration file.
   */
  @SuppressWarnings ("deprecation")
  public final void resetToConfiguration ()
  {
    setUseSystemProperties (SMPClientConfiguration.isUseProxySystemProperties ());
    setProxyHost (SMPClientConfiguration.getHttpProxy ());
    setProxyCredentials (SMPClientConfiguration.getHttpProxyCredentials ());
    nonProxyHosts ().clear ();
    addNonProxyHostsFromPipeString (SMPClientConfiguration.getNonProxyHosts ());
    setUseDNSClientCache (SMPClientConfiguration.isUseDNSClientCache ());
    setConnectionTimeout (SMPClientConfiguration.getConnectionTimeout ());
    setResponseTimeout (SMPClientConfiguration.getRequestTimeout ());
  }
}
