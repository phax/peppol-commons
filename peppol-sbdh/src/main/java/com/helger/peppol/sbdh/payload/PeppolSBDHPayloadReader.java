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

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.jaxb.builder.JAXBReaderBuilder;
import com.helger.peppol.sbdh.spec12.BinaryContentType;
import com.helger.peppol.sbdh.spec12.TextContentType;

/**
 * A reader builder for Peppol SBDH payloads.
 *
 * @author Philip Helger
 * @param <JAXBTYPE>
 *        The implementation class to be read
 */
@NotThreadSafe
@Deprecated (since = "9.0.4", forRemoval = true)
public class PeppolSBDHPayloadReader <JAXBTYPE> extends JAXBReaderBuilder <JAXBTYPE, PeppolSBDHPayloadReader <JAXBTYPE>>
{
  public PeppolSBDHPayloadReader (@Nonnull final EPeppolSBDHPayloadType eDocType,
                                  @Nonnull final Class <JAXBTYPE> aImplClass)
  {
    super (eDocType, aImplClass);
  }

  /**
   * Create a reader builder for BinaryContentType.
   *
   * @return The builder and never <code>null</code>
   */
  @Nonnull
  public static PeppolSBDHPayloadReader <BinaryContentType> binaryContent ()
  {
    return new PeppolSBDHPayloadReader <> (EPeppolSBDHPayloadType.BINARY, BinaryContentType.class);
  }

  /**
   * Create a reader builder for TextContentType.
   *
   * @return The builder and never <code>null</code>
   */
  @Nonnull
  public static PeppolSBDHPayloadReader <TextContentType> textContent ()
  {
    return new PeppolSBDHPayloadReader <> (EPeppolSBDHPayloadType.TEXT, TextContentType.class);
  }
}
