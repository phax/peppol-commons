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

import com.helger.commons.state.EChange;
import com.helger.security.certificate.TrustedCAChecker;

/**
 * This is a specific helper class to check the validity of Peppol certificates for a specific CA.
 * This class assumes the Peppol trust model. See {@link PeppolCertificateChecker} for predefined
 * instances of this class.
 *
 * @author Philip Helger
 * @since 9.6.0
 */
@Deprecated (forRemoval = true, since = "10.2.0")
public final class PeppolCAChecker extends TrustedCAChecker
{
  /**
   * Constructor
   *
   * @param aCACerts
   *        The trusted CA certificates to be used. May neither be <code>null</code> nor empty.
   */
  public PeppolCAChecker (@Nonnull final X509Certificate... aCACerts)
  {
    super (aCACerts);
  }

  /**
   * Remove all elements from the this revocation check result cache.
   *
   * @return {@link EChange#CHANGED} if at least one entry was removed
   */
  @Nonnull
  public EChange clearRevocationCache ()
  {
    return getRevocationCache ().clearCache ();
  }
}
