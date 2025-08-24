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
package com.helger.peppolid.peppol.doctype;

import com.helger.annotation.Nonempty;

import jakarta.annotation.Nonnull;

/**
 * Contains the Peppol document type identifier parts for XML based document
 * types.
 *
 * @author Philip Helger
 */
public interface IPeppolDocumentTypeIdentifierParts extends IPeppolGenericDocumentTypeIdentifierParts
{
  /**
   * Separator between XML root element namespace URI and local name
   */
  String NAMESPACE_SEPARATOR = "::";

  /**
   * @return The XML namespace URI of the root element. Never <code>null</code>
   *         nor empty.
   */
  @Nonnull
  @Nonempty
  String getRootNS ();

  /**
   * @return The XML element local name of the root element. Never
   *         <code>null</code> nor empty.
   */
  @Nonnull
  @Nonempty
  String getLocalName ();
}
