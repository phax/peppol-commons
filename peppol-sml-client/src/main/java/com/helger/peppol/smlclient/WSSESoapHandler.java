/*
 * Copyright (C) 2015-2022 Philip Helger
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
