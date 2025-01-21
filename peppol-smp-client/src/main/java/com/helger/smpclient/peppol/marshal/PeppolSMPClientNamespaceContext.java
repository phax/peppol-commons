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
package com.helger.smpclient.peppol.marshal;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.Singleton;
import com.helger.xml.namespace.MapBasedNamespaceContext;
import com.helger.xsds.peppol.id1.CPeppolID;
import com.helger.xsds.peppol.smp1.ObjectFactory;
import com.helger.xsds.wsaddr.CWSAddr;
import com.helger.xsds.xmldsig.CXMLDSig;

/**
 * The default namespace context for Peppol SMP v1.0.
 *
 * @author Philip Helger
 * @since 9.0.5
 */
@Singleton
public class PeppolSMPClientNamespaceContext extends MapBasedNamespaceContext
{
  private static final class SingletonHolder
  {
    static final PeppolSMPClientNamespaceContext INSTANCE = new PeppolSMPClientNamespaceContext ();
  }

  /**
   * Deprecated constructor.
   *
   * @deprecated Use {@link PeppolSMPClientNamespaceContext#getInstance()}
   *             instead.
   */
  @Deprecated (forRemoval = false)
  public PeppolSMPClientNamespaceContext ()
  {
    addMapping ("smp", ObjectFactory._ServiceGroup_QNAME.getNamespaceURI ());
    addMapping ("id", CPeppolID.NS_URI_PEPPOL_IDENTIFIERS);
    addMapping (CXMLDSig.DEFAULT_PREFIX, CXMLDSig.NAMESPACE_URI);
    addMapping (CWSAddr.DEFAULT_PREFIX, CWSAddr.NAMESPACE_URI);
  }

  @Nonnull
  public static PeppolSMPClientNamespaceContext getInstance ()
  {
    return SingletonHolder.INSTANCE;
  }
}
