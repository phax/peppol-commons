/*
 * Copyright (C) 2015-2026 Philip Helger (www.helger.com)
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

import com.helger.collection.commons.ICommonsList;
import com.helger.io.resource.ClassPathResource;
import com.helger.xsds.bdxr.smp2.CBDXRSMP2;
import com.helger.xsds.bdxr.smp2.ac.EndpointType;
import com.helger.xsds.bdxr.smp2.ac.ObjectFactory;

/**
 * A simple JAXB marshaller for a single {@link EndpointType}. In contrast to Peppol SMP and OASIS
 * BDXR SMP v1, the OASIS BDXR SMP v2 Aggregate Components XSD declares a global "Endpoint" element,
 * so XML Schema validation is possible.
 *
 * @author Philip Helger
 * @since 13.1.2
 */
public class BDXR2MarshallerEndpoint extends AbstractBDXR2Marshaller <EndpointType>
{
  private static final ICommonsList <ClassPathResource> XSDS = CBDXRSMP2.getAllXSDIncludes ();

  /**
   * Constructor with validation enabled by default. Use {@link #setUseSchema(boolean)} to change
   * this.
   */
  public BDXR2MarshallerEndpoint ()
  {
    super (EndpointType.class, XSDS, new ObjectFactory ()::createEndpoint);
  }
}
