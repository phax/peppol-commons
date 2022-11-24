/*
 * Copyright (C) 2015-2022 Philip Helger
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
import java.security.KeyStore;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.WillNotClose;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;

import org.apache.hc.core5.http.HttpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.collection.ArrayHelper;
import com.helger.commons.io.stream.NonBlockingByteArrayInputStream;
import com.helger.commons.io.stream.StreamHelper;
import com.helger.commons.state.ESuccess;
import com.helger.jaxb.GenericJAXBMarshaller;
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
  private static final Logger LOGGER = LoggerFactory.getLogger (SMPHttpResponseHandlerSigned.class);

  private final GenericJAXBMarshaller <T> m_aMarshaller;
  private boolean m_bVerifySignature = DEFAULT_VERIFY_SIGNATURE;
  private KeyStore m_aTrustStore;

  /**
   * Constructor
   *
   * @param aMarshaller
   *        The JAXB marshaller to be used. May not be <code>null</code>.
   * @param aTrustStore
   *        The trust store to be used. May be <code>null</code>.
   * @since 8.1.1
   */
  public SMPHttpResponseHandlerSigned (@Nonnull final GenericJAXBMarshaller <T> aMarshaller,
                                       @Nullable final KeyStore aTrustStore)
  {
    m_aMarshaller = ValueEnforcer.notNull (aMarshaller, "Marshaller");
    m_aTrustStore = aTrustStore;
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

  /**
   * Check the certificate retrieved from a signed SMP response? This may be
   * helpful for debugging and testing of SMP client connections!<br>
   * Uses the trust store configured in the SMP client configuration.
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
   * @return The trust store to be used for verifying the signature. May be
   *         <code>null</code> if an invalid trust store is configured.
   * @since 8.1.1
   */
  @Nullable
  public final KeyStore getTrustStore ()
  {
    return m_aTrustStore;
  }

  /**
   * Set the trust store to be used. If signature verification is enabled, a
   * trust store MUST be preset.
   *
   * @param aTrustStore
   *        The trust store to be used. May be <code>null</code>.
   * @return this for chaining
   * @since 8.1.1
   */
  @Nonnull
  public final SMPHttpResponseHandlerSigned <T> setTrustStore (@Nullable final KeyStore aTrustStore)
  {
    m_aTrustStore = aTrustStore;
    return this;
  }

  @Nonnull
  public static ESuccess checkSignature (@Nonnull final Document aDocument,
                                         @Nonnull final KeySelector aKeySelector) throws MarshalException,
                                                                                  XMLSignatureException
  {
    // We make sure that the XML is a Signed. If not, we don't have to check
    // any certificates.

    // Find all "Signature" elements
    final NodeList aNodeList = aDocument.getElementsByTagNameNS (XMLSignature.XMLNS, "Signature");
    if (aNodeList == null || aNodeList.getLength () == 0)
      throw new IllegalArgumentException ("Element <Signature> not found in SMP XML response");

    final int nSignatureCount = aNodeList.getLength ();
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Found " + nSignatureCount + " <Signature> elements to verify");

    final XMLSignatureFactory aSignatureFactory = XMLSignatureFactory.getInstance ("DOM");
    ESuccess eSuccess = ESuccess.SUCCESS;

    // OASIS BDXR SMP v2 can have more than one signature
    for (int nSignatureIndex = 0; nSignatureIndex < nSignatureCount; ++nSignatureIndex)
    {
      // Create a DOMValidateContext and specify a KeySelector
      final DOMValidateContext aValidateContext = new DOMValidateContext (aKeySelector,
                                                                          aNodeList.item (nSignatureIndex));
      aValidateContext.setProperty ("org.jcp.xml.dsig.secureValidation", Boolean.TRUE);
      final String sSignatureDebug = (nSignatureIndex + 1) + "/" + nSignatureCount;

      // Unmarshal the XMLSignature.
      final XMLSignature aSignature = aSignatureFactory.unmarshalXMLSignature (aValidateContext);

      // Validate the XMLSignature.
      final boolean bCoreValid = aSignature.validate (aValidateContext);
      if (bCoreValid)
      {
        if (LOGGER.isDebugEnabled ())
          LOGGER.debug ("Signature[" + sSignatureDebug + "] validation was successful");
      }
      else
      {
        eSuccess = ESuccess.FAILURE;

        // This code block is for debugging purposes only - it has no semantical
        // influence
        LOGGER.warn ("Signature[" + sSignatureDebug + "] failed core validation");

        final boolean bSignatureValueValid = aSignature.getSignatureValue ().validate (aValidateContext);
        if (bSignatureValueValid)
        {
          LOGGER.info ("  Signature[" + sSignatureDebug + "] SignatureValue validity status: valid");
        }
        else
        {
          LOGGER.warn ("  Signature[" + sSignatureDebug + "] SignatureValue validity status: NOT valid!");
        }

        {
          // Check the validation status of each Reference.
          final List <?> aRefs = aSignature.getSignedInfo ().getReferences ();
          final int nRefCount = aRefs.size ();
          int nRefIndex = 0;
          final Iterator <?> i = aRefs.iterator ();
          while (i.hasNext ())
          {
            final String sRefDebug = (nRefIndex + 1) + "/" + nRefCount;

            final Reference aRef = (Reference) i.next ();
            if (aRef.getTransforms ().size () != 1)
              LOGGER.warn ("  Signature[" +
                           sSignatureDebug +
                           "] Reference[" +
                           sRefDebug +
                           "] has an invalid number of Transforms. Expected 1 but having " +
                           aRef.getTransforms ().size ());

            final boolean bRefValid = aRef.validate (aValidateContext);
            if (bRefValid)
            {
              LOGGER.info ("  Signature[" + sSignatureDebug + "] Reference[" + sRefDebug + "] validity status: valid");
            }
            else
            {
              LOGGER.warn ("  Signature[" +
                           sSignatureDebug +
                           "] Reference[" +
                           sRefDebug +
                           "] validity status: NOT valid!");
            }
            ++nRefIndex;
          }
        }
      }
    }
    return eSuccess;
  }

  @Nonnull
  private static ESuccess _checkSignature (@Nonnull @WillNotClose final InputStream aEntityInputStream,
                                           @Nonnull final KeyStore aTrustStore) throws MarshalException,
                                                                                XMLSignatureException
  {
    // Get response from servlet
    final Document aDocument = DOMReader.readXMLDOM (aEntityInputStream);
    if (aDocument == null)
      throw new IllegalArgumentException ("The SMP response is not XML");

    final TrustStoreBasedX509KeySelector aKeySelector = new TrustStoreBasedX509KeySelector (aTrustStore);

    return checkSignature (aDocument, aKeySelector);
  }

  @Override
  @Nonnull
  public T handleEntity (@Nonnull final HttpEntity aEntity) throws SMPClientBadResponseException, IOException
  {
    // Get complete response as one big byte buffer
    final byte [] aResponseBytes = StreamHelper.getAllBytes (aEntity.getContent ());
    if (ArrayHelper.isEmpty (aResponseBytes))
      throw new SMPClientBadResponseException ("SMP server response content is empty/could not be read");

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Signed SMP response has " + aResponseBytes.length + " bytes");

    if (m_bVerifySignature)
    {
      if (m_aTrustStore == null)
        throw new SMPClientBadResponseException ("No trust store was configured - cannot verify signatures");

      try (final InputStream aIS = new NonBlockingByteArrayInputStream (aResponseBytes))
      {
        // Check the signature
        if (_checkSignature (aIS, m_aTrustStore).isFailure ())
          throw new SMPClientBadResponseException ("Signature returned from SMP server was not valid");

        if (LOGGER.isDebugEnabled ())
          LOGGER.debug ("Successfully verified signature of signed SMP response");
      }
      catch (final SMPClientBadResponseException ex)
      {
        // Avoid double wrapping
        throw ex;
      }
      catch (final Exception ex)
      {
        throw new SMPClientBadResponseException ("Error in validating signature returned from SMP server", ex);
      }
    }
    else
    {
      LOGGER.warn ("SMP response signature verification is disabled. This should not happen in production systems!");
    }

    // Finally convert to domain object
    final T ret = m_aMarshaller.read (aResponseBytes);
    if (ret == null)
      throw new SMPClientBadResponseException ("Malformed XML document returned from SMP server");

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Successfully parsed signed SMP HTTP response");

    return ret;
  }
}
