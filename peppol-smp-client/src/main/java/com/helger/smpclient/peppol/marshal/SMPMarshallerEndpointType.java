/*
 * Copyright (C) 2015-2026 Philip Helger (www.helger.com)
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
package com.helger.smpclient.peppol.marshal;

import javax.xml.namespace.QName;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.xsds.peppol.smp1.EndpointType;

import jakarta.xml.bind.JAXBElement;

/**
 * A simple JAXB marshaller for a single {@link EndpointType}. The Peppol SMP XSD declares no global
 * "Endpoint" element, so an Endpoint can neither be read nor written with XML Schema validation
 * enabled.
 *
 * @author Philip Helger
 * @since 13.1.2
 */
public class SMPMarshallerEndpointType extends AbstractSMPMarshaller <EndpointType>
{
  /** The qualified name of the Endpoint element */
  public static final QName ENDPOINT_QNAME = new QName ("http://busdox.org/serviceMetadata/publishing/1.0/",
                                                        "Endpoint");

  @NonNull
  private static JAXBElement <EndpointType> _createEndpoint (@Nullable final EndpointType aValue)
  {
    return new JAXBElement <> (ENDPOINT_QNAME, EndpointType.class, null, aValue);
  }

  /**
   * Constructor with validation disabled, because there is no global Endpoint element in the XSD.
   */
  public SMPMarshallerEndpointType ()
  {
    super (EndpointType.class, SMPMarshallerEndpointType::_createEndpoint);
    setUseSchema (false);
  }
}
