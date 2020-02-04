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
package com.helger.smpclient.bdxr1.marshal;

import javax.annotation.Nonnull;
import javax.xml.bind.JAXBElement;

import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.functional.IFunction;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.jaxb.GenericJAXBMarshaller;
import com.helger.xml.namespace.MapBasedNamespaceContext;
import com.helger.xsds.bdxr.smp1.CBDXRSMP1;
import com.helger.xsds.xmldsig.CXMLDSig;

/**
 * Abstract JAXB marshaller with namespace prefix mapping
 *
 * @author Philip Helger
 * @param <JAXBTYPE>
 *        JAXB type to use
 */
public abstract class AbstractBDXR1Marshaller <JAXBTYPE> extends GenericJAXBMarshaller <JAXBTYPE>
{
  private static final ICommonsList <ClassPathResource> XSDS;
  static
  {
    XSDS = CBDXRSMP1.getAllXSDIncludes ();
    XSDS.add (CBDXRSMP1.getXSDResource ());
  }

  public AbstractBDXR1Marshaller (@Nonnull final Class <JAXBTYPE> aType,
                                  final boolean bValidationEnabled,
                                  @Nonnull final IFunction <JAXBTYPE, JAXBElement <JAXBTYPE>> aWrapper)
  {
    super (aType, bValidationEnabled ? XSDS : null, aWrapper);

    final MapBasedNamespaceContext aNSContext = new MapBasedNamespaceContext ();
    aNSContext.addMapping (CXMLDSig.DEFAULT_PREFIX, CXMLDSig.NAMESPACE_URI);
    aNSContext.addMapping (CBDXRSMP1.DEFAULT_PREFIX, CBDXRSMP1.NAMESPACE_URI);
    setNamespaceContext (aNSContext);
  }
}
