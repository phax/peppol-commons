/**
 * Copyright (C) 2015-2018 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
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
 * @author PEPPOL.AT, BRZ, Philip Helger
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

  @PresentForCodeCoverage
  private static final CSMLDefault s_aInstance = new CSMLDefault ();

  private CSMLDefault ()
  {}
}
