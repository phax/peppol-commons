/**
 * Copyright (C) 2015-2018 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.identifier.generic.doctype;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;

/**
 * Contains all the different fields of a document type identifier for BusDox.
 * Note: it is important to note, that the PEPPOL specification separates the
 * sub type identifier more clearly!
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
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
