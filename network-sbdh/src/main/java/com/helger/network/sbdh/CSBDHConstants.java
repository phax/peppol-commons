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
package com.helger.network.sbdh;

import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.PresentForCodeCoverage;

/**
 * Constants of the Standard Business Document Header that are not specific to a single network.
 *
 * @author Philip Helger
 * @since 13.0.0
 */
@Immutable
public final class CSBDHConstants
{
  /** The expected SBDH header version */
  public static final String HEADER_VERSION = "1.0";

  /** The BusinessScope Type value of the document type identifier */
  public static final String SCOPE_DOCUMENT_TYPE_ID = "DOCUMENTID";

  /** The BusinessScope Type value of the process identifier */
  public static final String SCOPE_PROCESS_ID = "PROCESSID";

  @PresentForCodeCoverage
  private static final CSBDHConstants INSTANCE = new CSBDHConstants ();

  private CSBDHConstants ()
  {}
}
