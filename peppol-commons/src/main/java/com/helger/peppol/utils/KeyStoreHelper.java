/**
 * Copyright (C) 2015 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.ProtectionParameter;
import java.security.KeyStoreException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.io.resource.FileSystemResource;
import com.helger.commons.io.stream.StreamHelper;

/**
 * Helper methods to access Java key stores of type JKS (Java KeyStore).
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Immutable
public final class KeyStoreHelper
{
  /**
   * The classpath entry referencing the global truststore with all OpenPEPPOL
   * production entries
   */
  public static final String TRUSTSTORE_PRODUCTION_CLASSPATH = "truststore/global-truststore.jks";

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
  public static final String TRUSTSTORE_PILOT_CLASSPATH = "truststore/pilot-truststore.jks";

  /** The truststore alias for the OpenPEPPOL pilot root certificate */
  public static final String TRUSTSTORE_PILOT_ALIAS_ROOT = "peppol root test ca";

  /** The truststore alias for the OpenPEPPOL pilot AP certificate */
  public static final String TRUSTSTORE_PILOT_ALIAS_AP = "peppol access point test ca (peppol root test ca)";

  /** The truststore alias for the OpenPEPPOL pilot SMP certificate */
  public static final String TRUSTSTORE_PILOT_ALIAS_SMP = "peppol service metadata publisher test ca (peppol root test ca)";

  /**
   * The classpath entry referencing the complete truststore with all OpenPEPPOL
   * production AND pilot entries
   */
  public static final String TRUSTSTORE_COMPLETE_CLASSPATH = "truststore/complete-truststore.jks";

  public static final String KEYSTORE_TYPE_JKS = "JKS";

  /** The password used to access the truststores */
  public static final String TRUSTSTORE_PASSWORD = "peppol";

  @PresentForCodeCoverage
  @SuppressWarnings ("unused")
  private static final KeyStoreHelper s_aInstance = new KeyStoreHelper ();

  private KeyStoreHelper ()
  {}

  /**
   * Load a key store from a resource.
   *
   * @param sKeyStorePath
   *        The path pointing to the key store. May not be <code>null</code>.
   * @param sKeyStorePassword
   *        The key store password. May be <code>null</code> to indicate that no
   *        password is required.
   * @return The Java key-store object.
   * @see KeyStore#load(InputStream, char[])
   * @throws GeneralSecurityException
   *         In case of a key store error
   * @throws IOException
   *         In case key store loading fails
   */
  @Nonnull
  public static KeyStore loadKeyStore (@Nonnull final String sKeyStorePath, @Nullable final String sKeyStorePassword) throws GeneralSecurityException,
                                                                                                                     IOException
  {
    return loadKeyStore (sKeyStorePath, sKeyStorePassword == null ? null : sKeyStorePassword.toCharArray ());
  }

  /**
   * Load a key store from a resource.
   *
   * @param sKeyStorePath
   *        The path pointing to the key store. May not be <code>null</code>.
   * @param aKeyStorePassword
   *        The key store password. May be <code>null</code> to indicate that no
   *        password is required.
   * @return The Java key-store object.
   * @see KeyStore#load(InputStream, char[])
   * @throws GeneralSecurityException
   *         In case of a key store error
   * @throws IOException
   *         In case key store loading fails
   */
  @Nonnull
  public static KeyStore loadKeyStore (@Nonnull final String sKeyStorePath, @Nullable final char [] aKeyStorePassword) throws GeneralSecurityException,
                                                                                                                      IOException
  {
    // Open the resource stream
    InputStream aIS = ClassPathResource.getInputStream (sKeyStorePath);
    if (aIS == null)
    {
      // Fallback to file system - maybe this helps...
      aIS = new FileSystemResource (sKeyStorePath).getInputStream ();
    }
    if (aIS == null)
      throw new IllegalArgumentException ("Failed to open key store '" + sKeyStorePath + "'");

    try
    {
      final KeyStore aKeyStore = KeyStore.getInstance (KEYSTORE_TYPE_JKS);
      aKeyStore.load (aIS, aKeyStorePassword);
      return aKeyStore;
    }
    catch (final KeyStoreException ex)
    {
      throw new IllegalStateException ("No provider can handle JKS key stores! Very weird!", ex);
    }
    finally
    {
      StreamHelper.close (aIS);
    }
  }

  /**
   * Create a new key store based on an existing key store
   *
   * @param aBaseKeyStore
   *        The source key store. May not be <code>null</code>
   * @param sAliasToCopy
   *        The name of the alias in the source key store that should be put in
   *        the new key store
   * @param aAliasPassword
   *        The optional password to access the alias in the source key store.
   *        If it is not <code>null</code> the same password will be used in the
   *        created key store
   * @return The created in-memory key store
   * @throws GeneralSecurityException
   *         In case of a key store error
   * @throws IOException
   *         In case key store loading fails
   */
  @Nonnull
  public static KeyStore createKeyStoreWithOnlyOneItem (@Nonnull final KeyStore aBaseKeyStore,
                                                        @Nonnull final String sAliasToCopy,
                                                        @Nullable final char [] aAliasPassword) throws GeneralSecurityException,
                                                                                               IOException
  {
    ValueEnforcer.notNull (aBaseKeyStore, "BaseKeyStore");
    ValueEnforcer.notNull (sAliasToCopy, "AliasToCopy");

    final KeyStore aKeyStore = KeyStore.getInstance (aBaseKeyStore.getType (), aBaseKeyStore.getProvider ());
    // null stream means: create new key store
    aKeyStore.load (null, null);

    // Do we need a password?
    ProtectionParameter aPP = null;
    if (aAliasPassword != null)
      aPP = new PasswordProtection (aAliasPassword);

    aKeyStore.setEntry (sAliasToCopy, aBaseKeyStore.getEntry (sAliasToCopy, aPP), aPP);
    return aKeyStore;
  }
}
