/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.annotation.Nonnull;
import javax.annotation.WillNotClose;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;

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
import com.helger.jaxb.GenericJAXBMarshaller;
import com.helger.peppol.smpclient.SMPClientConfiguration;
import com.helger.xml.serialize.read.DOMReader;

/**
 * This is the Apache HTTP client response handler to verify signed HTTP
 * response messages.
 * <p>
 * Note: this class is also licensed under Apache 2 license, as it was not part
 * of the original implementation
 * </p>
 *
 * @author Philip Helger
 * @param <T>
 *        The type of object to be handled.
 */
public class SMPHttpResponseHandlerSigned <T> extends AbstractSMPResponseHandler <T>
{
  public static final boolean DEFAULT_CHECK_CERTIFICATE = true;
  private static final Logger s_aLogger = LoggerFactory.getLogger (SMPHttpResponseHandlerSigned.class);

  private final GenericJAXBMarshaller <T> m_aMarshaller;
  private boolean m_bCheckCertificate = DEFAULT_CHECK_CERTIFICATE;

  public SMPHttpResponseHandlerSigned (@Nonnull final GenericJAXBMarshaller <T> aMarshaller)
  {
    m_aMarshaller = ValueEnforcer.notNull (aMarshaller, "Marshaller");
  }

  /**
   * Check the certificate retrieved from a signed SMP response? This may be
   * helpful for debugging and testing of SMP client connections!
   *
   * @param bCheckCertificate
   *        <code>true</code> to enable SMP response checking (on by default) or
   *        <code>false</code> to disable it.
   * @return this for chaining
   * @since 5.2.1
   */
  @Nonnull
  public SMPHttpResponseHandlerSigned <T> setCheckCertificate (final boolean bCheckCertificate)
  {
    m_bCheckCertificate = bCheckCertificate;
    return this;
  }

  /**
   * @return <code>true</code> if SMP client response certificate checking is
   *         enabled, <code>false</code> if it is disabled. By default this
   *         check is enabled (see {@link #DEFAULT_CHECK_CERTIFICATE}).
   * @since 5.2.1
   */
  public boolean isCheckCertificate ()
  {
    return m_bCheckCertificate;
  }

  private static boolean _checkSignature (@Nonnull @WillNotClose final InputStream aEntityInputStream) throws Exception
  {
    // Get response from servlet
    final Document aDocument = DOMReader.readXMLDOM (aEntityInputStream);

    // We make sure that the XML is a Signed. If not, we don't have to check
    // any certificates.

    // Find Signature element.
    final NodeList aNodeList = aDocument.getElementsByTagNameNS (XMLSignature.XMLNS, "Signature");
    if (aNodeList == null || aNodeList.getLength () == 0)
      throw new IllegalArgumentException ("Element <Signature> not found in SMP XML response");

    final String sTruststoreLocation = SMPClientConfiguration.getTruststoreLocation ();
    final String sTrustStorePassword = SMPClientConfiguration.getTruststorePassword ();
    final TrustStoreBasedX509KeySelector aKeySelector = new TrustStoreBasedX509KeySelector (sTruststoreLocation,
                                                                                            sTrustStorePassword);

    // Create a DOMValidateContext and specify a KeySelector
    // and document context.
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

  @Override
  @Nonnull
  public T handleEntity (@Nonnull final HttpEntity aEntity) throws IOException
  {
    // Get complete response as one big byte buffer
    final byte [] aResponseBytes = StreamHelper.getAllBytes (aEntity.getContent ());
    if (ArrayHelper.isEmpty (aResponseBytes))
      throw new ClientProtocolException ("Could not read SMP server response content");

    if (m_bCheckCertificate)
    {
      try (final InputStream aIS = new NonBlockingByteArrayInputStream (aResponseBytes))
      {
        // Check the signature
        if (!_checkSignature (aIS))
          throw new ClientProtocolException ("Signature returned from SMP server was not valid");
      }
      catch (final Exception ex)
      {
        throw new ClientProtocolException ("Error in validating signature returned from SMP server", ex);
      }
    }
    else
      s_aLogger.warn ("SMP response certificate checking is disabled. This should not happen in production systems!");

    // Finally convert to domain object
    final T ret = m_aMarshaller.read (aResponseBytes);
    if (ret == null)
      throw new ClientProtocolException ("Malformed XML document returned from SMP server");
    return ret;
  }
}
