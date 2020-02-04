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
package com.helger.smpclient.exception;

import javax.annotation.Nonnull;

/**
 * This exception is thrown if the evaluation of the SMP response failed
 *
 * @author Philip Helger
 * @since 6.2.0
 */
public class SMPClientBadResponseException extends SMPClientException
{
  public SMPClientBadResponseException (@Nonnull final String sMessage)
  {
    super (sMessage);
  }

  public SMPClientBadResponseException (@Nonnull final String sMessage, @Nonnull final Throwable aCause)
  {
    super (sMessage, aCause);
  }
}
