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
package com.helger.smpclient.peppol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.function.Function;

import org.junit.Test;

import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.mutable.MutableInt;
import com.helger.commons.state.EContinue;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.peppolid.peppol.doctype.PeppolDocumentTypeIdentifier;
import com.helger.smpclient.peppol.PeppolWildcardSelector.EMode;

/**
 * Test class for class {@link PeppolWildcardSelector}.
 *
 * @author Philip Helger
 * @since 9.2.0
 */
public final class PeppolWildcardSelectorTest
{
  @Test
  @Deprecated
  public void testEmptyAll ()
  {
    final MutableInt aCount = new MutableInt (0);
    final Function <? super IDocumentTypeIdentifier, EContinue> aMatcherCount = x -> {
      aCount.inc ();
      return EContinue.CONTINUE;
    };

    // test with no document types
    final ICommonsList <IDocumentTypeIdentifier> aSupportedDocTypes = new CommonsArrayList <> ();

    for (final PeppolWildcardSelector.EMode e : PeppolWildcardSelector.EMode.values ())
    {
      final PeppolWildcardSelector aSelector = new PeppolWildcardSelector (e);
      aSelector.forEachMatchingDocumentType (aSupportedDocTypes,
                                             "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@jp-1@jp-sub-1::2.1",
                                             aMatcherCount);
      assertEquals (0, aCount.intValue ());
    }
  }

  @Test
  @Deprecated
  public void testMatchingModesAll ()
  {
    final PeppolIdentifierFactory aIF = PeppolIdentifierFactory.INSTANCE;

    final ICommonsList <IDocumentTypeIdentifier> aMatches = new CommonsArrayList <> ();
    final Function <? super IDocumentTypeIdentifier, EContinue> aMatcherCount = x -> {
      aMatches.add (x);
      return EContinue.CONTINUE;
    };
    final Function <? super IDocumentTypeIdentifier, EContinue> aMatcherFirstOnly = x -> {
      aMatches.add (x);
      return EContinue.BREAK;
    };

    // test with no document types
    final ICommonsList <IDocumentTypeIdentifier> aSupportedDocTypes = new CommonsArrayList <> ();

    // busdox Match
    final PeppolDocumentTypeIdentifier aDT1 = aIF.createDocumentTypeIdentifier (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS,
                                                                                "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@jp-1@jp-sub-1::2.1");
    aSupportedDocTypes.add (aDT1);

    // wildcard Match
    final PeppolDocumentTypeIdentifier aDT2 = aIF.createDocumentTypeIdentifier (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD,
                                                                                "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@jp-1@jp-sub-1*::2.1");
    aSupportedDocTypes.add (aDT2);

    // wildcard Match
    final PeppolDocumentTypeIdentifier aDT3 = aIF.createDocumentTypeIdentifier (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD,
                                                                                "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@jp-1*::2.1");
    aSupportedDocTypes.add (aDT3);

    // wildcard Match
    final PeppolDocumentTypeIdentifier aDT4 = aIF.createDocumentTypeIdentifier (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD,
                                                                                "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1*::2.1");
    aSupportedDocTypes.add (aDT4);

    {
      final PeppolWildcardSelector aSelector = new PeppolWildcardSelector (EMode.BUSDOX_ONLY);
      aSelector.forEachMatchingDocumentType (aSupportedDocTypes,
                                             "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@jp-1@jp-sub-1::2.1",
                                             aMatcherCount);
      assertEquals (1, aMatches.size ());
      assertTrue (aMatches.get (0).hasSameContent (aDT1));

      // Reset
      aMatches.clear ();
    }

    {
      final PeppolWildcardSelector aSelector = new PeppolWildcardSelector (EMode.WILDCARD_ONLY);
      aSelector.forEachMatchingDocumentType (aSupportedDocTypes,
                                             "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@jp-1@jp-sub-1::2.1",
                                             aMatcherCount);
      assertEquals (3, aMatches.size ());
      assertTrue (aMatches.get (0).hasSameContent (aDT2));
      assertTrue (aMatches.get (1).hasSameContent (aDT3));
      assertTrue (aMatches.get (2).hasSameContent (aDT4));

      // Reset
      aMatches.clear ();
    }

    {
      final PeppolWildcardSelector aSelector = new PeppolWildcardSelector (EMode.WILDCARD_ONLY);
      // Customization ID with * will never match
      aSelector.forEachMatchingDocumentType (aSupportedDocTypes,
                                             "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@jp-1*::2.1",
                                             aMatcherCount);
      assertEquals (0, aMatches.size ());

      // Reset
      aMatches.clear ();
    }

    {
      final PeppolWildcardSelector aSelector = new PeppolWildcardSelector (EMode.BUSDOX_THEN_WILDCARD);
      aSelector.forEachMatchingDocumentType (aSupportedDocTypes,
                                             "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@jp-1@jp-sub-1::2.1",
                                             aMatcherCount);
      assertEquals (4, aMatches.size ());
      assertTrue (aMatches.get (0).hasSameContent (aDT1));
      assertTrue (aMatches.get (1).hasSameContent (aDT2));
      assertTrue (aMatches.get (2).hasSameContent (aDT3));
      assertTrue (aMatches.get (3).hasSameContent (aDT4));

      // Reset
      aMatches.clear ();

      // First match only
      aSelector.forEachMatchingDocumentType (aSupportedDocTypes,
                                             "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@jp-1@jp-sub-1::2.1",
                                             aMatcherFirstOnly);
      assertEquals (1, aMatches.size ());
      assertTrue (aMatches.get (0).hasSameContent (aDT1));

      // Reset
      aMatches.clear ();
    }

    {
      final PeppolWildcardSelector aSelector = new PeppolWildcardSelector (EMode.WILDCARD_THEN_BUSDOX);
      aSelector.forEachMatchingDocumentType (aSupportedDocTypes,
                                             "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@jp-1@jp-sub-1::2.1",
                                             aMatcherCount);
      assertEquals (4, aMatches.size ());
      assertTrue (aMatches.get (0).hasSameContent (aDT2));
      assertTrue (aMatches.get (1).hasSameContent (aDT3));
      assertTrue (aMatches.get (2).hasSameContent (aDT4));
      assertTrue (aMatches.get (3).hasSameContent (aDT1));

      // Reset
      aMatches.clear ();

      // First match only
      aSelector.forEachMatchingDocumentType (aSupportedDocTypes,
                                             "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:peppol:pint:billing-1@jp-1@jp-sub-1::2.1",
                                             aMatcherFirstOnly);
      assertEquals (1, aMatches.size ());
      assertTrue (aMatches.get (0).hasSameContent (aDT2));

      // Reset
      aMatches.clear ();
    }
  }
}
