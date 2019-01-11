/**
 * Copyright (C) 2015-2019 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.text.util.TextHelper;
import com.helger.security.keystore.EKeyStoreType;
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

    /** The truststore alias for the OpenPEPPOL production root certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_ROOT = "peppol root ca";

    /** The truststore alias for the OpenPEPPOL production AP certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_AP = "peppol access point ca (peppol root ca)";

    /** The truststore alias for the OpenPEPPOL production SMP certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_SMP = "peppol service metadata publisher ca (peppol root ca)";

    /**
     * The classpath entry referencing the global truststore with all OpenPEPPOL
     * pilot entries
     */
    public static final String TRUSTSTORE_PILOT_CLASSPATH = "truststore/2010/pilot-truststore.jks";

    /** The truststore alias for the OpenPEPPOL pilot root certificate */
    public static final String TRUSTSTORE_PILOT_ALIAS_ROOT = "peppol root test ca";

    /** The truststore alias for the OpenPEPPOL pilot AP certificate */
    public static final String TRUSTSTORE_PILOT_ALIAS_AP = "peppol access point test ca (peppol root test ca)";

    /** The truststore alias for the OpenPEPPOL pilot SMP certificate */
    public static final String TRUSTSTORE_PILOT_ALIAS_SMP = "peppol service metadata publisher test ca (peppol root test ca)";
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

    /** The truststore alias for the OpenPEPPOL production root certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_ROOT = "peppol root ca - g2";

    /** The truststore alias for the OpenPEPPOL production AP certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_AP = "peppol access point ca - g2 (peppol root ca - g2)";

    /** The truststore alias for the OpenPEPPOL production SMP certificate */
    public static final String TRUSTSTORE_PRODUCTION_ALIAS_SMP = "peppol service metadata publisher ca - g2 (peppol root ca - g2)";

    /**
     * The classpath entry referencing the global truststore with all OpenPEPPOL
     * pilot entries
     */
    public static final String TRUSTSTORE_PILOT_CLASSPATH = "truststore/2018/pilot-truststore.jks";

    /** The truststore alias for the OpenPEPPOL pilot root certificate */
    public static final String TRUSTSTORE_PILOT_ALIAS_ROOT = "peppol root test ca - g2";

    /** The truststore alias for the OpenPEPPOL pilot AP certificate */
    public static final String TRUSTSTORE_PILOT_ALIAS_AP = "peppol access point test ca - g2 (peppol root test ca - g2)";

    /** The truststore alias for the OpenPEPPOL pilot SMP certificate */
    public static final String TRUSTSTORE_PILOT_ALIAS_SMP = "peppol service metadata publisher test ca - g2 (peppol root test ca - g2)";
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
