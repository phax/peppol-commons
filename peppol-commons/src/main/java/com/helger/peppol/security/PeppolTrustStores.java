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
package com.helger.peppol.security;

import java.security.KeyStore;
import java.security.cert.X509Certificate;


import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.PresentForCodeCoverage;
import com.helger.edelivery.security.NetworkTrustStoreHelper;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.ITrustStoreDescriptor;
import com.helger.security.keystore.TrustStoreDescriptor;

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



  /**
   * The truststore configuration for Peppol PKI G2 valid from 2018 to 2028. The G2 CAs are
   * deprecated and should no longer be used. Use {@link Config2025} instead which contains only G3
   * CAs (since 12.4.2). Note: the "2018" truststores are no longer relevant for production use.
   *
   * @author Philip Helger
   * @deprecated The G2 PKI CAs are deprecated. Use {@link Config2025} instead which contains G3
   *             only (since 12.4.2).
   */
  @Immutable
  @Deprecated (forRemoval = false, since = "12.3.8")
  public static class Config2018
  {
    /** Truststore key store type - always JKS */
    @Deprecated
    public static final EKeyStoreType TRUSTSTORE_TYPE = EKeyStoreType.JKS;

    @Deprecated
    protected Config2018 ()
    {}

    // AP Production

    /**
     * The classpath entry referencing the global truststore with all OpenPeppol production entries.
     * It works for Access Points.
     */
    @Deprecated
    public static final String TRUSTSTORE_AP_PRODUCTION_CLASSPATH = "truststore/2018/prod-truststore.jks";

    @Deprecated
    public static final ITrustStoreDescriptor TRUSTSTORE_DESCRIPTOR_AP_PRODUCTION = TrustStoreDescriptor.builder ()
                                                                                                        .type (TRUSTSTORE_TYPE)
                                                                                                        .path (TRUSTSTORE_AP_PRODUCTION_CLASSPATH)
                                                                                                        .password (TRUSTSTORE_PASSWORD)
                                                                                                        .build ();

    /**
     * The full AP production truststore. Never modify.
     */
    @Deprecated
    public static final KeyStore TRUSTSTORE_AP_PRODUCTION = NetworkTrustStoreHelper.loadTrustStore (TRUSTSTORE_DESCRIPTOR_AP_PRODUCTION,
                                                                                                    "production AP");

    // SMP Production

    /**
     * The classpath entry referencing the global truststore with all OpenPeppol production entries.
     * It works for Access Points.
     */
    @Deprecated
    public static final String TRUSTSTORE_SMP_PRODUCTION_CLASSPATH = "truststore/2018/smp-prod-truststore.jks";

    @Deprecated
    public static final ITrustStoreDescriptor TRUSTSTORE_DESCRIPTOR_SMP_PRODUCTION = TrustStoreDescriptor.builder ()
                                                                                                         .type (TRUSTSTORE_TYPE)
                                                                                                         .path (TRUSTSTORE_SMP_PRODUCTION_CLASSPATH)
                                                                                                         .password (TRUSTSTORE_PASSWORD)
                                                                                                         .build ();

    /**
     * The full SMP production truststore. Never modify.
     */
    @Deprecated
    public static final KeyStore TRUSTSTORE_SMP_PRODUCTION = NetworkTrustStoreHelper.loadTrustStore (TRUSTSTORE_DESCRIPTOR_SMP_PRODUCTION,
                                                                                                     "SMP production");

    // Production CA certificates

    /** The truststore alias for the OpenPeppol production root certificate */
    @Deprecated
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_ROOT = "peppol root ca - g2";

    /** The OpenPeppol production root certificate */
    @Deprecated
    public static final X509Certificate CERTIFICATE_PRODUCTION_ROOT = NetworkTrustStoreHelper.resolveCertificate (TRUSTSTORE_AP_PRODUCTION,
                                                                                                                  TRUSTSTORE_PRODUCTION_ALIAS_ROOT);

    /** The truststore alias for the OpenPeppol production AP certificate */
    @Deprecated
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_AP = "peppol access point ca - g2 (peppol root ca - g2)";

    /** The OpenPeppol production AP certificate */
    @Deprecated
    public static final X509Certificate CERTIFICATE_PRODUCTION_AP = NetworkTrustStoreHelper.resolveCertificate (TRUSTSTORE_AP_PRODUCTION,
                                                                                                                TRUSTSTORE_PRODUCTION_ALIAS_AP);

    /** The truststore alias for the OpenPeppol production SMP certificate */
    @Deprecated
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_SMP = "peppol service metadata publisher ca - g2 (peppol root ca - g2)";

    /** The OpenPeppol production SMP certificate */
    @Deprecated
    public static final X509Certificate CERTIFICATE_PRODUCTION_SMP = NetworkTrustStoreHelper.resolveCertificate (TRUSTSTORE_AP_PRODUCTION,
                                                                                                                 TRUSTSTORE_PRODUCTION_ALIAS_SMP);

    // AP Test

    /**
     * The classpath entry referencing the global truststore with all OpenPeppol pilot entries for
     * an AP.
     */
    @Deprecated
    public static final String TRUSTSTORE_AP_PILOT_CLASSPATH = "truststore/2018/pilot-truststore.jks";

    @Deprecated
    public static final ITrustStoreDescriptor TRUSTSTORE_DESCRIPTOR_AP_PILOT = TrustStoreDescriptor.builder ()
                                                                                                   .type (TRUSTSTORE_TYPE)
                                                                                                   .path (TRUSTSTORE_AP_PILOT_CLASSPATH)
                                                                                                   .password (TRUSTSTORE_PASSWORD)
                                                                                                   .build ();

    /**
     * The full AP pilot truststore. Never modify.
     */
    @Deprecated
    public static final KeyStore TRUSTSTORE_AP_PILOT = NetworkTrustStoreHelper.loadTrustStore (TRUSTSTORE_DESCRIPTOR_AP_PILOT,
                                                                                               "AP pilot");

    // SMP Test

    /**
     * The classpath entry referencing the global truststore with all OpenPeppol pilot entries for
     * SMPs.
     */
    @Deprecated
    public static final String TRUSTSTORE_SMP_PILOT_CLASSPATH = "truststore/2018/smp-pilot-truststore.jks";

    @Deprecated
    public static final ITrustStoreDescriptor TRUSTSTORE_DESCRIPTOR_SMP_PILOT = TrustStoreDescriptor.builder ()
                                                                                                    .type (TRUSTSTORE_TYPE)
                                                                                                    .path (TRUSTSTORE_SMP_PILOT_CLASSPATH)
                                                                                                    .password (TRUSTSTORE_PASSWORD)
                                                                                                    .build ();

    /**
     * The full SMP pilot truststore. Never modify.
     */
    @Deprecated
    public static final KeyStore TRUSTSTORE_SMP_PILOT = NetworkTrustStoreHelper.loadTrustStore (TRUSTSTORE_DESCRIPTOR_SMP_PILOT,
                                                                                                "SMP pilot");

    // Test CA certificates

    /** The truststore alias for the OpenPeppol pilot root certificate */
    @Deprecated
    public static final String TRUSTSTORE_PILOT_ALIAS_ROOT = "peppol root test ca - g2";

    /** The OpenPeppol pilot root certificate */
    @Deprecated
    public static final X509Certificate CERTIFICATE_PILOT_ROOT = NetworkTrustStoreHelper.resolveCertificate (TRUSTSTORE_AP_PILOT,
                                                                                                             TRUSTSTORE_PILOT_ALIAS_ROOT);

    /** The truststore alias for the OpenPeppol pilot AP certificate */
    @Deprecated
    public static final String TRUSTSTORE_PILOT_ALIAS_AP = "peppol access point test ca - g2 (peppol root test ca - g2)";

    /** The OpenPeppol pilot AP certificate */
    @Deprecated
    public static final X509Certificate CERTIFICATE_PILOT_AP = NetworkTrustStoreHelper.resolveCertificate (TRUSTSTORE_AP_PILOT,
                                                                                                           TRUSTSTORE_PILOT_ALIAS_AP);

    /** The truststore alias for the OpenPeppol pilot SMP certificate */
    @Deprecated
    public static final String TRUSTSTORE_PILOT_ALIAS_SMP = "peppol service metadata publisher test ca - g2 (peppol root test ca - g2)";

    /** The OpenPeppol pilot SMP certificate */
    @Deprecated
    public static final X509Certificate CERTIFICATE_PILOT_SMP = NetworkTrustStoreHelper.resolveCertificate (TRUSTSTORE_AP_PILOT,
                                                                                                            TRUSTSTORE_PILOT_ALIAS_SMP);
  }

  /**
   * The truststore configuration for Peppol PKI G3 valid from 2025 to 2035. Since 12.4.2 the 2025
   * truststores no longer contain the deprecated G2 CAs - they contain G3 only.
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
    public static final KeyStore TRUSTSTORE_AP_PRODUCTION = NetworkTrustStoreHelper.loadTrustStore (TRUSTSTORE_DESCRIPTOR_AP_PRODUCTION,
                                                                                                    "production AP");

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
    public static final KeyStore TRUSTSTORE_SMP_PRODUCTION = NetworkTrustStoreHelper.loadTrustStore (TRUSTSTORE_DESCRIPTOR_SMP_PRODUCTION,
                                                                                                     "SMP production");

    // Production CA certificates

    /** The truststore alias for the OpenPeppol production root certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_ROOT = "peppol root ca - g3";

    /** The OpenPeppol production root certificate */
    public static final X509Certificate CERTIFICATE_PRODUCTION_ROOT = NetworkTrustStoreHelper.resolveCertificate (TRUSTSTORE_AP_PRODUCTION,
                                                                                                                  TRUSTSTORE_PRODUCTION_ALIAS_ROOT);

    /** The truststore alias for the OpenPeppol production AP certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_AP = "peppol access point ca - g3 (peppol root ca - g3)";

    /** The OpenPeppol production AP certificate */
    public static final X509Certificate CERTIFICATE_PRODUCTION_AP = NetworkTrustStoreHelper.resolveCertificate (TRUSTSTORE_AP_PRODUCTION,
                                                                                                                TRUSTSTORE_PRODUCTION_ALIAS_AP);

    /** The truststore alias for the OpenPeppol production SMP certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_SMP = "peppol service metadata publisher ca - g3 (peppol root ca - g3)";

    /** The OpenPeppol production SMP certificate */
    public static final X509Certificate CERTIFICATE_PRODUCTION_SMP = NetworkTrustStoreHelper.resolveCertificate (TRUSTSTORE_AP_PRODUCTION,
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
    public static final KeyStore TRUSTSTORE_AP_TEST = NetworkTrustStoreHelper.loadTrustStore (TRUSTSTORE_DESCRIPTOR_AP_TEST,
                                                                                              "AP test");

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
    public static final KeyStore TRUSTSTORE_SMP_TEST = NetworkTrustStoreHelper.loadTrustStore (TRUSTSTORE_DESCRIPTOR_SMP_TEST,
                                                                                               "SMP test");

    // Test CA certificates

    /** The truststore alias for the OpenPeppol test root certificate */
    public static final String TRUSTSTORE_TEST_ALIAS_ROOT = "peppol root test ca - g3";

    /** The OpenPeppol test root certificate */
    public static final X509Certificate CERTIFICATE_TEST_ROOT = NetworkTrustStoreHelper.resolveCertificate (TRUSTSTORE_AP_TEST,
                                                                                                            TRUSTSTORE_TEST_ALIAS_ROOT);

    /** The truststore alias for the OpenPeppol test AP certificate */
    public static final String TRUSTSTORE_TEST_ALIAS_AP = "peppol access point test ca - g3 (peppol root test ca - g3)";

    /** The OpenPeppol test AP certificate */
    public static final X509Certificate CERTIFICATE_TEST_AP = NetworkTrustStoreHelper.resolveCertificate (TRUSTSTORE_AP_TEST,
                                                                                                          TRUSTSTORE_TEST_ALIAS_AP);

    /** The truststore alias for the OpenPeppol test SMP certificate */
    public static final String TRUSTSTORE_TEST_ALIAS_SMP = "peppol service metadata publisher test ca - g3 (peppol root test ca - g3)";

    /** The OpenPeppol test SMP certificate */
    public static final X509Certificate CERTIFICATE_TEST_SMP = NetworkTrustStoreHelper.resolveCertificate (TRUSTSTORE_AP_TEST,
                                                                                                           TRUSTSTORE_TEST_ALIAS_SMP);
  }

  @PresentForCodeCoverage
  private static final PeppolTrustStores INSTANCE = new PeppolTrustStores ();

  private PeppolTrustStores ()
  {}
}
