/**
 * Copyright (C) 2015-2020 Philip Helger
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
package com.helger.smpclient.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.annotation.Nonnull;
import javax.annotation.WillNotClose;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;

import org.apache.http.HttpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.DevelopersNote;
import com.helger.commons.collection.ArrayHelper;
import com.helger.commons.io.stream.NonBlockingByteArrayInputStream;
import com.helger.commons.io.stream.StreamHelper;
import com.helger.jaxb.GenericJAXBMarshaller;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.smpclient.config.SMPClientConfiguration;
import com.helger.smpclient.exception.SMPClientBadResponseException;
import com.helger.smpclient.security.TrustStoreBasedX509KeySelector;
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
  public static final boolean DEFAULT_VERIFY_SIGNATURE = true;
  @Deprecated
  @DevelopersNote ("since 8.0.3")
  public static final boolean DEFAULT_CHECK_CERTIFICATE = DEFAULT_VERIFY_SIGNATURE;
  private static final Logger LOGGER = LoggerFactory.getLogger (SMPHttpResponseHandlerSigned.class);

  private final GenericJAXBMarshaller <T> m_aMarshaller;
  private boolean m_bVerifySignature = DEFAULT_VERIFY_SIGNATURE;

  public SMPHttpResponseHandlerSigned (@Nonnull final GenericJAXBMarshaller <T> aMarshaller)
  {
    m_aMarshaller = ValueEnforcer.notNull (aMarshaller, "Marshaller");
  }

  /**
   * Check the certificate retrieved from a signed SMP response? This may be
   * helpful for debugging and testing of SMP client connections!
   *
   * @param bVerifySignature
   *        <code>true</code> to enable SMP response checking (on by default) or
   *        <code>false</code> to disable it.
   * @return this for chaining
   * @since 5.2.1
   * @deprecated since 8.0.3; Use {@link #setVerifySignature(boolean)} instead
   */
  @Deprecated
  @Nonnull
  public final SMPHttpResponseHandlerSigned <T> setCheckCertificate (final boolean bVerifySignature)
  {
    return setVerifySignature (bVerifySignature);
  }

  /**
   * Check the certificate retrieved from a signed SMP response? This may be
   * helpful for debugging and testing of SMP client connections!
   *
   * @param bVerifySignature
   *        <code>true</code> to enable SMP response checking (on by default) or
   *        <code>false</code> to disable it.
   * @return this for chaining
   * @since 8.0.3
   */
  @Nonnull
  public final SMPHttpResponseHandlerSigned <T> setVerifySignature (final boolean bVerifySignature)
  {
    m_bVerifySignature = bVerifySignature;
    return this;
  }

  /**
   * @return <code>true</code> if SMP client response certificate checking is
   *         enabled, <code>false</code> if it is disabled. By default this
   *         check is enabled (see {@link #DEFAULT_VERIFY_SIGNATURE}).
   * @since 5.2.1
   * @deprecated since 8.0.3; Use {@link #isVerifySignature()} instead
   */
  @Deprecated
  public final boolean isCheckCertificate ()
  {
    return isVerifySignature ();
  }

  /**
   * @return <code>true</code> if SMP client response certificate checking is
   *         enabled, <code>false</code> if it is disabled. By default this
   *         check is enabled (see {@link #DEFAULT_VERIFY_SIGNATURE}).
   * @since 8.0.3
   */
  public final boolean isVerifySignature ()
  {
    return m_bVerifySignature;
  }

  private static boolean _checkSignature (@Nonnull @WillNotClose final InputStream aEntityInputStream) throws MarshalException,
                                                                                                       XMLSignatureException
  {
    // Get response from servlet
    final Document aDocument = DOMReader.readXMLDOM (aEntityInputStream);

    // We make sure that the XML is a Signed. If not, we don't have to check
    // any certificates.

    // Find Signature element.
    final NodeList aNodeList = aDocument.getElementsByTagNameNS (XMLSignature.XMLNS, "Signature");
    if (aNodeList == null || aNodeList.getLength () == 0)
      throw new IllegalArgumentException ("Element <Signature> not found in SMP XML response");

    final EKeyStoreType eTruststoreType = SMPClientConfiguration.getTrustStoreType ();
    final String sTruststorePath = SMPClientConfiguration.getTrustStorePath ();
    final String sTrustStorePassword = SMPClientConfiguration.getTrustStorePassword ();
    final TrustStoreBasedX509KeySelector aKeySelector = new TrustStoreBasedX509KeySelector (eTruststoreType,
                                                                                            sTruststorePath,
                                                                                            sTrustStorePassword);

    // Create a DOMValidateContext and specify a KeySelector
    // and document context.
    // TODO OASIS BDXR SMP v2 can have more than one signature
    final DOMValidateContext aValidateContext = new DOMValidateContext (aKeySelector, aNodeList.item (0));
    final XMLSignatureFactory aSignatureFactory = XMLSignatureFactory.getInstance ("DOM");

    // Unmarshal the XMLSignature.
    final XMLSignature aSignature = aSignatureFactory.unmarshalXMLSignature (aValidateContext);

    // Validate the XMLSignature.
    final boolean bCoreValid = aSignature.validate (aValidateContext);
    if (bCoreValid)
    {
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Signature validation was successful");
    }
    else
    {
      // This code block is for debugging purposes only - it has no semantical
      // influence
      LOGGER.info ("Signature failed core validation");
      final boolean bSignatureValueValid = aSignature.getSignatureValue ().validate (aValidateContext);
      if (LOGGER.isInfoEnabled ())
        LOGGER.info ("  Signature value valid: " + bSignatureValueValid);
      if (!bSignatureValueValid)
      {
        // Check the validation status of each Reference.
        int nIndex = 0;
        final Iterator <?> i = aSignature.getSignedInfo ().getReferences ().iterator ();
        while (i.hasNext ())
        {
          final boolean bRefValid = ((Reference) i.next ()).validate (aValidateContext);
          if (LOGGER.isInfoEnabled ())
            LOGGER.info ("  Reference[" + nIndex + "] validity status: " + (bRefValid ? "valid" : "NOT valid!"));
          ++nIndex;
        }
      }
    }
    return bCoreValid;
  }

  @Override
  @Nonnull
  public T handleEntity (@Nonnull final HttpEntity aEntity) throws SMPClientBadResponseException, IOException
  {
    // Get complete response as one big byte buffer
    final byte [] aResponseBytes = StreamHelper.getAllBytes (aEntity.getContent ());
    if (ArrayHelper.isEmpty (aResponseBytes))
      throw new SMPClientBadResponseException ("Could not read SMP server response content");

    if (m_bVerifySignature)
    {
      try (final InputStream aIS = new NonBlockingByteArrayInputStream (aResponseBytes))
      {
        // Check the signature
        if (!_checkSignature (aIS))
          throw new SMPClientBadResponseException ("Signature returned from SMP server was not valid");
      }
      catch (final Exception ex)
      {
        throw new SMPClientBadResponseException ("Error in validating signature returned from SMP server", ex);
      }
    }
    else
      LOGGER.error ("SMP response certificate checking is disabled. This should not happen in production systems!");

    // Finally convert to domain object
    final T ret = m_aMarshaller.read (aResponseBytes);
    if (ret == null)
      throw new SMPClientBadResponseException ("Malformed XML document returned from SMP server");
    return ret;
  }
}
