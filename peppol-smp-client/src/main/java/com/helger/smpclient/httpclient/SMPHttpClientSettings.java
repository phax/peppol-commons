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
   * Constructor. Initializes all settings from configuration file.
   */
  public SMPHttpClientSettings ()
  {
    resetToConfiguration ();
  }

  /**
   * Overwrite all settings that can appear in the configuration file.
   */
  public final void resetToConfiguration ()
  {
    setUseSystemProperties (SMPClientConfiguration.isUseProxySystemProperties ());
    setProxyHost (SMPClientConfiguration.getHttpProxy ());
    setProxyCredentials (SMPClientConfiguration.getHttpProxyCredentials ());
    nonProxyHosts ().clear ();
    addNonProxyHostsFromPipeString (SMPClientConfiguration.getNonProxyHosts ());
    setUseDNSClientCache (SMPClientConfiguration.isUseDNSClientCache ());
    setConnectionTimeoutMS (SMPClientConfiguration.getConnectionTimeoutMS ());
    setSocketTimeoutMS (SMPClientConfiguration.getRequestTimeoutMS ());
  }
}
