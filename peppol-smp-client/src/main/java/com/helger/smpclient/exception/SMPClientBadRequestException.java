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
package com.helger.smpclient.exception;

import javax.annotation.Nonnull;

import org.apache.hc.client5.http.HttpResponseException;

/**
 * This exception is thrown, if the HTTP response was 400.
 *
 * @author Philip Helger
 */
public class SMPClientBadRequestException extends SMPClientException
{
  public SMPClientBadRequestException (@Nonnull final HttpResponseException ex)
  {
    super (ex);
  }
}
