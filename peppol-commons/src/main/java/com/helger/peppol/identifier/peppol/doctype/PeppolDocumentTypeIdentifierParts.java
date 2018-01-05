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

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
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
   * Separates the transaction ID from the extensions
   */
  public static final String TRANSACTIONID_SEPARATOR = ":#";

  /**
   * Separates the different extensions from each other
   */
  public static final String EXTENSION_SEPARATOR = "#";

  /**
   * Separates the customization ID from the version
   */
  public static final String VERSION_SEPARATOR = "::";

  private final IBusdoxDocumentTypeIdentifierParts m_aBusdoxParts;
  private final String m_sTransactionID;
  private final ICommonsList <String> m_aExtensionIDs;
  private final String m_sVersion;

  /**
   * Build the BusDox sub type identifier from the PEPPOL specific components.
   *
   * @param sTransactionID
   *        Transaction ID
   * @param aExtensionIDs
   *        extension IDs (at least one)
   * @param sVersion
   *        Version number
   * @return The sub type identifier.
   */
  @Nonnull
  private static String _buildSubTypeIdentifier (@Nonnull final String sTransactionID,
                                                 @Nonnull @Nonempty final List <String> aExtensionIDs,
                                                 @Nonnull final String sVersion)
  {
    return sTransactionID +
           TRANSACTIONID_SEPARATOR +
           StringHelper.getImploded (EXTENSION_SEPARATOR, aExtensionIDs) +
           VERSION_SEPARATOR +
           sVersion;
  }

  private PeppolDocumentTypeIdentifierParts (@Nonnull final IBusdoxDocumentTypeIdentifierParts aBusdoxParts,
                                             @Nonnull @Nonempty final String sTransactionID,
                                             @Nonnull @Nonempty final List <String> aExtensionIDs,
                                             @Nonnull @Nonempty final String sVersion)
  {
    ValueEnforcer.notEmpty (sTransactionID, "TransactionID");
    ValueEnforcer.notEmpty (aExtensionIDs, "ExtensionIDs");
    for (final String sExtensionID : aExtensionIDs)
      if (StringHelper.hasNoText (sExtensionID))
        throw new IllegalArgumentException ("the extension IDs contain at least one empty element!");
    ValueEnforcer.notEmpty (sVersion, "Version");

    m_aBusdoxParts = aBusdoxParts;
    m_sTransactionID = sTransactionID;
    m_aExtensionIDs = new CommonsArrayList <> (aExtensionIDs);
    m_sVersion = sVersion;
  }

  public PeppolDocumentTypeIdentifierParts (@Nonnull @Nonempty final String sRootNS,
                                            @Nonnull @Nonempty final String sLocalName,
                                            @Nonnull @Nonempty final String sTransactionID,
                                            @Nonnull @Nonempty final List <String> aExtensionIDs,
                                            @Nonnull @Nonempty final String sVersion)
  {
    this (new BusdoxDocumentTypeIdentifierParts (sRootNS,
                                                 sLocalName,
                                                 _buildSubTypeIdentifier (sTransactionID, aExtensionIDs, sVersion)),
          sTransactionID,
          aExtensionIDs,
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
  public String getTransactionID ()
  {
    return m_sTransactionID;
  }

  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public ICommonsList <String> getExtensionIDs ()
  {
    return m_aExtensionIDs.getClone ();
  }

  @Nonnull
  @Nonempty
  public String getVersion ()
  {
    return m_sVersion;
  }

  @Nonnull
  @Nonempty
  public String getAsUBLCustomizationID ()
  {
    return m_sTransactionID + TRANSACTIONID_SEPARATOR + StringHelper.getImploded (EXTENSION_SEPARATOR, m_aExtensionIDs);
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
    return new ToStringGenerator (this).append ("busdoxParts", m_aBusdoxParts)
                                       .append ("transactionID", m_sTransactionID)
                                       .append ("extensionIDs", m_aExtensionIDs)
                                       .append ("version", m_sVersion)
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
    final String [] aSubTypeParts = RegExHelper.getSplitToArray (sSubTypeIdentifier, VERSION_SEPARATOR, 2);
    if (aSubTypeParts.length < 2)
      throw new IllegalArgumentException ("The sub type identifier '" +
                                          sSubTypeIdentifier +
                                          "' is missing the separation between customization ID and version!");
    final String sVersion = aSubTypeParts[1];
    if (StringHelper.hasNoText (sVersion))
      throw new IllegalArgumentException ("The sub type identifier '" +
                                          sSubTypeIdentifier +
                                          "' contains an empty version!");

    final String sCustomizationID = aSubTypeParts[0];
    final String [] aCustomizationIDs = RegExHelper.getSplitToArray (sCustomizationID, TRANSACTIONID_SEPARATOR, 2);
    if (aCustomizationIDs.length < 2)
      throw new IllegalArgumentException ("The sub type identifier '" +
                                          sSubTypeIdentifier +
                                          "' is missing the separation between transaction ID and the extensions!");

    final String sTransactionID = aCustomizationIDs[0];
    if (StringHelper.hasNoText (sTransactionID))
      throw new IllegalArgumentException ("The customization ID '" +
                                          sCustomizationID +
                                          "' contains an empty transaction ID!");

    final String sExtensionIDs = aCustomizationIDs[1];
    if (StringHelper.hasNoText (sExtensionIDs))
      throw new IllegalArgumentException ("The customization ID '" +
                                          sCustomizationID +
                                          "' contains an empty customization ID!");

    final ICommonsList <String> aExtensionIDs = StringHelper.getExploded (EXTENSION_SEPARATOR, sExtensionIDs);
    return new PeppolDocumentTypeIdentifierParts (aBusdoxParts, sTransactionID, aExtensionIDs, sVersion);
  }
}
