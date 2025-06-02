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
package com.helger.smpclient.peppol;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.state.ESuccess;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.peppolid.CIdentifier;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.peppolid.peppol.Pfuoi430;
import com.helger.peppolid.peppol.doctype.IPeppolGenericDocumentTypeIdentifierParts;
import com.helger.peppolid.peppol.doctype.PeppolGenericDocumentTypeIdentifierParts;

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
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolWildcardSelector.class);

  private PeppolWildcardSelector ()
  {}

  /**
   * Helper method to find the best match wildcard document type identifier for
   * PFUOI 4.3.0. This method only work for peppol-doctype-wildcard scheme.
   *
   * @param aBaseDocTypes
   *        The list of document types to filter. Usually this list was obtained
   *        from an SMP query "get all receiving capabilities of participant".
   *        May not be <code>null</code>, but maybe empty.
   * @param aSearchDocTypeValue
   *        The document type identifier to search. It may or may not contain
   *        the Wildcard indicator.
   * @param aMatchingDocTypeConsumer
   *        The consumer to be invoked for the first match only. May not be
   *        <code>null</code>.
   * @return Non-<code>null</code>.
   */
  @Nonnull
  @Pfuoi430
  public static ESuccess findPeppolDoctypeWildcardMatch (@Nonnull final ICommonsList <? extends IDocumentTypeIdentifier> aBaseDocTypes,
                                                         @Nonnull @Nonempty final IDocumentTypeIdentifier aSearchDocTypeValue,
                                                         @Nonnull final Consumer <? super IDocumentTypeIdentifier> aMatchingDocTypeConsumer)
  {
    ValueEnforcer.notNull (aBaseDocTypes, "BaseDocTypes");
    ValueEnforcer.notNull (aSearchDocTypeValue, "SearchDocTypeValue");
    ValueEnforcer.notNull (aSearchDocTypeValue.getValue (), "SearchDocTypeValue.Value");
    ValueEnforcer.notNull (aMatchingDocTypeConsumer, "MatchingDocTypeConsumer");

    final BiFunction <String, String, IDocumentTypeIdentifier> aFuncCheckExistance = (sScheme, sValue) -> {
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Checking if document type ID '" +
                      CIdentifier.getURIEncoded (sScheme, sValue) +
                      "' is contained");
      if (aBaseDocTypes.containsAny (x -> x.hasScheme (sScheme) && x.hasValue (sValue)))
        return PeppolIdentifierFactory.INSTANCE.createDocumentTypeIdentifier (sScheme, sValue);
      return null;
    };

    // Split the document type identifier value into pieces (throws
    // IllegalArgumentException)
    final IPeppolGenericDocumentTypeIdentifierParts aParts = PeppolGenericDocumentTypeIdentifierParts.extractFromString (aSearchDocTypeValue.getValue ());

    // Just change the customization ID of the parts
    final Function <String, String> aFuncCustIDToDocTypeIDValue = sCustomizationID -> new PeppolGenericDocumentTypeIdentifierParts (aParts.getSyntaxSpecificID (),
                                                                                                                                    sCustomizationID,
                                                                                                                                    aParts.getVersion ()).getAsDocumentTypeIdentifierValue ();

    // Find the Customization ID to start (without an optional trailing "*")
    String sRemainingCustomizationID = StringHelper.trimEnd (aParts.getCustomizationID (),
                                                             PeppolIdentifierHelper.DOCUMENT_TYPE_WILDCARD_INDICATOR);

    if (sRemainingCustomizationID.indexOf (PeppolIdentifierHelper.DOCUMENT_TYPE_WILDCARD_INDICATOR) >= 0)
    {
      // If a Customization still contains a "*" it is invalid - no match
      LOGGER.error ("Customization ID contains a forbidden wildcard indicator: '" + sRemainingCustomizationID + "'");
      return ESuccess.FAILURE;
    }

    // Search wildcard exact match (make sure no "*" is contained in the
    // CustomizationID)
    IDocumentTypeIdentifier aSelectedDocTypeID = aFuncCheckExistance.apply (aSearchDocTypeValue.getScheme (),
                                                                            aFuncCustIDToDocTypeIDValue.apply (sRemainingCustomizationID));
    if (aSelectedDocTypeID != null)
    {
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Found a wildcard exact match: '" + aSelectedDocTypeID.getURIEncoded () + "'");
      aMatchingDocTypeConsumer.accept (aSelectedDocTypeID);
      return ESuccess.SUCCESS;
    }

    // Search wildcard best match
    aSelectedDocTypeID = aFuncCheckExistance.apply (aSearchDocTypeValue.getScheme (),
                                                    aFuncCustIDToDocTypeIDValue.apply (sRemainingCustomizationID +
                                                                                       PeppolIdentifierHelper.DOCUMENT_TYPE_WILDCARD_INDICATOR));
    if (aSelectedDocTypeID != null)
    {
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Found a wildcard best match: '" + aSelectedDocTypeID.getURIEncoded () + "'");
      aMatchingDocTypeConsumer.accept (aSelectedDocTypeID);
      return ESuccess.SUCCESS;
    }

    while (sRemainingCustomizationID.indexOf (PeppolIdentifierHelper.DOCUMENT_TYPE_WILDCARD_PART_SEPARATOR) >= 0)
    {
      // Remove last part (after last '@')
      sRemainingCustomizationID = sRemainingCustomizationID.substring (0,
                                                                       sRemainingCustomizationID.lastIndexOf (PeppolIdentifierHelper.DOCUMENT_TYPE_WILDCARD_PART_SEPARATOR));

      // Try more corse-grain part
      aSelectedDocTypeID = aFuncCheckExistance.apply (aSearchDocTypeValue.getScheme (),
                                                      aFuncCustIDToDocTypeIDValue.apply (sRemainingCustomizationID +
                                                                                         PeppolIdentifierHelper.DOCUMENT_TYPE_WILDCARD_INDICATOR));

      if (aSelectedDocTypeID != null)
      {
        if (LOGGER.isDebugEnabled ())
          LOGGER.debug ("Found a wildcard best match: '" + aSelectedDocTypeID.getURIEncoded () + "'");
        aMatchingDocTypeConsumer.accept (aSelectedDocTypeID);
        return ESuccess.SUCCESS;
      }
    }
    return ESuccess.FAILURE;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).getToString ();
  }
}
