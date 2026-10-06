/*
 * Copyright (C) 2015-2026 Philip Helger (www.helger.com)
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

import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.jspecify.annotations.NonNull;

/**
 * This is the Apache HTTP client response handler for messages which deliver an unspecified
 * response body that is returned as a byte array.
 *
 * @author Philip Helger
 * @since 13.1.2
 */
public class SMPHttpResponseHandlerByteArray extends AbstractSMPResponseHandler <byte []>
{
  @Override
  @NonNull
  public byte [] handleEntity (@NonNull final HttpEntity aEntity) throws IOException
  {
    return EntityUtils.toByteArray (aEntity);
  }
}
