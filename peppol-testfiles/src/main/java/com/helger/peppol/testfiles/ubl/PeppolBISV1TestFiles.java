/**
 * Copyright (C) 2015-2018 Philip Helger
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
package com.helger.peppol.testfiles.ubl;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.ArrayHelper;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.peppol.testfiles.ErrorDefinition;
import com.helger.peppol.testfiles.TestDocument;
import com.helger.peppol.testfiles.TestResource;

/**
 * Contains the utility methods to retrieve the test files for a certain
 * document type.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Immutable
public final class PeppolBISV1TestFiles
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (PeppolBISV1TestFiles.class);
  private static final String [] CALLFORTENDERS_SUCCESS = new String [] { "Catalogue pre award_Call for Tender_RDO MEPA_BIS 12a.xml" };
  private static final String [] CATALOGUES_SUCCESS = new String [] { "Consip_Catalogo_UBL.xml" };
  private static final String [] CREDITNOTES_SUCCESS = new String [] { "BII05-TRDM014-example-at.xml",
                                                                       "BII05-TRDM014-example-is.xml",
                                                                       "creditnote-with-all-elements-new-creditorid.xml",
                                                                       "creditnote-with-all-elements.xml" };
  private static final String [] CREDITNOTES_AT_SUCCESS = new String [] { "atgov_BIS5aCreditNote.xml",
                                                                          "atgov-t14-BIS5A-valid.xml",
                                                                          "atgov-t14-BIS5A-with-all-elements.xml" };
  private static final String [] INVOICES_SUCCESS = new String [] { "BII04 minimal invoice example 03.xml",
                                                                    "BII04 minimal invoice wo addr id.xml",
                                                                    "BII04 minimal VAT invoice example 02.xml",
                                                                    "BII04 XML example full core data 01.xml",
                                                                    "BII04 XML example full core data 02.xml",
                                                                    "invoice-it-c98fe7f7-8b46-4972-b61b-3b8824f16658.xml",
                                                                    "invoice-it-uuid8a69941e-52ae-4cbb-b284-a3a78fb89d07.xml",
                                                                    "PEP BII04 minimal invoice example 03.xml",
                                                                    "PEP BII04 minimal Reverce Charge VAT invoice example 01.xml",
                                                                    "PEP BII04 minimal Reverce Charge VAT invoice example no line 01.xml",
                                                                    "PEP BII04 minimal VAT invoice example 02.xml",
                                                                    "SubmitInvoice.008660-AA.b1478257-5bd1-4756-bd20-3262afb22923.xml",
                                                                    "TC10.3.TS1.xml",
                                                                    "TC10.4.TS1.xml",
                                                                    "TC10.15.TS1.xml",
                                                                    "test-invoice.xml" };
  private static final String [] INVOICES_AT_SUCCESS = new String [] { "atgov_BIS4aInvoice.xml",
                                                                       "atgov_BIS5aInvoice.xml",
                                                                       "atgov-t10-BIS4A-valid.xml",
                                                                       "atgov-t10-BIS5A-valid.xml",
                                                                       "atgov-ubl-42-8.xml" };
  private static final String [] ORDERS_SUCCESS = new String [] { "ADVORD_03_03_00_Order_v2p2.xml",
                                                                  "BII03 Order example 01.xml",
                                                                  "PEPPOL BIS-3a-FULL.xml",
                                                                  "PEPPOL BIS-3a-Small.xml",
                                                                  "TC01.0.TS1.xml",
                                                                  "test-order.xml",
                                                                  "UBL-Order-2.0-Example-International.xml",
                                                                  "UBL-Order-2.0-Example.xml" };
  private static final String [] ORDERRESPONSES_SUCCESS = new String [] { "CENBII-AcceptOrder-maximal.xml",
                                                                          "CENBII-RejectOrder-maximal.xml" };
  private static final String [] TENDER_SUCCESS = new String [] { "Catalogo preaward_Tender_Risposta con offerta ad una RDO MEPA_BIS 12a.xml" };
  private static final String [] TENDERINGCATALOGUES_SUCCESS = new String [] { "Tender-Sample.xml" };

  public static final TestDocument [] CREDITNOTES_AT_ERROR = new TestDocument [] { new TestDocument ("atgov-t14-fail-r001.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atgov-t14-fail-r002.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atgov-t14-fail-r003.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atgov-t14-fail-r004.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atgov-t14-fail-r005.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atgov-t14-fail-r007a.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atgov-t14-fail-r007b.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atgov-t14-fail-r008.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atgov-t14-fail-r009.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atgov-t14-fail-r010a.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atgov-t14-fail-r010b.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atgov-t14-fail-r011.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atgov-t14-fail-r012.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atgov-t14-fail-r013.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atgov-t14-fail-r014.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atgov-t14-fail-r015.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atgov-t14-fail-r016.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atnat-t14-fail-r001.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atnat-t14-fail-r002.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atnat-t14-fail-r003.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atnat-t14-fail-r004.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atnat-t14-fail-r005.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")),
                                                                                   new TestDocument ("atnat-t14-fail-r006.xml",
                                                                                                     ErrorDefinition.createWarning ("MOCK")) };

  public static final TestDocument [] INVOICES_ERROR = new TestDocument [] { new TestDocument ("ERR-2 BII04 minimal invoice example 02.xml",
                                                                                               ErrorDefinition.createWarning ("BIIRULE-T10-R002"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003"),
                                                                                               ErrorDefinition.createWarning ("BIICORE-T10-R392")),
                                                                             new TestDocument ("ERR-3 BII04 minimal VAT invoice example 02.xml",
                                                                                               ErrorDefinition.createWarning ("BIIRULE-T10-R003"),
                                                                                               ErrorDefinition.createError ("EUGEN-T10-R008"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R009"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                             new TestDocument ("ERR-4 BII04 minimal invoice example 02.xml",
                                                                                               ErrorDefinition.createWarning ("BIIRULE-T10-R004"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R002"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                             new TestDocument ("ERR-5 BII04 minimal VAT invoice example 02.xml",
                                                                                               ErrorDefinition.createWarning ("BIIRULE-T10-R005"),
                                                                                               ErrorDefinition.createError ("EUGEN-T10-R008"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R009")),
                                                                             new TestDocument ("ERR-9 BII04 minimal VAT invoice example 02.xml",
                                                                                               ErrorDefinition.createError ("BIIRULE-T10-R009"),
                                                                                               ErrorDefinition.createError ("EUGEN-T10-R008"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R009")),
                                                                             new TestDocument ("ERR-10 BII04 minimal VAT invoice example 02.xml",
                                                                                               ErrorDefinition.createError ("BIIRULE-T10-R010"),
                                                                                               ErrorDefinition.createError ("EUGEN-T10-R008"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R009"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                             new TestDocument ("ERR-11 BII04 minimal invoice example 02.xml",
                                                                                               ErrorDefinition.createError ("BIIRULE-T10-R011"),
                                                                                               ErrorDefinition.createError ("BIIRULE-T10-R012"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                             new TestDocument ("ERR-13 BII04 minimal invoice example 02.xml",
                                                                                               ErrorDefinition.createError ("BIIRULE-T10-R013"),
                                                                                               ErrorDefinition.createError ("BIIRULE-T10-R017"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                             new TestDocument ("ERR-18 BII04 minimal invoice example 02.xml",
                                                                                               ErrorDefinition.createError ("BIIRULE-T10-R018"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                             new TestDocument ("ERR-19 BII04 minimal invoice example 02.xml",
                                                                                               ErrorDefinition.createWarning ("BIIRULE-T10-R019"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                             new TestDocument ("ERR-20 BII04 reverse charge invoice example 01.xml",
                                                                                               ErrorDefinition.createError ("EUGEN-T10-R015"),
                                                                                               ErrorDefinition.createError ("EUGEN-T10-R016"),
                                                                                               ErrorDefinition.createError ("EUGEN-T10-R017"),
                                                                                               ErrorDefinition.createWarning ("BIIRULE-T10-R003"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R009")),
                                                                             new TestDocument ("TC10.1.TS1.xml",
                                                                                               ErrorDefinition.createError ("BIIRULE-T10-R052"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                             new TestDocument ("TC10.2.TS1.xml",
                                                                                               ErrorDefinition.createError ("EUGEN-T10-R008"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                             new TestDocument ("TC10.5.TS1.xml",
                                                                                               ErrorDefinition.createError ("BIIRULE-T10-R011"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                             new TestDocument ("TC10.6.TS1.xml",
                                                                                               ErrorDefinition.createError ("BIIRULE-T10-R013"),
                                                                                               ErrorDefinition.createError ("BIIRULE-T10-R017"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                             new TestDocument ("TC10.7.TS1.xml",
                                                                                               ErrorDefinition.createError ("BIIRULE-T10-R009"),
                                                                                               ErrorDefinition.createError ("EUGEN-T10-R008"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                             new TestDocument ("TC10.8.TS1.xml",
                                                                                               ErrorDefinition.createError ("BIIRULE-T10-R010"),
                                                                                               ErrorDefinition.createError ("EUGEN-T10-R008"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                             new TestDocument ("TC10.9.TS1.xml",
                                                                                               ErrorDefinition.createWarning ("BIIRULE-T10-R004"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R002"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                             new TestDocument ("TC10.10.TS1.xml",
                                                                                               ErrorDefinition.createWarning ("BIICORE-T10-R392"),
                                                                                               ErrorDefinition.createWarning ("BIIRULE-T10-R002"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                             new TestDocument ("TC10.11.TS1.xml",
                                                                                               ErrorDefinition.createError ("BIIRULE-T10-R018"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                             new TestDocument ("TC10.12.TS1.xml",
                                                                                               ErrorDefinition.createWarning ("BIIRULE-T10-R019"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                             new TestDocument ("TC10.13.TS1.xml",
                                                                                               ErrorDefinition.createError ("EUGEN-T10-R008"),
                                                                                               ErrorDefinition.createWarning ("BIIRULE-T10-R005"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                             new TestDocument ("TC10.14.TS1.xml",
                                                                                               ErrorDefinition.createError ("EUGEN-T10-R008"),
                                                                                               ErrorDefinition.createWarning ("BIIRULE-T10-R003"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R001"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                             new TestDocument ("TC10.16.TS1.xml"),
                                                                             new TestDocument ("TC10.17.TS1.xml",
                                                                                               ErrorDefinition.createError ("EUGEN-T10-R015"),
                                                                                               ErrorDefinition.createError ("EUGEN-T10-R016"),
                                                                                               ErrorDefinition.createError ("EUGEN-T10-R017"),
                                                                                               ErrorDefinition.createWarning ("BIIRULE-T10-R003"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R009")),
                                                                             new TestDocument ("TC10.18.TS1.xml",
                                                                                               ErrorDefinition.createError ("PCL-010-007"),
                                                                                               ErrorDefinition.createError ("PCL-010-008"),
                                                                                               ErrorDefinition.createWarning ("BIICORE-T10-R114"),
                                                                                               ErrorDefinition.createWarning ("BIICORE-T10-R185"),
                                                                                               ErrorDefinition.createWarning ("EUGEN-T10-R023")) };
  private static final TestDocument [] INVOICES_AT_ERROR = new TestDocument [] { new TestDocument ("atnat-t10-fail-r001.xml",
                                                                                                   ErrorDefinition.createError ("ATNAT-T10-R001"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                                 new TestDocument ("atnat-t10-fail-r002a.xml",
                                                                                                   ErrorDefinition.createError ("ATNAT-T10-R002"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323")),
                                                                                 new TestDocument ("atnat-t10-fail-r002b.xml",
                                                                                                   ErrorDefinition.createError ("ATNAT-T10-R002"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323")),
                                                                                 new TestDocument ("atnat-t10-fail-r003.xml",
                                                                                                   ErrorDefinition.createError ("ATNAT-T10-R003"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                                 new TestDocument ("atnat-t10-fail-r004.xml",
                                                                                                   ErrorDefinition.createError ("ATNAT-T10-R004"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createError ("EUGEN-T10-R015")),
                                                                                 new TestDocument ("atnat-t10-fail-r005.xml",
                                                                                                   ErrorDefinition.createError ("ATNAT-T10-R005"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createWarning ("EUGEN-T10-R003"),
                                                                                                   ErrorDefinition.createError ("EUGEN-T10-R007")),
                                                                                 new TestDocument ("atnat-t10-fail-r006.xml",
                                                                                                   ErrorDefinition.createError ("ATNAT-T10-R006"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createError ("EUGEN-T10-R018")),
                                                                                 new TestDocument ("atnat-t10-fail-r007.xml",
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                                 new TestDocument ("atnat-t10-fail-r007x.xml",
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createError ("EUGEN-T10-R016")),
                                                                                 new TestDocument ("atgov-t10-fail-r001.xml",
                                                                                                   ErrorDefinition.createError ("ATGOV-T10-R001"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                                 new TestDocument ("atgov-t10-fail-r002.xml",
                                                                                                   ErrorDefinition.createError ("ATGOV-T10-R002"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323")),
                                                                                 new TestDocument ("atgov-t10-fail-r003.xml",
                                                                                                   ErrorDefinition.createError ("ATGOV-T10-R003"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                                 new TestDocument ("atgov-t10-fail-r004.xml",
                                                                                                   ErrorDefinition.createWarning ("ATGOV-T10-R004"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                                 new TestDocument ("atgov-t10-fail-r005.xml",
                                                                                                   ErrorDefinition.createWarning ("ATGOV-T10-R005"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                                 new TestDocument ("atgov-t10-fail-r007a.xml",
                                                                                                   ErrorDefinition.createError ("ATGOV-T10-R007"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                                 new TestDocument ("atgov-t10-fail-r007b.xml",
                                                                                                   ErrorDefinition.createError ("ATGOV-T10-R007"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createWarning ("EUGEN-T10-R003"),
                                                                                                   ErrorDefinition.createWarning ("EUGEN-T10-R004"),
                                                                                                   ErrorDefinition.createWarning ("PCL-010-002")),
                                                                                 new TestDocument ("atgov-t10-fail-r008.xml",
                                                                                                   ErrorDefinition.createError ("ATGOV-T10-R008"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                                 new TestDocument ("atgov-t10-fail-r009.xml",
                                                                                                   ErrorDefinition.createError ("ATGOV-T10-R009"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                                 new TestDocument ("atgov-t10-fail-r010a.xml",
                                                                                                   ErrorDefinition.createError ("ATGOV-T10-R010"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                                 new TestDocument ("atgov-t10-fail-r010b.xml",
                                                                                                   ErrorDefinition.createError ("ATGOV-T10-R010"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                                 new TestDocument ("atgov-t10-fail-r011.xml",
                                                                                                   ErrorDefinition.createError ("ATGOV-T10-R011"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                                 new TestDocument ("atgov-t10-fail-r012.xml",
                                                                                                   ErrorDefinition.createError ("ATGOV-T10-R012"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                                 new TestDocument ("atgov-t10-fail-r013.xml",
                                                                                                   ErrorDefinition.createError ("ATGOV-T10-R013"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                                 new TestDocument ("atgov-t10-fail-r014.xml",
                                                                                                   ErrorDefinition.createError ("ATGOV-T10-R014"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                                 new TestDocument ("atgov-t10-fail-r015.xml",
                                                                                                   ErrorDefinition.createError ("ATGOV-T10-R015"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323")),
                                                                                 new TestDocument ("atgov-t10-fail-r016.xml",
                                                                                                   ErrorDefinition.createError ("ATGOV-T10-R016"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323")),
                                                                                 new TestDocument ("atgov-t10-fail-r017.xml",
                                                                                                   ErrorDefinition.createWarning ("ATGOV-T10-R005"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323"),
                                                                                                   ErrorDefinition.createWarning ("EUGEN-T10-R003")),
                                                                                 new TestDocument ("atgov-t10-fail-r018.xml",
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R263"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R266"),
                                                                                                   ErrorDefinition.createWarning ("BIICORE-T10-R323")) };

  /**
   * TC01.4.TS1.xml, TC01.5.TS1.xml, TC01.10.TS1.xml, TC01.39.TS1.xml,
   * TC01.40.TS1.xml, TC01.44.TS1.xml not XSD compliant
   */
  public static final TestDocument [] ORDERS_ERROR = new TestDocument [] { new TestDocument ("TC01.1.TS1.xml",
                                                                                             ErrorDefinition.createError ("BIIRULE-T01-R001")),
                                                                           new TestDocument ("TC01.2.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("BIICORE-T01-R000"),
                                                                                             ErrorDefinition.createError ("BIIRULE-T01-R002")),
                                                                           new TestDocument ("TC01.3.TS1.xml",
                                                                                             ErrorDefinition.createError ("BIIRULE-T01-R003")),
                                                                           new TestDocument ("TC01.6.TS1.xml",
                                                                                             ErrorDefinition.createError ("BIIRULE-T01-R027")),
                                                                           new TestDocument ("TC01.7.TS1.xml",
                                                                                             ErrorDefinition.createError ("BIIRULE-T01-R027")),
                                                                           new TestDocument ("TC01.8.TS1.xml",
                                                                                             ErrorDefinition.createError ("BIIRULE-T01-R030")),
                                                                           new TestDocument ("TC01.9.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("EUGEN-T01-R004")),
                                                                           new TestDocument ("TC01.11.TS1.xml",
                                                                                             ErrorDefinition.createError ("BIIRULE-T01-R008")),
                                                                           new TestDocument ("TC01.12.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("BIICORE-T01-R436"),
                                                                                             ErrorDefinition.createError ("BIIRULE-T01-R009")),
                                                                           new TestDocument ("TC01.13.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("BIICORE-T01-R439"),
                                                                                             ErrorDefinition.createError ("BIIRULE-T01-R010")),
                                                                           new TestDocument ("TC01.14.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("BIIRULE-T01-R015")),
                                                                           new TestDocument ("TC01.15.TS1.XML",
                                                                                             ErrorDefinition.createWarning ("BIICORE-T01-R080"),
                                                                                             ErrorDefinition.createWarning ("EUGEN-T01-R002"),
                                                                                             ErrorDefinition.createError ("BIIRULE-T01-R028")),
                                                                           new TestDocument ("TC01.16.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("EUGEN-T01-R001")),
                                                                           new TestDocument ("TC01.17.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("EUGEN-T01-R001")),
                                                                           new TestDocument ("TC01.18.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("EUGEN-T01-R001")),
                                                                           new TestDocument ("TC01.19.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("EUGEN-T01-R001")),
                                                                           new TestDocument ("TC01.20.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("EUGEN-T01-R001")),
                                                                           new TestDocument ("TC01.21.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("EUGEN-T01-R002")),
                                                                           new TestDocument ("TC01.22.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("EUGEN-T01-R002")),
                                                                           new TestDocument ("TC01.23.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("EUGEN-T01-R002")),
                                                                           new TestDocument ("TC01.24.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("EUGEN-T01-R002")),
                                                                           new TestDocument ("TC01.25.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("BIIRULE-T01-R018")),
                                                                           new TestDocument ("TC01.26.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("BIIRULE-T01-R019")),
                                                                           new TestDocument ("TC01.27.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("BIIRULE-T01-R020")),
                                                                           new TestDocument ("TC01.28.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("BIIRULE-T01-R021")),
                                                                           new TestDocument ("TC01.29.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("BIIRULE-T01-R021")),
                                                                           new TestDocument ("TC01.30.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("BIIRULE-T01-R021")),
                                                                           new TestDocument ("TC01.31.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("BIIRULE-T01-R021")),
                                                                           new TestDocument ("TC01.32.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("BIIRULE-T01-R021")),
                                                                           new TestDocument ("TC01.33.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("BIIRULE-T01-R029")),
                                                                           new TestDocument ("TC01.34.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("BIIRULE-T01-R018"),
                                                                                             ErrorDefinition.createWarning ("BIIRULE-T01-R021"),
                                                                                             ErrorDefinition.createError ("EUGEN-T01-R008")),
                                                                           new TestDocument ("TC01.35.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("BIIRULE-T01-R018"),
                                                                                             ErrorDefinition.createWarning ("BIIRULE-T01-R021"),
                                                                                             ErrorDefinition.createError ("EUGEN-T01-R009")),
                                                                           new TestDocument ("TC01.36.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("BIIRULE-T01-R019"),
                                                                                             ErrorDefinition.createError ("EUGEN-T01-R006")),
                                                                           new TestDocument ("TC01.37.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("BIIRULE-T01-R018"),
                                                                                             ErrorDefinition.createError ("BIIRULE-T01-R026"),
                                                                                             ErrorDefinition.createError ("EUGEN-T01-R009")),
                                                                           new TestDocument ("TC01.38.TS1.xml",
                                                                                             ErrorDefinition.createError ("BIIRULE-T01-R013")),
                                                                           new TestDocument ("TC01.41.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("EUGEN-T01-R005"),
                                                                                             ErrorDefinition.createError ("EUGEN-T01-R010")),
                                                                           new TestDocument ("TC01.42.TS1.xml",
                                                                                             ErrorDefinition.createError ("EUGEN-T01-R010")),
                                                                           new TestDocument ("TC01.43.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("BIIRULE-T01-R024")),
                                                                           new TestDocument ("TC01.45.TS1.xml",
                                                                                             ErrorDefinition.createError ("BIIRULE-T01-R011")),
                                                                           new TestDocument ("TC01.46.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("EUGEN-T01-R003")),
                                                                           new TestDocument ("TC01.47.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("EUGEN-T01-R003")),
                                                                           new TestDocument ("TC01.48.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("EUGEN-T01-R003")),
                                                                           new TestDocument ("TC01.49.TS1.xml",
                                                                                             ErrorDefinition.createWarning ("BIIRULE-T01-R020"),
                                                                                             ErrorDefinition.createWarning ("BIIRULE-T01-R021")) };

  private PeppolBISV1TestFiles ()
  {}

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsList <IReadableResource> getSuccessFiles (@Nonnull final EPeppolUBLTestFileType eFileType)
  {
    return getSuccessFiles (eFileType, null);
  }

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsList <IReadableResource> getSuccessFiles (@Nonnull final EPeppolUBLTestFileType eFileType,
                                                                  @Nullable final Locale aCountry)
  {
    ValueEnforcer.notNull (eFileType, "FileType");

    final String sCountry = aCountry == null ? null : aCountry.getCountry ();

    String [] aFilenames = new String [0];
    switch (eFileType)
    {
      case CALLFORTENDERS:
        aFilenames = CALLFORTENDERS_SUCCESS;
        break;
      case CATALOGUE:
        aFilenames = CATALOGUES_SUCCESS;
        break;
      case CREDITNOTE:
        if ("AT".equals (sCountry))
          aFilenames = CREDITNOTES_AT_SUCCESS;
        else
          aFilenames = CREDITNOTES_SUCCESS;
        break;
      case INVOICE:
        if ("AT".equals (sCountry))
          aFilenames = INVOICES_AT_SUCCESS;
        else
          aFilenames = ArrayHelper.getConcatenated (INVOICES_SUCCESS, INVOICES_AT_SUCCESS);
        break;
      case ORDER:
        aFilenames = ORDERS_SUCCESS;
        break;
      case ORDERRESPONSE:
        aFilenames = ORDERRESPONSES_SUCCESS;
        break;
      case TENDER:
        aFilenames = TENDER_SUCCESS;
        break;
      case TENDERINGCATALOGUE:
        aFilenames = TENDERINGCATALOGUES_SUCCESS;
        break;
      default:
        s_aLogger.warn ("No success test files present for type " + eFileType);
        break;
    }

    // Build result list
    final ICommonsList <IReadableResource> ret = new CommonsArrayList <> ();
    for (final String sFilename : aFilenames)
      ret.add (eFileType.getSuccessResource (sFilename));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsList <TestResource> getErrorFiles (@Nonnull final EPeppolUBLTestFileType eFileType)
  {
    return getErrorFiles (eFileType, null);
  }

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsList <TestResource> getErrorFiles (@Nonnull final EPeppolUBLTestFileType eFileType,
                                                           @Nullable final Locale aCountry)
  {
    ValueEnforcer.notNull (eFileType, "FileType");

    final String sCountry = aCountry == null ? null : aCountry.getCountry ();

    TestDocument [] aFilenames = new TestDocument [0];
    switch (eFileType)
    {
      case CREDITNOTE:
        if ("AT".equals (sCountry))
          aFilenames = CREDITNOTES_AT_ERROR;
        break;
      case INVOICE:
        if ("AT".equals (sCountry))
          aFilenames = INVOICES_AT_ERROR;
        else
          aFilenames = INVOICES_ERROR;
        break;
      case ORDER:
        aFilenames = ORDERS_ERROR;
        break;
      default:
        s_aLogger.warn ("No error test files present for type " + eFileType);
        break;
    }

    // Build result list
    final ICommonsList <TestResource> ret = new CommonsArrayList <> ();
    for (final TestDocument aTestDoc : aFilenames)
      ret.add (new TestResource (eFileType.getErrorResource (aTestDoc.getFilename ()),
                                 aTestDoc.getAllExpectedErrors ()));
    return ret;
  }
}
