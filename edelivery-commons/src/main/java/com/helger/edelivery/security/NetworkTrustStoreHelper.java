/*
 * Copyright (C) 2015-2026 Philip Helger (www.helger.com)
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
package com.helger.edelivery.security;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.PresentForCodeCoverage;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.security.keystore.ITrustStoreDescriptor;

/**
 * Helper methods to deal with the predefined trust stores of a network. Every network ships its own
 * trust stores, but the mechanics of loading them and of resolving single certificates from them
 * are always the same.
 *
 * @author Philip Helger
 * @since 13.0.0
 */
@Immutable
public final class NetworkTrustStoreHelper
{
  private static final Logger LOGGER = LoggerFactory.getLogger (NetworkTrustStoreHelper.class);

  @PresentForCodeCoverage
  private static final NetworkTrustStoreHelper INSTANCE = new NetworkTrustStoreHelper ();

  private NetworkTrustStoreHelper ()
  {}

  /**
   * Load the trust store of the provided descriptor. Contrary to
   * {@link ITrustStoreDescriptor#loadTrustStore()} this method throws an exception if the trust
   * store could not be loaded, so that it can be used to initialize a static field.
   *
   * @param aDescriptor
   *        The trust store descriptor to load. May not be <code>null</code>.
   * @param sDisplayName
   *        The name of the trust store, to be used in the error message. May neither be
   *        <code>null</code> nor empty.
   * @return The loaded trust store. Never <code>null</code>.
   * @throws IllegalStateException
   *         If the trust store could not be loaded.
   */
  @NonNull
  public static KeyStore loadTrustStore (@NonNull final ITrustStoreDescriptor aDescriptor,
                                         @NonNull @Nonempty final String sDisplayName)
  {
    ValueEnforcer.notNull (aDescriptor, "Descriptor");
    ValueEnforcer.notEmpty (sDisplayName, "DisplayName");

    final KeyStore ret = aDescriptor.loadTrustStore ().getKeyStore ();
    if (ret == null)
      throw new IllegalStateException ("Failed to load the pre-configured " + sDisplayName + " trust store");
    return ret;
  }

  /**
   * Resolve a single certificate from a trust store.
   *
   * @param aKeyStore
   *        The trust store to search in. May not be <code>null</code>.
   * @param sAlias
   *        The alias to resolve. May neither be <code>null</code> nor empty.
   * @return <code>null</code> if the alias is not contained in the trust store.
   */
  @Nullable
  public static X509Certificate resolveCertificate (@NonNull final KeyStore aKeyStore,
                                                    @NonNull @Nonempty final String sAlias)
  {
    ValueEnforcer.notNull (aKeyStore, "KeyStore");
    ValueEnforcer.notEmpty (sAlias, "Alias");

    try
    {
      final X509Certificate ret = (X509Certificate) aKeyStore.getCertificate (sAlias);
      if (ret == null)
        LOGGER.warn ("Failed to resolve alias '" + sAlias + "' in trust store");
      return ret;
    }
    catch (final KeyStoreException ex)
    {
      LOGGER.warn ("Failed to resolve alias '" + sAlias + "' in trust store.", ex);
      return null;
    }
  }
}
