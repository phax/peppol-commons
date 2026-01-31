/*
 * Copyright (C) 2015-2026 Philip Helger
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
package com.helger.smpclient.peppol.marshal;

import com.helger.xsds.peppol.smp1.CompleteServiceGroupType;
import com.helger.xsds.peppol.smp1.ObjectFactory;

/**
 * A simple JAXB marshaller for the {@link CompleteServiceGroupType} type.
 *
 * @author Philip Helger
 */
public class SMPMarshallerCompleteServiceGroupType extends AbstractSMPMarshaller <CompleteServiceGroupType>
{
  /**
   * Constructor with validation enabled by default. Use
   * {@link #setUseSchema(boolean)} to change this.
   *
   * @since 9.0.5
   */
  public SMPMarshallerCompleteServiceGroupType ()
  {
    super (CompleteServiceGroupType.class, new ObjectFactory ()::createCompleteServiceGroup);
  }
}
