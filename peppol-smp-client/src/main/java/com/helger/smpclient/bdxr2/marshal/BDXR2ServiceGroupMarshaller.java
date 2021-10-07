/*
 * Copyright (C) 2015-2021 Philip Helger
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

import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.xsds.bdxr.smp2.CBDXRSMP2;
import com.helger.xsds.bdxr.smp2.ObjectFactory;
import com.helger.xsds.bdxr.smp2.ServiceGroupType;

/**
 * OASIS BDXR SMP v2 ServiceGroup marshaller
 *
 * @author Philip Helger
 */
public class BDXR2ServiceGroupMarshaller extends AbstractBDXR2Marshaller <ServiceGroupType>
{
  private static final ICommonsList <ClassPathResource> XSDS = CBDXRSMP2.getAllXSDResourceServiceGroup ();

  /**
   * Constructor
   *
   * @param bValidationEnabled
   *        <code>true</code> if XSD validation should be used,
   *        <code>false</code> to not verify it.
   */
  public BDXR2ServiceGroupMarshaller (final boolean bValidationEnabled)
  {
    super (ServiceGroupType.class, bValidationEnabled, XSDS, new ObjectFactory ()::createServiceGroup);
  }
}
