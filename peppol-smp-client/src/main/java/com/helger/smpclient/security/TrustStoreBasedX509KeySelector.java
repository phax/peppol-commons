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
package com.helger.smpclient.security;

import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;

import javax.security.auth.x500.X500Principal;
import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.X509Data;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.base.enforce.ValueEnforcer;
import com.helger.collection.commons.ICommonsSet;
import com.helger.security.certificate.CertificateHelper;
import com.helger.security.certificate.ECertificateCheckResult;
import com.helger.security.keystore.ConstantKeySelectorResult;
import com.helger.security.keystore.KeyStoreHelper;
import com.helger.security.revocation.CertificateRevocationCheckerDefaults;
import com.helger.security.revocation.ERevocationCheckMode;
import com.helger.security.revocation.RevocationCheckBuilder;

/**
 * Finds and returns a key using the data contained in a {@link KeyInfo} object
 *
 * @author Philip Helger
 * @see <a href= "http://java.sun.com/developer/technicalArticles/xml/dig_signature_api/">
 *      Programming with the Java XML Digital Signature API</a>
 */
public final class TrustStoreBasedX509KeySelector extends KeySelector
{
  private static final Logger LOGGER = LoggerFactory.getLogger (TrustStoreBasedX509KeySelector.class);

  private final KeyStore m_aTrustStore;
  private LocalDateTime m_aValidationDateTime;
  // null means "use default"
  private ERevocationCheckMode m_eRevocationCheckMode;
  private boolean m_bAllowRevocationSoftFail = CertificateRevocationCheckerDefaults.isAllowSoftFail ();
  private boolean m_bSynchronizedRevocationCheck = CertificateRevocationCheckerDefaults.isExecuteInSynchronizedBlock ();

  /**
   * Constructor
   *
   * @param aTrustStore
   *        The trust store to use. May not be <code>null</code>.
   * @since 8.1.1
   */
  public TrustStoreBasedX509KeySelector (@NonNull final KeyStore aTrustStore)
  {
    ValueEnforcer.notNull (aTrustStore, "TrustStore");
    m_aTrustStore = aTrustStore;
  }

  /**
   * @return The selected validation date and time to use. <code>null</code> means current date
   *         time.
   * @since 8.6.2
   */
  @Nullable
  public LocalDateTime getValidationDateTime ()
  {
    return m_aValidationDateTime;
  }

  /**
   * Set the date and time when the trust store entry should be valid.
   *
   * @param aValidationDateTime
   *        The date and time to use. May be <code>null</code>.
   * @return this for chaining
   * @since 8.6.2
   */
  @NonNull
  public TrustStoreBasedX509KeySelector setValidationDateTime (@Nullable final LocalDateTime aValidationDateTime)
  {
    m_aValidationDateTime = aValidationDateTime;
    return this;
  }

  /**
   * @return The revocation check mode to use. <code>null</code> means use the default from
   *         {@link CertificateRevocationCheckerDefaults}.
   * @since 12.4.3
   */
  @Nullable
  public ERevocationCheckMode getRevocationCheckMode ()
  {
    return m_eRevocationCheckMode;
  }

  /**
   * Set the revocation check mode to use.
   *
   * @param e
   *        The revocation check mode to use. <code>null</code> means use the default from
   *        {@link CertificateRevocationCheckerDefaults}.
   * @return this for chaining
   * @since 12.4.3
   */
  @NonNull
  public TrustStoreBasedX509KeySelector setRevocationCheckMode (@Nullable final ERevocationCheckMode e)
  {
    m_eRevocationCheckMode = e;
    return this;
  }

  /**
   * @return <code>true</code> if an undeterminable revocation status (e.g. an unreachable CRL
   *         distribution point or OCSP responder) should be treated as "revocation soft fail" — the
   *         certificate is accepted in favour of the doubt. <code>false</code> means "revocation
   *         hard fail" — the certificate is rejected. The default is taken from
   *         {@link CertificateRevocationCheckerDefaults#isAllowSoftFail()}.
   * @since 12.4.4
   */
  public boolean isAllowRevocationSoftFail ()
  {
    return m_bAllowRevocationSoftFail;
  }

