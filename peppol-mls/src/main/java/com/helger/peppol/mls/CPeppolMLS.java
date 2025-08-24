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
package com.helger.peppol.mls;

import com.helger.annotation.concurrent.Immutable;

/**
 * Constants for Peppol MLS (Message Level Status)
 *
 * @author Philip Helger
 */
@Immutable
public final class CPeppolMLS
{
  public static final String MLS_CUSTOMIZATION_ID = "urn:peppol:edec:mls:1.0";
  public static final String MLS_PROFILE_ID = "urn:peppol:edec:mls";

  public static final String LINE_ID_NOT_AVAILABLE = "NA";

  /** The official Participant Identifier Scheme for SPIS */
  public static final String SPIS_PARTICIPANT_ID_SCHEME = "0242";

  private CPeppolMLS ()
  {}
}
