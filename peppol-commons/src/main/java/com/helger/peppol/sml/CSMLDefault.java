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
package com.helger.peppol.sml;

import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.PresentForCodeCoverage;

/**
 * This file contains default constants for the usage of the SML. It is strongly
 * recommended that the real values are out-sourced into a configuration file.
 * You can of course use the values in this file as the default values in case
 * no configuration item is specified.
 *
 * @author Philip Helger
 */
@Immutable
public final class CSMLDefault
{
  /**
   * The DNS zone name part used for SMP information in the SML.
   */
  public static final String DNS_PUBLISHER_SUBZONE = "publisher.";

  /**
   * The name of the service to manage SMP meta data. Valid for all SML
   * instances.
   */
  public static final String MANAGEMENT_SERVICE_METADATA = "manageservicemetadata";

  /**
   * The name of the service to manage participant identifiers. Valid for all
   * SML instances.
   */
  public static final String MANAGEMENT_SERVICE_PARTICIPANTIDENTIFIER = "manageparticipantidentifier";

  /**
   * The maximum length of a migration code identifier.<br>
   * New limit imposed by SMK 3: 24
   */
  public static final int MAX_MIGRATION_CODE_LENGTH = 24;

  /**
   * The migration code pattern as defined by BDMSL since v1.01.
   * 
   * @since 8.1.2
   */
  public static final String MIGRATION_CODE_PATTERN = "^(?=.{8,24}$)(?=(.*[@#$%()\\[\\]{}*^_\\-!~|+=]){2,})(?=(.*[A-Z]){2})(?=(.*[a-z]){2})(?=(.*[0-9]){2})(?=\\S+$).*$";

  @PresentForCodeCoverage
  private static final CSMLDefault INSTANCE = new CSMLDefault ();

  private CSMLDefault ()
  {}
}
