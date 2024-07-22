/*
 * Copyright (C) 2015-2024 Philip Helger
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

import java.net.ConnectException;
import java.net.UnknownHostException;

import javax.annotation.Nonnull;

import org.apache.hc.client5.http.HttpResponseException;

import com.helger.commons.annotation.DevelopersNote;

/**
 * This exception is thrown, if the HTTP response was 404. See also
 * {@link SMPClientParticipantNotFoundException}.
 *
 * @author Philip Helger
 */
public class SMPClientNotFoundException extends SMPClientException
{
  public SMPClientNotFoundException (@Nonnull final HttpResponseException ex)
  {
    super (ex);
  }

  @Deprecated (forRemoval = true, since = "9.5.0")
  @DevelopersNote ("Use SMPClientParticipantNotFoundException instead")
  public SMPClientNotFoundException (@Nonnull final UnknownHostException ex)
  {
    super (ex);
  }

  @Deprecated (forRemoval = true, since = "9.5.0")
  @DevelopersNote ("Use SMPClientParticipantNotFoundException instead")
  public SMPClientNotFoundException (@Nonnull final ConnectException ex)
  {
    super (ex);
  }
}
