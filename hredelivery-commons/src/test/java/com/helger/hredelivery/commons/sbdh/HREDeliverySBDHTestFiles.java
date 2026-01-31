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
package com.helger.hredelivery.commons.sbdh;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.ICommonsList;
import com.helger.io.resource.ClassPathResource;

/**
 * This class contains all the test resources for SBDH
 *
 * @author Philip Helger
 */
public final class HREDeliverySBDHTestFiles
{
  private static final ICommonsList <ClassPathResource> GOOD_CASES = new CommonsArrayList <> ();
  private static final ICommonsList <ClassPathResource> BAD_CASES = new CommonsArrayList <> ();

  @NonNull
  private static ClassLoader _getCL ()
  {
    return HREDeliverySBDHTestFiles.class.getClassLoader ();
  }

  static
  {
    final String sPrefix = "/external/sbdh";

    // good.xml must be the first file!
    for (final String sFilename : new String [] { "good.xml",
                                                  "good-bis3.xml",
                                                  "good-gxs1.xml",
                                                  "good-order-with-qty-ph.xml",
                                                  "good-with-timezone.xml" })
    {
      GOOD_CASES.add (new ClassPathResource (sPrefix + "/good/" + sFilename, _getCL ()));
    }

    for (final String sFilename : new String [] { "bad-invalid-business-message.xml",
                                                  "bad-invalid-creation-date-and-time.xml",
                                                  "bad-invalid-header-version.xml",
                                                  "bad-invalid-instance-identifier.xml",
                                                  "bad-invalid-receiver-authority.xml",
                                                  "bad-invalid-receiver-value.xml",
                                                  "bad-invalid-sender-authority.xml",
                                                  "bad-invalid-sender-value.xml",
                                                  "bad-no-business-message.xml",
                                                  "bad-no-sbdh.xml",
                                                  "bad-no-xml.txt",
                                                  "bad-too-few-receivers.xml",
                                                  "bad-too-few-scopes.xml",
                                                  "bad-too-few-senders.xml",
                                                  "bad-too-many-receivers.xml",
                                                  "bad-too-many-senders.xml" })
    {
      BAD_CASES.add (new ClassPathResource (sPrefix + "/bad/" + sFilename, _getCL ()));
    }
  }

  private HREDeliverySBDHTestFiles ()
  {}

  @NonNull
  @ReturnsMutableCopy
  public static ICommonsList <ClassPathResource> getAllGoodCases ()
  {
    return GOOD_CASES.getClone ();
  }

  @NonNull
  public static ClassPathResource getFirstGoodCase ()
  {
    return GOOD_CASES.getFirstOrNull ();
  }

  @NonNull
  @ReturnsMutableCopy
  public static ICommonsList <ClassPathResource> getAllBadCases ()
  {
    return BAD_CASES.getClone ();
  }

  @NonNull
  public static ClassPathResource getFirstBadCase ()
  {
    return BAD_CASES.getFirstOrNull ();
  }
}
