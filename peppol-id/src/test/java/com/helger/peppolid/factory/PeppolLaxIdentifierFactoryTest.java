/*
 * Copyright (C) 2015-2026 Philip Helger
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
package com.helger.peppolid.factory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.base.string.StringHelper;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;

/**
 * Test class for class {@link PeppolLaxIdentifierFactory}
 *
 * @author Philip Helger
 */
public final class PeppolLaxIdentifierFactoryTest
{
  private static final String VALUE_MAX_LENGTH_NO_SCHEME = StringHelper.getRepeated ('a',
                                                                                     PeppolIdentifierHelper.MAX_PARTICIPANT_VALUE_LENGTH -
                                                                                          5);
  private static final String VALUE_MAX_LENGTH = StringHelper.getRepeated ('a',
                                                                           PeppolIdentifierHelper.MAX_PARTICIPANT_VALUE_LENGTH);
  private static final String VALUE_MAX_LENGTH_PLUS_1 = VALUE_MAX_LENGTH + 'a';

  @Test
  public void testIsValidParticipantIdentifierValue ()
  {
    for (final String sBad : new String [] { null,
                                             "",
                                             VALUE_MAX_LENGTH,
                                             VALUE_MAX_LENGTH_PLUS_1,
                                             "990:976098897",
                                             "990976098897",
                                             "0123:" + VALUE_MAX_LENGTH })
      assertFalse ("'" + sBad + "' should be bad",
                   PeppolLaxIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid (PeppolIdentifierHelper.PARTICIPANT_SCHEME_ISO6523_ACTORID_UPIS,
                                                                                          sBad));

    for (final String sGood : new String [] { "9908:976098897",
                                              "9908:976098897 ",
                                              "9909:976098896",
                                              "9908:976098896",
                                              "9906:02419170044_01",
                                              "9930:DE211155632",
                                              "0204:991-11384-46",
                                              "0123:" + VALUE_MAX_LENGTH_NO_SCHEME,
                                              "nso0:dummy",
                                              "nso0:dummy@somewhere.org",
                                              "9956:DE:EPROC:BMIEVG:BeschA" })
      assertTrue ("'" + sGood + "' should be good",
                  PeppolLaxIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid (PeppolIdentifierHelper.PARTICIPANT_SCHEME_ISO6523_ACTORID_UPIS,
                                                                                         sGood));
  }

  @Test
  public void testIsValidProcessIdentifierValue ()
  {
    assertFalse (PeppolLaxIdentifierFactory.INSTANCE.isProcessIdentifierValueValid (PeppolIdentifierHelper.PROCESS_SCHEME_CENBII_PROCID_UBL,
                                                                                    null));
    assertFalse (PeppolLaxIdentifierFactory.INSTANCE.isProcessIdentifierValueValid (PeppolIdentifierHelper.PROCESS_SCHEME_CENBII_PROCID_UBL,
                                                                                    ""));

    assertTrue (PeppolLaxIdentifierFactory.INSTANCE.isProcessIdentifierValueValid (PeppolIdentifierHelper.PROCESS_SCHEME_CENBII_PROCID_UBL,
                                                                                   "proc1"));
    assertTrue (PeppolLaxIdentifierFactory.INSTANCE.isProcessIdentifierValueValid (PeppolIdentifierHelper.PROCESS_SCHEME_CENBII_PROCID_UBL,
                                                                                   "proc2"));
    assertFalse (PeppolLaxIdentifierFactory.INSTANCE.isProcessIdentifierValueValid (PeppolIdentifierHelper.PROCESS_SCHEME_CENBII_PROCID_UBL,
                                                                                    "proc2 "));

    assertTrue (PeppolLaxIdentifierFactory.INSTANCE.isProcessIdentifierValueValid (PeppolIdentifierHelper.PROCESS_SCHEME_CENBII_PROCID_UBL,
                                                                                   StringHelper.getRepeated ('a',
                                                                                                             PeppolIdentifierHelper.MAX_PROCESS_VALUE_LENGTH)));
    assertFalse (PeppolLaxIdentifierFactory.INSTANCE.isProcessIdentifierValueValid (PeppolIdentifierHelper.PROCESS_SCHEME_CENBII_PROCID_UBL,
                                                                                    StringHelper.getRepeated ('a',
                                                                                                              PeppolIdentifierHelper.MAX_PROCESS_VALUE_LENGTH +
                                                                                                                   1)));
  }

  @Test
  public void testIsValidDocumentTypeIdentifierValue ()
  {
    assertFalse (PeppolLaxIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS,
                                                                                         null));
    assertFalse (PeppolLaxIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS,
                                                                                         ""));

    // Difference to the regular one
    assertTrue (PeppolLaxIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS,
                                                                                        "invoice"));
    // Difference to the regular one
    assertTrue (PeppolLaxIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD,
                                                                                        "invoice"));
    // Difference to the regular one
    assertTrue (PeppolLaxIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid ("bla", "invoice"));
    // Difference to the regular one
    assertTrue (PeppolLaxIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (null, "invoice"));
    assertTrue (PeppolLaxIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS,
                                                                                        "order "));

    // Difference to the regular one
    assertTrue (PeppolLaxIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS,
                                                                                        StringHelper.getRepeated ('a',
                                                                                                                  PeppolIdentifierHelper.MAX_DOCUMENT_TYPE_VALUE_LENGTH)));
    assertFalse (PeppolLaxIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS,
                                                                                         StringHelper.getRepeated ('a',
                                                                                                                   PeppolIdentifierHelper.MAX_DOCUMENT_TYPE_VALUE_LENGTH +
                                                                                                                        1)));
    assertTrue (PeppolLaxIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS,
                                                                                        "urn:rootnamespace::localelement##customizationid::version"));

    // * only valid for peppol-doctype-wildcard
    // Difference to the regular one
    assertTrue (PeppolLaxIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS,
                                                                                        "urn:rootnamespace::localelement##customizationid*::version"));
    assertTrue (PeppolLaxIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD,
                                                                                        "urn:rootnamespace::localelement##customizationid*::version"));
  }
}
