/**
 * Copyright (C) 2015-2019 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.identifier;

import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.PresentForCodeCoverage;

/**
 * Constants on BUSDOX identifiers that are not PEPPOL specific.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Immutable
public final class CIdentifier
{
  /**
   * The default process identifier to indicate that no default process belongs
   * to it.<br>
   * See PEPPOL Common definitions chapter 3.6
   */
  public static final String DEFAULT_PROCESS_IDENTIFIER_NOPROCESS = "busdox:noprocess";

  /**
   * The delimiter used for the service group identifier ("::").
   */
  public static final String URL_SCHEME_VALUE_SEPARATOR = "::";

  @PresentForCodeCoverage
  private static final CIdentifier s_aInstance = new CIdentifier ();

  private CIdentifier ()
  {}
}
