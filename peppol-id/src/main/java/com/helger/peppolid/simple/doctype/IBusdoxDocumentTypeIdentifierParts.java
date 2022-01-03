/*
 * Copyright (C) 2015-2022 Philip Helger
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
package com.helger.peppolid.simple.doctype;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;

/**
 * Contains all the different fields of a document type identifier for BusDox.
 * Note: it is important to note, that the PEPPOL specification separates the
 * sub type identifier more clearly!
 *
 * @author Philip Helger
 */
public interface IBusdoxDocumentTypeIdentifierParts extends Serializable
{
  /**
   * Separator between namespace and local name
   */
  String NAMESPACE_SEPARATOR = "::";

  /**
   * Separator between namespace elements and the optional subtype
   */
  String SUBTYPE_SEPARATOR = "##";

  /**
   * @return The root namespace. Never <code>null</code> nor empty.
   */
  @Nonnull
  @Nonempty
  String getRootNS ();

  /**
   * @return The document element local name. Never <code>null</code> nor empty.
   */
  @Nonnull
  @Nonempty
  String getLocalName ();

  /**
   * @return The optional sub type identifier. May be <code>null</code>.
   */
  @Nullable
  String getSubTypeIdentifier ();

  /**
   * @return The parts assembled into a complete document identifier value.
   *         Never <code>null</code> nor empty.
   */
  @Nonnull
  @Nonempty
  String getAsDocumentTypeIdentifierValue ();
}
