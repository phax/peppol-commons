/*
 * Copyright (C) 2015-2023 Philip Helger
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
package com.helger.smpclient.peppol;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.state.EContinue;
import com.helger.commons.string.ToStringGenerator;
import com.helger.peppolid.CIdentifier;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.peppolid.peppol.doctype.IPeppolDocumentTypeIdentifierParts;
import com.helger.peppolid.peppol.doctype.PeppolDocumentTypeIdentifierParts;

/**
 * Helper class to support the different ways of dealing with
 * peppol-doctype-wildcard scheme in combination with the busdox-docid-qns
 * scheme.
 *
 * @author Philip Helger
 * @since 9.2.0
 */
@Immutable
public class PeppolWildcardSelector
{
  /**
   * Defines the different selection modes
   *
   * @author Philip Helger
   */
  public enum EMode
  {
    /**
     * Select document types with scheme <code>busdox-docid-qns</code> only.
     */
    BUSDOX_ONLY,
    /**
     * Select document types with scheme <code>peppol-doctype-wildcard</code>
     * only. This is e.g. the correct mode for PINT JP.
     */
    WILDCARD_ONLY,
    /**
     * Select document types with scheme <code>busdox-docid-qns</code> followed
     * by scheme <code>peppol-doctype-wildcard</code>.
     */
    BUSDOX_THEN_WILDCARD,
    /**
     * Select document types with scheme <code>peppol-doctype-wildcard</code>
     * followed by scheme <code>busdox-docid-qns</code>.
     */
    WILDCARD_THEN_BUSDOX
  }

  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolWildcardSelector.class);

  private final EMode m_eMode;

  /**
   * Create a new wildcard selector using the provided operational mode.
   *
   * @param eMode
   *        The selection mode to use. May not be <code>null</code>.
   */
  public PeppolWildcardSelector (@Nonnull final EMode eMode)
  {
    ValueEnforcer.notNull (eMode, "Mode");
    m_eMode = eMode;
  }

  /**
   * @return The selection mode as provided in the constructor.
   */
  @Nonnull
  public final EMode getMode ()
  {
    return m_eMode;
  }

  /**
   * Helper method to iterate all matching document type identifiers. The
   * preference of schemes is defined by the operational mode.
   *
   * @param aBaseDocTypes
   *        The list of document types to filter. Usually this list was obtained
   *        from an SMP query "get all receiving capabilities of participant".
   *        May not be <code>null</code>, but maybe empty.
   * @param sDocTypeValue
   *        The document type identifier value (!) <b>without</b> the scheme to
   *        search. The schemes are added internally automatically.
   * @param aMatchingDocTypeConsumer
   *        The consumer to be invoked for each match. May not be
   *        <code>null</code>.
   */
  public void forEachMatchingDocumentType (@Nonnull final ICommonsList <? extends IDocumentTypeIdentifier> aBaseDocTypes,
                                           @Nonnull @Nonempty final String sDocTypeValue,
                                           @Nonnull final Function <? super IDocumentTypeIdentifier, EContinue> aMatchingDocTypeConsumer)
  {
    ValueEnforcer.notNull (aBaseDocTypes, "BaseDocTypes");
    ValueEnforcer.notEmpty (sDocTypeValue, "DocTypeValue");
    ValueEnforcer.notNull (aMatchingDocTypeConsumer, "MatchingDocTypeConsumer");

    final BiFunction <String, String, IDocumentTypeIdentifier> aFuncCheckExistance = (sScheme, sValue) -> {
      if (LOGGER.isInfoEnabled ())
        LOGGER.info ("Checking if document type ID '" + CIdentifier.getURIEncoded (sScheme, sValue) + "' is contained");
      if (aBaseDocTypes.containsAny (x -> x.hasScheme (sScheme) && x.hasValue (sValue)))
        return PeppolIdentifierFactory.INSTANCE.createDocumentTypeIdentifier (sScheme, sValue);
      return null;
    };

    // Try busdox-docid-qns ("as is")
    final Supplier <EContinue> aBusdoxMatch = () -> {
      final IDocumentTypeIdentifier aSelectedDocTypeID = aFuncCheckExistance.apply (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS,
                                                                                    sDocTypeValue);
      if (aSelectedDocTypeID != null)
        if (aMatchingDocTypeConsumer.apply (aSelectedDocTypeID).isBreak ())
          return EContinue.BREAK;

      return EContinue.CONTINUE;
    };

    // Try wildcard match (new PINT style logic)
    final Supplier <EContinue> aWildcardMatch = () -> {
      try
      {
        // Split the document type identifier value into pieces (throws
        // IllegalArgumentException)
        final IPeppolDocumentTypeIdentifierParts aParts = PeppolDocumentTypeIdentifierParts.extractFromString (sDocTypeValue);

        // Just change the customization ID of the parts
        final Function <String, String> aFuncCustIDToDocTypeIDValue = sCustomizationID -> new PeppolDocumentTypeIdentifierParts (aParts.getRootNS (),
                                                                                                                                 aParts.getLocalName (),
                                                                                                                                 sCustomizationID,
                                                                                                                                 aParts.getVersion ()).getAsDocumentTypeIdentifierValue ();

        // Iterate all the peppol-doctype-wildcard stuff
        String sRemainingCustomizationID = aParts.getCustomizationID ();
        IDocumentTypeIdentifier aSelectedDocTypeID = aFuncCheckExistance.apply (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD,
                                                                                aFuncCustIDToDocTypeIDValue.apply (sRemainingCustomizationID +
                                                                                                                   PeppolIdentifierHelper.DOCUMENT_TYPE_WILDCARD_INDICATOR));
        if (aSelectedDocTypeID != null)
          if (aMatchingDocTypeConsumer.apply (aSelectedDocTypeID).isBreak ())
            return EContinue.BREAK;

        while (sRemainingCustomizationID.indexOf (PeppolIdentifierHelper.DOCUMENT_TYPE_WILDCARD_PART_SEPARATOR) >= 0)
        {
          // Remove last part (after last '@')
          sRemainingCustomizationID = sRemainingCustomizationID.substring (0,
                                                                           sRemainingCustomizationID.lastIndexOf (PeppolIdentifierHelper.DOCUMENT_TYPE_WILDCARD_PART_SEPARATOR));

          // Try more corse-grain part
          aSelectedDocTypeID = aFuncCheckExistance.apply (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD,
                                                          aFuncCustIDToDocTypeIDValue.apply (sRemainingCustomizationID +
                                                                                             PeppolIdentifierHelper.DOCUMENT_TYPE_WILDCARD_INDICATOR));

          if (aSelectedDocTypeID != null)
            if (aMatchingDocTypeConsumer.apply (aSelectedDocTypeID).isBreak ())
              return EContinue.BREAK;
        }
      }
      catch (final IllegalArgumentException ex)
      {
        LOGGER.error ("Failed to split document type ID into pieces: " + ex.getMessage ());
      }
      return EContinue.CONTINUE;
    };

    switch (m_eMode)
    {
      case BUSDOX_ONLY:
        aBusdoxMatch.get ();
        break;
      case WILDCARD_ONLY:
        aWildcardMatch.get ();
        break;
      case BUSDOX_THEN_WILDCARD:
        if (aBusdoxMatch.get ().isContinue ())
          aWildcardMatch.get ();
        break;
      case WILDCARD_THEN_BUSDOX:
        if (aWildcardMatch.get ().isContinue ())
          aBusdoxMatch.get ();
        break;
      default:
        throw new IllegalStateException ("Unsupported operation mode " + m_eMode);
    }
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("Mode", m_eMode).getToString ();
  }
}
