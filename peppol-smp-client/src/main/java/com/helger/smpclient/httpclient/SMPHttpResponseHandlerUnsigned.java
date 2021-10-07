/*
 * Copyright (C) 2015-2021 Philip Helger
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

import javax.annotation.Nonnull;

import org.apache.http.HttpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.jaxb.GenericJAXBMarshaller;
import com.helger.smpclient.exception.SMPClientBadResponseException;

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
    // Read without charset, because XML has self-determination
    // Additionally the BOM handling is enabled when using InputStream
    final T ret = m_aMarshaller.read (aEntity.getContent ());
    if (ret == null)
      throw new SMPClientBadResponseException ("Malformed XML document returned from SMP server");

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Successfully parsed unsigned SMP HTTP response");
    return ret;
  }
}
