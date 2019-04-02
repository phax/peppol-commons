/**
 * Copyright (C) 2014-2019 Philip Helger
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

import com.helger.jaxb.builder.JAXBValidationBuilder;
import com.helger.peppol.sbdh.spec12.BinaryContentType;
import com.helger.peppol.sbdh.spec12.TextContentType;

/**
 * A writer builder for Peppol SBDH payloads.
 *
 * @author Philip Helger
 * @param <JAXBTYPE>
 *        The implementation class to be validated
 */
@NotThreadSafe
public class PeppolSBDHPayloadValidator <JAXBTYPE> extends
                                        JAXBValidationBuilder <JAXBTYPE, PeppolSBDHPayloadValidator <JAXBTYPE>>
{
  public PeppolSBDHPayloadValidator (@Nonnull final EPeppolSBDHPayloadType eDocType)
  {
    super (eDocType);
  }

  /**
   * Create a validation builder for BinaryContentType.
   *
   * @return The builder and never <code>null</code>
   */
  @Nonnull
  public static PeppolSBDHPayloadValidator <BinaryContentType> binaryContent ()
  {
    return new PeppolSBDHPayloadValidator <> (EPeppolSBDHPayloadType.BINARY);
  }

  /**
   * Create a validation builder for TextContentType.
   *
   * @return The builder and never <code>null</code>
   */
  @Nonnull
  public static PeppolSBDHPayloadValidator <TextContentType> textContent ()
  {
    return new PeppolSBDHPayloadValidator <> (EPeppolSBDHPayloadType.TEXT);
  }
}
