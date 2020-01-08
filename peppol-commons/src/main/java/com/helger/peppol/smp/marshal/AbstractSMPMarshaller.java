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
package com.helger.peppol.smp.marshal;

import javax.annotation.Nonnull;
import javax.xml.bind.JAXBElement;

import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.functional.IFunction;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.jaxb.GenericJAXBMarshaller;
import com.helger.peppol.smp.ObjectFactory;
import com.helger.xml.namespace.MapBasedNamespaceContext;
import com.helger.xsds.xmldsig.CXMLDSig;

/**
 * Abstract JAXB marshaller with namespace prefix mapping
 *
 * @author Philip Helger
 * @param <JAXBTYPE>
 *        JAXB type to use
 */
public abstract class AbstractSMPMarshaller <JAXBTYPE> extends GenericJAXBMarshaller <JAXBTYPE>
{
  public static final ClassPathResource XSD1 = new ClassPathResource ("/schemas/Identifiers-1.0.xsd",
                                                                      AbstractSMPMarshaller.class.getClassLoader ());
  public static final ClassPathResource XSD2 = new ClassPathResource ("/schemas/ws-addr.xsd",
                                                                      AbstractSMPMarshaller.class.getClassLoader ());
  public static final ClassPathResource XSD3 = new ClassPathResource ("/schemas/ServiceMetadataPublishingTypes-1.0.xsd",
                                                                      AbstractSMPMarshaller.class.getClassLoader ());

  public AbstractSMPMarshaller (@Nonnull final Class <JAXBTYPE> aType,
                                final boolean bValidationEnabled,
                                @Nonnull final IFunction <JAXBTYPE, JAXBElement <JAXBTYPE>> aWrapper)
  {
    super (aType,
           bValidationEnabled ? new CommonsArrayList <> (CXMLDSig.getXSDResource (), XSD1, XSD2, XSD3) : null,
           aWrapper);

    final MapBasedNamespaceContext aNSContext = new MapBasedNamespaceContext ();
    aNSContext.addMapping ("smp", ObjectFactory._ServiceGroup_QNAME.getNamespaceURI ());
    aNSContext.addMapping ("id", "http://busdox.org/transport/identifiers/1.0/");
    aNSContext.addMapping (CXMLDSig.DEFAULT_PREFIX, CXMLDSig.NAMESPACE_URI);
    aNSContext.addMapping ("wsa", "http://www.w3.org/2005/08/addressing");
    setNamespaceContext (aNSContext);
  }
}
