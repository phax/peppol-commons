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
    assertFalse (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid (null));
    assertFalse (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid (""));

    assertTrue (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid ("9908:976098897"));
    assertTrue (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid ("9908:976098897 "));
    assertTrue (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid ("990:976098897"));
    assertTrue (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid ("990976098897"));
    assertTrue (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid ("9909:976098896"));
    assertTrue (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid ("9908:976098896"));
    assertTrue (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid ("9956:DE:EPROC:BMIEVG:BeschA"));
    assertTrue (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid ("9906:02419170044_01"));

    assertTrue (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid (VALUE_MAX_LENGTH));
    assertFalse (PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierValueValid (VALUE_MAX_LENGTH_PLUS_1));
  }

  @Test
  public void testIsValidProcessIdentifierValue ()
  {
    assertFalse (PeppolIdentifierFactory.INSTANCE.isProcessIdentifierValueValid (null));
    assertFalse (PeppolIdentifierFactory.INSTANCE.isProcessIdentifierValueValid (""));

    assertTrue (PeppolIdentifierFactory.INSTANCE.isProcessIdentifierValueValid ("proc1"));
    assertTrue (PeppolIdentifierFactory.INSTANCE.isProcessIdentifierValueValid ("proc2"));
    assertFalse (PeppolIdentifierFactory.INSTANCE.isProcessIdentifierValueValid ("proc2 "));

    assertTrue (PeppolIdentifierFactory.INSTANCE.isProcessIdentifierValueValid (StringHelper.getRepeated ('a',
                                                                                                          PeppolIdentifierHelper.MAX_PROCESS_VALUE_LENGTH)));
    assertFalse (PeppolIdentifierFactory.INSTANCE.isProcessIdentifierValueValid (StringHelper.getRepeated ('a',
                                                                                                           PeppolIdentifierHelper.MAX_PROCESS_VALUE_LENGTH +
                                                                                                                1)));
  }

  @Test
  public void testIsValidDocumentTypeIdentifierValue ()
  {
    assertFalse (PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (null));
    assertFalse (PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (""));

    assertFalse (PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid ("invoice"));
    assertFalse (PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid ("order "));

    assertFalse (PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (StringHelper.getRepeated ('a',
                                                                                                                PeppolIdentifierHelper.MAX_DOCUMENT_TYPE_VALUE_LENGTH)));
    assertFalse (PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid (StringHelper.getRepeated ('a',
                                                                                                                PeppolIdentifierHelper.MAX_DOCUMENT_TYPE_VALUE_LENGTH +
                                                                                                                     1)));
    assertTrue (PeppolIdentifierFactory.INSTANCE.isDocumentTypeIdentifierValueValid ("urn:rootnamespace::localelement##customizationid::version"));
  }
}
