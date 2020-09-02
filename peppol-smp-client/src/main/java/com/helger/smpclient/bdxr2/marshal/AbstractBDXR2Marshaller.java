/**
 * Copyright (C) 2015-2020 Philip Helger
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
package com.helger.smpclient.bdxr2.marshal;

import javax.annotation.Nonnull;
import javax.xml.bind.JAXBElement;

import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.functional.IFunction;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.jaxb.GenericJAXBMarshaller;

/**
 * Abstract JAXB marshaller with namespace prefix mapping
 *
 * @author Philip Helger
 * @param <JAXBTYPE>
 *        JAXB type to use
 * @since 8.0.5
 */
public abstract class AbstractBDXR2Marshaller <JAXBTYPE> extends GenericJAXBMarshaller <JAXBTYPE>
{
  public AbstractBDXR2Marshaller (@Nonnull final Class <JAXBTYPE> aType,
                                  final boolean bValidationEnabled,
                                  @Nonnull final ICommonsList <ClassPathResource> aXSDs,
                                  @Nonnull final IFunction <JAXBTYPE, JAXBElement <JAXBTYPE>> aWrapper)
  {
    super (aType, bValidationEnabled ? aXSDs : null, aWrapper);

    setNamespaceContext (new BDXR2NamespaceContext ());
  }
}
