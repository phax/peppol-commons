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

import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.PresentForCodeCoverage;

/**
 * Constants for the usage of SBDH headers in HR eDelivery.
 *
 * @author Philip Helger
 */
@Immutable
public final class CHREDeliverySBDH
{
  /** The expected SBDH header version */
  public static final String HEADER_VERSION = "1.0";

  /** UBL 2.1 constant */
  public static final String TYPE_VERSION_21 = "2.1";

  @PresentForCodeCoverage
  private static final CHREDeliverySBDH INSTANCE = new CHREDeliverySBDH ();

  private CHREDeliverySBDH ()
  {}
}
