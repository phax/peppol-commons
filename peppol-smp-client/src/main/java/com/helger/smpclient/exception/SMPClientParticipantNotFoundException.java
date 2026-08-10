/*
 * Copyright (C) 2015-2026 Philip Helger
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

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import org.jspecify.annotations.NonNull;

/**
 * This exception is thrown if the SMP server could not be contacted at all.
 * <p>
 * Note: despite its name, this exception never indicated that a Peppol participant does not exist.
 * It was only ever thrown if the SMP host name could not be resolved or if the socket connection to
 * the SMP failed. The class name was therefore misleading.
 * </p>
 *
 * @author Philip Helger
 * @since 9.5.0
 * @deprecated Since 12.8.0 - use {@link SMPClientSMPUnavailableException} instead, which describes
 *             the situation correctly.
 */
@Deprecated (forRemoval = true, since = "12.8.0")
public class SMPClientParticipantNotFoundException extends SMPClientException
{
  public SMPClientParticipantNotFoundException (@NonNull final UnknownHostException ex)
  {
    super (ex);
  }

  public SMPClientParticipantNotFoundException (@NonNull final ConnectException ex)
  {
    super (ex);
  }

  /**
   * Constructor for derived classes only.
   *
   * @param sMsg
   *        The error message. May not be <code>null</code>.
   * @param ex
   *        The causing exception. May not be <code>null</code>.
   * @since 12.8.0
   */
  protected SMPClientParticipantNotFoundException (@NonNull final String sMsg, @NonNull final IOException ex)
  {
    super (sMsg, ex);
  }
}
