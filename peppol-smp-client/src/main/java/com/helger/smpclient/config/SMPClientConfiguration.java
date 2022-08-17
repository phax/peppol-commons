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
package com.helger.smpclient.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
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
import com.helger.config.Config;
import com.helger.config.ConfigFactory;
import com.helger.config.IConfig;
import com.helger.config.source.EConfigSourceType;
import com.helger.config.source.MultiConfigurationValueProvider;
import com.helger.config.source.res.ConfigurationSourceProperties;
import com.helger.config.value.ConfiguredValue;
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
   * @return The configuration value provider for phase4 that contains backward
   *         compatibility support.
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

  private static final IConfig DEFAULT_CONFIG = Config.create (createSMPClientValueProvider ());
  private static final SimpleReadWriteLock RW_LOCK = new SimpleReadWriteLock ();
  @GuardedBy ("RW_LOCK")
  private static IConfig s_aConfig = DEFAULT_CONFIG;

  private SMPClientConfiguration ()
  {}

  /**
   * @return The current global configuration. Never <code>null</code>.
   */
  @Nonnull
  public static IConfig getConfig ()
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
  public static IConfig setConfig (@Nonnull final IConfig aNewConfig)
  {
    ValueEnforcer.notNull (aNewConfig, "NewConfig");
    final IConfig ret;
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
      if (LOGGER.isInfoEnabled ())
        LOGGER.info ("The SMPClient configuration provider was changed to " + aNewConfig);
    return ret;
  }

  private static void _logRenamedConfig (@Nonnull final String sOld, @Nonnull final String sNew)
  {
    if (LOGGER.isWarnEnabled ())
      LOGGER.warn ("Please rename the configuration property '" +
                   sOld +
                   "' to '" +
                   sNew +
                   "'. Support for the old property name will be removed in v9.0.");
  }

  @Nullable
  private static String _getAsStringOrFallback (@Nonnull final String sPrimary, @Nonnull final String... aOldOnes)
  {
    String ret = getConfig ().getAsString (sPrimary);
    if (StringHelper.hasNoText (ret))
    {
      // Try the old names
      for (final String sOld : aOldOnes)
      {
        ret = getConfig ().getAsString (sOld);
        if (StringHelper.hasText (ret))
        {
          // Notify on old name usage
          _logRenamedConfig (sOld, sPrimary);
          break;
        }
      }
    }
    return ret;
  }

  private static int _getAsIntOrFallback (@Nonnull final String sPrimary,
                                          final int nBogus,
                                          final int nDefault,
                                          @Nonnull final String... aOldOnes)
  {
    int ret = getConfig ().getAsInt (sPrimary, nBogus);
    if (ret == nBogus)
    {
      // Try the old names
      for (final String sOld : aOldOnes)
      {
        ret = getConfig ().getAsInt (sOld, nBogus);
        if (ret != nBogus)
        {
          // Notify on old name usage
          _logRenamedConfig (sOld, sPrimary);
          break;
        }
      }
    }
    return ret == nBogus ? nDefault : ret;
  }

  private static long _getAsLongOrFallback (@Nonnull final String sPrimary,
                                            final long nBogus,
                                            final long nDefault,
                                            @Nonnull final String... aOldOnes)
  {
    long ret = getConfig ().getAsLong (sPrimary, nBogus);
    if (ret == nBogus)
    {
      // Try the old names
      for (final String sOld : aOldOnes)
      {
        ret = getConfig ().getAsLong (sOld, nBogus);
        if (ret != nBogus)
        {
          // Notify on old name usage
          _logRenamedConfig (sOld, sPrimary);
          break;
        }
      }
    }
    return ret == nBogus ? nDefault : ret;
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
    final String ret = _getAsStringOrFallback ("smpclient.truststore.type", "truststore.type");
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
    String ret = _getAsStringOrFallback ("smpclient.truststore.path", "truststore.path", "truststore.location");
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
  public static String getTrustStorePassword ()
  {
    String ret = _getAsStringOrFallback ("smpclient.truststore.password", "truststore.password");
    if (StringHelper.hasNoText (ret))
      ret = PeppolKeyStoreHelper.TRUSTSTORE_PASSWORD;
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
      return KeyStoreHelper.loadKeyStoreDirect (getTrustStoreType (), getTrustStorePath (), getTrustStorePassword ());
    }
    catch (final GeneralSecurityException | IOException ex)
    {
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
    final String sProxyHost = _getAsStringOrFallback ("http.proxy.host", "http.proxyHost");
    final int nProxyPort = _getAsIntOrFallback ("http.proxy.port", -1, 0, "http.proxyPort");
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
    final String sProxyUsername = _getAsStringOrFallback ("http.proxy.username", "http.proxyUsername");
    final String sProxyPassword = _getAsStringOrFallback ("http.proxy.password", "http.proxyPassword");
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
    return _getAsStringOrFallback ("http.proxy.nonProxyHosts", "http.nonProxyHosts");
  }

  /**
   * Get the content of the property "http.useSystemProperties" or
   * <code>false</code> if undefined.
   *
   * @return <code>true</code> if the SMP client proxy configuration should be
   *         take from the system properties, or <code>false</code> if not. The
   *         default behavior is to return <code>false</code>.
   * @since 5.2.4
   * @deprecated Since v8.7.2 To be removed in v9
   */
  @Deprecated
  public static boolean isUseProxySystemProperties ()
  {
    return getConfig ().getAsBoolean ("http.useSystemProperties", HttpClientSettings.DEFAULT_USE_SYSTEM_PROPERTIES);
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
    return HttpClientSettings.DEFAULT_CONNECTION_TIMEOUT;
  }

  /**
   * Get the content of the property "http.connect.timeout.ms" or the default
   * value.
   *
   * @return The connection timeout of the SMP client. Defaults to 5 seconds.
   * @since 7.0.4
   * @deprecated Since 8.8.0. Use {@link #getConnectTimeout()} instead.
   */
  @Nonnull
  @Deprecated
  public static Timeout getConnectionTimeout ()
  {
    return getConnectTimeout ();
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
    final long nMS = _getAsLongOrFallback ("http.response.timeout.ms", -1, -1, "http.request.timeout.ms");
    if (nMS >= 0)
      return Timeout.ofMilliseconds (nMS);
    return HttpClientSettings.DEFAULT_SOCKET_TIMEOUT;
  }

  /**
   * Get the content of the property "http.response.timeout.ms" or the default
   * value.
   *
   * @return The request timeout of the SMP client. Defaults to 10 seconds.
   * @since 7.0.4
   * @deprecated Since 8.8.0. Use {@link #getResponseTimeout()} instead.
   */
  @Nonnull
  @Deprecated
  public static Timeout getRequestTimeout ()
  {
    return getResponseTimeout ();
  }

  /**
   * This is a utility method, that takes the provided property names, checks if
   * they are defined in the configuration and if so, applies applies them as
   * System properties. It does it only when the configuration file was read
   * correctly.
   *
   * @param aPropertyNames
   *        The property names to consider.
   * @deprecated Since v8.7.2 To be removed in v9
   */
  @Deprecated
  public static void applyAsSystemProperties (@Nullable final String... aPropertyNames)
  {
    if (aPropertyNames != null)
      for (final String sProperty : aPropertyNames)
      {
        final ConfiguredValue aValue = getConfig ().getConfiguredValue (sProperty);
        if (aValue != null && aValue.getConfigurationSource ().getSourceType () == EConfigSourceType.RESOURCE)
        {
          final String sConfigFileValue = aValue.getValue ();
          SystemProperties.setPropertyValue (sProperty, sConfigFileValue);
          if (LOGGER.isInfoEnabled ())
            LOGGER.info ("Set Java system property from configuration: " + sProperty + "=" + sConfigFileValue);
        }
      }
  }

  /**
   * This is a utility method, that applies all Java network/proxy system
   * properties which are present in this configuration file. It does it only
   * when the configuration file was read correctly.
   *
   * @see SystemProperties#getAllJavaNetSystemProperties()
   * @deprecated Since v8.7.2 To be removed in v9
   */
  @Deprecated
  public static void applyAllNetworkSystemProperties ()
  {
    applyAsSystemProperties (SystemProperties.getAllJavaNetSystemProperties ());
  }
}
