/**
 * Copyright (C) 2015-2018 Philip Helger (www.helger.com)
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
import java.nio.charset.Charset;

import javax.annotation.Nonnull;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.debug.GlobalDebug;
import com.helger.httpclient.HttpClientHelper;
import com.helger.jaxb.GenericJAXBMarshaller;
import com.helger.peppol.smpclient.exception.SMPClientBadResponseException;

/**
 * This is the Apache HTTP client response handler to verify unsigned HTTP
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
public class SMPHttpResponseHandlerUnsigned <T> extends AbstractSMPResponseHandler <T>
{
  private static final Logger LOGGER = LoggerFactory.getLogger (SMPHttpResponseHandlerUnsigned.class);
  private final GenericJAXBMarshaller <T> m_aMarshaller;

  public SMPHttpResponseHandlerUnsigned (@Nonnull final GenericJAXBMarshaller <T> aMarshaller)
  {
    m_aMarshaller = ValueEnforcer.notNull (aMarshaller, "Marshaller");
  }

  @Override
  @Nonnull
  public T handleEntity (@Nonnull final HttpEntity aEntity) throws SMPClientBadResponseException, IOException
  {
    // Read the payload
    if (false && GlobalDebug.isDebugMode ())
    {
      final ContentType aContentType = ContentType.getOrDefault (aEntity);
      final Charset aCharset = HttpClientHelper.getCharset (aContentType);
      final byte [] aContent = EntityUtils.toByteArray (aEntity);
      if (LOGGER.isInfoEnabled ())
        LOGGER.info (new String (aContent, aCharset));
      final T ret = m_aMarshaller.read (aContent);
      if (ret == null)
        throw new SMPClientBadResponseException ("Malformed XML document returned from SMP server");
      return ret;
    }

    // Read without charset, because XML has self-determination
    // Additionally the BOM handling is enabled when using InputStream
    final T ret = m_aMarshaller.read (aEntity.getContent ());
    if (ret == null)
      throw new SMPClientBadResponseException ("Malformed XML document returned from SMP server");
    return ret;
  }
}
