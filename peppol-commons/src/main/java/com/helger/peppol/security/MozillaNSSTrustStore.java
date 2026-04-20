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

import org.jspecify.annotations.NonNull;

import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.PresentForCodeCoverage;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.ITrustStoreDescriptor;
import com.helger.security.keystore.TrustStoreDescriptor;

/**
 * This class provides the Mozilla NSS root certificate trust store for TLS connection validation.
 * Unlike {@link PeppolTrustStores} which handles Peppol-specific CA certificates for AS4 payload
 * signing and encryption, this trust store contains the Mozilla NSS root CAs used for general TLS
 * server certificate verification (e.g. when connecting to SMP or SML endpoints over HTTPS).
 * <p>
 * The trust store is derived from the Mozilla NSS <code>certdata.txt</code> file and contains only
 * certificates trusted for TLS server authentication (<code>CKT_NSS_TRUSTED_DELEGATOR</code> for
 * <code>CKA_TRUST_SERVER_AUTH</code>).
 * </p>
 * <p>
 * The trust store can be regenerated using <code>MainConvertNSSCertData</code>.
 * </p>
 *
 * @author Philip Helger
 * @since 12.4.2
 */
@Immutable
public final class MozillaNSSTrustStore
{
  /** Truststore key store type - always PKCS 12 */
  public static final EKeyStoreType TRUSTSTORE_TYPE = EKeyStoreType.PKCS12;

  /** The password used to access the trust store */
  public static final String TRUSTSTORE_PASSWORD = "changeit";

  /** The classpath entry referencing the Mozilla NSS root certificate trust store */
  public static final String TRUSTSTORE_CLASSPATH = "truststore/mozilla-nss-root-certs.p12";

  /** The trust store descriptor for the Mozilla NSS root certificate trust store */
  public static final ITrustStoreDescriptor TRUSTSTORE_DESCRIPTOR = TrustStoreDescriptor.builder ()
                                                                                        .type (TRUSTSTORE_TYPE)
                                                                                        .path (TRUSTSTORE_CLASSPATH)
                                                                                        .password (TRUSTSTORE_PASSWORD)
                                                                                        .build ();

  /**
   * The full Mozilla NSS root certificate trust store. Never modify.
   */
  @NonNull
  public static final KeyStore TRUSTSTORE = TRUSTSTORE_DESCRIPTOR.loadTrustStore ().getKeyStore ();

  static
  {
    if (TRUSTSTORE == null)
      throw new IllegalStateException ("Failed to load the Mozilla NSS root certificate trust store from '" +
                                       TRUSTSTORE_CLASSPATH +
                                       "'");
  }

  @PresentForCodeCoverage
  private static final MozillaNSSTrustStore INSTANCE = new MozillaNSSTrustStore ();

  private MozillaNSSTrustStore ()
  {}
}
