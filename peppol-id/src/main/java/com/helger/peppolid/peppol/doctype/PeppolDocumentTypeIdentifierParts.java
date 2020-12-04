/**
 * Copyright (C) 2015-2020 Philip Helger
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
import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.simple.doctype.BusdoxDocumentTypeIdentifierParts;
import com.helger.peppolid.simple.doctype.IBusdoxDocumentTypeIdentifierParts;

/**
 * A standalone wrapper class for the {@link IPeppolDocumentTypeIdentifierParts}
 * interface for Peppol BISs.
 *
 * @author Philip Helger
 */
@Immutable
public final class PeppolDocumentTypeIdentifierParts implements IPeppolDocumentTypeIdentifierParts
{
  /**
   * Separates the customization ID from the version
   */
  public static final String VERSION_SEPARATOR = "::";

  private final IBusdoxDocumentTypeIdentifierParts m_aBusdoxParts;
  private final String m_sCustomizationID;
  private final String m_sVersion;

  /**
   * Build the BusDox sub type identifier from the Peppol specific components.
   *
   * @param sCustomizationID
   *        Customization ID
   * @param sVersion
   *        Version number
   * @return The sub type identifier.
   */
  @Nonnull
  private static String _buildSubTypeIdentifier (@Nonnull final String sCustomizationID, @Nonnull final String sVersion)
  {
    return sCustomizationID + VERSION_SEPARATOR + sVersion;
  }

  private PeppolDocumentTypeIdentifierParts (@Nonnull final IBusdoxDocumentTypeIdentifierParts aBusdoxParts,
                                             @Nonnull @Nonempty final String sCustomizationID,
                                             @Nonnull @Nonempty final String sVersion)
  {
    ValueEnforcer.notEmpty (sCustomizationID, "CustomizationID");
    ValueEnforcer.notEmpty (sVersion, "Version");

    m_aBusdoxParts = aBusdoxParts;
    m_sCustomizationID = sCustomizationID;
    m_sVersion = sVersion;
  }

  public PeppolDocumentTypeIdentifierParts (@Nonnull @Nonempty final String sRootNS,
                                            @Nonnull @Nonempty final String sLocalName,
                                            @Nonnull @Nonempty final String sCustomizationID,
                                            @Nonnull @Nonempty final String sVersion)
  {
    this (new BusdoxDocumentTypeIdentifierParts (sRootNS, sLocalName, _buildSubTypeIdentifier (sCustomizationID, sVersion)),
          sCustomizationID,
          sVersion);
  }

  @Nonnull
  @Nonempty
  public String getRootNS ()
  {
    return m_aBusdoxParts.getRootNS ();
  }

  @Nonnull
  @Nonempty
  public String getLocalName ()
  {
    return m_aBusdoxParts.getLocalName ();
  }

  /**
   * @return The whole sub-type identifier, incl. Peppol transaction ID,
   *         extensions and version ID.
   */
  @Nonnull
  @Nonempty
  public String getSubTypeIdentifier ()
  {
    return m_aBusdoxParts.getSubTypeIdentifier ();
  }

  @Nonnull
  @Nonempty
  public String getCustomizationID ()
  {
    return m_sCustomizationID;
  }

  @Nonnull
  @Nonempty
  public String getVersion ()
  {
    return m_sVersion;
  }

  @Nonnull
  @Nonempty
  public String getAsDocumentTypeIdentifierValue ()
  {
    return m_aBusdoxParts.getAsDocumentTypeIdentifierValue ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("BusdoxParts", m_aBusdoxParts)
                                       .append ("CustomizationID", m_sCustomizationID)
                                       .append ("Version", m_sVersion)
                                       .getToString ();
  }

  /**
   * Extract the Peppol document identifier elements from the passed document
   * identifier value. The different of the Peppol document identifier parts to
   * the BusDox document identifier parts is, that the Peppol subtype identifier
   * is further defined as
   * <code>&lt;customization id&gt;::&lt;version&gt;</code>. The customization
   * ID can be further detailed into
   * <code>&lt;transactionId&gt;:#&lt;extensionId&gt;[#&lt;extensionId&gt;]</code>
   *
   * @param sDocTypeID
   *        The document identifier value to be split. May neither be
   *        <code>null</code> nor empty.
   * @return The non-<code>null</code> Peppol identifier parts
   * @throws IllegalArgumentException
   *         if the passed document identifier value does not match the
   *         specifications
   */
  @Nonnull
  public static IPeppolDocumentTypeIdentifierParts extractFromString (@Nonnull @Nonempty final String sDocTypeID)
  {
    // Extract the main 3 elements (root namespace, local name and sub-type)
    final IBusdoxDocumentTypeIdentifierParts aBusdoxParts = BusdoxDocumentTypeIdentifierParts.extractFromString (sDocTypeID);

    // Now start splitting the sub-type identifier
    final String sSubTypeIdentifier = aBusdoxParts.getSubTypeIdentifier ();
    if (StringHelper.hasNoText (sSubTypeIdentifier))
      throw new IllegalArgumentException ("The passed document identifier has an empty sub type identifier which is not Peppol compliant!");

    // Peppol sub-type identifier
    // <customization id>::<version>
    // --> even more detailed:
    // <transactionId>:#<extensionId>[#<extensionId>]::<version>
    // or
    // <transactionId>:extended:<extensionId>[:extended:<extensionId>]::<version>
    final String [] aSubTypeParts = RegExHelper.getSplitToArray (sSubTypeIdentifier, VERSION_SEPARATOR, 2);
    if (aSubTypeParts.length < 2)
      throw new IllegalArgumentException ("The sub type identifier '" +
                                          sSubTypeIdentifier +
                                          "' is missing the separation between customization ID and version!");

    final String sCustomizationID = aSubTypeParts[0];
    if (StringHelper.hasNoText (sCustomizationID))
      throw new IllegalArgumentException ("The sub type identifier '" + sSubTypeIdentifier + "' contains an empty customization ID!");

    final String sVersion = aSubTypeParts[1];
    if (StringHelper.hasNoText (sVersion))
      throw new IllegalArgumentException ("The sub type identifier '" + sSubTypeIdentifier + "' contains an empty version!");

    return new PeppolDocumentTypeIdentifierParts (aBusdoxParts, sCustomizationID, sVersion);
  }

  /**
   * Convert the passed document type identifier into its parts. First the old
   * Peppol scheme for identifiers is tried, and afterwards the OpenPEPPOL
   * scheme for document type identifiers is used.
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
  public static IPeppolDocumentTypeIdentifierParts extractFromIdentifier (@Nonnull final IDocumentTypeIdentifier aIdentifier)
  {
    ValueEnforcer.notNull (aIdentifier, "Identifier");

    return extractFromString (aIdentifier.getValue ());
  }
}
