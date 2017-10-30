/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
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

/**
 * Base class for all SMP client exceptions. It can also be used as a generic
 * exception for catching.
 *
 * @author Philip Helger
 */
public class SMPClientException extends Exception
{
  public SMPClientException (@Nonnull final String sMsg)
  {
    super (sMsg);
  }

  public SMPClientException (@Nonnull final Throwable aCause)
  {
    super (aCause);
  }

  public SMPClientException (@Nonnull final String sMsg, @Nonnull final Throwable aCause)
  {
    super (sMsg, aCause);
  }
}
