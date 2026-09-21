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
package com.helger.smpclient.config;

import java.security.KeyStore;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.concurrent.GuardedBy;
import com.helger.annotation.concurrent.Immutable;
import com.helger.base.concurrent.SimpleReadWriteLock;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.equals.EqualsHelper;
import com.helger.config.ConfigFactory;
import com.helger.config.IConfig;
import com.helger.config.fallback.ConfigWithFallback;
import com.helger.config.fallback.IConfigWithFallback;
import com.helger.config.source.MultiConfigurationValueProvider;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.KeyStoreHelper;

/**
 * This class manages the configuration properties of the SMP client. The order of the properties
 * file resolving is as follows:
 * <ol>
 * <li>Check for the value of the system property
 * <code>peppol.smp.client.properties.path</code></li>
 * <li>Check for the value of the system property <code>smp.client.properties.path</code></li>
 * <li>The filename <code>private-smp-client.properties</code> in the root of the classpath</li>
 * <li>The filename <code>smp-client.properties</code> in the root of the classpath</li>
 * </ol>
 * <p>
 * Note: this class is also licensed under Apache 2 license, as it was not part of the original
 * implementation
 * </p>
 *
 * @author Philip Helger
 */
@Immutable
public final class SMPClientConfiguration
{
  private static final Logger LOGGER = LoggerFactory.getLogger (SMPClientConfiguration.class);

  /**
   * @return The configuration value provider for SMP client that contains backward compatibility
   *         support.
   */
  @NonNull
  public static MultiConfigurationValueProvider createSMPClientValueProvider ()
  {
    // Start with default setup
    return ConfigFactory.createDefaultValueProvider ();
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
  @NonNull
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
  @NonNull
  public static IConfigWithFallback setConfig (@NonNull final IConfigWithFallback aNewConfig)
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
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("The SMPClient configuration provider was changed to " + aNewConfig);
      else
        LOGGER.info ("The SMPClient configuration provider was changed");
    return ret;
  }

  /**
   * @return The truststore type as specified in the configuration file by the key
   *         <code>smpclient.truststore.type</code> or <code>null</code>.
   * @since 6.0.0
   */
  @Nullable
  public static EKeyStoreType getTrustStoreType ()
  {
    final String ret = getConfig ().getAsString ("smpclient.truststore.type");
    return EKeyStoreType.getFromIDCaseInsensitiveOrDefault (ret, null);
  }

  /**
   * @return The truststore location as specified in the configuration file by the key
   *         <code>tsmpclient.truststore.path</code>.
   * @since 6.0.0 - was getTruststoreLocation before
   */
  @NonNull
  public static String getTrustStorePath ()
  {
    return getConfig ().getAsString ("smpclient.truststore.path");
  }

  /**
   * @return The truststore password as specified in the configuration file by the key
   *         <code>smpclient.truststore.password</code>. If none is present
   *         no password is used.
   */
  @Nullable
  public static char [] getTrustStorePasswordCharArray ()
  {
    return getConfig ().getAsCharArray ("smpclient.truststore.password");
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
      final var eType = getTrustStoreType ();
      final var sPath = getTrustStorePath ();
      if (eType == null || sPath == null)
        return null;

      return KeyStoreHelper.loadKeyStoreDirect (eType, sPath, getTrustStorePasswordCharArray ());
    }
    catch (final Exception ex)
    {
      // May also be a runtime exception if the path is invalid
      LOGGER.warn ("Failed to load SMP client truststore: " + ex.getClass ().getName () + " - " + ex.getMessage ());
      return null;
    }
  }
}
