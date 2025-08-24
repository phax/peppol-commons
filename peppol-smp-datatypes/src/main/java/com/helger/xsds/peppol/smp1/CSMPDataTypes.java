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
package com.helger.xsds.peppol.smp1;

import com.helger.annotation.concurrent.Immutable;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.ICommonsList;
import com.helger.io.resource.ClassPathResource;
import com.helger.xsds.peppol.id1.CPeppolID;
import com.helger.xsds.wsaddr.CWSAddr;
import com.helger.xsds.xmldsig.CXMLDSig;

import jakarta.annotation.Nonnull;

/**
 * Constants for the Peppol SMP data types
 *
 * @author Philip Helger
 */
@Immutable
public final class CSMPDataTypes
{
  private CSMPDataTypes ()
  {}

  @Nonnull
  public static ClassPathResource getXSDResourcePeppolSMP ()
  {
    return new ClassPathResource ("/external/schemas/peppol-smp-types-v1-ext.xsd",
                                  CSMPDataTypes.class.getClassLoader ());
  }

  @Nonnull
  public static ICommonsList <ClassPathResource> getAllXSDResources ()
  {
    return new CommonsArrayList <> (CXMLDSig.getXSDResource (),
                                    CPeppolID.getXSDResourcePeppolIdentifiers (),
                                    CWSAddr.getXSDResource (),
                                    getXSDResourcePeppolSMP ());
  }
}
