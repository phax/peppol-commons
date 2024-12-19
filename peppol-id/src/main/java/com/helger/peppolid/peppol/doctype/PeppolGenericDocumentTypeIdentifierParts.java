/*
 * Copyright (C) 2015-2024 Philip Helger
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
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.peppolid.IDocumentTypeIdentifier;

/**
 * A generic implementation of {@link IPeppolGenericDocumentTypeIdentifierParts}
 * for Peppol Document Type Identifiers.
 *
 * @author Philip Helger
 * @since 9.6.2
 */
@Immutable
public class PeppolGenericDocumentTypeIdentifierParts implements IPeppolGenericDocumentTypeIdentifierParts
{
  private final String m_sSyntaxSpecificID;
  private final String m_sCustomizationID;
  private final String m_sVersion;

  public PeppolGenericDocumentTypeIdentifierParts (@Nonnull @Nonempty final String sSyntaxSpecificID,
                                                   @Nonnull @Nonempty final String sCustomizationID,
                                                   @Nonnull @Nonempty final String sVersion)
  {
    ValueEnforcer.notEmpty (sSyntaxSpecificID, "SyntaxSpecificID");
    ValueEnforcer.notEmpty (sCustomizationID, "CustomizationID");
    ValueEnforcer.notEmpty (sVersion, "Version");

    m_sSyntaxSpecificID = sSyntaxSpecificID;
    m_sCustomizationID = sCustomizationID;
    m_sVersion = sVersion;
  }

  @Nonnull
  public final String getSyntaxSpecificID ()
  {
    return m_sSyntaxSpecificID;
  }

  @Nonnull
  @Nonempty
  public final String getCustomizationID ()
  {
    return m_sCustomizationID;
  }

  @Nonnull
  @Nonempty
  public final String getVersion ()
  {
    return m_sVersion;
  }

  @Nonnull
  public PeppolGenericDocumentTypeIdentifierParts withCustomizationID (@Nonnull @Nonempty final String sCustomizationID)
  {
    return new PeppolGenericDocumentTypeIdentifierParts (m_sSyntaxSpecificID, sCustomizationID, m_sVersion);
  }

  @Nonnull
  public PeppolGenericDocumentTypeIdentifierParts withVersion (@Nonnull @Nonempty final String sVersion)
  {
    return new PeppolGenericDocumentTypeIdentifierParts (m_sSyntaxSpecificID, m_sCustomizationID, sVersion);
  }

  @Nonnull
  @Nonempty
  public final String getAsDocumentTypeIdentifierValue ()
  {
    return m_sSyntaxSpecificID + SYNTAX_SPECIFIC_ID_SEPARATOR + m_sCustomizationID + VERSION_SEPARATOR + m_sVersion;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("SyntaxSpecificID", m_sSyntaxSpecificID)
                                       .append ("CustomizationID", m_sCustomizationID)
                                       .append ("Version", m_sVersion)
                                       .getToString ();
  }

  /**
   * Extract the Peppol document identifier type elements from the passed
   * document identifier value. The expected format is
   * <code>&lt;syntax specific id&gt;##&lt;customization id&gt;::&lt;version&gt;</code>
   *
   * @param sDocTypeIDValue
   *        The document identifier value (without the scheme) to be split. May
   *        neither be <code>null</code> nor empty.
   * @return The non-<code>null</code> Peppol identifier parts
   * @throws IllegalArgumentException
   *         if the passed document identifier value does not match the
   *         specifications
   */
  @Nonnull
  public static PeppolGenericDocumentTypeIdentifierParts extractFromString (@Nonnull @Nonempty final String sDocTypeIDValue)
  {
    ValueEnforcer.notEmpty (sDocTypeIDValue, "DocumentTypeIdentifier");

    // <syntax specific id>##<customization id>::<version>
    final ICommonsList <String> aMain = StringHelper.getExploded (SYNTAX_SPECIFIC_ID_SEPARATOR, sDocTypeIDValue, 2);
    if (aMain.size () != 2)
      throw new IllegalArgumentException ("The passed document type identifier is missing the separator between the Syntax specific ID and the Customization ID!");

    final String sSyntaxSpecificID = aMain.get (0);
    if (StringHelper.hasNoText (sSyntaxSpecificID))
      throw new IllegalArgumentException ("The passed document type identifier has an empty Syntax specific ID!");

    final String sRest = aMain.get (1);
    if (StringHelper.hasNoText (sRest))
      throw new IllegalArgumentException ("The passed document type identifier has an nothing after the Syntax specific ID!");

    // Rest: <customization id>::<version>
    // Problem: if Customization ID contains a "::"
    final int nLastIndex = sRest.lastIndexOf (VERSION_SEPARATOR);
    if (nLastIndex < 0)
      throw new IllegalArgumentException ("The passed document type identifier remainder '" +
                                          sRest +
                                          "' is missing the separation between Customization ID and Version!");

    // Before last "::"
    final String sCustomizationID = sRest.substring (0, nLastIndex);
    if (StringHelper.hasNoText (sCustomizationID))
      throw new IllegalArgumentException ("The passed document type identifier remainder '" +
                                          sRest +
                                          "' contains an empty Customization ID!");

    // After last "::"
    final String sVersion = sRest.substring (nLastIndex + VERSION_SEPARATOR.length ());
    if (StringHelper.hasNoText (sVersion))
      throw new IllegalArgumentException ("The passed document type identifier remainder '" +
                                          sRest +
                                          "' contains an empty Version!");

    return new PeppolGenericDocumentTypeIdentifierParts (sSyntaxSpecificID, sCustomizationID, sVersion);
  }

  /**
   * Convert the passed document type identifier value into its parts.
   *
   * @param aIdentifier
   *        The document type identifier to be split. May not be
   *        <code>null</code>.
   * @return Never <code>null</code>.
   * @throws IllegalArgumentException
   *         If the passed document type identifier is not a Peppol document
   *         type identifier.
   */
  @Nonnull
  public static PeppolGenericDocumentTypeIdentifierParts extractFromIdentifier (@Nonnull final IDocumentTypeIdentifier aIdentifier)
  {
    ValueEnforcer.notNull (aIdentifier, "Identifier");

    return extractFromString (aIdentifier.getValue ());
  }
}
