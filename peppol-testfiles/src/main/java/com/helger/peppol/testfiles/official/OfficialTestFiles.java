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
package com.helger.peppol.testfiles.official;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.io.resource.ClassPathResource;

@Immutable
public final class OfficialTestFiles
{
  private static final ICommonsList <ClassPathResource> CATALOGUE_01_T19 = new CommonsArrayList <> ();
  private static final ICommonsList <ClassPathResource> CATALOGUE_01_T58 = new CommonsArrayList <> ();
  private static final ICommonsList <ClassPathResource> ORDER_03_T01 = new CommonsArrayList <> ();
  private static final ICommonsList <ClassPathResource> INVOICE_04_T10 = new CommonsArrayList <> ();
  private static final ICommonsList <ClassPathResource> BILLING_05_T14 = new CommonsArrayList <> ();
  private static final ICommonsList <ClassPathResource> ORDERING_28_T01 = new CommonsArrayList <> ();
  private static final ICommonsList <ClassPathResource> ORDERING_28_T76 = new CommonsArrayList <> ();
  private static final ICommonsList <ClassPathResource> DESPATCH_ADVICE_30_T16 = new CommonsArrayList <> ();

  @Nonnull
  private static ClassLoader _getCL ()
  {
    return OfficialTestFiles.class.getClassLoader ();
  }

  static
  {
    final String sPrefix = "/external/peppol-official";

    for (final String sFilename : new String [] { "Catalogue Use case 1.xml",
                                                  "Catalogue Use case 2.xml",
                                                  "Catalogue Use case 3.xml",
                                                  "Catalogue Use case 4.xml",
                                                  "Catalogue Use case 5.xml" })
      CATALOGUE_01_T19.add (new ClassPathResource (sPrefix + "/BIS01A/" + sFilename, _getCL ()));

    for (final String sFilename : new String [] { "Catalogue Response use case 1 TP.xml",
                                                  "Catalogue Response use case 2 TP.xml",
                                                  "Catalogue Response use case 3 TP.xml",
                                                  "Catalogue Response use case 4 TP.xml",
                                                  "Catalogue Response use case 5 TP.xml" })
      CATALOGUE_01_T58.add (new ClassPathResource (sPrefix + "/BIS01A/" + sFilename, _getCL ()));

    for (final String sFilename : new String [] { "UC1_Order.xml", "UC2_Order.xml", "UC3_Order.xml", "UC4_Order.xml" })
      ORDER_03_T01.add (new ClassPathResource (sPrefix + "/BIS03A/" + sFilename, _getCL ()));

    for (final String sFilename : new String [] { "good-bis4a-zero-amount.xml",
                                                  "Use Case 1.a_ExampleFile_PEPPOL BIS.xml",
                                                  "Use Case 1.b_ExampleFile_PEPPOL BIS.xml",
                                                  "Use Case 2_ExampleFile_PEPPOL BIS.xml",
                                                  "Use Case 3_ExampleFile_PEPPOL BIS.xml",
                                                  "Use Case 4_ExampleFile_PEPPOL BIS.xml",
                                                  "Use Case 5_ExampleFile_PEPPOL BIS.xml" })
      INVOICE_04_T10.add (new ClassPathResource (sPrefix + "/BIS04A/" + sFilename, _getCL ()));

    for (final String sFilename : new String [] { "good-bis5a-zero-amount.xml",
                                                  "Use Case 1.a_CreditNote_PEPPOL BIS.xml",
                                                  "Use Case 1.b_CreditNote_PEPPOL BIS.xml",
                                                  "Use Case 2_CreditNote_PEPPOL BIS.xml",
                                                  "Use Case 3_CreditNote_PEPPOL BIS.xml",
                                                  "Use Case 4_CreditNote_PEPPOL BIS.xml",
                                                  "Use Case 5_CreditNote_PEPPOL BIS.xml" })
      BILLING_05_T14.add (new ClassPathResource (sPrefix + "/BIS05A/" + sFilename, _getCL ()));

    for (final String sFilename : new String [] { "UC1_Order.xml", "UC2_Order.xml", "UC3_Order.xml", "UC4_Order.xml" })
      ORDERING_28_T01.add (new ClassPathResource (sPrefix + "/BIS28A/" + sFilename, _getCL ()));

    for (final String sFilename : new String [] { "UC1_Order_response.xml",
                                                  "UC2_Order_response.xml",
                                                  "UC3_Order_response.xml",
                                                  "UC4_Order_response.xml" })
      ORDERING_28_T76.add (new ClassPathResource (sPrefix + "/BIS28A/" + sFilename, _getCL ()));

    for (final String sFilename : new String [] { "UBL-DespatchAdvice-2_1BII2 openPEPPOL BIS_UseCase4.xml",
                                                  "UBL-DespatchAdvice-2_1BII2 openPEPPOL BIS_UseCase5.xml",
                                                  "UBL-DespatchAdvice-2.1BII2 openPEPPOL BIS_UseCase1.xml",
                                                  "UBL-DespatchAdvice-2.1BII2 openPEPPOL BIS_UseCase2.xml",
                                                  "UBL-DespatchAdvice-2.1BII2 openPEPPOL BIS_UseCase3.xml" })
      DESPATCH_ADVICE_30_T16.add (new ClassPathResource (sPrefix + "/BIS30A/" + sFilename, _getCL ()));
  }

  private OfficialTestFiles ()
  {}

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsList <ClassPathResource> getAllTestFilesCatalogue_01_T19 ()
  {
    return CATALOGUE_01_T19.getClone ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsList <ClassPathResource> getAllTestFilesCatalogue_01_T58 ()
  {
    return CATALOGUE_01_T58.getClone ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsList <ClassPathResource> getAllTestFilesOrder_03_T01 ()
  {
    return ORDER_03_T01.getClone ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsList <ClassPathResource> getAllTestFilesInvoice_04_T10 ()
  {
    return INVOICE_04_T10.getClone ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsList <ClassPathResource> getAllTestFilesBilling_05_T14 ()
  {
    return BILLING_05_T14.getClone ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsList <ClassPathResource> getAllTestFilesOrdering_28_T01 ()
  {
    return ORDERING_28_T01.getClone ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsList <ClassPathResource> getAllTestFilesOrdering_28_T76 ()
  {
    return ORDERING_28_T76.getClone ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsList <ClassPathResource> getAllTestFilesDespatchAdvice_30_T16 ()
  {
    return DESPATCH_ADVICE_30_T16.getClone ();
  }
}
