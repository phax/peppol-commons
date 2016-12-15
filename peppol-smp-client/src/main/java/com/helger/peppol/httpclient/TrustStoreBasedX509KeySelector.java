/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Note: some files, that were not part of the original package are currently
 *   licensed under Apache 2.0 license - https://www.apache.org/licenses/LICENSE-2.0
 *   The respective files contain a special class header!
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

import com.helger.commons.collection.ext.CommonsArrayList;
import com.helger.peppol.smpclient.SMPClientConfiguration;
import com.helger.security.certificate.CertificateHelper;
import com.helger.security.keystore.ConstantKeySelectorResult;
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
  private static final Logger s_aLogger = LoggerFactory.getLogger (TrustStoreBasedX509KeySelector.class);

  private final String m_sTrustoreLocation;
  private final String m_sTrustStorePassword;

  private transient KeyStore m_aKeyStore;

  public TrustStoreBasedX509KeySelector ()
  {
    m_sTrustoreLocation = SMPClientConfiguration.getTruststoreLocation ();
    m_sTrustStorePassword = SMPClientConfiguration.getTruststorePassword ();
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
        if (sAlgURI.equalsIgnoreCase (SignatureMethod.RSA_SHA1))
          return true;
      }
      else
        if (sAlgName.equalsIgnoreCase ("EC"))
        {
          if (sAlgURI.equalsIgnoreCase ("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256"))
            return true;
        }
    s_aLogger.warn ("Algorithm mismatch between JCA/JCE public key algorithm name ('" +
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
                m_aKeyStore = KeyStoreHelper.loadKeyStoreDirect (m_sTrustoreLocation, m_sTrustStorePassword);

              // The PKIXParameters constructor may fail because:
              // - the trustAnchorsParameter is empty
              final PKIXParameters aPKIXParams = new PKIXParameters (m_aKeyStore);
              aPKIXParams.setRevocationEnabled (false);
              final CertificateFactory aCertificateFactory = CertificateHelper.getX509CertificateFactory ();
              final CertPath aCertPath = aCertificateFactory.generateCertPath (new CommonsArrayList<> (aCertArray));
              final CertPathValidator aPathValidator = CertPathValidator.getInstance ("PKIX");
              aPathValidator.validate (aCertPath, aPKIXParams);

              final PublicKey aPublicKey = aCertificate.getPublicKey ();

              // Make sure the algorithm is compatible with the method.
              if (algorithmEquals (aMethod.getAlgorithm (), aPublicKey.getAlgorithm ()))
                return new ConstantKeySelectorResult (aPublicKey);
              // Else a warning was already emitted
            }
            catch (final Throwable t)
            {
              throw new KeySelectorException ("Failed to select public key from certificate " + aCertificate, t);
            }
          }
        }
      }
    }
    throw new KeySelectorException ("No public key found!");
  }
}
