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
 * The implementation of {@link IPeppolDocumentTypeIdentifierParts} for Peppol
 * Document Type Identifiers that are XML based.
 *
 * @author Philip Helger
 */
@Immutable
public class PeppolDocumentTypeIdentifierParts extends PeppolGenericDocumentTypeIdentifierParts implements
                                               IPeppolDocumentTypeIdentifierParts
{
  private final String m_sRootNS;
  private final String m_sLocalName;

  public static String createSyntaxSpecificID (@Nonnull @Nonempty final String sRootNS,
                                               @Nonnull @Nonempty final String sLocalName)
  {
    return sRootNS + NAMESPACE_SEPARATOR + sLocalName;
  }

  public PeppolDocumentTypeIdentifierParts (@Nonnull @Nonempty final String sRootNS,
                                            @Nonnull @Nonempty final String sLocalName,
                                            @Nonnull @Nonempty final String sCustomizationID,
                                            @Nonnull @Nonempty final String sVersion)
  {
    super (createSyntaxSpecificID (sRootNS, sLocalName), sCustomizationID, sVersion);
    ValueEnforcer.notEmpty (sRootNS, "RootNS");
    ValueEnforcer.notEmpty (sLocalName, "LocalName");

    m_sRootNS = sRootNS;
    m_sLocalName = sLocalName;
  }

  @Nonnull
  @Nonempty
  public String getRootNS ()
  {
    return m_sRootNS;
  }

  @Nonnull
  @Nonempty
  public String getLocalName ()
  {
    return m_sLocalName;
  }

  @Override
  @Nonnull
  public PeppolDocumentTypeIdentifierParts withCustomizationID (@Nonnull @Nonempty final String sCustomizationID)
  {
    return new PeppolDocumentTypeIdentifierParts (m_sRootNS, m_sLocalName, sCustomizationID, getVersion ());
  }

  @Override
  @Nonnull
  public PeppolDocumentTypeIdentifierParts withVersion (@Nonnull @Nonempty final String sVersion)
  {
    return new PeppolDocumentTypeIdentifierParts (m_sRootNS, m_sLocalName, getCustomizationID (), sVersion);
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ())
                            .append ("RootNS", m_sRootNS)
                            .append ("LocalName", m_sLocalName)
                            .getToString ();
  }

  /**
   * Parse an OpenPeppol Document Type Identifier using the XML syntax.
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
  public static PeppolDocumentTypeIdentifierParts extractFromString (@Nonnull @Nonempty final String sDocTypeIDValue)
  {
    final PeppolGenericDocumentTypeIdentifierParts aGenericParts = PeppolGenericDocumentTypeIdentifierParts.extractFromString (sDocTypeIDValue);
    final String sSyntaxSpecificID = aGenericParts.getSyntaxSpecificID ();

    final ICommonsList <String> aFirst = StringHelper.getExploded (NAMESPACE_SEPARATOR, sSyntaxSpecificID, 2);
    if (aFirst.size () < 2)
      throw new IllegalArgumentException ("The Syntax Specific ID '" +
                                          sSyntaxSpecificID +
                                          "' is missing the separation between the root namespace URI and the local name!");

    final String sRootNS = aFirst.get (0);
    if (StringHelper.hasNoText (sRootNS))
      throw new IllegalArgumentException ("The Syntax Specific ID '" +
                                          sSyntaxSpecificID +
                                          "' contains an empty root namespace URI!");
    final String sLocalName = aFirst.get (1);
    if (StringHelper.hasNoText (sRootNS))
      throw new IllegalArgumentException ("The Syntax Specific ID '" +
                                          sSyntaxSpecificID +
                                          "' contains an empty local name!");

    return new PeppolDocumentTypeIdentifierParts (sRootNS,
                                                  sLocalName,
                                                  aGenericParts.getCustomizationID (),
                                                  aGenericParts.getVersion ());
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
  public static PeppolDocumentTypeIdentifierParts extractFromIdentifier (@Nonnull final IDocumentTypeIdentifier aIdentifier)
  {
    ValueEnforcer.notNull (aIdentifier, "Identifier");

    return extractFromString (aIdentifier.getValue ());
  }
}
