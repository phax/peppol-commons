/*
 * Copyright (C) 2025 Philip Helger
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
package com.helger.hredelivery.commons.security;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.PresentForCodeCoverage;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.ITrustStoreDescriptor;
import com.helger.security.keystore.TrustStoreDescriptor;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Helper methods to access HR eDelivery Trust Stores.
 *
 * @author Philip Helger
 * @since 12.0.2
 */
@Immutable
public final class HREDeliveryTrustStores
{
  /** Truststore key store type - always PKCS12 */
  public static final EKeyStoreType TRUSTSTORE_TYPE = EKeyStoreType.PKCS12;

  /** The password used to access the trust stores */
  public static final String TRUSTSTORE_PASSWORD = "hredelivery";

  private static final Logger LOGGER = LoggerFactory.getLogger (HREDeliveryTrustStores.class);

  @Nullable
  private static X509Certificate _resolveCert (@Nonnull final KeyStore aKeyStore,
                                               @Nonnull @Nonempty final String sAlias)
  {
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

  /**
   * The truststore configuration for FINA root CA valid from 2015 to 2035.
   *
   * @author Philip Helger
   */
  @Immutable
  public static class Fina2015
  {
    // Production

    /**
     * The classpath entry referencing the global truststore with all Fina production entries.
     */
    public static final String TRUSTSTORE_PRODUCTION_CLASSPATH = "truststore-hredelivery/truststore-fina-prod.p12";

    public static final ITrustStoreDescriptor TRUSTSTORE_DESCRIPTOR_PRODUCTION = TrustStoreDescriptor.builder ()
                                                                                                     .type (TRUSTSTORE_TYPE)
                                                                                                     .path (TRUSTSTORE_PRODUCTION_CLASSPATH)
                                                                                                     .password (TRUSTSTORE_PASSWORD)
                                                                                                     .build ();

    /**
     * The full Fina production truststore. Never modify.
     */
    public static final KeyStore TRUSTSTORE_PRODUCTION = TRUSTSTORE_DESCRIPTOR_PRODUCTION.loadTrustStore ()
                                                                                         .getKeyStore ();

    static
    {
      if (TRUSTSTORE_PRODUCTION == null)
        throw new IllegalStateException ("Failed to load pre-configured production trust store");
    }

    // Production CA certificates

    /** The truststore alias for the Fina production root certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_ROOT = "fina root ca";

    /** The Fina production root certificate */
    public static final X509Certificate CERTIFICATE_PRODUCTION_ROOT = _resolveCert (TRUSTSTORE_PRODUCTION,
                                                                                    TRUSTSTORE_PRODUCTION_ALIAS_ROOT);

    /** The truststore alias for the Fina production RDC 2020 CA certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_RDC_2020 = "fina rdc 2020 (fina root ca)";

    /** The Fina RDC 2020 CA certificate */
    public static final X509Certificate CERTIFICATE_PRODUCTION_RDC_2020 = _resolveCert (TRUSTSTORE_PRODUCTION,
                                                                                        TRUSTSTORE_PRODUCTION_ALIAS_RDC_2020);

    // Test

    /**
     * The classpath entry referencing the global truststore with all Fina demo entries.
     */
    public static final String TRUSTSTORE_DEMO_CLASSPATH = "truststore-hredelivery/truststore-fina-demo.p12";

    public static final ITrustStoreDescriptor TRUSTSTORE_DESCRIPTOR_DEMO = TrustStoreDescriptor.builder ()
                                                                                               .type (TRUSTSTORE_TYPE)
                                                                                               .path (TRUSTSTORE_DEMO_CLASSPATH)
                                                                                               .password (TRUSTSTORE_PASSWORD)
                                                                                               .build ();

    /**
     * The full Fina demo truststore. Never modify.
     */
    public static final KeyStore TRUSTSTORE_DEMO = TRUSTSTORE_DESCRIPTOR_DEMO.loadTrustStore ().getKeyStore ();

    static
    {
      if (TRUSTSTORE_DEMO == null)
        throw new IllegalStateException ("Failed to load pre-configured demo trust store");
    }

    // Demo CA certificates

    /** The truststore alias for the Fina demo root certificate */
    public static final String TRUSTSTORE_DEMO_ALIAS_ROOT = "fina demo root ca";

    /** The Fina demo root certificate */
    public static final X509Certificate CERTIFICATE_DEMO_ROOT = _resolveCert (TRUSTSTORE_DEMO,
                                                                              TRUSTSTORE_DEMO_ALIAS_ROOT);

    /** The truststore alias for the Fina Demo CA 2020 certificate */
    public static final String TRUSTSTORE_DEMO_ALIAS_DEMO_CA_2020 = "fina demo ca 2020 (fina demo root ca)";

    /** The Fina Demo CA 2020 certificate */
    public static final X509Certificate CERTIFICATE_DEMO_CA_2020 = _resolveCert (TRUSTSTORE_DEMO,
                                                                                 TRUSTSTORE_DEMO_ALIAS_DEMO_CA_2020);
  }

  @PresentForCodeCoverage
  private static final HREDeliveryTrustStores INSTANCE = new HREDeliveryTrustStores ();

  private HREDeliveryTrustStores ()
  {}
}
