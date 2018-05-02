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
package com.helger.peppol.identifier.peppol.doctype;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.peppol.identifier.generic.doctype.BusdoxDocumentTypeIdentifierParts;
import com.helger.peppol.identifier.generic.doctype.IBusdoxDocumentTypeIdentifierParts;

/**
 * A standalone wrapper class for the {@link IPeppolDocumentTypeIdentifierParts}
 * interface for PEPPOL BISs.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
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
   * Build the BusDox sub type identifier from the PEPPOL specific components.
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
    this (new BusdoxDocumentTypeIdentifierParts (sRootNS,
                                                 sLocalName,
                                                 _buildSubTypeIdentifier (sCustomizationID, sVersion)),
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
   * @return The whole sub-type identifier, incl. PEPPOL transaction ID,
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
   * Extract the PEPPOL document identifier elements from the passed document
   * identifier value. The different of the PEPPOL document identifier parts to
   * the BusDox document identifier parts is, that the PEPPOL subtype identifier
   * is further defined as
   * <code>&lt;customization id&gt;::&lt;version&gt;</code>. The customization
   * ID can be further detailed into
   * <code>&lt;transactionId&gt;:#&lt;extensionId&gt;[#&lt;extensionId&gt;]</code>
   *
   * @param sDocTypeID
   *        The document identifier value to be split. May neither be
   *        <code>null</code> nor empty.
   * @return The non-<code>null</code> PEPPOL identifier parts
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
      throw new IllegalArgumentException ("The passed document identifier has an empty sub type identifier which is not PEPPOL compliant!");

    // PEPPOL sub-type identifier
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
      throw new IllegalArgumentException ("The sub type identifier '" +
                                          sSubTypeIdentifier +
                                          "' contains an empty customization ID!");

    final String sVersion = aSubTypeParts[1];
    if (StringHelper.hasNoText (sVersion))
      throw new IllegalArgumentException ("The sub type identifier '" +
                                          sSubTypeIdentifier +
                                          "' contains an empty version!");

    return new PeppolDocumentTypeIdentifierParts (aBusdoxParts, sCustomizationID, sVersion);
  }
}
