/*
 * Copyright (C) 2025 Philip Helger
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
package com.helger.hredelivery.commons;

import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.PresentForCodeCoverage;

/**
 * Constants for the usage of HR eDelivery.
 *
 * @author Philip Helger
 * @since 12.1.1
 */
@Immutable
public final class CHREDelivery
{
  /** The transport profile to be used. */
  public static final String TRANSPORT_PROFILE_HR_EDELIVERY = "eracun-transport-as4-v1_0";

  @PresentForCodeCoverage
  private static final CHREDelivery INSTANCE = new CHREDelivery ();

  private CHREDelivery ()
  {}
}
