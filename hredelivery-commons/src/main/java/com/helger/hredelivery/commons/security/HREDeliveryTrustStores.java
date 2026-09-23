/*
 * Copyright (C) 2025-2026 Philip Helger
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
import java.security.cert.X509Certificate;


import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.PresentForCodeCoverage;
import com.helger.edelivery.security.NetworkTrustStoreHelper;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.ITrustStoreDescriptor;
import com.helger.security.keystore.TrustStoreDescriptor;

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



  /**
   * The truststore configuration for FINA root CA valid from 2015 to 2035.
   *
   * @author Philip Helger
   */
  @Immutable
  public static class Fina2015
  {
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
    public static final KeyStore TRUSTSTORE_DEMO = NetworkTrustStoreHelper.loadTrustStore (TRUSTSTORE_DESCRIPTOR_DEMO,
                                                                                           "demo");

    // Demo CA certificates

    /** The truststore alias for the Fina demo root certificate */
    public static final String TRUSTSTORE_DEMO_ALIAS_ROOT = "fina demo root ca";

    /** The Fina demo root certificate */
    public static final X509Certificate CERTIFICATE_DEMO_ROOT = NetworkTrustStoreHelper.resolveCertificate (TRUSTSTORE_DEMO,
                                                                                                            TRUSTSTORE_DEMO_ALIAS_ROOT);

    /** The truststore alias for the Fina Demo CA 2020 certificate */
    public static final String TRUSTSTORE_DEMO_ALIAS_DEMO_CA_2020 = "fina demo ca 2020 (fina demo root ca)";

    /** The Fina Demo CA 2020 certificate */
    public static final X509Certificate CERTIFICATE_DEMO_CA_2020 = NetworkTrustStoreHelper.resolveCertificate (TRUSTSTORE_DEMO,
                                                                                                               TRUSTSTORE_DEMO_ALIAS_DEMO_CA_2020);

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
    public static final KeyStore TRUSTSTORE_PRODUCTION = NetworkTrustStoreHelper.loadTrustStore (TRUSTSTORE_DESCRIPTOR_PRODUCTION,
                                                                                                 "production");

    // Production CA certificates

    /** The truststore alias for the Fina production root certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_ROOT = "fina root ca";

    /** The Fina production root certificate */
    public static final X509Certificate CERTIFICATE_PRODUCTION_ROOT = NetworkTrustStoreHelper.resolveCertificate (TRUSTSTORE_PRODUCTION,
                                                                                                                  TRUSTSTORE_PRODUCTION_ALIAS_ROOT);

    /** The truststore alias for the Fina production RDC 2020 CA certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_RDC_2020 = "fina rdc 2020 (fina root ca)";

    /** The Fina RDC 2020 CA certificate */
    public static final X509Certificate CERTIFICATE_PRODUCTION_RDC_2020 = NetworkTrustStoreHelper.resolveCertificate (TRUSTSTORE_PRODUCTION,
                                                                                                                      TRUSTSTORE_PRODUCTION_ALIAS_RDC_2020);

    /** The truststore alias for the Fina production RDC 2025 CA certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_RDC_2025 = "fina rdc 2025 (fina root ca)";

    /** The Fina RDC 2025 CA certificate */
    public static final X509Certificate CERTIFICATE_PRODUCTION_RDC_2025 = NetworkTrustStoreHelper.resolveCertificate (TRUSTSTORE_PRODUCTION,
                                                                                                                      TRUSTSTORE_PRODUCTION_ALIAS_RDC_2025);

    // Complete
    // Production

    /**
     * The classpath entry referencing the global truststore with all Fina entries.
     */
    public static final String TRUSTSTORE_COMPLETE_CLASSPATH = "truststore-hredelivery/truststore-fina-complete.p12";

    public static final ITrustStoreDescriptor TRUSTSTORE_DESCRIPTOR_COMPLETE = TrustStoreDescriptor.builder ()
                                                                                                   .type (TRUSTSTORE_TYPE)
                                                                                                   .path (TRUSTSTORE_COMPLETE_CLASSPATH)
                                                                                                   .password (TRUSTSTORE_PASSWORD)
                                                                                                   .build ();

    /**
     * The full Fina complete truststore. Never modify.
     */
    public static final KeyStore TRUSTSTORE_COMPLETE = NetworkTrustStoreHelper.loadTrustStore (TRUSTSTORE_DESCRIPTOR_COMPLETE,
                                                                                               "complete");
  }

  @PresentForCodeCoverage
  private static final HREDeliveryTrustStores INSTANCE = new HREDeliveryTrustStores ();

  private HREDeliveryTrustStores ()
  {}
}
