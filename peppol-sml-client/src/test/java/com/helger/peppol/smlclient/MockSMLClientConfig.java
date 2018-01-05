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
package com.helger.peppol.smlclient;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.settings.exchange.configfile.ConfigFile;
import com.helger.settings.exchange.configfile.ConfigFileBuilder;

/**
 * This class manages the special test configuration file for this project. The
 * configuration file is located in
 * <code>src/test/resources/sml-client-test.properties</code>
 *
 * @author Philip Helger
 */
@Immutable
public final class MockSMLClientConfig
{
  private static final ConfigFile s_aConfig = new ConfigFileBuilder ().addPath ("private-sml-client-test.properties")
                                                                      .addPath ("sml-client-test.properties")
                                                                      .build ();

  // init
  static
  {
    // Apply system properties
    s_aConfig.applyAllNetworkSystemProperties ();
  }

  private MockSMLClientConfig ()
  {}

  @Nullable
  public static String getKeyStorePath ()
  {
    return s_aConfig.getAsString ("keystore.path");
  }

  @Nullable
  public static String getKeyStorePassword ()
  {
    return s_aConfig.getAsString ("keystore.password");
  }
}
