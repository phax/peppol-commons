/*
 * Copyright (C) 2014-2024 Philip Helger
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

import java.util.List;

import javax.annotation.Nonnull;
import javax.xml.validation.Schema;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.string.StringHelper;
import com.helger.jaxb.builder.IJAXBDocumentType;
import com.helger.jaxb.builder.JAXBDocumentType;
import com.helger.peppol.sbdh.CPeppolSBDH;
import com.helger.peppol.sbdh.spec12.BinaryContentType;
import com.helger.peppol.sbdh.spec12.TextContentType;

/**
 * Enumeration with special payload documents for PEPPOL Envelope spec 1.2
 *
 * @author Philip Helger
 */
@Deprecated (since = "9.0.4", forRemoval = true)
public enum EPeppolSBDHPayloadType implements IJAXBDocumentType
{
  BINARY (BinaryContentType.class, CPeppolSBDH.PEPPOL_SPECIAL_PAYLOADS_XSDS),
  TEXT (TextContentType.class, CPeppolSBDH.PEPPOL_SPECIAL_PAYLOADS_XSDS);

  private final JAXBDocumentType m_aDocType;

  EPeppolSBDHPayloadType (@Nonnull final Class <?> aClass, @Nonnull final List <? extends ClassPathResource> aXSDPaths)
  {
    m_aDocType = new JAXBDocumentType (aClass, aXSDPaths, x -> StringHelper.trimEnd (x, "Type"));
  }

  @Nonnull
  public Class <?> getImplementationClass ()
  {
    return m_aDocType.getImplementationClass ();
  }

  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public ICommonsList <ClassPathResource> getAllXSDResources ()
  {
    return m_aDocType.getAllXSDResources ();
  }

  @Nonnull
  public String getNamespaceURI ()
  {
    return m_aDocType.getNamespaceURI ();
  }

  @Nonnull
  @Nonempty
  public String getLocalName ()
  {
    return m_aDocType.getLocalName ();
  }

  @Nonnull
  public Schema getSchema ()
  {
    return m_aDocType.getSchema ();
  }
}
