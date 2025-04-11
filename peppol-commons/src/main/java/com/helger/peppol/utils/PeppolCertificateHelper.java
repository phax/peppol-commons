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

import java.security.cert.X509Certificate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.naming.InvalidNameException;
import javax.security.auth.x500.X500Principal;

import com.helger.security.certificate.CertificateHelper;

/**
 * A specific Peppol certificate helper
 *
 * @author Philip Helger
 * @since 7.0.3
 * @deprecated Use the methods from {@link CertificateHelper} instead.
 */
@Immutable
@Deprecated (forRemoval = true, since = "10.2.0")
public final class PeppolCertificateHelper
{
  public static final String PRINCIPAL_TYPE_CN = "CN";
  public static final String PRINCIPAL_TYPE_O = "O";

  private PeppolCertificateHelper ()
  {}

  @Nullable
  public static String getPrincipalTypeValue (@Nullable final String sPrincipal, @Nonnull final String sType)
                                                                                                              throws InvalidNameException
  {
    return CertificateHelper.getPrincipalTypeValue (sPrincipal, sType);
  }

  @Nullable
  public static String getCN (@Nullable final String sPrincipal) throws InvalidNameException
  {
    return CertificateHelper.getCN (sPrincipal);
  }

  @Nullable
  public static String getSubjectCN (@Nullable final X509Certificate aCert)
  {
    return CertificateHelper.getSubjectCN (aCert);
  }

  @Nullable
  public static String getCNOrNull (@Nullable final X500Principal aPrincipal)
  {
    return CertificateHelper.getCNOrNull (aPrincipal);
  }

  @Nullable
  public static String getCNOrNull (@Nullable final String sPrincipal)
  {
    return CertificateHelper.getCNOrNull (sPrincipal);
  }
}
