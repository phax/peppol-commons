/*
 * Copyright (C) 2024 Philip Helger
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
package com.helger.peppol.xhe;

import javax.annotation.concurrent.Immutable;

/**
 * Constants for the usage of XHE in DBNAlliance.
 *
 * @author Robinson Garcia
 */
@Immutable
public final class CDBNAllianceXHE
{
  /** The expected XHE version */
  public static final String XHE_VERSION_ID = "1.0";

  /** Constant for the Data Model */
  public static final String CUSTOMIZATION_ID = "http://docs.oasisopen.org/bdxr/ns/XHE/1/ExchangeHeaderEnvelope::XHE##dbnalliance-envelope-1.0";

  /** Constant for the identifier scheme */
  public static final String CUSTOMIZATION_SCHEMA_ID = "bdx-docid-qns";

  /** Constant for the reference to the DBNAlliance specification */
  public static final String PROFILE_ID = "dbnalliance-envelope-1.0";

  private CDBNAllianceXHE ()
  {}
}
