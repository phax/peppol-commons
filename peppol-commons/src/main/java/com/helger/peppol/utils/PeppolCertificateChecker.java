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
import javax.annotation.concurrent.ThreadSafe;
import javax.security.auth.x500.X500Principal;

import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.peppol.security.PeppolTrustedCA;
import com.helger.security.certificate.CertificateHelper;
import com.helger.security.certificate.ECertificateCheckResult;
import com.helger.security.certificate.TrustedCAChecker;
import com.helger.security.revocation.AbstractRevocationCheckBuilder;
import com.helger.security.revocation.RevocationCheckResultCache;

/**
 * The Peppol certificate checker
 *
 * @author Philip Helger
 * @since 7.0.4
 */
@ThreadSafe
@Deprecated (forRemoval = true, since = "10.2.0")
public final class PeppolCertificateChecker
{
  private PeppolCertificateChecker ()
  {}

  /**
   * @return The Peppol CA checker for Pilot AP certificates.
   * @since 9.6.0
   */
  @Nonnull
  public static TrustedCAChecker peppolTestAP ()
  {
    return PeppolTrustedCA.peppolTestAP ();
  }

  /**
   * @return The Peppol CA checker for Production AP certificates.
   * @since 9.6.0
   */
  @Nonnull
  public static TrustedCAChecker peppolProductionAP ()
  {
    return PeppolTrustedCA.peppolProductionAP ();
  }

  /**
   * @return The Peppol CA checker for Pilot and Production AP certificates.
   * @since 9.6.0
   */
  @Nonnull
  public static TrustedCAChecker peppolAllAP ()
  {
    return PeppolTrustedCA.peppolAllAP ();
  }

  /**
   * @return The Peppol CA checker for Pilot SMP certificates.
   * @since 9.6.0
   */
  @Nonnull
  public static TrustedCAChecker peppolTestSMP ()
  {
    return PeppolTrustedCA.peppolTestSMP ();
  }

  /**
   * @return The Peppol CA checker for Production SMP certificates.
   * @since 9.6.0
   */
  @Nonnull
  public static TrustedCAChecker peppolProductionSMP ()
  {
    return PeppolTrustedCA.peppolProductionSMP ();
  }

  /**
   * @return The Peppol CA checker for Pilot and Production SMP certificates.
   * @since 9.6.0
   */
  @Nonnull
  public static TrustedCAChecker peppolAllSMP ()
  {
    return PeppolTrustedCA.peppolAllSMP ();
  }

  /**
   * @return The Peppol CA checker for Pilot eB2B AP certificates.
   * @since 9.6.0
   */
  @Nonnull
  public static TrustedCAChecker peppolTestEb2bAP ()
  {
    return PeppolTrustedCA.peppolTestEb2bAP ();
  }

  /**
   * @return The Peppol CA checker for production eB2B AP certificates.
   * @since 10.0.1
   */
  @Nonnull
  public static TrustedCAChecker peppolProductionEb2bAP ()
  {
    return PeppolTrustedCA.peppolProductionEb2bAP ();
  }

  /**
   * Remove all entries from the OSCP cache.
   */
  public static void clearRevocationCheckCache ()
  {
    PeppolTrustedCA.clearRevocationCheckCache ();
  }

  /**
   * Check if the provided certificate (from the revocation checker) is a valid certificate. It
   * checks:
   * <ol>
   * <li>Validity at the provided date time (aRevocationChecker.checkDate) or per now if none was
   * provided</li>
   * <li>If the certificate issuer is part of the provided list of issuers</li>
   * <li>If the certificate is revoked</li>
   * </ol>
   *
   * @param aIssuers
   *        The list of valid certificate issuers to check against. May be <code>null</code> to not
   *        perform this check.
   * @param aRevocationCache
   *        The cache. May be <code>null</code> to disable caching.
   * @param aRevocationChecker
   *        The revocation checker builder with all necessary parameters already set. May not be
   *        <code>null</code>.
   * @return {@link ECertificateCheckResult} and never <code>null</code>.
   * @since 8.5.2
   * @deprecated Use
   *             {@link CertificateHelper#checkCertificate(ICommonsSet, RevocationCheckResultCache, AbstractRevocationCheckBuilder)}
   *             instead
   */
  @Nonnull
  @Deprecated (forRemoval = true, since = "10.2.0")
  public static ECertificateCheckResult checkCertificate (@Nullable final ICommonsSet <X500Principal> aIssuers,
                                                          @Nullable final RevocationCheckResultCache aRevocationCache,
                                                          @Nonnull final AbstractRevocationCheckBuilder <?> aRevocationChecker)
  {
    return CertificateHelper.checkCertificate (aIssuers, aRevocationCache, aRevocationChecker);
  }
}
