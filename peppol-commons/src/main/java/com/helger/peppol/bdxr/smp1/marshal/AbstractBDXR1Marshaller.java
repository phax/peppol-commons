/**
 * Copyright (C) 2015-2019 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.bdxr.smp1.marshal;

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
