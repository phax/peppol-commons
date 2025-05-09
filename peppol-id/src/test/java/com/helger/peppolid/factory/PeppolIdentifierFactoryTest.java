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
package com.helger.peppolid.factory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.string.StringHelper;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;

/**
 * Test class for class {@link PeppolIdentifierFactory}
 *
 * @author Philip Helger
 */
public final class PeppolIdentifierFactoryTest
{
  private static final String [] PARTICIPANT_SCHEME_VALID = { "busdox-actorid-upis",
                                                              "BUSDOX-ACTORID-UPIS",
                                                              PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME,
                                                              "any-actorid-any",
                                                              "any-ACTORID-any" };
  private static final String [] PARTIFCIPANT_SCHEME_INVALID = { null,
                                                                 "",
                                                                 "busdox_actorid_upis",
                                                                 "busdox-upis",
                                                                 "-actorid-upis",
                                                                 "actorid-upis",
                                                                 "busdox-actorid-",
                                                                 "busdox-actorid",
                                                                 "any-domain_actorid_any-type",
                                                                 "any-nonactoid-anybutmuchtoooooooooooooooooooooooolong" };
  private static final String VALUE_MAX_LENGTH = StringHelper.getRepeated ('a',
                                                                           PeppolIdentifierHelper.MAX_PARTICIPANT_VALUE_LENGTH);
  private static final String VALUE_MAX_LENGTH_PLUS_1 = VALUE_MAX_LENGTH + 'a';

  @Test
  public void testIsValidParticipantIdentifierScheme ()
  {
    // valid
    for (final String sScheme : PARTICIPANT_SCHEME_VALID)
      assertTrue (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierSchemeValid (sScheme));

    // invalid
    for (final String sScheme : PARTIFCIPANT_SCHEME_INVALID)
      assertFalse (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierSchemeValid (sScheme));
  }

  @Test
  public void testIsValidParticipantIdentifierValue ()
  {
    assertFalse (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid (PeppolIdentifierHelper.PARTICIPANT_SCHEME_ISO6523_ACTORID_UPIS,
                                                                                     null));
    assertFalse (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid (PeppolIdentifierHelper.PARTICIPANT_SCHEME_ISO6523_ACTORID_UPIS,
                                                                                     ""));

    assertTrue (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid (PeppolIdentifierHelper.PARTICIPANT_SCHEME_ISO6523_ACTORID_UPIS,
                                                                                    "9908:976098897"));
    assertTrue (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid (PeppolIdentifierHelper.PARTICIPANT_SCHEME_ISO6523_ACTORID_UPIS,
                                                                                    "9908:976098897 "));
    assertFalse (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid (PeppolIdentifierHelper.PARTICIPANT_SCHEME_ISO6523_ACTORID_UPIS,
                                                                                     "990:976098897"));
    assertFalse (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid (PeppolIdentifierHelper.PARTICIPANT_SCHEME_ISO6523_ACTORID_UPIS,
                                                                                     "990976098897"));
    assertTrue (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid (PeppolIdentifierHelper.PARTICIPANT_SCHEME_ISO6523_ACTORID_UPIS,
                                                                                    "9909:976098896"));
    assertTrue (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid (PeppolIdentifierHelper.PARTICIPANT_SCHEME_ISO6523_ACTORID_UPIS,
                                                                                    "9908:976098896"));
    assertTrue (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid (PeppolIdentifierHelper.PARTICIPANT_SCHEME_ISO6523_ACTORID_UPIS,
                                                                                    "9956:DE:EPROC:BMIEVG:BeschA"));
    assertTrue (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid (PeppolIdentifierHelper.PARTICIPANT_SCHEME_ISO6523_ACTORID_UPIS,
                                                                                    "9906:02419170044_01"));

    assertFalse (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid (PeppolIdentifierHelper.PARTICIPANT_SCHEME_ISO6523_ACTORID_UPIS,
                                                                                     VALUE_MAX_LENGTH));
    assertFalse (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid (PeppolIdentifierHelper.PARTICIPANT_SCHEME_ISO6523_ACTORID_UPIS,
                                                                                     VALUE_MAX_LENGTH_PLUS_1));
  }

  @Test
  public void testIsValidProcessIdentifierValue ()
  {
    assertFalse (PeppolIdentifierFactory.INSTANCE.isProcessIdentifierValueValid (PeppolIdentifierHelper.PROCESS_SCHEME_CENBII_PROCID_UBL,
                                                                                 null));
    assertFalse (PeppolIdentifierFactory.INSTANCE.isProcessIdentifierValueValid (PeppolIdentifierHelper.PROCESS_SCHEME_CENBII_PROCID_UBL,
                                                                                 ""));

    assertTrue (PeppolIdentifierFactory.INSTANCE.isProcessIdentifierValueValid (PeppolIdentifierHelper.PROCESS_SCHEME_CENBII_PROCID_UBL,
                                                                                "proc1"));
    assertTrue (PeppolIdentifierFactory.INSTANCE.isProcessIdentifierValueValid (PeppolIdentifierHelper.PROCESS_SCHEME_CENBII_PROCID_UBL,
                                                                                "proc2"));
    assertFalse (PeppolIdentifierFactory.INSTANCE.isProcessIdentifierValueValid (PeppolIdentifierHelper.PROCESS_SCHEME_CENBII_PROCID_UBL,
                                                                                 "proc2 "));

    assertTrue (PeppolIdentifierFactory.INSTANCE.isProcessIdentifierValueValid (PeppolIdentifierHelper.PROCESS_SCHEME_CENBII_PROCID_UBL,
                                                                                StringHelper.getRepeated ('a',
                                                                                                          PeppolIdentifierHelper.MAX_PROCESS_VALUE_LENGTH)));
    assertFalse (PeppolIdentifierFactory.INSTANCE.isProcessIdentifierValueValid (PeppolIdentifierHelper.PROCESS_SCHEME_CENBII_PROCID_UBL,
                                                                                 StringHelper.getRepeated ('a',
                                                                                                           PeppolIdentifierHelper.MAX_PROCESS_VALUE_LENGTH +
                                                                                                                1)));
  }

  @Test
  public void testIsValidDocumentTypeIdentifierValue ()
  {
    assertFalse (PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS,
                                                                                      null));
    assertFalse (PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS,
                                                                                      ""));

    assertFalse (PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS,
                                                                                      "invoice"));
    assertFalse (PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD,
                                                                                      "invoice"));
    assertFalse (PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid ("bla", "invoice"));
    assertFalse (PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (null, "invoice"));
    assertFalse (PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS,
                                                                                      "order "));

    assertFalse (PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS,
                                                                                      StringHelper.getRepeated ('a',
                                                                                                                PeppolIdentifierHelper.MAX_DOCUMENT_TYPE_VALUE_LENGTH)));
    assertFalse (PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS,
                                                                                      StringHelper.getRepeated ('a',
                                                                                                                PeppolIdentifierHelper.MAX_DOCUMENT_TYPE_VALUE_LENGTH +
                                                                                                                     1)));
    assertTrue (PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS,
                                                                                     "urn:rootnamespace::localelement##customizationid::version"));

    // * only valid for peppol-doctype-wildcard
    assertFalse (PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS,
                                                                                      "urn:rootnamespace::localelement##customizationid*::version"));
    assertTrue (PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD,
                                                                                     "urn:rootnamespace::localelement##customizationid*::version"));
  }

  @Test
  public void testIsValidDocumentTypeIdentifierValueLax ()
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
