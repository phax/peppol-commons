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
package com.helger.dbnalliance.commons.security;

import java.security.KeyStore;
import java.security.cert.X509Certificate;


import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.PresentForCodeCoverage;
import com.helger.network.security.NetworkTrustStoreHelper;
import com.helger.security.certificate.TrustedCAChecker;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.ITrustStoreDescriptor;
import com.helger.security.keystore.TrustStoreDescriptor;

/**
 * Helper methods to access DBNAlliance Trust Stores.
 *
 * @author Philip Helger
 */
@Immutable
public final class DBNAllianceTrustStores
{
  /** The password used to access the trust stores */
  public static final String TRUSTSTORE_PASSWORD = "dbnalliance";



  /**
   * The truststore configuration for DBNAlliance valid from 2023 to 2033.
   *
   * @author Philip Helger
   */
  @Immutable
  public static final class Config2023
  {
    /** Truststore key store type - always PKCS12 */
    public static final EKeyStoreType TRUSTSTORE_TYPE = EKeyStoreType.PKCS12;

    // Pilot
    public static final String TRUSTSTORE_PILOT_CLASSPATH = "truststore-dbnalliance/2023/pilot-truststore.p12";

    public static final ITrustStoreDescriptor TRUSTSTORE_DESCRIPTOR_PILOT = TrustStoreDescriptor.builder ()
                                                                                                .type (TRUSTSTORE_TYPE)
                                                                                                .path (TRUSTSTORE_PILOT_CLASSPATH)
                                                                                                .password (TRUSTSTORE_PASSWORD)
                                                                                                .build ();

    /**
     * The full Pilot truststore. Never modify.
     */
    public static final KeyStore TRUSTSTORE_PILOT = NetworkTrustStoreHelper.loadTrustStore (TRUSTSTORE_DESCRIPTOR_PILOT,
                                                                                            "production Pilot");

    // Pilo CA certificates

    /** The truststore alias for the DBNAlliance Pilot root certificate */
    public static final String TRUSTSTORE_PILOT_ALIAS_ROOT = "dbnalliance demo root ca";

    /** The DBNAlliance Pilot certificate */
    public static final X509Certificate CERTIFICATE_PILOT_ROOT = NetworkTrustStoreHelper.resolveCertificate (TRUSTSTORE_PILOT,
                                                                                                             TRUSTSTORE_PILOT_ALIAS_ROOT);

    /** The truststore alias for the DBNAlliance Pilot Intermediate certificate */
    public static final String TRUSTSTORE_PILOT_ALIAS_INTERMEDIATE = "dbnalliance demo intermediate test (dbnalliance demo root ca)";

    /** The DBNAlliance Pilot Intermediate certificate */
    public static final X509Certificate CERTIFICATE_PILOT_INTERMEDIATE = NetworkTrustStoreHelper.resolveCertificate (TRUSTSTORE_PILOT,
                                                                                                                     TRUSTSTORE_PILOT_ALIAS_INTERMEDIATE);

    /** The Pilot CA checker */
    public static final TrustedCAChecker PILOT_CA = TrustedCAChecker.builder ()
                                                                    .trustedCACertificates (CERTIFICATE_PILOT_INTERMEDIATE)
                                                                    .build ();

    private Config2023 ()
    {}
  }

  @PresentForCodeCoverage
  private static final DBNAllianceTrustStores INSTANCE = new DBNAllianceTrustStores ();

  private DBNAllianceTrustStores ()
  {}
}
