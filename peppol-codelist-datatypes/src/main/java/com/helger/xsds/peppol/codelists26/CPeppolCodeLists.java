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
package com.helger.xsds.peppol.codelists26;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.PresentForCodeCoverage;
import com.helger.io.resource.ClassPathResource;

/**
 * Constants on the Peppol Code Lists.
 *
 * @author Philip Helger
 */
@Immutable
public final class CPeppolCodeLists
{
  /** The Peppol Code List XSD has no target namespace */
  public static final String NS_URI_PEPPOL_CODELISTS = "";

  @PresentForCodeCoverage
  private static final CPeppolCodeLists INSTANCE = new CPeppolCodeLists ();

  private CPeppolCodeLists ()
  {}

  @NonNull
  private static ClassLoader _getCL ()
  {
    return CPeppolCodeLists.class.getClassLoader ();
  }

  @NonNull
  public static ClassPathResource getXSDPeppolCodeLists ()
  {
    return new ClassPathResource ("/external/schemas/peppol-codelists-v2.6.xsd", _getCL ());
  }
}
