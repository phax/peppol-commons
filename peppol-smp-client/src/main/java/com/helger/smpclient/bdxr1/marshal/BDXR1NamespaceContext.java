/*
 * Copyright (C) 2015-2023 Philip Helger
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

import javax.annotation.Nonnull;

import com.helger.commons.annotation.Singleton;
import com.helger.xml.namespace.MapBasedNamespaceContext;
import com.helger.xsds.bdxr.smp1.CBDXRSMP1;
import com.helger.xsds.xmldsig.CXMLDSig;

/**
 * The default namespace context for OASIS BDXR SMP v1.0.
 *
 * @author Philip Helger
 * @since 8.0.5
 */
@Singleton
public class BDXR1NamespaceContext extends MapBasedNamespaceContext
{
  private static final class SingletonHolder
  {
    static final BDXR1NamespaceContext INSTANCE = new BDXR1NamespaceContext ();
  }

  /**
   * Deprecated constructor.
   *
   * @deprecated Since 8.7.3. Use {@link BDXR1NamespaceContext#getInstance()}
   *             instead.
   */
  @Deprecated (forRemoval = false)
  public BDXR1NamespaceContext ()
  {
    addMapping (CXMLDSig.DEFAULT_PREFIX, CXMLDSig.NAMESPACE_URI);
    addMapping (CBDXRSMP1.DEFAULT_PREFIX, CBDXRSMP1.NAMESPACE_URI);
  }

  @Nonnull
  public static BDXR1NamespaceContext getInstance ()
  {
    return SingletonHolder.INSTANCE;
  }
}
