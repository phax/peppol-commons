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
package com.helger.peppol.testfiles.sbdh;

import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.ICommonsList;
import com.helger.io.resource.ClassPathResource;

import jakarta.annotation.Nonnull;

/**
 * This class contains all the test resources for SBDH
 *
 * @author Philip Helger
 */
public final class PeppolSBDHTestFiles
{
  private static final ICommonsList <ClassPathResource> GOOD_CASES = new CommonsArrayList <> ();
  private static final ICommonsList <ClassPathResource> BAD_CASES = new CommonsArrayList <> ();

  @Nonnull
  private static ClassLoader _getCL ()
  {
    return PeppolSBDHTestFiles.class.getClassLoader ();
  }

  static
  {
    final String sPrefix = "/external/sbdh";

    // good.xml must be the first file!
    for (final String sFilename : new String [] { "good.xml",
                                                  "good-additional-scopes.xml",
                                                  "good-bis3.xml",
                                                  "good-bis3-full.xml",
                                                  "good-bis3-with-country_c1.xml",
                                                  "good-bis3-with-mls-to.xml",
                                                  "good-bis3-with-mls-type.xml",
                                                  "good-gxs1.xml",
                                                  "good-hybrid-invoice.xml",
                                                  "good-order-with-qty-ph.xml",
                                                  "good-to-9915-test.xml",
                                                  "good-type-version-20.xml",
                                                  "good-v11.xml",
                                                  "good-with-timezone.xml" })
    {
      GOOD_CASES.add (new ClassPathResource (sPrefix + "/good/" + sFilename, _getCL ()));
    }

    for (final String sFilename : new String [] { "bad-no-xml.txt",
                                                  "bad-no-sbdh.xml",
                                                  "bad-invalid-header-version.xml",
                                                  "bad-too-few-senders.xml",
                                                  "bad-too-many-senders.xml",
                                                  "bad-invalid-sender-authority.xml",
                                                  "bad-invalid-sender-value.xml",
                                                  "bad-too-few-receivers.xml",
                                                  "bad-too-many-receivers.xml",
                                                  "bad-invalid-receiver-authority.xml",
                                                  "bad-invalid-receiver-value.xml",
                                                  "bad-mls-to-empty.xml",
                                                  "bad-mls-to-invalid-value.xml",
                                                  "bad-mls-to-no-scheme.xml",
                                                  "bad-mls-to-no-value.xml",
                                                  "bad-mls-type-empty.xml",
                                                  "bad-mls-type-invalid-value.xml",
                                                  "bad-no-business-scope.xml",
                                                  "bad-too-few-scopes.xml",
                                                  "bad-invalid-document-type-identifier.xml",
                                                  "bad-invalid-process-identifier.xml",
                                                  "bad-no-document-type-identifier.xml",
                                                  "bad-no-process-identifier.xml",
                                                  "bad-no-business-message.xml",
                                                  "bad-invalid-business-message.xml",
                                                  "bad-invalid-standard.xml",
                                                  "bad-invalid-type-version.xml",
                                                  "bad-invalid-type.xml",
                                                  "bad-invalid-instance-identifier.xml",
                                                  "bad-invalid-creation-date-and-time.xml" })
    {
      BAD_CASES.add (new ClassPathResource (sPrefix + "/bad/" + sFilename, _getCL ()));
    }
  }

  private PeppolSBDHTestFiles ()
  {}

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsList <ClassPathResource> getAllGoodCases ()
  {
    return GOOD_CASES.getClone ();
  }

  @Nonnull
  public static ClassPathResource getFirstGoodCase ()
  {
    return GOOD_CASES.getFirstOrNull ();
  }

  @Nonnull
  public static ClassPathResource getFirstGoodCaseV11 ()
  {
    return GOOD_CASES.findFirst (x -> x.getPath ().endsWith ("good-v11.xml"));
  }

  @Nonnull
  public static ClassPathResource getFirstGoodCaseV20 ()
  {
    return GOOD_CASES.findFirst (x -> x.getPath ().endsWith ("good-bis3-with-country_c1.xml"));
  }

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsList <ClassPathResource> getAllBadCases ()
  {
    return BAD_CASES.getClone ();
  }

  @Nonnull
  public static ClassPathResource getFirstBadCase ()
  {
    return BAD_CASES.getFirstOrNull ();
  }
}
