/*
 * Copyright (C) 2025-2026 Philip Helger
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
package com.helger.hredelivery.commons;

import com.helger.annotation.concurrent.Immutable;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;

/**
 * Specific predefined IDs for HR eRacun stuff
 *
 * @author Philip Helger
 * @since 12.3.4
 */
@Immutable
public final class CHREDeliveryID
{
  /** HR eRacun Invoice Extension 2025 1.0 */
  public static final IDocumentTypeIdentifier DOC_TYPE_ID_HR_ERACUN_INVOICE_EXT_2025_1_0 = PeppolIdentifierFactory.INSTANCE.createDocumentTypeIdentifier ("busdox-docid-qns",
                                                                                                                                                          "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:mfin.gov.hr:cius-2025:1.0#conformant#urn:mfin.gov.hr:ext-2025:1.0::2.1");
  /** HR eRacun CreditNote Extension 2025 1.0 */
  public static final IDocumentTypeIdentifier DOC_TYPE_ID_HR_ERACUN_CREDIT_NOTE_EXT_2025_1_0 = PeppolIdentifierFactory.INSTANCE.createDocumentTypeIdentifier ("busdox-docid-qns",
                                                                                                                                                              "urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:cen.eu:en16931:2017#compliant#urn:mfin.gov.hr:cius-2025:1.0#conformant#urn:mfin.gov.hr:ext-2025:1.0::2.1");

  public static final IProcessIdentifier PROCESS_ID_HR_ERACUN = PeppolIdentifierFactory.INSTANCE.createProcessIdentifierWithDefaultScheme ("urn:fdc:eracun.hr:poacc:en16931:any");

  private CHREDeliveryID ()
  {}
}
