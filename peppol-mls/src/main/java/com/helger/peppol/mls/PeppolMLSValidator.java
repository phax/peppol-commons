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
package com.helger.peppol.mls;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.base.exception.InitializationException;
import com.helger.schematron.ISchematronResource;
import com.helger.schematron.xslt.SchematronResourceXSLT;

/**
 * This class can be used to trigger Schematron validation of Peppol MLS documents.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public final class PeppolMLSValidator
{
  public static final String SCH_MLS_101_PATH = "external/schematron/peppol-mls-1.0.1.sch";

  private static final ISchematronResource SCH_MLS_101 = SchematronResourceXSLT.builderFromClassPath (SCH_MLS_101_PATH.replace (".sch",
                                                                                                                                ".xslt"))
                                                                               .build ();

  static
  {
    for (final ISchematronResource aSch : new ISchematronResource [] { SCH_MLS_101 })
      if (!aSch.isValidSchematron ())
        throw new InitializationException ("Schematron in " + aSch.getResource ().getPath () + " is invalid");
  }

  private PeppolMLSValidator ()
  {}

  /**
   * @return Schematron MLS v1.x
   * @since 12.10.0
   */
  @NonNull
  public static ISchematronResource getSchematronMLS_1 ()
  {
    return SCH_MLS_101;
  }

  /**
   * @return Schematron MLS v1.0.x
   * @since 12.10.0
   */
  @NonNull
  public static ISchematronResource getSchematronMLS_10 ()
  {
    return SCH_MLS_101;
  }

  /**
   * @return Schematron MLS v1.0.1
   */
  @NonNull
  public static ISchematronResource getSchematronMLS_101 ()
  {
    return SCH_MLS_101;
  }
}