  /**
   * Modify how an undeterminable revocation status is handled.
   *
   * @param b
   *        <code>true</code> to allow "revocation soft fail" (accept the certificate),
   *        <code>false</code> for "revocation hard fail" (reject the certificate).
   * @return this for chaining
   * @since 12.4.4
   */
  @NonNull
  public TrustStoreBasedX509KeySelector setAllowRevocationSoftFail (final boolean b)
  {
    m_bAllowRevocationSoftFail = b;
    return this;
  }

  /**
   * @return <code>true</code> if the revocation check should be executed in a synchronized block,
   *         <code>false</code> if not. The default is taken from
   *         {@link CertificateRevocationCheckerDefaults#isExecuteInSynchronizedBlock()}.
   * @since 12.5.4
   */
  public boolean isSynchronizedRevocationCheck ()
  {
    return m_bSynchronizedRevocationCheck;
  }

  /**
   * Modify whether the revocation check should be executed in a synchronized block or not.
   *
   * @param b
   *        <code>true</code> to execute the revocation check in a synchronized block,
   *        <code>false</code> if not.
   * @return this for chaining
   * @since 12.5.4
   */
  @NonNull
  public TrustStoreBasedX509KeySelector setSynchronizedRevocationCheck (final boolean b)
  {
    m_bSynchronizedRevocationCheck = b;
    return this;
  }

  public static boolean algorithmEquals (@NonNull final String sAlgURI, @NonNull final String sAlgName)
  {
    if (sAlgName.equalsIgnoreCase ("DSA"))
    {
      if (sAlgURI.equalsIgnoreCase (SignatureMethod.DSA_SHA1) ||
        sAlgURI.equalsIgnoreCase ("http://www.w3.org/2009/xmldsig11#dsa-sha256"))
        return true;
    }
    else
      if (sAlgName.equalsIgnoreCase ("RSA"))
      {
        if (sAlgURI.equalsIgnoreCase (SignatureMethod.RSA_SHA1) ||
          sAlgURI.equalsIgnoreCase ("http://www.w3.org/2007/05/xmldsig-more#sha1-rsa-MGF1") ||
          sAlgURI.equalsIgnoreCase ("http://www.w3.org/2001/04/xmldsig-more#rsa-ripemd160") ||
          sAlgURI.equalsIgnoreCase ("http://www.w3.org/2001/04/xmldsig-more#rsa-sha224") ||
          sAlgURI.equalsIgnoreCase ("http://www.w3.org/2007/05/xmldsig-more#sha224-rsa-MGF1") ||
          sAlgURI.equalsIgnoreCase ("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256") ||
          sAlgURI.equalsIgnoreCase ("http://www.w3.org/2007/05/xmldsig-more#sha256-rsa-MGF1") ||
          sAlgURI.equalsIgnoreCase ("http://www.w3.org/2001/04/xmldsig-more#rsa-sha384") ||
          sAlgURI.equalsIgnoreCase ("http://www.w3.org/2007/05/xmldsig-more#sha384-rsa-MGF1") ||
          sAlgURI.equalsIgnoreCase ("http://www.w3.org/2001/04/xmldsig-more#rsa-sha512") ||
          sAlgURI.equalsIgnoreCase ("http://www.w3.org/2007/05/xmldsig-more#sha512-rsa-MGF1") ||
          sAlgURI.equalsIgnoreCase ("http://www.w3.org/2007/05/xmldsig-more#sha3-224-rsa-MGF1") ||
          sAlgURI.equalsIgnoreCase ("http://www.w3.org/2007/05/xmldsig-more#sha3-256-rsa-MGF1") ||
          sAlgURI.equalsIgnoreCase ("http://www.w3.org/2007/05/xmldsig-more#sha3-384-rsa-MGF1") ||
          sAlgURI.equalsIgnoreCase ("http://www.w3.org/2007/05/xmldsig-more#sha3-512-rsa-MGF1"))
          return true;
      }
      else
        if (sAlgName.equalsIgnoreCase ("EC"))
        {
          if (sAlgURI.equalsIgnoreCase ("http://www.w3.org/2007/05/xmldsig-more#ecdsa-ripemd160") ||
            sAlgURI.equalsIgnoreCase ("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1") ||
            sAlgURI.equalsIgnoreCase ("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha224") ||
            sAlgURI.equalsIgnoreCase ("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256") ||
            sAlgURI.equalsIgnoreCase ("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha384") ||
            sAlgURI.equalsIgnoreCase ("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha512"))
            return true;
        }

    LOGGER.warn ("Algorithm mismatch between JCA/JCE public key algorithm name ('" +
                 sAlgName +
                 "') and signature algorithm URI ('" +
                 sAlgURI +
                 "')");
    return false;
  }

