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
package com.helger.smpclient.config;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.style.IsSPIImplementation;
import com.helger.smpclient.phosssmp.SGCustomProperty;
import com.helger.smpclient.phosssmp.SGCustomPropertyList;
import com.helger.smpclient.phosssmp.SGCustomPropertyListMicroTypeConverter;
import com.helger.smpclient.phosssmp.SGCustomPropertyMicroTypeConverter;
import com.helger.xml.microdom.convert.IMicroTypeConverterRegistrarSPI;
import com.helger.xml.microdom.convert.IMicroTypeConverterRegistry;

/**
 * Special micro type converter for this project.
 *
 * @author Philip Helger
 * @since 13.2.0
 */
@IsSPIImplementation
public final class MicroTypeConverterRegistrar_smp_client_base implements IMicroTypeConverterRegistrarSPI
{
  public void registerMicroTypeConverter (@NonNull final IMicroTypeConverterRegistry aRegistry)
  {
    aRegistry.registerMicroElementTypeConverter (SGCustomProperty.class, new SGCustomPropertyMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (SGCustomPropertyList.class,
                                                 new SGCustomPropertyListMicroTypeConverter ());
  }
}
