/*
 * Copyright (C) 2015-2024 Philip Helger
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
package com.helger.peppol.utils;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsHashSet;
import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.commons.text.util.TextHelper;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.ITrustStoreDescriptor;
import com.helger.security.keystore.KeyStoreHelper;
import com.helger.security.keystore.LoadedKey;
import com.helger.security.keystore.LoadedKeyStore;
import com.helger.security.keystore.TrustStoreDescriptor;

/**
 * Helper methods to access Java key stores of type JKS (Java KeyStore).
 *
 * @author Philip Helger
 */
@Immutable
public final class PeppolKeyStoreHelper
{
  /** Truststore key store type - always JKS */
  public static final EKeyStoreType TRUSTSTORE_TYPE = EKeyStoreType.JKS;

  /**
   * The classpath entry referencing the complete truststore with all OpenPeppol
   * production AND pilot entries
   */
  public static final String TRUSTSTORE_COMPLETE_CLASSPATH = "truststore/complete-truststore.jks";

  /** The password used to access the trust stores */
  public static final String TRUSTSTORE_PASSWORD = "peppol";

  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolKeyStoreHelper.class);

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
   * The truststore configuration for Peppol PKI v3 valid from 2018 to 2028.
   *
   * @author Philip Helger
   */
  @Immutable
  public static final class Config2018
  {
    private Config2018 ()
    {}

    // AP Production

    /**
     * The classpath entry referencing the global truststore with all OpenPeppol
     * production entries. It works for Access Points.
     */
    public static final String TRUSTSTORE_AP_PRODUCTION_CLASSPATH = "truststore/2018/prod-truststore.jks";

    /**
     * The classpath entry referencing the global truststore with all OpenPeppol
     * production entries. It works for Access Points.
     */
    @Deprecated (forRemoval = true, since = "9.1.4")
    public static final String TRUSTSTORE_PRODUCTION_CLASSPATH = TRUSTSTORE_AP_PRODUCTION_CLASSPATH;

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

    /**
     * The full AP production truststore. Never modify.
     */
    @Deprecated (forRemoval = true, since = "9.1.4")
    public static final KeyStore TRUSTSTORE_PRODUCTION = TRUSTSTORE_AP_PRODUCTION;

    static
    {
      if (TRUSTSTORE_AP_PRODUCTION == null)
        throw new IllegalStateException ("Failed to load pre-configured production trust store");
    }

    // SMP Production

    /**
     * The classpath entry referencing the global truststore with all OpenPeppol
     * production entries. It works for Access Points.
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
     * The classpath entry referencing the global truststore with all OpenPeppol
     * pilot entries for an AP.
     */
    public static final String TRUSTSTORE_AP_PILOT_CLASSPATH = "truststore/2018/pilot-truststore.jks";

    /**
     * The classpath entry referencing the global truststore with all OpenPeppol
     * pilot entries for an AP.
     */
    @Deprecated (forRemoval = true, since = "9.1.4")
    public static final String TRUSTSTORE_PILOT_CLASSPATH = TRUSTSTORE_AP_PILOT_CLASSPATH;

    public static final ITrustStoreDescriptor TRUSTSTORE_DESCRIPTOR_AP_PILOT = TrustStoreDescriptor.builder ()
                                                                                                   .type (TRUSTSTORE_TYPE)
                                                                                                   .path (TRUSTSTORE_AP_PILOT_CLASSPATH)
                                                                                                   .password (TRUSTSTORE_PASSWORD)
                                                                                                   .build ();

    /**
     * The full AP pilot truststore. Never modify.
     */
    public static final KeyStore TRUSTSTORE_AP_PILOT = TRUSTSTORE_DESCRIPTOR_AP_PILOT.loadTrustStore ().getKeyStore ();

    /**
     * The full AP pilot truststore. Never modify.
     */
    @Deprecated (forRemoval = true, since = "9.1.4")
    public static final KeyStore TRUSTSTORE_PILOT = TRUSTSTORE_AP_PILOT;

    static
    {
      if (TRUSTSTORE_AP_PILOT == null)
        throw new IllegalStateException ("Failed to load pre-configured pilot trust store");
    }

    // SMP Test

    /**
     * The classpath entry referencing the global truststore with all OpenPeppol
     * pilot entries for SMPs.
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

  @PresentForCodeCoverage
  private static final PeppolKeyStoreHelper INSTANCE = new PeppolKeyStoreHelper ();

  private PeppolKeyStoreHelper ()
  {}

  @Nullable
  public static String getLoadError (@Nonnull final LoadedKeyStore aLKS)
  {
    return aLKS == null ? null : aLKS.getErrorText (TextHelper.EN);
  }

  @Nullable
  public static String getLoadError (@Nonnull final LoadedKey <?> aLK)
  {
    return aLK == null ? null : aLK.getErrorText (TextHelper.EN);
  }

  /**
   * Get all trusted certificates
   *
   * @param aTrustStore
   *        Trust store to iterate
   * @return A non-<code>null</code> set of all trusted certificates. Never
   *         <code>null</code>.
   */
  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsSet <X509Certificate> getAllTrustedCertificates (@Nullable final KeyStore aTrustStore)
  {
    final ICommonsSet <X509Certificate> aCerts = new CommonsHashSet <> ();
    if (aTrustStore != null)
      KeyStoreHelper.iterateKeyStore (aTrustStore, alias -> {
        if (aTrustStore.isCertificateEntry (alias))
        {
          final Certificate aCert = aTrustStore.getCertificate (alias);
          if (aCert instanceof X509Certificate)
            aCerts.add ((X509Certificate) aCert);
        }
      });
    return aCerts;
  }
}
