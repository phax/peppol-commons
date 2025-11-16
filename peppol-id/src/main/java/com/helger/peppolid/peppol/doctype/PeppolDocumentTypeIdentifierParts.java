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

import java.util.List;
import java.util.function.BiConsumer;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.Immutable;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.string.StringHelper;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.base.wrapper.Wrapper;
import com.helger.peppolid.IDocumentTypeIdentifier;

/**
 * The implementation of {@link IPeppolDocumentTypeIdentifierParts} for Peppol Document Type
 * Identifiers that are XML based.
 *
 * @author Philip Helger
 */
@Immutable
public class PeppolDocumentTypeIdentifierParts extends PeppolGenericDocumentTypeIdentifierParts implements
                                               IPeppolDocumentTypeIdentifierParts
{
  private final String m_sRootNS;
  private final String m_sLocalName;

  /**
   * Convert the XML specific syntax elements back to a single syntax specific ID
   *
   * @param sRootNS
   *        The XML root element namespace URI. May neither be <code>null</code> nor empty.
   * @param sLocalName
   *        The XML root element local name. May neither be <code>null</code> nor empty.
   * @return The combination of <code>rootNS + :: + localName</code>
   */
  @NonNull
  @Nonempty
  public static String createSyntaxSpecificID (@NonNull @Nonempty final String sRootNS,
                                               @NonNull @Nonempty final String sLocalName)
  {
    return sRootNS + NAMESPACE_SEPARATOR + sLocalName;
  }

  public PeppolDocumentTypeIdentifierParts (@NonNull @Nonempty final String sRootNS,
                                            @NonNull @Nonempty final String sLocalName,
                                            @NonNull @Nonempty final String sCustomizationID,
                                            @NonNull @Nonempty final String sVersion)
  {
    super (createSyntaxSpecificID (sRootNS, sLocalName), sCustomizationID, sVersion);
    ValueEnforcer.notEmpty (sRootNS, "RootNS");
    ValueEnforcer.notEmpty (sLocalName, "LocalName");

    m_sRootNS = sRootNS;
    m_sLocalName = sLocalName;
  }

  @NonNull
  @Nonempty
  public String getRootNS ()
  {
    return m_sRootNS;
  }

  @NonNull
  @Nonempty
  public String getLocalName ()
  {
    return m_sLocalName;
  }

  @Override
  @NonNull
  public PeppolDocumentTypeIdentifierParts withCustomizationID (@NonNull @Nonempty final String sCustomizationID)
  {
    return new PeppolDocumentTypeIdentifierParts (m_sRootNS, m_sLocalName, sCustomizationID, getVersion ());
  }

  @Override
  @NonNull
  public PeppolDocumentTypeIdentifierParts withVersion (@NonNull @Nonempty final String sVersion)
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

  public static boolean isSyntaxSpecificIDLookingLikeXML (@Nullable final String sSyntaxSpecificID)
  {
    if (StringHelper.isNotEmpty (sSyntaxSpecificID))
    {
      final int nIndex = sSyntaxSpecificID.indexOf (NAMESPACE_SEPARATOR);
      if (nIndex >= 0)
      {
        // It's contains the separator and it's not the start and not the end
        return nIndex > 0 && nIndex < sSyntaxSpecificID.length () - NAMESPACE_SEPARATOR.length ();
      }
    }
    return false;
  }

  /**
   * Split the provided syntax specific ID into the XML root element namespace URI and the XML root
   * element local name.
   *
   * @param sSyntaxSpecificID
   *        The syntax specific ID to parse. May neither be <code>null</code> nor empty.
   * @param aResultConsumer
   *        The consumer that takes root namespace URI and local name as a callback.
   * @since 9.6.2
   */
  public static void extractXMLSyntaxSpecificID (@NonNull @Nonempty final String sSyntaxSpecificID,
                                                 @NonNull final BiConsumer <String, String> aResultConsumer)
  {
    ValueEnforcer.notEmpty (sSyntaxSpecificID, "SyntaxSpecificID");
    ValueEnforcer.notNull (aResultConsumer, "ResultConsumer");

    final List <String> aFirst = StringHelper.getExploded (NAMESPACE_SEPARATOR, sSyntaxSpecificID, 2);
    if (aFirst.size () < 2)
      throw new IllegalArgumentException ("The Syntax Specific ID '" +
                                          sSyntaxSpecificID +
                                          "' is missing the separation between the root namespace URI and the local name!");

    final String sRootNS = aFirst.get (0);
    if (StringHelper.isEmpty (sRootNS))
      throw new IllegalArgumentException ("The Syntax Specific ID '" +
                                          sSyntaxSpecificID +
                                          "' contains an empty root namespace URI!");
    final String sLocalName = aFirst.get (1);
    if (StringHelper.isEmpty (sRootNS))
      throw new IllegalArgumentException ("The Syntax Specific ID '" +
                                          sSyntaxSpecificID +
                                          "' contains an empty local name!");

    aResultConsumer.accept (sRootNS, sLocalName);
  }

  /**
   * Parse an OpenPeppol Document Type Identifier using the XML syntax.
   *
   * @param sDocTypeIDValue
   *        The document identifier value (without the scheme) to be split. May neither be
   *        <code>null</code> nor empty.
   * @return The non-<code>null</code> Peppol identifier parts
   * @throws IllegalArgumentException
   *         if the passed document identifier value does not match the specifications
   */
  @NonNull
  public static PeppolDocumentTypeIdentifierParts extractFromString (@NonNull @Nonempty final String sDocTypeIDValue)
  {
    final PeppolGenericDocumentTypeIdentifierParts aGenericParts = PeppolGenericDocumentTypeIdentifierParts.extractFromString (sDocTypeIDValue);

    final Wrapper <String> aRootNS = new Wrapper <> ();
    final Wrapper <String> aLocalName = new Wrapper <> ();
    extractXMLSyntaxSpecificID (aGenericParts.getSyntaxSpecificID (), (ns, ln) -> {
      aRootNS.set (ns);
      aLocalName.set (ln);
    });

    return new PeppolDocumentTypeIdentifierParts (aRootNS.get (),
                                                  aLocalName.get (),
                                                  aGenericParts.getCustomizationID (),
                                                  aGenericParts.getVersion ());
  }

  /**
   * Convert the passed document type identifier into its parts. First the old Peppol scheme for
   * identifiers is tried, and afterwards the OpenPEPPOL scheme for document type identifiers is
   * used.
   *
   * @param aIdentifier
   *        The document type identifier to be split. May not be <code>null</code>.
   * @return Never <code>null</code>.
   * @throws IllegalArgumentException
   *         If the passed document type identifier is not a Peppol document type identifier.
   */
  @NonNull
  public static PeppolDocumentTypeIdentifierParts extractFromIdentifier (@NonNull final IDocumentTypeIdentifier aIdentifier)
  {
    ValueEnforcer.notNull (aIdentifier, "Identifier");

    return extractFromString (aIdentifier.getValue ());
  }

  @NonNull
  public static PeppolDocumentTypeIdentifierParts extract (@NonNull final IPeppolGenericDocumentTypeIdentifierParts aDocIDParts)
  {
    ValueEnforcer.notNull (aDocIDParts, "DocIDParts");

    final Wrapper <String> aRootNS = new Wrapper <> ();
    final Wrapper <String> aLocalName = new Wrapper <> ();
    extractXMLSyntaxSpecificID (aDocIDParts.getSyntaxSpecificID (), (ns, ln) -> {
      aRootNS.set (ns);
      aLocalName.set (ln);
    });

    return new PeppolDocumentTypeIdentifierParts (aRootNS.get (),
                                                  aLocalName.get (),
                                                  aDocIDParts.getCustomizationID (),
                                                  aDocIDParts.getVersion ());
  }
}
