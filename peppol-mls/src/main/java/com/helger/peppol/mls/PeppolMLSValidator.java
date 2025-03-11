/*
 * Copyright (C) 2022-2025 Philip Helger
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

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.exception.InitializationException;
import com.helger.schematron.ISchematronResource;
import com.helger.schematron.sch.SchematronResourceSCH;

/**
 * This class can be used to trigger Schematron validation of Peppol MLS
 * documents.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public final class PeppolMLSValidator
{
  public static final String SCH_MLS_100_PATH = "external/schematron/peppol-mls-1.0.0.sch";

  private static final ISchematronResource SCH_MLS_100 = SchematronResourceSCH.fromClassPath (SCH_MLS_100_PATH);

  static
  {
    for (final ISchematronResource aSch : new ISchematronResource [] { SCH_MLS_100 })
      if (!aSch.isValidSchematron ())
        throw new InitializationException ("Schematron in " + aSch.getResource ().getPath () + " is invalid");
  }

  private PeppolMLSValidator ()
  {}

  /**
   * @return Schematron MLS v1.0.0
   */
  @Nonnull
  public static ISchematronResource getSchematronMLS_100 ()
  {
    return SCH_MLS_100;
  }
}
