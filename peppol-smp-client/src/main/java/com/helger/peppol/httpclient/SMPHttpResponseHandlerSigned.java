/**
 * Copyright (C) 2015-2020 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.httpclient;

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
import com.helger.commons.collection.ArrayHelper;
import com.helger.commons.io.stream.NonBlockingByteArrayInputStream;
import com.helger.commons.io.stream.StreamHelper;
import com.helger.jaxb.GenericJAXBMarshaller;
import com.helger.peppol.smpclient.SMPClientConfiguration;
import com.helger.peppol.smpclient.exception.SMPClientBadResponseException;
import com.helger.security.keystore.EKeyStoreType;
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
  private static final Logger LOGGER = LoggerFactory.getLogger (SMPHttpResponseHandlerSigned.class);

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
  public final SMPHttpResponseHandlerSigned <T> setCheckCertificate (final boolean bCheckCertificate)
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
  public final boolean isCheckCertificate ()
  {
    return m_bCheckCertificate;
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
    if (!bCoreValid)
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

    if (m_bCheckCertificate)
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
