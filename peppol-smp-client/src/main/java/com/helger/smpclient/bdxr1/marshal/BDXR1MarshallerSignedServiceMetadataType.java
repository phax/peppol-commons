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
package com.helger.smpclient.bdxr1.marshal;

import com.helger.xsds.bdxr.smp1.ObjectFactory;
import com.helger.xsds.bdxr.smp1.SignedServiceMetadataType;

/**
 * A simple JAXB marshaller for the {@link SignedServiceMetadataType} type.
 *
 * @author Philip Helger
 */
public class BDXR1MarshallerSignedServiceMetadataType extends AbstractBDXR1Marshaller <SignedServiceMetadataType>
{
  /**
   * Constructor with validation enabled by default. Use
   * {@link #setUseSchema(boolean)} to change this.
   *
   * @since 9.0.5
   */
  public BDXR1MarshallerSignedServiceMetadataType ()
  {
    super (SignedServiceMetadataType.class, new ObjectFactory ()::createSignedServiceMetadata);
  }
}
