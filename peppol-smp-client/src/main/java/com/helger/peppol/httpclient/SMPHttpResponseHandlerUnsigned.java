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
import java.nio.charset.Charset;

import javax.annotation.Nonnull;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.debug.GlobalDebug;
import com.helger.httpclient.HttpClientHelper;
import com.helger.jaxb.GenericJAXBMarshaller;

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
  private static final Logger s_aLogger = LoggerFactory.getLogger (SMPHttpResponseHandlerUnsigned.class);
  private final GenericJAXBMarshaller <T> m_aMarshaller;

  public SMPHttpResponseHandlerUnsigned (@Nonnull final GenericJAXBMarshaller <T> aMarshaller)
  {
    m_aMarshaller = ValueEnforcer.notNull (aMarshaller, "Marshaller");
  }

  @Override
  @Nonnull
  public T handleEntity (@Nonnull final HttpEntity aEntity) throws IOException
  {
    // Read the payload
    final ContentType aContentType = ContentType.getOrDefault (aEntity);

    if (false && GlobalDebug.isDebugMode ())
    {
      final Charset aCharset = HttpClientHelper.getCharset (aContentType);
      final byte [] aContent = EntityUtils.toByteArray (aEntity);
      s_aLogger.info (new String (aContent, aCharset));
      final T ret = m_aMarshaller.read (aContent);
      if (ret == null)
        throw new ClientProtocolException ("Malformed XML document returned from SMP server");
      return ret;
    }

    // Read without charset, because XML has self-determination
    // Additionally the BOM handling is enabled when using InputStream
    final T ret = m_aMarshaller.read (aEntity.getContent ());
    if (ret == null)
      throw new ClientProtocolException ("Malformed XML document returned from SMP server");
    return ret;
  }
}
