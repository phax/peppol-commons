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
   * Handle the response entity and transform it into the actual response
   * object.
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
  public T handleResponse (final HttpResponse aResponse) throws HttpResponseException, IOException
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
