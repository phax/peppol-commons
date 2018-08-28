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

import javax.annotation.Nullable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import com.helger.commons.debug.GlobalDebug;

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
   * Handle the response entity and transform it into the actual response object.
   *
   * @param aEntity
   *        The entity to handle
   * @return the result. May be <code>null</code>.
   * @throws IOException
   *         if something goes wrong
   */
  @Nullable
  public abstract T handleEntity (HttpEntity aEntity) throws IOException;

  /**
   * Read the entity from the response body and pass it to the entity handler
   * method if the response was successful (a 2xx status code). If no response
   * body exists, this returns null. If the response was unsuccessful (&gt;= 300
   * status code), throws an {@link HttpResponseException}.
   */
  @Nullable
  public T handleResponse (final HttpResponse aResponse) throws IOException
  {
    final StatusLine aStatusLine = aResponse.getStatusLine ();
    final HttpEntity aEntity = aResponse.getEntity ();
    if (aStatusLine.getStatusCode () >= 300)
    {
      final String sEntity = EntityUtils.toString (aEntity);
      if (false && GlobalDebug.isDebugMode ())
        throw new HttpResponseException (aStatusLine.getStatusCode (), aStatusLine.getReasonPhrase () + "\n" + sEntity);
      throw new HttpResponseException (aStatusLine.getStatusCode (), aStatusLine.getReasonPhrase ());
    }
    return aEntity == null ? null : handleEntity (aEntity);
  }
}
