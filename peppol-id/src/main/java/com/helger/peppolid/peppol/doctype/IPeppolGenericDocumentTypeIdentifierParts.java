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

import javax.annotation.Nonnull;

import com.helger.commons.annotation.Nonempty;

/**
 * Contains the Peppol generic document type identifier parts. See
 * {@link IPeppolDocumentTypeIdentifierParts} for the XML specific version.
 *
 * @author Philip Helger
 * @since 9.6.2
 */
public interface IPeppolGenericDocumentTypeIdentifierParts
{
  /**
   * Separator between syntax specific ID and the customization ID
   */
  String SYNTAX_SPECIFIC_ID_SEPARATOR = "##";

  /**
   * Separates the customization ID from the version
   */
  String VERSION_SEPARATOR = "::";

  /**
   * @return Syntax specific ID
   */
  @Nonnull
  @Nonempty
  String getSyntaxSpecificID ();

  /**
   * @return The Customization ID
   */
  @Nonnull
  @Nonempty
  String getCustomizationID ();

  /**
   * @return The syntax version number.
   */
  @Nonnull
  @Nonempty
  String getVersion ();

  /**
   * @return {@link #getSyntaxSpecificID()} +
   *         {@link #SYNTAX_SPECIFIC_ID_SEPARATOR} +
   *         {@link #getCustomizationID()} + {@link #VERSION_SEPARATOR} +
   *         {@link #getVersion()}
   */
  @Nonnull
  @Nonempty
  String getAsDocumentTypeIdentifierValue ();
}
