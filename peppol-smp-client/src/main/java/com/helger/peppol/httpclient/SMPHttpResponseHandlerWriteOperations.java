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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

/**
 * This is the Apache HTTP client response handler for messages which don't
 * deliver a response body (PUT or DELETE HTTP operations).
 * <p>
 * Note: this class is also licensed under Apache 2 license, as it was not part
 * of the original implementation
 * </p>
 *
 * @author Philip Helger
 */
public class SMPHttpResponseHandlerWriteOperations extends AbstractSMPResponseHandler <Object>
{
  @Override
  @Nullable
  public Object handleEntity (@Nonnull final HttpEntity aEntity) throws IOException
  {
    // Ignore body
    EntityUtils.consume (aEntity);
    return null;
  }
}
