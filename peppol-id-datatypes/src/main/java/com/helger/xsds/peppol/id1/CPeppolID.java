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
package com.helger.xsds.peppol.id1;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.io.resource.ClassPathResource;

/**
 * Constants on Peppol identifiers.
 *
 * @author Philip Helger
 */
@Immutable
public final class CPeppolID
{
  public static final String NS_URI_PEPPOL_IDENTIFIERS = "http://busdox.org/transport/identifiers/1.0/";

  public static final String NS_URI_PEPPOL_CODELISTS = "";

  @PresentForCodeCoverage
  private static final CPeppolID INSTANCE = new CPeppolID ();

  private CPeppolID ()
  {}

  @Nonnull
  private static ClassLoader _getCL ()
  {
    return CPeppolID.class.getClassLoader ();
  }

  @Nonnull
  public static ClassPathResource getXSDResourcePeppolIdentifiers ()
  {
    return new ClassPathResource ("/external/schemas/peppol-identifiers-v1.xsd", _getCL ());
  }

  @Nonnull
  public static ClassPathResource getXSDPeppolCodeLists ()
  {
    return new ClassPathResource ("/external/schemas/peppol-codelists-v2.5.xsd", _getCL ());
  }
}
