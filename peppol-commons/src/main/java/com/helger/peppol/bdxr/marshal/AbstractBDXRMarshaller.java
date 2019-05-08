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
package com.helger.peppol.bdxr.marshal;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;
import javax.xml.bind.JAXBElement;

import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.functional.IFunction;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.jaxb.GenericJAXBMarshaller;
import com.helger.xml.namespace.MapBasedNamespaceContext;
import com.helger.xsds.bdxr.smp1.ObjectFactory;
import com.helger.xsds.xmldsig.CXMLDSig;

/**
 * Abstract JAXB marshaller with namespace prefix mapping
 *
 * @author Philip Helger
 * @param <JAXBTYPE>
 *        JAXB type to use
 */
public abstract class AbstractBDXRMarshaller <JAXBTYPE> extends GenericJAXBMarshaller <JAXBTYPE>
{
  // For backwards compatibility
  public static final boolean DEFAULT_VALIDATION_ENABLED = false;
  private static final AtomicBoolean VALIDATION_ENABLED = new AtomicBoolean (DEFAULT_VALIDATION_ENABLED);

  public static boolean isValidationEnabled ()
  {
    return VALIDATION_ENABLED.get ();
  }

  public static void setValidationEnabled (final boolean bEnabled)
  {
    VALIDATION_ENABLED.set (bEnabled);
  }

  private static final ClassPathResource XSD = new ClassPathResource ("/WEB-INF/wsdl/bdx-smp-201605.xsd",
                                                                      AbstractBDXRMarshaller.class.getClassLoader ());

  public AbstractBDXRMarshaller (@Nonnull final Class <JAXBTYPE> aType,
                                 @Nonnull final IFunction <JAXBTYPE, JAXBElement <JAXBTYPE>> aWrapper)
  {
    super (aType, isValidationEnabled () ? new CommonsArrayList <> (CXMLDSig.getXSDResource (), XSD) : null, aWrapper);

    final MapBasedNamespaceContext aNSContext = new MapBasedNamespaceContext ();
    aNSContext.addMapping ("bdxr", ObjectFactory._ServiceGroup_QNAME.getNamespaceURI ());
    aNSContext.addMapping ("ds", "http://www.w3.org/2000/09/xmldsig#");
    setNamespaceContext (aNSContext);
  }
}
