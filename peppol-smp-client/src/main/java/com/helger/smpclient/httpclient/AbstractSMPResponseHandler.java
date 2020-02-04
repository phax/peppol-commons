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
package com.helger.smpclient.httpclient;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import com.helger.commons.debug.GlobalDebug;
import com.helger.smpclient.exception.SMPClientBadResponseException;

/**
 * Abstract {@link ResponseHandler} implementation that ensures a leak free
 * usage of the returned response.
 * <p>
 * Note: this class is also licensed under Apache 2 license, as it was not part
 * of the original implementation
 * </p>
 *
 * @author Philip Helger
 * @param <T>
 *        Date type to be created
 */
public abstract class AbstractSMPResponseHandler <T> implements ResponseHandler <T>
{
  /**
   * Handle the response entity and transform it into the actual response
   * object.
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
  public abstract T handleEntity (@Nonnull HttpEntity aEntity) throws IOException, SMPClientBadResponseException;

  /**
   * Read the entity from the response body and pass it to the entity handler
   * method if the response was successful (a 2xx status code). If no response
   * body exists, this returns null. If the response was unsuccessful (&gt;= 300
   * status code), throws an {@link HttpResponseException}.
   */
  @Nullable
  public T handleResponse (@Nonnull final HttpResponse aResponse) throws IOException
  {
    final StatusLine aStatusLine = aResponse.getStatusLine ();
    final HttpEntity aEntity = aResponse.getEntity ();
    if (aStatusLine.getStatusCode () >= 300)
    {
      if (false && GlobalDebug.isDebugMode ())
      {
        final String sEntity = EntityUtils.toString (aEntity);
        throw new HttpResponseException (aStatusLine.getStatusCode (), aStatusLine.getReasonPhrase () + "\n" + sEntity);
      }
      throw new HttpResponseException (aStatusLine.getStatusCode (), aStatusLine.getReasonPhrase ());
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
