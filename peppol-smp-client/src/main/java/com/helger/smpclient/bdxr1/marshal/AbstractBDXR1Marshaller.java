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
package com.helger.smpclient.bdxr1.marshal;

import java.util.function.Function;

import javax.annotation.Nonnull;

import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.jaxb.GenericJAXBMarshaller;
import com.helger.xsds.bdxr.smp1.CBDXRSMP1;

import jakarta.xml.bind.JAXBElement;

/**
 * Abstract JAXB marshaller with namespace prefix mapping
 *
 * @author Philip Helger
 * @param <JAXBTYPE>
 *        JAXB type to use
 */
public abstract class AbstractBDXR1Marshaller <JAXBTYPE> extends GenericJAXBMarshaller <JAXBTYPE>
{
  private static final ICommonsList <ClassPathResource> XSDS = CBDXRSMP1.getAllXSDResources ();

  protected AbstractBDXR1Marshaller (@Nonnull final Class <JAXBTYPE> aType,
                                     @Nonnull final Function <JAXBTYPE, JAXBElement <JAXBTYPE>> aWrapper)
  {
    super (aType, XSDS, aWrapper);
    setNamespaceContext (BDXR1NamespaceContext.getInstance ());
  }

  @Deprecated (since = "9.0.5", forRemoval = true)
  public AbstractBDXR1Marshaller (@Nonnull final Class <JAXBTYPE> aType,
                                  final boolean bValidationEnabled,
                                  @Nonnull final Function <JAXBTYPE, JAXBElement <JAXBTYPE>> aWrapper)
  {
    this (aType, aWrapper);
    // Call this from the outside if needed
    setUseSchema (bValidationEnabled);
  }
}
