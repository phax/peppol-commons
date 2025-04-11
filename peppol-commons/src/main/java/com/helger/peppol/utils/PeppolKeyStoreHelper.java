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
package com.helger.peppol.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.peppol.security.PeppolTrustStores;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.LoadedKey;
import com.helger.security.keystore.LoadedKeyStore;

/**
 * Helper methods to access Java key stores of type JKS (Java KeyStore).
 *
 * @author Philip Helger
 */
@Immutable
@Deprecated (forRemoval = true, since = "10.2.0")
public final class PeppolKeyStoreHelper
{
  /** Truststore key store type - always JKS */
  public static final EKeyStoreType TRUSTSTORE_TYPE = PeppolTrustStores.Config2018.TRUSTSTORE_TYPE;

  /**
   * The classpath entry referencing the complete truststore with all OpenPeppol production AND
   * pilot entries
   */
  public static final String TRUSTSTORE_COMPLETE_CLASSPATH = PeppolTrustStores.TRUSTSTORE_COMPLETE_CLASSPATH;

  /** The password used to access the trust stores */
  public static final String TRUSTSTORE_PASSWORD = PeppolTrustStores.TRUSTSTORE_PASSWORD;

  /**
   * The truststore configuration for Peppol PKI v3 valid from 2018 to 2028.
   *
   * @author Philip Helger
   */
  @Immutable
  @Deprecated (forRemoval = true, since = "10.2.0")
  public static final class Config2018 extends PeppolTrustStores.Config2018
  {
    private Config2018 ()
    {}
  }

  @PresentForCodeCoverage
  private static final PeppolKeyStoreHelper INSTANCE = new PeppolKeyStoreHelper ();

  private PeppolKeyStoreHelper ()
  {}

  @Nullable
  @Deprecated (forRemoval = true, since = "10.2.0")
  public static String getLoadError (@Nonnull final LoadedKeyStore aLKS)
  {
    return LoadedKeyStore.getLoadError (aLKS);
  }

  @Nullable
  @Deprecated (forRemoval = true, since = "10.2.0")
  public static String getLoadError (@Nonnull final LoadedKey <?> aLK)
  {
    return LoadedKey.getLoadError (aLK);
  }
}
