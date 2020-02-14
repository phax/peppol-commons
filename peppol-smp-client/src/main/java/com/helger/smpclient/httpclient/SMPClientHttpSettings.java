package com.helger.smpclient.httpclient;

import com.helger.commons.string.StringHelper;
import com.helger.httpclient.HttpClientSettings;
import com.helger.smpclient.config.SMPClientConfiguration;

/**
 * Special SMP client {@link HttpClientSettings} that are fed from the
 * configuration file.
 *
 * @author Philip Helger
 */
public class SMPClientHttpSettings extends HttpClientSettings
{
  public SMPClientHttpSettings ()
  {
    // Set default proxy from configuration file
    setProxyHost (SMPClientConfiguration.getHttpProxy ());
    setProxyCredentials (SMPClientConfiguration.getHttpProxyCredentials ());

    final String sNonProxyHosts = SMPClientConfiguration.getNonProxyHosts ();
    if (StringHelper.hasText (sNonProxyHosts))
      StringHelper.explode ('|', sNonProxyHosts, sHost -> {
        final String sTrimmedHost = sHost.trim ();
        if (StringHelper.hasText (sTrimmedHost))
          nonProxyHosts ().add (sTrimmedHost);
      });
    setUseSystemProperties (SMPClientConfiguration.isUseProxySystemProperties ());
    setUseDNSClientCache (SMPClientConfiguration.isUseDNSClientCache ());
    setConnectionTimeoutMS (SMPClientConfiguration.getConnectionTimeoutMS ());
    setSocketTimeoutMS (SMPClientConfiguration.getRequestTimeoutMS ());
  }
}
