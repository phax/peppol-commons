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
package com.helger.peppol.bdxr.smp2.marshal;

import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.jaxb.GenericJAXBMarshaller;
import com.helger.xml.namespace.MapBasedNamespaceContext;
import com.helger.xsds.bdxr.smp2.CBDXRSMP2;
import com.helger.xsds.bdxr.smp2.ObjectFactory;
import com.helger.xsds.bdxr.smp2.ServiceMetadataType;
import com.helger.xsds.ccts.cct.schemamodule.CCCTS;
import com.helger.xsds.xades132.CXAdES132;
import com.helger.xsds.xades141.CXAdES141;
import com.helger.xsds.xmldsig.CXMLDSig;

/**
 * OASIS BDXR SMP v2 ServiceMetadata marshaller
 *
 * @author Philip Helger
 */
public class BDXR2ServiceMetadataMarshaller extends GenericJAXBMarshaller <ServiceMetadataType>
{
  private static final ICommonsList <ClassPathResource> XSDS;
  static
  {
    XSDS = CBDXRSMP2.getAllXSDIncludes ();
    XSDS.add (CBDXRSMP2.getXSDResourceServiceMetadata ());
  }

  public BDXR2ServiceMetadataMarshaller ()
  {
    this (true);
  }

  public BDXR2ServiceMetadataMarshaller (final boolean bValidationEnabled)
  {
    super (ServiceMetadataType.class, bValidationEnabled ? XSDS : null, new ObjectFactory ()::createServiceMetadata);

    final MapBasedNamespaceContext aNSContext = new MapBasedNamespaceContext ();
    aNSContext.addMapping (CCCTS.DEFAULT_PREFIX, CCCTS.NAMESPACE_URI);
    aNSContext.addMapping (CXMLDSig.DEFAULT_PREFIX, CXMLDSig.NAMESPACE_URI);
    aNSContext.addMapping (CXAdES132.DEFAULT_PREFIX, CXAdES132.NAMESPACE_URI);
    aNSContext.addMapping (CXAdES141.DEFAULT_PREFIX, CXAdES141.NAMESPACE_URI);
    aNSContext.addMapping (CBDXRSMP2.DEFAULT_PREFIX_UNQUALIFIED_DATA_TYPES,
                           CBDXRSMP2.NAMESPACE_URI_UNQUALIFIED_DATA_TYPES);
    aNSContext.addMapping (CBDXRSMP2.DEFAULT_PREFIX_QUALIFIED_DATA_TYPES, CBDXRSMP2.NAMESPACE_URI_QUALIFIED_DATA_TYPES);
    aNSContext.addMapping (CBDXRSMP2.DEFAULT_PREFIX_BASIC_COMPONENTS, CBDXRSMP2.NAMESPACE_URI_BASIC_COMPONENTS);
    aNSContext.addMapping (CBDXRSMP2.DEFAULT_PREFIX_AGGREGATE_COMPONENTS, CBDXRSMP2.NAMESPACE_URI_AGGREGATE_COMPONENTS);
    aNSContext.addMapping (CBDXRSMP2.DEFAULT_PREFIX_EXTENSION_COMPONENTS, CBDXRSMP2.NAMESPACE_URI_EXTENSION_COMPONENTS);
    aNSContext.addMapping (CBDXRSMP2.DEFAULT_PREFIX_SERVICE_METADATA, CBDXRSMP2.NAMESPACE_URI_SERVICE_METADATA);
    setNamespaceContext (aNSContext);
  }
}
