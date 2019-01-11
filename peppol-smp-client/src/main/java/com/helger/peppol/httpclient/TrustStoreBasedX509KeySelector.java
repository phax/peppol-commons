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
package com.helger.peppol.httpclient;

import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXParameters;
import java.security.cert.X509Certificate;
import java.util.Iterator;

import javax.annotation.Nonnull;
import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.X509Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.security.certificate.CertificateHelper;
import com.helger.security.keystore.ConstantKeySelectorResult;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.keystore.KeyStoreHelper;

/**
 * Finds and returns a key using the data contained in a {@link KeyInfo} object
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 * @see <a href=
 *      "http://java.sun.com/developer/technicalArticles/xml/dig_signature_api/">
 *      Programming with the Java XML Digital Signature API</a>
 */
public final class TrustStoreBasedX509KeySelector extends KeySelector
{
  private static final Logger LOGGER = LoggerFactory.getLogger (TrustStoreBasedX509KeySelector.class);

  private final EKeyStoreType m_eTruststoreType;
  private final String m_sTruststorePath;
  private final String m_sTrustStorePassword;

  private transient KeyStore m_aKeyStore;

  public TrustStoreBasedX509KeySelector (@Nonnull final EKeyStoreType eTruststoreType,
                                         @Nonnull final String sTruststorePath,
                                         @Nonnull final String sTruststorePassword)
  {
    ValueEnforcer.notNull (eTruststoreType, "TruststoreType");
    ValueEnforcer.notNull (sTruststorePath, "TruststorePath");
    ValueEnforcer.notNull (sTruststorePassword, "TruststorePassword");
    m_eTruststoreType = eTruststoreType;
    m_sTruststorePath = sTruststorePath;
    m_sTrustStorePassword = sTruststorePassword;
  }

  public static boolean algorithmEquals (@Nonnull final String sAlgURI, @Nonnull final String sAlgName)
  {
    if (sAlgName.equalsIgnoreCase ("DSA"))
    {
      if (sAlgURI.equalsIgnoreCase (SignatureMethod.DSA_SHA1))
        return true;
    }
    else
      if (sAlgName.equalsIgnoreCase ("RSA"))
      {
        if (sAlgURI.equalsIgnoreCase (SignatureMethod.RSA_SHA1) ||
            sAlgURI.equalsIgnoreCase ("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256") ||
            sAlgURI.equalsIgnoreCase ("http://www.w3.org/2001/04/xmldsig-more#rsa-sha512"))
          return true;
      }
      else
        if (sAlgName.equalsIgnoreCase ("EC"))
        {
          if (sAlgURI.equalsIgnoreCase ("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256"))
            return true;
        }

    if (LOGGER.isWarnEnabled ())
      LOGGER.warn ("Algorithm mismatch between JCA/JCE public key algorithm name ('" +
                   sAlgName +
                   "') and signature algorithm URI ('" +
                   sAlgURI +
                   "')");
    return false;
  }

  @Override
  public KeySelectorResult select (@Nonnull final KeyInfo aKeyInfo,
                                   final KeySelector.Purpose aPurpose,
                                   @Nonnull final AlgorithmMethod aMethod,
                                   final XMLCryptoContext aCryptoContext) throws KeySelectorException
  {
    // For all XMLStructure
    final Iterator <?> aContentIter = aKeyInfo.getContent ().iterator ();
    while (aContentIter.hasNext ())
    {
      final XMLStructure aStructure = (XMLStructure) aContentIter.next ();
      if (aStructure instanceof X509Data)
      {
        final X509Data aX509Data = (X509Data) aStructure;
        // For all content - can be many different types
        final Iterator <?> aX509Iter = aX509Data.getContent ().iterator ();
        while (aX509Iter.hasNext ())
        {
          final Object aElement = aX509Iter.next ();
          if (aElement instanceof X509Certificate)
          {
            // We found a certificate
            final X509Certificate aCertificate = (X509Certificate) aElement;
            try
            {
              // Check if the certificate is expired or active.
              aCertificate.checkValidity ();

              // Checks whether the certificate is in the trusted store.
              final X509Certificate [] aCertArray = new X509Certificate [] { aCertificate };

              if (m_aKeyStore == null)
              {
                // Load once only
                m_aKeyStore = KeyStoreHelper.loadKeyStoreDirect (m_eTruststoreType,
                                                                 m_sTruststorePath,
                                                                 m_sTrustStorePassword);
              }

              // The PKIXParameters constructor may fail because:
              // - the trustAnchorsParameter is empty
              final PKIXParameters aPKIXParams = new PKIXParameters (m_aKeyStore);
              aPKIXParams.setRevocationEnabled (false);
              final CertificateFactory aCertificateFactory = CertificateHelper.getX509CertificateFactory ();
              final CertPath aCertPath = aCertificateFactory.generateCertPath (new CommonsArrayList <> (aCertArray));
              final CertPathValidator aPathValidator = CertPathValidator.getInstance ("PKIX");
              aPathValidator.validate (aCertPath, aPKIXParams);

              final PublicKey aPublicKey = aCertificate.getPublicKey ();

              // Make sure the algorithm is compatible with the method.
              if (algorithmEquals (aMethod.getAlgorithm (), aPublicKey.getAlgorithm ()))
                return new ConstantKeySelectorResult (aPublicKey);
              // Else a warning was already emitted
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
