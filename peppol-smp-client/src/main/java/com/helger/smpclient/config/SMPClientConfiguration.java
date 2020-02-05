/**
 * Copyright (C) 2015-2020 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.smpclient.config;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.apache.http.HttpHost;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.httpclient.HttpClientFactory;
import com.helger.peppol.utils.PeppolKeyStoreHelper;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.settings.exchange.configfile.ConfigFile;
import com.helger.settings.exchange.configfile.ConfigFileBuilder;
import com.helger.smpclient.httpclient.AbstractGenericSMPClient;

/**
 * This class manages the configuration properties of the SMP client. The order
 * of the properties file resolving is as follows:
 * <ol>
 * <li>Check for the value of the system property
 * <code>peppol.smp.client.properties.path</code></li>
 * <li>Check for the value of the system property
 * <code>smp.client.properties.path</code></li>
 * <li>The filename <code>private-smp-client.properties</code> in the root of
 * the classpath</li>
 * <li>The filename <code>smp-client.properties</code> in the root of the
 * classpath</li>
 * </ol>
 * <p>
 * Note: this class is also licensed under Apache 2 license, as it was not part
 * of the original implementation
 * </p>
 *
 * @author Philip Helger
 */
@Immutable
public final class SMPClientConfiguration
{
  private static final Logger LOGGER = LoggerFactory.getLogger (SMPClientConfiguration.class);
  private static final ConfigFile s_aConfigFile;

  static
  {
    final ConfigFileBuilder aCFB = new ConfigFileBuilder ().addPathFromSystemProperty ("peppol.smp.client.properties.path")
                                                           .addPathFromSystemProperty ("smp.client.properties.path")
                                                           .addPathFromEnvVar ("SMP_CLIENT_CONFIG")
                                                           .addPath ("private-smp-client.properties")
                                                           .addPath ("smp-client.properties");

    s_aConfigFile = aCFB.build ();
    if (s_aConfigFile.isRead ())
      LOGGER.info ("Read SMP client properties from " + s_aConfigFile.getReadResource ().getPath ());
    else
      LOGGER.warn ("Failed to read SMP client properties from " + aCFB.getAllPaths ());
  }

  private SMPClientConfiguration ()
  {}

  /**
   * @return The global config file for the SMP client.
   */
  @Nonnull
  public static ConfigFile getConfigFile ()
  {
    return s_aConfigFile;
  }

  /**
   * @return The truststore type as specified in the configuration file by the
   *         key <code>truststore.type</code>. If none is present
   *         {@link PeppolKeyStoreHelper#TRUSTSTORE_TYPE} is returned as a
   *         default.
   * @since 6.0.0
   */
  @Nonnull
  public static EKeyStoreType getTrustStoreType ()
  {
    final String sType = s_aConfigFile.getAsString ("truststore.type");
    return EKeyStoreType.getFromIDCaseInsensitiveOrDefault (sType, PeppolKeyStoreHelper.TRUSTSTORE_TYPE);
  }

  /**
   * @return The truststore location as specified in the configuration file by
   *         the key <code>truststore.path</code>. If none is present
   *         {@link PeppolKeyStoreHelper#TRUSTSTORE_COMPLETE_CLASSPATH} is
   *         returned as a default. Note: for backwards compatibility, also the
   *         key <code>truststore.location</code> is evaluated.
   * @since 6.0.0 - was getTruststoreLocation before
   */
  @Nonnull
  public static String getTrustStorePath ()
  {
    String ret = s_aConfigFile.getAsString ("truststore.path");
    if (ret == null)
      ret = s_aConfigFile.getAsString ("truststore.location", PeppolKeyStoreHelper.TRUSTSTORE_COMPLETE_CLASSPATH);
    return ret;
  }

