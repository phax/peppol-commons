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
package com.helger.dbnalliance.commons.security;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.PresentForCodeCoverage;
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
  private static final Logger LOGGER = LoggerFactory.getLogger (DBNAllianceTrustStores.class);

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
   * The truststore configuration for DBNAlliance valid from 2023 to 2033.
   *
   * @author Philip Helger
   */
  @Immutable
  public static final class Config2023
  {
    /** Truststore key store type - always PKCS12 */
    public static final EKeyStoreType TRUSTSTORE_TYPE = EKeyStoreType.PKCS12;

    /** The password used to access the trust stores */
    public static final String TRUSTSTORE_PASSWORD = "dbnalliance";

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
    public static final KeyStore TRUSTSTORE_PILOT = TRUSTSTORE_DESCRIPTOR_PILOT.loadTrustStore ().getKeyStore ();

    static
    {
      if (TRUSTSTORE_PILOT == null)
        throw new IllegalStateException ("Failed to load pre-configured production Pilot trust store");
    }

    // Pilo CA certificates

    /** The truststore alias for the DBNAlliance Pilot root certificate */
    public static final String TRUSTSTORE_PILOT_ALIAS_ROOT = "dbnalliance demo root ca";

    /** The DBNAlliance Pilot certificate */
    public static final X509Certificate CERTIFICATE_PILOT_ROOT = _resolveCert (TRUSTSTORE_PILOT,
                                                                               TRUSTSTORE_PILOT_ALIAS_ROOT);

    /** The truststore alias for the DBNAlliance Pilot Intermediate certificate */
    public static final String TRUSTSTORE_PILOT_ALIAS_INTERMEDIATE = "dbnalliance demo intermediate test (dbnalliance demo root ca)";

    /** The DBNAlliance Pilot Intermediate certificate */
    public static final X509Certificate CERTIFICATE_PILOT_INTERMEDIATE = _resolveCert (TRUSTSTORE_PILOT,
                                                                                       TRUSTSTORE_PILOT_ALIAS_INTERMEDIATE);

    /** The Pilot CA checker */
    public static final TrustedCAChecker PILOT_CA = new TrustedCAChecker (CERTIFICATE_PILOT_INTERMEDIATE);

    private Config2023 ()
    {}
  }

  @PresentForCodeCoverage
  private static final DBNAllianceTrustStores INSTANCE = new DBNAllianceTrustStores ();

  private DBNAllianceTrustStores ()
  {}
}
