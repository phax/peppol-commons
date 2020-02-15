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
  public SMPHttpClientSettings ()
  {
    setUseSystemProperties (SMPClientConfiguration.isUseProxySystemProperties ());
    setProxyHost (SMPClientConfiguration.getHttpProxy ());
    setProxyCredentials (SMPClientConfiguration.getHttpProxyCredentials ());
    addNonProxyHostsFromPipeString (SMPClientConfiguration.getNonProxyHosts ());
    setUseDNSClientCache (SMPClientConfiguration.isUseDNSClientCache ());
    setConnectionTimeoutMS (SMPClientConfiguration.getConnectionTimeoutMS ());
    setSocketTimeoutMS (SMPClientConfiguration.getRequestTimeoutMS ());
  }
}
