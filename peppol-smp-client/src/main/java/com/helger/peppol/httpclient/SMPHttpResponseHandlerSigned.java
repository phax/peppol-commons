/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
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
package com.helger.peppol.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXParameters;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Iterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.WillClose;
import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.X509Data;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.collection.ArrayHelper;
import com.helger.commons.io.stream.NonBlockingByteArrayInputStream;
import com.helger.commons.io.stream.StreamHelper;
import com.helger.commons.xml.serialize.read.DOMReader;
import com.helger.jaxb.AbstractJAXBMarshaller;
import com.helger.peppol.smpclient.SMPClientConfiguration;
import com.helger.peppol.utils.KeyStoreHelper;

/**
 * This is the Apache HTTP client response handler to verify signed HTTP
 * response messages.
 *
 * @author Philip Helger
 * @param <T>
 *        The type of object to be handled.
 */
public final class SMPHttpResponseHandlerSigned <T> extends AbstractSMPResponseHandler <T>
{
  private static final class ConstantKeySelectorResult implements KeySelectorResult
  {
    private final Key m_aKey;

    public ConstantKeySelectorResult (@Nullable final Key aKey)
    {
      m_aKey = aKey;
    }

    @Nullable
    public Key getKey ()
    {
      return m_aKey;
    }
  }

  /**
   * Finds and returns a key using the data contained in a {@link KeyInfo}
   * object
   *
   * @author PEPPOL.AT, BRZ, Philip Helger
   * @see <a href=
   *      "http://java.sun.com/developer/technicalArticles/xml/dig_signature_api/">
   *      Programming with the Java XML Digital Signature API</a>
   */
  private static final class X509KeySelector extends KeySelector
  {
    private final String m_sTrustoreLocation;
    private final String m_sTrustStorePassword;

    public X509KeySelector ()
    {
      m_sTrustoreLocation = SMPClientConfiguration.getTruststoreLocation ();
      m_sTrustStorePassword = SMPClientConfiguration.getTruststorePassword ();
    }

    public static boolean algorithmEquals (@Nonnull final String sAlgURI, @Nonnull final String sAlgName)
    {
      return (sAlgName.equalsIgnoreCase ("DSA") && sAlgURI.equalsIgnoreCase (SignatureMethod.DSA_SHA1)) ||
             (sAlgName.equalsIgnoreCase ("RSA") && sAlgURI.equalsIgnoreCase (SignatureMethod.RSA_SHA1));
    }

    @Override
    public KeySelectorResult select (@Nonnull final KeyInfo aKeyInfo,
                                     final KeySelector.Purpose aPurpose,
                                     @Nonnull final AlgorithmMethod aMethod,
                                     final XMLCryptoContext aCryptoContext) throws KeySelectorException
    {
      final Iterator <?> aContentIter = aKeyInfo.getContent ().iterator ();
      while (aContentIter.hasNext ())
      {
        final XMLStructure aStructure = (XMLStructure) aContentIter.next ();
        if (!(aStructure instanceof X509Data))
          continue;

        final X509Data aX509Data = (X509Data) aStructure;
        final Iterator <?> aX509Iter = aX509Data.getContent ().iterator ();
        while (aX509Iter.hasNext ())
        {
          final Object aElement = aX509Iter.next ();
          if (!(aElement instanceof X509Certificate))
            continue;

          final X509Certificate aCertificate = (X509Certificate) aElement;
          try
          {
            // Check if the certificate is expired or active.
            aCertificate.checkValidity ();

            // Checks whether the certificate is in the trusted store.
            final X509Certificate [] aCertArray = new X509Certificate [] { aCertificate };

            final KeyStore aKeyStore = KeyStoreHelper.loadKeyStore (m_sTrustoreLocation, m_sTrustStorePassword);
            // The PKIXParameters constructor may fail because:
            // - the trustAnchorsParameter is empty
            final PKIXParameters aPKIXParams = new PKIXParameters (aKeyStore);
            aPKIXParams.setRevocationEnabled (false);
            final CertificateFactory aCertificateFactory = CertificateFactory.getInstance ("X509");
            final CertPath aCertPath = aCertificateFactory.generateCertPath (Arrays.asList (aCertArray));
            final CertPathValidator aPathValidator = CertPathValidator.getInstance ("PKIX");
            aPathValidator.validate (aCertPath, aPKIXParams);

            final PublicKey aPublicKey = aCertificate.getPublicKey ();

            // Make sure the algorithm is compatible with the method.
            if (algorithmEquals (aMethod.getAlgorithm (), aPublicKey.getAlgorithm ()))
              return new ConstantKeySelectorResult (aPublicKey);
          }
          catch (final Throwable t)
          {
            throw new KeySelectorException ("Failed to select public key", t);
          }
        }
      }
      throw new KeySelectorException ("No public key found!");
    }
  }

