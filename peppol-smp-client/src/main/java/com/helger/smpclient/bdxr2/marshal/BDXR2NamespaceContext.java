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
package com.helger.smpclient.bdxr2.marshal;

import com.helger.xml.namespace.MapBasedNamespaceContext;
import com.helger.xsds.bdxr.smp2.CBDXRSMP2;
import com.helger.xsds.ccts.cct.schemamodule.CCCTS;
import com.helger.xsds.xades132.CXAdES132;
import com.helger.xsds.xades141.CXAdES141;
import com.helger.xsds.xmldsig.CXMLDSig;

import jakarta.annotation.Nonnull;

/**
 * The default namespace context for OASIS BDXR SMP v2.0.
 *
 * @author Philip Helger
 * @since 8.0.5
 */
public class BDXR2NamespaceContext extends MapBasedNamespaceContext
{
  private static final class SingletonHolder
  {
    static final BDXR2NamespaceContext INSTANCE = new BDXR2NamespaceContext ();
  }

  /**
   * Deprecated constructor.
   *
   * @deprecated Since 8.7.3. Use {@link BDXR2NamespaceContext#getInstance()}
   *             instead.
   */
  @Deprecated (forRemoval = false)
  public BDXR2NamespaceContext ()
  {
    addMapping (CCCTS.DEFAULT_PREFIX, CCCTS.NAMESPACE_URI);
    addMapping (CXMLDSig.DEFAULT_PREFIX, CXMLDSig.NAMESPACE_URI);
    addMapping (CXAdES132.DEFAULT_PREFIX, CXAdES132.NAMESPACE_URI);
    addMapping (CXAdES141.DEFAULT_PREFIX, CXAdES141.NAMESPACE_URI);
    addMapping (CBDXRSMP2.DEFAULT_PREFIX_UNQUALIFIED_DATA_TYPES, CBDXRSMP2.NAMESPACE_URI_UNQUALIFIED_DATA_TYPES);
    addMapping (CBDXRSMP2.DEFAULT_PREFIX_QUALIFIED_DATA_TYPES, CBDXRSMP2.NAMESPACE_URI_QUALIFIED_DATA_TYPES);
    addMapping (CBDXRSMP2.DEFAULT_PREFIX_BASIC_COMPONENTS, CBDXRSMP2.NAMESPACE_URI_BASIC_COMPONENTS);
    addMapping (CBDXRSMP2.DEFAULT_PREFIX_AGGREGATE_COMPONENTS, CBDXRSMP2.NAMESPACE_URI_AGGREGATE_COMPONENTS);
    addMapping (CBDXRSMP2.DEFAULT_PREFIX_EXTENSION_COMPONENTS, CBDXRSMP2.NAMESPACE_URI_EXTENSION_COMPONENTS);
    addMapping (CBDXRSMP2.DEFAULT_PREFIX_SERVICE_GROUP, CBDXRSMP2.NAMESPACE_URI_SERVICE_GROUP);
    addMapping (CBDXRSMP2.DEFAULT_PREFIX_SERVICE_METADATA, CBDXRSMP2.NAMESPACE_URI_SERVICE_METADATA);
  }

  @Nonnull
  public static BDXR2NamespaceContext getInstance ()
  {
    return SingletonHolder.INSTANCE;
  }
}
