/**
 * Copyright (C) 2015-2019 Philip Helger
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
import java.security.cert.X509Certificate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.text.util.TextHelper;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.KeyStoreHelper;
import com.helger.security.keystore.LoadedKey;
import com.helger.security.keystore.LoadedKeyStore;

/**
 * Helper methods to access Java key stores of type JKS (Java KeyStore).
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Immutable
public final class PeppolKeyStoreHelper
{
  /** Truststore key store type - always JKS */
  public static final EKeyStoreType TRUSTSTORE_TYPE = EKeyStoreType.JKS;

  /**
   * The classpath entry referencing the complete truststore with all OpenPEPPOL
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
      return (X509Certificate) aKeyStore.getCertificate (sAlias);
    }
    catch (final KeyStoreException ex)
    {
      LOGGER.warn ("Failed to resolve alias '" + sAlias + "' in trust store.", ex);
      return null;
    }
  }

  /**
   * The truststore configuration for PEPPOL PKI v2 valid from 2010 to 2020.
   *
   * @author Philip Helger
   */
  @Immutable
  public static final class Config2010
  {
    private Config2010 ()
    {}

    /**
     * The classpath entry referencing the global truststore with all OpenPEPPOL
     * production entries
     */
    public static final String TRUSTSTORE_PRODUCTION_CLASSPATH = "truststore/2010/prod-truststore.jks";

    private static final KeyStore TRUSTSTORE_PRODUCTION;
    static
    {
      TRUSTSTORE_PRODUCTION = KeyStoreHelper.loadKeyStore (TRUSTSTORE_TYPE,
                                                           TRUSTSTORE_PRODUCTION_CLASSPATH,
                                                           TRUSTSTORE_PASSWORD)
                                            .getKeyStore ();
    }

    /** The truststore alias for the OpenPEPPOL production root certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_ROOT = "peppol root ca";

    /** The OpenPEPPOL production root certificate */
    public static final X509Certificate CERTIFICATE_PRODUCTION_ROOT = _resolveCert (TRUSTSTORE_PRODUCTION,
                                                                                    TRUSTSTORE_PRODUCTION_ALIAS_ROOT);

    /** The truststore alias for the OpenPEPPOL production AP certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_AP = "peppol access point ca (peppol root ca)";

    /** The OpenPEPPOL production AP certificate */
    public static final X509Certificate CERTIFICATE_PRODUCTION_AP = _resolveCert (TRUSTSTORE_PRODUCTION,
                                                                                  TRUSTSTORE_PRODUCTION_ALIAS_AP);

    /** The truststore alias for the OpenPEPPOL production SMP certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_SMP = "peppol service metadata publisher ca (peppol root ca)";

    /** The OpenPEPPOL production SMP certificate */
    public static final X509Certificate CERTIFICATE_PRODUCTION_SMP = _resolveCert (TRUSTSTORE_PRODUCTION,
                                                                                   TRUSTSTORE_PRODUCTION_ALIAS_SMP);

    /**
     * The classpath entry referencing the global truststore with all OpenPEPPOL
     * pilot entries
     */
    public static final String TRUSTSTORE_PILOT_CLASSPATH = "truststore/2010/pilot-truststore.jks";

    private static final KeyStore TRUSTSTORE_PILOT;
    static
    {
      TRUSTSTORE_PILOT = KeyStoreHelper.loadKeyStore (TRUSTSTORE_TYPE, TRUSTSTORE_PILOT_CLASSPATH, TRUSTSTORE_PASSWORD)
                                       .getKeyStore ();
    }

    /** The truststore alias for the OpenPEPPOL pilot root certificate */
    public static final String TRUSTSTORE_PILOT_ALIAS_ROOT = "peppol root test ca";

    /** The OpenPEPPOL pilot root certificate */
    public static final X509Certificate CERTIFICATE_PILOT_ROOT = _resolveCert (TRUSTSTORE_PILOT,
                                                                               TRUSTSTORE_PILOT_ALIAS_ROOT);

    /** The truststore alias for the OpenPEPPOL pilot AP certificate */
    public static final String TRUSTSTORE_PILOT_ALIAS_AP = "peppol access point test ca (peppol root test ca)";

    /** The OpenPEPPOL pilot AP certificate */
    public static final X509Certificate CERTIFICATE_PILOT_AP = _resolveCert (TRUSTSTORE_PILOT,
                                                                             TRUSTSTORE_PILOT_ALIAS_AP);

    /** The truststore alias for the OpenPEPPOL pilot SMP certificate */
    public static final String TRUSTSTORE_PILOT_ALIAS_SMP = "peppol service metadata publisher test ca (peppol root test ca)";

    /** The OpenPEPPOL pilot SMP certificate */
    public static final X509Certificate CERTIFICATE_PILOT_SMP = _resolveCert (TRUSTSTORE_PILOT,
                                                                              TRUSTSTORE_PILOT_ALIAS_SMP);
  }

  /**
   * The truststore configuration for PEPPOL PKI v3 valid from 2018 to 2028.
   *
   * @author Philip Helger
   */
  @Immutable
  public static final class Config2018
  {
    private Config2018 ()
    {}

    /**
     * The classpath entry referencing the global truststore with all OpenPEPPOL
     * production entries
     */
    public static final String TRUSTSTORE_PRODUCTION_CLASSPATH = "truststore/2018/prod-truststore.jks";

    private static final KeyStore TRUSTSTORE_PRODUCTION;
    static
    {
      TRUSTSTORE_PRODUCTION = KeyStoreHelper.loadKeyStore (TRUSTSTORE_TYPE,
                                                           TRUSTSTORE_PRODUCTION_CLASSPATH,
                                                           TRUSTSTORE_PASSWORD)
                                            .getKeyStore ();
    }

    /** The truststore alias for the OpenPEPPOL production root certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_ROOT = "peppol root ca - g2";

    /** The OpenPEPPOL production root certificate */
    public static final X509Certificate CERTIFICATE_PRODUCTION_ROOT = _resolveCert (TRUSTSTORE_PRODUCTION,
                                                                                    TRUSTSTORE_PRODUCTION_ALIAS_ROOT);

    /** The truststore alias for the OpenPEPPOL production AP certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_AP = "peppol access point ca - g2 (peppol root ca - g2)";

    /** The OpenPEPPOL production AP certificate */
    public static final X509Certificate CERTIFICATE_PRODUCTION_AP = _resolveCert (TRUSTSTORE_PRODUCTION,
                                                                                  TRUSTSTORE_PRODUCTION_ALIAS_AP);

    /** The truststore alias for the OpenPEPPOL production SMP certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_SMP = "peppol service metadata publisher ca - g2 (peppol root ca - g2)";

    /** The OpenPEPPOL production SMP certificate */
    public static final X509Certificate CERTIFICATE_PRODUCTION_SMP = _resolveCert (TRUSTSTORE_PRODUCTION,
                                                                                   TRUSTSTORE_PRODUCTION_ALIAS_SMP);

    /**
     * The classpath entry referencing the global truststore with all OpenPEPPOL
     * pilot entries
     */
    public static final String TRUSTSTORE_PILOT_CLASSPATH = "truststore/2018/pilot-truststore.jks";

    private static final KeyStore TRUSTSTORE_PILOT;
    static
    {
      TRUSTSTORE_PILOT = KeyStoreHelper.loadKeyStore (TRUSTSTORE_TYPE, TRUSTSTORE_PILOT_CLASSPATH, TRUSTSTORE_PASSWORD)
                                       .getKeyStore ();
    }

    /** The truststore alias for the OpenPEPPOL pilot root certificate */
    public static final String TRUSTSTORE_PILOT_ALIAS_ROOT = "peppol root test ca - g2";

    /** The OpenPEPPOL pilot root certificate */
    public static final X509Certificate CERTIFICATE_PILOT_ROOT = _resolveCert (TRUSTSTORE_PILOT,
                                                                               TRUSTSTORE_PILOT_ALIAS_ROOT);

    /** The truststore alias for the OpenPEPPOL pilot AP certificate */
    public static final String TRUSTSTORE_PILOT_ALIAS_AP = "peppol access point test ca - g2 (peppol root test ca - g2)";

    /** The OpenPEPPOL pilot AP certificate */
    public static final X509Certificate CERTIFICATE_PILOT_AP = _resolveCert (TRUSTSTORE_PILOT,
                                                                             TRUSTSTORE_PILOT_ALIAS_AP);

    /** The truststore alias for the OpenPEPPOL pilot SMP certificate */
    public static final String TRUSTSTORE_PILOT_ALIAS_SMP = "peppol service metadata publisher test ca - g2 (peppol root test ca - g2)";

    /** The OpenPEPPOL pilot SMP certificate */
    public static final X509Certificate CERTIFICATE_PILOT_SMP = _resolveCert (TRUSTSTORE_PILOT,
                                                                              TRUSTSTORE_PILOT_ALIAS_SMP);
  }

  @PresentForCodeCoverage
  private static final PeppolKeyStoreHelper s_aInstance = new PeppolKeyStoreHelper ();

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
}
