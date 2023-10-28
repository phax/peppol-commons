/*
 * Copyright (C) 2015-2023 Philip Helger (www.helger.com)
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
package com.helger.pd.businesscard.v2;

import java.util.List;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.jaxb.GenericJAXBMarshaller;

/**
 * This is the reader and writer for {@link PD2BusinessCardType} documents. This
 * class may be derived to override protected methods from
 * {@link GenericJAXBMarshaller}.
 *
 * @author Philip Helger
 */
public class PD2BusinessCardMarshaller extends GenericJAXBMarshaller <PD2BusinessCardType>
{
  @Nonnull
  private static ClassLoader _getCL ()
  {
    return PD2BusinessCardMarshaller.class.getClassLoader ();
  }

  /** The namespace URI of the BusinessInformation element */
  public static final String BUSINESS_INFORMATION_NS_URI = ObjectFactory._BusinessCard_QNAME.getNamespaceURI ();

  /** XSD resources */
  @CodingStyleguideUnaware
  public static final List <ClassPathResource> BUSINESS_CARD_XSDS = new CommonsArrayList <> (new ClassPathResource ("/schemas/peppol-directory-business-card-20161123.xsd",
                                                                                                                    _getCL ())).getAsUnmodifiable ();

  /**
   * Constructor
   */
  public PD2BusinessCardMarshaller ()
  {
    super (PD2BusinessCardType.class, BUSINESS_CARD_XSDS, new ObjectFactory ()::createBusinessCard);
  }
}
