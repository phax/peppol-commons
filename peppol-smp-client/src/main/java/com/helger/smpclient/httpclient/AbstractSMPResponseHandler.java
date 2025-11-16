/*
 * Copyright (C) 2015-2025 Philip Helger
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
import java.nio.charset.StandardCharsets;

import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.HttpResponseException;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.base.debug.GlobalDebug;
import com.helger.httpclient.HttpClientHelper;
import com.helger.smpclient.exception.SMPClientBadResponseException;

/**
 * Abstract {@link HttpClientResponseHandler} implementation that ensures a leak free usage of the
 * returned response.
 * <p>
 * Note: this class is also licensed under Apache 2 license, as it was not part of the original
 * implementation
 * </p>
 *
 * @author Philip Helger
 * @param <T>
 *        Date type to be created
 */
public abstract class AbstractSMPResponseHandler <T> implements HttpClientResponseHandler <T>
{
  /**
   * Handle the response entity and transform it into the actual response object.
   *
   * @param aEntity
   *        The entity to handle. Never <code>null</code>.
   * @return the result. May be <code>null</code>.
   * @throws IOException
   *         on IO error
   * @throws SMPClientBadResponseException
   *         if something goes wrong
   */
  @Nullable
  public abstract T handleEntity (@NonNull HttpEntity aEntity) throws IOException, SMPClientBadResponseException;

  /**
   * Read the entity from the response body and pass it to the entity handler method if the response
   * was successful (a 2xx status code). If no response body exists, this returns null. If the
   * response was unsuccessful (&gt;= 300 status code), throws an {@link HttpResponseException}.
   */
  @Nullable
  public T handleResponse (@NonNull final ClassicHttpResponse aResponse) throws IOException
  {
    final HttpEntity aEntity = aResponse.getEntity ();
    if (aResponse.getCode () >= 300)
    {
      if (GlobalDebug.isDebugMode ())
      {
        // Contain response details in exception
        final String sEntity = HttpClientHelper.entityToString (aEntity, StandardCharsets.UTF_8);
        throw new HttpResponseException (aResponse.getCode (), aResponse.getReasonPhrase () + "\n" + sEntity);
      }
      throw new HttpResponseException (aResponse.getCode (), aResponse.getReasonPhrase ());
    }
    try
    {
      return aEntity == null ? null : handleEntity (aEntity);
    }
    catch (final SMPClientBadResponseException ex)
    {
      // Wrap to comply to API :(
      throw new ClientProtocolException (ex);
    }
  }
}
