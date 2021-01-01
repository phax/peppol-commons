/**
 * Copyright (C) 2015-2021 Philip Helger
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
package com.helger.peppolid.bdxr.smp1;

import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.PresentForCodeCoverage;

/**
 * Constants of OASIS BDXR SMP v1 specification.
 *
 * @author Philip Helger
 */
@Immutable
public final class CBDXR1Identifier
{
  /**
   * The default document identifier scheme.
   */
  public static final String DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME = "bdx-docid-qns";

  /**
   * The default process identifier scheme.
   */
  public static final String DEFAULT_PROCESS_IDENTIFIER_SCHEME = "bdx-procid-transport";

  /**
   * The default process identifier to indicate that no default process belongs
   * to it. Must be treated case insensitive.
   */
  public static final String DEFAULT_PROCESS_IDENTIFIER_NOPROCESS = "bdx:noprocess";

  @PresentForCodeCoverage
  private static final CBDXR1Identifier s_aInstance = new CBDXR1Identifier ();

  private CBDXR1Identifier ()
  {}
}
