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

import org.jspecify.annotations.NonNull;

/**
 * This exception is thrown if the SMP server could not be contacted at all - because the host name
 * could not be resolved to an IP address, because the TCP connection was refused or because the
 * connection ran into a timeout.
 * <p>
 * This exception says nothing about the existence of a Peppol participant. A participant is
 * registered - and therefore formally existing - if the SML created a DNS NAPTR record for its
 * participant identifier. That phase happens before any SMP query and reports its errors via
 * {@link com.helger.smpclient.url.SMPDNSResolutionException} (see e.g. the error code
 * <code>PARTICIPANT_NOT_REGISTERED</code>). This exception is strictly downstream of that and means
 * "the SMP of an existing participant is currently unavailable".
 * </p>
 * <p>
 * Errors of this type are potentially transient, so retrying the query later may succeed.
 * </p>
 *
 * @author Philip Helger
 * @since 12.7.1
 */
@SuppressWarnings ("removal")
public class SMPClientSMPUnavailableException extends SMPClientParticipantNotFoundException
{
  public SMPClientSMPUnavailableException (@NonNull final IOException ex)
  {
    super ("Failed to contact the SMP server: " + ex.getClass ().getName () + " - " + ex.getMessage (), ex);
  }
}
