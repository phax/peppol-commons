/*
 * Copyright (C) 2015-2025 Philip Helger
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
package com.helger.smpclient.config;

import java.nio.charset.StandardCharsets;
import java.security.KeyStore;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.Immutable;

import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.concurrent.SimpleReadWriteLock;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.exception.InitializationException;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.commons.io.resourceprovider.ReadableResourceProviderChain;
import com.helger.commons.string.StringHelper;
import com.helger.commons.system.SystemProperties;
import com.helger.config.ConfigFactory;
import com.helger.config.IConfig;
import com.helger.config.fallback.ConfigWithFallback;
import com.helger.config.fallback.IConfigWithFallback;
import com.helger.config.source.MultiConfigurationValueProvider;
import com.helger.config.source.res.ConfigurationSourceProperties;
import com.helger.httpclient.HttpClientSettings;
import com.helger.peppol.utils.PeppolKeyStoreHelper;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.KeyStoreHelper;

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

  static
  {
    // Since 8.2.0
    if (StringHelper.hasText (SystemProperties.getPropertyValueOrNull ("peppol.smp.client.properties.path")))
      throw new InitializationException ("The system property 'peppol.smp.client.properties.path' is no longer supported." +
                                         " See https://github.com/phax/ph-commons#ph-config for alternatives." +
                                         " Consider using the system property 'config.file' instead.");
    if (StringHelper.hasText (SystemProperties.getPropertyValueOrNull ("smp.client.properties.path")))
      throw new InitializationException ("The system property 'smp.client.properties.path' is no longer supported." +
                                         " See https://github.com/phax/ph-commons#ph-config for alternatives." +
                                         " Consider using the system property 'config.file' instead.");
    if (StringHelper.hasText (System.getenv ().get ("SMP_CLIENT_CONFIG")))
      throw new InitializationException ("The environment variable 'SMP_CLIENT_CONFIG' is no longer supported." +
                                         " See https://github.com/phax/ph-commons#ph-config for alternatives." +
                                         " Consider using the environment variable 'CONFIG_FILE' instead.");
  }

  /**
   * @return The configuration value provider for SMP client that contains
   *         backward compatibility support.
   */
  @Nonnull
  public static MultiConfigurationValueProvider createSMPClientValueProvider ()
  {
    // Start with default setup
    final MultiConfigurationValueProvider ret = ConfigFactory.createDefaultValueProvider ();

    final ReadableResourceProviderChain aResourceProvider = ConfigFactory.createDefaultResourceProviderChain ();

    IReadableResource aRes;
    final int nBasePrio = ConfigFactory.APPLICATION_PROPERTIES_PRIORITY;

    // Lower priority than the standard files
    aRes = aResourceProvider.getReadableResourceIf ("private-smp-client.properties", IReadableResource::exists);
    if (aRes != null)
    {
      LOGGER.warn ("The support for the properties file 'private-smp-client.properties' is deprecated. Place the properties in 'application.properties' instead.");
      ret.addConfigurationSource (new ConfigurationSourceProperties (aRes, StandardCharsets.UTF_8), nBasePrio - 1);
    }

    aRes = aResourceProvider.getReadableResourceIf ("smp-client.properties", IReadableResource::exists);
    if (aRes != null)
    {
      LOGGER.warn ("The support for the properties file 'smp-client.properties' is deprecated. Place the properties in 'application.properties' instead.");
      ret.addConfigurationSource (new ConfigurationSourceProperties (aRes, StandardCharsets.UTF_8), nBasePrio - 2);
    }

    return ret;
  }

  private static final IConfigWithFallback DEFAULT_CONFIG = new ConfigWithFallback (createSMPClientValueProvider ());
  private static final SimpleReadWriteLock RW_LOCK = new SimpleReadWriteLock ();
  @GuardedBy ("RW_LOCK")
  private static IConfigWithFallback s_aConfig = DEFAULT_CONFIG;

  private SMPClientConfiguration ()
  {}

  /**
   * @return The current global configuration. Never <code>null</code>.
   */
  @Nonnull
  public static IConfigWithFallback getConfig ()
  {
    // Inline for performance
    RW_LOCK.readLock ().lock ();
    try
    {
      return s_aConfig;
    }
    finally
    {
      RW_LOCK.readLock ().unlock ();
    }
  }

  /**
   * Overwrite the global configuration. This is only needed for testing.
   *
   * @param aNewConfig
   *        The configuration to use globally. May not be <code>null</code>.
   * @return The old value of {@link IConfig}. Never <code>null</code>.
   */
  @Nonnull
  public static IConfigWithFallback setConfig (@Nonnull final IConfigWithFallback aNewConfig)
  {
    ValueEnforcer.notNull (aNewConfig, "NewConfig");
    final IConfigWithFallback ret;
    RW_LOCK.writeLock ().lock ();
    try
    {
      ret = s_aConfig;
      s_aConfig = aNewConfig;
    }
    finally
    {
      RW_LOCK.writeLock ().unlock ();
    }

    if (!EqualsHelper.identityEqual (ret, aNewConfig))
      LOGGER.info ("The SMPClient configuration provider was changed to " + aNewConfig);
    return ret;
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
    final String ret = getConfig ().getAsStringOrFallback ("smpclient.truststore.type", "truststore.type");
    return EKeyStoreType.getFromIDCaseInsensitiveOrDefault (ret, PeppolKeyStoreHelper.TRUSTSTORE_TYPE);
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
    String ret = getConfig ().getAsStringOrFallback ("smpclient.truststore.path",
                                                     "truststore.path",
                                                     "truststore.location");
    if (StringHelper.hasNoText (ret))
      ret = PeppolKeyStoreHelper.TRUSTSTORE_COMPLETE_CLASSPATH;
    return ret;
  }

  /**
   * @return The truststore password as specified in the configuration file by
   *         the key <code>truststore.password</code>. If none is present
   *         {@link PeppolKeyStoreHelper#TRUSTSTORE_PASSWORD} is returned as a
   *         default.
   */
  @Nonnull
  public static char [] getTrustStorePasswordCharArray ()
  {
    char [] ret = getConfig ().getAsCharArrayOrFallback ("smpclient.truststore.password", "truststore.password");
    if (ret == null)
      ret = PeppolKeyStoreHelper.TRUSTSTORE_PASSWORD.toCharArray ();
    return ret;
  }

  /**
   * Try to load the configured trust store.
   *
   * @return <code>null</code> if it cannot be loaded.
   * @since 8.1.1
   */
  @Nullable
  public static KeyStore loadTrustStore ()
  {
    try
    {
      return KeyStoreHelper.loadKeyStoreDirect (getTrustStoreType (),
                                                getTrustStorePath (),
                                                getTrustStorePasswordCharArray ());
    }
    catch (final Exception ex)
    {
      // May also be a runtime exception if the path is invalid
      LOGGER.warn ("Failed to load SMP client truststore: " + ex.getClass ().getName () + " - " + ex.getMessage ());
      return null;
    }
  }

  /**
   * @return The HttpProxy object to be used by SMP clients based on the Java
   *         System properties "http.proxyHost" and "http.proxyPort". Note:
   *         https is not needed, because SMPs must run on http only.
   */
  @Nullable
  public static HttpHost getHttpProxy ()
  {
    final String sProxyHost = getConfig ().getAsStringOrFallback ("http.proxy.host", "http.proxyHost");
    final int nProxyPort = getConfig ().getAsIntOrFallback ("http.proxy.port", -1, 0, "http.proxyPort");
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
    final String sProxyUsername = getConfig ().getAsStringOrFallback ("http.proxy.username", "http.proxyUsername");
    final String sProxyPassword = getConfig ().getAsStringOrFallback ("http.proxy.password", "http.proxyPassword");
    if (sProxyUsername != null && sProxyPassword != null)
      return new UsernamePasswordCredentials (sProxyUsername, sProxyPassword.toCharArray ());

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
    return getConfig ().getAsStringOrFallback ("http.proxy.nonProxyHosts", "http.nonProxyHosts");
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
    return getConfig ().getAsBoolean ("http.useDNSClientCache", HttpClientSettings.DEFAULT_USE_DNS_CACHE);
  }

  /**
   * Get the content of the property "http.connect.timeout.ms" or the default
   * value.
   *
   * @return The connection timeout of the SMP client. Defaults to 5 seconds.
   * @since 8.8.0
   */
  @Nonnull
  public static Timeout getConnectTimeout ()
  {
    final long nMS = getConfig ().getAsLong ("http.connect.timeout.ms", -1);
    if (nMS >= 0)
      return Timeout.ofMilliseconds (nMS);
    return HttpClientSettings.DEFAULT_CONNECT_TIMEOUT;
  }

  /**
   * Get the content of the property "http.response.timeout.ms" or the default
   * value. The fallback value is "http.request.timeout.ms".
   *
   * @return The response timeout of the SMP client. Defaults to 10 seconds.
   * @since 8.8.0
   */
  @Nonnull
  public static Timeout getResponseTimeout ()
  {
    final long nMS = getConfig ().getAsLongOrFallback ("http.response.timeout.ms", -1, -1, "http.request.timeout.ms");
    if (nMS >= 0)
      return Timeout.ofMilliseconds (nMS);
    return HttpClientSettings.DEFAULT_RESPONSE_TIMEOUT;
  }
}
