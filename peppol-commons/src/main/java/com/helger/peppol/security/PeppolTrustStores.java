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
package com.helger.peppol.security;

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
 * This class contains the predefined Peppol trust store handling.
 *
 * @author Philip Helger
 */
@Immutable
public final class PeppolTrustStores
{
  /** The password used to access the trust stores */
  public static final String TRUSTSTORE_PASSWORD = "peppol";

  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolTrustStores.class);

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
   * The truststore configuration for Peppol PKI G2 valid from 2018 to 2028.
   *
   * @author Philip Helger
   */
  @Immutable
  public static class Config2018
  {
    /** Truststore key store type - always JKS */
    public static final EKeyStoreType TRUSTSTORE_TYPE = EKeyStoreType.JKS;

    protected Config2018 ()
    {}

    // AP Production

    /**
     * The classpath entry referencing the global truststore with all OpenPeppol production entries.
     * It works for Access Points.
     */
    public static final String TRUSTSTORE_AP_PRODUCTION_CLASSPATH = "truststore/2018/prod-truststore.jks";

    public static final ITrustStoreDescriptor TRUSTSTORE_DESCRIPTOR_AP_PRODUCTION = TrustStoreDescriptor.builder ()
                                                                                                        .type (TRUSTSTORE_TYPE)
                                                                                                        .path (TRUSTSTORE_AP_PRODUCTION_CLASSPATH)
                                                                                                        .password (TRUSTSTORE_PASSWORD)
                                                                                                        .build ();

    /**
     * The full AP production truststore. Never modify.
     */
    public static final KeyStore TRUSTSTORE_AP_PRODUCTION = TRUSTSTORE_DESCRIPTOR_AP_PRODUCTION.loadTrustStore ()
                                                                                               .getKeyStore ();

    static
    {
      if (TRUSTSTORE_AP_PRODUCTION == null)
        throw new IllegalStateException ("Failed to load pre-configured production AP trust store");
    }

    // SMP Production

    /**
     * The classpath entry referencing the global truststore with all OpenPeppol production entries.
     * It works for Access Points.
     */
    public static final String TRUSTSTORE_SMP_PRODUCTION_CLASSPATH = "truststore/2018/smp-prod-truststore.jks";

    public static final ITrustStoreDescriptor TRUSTSTORE_DESCRIPTOR_SMP_PRODUCTION = TrustStoreDescriptor.builder ()
                                                                                                         .type (TRUSTSTORE_TYPE)
                                                                                                         .path (TRUSTSTORE_SMP_PRODUCTION_CLASSPATH)
                                                                                                         .password (TRUSTSTORE_PASSWORD)
                                                                                                         .build ();

    /**
     * The full SMP production truststore. Never modify.
     */
    public static final KeyStore TRUSTSTORE_SMP_PRODUCTION = TRUSTSTORE_DESCRIPTOR_SMP_PRODUCTION.loadTrustStore ()
                                                                                                 .getKeyStore ();

    static
    {
      if (TRUSTSTORE_SMP_PRODUCTION == null)
        throw new IllegalStateException ("Failed to load pre-configured SMP production trust store");
    }

    // Production CA certificates

    /** The truststore alias for the OpenPeppol production root certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_ROOT = "peppol root ca - g2";

    /** The OpenPeppol production root certificate */
    public static final X509Certificate CERTIFICATE_PRODUCTION_ROOT = _resolveCert (TRUSTSTORE_AP_PRODUCTION,
                                                                                    TRUSTSTORE_PRODUCTION_ALIAS_ROOT);

    /** The truststore alias for the OpenPeppol production AP certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_AP = "peppol access point ca - g2 (peppol root ca - g2)";

    /** The OpenPeppol production AP certificate */
    public static final X509Certificate CERTIFICATE_PRODUCTION_AP = _resolveCert (TRUSTSTORE_AP_PRODUCTION,
                                                                                  TRUSTSTORE_PRODUCTION_ALIAS_AP);

    /** The truststore alias for the OpenPeppol production SMP certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_SMP = "peppol service metadata publisher ca - g2 (peppol root ca - g2)";

    /** The OpenPeppol production SMP certificate */
    public static final X509Certificate CERTIFICATE_PRODUCTION_SMP = _resolveCert (TRUSTSTORE_AP_PRODUCTION,
                                                                                   TRUSTSTORE_PRODUCTION_ALIAS_SMP);

    // AP Test

    /**
     * The classpath entry referencing the global truststore with all OpenPeppol pilot entries for
     * an AP.
     */
    public static final String TRUSTSTORE_AP_PILOT_CLASSPATH = "truststore/2018/pilot-truststore.jks";

    public static final ITrustStoreDescriptor TRUSTSTORE_DESCRIPTOR_AP_PILOT = TrustStoreDescriptor.builder ()
                                                                                                   .type (TRUSTSTORE_TYPE)
                                                                                                   .path (TRUSTSTORE_AP_PILOT_CLASSPATH)
                                                                                                   .password (TRUSTSTORE_PASSWORD)
                                                                                                   .build ();

    /**
     * The full AP pilot truststore. Never modify.
     */
    public static final KeyStore TRUSTSTORE_AP_PILOT = TRUSTSTORE_DESCRIPTOR_AP_PILOT.loadTrustStore ().getKeyStore ();

    static
    {
      if (TRUSTSTORE_AP_PILOT == null)
        throw new IllegalStateException ("Failed to load pre-configured AP pilot trust store");
    }

    // SMP Test

    /**
     * The classpath entry referencing the global truststore with all OpenPeppol pilot entries for
     * SMPs.
     */
    public static final String TRUSTSTORE_SMP_PILOT_CLASSPATH = "truststore/2018/smp-pilot-truststore.jks";

    public static final ITrustStoreDescriptor TRUSTSTORE_DESCRIPTOR_SMP_PILOT = TrustStoreDescriptor.builder ()
                                                                                                    .type (TRUSTSTORE_TYPE)
                                                                                                    .path (TRUSTSTORE_SMP_PILOT_CLASSPATH)
                                                                                                    .password (TRUSTSTORE_PASSWORD)
                                                                                                    .build ();

    /**
     * The full SMP pilot truststore. Never modify.
     */
    public static final KeyStore TRUSTSTORE_SMP_PILOT = TRUSTSTORE_DESCRIPTOR_SMP_PILOT.loadTrustStore ()
                                                                                       .getKeyStore ();

    static
    {
      if (TRUSTSTORE_SMP_PILOT == null)
        throw new IllegalStateException ("Failed to load pre-configured SMP pilot trust store");
    }

    // Test CA certificates

    /** The truststore alias for the OpenPeppol pilot root certificate */
    public static final String TRUSTSTORE_PILOT_ALIAS_ROOT = "peppol root test ca - g2";

    /** The OpenPeppol pilot root certificate */
    public static final X509Certificate CERTIFICATE_PILOT_ROOT = _resolveCert (TRUSTSTORE_AP_PILOT,
                                                                               TRUSTSTORE_PILOT_ALIAS_ROOT);

    /** The truststore alias for the OpenPeppol pilot AP certificate */
    public static final String TRUSTSTORE_PILOT_ALIAS_AP = "peppol access point test ca - g2 (peppol root test ca - g2)";

    /** The OpenPeppol pilot AP certificate */
    public static final X509Certificate CERTIFICATE_PILOT_AP = _resolveCert (TRUSTSTORE_AP_PILOT,
                                                                             TRUSTSTORE_PILOT_ALIAS_AP);

    /** The truststore alias for the OpenPeppol pilot SMP certificate */
    public static final String TRUSTSTORE_PILOT_ALIAS_SMP = "peppol service metadata publisher test ca - g2 (peppol root test ca - g2)";

    /** The OpenPeppol pilot SMP certificate */
    public static final X509Certificate CERTIFICATE_PILOT_SMP = _resolveCert (TRUSTSTORE_AP_PILOT,
                                                                              TRUSTSTORE_PILOT_ALIAS_SMP);
  }

  /**
   * The truststore configuration for Peppol PKI G3 valid from 2025 to 2035.
   *
   * @author Philip Helger
   * @since 11.0.2
   */
  @Immutable
  public static class Config2025
  {
    /** Truststore key store type - always PKCS 12 */
    public static final EKeyStoreType TRUSTSTORE_TYPE = EKeyStoreType.PKCS12;

    protected Config2025 ()
    {}

    // AP Production

    /**
     * The classpath entry referencing the global truststore with all OpenPeppol production entries.
     * It works for Access Points.
     */
    public static final String TRUSTSTORE_AP_PRODUCTION_CLASSPATH = "truststore/2025/ap-prod-truststore.p12";

    public static final ITrustStoreDescriptor TRUSTSTORE_DESCRIPTOR_AP_PRODUCTION = TrustStoreDescriptor.builder ()
                                                                                                        .type (TRUSTSTORE_TYPE)
                                                                                                        .path (TRUSTSTORE_AP_PRODUCTION_CLASSPATH)
                                                                                                        .password (TRUSTSTORE_PASSWORD)
                                                                                                        .build ();

    /**
     * The full AP production truststore. Never modify.
     */
    public static final KeyStore TRUSTSTORE_AP_PRODUCTION = TRUSTSTORE_DESCRIPTOR_AP_PRODUCTION.loadTrustStore ()
                                                                                               .getKeyStore ();

    static
    {
      if (TRUSTSTORE_AP_PRODUCTION == null)
        throw new IllegalStateException ("Failed to load pre-configured production AP trust store");
    }

    // SMP Production

    /**
     * The classpath entry referencing the global truststore with all OpenPeppol production entries.
     * It works for Access Points.
     */
    public static final String TRUSTSTORE_SMP_PRODUCTION_CLASSPATH = "truststore/2025/smp-prod-truststore.p12";

    public static final ITrustStoreDescriptor TRUSTSTORE_DESCRIPTOR_SMP_PRODUCTION = TrustStoreDescriptor.builder ()
                                                                                                         .type (TRUSTSTORE_TYPE)
                                                                                                         .path (TRUSTSTORE_SMP_PRODUCTION_CLASSPATH)
                                                                                                         .password (TRUSTSTORE_PASSWORD)
                                                                                                         .build ();

    /**
     * The full SMP production truststore. Never modify.
     */
    public static final KeyStore TRUSTSTORE_SMP_PRODUCTION = TRUSTSTORE_DESCRIPTOR_SMP_PRODUCTION.loadTrustStore ()
                                                                                                 .getKeyStore ();

    static
    {
      if (TRUSTSTORE_SMP_PRODUCTION == null)
        throw new IllegalStateException ("Failed to load pre-configured SMP production trust store");
    }

    // Production CA certificates

    /** The truststore alias for the OpenPeppol production root certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_ROOT = "peppol root ca - g3";

    /** The OpenPeppol production root certificate */
    public static final X509Certificate CERTIFICATE_PRODUCTION_ROOT = _resolveCert (TRUSTSTORE_AP_PRODUCTION,
                                                                                    TRUSTSTORE_PRODUCTION_ALIAS_ROOT);

    /** The truststore alias for the OpenPeppol production AP certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_AP = "peppol access point ca - g3 (peppol root ca - g3)";

    /** The OpenPeppol production AP certificate */
    public static final X509Certificate CERTIFICATE_PRODUCTION_AP = _resolveCert (TRUSTSTORE_AP_PRODUCTION,
                                                                                  TRUSTSTORE_PRODUCTION_ALIAS_AP);

    /** The truststore alias for the OpenPeppol production SMP certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_SMP = "peppol service metadata publisher ca - g3 (peppol root ca - g3)";

    /** The OpenPeppol production SMP certificate */
    public static final X509Certificate CERTIFICATE_PRODUCTION_SMP = _resolveCert (TRUSTSTORE_AP_PRODUCTION,
                                                                                   TRUSTSTORE_PRODUCTION_ALIAS_SMP);

    // AP Test

    /**
     * The classpath entry referencing the global truststore with all OpenPeppol test entries for an
     * AP.
     */
    public static final String TRUSTSTORE_AP_TEST_CLASSPATH = "truststore/2025/ap-test-truststore.p12";

    public static final ITrustStoreDescriptor TRUSTSTORE_DESCRIPTOR_AP_TEST = TrustStoreDescriptor.builder ()
                                                                                                  .type (TRUSTSTORE_TYPE)
                                                                                                  .path (TRUSTSTORE_AP_TEST_CLASSPATH)
                                                                                                  .password (TRUSTSTORE_PASSWORD)
                                                                                                  .build ();

    /**
     * The full AP test truststore. Never modify.
     */
    public static final KeyStore TRUSTSTORE_AP_TEST = TRUSTSTORE_DESCRIPTOR_AP_TEST.loadTrustStore ().getKeyStore ();

    static
    {
      if (TRUSTSTORE_AP_TEST == null)
        throw new IllegalStateException ("Failed to load pre-configured AP test trust store");
    }

    // SMP Test

    /**
     * The classpath entry referencing the global truststore with all OpenPeppol test entries for
     * SMPs.
     */
    public static final String TRUSTSTORE_SMP_TEST_CLASSPATH = "truststore/2025/smp-test-truststore.p12";

    public static final ITrustStoreDescriptor TRUSTSTORE_DESCRIPTOR_SMP_TEST = TrustStoreDescriptor.builder ()
                                                                                                   .type (TRUSTSTORE_TYPE)
                                                                                                   .path (TRUSTSTORE_SMP_TEST_CLASSPATH)
                                                                                                   .password (TRUSTSTORE_PASSWORD)
                                                                                                   .build ();

    /**
     * The full SMP test truststore. Never modify.
     */
    public static final KeyStore TRUSTSTORE_SMP_TEST = TRUSTSTORE_DESCRIPTOR_SMP_TEST.loadTrustStore ().getKeyStore ();

    static
    {
      if (TRUSTSTORE_SMP_TEST == null)
        throw new IllegalStateException ("Failed to load pre-configured SMP test trust store");
    }

    // Test CA certificates

    /** The truststore alias for the OpenPeppol test root certificate */
    public static final String TRUSTSTORE_TEST_ALIAS_ROOT = "peppol root test ca - g3";

    /** The OpenPeppol test root certificate */
    public static final X509Certificate CERTIFICATE_TEST_ROOT = _resolveCert (TRUSTSTORE_AP_TEST,
                                                                              TRUSTSTORE_TEST_ALIAS_ROOT);

    /** The truststore alias for the OpenPeppol test AP certificate */
    public static final String TRUSTSTORE_TEST_ALIAS_AP = "peppol access point test ca - g3 (peppol root test ca - g3)";

    /** The OpenPeppol test AP certificate */
    public static final X509Certificate CERTIFICATE_TEST_AP = _resolveCert (TRUSTSTORE_AP_TEST,
                                                                            TRUSTSTORE_TEST_ALIAS_AP);

    /** The truststore alias for the OpenPeppol test SMP certificate */
    public static final String TRUSTSTORE_TEST_ALIAS_SMP = "peppol service metadata publisher test ca - g3 (peppol root test ca - g3)";

    /** The OpenPeppol test SMP certificate */
    public static final X509Certificate CERTIFICATE_TEST_SMP = _resolveCert (TRUSTSTORE_AP_TEST,
                                                                             TRUSTSTORE_TEST_ALIAS_SMP);
  }

  @PresentForCodeCoverage
  private static final PeppolTrustStores INSTANCE = new PeppolTrustStores ();

  private PeppolTrustStores ()
  {}
}
