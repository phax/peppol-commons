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
package com.helger.peppol.smpclient.exception;

import javax.annotation.Nonnull;

import org.apache.http.client.HttpResponseException;

/**
 * This exception is thrown, if the HTTP response was 403.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public class SMPClientUnauthorizedException extends SMPClientException
{
  public SMPClientUnauthorizedException (@Nonnull final HttpResponseException ex)
  {
    super (ex);
  }
}
