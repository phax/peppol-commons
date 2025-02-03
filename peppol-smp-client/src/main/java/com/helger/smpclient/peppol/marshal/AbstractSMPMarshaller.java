/*
 * Copyright (C) 2015-2025 Philip Helger
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

import java.util.function.Function;

import javax.annotation.Nonnull;

import com.helger.jaxb.GenericJAXBMarshaller;
import com.helger.xsds.peppol.smp1.CSMPDataTypes;

import jakarta.xml.bind.JAXBElement;

/**
 * Abstract JAXB marshaller with namespace prefix mapping
 *
 * @author Philip Helger
 * @param <JAXBTYPE>
 *        JAXB type to use
 */
public abstract class AbstractSMPMarshaller <JAXBTYPE> extends GenericJAXBMarshaller <JAXBTYPE>
{
  protected AbstractSMPMarshaller (@Nonnull final Class <JAXBTYPE> aType,
                                   @Nonnull final Function <JAXBTYPE, JAXBElement <JAXBTYPE>> aWrapper)
  {
    super (aType, CSMPDataTypes.getAllXSDResources (), aWrapper);
    setNamespaceContext (PeppolSMPClientNamespaceContext.getInstance ());
  }
}
