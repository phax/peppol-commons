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
import java.security.cert.X509Certificate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.security.auth.x500.X500Principal;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.ICommonsSet;

/**
 * A specific Peppol certificate helper
 *
 * @author Philip Helger
 * @since 7.0.3
 */
@Immutable
public final class PeppolCertificateHelper
{
  public static final String PRINCIPAL_TYPE_CN = "CN";

  private PeppolCertificateHelper ()
  {}

  @Nullable
  public static String getSubjectCN (@Nullable final X509Certificate aCert)
  {
    return aCert != null ? getCNOrNull (aCert.getSubjectX500Principal ()) : null;
  }

  @Nullable
  public static String getCNOrNull (@Nullable final X500Principal aPrincipal)
  {
    return aPrincipal != null ? getCNOrNull (aPrincipal.getName ()) : null;
  }

  @Nullable
  public static String getCNOrNull (@Nullable final String sPrincipal)
  {
    try
    {
      return getCN (sPrincipal);
    }
    catch (final InvalidNameException ex)
    {
      return null;
    }
  }

  @Nullable
  public static String getPrincipalTypeValue (@Nullable final String sPrincipal,
                                              @Nonnull final String sType) throws InvalidNameException
  {
    ValueEnforcer.notNull (sType, "Type");
    if (sPrincipal != null)
      for (final Rdn aRdn : new LdapName (sPrincipal).getRdns ())
        if (aRdn.getType ().equalsIgnoreCase (sType))
          return (String) aRdn.getValue ();
    return null;
  }

  @Nullable
  public static String getCN (@Nullable final String sPrincipal) throws InvalidNameException
  {
    return getPrincipalTypeValue (sPrincipal, PRINCIPAL_TYPE_CN);
  }

  /**
   * Get all trusted certificates
   *
   * @param aTrustStore
   *        Trust store to iterate
   * @return A non-<code>null</code> set of all trusted certificates. Never
   *         <code>null</code>.
   * @deprecated Use the method in {@link PeppolKeyStoreHelper} instead
   */
  @Nonnull
  @ReturnsMutableCopy
  @Deprecated (forRemoval = true, since = "9.6.0")
  public static ICommonsSet <X509Certificate> getAllTrustedCertificates (@Nullable final KeyStore aTrustStore)
  {
    return PeppolKeyStoreHelper.getAllTrustedCertificates (aTrustStore);
  }
}