  private static final Logger s_aLogger = LoggerFactory.getLogger (SMPHttpResponseHandlerSigned.class);

  private final AbstractJAXBMarshaller <T> m_aMarshaller;

  public SMPHttpResponseHandlerSigned (@Nonnull final AbstractJAXBMarshaller <T> aMarshaller)
  {
    m_aMarshaller = ValueEnforcer.notNull (aMarshaller, "Marshaller");
  }

  private static boolean _checkSignature (@Nonnull @WillClose final InputStream aEntityInputStream) throws Exception
  {
    try
    {
      // Get response from servlet
      final Document aDocument = DOMReader.readXMLDOM (aEntityInputStream);

      // We make sure that the XML is a Signed. If not, we don't have to check
      // any certificates.

      // Find Signature element.
      final NodeList aNodeList = aDocument.getElementsByTagNameNS (XMLSignature.XMLNS, "Signature");
      if (aNodeList == null || aNodeList.getLength () == 0)
        throw new IllegalArgumentException ("Element <Signature> not found in SMP XML response");

      // Create a DOMValidateContext and specify a KeySelector
      // and document context.
      final X509KeySelector aKeySelector = new X509KeySelector ();
      final DOMValidateContext aValidateContext = new DOMValidateContext (aKeySelector, aNodeList.item (0));
      final XMLSignatureFactory aSignatureFactory = XMLSignatureFactory.getInstance ("DOM");

      // Unmarshal the XMLSignature.
      final XMLSignature aSignature = aSignatureFactory.unmarshalXMLSignature (aValidateContext);

      // Validate the XMLSignature.
      final boolean bCoreValid = aSignature.validate (aValidateContext);
      if (!bCoreValid)
      {
        // This code block is for debugging purposes only - it has no semantical
        // influence
        s_aLogger.info ("Signature failed core validation");
        final boolean bSignatureValueValid = aSignature.getSignatureValue ().validate (aValidateContext);
        s_aLogger.info ("  Signature value valid: " + bSignatureValueValid);
        if (!bSignatureValueValid)
        {
          // Check the validation status of each Reference.
          int nIndex = 0;
          final Iterator <?> i = aSignature.getSignedInfo ().getReferences ().iterator ();
          while (i.hasNext ())
          {
            final boolean bRefValid = ((Reference) i.next ()).validate (aValidateContext);
            s_aLogger.info ("  Reference[" + nIndex + "] validity status: " + (bRefValid ? "valid" : "NOT valid!"));
            ++nIndex;
          }
        }
      }
      return bCoreValid;
    }
    finally
    {
      // Close the input stream
      StreamHelper.close (aEntityInputStream);
    }
  }

  @Override
  @Nonnull
  public T handleEntity (@Nonnull final HttpEntity aEntity) throws IOException
  {
    // Get complete response as one big byte buffer
    final byte [] aResponseBytes = StreamHelper.getAllBytes (aEntity.getContent ());
    if (ArrayHelper.isEmpty (aResponseBytes))
      throw new ClientProtocolException ("Could not read SMP server response content");

    try
    {
      // Check the signature
      if (!_checkSignature (new NonBlockingByteArrayInputStream (aResponseBytes)))
        throw new ClientProtocolException ("Signature returned from SMP server was not valid");
    }
    catch (final Exception ex)
    {
      throw new ClientProtocolException ("Error in validating signature returned from SMP server", ex);
    }

    // Finally convert to domain object
    final T ret = m_aMarshaller.read (aResponseBytes);
    if (ret == null)
      throw new ClientProtocolException ("Malformed XML document returned from SMP server");
    return ret;
  }

  @Nonnull
  public static <U> SMPHttpResponseHandlerSigned <U> create (@Nonnull final AbstractJAXBMarshaller <U> aMarshaller)
  {
    return new SMPHttpResponseHandlerSigned <U> (aMarshaller);
  }
}
