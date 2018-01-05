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
package com.helger.peppol.smlclient;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.collection.impl.CommonsHashSet;

/**
 * A dummy SOAP handler that handles wsse:Security SOAP header elements as
 * returned by the BDSML 3.0
 * <p>
 * Note: this class is also licensed under Apache 2 license, as it was not part
 * of the original implementation
 * </p>
 *
 * @author Philip Helger
 */
public class WSSESoapHandler implements SOAPHandler <SOAPMessageContext>
{
  private static final QName WSSE_HEADER_NAME = new QName ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd",
                                                           "Security",
                                                           "wsse");

  @Override
  @CodingStyleguideUnaware
  public Set <QName> getHeaders ()
  {
    return new CommonsHashSet <> (WSSE_HEADER_NAME);
  }

  public boolean handleMessage (final SOAPMessageContext aContext)
  {
    return true;
  }

  public boolean handleFault (final SOAPMessageContext aContext)
  {
    return true;
  }

  public void close (final MessageContext aContext)
  {}
}