  @Override
  @NonNull
  public KeySelectorResult select (@NonNull final KeyInfo aKeyInfo,
                                   final KeySelector.Purpose aPurpose,
                                   @NonNull final AlgorithmMethod aMethod,
                                   final XMLCryptoContext aCryptoContext) throws KeySelectorException
  {
    final ICommonsSet <X500Principal> aTrustedIssuers = KeyStoreHelper.getAllTrustedCertificates (m_aTrustStore)
                                                                      .getAllMapped (X509Certificate::getSubjectX500Principal);
    final ERevocationCheckMode eRevCheckMode = m_eRevocationCheckMode != null ? m_eRevocationCheckMode
                                                                              : CertificateRevocationCheckerDefaults.getRevocationCheckMode ();

    // For all XMLStructure
    for (final XMLStructure aStructure : aKeyInfo.getContent ())
    {
      if (aStructure instanceof final X509Data aX509Data)
      {
        // For all content - can be many different types
        for (final Object aElement : aX509Data.getContent ())
        {
          if (aElement instanceof final X509Certificate aCertificate)
          {
            // We found a certificate
            // Now at Signature/KeyInfo/X509Data/X509Certificate
            try
            {
              // The SMP response must be signed with an SMP certificate
              // Check
              // * Trusted issuers only
              // * If SMP certificate is revoked
              final ECertificateCheckResult eCheckResult = CertificateHelper.checkCertificate (aTrustedIssuers,
                                                                                               null,
                                                                                               new RevocationCheckBuilder ().certificate (aCertificate)
                                                                                                                            .checkDate (m_aValidationDateTime)
                                                                                                                            .validCAs (m_aTrustStore)
                                                                                                                            .checkMode (eRevCheckMode)
                                                                                                                            .executeInSynchronizedBlock (m_bSynchronizedRevocationCheck));
              LOGGER.info ("SMP Client SMP certificate check result: " +
                           eCheckResult +
                           " (using revocation check mode " +
                           eRevCheckMode +
                           ")");
              if (eCheckResult.isInvalid ())
              {
                if (eCheckResult != ECertificateCheckResult.REVOCATION_STATUS_UNKNOWN || !m_bAllowRevocationSoftFail)
                  throw new KeySelectorException ("Failed to verify the contained SMP certificate (issuer '" +
                                                  aCertificate.getIssuerX500Principal ().getName () +
                                                  "'; subject '" +
                                                  aCertificate.getSubjectX500Principal ().getName () +
                                                  "') with result " +
                                                  eCheckResult);

                LOGGER.warn ("Failed to verify the revocation status, but revocation soft fail is enabled - accepting the certificate");
              }

              final PublicKey aPublicKey = aCertificate.getPublicKey ();

              // Make sure the algorithm is compatible with the method.
              if (algorithmEquals (aMethod.getAlgorithm (), aPublicKey.getAlgorithm ()))
                return new ConstantKeySelectorResult (aPublicKey);
              // Else a warning was already emitted
            }
            catch (final KeySelectorException ex)
            {
              throw ex;
            }
            catch (final Exception ex)
            {
              throw new KeySelectorException ("Failed to select public key from certificate " + aCertificate, ex);
            }
          }
        }
      }
    }

    throw new KeySelectorException ("No public key found!");
  }
}