  /**
   * @return The truststore password as specified in the configuration file by
   *         the key <code>truststore.password</code>. If none is present
   *         {@link PeppolKeyStoreHelper#TRUSTSTORE_PASSWORD} is returned as a
   *         default.
   */
  @Nonnull
  public static String getTrustStorePassword ()
  {
    return s_aConfigFile.getAsString ("truststore.password", PeppolKeyStoreHelper.TRUSTSTORE_PASSWORD);
  }

  /**
   * @return The HttpProxy object to be used by SMP clients based on the Java
   *         System properties "http.proxyHost" and "http.proxyPort". Note:
   *         https is not needed, because SMPs must run on http only.
   */
  @Nullable
  public static HttpHost getHttpProxy ()
  {
    final String sProxyHost = s_aConfigFile.getAsString ("http.proxyHost");
    final int nProxyPort = s_aConfigFile.getAsInt ("http.proxyPort", 0);
    if (sProxyHost != null && nProxyPort > 0)
      return new HttpHost (sProxyHost, nProxyPort);

    return null;
  }

  /**
   * @return The {@link UsernamePasswordCredentials} object to be used for proxy
   *         server authentication.
   * @since 5.2.5
   */
  @Nullable
  public static UsernamePasswordCredentials getHttpProxyCredentials ()
  {
    final String sProxyUsername = s_aConfigFile.getAsString ("http.proxyUsername");
    final String sProxyPassword = s_aConfigFile.getAsString ("http.proxyPassword");
    if (sProxyUsername != null && sProxyPassword != null)
      return new UsernamePasswordCredentials (sProxyUsername, sProxyPassword);

    return null;
  }

  /**
   * @return A pipe separated list of non-proxy hosts. E.g.
   *         <code>localhost|127.0.0.1</code>. May be <code>null</code>.
   * @since 6.2.4
   */
  @Nullable
  public static String getNonProxyHosts ()
  {
    return s_aConfigFile.getAsString ("http.nonProxyHosts");
  }

  /**
   * Get the content of the property "http.useSystemProperties" or
   * <code>false</code> if undefined.
   *
   * @return <code>true</code> if the SMP client proxy configuration should be
   *         take from the system properties, or <code>false</code> if not. The
   *         default behavior is to return <code>false</code>.
   * @since 5.2.4
   */
  public static boolean isUseProxySystemProperties ()
  {
    return s_aConfigFile.getAsBoolean ("http.useSystemProperties", HttpClientFactory.DEFAULT_USE_SYSTEM_PROPERTIES);
  }

  /**
   * Get the content of the property "http.useDNSClientCache" or
   * <code>true</code> if undefined.
   *
   * @return <code>true</code> if the SMP client should use DNS client caching
   *         (default) or <code>false</code> if DNS caching should be disabled.
   *         The default behavior is to return <code>true</code>.
   * @since 5.2.5
   */
  public static boolean isUseDNSClientCache ()
  {
    return s_aConfigFile.getAsBoolean ("http.useDNSClientCache", HttpClientFactory.DEFAULT_USE_DNS_CACHE);
  }

  /**
   * Get the content of the property "http.connect.timeout.ms" or the default
   * value.
   *
   * @return The connection timeout of the SMP client in milliseconds. Defaults
   *         to 5 seconds.
   * @since 7.0.4
   */
  public static int getConnectionTimeoutMS ()
  {
    return s_aConfigFile.getAsInt ("http.connect.timeout.ms", AbstractGenericSMPClient.DEFAULT_CONNECTION_TIMEOUT_MS);
  }

  /**
   * Get the content of the property "http.request.timeout.ms" or the default
   * value.
   *
   * @return The request timeout of the SMP client in milliseconds. Defaults to
   *         10 seconds.
   * @since 7.0.4
   */
  public static int getRequestTimeoutMS ()
  {
    return s_aConfigFile.getAsInt ("http.request.timeout.ms", AbstractGenericSMPClient.DEFAULT_REQUEST_TIMEOUT_MS);
  }
}
