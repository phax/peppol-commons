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
package com.helger.peppol.bdxr.marshal;

import javax.annotation.Nonnull;
import javax.xml.bind.JAXBElement;

import com.helger.commons.functional.IFunction;
import com.helger.jaxb.GenericJAXBMarshaller;
import com.helger.peppol.bdxr.ObjectFactory;
import com.helger.xml.namespace.MapBasedNamespaceContext;

/**
 * Abstract JAXB marshaller with namespace prefix mapping
 *
 * @author Philip Helger
 * @param <JAXBTYPE>
 *        JAXB type to use
 */
public abstract class AbstractBDXRMarshaller <JAXBTYPE> extends GenericJAXBMarshaller <JAXBTYPE>
{
  public AbstractBDXRMarshaller (@Nonnull final Class <JAXBTYPE> aType,
                                 @Nonnull final IFunction <JAXBTYPE, JAXBElement <JAXBTYPE>> aWrapper)
  {
    // No XSD
    super (aType, aWrapper);

    final MapBasedNamespaceContext aNSContext = new MapBasedNamespaceContext ();
    aNSContext.addMapping ("bdxr", ObjectFactory._ServiceGroup_QNAME.getNamespaceURI ());
    aNSContext.addMapping ("ds", "http://www.w3.org/2000/09/xmldsig#");
    setNamespaceContext (aNSContext);
  }
}
