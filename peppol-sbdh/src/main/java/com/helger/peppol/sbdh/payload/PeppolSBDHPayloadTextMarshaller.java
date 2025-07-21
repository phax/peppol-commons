/*
 * Copyright (C) 2014-2025 Philip Helger
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
package com.helger.peppol.sbdh.payload;

import com.helger.jaxb.GenericJAXBMarshaller;
import com.helger.peppol.sbdh.CPeppolSBDH;
import com.helger.peppol.sbdh.spec12.ObjectFactory;
import com.helger.peppol.sbdh.spec12.TextContentType;

/**
 * Special JAXB marshaller for Peppol SBDH Text payload.
 *
 * @author Philip Helger
 * @since 9.0.4
 */
public class PeppolSBDHPayloadTextMarshaller extends GenericJAXBMarshaller <TextContentType>
{
  public static final String NAMESPACE_URI = ObjectFactory._TextContent_QNAME.getNamespaceURI ();
  public static final String ROOT_ELEMENT = ObjectFactory._TextContent_QNAME.getLocalPart ();

  public PeppolSBDHPayloadTextMarshaller ()
  {
    super (TextContentType.class, CPeppolSBDH.PEPPOL_SPECIAL_PAYLOADS_XSDS, new ObjectFactory ()::createTextContent);
  }
}
