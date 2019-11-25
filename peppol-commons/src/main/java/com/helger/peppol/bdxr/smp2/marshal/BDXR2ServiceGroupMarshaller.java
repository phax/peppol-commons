/**
 * Copyright (C) 2015-2019 Philip Helger
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
package com.helger.peppol.bdxr.smp2.marshal;

import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.jaxb.GenericJAXBMarshaller;
import com.helger.xml.namespace.MapBasedNamespaceContext;
import com.helger.xsds.bdxr.smp2.CBDXRSMP2;
import com.helger.xsds.bdxr.smp2.ObjectFactory;
import com.helger.xsds.bdxr.smp2.ServiceGroupType;
import com.helger.xsds.ccts.cct.schemamodule.CCCTS;
import com.helger.xsds.xades132.CXAdES132;
import com.helger.xsds.xades141.CXAdES141;
import com.helger.xsds.xmldsig.CXMLDSig;

/**
 * OASIS BDXR SMP v2 ServiceGroup marshaller
 *
 * @author Philip Helger
 */
public class BDXR2ServiceGroupMarshaller extends GenericJAXBMarshaller <ServiceGroupType>
{
  private static final ICommonsList <ClassPathResource> XSDS = CBDXRSMP2.getAllXSDResourceServiceGroup ();

  public BDXR2ServiceGroupMarshaller ()
  {
    this (true);
  }

  public BDXR2ServiceGroupMarshaller (final boolean bValidationEnabled)
  {
    super (ServiceGroupType.class, bValidationEnabled ? XSDS : null, new ObjectFactory ()::createServiceGroup);

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
    aNSContext.addMapping (CBDXRSMP2.DEFAULT_PREFIX_SERVICE_GROUP, CBDXRSMP2.NAMESPACE_URI_SERVICE_GROUP);
    setNamespaceContext (aNSContext);
  }
}
