/**
 * Copyright (C) 2015-2020 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
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
